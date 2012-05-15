package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;

public class Arena {
	/*
	 * Arena handles all arena specific data
	 * such as spawn points and chests.
	 */
	private HungerBarGames pl;
	//Name of arena
	private String name;
	//Whether or not to load the arena to database
	private boolean changes;
	//Arena's world
	private World world;
	//Holds player spawn points
	private List<Location> spawns=new ArrayList<Location>();
	//Holds chests associated with the arena
	private Map<ChestClass,Set<Chest>> chests=new HashMap<ChestClass,Set<Chest>>();
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
		this(instance,arenaName,null,ConfigManager.getMaxPlayers(),ConfigManager.getMinPlayers(),null,null,new ArrayList<Location>(),new HashMap<ChestClass,Set<Chest>>());
	}
	public Arena(HungerBarGames instance,String arenaName,World w,int maxP,int minP,Location lobby,Location spec,List<Location> spwns,Map<ChestClass,Set<Chest>> chsts)
	{
		pl=instance;
		name=arenaName;
		changes=false;
		maxPlayers=maxP;
		minPlayers=minP;
		lobbyPoint=lobby;
		specPoint=spec;
		spawns=spwns;
		chests=chsts;
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
		world=lobby.getWorld();
		changes=true;
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
		changes=true;
	}
	public List<Location> getSpawns()
	{
		return spawns;
	}
	public Location getSpec()
	{
		return specPoint;
	}
	public void setSpec(Location spec)
	{
		specPoint=spec;
		changes=true;
	}
	public boolean getChanged()
	{
		return changes;
	}
	public World getWorld()
	{
		return world;
	}
	public void fillChests()
	{
		for(Map.Entry<ChestClass,Set<Chest>> entry:chests.entrySet())
		{
			ChestClass cc=entry.getKey();
			for(Chest c:entry.getValue())
			{
				cc.fillChest(c);
			}
		}
	}
	public Map<ChestClass,Set<Chest>> getChests()
	{
		return chests;
	}
}
