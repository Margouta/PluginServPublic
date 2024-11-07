package fr.communaywen.core.evenements;

import lombok.Getter;

@Getter
public enum WeeklyItemType {

    LUCKY_BLOCK ("custom","luckyblock:luckyblock", "§dLucky Block" ,"PAPER", 10015,5, "halloween:candy_purple", "Bonbon violet","Acheter pour 5 Bonbon violet",0),
    CANDY_RED ("custom","halloween:candy_red", "Bonbon rouge","PAPER",10030,3, "halloween:candy_blue", "Bonbon bleu","Acheter pour 3 Bonbon bleu",0),
    HALLOWEEN_KEY ("custom","halloween:halloween_key","clée d'halloween", "PAPER",10047,2,"halloween:candy_yellow", "Bonbon jaune","Acheter pour 2 Bonbon jaune",0),
    CONTEST ("custom","contest:contest_shell","coquillage de contest", "NAUTILUS_SHELL",10000,6,"halloween:candy_blue","Bonbon bleu","Acheter pour 6 Bonbon bleu",0),
    DIAMOND ("normal","diamond","diamant", "DIAMOND",0,1,"halloween:candy_blue","Bonbon bleu","Acheter pour 1 Bonbon bleu",0),
    EMERALD ("normal","emerald","émeraude", "EMERALD",0,2,"halloween:candy_blue","Bonbon bleu","Acheter pour 2 Bonbon bleu",0),
    NETHERITE ("normal","netherite_ingot","lingot de netherite", "NETHERITE_INGOT",0,3,"halloween:candy_purple","Bonbon violet","Acheter pour 3 Bonbon violet",0),
    TOTEM ("single","totem_of_undying","totem d'immortalité", "TOTEM_OF_UNDYING",0,5,"halloween:candy_purple","Bonbon violet","Acheter pour 5 Bonbon violet",0),
    SHULKER_SHELL ("normal","shulker_shell","carapace de shulker", "SHULKER_SHELL",0,4,"halloween:candy_purple","Bonbon violet","Acheter pour 4 Bonbon violet",0),
    APPLE_CHEAT ("normal","enchanted_golden_apple","pomme dorée enchantée", "ENCHANTED_GOLDEN_APPLE",0,1,"halloween:candy_yellow","Bonbon jaune","Acheter pour 1 Bonbon jaune",0),
    PROTECTION_IV ("enchanted_book","PROTECTION","Protection IV", "ENCHANTED_BOOK",0,10,"halloween:candy_purple","Bonbon violet","Acheter pour 10 Bonbon violet",4),
    SHARPNESS_V ("enchanted_book","SHARPNESS","Tranchant V", "ENCHANTED_BOOK",0,10,"halloween:candy_purple","Bonbon violet","Acheter pour 10 Bonbon violet",5),;

    private final String itemType;
    private final String itemName;
    private final String displayName;
    private final String material;
    private final int data;
    private final int cost;
    private final String money;
    private final String moneyName;
    private final String lore;
    private final int enchant_level;


    WeeklyItemType(String itemType, String itemName, String displayName, String material, int data, int cost, String money, String moneyName,String lore, int enchant_level){

        this.itemType = itemType;
        this.itemName = itemName;
        this.displayName = displayName;
        this.material = material;
        this.data = data;
        this.cost = cost;
        this.money = money;
        this.moneyName = moneyName;
        this.lore = lore;
        this.enchant_level = enchant_level;


    }

}
