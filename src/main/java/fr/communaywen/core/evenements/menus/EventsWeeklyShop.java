package fr.communaywen.core.evenements.menus;

import fr.communaywen.core.utils.constant.MessageManager;
import fr.communaywen.core.utils.constant.MessageType;
import fr.communaywen.core.utils.constant.Prefix;
import org.bukkit.enchantments.Enchantment;
import dev.lone.itemsadder.api.CustomStack;
import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import fr.communaywen.core.evenements.WeeklyItemType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EventsWeeklyShop extends Menu {

    private static WeeklyItemType currentWeeklyItemType;
    private final Player player;

    public EventsWeeklyShop(Player player) {

        super(player);
        this.player = player;

    }

    public static void setWeeklyItemType(WeeklyItemType selectWeeklyItemType) {

        currentWeeklyItemType = selectWeeklyItemType;

    }

    @Override
    public @NotNull String getName() {

        return PlaceholderAPI.setPlaceholders(player, "§r§f%img_offset_-8%%img_weekly_shop_menu%");

    }

    @Override
    public @NotNull InventorySize getInventorySize() {

        return InventorySize.NORMAL;

    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        String Items = currentWeeklyItemType.getMaterial();

        if (clickedItem != null && player.getInventory().firstEmpty() != -1){

            String currencyItemName = currentWeeklyItemType.getMoney();
            String currencyMoneyName = currentWeeklyItemType.getMoneyName();
            String currencyItemType = currentWeeklyItemType.getItemType();
            int requiredAmountPerItem = currentWeeklyItemType.getCost();

            int totalCurrencyItems = getTotalItemAmount(player, currencyItemName);

            if (totalCurrencyItems >= requiredAmountPerItem) {

                if (event.isShiftClick() && !currencyItemType.equals("enchanted_book")) {

                    int maxItemsToGive = totalCurrencyItems / requiredAmountPerItem;

                    removeItems(player, currencyItemName, maxItemsToGive * requiredAmountPerItem);

                    if (currencyItemType.equalsIgnoreCase("custom")){

                        giveCustomItems(player, currentWeeklyItemType.getItemName(), maxItemsToGive);

                    } else if (currencyItemType.equalsIgnoreCase("normal")){

                        giveItems(player, ItemStack.of(Material.valueOf(Items), maxItemsToGive));

                    }

                    MessageManager.sendMessageType(player,ChatColor.GREEN + "Tu as reçu " + ChatColor.GREEN + maxItemsToGive + " objets !", Prefix.HALLOWEEN, MessageType.SUCCESS, true);

                } else {

                    removeItems(player, currencyItemName, requiredAmountPerItem);

                    if (currencyItemType.equalsIgnoreCase("custom")){

                        giveCustomItems(player, currentWeeklyItemType.getItemName(), 1);

                    } else if (currencyItemType.equalsIgnoreCase("normal") || currencyItemType.equalsIgnoreCase("single")){

                        giveItems(player, ItemStack.of(Material.valueOf(Items), 1));

                    } else if (currencyItemType.equalsIgnoreCase("enchanted_book")){

                        ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                        if (meta != null) {

                            meta.addStoredEnchant(Objects.requireNonNull(Enchantment.getByName(currentWeeklyItemType.getItemName())), currentWeeklyItemType.getEnchant_level(), true);
                            enchantedBook.setItemMeta(meta);

                        }

                        player.getInventory().addItem(enchantedBook);

                    }

                    MessageManager.sendMessageType(player,ChatColor.GREEN + "Objet reçu !", Prefix.HALLOWEEN, MessageType.SUCCESS, true);

                }

            } else {

                MessageManager.sendMessageType(player, ChatColor.RED +  "Tu n'as pas assez de " + currencyMoneyName + " !", Prefix.HALLOWEEN, MessageType.ERROR, true);
                player.closeInventory();

            }

            event.setCancelled(true);

        } else {

            MessageManager.sendMessageType(player, ChatColor.RED +  "Votre Inventaire est plein", Prefix.HALLOWEEN, MessageType.ERROR, true);

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

    private void giveItems(Player player, ItemStack item){

        int maxStackSize = 64;
        int amountToAdd = item.getAmount();

        while (amountToAdd > 0) {

            int amountToSet = Math.min(amountToAdd, maxStackSize);
            ItemStack itemToAdd = item.clone();
            itemToAdd.setAmount(amountToSet);

            Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(itemToAdd);

            if (remainingItems.isEmpty()) {

                amountToAdd -= amountToSet;

            } else {

                MessageManager.sendMessageType(player, ChatColor.RED +  "Votre Inventaire est plein", Prefix.HALLOWEEN, MessageType.ERROR, true);
                break;

            }
        }
    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> map = new HashMap<>();
        List<String> lore_item = new ArrayList<>();

        lore_item.add(currentWeeklyItemType.getLore());

        map.put(13, new ItemBuilder(this, Objects.requireNonNull(Material.getMaterial(currentWeeklyItemType.getMaterial())), itemMeta -> {

            itemMeta.setDisplayName(currentWeeklyItemType.getDisplayName());
            itemMeta.setCustomModelData(currentWeeklyItemType.getData());
            itemMeta.setLore(lore_item);
        }));

        return map;

    }

}
