package me.tomjw64.HungerBarGames.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.General.ChestClass;
import me.tomjw64.HungerBarGames.General.ChestItem;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

public class ConfigManager {
	/*
	 * ConfigManager creates a config file for the plugin.
	 * Plugin preferences and options are set in this file.
	 * This file will be edited by the user in almost all cases.
	 */
	private static File configFile;
	private static FileConfiguration config;
	//Configuration Options
	//The displayed plugin prefix
	private static String prefix;
	//Whether to restrict chat
	private static boolean restrictChat;
	//Restricted chat radius
	private static int chatRadius;
	//Whether or not for people to explode when moving during countdown
	private static boolean onPlayerMove;
	//Countdown time for games
	private static int countdown;
	//Lobby delay between rounds
	private static int delay;
	//Arena defaults
	private static int defaultMax;
	private static int defaultMin;
	//Whether plugin should block pvp on the server
	private static boolean pvp;
	//Whether plugin should restrict block editing
	private static boolean restrictBlock;
	//Whether plugin should use player group permissions
	private static boolean playerPerms;
	//Holds whitelisted/blacklisted blocks
	private static Set<Integer> exempt=new HashSet<Integer>();
	//Chest Classes
	private static Set<ChestClass> chests=new HashSet<ChestClass>();
	
	
	//Call onEnable for initialization
	public static void loadConfig(HungerBarGames pl)
	{
		PluginDescriptionFile pdf=pl.getDescription();
		//Naming the file
		configFile=new File(pl.getDataFolder(),"config.yml");
		//Create if it doesn't exist
		if(!configFile.exists())
		{
			configFile.getParentFile().mkdirs();
			try {
				configFile.createNewFile();
				HungerBarGames.logger.info("["+pdf.getName()+"] Generating config!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}
		//Actually loading the file
		config=new YamlConfiguration();
		try {
			config.load(configFile);
			HungerBarGames.logger.info("["+pdf.getName()+"] Loading config!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}		
		
		//Check for missing config sections and set defaults
		if(config.options().header()==null)
		{
			config.options().header("HungerBarGames Config File.\nGo to http://dev.bukkit.org/server-mods/hungerbargames/pages/configuration-tutorial/ for a tutorial.");
		}
		if(!config.contains("Prefix"))
		{
			config.createSection("Prefix");
			config.set("Prefix", "HBG");
		}
		if(!config.contains("RestrictChat"))
		{
			config.createSection("RestrictChat");
			config.set("RestrictChat", false);
		}
		if(!config.contains("ChatRadius"))
		{
			config.createSection("ChatRadius");
			config.set("ChatRadius", 30);
		}
		if(!config.contains("ExplodeOnPlayerMove"))
		{
			config.createSection("ExplodeOnPlayerMove");
			config.set("ExplodeOnPlayerMove", false);
		}
		if(!config.contains("Countdown"))
		{
			config.createSection("Countdown");
			config.set("Countdown", 30);
		}
		if(!config.contains("Delay"))
		{
			config.createSection("Delay");
			config.set("Delay", 120);
		}
		if(!config.contains("DefaultMax"))
		{
			config.createSection("DefaultMax");
			config.set("DefaultMax", 24);
		}
		if(!config.contains("DefaultMin"))
		{
			config.createSection("DefaultMin");
			config.set("DefaultMin", 12);
		}
		if(!config.contains("HandlePvP"))
		{
			config.createSection("HandlePvP");
			config.set("HandlePvP", false);
		}
		if(!config.contains("PlayerPerms"))
		{
			config.createSection("PlayerPerms");
			config.set("PlayerPerms", false);
		}
		if(!config.contains("RestrictBlocks"))
		{
			config.createSection("RestrictBlocks");
			config.set("RestrictBlocks", false);
		}
		if(!config.contains("ExemptBlocks"))
		{
			config.createSection("ExemptBlocks");
			config.set("ExemptBlocks", new ArrayList<String>());
		}
		if(!config.contains("ChestClasses"))
		{
			config.createSection("ChestClasses");
		}
		saveConfig();
		
		//Load configuration options to memory
		prefix=ChatColor.BLUE+"["+ChatColor.YELLOW+config.getString("Prefix")+ChatColor.BLUE+"] "+ChatColor.WHITE;
		restrictChat=config.getBoolean("RestrictChat");
		chatRadius=config.getInt("ChatRadius");
		onPlayerMove=config.getBoolean("ExplodeOnPlayerMove");
		countdown=config.getInt("Countdown");
		delay=config.getInt("Delay");
		defaultMax=config.getInt("DefaultMax");
		defaultMin=config.getInt("DefaultMin");
		pvp=config.getBoolean("HandlePvP");
		playerPerms=config.getBoolean("PlayerPerms");
		restrictBlock=config.getBoolean("RestrictBlocks");
		for(String id:config.getStringList("ExemptBlocks"))
		{
			try
			{
				exempt.add(Integer.valueOf(Integer.parseInt(id)));
			}
			catch(Exception wtf)
			{
				HungerBarGames.logger.warning("Could not load block "+id+" as an exemption!");
			}
		}
		ConfigurationSection classes=config.getConfigurationSection("ChestClasses");
		for(String x:classes.getKeys(false))
		{
			ChestClass cc= new ChestClass(x);
			for(String i:classes.getStringList(x))
			{
				String[] info=i.split(";");
				try
				{
					int chance=Integer.parseInt(info[1]);
					int amount=Integer.parseInt(info[2]);
					int item;
					if(info[0].contains("#"))
					{
						String[] itemInfo=info[0].split("#");
						item=Integer.parseInt(itemInfo[0]);
						short data=Short.parseShort(itemInfo[1]);
						cc.addItem(new ChestItem(item,chance,amount,data));
					}
					else
					{
						item=Integer.parseInt(info[0]);
						cc.addItem(new ChestItem(item,chance,amount));
					}
				}
				catch(Exception wtf)
				{
					wtf.printStackTrace();
					HungerBarGames.logger.warning("Could not load a chest item under class "+x);
				}
			}
			chests.add(cc);
		}
		
	}
	//Get the config
	public static FileConfiguration getConfig()
	{
		return config;
	}
	//Save config
	public static void saveConfig()
	{
		try {
			config.save(configFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
	//Get the plugin's prefix
	public static String getPrefix()
	{
		return prefix;
	}
	//Get if chat is restricted
	public static boolean getChatRestricted()
	{
		return restrictChat;
	}
	//Get chat radius restriction
	public static int getChatRadius()
	{
		return chatRadius;
	}
	//Gets onPlayerMove action
	public static boolean getExplode()
	{
		return onPlayerMove;
	}
	//Gets the countdown time
	public static int getCountdown()
	{
		return countdown;
	}
	//Gets the delay between rounds
	public static int getDelay()
	{
		return delay;
	}
	//Gets defualt max players
	public static int getMaxPlayers()
	{
		return defaultMax;
	}
	//Gets defualt min players
	public static int getMinPlayers()
	{
		return defaultMin;
	}
	//Gets whether the plugin should handle pvp or not
	public static boolean getPvP()
	{
		return pvp;
	}
	public static boolean restrictEditing()
	{
		return restrictBlock;
	}
	public static boolean isListed(int id)
	{
		for(Integer i:exempt)
		{
			if(i.intValue()==id)
			{
				return true;
			}
		}
		return false;
	}
	public static ChestClass getChestClass(String name)
	{
		for(ChestClass cc:chests)
		{
			if(cc.getName().equalsIgnoreCase(name))
			{
				return cc;
			}
		}
		return null;
	}
	public static boolean usingPlayerPerms()
	{
		return playerPerms;
	}
}
