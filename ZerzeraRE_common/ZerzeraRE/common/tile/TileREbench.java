package ZerzeraRE.common.tile;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;

import ZerzeraRE.common.tile.ModdedTile;

public class TileREbench extends ModdedTile implements IInventory {

	@Override
	public int getSizeInventory() {
		return 0;
	}
	
	@Override
	public ItemStack getStackInSlot(int var1) {
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {}
	
	@Override
	public String getInvName() {
		return "container." + ZerzeraRE.common.lib.DefaultProps.RE_BENCH_NAME;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 0;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}
	
	public void openChest() {}
	public void closeChest() {}
}
