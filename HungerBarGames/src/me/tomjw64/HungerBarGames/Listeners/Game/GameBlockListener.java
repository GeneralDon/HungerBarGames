package me.tomjw64.HungerBarGames.Listeners.Game;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class GameBlockListener extends GameListener{
	
	public GameBlockListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void blockBreak(BlockBreakEvent broken)
	{
		if(getGame().isTribute(broken.getPlayer()))
		{
			if(ConfigManager.restrictEditing())
			{
				if(!ConfigManager.isListed(broken.getBlock().getTypeId()))
				{
					broken.setCancelled(true);
				}
			}
			else if(ConfigManager.isListed(broken.getBlock().getTypeId()))
			{
				broken.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void blockPlace(BlockPlaceEvent placed)
	{
		if(getGame().isTribute(placed.getPlayer()))
		{
			if(ConfigManager.restrictEditing())
			{
				if(!ConfigManager.isListed(placed.getBlock().getTypeId()))
				{
					placed.setCancelled(true);
				}
			}
			else if(ConfigManager.isListed(placed.getBlock().getTypeId()))
			{
				placed.setCancelled(true);
			}
		}
	}
	
}
