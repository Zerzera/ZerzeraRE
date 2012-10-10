package ZerzeraRE.common.container;

import java.util.Iterator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.tile.TileREbench;

public class ContainerREbench extends Container{
	
	private int lastFuelLevel = 0;
	private TileREbench rebench;

	public ContainerREbench(InventoryPlayer InvPlayer, TileREbench TileREbench) {
		rebench = TileREbench;
		
		// Adding rebench input slot
		addSlotToContainer(new Slot( rebench, 0, 8, 8));
		
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
	public void addCraftingToCrafters(ICrafting iCrafting) {
		super.addCraftingToCrafters(iCrafting);
		iCrafting.updateCraftingInventoryInfo(this, 0, this.rebench.fuelLevel);
	}

	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();
		Iterator iterator = this.crafters.iterator();
		while(iterator.hasNext())
		{
			ICrafting subICrafting = (ICrafting)iterator.next();
            if (this.lastFuelLevel != this.rebench.fuelLevel)
            {
            	subICrafting.updateCraftingInventoryInfo(this, 0, this.rebench.fuelLevel);
            }
		}
		this.lastFuelLevel = this.rebench.fuelLevel;
	}
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int slotID, int i)
    {
		if(slotID == 0)
		{
			this.rebench.fuelLevel = i;
		}
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
