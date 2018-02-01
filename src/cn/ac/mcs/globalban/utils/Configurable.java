package cn.ac.mcs.globalban.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author SotrForgotten
 */
public abstract class Configurable {
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Node {
        String path();
    }
    
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    protected static @interface Locale {}
    
    public static void restoreNodes(JavaPlugin plugin, File file, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException, IOException {
        assert plugin != null;
        FileConfiguration config = AzureAPI.loadOrCreateConfig(file);
        
        for (Field field : clazz.getDeclaredFields()) {
            Node node = field.getAnnotation(Node.class);
            if (node == null) continue;
            field.setAccessible(true);
            
            Object def = field.get(null);
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) && !Modifier.isFinal(mod)) {
                String path = node.path();
                Object value = config.get(path);
                if (value == null) {
                    config.set(path, def);
                    field.set(null, colorzine(def)); // for colorzine
                } else {
                    field.set(null, colorzine(value));
                }
            }
        }
        
        config.save(file);
    }
    
    @SuppressWarnings("all")
    public static Object colorzine(Object o) {
        if (o instanceof String) {
            return StringUtils.replaceChars((String) o, '&', '§');
        }
        if (o instanceof List) {
            List list = (List) o;
            for (Object obj : list) {
                if (obj instanceof String) {
                    list.set(list.indexOf(obj), StringUtils.replaceChars((String) obj, '&', '§'));
                }
            }
            return list;
        }
        return o;
    }
}
