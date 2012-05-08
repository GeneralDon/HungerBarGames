package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class GamesManager{
	private static boolean gameRunning=false;
	private static boolean reaping=false;
	private static boolean countdown=false;
	private static Set<String> deathList=new HashSet<String>();
	private static Set<Player> inGame=new HashSet<Player>();
	
	public static Set<String> getDeathList()
	{
		return deathList;
	}
	public static void addToDeathList(String message)
	{
		deathList.add(message);
	}
	public static void clearDeathList()
	{
		deathList.clear();
	}
	
	public static Set<Player> getGameList()
	{
		return inGame;
	}
	public static void addToGameList(Player p)
	{
		inGame.add(p);
	}
	public static void removeFromGameList(Player p)
	{
		inGame.remove(p);
	}
	public static void clearGameList()
	{
		inGame.clear();
	}
	
	public static void startGame()
	{
		gameRunning=true;
	}
	public static void stopGame()
	{
		gameRunning=false;
	}
	public static boolean gameRunning()
	{
		return gameRunning;
	}
	
	public static void setReaping()
	{
		reaping=true;
	}
	public static void stopReaping()
	{
		reaping=false;
	}
	public static boolean isReaping()
	{
		return reaping;
	}
	
	public static void startCountdown()
	{
		countdown=true;
	}
	public static void stopCountdown()
	{
		countdown=false;
	}
	public static boolean isCountdown()
	{
		return countdown;
	}
}
