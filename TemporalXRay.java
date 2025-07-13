package ry.theghostor;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class TemporalXRay extends JavaPlugin {

    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        // Guardar archivos de mensajes solo si no existen
        saveResource("messages_es.yml", false);
        saveResource("messages_en.yml", false);

        configManager = new ConfigManager(this);
        messageManager = new MessageManager(this);

        messageManager.loadMessages("es");
        messageManager.loadMessages("en");

        getServer().getPluginManager().registerEvents(new XRayListener(this, configManager, messageManager), this);

        if (this.getCommand("rayosx2reload") != null) {
            Objects.requireNonNull(this.getCommand("rayosx2reload")).setExecutor(new ReloadCommand(this));
        } else {
            getLogger().warning("El comando 'rayosx2reload' no está definido en plugin.yml");
        }

        getLogger().info("[RayosX2] Plugin activado correctamente. Creado por TheGhostor.");

        UpdateChecker updateChecker = new UpdateChecker(this, 123456); // Cambia 123456 por el ID real de tu recurso
        updateChecker.checkForUpdate();
    }

    @Override
    public void onDisable() {
        getLogger().info("[RayosX2] Plugin desactivado.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
