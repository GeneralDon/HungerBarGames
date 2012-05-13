package me.tomjw64.HungerBarGames;

public class ChestFillItem {
	private int item;
	private int chance;
	private int amount;
	
	public ChestFillItem(int id, int prob, int num)
	{
		item=id;
		chance=prob;
		amount=num;
	}
}
