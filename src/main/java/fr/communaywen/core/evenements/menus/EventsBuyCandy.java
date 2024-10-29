package fr.communaywen.core.evenements.menus;

import dev.lone.itemsadder.api.CustomStack;
import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import fr.communaywen.core.evenements.CandyType;
import lombok.Setter;
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

public class EventsBuyCandy extends Menu {

    @Setter
    private CandyType selectedCandyType;
    Player player;

    public EventsBuyCandy(Player player) {

        super(player);
        this.player = player;

    }

    @Override
    public @NotNull String getName() {

        World world = player.getWorld();
        long time = world.getTime();

        String dayNightStatus = "";

        if (time >= 0 && time < 12300) {

            dayNightStatus = "§r§f%img_offset_-8%%img_day_buy_menu%";
        }

        else if (time >= 12300 && time < 24000) {

            dayNightStatus = "§r§f%img_offset_-8%%img_night_buy_menu%";

        }

        return PlaceholderAPI.setPlaceholders(player, dayNightStatus);

    }


    @Override
    public @NotNull InventorySize getInventorySize() {

        return InventorySize.NORMAL;

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem != null && clickedItem.getType() == Material.PAPER && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "buy")) {

            String currencyItemName = selectedCandyType.getMoney();
            String currencyMoneyName = selectedCandyType.getMoneyName();
            int requiredAmountPerItem = selectedCandyType.getCost();

            int totalCurrencyItems = getTotalItemAmount(player, currencyItemName);

            if (totalCurrencyItems >= requiredAmountPerItem) {

                if (event.isShiftClick()) {

                    int maxItemsToGive = totalCurrencyItems / requiredAmountPerItem;

                    removeItems(player, currencyItemName, maxItemsToGive * requiredAmountPerItem);
                    giveCustomItems(player, selectedCandyType.getItemName(), maxItemsToGive);

                    player.sendMessage(ChatColor.GREEN + "Tu as reçu " + ChatColor.GREEN + maxItemsToGive + " objets !");

                } else {

                    removeItems(player, currencyItemName, requiredAmountPerItem);
                    giveCustomItems(player, selectedCandyType.getItemName(), 1);

                    player.sendMessage(ChatColor.GREEN + "Objet reçu !");

                }

            } else {

                player.sendMessage(ChatColor.RED + "Tu n'as pas assez de " + currencyMoneyName + " !");
                player.closeInventory();

            }

            event.setCancelled(true);
        }

    }

    private int getTotalItemAmount(Player player, String customItemName) {

        int totalAmount = 0;

        for (ItemStack item : player.getInventory().getContents()) {

            if (item != null) {

                CustomStack customStack = CustomStack.byItemStack(item);

                if (customStack != null && customStack.getNamespacedID().equals(customItemName)) {

                    totalAmount += item.getAmount();

                }

            }

        }

        return totalAmount;

    }

    private void removeItems(Player player, String customItemName, int amount) {

        for (ItemStack item : player.getInventory().getContents()) {

            if (item != null) {

                CustomStack customStack = CustomStack.byItemStack(item);
                if (customStack != null && customStack.getNamespacedID().equals(customItemName)) {

                    int itemAmount = item.getAmount();

                    if (itemAmount > amount) {

                        item.setAmount(itemAmount - amount);

                        break;

                    } else {

                        amount -= itemAmount;

                        player.getInventory().remove(item);

                        if (amount <= 0) {

                            break;

                        }

                    }

                }

            }

        }

    }

    private void giveCustomItems(Player player, String itemName, int amount) {

        CustomStack customItem = CustomStack.getInstance(itemName);

        if (customItem != null) {

            int maxStackSize = 64;

            ItemStack itemsToGive = customItem.getItemStack();

            while (amount > 0) {

                int stackSize = Math.min(amount, maxStackSize);

                ItemStack stack = itemsToGive.clone();
                stack.setAmount(stackSize);
                player.getInventory().addItem(stack);
                amount -= stackSize;

            }

        } else {

            player.sendMessage(ChatColor.RED + "Erreur : si celle-ci persiste, contactez un opérateur");

        }

    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.GOLD + "Etes vous sûr de voiloir acheter cela ?");

        map.put(13, new ItemBuilder(this, Material.PAPER, itemMeta -> {
            itemMeta.setDisplayName(ChatColor.GOLD + "buy");
            itemMeta.setCustomModelData(10030);
            itemMeta.setLore(lore);
        }));

        return map;

    }

}
