package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.Listeners.PlayerActionListener;
import me.tomjw64.HungerBarGames.Listeners.PlayerMotionListener;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

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
	//Deaths for the day
	private Set<String> deaths=new HashSet<String>();
	//Listeners
	private PlayerActionListener pal;
	private PlayerMotionListener pml;
	//Colors
	private final ChatColor GREEN=ChatColor.GREEN;
	private final ChatColor RED=ChatColor.RED;
	private final ChatColor BLUE=ChatColor.BLUE;
	private final ChatColor YELLOW=ChatColor.YELLOW;
	//Plugin prefix
	private String prefix=ConfigManager.getPrefix();
	//Time delays
	private long delay;
	private int countdown;
	private long lastDisplay;
	//Countdown task ID
	private int taskID;
	//Whether to repeat the game after it ends
	private boolean repeat;
	//Lobby boolean flags
	private boolean lobby;
	private boolean notEnoughPlayers=false;
	
	public Game(HungerBarGames instance, Arena ar, boolean rpt)
	{
		pl=instance;
		//Load event listeners
		pal=new PlayerActionListener(this);
		pml=new PlayerMotionListener(this);
		Bukkit.getServer().getPluginManager().registerEvents(pal, pl);
		//Initialize game variables
		lobby=true;
		arena=ar;
		delay=ConfigManager.getDelay()*20;
		countdown=ConfigManager.getCountdown();
		repeat=rpt;
		startGame();
	}
	//Start the game
	public void startGame()
	{
		pl.getServer().broadcastMessage(prefix+YELLOW+"A lobby has been started for arena "+BLUE+arena.getName()+"!");
		pl.getServer().broadcastMessage(prefix+YELLOW+"Type "+BLUE+"/hbg join "+arena.getName()+YELLOW+" to join the game");
		pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
			{
				public void run()
				{
					if(/*getNumTributes()>arena.getMinPlayers()*/true)
					{
						startCountdown();
					}
					else
					{
						for(Player p:tributes)
						{
							p.sendMessage(prefix+RED+"There are not enough players in the game!");
							p.sendMessage(prefix+RED+"Have "+getNumTributes()+"/"+arena.getMinPlayers()+" players needed to start!");
							p.sendMessage(prefix+RED+"The game will start when enough players have joined!");
							notEnoughPlayers=true;
						}
					}
				}
			},delay);
	}
	//Start countdown
	public void startCountdown()
	{
		lobby=false;
		int point=0;
		String list=prefix+GREEN+"Tributes: "+RED;
		for(Player p:tributes)
		{
			list+=p.getName()+", ";
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
		Bukkit.getServer().getPluginManager().registerEvents(pml, pl);
		list=list.substring(0,list.length()-2);
		for(Player p:tributes)
		{
			p.sendMessage(list);
			p.sendMessage(prefix+GREEN+"The countdown has begun!");
		}
		taskID=pl.getServer().getScheduler().scheduleAsyncRepeatingTask(pl, new Runnable()
			{
				public void run()
				{
					if((countdown<11||countdown%10==0)&&countdown>0)
					{
						for(Player p:tributes)
						{
							p.sendMessage(prefix+GREEN+"The game begins in "+countdown+" seconds!");
						}
					}
					else if(countdown==0)
					{
						pl.getServer().broadcastMessage(prefix+YELLOW+"A game has begun in arena "+BLUE+arena.getName()+"!");
						pml.unregister();
						pml=null;
						for(Player p:tributes)
						{
							p.sendMessage(prefix+GREEN+"May the odds be ever in your favor!");
						}
					}
					else if(countdown%120==0&&arena.getWorld().getTime()>13000&&System.currentTimeMillis()>lastDisplay+600000)
					{
						for(Player p:tributes)
						{
							p.sendMessage(prefix+YELLOW+"Tributes killed today:");
							for(String x:deaths)
							{
								p.sendMessage(GREEN+x);
							}
						}
						lastDisplay=System.currentTimeMillis();
						deaths.clear();
					}
					countdown--;
				}
			},0L,20L);
	}
	//Ends the game
	public void endGame()
	{
		pl.getServer().getScheduler().cancelTask(taskID);
		pal.unregister();
		pal=null;
		arena.endGame(repeat);
	}
	//Declares a winner, then ends game
	public void declareWinner(Player p)
	{
		pl.getServer().broadcastMessage(prefix+YELLOW+"Player "+BLUE+p.getName()+YELLOW+"has won the game in arena "+BLUE+arena.getName()+"!");
		//TODO: Give rewards
		endGame();
	}
	//Check if a player is in a game
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	//Add a player to the game
	public void addTribute(Player p)
	{
		if(getNumTributes()<arena.getMaxPlayers()&&lobby&&getNumTributes()<arena.getNumSpawns())
		{
			tributes.add(p);
			GamesManager.setInGame(p,true);
			p.sendMessage(prefix+YELLOW+"You have joined the game in arena "+BLUE+arena.getName()+"!");
			p.sendMessage(prefix+YELLOW+"This game has "+BLUE+getNumTributes()+"/"+arena.getMaxPlayers()+YELLOW+" players!");
			p.getInventory().clear();
			p.getInventory().setHelmet(new ItemStack(Material.AIR));
			p.getInventory().setChestplate(new ItemStack(Material.AIR));
			p.getInventory().setLeggings(new ItemStack(Material.AIR));
			p.getInventory().setBoots(new ItemStack(Material.AIR));
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
			p.teleport(arena.getLobby());
			if(notEnoughPlayers&&getNumTributes()>=arena.getMinPlayers())
			{
				notEnoughPlayers=false;
				startCountdown();
			}
		}
		else if(getNumTributes()>=arena.getMaxPlayers()||getNumTributes()>=arena.getNumSpawns())
		{
			p.sendMessage(prefix+RED+"There is not enough room in the game!");
		}
		else
		{
			p.sendMessage(prefix+RED+"The game has already been started!");
		}
	}
	//Remove a player from the game
	public void removeTribute(Player p)
	{
		tributes.remove(p);
		GamesManager.setInGame(p,false);
	}
	//Returns the current number or tributes
	public int getNumTributes()
	{
		return tributes.size();
	}
	//Returns the list of tributes
	public Set<Player> getTributes()
	{
		return tributes;
	}
	//Check if in lobby
	public boolean inLobby()
	{
		return lobby;
	}
	//Add a player to the death list
	public void addDead(String playerName)
	{
		deaths.add(playerName);
	}
}
