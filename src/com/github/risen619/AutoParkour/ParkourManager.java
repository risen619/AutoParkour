package com.github.risen619.AutoParkour;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class ParkourManager
{
	private static ParkourManager instance = null;
	private HashMap<UUID, ParkourLine> lines = null;
	
	private ParkourManager() { lines = new HashMap<UUID, ParkourLine>(); }
	
	public static ParkourManager getInstance()
	{
		if(instance == null)
			instance = new ParkourManager();
		return instance;
	}
	
	public void addLine(Player p)
	{
		if(lines.containsKey(p.getUniqueId()))
		{
			lines.get(p.getUniqueId()).clear();
			lines.remove(p.getUniqueId());
		}
		
		lines.put(p.getUniqueId(), new ParkourLine(p));
	}
	public boolean hasLine(Player p) { return lines.containsKey(p.getUniqueId()); }
	
	public ParkourLine getLine(Player p) { return lines.get(p.getUniqueId()); }
	
	public void removeLine(Player p)
	{
		p.sendMessage("You lose...");
		lines.get(p.getUniqueId()).clear();
		lines.remove(p.getUniqueId());
	}
}