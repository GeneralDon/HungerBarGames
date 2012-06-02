package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class EliminationListener extends GameListener{

	public EliminationListener(Game gm) {
		super(gm);
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void death(PlayerDeathEvent death)
	{
		if(getGame().isTribute(death.getEntity()))
		{
			Player dead=death.getEntity();
			death.setDeathMessage(null);
			getGame().eliminateTribute(dead);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void leave(PlayerQuitEvent quit)
	{
		if(getGame().isTribute(quit.getPlayer()))
		{
			Player quitter=quit.getPlayer();
			quit.setQuitMessage(null);
			getGame().eliminateTribute(quitter);
		}
	}
	
}
