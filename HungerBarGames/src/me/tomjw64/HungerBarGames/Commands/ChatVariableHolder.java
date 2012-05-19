package me.tomjw64.HungerBarGames.Commands;

import org.bukkit.ChatColor;

import me.tomjw64.HungerBarGames.Managers.ConfigManager;

public class ChatVariableHolder {
	protected final String prefix=ConfigManager.getPrefix();
	protected final ChatColor RED=ChatColor.RED;
	protected final ChatColor GREEN=ChatColor.GREEN;
	protected final ChatColor BLUE=ChatColor.BLUE;
	protected final ChatColor YELLOW=ChatColor.YELLOW;
	protected final ChatColor WHITE=ChatColor.WHITE;
}
