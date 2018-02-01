package cn.ac.mcs.globalban.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.spigotmc.CaseInsensitiveMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author SotrForgotten
 */
public class AzureAPI {
    private static String loggerPrefix = "";

    private static final class LazyAPI {
        private static final AzureAPI api = new AzureAPI();
    }

    private AzureAPI() {
        assert Bukkit.getServer() != null;
        assert LazyAPI.api == null;
    }

    public static final AzureAPI getAPI() {
        return LazyAPI.api;
    }

    public static int viewDistance(final Player player) {
        return /*isPaper() ? player.getViewDistance() :*/ Bukkit.getViewDistance();
    }

    public static boolean customViewDistance(final Player player) {
    //    if (!isPaper()) return false;
    //    return Bukkit.getViewDistance() != player.getViewDistance();
		return false;
    }

    public static String setPrefix(final String prefix) {
        loggerPrefix = prefix;
        return prefix;
    }

    public static void resetPrefix() {
        loggerPrefix = "";
    }
    
    public static void fatal(final String context) {
        fatal(loggerPrefix, context);
    }
    
    public static void fatal(final String prefix, final String context) {
        Bukkit.getLogger().severe(prefix + context);
        Bukkit.shutdown();
    }
    
    public static boolean isBlank(final String s) {
        return s.equals("");
    }
    
    public static void warn(final String context) {
        warn(loggerPrefix, context);
    }
    
    public static void warn(final String prefix, final String context) {
        if (isBlank(context)) return;
        Bukkit.getLogger().warning(prefix + context);
    }
    
    public static void log(final String context) {
        log(loggerPrefix, context);
    }

    public static void log(final String prefix, final String context) {
        if (isBlank(context)) return;
        Bukkit.getConsoleSender().sendMessage(prefix + context);
    }

    public static void log(final CommandSender sender, final String context) {
        log(sender, loggerPrefix, context);
    }

    public static void log(final CommandSender sender, final String prefix, final String msg) {
        if (isBlank(msg)) return;
        sender.sendMessage(prefix + msg);
    }
    
    public static void bc(final String context) {
        bc(loggerPrefix, context);
    }
    
    public static void bc(final String prefix, final String context) {
        if (isBlank(context)) return;
        Bukkit.broadcastMessage(prefix + context);
    }

    public static long toTicks(TimeUnit unit, long duration) {
        return unit.toSeconds(duration) * 20;
    }

    public static Logger createLogger(final String prefix) {
        assert prefix != null;
        return new PrefixedLogger(prefix);
    }

    protected static class PrefixedLogger extends Logger {
        protected final String prefix;

        protected PrefixedLogger(final String prefix) {
            super(prefix, null);
            this.prefix = prefix;
            setParent(Logger.getGlobal());
            setLevel(Level.INFO);
        }

        @Override
        public void log(final LogRecord logRecord) {
            if (this.isLoggable(logRecord.getLevel())) Bukkit.getConsoleSender().sendMessage(prefix + logRecord.getMessage());
        }
    }

    public static <K, V> Coord<K, V> wrapCoord(K key, V value) {
        return new Coord<K, V>(key, value);
    }
    
    public static class Coord<K, V> {
        final K k;
        final V v;
        
        public Coord(K key, V value) {
            k = key;
            v = value;
        }
        
        public K getKey() {
            return k;
        }
        
        public V getValue() {
            return v;
        }
    }
    
    public static <K, V, E> Coord3<K, V, E> wrapCoord3(K key, V value, E extra) {
        return new Coord3<K, V, E>(key, value, extra);
    }
    
    public static class Coord3<K, V, E> {
        final K k;
        final V v;
        final E e;
        
        public Coord3(K key, V value, E extra) {
            k = key;
            v = value;
            e = extra;
        }
        
        public K getKey() {
            return k;
        }
        
        public V getValue() {
            return v;
        }
        
        public E getExtra() {
            return e;
        }
    }
    
    public static LocationData wrapLocation(Location loc) {
        return new LocationData(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ());
    }
    
    public static Location unwrapLocation(LocationData loc) {
        return new Location(Bukkit.getWorld(loc.world), loc.x, loc.y, loc.z);
    }
    
    public static class LocationData implements Serializable {
        private static final long serialVersionUID = -2018113L;
        final String world;
        final double x;
        final double y;
        final double z;
        
        public LocationData(String w, double x, double y, double z) {
            world = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static <E> Map<String, E> newCaseInsensitiveMap() {
        return new CaseInsensitiveMap<E>();
    }

    @SuppressWarnings("deprecation")
    public static Set<String> newCaseInsensitiveSet() {
        return Sets.newSetFromMap(new CaseInsensitiveMap<Boolean>());
    }
    
    public static <E> List<E> matchElements(List<E> list, int start) {
        return matchElements(list, start, list.size() - 1);
    }
    
    /**
     * Returns elements between the start and end index, included the edge as well, collect to a list with capacity 'end - start + 1'
     */
    public static <E> List<E> matchElements(List<E> list, int start, int end) {
        List<E> t = Lists.newArrayListWithCapacity(end - start + 1);
        for (; start <= end; start++) {
            t.add(list.get(start));
        }
        return t;
    }
    
    public static String contactBetween(List<String> list, int start, char spilt) {
        return contactBetween(list, start, spilt);
    }
    
    public static String contactBetween(List<String> list, int start, String spilt) {
        return contactBetween(list, start, list.size() - 1, spilt);
    }
    
    public static String contactBetween(List<String> list, int start, int end, char spilt) {
        return contactBetween(list, start, end, spilt);
    }
    
    /**
     * Contacts strings between the start and end index, included the edge as well, then spilt with the given string
     */
    public static String contactBetween(List<String> list, int start, int end, String spilt) {
        String r = "";
        for (; start <= end; start++) {
            r = r.concat(list.get(start) + (start == end ? "" : spilt));
        }
        return r;
    }
    
    public static ChainArrayList<String> newChainStringList() {
        return new ChainArrayList<String>();
    }
    
    @SuppressWarnings("serial")
    public static class ChainArrayList<E> extends ArrayList<E> {
        public ChainArrayList<E> to(E e) {
            add(e);
            return this;
        }
    }
    
    public static boolean hasPerm(CommandSender sender, String perm) {
        return sender.isOp() || sender.hasPermission(perm);
    }
    
    public static boolean hasPerm(CommandSender sender, Permission perm) {
        return sender.isOp() || sender.hasPermission(perm);
    }
    
    public static FileConfiguration loadOrCreateConfig(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    
    public static File loadOrCreateDir(File file) {
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static void tryRestartServer(){
        //if (VersionLevel.isSpigot() | VersionLevel.isHigherEquals(Version.MINECRAFT_1_7_R1)) {
            new org.spigotmc.RestartCommand("restart").execute(Bukkit.getConsoleSender(), null, null);
        //} else {
            // handle by lanuch-script
        //    Bukkit.shutdown();
        //}
    }
    
    public static void playSound(Player player, Sound sound) {
        playSound(player, sound, false);
    }
    
    public static void playSound(Player player, Sound sound, boolean broadcast) {
        if (broadcast) {
            player.getWorld().playSound(player.getLocation(), sound, 1F, 1F);
        } else {
            player.playSound(player.getLocation(), sound, 1F, 1F);
        }
    }
    
    public static <E> E containEquals(Collection<E> c, Object o) {
        for (E e : c) {
            if (o.equals(e)) return e;
        }
        return null;
    }
}
