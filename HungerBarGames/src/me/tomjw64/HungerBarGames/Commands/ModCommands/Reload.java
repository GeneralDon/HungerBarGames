package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.HungerBarGames;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

import org.bukkit.command.CommandSender;

public class Reload extends ChatVariableHolder implements HBGCommand{
	public static HungerBarGames pl;
	
	public Reload(HungerBarGames instance)
	{
		pl=instance;
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String cmd() {
		return "reload";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "does nothing";
	}

	@Override
	public String permission() {
		return "HBG.mod.reload";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
