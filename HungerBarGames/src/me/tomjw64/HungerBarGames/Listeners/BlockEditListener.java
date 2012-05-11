package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockEditListener implements Listener{
	/*
	 * BlockEditListener will listen for block breaking
	 * and block placing. It will perform necessary actions
	 * and then proceed to log the change.
	 */
	
	//Game that holds the listener
	private Game game;
	
	public BlockEditListener(Game gm)
	{
		game=gm;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	//On a block break
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockBreak(BlockBreakEvent broken)
	{
		//Check if player is in this game
		if(game.isTribute(broken.getPlayer()))
		{
			
		}
	}
	//On a block place
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockPlace(BlockBreakEvent placed)
	{
		//Check if player is in this game
		if(game.isTribute(placed.getPlayer()))
		{
			
		}
	}
}
