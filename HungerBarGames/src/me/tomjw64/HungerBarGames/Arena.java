package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Location;
import org.bukkit.block.Chest;

public class Arena {
	/*
	 * Arena handles all arena specific data
	 * such as spawn points and chests.
	 */
	private HungerBarGames pl;
	//Name of arena
	private String name;
	//Holds player spawn points
	private List<Location> spawns=new ArrayList<Location>();
	//Holds chests associated with the arena
	private Set<Chest> chests=new HashSet<Chest>();
	//The spectator spawn
	private Location specPoint;
	//The lobby spawn
	private Location lobbyPoint;
	//The maximum players set for this arena
	private int maxPlayers;
	//The minimum players set for this arena
	private int minPlayers;
	//Game being played in this arena
	private Game game;
	
	public Arena(HungerBarGames instance,String arenaName)
	{
		name=arenaName;
		pl=instance;
		maxPlayers=ConfigManager.getMaxPlayers();
		minPlayers=ConfigManager.getMinPlayers();
	}
	public void startGame(boolean repeat)
	{
		game=new Game(pl,this,repeat);
	}
	public void endGame(boolean repeat)
	{
		if(repeat)
		{
			game=new Game(pl,this,repeat);
		}
		else
		{
			game=null;
		}
	}
	public String getName()
	{
		return name;
	}
	public Game getGame()
	{
		return game;
	}
	public int getMaxPlayers()
	{
		return maxPlayers;
	}
	public int getMinPlayers()
	{
		return minPlayers;
	}
	public Location getLobby()
	{
		return lobbyPoint;
	}
	public void setLobby(Location lobby)
	{
		lobbyPoint=lobby;
	}
	public int getNumSpawns()
	{
		return spawns.size();
	}
	public Location spawnAt(int index)
	{
		return spawns.get(index);
	}
	public void addSpawn(Location spawn)
	{
		spawns.add(spawn);
	}
	public Location getSpec()
	{
		return specPoint;
	}
	public void setSpec(Location spec)
	{
		specPoint=spec;
	}
}
