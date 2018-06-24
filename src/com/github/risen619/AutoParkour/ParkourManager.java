package com.github.risen619.AutoParkour;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;


public class ParkourManager
{
	private static ParkourManager instance = null;
	private static Server server = null;
	
	private HashMap<UUID, ParkourLine> lines = null;
	private Location startLocation = null;
	private Location position1 = null;
	private Location position2 = null;
	
	public Location startLocation() { return startLocation; }
	public Location position1() { return position1; }
	public Location position2() { return position2; }
	
	private ParkourManager()
	{
		lines = new HashMap<UUID, ParkourLine>();
		prepareConfig();
	}
	
	private void prepareConfig()
	{
		Main plugin = Main.getPlugin(Main.class);
		File folder = new File(plugin.getDataFolder(), "config.yml");
		
		if(!folder.exists())
			plugin.saveDefaultConfig();
		
		Map<String, Object> map = plugin.getConfig().getConfigurationSection("start").getValues(false);
		String world = (String) map.get("world");
		int x = (Integer)map.get("x");
		int y = (Integer)map.get("y");
		int z = (Integer)map.get("z");
		startLocation = server().getWorld(world).getBlockAt(x, y, z).getLocation();
		
		map = plugin.getConfig().getConfigurationSection("position1").getValues(false);
		x = (Integer)map.get("x");
		y = (Integer)map.get("y");
		z = (Integer)map.get("z");
		position1 = server().getWorld(world).getBlockAt(x, y, z).getLocation();
		
		map = plugin.getConfig().getConfigurationSection("position2").getValues(false);
		x = (Integer)map.get("x");
		y = (Integer)map.get("y");
		z = (Integer)map.get("z");
		position2 = server().getWorld(world).getBlockAt(x, y, z).getLocation();
	}
	
	public static ParkourManager getInstance()
	{
		if(instance == null)
			instance = new ParkourManager();
		return instance;
	}
	
	public static Server server() { return server; }
	public static void server(Server s) { server = s; } 
	
	public void addLine(Player p, boolean isStarter)
	{
		if(lines.containsKey(p.getUniqueId()))
		{
			lines.get(p.getUniqueId()).clear();
			lines.remove(p.getUniqueId());
		}
		
		if(!isStarter)
			lines.put(p.getUniqueId(), new ParkourLine(p));
		else lines.put(p.getUniqueId(), new ParkourLine(p, position1, position2));
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