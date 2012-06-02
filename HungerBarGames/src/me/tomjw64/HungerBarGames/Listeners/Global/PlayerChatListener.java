package me.tomjw64.HungerBarGames.Listeners.Global;

import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener{

public static HungerBarGames pl;
	
	public PlayerChatListener(HungerBarGames instance)
	{
		pl=instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
	public void chat(PlayerChatEvent chat)
	{
		
	}
}