package me.tomjw64.HungerBarGames;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerBarGames extends JavaPlugin{
	public static HungerBarGames plugin;
	public final Logger logger=Logger.getLogger("Minecraft");
	public PlayerListener pListener;
	public BlockListener bListener;
	private File configFile;
	private FileConfiguration config;
	private File databaseFile;
	private FileConfiguration database;
	private long lastDisplay;
	private World world;
	private ChatColor GREEN=ChatColor.GREEN;
	private ChatColor RED=ChatColor.RED;
	
	@Override
	public void onDisable()
	{
		PluginDescriptionFile pdf = getDescription();
		logger.info("["+pdf.getName()+"] disabled!");
	}
	@Override
	public void onEnable()
	{
		pListener=new PlayerListener(this);
		bListener=new BlockListener(this);
		PluginDescriptionFile pdf=getDescription();
		loadConfig(pdf);
		loadDatabase(pdf);
		startTimeCheck();
		logger.info("["+pdf.getName()+"] version "+pdf.getVersion()+" is enabled!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("hbg"))
		{
			if(args.length==1)
			{
				if(args[0].equalsIgnoreCase("reap")&&!GamesManager.gameRunning()&&!GamesManager.isReaping()&&sender.isOp())
				{
					GamesManager.setReaping();
					getServer().broadcastMessage(GREEN+"The reaping for The Hunger Games has begun!");
					getServer().broadcastMessage(GREEN+"Type \"/hgs join!\" to join the games!");
					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run()
						{
							if(GamesManager.isReaping())
							{
								GamesManager.stopReaping();
								GamesManager.startGame();
								startCountdown();
							}
						}
					},1200L);
					getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
						public void run()
						{
							if(GamesManager.isReaping())
							{
								for(Player p:GamesManager.getGameList())
								{
									p.sendMessage(RED+"Don't move, the games are about to begin!");
								}
							}
						}
					},1100L);
				}
				else if(args[0].equalsIgnoreCase("join")&&GamesManager.isReaping())
				{
					if(sender instanceof Player)
					{
						if(GamesManager.getGameList().size()<24)
						{
							sender.sendMessage(GREEN+"You've volunteered as tribute!");
							sender.sendMessage(RED+"Put away your stuff!");
							GamesManager.addToGameList((Player)sender);
						}
						else
						{
							sender.sendMessage(RED+"There is no more room for tributes!");
						}
					}
				}
				else if(args[0].equalsIgnoreCase("view")&&GamesManager.gameRunning())
				{
					if(sender instanceof Player)
					{
						if(GamesManager.getGameList().contains((Player)sender))
						{
							sender.sendMessage(GREEN+"There are "+GamesManager.getGameList().size()+" tributes left in the game!");
						}
						else
						{
							String tributes=GREEN+"Tributes: "+RED;
							for(Player p:GamesManager.getGameList())
							{
								tributes+=p.getName()+", ";
							}
							tributes=tributes.substring(0,tributes.length()-1);
							sender.sendMessage(tributes);
						}
					}
				}
				else if(args[0].equalsIgnoreCase("end")&&sender.isOp())
				{
					if(GamesManager.gameRunning())
					{
						GamesManager.stopGame();
						GamesManager.clearGameList();
						GamesManager.clearDeathList();
						getServer().broadcastMessage(RED+"The Hunger Games have been ended!");
					}
					else if(GamesManager.isReaping())
					{
						GamesManager.stopGame();
						GamesManager.clearGameList();
						GamesManager.clearDeathList();
						GamesManager.stopReaping();
						getServer().broadcastMessage(RED+"The Hunger Games have been canceled!");
					}
				}
				else if(args[0].equalsIgnoreCase("fill")&&sender.isOp())
				{
					for(String s:database.getStringList("Chests"))
					{
						String[] info=s.split(";");
						int x=Integer.parseInt(info[0]);
						int y=Integer.parseInt(info[1]);
						int z=Integer.parseInt(info[2]);
						if(world.getBlockAt(x,y,z).getState() instanceof Chest)
						{
							Chest c=(Chest)world.getBlockAt(x,y,z).getState();
							c.getInventory().clear();
							String type=info[3];
							for(String t:config.getStringList("Chests."+type))
							{ 
								String[] conf=t.split(";");
								int item=Integer.parseInt(conf[0]);
								int amount=Integer.parseInt(conf[1]);
								double chance=Double.parseDouble(conf[2])/100;
								if(Math.random()<chance)
								{
									c.getInventory().addItem(new ItemStack(item,amount));
								}
							}
						}
					}
					sender.sendMessage(GREEN+"Chests filled!");
				}
			}
			else if(args.length==2)
			{
				if(args[0].equalsIgnoreCase("set"))
				{
					if(sender instanceof Player)
					{
						try
						{
							int pos=Integer.parseInt(args[1]);
							Location l=((Player)sender).getLocation();
							database.set("Starts."+pos+".X",l.getX());
							database.set("Starts."+pos+".Y",l.getY());
							database.set("Starts."+pos+".Z",l.getZ());
							database.set("Starts."+pos+".Yaw",l.getYaw());
							database.set("Starts."+pos+".Pitch",l.getPitch());
							saveDatabase();
							sender.sendMessage(GREEN+"Point set for position "+pos);
						}
						catch(Exception wtf)
						{
							sender.sendMessage(RED+"Could not set the point for some reason!");
						}
					}
				}
				else if(args[0].equalsIgnoreCase("warp"))
				{
					if(sender instanceof Player)
					{
						try
						{
							int pos=Integer.parseInt(args[1]);
							double x=(double)database.getDouble("Starts."+pos+".X");
							double y=database.getDouble("Starts."+pos+".Y");
							double z=database.getDouble("Starts."+pos+".Z");
							float yaw=Float.parseFloat(database.getString("Starts."+pos+".Yaw"));
							float pitch=Float.parseFloat(database.getString("Starts."+pos+".Pitch"));
							((Player)sender).teleport(new Location(world,x,y,z,yaw,pitch));
							sender.sendMessage(GREEN+"Warping to position "+pos);
						}
						catch(Exception wtf)
						{
							sender.sendMessage(RED+"Could not warp to the point for some reason!");
						}
					}
				}
				else if(args[0].equalsIgnoreCase("c")&&sender.isOp())
				{
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						Block b=p.getTargetBlock(null, 20);
						if(b.getState() instanceof Chest)
						{
							String type=args[1];
							List<String> coords=database.getStringList("Chests");
							if(!coords.contains(b.getX()+";"+b.getY()+";"+b.getZ()+";"+type))
							{
								coords.add(b.getX()+";"+b.getY()+";"+b.getZ()+";"+type);
								database.set("Chests", coords);
								saveDatabase();
								sender.sendMessage(GREEN+"Chest assigned!");
							}
							else
							{
								sender.sendMessage(RED+"This chest is already assigned that way!");
							}
						}
						else
						{
							sender.sendMessage(RED+"You aren't looking at a chest!");
						}
					}
				}
				else if(args[0].equalsIgnoreCase("cu")&&sender.isOp())
				{
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						Block b=p.getTargetBlock(null, 20);
						if(b.getState() instanceof Chest)
						{
							String type=args[1];
							List<String> coords=database.getStringList("Chests");
							if(coords.contains(b.getX()+";"+b.getY()+";"+b.getZ()+";"+type))
							{
								coords.remove(b.getX()+";"+b.getY()+";"+b.getZ()+";"+type);
								database.set("Chests", coords);
								saveDatabase();
								sender.sendMessage(GREEN+"Chest unassigned!");
							}
							else
							{
								sender.sendMessage(RED+"This chest is not assigned that way!");
							}
						}
						else
						{
							sender.sendMessage(RED+"You aren't looking at a chest!");
						}
					}
				}
			}
		}
		return true;
	}
	public void startCountdown()
	{
		String tributes=GREEN+"Tributes: "+RED;
		int i=1;
		for(Player p:GamesManager.getGameList())
		{
			p.getInventory().clear();
			p.getInventory().setHelmet(new ItemStack(Material.AIR));
			p.getInventory().setChestplate(new ItemStack(Material.AIR));
			p.getInventory().setLeggings(new ItemStack(Material.AIR));
			p.getInventory().setBoots(new ItemStack(Material.AIR));
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
			double x=(double)database.getDouble("Starts."+i+".X");
			double y=database.getDouble("Starts."+i+".Y");
			double z=database.getDouble("Starts."+i+".Z");
			float yaw=Float.parseFloat(database.getString("Starts."+i+".Yaw"));
			float pitch=Float.parseFloat(database.getString("Starts."+i+".Pitch"));
			p.teleport(new Location(world,x,y,z,yaw,pitch));
			tributes+=p.getName()+", ";
			i++;
		}
		tributes=tributes.substring(0,tributes.length()-1);
		GamesManager.startCountdown();
		getServer().broadcastMessage(tributes);
		getServer().broadcastMessage(GREEN+"The countdown has begun!");
		getServer().broadcastMessage(RED+"Don't move until the games begin!");
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run()
			{
				getServer().broadcastMessage(GREEN+"The Hunger Games have begun!");
				getServer().broadcastMessage(GREEN+"May the odds be ever in your favor!");
				GamesManager.stopCountdown();
			}
		},200L);
	}
	public void startTimeCheck()
	{
		final World w=world;
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run()
			{
				if(w.getTime()>13000&&System.currentTimeMillis()>lastDisplay+600000&&GamesManager.gameRunning())
				{
					getServer().broadcastMessage(ChatColor.RED+"Tributes Killed Today: ");
					for(String x:GamesManager.getDeathList())
					{
						getServer().broadcastMessage(x);
					}
					lastDisplay=System.currentTimeMillis();
					GamesManager.clearDeathList();
				}
			}
		}, 0L, 1200L);
	}
	public void loadConfig(PluginDescriptionFile pdf)
	{
		configFile=new File(getDataFolder(),"config.yml");
		if(!configFile.exists())
		{
			configFile.getParentFile().mkdirs();
			try{
				configFile.createNewFile();
				logger.info("["+pdf.getName()+"] Generating empty config.yml!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}
		config=new YamlConfiguration();
		try {
			config.load(configFile);
			logger.info("["+pdf.getName()+"] Loading config!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}
		
		if(!config.contains("World"))
		{
			config.createSection("World");
			config.set("World", "world");
			saveConfig();
		}
		world=getServer().getWorld(config.getString("World"));
	}
	public void saveConfig()
	{
		try {
			config.save(configFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
	public void loadDatabase(PluginDescriptionFile pdf)
	{
		databaseFile=new File(getDataFolder(),"database.yml");
		if(!databaseFile.exists())
		{
			databaseFile.getParentFile().mkdirs();
			try {
				databaseFile.createNewFile();
				logger.info("["+pdf.getName()+"] Generating flat-file database!");
			} catch (IOException wtf){
				wtf.printStackTrace();
			}
		}
		database=new YamlConfiguration();
		try {
			database.load(databaseFile);
			logger.info("["+pdf.getName()+"] Loading database!");
		} catch (Exception wtf) {
			wtf.printStackTrace();
		}
	}
	public FileConfiguration getYamlDatabase()
	{
		return database;
	}
	public void saveDatabase()
	{
		try {
			database.save(databaseFile);
		} catch (IOException wtf) {
			wtf.printStackTrace();
		}
	}
	public World getWorld()
	{
		return world;
	}
}
