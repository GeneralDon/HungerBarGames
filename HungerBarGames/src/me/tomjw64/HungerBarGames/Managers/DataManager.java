package me.tomjw64.HungerBarGames.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class DataManager {
	/*
	 * DataManager creates a file for getting and setting data
	 * related or pertaining to arenas and general plugin save files.
	 * This file should not be edited by a user in most cases.
	 */
	private static File databaseFile;
	private static FileConfiguration database;
	
	//Call onEnable for initialization
	public static void loadDatabase(HungerBarGames pl)
	{
		PluginDescriptionFile pdf=pl.getDescription();
		//Naming the file
		databaseFile=new File(pl.getDataFolder(),"database.yml");
		//Create if it doesn't exist
		if(!databaseFile.exists())
		{
			databaseFile.getParentFile().mkdirs();
			try {
				databaseFile.createNewFile();
				HungerBarGames.logger.info("["+pdf.getName()+"] Generating database!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}
		//Actually loading the file
		database=new YamlConfiguration();
		try {
			database.load(databaseFile);
			HungerBarGames.logger.info("["+pdf.getName()+"] Loading database!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}
		
		//Load Arenas
		for(String s:database.getKeys(false))
		{
			String path=s+".";
			World w=Bukkit.getServer().getWorld(database.getString(path+"World"));
			String[] lobbyData=database.getString(path+"Lobby").split(";");
			double lx=Double.parseDouble(lobbyData[0]);
			double ly=Double.parseDouble(lobbyData[1]);
			double lz=Double.parseDouble(lobbyData[2]);
			float lyaw=Float.parseFloat(lobbyData[3]);
			float lpitch=Float.parseFloat(lobbyData[4]);
			Location lobby=new Location(w,lx,ly,lz,lyaw,lpitch);
			String[] specData=database.getString(path+"Spec").split(";");
			double sx=Double.parseDouble(specData[0]);
			double sy=Double.parseDouble(specData[1]);
			double sz=Double.parseDouble(specData[2]);
			float syaw=Float.parseFloat(specData[3]);
			float spitch=Float.parseFloat(specData[4]);
			Location spec=new Location(w,sx,sy,sz,syaw,spitch);
			List<Location> spawns=new ArrayList<Location>();
			for(String sp:database.getStringList(path+"Spawns"))
			{
				String[] data=sp.split(";");
				double x=Double.parseDouble(data[0]);
				double y=Double.parseDouble(data[1]);
				double z=Double.parseDouble(data[2]);
				float yaw=Float.parseFloat(data[3]);
				float pitch=Float.parseFloat(data[4]);
				spawns.add(new Location(w,x,y,z,yaw,pitch));
			}
			GamesManager.addArena(new Arena(pl,s,database.getInt(path+"Min"),database.getInt("Max"),lobby,spec,spawns));
		}
	}
	//Get the database
	public static FileConfiguration getDatabase()
	{
		return database;
	}
	//Save database
	public static void saveDatabase()
	{
		try {
			database.save(databaseFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
	//Save changed and new arenas to database
	public static void saveArenas()
	{
		for(Arena a:GamesManager.getArenas())
		{
			if(!database.contains(a.getName())||a.getChanged())
			{
				String path=a.getName()+".";
				database.set(path+"Min", a.getMinPlayers());
				database.set(path+"Max", a.getMaxPlayers());
				Location l=a.getLobby();
				database.set(path+"Lobby",l.getX()+";"+l.getY()+";"+l.getZ()+";"+l.getYaw()+";"+l.getPitch());
				database.set(path+"World",l.getWorld().getName());
				Location l1=a.getSpec();
				database.set(path+"Spec",l1.getX()+";"+l1.getY()+";"+l1.getZ()+";"+l1.getYaw()+";"+l1.getPitch());
				List<String> spawns=new ArrayList<String>();
				for(Location l2:a.getSpawns())
				{
					spawns.add(l2.getX()+";"+l2.getY()+";"+l2.getZ()+";"+l2.getYaw()+";"+l2.getPitch());
				}
				database.set(path+"Spawns",spawns);
			}
			saveDatabase();
		}
	}
}
