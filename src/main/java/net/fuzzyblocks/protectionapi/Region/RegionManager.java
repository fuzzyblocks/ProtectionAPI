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

import net.fuzzyblocks.protectionapi.ProtectionAPI;
import net.fuzzyblocks.protectionapi.storage.FlatFileStore;
import net.fuzzyblocks.protectionapi.storage.ProtectionStore;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Region manager to handle the creation and modification of regions in
 * ProtectionAPI
 */
public class RegionManager {

    private ProtectionAPI api;
    private ProtectionStore store;

    public RegionManager(ProtectionAPI instance) {
        this.api = instance;

        switch (api.getConfig().getString("Storage", "Flat").toLowerCase()) {
            case "mysql":
                // Select MySQL storage
            default:
                //Select flat file storage
                store = new FlatFileStore(new File(api.getDataFolder(), "regions.yml"));
        }
    }

    /**
     * Create and store a region with the specified parameters
     *
     * @param id         ID to create region with
     * @param owner      Name of the owner to create the region with
     * @param members    Members to add to the region
     * @param flags      Flags to add to the region
     * @param minimumLoc Location of one corner of cuboid
     * @param maximumLoc Location of the other corner of the cuboid
     */
    public void createRegion(String id, String owner, String[] members, Flag[] flags, Location minimumLoc, Location maximumLoc) {
        api.debug("Creating region: " + id + " for owner: " + owner);
        store.storeRegion(new Region(id, owner, members, flags, minimumLoc, maximumLoc));
    }

    /**
     * Add members to an already created region
     *
     * @param region  Region to add members to
     * @param members Members to add to the region
     */
    public void addMembersToRegion(Region region, String[] members) {
        api.debug("Adding members: " + members.toString() + " to region: " + region.getId());
        region.addMembers(members);
        store.storeRegion(region);
    }

    /**
     * Get a set of regions at a point
     *
     * @param location Location of point to check for regions
     * @return Set containing all regions at the Location
     */
    public Set getRegionsAtPoint(Location location) {
        api.debug("Getting regions at point: " + location.getX() + ":" + location.getY() + ":" + location.getZ());
        Map regions = store.getRegionList();
        Set<Region> regionsAtPoint = new HashSet();
        for (Object object : regions.values()) {
            Region region = (Region) object;
            if (regionContainsPoint(region, location))
                regionsAtPoint.add(region);

        }
        return regionsAtPoint;
    }

    /**
     * Check whether a region contains a point
     *
     * @param region   Region to check
     * @param location Location to check
     * @return True if region contains location, else false
     */
    public boolean regionContainsPoint(Region region, Location location) {
        Vector vector = location.toVector();
        Vector minBoundary = region.getMinBoundary();
        Vector maxBoundary = region.getMaxBoundary();

        if (location.getWorld() == region.getWorld())
            if ((maxBoundary.getBlockZ() > vector.getBlockZ()) && (vector.getBlockZ() > minBoundary.getBlockZ()))
                if ((maxBoundary.getBlockY() > vector.getBlockY()) && (vector.getBlockY() > minBoundary.getBlockY()))
                    if ((maxBoundary.getBlockX() > vector.getBlockX()) && (vector.getBlockX() > minBoundary.getBlockX()))
                        //TODO: Optimise checking if region contains point
                        return true;
        return false;
    }
}
