package pro.diamondworld.insanemobs.mobs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public abstract class InsaneMob {
    @Getter protected HashMap<String, Double> playerDamageList = new HashMap<>();
    protected LivingEntity entity;
    protected EntityType entityType;

    @Getter private UUID UUID;
    @Getter @Setter
    private Location spawnLocation;

    @Getter @Setter
    protected String name;

    @Getter @Setter
    protected int respawnTime;

    @Setter private int maxHP;
    @Setter private int defaultDamage;

    @Setter protected ItemStack itemInMainHand;

    @Getter protected boolean potionInvulnerable;

    @Getter protected boolean arrowInvulnerable;

    @Getter protected boolean knockBackInvulnerable;

    public void spawn() {
        LivingEntity entity = (LivingEntity) Bukkit.getWorld("world").spawnEntity(spawnLocation, entityType);
        UUID = entity.getUniqueId();

        entity.setCustomNameVisible(true);
        entity.setCustomName(name);

        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHP);
        entity.setHealth(maxHP);

        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(defaultDamage);

        if (itemInMainHand != null) {
            entity.getEquipment().setItemInMainHand(itemInMainHand);
        }

        this.entity = entity;
    }
    public void addPlayerDamage(String playerName, double damage) {
        double total = damage;

        if (playerDamageList.containsKey(playerName)) {
            total += playerDamageList.get(playerName);
        }

        playerDamageList.put(playerName, total);
    }

    public void reset() {
        playerDamageList.clear();
    }
}
