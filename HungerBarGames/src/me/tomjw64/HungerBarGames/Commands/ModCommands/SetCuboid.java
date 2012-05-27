package me.tomjw64.HungerBarGames.Commands.ModCommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;

public class SetCuboid extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a=CommandHandler.getSelections().get(p);
			if(a!=null)
			{
				Location l=p.getLocation();
				int pos;
				try
				{
					pos=Integer.parseInt(args[0]);
					if(pos==1)
					{
						if(a.getCuboid2()==null||a.getCuboid2().getWorld().equals(l.getWorld()))
						{
							a.setCuboid1(l.getWorld(),l.getBlockX(),l.getBlockZ());
							p.sendMessage(prefix+YELLOW+"Cuboid point "+BLUE+pos+YELLOW+" set for arena "+BLUE+a.getName()+YELLOW+"!");
						}
						else
						{
							p.sendMessage(prefix+RED+"You cannot set cuboid points in different worlds!");
						}
					}
					else if(pos==2)
					{
						if(a.getCuboid1()==null||a.getCuboid1().getWorld().equals(l.getWorld()))
						{
							a.setCuboid2(l.getWorld(),l.getBlockX(),l.getBlockZ());
							p.sendMessage(prefix+YELLOW+"Cuboid point "+BLUE+pos+YELLOW+" set for arena "+BLUE+a.getName()+YELLOW+"!");
						}
						else
						{
							p.sendMessage(prefix+RED+"You cannot set cuboid points in different worlds!");
						}
					}
					else
					{
						p.sendMessage(prefix+RED+"Argument must be 1 or 2!");
					}
				}
				catch(Exception wtf)
				{
					p.sendMessage(prefix+RED+"Could not process command!");
				}
			}
			else
			{
				p.sendMessage(prefix+RED+"You have no arena selected! Type "+BLUE+"/hbg select [arena]"+RED+" to select an arena!");
			}
		}
	}

	@Override
	public String cmd() {
		return "setcuboid";
	}

	@Override
	public String usage() {
		return cmd()+" [1/2]";
	}

	@Override
	public String description() {
		return "sets cuboid points for an arena";
	}

	@Override
	public String permission() {
		return "HBG.admin.setcuboid";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
