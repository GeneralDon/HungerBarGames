package me.tomjw64.HungerBarGames;

import org.bukkit.command.CommandSender;

public class CommandHandler {
	/*
	 * CommandHandler will handle all commands sent to the plugin.
	 */
	public static void handleCommand(CommandSender sender, String[] args)
	{
		//The command sent to the plugin
		String cmd=args[1];
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
			}
		}
		//Two argument commands
		else if(args.length==2)
		{
			switch(cmd)
			{
			}
		}
		//Three argument commands
		else if(args.length==3)
		{
			switch(cmd)
			{
			}
		}
	}
}
