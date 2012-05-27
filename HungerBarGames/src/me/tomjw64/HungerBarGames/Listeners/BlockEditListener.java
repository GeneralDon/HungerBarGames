package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockEditListener extends GameListener{
	/*
	 * BlockEditListener will listen for block breaking
	 * and block placing. It will perform necessary actions
	 * and then proceed to log the change.
	 */
	
	public BlockEditListener(Game gm)
	{
		super(gm);
	}
	//On a block break
	@EventHandler(priority=EventPriority.NORMAL)
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
	//On a block place
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockPlace(BlockBreakEvent placed)
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
