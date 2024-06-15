package com.Simply.Board.Plugin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class MioPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        startScoreboardUpdater();
        getLogger().info("MioPlugin è stato abilitato!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MioPlugin è stato disabilitato!");
    }

    private void startScoreboardUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(this, 0, 20); // Aggiorna ogni secondo
    }

    private void updateScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("test", "dummy", ChatColor.translateAlternateColorCodes('&', getConfig().getString("scoreboard.title")));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int index = getConfig().getStringList("scoreboard.lines").size();
        for (String line : getConfig().getStringList("scoreboard.lines")) {
            String parsedLine = PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', line));
            objective.getScore(parsedLine).setScore(index);
            index--;
        }

        player.setScoreboard(board);
    }
}
