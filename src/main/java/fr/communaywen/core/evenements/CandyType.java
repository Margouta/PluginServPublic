package fr.communaywen.core.evenements;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum CandyType {
    CANDY_GREEN("halloween:candy_green", 5, "Bonbon " + ChatColor.GREEN + "vert", "halloween:candy_monster", "Bonbon Monstre"),
    CANDY_BLUE("halloween:candy_blue", 3, "Bonbon " + ChatColor.BLUE + "bleu", "halloween:candy_green", "Bonbon Vert"),
    CANDY_PURPLE("halloween:candy_purple", 3, "Bonbon " + ChatColor.LIGHT_PURPLE + "violet", "halloween:candy_blue", "Bonbon bleu"),
    CANDY_YELLOW("halloween:candy_yellow", 3, "Bonbon " + ChatColor.GOLD + "jaune", "halloween:candy_purple", "Bonbon Violet");

    private final String itemName;
    private final int cost;
    private final String displayName;
    private final String money;
    private final String moneyName;

    CandyType(String itemName, int cost, String displayName, String money, String moneyName) {

        this.itemName = itemName;
        this.cost = cost;
        this.displayName = displayName;
        this.money = money;
        this.moneyName = moneyName;

    }

}