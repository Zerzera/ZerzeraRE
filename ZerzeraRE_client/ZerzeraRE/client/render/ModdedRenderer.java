package ZerzeraRE.client.render;

import java.util.logging.Logger;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import ZerzeraRE.common.ZerzeraRE;

public class ModdedRenderer extends RenderBlocks implements ISimpleBlockRenderingHandler {
	protected static int     RenderID = 0;
	protected static boolean RenderHiRes = false;
	protected static int     hiResSide = -1;
	protected static boolean ForceHiResRender = false;
	protected static boolean renderForInv = false;
	
	public Logger log = ZerzeraRE.log;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		
		// -- Render for the inventory bar
		ModdedRenderer.renderForInv = true;
		// -- Top
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		this.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(1) );
		tessellator.draw();
		
		// -- Front
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		this.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(2), true); // HiRes
		tessellator.draw();
		
		// -- Back
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		this.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(3));
		tessellator.draw();
		
		// -- Side
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		this.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
		tessellator.draw();
		
		// -- Side
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		this.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(4));
		tessellator.draw();
		
		// -- Bottom
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		
		this.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSide(0));
		tessellator.draw();

		ModdedRenderer.renderForInv = false;
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		this.blockAccess = world;
		ModdedRenderer.hiResSide   = world.getBlockMetadata(x, y, z);
		
		super.renderStandardBlock(block, x, y, z);
		
		return true;
	}
	
	// -- Ingame this is the South side of the block ( f = 0 )
	@Override
	public void renderEastFace(Block block, double x, double y, double z, int obt) {
        Tessellator tessellator = Tessellator.instance;

        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetU = (obt & 15) << 4;
        int offsetV = obt & 240;
        
        double textureSize = ( (ModdedRenderer.hiResSide == 0 && ModdedRenderer.RenderHiRes == true && ModdedRenderer.renderForInv == false) || ModdedRenderer.ForceHiResRender ? 64.0D : 16.0D );
        
        if(ModdedRenderer.hiResSide == 1 && ModdedRenderer.renderForInv == false) // Rotate side 180 so texture faces to the front
        {
        	this.uvRotateEast = 3;
        }
        if(ModdedRenderer.hiResSide == 3 && ModdedRenderer.renderForInv == false) // Rotate side 180 so texture faces to the front
        {
        	this.uvRotateEast = 0;
        }
        else
        {
        	this.uvRotateEast = 0;
        }

        float safetyMin = 0.0F;
        float safetyMax = (float) (textureSize - 0.01D);

		double minUvX = ( offsetU                 + block.minX * textureSize)         / 256.0D;
        double maxUvX = ( offsetU                 + block.maxX * textureSize - 0.01D) / 256.0D;
        double maxUvY = ( offsetV + textureSize - block.maxY * textureSize)         / 256.0D;
        double minUvY = ( offsetV + textureSize - block.minY * textureSize - 0.01D) / 256.0D;
        double tMinUvX;

        if (this.flipTexture)
        {
            tMinUvX = minUvX;
            minUvX = maxUvX;
            maxUvX = tMinUvX;
        }
        
		if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            minUvX = (offsetU + safetyMin) / 256.0F;
            maxUvX = (offsetU + safetyMax) / 256.0F;
        }

        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            maxUvY = (offsetV + safetyMin) / 256.0F;
            minUvY = (offsetV + safetyMax) / 256.0F;
        }

        tMinUvX = maxUvX;
        double ttMinUvX = minUvX;
        double tMaxUvY = maxUvY;
        double tMinUvY = minUvY;

        if (this.uvRotateEast == 2)
        {
            minUvX = ( offsetU                 + block.minY * textureSize) / 256.0D;
            maxUvY = ( offsetV + textureSize - block.minX * textureSize) / 256.0D;
            maxUvX = ( offsetU                 + block.maxY * textureSize) / 256.0D;
            minUvY = ( offsetV + textureSize - block.maxX * textureSize) / 256.0D;
            tMaxUvY = maxUvY;
            tMinUvY = minUvY;
            tMinUvX = minUvX;
            ttMinUvX = maxUvX;
            maxUvY = minUvY;
            minUvY = tMaxUvY;
        }
        else if (this.uvRotateEast == 1)
        {
            minUvX = ( offsetU + textureSize - block.maxY * textureSize) / 256.0D;
            maxUvY = ( offsetV                 + block.maxX * textureSize) / 256.0D;
            maxUvX = ( offsetU + textureSize - block.minY * textureSize) / 256.0D;
            minUvY = ( offsetV                 + block.minX * textureSize) / 256.0D;
            tMinUvX = maxUvX;
            ttMinUvX = minUvX;
            minUvX = maxUvX;
            maxUvX = ttMinUvX;
            tMaxUvY = minUvY;
            tMinUvY = maxUvY;
        }
        else if (this.uvRotateEast == 3)
        {
            minUvX = (offsetU + textureSize - block.minX * textureSize)         / 256.0D;
            maxUvX = (offsetU + textureSize - block.maxX * textureSize - 0.01D) / 256.0D;
            maxUvY = (offsetV                 + block.maxY * textureSize)         / 256.0D;
            minUvY = (offsetV                 + block.minY * textureSize - 0.01D) / 256.0D;
            tMinUvX = maxUvX;
            ttMinUvX = minUvX;
            tMaxUvY = maxUvY;
            tMinUvY = minUvY;
        }

        double minSizeX = x + block.minX;
        double maxSizeX = x + block.maxX;
        double minSizeY = y + block.minY;
        double maxSizeY = y + block.maxY;
        double minSizeZ = z + block.minZ;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, tMinUvX, tMaxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(maxSizeX, maxSizeY, minSizeZ, minUvX, maxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, ttMinUvX, tMinUvY);
            
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, maxUvX, minUvY);
        }
        else
        {
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, tMinUvX, tMaxUvY);
            tessellator.addVertexWithUV(maxSizeX, maxSizeY, minSizeZ, minUvX, maxUvY);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, ttMinUvX, tMinUvY);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, maxUvX, minUvY);
        }
	}
	public void renderEastFace(Block block, double x, double y, double z, int obt, boolean HiRes) {
		ModdedRenderer.ForceHiResRender = true;
		this.renderEastFace(block, x, y, z, obt);
		ModdedRenderer.ForceHiResRender = false;
	}
	// -- Ingame this is the North side of the block ( f = 2 )
	@Override
	public void renderWestFace(Block block, double x, double y, double z, int obt) {
		Tessellator tessellator = Tessellator.instance;

        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetU = (obt & 15) << 4;
        int offsetV = obt & 240;
        
        double textureSize = ( (ModdedRenderer.hiResSide == 2 && ModdedRenderer.RenderHiRes == true && ModdedRenderer.renderForInv == false) || ModdedRenderer.ForceHiResRender ? 64.0D : 16.0D );
        
        if(ModdedRenderer.hiResSide == 3 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateWest = 3;
        }
        else if(ModdedRenderer.hiResSide == 1 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateWest = 0;
        }
        else
        {
        	this.uvRotateWest = 0;
        }
        
        float safetyMin = 0.0F;
        float safetyMax = (float) (textureSize - 0.01D);
        
		double minUvX = ( offsetU                 + block.minX * textureSize)         / 256.0D;
        double maxUvX = ( offsetU                 + block.maxX * textureSize - 0.01D) / 256.0D;
        double maxUvY = ( offsetV + textureSize - block.maxY * textureSize)         / 256.0D;
        double minUvY = ( offsetV + textureSize - block.minY * textureSize - 0.01D) / 256.0D;
        double tMinUvX;

        if (this.flipTexture)
        {
            tMinUvX = minUvX;
            minUvX = maxUvX;
            maxUvX = tMinUvX;
        }

		if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            minUvX = (offsetU + safetyMin) / 256.0F;
            maxUvX = (offsetU + safetyMax) / 256.0F;
        }

        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            maxUvY = (offsetV + safetyMin) / 256.0F;
            minUvY = (offsetV + safetyMax) / 256.0F;
        }

        tMinUvX = maxUvX;
        double ttMinUvX = minUvX;
        double tMaxUvX = maxUvY;
        double tMinUvY = minUvY;

        if (this.uvRotateWest == 1)
        {
            minUvX = ( offsetU                 + block.minY * textureSize) / 256.0D;
            minUvY = ( offsetV + textureSize - block.minX * textureSize) / 256.0D;
            maxUvX = ( offsetU                 + block.maxY * textureSize) / 256.0D;
            maxUvY = ( offsetV + textureSize - block.maxX * textureSize) / 256.0D;
            tMaxUvX = maxUvY;
            tMinUvY = minUvY;
            tMinUvX = minUvX;
            ttMinUvX = maxUvX;
            maxUvY = minUvY;
            minUvY = tMaxUvX;
        }
        else if (this.uvRotateWest == 2)
        {
            minUvX = ( offsetU + textureSize - block.maxY * textureSize) / 256.0D;
            maxUvY = ( offsetV                 + block.minX * textureSize) / 256.0D;
            maxUvX = ( offsetU + textureSize - block.minY * textureSize) / 256.0D;
            minUvY = ( offsetV                 + block.maxX * textureSize) / 256.0D;
            tMinUvX = maxUvX;
            ttMinUvX = minUvX;
            minUvX = maxUvX;
            maxUvX = ttMinUvX;
            tMaxUvX = minUvY;
            tMinUvY = maxUvY;
        }
        else if (this.uvRotateWest == 3)
        {
            minUvX = (offsetU + textureSize - block.minX * textureSize) / 256.0D;
            maxUvX = (offsetU + textureSize - block.maxX * textureSize - 0.01D) / 256.0D;
            maxUvY = (offsetV                 + block.maxY * textureSize) / 256.0D;
            minUvY = (offsetV                 + block.minY * textureSize - 0.01D) / 256.0D;
            tMinUvX = maxUvX;
            ttMinUvX = minUvX;
            tMaxUvX = maxUvY;
            tMinUvY = minUvY;
        }

        double minSizeX = x + block.minX;
        double maxSizeX = x + block.maxX;
        double minSizeY = y + block.minY;
        double maxSizeY = y + block.maxY;
        double maxSizeZ = z + block.maxZ;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, minUvX, maxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, ttMinUvX, tMinUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ, maxUvX, minUvY);
            
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(maxSizeX, maxSizeY, maxSizeZ, tMinUvX, tMaxUvX);
        }
        else
        {
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, minUvX, maxUvY);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, ttMinUvX, tMinUvY);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ, maxUvX, minUvY);
            tessellator.addVertexWithUV(maxSizeX, maxSizeY, maxSizeZ, tMinUvX, tMaxUvX);
        }
	}
	public void renderWestFace(Block block, double x, double y, double z, int obt, boolean HiRes) {
		ModdedRenderer.ForceHiResRender = true;
		this.renderWestFace(block, x, y, z, obt);
		ModdedRenderer.ForceHiResRender = false;
	}
	// -- Ingame this is the West side of the block ( f = 1 )
	@Override
    public void renderSouthFace(Block block, double x, double y, double z, int obt)
    {
        Tessellator tessellator = Tessellator.instance;

        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetU = (obt & 15) << 4;
        int offsetV = obt & 240;
        
        double textureSize = ( (ModdedRenderer.hiResSide == 1 && ModdedRenderer.RenderHiRes == true && ModdedRenderer.renderForInv == false) || ModdedRenderer.ForceHiResRender ? 64.0D : 16.0D );
        if(ModdedRenderer.hiResSide == 2 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateSouth = 3;
        }
        else if(ModdedRenderer.hiResSide == 0 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateSouth = 0;
        }
        else
        {
        	this.uvRotateSouth = 0;
        }

        float safetyMax = (float) (textureSize - 0.01D);
		float safetyMin = 0.0F;
		
		double minUvZ = ( offsetU                 + block.minZ * textureSize)         / 256.0D;
        double maxUvZ = ( offsetU                 + block.maxZ * textureSize - 0.01D) / 256.0D;
        double minUvY = ( offsetV + textureSize - block.maxY * textureSize)         / 256.0D;
        double maxUvY = ( offsetV + textureSize - block.minY * textureSize - 0.01D) / 256.0D;
        double tMinUvZ;

        if (this.flipTexture)
        {
            tMinUvZ = minUvZ;
            minUvZ = maxUvZ;
            maxUvZ = tMinUvZ;
        }

		if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            minUvZ = (offsetU + safetyMin) / 256.0F;
            maxUvZ = (offsetU + safetyMax) / 256.0F;
        }

        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            minUvY = (offsetV + safetyMin) / 256.0F;
            maxUvY = (offsetV + safetyMax) / 256.0F;
        }

        tMinUvZ = maxUvZ;
        double tMaxUvZ = minUvZ;
        double tMinUvY = minUvY;
        double tMaxUvY = maxUvY;

        if (this.uvRotateSouth == 2)
        {
            minUvZ = ( offsetU                 + block.minY * textureSize) / 256.0D;
            minUvY = ( offsetV + textureSize - block.minZ * textureSize) / 256.0D;
            maxUvZ = ( offsetU                 + block.maxY * textureSize) / 256.0D;
            maxUvY = ( offsetV + textureSize - block.maxZ * textureSize) / 256.0D;
            tMinUvY = minUvY;
            tMaxUvY = maxUvY;
            tMinUvZ = minUvZ;
            tMaxUvZ = maxUvZ;
            minUvY = maxUvY;
            maxUvY = tMinUvY;
        }
        else if (this.uvRotateSouth == 1)
        {
            minUvZ = ( offsetU + textureSize - block.maxY * textureSize) / 256.0D;
            minUvY = ( offsetV                 + block.maxZ * textureSize) / 256.0D;
            maxUvZ = ( offsetU + textureSize - block.minY * textureSize) / 256.0D;
            maxUvY = ( offsetV                 + block.minZ * textureSize) / 256.0D;
            tMinUvZ = maxUvZ;
            tMaxUvZ = minUvZ;
            minUvZ = maxUvZ;
            maxUvZ = tMaxUvZ;
            tMinUvY = maxUvY;
            tMaxUvY = minUvY;
        }
        else if (this.uvRotateSouth == 3)
        {
            minUvZ = ( offsetU + textureSize - block.minZ * textureSize)         / 256.0D;
            maxUvZ = ( offsetU + textureSize - block.maxZ * textureSize - 0.01D) / 256.0D;
            minUvY = ( offsetV                 + block.maxY * textureSize)         / 256.0D;
            maxUvY = ( offsetV                 + block.minY * textureSize - 0.01D) / 256.0D;
            
            tMinUvZ = maxUvZ;
            tMaxUvZ = minUvZ;
            tMinUvY = minUvY;
            tMaxUvY = maxUvY;
        }

        double minSizeX = x + block.maxX;
        double minSizeY = y + block.minY;
        double maxSizeY = y + block.maxY;
        double minSizeZ = z + block.minZ;
        double maxSizeZ = z + block.maxZ;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMaxUvZ, tMaxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, maxUvZ, maxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, tMinUvZ, tMinUvY);
            
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, minUvZ, minUvY);
        }
        else
        {
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMaxUvZ, tMaxUvY);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, maxUvZ, maxUvY);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, tMinUvZ, tMinUvY);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, minUvZ, minUvY);
        }
    }
	public void renderSouthFace(Block block, double x, double y, double z, int obt, boolean HiRes) {
		ModdedRenderer.ForceHiResRender = true;
		this.renderSouthFace(block, x, y, z, obt);
		ModdedRenderer.ForceHiResRender = false;
	}
	// -- Ingame this is the East side of the block ( f = 3 )
	@Override
    public void renderNorthFace(Block block, double x, double y, double z, int obt)
    {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetU = (obt & 15) << 4;
        int offsetV = obt & 240;
        
        double textureSize = ( (ModdedRenderer.hiResSide == 3 && ModdedRenderer.RenderHiRes == true && ModdedRenderer.renderForInv == false) || ModdedRenderer.ForceHiResRender ? 64.0D : 16.0D );
        
        if(ModdedRenderer.hiResSide == 2 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateNorth = 0;
        }
        else if(ModdedRenderer.hiResSide == 0 && ModdedRenderer.renderForInv == false) // Rotate texture so side face to the front
        {
        	this.uvRotateNorth = 3;
        }
        else
        {
        	this.uvRotateNorth = 0;
        }

        float safetyMin = 0.0F;
        float safetyMax = (float) (textureSize - 0.01D);
        
		double minUvZ = ( offsetU                 + block.minZ * textureSize)         / 256.0D;
        double maxUvZ = ( offsetU                 + block.maxZ * textureSize - 0.01D) / 256.0D;
        double maxUvY = ( offsetV + textureSize - block.maxY * textureSize)         / 256.0D;
        double minUvY = ( offsetV + textureSize - block.minY * textureSize - 0.01D) / 256.0D;
        
        double tMinUvZ;

        if (this.flipTexture)
        {
            tMinUvZ = minUvZ;
            minUvZ  = maxUvZ;
            maxUvZ  = tMinUvZ;
        }

		if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            minUvZ = (offsetU + safetyMin) / 256.0F;
            maxUvZ = (offsetU + safetyMax) / 256.0F;
        }

        if (block.minY < 0.0D || block.maxY > 1.0D)
        {
            maxUvY = (offsetV + safetyMin) / 256.0F;
            minUvY = (offsetV + safetyMax) / 256.0F;
        }

        tMinUvZ = maxUvZ;
        double ttMinUvZ = minUvZ;
        double tMaxUvY = maxUvY;
        double tMinUvY = minUvY;

        if (this.uvRotateNorth == 1)
        {
            minUvZ = ( offsetU                 + block.minY * textureSize) / 256.0D;
            maxUvY = ( offsetV + textureSize - block.maxZ * textureSize) / 256.0D;
            maxUvZ = ( offsetU                 + block.maxY * textureSize) / 256.0D;
            minUvY = ( offsetV + textureSize - block.minZ * textureSize) / 256.0D;
            tMaxUvY = maxUvY;
            tMinUvY = minUvY;
            tMinUvZ = minUvZ;
            ttMinUvZ = maxUvZ;
            maxUvY = minUvY;
            minUvY = tMaxUvY;
        }
        else if (this.uvRotateNorth == 2)
        {
            minUvZ = ( offsetU + textureSize - block.maxY * textureSize) / 256.0D;
            maxUvY = ( offsetV                 + block.minZ * textureSize) / 256.0D;
            maxUvZ = ( offsetU + textureSize - block.minY * textureSize) / 256.0D;
            minUvY = ( offsetV                 + block.maxZ * textureSize) / 256.0D;
            
            tMinUvZ = maxUvZ;
            ttMinUvZ = minUvZ;
            minUvZ = maxUvZ;
            maxUvZ = ttMinUvZ;
            tMaxUvY = minUvY;
            tMinUvY = maxUvY;
        }
        else if (this.uvRotateNorth == 3)
        {
            minUvZ = ( offsetU + textureSize - block.minZ * textureSize)         / 256.0D;
            maxUvZ = ( offsetU + textureSize - block.maxZ * textureSize - 0.01D) / 256.0D;
            maxUvY = ( offsetV                 + block.maxY * textureSize)         / 256.0D;
            minUvY = ( offsetV                 + block.minY * textureSize - 0.01D) / 256.0D;
            
            tMinUvZ = maxUvZ;
            ttMinUvZ = minUvZ;
            tMaxUvY = maxUvY;
            tMinUvY = minUvY;
        }

        double minSizeX = x + block.minX;
        double minSizeY = y + block.minY;
        double maxSizeY = y + block.maxY;
        double minSizeZ = z + block.minZ;
        double maxSizeZ = z + block.maxZ;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, tMinUvZ, tMaxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, minUvZ, maxUvY);
            
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, ttMinUvZ, tMinUvY);
            
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, maxUvZ, minUvY);
        }
        else
        {
            tessellator.addVertexWithUV(minSizeX, maxSizeY, maxSizeZ, tMinUvZ, tMaxUvY);
            tessellator.addVertexWithUV(minSizeX, maxSizeY, minSizeZ, minUvZ, maxUvY);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, ttMinUvZ, tMinUvY);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, maxUvZ, minUvY);
        }
    }
	public void renderNorthFace(Block block, double x, double y, double z, int obt, boolean HiRes) {
		ModdedRenderer.ForceHiResRender = true;
		renderNorthFace(block, x, y, z, obt);
		ModdedRenderer.ForceHiResRender = false;
	}
	
	@Override
    public void renderTopFace(Block block, double x, double y, double z, int obt)
    {
		// faceDir = 0 - South
		// faceDir = 1 - West
		// faceDir = 2 - North
		// faceDir = 3 - East
		
		if(ModdedRenderer.RenderHiRes)
		{
			switch( ModdedRenderer.hiResSide )
			{
				case 0: this.uvRotateTop = 0; break;
				case 1: this.uvRotateTop = 1; break;
				case 2: this.uvRotateTop = 3; break;
				case 3: this.uvRotateTop = 2; break;
			}
		}
	   
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetX = (obt & 15) << 4;
        int offsetZ = obt & 240;
        
		double textureSize = 16.0D;
		float safetyMax = (float) (textureSize - 0.01D);
		float safetyMin = 0.0F;
        
        double minUvX = ( offsetX + block.minX * textureSize ) 		 / 256.0D;
        double maxUvX = ( offsetX + block.maxX * textureSize - 0.01D) / 256.0D;
        double minUvZ = ( offsetZ + block.minZ * textureSize ) 		 / 256.0D;
        double maxUvZ = ( offsetZ + block.maxZ * textureSize - 0.01D) / 256.0D;
        
        if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            minUvX = ( offsetX + safetyMin) / 256.0F;
            maxUvX = ( offsetX + safetyMax) / 256.0F;
        }

        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            minUvZ = ( offsetZ + safetyMin ) / 256.0F;
            maxUvZ = ( offsetZ + safetyMax ) / 256.0F;
        }

        double tMaxUvX = maxUvX;
        double tMinUvX = minUvX;
        double tMinUvZ = minUvZ;
        double tMaxUvZ = maxUvZ;

        if (this.uvRotateTop == 1)
        {
            minUvX = ( offsetX + block.minZ * textureSize) / 256.0D;
            minUvZ = ( offsetZ + (int) textureSize - block.maxX * textureSize) / 256.0D;
            maxUvX = ( offsetX + block.maxZ * textureSize) / 256.0D;
            maxUvZ = ( offsetZ + (int) textureSize - block.minX * textureSize) / 256.0D;
            
            tMinUvZ = minUvZ;
            tMaxUvZ = maxUvZ;
            tMaxUvX = minUvX;
            tMinUvX = maxUvX;
            
            minUvZ = maxUvZ;
            maxUvZ = tMinUvZ;
        }
        else if (this.uvRotateTop == 2)
        {
            minUvX = ( offsetX + (int) textureSize - block.maxZ * textureSize) / 256.0D;
            minUvZ = ( offsetZ + block.minX * textureSize) / 256.0D;
            maxUvX = ( offsetX + (int) textureSize - block.minZ * textureSize) / 256.0D;
            maxUvZ = ( offsetZ + block.maxX * textureSize) / 256.0D;
            tMaxUvX = maxUvX;
            tMinUvX = minUvX;
            minUvX  = maxUvX;
            maxUvX  = tMinUvX;
            tMinUvZ = maxUvZ;
            tMaxUvZ = minUvZ;
        }
        else if (this.uvRotateTop == 3)
        {
            minUvX = (offsetX + (int) textureSize - block.minX * textureSize) / 256.0D;
            maxUvX = (offsetX + (int) textureSize - block.maxX * textureSize - 0.01D) / 256.0D;
            minUvZ = (offsetZ + (int) textureSize - block.minZ * textureSize) / 256.0D;
            maxUvZ = (offsetZ + (int) textureSize - block.maxZ * textureSize - 0.01D) / 256.0D;
            tMaxUvX = maxUvX;
            tMinUvX = minUvX;
            tMinUvZ = minUvZ;
            tMaxUvZ = maxUvZ;
        }

        double minSizeX = x + block.minX;
        double maxSizeX = x + block.maxX;
        double minSizeZ = z + block.minZ;
        double maxSizeZ = z + block.maxZ;
        
        double minSizeY = y + block.maxY;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ, maxUvX, maxUvZ);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, tMaxUvX, tMinUvZ);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, minUvX, minUvZ);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMinUvX, tMaxUvZ);
        }
        else
        {
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ,  maxUvX,  maxUvZ);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, tMaxUvX, tMinUvZ);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ,  minUvX,  minUvZ);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMinUvX, tMaxUvZ);
        }
    }
	
	@Override
	public void renderBottomFace(Block block, double x, double y, double z, int obt) {
		// faceDir = 0 - South
		// faceDir = 1 - West
		// faceDir = 2 - North
		// faceDir = 3 - East
		
		switch( ModdedRenderer.hiResSide )
		{
			case 0: this.uvRotateBottom = 0; break;
			case 1: this.uvRotateBottom = 2; break;
			case 2: this.uvRotateBottom = 3; break;
			case 3: this.uvRotateBottom = 1; break;
		}
	   
		Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) obt = this.overrideBlockTexture;

        int offsetU = (obt & 15) << 4;
        int offsetV = obt & 240;
        
        double textureSize = 16.0D;
        float safetyMax = (float) (textureSize - 0.01D);
        float safetyMin = 0.0F;
        
		double minUvX = ( offsetU + block.minX * textureSize)         / 256.0D;
        double maxUvX = ( offsetU + block.maxX * textureSize - 0.01D) / 256.0D;
        double minUvZ = ( offsetV + block.minZ * textureSize)         / 256.0D;
        double maxUvZ = ( offsetV + block.maxZ * textureSize - 0.01D) / 256.0D;

		
		if (block.minX < 0.0D || block.maxX > 1.0D)
        {
            minUvX = (offsetU + safetyMin) / 256.0F;
            maxUvX = (offsetU + safetyMax) / 256.0F;
        }

        if (block.minZ < 0.0D || block.maxZ > 1.0D)
        {
            minUvZ = (offsetV + safetyMin) / 256.0F;
            maxUvZ = (offsetV + safetyMax) / 256.0F;
        }

        double tMaxUvX = maxUvX;
        double tMinUvX = minUvX;
        double tMinUvZ = minUvZ;
        double tMaxUvZ = maxUvZ;

        if (this.uvRotateBottom == 2)
        {
            minUvX = ( offsetU                 + block.minZ * textureSize) / 256.0D;
            minUvZ = ( offsetV + textureSize - block.maxX * textureSize) / 256.0D;
            maxUvX = ( offsetU                 + block.maxZ * textureSize) / 256.0D;
            maxUvZ = ( offsetV + textureSize - block.minX * textureSize) / 256.0D;
            tMinUvZ = minUvZ;
            tMaxUvZ = maxUvZ;
            tMaxUvX = minUvX;
            tMinUvX = maxUvX;
            minUvZ = maxUvZ;
            maxUvZ = tMinUvZ;
        }
        else if (this.uvRotateBottom == 1)
        {
            minUvX = ( offsetU + textureSize - block.maxZ * textureSize) / 256.0D;
            minUvZ = ( offsetV                 + block.minX * textureSize) / 256.0D;
            maxUvX = ( offsetU + textureSize - block.minZ * textureSize) / 256.0D;
            maxUvZ = ( offsetV                 + block.maxX * textureSize) / 256.0D;
            tMaxUvX = maxUvX;
            tMinUvX = minUvX;
            minUvX = maxUvX;
            maxUvX = tMinUvX;
            tMinUvZ = maxUvZ;
            tMaxUvZ = minUvZ;
        }
        else if (this.uvRotateBottom == 3)
        {
            minUvX = ( offsetU + textureSize - block.minX * textureSize)         / 256.0D;
            maxUvX = ( offsetU + textureSize - block.maxX * textureSize - 0.01D) / 256.0D;
            minUvZ = ( offsetV + textureSize - block.minZ * textureSize)         / 256.0D;
            maxUvZ = ( offsetV + textureSize - block.maxZ * textureSize - 0.01D) / 256.0D;
            tMaxUvX = maxUvX;
            tMinUvX = minUvX;
            tMinUvZ = minUvZ;
            tMaxUvZ = maxUvZ;
        }

        double minSizeX = x + block.minX;
        double maxSizeX = x + block.maxX;
        double minSizeY = y + block.minY;
        double minSizeZ = z + block.minZ;
        double maxSizeZ = z + block.maxZ;

        if (this.enableAO)
        {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.setBrightness(this.brightnessTopLeft);
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMinUvX, tMaxUvZ);
            
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.setBrightness(this.brightnessBottomLeft);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, minUvX, minUvZ);
            
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.setBrightness(this.brightnessBottomRight);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, tMaxUvX, tMinUvZ);
            
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.setBrightness(this.brightnessTopRight);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ, maxUvX, maxUvZ);
        }
        else
        {
            tessellator.addVertexWithUV(minSizeX, minSizeY, maxSizeZ, tMinUvX, tMaxUvZ);
            tessellator.addVertexWithUV(minSizeX, minSizeY, minSizeZ, minUvX, minUvZ);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, minSizeZ, tMaxUvX, tMinUvZ);
            tessellator.addVertexWithUV(maxSizeX, minSizeY, maxSizeZ, maxUvX, maxUvZ);
        }
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderID;
	}

}
