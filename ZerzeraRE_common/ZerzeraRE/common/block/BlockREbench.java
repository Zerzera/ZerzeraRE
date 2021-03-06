package ZerzeraRE.common.block;

import java.util.logging.Logger;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraftforge.common.ForgeDirection;
import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;

public class BlockREbench extends ModdedBlock {
	private final boolean isActive;
	private static boolean keepInventory = false;
	
	static int 		REbenchId 			= ZerzeraRE.REConf.getBlock( "re_bench.id", DefaultProps.RE_BENCH_ID ).getInt();
	static int 		blockIndexInTexture = DefaultProps.RE_BENCH_GFX_ID;
	static Logger   log					= ZerzeraRE.log;
	
	static int      textureBottom		= 0;
	static int      textureTop			= 1;
	static int      textureSide			= 0 + 2;
	static int      textureBack			= 1 + 2;
	static int      textureFront		= 16;

	protected BlockREbench (boolean active) {
		super( REbenchId , Material.iron);
		this.isActive	 = active;
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
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return true; 
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving) {
		super.onBlockPlacedBy(world, x, y, z, entityliving);
		
		// -- Current direction the player is facing while placing the block ( f variable if you press F3 )
		int f = MathHelper.floor_double( entityliving.rotationYaw * 4.0F / 360.0F  + 0.5D) & 3 ;
		world.setBlockMetadataWithNotify(x, y, z, f );
	}
		
	@Override
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int iFaceActivated, float facingX, float facingY, float facingZ) {
		if (world.isRemote) return true;
		if(this.GUID == -1) return true;
		if ( entityplayer.isSneaking() ) return false; // Suppress if player is sneaking, so you can still place a block
		
		TileREbench tileREbench = (TileREbench) world.getBlockTileEntity(x, y, z);
		
		if( tileREbench != null)
		{
			entityplayer.openGui(ZerzeraRE.instance, this.GUID , world, x, y, z);
		}
		return true;
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
	public TileEntity createNewTileEntity(World world) { 
		return new TileREbench();
	}

	public static void updateBlockState(boolean active, World world, int x, int y, int z) {
        int subId = world.getBlockMetadata(x, y, z);
        TileEntity rebench = world.getBlockTileEntity(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, subId);
	}
}
