package me.tomjw64.HungerBarGames.Threads;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

public class Countdown extends ChatVariableHolder implements Runnable{
	private Game game;
	
	public Countdown(Game gm)
	{
		game=gm;
	}
	@Override
	public void run()
	{
		
	}
	
}
