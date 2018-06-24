package com.github.risen619.AutoParkour;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ParkourLine
{
	private Player player;
	private Material block;
	private byte blockData;
	
	private Location start;
	private Location pos1;
	private Location pos2;
	
	private Block currentBlock;
	private Block nextBlock;
	
	public Block currentBlock() { return currentBlock; }
	public Block nextBlock() { return nextBlock; }
	
	public ParkourLine(Player p) { this(p, null, null); }
	
	public ParkourLine(Player p, Location pos1, Location pos2)
	{
		player = p;
		block = Material.WOOL;
		blockData = (byte)((Math.random() * 100) % 16);
		currentBlock = p.getLocation().getBlock();
		
		if(pos1 == null || pos2 == null)
		{
			pos1 = p.getLocation().clone().subtract(20, 0, 20);
			pos1 = p.getWorld().getHighestBlockAt(pos1).getLocation().add(0, 5, 0);
			pos2 = pos1.clone().add(41, 21, 41);
			start = p.getLocation();
		}
		else
		{
			this.pos1 = pos1.clone();
			this.pos2 = pos2.clone();
			start = p.getLocation().clone().subtract(2, 0, 0);
		}
		
		int rx = randomBetween(pos1.getBlockX(), pos2.getBlockX());
		int ry = randomBetween(pos1.getBlockY(), pos2.getBlockY());
		int rz = randomBetween(pos1.getBlockZ(), pos2.getBlockZ());
		
		currentBlock = p.getWorld().getBlockAt(rx, ry, rz);
		pos1.getBlock().setType(Material.SEA_LANTERN);
		pos2.getBlock().setType(Material.SEA_LANTERN);

		setBlock(currentBlock, block, blockData, true);
		
		player.teleport(currentBlock.getLocation().add(0, 1, 0));
		spawnNext();
	}
	
	public int randomBetween(int a, int b)
	{
		if(a == b) return a;
		if(a > b)
			return b + ((int)(Math.random() * 1000000) % (a-b));
		else return a + ((int)(Math.random() * 1000000) % (b-a));
	}
	
	public void clear()
	{
		pos1.getBlock().setType(Material.AIR);
		pos2.getBlock().setType(Material.AIR);
		currentBlock.setType(Material.AIR);
		nextBlock.setType(Material.AIR);
		player.teleport(start);
	}
	
	private boolean isInArea(Location a, Location b, Location l)
	{
		Vector min = Vector.getMinimum(a.toVector(), b.toVector());
		Vector max = Vector.getMaximum(a.toVector(), b.toVector());
		Vector o = l.toVector();
		return (o.getX() >= min.getX() && o.getX() <= max.getX()) &&
				(o.getY() >= min.getY() && o.getY() <= max.getY()) &&
				(o.getZ() >= min.getZ() && o.getZ() <= max.getZ());
	}
	
	private void spawnNext()
	{
		int rx, ry, rz, i = 0;
		Location tmp;
		do
		{	
			ry = randomBetween(-2, 2);
			int max = ry > 0 ? 4 : (3 - ry);
			rx = randomBetween(2, max) * (Math.random() > 0.5 ? -1 : 1);
			rz = randomBetween(2, max) * (Math.random() > 0.5 ? -1 : 1);
			if(rx != 0)
				rz = Math.random() > 0.75 ? 0 : rz;

			tmp = currentBlock.getLocation().clone().add(rx, ry, rz);
			i++;
		} while(!isInArea(pos1, pos2, tmp) && i <= 1000);
		
		nextBlock = currentBlock.getLocation().add(rx, ry, rz).getBlock();
		setBlock(nextBlock, block, blockData, false);
	}
	
	public void proceed()
	{
		currentBlock.setType(Material.AIR);
		currentBlock = nextBlock;
		spawnNext();
	}
	
	@SuppressWarnings("deprecation")
	private void setBlock(Block b, Material m, byte data, boolean noEffect)
	{
		b.setType(m);
		b.setData(data);
		
		if(!noEffect)
		{
			b.getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 10);
			b.getWorld().playSound(b.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0f, data / 16.0f);
		}
	}
	
}