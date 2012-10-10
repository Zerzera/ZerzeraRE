package ZerzeraRE.client.gui;

import java.awt.Color;
import java.util.logging.Logger;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiStats;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.container.ContainerREbench;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;

public class GuiREbench extends GuiContainer {
	
	private TileREbench tileREbench;
	public Logger log = ZerzeraRE.instance.log;
	
	protected int guiX;
	protected int guiY;
	
	protected int xLevel;
	protected int yLevel;
	protected int wLevel;
	protected int hLevel;
	
	protected int shadowColor;
	protected int highlightColor;
	protected int indicatorColor;
	protected int gradientStart;
	protected int gradientEnd;
	
	public GuiREbench( EntityPlayer player, TileREbench rebench ) {
		super( new ContainerREbench(player.inventory, rebench) );
		tileREbench = rebench;
		
		// -- Gui sizes
		xSize = 176;
		ySize = 153;
		guiX  = (width  - xSize) / 2;
		guiY  = (height - ySize) / 2;
		
		// -- Level indicator
		wLevel = 10;
		hLevel = 58;
		xLevel = (xSize - wLevel) - 9;
		yLevel = 7;
		
		// -- Colors
		shadowColor    = new Color(0x606060).hashCode();
		highlightColor = new Color(0xFCFCFC).hashCode();
		indicatorColor = new Color(0x750000).hashCode();
		gradientStart  = new Color(0xFF0000).hashCode();
		gradientEnd    = new Color(0x00FF00).hashCode();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		// -- Draw GUI background
		int i = mc.renderEngine.getTexture(DefaultProps.RE_BENCH_GUI);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(i);
		
		guiX  = (width  - xSize) / 2;
		guiY  = (height - ySize) / 2;
		
		drawTexturedModalRect(guiX, guiY, 0, 0, xSize, ySize);
		
		drawTexturedrotatedRect(guiX+32, guiY+8, 177, 0, 16, 16, (float) 360 - tileREbench.getFuelRemainingScaled( 360 * -8 ));
		
		// -- Draw level gradient
        drawGradientRect(guiX + xLevel, guiY + yLevel, guiX + xLevel + wLevel, guiY + yLevel + hLevel, gradientStart, gradientEnd);
        
        // -- Draw box around the gradient with
        drawHorizontalLine(guiX + xLevel , guiX + xLevel + wLevel,  guiY + yLevel , shadowColor);
        drawHorizontalLine(guiX + xLevel , guiX + xLevel + wLevel,  guiY + yLevel + hLevel, highlightColor);
        drawVerticalLine(guiX + xLevel, guiY + yLevel, guiY + yLevel + hLevel + 1 , shadowColor);
        drawVerticalLine(guiX + xLevel + wLevel , guiY + yLevel, guiY + yLevel + hLevel , highlightColor);
        
        // -- Draw the indicator
        int perc  = tileREbench.getFuelRemainingScaled( hLevel - 4 );
        drawRect(
        		guiX + xLevel + 1,			// Start point x
        		guiY + yLevel + perc + 1,   // Start point y
        		guiX + xLevel + wLevel,		// End point x
        		guiY + yLevel + perc + 4,   // End point y
        		shadowColor				    // Color
		);
        drawHorizontalLine(
        		guiX + xLevel + 2,			// Start point x
        		guiX + xLevel + wLevel - 2,	// End point x
        		guiY + yLevel + perc + 2,   // Start point y
        		highlightColor 				// Color line
        );
        
//        drawRotatedRect(guiX + 50, guiY + 10, guiX + 70, guiY + 30, shadowColor, (float) 360 - tileREbench.getFuelRemainingScaled( 360 * 20 ));
        
        /*
        drawHorizontalLine(
        		guiX,			// Start point x
        		guiX + xSize,	// End point x
        		guiY + yLevel + hLevel,   // Start point y
        		highlightColor 				// Color line
        );
        */
        
// -- TODO show progress
/*        
		int progressSizeX      = 45;
		int progressSizeY      = 29;
		
		int progressOffsetX    = 26;
		int progressOffsetY    = 6;
		int progressPercentage = 0;
		
		if (this.tileREbench.hasFuel())
        {
            int percentage = this.tileREbench.getFuelRemainingScaled( progressSizeX );
            ZerzeraRE.log.info("" + percentage);
            ZerzeraRE.log.info("" + this.tileREbench.fuelLevel);
            ZerzeraRE.log.info("" + this.tileREbench.currentItemFuelLevel);
            this.drawTexturedModalRect(leftEdge + progressOffsetX, topEdge + progressOffsetY, 177, 0,percentage, progressSizeY );
        }
*/				
	}
	
	protected void drawTexturedrotatedRect(int startX, int startY, int sourceX, int sourceY, int width, int height, float angle)
	{
		float ratio = 0.00390625F;
		
		double pAngle = (float) (angle % 360) * Math.PI/180;
		int endX = startX + width;
		int endY = startY + height;
		int endSourceX = sourceX + width;
		int endSourceY = sourceY + height;
		
        Tessellator tessellator = Tessellator.instance;
        
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
//        GL11.glEnable(GL11.GL_BLEND);    
        GL11.glPushMatrix();
        GL11.glTranslatef( (float) startX + (width / 2 ), (float) startY + (height / 2 ) , 0.0f);
        GL11.glRotatef(angle, 0, 0, 1.0f);
        
        tessellator.startDrawingQuads();
        
        tessellator.addVertexWithUV( -(width / 2),  (height / 2), this.zLevel, sourceX    * ratio, endSourceY * ratio);
        tessellator.addVertexWithUV(  (width / 2),  (height / 2), this.zLevel, endSourceX * ratio, endSourceY * ratio);
        tessellator.addVertexWithUV(  (width / 2), -(height / 2), this.zLevel, endSourceX * ratio, sourceY    * ratio);
        tessellator.addVertexWithUV( -(width / 2), -(height / 2), this.zLevel, sourceX    * ratio, sourceY    * ratio);
        
        tessellator.draw();
        
        GL11.glPopMatrix();
	}
	
	protected void drawRotatedRect(int startX, int startY, int endX, int endY, int color, float angle){
        int temp;

        double pAngle = (float) (angle % 360) * Math.PI/180;
        double cosAngle = Math.abs( Math.cos(pAngle) );
        double sinAngle = Math.abs( Math.sin(pAngle) );

        float width  = MathHelper.abs_int(endX - startX);
        float height = MathHelper.abs_int(endY - startY);
        
        double newX = width * sinAngle + height * cosAngle;
        double newY = width * cosAngle + height * sinAngle;;
        
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float red   = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8  & 255) / 255.0F;
        float blue  = (float) (color       & 255) / 255.0F;
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glPushMatrix();
        
        GL11.glTranslatef( (float) startX + (width / 2 ), (float) startY + (height / 2 ) , 0.0f);
        GL11.glRotatef(angle, 0, 0, 1.0f);
        
//        tessellator.setTextureUV( guiX, guiY);
        tessellator.startDrawingQuads();
        tessellator.addVertex((float) -(height / 2) , (float)  (width / 2), 0.0D);
        tessellator.addVertex((float)  (height / 2) , (float)  (width / 2), 0.0D);
        tessellator.addVertex((float)  (height / 2) , (float) -(width / 2), 0.0D);
        tessellator.addVertex((float) -(height / 2) , (float) -(width / 2), 0.0D);
        
        tessellator.draw();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public boolean mouseInArea(int minX, int minY, int sizeX, int sizeY, int targetX, int targetY)
	{
		int maxX = minX + sizeX;
		int maxY = minY + sizeY;
		return (targetX < maxX && targetX > minX && targetY > minY && targetY < maxY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString(DefaultProps.RE_BENCH_FULLNAME, 8, 60, 0x373737);
		
        int iPercentage =  100 - tileREbench.getFuelRemainingScaled( 100 );
        String sPercentage =  iPercentage + "%";
        int sWidth = this.fontRenderer.getStringWidth(sPercentage);
        
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight;
        int tooltipOffset = 12;
        
        if(mouseInArea(xLevel, yLevel, wLevel, hLevel, mouseX - guiLeft, mouseY - guiTop ))
        {
        	drawGradientRect( 
        			mouseX - guiLeft - 3 + tooltipOffset,
        			mouseY - guiTop - 3 - tooltipOffset,
        			mouseX - guiLeft + sWidth + 3  + tooltipOffset,
        			mouseY - guiTop + 8 + 3 - tooltipOffset,
        			-1073741824,
        			-1073741824
			);
        	this.fontRenderer.drawStringWithShadow(sPercentage, mouseX - guiLeft + tooltipOffset, mouseY - guiTop - tooltipOffset, -1);
        }
        

	}


}
