package me.minkuss.rpg_plugin.events.quests;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClanQuestCompleteEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCanceled = false;

    private final String _clan_name;
    private final int _experience;

    public ClanQuestCompleteEvent(String clan_name, int experience) {
        _clan_name = clan_name;
        _experience = experience;
    }

    public String getClanName() {
        return _clan_name;
    }

    public int getExp() {
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
