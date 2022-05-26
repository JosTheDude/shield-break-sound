package xyz.joscodes.shieldbreak.shieldbreaksound;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Shieldbreaksound extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, (Plugin) this);
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
		if (damager.getEquipment().getItemInMainHand().getType().toString().toLowerCase().contains("axe") && player.isBlocking() && (int) event.getFinalDamage() == 0)
			damager.playSound(damager.getLocation(), Sound.ENTITY_WITHER_SKELETON_HURT, 1.0F, 1.0F);
		// yeah im so lazy i didnt add a config to the noise, fight me
	}
}
