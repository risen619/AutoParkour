package com.github.risen619.AutoParkour;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.risen619.AutoParkour.Executors.ReloadExecutor;
import com.github.risen619.AutoParkour.Executors.RespawnExecutor;
import com.github.risen619.AutoParkour.Executors.StartExecutor;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		ParkourManager.server(getServer());
		ParkourManager.getInstance();
		
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		
		getCommand("apstart").setExecutor(new StartExecutor());
		getCommand("apreload").setExecutor(new ReloadExecutor());
		getCommand("aprespawn").setExecutor(new RespawnExecutor());
		
		System.out.println("AutoParkour enabled!");
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("AutoParkour disabled!");
	}
}