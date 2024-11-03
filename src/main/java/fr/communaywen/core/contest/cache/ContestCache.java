package fr.communaywen.core.contest.cache;

import fr.communaywen.core.AywenCraftPlugin;
import fr.communaywen.core.contest.managers.ContestManager;
import fr.communaywen.core.utils.database.DatabaseConnector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContestCache extends DatabaseConnector {

    private JavaPlugin plugin;
    public ContestCache(AywenCraftPlugin plug) {
        plugin = plug;
    }

    private final long cacheDuration = 120000;

    // CONTEST DATA
    private ContestDataCache contestCache;
    private boolean isContestCacheLoading = false;

    public void initContestDataCache() {

        if (isContestCacheLoading) {
            return;
        }
        isContestCacheLoading = true;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (PreparedStatement states = connection.prepareStatement("SELECT * FROM contest WHERE 1")) {
                ResultSet result = states.executeQuery();
                if (result.next()) {
                    String camp1 = result.getString("camp1");
                    String camp2 = result.getString("camp2");
                    String color1 = result.getString("color1");
                    String color2 = result.getString("color2");
                    int phase = result.getInt("phase");
                    String startdate = result.getString("startdate");

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        contestCache = new ContestDataCache(camp1, camp2, color1, color2, phase, startdate);
                        isContestCacheLoading = false;
                    });
                }
            } catch (SQLException e) {
                isContestCacheLoading = false;
                throw new RuntimeException(e);
            }
        });
    }

    public String getCamp1Cache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getCamp1();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getCamp1();
            }
            return null;
        }
    }

    public String getCamp2Cache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getCamp2();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getCamp2();
            }
            return null;
        }
    }

    public String getColor1Cache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getColor1();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getColor1();
            }
            return null;
        }
    }

    public String getColor2Cache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getColor2();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getColor2();
            }
            return null;
        }
    }

    public int getPhaseCache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getPhase();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getPhase();
            }
            return -1;
        }
    }

    public String getStartDateCache() {
        ContestDataCache cache = contestCache;

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getStartDate();
        } else {
            initContestDataCache();
            if (cache!=null) {
                return cache.getStartDate();
            }
            return null;
        }
    }

    // CONTEST PLAYER DATA
    private Map<UUID, ContestPlayerCache> playerCache = new HashMap<>();
    private Map<UUID, Boolean> isPlayerCacheLoading = new HashMap<>();

    public void initPlayerDataCache(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (isPlayerCacheLoading.getOrDefault(playerUUID, false)) {
            return;
        }

        isPlayerCacheLoading.put(playerUUID, true);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String sql = "SELECT * FROM camps WHERE minecraft_uuid = ?";
            try (PreparedStatement states = connection.prepareStatement(sql)) {
                states.setString(1, playerUUID.toString());
                ResultSet result = states.executeQuery();
                if (result.next()) {
                    int points = result.getInt("point_dep");
                    int camp = result.getInt("camps");
                    String color = ContestManager.getString("contest","color" + camp);
                    ChatColor campColor = ChatColor.valueOf(color);

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        playerCache.put(playerUUID, new ContestPlayerCache(points, camp, campColor));
                        isPlayerCacheLoading.put(playerUUID, false);
                    });
                }
            } catch (SQLException e) {
                isPlayerCacheLoading.put(playerUUID, false);
                throw new RuntimeException(e);
            }
        });
    }

    public int getPlayerPointsCache(Player player) {
        UUID playerUUID = player.getUniqueId();
        ContestPlayerCache cache = playerCache.get(playerUUID);

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getPoints();
        } else {
            initPlayerDataCache(player);
            if (cache!=null) {
                return cache.getPoints();
            }
            return 0;
        }
    }

    public int getPlayerCampsCache(Player player) {
        UUID playerUUID = player.getUniqueId();
        ContestPlayerCache cache = playerCache.get(playerUUID);

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getCamp();
        } else {
            initPlayerDataCache(player);
            if (cache!=null) {
                return cache.getCamp();
            }
            return -1;
        }
    }
    public ChatColor getPlayerColorCache(Player player) {
        UUID playerUUID = player.getUniqueId();
        ContestPlayerCache cache = playerCache.get(playerUUID);

        if (cache != null && !cache.isCacheNull(cacheDuration)) {
            return cache.getColor();
        } else {
            initPlayerDataCache(player);
            if (cache!=null) {
                return cache.getColor();
            }
            return null;
        }
    }

    public void clearContestCache() {
        contestCache = null;
    }

    public void clearPlayerContestCache() {
        playerCache = null;
    }

    public void clearPlayerCache(Player player) {
        playerCache.remove(player.getUniqueId());
    }

}
