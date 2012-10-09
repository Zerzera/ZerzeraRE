package ZerzeraRE.common.block;

import java.util.logging.Logger;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraftforge.common.Configuration;

import ZerzeraRE.common.ZerzeraRE;
import ZerzeraRE.client.core.ClientProxy;
import ZerzeraRE.common.lib.DefaultProps;

public abstract class ModdedBlock extends BlockContainer {
	protected String textureFile;
	protected int GUID = -1;
	
	public ModdedBlock(int id, Material material) {
		super(id, material);
	}
	
	@Override
	public String getTextureFile () {
		return this.textureFile;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int iFaceActivated, float facingX, float facingY, float facingZ) {
		if (world.isRemote)
        {
            return true;
        }
		
		if(this.GUID == -1)
	    {
            return true;
        }
		
		// Suppress if player is sneaking, so you can still place a block
		if ( entityplayer.isSneaking() )
		{
			return false;
		}
		
//		super.onBlockActivated(world, x, y, z, entityplayer, iFaceActivated, facingX, facingY, facingZ);

//		Logger log = ClientProxy.proxy.log;
		
		return true;
	}

}