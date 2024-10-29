package fr.communaywen.core.evenements.listeners;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.evenements.LootboxCustomLoots;
import fr.communaywen.core.utils.Transaction;
import fr.communaywen.core.utils.constant.MessageManager;
import fr.communaywen.core.utils.constant.MessageType;
import fr.communaywen.core.utils.constant.Prefix;
import fr.communaywen.core.utils.database.TransactionsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;


public class InteractLootbox implements Listener {

    private int RewardType;
    private int Money;

    @EventHandler
    private void onFurnitureInteractEvent(FurnitureInteractEvent furniture) {
        if (furniture.getNamespacedID().equals("halloween:lootbox")) {

            Player player = furniture.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            CustomStack customStack = CustomStack.byItemStack(itemInHand);

            if (customStack != null) {

                String itemId = customStack.getId();

                if (player.getInventory().firstEmpty() == -1){

                    MessageManager.sendMessageType(player,ChatColor.RED + "Votre inventaire est plein ! Veuillez libérer de l'espace pour utiliser la lootbox", Prefix.HALLOWEEN, MessageType.ERROR, true);

                } else {

                    if (itemId.equals("halloween_key")){

                        LootboxCustomLoots customItems = null;
                        Material Items = null;

                        Random r = new Random();
                        int chance = r.nextInt(201);

                        if (chance >= 1 && chance <= 5){

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.YELLOW_CANDY_BLOCK;

                        } else if (chance >=6 && chance <=10){

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.PURPLE_CANDY_BLOCK;

                        } else if (chance >=11 && chance <=15){

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.HALLOWEEN_KEY;

                        } else if (chance >=16 && chance <=25){

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.BLUE_CANDY_BLOCK;

                        } else if (chance >=26 && chance <=50){

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.WITCH_HAT;

                        } else if (chance >=51 && chance <=75) {

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.SPIDER_HAT;

                        } else if (chance >=76 && chance <=100) {

                            this.RewardType = 0;
                            customItems = LootboxCustomLoots.SCREAM_MASK;

                        }else if (chance >=101 && chance <=125){

                            this.RewardType = 1;
                            Items = Material.DIAMOND;

                        } else if (chance >=126 && chance <=150){

                            this.RewardType = 1;
                            Items = Material.EMERALD;

                        }else if (chance >=151 && chance <=175){

                            this.RewardType = 2;
                            this.Money = 100;

                        }else if (chance >=176 && chance <=190){

                            this.RewardType = 2;
                            this.Money = 250;

                        }else if (chance >=191 && chance <=195){

                            this.RewardType = 2;
                            this.Money = 500;

                        }else if (chance >=196 && chance <=200){

                            this.RewardType = 2;
                            this.Money = 750;

                        }else if (chance == 201){

                            this.RewardType = 2;
                            this.Money = 10000;

                        }

                        if (itemInHand.getAmount() > 1) {

                            itemInHand.setAmount(itemInHand.getAmount() - 1);

                        } else {

                            player.getInventory().remove(itemInHand);

                        }

                        if (RewardType == 0){

                            int amount = customItems.getAmount();
                            ItemStack itemStack = customItems.getItemStack();
                            player.getInventory().addItem(itemStack.asQuantity(amount));
                            player.sendMessage(ChatColor.GREEN + "objet reçu :" + ChatColor.GREEN + itemStack.getDisplayName());
                            MessageManager.sendMessageType(player,ChatColor.GREEN + "objet reçu :" + ChatColor.GREEN + itemStack.getDisplayName(), Prefix.HALLOWEEN, MessageType.SUCCESS, true);

                        } else if (RewardType == 1){

                            player.getInventory().addItem(ItemStack.of(Items));
                            MessageManager.sendMessageType(player,ChatColor.GREEN + "objet reçu :" + Items, Prefix.HALLOWEEN, MessageType.SUCCESS, true);

                        } else if (RewardType == 2){

                            AywenCraftPlugin.getInstance().getManagers().getEconomyManager().addBalance(player.getUniqueId(), Money);
                            new TransactionsManager().addTransaction(new Transaction(
                                    player.getUniqueId().toString(),
                                    "CONSOLE",
                                    Money,
                                    "Quest Reward"
                            ));

                            MessageManager.sendMessageType(player,ChatColor.GREEN + " money reçu :" + Money, Prefix.HALLOWEEN, MessageType.SUCCESS, true);

                        }

                    } else {

                        MessageManager.sendMessageType(player,ChatColor.RED + "Vous devez tenir une clée halloween en main pour utiliser la lootbox", Prefix.HALLOWEEN, MessageType.WARNING, true);

                    }

                }

            } else {

                MessageManager.sendMessageType(player,ChatColor.RED + "Vous devez tenir une clée halloween en main pour utiliser la lootbox", Prefix.HALLOWEEN, MessageType.WARNING, true);

            }

        }

    }

}