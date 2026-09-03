package com.pntmc.verity;

import org.bukkit.plugin.java.JavaPlugin;

public final class VerityPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("VerityPlugin da bat!");

        getServer().getPluginManager().registerEvents(
                new VerityResurrection(),
                this
        );

        getCommand("verity").setExecutor(new VerityCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("VerityPlugin da tat!");
    }
}