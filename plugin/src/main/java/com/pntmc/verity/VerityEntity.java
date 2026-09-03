package com.pntmc.verity;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class VerityEntity {

    private VerityEntity() {}

    private static final String KEY = "verity";
    private static final String CHASE_KEY = "verity_chase";

    public static Zombie spawn(Player player) {

        Location loc = player.getLocation().clone();

        // Spawn phía sau người chơi
        loc.add(player.getLocation().getDirection().multiply(-3));

        Zombie verity = player.getWorld().spawn(loc, Zombie.class);

        markVerity(verity);

        verity.setCustomName("Verity");
        verity.setCustomNameVisible(false);

        // Không cháy dưới ánh sáng mặt trời
        verity.setRemoveWhenFarAway(false);

        // Không bị đẩy
        verity.setCollidable(false);

        // 100.000 HP
        Attribute maxHealth = Attribute.MAX_HEALTH;

        if (verity.getAttribute(maxHealth) != null) {
            verity.getAttribute(maxHealth).setBaseValue(100000.0);
            verity.setHealth(100000.0);
        }

        // Verity bình thường đứng yên
        freeze(verity);

        return verity;
    }

    private static void markVerity(Zombie entity) {

        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityEntity.class);

        entity.getPersistentDataContainer().set(
                new NamespacedKey(plugin, KEY),
                PersistentDataType.BYTE,
                (byte) 1
        );

        entity.getPersistentDataContainer().set(
                new NamespacedKey(plugin, CHASE_KEY),
                PersistentDataType.BYTE,
                (byte) 0
        );
    }

    public static boolean isVerity(Entity entity) {

        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityEntity.class);

        return entity.getPersistentDataContainer().has(
                new NamespacedKey(plugin, KEY),
                PersistentDataType.BYTE
        );
    }

    public static boolean isChasing(Entity entity) {

        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityEntity.class);

        Byte value = entity.getPersistentDataContainer().get(
                new NamespacedKey(plugin, CHASE_KEY),
                PersistentDataType.BYTE
        );

        return value != null && value == 1;
    }

    public static void startChase(Zombie verity) {

        if (!isVerity(verity)) {
            return;
        }

        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityEntity.class);

        verity.getPersistentDataContainer().set(
                new NamespacedKey(plugin, CHASE_KEY),
                PersistentDataType.BYTE,
                (byte) 1
        );

        verity.setAI(true);
        verity.setCollidable(false);

        if (verity.getAttribute(Attribute.MOVEMENT_SPEED) != null) {
            verity.getAttribute(Attribute.MOVEMENT_SPEED)
                    .setBaseValue(0.675);
        }

        if (verity.getAttribute(Attribute.FOLLOW_RANGE) != null) {
            verity.getAttribute(Attribute.FOLLOW_RANGE)
                    .setBaseValue(128.0);
        }
    }

    public static void stopChase(Zombie verity) {

        if (!isVerity(verity)) {
            return;
        }

        JavaPlugin plugin =
                JavaPlugin.getProvidingPlugin(VerityEntity.class);

        verity.getPersistentDataContainer().set(
                new NamespacedKey(plugin, CHASE_KEY),
                PersistentDataType.BYTE,
                (byte) 0
        );

        freeze(verity);
    }

    private static void freeze(Zombie verity) {

        verity.setAI(false);
        verity.setTarget(null);

        if (verity.getAttribute(Attribute.MOVEMENT_SPEED) != null) {
            verity.getAttribute(Attribute.MOVEMENT_SPEED)
                    .setBaseValue(0.0);
        }

        if (verity.getAttribute(Attribute.FOLLOW_RANGE) != null) {
            verity.getAttribute(Attribute.FOLLOW_RANGE)
                    .setBaseValue(128.0);
        }
    }
}
