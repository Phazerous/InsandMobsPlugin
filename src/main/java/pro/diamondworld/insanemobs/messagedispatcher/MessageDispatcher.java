package pro.diamondworld.insanemobs.messagedispatcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

public class MessageDispatcher {
    public void broadcastBossKilled(String bossName, List<String> topPlayers) {
        broadcast(String.format("&6Босс %s был повержен", bossName));
        broadcast("&eНаибольший урон от игроков:");

        String playerLine;
        for (int i = 1; i < 4; i++) {
            if (topPlayers.size() >= i) {
                playerLine = String.format("%d. %s", i, topPlayers.get(i - 1));

                playerLine = i == 1 ? "&3" + playerLine : "&7" + playerLine;

                broadcast(playerLine);
            }
        }
    }

    public void broadcast(String message) {
        Bukkit.getServer().broadcastMessage(color(message));
    }

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
