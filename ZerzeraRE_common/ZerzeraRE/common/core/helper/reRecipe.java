package ZerzeraRE.common.core.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ShapedRecipes;
import net.minecraft.src.ShapelessRecipes;
import net.minecraft.src.StringTranslate;

public class reRecipe{
	
	public IRecipe iRecipe;
	public Object __class;
	public String itemID          = "";
	public String itemName        = "";
	public String itemDisplayName = "";
	
	private TreeMap<String,ItemStack> ingredients = new TreeMap<String,ItemStack>();
	private Method returnItemstack;
	
	public reRecipe(IRecipe iRecipe){
		this.iRecipe = iRecipe;
		
		try {
			if(this.iRecipe instanceof ShapedRecipes)
			{
				this.__class         = ShapedRecipes.class;
				this.returnItemstack = this.getClass().getMethod("getItemStackShapedRecipes");
			}
			else if(this.iRecipe instanceof ShapelessRecipes)
			{
				this.__class         = ShapelessRecipes.class;
				this.returnItemstack = this.getClass().getMethod("getItemStackShapelessRecipes");
			}
			else
			{
				this.__class         = IRecipe.class;
				this.returnItemstack = this.getClass().getMethod("getItemStackDummyRecipes");
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ItemStack itemStack = iRecipe.getRecipeOutput();
		if(itemStack != null)
		{
			Item item = itemStack.getItem();
			if(item != null)
			{
				String subId = ":   ";
				if(item.getHasSubtypes())
				{
					subId = ":"+String.format("%03d", itemStack.getItemDamage());
					this.itemName		= item.getItemNameIS(itemStack);
					this.itemDisplayName=  StringTranslate.getInstance().translateKey(this.itemName+".name");
				}
				else
				{
					this.itemName		= item.getItemName();
					this.itemDisplayName= item.getItemDisplayName( new ItemStack(item) );
				}
				this.itemID 		= String.format("%03d", itemStack.itemID)+subId;
				this.ingredients    = getIngredients();
			}
		}
		
	}
	
	public String getItemID() {
		return this.itemID;
	}
	public String getItemName() {
		return this.itemName;
	}
	public String getItemDisplayName() {
		return this.itemDisplayName;
	}
	public String getIngredientsString(){
		String returnString = "Recipe = ";
		for(Map.Entry<String, ItemStack> entry : ingredients.entrySet() )
		{
			ItemStack itemStack = entry.getValue();
			Item item = itemStack.getItem();
			if(item != null)
			{
				String itemName = item.getItemDisplayName(itemStack);
//				String itemName = item.getItemName();
				String itemID   = entry.getKey();
				int Amount		= itemStack.stackSize;
				returnString += itemName + " ["+itemID+"] ("+Amount+")";
				if(itemID != ingredients.lastKey())
				{
					returnString += " x ";
				}
			}
		}
		return returnString;
	}

	public TreeMap<String,ItemStack> getIngredients(){
		TreeMap<String,ItemStack> tm = new TreeMap();
		if(this.returnItemstack != null)
		{
			ItemStack[] itemStacks = new ItemStack[0];
			try {
				itemStacks = (ItemStack[]) this.returnItemstack.invoke(this);
			}
			catch (IllegalAccessException e) { e.printStackTrace(); } 
			catch (IllegalArgumentException e) { e.printStackTrace(); }
			catch (InvocationTargetException e) { e.printStackTrace(); }
			
			for(ItemStack itemStack : itemStacks)
			{
				if(itemStack != null)
				{
					Item item = itemStack.getItem();
					if( item  != null)
					{
						String subId = "";
						if(item.getHasSubtypes())
						{
							subId = ":"+String.format("%03d", itemStack.getItemDamage());
						}
						
						ItemStack entry = tm.get(itemStack.itemID+"");
						if( entry != null )
						{
							 entry.stackSize += itemStack.stackSize;
							 tm.put(itemStack.itemID+subId, entry);
						}
						else
						{
							tm.put(itemStack.itemID+subId, itemStack);
						}
					}
				}
			}
		}
		return tm;
	}

	public ItemStack[] getItemStackDummyRecipes(){
		return new ItemStack[0];
	}
	public ItemStack[] getItemStackShapedRecipes(){
		return (ItemStack[]) ModLoader.getPrivateValue((Class)__class, iRecipe, "recipeItems");
	}
	public ItemStack[] getItemStackShapelessRecipes(){
		ArrayList<ItemStack> items = (ArrayList<ItemStack>) ModLoader.getPrivateValue((Class)__class, iRecipe, "recipeItems");
		ItemStack[] returnStack = new ItemStack[items.size()];
		for(int i = 0; i< items.size(); i++)
		{
			ItemStack itemStack = items.get(i);
			if(itemStack != null)
			{
				returnStack[i] = itemStack;
			}
		}
		return returnStack;
	}
}
