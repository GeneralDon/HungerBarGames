package me.tomjw64.HungerBarGames.Managers;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.HungerBarGames;

public class GamesManager {
	/*
	 * GamesManager handles arena and game associations
	 * Methods and fields in this class should be static.
	 */
	//All arenas
	private static Set<Arena> arenas=new HashSet<Arena>();
	//Players in a game
	private static Set<Player> inGame=new HashSet<Player>();
	
	public static void addArena(Arena ar)
	{
		if(getArena(ar.getName())==null)
		{
			arenas.add(ar);
		}
		else
		{
			HungerBarGames.logger.warning("Did not load arena "+ar.getName()+"! There is already an arena with that name!");
		}
	}
	public static Set<Arena> getArenas()
	{
		return arenas;
	}
	public static Arena getArena(String name)
	{
		for(Arena a:arenas)
		{
			if(a.getName().equalsIgnoreCase(name))
			{
				return a;
			}
		}
		return null;
	}
	public static boolean isInGame(Player pl)
	{
		for(Player p:inGame)
		{
			if(p.equals(pl))
			{
				return true;
			}
		}
		return false;
	}
	public static void setInGame(Player p,boolean isPlaying)
	{
		if(isPlaying)
		{
			inGame.add(p);
		}
		else
		{
			inGame.remove(p);
		}
	}
	public static void delArena(Arena a)
	{
		arenas.remove(a);
	}
}
