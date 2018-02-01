package cn.ac.mcs.globalban;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import cn.ac.mcs.globalban.utils.AzureAPI;
import cn.ac.mcs.globalban.utils.Configurable;

import java.io.File;

public class GlobalBan extends JavaPlugin {
    static GlobalBan instance;
    
    @Override
    public void onEnable() {
        loadModules();
        
        try {
            Configurable.restoreNodes(this, new File(AzureAPI.loadOrCreateDir(this.getDataFolder()), "config.yml"), GlobalBan.class);
        } catch (Throwable t) {
            t.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    public static GlobalBan getInstance() {
        return instance;
    }

    static void loadModules() {
        ;
    }
}