package ry.theghostor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private final TemporalXRay plugin;

    public ReloadCommand(TemporalXRay plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission("rayosx2.reload")) {
            sender.sendMessage("§cNo tienes permiso para usar este comando.");
            return true;
        }

        plugin.getConfigManager().loadConfig();
        plugin.getMessageManager().loadMessages("es");
        plugin.getMessageManager().loadMessages("en");

        sender.sendMessage("§aConfiguración y mensajes recargados correctamente.");
        return true;
    }
}
