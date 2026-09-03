package com.pntmc.verity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class VerityResurrection implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        // Không phải Verity thì bỏ qua
        if (!VerityEntity.isVerity(event.getEntity())) {
            return;
        }

        // Không rơi đồ / XP
        event.getDrops().clear();
        event.setDroppedExp(0);

        // Người thực sự giết Verity
        Player killer = event.getEntity().getKiller();

        if (killer == null) {
            return;
        }

        // Chờ 5 giây = 100 ticks
        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityResurrection.class);

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> {

                    if (!killer.isOnline()) {
                        return;
                    }

                    VerityEntity.spawnBehind(killer);

                    killer.sendMessage("§5Verity đã trở lại...");
                },
                100L
        );
    }
}