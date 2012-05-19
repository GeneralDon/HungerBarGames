package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;

public class Delete extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=CommandHandler.getSelections().get(sender);
		if(a!=null)
		{
			GamesManager.delArena(a);
			CommandHandler.getSelections().remove(sender);
		}
		else
		{
			sender.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
		}
	}

	@Override
	public String cmd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String permissionTier() {
		// TODO Auto-generated method stub
		return null;
	}

}
