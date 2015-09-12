package jotato.quantumflux.machine.cluster;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jotato.quantumflux.redflux.ISetEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityQuibitCluster extends TileEntity implements IEnergyHandler, ISetEnergy {
	protected EnergyStorage localEnergyStorage;
	private int transferRate;
	private int capacity;
	public int level;

	public TileEntityQuibitCluster(QuibitClusterSettings settings) {
		
		this.transferRate = settings.transferRate;
		this.capacity = settings.capacity;
		this.level = settings.level;
		
		localEnergyStorage = new EnergyStorage(this.capacity, this.transferRate);
	}
	
	public TileEntityQuibitCluster(){}

	protected EnergyStorage getEnergyDevice() {
		return localEnergyStorage;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	public int getEnergyTransferRate() {
		return this.transferRate;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {

		int used = getEnergyDevice().receiveEnergy(maxReceive, simulate);
		if (used > 0 && !simulate) {
			this.markDirty();
		}
		return used;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		int given = getEnergyDevice().extractEnergy(maxExtract, simulate);

		if (given > 0 && !simulate) {
			this.markDirty();
		}
		return given;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return getEnergyDevice().getEnergyStored();
	}

	public void setEnergyStored(int energy) {
		this.markDirty();
		this.getEnergyDevice().setEnergyStored(energy);
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getEnergyDevice().getMaxEnergyStored();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound energyTag = new NBTTagCompound();
		this.getEnergyDevice().writeToNBT(energyTag);
		tag.setTag("Energy", energyTag);
		tag.setInteger("XferRate", this.transferRate);
		tag.setInteger("Capacity", this.capacity);
		tag.setInteger("Level", this.level);

	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		this.level = tag.getInteger("Level");
		this.capacity = tag.getInteger("Capacity");
		this.transferRate = tag.getInteger("XferRate");
		
		if(this.getEnergyDevice()==null){
			this.localEnergyStorage = new EnergyStorage(capacity,transferRate);
		}
		
		NBTTagCompound energyTag = tag.getCompoundTag("Energy");
		this.getEnergyDevice().readFromNBT(energyTag);
	}

	@SideOnly(Side.CLIENT)
	public int getBufferScaled(int scale) {
		double stored = getEnergyStored(null);
		double max = getMaxEnergyStored(null);
		double v = ((stored / max) * scale);
		return (int) v;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int targetX = xCoord + dir.offsetX;
			int targetY = yCoord + dir.offsetY;
			int targetZ = zCoord + dir.offsetZ;

			TileEntity tile = worldObj.getTileEntity(targetX, targetY, targetZ);
			// todo: make configurable sides for the cluster
			if (tile instanceof TileEntityQuibitCluster)
				return;
			if (tile instanceof IEnergyReceiver) {
				IEnergyReceiver receiver = (IEnergyReceiver) tile;

				if (receiver.canConnectEnergy(dir.getOpposite())) {
					int tosend = localEnergyStorage.extractEnergy(transferRate,
							true);
					int used = ((IEnergyReceiver) tile).receiveEnergy(
							dir.getOpposite(), tosend, false);
					if (used > 0) {
						this.markDirty();
					}
					localEnergyStorage.extractEnergy(used, false);
				}

			}

		}
		super.updateEntity();
	}
}
