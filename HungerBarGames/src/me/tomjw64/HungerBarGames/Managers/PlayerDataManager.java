package me.tomjw64.HungerBarGames.Managers;

import java.io.File;
import java.io.IOException;

import me.tomjw64.HungerBarGames.HungerBarGames;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class PlayerDataManager {
	/*
	 * PlayerDataManager creates a file to hold player information and data.
	 * Information such as thirst, energy, and ranking points is stored here.
	 * This file may be occasionally edited by the user.
	 */
	private static File databaseFile;
	private static FileConfiguration database;
	
	//Call onEnable for initialization
	public static void loadPlayerData(HungerBarGames pl)
	{
		PluginDescriptionFile pdf=pl.getDescription();	
		//Naming the file
		databaseFile=new File(pl.getDataFolder(),"playerdata.yml");		
		//Create if it doesn't exist
		if(!databaseFile.exists())
		{
			databaseFile.getParentFile().mkdirs();
			try {
				databaseFile.createNewFile();
				HungerBarGames.logger.info("["+pdf.getName()+"] Generating player data file!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}	
		//Actually loading the file
		database=new YamlConfiguration();
		try {
			database.load(databaseFile);
			HungerBarGames.logger.info("["+pdf.getName()+"] Loading player data file!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}
	}
	
	//Get the player data file
	public static FileConfiguration getPlayerData()
	{
		return database;
	}
	
	//Save player data file
	public static void savePlayerData()
	{
		try {
			database.save(databaseFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
}
