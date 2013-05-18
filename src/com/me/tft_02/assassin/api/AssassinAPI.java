package com.me.tft_02.assassin.api;

import org.bukkit.entity.Player;

import com.me.tft_02.assassin.Assassin;
import com.me.tft_02.assassin.util.PlayerData;

public final class AssassinAPI {

    private Assassin plugin;
    
    /**
     * Let's get rid of the constructor and replace it with a method
     * Must use this method in base class to work
     * <p>
     * More api-like
     * 
     * @param instance The plugin to be used in the api
     */ 
    public void setPlugin(Assassin instance){
        this.plugin = instance;
    }

    private PlayerData data = new PlayerData(plugin);

    /**
     * Check if the player is an Assassin.
     *
     * @param player The player to check
     * @return true if player is an Assassin.
     */
    public boolean isAssassin(Player player) {
        return data.isAssassin(player);
    }

    /**
     * Get the Bounty collected by a player
     *
     * @param player The player to check
     * @return bounty collected
     */
    public int getBountyCollected(Player player) {
        return data.getBountyCollected(player);
    }

    /**
     * Get the Kill count of a player
     *
     * @param player The player to check
     * @return kill count
     */
    public int getKillCount(Player player) {
        return data.getKillCount(player);
    }
}
