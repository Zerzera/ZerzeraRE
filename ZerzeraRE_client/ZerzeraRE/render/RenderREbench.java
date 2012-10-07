package ZerzeraRE.render;

import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TileEntitySpecialRenderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;

public class RenderREbench implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess iblockaccess, int x, int y, int z, Block block, int l, RenderBlocks renderblocks) {
		
		/*
		renderblocks.renderBlockRepeater(block, x, y, z);
		int var5 = block.colorMultiplier(iblockaccess, x, y, z);
        float var6 = (float)(var5 >> 16 & 255) / 255.0F;
        float var7 = (float)(var5 >> 8 & 255) / 255.0F;
        float var8 = (float)(var5 & 255) / 255.0F;
		renderblocks.renderStandardBlockWithColorMultiplier(block, x, y, z, var6, var7, var8);
		*/
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return DefaultProps.RENDER_RE_BENCH_ID;
	}
}
