package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a=CommandHandler.getSelections().get(p);
			if(a!=null)
			{
				if(a.isCuboidSet())
				{
					if(a.isInArena(p))
					{
						int pos;
						try
						{
							pos=Integer.parseInt(args[0]);
							a.addSpawn(p.getLocation());
							p.sendMessage(prefix+YELLOW+"Spawn point "+BLUE+pos+YELLOW+" set for arena "+BLUE+a.getName()+YELLOW+"!");
						}
						catch(Exception wtf)
						{
							p.sendMessage(prefix+RED+"Could not process command!");
						}
					}
					else
					{
						p.sendMessage(prefix+RED+"You should set your spawns within your arena!");
					}
				}
				else
				{
					p.sendMessage(prefix+RED+"You need to set up your arena cuboid first!");
				}
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
			}
		}
	}

	@Override
	public String cmd() {
		return "setspawn";
	}

	@Override
	public String usage() {
		return cmd()+" [#]";
	}

	@Override
	public String description() {
		return "sets a starting point";
	}

	@Override
	public String permission() {
		return "HBG.admin.setspawn";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
