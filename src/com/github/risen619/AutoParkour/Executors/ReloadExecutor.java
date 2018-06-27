package com.github.risen619.AutoParkour.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.risen619.AutoParkour.ParkourManager;

public class ReloadExecutor implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args)
	{
		ParkourManager.getInstance().loadConfig();
		return true;
	}

}
