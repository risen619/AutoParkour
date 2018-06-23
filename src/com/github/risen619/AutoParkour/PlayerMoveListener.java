package com.github.risen619.AutoParkour;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener
{
	public PlayerMoveListener() { }
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		ParkourManager pm = ParkourManager.getInstance();
		if(!pm.hasLine(e.getPlayer())) return;
		ParkourLine pl = pm.getLine(e.getPlayer());
		Location l = e.getPlayer().getLocation();
//		e.getPlayer().sendMessage(pl.nextBlock().getLocation().toString() + " : ");
		
		if(l.subtract(0, 1, 0).getBlock().equals(pl.nextBlock()))
			pl.proceed();
		
		if(l.getY() < pl.currentBlock().getY() && l.getY() < pl.nextBlock().getY())
			pm.removeLine(e.getPlayer());
		
	}
}