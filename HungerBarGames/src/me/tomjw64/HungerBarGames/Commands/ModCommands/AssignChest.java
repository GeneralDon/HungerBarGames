package me.tomjw64.HungerBarGames.Commands.ModCommands;

import me.tomjw64.HungerBarGames.Arena;
import me.tomjw64.HungerBarGames.ChestClass;
import me.tomjw64.HungerBarGames.CommandHandler;
import me.tomjw64.HungerBarGames.Commands.ChatVariableHolder;
import me.tomjw64.HungerBarGames.Commands.HBGCommand;
import me.tomjw64.HungerBarGames.Managers.ConfigManager;

import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AssignChest extends ChatVariableHolder implements HBGCommand{

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
		{
			Player p=(Player)sender;
			Arena a=CommandHandler.getSelections().get(p);
			if(a!=null)
			{
				if(a.isCuboidSet())
				{
					ChestClass cc=ConfigManager.getChestClass(args[0]);
					if(cc!=null)
					{
						BlockState target=p.getTargetBlock(null,30).getState();
						if(a.isInArena(target.getBlock()))
						{
							if(target instanceof Chest)
							{
								Chest c=(Chest)target;
								if(!a.isAssigned(cc,c))
								{
									a.addChest(cc,(Chest)target);
									p.sendMessage(prefix+GREEN+"Chest assigned!");
								}
								else
								{
									p.sendMessage(prefix+RED+"That chest is already assigned to that class for this arena!");
								}
							}
						}
						else
						{
							p.sendMessage(prefix+RED+"You should set your chests within your arena!");
						}
					}
					else
					{
						p.sendMessage(prefix+RED+"There is no chest class by that name! Create it in the config!");
					}
				}
				else
				{
					p.sendMessage(prefix+RED+"You need to set up your arena cuboid first!");
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
		return "assign";
	}

	@Override
	public String usage() {
		return cmd()+" [class]";
	}

	@Override
	public String description() {
		return "assigns a chest to a class";
	}

	@Override
	public String permission() {
		return "HBG.admin.chestassign";
	}

	@Override
	public int numArgs() {
		return 1;
	}

}
