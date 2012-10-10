package ZerzeraRE.common.block;

import cpw.mods.fml.common.registry.GameRegistry;

import ZerzeraRE.common.lib.DefaultProps;

public abstract class ModBlocks {

	public static void init(){
		// -- Registry
		GameRegistry.registerBlock( new BlockREbench(false) );
	}
}