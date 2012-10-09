package ZerzeraRE.common.core;

import java.util.logging.Logger;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.IGuiHandler;

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
	
	public void initializeTileEntities() {}

	@Override
	public Object getServerGuiElement(int GUID, EntityPlayer player, World world, int x, int y, int z) {
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int GUID, EntityPlayer player, World world, int x, int y, int z) {
		
		return null;
	}


}
