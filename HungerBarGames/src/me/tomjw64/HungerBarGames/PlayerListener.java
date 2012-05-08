package me.tomjw64.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener{
	public static HungerBarGames plugin;
	
	public PlayerListener(HungerBarGames instance)
	{
		plugin=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pDeath(final PlayerDeathEvent death)
	{
		Player died=death.getEntity();
		if(GamesManager.gameRunning()&&GamesManager.getGameList().contains(died))
		{
			died.getWorld().strikeLightningEffect(died.getLocation().add(0, 100, 0));
			GamesManager.removeFromGameList(died);
			GamesManager.addToDeathList(died.getName());
			death.setDeathMessage(null);
			if(GamesManager.getGameList().size()==1)
			{
				endGame();
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pQuit(final PlayerQuitEvent quit)
	{
		Player left=quit.getPlayer();
		if(GamesManager.gameRunning()&&GamesManager.getGameList().contains(left))
		{
			left.getWorld().strikeLightningEffect(left.getLocation().add(0, 100, 0));
			GamesManager.removeFromGameList(left);
			if(GamesManager.getGameList().size()==1)
			{
				endGame();
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pJoin(final PlayerJoinEvent join)
	{
		Player joined=join.getPlayer();
		if(GamesManager.getGameList().contains(joined))
		{
			GamesManager.removeFromGameList(joined);
		}
		joined.setGameMode(GameMode.CREATIVE);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pRespawn(final PlayerRespawnEvent respawn)
	{
		Player spawned=respawn.getPlayer();
		if(GamesManager.getGameList().contains(spawned))
		{
			GamesManager.removeFromGameList(spawned);
		}
		spawned.setGameMode(GameMode.CREATIVE);
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pDamage(final EntityDamageByEntityEvent damage)
	{
		if(damage.getDamager() instanceof Player&&damage.getEntity() instanceof Player)
		{
			if(!GamesManager.getGameList().contains((Player)damage.getDamager())
					||!GamesManager.getGameList().contains((Player)damage.getEntity()))
			{
				damage.setCancelled(true);
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pMove(final PlayerMoveEvent move)
	{
		if(GamesManager.isCountdown()&&GamesManager.getGameList().contains(move.getPlayer()))
		{
			Location from=move.getFrom();
			Location to=move.getTo();
			if(Math.floor(to.getX())!=Math.floor(from.getX())||Math.floor(to.getZ())!=Math.floor(from.getZ()))
			{
				move.getFrom().getWorld().createExplosion(to,0,false);
				move.getPlayer().setHealth(0);
			}
		}
	}
	@EventHandler(priority=EventPriority.NORMAL)
	public void pDrop(final PlayerDropItemEvent drop)
	{
		if(!drop.getPlayer().isOp()&&!GamesManager.getGameList().contains(drop.getPlayer()))
		{
			drop.setCancelled(true);
		}
	}
	public void endGame()
	{
		GamesManager.stopGame();
		plugin.getServer().broadcastMessage(ChatColor.GREEN+((Player)GamesManager.getGameList().toArray()[0]).getName()+" has won The Hunger Games!");
		((Player)GamesManager.getGameList().toArray()[0]).setGameMode(GameMode.CREATIVE);
		GamesManager.clearGameList();
		GamesManager.clearDeathList();
	}
}

