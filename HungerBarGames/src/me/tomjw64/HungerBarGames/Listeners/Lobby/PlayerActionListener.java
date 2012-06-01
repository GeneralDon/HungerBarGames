package me.tomjw64.HungerBarGames.Listeners.Lobby;

import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Listeners.GameListener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerActionListener extends GameListener{
	/*
	 * PlayerActionListener listens for player events that can take
	 * place at any time during the game. So, basically all player events
	 * except PlayerMotionListener.
	 */
	
	public PlayerActionListener(Game gm)
	{
		super(gm);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void death(PlayerDeathEvent death)
	{
		if(getGame().isTribute(death.getEntity()))
		{
			Player dead=death.getEntity();
			death.setDeathMessage(null);
			eliminatePlayer(dead);
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void leave(PlayerQuitEvent quit)
	{
		if(getGame().isTribute(quit.getPlayer()))
		{
			Player quitter=quit.getPlayer();
			quit.setQuitMessage(null);
			eliminatePlayer(quitter);
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void damage(EntityDamageByEntityEvent dmg)
	{
		//Check if both are players
		if(dmg.getEntity() instanceof Player&&dmg.getDamager() instanceof Player)
		{
			Player dmgd=(Player)dmg.getEntity();
			Player dmgr=(Player)dmg.getDamager();
			//Check if one is in game and one is not
			if((getGame().isTribute(dmgd)||getGame().isTribute(dmgr))&&(!getGame().isTribute(dmgd)||!getGame().isTribute(dmgr)))
			{
				//Cancel damage if one is in game and one is not
				dmg.setCancelled(true);
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void foodChange(FoodLevelChangeEvent fc)
	{
		if(fc.getEntity() instanceof Player)
		{
			if(getGame().inLobby()&&getGame().isTribute((Player)fc.getEntity()))
			{
				fc.setCancelled(true);
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGH)
	public void damageAll(EntityDamageEvent dmg)
	{
		if(dmg.getEntity() instanceof Player)
		{
			if(getGame().inLobby()&&getGame().isTribute((Player)dmg.getEntity()))
			{
				dmg.setCancelled(true);
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void move(PlayerMoveEvent move)
	{
		if(getGame().isTribute(move.getPlayer())&&!getGame().getArena().isInArena(move.getTo())&&!getGame().inLobby())
		{
			Location from=move.getFrom();
			double x=Math.floor(from.getX()+.5);
			double z=Math.floor(from.getZ()+.5);
			move.getPlayer().teleport(new Location(from.getWorld(),x,from.getY(),z,from.getYaw(),from.getPitch()));
			move.getPlayer().sendMessage(ChatColor.RED+"You cannot go outside the arena!");
		}
	}
	public void eliminatePlayer(Player p)
	{
		p.getWorld().strikeLightning(p.getLocation().add(0, 100, 0));
		getGame().addDeath(p.getName());
		getGame().removeTribute(p);
		if(getGame().getNumTributes()==1)
		{
			getGame().declareWinner();
		}
	}
}
