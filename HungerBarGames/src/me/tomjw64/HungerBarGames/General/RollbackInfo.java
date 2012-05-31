package me.tomjw64.HungerBarGames.General;

public class RollbackInfo {
	private int ID;
	private byte data;
	
	public RollbackInfo(int id,byte metadata)
	{
		ID=id;
		data=metadata;
	}
	public int getID()
	{
		return ID;
	}
	public byte getData()
	{
		return data;
	}
}
