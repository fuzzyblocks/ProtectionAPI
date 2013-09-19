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
package net.fuzzyblocks.protectionapi.storage;

import net.fuzzyblocks.protectionapi.Region.Flag;
import net.fuzzyblocks.protectionapi.Region.Region;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;

public class FlatFileStore implements ProtectionStore {

    private static final String OWNER_FIELD = "owner:";
    private static final String MEMBER_FIELD = "members:";
    private static final String FLAGS_FIELD = "flags:";
    private static final String WORLD_FIELD = "world:";
    private static final String MINIMUM_BOUNDARIES_FIELD = "min:";
    private static final String MAXIMUM_BOUNDARIES_FIELD = "max:";
    private Map<String, Region> regions;
    private final File file;

    public FlatFileStore(File storage) {
        file = storage;
        fromDisk();
    }

    @Override
    public void storeRegion(Region region) {
        regions.put(region.getId(), region);
        toDisk();
    }

    @Override
    public Map getRegionList() {
        return regions;
    }

    private void fromDisk() {
        regions = new HashMap<>();
        YamlConfiguration regionList = YamlConfiguration.loadConfiguration(file);

        for (String key : regionList.getKeys(false)) {
            ConfigurationSection cs = regionList.getConfigurationSection(key);

            String owner = cs.getString(OWNER_FIELD);
            String[] members = (String[]) cs.get(MEMBER_FIELD);
            Flag[] flags = stringsToFlags((String[]) cs.get(FLAGS_FIELD));
            String world = cs.getString(WORLD_FIELD);
            Vector minimumBoundary = parseVectorInput(cs.getString(MINIMUM_BOUNDARIES_FIELD));
            Vector maximumBoundary = parseVectorInput(cs.getString(MAXIMUM_BOUNDARIES_FIELD));

            Region region = new Region(key, owner, members, flags, world, minimumBoundary, maximumBoundary);
            regions.put(key, region);
        }
    }

    //TODO: Parse flags to strings
    private Flag[] stringsToFlags(String[] strings) {
        return new Flag[0];
    }

    public void toDisk() {
        YamlConfiguration regionlist = new YamlConfiguration();
        List<Region> regionsList = new ArrayList<>();

        for (Region region : regions.values()) {
            regionsList.add(region);
        }

        for (Region region : regionsList) {
            ConfigurationSection cs = regionlist.createSection(region.getId().toLowerCase());

            cs.set(OWNER_FIELD, region.getOwner());
            cs.set(MEMBER_FIELD, region.getMembers());
            cs.set(FLAGS_FIELD, region.getFlags());
            cs.set(WORLD_FIELD, region.getWorld().getName());
            cs.set(MINIMUM_BOUNDARIES_FIELD, parseVectorOutput(region.getMinBoundary()));
            cs.set(MAXIMUM_BOUNDARIES_FIELD, parseVectorOutput(region.getMaxBoundary()));
        }
    }

    private String parseVectorOutput(Vector vector) {
        return vector.getBlockX() + ":" + vector.getBlockY() + ":" + vector.getBlockZ();
    }

    private Vector parseVectorInput(String input) {
        List<String> list = Arrays.asList(input.split(":"));
        int x, y, z;
        x = Integer.parseInt(list.get(0));
        y = Integer.parseInt(list.get(1));
        z = Integer.parseInt(list.get(2));
        return new Vector(x, y, z);
    }

    @Override
    public Region getRegion(String regionName) {
        return regions.get(regionName.toLowerCase());
    }

}
