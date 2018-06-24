package com.github.risen619.AutoParkour;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		ParkourManager.server(getServer());
		ParkourManager.getInstance();
		
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		
		System.out.println("AutoParkour enabled!");
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("AutoParkour disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args)
	{
		if(s instanceof Player)
			ParkourManager.getInstance().addLine((Player)s, false);
		
		return true;
	}
}