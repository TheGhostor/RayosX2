package ry.theghostor;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XRayListener implements Listener {

    private final TemporalXRay plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;

    private final Map<Player, Map<Block, Material>> playerBlocks = new HashMap<>();
    private final Set<Player> cooldowns = new HashSet<>();

    public XRayListener(TemporalXRay plugin, ConfigManager configManager, MessageManager messageManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageManager = messageManager;
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        // Validar permisos
        if (!player.hasPermission("rayosx2.use")) {
            player.sendMessage(messageManager.getMessage(player, "messages.no_permission"));
            return;
        }

        // Validar objeto activador
        Material trigger = Material.matchMaterial(configManager.getTriggerItem());
        if (trigger == null || !event.getItem().getType().equals(trigger)) {
            return;
        }

        // Validar cooldown
        if (cooldowns.contains(player)) {
            int cd = configManager.getCooldownSeconds();
            String msg = messageManager.getMessage(player, "messages.cooldown").replace("%seconds%", String.valueOf(cd));
            player.sendMessage(msg);
            return;
        }

        // Añadir al cooldown
        cooldowns.add(player);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> cooldowns.remove(player), configManager.getCooldownSeconds() * 20L);

        // Mostrar mensaje
        player.sendMessage(messageManager.getMessage(player, "messages.effect_start"));
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0f, 1.5f);
        player.spawnParticle(Particle.END_ROD, player.getLocation().add(0, 1, 0), 20, 0.5, 1, 0.5, 0.01);

        Map<Block, Material> changed = new HashMap<>();

        int radius = configManager.getRadius();
        player.getLocation().toVector();
        Block blockUnder = player.getLocation().subtract(0, 1, 0).getBlock();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = player.getLocation().clone().add(x, y, z).getBlock();

                    // No eliminar aire, minerales valiosos ni el bloque bajo el jugador
                    if (block.getType() == Material.AIR) continue;
                    if (block.equals(blockUnder)) continue;
                    if (isValuable(block.getType())) continue;

                    changed.put(block, block.getType());
                    block.setType(Material.AIR, false);
                }
            }
        }

        playerBlocks.put(player, changed);

        new BukkitRunnable() {
            @Override
            public void run() {
                Map<Block, Material> toRestore = playerBlocks.remove(player);
                if (toRestore != null) {
                    toRestore.forEach((block, material) -> block.setType(material, false));
                }

                player.sendMessage(messageManager.getMessage(player, "messages.effect_end"));
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.0f, 1.0f);
                player.spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 15, 0.5, 0.5, 0.5, 0.01);
            }
        }.runTaskLater(plugin, configManager.getDurationSeconds() * 20L);
    }

    private boolean isValuable(Material type) {
        return switch (type) {
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, GOLD_ORE, IRON_ORE,
                 EMERALD_ORE, REDSTONE_ORE, COAL_ORE, COPPER_ORE,
                 LAPIS_ORE, CHEST, ANCIENT_DEBRIS -> true;
            default -> false;
        };
    }
}
