package me.minkuss.rpg_plugin.events.quests;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QuestCompleteEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCanceled = false;

    private final Player _player;
    private final int _experience;
    private final int _money;

    public QuestCompleteEvent(Player player, int experience, int money) {
        _player = player;
        _experience = experience;
        _money = money;
    }

    public Player getPlayer() {
        return _player;
    }

    public int getExp() {
        return _experience;
    }

    public int getMoney() {return _money;}


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
