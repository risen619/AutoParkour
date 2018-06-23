package com.github.risen619.AutoParkour;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ParkourLine
{
	private Player player;
	private Material block;
	private byte blockData;
	
	private Location pos1;
	private Location pos2;
	
	private Block currentBlock;
	private Block nextBlock;
	
	public Block currentBlock() { return currentBlock; }
	public Block nextBlock() { return nextBlock; }
	
	public ParkourLine(Player p)
	{
		player = p;
		block = Material.WOOL;
		blockData = (byte)((Math.random() * 100) % 16);
		currentBlock = p.getLocation().getBlock();
		
		pos1 = p.getLocation().subtract(20, 0, 20);
		pos1 = p.getWorld().getHighestBlockAt(pos1).getLocation().add(0, 5, 0);
		pos2 = pos1.clone().add(41, 21, 41);
		
		int rx = pos1.getBlockX() + ((int)(Math.random() * 10000) % (pos2.getBlockX() - pos1.getBlockX()));
		int ry = pos1.getBlockY() + ((int)(Math.random() * 10000) % (pos2.getBlockY() - pos1.getBlockY()));
		int rz = pos1.getBlockZ() + ((int)(Math.random() * 10000) % (pos2.getBlockZ() - pos1.getBlockZ()));
		
		currentBlock = p.getWorld().getBlockAt(rx, ry, rz);

		setBlock(currentBlock, block, blockData, true);
		
		player.teleport(currentBlock.getLocation().add(0, 1, 0));
		spawnNext();
	}
	
	public void clear()
	{
		currentBlock.setType(Material.AIR);
		nextBlock.setType(Material.AIR);
	}
	
	private void spawnNext()
	{
		int rx, ry, rz, i = 0;
		Location tmp;
		do
		{
			rx = (int)(Math.random() * 10 % 2 + 2) * (Math.random() > 0.5 ? -1 : 1);
			ry = 1 - ((int)(Math.random() * 10) % 3);
			rz = (int)(Math.random() * 10 % 2 + 2) * (Math.random() > 0.5 ? -1 : 1);
			if(rx != 0)
				rz = Math.random() > 0.75 ? 0 : rz;

			tmp = currentBlock.getLocation().add(rx, ry, rz);
			i++;
			
		} while(!tmp.toVector().isInAABB(pos1.toVector(), pos2.toVector()) && i <= 10);
				
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