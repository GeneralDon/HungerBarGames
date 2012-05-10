package me.tomjw64.HungerBarGames.Managers;

import java.io.File;
import java.io.IOException;

import me.tomjw64.HungerBarGames.HungerBarGames;

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
}
