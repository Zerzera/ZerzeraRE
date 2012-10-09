package ZerzeraRE.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.World;

import ZerzeraRE.common.container.ContainerREbench;
import ZerzeraRE.common.lib.DefaultProps;
import ZerzeraRE.common.tile.TileREbench;

public class GuiREbench extends GuiContainer {
	
	private TileREbench tileREbench;
	
	public GuiREbench( EntityPlayer player, TileREbench rebench ) {
		super( new ContainerREbench(player.inventory, rebench) );
		tileREbench = rebench;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,int var3) {
		int i = mc.renderEngine.getTexture(DefaultProps.RE_BENCH_GUI);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		
		drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

	}
	
	@Override
	protected void drawGuiContainerForegroundLayer() {
		super.drawGuiContainerForegroundLayer();
	}


}
