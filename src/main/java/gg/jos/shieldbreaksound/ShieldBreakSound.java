package gg.jos.shieldbreaksound;

import gg.jos.shieldbreaksound.events.ShieldBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for ShieldBreakSound.
 */
public class ShieldBreakSound extends JavaPlugin {

    public boolean soundEnabled;

    @Override
    public void onEnable() {

        // Set up config defaults
        this.saveDefaultConfig();
		this.saveConfig();

        // Load Configuration
        loadConfig();

        // Load Listeners
        loadListeners();

    }

    /**
     * Load configuration values
     */
    private void loadConfig() {
        this.soundEnabled = getConfig().getBoolean("sound-enabled", true);
    }

    /**
     * Register listeners based on configuration
     */
    private void loadListeners() {
        if (soundEnabled) {
            this.getServer().getPluginManager().registerEvents(new ShieldBreakEvent(this, getConfig()), this);
        } else {
            this.getLogger().info("Sound is disabled - listener for shield breaks was not registered.");
        }
    }
}
