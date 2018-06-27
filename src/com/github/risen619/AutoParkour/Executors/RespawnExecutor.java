package com.github.risen619.AutoParkour.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.risen619.AutoParkour.ParkourManager;
import com.github.risen619.AutoParkour.Helpers.ConfigHelper;

public class RespawnExecutor implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args)
	{
		if(s instanceof Player)
		{
			new ConfigHelper().setLocation("position.respawn", ((Player)s).getLocation());
			ParkourManager.getInstance().loadConfig();
		}
		else s.sendMessage("You must be a player to use this command!");
		return true;
	}
	
}