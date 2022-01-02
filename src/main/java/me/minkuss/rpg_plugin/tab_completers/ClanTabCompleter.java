package me.minkuss.rpg_plugin.tab_completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ClanTabCompleter implements TabCompleter {

    public ClanTabCompleter() {}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String str, String[] args) {

        if(args.length == 1) {
            return List.of(
                    "create", "leave", "help",
                    "delete", "invite", "join",
                    "kick", "list", "info",
                    "requests", "chat", "sethome",
                    "rmhome", "home", "setmod",
                    "unmod", "accept");
        }

        return null;
    }
}
