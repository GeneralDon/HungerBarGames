 package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.General.ChatVariableHolder;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLobby extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a1=CommandHandler.getSelections().get(p);
			if(a1!=null)
			{
				a1.setLobby(p.getLocation());
				p.sendMessage(prefix+YELLOW+"Lobby spawn set for arena "+BLUE+a1.getName()+YELLOW+"!");
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
			}
		}
	}

	@Override
	public String cmd() {
		return "setlobby";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "sets the lobby spawn point";
	}

	@Override
	public String permission() {
		return "HBG.admin.setlobby";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
