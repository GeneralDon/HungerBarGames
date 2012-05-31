package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;

public class Select extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=GamesManager.getArena(args[0]);
		if(a!=null)
		{
			CommandHandler.getSelections().put(sender,a);
			sender.sendMessage(prefix+YELLOW+"Arena "+BLUE+a.getName()+YELLOW+" has been selected!");
		}
	}

	@Override
	public String cmd() {
		return "select";
	}

	@Override
	public String usage() {
		return cmd()+" [arena]";
	}

	@Override
	public String description() {
		return "selects an arena for editing";
	}

	@Override
	public String permission() {
		return "HBG.mod.select";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
