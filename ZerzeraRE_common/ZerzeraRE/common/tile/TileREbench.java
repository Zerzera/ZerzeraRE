package ZerzeraRE.common.tile;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.block.BlockREbench;
import ZerzeraRE.common.core.helper.Recipes;
import ZerzeraRE.common.core.helper.reRecipe;
import ZerzeraRE.common.lib.DefaultProps;

public class TileREbench extends ModdedTile implements IInventory {
	
	private ItemStack[] itemStack = new ItemStack[3];
	
	public int fuelLevel 	        = 0;
	public int currentItemFuelLevel = 0;
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		this.itemStack = new ItemStack[ this.getSizeInventory() ];
		
		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList("Items");
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < itemStack.length) {
				itemStack[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
		
		this.fuelLevel = nbtTagCompound.getShort("fuelLevel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setShort("fuelLevel", (short)this.fuelLevel);
		
		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < this.itemStack.length; ++currentIndex) 
		{
			if (this.itemStack[currentIndex] != null) 
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte)currentIndex);
				this.itemStack[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		nbtTagCompound.setTag("Items", tagList);
	}	
	
	@Override
	public int getSizeInventory() {
		return itemStack.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return itemStack[i];
	}

	@Override
	public ItemStack decrStackSize(int slotID, int amount) {
		if(this.itemStack[slotID] != null)
		{
			ItemStack newItemstack;
            if (this.itemStack[slotID].stackSize <= amount)
            {
            	newItemstack = this.itemStack[slotID];
                this.itemStack[slotID] = null;
                this.onInventoryChanged();
                return newItemstack;
            }
            else
            {
            	newItemstack = this.itemStack[slotID].splitStack(amount);

                if (this.itemStack[slotID].stackSize == 0)
                {
                    this.itemStack[slotID] = null;
                }
                this.onInventoryChanged();
                return newItemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotID) {
		ZerzeraRE.log.info("Trying to get the current stack in "+slotID + " while closing GUI");

        if (this.itemStack[slotID] != null)
        {
            ItemStack newItemstack = this.itemStack[slotID];
            this.itemStack[slotID] = null;
            return newItemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void updateEntity() {
		boolean changeInv = false;
		boolean hasFuel   = this.hasFuel();
		
		if(this.fuelLevel > 0)
		{
			this.fuelLevel--;
		}
		if(!this.worldObj.isRemote)
		{
			if(this.fuelLevel == 0 && this.itemIsFuel() )
			{
				 this.currentItemFuelLevel = this.fuelLevel = 500;
				 
				 if( this.hasFuel() )
				 {
					 changeInv = true;
					 if (this.itemStack[0] != null)
					 {
						 //--this.itemStack[0].stackSize;
						 if(this.itemStack[0].stackSize == 0)
						 {
                            this.itemStack[0] = this.itemStack[0].getItem().getContainerItemStack(itemStack[0]);
						 }
					 }
				 }
			}
			if (hasFuel != this.hasFuel())
            {
				changeInv = true;
                BlockREbench.updateBlockState(this.hasFuel(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
		}
        if (changeInv)
        {
            this.onInventoryChanged();
        }

	}
	
	@Override
    public void setInventorySlotContents(int slotID, ItemStack itemStack)
    {
        this.itemStack[slotID] = itemStack;

    	if (itemStack != null && this.worldObj.isRemote)
		{
    		Item item = this.itemStack[slotID].getItem();
    		if(item != null)
    		{
    			
    			reRecipe resource = Recipes.getResourcesByItem(item);
    			if(resource != null)
    			{
    				
    			}
    		}
		}
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
        	itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    @Override
	public void onInventoryChanged()
    {
        if (this.worldObj != null)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.updateTileEntityChunkAndDoNothing(this.xCoord, this.yCoord, this.zCoord, this);
        }
    }
    
	private boolean itemIsFuel() {
		if(this.itemStack[0] == null) return false;
		else return true;
	}

	public boolean hasFuel(){
		return this.fuelLevel > 0;
	}
	
	@Override
	public String getInvName() {
		return "container."+DefaultProps.RE_BENCH_NAME;
	}

    public int getFuelRemainingScaled(int max)
    {
        if (this.currentItemFuelLevel == 0)
        {
            this.currentItemFuelLevel = 500;
        }

        return max - (this.fuelLevel * max / this.currentItemFuelLevel);
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
