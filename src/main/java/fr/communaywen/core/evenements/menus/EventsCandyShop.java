package fr.communaywen.core.evenements.menus;

import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import fr.communaywen.core.evenements.CandyType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsCandyShop extends Menu {

    Player player;

    public EventsCandyShop(Player player) {

        super(player);
        this.player = player;

    }

    @Override
    public @NotNull String getName() {

        World world = player.getWorld();
        long time = world.getTime();

        String dayNightStatus = "";

        if (time >= 0 && time < 12300) {

            dayNightStatus = "§r§f%img_offset_-8%%img_day_shop_menu%";
        }

        else if (time >= 12300 && time < 24000) {

            dayNightStatus = "§r§f%img_offset_-8%%img_night_shop_menu%";

        }

        return PlaceholderAPI.setPlaceholders(player, dayNightStatus);

    }

    @Override
    public @NotNull InventorySize getInventorySize() {

        return InventorySize.NORMAL;

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.hasItemMeta()) {

            String itemName = clickedItem.getItemMeta().getDisplayName();

            CandyType candyType = null;

            if (itemName.contains(ChatColor.GREEN.toString())) {

                candyType = CandyType.CANDY_GREEN;

            } else if (itemName.contains(ChatColor.BLUE.toString())) {

                candyType = CandyType.CANDY_BLUE;

            } else if (itemName.contains(ChatColor.LIGHT_PURPLE.toString())) {

                candyType = CandyType.CANDY_PURPLE;

            } else if (itemName.contains(ChatColor.GOLD.toString())) {

                candyType = CandyType.CANDY_YELLOW;

            }

            if (candyType != null) {

                EventsBuyCandy buyCandyMenu = new EventsBuyCandy(player);

                buyCandyMenu.setSelectedCandyType(candyType);

                buyCandyMenu.open();

            } else {

                player.sendMessage(ChatColor.RED + "Erreur : Item Invalide.");

            }

            // débug a retirer plus tard
            player.sendMessage("Tu as sélectionné : " + itemName);

        }

    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();

        List<String> lore_money1 = new ArrayList<>();
        List<String> lore_money2 = new ArrayList<>();
        List<String> lore_money3 = new ArrayList<>();
        List<String> lore_money4 = new ArrayList<>();

        lore_money1.add("A vendre pour 5 Bonbon Monstre");
        lore_money2.add("Acheter pour 3 Bonbon " + ChatColor.GREEN + "Vert");
        lore_money3.add("Acheter pour 3 Bonbon " + ChatColor.BLUE + "Bleu");
        lore_money4.add("Acheter pour 3 Bonbon " + ChatColor.LIGHT_PURPLE + "Violet");

        map.put(10, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(CandyType.CANDY_GREEN.getDisplayName());
            itemMeta.setCustomModelData(10000);
            itemMeta.setLore(lore_money1);
        }));

        map.put(12, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(CandyType.CANDY_BLUE.getDisplayName());
            itemMeta.setCustomModelData(10001);
            itemMeta.setLore(lore_money2);
        }));

        map.put(14, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(CandyType.CANDY_PURPLE.getDisplayName());
            itemMeta.setCustomModelData(10002);
            itemMeta.setLore(lore_money3);
        }));

        map.put(16, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(CandyType.CANDY_YELLOW.getDisplayName());
            itemMeta.setCustomModelData(10003);
            itemMeta.setLore(lore_money4);
        }));

        return map;

    }

}
