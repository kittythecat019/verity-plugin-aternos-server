package com.pntmc.verity;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class VerityEntity {

    private VerityEntity() {}

    private static final String KEY = "verity";

    public static Zombie spawnBehind(Player player) {

        Location loc = player.getLocation().clone();

        // Spawn phía sau người chơi
        loc.add(player.getLocation().getDirection().multiply(-3));

        Zombie verity = player.getWorld().spawn(loc, Zombie.class);

        verity.setCustomName("Verity");
        verity.setCustomNameVisible(true);

        // Đánh dấu đây là Verity
        verity.getPersistentDataContainer().set(
                new NamespacedKey(
                        JavaPlugin.getProvidingPlugin(VerityEntity.class),
                        KEY
                ),
                PersistentDataType.BYTE,
                (byte) 1
        );

        // 100.000 HP
        if (verity.getAttribute(Attribute.MAX_HEALTH) != null) {
            verity.getAttribute(Attribute.MAX_HEALTH)
                    .setBaseValue(100000.0);

            verity.setHealth(100000.0);
        }

        return verity;
    }

    public static boolean isVerity(Entity entity) {

        return entity.getPersistentDataContainer().has(
                new NamespacedKey(
                        JavaPlugin.getProvidingPlugin(VerityEntity.class),
                        KEY
                ),
                PersistentDataType.BYTE
        );
    }
}