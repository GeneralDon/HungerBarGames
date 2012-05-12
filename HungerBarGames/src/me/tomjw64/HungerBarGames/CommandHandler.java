package me.tomjw64.HungerBarGames;

import org.bukkit.command.CommandSender;

public class CommandHandler {
	/*
	 * CommandHandler will handle all commands sent to the plugin.
	 */
	public static void handleCommand(CommandSender sender, String[] args)
	{
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
					break;
				case "select":
					//Select arena, and set to player in a map
					break;
				default:
					sender.sendMessage("That command doesn't exist!");
			}
		}
		//Two argument commands
		else if(args.length==2)
		{
			switch(cmd)
			{
				default:
					sender.sendMessage("That command doesn't exist!");
			}
		}
		//Three argument commands
		else if(args.length==3)
		{
			switch(cmd)
			{
				default:
					sender.sendMessage("That command doesn't exist!");
			}
		}
	}
}
