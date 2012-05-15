package me.tomjw64.HungerBarGames;

public class ChestItem {
	private int item;
	private double chance;
	private int amount;
	
	public ChestItem(int id, int prob, int num)
	{
		item=id;
		chance=((double)prob)/100;
		amount=num;
	}
	public int getID()
	{
		return item;
	}
	public double getChance()
	{
		return chance;
	}
	public int getAmount()
	{
		return amount;
	}
}
