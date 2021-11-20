package me.minkuss.rpg_plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LevelUpEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean _isCancelled = false;

    private final Player _player;
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
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return _isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        _isCancelled = b;
    }
}
