package fr.communaywen.core.staff.players;

import dev.lone.itemsadder.api.CustomStack;
import dev.xernas.menulib.PaginatedMenu;
import dev.xernas.menulib.utils.ItemBuilder;
import dev.xernas.menulib.utils.ItemUtils;
import dev.xernas.menulib.utils.StaticSlots;
import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.teams.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersMenu extends PaginatedMenu {
	private final AywenCraftPlugin instance = AywenCraftPlugin.getInstance();
	private final TeamManager teamManager = new TeamManager(instance);
	
	public PlayersMenu(Player owner) {
		super(owner);
	}
	
	@Override
	public @Nullable Material getBorderMaterial() {
		return Material.LIGHT_GRAY_STAINED_GLASS_PANE;
	}
	
	@Override
	public @NotNull List<Integer> getStaticSlots() {
		return StaticSlots.BOTTOM;
	}
	
	@Override
	public @NotNull List<ItemStack> getItems() {
		ArrayList<Player> players = new ArrayList<>(this.getOwner().getServer().getOnlinePlayers());
		
		List<ItemStack> items = new ArrayList<>();
		for (Player player : players) {
			items.add(new ItemBuilder(this, ItemUtils.getPlayerSkull(player.getUniqueId()), itemMeta -> {
				itemMeta.setDisplayName(ChatColor.WHITE + player.getName());
				itemMeta.setLore(List.of(
						ChatColor.GOLD + "Vie : " + ChatColor.RED + player.getHealth(),
						ChatColor.GOLD + "XP : " + ChatColor.AQUA + player.getExp(),
						ChatColor.BLUE + "Team : " + (teamManager.getTeamByPlayer(player.getUniqueId()) == null ?
								ChatColor.ITALIC + "Wilderness" : teamManager.getTeamByPlayer(player.getUniqueId()).getName())
				));
			}).setNextMenu(new PlayerDetailsMenu(this.getOwner(), player)));
		}
		return items;
	}
	
	@Override
	public Map<Integer, ItemStack> getButtons() {
		Map<Integer, ItemStack> map = new HashMap<>();
		map.put(49, new ItemBuilder(this, CustomStack.getInstance("_iainternal:icon_cancel")
				.getItemStack(), itemMeta -> itemMeta.setDisplayName(ChatColor.GRAY + "Fermer")).setCloseButton());
		map.put(48, new ItemBuilder(this, CustomStack.getInstance("_iainternal:icon_back_orange")
				.getItemStack(), itemMeta -> itemMeta.setDisplayName(ChatColor.RED + "Page précédente")).setPreviousPageButton());
		map.put(50, new ItemBuilder(this, CustomStack.getInstance("_iainternal:icon_next_orange")
				.getItemStack(), itemMeta -> itemMeta.setDisplayName(ChatColor.GREEN + "Page suivante")).setNextPageButton());
		return map;
	}
	
	@Override
	public @NotNull String getName() {
		return ChatColor.BLUE + "Liste des joueurs";
	}
	
	@Override
	public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
	}
}
