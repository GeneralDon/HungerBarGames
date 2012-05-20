package me.tomjw64.HungerBarGames.Commands;

import org.bukkit.command.CommandSender;

public interface HBGCommand {
	public void execute(CommandSender sender, String[] args);
	public String cmd();
	public String usage();
	public String description();
	public String permission();
	public int numArgs();
}
