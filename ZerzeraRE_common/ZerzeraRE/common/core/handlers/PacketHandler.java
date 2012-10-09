package ZerzeraRE.common.core.handlers;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import ZerzeraRE.common.network.ModdedPacket;
import ZerzeraRE.common.network.PacketTypeHandler;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player) {
    	ModdedPacket mPacket = PacketTypeHandler.buildPacket(packet.data);
    	
    	// Execute the appropriate actions based on the PacketEE type
    	mPacket.execute(manager, player);
    }

}
