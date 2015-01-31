package jotato.quantumflux.gui;

import jotato.quantumflux.inventory.ContainerEntropyAccelerator;
import jotato.quantumflux.tileentity.TileEntityEntropyAccelerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class QFGuiHandler implements IGuiHandler
{

    public enum GUI
    {
        INCINERATOR(0);

        public int ordinal;

        private GUI(int id)
        {
            this.ordinal = id;
        }

        private static GUI[] allValues = values();

        public static GUI fromOrdinal(int n)
        {
            return allValues[n];
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        GUI gui = GUI.fromOrdinal(ID);

        if (gui == GUI.INCINERATOR)
        {
            TileEntityEntropyAccelerator incinerator = (TileEntityEntropyAccelerator) world.getTileEntity(x, y, z);
            return new ContainerEntropyAccelerator(player.inventory, incinerator);
        }
        return null;

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        GUI gui = GUI.fromOrdinal(ID);

        if (gui == GUI.INCINERATOR)
        {
            TileEntityEntropyAccelerator incinerator = (TileEntityEntropyAccelerator) world.getTileEntity(x, y, z);
            return new GuiEntropyAccelerator(player.inventory,incinerator);
        }

        return null;
    }

}
