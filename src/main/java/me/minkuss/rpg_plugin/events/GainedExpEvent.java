package me.minkuss.rpg_plugin.events;

import me.minkuss.rpg_plugin.Rpg_plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GainedExpEvent extends Event implements Cancellable {

    private final static HandlerList _handlers = new HandlerList();
    private boolean isCanceled = false;
    private Player _player;
    private int _experience;

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
        return _handlers;
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
