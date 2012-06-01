package me.tomjw64.HungerBarGames;

import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.GameStatus;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Game extends ChatVariableHolder{
	//Arena that the game is in
	private Arena arena;
	//Players in the game
	private Set<Player> tributes=new HashSet<Player>();
	//Deaths for the day
	private Set<String> deaths=new HashSet<String>();
	//Whether to repeat the game after it ends
	private boolean repeat;
	//Status of the game
	private GameStatus status;
	//If there are not enough players to start
	private boolean notEnoughPlayers=false;
		
	public Game(Arena ar, boolean rpt)
	{
		arena=ar;
		repeat=rpt;
		
		startLobby();
	}
	public void startLobby()
	{
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"A lobby has been started for arena "+BLUE+arena.getName()+"!");
		Bukkit.getServer().broadcastMessage(prefix+YELLOW+"Type "+BLUE+"/hbg join "+arena.getName()+YELLOW+" to join the game");
		
		//Start Lobby Timer
		
		//Register Lobby Listeners
	}
	public void startCountdown()
	{
		int point=0;
		String list=prefix+GREEN+"Tributes: "+RED;
		for(Player p:tributes)
		{
			list+=p.getName()+", ";
			p.teleport(arena.spawnAt(point));
			point++;
			p.setGameMode(GameMode.SURVIVAL);
			clearInv(p);
			fullHeal(p);
		}
		arena.fillChests();
		list=list.substring(0,list.length()-2);
		for(Player p:tributes)
		{
			p.sendMessage(list);
			p.sendMessage(prefix+GREEN+"The countdown has begun!");
		}
		
		//Start Countdown
		
		//Register Countdown Listeners
	}
	public void endGame()
	{
		//Unregister Listeners
		
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
		if(getNumTributes()<arena.getMaxPlayers()&&status==GameStatus.LOBBY&&getNumTributes()<arena.getNumSpawns())
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
	public void removeTribute(Player p)
	{
		tributes.remove(p);
		GamesManager.setInGame(p,false);
	}
	public boolean isTribute(Player p)
	{
		return tributes.contains(p);
	}
	public int getNumTributes()
	{
		return tributes.size();
	}
	public void addDeath(String playerName)
	{
		deaths.add(playerName);
	}
	public GameStatus getStatus()
	{
		return status;
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
	public Arena getArena()
	{
		return arena;
	}
}
