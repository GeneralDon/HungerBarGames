package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiPvPListener implements Listener{
	public static HungerBarGames pl;
	
	public AntiPvPListener(HungerBarGames instance)
	{
		pl=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		//Check if both are players
		if(dmg.getEntity() instanceof Player&&dmg.getDamager() instanceof Player)
		{
			Player dmgd=(Player)dmg.getEntity();
			Player dmgr=(Player)dmg.getDamager();
			if(!GamesManager.isInGame(dmgd)||!GamesManager.isInGame(dmgr))
			{
				dmg.setCancelled(true);
			}
		}
	}
}
