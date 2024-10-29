package fr.communaywen.core.evenements;

import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.credit.Collaborators;
import fr.communaywen.core.credit.Credit;
import fr.communaywen.core.credit.Feature;
import fr.communaywen.core.evenements.menus.EventsMenus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Feature("evenement")
@Credit("Nocolm")
@Collaborators({"Gab400", "Iambibi", "Gyro3630", "Mcross_bow", "Cendriz" })
@Command("evenement")
@Description("voir les evenements")

public class EventsCommand {

    private final AywenCraftPlugin plugin;

    public EventsCommand(AywenCraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Getter
    public static int CommandState;



    @Subcommand("on")
    @CommandPermission("openmc.staff.evenements")
    @Description("active les évenements")
    public void StateOn(Player player){
        CommandState = 0;
        player.sendMessage(ChatColor.GREEN + "Evenement Activer");
        SaveState(CommandState);
    }

    @Subcommand("off")
    @CommandPermission("openmc.staff.evenements")
    @Description("désactive les évenements")
    public void StateOff(Player player){
        CommandState = 1;
        player.sendMessage(ChatColor.GREEN + "Evenement Désactiver");
        SaveState(CommandState);
    }

    @Subcommand("halloween")
    @Description("ouvre l'évenements d'halloween")
    public void onCommand(Player player) {
        if (CommandState == 0) {

            EventsMenus menus = new EventsMenus(player);
            menus.open();

        } else {

            player.sendMessage(ChatColor.RED + "Aucun évenement en cours revenez plus tard");

        }

    }

    @Subcommand("manoir")
    @Description("Tp au Manoir")
    public void onCoammndMansion(Player player) {
        if (CommandState == 0){

            World customWorld = Bukkit.getWorld("Mansion");
            Location mansion = new Location(customWorld,0.5, 101, 0.5);
            player.teleport(mansion);

        } else {

            player.sendMessage(ChatColor.RED + "Manoir indisponible en ce moment");

        }

    }

    public void SaveState(int commandState){

        plugin.getConfig().set("Halloween State ( on = 0 / off = 1 )", commandState);
        plugin.saveConfig();

    }

}