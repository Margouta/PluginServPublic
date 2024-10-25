package fr.communaywen.core.contest.menu;

import dev.lone.itemsadder.api.CustomStack;
import dev.xernas.menulib.Menu;
import dev.xernas.menulib.utils.InventorySize;
import dev.xernas.menulib.utils.ItemBuilder;
import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.contest.cache.ContestCache;
import fr.communaywen.core.contest.managers.ColorConvertor;
import fr.communaywen.core.contest.managers.ContestManager;
import fr.communaywen.core.utils.ItemUtils;
import fr.communaywen.core.utils.constant.MessageManager;
import fr.communaywen.core.utils.constant.MessageType;
import fr.communaywen.core.utils.constant.Prefix;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class ContributionMenu extends Menu {
    private final AywenCraftPlugin plugin;
    private final ContestManager contestManager;
    private final ContestCache contestCache;

    public ContributionMenu(Player owner, AywenCraftPlugin plugin, ContestManager manager) {
        super(owner);
        this.contestManager = manager;
        this.contestCache = plugin.getManagers().getContestCache();
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getName() {
        return PlaceholderAPI.setPlaceholders(getOwner(), "§r§f%img_offset_-48%%img_contest_menu%");
    }

    @Override
    public @NotNull InventorySize getInventorySize() {
        return InventorySize.LARGE;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent click) {
    }

    @Override
    public @NotNull Map<Integer, ItemStack> getContent() {
        Map<Integer, ItemStack> inventory = new HashMap<>();

        String campName = contestManager.getPlayerCampName(getOwner());
        ChatColor campColor = contestCache.getPlayerColorCache(getOwner());
        Material m = ColorConvertor.getMaterialFromColor(campColor);

        List<String> loreinfo = new ArrayList<String>();
        List<String> lore_randomevent = new ArrayList<String>();
        List<String> lore_contribute = new ArrayList<String>();
        List<String> lore_trade = new ArrayList<String>();
        List<String> lore_rang = new ArrayList<String>();

        loreinfo.add("§7Apprenez en plus sur les Contest !");
        loreinfo.add("§7Le déroulement..., Les résultats, ...");
        loreinfo.add("§e§lCLIQUEZ ICI POUR EN VOIR PLUS!");

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection boostEvents = config.getConfigurationSection("contest.boost_event");

        if  (boostEvents != null) {
            for (String event : boostEvents.getKeys(true)) {
                ConfigurationSection eventInfo = boostEvents.getConfigurationSection(event);

                if (eventInfo != null) {
                    lore_randomevent.add("§b+" + eventInfo.getInt("boost") + "% §7" + eventInfo.getString("name"));
                }
            }
        }

        lore_randomevent.add("§8Les probabilités qu'un event se produise plus souvent, sont choisis dès le début du Contest");

        Material shell_contest = CustomStack.getInstance("contest:contest_shell").getItemStack().getType();
        lore_contribute.add("§7Donner vos §bCoquillages de Contest");
        lore_contribute.add("§7Pour faire gagner votre"+ campColor +" Team!");
        lore_contribute.add("§e§lCliquez pour verser tout vos Coquillages");

        lore_trade.add("§7Faites des Trades contre des §bCoquillages de Contest");
        lore_trade.add("§7Utile pour faire gagner ta"+ campColor +" Team");
        lore_trade.add("§e§lCliquez pour acceder au Menu des trades");

        lore_rang.add(campColor + contestManager.getRankContest(getOwner()) + campName);
        lore_rang.add("§7Progression §8: " + campColor + contestCache.getPlayerPointsCache(getOwner()) + "§8/" + campColor + contestManager.getRepPointsToRank(getOwner()));
        lore_rang.add("§e§lAUGMENTER DE RANG POUR VOIR DES RECOMPENSES MEILLEURES");

                inventory.put(8, new ItemBuilder(this, Material.GOLD_BLOCK, itemMeta -> {
                    itemMeta.setDisplayName("§6§lVotre Grade");
                    itemMeta.setLore(lore_rang);
                }));


                inventory.put(10, new ItemBuilder(this, shell_contest, itemMeta -> {
                    itemMeta.setDisplayName("§7Les Trades");
                    itemMeta.setLore(lore_trade);
                    itemMeta.setCustomModelData(10000);
                }).setNextMenu(new TradeMenu(getOwner(), contestManager)));

                inventory.put(13, new ItemBuilder(this, m, itemMeta -> {
                    itemMeta.setDisplayName("§r§7Contribuer pour la"+ campColor+ " Team " + campName);
                    itemMeta.setLore(lore_contribute);
                }).setOnClick(inventoryClickEvent -> {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                        try {
                            ItemStack shell_contestItem = CustomStack.getInstance("contest:contest_shell").getItemStack();

                            int shellCount = Arrays.stream(getOwner().getInventory().getContents()).filter(is -> is != null && is.isSimilar(shell_contestItem)).mapToInt(ItemStack::getAmount).sum();

                            if (ItemUtils.hasEnoughItems(getOwner(), shell_contestItem.getType(), shellCount)) {
                                ItemUtils.removeItemsFromInventory(getOwner(), shell_contestItem.getType(), shellCount);

                                    int newPlayerPoints = shellCount + contestManager.getPlayerPoints(getOwner());
                                    int updatedCampPoints = shellCount + contestManager.getInt("contest", "points" + contestCache.getPlayerCampsCache(getOwner()));


                                contestManager.addPointPlayer(newPlayerPoints, getOwner());

                                contestManager.updateColumnInt("contest", "points" + contestCache.getPlayerCampsCache(getOwner()), updatedCampPoints);

                                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                                        MessageManager.sendMessageType(getOwner(), "§7Vous avez déposé§b " + shellCount + " Coquillage(s) de Contest§7 pour votre Team!", Prefix.CONTEST, MessageType.SUCCESS, true);
                                    });
                            } else {
                                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                                    MessageManager.sendMessageType(getOwner(), "§cVous n'avez pas de Coquillage(s) de Contest§7", Prefix.CONTEST, MessageType.ERROR, true);
                                });
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }));

                inventory.put(16, new ItemBuilder(this, Material.OMINOUS_TRIAL_KEY, itemMeta -> {
                    itemMeta.setDisplayName("§r§1Boost d'Evenement!");
                    itemMeta.setLore(lore_randomevent);
                }));

                inventory.put(35, new ItemBuilder(this, Material.EMERALD, itemMeta -> {
                    itemMeta.setDisplayName("§r§aPlus d'info !");
                    itemMeta.setLore(loreinfo);
                }).setNextMenu(new MoreInfoMenu(getOwner(), contestManager)));

        return inventory;
    }
}
