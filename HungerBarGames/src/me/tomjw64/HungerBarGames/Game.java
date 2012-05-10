package me.tomjw64.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Game implements Listener{
	/*
	 * Game actually performs all game actions and checks
	 * Each game is associated with an area.
	 * Will also act as a listener for events that affect the game.
	 */
	public static HungerBarGames pl;
	
	public Game(HungerBarGames instance)
	{
		pl=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
}
