package fr.communaywen.core.evenements.menus;

import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import fr.communaywen.core.evenements.EventsCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsFloorMenu extends Menu {

    private int OpenFloor;

    Player player;

    public EventsFloorMenu(Player player) {

        super(player);

        if (EventsCommand.CommandState == 0) {

            ZoneId franceZoneId = ZoneId.of("Europe/Paris");

            //fuseau horaire de la France
            LocalDate currentDateInFrance = LocalDate.now(franceZoneId);

            if (currentDateInFrance.getDayOfWeek() == DayOfWeek.WEDNESDAY) {

                OpenFloor = 0;

            } else if (currentDateInFrance.getDayOfWeek() == DayOfWeek.THURSDAY) {

                OpenFloor = 1;

            } else if (currentDateInFrance.getDayOfWeek() == DayOfWeek.FRIDAY) {

                OpenFloor = 2;

            } else if (currentDateInFrance.getDayOfWeek() == DayOfWeek.SATURDAY) {

                OpenFloor = 3;

            } else if (currentDateInFrance.getDayOfWeek() == DayOfWeek.SUNDAY) {

                OpenFloor = 4;

            }

        }

    }

    @Override
    public @NotNull String getName() {

        return PlaceholderAPI.setPlaceholders(player, "§r§f%img_offset_-8%%img_mansion_menu%");
    }

    @Override
    public @NotNull InventorySize getInventorySize() {

        return InventorySize.LARGE;

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.PAPER && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Informations")){

            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta) book.getItemMeta();

            meta.setTitle("Manoir");
            meta.setAuthor("Nocolm le best enfaite x)");
            meta.addPage("§4Information très importante :\n\nVOUS POUVEZ PERDRE VOTRE EQUIPEMENT ET VOUS FAIRES VOLER DONC A VOS RISQUE ET PERILE");
            meta.addPage("chaque Jour un nouvel étage.\n\nLa difficulté augmente ainsi que la rareté des objets donnés en tuant des monstres");

            player.closeInventory();
            book.setItemMeta(meta);
            player.openBook(book);

        }

        if (clickedItem.getDisplayName().equals(ChatColor.GREEN + "Etage 1 : l'entrée")){

            player.sendMessage("tp au 1er étage");

            Location floor = new Location(player.getWorld(),26.5, 104, 6.5);
            player.teleport(floor);

        } else if (clickedItem.getDisplayName().equals(ChatColor.GREEN + "Etage 2 : L'étage")){

            player.sendMessage("tp au 2e étage");

            Location floor = new Location(player.getWorld(),30.5, 104, 200.5);
            player.teleport(floor);

        } else if (clickedItem.getDisplayName().contains(ChatColor.GREEN + "Etage 3 : L'étage" )){

            player.sendMessage("tp au 3e étage");

            Location floor = new Location(player.getWorld(),7.5, 104, 400.5);
            player.teleport(floor);

        } else if (clickedItem.getDisplayName().equals(ChatColor.GREEN + "Etage 4 : L'étage de l'éffroi")){

            player.sendMessage("tp au 4e étage");

            Location floor = new Location(player.getWorld(),7.5, 104, 600.5);
            player.teleport(floor);

        } else if (clickedItem.getDisplayName().contains(ChatColor.GREEN + "Etage §kl§r" + ChatColor.GREEN + " : Le §kGrenier§r ")){

            player.sendMessage("tp au §ke§re étage");

            Location floor = new Location(player.getWorld(),7.5, 104, 800.5);
            player.teleport(floor);

        }

    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();

        List<String> lore_floor1 = new ArrayList<>();
        List<String> lore_floor2 = new ArrayList<>();
        List<String> lore_floor3 = new ArrayList<>();
        List<String> lore_floor4 = new ArrayList<>();
        List<String> lore_floor5 = new ArrayList<>();

        lore_floor1.add(ChatColor.GOLD + "Difficulter : " + ChatColor.GREEN + "Moyenne" );
        lore_floor2.add(ChatColor.GOLD + "Difficulter : " + ChatColor.DARK_GREEN + "Normal" );
        lore_floor3.add(ChatColor.GOLD + "Difficulter : " + ChatColor.RED + "Difficile" );
        lore_floor4.add(ChatColor.GOLD + "Difficulter : " + ChatColor.DARK_RED + "Très Difficile" );
        lore_floor5.add(ChatColor.GOLD + "Difficulter : " + ChatColor.GREEN + "Impossible" );

        map.put(1, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.DARK_RED + "Fermer");
            itemMeta.setCustomModelData(10003);
        }).setCloseButton());

        map.put(7, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.GREEN + "Informations");
            itemMeta.setCustomModelData(10029);
        }));

        map.put(18, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.GREEN + "Etage 1 : l'entrée");
            itemMeta.setCustomModelData(10004);
            itemMeta.setLore(lore_floor1);
        }).setCloseButton());

        if (OpenFloor >= 1){

            map.put(20, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GREEN + "Etage 2 : L'étage");
                itemMeta.setCustomModelData(10000);
                itemMeta.setLore(lore_floor2);
            }).setCloseButton());

        } else {

            map.put(20, new ItemBuilder(this, Material.BARRIER, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GOLD + "§kl" + ChatColor.DARK_RED + " Etage indisponible : REVENEZ JEUDI " + ChatColor.GOLD + "§kl");
            }));

        }

        if (OpenFloor >= 2){

            map.put(22, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GREEN + "Etage 3 : L'étage ét§ka§r" + ChatColor.GREEN + "ng" + ChatColor.GREEN + "§ke§r");
                itemMeta.setCustomModelData(10001);
                itemMeta.setLore(lore_floor3);
            }).setCloseButton());

        } else {

            map.put(22, new ItemBuilder(this, Material.BARRIER, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GOLD + "§kl" + ChatColor.DARK_RED + " Etage indisponible : REVENEZ VENDREDI " + ChatColor.GOLD + "§kl");
            }));

        }

        if (OpenFloor >= 3){

            map.put(24, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GREEN + "Etage 4 : L'étage de l'éffroi");
                itemMeta.setCustomModelData(10002);
                itemMeta.setLore(lore_floor4);
            }).setCloseButton());

        } else {

            map.put(24, new ItemBuilder(this, Material.BARRIER, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GOLD + "§kl" + ChatColor.DARK_RED + " Etage indisponible : REVENEZ SAMEDI " + ChatColor.GOLD + "§kl");
            }));

        }

        if (OpenFloor >= 4){

            map.put(26, new ItemBuilder(this, Material.COOKIE, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GREEN + "Etage §kl§r" + ChatColor.GREEN + " : Le §kGrenier§r ");
                itemMeta.setCustomModelData(10003);
                itemMeta.setLore(lore_floor5);
            }).setCloseButton());

        } else {

            map.put(26, new ItemBuilder(this, Material.BARRIER, itemMeta -> {
                itemMeta.setDisplayName(ChatColor.GOLD + "§kl" + ChatColor.DARK_RED + " Etage indisponible : REVENEZ DIMANCHE " + ChatColor.GOLD + "§kl");
            }));

        }

        return map;

    }

}