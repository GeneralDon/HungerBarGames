package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener extends GameListener{
	/*
	 * PlayerActionListener listens for player events that can take
	 * place at any time during the game. So, basically all player events
	 * except PlayerMotionListener.
	 */
	
	public PlayerActionListener(Game gm)
	{
		super(gm);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void death(PlayerDeathEvent death)
	{
		if(getGame().isTribute(death.getEntity()))
		{
			
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void leave(PlayerQuitEvent quit)
	{
		if(getGame().isTribute(quit.getPlayer()))
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
			if((getGame().isTribute(dmgd)||getGame().isTribute(dmgr))&&(!getGame().isTribute(dmgd)||!getGame().isTribute(dmgr)))
			{
				//Cancel damage if one is in game and one is not
				dmg.setCancelled(true);
			}
		}
	}
}
