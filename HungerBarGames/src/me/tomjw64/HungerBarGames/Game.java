package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.Listeners.BlockEditListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerActionListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerMotionListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Game {
	/*
	 * Game actually performs all game actions and checks
	 * Each game is associated with an area.
	 * Will also contain listeners for events that affect the game.
	 */
	public static HungerBarGames pl;
	//Arena that the game is in
	private Arena arena;
	//Players in the game
	private Set<Player> tributes=new HashSet<Player>();
	private Set<Player> specing=new HashSet<Player>();
	//Listeners
	private BlockEditListener bel;
	private PlayerActionListener pal;
	private PlayerMotionListener pml;
	//Colors
	private final ChatColor GREEN=ChatColor.GREEN;
	private final ChatColor RED=ChatColor.RED;
	private final ChatColor BLUE=ChatColor.BLUE;
	private final ChatColor YELLOW=ChatColor.YELLOW;
	//Plugin prefix
	private String prefix=ConfigManager.getPrefix();
	//Lobby/reaping time delay
	private long delay;
	//Whether to repeat the game after it ends
	private boolean repeat;
	//Lobby boolean flag
	private boolean lobby=true;
	
	public Game(HungerBarGames instance, Arena ar, long delaySec)
	{
		 this(instance,ar,delaySec,false);
	}
	public Game(HungerBarGames instance, Arena ar,long delaySec, boolean rpt)
	{
		pl=instance;
		//Load event listeners
		bel=new BlockEditListener(this);
		pal=new PlayerActionListener(this);
		pml=new PlayerMotionListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(bel, pl);
		Bukkit.getServer().getPluginManager().registerEvents(pal, pl);
		//Initialize game variables
		arena=ar;
		delay=delaySec*20;
		repeat=rpt;
		startGame();
	}
	//Start the game
	public void startGame()
	{
		pl.getServer().broadcastMessage(prefix+YELLOW+"A lobby has been started for Arena "+BLUE+arena.getName()+"!");
		pl.getServer().broadcastMessage(prefix+YELLOW+"Type "+BLUE+"/hbg join "+arena.getName()+YELLOW+" to join the game");
		pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
			{
				public void run()
				{
					Bukkit.getServer().getPluginManager().registerEvents(pml, pl);
					startCountdown();
				}
			},delay);
	}
	//Start countdown
	public void startCountdown()
	{
		String list=GREEN+"Tributes: "+RED;
		for(Player p:tributes)
		{
			list+=p.getName()+", ";
			int point=0;
			p.teleport(arena.spawnAt(point));
			point++;
			p.getInventory().clear();
			p.getInventory().setHelmet(new ItemStack(Material.AIR));
			p.getInventory().setChestplate(new ItemStack(Material.AIR));
			p.getInventory().setLeggings(new ItemStack(Material.AIR));
			p.getInventory().setBoots(new ItemStack(Material.AIR));
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
		}
		list=list.substring(0,list.length()-1);
		list=prefix+" "+list;
		for(Player p:tributes)
		{
			p.sendMessage(list);
			p.sendMessage(prefix+GREEN+"The countdown has begun!");
			p.sendMessage(prefix+GREEN+"The game begins in 30 seconds!");
		}
		lobby=false;
		pl.getServer().getScheduler().scheduleAsyncRepeatingTask(pl, new Runnable()
			{
				int seconds=30;
				public void run()
				{
					if(seconds<11&&seconds>0)
					{
						for(Player p:tributes)
						{
							p.sendMessage(prefix+GREEN+"The game begins in "+seconds+" seconds!");
						}
					}
					else if(seconds==0)
					{
						pl.getServer().broadcastMessage(prefix+YELLOW+"A game has begun in Arena "+BLUE+arena.getName()+"!");
						for(Player p:tributes)
						{
							pml.unregister();
							p.sendMessage(prefix+GREEN+"May the odds be ever in your favor!");
						}
					}
				}
			},0L,20L);
	}
	//Check if a player is in a game
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	//Add a player to the game
	public void addTribute(Player p)
	{
		tributes.add(p);
	}
	//Remove a player from the game
	public void removeTribute(Player p)
	{
		tributes.remove(p);
	}
	//Check if a player is in a game
	public boolean isSpec(Player p)
	{
		return specing.contains(p);
	}
	//Add a player to the game
	public void addSpec(Player p)
	{
		specing.add(p);
	}
	//Remove a player from the game
	public void removeSpec(Player p)
	{
		specing.remove(p);
	}
	//Check if in lobby
	public boolean inLobby()
	{
		return lobby;
	}
}
