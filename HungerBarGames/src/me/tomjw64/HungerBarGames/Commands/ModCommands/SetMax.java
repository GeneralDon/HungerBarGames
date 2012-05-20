package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;

public class SetMax extends ChatVariableHolder implements HBGCommand {

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=CommandHandler.getSelections().get(sender);
		if(a!=null)
		{ 
			int max;
			try
			{
				max=Integer.parseInt(args[0]);
				if(max>=2&&max>=a.getMinPlayers())
				{
					a.setMaxPlayers(max);
					sender.sendMessage(prefix+YELLOW+"Maximum number of players set to "+BLUE+max+YELLOW+" for arena "+BLUE+a.getName()+YELLOW+"!");
				}
				else
				{
					sender.sendMessage(prefix+RED+"Max must be greater than or equal to 2 and Min!");
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
		return "setmax";
	}

	@Override
	public String usage() {
		return cmd()+" [#]";
	}

	@Override
	public String description() {
		return "sets max amount of players";
	}

	@Override
	public String permission() {
		return "HBG.admin.setmax";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
