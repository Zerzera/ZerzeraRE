package ZerzeraRE.common.tile;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

import ZerzeraRE.common.lib.DefaultProps;

public class TileREbench extends ModdedTile implements IInventory {
	
	private ItemStack[] rebenchItemStack = new ItemStack[3];
	
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList("Items");
		this.rebenchItemStack = new ItemStack[ getSizeInventory() ];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < rebenchItemStack.length) {
				rebenchItemStack[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < rebenchItemStack.length; ++currentIndex) 
		{
			if (rebenchItemStack[currentIndex] != null) 
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte)currentIndex);
				rebenchItemStack[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag("Items", tagList);
	}	
	
	@Override
	public int getSizeInventory() {
		return rebenchItemStack.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return rebenchItemStack[i];
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
	public void setInventorySlotContents(int var1, ItemStack var2) {
		
	}

	@Override
	public String getInvName() {
		return "Container_"+DefaultProps.RE_BENCH_NAME;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

}
