package ZerzeraRE.common.block;

import java.util.logging.Logger;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.Property;
import net.minecraftforge.common.Configuration;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;

public class BlockREbench extends ModdedBlock {
	static int 		REbenchId 			= ZerzeraRE.REConf.getOrCreateIntProperty("re_bench.id",    Configuration.CATEGORY_ITEM, DefaultProps.RE_BENCH_ID    ).getInt();
	static Property blockIndexInTexture = ZerzeraRE.REConf.getOrCreateIntProperty("re_bench.gfxid", Configuration.CATEGORY_ITEM, DefaultProps.RE_BENCH_GFX_ID);
	static Logger   log					= ZerzeraRE.log;
	
	static int      textureBottom		= 0;
	static int      textureTop			= 1;
	static int      textureSide			= 0 + 2;
	static int      textureBack			= 1 + 2;
	static int      textureFront		= 16;
	
	public BlockREbench () {
		super( REbenchId , Material.iron);
		
		this.textureFile = DefaultProps.TEXTURE_BLOCKS;
		this.GUID        = DefaultProps.RE_BENCH_GUID;
		
		setHardness(5F);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockName( DefaultProps.RE_BENCH_NAME );
		
		LanguageRegistry.addName(this,  DefaultProps.RE_BENCH_FULLNAME);
	}
	
	@Override
	public int getRenderType() {
		return DefaultProps.RENDER_RE_BENCH_ID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
        return false;
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving) {
		super.onBlockPlacedBy(world, x, y, z, entityliving);
		
		// -- Current direction the player is facing while placing the block ( f variable if you press F3 )
		int f = MathHelper.floor_double( (double) ( entityliving.rotationYaw * 4.0F / 360.0F )  + 0.5D) & 3 ;
		world.setBlockMetadataWithNotify(x, y, z, f );
	}
	
	@Override
	public void updateBlockMetadata(World world, int x, int y, int z, int sidePlacedAgainst, float par6, float par7, float par8) {
		super.updateBlockMetadata(world, x, y, z, sidePlacedAgainst, par6, par7, par8);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
		int m = iblockaccess.getBlockMetadata(x,y,z);
		return getBlockTextureFromSideAndMetadata(side, m);
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int faceDir){
		// faceDir = 0 - South
		// faceDir = 1 - West
		// faceDir = 2 - North
		// faceDir = 3 - East
		
		// EastFace is the front one
		if(faceDir == 0)
		{
			switch( side )
			{
				case 2: return textureFront; // Highres East texture
				case 3: return textureBack;  // Lowres  West texture
				case 4: 
				case 5: return textureSide;  // Lowres  North/South texture
			}
		}
		
		// SouthFace is the front one
		if(faceDir == 1)
		{
			switch( side )
			{
				case 2: 
				case 3: return textureSide;  // Lowres  East/West texture
				case 4: return textureBack;  // Lowres  North texture
				case 5: return textureFront; // Highres South texture
			}
		}
		
		// WestFace is the front one
		if(faceDir == 2)
		{
			switch( side )
			{
				case 2: return textureBack;  // Lowres East texture
				case 3: return textureFront; // Highres  West texture
				case 4: 
				case 5: return textureSide;  // Lowres  North/South texture
			}
		}	
		
		// NorthFace is the front one
		if(faceDir == 3)
		{
			switch( side )
			{
				case 2: 
				case 3: return textureSide;  // Lowres  East/West texture
				case 4: return textureFront; // Highres  North texture
				case 5: return textureBack;  // Lowres South texture
			}
		}	
		
		// -- Lowres top and bottom texture
		switch(side)
		{
			case 0: return textureBottom; // Bottom
			case 1: return textureTop;    // Top
		}
		return 255;
	}
	
	@Override
	public int getBlockTextureFromSide(int side) {
		switch( side ){
			case 0: return textureBottom;
			case 1: return textureTop;
			case 2: return textureFront;
			case 3: return textureBack;
		}
		return textureSide;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) { return null; }
}
