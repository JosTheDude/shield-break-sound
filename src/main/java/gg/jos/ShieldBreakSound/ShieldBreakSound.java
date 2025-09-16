package gg.jos.ShieldBreakSound;

import gg.jos.ShieldBreakSound.events.ShieldBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for ShieldBreakSound.
 */
public class ShieldBreakSound extends JavaPlugin {
    @Override
    public void onEnable() {

        // Set up config defaults
        this.saveDefaultConfig();
		this.saveConfig();

        // Register event listener
        this.getServer().getPluginManager().registerEvents(new ShieldBreakEvent(this, getConfig()), this);
    }
}
