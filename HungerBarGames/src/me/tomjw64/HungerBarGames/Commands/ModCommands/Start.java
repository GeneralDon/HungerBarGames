package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;

public class Start extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Arena a=GamesManager.getArena(args[0]);
		if(a!=null)
		{
			if(a.getGame()==null)
			{
				if(a.getSpec()!=null&&a.getLobby()!=null&&a.getCuboid1()!=null&a.getCuboid2()!=null)
				{
					a.startGame(false);
				}
				else
				{
					sender.sendMessage(prefix+RED+"Arena has not been set up correctly!");
				}
			}
			else
			{
				sender.sendMessage(prefix+RED+"A game is already running in arena"+BLUE+args[0]+"!");
			}
		}
		else
		{
			sender.sendMessage(prefix+RED+"There is no arena by that name!");
		}
	}

	@Override
	public String cmd() {
		return "start";
	}

	@Override
	public String usage() {
		return cmd()+" [arena]";
	}

	@Override
	public String description() {
		return "starts a non-repeating game";
	}

	@Override
	public String permission() {
		return "HBG.mod.start";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
