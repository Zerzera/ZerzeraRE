package ZerzeraRE.common.container;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Slot;

import ZerzeraRE.common.tile.TileREbench;

public class ContainerREbench extends Container{
	
	private TileREbench rebench;

	public ContainerREbench(InventoryPlayer InvPlayer, TileREbench TileREbench) {
		rebench = TileREbench;
		

		// Adding rebench input slot
		addSlotToContainer(new Slot( rebench, 0, 9, 8));
		
		// Player inventory
		for (int i1 = 0; i1 < 3; i1++)
		{
			for (int l1 = 0; l1 < 9; l1++) 
			{
				addSlotToContainer(new Slot(InvPlayer, l1 + i1 * 9 + 9, 8 + l1 * 18, 71 + i1 * 18));
			}
		}

		// Player hotbar
		for (int j1 = 0; j1 < 9; j1++)
		{
			addSlotToContainer(new Slot(InvPlayer, j1, 8 + j1 * 18, 129));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
