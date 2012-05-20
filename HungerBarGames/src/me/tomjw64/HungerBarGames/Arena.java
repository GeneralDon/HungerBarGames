package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
		this(instance,arenaName,ConfigManager.getMaxPlayers(),ConfigManager.getMinPlayers(),null,null,new ArrayList<Location>(),new HashMap<ChestClass,Set<Chest>>());
	}
	public Arena(HungerBarGames instance,String arenaName,int maxP,int minP,Location lobby,Location spec,List<Location> spwns,Map<ChestClass,Set<Chest>> chsts)
	{
		pl=instance;
		name=arenaName;
		changes=false;
		if(maxP<2)
		{
			maxP=2;
			changes=true;
		}
		maxPlayers=maxP;
		if(minP<2||minP>maxP)
		{
			minP=2;
			changes=true;
		}
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
	public void setMaxPlayers(int max)
	{
		maxPlayers=max;
		changes=true;
	}
	public int getMinPlayers()
	{
		return minPlayers;
	}
	public void setMinPlayers(int min)
	{
		if(min>=2)
		{
			minPlayers=min;
		}
		changes=true;
	}
	public Location getLobby()
	{
		return lobbyPoint;
	}
	public void setLobby(Location lobby)
	{
		lobbyPoint=lobby;
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
		return specPoint.getWorld();
	}
	public void fillChests()
	{
		for(Map.Entry<ChestClass,Set<Chest>> entry:chests.entrySet())
		{
			ChestClass cc=entry.getKey();
			for(Chest c:entry.getValue())
			{
				c.getInventory().clear();
				cc.fillChest(c);
			}
		}
	}
	public Map<ChestClass,Set<Chest>> getChests()
	{
		return chests;
	}
	public void addChest(ChestClass cc, Chest c)
	{
		if(chests.keySet().contains(cc))
		{
			chests.get(cc).add(c);
		}
		else
		{
			Set<Chest> newChestSet=new HashSet<Chest>();
			newChestSet.add(c);
			chests.put(cc,newChestSet);
		}
		changes=true;
	}
	public boolean isAssigned(ChestClass cc, Chest c)
	{
		if(chests.keySet().contains(cc))
		{
			return chests.get(cc).contains(c);
		}
		return false;
	}
}
