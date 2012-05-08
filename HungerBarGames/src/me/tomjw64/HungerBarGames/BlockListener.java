package me.tomjw64.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener{
	public static HungerBarGames plugin;
	
	public BlockListener(HungerBarGames instance)
	{
		plugin=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pBreak(final BlockBreakEvent bBreak)
	{
		Player breaker=bBreak.getPlayer();
		if(!breaker.isOp())
		{
			if(bBreak.getBlock().getType()!=Material.RED_MUSHROOM
					&&bBreak.getBlock().getType()!=Material.BROWN_MUSHROOM
					&&bBreak.getBlock().getType()!=Material.LEAVES
					&&bBreak.getBlock().getType()!=Material.RED_ROSE
					&&bBreak.getBlock().getType()!=Material.YELLOW_FLOWER)
			{
				bBreak.setCancelled(true);
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pPlace(final BlockPlaceEvent bPlace)
	{
		Player placer=bPlace.getPlayer();
		if(!placer.isOp())
		{
			bPlace.setCancelled(true);
		}
	}
}
