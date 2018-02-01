package cn.ac.mcs.globalban.Utils;

import cn.ac.mcs.globalban.GlobalBan;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {
// Author: @RainbowEna
// ConfigManager
    private static YamlConfiguration config;

    public static void loadConfig() {
        GlobalBan.getPlugin().getLogger().info("正在进行插件配置文件合法性检查...");
        try {
            GlobalBan.ConfigFile = new File(GlobalBan.getPlugin().getDataFolder(), "config.yml");
            if (GlobalBan.ConfigFile.exists() == false) {
                try {
                    GlobalBan.ConfigFile.createNewFile();
                } catch (IOException ex) {
                }
            }
            config = YamlConfiguration.loadConfiguration(GlobalBan.ConfigFile);
        } catch (Exception ex) {
            GlobalBan.getPlugin().getLogger().info("错误:" + ConfigManager.class.getName() + "加载出现错误，配置文件不合法?");
            ex.printStackTrace();
        }
    }

    public static Integer getInteger(String string, int integer) {
        if (config.isInt(string) == false) {
            config.set(string, integer);
            try {
                config.save(GlobalBan.ConfigFile);
            } catch (IOException ex) {
            }
        }
        return config.getInt(string);
    }

    public static Boolean getBoolean(String string, boolean bl) {
        if (config.isBoolean(string) == false) {
            config.set(string, bl);
            try {
                config.save(GlobalBan.ConfigFile);
            } catch (IOException ex) {
            }
        }
        return config.getBoolean(string);
    }

    public static String getString(String string, String dstring) {
        if (config.isString(string) == false) {
            config.set(string, dstring);
            try {
                config.save(GlobalBan.ConfigFile);
            } catch (IOException ex) {
            }
        }
        return config.getString(string);
    }

    public static List<String> getStringList(String string, List<String> list) {
        if (config.isList(string) == false) {
            config.set(string, list);
            try {
                config.save(GlobalBan.ConfigFile);
            } catch (IOException ex) {
            }
        }
        return config.getStringList(string);
    }

    public static List<Integer> getIntegerList(String string, List<Integer> list) {
        if (config.isList(string) == false) {
            config.set(string, list);
            try {
                config.save(GlobalBan.ConfigFile);
            } catch (IOException ex) {
            }
        }
        return config.getIntegerList(string);
    }
}
