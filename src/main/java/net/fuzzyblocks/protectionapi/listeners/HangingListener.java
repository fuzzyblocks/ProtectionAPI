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
import net.fuzzyblocks.protectionapi.events.IllegalHangingBreakEvent;
import net.fuzzyblocks.protectionapi.events.IllegalHangingPlaceEvent;
import net.fuzzyblocks.protectionapi.region.RegionManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

public class HangingListener extends ProtectionAPIListener {

    private RegionManager regionManager;

    public HangingListener(ProtectionAPI instance) {
        super(instance);
        regionManager = instance.getRegionManager();
    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        Location loc = event.getEntity().getLocation();
        if (event.getRemover() instanceof Player) {
            Player player = (Player) event.getRemover();
            if (!regionManager.canBuildAtPoint(player.getName(), loc)) {
                IllegalHangingBreakEvent apiEvent = new IllegalHangingBreakEvent(player, event.getEntity(),
                        regionManager.getRegionsAtPoint(loc), event);
            }
        }
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        Location loc = event.getEntity().getLocation();
        Player player = event.getPlayer();
        if (!regionManager.canBuildAtPoint(player.getName(), loc)) {
            IllegalHangingPlaceEvent apiEvent = new IllegalHangingPlaceEvent(player, event.getEntity(),
                    regionManager.getRegionsAtPoint(loc), event);
        }
    }
}
