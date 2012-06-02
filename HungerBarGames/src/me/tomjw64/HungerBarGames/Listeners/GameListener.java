package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class GameListener implements Listener{
	/*
	 * All Listeners active only during a game inherit this.
	 * It provides a Game field and a method to unregister the events.
	 */
	//The game that holds the Listener
	private Game game;
	
	public GameListener(Game gm)
	{
		game=gm;
		Bukkit.getServer().getPluginManager().registerEvents(this,HungerBarGames.plugin);
		game.addListener(this);
	}
	public Game getGame()
	{
		return game;
	}
	//Method used to unregister events
	public void unregister()
	{
		HandlerList.unregisterAll(this);
	}
}
