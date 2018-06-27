package com.github.risen619.AutoParkour;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.github.risen619.AutoParkour.Helpers.LocationHelpers;
import com.github.risen619.AutoParkour.Helpers.MathHelpers;

public class ParkourLine
{	
	private Player player;
	private BlockChain blocks;
	
	private Location start;
	private Location pos1;
	private Location pos2;
	
	private Block currentBlock;
	private Block nextBlock;
	
	public Block currentBlock() { return currentBlock; }
	public Block nextBlock() { return nextBlock; }
	
	public int yOffset() { return blocks.blocks().size(); }
	
	public ParkourLine(Player p) { this(p, null, null); }
	
	public ParkourLine(Player p, Location pos1, Location pos2)
	{
		ParkourManager pm = ParkourManager.getInstance();
		
		player = p;
		
		blocks = pm.allowedChains().get(MathHelpers.randomBetween(0, pm.allowedChains().size()));
		for(AllowedBlock ab : blocks.blocks())
			ab.data((ab.allowedAll() || ab.data() == null) ? (byte)MathHelpers.randomBetween(0, 16) : ab.data());
		
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
			start = pm.respawnLocation().clone();
		}
		
		currentBlock = LocationHelpers.randomPoint(pos1, pos2).getBlock();
		setBlock(currentBlock, blocks, true);
		
		player.teleport(currentBlock.getLocation().add(0.5, 1, 0.5));
		spawnNext();
	}
	
	public void clear()
	{
		for(int i=0; i<blocks.blocks().size(); i++)
		{
			currentBlock.getLocation().clone().subtract(0, i, 0).getBlock().setType(Material.AIR);
			nextBlock.getLocation().clone().subtract(0, i, 0).getBlock().setType(Material.AIR);	
		}
		player.teleport(start);
	}
	
	private void spawnNext()
	{
		double rx, ry, rz;
		int i = 0;
		Location tmp;
		do
		{	
			ry = MathHelpers.randomBetween(-2, 2);
			double max = ry > 0 ? 4 : (3 - ry);
			rx = MathHelpers.randomBetween(2, max) * (Math.random() > 0.5 ? -1 : 1);
			rz = MathHelpers.randomBetween(2, max) * (Math.random() > 0.5 ? -1 : 1);
			if(rx != 0)
				rz = Math.random() > 0.75 ? 0 : rz;

			tmp = currentBlock.getLocation().clone().add(rx, ry, rz);
			i++;
		} while(!LocationHelpers.isInArea(pos1, pos2, tmp) && i <= 100);
		
		nextBlock = currentBlock.getLocation().add(rx, ry, rz).getBlock();
		setBlock(nextBlock, blocks, false);
	}
	
	public void proceed()
	{
		for(int i=0; i<blocks.blocks().size(); i++)
			currentBlock.getLocation().clone().subtract(0, i, 0).getBlock().setType(Material.AIR);
		
		currentBlock = nextBlock;
		spawnNext();
	}
	
	@SuppressWarnings("deprecation")
	private void setBlock(Block b, BlockChain bc, boolean noEffect)
	{
		for(int i=blocks.blocks().size()-1; i>=0; i--)
		{
			b.setType(blocks.blocks().get(i).material());
			b.setData(blocks.blocks().get(i).data());
			b = b.getLocation().subtract(0, 1, 0).getBlock();
		}
		
		if(!noEffect)
		{
			b.getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 10);
			b.getWorld().playSound(b.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 25, 1);
		}
	}
	
}