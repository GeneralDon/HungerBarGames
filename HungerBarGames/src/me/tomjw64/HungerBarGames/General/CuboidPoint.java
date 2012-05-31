package me.tomjw64.HungerBarGames.General;

import org.bukkit.World;

public class CuboidPoint {
	private World world;
	private int x,z;
	
	public CuboidPoint(World w,int xPos,int zPos)
	{
		world=w;
		x=xPos;
		z=zPos;
	}
	public World getWorld()
	{
		return world;
	}
	public int getX()
	{
		return x;
	}
	public int getZ()
	{
		return z;
	}
}
