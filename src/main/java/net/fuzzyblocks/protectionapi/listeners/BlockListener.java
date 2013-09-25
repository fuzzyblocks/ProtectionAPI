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
package net.fuzzyblocks.protectionapi.listeners;

import net.fuzzyblocks.protectionapi.ProtectionAPI;
import net.fuzzyblocks.protectionapi.events.NoPermBlockBreakEvent;
import net.fuzzyblocks.protectionapi.events.NoPermBlockDamageEvent;
import net.fuzzyblocks.protectionapi.events.NoPermBlockIgniteEvent;
import net.fuzzyblocks.protectionapi.events.NoPermBlockPlaceEvent;
import net.fuzzyblocks.protectionapi.region.RegionManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/** This class handles any interactions with blocks on the minecraft server. */
public class BlockListener extends ProtectionAPIListener {

    private final RegionManager regionManager;

    public BlockListener(ProtectionAPI instance) {
        super(instance);
        regionManager = plugin.getRegionManager();
    }

    /** Called when a block is damaged. Currently only used for Cake. */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block damagedBlock = event.getBlock();

        if (damagedBlock.getType() == Material.CAKE_BLOCK) {
            //Check if player can build
            if (!plugin.getRegionManager().canBuildAtPoint(player.getName(), damagedBlock.getLocation())) {
                NoPermBlockDamageEvent noPermBlockDamageEvent = new NoPermBlockDamageEvent(player, damagedBlock,
                        regionManager.getRegionsAtPoint(damagedBlock.getLocation()));
                plugin.fireEvent(noPermBlockDamageEvent);
                if (noPermBlockDamageEvent.isPrevented()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /** Called when a block is broken. */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block brokenBlock = event.getBlock();

        //Check if player can build
        if (!plugin.getRegionManager().canBuildAtPoint(player.getName(), brokenBlock.getLocation())) {
            NoPermBlockBreakEvent noPermBlockBreakEvent = new NoPermBlockBreakEvent(player, brokenBlock,
                    regionManager.getRegionsAtPoint(brokenBlock.getLocation()));
            plugin.fireEvent(noPermBlockBreakEvent);
            if (noPermBlockBreakEvent.isPrevented()) {
                event.setCancelled(true);
            }
        }
    }

    /** Called when a block gets ignited */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent event) {
        BlockIgniteEvent.IgniteCause igniteCause = event.getCause();
        Block ignitedBlock = event.getBlock();
        //Check if fire is caused by a player
        if (igniteCause == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL
                || igniteCause == BlockIgniteEvent.IgniteCause.FIREBALL
                && event.getPlayer() != null) {
            //Check if player can build
            if (!plugin.getRegionManager().canBuildAtPoint(event.getPlayer().getName(), ignitedBlock.getLocation())) {
                NoPermBlockIgniteEvent noPermBlockIgniteEvent = new NoPermBlockIgniteEvent(event.getPlayer(), ignitedBlock,
                        regionManager.getRegionsAtPoint(ignitedBlock.getLocation()));
                plugin.fireEvent(noPermBlockIgniteEvent);
                if (noPermBlockIgniteEvent.isPrevented()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /** Called when a block is placed */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block placedBlock = event.getBlock();
        Player player = event.getPlayer();

        //Check if a player can build
        if (!plugin.getRegionManager().canBuildAtPoint(player.getName(), placedBlock.getLocation())) {
            NoPermBlockPlaceEvent noPermBlockPlaceEvent = new NoPermBlockPlaceEvent(player, placedBlock,
                    regionManager.getRegionsAtPoint(placedBlock.getLocation()));
            plugin.fireEvent(noPermBlockPlaceEvent);
            if (noPermBlockPlaceEvent.isPrevented()) {
                event.setCancelled(true);
            }
        }
    }
}
