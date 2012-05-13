package me.tomjw64.HungerBarGames;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.tomjw64.HungerBarGames.Managers.ConfigManager;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler {
	/*
	 * CommandHandler will handle all commands sent to the plugin.
	 */
	//Colors
	private static final ChatColor RED=ChatColor.RED;
	private static final ChatColor BLUE=ChatColor.BLUE;
	private static final ChatColor YELLOW=ChatColor.YELLOW;
	private static final ChatColor GREEN=ChatColor.GREEN;
	private static final ChatColor WHITE=ChatColor.WHITE;
	//Player, selection associations
	private static Map<CommandSender,Arena> selection=new HashMap<CommandSender,Arena>();
	
	public static void handleCommand(HungerBarGames pl,CommandSender sender, String[] args)
	{
		//Plugin prefix
		String prefix=ConfigManager.getPrefix();
		//The command sent to the plugin
		String cmd=args[0];
		//One argument commands
		if(args.length==1)
		{
			switch(cmd)
			{
				case "help":
					//Show help/commands
					break;
				case "reload":
					//Reload config
					break;
				case "arenas":
					//List arenas
					Set<Arena> arenas=GamesManager.getArenas();
					if(arenas.size()==0)
					{
						sender.sendMessage(prefix+RED+"There are no arenas currently available!");
					}
					else
					{
						String list=prefix+YELLOW+"Arenas: ";
						for(Arena a:arenas)
						{
							ChatColor color;
							Game g=a.getGame();
							if(g==null)
							{
								color=RED;
							}
							else if(!g.inLobby())
							{
								color=GREEN;
							}
							else
							{
								color=BLUE;
							}
							list+=color+a.getName()+WHITE+", ";
						}
						list=list.substring(0,list.length()-2);
						sender.sendMessage(list);
						if(sender instanceof Player)
						{
							sender.sendMessage(prefix+YELLOW+"Key: "+RED+"No Game Running"+WHITE+"; "
									+GREEN+"Game In Session"+WHITE+"; "
									+BLUE+"In Lobby");
						}
					}
					break;
				case "setlobby":
					//Set lobby point
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						Arena a1=selection.get(p);
						if(a1!=null)
						{
							a1.setLobby(p.getLocation());
							p.sendMessage(prefix+YELLOW+"Lobby spawn set for arena "+BLUE+a1.getName()+YELLOW+"!");
						}
						else
						{
							p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
						}
					}
					break;
				case "setspec":
					//Set spectator point
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						Arena a1=selection.get(p);
						if(a1!=null)
						{
							a1.setSpec(p.getLocation());
							p.sendMessage(prefix+YELLOW+"Spectator spawn set for arena "+BLUE+a1.getName()+YELLOW+"!");
						}
						else
						{
							p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
						}
					}
					break;
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
		//Two argument commands
		else if(args.length==2)
		{
			String arg1=args[1];
			switch(cmd)
			{
				case "join":
					//Join a game
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						if(!GamesManager.isInGame(p))
						{
							Arena a=GamesManager.getArena(arg1);
							if(a!=null)
							{
								a.getGame().addTribute(p);
							}
							else
							{
								p.sendMessage(prefix+RED+"There is no arena by that name!");
							}
						}
						else
						{
							p.sendMessage(prefix+RED+"You are already in a game! Leave before you join another!");
						}
					}
					break;
				case "create":
					//Create an arena
					if(GamesManager.getArena(arg1)==null)
					{
						sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+arg1+YELLOW+" has been created!");
						Arena a=new Arena(pl,arg1);
						GamesManager.addArena(a);
					}
					else
					{
						sender.sendMessage(prefix+RED+"Did not create arena "+BLUE+arg1+RED+"! There is already an arena with that name!");
					}
					break;
				case "select":
					//Select arena, and set to player in a map
					Arena a=GamesManager.getArena(arg1);
					if(a!=null)
					{
						selection.put(sender,a);
						sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+a.getName()+YELLOW+" has been selected!");
					}
					break;
				case "spawn":
					//Set lobby point
					if(sender instanceof Player)
					{
						Player p=(Player)sender;
						Arena a1=selection.get(p);
						if(a1!=null)
						{
							int pos;
							try
							{
								pos=Integer.parseInt(arg1);
								a1.addSpawn(p.getLocation());
								p.sendMessage(prefix+YELLOW+"Spawn point "+BLUE+pos+YELLOW+" set for arena "+BLUE+a1.getName()+YELLOW+"!");
							}
							catch(Exception wtf)
							{
								p.sendMessage(prefix+RED+"Could not process command!");
							}
						}
						else
						{
							p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
						}
					}
					break;
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
		//Three argument commands
		else if(args.length==3)
		{
			String arg1=args[1];
			String arg2=args[2];
			switch(cmd)
			{
				case "start":
					//Start a game
					Arena a=GamesManager.getArena(arg1);
					if(a!=null)
					{
						if(a.getGame()==null)
						{
							if(a.getSpec()!=null&&a.getLobby()!=null)
							{
								boolean repeat;
								try
								{
									repeat=Boolean.parseBoolean(arg2);
									a.startGame(repeat);
								}
								catch(Exception wtf)
								{
									wtf.printStackTrace();
									sender.sendMessage(prefix+RED+"Could not process command!");
								}
							}
							else
							{
								sender.sendMessage(prefix+RED+"Arena has not been set up correctly!");
							}
						}
						else
						{
							sender.sendMessage(prefix+RED+"A game is already running in arena"+BLUE+arg1+"!");
						}
					}
					else
					{
						sender.sendMessage(prefix+RED+"There is no arena by that name!");
					}
					break;
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
	}
}
