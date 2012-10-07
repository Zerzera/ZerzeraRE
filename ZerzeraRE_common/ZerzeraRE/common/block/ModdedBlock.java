package ZerzeraRE.common.block;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.common.lib.DefaultProps;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class ModdedBlock extends BlockContainer {
	
	public ModdedBlock(int id, Material material) {
		super(id, material);
	}
	
	@Override
	public String getTextureFile () {
		return DefaultProps.TEXTURE_BLOCKS;
	}

    
}