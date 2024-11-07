package fr.communaywen.core.evenements.listeners;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Random;

public class MansionLootTable implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();

        if (event.getEntity().getCustomName() != null) {

            if (entity.getCustomName().equals("Level_1")) {
                event.getDrops().clear();

                Random random = new Random();


                if (random.nextInt(100) <= 75) {
                    ItemStack item1 = CustomStack.getInstance("halloween:candy_monster").getItemStack();
                    event.getDrops().add(item1);
                }

                if (random.nextInt(100) <= 25) {
                    ItemStack item2 = CustomStack.getInstance("halloween:candy_green").getItemStack();
                    event.getDrops().add(item2);
                }
            } else if (entity.getCustomName().equals("Level_2")) {

                event.getDrops().clear();

                Random random = new Random();


                if (random.nextInt(100) <= 50) {
                    ItemStack item1 = CustomStack.getInstance("halloween:candy_monster").getItemStack();
                    event.getDrops().add(item1);
                }

                if (random.nextInt(100) <= 35) {
                    ItemStack item2 = CustomStack.getInstance("halloween:candy_green").getItemStack();
                    event.getDrops().add(item2);
                }

                if (random.nextInt(100) <= 25) {
                    ItemStack item3 = CustomStack.getInstance("halloween:candy_blue").getItemStack();
                    event.getDrops().add(item3);
                }

            } else if (entity.getCustomName().equals("Level_3")) {

                event.getDrops().clear();

                Random random = new Random();


                if (random.nextInt(100) <= 50) {
                    ItemStack item1 = CustomStack.getInstance("halloween:candy_green").getItemStack();
                    event.getDrops().add(item1);
                }

                if (random.nextInt(100) <= 35) {
                    ItemStack item2 = CustomStack.getInstance("halloween:candy_blue").getItemStack();
                    event.getDrops().add(item2);
                }

                if (random.nextInt(100) <= 25) {
                    ItemStack item3 = CustomStack.getInstance("halloween:candy_purple").getItemStack();
                    event.getDrops().add(item3);
                }

                if (random.nextInt(100) <= 2) {
                    ItemStack item4 = CustomStack.getInstance("halloween:candy_yellow").getItemStack();
                    event.getDrops().add(item4);
                }

            } else if (entity.getCustomName().equals("Level_4")) {

                event.getDrops().clear();

                Random random = new Random();


                if (random.nextInt(100) <= 75) {
                    ItemStack item1 = CustomStack.getInstance("halloween:candy_green").getItemStack();
                    event.getDrops().add(item1);
                }

                if (random.nextInt(100) <= 50) {
                    ItemStack item2 = CustomStack.getInstance("halloween:candy_blue").getItemStack();
                    event.getDrops().add(item2);
                }

                if (random.nextInt(100) <= 25) {
                    ItemStack item3 = CustomStack.getInstance("halloween:candy_purple").getItemStack();
                    event.getDrops().add(item3);
                }

                if (random.nextInt(100) <= 5) {
                    ItemStack item4 = CustomStack.getInstance("halloween:candy_yellow").getItemStack();
                    event.getDrops().add(item4);
                }

                if (random.nextInt(100) <= 2) {
                    ItemStack item5 = CustomStack.getInstance("halloween:halloween_key").getItemStack();
                    event.getDrops().add(item5);
                }

            } else if (entity.getCustomName().equals("Level_5")) {

                event.getDrops().clear();

                Random random = new Random();


                if (random.nextInt(100) <= 75) {
                    ItemStack item2 = CustomStack.getInstance("halloween:candy_purple").getItemStack();
                    event.getDrops().add(item2);
                }

                if (random.nextInt(100) <= 25) {
                    ItemStack item3 = CustomStack.getInstance("halloween:candy_yellow").getItemStack();
                    event.getDrops().add(item3);
                }

                if (random.nextInt(100) <= 5) {
                    ItemStack item4 = CustomStack.getInstance("halloween:halloween_key").getItemStack();
                    event.getDrops().add(item4);
                }

                if (random.nextInt(500) <= 1) {
                    ItemStack item5 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item5.getItemMeta();
                    meta.addStoredEnchant(Enchantment.SHARPNESS, 6, true);
                    item5.setItemMeta(meta);
                    event.getDrops().add(item5);
                }

                if (random.nextInt(500) <= 1) {
                    ItemStack item6 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item6.getItemMeta();
                    meta.addStoredEnchant(Enchantment.UNBREAKING, 4, true);
                    item6.setItemMeta(meta);
                    event.getDrops().add(item6);
                }

                if (random.nextInt(500) <= 1) {
                    ItemStack item7 = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item7.getItemMeta();
                    meta.addStoredEnchant(Enchantment.PROTECTION, 5, true);
                    item7.setItemMeta(meta);
                    event.getDrops().add(item7);
                }
            }
        }
    }
}
