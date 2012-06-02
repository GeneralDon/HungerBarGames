package me.tomjw64.HungerBarGames.Listeners;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class GameListener implements Listener{
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
	
	public void unregister()
	{
		HandlerList.unregisterAll(this);
	}
	
}
