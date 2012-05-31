package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

import org.bukkit.entity.Player;

public class GameRewrite extends ChatVariableHolder{
	//Arena that the game is in
	private Arena arena;
	//Players in the game
	private Set<Player> tributes=new HashSet<Player>();
	//Deaths for the day
	private Set<String> deaths=new HashSet<String>();
	//Whether to repeat the game after it ends
	private boolean repeat;
		
	public GameRewrite(Arena ar, boolean rpt)
	{
		arena=ar;
		repeat=rpt;
		
		//Start Lobby
	}
}
