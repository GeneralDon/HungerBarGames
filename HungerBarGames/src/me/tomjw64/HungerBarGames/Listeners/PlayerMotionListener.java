package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMotionListener implements Listener{
	/*
	 * PlayerMotionListener listens to PlayerMoveEvent only.
	 * The listener will only be active during the countdown
	 * to avoid using resources by always listening to player
	 * movement.
	 */
	//Game that holds the listener
	private Game game;
	public PlayerMotionListener(Game gm)
	{
		game=gm;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		if(game.isTribute(move.getPlayer()))
		{
			Location from=move.getFrom();
			Location to=move.getTo();
			//If player moves a block
			if(Math.floor(to.getX())!=Math.floor(from.getX())||Math.floor(to.getZ())!=Math.floor(from.getZ()))
			{
				//Make player go boom
				move.getFrom().getWorld().createExplosion(to,0,false);
				move.getPlayer().setHealth(0);
			}
		}
	}
}
