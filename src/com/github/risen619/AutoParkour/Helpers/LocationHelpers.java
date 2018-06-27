package com.github.risen619.AutoParkour.Helpers;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationHelpers
{	
	public static Location lookAt(Location who, Location what)
	{
		Vector v = what.toVector().subtract(who.toVector());
		double x = (int)v.getX();
		double z = (int)v.getZ();
		System.out.println(x + " :::: " + z);
		double yaw = 0;
		
		if(x != 0 && z != 0)
		{
			yaw = Math.abs(Math.atan(x/z))/Math.PI * 180;
			
			if(x > 0 && z > 0) yaw += -90;
			else if(x > 0 && z < 0) yaw += -180;
			else if(x < 0 && z < 0) yaw += 90;
			else if(x < 0 && z > 0) yaw += 0;
		}
		else
		{
			x = MathHelpers.clamp(-1, 1, x);
			z = MathHelpers.clamp(-1, 1, z);
			if(x == 0 && z > 0) yaw = 0;
			else if(x == 0 && z < 0) yaw = -180;
			else if(z == 0 && x > 0) yaw = -90;
			else if(z == 0 && x < 0) yaw = 90;
		}
		double pitch = -v.getY() / what.distance(who) * 67.66;
		
		Location r = who.clone();
		r.setYaw((float)yaw);
		r.setPitch((float)pitch);
		
		return r;
	}
	
	public static boolean isInArea(Location a, Location b, Location l)
	{
		Vector min = Vector.getMinimum(a.toVector(), b.toVector());
		Vector max = Vector.getMaximum(a.toVector(), b.toVector());
		Vector o = l.toVector();
		return (o.getX() >= min.getX() && o.getX() <= max.getX()) &&
				(o.getY() >= min.getY() && o.getY() <= max.getY()) &&
				(o.getZ() >= min.getZ() && o.getZ() <= max.getZ());
	}
	
	public static Location randomPoint(Location min, Location max)
	{
		double x = MathHelpers.randomBetween(min.getX(), max.getX());
		double y = MathHelpers.randomBetween(min.getY(), max.getY());
		double z = MathHelpers.randomBetween(min.getZ(), max.getZ());
		
		return new Location(min.getWorld(), x, y, z);
	}
	
	public static boolean isAbove(Location who, Location what)
	{
		return who.getY() >= what.getY() && 
				who.getBlockX() == what.getBlockX() &&
				who.getBlockZ() == what.getBlockZ();
	}
	
}