package pro.diamondworld.insanemobs.abilities;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DashAbility extends Ability {

    public DashAbility(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public void activate(LivingEntity entity) {
        if (entity instanceof Zombie) {
            entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1 * 20, 3, true, false));
            LivingEntity target = ((Zombie) entity).getTarget();

            if (target == null) {
                return;
            }

            Location difference = entity.getLocation().subtract(target.getLocation());
            Vector normalizedDifference = difference.toVector().normalize().multiply(-2);
            entity.setVelocity(normalizedDifference);
        }
    }
}
