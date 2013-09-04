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
import java.util.List;

/** A representation of a stored region */
public class Region {

    private String id;
    private String owner;
    private String[] members;
    private Flag[] flags;
    private String world;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int minZ;
    private int maxZ;

    public Region(String id, String owner, String[] members, Flag[] flags, String world, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        this.id = id;
        this.owner = owner;
        this.members = members;
        this.flags = flags;
        this.world = world;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public Region(String id, String owner, String[] members, Flag[] flags, Location minLoc, Location maxLoc) {
        this(id, owner, members, flags, minLoc.getWorld().getName(), minLoc.getBlockX(), maxLoc.getBlockX(), minLoc.getBlockY(), maxLoc.getBlockY(), minLoc.getBlockZ(), maxLoc.getBlockZ());
    }

    public Region(String id, String owner, String[] members, Flag[] flags, String world, Vector minLoc, Vector maxLoc) {
        this(id, owner, members, flags, world, minLoc.getBlockX(), maxLoc.getBlockX(), minLoc.getBlockY(), maxLoc.getBlockY(), minLoc.getBlockZ(), maxLoc.getBlockZ());
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
     * Get a list of members of the region
     *
     * @return List of members of the region
     */
    public String[] getMembers() {
        return members;
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
        return new Vector(minX, minY, minZ);
    }

    /**
     * Get the maximum boundary of the region
     *
     * @return Location of the maximum boundary
     */
    public Vector getMaxBoundary() {
        return new Vector(maxX, maxY, maxZ);
    }

    private void setMinMaxBoundaries(List<Vector> points) {

    }
}
