package pro.diamondworld.insanemobs.mobfactory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;


public class LittleZombieFactory {
    private final EntityType entityType = EntityType.ZOMBIE;

    public void spawnEntities(Location location, int count) {
        for (int i = 0; i < count; i++) {
            Zombie zombie = (Zombie) Bukkit.getWorld("world").spawnEntity(location, entityType);
            zombie.setBaby();
        }
    }
}
