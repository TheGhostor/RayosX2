package ry.theghostor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private final Plugin plugin;
    private final Map<String, FileConfiguration> messagesByLang = new HashMap<>();

    public MessageManager(Plugin plugin) {
        this.plugin = plugin;
        loadMessages("es");
        loadMessages("en");
    }

    public void loadMessages(String lang) {
        try {
            File file = new File(plugin.getDataFolder(), "messages_" + lang + ".yml");
            if (!file.exists()) {
                plugin.saveResource("messages_" + lang + ".yml", false);
            }
            messagesByLang.put(lang, YamlConfiguration.loadConfiguration(file));
        } catch (Exception e) {
            plugin.getLogger().warning("Error cargando mensajes para idioma " + lang);
        }
    }

    public String getMessage(Player player, String path) {
        String lang = player.getLocale().split("_")[0];
        FileConfiguration config = messagesByLang.getOrDefault(lang, messagesByLang.get("es"));
        return config.getString(path, "Mensaje no encontrado: " + path);
    }
}
