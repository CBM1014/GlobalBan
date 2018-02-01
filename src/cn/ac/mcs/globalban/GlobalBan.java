package cn.ac.mcs.globalban;

import org.bukkit.plugin.java.JavaPlugin;

import cn.ac.mcs.globalban.utils.ConfigManager;

import java.io.File;

public class GlobalBan extends JavaPlugin {
    private static GlobalBan MainPlugin;
    public static File ConfigFile;
    @Override
    public void onEnable() {
        MainPlugin = this;
        ConfigManager.loadConfig();
        GlobalBan.loadModules();
        getLogger().info("GlobalBan Enabled");
    }
    @Override
    public void onDisable() {
        getLogger().info("GlobalBan Disabled");
    }

    public static GlobalBan getPlugin() {
        return MainPlugin;
    }

    public static void loadModules() {
    }
}