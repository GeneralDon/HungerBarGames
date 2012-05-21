package me.tomjw64.HungerBarGames;

public class ChestItem {
	private int item;
	private double chance;
	private int amount;
	private short data;
	
	public ChestItem(int id, int prob, int num)
	{
		this(id,prob,num,(short)0);
	}
	public ChestItem(int id, int prob, int num, short dt)
	{
		item=id;
		chance=((double)prob)/100;
		amount=num;
		data=dt;
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
	public short getData()
	{
		return data;
	}
}
