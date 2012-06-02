package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.command.CommandSender;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

public class Create extends ChatVariableHolder implements HBGCommand{
	
	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(GamesManager.getArena(args[0])==null)
		{
			sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+args[0]+YELLOW+" has been created!");
			Arena a=new Arena(args[0]);
			GamesManager.addArena(a);
		}
		else
		{
			sender.sendMessage(prefix+RED+"Did not create arena "+BLUE+args[0]+RED+"! There is already an arena with that name!");
		}
	}

	@Override
	public String cmd() {
		return "create";
	}

	@Override
	public String usage() {
		return cmd()+" [name]";
	}

	@Override
	public String description() {
		return "creates an arena with the given name";
	}

	@Override
	public String permission() {
		return "HBG.admin.create";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
