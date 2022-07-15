package pro.diamondworld.insanemobs.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;
import pro.diamondworld.insanemobs.InsaneMobsPlugin;
import pro.diamondworld.insanemobs.managers.MobManager;
import pro.diamondworld.insanemobs.mobs.Destroyer;
import pro.diamondworld.insanemobs.mobs.InsaneMob;

import java.text.DecimalFormat;
import java.util.UUID;

public class MobListener implements Listener {
    private final InsaneMobsPlugin plugin;
    private final MobManager mobManager;
    public MobListener(InsaneMobsPlugin plugin, MobManager mobManager) {
        this.plugin = plugin;
        this.mobManager = mobManager;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        UUID uuid = e.getEntity().getUniqueId();

        if (!mobManager.isCustomMob(uuid)) {
            return;
        }

        mobManager.killMob(uuid);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof LivingEntity) || !(mobManager.isCustomMob(e.getEntity().getUniqueId()))) {
            return;
        }

        LivingEntity entity = (LivingEntity) e.getEntity();
        InsaneMob mob = mobManager.getCustomMob(entity.getUniqueId());
        Player player = (Player) e.getDamager();

        double damage = e.getDamage();

        mob.addPlayerDamage(player.getName(), damage);
        double hp = entity.getHealth() - damage;

        if (mob instanceof Destroyer && !((Destroyer) mob).isRaged() && hp <= entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 2) {
            ((Destroyer) mob).rage();
        }

        String message;

        if (hp <= 0) {
            message = ChatColor.RED + "[Killed]";
        } else {
            DecimalFormat format = new DecimalFormat("0.#");
            message = ChatColor.RED + format.format(hp) + "❤";
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        UUID uuid = e.getEntity().getUniqueId();

        if (!mobManager.isCustomMob(uuid)) {
            return;
        }
        InsaneMob mob = mobManager.getCustomMob(uuid);

        EntityDamageEvent.DamageCause damageCause = e.getCause();

        if (mob.isPotionInvulnerable() && (damageCause.equals(EntityDamageEvent.DamageCause.MAGIC) || damageCause.equals(EntityDamageEvent.DamageCause.POISON))) {
            e.setCancelled(true);
        }

        if (mob.isKnockBackInvulnerable()) {
            final Vector vector = new Vector();
            e.getEntity().setVelocity(vector);
            Bukkit.getScheduler().runTaskLater(plugin, () -> e.getEntity().setVelocity(vector), 1L);
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e) {
        UUID uuid = e.getEntity().getUniqueId();

        if (!mobManager.isCustomMob(uuid)) {
            return;
        }

        InsaneMob mob = mobManager.getCustomMob(uuid);

        if (e.getDamager() instanceof Arrow && mob.isArrowInvulnerable()) {
            e.getDamager().remove();
            e.setCancelled(true);
        }
    }
}
