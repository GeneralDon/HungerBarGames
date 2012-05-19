package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;

public class Select extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender.isOp())
		{
			Arena a=GamesManager.getArena(args[0]);
			if(a!=null)
			{
				CommandHandler.getSelections().put(sender,a);
				sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+a.getName()+YELLOW+" has been selected!");
			}
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
