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
/**
 * This class handles any interactions with blocks on the minecraft server.
 */
public class BlockListener implements Listener{
  
  private ProtectionAPI api;
  
  public BlockListener(ProtectionAPI instance){
    api = instance;
  }
  
  /**
   * Called when a block is damaged.
   * Currently only used for Cake.
   */
   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
   public void onBlockDamage(BlockDamageEvent event){
     Player player = event.getPlayer();
     Block damagedBlock = event.getBlock();
     
     if (damagedBlock.getType == Material.CAKE_BLOCK){
       //Check if player can build
       if(!plugin.getRegionManager.canBuildAtPoint(player.getName(), block.getLocation()){
         //TODO: load strings from configuration manager
         player.sendMessage();
         event.setCancelled(true);
         return;
       }
     }
   }
   
   /**
    * Called when a block is broken.
    */
   @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
   public void onBlockBreak(BlockBreakEvent event){
     Player player = event.getPlayer();
     Block block = event.getBlock();
     
     //Check if player can build
     if (!plugin.getRegionManager.canBuildAtPoint(player.getName(), block.getLocation()){
       //TODO: load strings from configuration manager
       player.sendMessage();
       event.setCancelled(true);
     }
   }
   
   /**
    * Called when a bloick gets ignited
    */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent event){
      IgniteCause igniteCause = event.getCause();
      Block block = event.getBlock();
      //Check if fire is caused by a player
      if (igniteCause == IgniteCause.FLINT_AND_STEEL ||
      cause == IgniteCause.FIREBALL &&
      event.getPlayer != null)
      //Check if playe can build
      if (!plugin.getRegionManager.canBuildAtPoint(event.getPlayer().getName(), block.getLocation()) {
        //TODO: load strings from configuration manager
        player.sendMessage();
        event.setCancelled(true);
      }
    }
}
