package jotato.quantumflux.fmp;

import jotato.quantumflux.blocks.BlockRFExciter;
import jotato.quantumflux.blocks.ModBlocks;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;

//thanks to tterrag https://github.com/TheCricket/Chisel-2/commit/fd33a66d18013ae7742b0599ce96e562070333d2
public class FMPIntegration implements IPartFactory, IPartConverter
{

	public void init()
	{
		MultiPartRegistry.registerConverter(this);
		MultiPartRegistry.registerParts(this, new String[] { "quantumflux_rfExciter" });
	}

	@Override
	public Iterable<Block> blockTypes()
	{
		return Lists.newArrayList((Block) ModBlocks.rfExciter);
	}

	@Override
	public TMultiPart convert(World world, BlockCoord coord)
	{
		Block block = world.getBlock(coord.x, coord.y, coord.z);
		if (block instanceof BlockRFExciter)
		{
			return new PartRFExciter(world.getBlockMetadata(coord.x, coord.y, coord.z));
		}
		return null;
	}

	@Override
	public TMultiPart createPart(String type, boolean client)
	{
		if (type.equals("quantumflux_rfExciter"))
		{
			return new PartRFExciter(0);
		}
		return null;
	}

}
