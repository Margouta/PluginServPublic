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
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class EventsMenus extends Menu {

    Player player;

    public EventsMenus(Player player) {

        super(player);

    }

    @Override
    public @NotNull String getName() {

        return PlaceholderAPI.setPlaceholders(player, "§r§f%img_offset_-8%%img_main_menu%");

    }

    @Override
    public @NotNull InventorySize getInventorySize() {
        return InventorySize.LARGEST;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.PAPER && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Informations")){

            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();
            meta.setTitle("halloween");
            meta.setAuthor("Nocolm le best enfaite x)");
            meta.addPage("§2Infortmation§r :\n\n-page 2 : évenements\n\n-page 3 : shop !\n\n-page 4 : weekly shop !\n\n-page 5 : lootbox !\n\n§cPour voir les crafts faites /iarecipe halloween:...");
            meta.addPage("§6Evenements§r :\n\nDurée : 1 semaine\n\nThème : Halloween\n\nCréateur : Nocolm\n\nContributeur :\n\n- Mcroos_bow\n- gab400\n- iambibi\n- Cendriz");
            meta.addPage("§6Shop§r :\n\nLe shop est un endroit ou vous pouvez acheter des bonbons ( logique ) \n\n §cLes bonbons sont a consommer sans modérations !§r");
            meta.addPage("§6Weekly Shop§r :\n\nLe weekly shop est un shop hebdomadaire/temporaire, c'est à dire que les items présent a l'achat sont §créinitialisés toutes les 2 heures !§r");
            meta.addPage("§6Lootbox§r : La lootbox se trouve au spawn, vous pouvez l'utiliser en ayant une clée halloween ( pour voir son craft faites\n§6/iarecipe halloween:halloween_key§r )");


            player.closeInventory();
            book.setItemMeta(meta);
            player.openBook(book);

        }

    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();

        map.put(2, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.DARK_RED + "Fermer");
            itemMeta.setCustomModelData(10003);
        }).setCloseButton());

        map.put(6, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.GREEN + "Informations");
            itemMeta.setCustomModelData(10029);
        }));

        map.put(29, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.YELLOW + "Accès au " + ChatColor.RED + " Weekly Shop ");
            itemMeta.setCustomModelData(10004);
        }).setNextMenu(new EventsWeeklyShop(getOwner())));

        map.put(33, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.YELLOW + "Accès au " + ChatColor.RED + "Shop " + ChatColor.YELLOW + "de " + ChatColor.RED + "BONBON");
            itemMeta.setCustomModelData(10030);
        }).setNextMenu(new EventsCandyShop(getOwner())));

        return map;

    }

}