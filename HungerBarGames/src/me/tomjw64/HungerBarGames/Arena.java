package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;

public class Arena {
	/*
	 * Arena handles all arena specific data
	 * such as spawn points and chests.
	 */
	//Name of arena
	private String name;
	//Holds player spawn points
	private List<Location> spawns=new ArrayList<Location>();
	//Holds chests associated with the arena
	private List<Chest> chests=new ArrayList<Chest>();
	//The spectator spawn
	private Location specPoint;
	//The maximum players set for this arena
	private int maxPlayers;
	//The minimum players set for this arena
	private int minPlayers;
	//Game being played in this arena
	private Game game;
}
