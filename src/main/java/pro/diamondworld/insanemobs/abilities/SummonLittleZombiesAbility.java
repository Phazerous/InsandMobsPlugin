package pro.diamondworld.insanemobs.abilities;

import org.bukkit.entity.LivingEntity;
import pro.diamondworld.insanemobs.mobfactory.LittleZombieFactory;

public class SummonLittleZombiesAbility extends Ability {
    private final int entityCount;
    private final LittleZombieFactory zombieFactory = new LittleZombieFactory();

    public SummonLittleZombiesAbility(int coolDown, int amount) {
        this.coolDown = coolDown;
        entityCount = amount;
    }
    @Override
    public void activate(LivingEntity entity) {
        zombieFactory.spawnEntities(entity.getLocation(), entityCount);
    }
}
