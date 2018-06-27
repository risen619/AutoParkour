package com.github.risen619.AutoParkour;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.risen619.AutoParkour.Helpers.LocationHelpers;

public class PlayerMoveListener implements Listener
{
	public PlayerMoveListener() { }
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		ParkourManager pm = ParkourManager.getInstance();
		if(!pm.hasLine(e.getPlayer()))
		{
			Location to = e.getTo().clone().subtract(0, 1, 0);
			Location s = pm.startLocation();
			
			if(to.getBlockX() == s.getBlockX() && to.getBlockY() == s.getBlockY() && to.getBlockZ() == s.getBlockZ())
				pm.addLine(e.getPlayer(), true);
			return;
		}
		ParkourLine pl = pm.getLine(e.getPlayer());
		Location l = e.getPlayer().getLocation();
		
		if(l.getY() < (Integer.min(pl.currentBlock().getY(), pl.nextBlock().getY()) - pl.yOffset()))
			pm.removeLine(e.getPlayer());
		
		if(LocationHelpers.isAbove(l, pl.nextBlock().getLocation()))
			pl.proceed();
		
	}
}