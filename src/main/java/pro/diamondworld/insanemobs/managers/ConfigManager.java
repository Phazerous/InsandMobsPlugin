package pro.diamondworld.insanemobs.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager(FileConfiguration fileConfiguration) {
        config = fileConfiguration;
    }

    public Location getMobLocation(String mobClass) {
        int x = config.getInt(mobClass + ".location.x");
        int y = config.getInt(mobClass + ".location.y");
        int z = config.getInt(mobClass + ".location.z");

        return new Location(Bukkit.getWorld("world"), x, y, z);
    }

    public int getRespawnTime(String mobClass) {
        return config.getInt(mobClass + ".respawn_time");
    }

    public String getMobName(String mobClass) {
        return config.getString(mobClass + ".name");
    }

    public int getMaxHP(String mobClass) {
        System.out.println(config.getInt(mobClass + ".max_hp"));
        System.out.println(mobClass + ".max_hp");
        return config.getInt(mobClass + ".max_hp");
    }

    public int getDefaultDamage(String mobClass) {
        return config.getInt(mobClass + ".default_damage");
    }
}
