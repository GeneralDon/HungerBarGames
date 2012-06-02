package me.tomjw64.HungerBarGames.Listeners.Lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class LobbyBlockListener extends GameListener{

	public LobbyBlockListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void blockBreak(BlockBreakEvent broken)
	{
		if(getGame().isTribute(broken.getPlayer()))
		{
			broken.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void blockPlace(BlockPlaceEvent place)
	{
		if(getGame().isTribute(place.getPlayer()))
		{
			place.setCancelled(true);
		}
	}

}
