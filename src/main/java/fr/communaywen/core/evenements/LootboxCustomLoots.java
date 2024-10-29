package fr.communaywen.core.evenements;

import dev.lone.itemsadder.api.CustomStack;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public enum LootboxCustomLoots {

    YELLOW_CANDY_BLOCK("halloween:yellow_candy_block",1),
    PURPLE_CANDY_BLOCK("halloween:purple_candy_block",1),
    BLUE_CANDY_BLOCK("halloween:blue_candy_block",1),
    WITCH_HAT("halloween:witch_hat",1),
    SPIDER_HAT("halloween:spider_hat",1),
    SCREAM_MASK("halloween:scream_mask",1),
    HALLOWEEN_KEY("halloween:halloween_key",2);

    private final String itemName;
    private final int amount;

    LootboxCustomLoots(String itemName, int amount) {

        this.itemName = itemName;
        this.amount = amount;

    }

    public ItemStack getItemStack() {

        CustomStack customStack = CustomStack.getInstance(itemName);
        return customStack != null ? customStack.getItemStack() : null;

    }

}
