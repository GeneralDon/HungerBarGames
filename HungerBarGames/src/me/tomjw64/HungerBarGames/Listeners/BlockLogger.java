package me.tomjw64.HungerBarGames.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.RollbackInfo;

public class BlockLogger extends GameListener{

	public BlockLogger(Game gm)
	{
		super(gm);
	}
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void blockBreak(BlockBreakEvent broken)
	{
		if(getGame().isTribute(broken.getPlayer()))
		{
			Block b=broken.getBlock();
			getGame().getArena().addRollback(b,new RollbackInfo(b.getTypeId(),b.getData()));
		}
	}
	//On a block place
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void blockPlace(BlockPlaceEvent placed)
	{
		if(getGame().isTribute(placed.getPlayer()))
		{
			Block b=placed.getBlock();
			getGame().getArena().addRollback(b,new RollbackInfo(0,(byte)0));
			HungerBarGames.logger.info("Block Placed!");
		}
	}
}
