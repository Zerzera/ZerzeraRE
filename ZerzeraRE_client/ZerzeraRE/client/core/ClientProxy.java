package ZerzeraRE.client.core;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import ZerzeraRE.client.render.RenderREbench;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;
import ZerzeraRE.common.core.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRendering() {
		DefaultProps.RENDER_RE_BENCH_ID = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.preloadTexture(DefaultProps.TEXTURE_BLOCKS);
	}
	
    @Override
    public void initRenderBlocks() {
    	super.initRenderBlocks();
		RenderingRegistry.registerBlockHandler( DefaultProps.RENDER_RE_BENCH_ID, new RenderREbench() );
    }
    
	@Override
	public void initializeTileEntities() {
		super.initializeTileEntities();
//		ClientRegistry.bindTileEntitySpecialRenderer(TileREbench.class, new RenderREbench());
	}

}
