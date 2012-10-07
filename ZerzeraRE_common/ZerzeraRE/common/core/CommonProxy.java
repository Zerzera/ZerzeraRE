package ZerzeraRE.common.core;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

import ZerzeraRE.common.block.ModBlocks;
import ZerzeraRE.common.tile.TileREbench;

public class CommonProxy implements IGuiHandler {
	
	@SidedProxy(clientSide="ZerzeraRE.client.core.ClientProxy", serverSide="ZerzeraRE.common.core.CommonProxy")
	public static CommonProxy proxy;

	public String getMinecraftVersion() { return "1.3.2"; }
	
	public void initRenderingAndTextures() {}
	
	/* REGISTRATION */
    public void initTileEntities() {	
//    	GameRegistry.registerTileEntity(TileREbench.class, "tileREbench");
    }
	
	/* LOCALIZATION */
	public void addName(Object obj, String s) {}
	
	public void initializeTileEntities() {}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}


}
