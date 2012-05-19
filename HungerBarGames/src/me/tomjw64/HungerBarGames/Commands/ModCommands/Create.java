package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

public class Create extends ChatVariableHolder implements HBGCommand{
	public static HungerBarGames pl;
	
	public Create(HungerBarGames instance)
	{
		pl=instance;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(GamesManager.getArena(args[0])==null)
		{
			sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+args[0]+YELLOW+" has been created!");
			Arena a=new Arena(pl,args[0]);
			GamesManager.addArena(a);
		}
		else
		{
			sender.sendMessage(prefix+RED+"Did not create arena "+BLUE+args[0]+RED+"! There is already an arena with that name!");
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
