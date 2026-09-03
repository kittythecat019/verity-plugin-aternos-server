package com.pntmc.verity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public final class VerityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {

            sender.sendMessage(
                    "Chi nguoi choi moi dung duoc lenh nay."
            );

            return true;
        }

        if (args.length == 0) {

            player.sendMessage("§e/verity spawn");
            player.sendMessage("§e/verity chase");
            player.sendMessage("§e/verity stop");
            player.sendMessage("§e/verity kill");

            return true;
        }

        switch (args[0].toLowerCase()) {

            case "spawn" -> {

                VerityEntity.spawn(player);

                player.sendMessage(
                        "§5Verity da xuat hien."
                );
            }

            case "chase" -> {

                Zombie verity = findNearestVerity(player);

                if (verity == null) {

                    player.sendMessage(
                            "§cKhong tim thay Verity."
                    );

                    return true;
                }

                VerityEntity.startChase(verity);

                player.sendMessage(
                        "§cVerity bat dau chase."
                );
            }

            case "stop" -> {

                Zombie verity = findNearestVerity(player);

                if (verity == null) {

                    player.sendMessage(
                            "§cKhong tim thay Verity."
                    );

                    return true;
                }

                VerityEntity.stopChase(verity);

                player.sendMessage(
                        "§aVerity da dung lai."
                );
            }

            case "kill" -> {

                Zombie verity = findNearestVerity(player);

                if (verity == null) {

                    player.sendMessage(
                            "§cKhong tim thay Verity."
                    );

                    return true;
                }

                verity.remove();

                player.sendMessage(
                        "§aDa xoa Verity."
                );
            }

            default -> {

                player.sendMessage(
                        "§cLenh khong hop le."
                );

                player.sendMessage("§e/verity spawn");
                player.sendMessage("§e/verity chase");
                player.sendMessage("§e/verity stop");
                player.sendMessage("§e/verity kill");
            }
        }

        return true;
    }

    private Zombie findNearestVerity(Player player) {

        double bestDistance = 8 * 8;
        Zombie nearest = null;

        for (Entity entity : player.getWorld()
                .getNearbyEntities(
                        player.getLocation(),
                        8,
                        8,
                        8
                )) {

            if (!(entity instanceof Zombie zombie)) {
                continue;
            }

            if (!VerityEntity.isVerity(zombie)) {
                continue;
            }

            double distance =
                    zombie.getLocation()
                            .distanceSquared(player.getLocation());

            if (distance < bestDistance) {

                bestDistance = distance;
                nearest = zombie;
            }
        }

        return nearest;
    }
}
