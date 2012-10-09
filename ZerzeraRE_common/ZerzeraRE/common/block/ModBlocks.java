package ZerzeraRE.common.block;

import cpw.mods.fml.common.registry.GameRegistry;

public abstract class ModBlocks {

	public static void init(){
		// -- Registry
		GameRegistry.registerBlock( new BlockREbench() );
		
	}
}