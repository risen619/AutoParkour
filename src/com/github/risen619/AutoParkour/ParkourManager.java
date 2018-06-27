package com.github.risen619.AutoParkour;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.github.risen619.AutoParkour.Helpers.ConfigHelper;
import com.github.risen619.AutoParkour.Helpers.LocationHelpers;


public class ParkourManager
{
	private static ParkourManager instance = null;
	private static Server server = null;
	
	private HashMap<UUID, ParkourLine> lines = null;
	
	private Location startLocation = null;
	private Location respawnLocation = null;
	private Location position1 = null;
	private Location position2 = null;
	
	private List<BlockChain> allowedChains = null;
	
	public Location startLocation() { return startLocation; }
	public Location respawnLocation() { return respawnLocation; }
	public Location position1() { return position1; }
	public Location position2() { return position2; }
	public List<BlockChain> allowedChains() { return allowedChains; }
	
	private ParkourManager()
	{
		lines = new HashMap<UUID, ParkourLine>();
		loadConfig();
	}
	
	public void loadConfig()
	{
		Main plugin = Main.getPlugin(Main.class);
		File folder = new File(plugin.getDataFolder(), "config.yml");
		ConfigHelper ch = new ConfigHelper();
		
		if(!folder.exists())
			plugin.saveDefaultConfig();
		else ch.checkAndFix();
		
		startLocation = ch.getLocation("position", "start");
		position1 = ch.getLocation("position", "position1", startLocation.getWorld().getName());
		position2 = ch.getLocation("position", "position2", startLocation.getWorld().getName());
		respawnLocation = LocationHelpers.lookAt(ch.getLocation("position", "respawn").add(0.5, 0, 0.5), startLocation);
		allowedChains = ch.platformList("blocks", "platform");
		
		System.out.println(respawnLocation);
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