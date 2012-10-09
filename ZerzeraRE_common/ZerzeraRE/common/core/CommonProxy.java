package ZerzeraRE.common.core;

import java.util.logging.Logger;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

import ZerzeraRE.client.gui.GuiREbench;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;
import ZerzeraRE.common.container.ContainerREbench;

public class CommonProxy implements IGuiHandler {
	
	@SidedProxy(clientSide="ZerzeraRE.client.core.ClientProxy", serverSide="ZerzeraRE.common.core.CommonProxy")
	public static CommonProxy proxy;
	public static Logger log = ZerzeraRE.common.ZerzeraRE.log;
	
	public String getMinecraftVersion() { return "1.3.2"; }
	
	public void initRendering() {}
	
	/* REGISTRATION */
    public void initRenderBlocks() {}
	
	/* LOCALIZATION */
	public void addName(Object obj, String s) {}
	
	public void initializeTileEntities() {
    	// TODO: Constant
    	GameRegistry.registerTileEntity(TileREbench.class, "tileREbench");
	}

	@Override
	public Object getServerGuiElement(int GUID, EntityPlayer player, World world, int x, int y, int z) {
		if(GUID == DefaultProps.RE_BENCH_GUID)
		{
			return new ContainerREbench(player.inventory, (TileREbench) world.getBlockTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int GUID, EntityPlayer player, World world, int x, int y, int z) {
		if(GUID == DefaultProps.RE_BENCH_GUID)
		{
			return new GuiREbench(player, (TileREbench) world.getBlockTileEntity(x, y, z) );
		}
		return null;
	}


}
