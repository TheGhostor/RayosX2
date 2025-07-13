package ry.theghostor;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId; // ID del recurso en SpigotMC o similar

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String latestVersion = fetchLatestVersion();

                if (latestVersion == null) {
                    plugin.getLogger().warning("[RayosX2] No se pudo obtener la versión más reciente.");
                    return;
                }

                String currentVersion = plugin.getDescription().getVersion();
                if (!latestVersion.equalsIgnoreCase(currentVersion)) {
                    String msg = "§e[RayosX2] ¡Nueva versión disponible! (" + latestVersion + "). Estás usando: " + currentVersion;
                    plugin.getLogger().log(Level.WARNING, msg.replaceAll("§.", ""));
                    Bukkit.getOnlinePlayers().stream()
                            .filter(p -> p.hasPermission("rayosx2.update"))
                            .forEach(p -> p.sendMessage(msg));
                }
            } catch (Exception e) {
                plugin.getLogger().warning("[RayosX2] Error al verificar actualizaciones: " + e.getMessage());
            }
        });
    }

    private String fetchLatestVersion() {
        try {
            URL url = new URL("https://api.spiget.org/v2/resources/" + resourceId + "/versions/latest");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            try (InputStreamReader reader = new InputStreamReader(con.getInputStream())) {
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = reader.read()) != -1) {
                    sb.append((char) c);
                }
                String jsonResponse = sb.toString();

                // Ejemplo simple: extraer el valor de "name" del JSON (podrías usar una librería JSON)
                int nameIndex = jsonResponse.indexOf("\"name\":\"");
                if (nameIndex == -1) return null; // No encontrado
                int start = nameIndex + 8;
                int end = jsonResponse.indexOf("\"", start);
                if (end == -1) return null;
                return jsonResponse.substring(start, end);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("[RayosX2] Error al obtener la versión desde la web: " + e.getMessage());
            return null;
        }
    }
}
