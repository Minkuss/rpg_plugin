package me.minkuss.rpg_plugin.runnables;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SneakChecker extends BukkitRunnable {

    Player _player;

    public SneakChecker(Player player) {
        _player = player;
    }

    @Override
    public void run() {
        if(_player.isSneaking())
            _player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 41, 1, false, false));
        else
            cancel();
    }
}
