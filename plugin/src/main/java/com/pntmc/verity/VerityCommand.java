package com.pntmc.verity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class VerityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Chi nguoi choi moi dung duoc lenh nay.");
            return true;
        }

        if (args.length == 0) {

            player.sendMessage("§e/verity spawn");
            player.sendMessage("§e/verity kill");
            player.sendMessage("§e/verity respawn");

            return true;
        }

        switch (args[0].toLowerCase()) {

            case "spawn", "respawn" -> {

                VerityEntity.spawnBehind(player);

                player.sendMessage(
                        "§aVerity đã xuất hiện phía sau bạn."
                );
            }

            case "kill" -> {

                Entity target = player.getWorld()
                        .getNearbyEntities(
                                player.getLocation(),
                                5,
                                5,
                                5
                        )
                        .stream()
                        .filter(VerityEntity::isVerity)
                        .findFirst()
                        .orElse(null);

                if (target == null) {

                    player.sendMessage(
                            "§cKhông tìm thấy Verity gần bạn."
                    );

                    return true;
                }

                target.remove();

                player.sendMessage(
                        "§aĐã xóa Verity."
                );
            }

            default -> {

                player.sendMessage(
                        "§cLệnh không hợp lệ."
                );

                player.sendMessage(
                        "§e/verity spawn"
                );

                player.sendMessage(
                        "§e/verity kill"
                );

                player.sendMessage(
                        "§e/verity respawn"
                );
            }
        }

        return true;
    }
}