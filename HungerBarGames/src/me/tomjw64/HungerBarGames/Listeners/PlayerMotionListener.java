package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMotionListener extends GameListener{
	/*
	 * PlayerMotionListener listens to PlayerMoveEvent only.
	 * The listener will only be active during the countdown
	 * to avoid using resources by always listening to player
	 * movement.
	 */
	
	public PlayerMotionListener(Game gm)
	{
		super(gm);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		if(getGame().isTribute(move.getPlayer()))
		{
			Location from=move.getFrom();
			Location to=move.getTo();
			//If player moves a block
			if(Math.floor(to.getX())!=Math.floor(from.getX())||Math.floor(to.getZ())!=Math.floor(from.getZ()))
			{
				if(ConfigManager.getExplode())
				{
					//Make player go boom
					move.getFrom().getWorld().createExplosion(to,0,false);
					move.getPlayer().setHealth(0);
				}
				else
				{
					move.setCancelled(true);
				}
			}
		}
	}
}
