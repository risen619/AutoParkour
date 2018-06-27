package com.github.risen619.AutoParkour.Helpers;

public class MathHelpers
{
	public static double clamp(double min, double max, double x)
	{
		if(x > max) return max;
		if(x < min) return min;
		return x;
	}

	public static int clamp(int min, int max, int x) { return (int)clamp((double)min, max, x); }
	
	public static double randomBetween(double a, double b)
	{
		if(a == b) return a;
		if(a > b)
			return b + ((int)(Math.random() * 1000000) % (a-b));
		else return a + ((int)(Math.random() * 1000000) % (b-a));
	}
	
	public static int randomBetween(int a, int b) { return (int)randomBetween((double)a,b); }
}