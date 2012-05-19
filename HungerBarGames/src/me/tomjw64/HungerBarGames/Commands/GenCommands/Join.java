package me.tomjw64.HungerBarGames.Commands.GenCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Join extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			if(!GamesManager.isInGame(p))
			{
				Arena a=GamesManager.getArena(args[0]);
				if(a!=null)
				{
					if(a.getGame()!=null)
					{
						a.getGame().addTribute(p);
					}
					else
					{
						p.sendMessage(prefix+RED+"There is not a game running in that arena!");
					}
				}
				else
				{
					p.sendMessage(prefix+RED+"There is no arena by that name!");
				}
			}
			else
			{
				p.sendMessage(prefix+RED+"You are already in a game! Leave before you join another!");
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
