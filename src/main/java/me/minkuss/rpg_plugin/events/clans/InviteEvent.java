package me.minkuss.rpg_plugin.events.clans;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InviteEvent extends Event {
    private final Player _player;
    private final static HandlerList _handlers = new HandlerList();
    private final String _accepting_clan;

    public InviteEvent(Player player, String clan_name) {
        _player = player;
        _accepting_clan = clan_name;
    }

    public Player getPlayer() {return _player;}

    public String getClanName() {return _accepting_clan;}

    @Override
    public HandlerList getHandlers() {return _handlers;}

    public static HandlerList getHandlerList() { return _handlers; };
}
