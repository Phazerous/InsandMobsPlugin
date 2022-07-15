package pro.diamondworld.insanemobs.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import pro.diamondworld.insanemobs.common.CountdownTimer;
import pro.diamondworld.insanemobs.databasemanager.DatabaseManager;
import pro.diamondworld.insanemobs.messagedispatcher.MessageDispatcher;
import pro.diamondworld.insanemobs.mobs.InsaneMob;

import java.util.*;
import java.util.stream.Collectors;

public class MobManager {

    private final JavaPlugin plugin;
    private final DatabaseManager databaseManager;
    private final MessageDispatcher messageDispatcher;
    private final ConfigManager configManager;
    private final Map<UUID, InsaneMob> mobs = new HashMap<>();

    public MobManager(JavaPlugin plugin, DatabaseManager databaseManager, MessageDispatcher messageDispatcher, ConfigManager configManager) {
        this.plugin = plugin;
        this.databaseManager = databaseManager;
        this.messageDispatcher = messageDispatcher;
        this.configManager = configManager;
    }

    public void primarySpawn(InsaneMob mob) {
        String classPath = mob.getClass().toString();
        String mobClass = classPath.substring(classPath.lastIndexOf(".") + 1).toLowerCase();

        mob.setSpawnLocation(configManager.getMobLocation(mobClass));
        mob.setName(configManager.getMobName(mobClass));
        mob.setRespawnTime(configManager.getRespawnTime(mobClass));
        mob.setMaxHP(configManager.getMaxHP(mobClass));
        mob.setDefaultDamage(configManager.getDefaultDamage(mobClass));

        mob.spawn();
        addMob(mob);
    }

    public void addMob(InsaneMob mob) {
        mobs.put(mob.getUUID(), mob);
    }

    public boolean isCustomMob(UUID uuid) {
        return mobs.containsKey(uuid);
    }

    public InsaneMob getCustomMob(UUID uuid) {
        return mobs.get(uuid);
    }

    public void killMob(UUID uuid) {
        InsaneMob mob = mobs.remove(uuid);

        showTopDamagePlayers(mob);

        mob.reset();

        tillSpawnHandle(mob);
    }

    private void showTopDamagePlayers(InsaneMob mob) {
        Map<String, Double> topPlayers = mob.getPlayerDamageList().entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        final int bossId = databaseManager.getLastBossId() + 1;
        for (Map.Entry<String, Double> entry : topPlayers.entrySet()) {
            databaseManager.insertBossData(bossId, entry.getKey(), entry.getValue());
        }

        messageDispatcher.broadcastBossKilled(mob.getName(), new ArrayList<>(topPlayers.keySet()));
    }

    private void tillSpawnHandle(InsaneMob mob) {
        ArmorStand respawnHolo = (ArmorStand) Bukkit.getWorld("world").spawnEntity(mob.getSpawnLocation(), EntityType.ARMOR_STAND);
        respawnHolo.setVisible(false);
        respawnHolo.setCustomNameVisible(true);

        new CountdownTimer(plugin, mob.getRespawnTime(),
                () -> {
                    respawnHolo.remove();
                    mob.spawn();
                    addMob(mob);
                },
                (t) -> respawnHolo.setCustomName(String.format(ChatColor.GOLD + "%s появится через %01d:%02d", mob.getName(), t.getMinutesLeft(), t.getSecondsLeft()))).scheduleTimer();
    }
}
