package fr.communaywen.core.luckyblocks.commands;

import dev.lone.itemsadder.api.CustomStack;
import fr.communaywen.core.credit.Credit;
import fr.communaywen.core.credit.Feature;
import fr.communaywen.core.luckyblocks.managers.LBPlayerManager;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Subcommand;

import java.util.Random;
import java.util.UUID;

@Feature("Lucky Blocks")
@Credit("Fnafgameur")
@Command({"luckyblock", "lb"})
public class LuckyBlockCommand {

    private final Random random = new Random();
    private final LBPlayerManager playerManager;

    public LuckyBlockCommand(LBPlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @DefaultFor("~")
    public void defaultCommand(Player player) {
        sendHelp(player);
    }

    @Subcommand("claim")
    public void claim(Player player) {

        UUID uuid = player.getUniqueId();

        if (!playerManager.canClaimLuckyBlocks(uuid)) {
            StringBuilder sb =new StringBuilder();
            int timeHour = playerManager.getRemainingHours(uuid);
            int timeMinute = playerManager.getRemainingMinutes(uuid);
            int timeSecond = playerManager.getRemainingSeconds(uuid);

            sb.append("§cVous avez déjà réclamé vos lucky blocks du jour !\nTemps restant: ")
                    .append(timeHour)
                    .append("h")
                    .append(timeMinute)
                    .append("m")
                    .append(timeSecond)
                    .append("s");

            player.sendMessage(sb.toString());
            return;
        }

        CustomStack luckyBlock = CustomStack.getInstance("luckyblock");

        int amount = random.nextInt(3) + 1;

        for (int i = 0; i < amount; i++) {

            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), luckyBlock.getItemStack());
                continue;
            }

            player.getInventory().addItem(luckyBlock.getItemStack());
        }

        playerManager.setLuckyBlocksCooldown(uuid, System.currentTimeMillis());
    }

    private void sendHelp(Player player) {

        String help = """
                §7---------- §bLucky Blocks §7----------
                §8/§eluckyblock §bclaim §8- §7Permet de réclamer entre 1 et 3 lucky blocks par jour""";

        player.sendMessage(help);
    }
}
