package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;

public class StartRpt extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender.isOp())
		{
			Arena a2=GamesManager.getArena(args[0]);
			if(a2!=null)
			{
				if(a2.getGame()==null)
				{
					if(a2.getSpec()!=null&&a2.getLobby()!=null)
					{
						a2.startGame(true);
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
