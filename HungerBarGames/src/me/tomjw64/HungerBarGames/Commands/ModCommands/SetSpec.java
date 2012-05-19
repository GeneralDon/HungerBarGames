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
			Arena a1=CommandHandler.getSelections().get(p);
			if(a1!=null)
			{
				a1.setSpec(p.getLocation());
				p.sendMessage(prefix+YELLOW+"Spectator spawn set for arena "+BLUE+a1.getName()+YELLOW+"!");
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
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
