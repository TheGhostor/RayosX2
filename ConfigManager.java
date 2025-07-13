package ry.theghostor;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final JavaPlugin plugin;

    private int radius;
    private int durationSeconds;
    private int cooldownSeconds;
    private String triggerItem;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        radius = config.getInt("radius", 10);
        durationSeconds = config.getInt("duration-seconds", 5);
        cooldownSeconds = config.getInt("cooldown-seconds", 30);
        triggerItem = config.getString("trigger-item", "GOLDEN_APPLE");
    }

    public int getRadius() {
        return radius;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public String getTriggerItem() {
        return triggerItem;
    }
}
