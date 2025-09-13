package gg.jos.ShieldBreakSound.events;

import gg.jos.ShieldBreakSound.ShieldBreakSound;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Event listener for handling shield break sounds when a player is hit by an axe while blocking.
 */
public class ShieldBreakEvent implements Listener {
    private final ShieldBreakSound shieldBreakSound;
    private final FileConfiguration config;
    private final Set<Material> axeMaterials;

    /**
     * Constructor for ShieldBreakEvent.
     * @param shieldBreakSound
     * @param config
     */
    public ShieldBreakEvent(ShieldBreakSound shieldBreakSound, FileConfiguration config) {
        this.shieldBreakSound = shieldBreakSound;
        this.config = config;
        this.axeMaterials = loadAxeMaterials(config);
    }

    /**
     * Event handler for when a player is hit by another entity and checking if it when a player has their axe disabled.
     * @param event
     */
    @EventHandler
    public void onAxeDisableShield(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;

        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if (!isAxe(attacker.getInventory().getItemInMainHand().getType())) {
            return;
        }

        if (!victim.isBlocking()) {
            return;
        }

        String soundName = config.getString("noise", "ENTITY_SHIELD_BREAK");

        Sound sound;
        try {
            sound = Sound.valueOf(soundName);
        } catch (IllegalArgumentException e) {
            sound = Sound.ITEM_SHIELD_BREAK;
        }

        // Play sound for both players with configurable volume and pitch
        double volume = config.getDouble("volume", 1.0);
        double pitch = config.getDouble("pitch", 1.0);

        victim.playSound(victim.getLocation(), sound, (float) volume, (float) pitch);
        attacker.playSound(attacker.getLocation(), sound, (float) volume, (float) pitch);
    }

    /**
     * Check if the given material is an axe (from the config)
     * @param material
     * @return
     */
    private boolean isAxe(Material material) {
        return axeMaterials.contains(material);
    }

    /**
     * Load axe materials from the configuration.
     * @param config
     * @return
     */
    private Set<Material> loadAxeMaterials(FileConfiguration config) {
        Set<Material> axes = new HashSet<>();
        List<String> axeList = config.getStringList("axes");

        for (String axeName : axeList) {
            try {
                axes.add(Material.valueOf(axeName));
            } catch (IllegalArgumentException e) {
                shieldBreakSound.getLogger().warning("Invalid axe name: " + axeName);
            }
        }
        return axes;
    }
}
