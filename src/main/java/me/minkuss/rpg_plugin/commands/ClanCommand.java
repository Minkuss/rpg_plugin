package me.minkuss.rpg_plugin.commands;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.events.clans.InviteEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class ClanCommand extends AbstractCommand {
    public ClanCommand(String name) {
        super(name);
    }

    @Override
    public void execute(Rpg_plugin plugin, CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Эту команду может отправить только игрок.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Чтобы увидеть перечень всех комманд, напишите /clan help");
            return;
        }

        switch (args[0]) {
            case "create" -> Create(player, plugin, args);
            case "leave" -> Leave(player, plugin);
            case "delete" -> Delete(player, plugin);
            case "accept" -> Accept(player, plugin);
            case "invite" -> Invite(player, plugin, args);
            case "join" -> Join(player, plugin, args);
            case "kick" -> Kick(player, plugin, args);
            case "list" -> List(player, plugin);
            case "info" -> Info(player, plugin, args);
            case "requests" -> Requests(player, plugin.getConfig());
            case "chat" -> Chat(player, plugin);
            case "sethome" -> SetHome(player, plugin, args);
            case "rmhome" -> RemoveHome(player, plugin, args);
            case "home" -> Home(player, plugin.getConfig());
            case "setmod" -> SetModerator(player, plugin, args);
            case "unmod" -> UnMod(player, plugin, args);
            case "help" -> Help(player);
            default -> {
                sender.sendMessage(ChatColor.RED + "Чтобы увидеть перечень всех комманд, напишите /clan help");
            }
        }
    }

    private void Create(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String clan_name = args[1];

        boolean inClan = config.contains("players." + player.getName() + ".clan");

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите название своего клана");
            return;
        }

        if (!inClan) {
            List<String> player_list = List.of(player.getName());
            List<String> clan_list = config.getStringList("clan_list");
            List<String> owners = List.of(player.getName());

            if (clan_list.contains(clan_name)) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Клан с таким названием уже существует");
                return;
            }

            clan_list.add(clan_name);

            config.set("clans." + clan_name + ".clanmates", 1);
            config.set("clans." + clan_name + ".participants", player_list);
            config.set("clans." + clan_name + ".owners", owners);
            config.set("clans." + clan_name + ".first-owner", player.getName());
            config.set("clan-list", clan_list);
            config.set("players." + player.getName() + ".clan", clan_name);
            plugin.saveConfig();

            player.sendMessage(ChatColor.GOLD + "Вы создали клан - " + clan_name);
        } else player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы уже состоите в клане");
    }

    private void Leave(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        String clan_name;
        String firstOwner;
        List<String> owners;
        List<String> clan_list = config.getStringList("clan-list");
        int mates_number;
        boolean inClan = config.contains("players." + player.getName() + ".clan");

        if (inClan) {
            clan_name = config.getString("players." + player.getName() + ".clan");
            firstOwner = config.getString("clans." + clan_name + ".first-owner");
            owners = config.getStringList("clans." + clan_name + ".owners");
            mates_number = config.getInt("clans." + clan_name + ".clanmates");

            if (mates_number == 1) {
                clan_list.remove(clan_name);
                config.set("clan-list", clan_list);
                config.set("clans." + clan_name, null);
                config.set("players." + player.getName() + ".clan", null);
                player.sendMessage(ChatColor.GOLD + "Вы успешно вышли из клана. Теперь советуем вам скорее найти или создать новый!");
                plugin.saveConfig();
                return;
            }

            if (owners.size() == 1 && owners.contains(player.getName())) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы единственный модератор клана, поэтому перед выходом передайте пожалуйста права другому участнику, выбирайте с умом)");
                return;
            }

            if (firstOwner.equals(player.getName())) {
                owners.remove(player.getName());
                String temp = owners.get(new Random().nextInt(owners.size()));
                config.set("clans." + clan_name + ".owners", owners);
                config.set("clans." + clan_name + ".first-owner", temp);
                player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Права создателя перешли игроку - " + temp + ". Теперь вы их враг");
            }

            if (owners.contains(player.getName())) {
                owners.remove(player.getName());
                config.set("clans." + clan_name + ".owners", owners);
            }

            List<String> players_list = config.getStringList("clans." + clan_name + ".participants");
            players_list.remove(player.getName());

            config.set("clans." + clan_name + ".participants", players_list);
            config.set("players." + player.getName() + ".clan", null);
            config.set("clans." + clan_name + ".clanmates", mates_number - 1);
            plugin.saveConfig();

            player.sendMessage(ChatColor.GOLD + "Вы успешно вышли из клана. Теперь советуем вам скорее найти или создать новый!");
        } else player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
    }

    private void Help(Player player) {
        List<String> commands = List.of(
                "accept", "create", "leave",
                "help", "delete", "invite",
                "join", "kick", "list",
                "info", "requests", "chat",
                "sethome", "rmhome", "home",
                "setmod", "unmod");

        player.sendMessage(ChatColor.BLUE + "[Список комманд]: ");
        for (String item : commands) {
            player.sendMessage(ChatColor.GREEN + item);
        }
    }

    private void Delete(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        List<String> clan_list = config.getStringList("clan-list");
        String firstOwner;

        String clan_name;
        if (config.contains("players." + player.getName() + ".clan")) {
            clan_name = config.getString("players." + player.getName() + ".clan");
            firstOwner = config.getString("clans." + clan_name + ".first-owner");
        } else {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        if (!(firstOwner.equals(player.getName()))) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Удалить клан может только создатель клана");
            return;
        }

        List<String> players_list = config.getStringList("clans." + clan_name + ".participants");
        for (String item : players_list) {
            config.set("players." + item + ".clan", null);
            plugin.saveConfig();
        }

        clan_list.remove(clan_name);
        config.set("clan-list", clan_list);
        config.set("clans." + clan_name, null);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GOLD + "Вы успешно удалили клан - " + clan_name);
    }

    private void Accept(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        String player_name = player.getName();
        String clanName = config.getString("players." + player_name + ".acceptingClan");
        boolean accepting = config.getBoolean("players." + player_name + ".accepting");

        if (accepting) {
            int mates_number = config.getInt("clans." + clanName + ".clanmates");
            List<String> participants = config.getStringList("clans." + clanName + ".participants");
            String inviter = config.getString("players." + player_name + ".inviter");

            config.set("players." + player_name + ".clan", clanName);

            participants.add(player_name);
            config.set("clans." + clanName + ".participants", participants);
            config.set("clans." + clanName + ".clanmates", mates_number + 1);

            config.set("players." + player_name + ".accepting", null);
            config.set("players." + player_name + ".acceptingClan", null);
            config.set("players." + player_name + ".inviter", null);
            plugin.getServer().getPlayer(inviter).sendMessage(ChatColor.GOLD + "Игрок - " + player_name + " принял ваше приглашение.");
            plugin.saveConfig();
            player.sendMessage(ChatColor.GOLD + "Теперь вы в клане - " + clanName);
        } else {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У вас нет активных приглашений");
        }
    }

    private void Invite(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String player_name_for_invite = args[1];
        String clan_name;
        Player player_for_invite = plugin.getServer().getPlayer(player_name_for_invite);

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите ник игрока");
            return;
        }

        if (!config.contains("players." + player.getName() + ".clan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        if (config.contains("players." + player_name_for_invite + ".clan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Приглашаемый игрок уже состоит в клане");
            return;
        }

        if (player_for_invite == null) {
            player.sendMessage("Такого игрока нет на сервере");
            return;
        }

        clan_name = config.getString("players." + player.getName() + ".clan");

        if (!config.contains("players." + player_name_for_invite + ".acceptingClan")) {
            config.set("players." + player_name_for_invite + ".accepting", true);
            config.set("players." + player_name_for_invite + ".acceptingClan", clan_name);
            config.set("players." + player_name_for_invite + ".inviter", player.getName());

            plugin.getServer().getPlayer(player_name_for_invite).sendMessage(ChatColor.GOLD + "Вам пришло приглашение от клана - " + clan_name + " чтобы принять его напишите /clan accept. Срок 30 секунд.");
            plugin.saveConfig();
            player.sendMessage(ChatColor.GOLD + "Вы отправили приглашение игроку - " + player_name_for_invite);
            plugin.getServer().getPluginManager().callEvent(new InviteEvent(player_for_invite, clan_name));
        } else if (config.contains("players." + player_name_for_invite + ".acceptingClan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Данному игроку уже пришло приглашение, попробуйте еще раз через 30 секунд");
        }
    }

    private void Join(Player player, Rpg_plugin plugin, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите название клана");
            return;
        }

        FileConfiguration config = plugin.getConfig();
        String clan_name = args[1];
        List<String> clan_list = config.getStringList("clan-list");
        String owner = config.getString("clans." + clan_name + ".first-owner");
        List<String> joins = config.getStringList("players." + owner + ".joiners");
        boolean in_clan = config.contains("players." + player.getName() + ".clan");

        if (!(clan_list.contains(clan_name))) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого клана не существует");
            return;
        }

        if (in_clan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы уже состоите в клане");
            return;
        }

        if (joins != null) {
            if (joins.contains(player.getName())) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы уже отправили приглашение в этот клан");
                return;
            }
            joins.add(player.getName());
            config.set("players." + owner + ".joiners", joins);
        } else {
            List<String> joins_list = List.of(player.getName());
            config.set("players." + owner + ".joiners", joins_list);
        }

        player.sendMessage(ChatColor.GOLD + "Вы успешно отправили приглашение");
        plugin.saveConfig();
    }

    private void Kick(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String clan = config.getString("players." + player.getName() + ".clan");
        boolean in_clan = config.contains("players." + player.getName() + ".clan");
        List<String> owners = config.getStringList("clans." + clan + ".owners");
        String firstOwner = config.getString("clans." + clan + ".first-owner");
        int mates_nubmer = config.getInt("clans." + clan + ".clanmates");

        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите имя игрока, которого хотите исключить");
            return;
        }

        if (!in_clan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        if (!(owners.contains(player.getName()))) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не являетесь модератором или создателем клана");
            return;
        }

        String player_to_kick = args[1];

        if (args.length == 2) {

            if (owners.contains(player_to_kick) && !firstOwner.equals(player.getName())) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не можете исключить модератора или создателя клана");
                return;
            }

            if (owners.contains(player_to_kick) && firstOwner.equals(player.getName())) {
                owners.remove(player_to_kick);
                config.set("clans." + clan + ".owners", owners);
            }

            if (player.getName().equals(player_to_kick)) {
                player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не можете исключить самого себя");
                return;
            }

            List<String> playerslist = config.getStringList("clans." + clan + ".participants");
            playerslist.remove(player_to_kick);

            config.set("clans." + clan + ".participants", playerslist);
            config.set("players." + player_to_kick + ".clan", null);
            config.set("clans." + clan + ".clanmates", mates_nubmer - 1);
            player.sendMessage(ChatColor.GOLD + "Вы успешно исключили игрока - " + player_to_kick + " из клана.");

            if (plugin.getServer().getPlayer(player_to_kick) != null) {
                plugin.getServer().getPlayer(player_to_kick).sendMessage(ChatColor.RED + "К сожалению, вас исключили из клана - " + clan + ". Зря они так поступили(");
            } else {
                config.set("players." + player_to_kick + ".message", "К сожалению, вас исключили из клана. Зря они так поступили(");
            }
            plugin.saveConfig();
        }
    }

    private void List(Player player, Rpg_plugin plugin) {
        List<String> clanlist = plugin.getConfig().getStringList("clan-list");

        player.sendMessage(ChatColor.BLUE + "[Список кланов]: ");
        for (String item : clanlist) {
            player.sendMessage(ChatColor.GOLD + item);
        }
    }

    private void Info(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        boolean inClan = config.contains("players." + player.getName() + ".clan");

        if (inClan && args.length == 1) {
            String clan_name = config.getString("players." + player.getName() + ".clan");

            DisplayClanInfo(player, config, clan_name);
        } else if (args.length == 2 && config.getStringList("clan-list").contains(args[1])) {
            DisplayClanInfo(player, config, args[1]);
        } else player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите название существующего клана");
    }

    private void DisplayClanInfo(Player player, FileConfiguration config, String clan_name) {
        String firstOwner = config.getString("clans." + clan_name + ".first-owner");
        List<String> owners = config.getStringList("clans." + clan_name + ".owners");
        List<String> participants = config.getStringList("clans." + clan_name + ".participants");
        int clanmates = config.getInt("clans." + clan_name + ".clanmates");

        player.sendMessage(ChatColor.BLUE + "[Информация]");
        player.sendMessage(ChatColor.GREEN + "[Название клана]: " + ChatColor.GOLD + clan_name);
        player.sendMessage(ChatColor.GREEN + "[Создатель клана]: " + ChatColor.GOLD + firstOwner);
        player.sendMessage(ChatColor.GREEN + "[Список модераторов]: ");

        for (String item : owners) {
            player.sendMessage(ChatColor.RED + item);
        }

        player.sendMessage(ChatColor.GREEN + "[Количество игроков]: " + ChatColor.GOLD + clanmates);
        player.sendMessage(ChatColor.GREEN + "[Список игроков]: ");

        for (String item : participants) {
            player.sendMessage(ChatColor.GOLD + item);
        }
    }

    private void Requests(Player player, FileConfiguration config) {
        List<String> joins = config.getStringList("players." + player.getName() + ".joiners");
        boolean isHasJoins = config.contains("players." + player.getName() + ".joiners");

        if (!isHasJoins) {
            player.sendMessage(ChatColor.GOLD + "У вас нет активных запросов");
            return;
        }

        player.sendMessage(ChatColor.BLUE + "[Список запросов]: ");
        for (String item : joins) {
            player.sendMessage(ChatColor.GOLD + item);
        }

        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Чтобы принять одного человека в клан, напишите /req accept <ник>. Чтобы принять всех, напишите /req accept, чтобы отчистить, напишите /req clean");
    }

    private void Chat(Player player, Rpg_plugin plugin) {
        FileConfiguration config = plugin.getConfig();
        boolean inClan = config.contains("players." + player.getName() + ".clan");

        if (!inClan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        if (!config.contains("players." + player.getName() + ".chat")) {
            config.set("players." + player.getName() + ".chat", true);
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Режим кланового чата активирован");
        }
        else {
            boolean inClanChat = config.getBoolean("players." + player.getName() + ".chat");
            config.set("players." + player.getName() + ".chat", !inClanChat);

            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Режим кланового чата " + (!inClanChat ? "активирован" : "выключен"));
        }

        plugin.saveConfig();
    }

    private void SetHome(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String new_home_name;
        String clan;
        String owner;
        String home_name;
        boolean hasHome;

        if (!config.contains("players." + player.getName() + ".clan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        clan = config.getString("players." + player.getName() + ".clan");
        owner = config.getString("clans." + clan + ".first-owner");
        hasHome = config.contains("clans." + clan + ".homeName");

        if (!owner.equals(player.getName())) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не являетесь владельцем клана");
            return;
        }

        if (player.getWorld().getName().equals("hub")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не можете ставить точку дома здесь");
            return;
        }

        if (hasHome) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не можете создать две точки дома");
            return;
        }

        home_name = config.getString("clans." + clan + ".homeName");

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите название точки дома");
            return;
        }

        new_home_name = args[1];
        if (new_home_name.equals(home_name)) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Точка дома с таким именем уже существует");
        } else {
            List<Double> coords = List.of(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            config.set("clans." + clan + ".clanhomeLoc", coords);
            config.set("clans." + clan + ".homeName", new_home_name);
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вы создали точку кланового дома");
            plugin.saveConfig();
        }
    }

    private void RemoveHome(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String home_name_for_remove;
        String home_name;
        String clan_name;
        String owner_name;

        if (!config.contains("players." + player.getName() + ".clan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        clan_name = config.getString("players." + player.getName() + ".clan");
        owner_name = config.getString("clans." + clan_name + ".first-owner");

        if (!owner_name.equals(player.getName())) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не являетесь владельцем клана");
            return;
        }

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите название точки дома");
            return;
        }

        home_name = config.getString("clans." + clan_name + ".homeName");
        home_name_for_remove = args[1];

        if (home_name_for_remove.equals(home_name)) {
            config.set("clans." + clan_name + ".homeName", null);
            config.set("clans." + clan_name + ".clanhomeLoc", null);
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вы успешно удалили точку дома");
            plugin.saveConfig();
        } else {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такой точки дома не существует");
        }

    }

    private void Home(Player player, FileConfiguration config) {
        boolean in_clan = config.contains("players." + player.getName() + ".clan");
        String clan;
        boolean hasHome;

        if (!in_clan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        } else {
            clan = config.getString("players." + player.getName() + ".clan");
            hasHome = config.contains("clans." + clan + ".homeName");
        }

        if (!hasHome) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "У вашего клана нет точки дома");
            return;
        }

        List<Double> coords = config.getDoubleList("clans." + clan + ".clanhomeLoc");
        String homeName = config.getString("clans." + clan + ".homeName");
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Ваш клановый дом - " + homeName + " находится по координатам: ");
        player.sendMessage(ChatColor.BLUE + "X " + ChatColor.GOLD + coords.get(0));
        player.sendMessage(ChatColor.BLUE + "Y " + ChatColor.GOLD + coords.get(1));
        player.sendMessage(ChatColor.BLUE + "Z " + ChatColor.GOLD + coords.get(2));
    }

    private void SetModerator(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String player_for_set_name = args[1];

        String clan;
        String firstOwner;
        List<String> owners;
        List<String> participants;

        if (!config.contains("players." + player.getName() + ".clan")) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        } else {
            clan = config.getString("players." + player.getName() + ".clan");
            firstOwner = config.getString("clans." + clan + ".first-owner");
            owners = config.getStringList("clans." + clan + ".owners");
            participants = config.getStringList("clans." + clan + ".participants");
        }

        if (!firstOwner.equals(player.getName())) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не являетесь создателем клана");
            return;
        }

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Введите имя игрока");
            return;
        }

        if (!participants.contains(player_for_set_name)) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Такого игрока нет в вашем клане");
            return;
        }

        if (owners.contains(player_for_set_name)) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Этот игрок уже является модератором");
            return;
        }

        owners.add(player_for_set_name);
        config.set("clans." + clan + ".owners", owners);
        player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Вы добавили нового модератора");

        if (plugin.getServer().getPlayer(player_for_set_name) != null)
            plugin.getServer().getPlayer(player_for_set_name).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "Поздравляем! Отныне вы модератор клана");
        else
            config.set("players." + player_for_set_name + ".message", "Поздравляем! Отныне вы модератор сервера");

        plugin.saveConfig();
    }

    private void UnMod(Player player, Rpg_plugin plugin, String[] args) {
        FileConfiguration config = plugin.getConfig();
        String clan = config.getString("players." + player.getName() + ".clan");
        String firstOwner = config.getString("clans." + clan + ".first-owner");
        String playerDel = args[1];
        List<String> moderators = config.getStringList("clans." + clan + ".owners");
        boolean inClan = config.contains("players." + player.getName() + ".clan");

        if (!inClan) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не состоите в клане");
            return;
        }

        if (!player.getName().equals(firstOwner)) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Вы не являетесь создателем клана");
            return;
        }

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Напишите имя игрока");
            return;
        }

        if (!moderators.contains(playerDel)) {
            player.sendMessage(ChatColor.RED + "[Error] " + ChatColor.GOLD + "Этот игрок не является модератором вашего клана");
            return;
        }

        moderators.remove(playerDel);
        config.set("clans." + clan + ".owners", moderators);

        if (plugin.getServer().getPlayer(playerDel) != null) {
            plugin.getServer().getPlayer(playerDel).sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + "К сожалению, вы теперь не являетесь модератором клана(");
        } else {
            config.set("players." + playerDel + ".message", "К сожалению, вы теперь не являетесь модератором клана(");
        }

        plugin.saveConfig();
    }
}

