package ZerzeraRE.common.core.helper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class Recipes {
	
	private static TreeMap<String, reRecipe> itemRecipes = new TreeMap<String, reRecipe>();
	private static final Recipes instance = new Recipes();
	
    public static final Recipes getInstance()
    {
        return instance;
    }
    
    private Recipes()
    {
    	for(IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList())
    	{	
			reRecipe cRecipe = null;
			cRecipe = new reRecipe(irecipe);
			itemRecipes.put(cRecipe.getItemID(), cRecipe);
    	}
    }
    
    public void init(){
    	printOutRecipes();
    }

	private void printOutRecipes() {
		Iterator it = itemRecipes.entrySet().iterator();
		for ( Map.Entry<String, reRecipe> entry : itemRecipes.entrySet() )
		{
//			if(entry.getKey() == 351)
//			{
				ZerzeraRE.common.ZerzeraRE.log.info("Key = " + entry.getKey() + " Value = " + entry.getValue().getItemDisplayName() +" "+ entry.getValue().getIngredientsString());
//			}
	    }
	}

	private Item getItemFromItemStack(ItemStack itemStack) {
		if(itemStack != null)
		{
			Item item = itemStack.getItem();
			if (item!=null)
			{
				return item;
			}
		}
		return null;
	}
	
	
	// -- TODO implement getRecipeFromItem
	private void getRecipeFromItem(Item item) {}

	public static reRecipe getResourcesByItem(Item item) {return null;}
}