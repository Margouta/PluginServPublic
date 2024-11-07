package fr.communaywen.core.evenements.listeners;

import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.evenements.menus.EventsFloorMenu;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class VillagerListener implements Listener {

    AywenCraftPlugin plugin;

    public VillagerListener(AywenCraftPlugin plugin){

        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.VILLAGER) {

            World customWorld = Bukkit.getWorld("Mansion");
            if (player.getWorld().equals(customWorld)) {

                    EventsFloorMenu menu = new EventsFloorMenu(player);
                    menu.open();


            }

        }

    }

}
