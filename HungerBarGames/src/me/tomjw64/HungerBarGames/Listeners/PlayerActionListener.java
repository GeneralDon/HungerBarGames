package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener implements Listener{
	/*
	 * PlayerActionListener listens for player events that can take
	 * place at any time during the game. So, basically all player events
	 * except PlayerMotionListener.
	 */
	//Game that holds the listener
	private Game game;
	
	public PlayerActionListener(Game gm)
	{
		game=gm;
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void death(PlayerDeathEvent death)
	{
		if(game.isTribute(death.getEntity()))
		{
			
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void leave(PlayerQuitEvent quit)
	{
		if(game.isTribute(quit.getPlayer()))
		{
			
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		//Check if both are players
		if(dmg.getEntity() instanceof Player&&dmg.getDamager() instanceof Player)
		{
			Player dmgd=(Player)dmg.getEntity();
			Player dmgr=(Player)dmg.getDamager();
			//Check if one is in game and one is not
			if((game.isTribute(dmgd)||game.isTribute(dmgr))&&(!game.isTribute(dmgd)||!game.isTribute(dmgr)))
			{
				//Cancel damage if one is in game and one is not
				dmg.setCancelled(true);
			}
		}
	}
}
