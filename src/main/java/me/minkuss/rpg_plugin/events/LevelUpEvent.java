package me.minkuss.rpg_plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LevelUpEvent extends Event {

    private final Player _player;
    private final static HandlerList _handlers = new HandlerList();
    private final int _level;

    public LevelUpEvent(Player player, int level) {
        _player = player;
        _level = level;
    }

    public Player getPlayer() {
        return _player;
    }

    public int getLevel() {
        return _level;
    }

    @Override
    public HandlerList getHandlers() {
        return _handlers;
    }
}
