package xyz.joscodes.shieldbreak.shieldbreaksound;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Shieldbreaksound extends JavaPlugin implements Listener {
	final FileConfiguration config = this.getConfig();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, (Plugin) this);
		config.addDefault("noise", "ENTITY_WITHER_SKELETON_HURT");
		config.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
	}

	@EventHandler
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
			return;
		final Player player = (Player) event.getEntity();
		final Player damager = (Player) event.getDamager();
		if (damager.getEquipment().getItemInMainHand().getType().toString().toLowerCase().contains("axe") && player.isBlocking() && (int) event.getFinalDamage() == 0) {
			try {
				damager.playSound(damager.getLocation(), Sound.valueOf(getConfig().getString("noise")), 5, 5);
				//Everything is OK!
			} catch (final Exception e) {
				damager.playSound(damager.getLocation(), Sound.ENTITY_WITHER_SKELETON_HURT, 5, 5);
				System.out.println("The entered sound in the config is not a valid sound, so ShieldBreakNoise played the default one! Please enter a valid value in the config.");
				//Error, entered sound does not exist / missing sound from config
			}
		}
	}
}
