package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Listeners.GameListener;
import me.tomjw64.HungerBarGames.Listeners.Lobby.*;
import me.tomjw64.HungerBarGames.Listeners.Countdown.*;
import me.tomjw64.HungerBarGames.Listeners.Game.*;
import me.tomjw64.HungerBarGames.Managers.GamesManager;
import me.tomjw64.HungerBarGames.Threads.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Game extends ChatVariableHolder{
	private Arena arena;
	private Set<Player> tributes=new HashSet<Player>();
	private Set<String> deaths=new HashSet<String>();
	private Set<GameListener> listeners=new HashSet<GameListener>();
	private boolean repeat;
	private Status status;
	private boolean notEnoughPlayers=false;
		
	public Game(Arena ar, boolean rpt)
	{
		arena=ar;
		repeat=rpt;
		
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"A lobby has been started for arena "+BLUE+arena.getName()+"!");
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"Type "+BLUE+"/hbg join "+arena.getName()+YELLOW+" to join the game");
		
		new Lobby(this);
	}
	
	public void startGame()
	{
		status=Status.IN_GAME;
		updateListeners();
		
		new NightCheck(this);
	}
	
	public void startCountdown()
	{
		int point=0;
		String list=prefix+GREEN+"Tributes: "+RED;
		for(Player p:tributes)
		{
			list+=p.getName()+", ";
			p.teleport(arena.spawnAt(point));
			p.setGameMode(GameMode.SURVIVAL);
			clearInv(p);
			fullHeal(p);
			point++;
		}
		list=list.substring(0,list.length()-2);
		for(Player p:tributes)
		{
			p.sendMessage(prefix+GREEN+"The countdown has begun!");
			p.sendMessage(list);
		}
		
		arena.fillChests();
		new Countdown(this);
		
	}
	
	public void endGame()
	{
		status=null;
		clearListeners();
		arena.endGame(repeat);
	}
	
	public void declareWinner()
	{
		Player p=(Player)tributes.toArray()[0];
		GamesManager.setInGame(p,false);
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"Player "+BLUE+p.getName()+YELLOW+" has won the game in arena "+BLUE+arena.getName()+"!");
		fullHeal(p);
		//TODO: Give rewards
		endGame();
	}
	
	public void addTribute(Player p)
	{
		if(getNumTributes()<arena.getMaxPlayers()&&status==Status.LOBBY&&getNumTributes()<arena.getNumSpawns())
		{
			p.sendMessage(prefix+YELLOW+"You have joined the game in arena "+BLUE+arena.getName()+"!");
			p.sendMessage(prefix+YELLOW+"This game has "+BLUE+getNumTributes()+"/"+arena.getMaxPlayers()+YELLOW+" players!");
			tributes.add(p);
			GamesManager.setInGame(p,true);
			p.setGameMode(GameMode.SURVIVAL);
			clearInv(p);
			fullHeal(p);
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
	
	public void eliminateTribute(Player p)
	{
		p.getWorld().strikeLightning(p.getLocation().add(0, 100, 0));
		deaths.add(p.getName());
		removeTribute(p);
		if(getNumTributes()==1)
		{
			declareWinner();
		}
	}
	
	public void removeTribute(Player p)
	{
		tributes.remove(p);
		GamesManager.setInGame(p,false);
	}
	
	public Set<Player> getTributes()
	{
		return tributes;
	}
	
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	
	public int getNumTributes()
	{
		return tributes.size();
	}
	
	public Set<String> getDeaths()
	{
		return deaths;
	}
	
	public void clearDeaths()
	{
		deaths.clear();
	}
	
	public void setStatus(Status stat)
	{
		status=stat;
		clearListeners();
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public void setNotEnoughPlayers()
	{
		notEnoughPlayers=true;
	}
	
	public void clearInv(Player p)
	{
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.AIR));
		p.getInventory().setChestplate(new ItemStack(Material.AIR));
		p.getInventory().setLeggings(new ItemStack(Material.AIR));
		p.getInventory().setBoots(new ItemStack(Material.AIR));
	}
	
	public void fullHeal(Player p)
	{
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);
	}
	
	public void addListener(GameListener gl)
	{
		listeners.add(gl);
	}
	
	public void updateListeners()
	{
		listeners.clear();
		switch(status)
		{
		case LOBBY:
			new LobbyLevelListener(this);
			new LobbyBlockListener(this);
			break;
		case COUNTDOWN:
			new CountdownMotionListener(this);
			new CountdownInteractListener(this);
			break;
		case IN_GAME:
			new EliminationListener(this);
			new GameDamageListener(this);
			new GameBlockListener(this);
			new BlockLogger(this);
			break;
		}
	}
	
	public void clearListeners()
	{
		for(GameListener gl:listeners)
		{
			gl.unregister();
			gl=null;
		}
	}
	
	public Arena getArena()
	{
		return arena;
	}
	
}
