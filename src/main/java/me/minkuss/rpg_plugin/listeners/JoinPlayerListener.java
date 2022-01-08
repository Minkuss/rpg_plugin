package me.minkuss.rpg_plugin.listeners;

import me.minkuss.rpg_plugin.Rpg_plugin;
import me.minkuss.rpg_plugin.runnables.CooldownCounter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import java.util.Arrays;
import java.util.List;

public class JoinPlayerListener implements Listener {
    private final Rpg_plugin _plugin;

    public JoinPlayerListener(Rpg_plugin plugin) {
        _plugin = plugin;
    }

    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        FileConfiguration config = _plugin.getConfig();
        Player player = event.getPlayer();
        String player_name = player.getName();

        if(!config.contains("players." + player_name)) {
            config.set("players." + player_name + ".exp", 0);
            config.set("players." + player_name + ".level", 1);
            config.createSection("players." + player_name + ".class");
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta bm = (BookMeta) book.getItemMeta();
            assert bm != null;
            String page1 = "Добро пожаловать! В этой книге будут подробно описаны все механики сервера. Настоятельно советуем прочитать!";
            String page2 = "Одним из самых важных аспектов нашего сервера являются роли игроков. На данный момент их всего две: рыцарь и разведчик. У каждой роли есть свои индивидуальные способности, которые вы будете получать, повышая свой уровень. Чтобы";
            String page3 = "выбрать себе роль, напишите комманду /role give. Подробнее про способности на следующей странице.";
            String page4 = "Способности рыцаря: берсерк, вампиризм. Берсерк: Чем меньше здоровье - тем больше урон. Вампиризм: чем больше урона вы наносите - тем быстрее восстанавливаете здоровье.";
            String page5 = "Способности разведчика: скрытное подкрадывание, невидимость, кража. Скрытное подкрадывание: в присяди вы становитесь невидимым. Невидимость: при получении критического урона накладывается бафф невидимость и";
            String page6 = "скорость. Кража: при ударе другого игрока в присяди, вы с вероятностью получите предмет из его инвентаря.";
            String page7 = "Следующий аспект - это получение опыта и уровней. Уровни повышаются за опыт, а опыт дается за убийство мобов, добычу руды и выполнение заданий, которые вы можете получить у жителей без профессии в маленьких башнях замка.";
            String page8 = "Последний аспект - это кланы. Чтобы увидеть весь перечень комманд кланов, напишите: /clan help. Исходя из названия сервера вы скорее всего поняли, что у нас лучше всего играть кланами. Кланы могут воевать за ресурсы и территорию, а так же выполнять";
            String page9 = "клановые задания, которые можно взять у тех же жителей создателю клана.";
            String page10 = "На этом гайд закончен, но он будет пополнятся, так как на сервере будет появляться очень много нововведений. Спасибо что прочитали, удачной игры!";
            bm.addPage(page1, page2, page3, page4, page5, page6, page7, page8, page9, page10);
            bm.setAuthor("ClanCraft");
            bm.setTitle("Гид по серверу");
            book.setItemMeta(bm);
            PlayerInventory playerInv = player.getInventory();
            playerInv.addItem(book);
            _plugin.saveConfig();
        }
        else if(config.contains("players." + player_name + ".class.skills")) {
            String role_name = config.getString("players." + player_name + ".class.name");
            List<String> skills = config.getStringList("role-skills." + role_name);

            for(String skill : skills) {
                if(config.getLong("players." + player_name + ".class.skills." + skill + ".time-left") > 0) {
                    new CooldownCounter(player_name, _plugin, skill).runTaskTimer(_plugin, 0, 20);
                }
            }
        }
        if(config.contains("players." + player.getName() + ".message")) {
            player.sendMessage(ChatColor.GREEN + "[Info] " + ChatColor.GOLD + config.getString("players." + player.getName() + ".message"));
            config.set("players." + player.getName() + ".message", null);
            _plugin.saveConfig();
        }


    }

}
