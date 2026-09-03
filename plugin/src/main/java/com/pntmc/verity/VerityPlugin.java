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

        if (getCommand("verity") != null) {
            getCommand("verity").setExecutor(
                    new VerityCommand()
            );
        }

        VerityAI.start(this);
    }

    @Override
    public void onDisable() {

        getLogger().info("VerityPlugin da tat!");
    }
}
