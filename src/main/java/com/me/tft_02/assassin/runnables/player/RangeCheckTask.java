package com.me.tft_02.assassin.runnables.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.assassin.Assassin;
import com.me.tft_02.assassin.config.Config;
import com.me.tft_02.assassin.util.PlayerData;

public class RangeCheckTask extends BukkitRunnable {

    private PlayerData data = new PlayerData(Assassin.p);

    @Override
    public void run() {
        checkIfAssassinNear();
    }

    public void checkIfAssassinNear() {
        double distance = Config.getInstance().getMessageDistance();

        if (distance <= 0) {
            return;
        }

        for (Player player : Assassin.p.getServer().getOnlinePlayers()) {
            if (!data.isAssassin(player)) {
                continue;
            }

            for (Player assassin : data.getOnlineAssassins()) {
                if (!player.getWorld().equals(assassin.getWorld())) {
                    continue;
                }

                if (player.getLocation().distance(assassin.getLocation()) < distance && data.firstTimeNear(player)) {
                    player.sendMessage(ChatColor.DARK_RED + "ASSASSIN SIGHTED!");
                    data.addNearSent(player);
                }
                else {
                    data.removeNearSent(player);
                }
            }
        }
    }
}
