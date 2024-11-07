package fr.communaywen.core.evenements.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class CandyEatListener implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData()) {

            if (item.getItemMeta().getCustomModelData() == 10000) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));

            } else if (item.getItemMeta().getCustomModelData() == 10001) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 0));

            } else if (item.getItemMeta().getCustomModelData() == 10002) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 300, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 600, 0));

            } else if (item.getItemMeta().getCustomModelData() == 10003) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1200, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 600, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1200, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));

            } else if (item.getItemMeta().getCustomModelData() == 10004) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 600, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 1));

            }

        }

    }

}