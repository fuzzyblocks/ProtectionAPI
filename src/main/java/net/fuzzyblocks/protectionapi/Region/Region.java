/*
 * Copyright (c) 2013, LankyLord
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.fuzzyblocks.protectionapi.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** A representation of a stored region */
public class Region {

    private String id;
    private String owner;
    private String[] members;
    private Flag[] flags;
    private String world;
    private Vector minBoundary;
    private Vector maxBoundary;

    public Region(String id, String owner, String[] members, Flag[] flags, String world, Vector minBoundary, Vector maxBoundary) {
        this.id = id;
        this.owner = owner;
        this.members = members;
        this.flags = flags;
        this.world = world;
        this.setMinMaxBoundaries(minBoundary, maxBoundary);
    }

    public Region(String id, String owner, String[] members, Flag[] flags, String world, Location minLoc, Location maxLoc) {
        this(id, owner, members, flags, world, minLoc.toVector(), maxLoc.toVector());
    }

    /**
     * Get the Id of a region
     *
     * @return The Id of the region
     */
    public String getId() {
        return id;
    }

    /**
     * Get the owner of the region
     *
     * @return The name of the owner of the region
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the owner of the region
     *
     * @param owner Name of the new owner of the region
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get a list of members of the region
     *
     * @return List of members of the region
     */
    public String[] getMembers() {
        return members;
    }

    /**
     * Set the members of a region
     *
     * @param members Array of members
     */
    public void setMembers(String[] members) {
        this.members = members;
    }

    /**
     * Add members to the region
     *
     * @param members Members to add to the region
     */
    public void addMembers(String[] members) {
        List<String> newMembers = new ArrayList<>();
        for (String s : members)
            newMembers.add(s);
        for (String s : getMembers())
            newMembers.add(s);
        this.members = newMembers.toArray(new String[0]);
    }

    /**
     * Get a list of flags stored in the region
     *
     * @return List of flags stored in the region
     */
    public Flag[] getFlags() {
        return flags;
    }

    /**
     * Set the flags of the region
     *
     * @param flags Array of flags
     */
    public void setFlags(Flag[] flags) {
        this.flags = flags;
    }

    /**
     * Set a flag of the region
     *
     * @param flag Flag to set
     */
    public void setFlag(Flag flag) {
        List<Flag> newFlags = new ArrayList<>();
        for (Flag f : flags)
            newFlags.add(f);
        newFlags.add(flag);
        this.flags = newFlags.toArray(new Flag[0]);
    }

    /**
     * Get the world stored in the region
     *
     * @return The world stored in the region
     */
    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    /**
     * Get the minimum boundary of the region
     *
     * @return Location of the minimum boundary
     */
    public Vector getMinBoundary() {
        return this.minBoundary;
    }

    /**
     * Get the maximum boundary of the region
     *
     * @return Location of the maximum boundary
     */
    public Vector getMaxBoundary() {
        return this.maxBoundary;
    }

    /**
     * Set the minimum and maximum boundaries from a two vectors
     *
     * @param vector1 One of the boundaries
     * @param vector2 The other boundary
     */
    private void setMinMaxBoundaries(Vector vector1, Vector vector2) {
        int minX = Math.min(vector1.getBlockX(), vector2.getBlockX());
        int minY = Math.min(vector1.getBlockY(), vector2.getBlockY());
        int minZ = Math.min(vector1.getBlockZ(), vector2.getBlockY());
        int maxX = Math.max(vector1.getBlockX(), vector2.getBlockX());
        int maxY = Math.max(vector1.getBlockY(), vector2.getBlockY());
        int maxZ = Math.max(vector1.getBlockZ(), vector2.getBlockY());

        this.minBoundary = new Vector(minX, minY, minZ);
        this.maxBoundary = new Vector(maxX, maxY, maxZ);
    }

    /**
     * Check if the region contains a point
     *
     * @param location Location to check region for
     * @return True if region contains point, else false
     */
    public boolean containsPoint(Location location) {
        if (location.getWorld() == this.getWorld())
            if ((maxBoundary.getBlockZ() > location.getBlockZ()) &&
                    (location.getBlockZ() > minBoundary.getBlockZ()))
                if ((maxBoundary.getBlockY() > location.getBlockY()) &&
                        (location.getBlockY() > minBoundary.getBlockY()))
                    if ((maxBoundary.getBlockX() > location.getBlockX()) &&
                            (location.getBlockX() > minBoundary.getBlockX()))
                        //TODO: Optimise checking if region contains point
                        return true;
        return false;
    }

    public boolean canBuild(String playerName) {
        Boolean buildFlag = false;
        for (Flag flag : flags) {
            if (flag.getFlagName() == "build")
                if (flag.getFlagState()) {
                    buildFlag = true;
                }
        }

        if (owner == playerName ||
                Arrays.asList(members).contains(playerName) ||
                buildFlag) {
            return true;
        }
        if (Bukkit.getPlayer(playerName) != null) {
            if (Bukkit.getPlayer(playerName).hasPermission("protectionapi.bypass")) {
                return true;
            }
        }
        return false;
    }
}
