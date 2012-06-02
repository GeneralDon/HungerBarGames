package me.tomjw64.HungerBarGames.Listeners.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

public class GameDamageListener extends GameListener{

	public GameDamageListener(Game gm) {
		super(gm);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		if(dmg.getEntity() instanceof Player&&dmg.getDamager() instanceof Player)
		{
			Player dmgd=(Player)dmg.getEntity();
			Player dmgr=(Player)dmg.getDamager();
			if((getGame().isTribute(dmgd)!=getGame().isTribute(dmgr)))
			{
				dmg.setCancelled(true);
			}
		}
	}

}
