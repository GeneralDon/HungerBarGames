package me.tomjw64.HungerBarGames.Listeners.Countdown;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class CountdownInteractListener extends GameListener{

	public CountdownInteractListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void interact(PlayerInteractEvent interact)
	{
		if(getGame().isTribute(interact.getPlayer()))
		{
			interact.setCancelled(true);
		}
	}

}
