package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

public class SetMin extends ChatVariableHolder implements HBGCommand {

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=CommandHandler.getSelections().get(sender);
		if(a!=null)
		{ 
			int min;
			try
			{
				min=Integer.parseInt(args[0]);
				if(min>=2&&min<=a.getMaxPlayers())
				{
					a.setMinPlayers(min);
					sender.sendMessage(prefix+YELLOW+"Minimum number of players set to "+BLUE+min+YELLOW+" for arena "+BLUE+a.getName()+YELLOW+"!");
				}
				else
				{
					sender.sendMessage(prefix+RED+"Min must be between 2 and Max!");
				}
			}
			catch(Exception wtf)
			{
				sender.sendMessage(prefix+RED+"Could not process command!");
			}
		}
		else
		{
			sender.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
		}
	}

	@Override
	public String cmd() {
		return "setmin";
	}

	@Override
	public String usage() {
		return cmd()+" [#]";
	}

	@Override
	public String description() {
		return "sets min amount of players";
	}

	@Override
	public String permission() {
		return "HBG.admin.setmin";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
