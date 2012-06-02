package me.tomjw64.HungerBarGames.Listeners.Lobby;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class LobbyLevelListener extends GameListener{
	
	public LobbyLevelListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void damage(EntityDamageEvent dmg)
	{
		if(dmg.getEntity() instanceof Player)
		{
			if(getGame().isTribute((Player)dmg.getEntity()))
			{
				dmg.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void foodChange(FoodLevelChangeEvent fc)
	{
		if(fc.getEntity() instanceof Player)
		{
			if(getGame().isTribute((Player)fc.getEntity()))
			{
				fc.setCancelled(true);
			}
		}
	}

}
