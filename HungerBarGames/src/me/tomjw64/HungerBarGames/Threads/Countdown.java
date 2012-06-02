package me.tomjw64.HungerBarGames.Threads;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

public class Countdown extends ChatVariableHolder implements Runnable{
	private Game game;
	private int time=ConfigManager.getCountdown();
	private String arena;
	private Set<Player> tributes;
	
	public Countdown(Game gm)
	{
		game=gm;
		game.setStatus(Status.COUNTDOWN);
		game.updateListeners();
		arena=game.getArena().getName();
		tributes=game.getTributes();
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		while(time>0)
		{
			if(time<11||time%10==0)
			{
				for(Player p:tributes)
				{
					p.sendMessage(prefix+GREEN+"The game begins in "+time+" seconds!");
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception wtf) {
				wtf.printStackTrace();
			}
			time--;
		}
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"A game has begun in arena "+BLUE+arena+"!");
		for(Player p:tributes)
		{
			p.sendMessage(prefix+GREEN+"May the odds be ever in your favor!");
		}
		game.startGame();
	}
	
}
