package me.tomjw64.HungerBarGames.Managers;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.Arena;

public class GamesManager {
	/*
	 * GamesManager handles arena and game associations
	 * Methods and fields in this class should be static.
	 */
	private static Set<Arena> arenas=new HashSet<Arena>();
	
	public static void addArena(Arena ar)
	{
		arenas.add(ar);
	}
	public static Set<Arena> getArenas()
	{
		return arenas;
	}
}
