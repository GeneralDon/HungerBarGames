package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpec extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a=CommandHandler.getSelections().get(p);
			if(a!=null)
			{
				a.setSpec(p.getLocation());
				p.sendMessage(prefix+YELLOW+"Spectator spawn set for arena "+BLUE+a.getName()+YELLOW+"!");
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
			}
		}
	}

	@Override
	public String cmd() {
		return "setspec";
	}

	@Override
	public String usage() {
		return cmd();
	}

	@Override
	public String description() {
		return "sets the spectator spawn point";
	}

	@Override
	public String permission() {
		return "HBG.admin.setspec";
	}

	@Override
	public int numArgs() {
		return 0;
	}

}
