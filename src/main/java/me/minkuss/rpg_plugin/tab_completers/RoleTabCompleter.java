package me.minkuss.rpg_plugin.tab_completers;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class RoleTabCompleter implements TabCompleter {
    private final Rpg_plugin _plugin;

    public RoleTabCompleter(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 1) {
            return List.of("give", "set", "show");
        }
        else if(args[0].equals("give") && args.length == 2) {
            return _plugin.getConfig().getStringList("role-names");
        }
        else if(args[0].equals("set") && args.length == 3) {
            return _plugin.getConfig().getStringList("role-names");
        }

        return null;
    }
}
