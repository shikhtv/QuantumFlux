package jotato.quantumflux.fmp;

import jotato.quantumflux.blocks.BlockRFExciter;
import jotato.quantumflux.blocks.ModBlocks;
import net.minecraft.block.Block;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;


public class PartRFExciter extends McMetaPart
{

	public PartRFExciter(int meta){
		super(meta);
	}

	@Override
	public Cuboid6 getBounds()
	{
		return new Cuboid6(BlockRFExciter.getBlockBoundsForState(this.meta));
	}

	@Override
	public Block getBlock()
	{
		return ModBlocks.rfExciter;
	}
	@Override
	public boolean doesTick()
	{
		return true;
	}
	
	@Override
	public void update()
	{
		//todo: do the rf stuff. will need to pull out the logic from the tile entity and make it reusable for here
	}

	@Override
	public String getType()
	{
		return "quantumflux_rfExciter";
	}
}
