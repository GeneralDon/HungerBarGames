package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.List;

public class ChestClass {
	private String name;
	private List<ChestItem> fillItems=new ArrayList<ChestItem>();
	
	public ChestClass(String className)
	{
		name=className;
	}
	public String getName()
	{
		return name;
	}
	public void fillChest(Chest c)
	{
		//TODO: Fill chests
	}
}
