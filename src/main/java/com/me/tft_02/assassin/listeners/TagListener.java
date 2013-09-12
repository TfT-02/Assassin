package com.me.tft_02.assassin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.me.tft_02.assassin.Assassin;
import com.me.tft_02.assassin.util.player.PlayerData;
import com.me.tft_02.assassin.util.player.UserManager;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagListener implements Listener {

    private PlayerData data = new PlayerData();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onNameTag(PlayerReceiveNameTagEvent event) {
        Player namedPlayer = event.getNamedPlayer();

        if (data.isAssassin(UserManager.getPlayer(namedPlayer))) {
            event.setTag(ChatColor.DARK_RED + "[ASSASSIN] " + data.getKillCount(namedPlayer));

            Assassin.p.debug("Changed player tag to [ASSASSIN] for " + namedPlayer.getName());
        }
        else {
            event.setTag(ChatColor.RESET + namedPlayer.getDisplayName());

            Assassin.p.debug("Reset player tag for " + namedPlayer.getName());
        }

        //		if (!data.isAssassin(player)) {
        //			event.setTag(ChatColor.DARK_GRAY + "PLAYER");
        //			System.out.println("Changed player tag to Player for " + player.getName());
        //		} else {
        //			event.setTag(ChatColor.RESET + player.getDisplayName());
        //			System.out.println("Reset player tag for " + player.getName());
        //		}
        //		for(Player p : getServer().getOnlinePlayers()){
        //		if (event.getPlayer().getName().equals(player)) {
        //			event.setTag(ChatColor.DARK_GRAY + "PLAYER");
        //		}
    }
}
