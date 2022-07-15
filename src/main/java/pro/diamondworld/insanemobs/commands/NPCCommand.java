package pro.diamondworld.insanemobs.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pro.diamondworld.insanemobs.managers.MobManager;
import pro.diamondworld.insanemobs.mobs.Destroyer;
import pro.diamondworld.insanemobs.mobs.InsaneMob;
import pro.diamondworld.insanemobs.mobs.Summoner;

public class NPCCommand implements CommandExecutor {
    private final MobManager mobManager;

    public NPCCommand(MobManager mobManager) {
        this.mobManager = mobManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only player can use the command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Not enough arguments.");
            return true;
        }

        if (!args[0].equalsIgnoreCase("spawn")) {
            player.sendMessage(ChatColor.RED + "/npc <spawn> <npcName>");
            return true;
        }

        InsaneMob mob;

        switch (args[1].toLowerCase()) {
            case "summoner":
                mob = new Summoner();
                break;

            case "destroyer":
                mob = new Destroyer();
                break;

            default:
                player.sendMessage(ChatColor.RED + "No such npc.");
                return true;
        }

        mobManager.primarySpawn(mob);

        player.sendMessage(ChatColor.GOLD + "[Spawned].");

        return true;
    }
}
