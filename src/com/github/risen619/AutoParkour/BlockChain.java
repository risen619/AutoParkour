package com.github.risen619.AutoParkour;

import java.util.ArrayList;
import java.util.List;

public class BlockChain
{
	private List<AllowedBlock> blocks = new ArrayList<>();
	
	public List<AllowedBlock> blocks() { return blocks; }
	
	public BlockChain() { }
	
	public void add(AllowedBlock ab) { blocks.add(ab); }
	
	@Override
	public String toString()
	{
		String result = "[";
		for(int i=0; i<blocks.size(); i++)
			result += blocks.get(i).toString() + (i == blocks.size()-1 ? "]" : ", ");
		
		return result;
	}
}