package me.tomjw64.HungerBarGames;

import java.util.Set;

import me.tomjw64.HungerBarGames.Managers.ConfigManager;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
	
	public static void handleCommand(CommandSender sender, String[] args)
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
							list+=GREEN+a.getName()+WHITE+", ";
						}
						list=list.substring(0,list.length()-1);
						sender.sendMessage(list);
					}
					break;
				case "select":
					//Select arena, and set to player in a map
					break;
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
		//Two argument commands
		else if(args.length==2)
		{
			switch(cmd)
			{
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
		//Three argument commands
		else if(args.length==3)
		{
			switch(cmd)
			{
				default:
					sender.sendMessage(prefix+RED+"That command doesn't exist!");
			}
		}
	}
}
