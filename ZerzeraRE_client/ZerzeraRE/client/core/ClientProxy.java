package ZerzeraRE.client.core;

import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;
import ZerzeraRE.common.core.CommonProxy;
import ZerzeraRE.render.RenderREbench;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void initRenderingAndTextures () {
		DefaultProps.RENDER_RE_BENCH_ID = RenderingRegistry.getNextAvailableRenderId();
		
		MinecraftForgeClient.preloadTexture(DefaultProps.TEXTURE_BLOCKS);
	}
	
    @Override
    public void initTileEntities() {
    	super.initTileEntities();
    	
		RenderingRegistry.registerBlockHandler( DefaultProps.RENDER_RE_BENCH_ID, new RenderREbench() );
		
//    	ClientRegistry.bindTileEntitySpecialRenderer(TileREbench.class, new RenderREbench());
    }
}
