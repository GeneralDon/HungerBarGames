package me.tomjw64.HungerBarGames.Threads;

import org.bukkit.World;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Status;

public class NightCheck extends ChatVariableHolder implements Runnable{
	private Game game;
	private World world;
	private long lastDisplay;
	
	public NightCheck(Game gm)
	{
		game=gm;
		world=game.getArena().getWorld();
	}
	
	@Override
	public void run()
	{
		while(game.getStatus()==Status.IN_GAME)
		{
			if(world.getTime()>13000&&System.currentTimeMillis()>lastDisplay+600000)
			{
				for(Player p:game.getTributes())
				{
					p.sendMessage(prefix+YELLOW+"Tributes killed today:");
					if(game.getDeaths().size()>0)
					{
						for(String x:game.getDeaths())
						{
							p.sendMessage(GREEN+x);
						}
					}
					else
					{
						p.sendMessage(GREEN+"None");
					}
				}
				lastDisplay=System.currentTimeMillis();
				game.clearDeaths();
			}
			try {
				Thread.sleep(120000);
			} catch (Exception wtf) {
				wtf.printStackTrace();
			}
		}
	}

}
