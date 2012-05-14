package me.tomjw64.HungerBarGames;

public class ChestItem {
	private int item;
	private int chance;
	private int amount;
	
	public ChestItem(int id, int prob, int num)
	{
		item=id;
		chance=prob;
		amount=num;
	}
}
