package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.Bukkit;
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
	public static HungerBarGames pl;
	
	public BlockEditListener(HungerBarGames instance)
	{
		pl=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	//On a block break
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockBreak(BlockBreakEvent broken)
	{
	}
	//On a block place
	@EventHandler(priority=EventPriority.NORMAL)
	public void blockPlace(BlockBreakEvent placed)
	{
	}
}
