package pro.diamondworld.insanemobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pro.diamondworld.insanemobs.commands.NPCCommand;
import pro.diamondworld.insanemobs.databasemanager.DatabaseManager;
import pro.diamondworld.insanemobs.listeners.MobListener;
import pro.diamondworld.insanemobs.managers.ConfigManager;
import pro.diamondworld.insanemobs.managers.MobManager;
import pro.diamondworld.insanemobs.messagedispatcher.MessageDispatcher;

public final class InsaneMobsPlugin extends JavaPlugin {
    private static InsaneMobsPlugin plugin;
    public static InsaneMobsPlugin getInsaneMobsPlugin() {
        return plugin;
    }
    private final DatabaseManager databaseManager = new DatabaseManager();
    private final MessageDispatcher messageDispatcher = new MessageDispatcher();
    private final FileConfiguration config = getConfig();
    private final ConfigManager configManager = new ConfigManager(config);
    private final MobManager mobManager = new MobManager(this, databaseManager, messageDispatcher, configManager);
    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[InsaneMobsPlugin] " + ChatColor.DARK_GREEN + "enabled.");

        if (!databaseManager.open()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Database] connection error.");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "[Database] connection successfully.");
        }

        getServer().getPluginManager().registerEvents(new MobListener(this, mobManager), this);
        getCommand("npc").setExecutor(new NPCCommand(mobManager));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[InsaneMobsPlugin] " + ChatColor.RED + "disabled.");
        databaseManager.close();
    }
}
