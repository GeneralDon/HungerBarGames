package me.tomjw64.HungerBarGames;

import java.util.ArrayList;
import java.util.List;

public class ChestClass {
	private String name;
	private List<ChestItem> items=new ArrayList<ChestItem>();
	
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
		Inventory i=c.getInventory();
		int limit=i.getSize();
		for(ChestItem ci:items)
		{
			if(i.contains(ci.getID()))
			{
				
			}
			else
			{
				int index=(int)Math.random()*27;
			}
		}
	}
}
