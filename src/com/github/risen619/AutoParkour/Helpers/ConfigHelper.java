package com.github.risen619.AutoParkour.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.risen619.AutoParkour.AllowedBlock;
import com.github.risen619.AutoParkour.BlockChain;
import com.github.risen619.AutoParkour.Main;

public class ConfigHelper
{
	private Main plugin;
	
	public ConfigHelper()
	{
		plugin = Main.getPlugin(Main.class);
		plugin.reloadConfig();
	}
	
	public Location getLocation(ConfigurationSection cs, String key) { return getLocation(cs, key, "world"); }
	
	public Location getLocation(String section, String key)
	{
		return getLocation(plugin.getConfig().getConfigurationSection(section), key, "world");
	}
	
	public Location getLocation(String section, String key, String world)
	{
		return getLocation(plugin.getConfig().getConfigurationSection(section), key, world);
	}
	
	public Location getLocation(ConfigurationSection cs, String key, String defaultWorld)
	{
		Map<String, Object> map = cs.getConfigurationSection(key).getValues(false);
		String world = (String) map.get("world");
		int x = (Integer)map.get("x");
		int y = (Integer)map.get("y");
		int z = (Integer)map.get("z");
		
		return new Location(plugin.getServer().getWorld(world == null ? defaultWorld : world), x, y, z);
	}
	
	public void setLocation(String path, Location l)
	{
		FileConfiguration fc = plugin.getConfig();
		fc.set(path + ".world", l.getWorld().getName());
		fc.set(path + ".x", l.getBlockX());
		fc.set(path + ".y", l.getBlockY());
		fc.set(path + ".z", l.getBlockZ());
		System.out.println(fc.get(path).toString());
		plugin.saveConfig();
	}
	
	public List<BlockChain> platformList(String section, String key)
	{
		return platformList(plugin.getConfig().getConfigurationSection(section), key);
	}
	
	public List<BlockChain> platformList(ConfigurationSection cs, String key)
	{
		Pattern pattern = Pattern.compile("^(\\d+:?\\d+);?(\\d+:?\\d+)?$");
		List<String> list = cs.getStringList(key);
		List<BlockChain> result = new ArrayList<>();
		
		for(String l : list)
		{
			if(pattern.matcher(l).matches())
			{
				BlockChain bc = new BlockChain();
				
				String[] parts = l.split(";");
				for(String part : parts)
				{
					String[] blockData = part.split(":", 2);
					int type = Integer.parseInt(blockData[0]);
					Byte data = blockData.length > 1 ? Byte.parseByte(blockData[1]) : null;
					bc.add(new AllowedBlock(type, data));
				}
				
				result.add(bc);
			}
		}
		
		System.out.println(result.toString());
		return result;
	}
	
	public void checkAndFix()
	{
		Set<String> defaults = plugin.getConfig().getDefaults().getKeys(false);
		Set<String> current = plugin.getConfig().getKeys(false);
		boolean removal = defaults.removeAll(current);
		if(!removal || (removal && defaults.size() > 0))
		{
			System.out.println(defaults.toString());
			for(String def : defaults)
			{
				System.out.println(def);
				 ConfigurationSection cs = plugin.getConfig().getDefaults().getConfigurationSection(def);
				 if(cs != null)
				 {
					 Map<String, Object> map = cs.getValues(false);
					 plugin.getConfig().set(def, map);
				 }
			}
			plugin.saveConfig();
		}
	}
}