package fr.communaywen.core.evenements.Data;

import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.evenements.WeeklyItemType;
import fr.communaywen.core.evenements.menus.EventsWeeklyShop;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DataTime {

    private final AywenCraftPlugin plugin;
    private WeeklyItemType currentWeeklyItemType;
    private int timeLeft;

    public DataTime(AywenCraftPlugin plugin, EventsWeeklyShop weeklyShop, WeeklyItemType initialWeeklyItemType, int timeLeft) {
        this.plugin = plugin;
        this.currentWeeklyItemType = initialWeeklyItemType;
        this.timeLeft = timeLeft;
        EventsWeeklyShop.setWeeklyItemType(currentWeeklyItemType);

    }



    public void start() {

        new BukkitRunnable() {

            @Override
            public void run() {

                if (timeLeft <= 0) {

                    changeWeeklyItemType();
                    timeLeft = 120 * 1200; // 120 minute en ticks

                } else {

                    timeLeft -= 20;
                    saveState(timeLeft);

                }

            }

        }.runTaskTimer(plugin, 0L, 20L);

    }

    private void changeWeeklyItemType() {

        Random random = new Random();
        int itemTypeIndex = random.nextInt(WeeklyItemType.values().length);

        currentWeeklyItemType = WeeklyItemType.values()[itemTypeIndex];
        //debug a retirer plus tard ou pas
        Bukkit.getConsoleSender().sendMessage("Le WeeklyItemType a été changé en : " + currentWeeklyItemType.getItemName());

        saveState(timeLeft);

        EventsWeeklyShop.setWeeklyItemType(currentWeeklyItemType);

    }

    private void saveState(int timeLeft) {
        plugin.getConfig().set("weekly-item", currentWeeklyItemType.name());
        plugin.getConfig().set("time-left", timeLeft);
        plugin.saveConfig();
    }
}

