package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

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
		//Check if player is in this game
		if(getGame().isTribute(broken.getPlayer()))
		{
			
		}
	}
	//On a block place
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockPlace(BlockBreakEvent placed)
	{
		//Check if player is in this game
		if(getGame().isTribute(placed.getPlayer()))
		{
			
		}
	}
}
