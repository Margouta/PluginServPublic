package fr.communaywen.core.evenements.menus;

import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EventsLootBoxMenu extends Menu {

    Player player;

    public EventsLootBoxMenu(Player player) {
        super(player);
    }

    @Override
    public @NotNull String getName() {

        return PlaceholderAPI.setPlaceholders(player, "§r§f%img_offset_-8%%img_buy_menu%");

    }

    @Override
    public @NotNull InventorySize getInventorySize() {

        return InventorySize.NORMAL;

    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();

        map.put(2, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.DARK_RED + "Fermer");
            itemMeta.setCustomModelData(10003);
        }).setCloseButton());

        return map;

    }

}
