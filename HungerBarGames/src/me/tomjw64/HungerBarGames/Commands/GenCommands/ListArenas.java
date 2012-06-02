package me.tomjw64.HungerBarGames.Commands.GenCommands;

import java.util.Set;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.Game;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;
import me.tomjw64.HungerBarGames.General.Status;
import me.tomjw64.HungerBarGames.Managers.GamesManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListArenas extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		Set<Arena> arenas=GamesManager.getArenas();
		if(arenas.size()==0)
		{
			sender.sendMessage(prefix+RED+"There are no arenas currently available!");
		}
		else
		{
			String list=prefix+YELLOW+"Arenas: ";
			for(Arena a:arenas)
			{
				ChatColor color;
				Game g=a.getGame();
				if(g==null)
				{
					color=RED;
				}
				else if(g.getStatus()==Status.LOBBY)
				{
					color=BLUE;
				}
				else
				{
					color=GREEN;
				}
				list+=color+a.getName()+WHITE+", ";
			}
			list=list.substring(0,list.length()-2);
			sender.sendMessage(list);
			if(sender instanceof Player)
			{
				sender.sendMessage(prefix+YELLOW+"Key: "+RED+"No Game Running"+WHITE+"; "
						+GREEN+"Game In Session"+WHITE+"; "
						+BLUE+"In Lobby");
			}
		}
	}

	@Override
	public String cmd() {
		return "arenas";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "lists arenas";
	}

	@Override
	public String permission() {
		return "HBG.player.list";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
