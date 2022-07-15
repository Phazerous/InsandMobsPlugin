package pro.diamondworld.insanemobs.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pro.diamondworld.insanemobs.InsaneMobsPlugin;
import pro.diamondworld.insanemobs.abilities.SummonLittleZombiesAbility;

public class Summoner extends InsaneMob {
    public Summoner() {
        entityType = EntityType.ZOMBIE;
        itemInMainHand = new ItemStack(Material.BONE);

        potionInvulnerable = true;
        arrowInvulnerable = true;

        SummonLittleZombiesAbility ability = new SummonLittleZombiesAbility(20, 3);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (entity == null) {
                    return;
                }

                if (entity.isDead()) {
                    cancel();
                    return;
                }

                ability.activate(entity);
            }
        }.runTaskTimer(InsaneMobsPlugin.getInsaneMobsPlugin(), ability.getCoolDown(), ability.getCoolDown());
    }
}
