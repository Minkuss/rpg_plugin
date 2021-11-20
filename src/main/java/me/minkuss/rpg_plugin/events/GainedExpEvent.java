package me.minkuss.rpg_plugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GainedExpEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCanceled = false;

    private final Player _player;
    private final int _experience;

    public GainedExpEvent(Player player, int exp) {
        _experience = exp;
        _player = player;
    }

    public Player getPlayer() {
        return _player;
    }

    public int getExperience() {
        return _experience;
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
        return isCanceled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCanceled = b;
    }
}
