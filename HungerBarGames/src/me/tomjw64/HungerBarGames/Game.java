package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.Listeners.BlockEditListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerActionListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerMotionListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Game {
	/*
	 * Game actually performs all game actions and checks
	 * Each game is associated with an area.
	 * Will also contain listeners for events that affect the game.
	 */
	public static HungerBarGames pl;
	//Arena that the game is in
	private Arena arena;
	//Players in the game
	private Set<Player> tributes=new HashSet<Player>();
	private Set<Player> specing=new HashSet<Player>();
	//Listeners
	private BlockEditListener bel;
	private PlayerMotionListener pml;
	private PlayerActionListener pal;
	//Colors
	private final ChatColor GREEN=ChatColor.GREEN;
	private final ChatColor RED=ChatColor.RED;
	private final ChatColor AQUA=ChatColor.AQUA;
	private final ChatColor YELLOW=ChatColor.YELLOW;
	//Plugin prefix
	private String prefix=ConfigManager.getPrefix();
	
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
		pl.getServer().broadcastMessage(prefix+YELLOW+" A game has been started in Arena "+ChatColor.RED+arena.getName()+RED+"!");
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
	//Check if a player is in a game
	public boolean isSpec(Player p)
	{
		return specing.contains(p);
	}
	//Add a player to the game
	public void addSpec(Player p)
	{
		specing.add(p);
	}
	//Remove a player from the game
	public void removeSpec(Player p)
	{
		specing.remove(p);
	}
}
