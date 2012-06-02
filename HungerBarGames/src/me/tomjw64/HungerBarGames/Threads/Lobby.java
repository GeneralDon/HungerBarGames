package me.tomjw64.HungerBarGames.Threads;

import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

public class Lobby extends ChatVariableHolder implements Runnable{
	private Game game;
	private int time=ConfigManager.getDelay();
	
	public Lobby(Game gm)
	{
		game=gm;
		game.setStatus(Status.LOBBY);
		game.updateListeners();
		new Thread(this).start();
	}
	
	@Override
	public void run()
	{
		while(time>0)
		{
			if(time<6||time%30==0)
			{
				for(Player p:game.getTributes())
				{
					p.sendMessage(prefix+GREEN+"The countdown begins in "+time+" seconds!");
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception wtf) {
				wtf.printStackTrace();
			}
			time--;
		}
		if(game.getNumTributes()>=game.getArena().getMinPlayers())
		{
			game.startCountdown();
		}
		else
		{
			for(Player p:game.getTributes())
			{
				p.sendMessage(prefix+RED+"There are not enough players in the game!");
				p.sendMessage(prefix+RED+"Have "+game.getNumTributes()+"/"+game.getArena().getMinPlayers()+" players needed to start!");
				p.sendMessage(prefix+RED+"The game will start when enough players have joined!");
				game.setNotEnoughPlayers();
			}
		}
	}
	
}
