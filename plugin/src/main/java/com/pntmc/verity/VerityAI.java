package com.pntmc.verity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;

public final class VerityAI {

    private VerityAI() {}

    private static final double DETECTION_RANGE = 128.0;

    public static void start(JavaPlugin plugin) {

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                () -> {

                    for (org.bukkit.World world : Bukkit.getWorlds()) {

                        for (org.bukkit.entity.Entity entity
                                : world.getEntities()) {

                            if (!(entity instanceof Zombie verity)) {
                                continue;
                            }

                            if (!VerityEntity.isVerity(verity)) {
                                continue;
                            }

                            if (!VerityEntity.isChasing(verity)) {
                                continue;
                            }

                            Player target =
                                    findNearestPlayer(verity);

                            if (target == null) {
                                verity.setTarget(null);
                                continue;
                            }

                            verity.setTarget(target);
                        }
                    }

                },
                1L,
                10L
        );
    }

    private static Player findNearestPlayer(Zombie verity) {

        Location location = verity.getLocation();

        Player nearest = null;
        double nearestDistanceSquared =
                DETECTION_RANGE * DETECTION_RANGE;

        for (Player player : verity.getWorld().getPlayers()) {

            if (!player.isOnline()) {
                continue;
            }

            double distance =
                    player.getLocation()
                            .distanceSquared(location);

            if (distance < nearestDistanceSquared) {

                nearestDistanceSquared = distance;
                nearest = player;
            }
        }

        return nearest;
    }
}
