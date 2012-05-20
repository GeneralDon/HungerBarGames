package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Commands.GenCommands.*;
import me.tomjw64.HungerBarGames.Commands.ModCommands.*;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandHandler {
	/*
	 * CommandHandler will handle all commands sent to the plugin.
	 */
	//Colors
	private static final ChatColor RED=ChatColor.RED;
	//Commands
	private static List<HBGCommand> cmds=new ArrayList<HBGCommand>();
	//Player, selection associations
	private static Map<CommandSender,Arena> selections=new HashMap<CommandSender,Arena>();
	
	public static void loadCommands(HungerBarGames pl)
	{
		cmds.add(new Help());
		cmds.add(new ListArenas());
		cmds.add(new Join());
		cmds.add(new Spec());
		cmds.add(new AssignChest());
		cmds.add(new Create(pl));
		cmds.add(new Delete());
		cmds.add(new Reload(pl));
		cmds.add(new Select());
		cmds.add(new SetLobby());
		cmds.add(new SetSpawn());
		cmds.add(new SetSpec());
		cmds.add(new Start());
		cmds.add(new StartRpt());
	}
	public static void handleCommand(HungerBarGames pl,CommandSender sender, String[] args)
	{
		//The command sent to the plugin
		String cmd=args[0];
		//New arguments
		String[] subArgs=new String[0];
		if(args.length!=0)
		{
			subArgs=new String[args.length-1];
			System.arraycopy(args,1,subArgs,0,subArgs.length);
		}
		//Check for the command
		for(HBGCommand c:cmds)
		{
			if(cmd.equalsIgnoreCase(c.cmd()))
			{
				if(subArgs.length>=c.numArgs())
				{
					String perm=c.permission();
					String[] permSplit=perm.split(".");
					String permGroup="";
					for(int x=0;x<permSplit.length-1;x++)
					{
						permGroup+=permSplit[x]+".";
					}
					permGroup+="*";
					if(sender.isOp()||sender.hasPermission(perm)||sender.hasPermission(permGroup)||sender.hasPermission("HBG.*"))
					{
						c.execute(sender,subArgs);
					}
					else
					{
						sender.sendMessage(RED+"Insufficient permissions!");
					}
				}
				else
				{
					sender.sendMessage(RED+"Incompatible arguments!");
				}
				return;
			}
		}
		sender.sendMessage(RED+"Not a valid command!");
	}
	public static Map<CommandSender,Arena> getSelections()
	{
		return selections;
	}
	public static List<HBGCommand> getCmds()
	{
		return cmds;
	}
}