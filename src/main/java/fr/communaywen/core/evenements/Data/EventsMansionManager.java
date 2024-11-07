package fr.communaywen.core.evenements.Data;

import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.evenements.listeners.*;
import org.bukkit.*;
import org.bukkit.event.Listener;

public class EventsMansionManager implements Listener {

    AywenCraftPlugin plugin;
    Server server;

    public EventsMansionManager(AywenCraftPlugin plugin){

        this.plugin = plugin;
        this.server = plugin.getServer();

    }

    public void init(){

        CreateMansonDim();

        plugin.registerEvents(

                new MobSpawnListener("Mansion"),
                new VillagerListener(plugin),
                new CandyEatListener(),
                new InteractLootbox(),
                new MansionLootTable()

        );

    }

    // La dimension sera retirée plus tard si besoin
    public void CreateMansonDim(){

        WorldCreator creator = new WorldCreator("Mansion");
        creator.environment(World.Environment.NORMAL);
        creator.type(WorldType.FLAT);
        creator.generateStructures(false);
        creator.generatorSettings("{\"layers\": [{\"block\": \"minecraft:air\",\"height\":1}], \"biome\":\"plains\"}");
        World Manson = creator.createWorld();

        Manson.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Manson.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        Manson.setGameRule(GameRule.DISABLE_RAIDS, true);
        Manson.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        Manson.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        Manson.setGameRule(GameRule.NATURAL_REGENERATION, false);
        Manson.setGameRule(GameRule.DO_FIRE_TICK, false);

        Manson.setTime(12500);
        Manson.setStorm(true);

        plugin.getLogger().info("Mansion dimension created successfully!");

    }
}
