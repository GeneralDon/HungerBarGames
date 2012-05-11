package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.Listeners.BlockEditListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerActionListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerMotionListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Game {
	/*
	 * Game actually performs all game actions and checks
	 * Each game is associated with an area.
	 * Will also contain listeners for events that affect the game.
	 */
	public static HungerBarGames pl;
	//Players in the game
	private Set<Player> tributes=new HashSet<Player>();
	//Listeners
	private BlockEditListener bel;
	private PlayerMotionListener pml;
	private PlayerActionListener pal;
	
	public Game(HungerBarGames instance)
	{
		pl=instance;
		//Load event listeners
		bel=new BlockEditListener(this);
		pml=new PlayerMotionListener(this);
		pal=new PlayerActionListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(bel, pl);
		Bukkit.getServer().getPluginManager().registerEvents(pml, pl);
		Bukkit.getServer().getPluginManager().registerEvents(pal, pl);
	}
	//Check if a player is in a game
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	//Add a player to the game
	public void addTribute(Player p)
	{
		tributes.add(p);
	}
	//Remove a player from the game
	public void removeTribute(Player p)
	{
		tributes.remove(p);
	}
}
