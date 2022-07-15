package pro.diamondworld.insanemobs.abilities;

import lombok.Getter;
import org.bukkit.entity.LivingEntity;

public abstract class Ability {
    protected int coolDown;

   public int getCoolDown() {
       return coolDown * 20;
   }

    public abstract void activate(LivingEntity entity);
}
