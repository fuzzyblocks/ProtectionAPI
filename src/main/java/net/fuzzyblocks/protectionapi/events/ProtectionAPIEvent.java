/*
* Copyright (c) 2013 cedeel.
* All rights reserved.
*
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
* * Redistributions of source code must retain the above copyright
* notice, this list of conditions and the following disclaimer.
* * Redistributions in binary form must reproduce the above copyright
* notice, this list of conditions and the following disclaimer in the
* documentation and/or other materials provided with the distribution.
* * The name of the author may not be used to endorse or promote products
* derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.fuzzyblocks.protectionapi.events;

import net.fuzzyblocks.protectionapi.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public abstract class ProtectionAPIEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private boolean cancelled = false;
    private Set<Region> regionSet;
    private Event bukkitEvent;

    public ProtectionAPIEvent(Player player, Set<Region> regionSet, Event bukkitEvent) {
        this.player = player;
        this.regionSet = regionSet;
        this.bukkitEvent = bukkitEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Set<Region> getApplicableRegions() {
        return regionSet;
    }

    public Event getRelatedEvent() {
        return bukkitEvent;
    }
}
