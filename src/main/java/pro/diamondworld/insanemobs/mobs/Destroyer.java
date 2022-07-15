package pro.diamondworld.insanemobs.mobs;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pro.diamondworld.insanemobs.InsaneMobsPlugin;
import pro.diamondworld.insanemobs.abilities.DashAbility;

public class Destroyer extends InsaneMob {
    @Getter boolean raged = false;
    public Destroyer() {
        entityType = EntityType.ZOMBIE;
        name = "Разоритель";
        itemInMainHand = getCrossbow();
        knockBackInvulnerable = true;
    }

    private ItemStack getCrossbow() {
        ItemStack item = new ItemStack(Material.CROSSBOW);
        item.addEnchantment(Enchantment.PIERCING, 1);
        item.addEnchantment(Enchantment.MULTISHOT, 1);

        return item;
    }

    private ItemStack getAxe() {
        return new ItemStack(Material.IRON_AXE);
    }

    public void rage() {
        entity.getEquipment().setItemInMainHand(getAxe());
        raged = true;

        dashAbility();
    }

    public void dashAbility() {
        DashAbility ability = new DashAbility(5);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity.isDead()) {
                    cancel();
                    return;
                }

                ability.activate(entity);
            }
        }.runTaskTimer(InsaneMobsPlugin.getInsaneMobsPlugin(), ability.getCoolDown(), ability.getCoolDown());
    }

    public void reset() {
        super.reset();
        raged = false;
    }
}
