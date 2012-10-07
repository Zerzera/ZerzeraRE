package ZerzeraRE.common.block;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;

public class BlockREbench extends ModdedBlock {
	static int 		REbenchId 			= ZerzeraRE.REConf.getOrCreateIntProperty("re_bench.id", Configuration.CATEGORY_ITEM, DefaultProps.RE_BENCH_ID).getInt();
	static Property blockIndexInTexture = ZerzeraRE.REConf.getOrCreateIntProperty("re_bench.gfxid", Configuration.CATEGORY_ITEM, DefaultProps.RE_BENCH_GFX_ID);
	
	public BlockREbench () {
		super( REbenchId , Material.iron);
		setHardness(5F);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName( DefaultProps.RE_BENCH_NAME );
		
		LanguageRegistry.addName(this,  DefaultProps.RE_BENCH_FULLNAME);
		
//		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
//		ZerzeraRE.log.info(new String(this.maxX + " " + this.maxY + " " + this.maxZ));
	}
	@Override
	public int getRenderType() {
//		ZerzeraRE.log.info("Rendering with ID: " + DefaultProps.RENDER_RE_BENCH_ID);
//		return DefaultProps.RENDER_RE_BENCH_ID;
		return 0;
	}
	public boolean renderAsNormalBlock()
    {
        return false;
    }

	
	@Override
    public int getBlockTextureFromSide(int par1)
    {
        return blockIndexInTexture.getInt();
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
