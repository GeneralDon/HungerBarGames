package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;

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
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		if(game.isTribute(move.getPlayer()))
		{
			int b1=1;
		}
	}
}
