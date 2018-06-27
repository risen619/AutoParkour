package com.github.risen619.AutoParkour;

import org.bukkit.Material;

public class AllowedBlock
{
	private Material material;
	private Byte data;
	private boolean allowedAll;
	
	public Material material() { return material; }
	public Byte data() { return data; }
	public boolean allowedAll() { return allowedAll; }

	public void data(byte d) { data = d; }
	
	public AllowedBlock(int material, Byte data)
	{
		this(Material.values()[material], data);
	}
	
	public AllowedBlock(Material m, Byte d)
	{
		material = m;
		data = d;
		allowedAll = data == null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString()
	{
		return String.format("%d%s", material.getId(), allowedAll ? "" : (":" + data));
	}
}