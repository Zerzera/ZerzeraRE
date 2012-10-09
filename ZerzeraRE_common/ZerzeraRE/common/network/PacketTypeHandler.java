package ZerzeraRE.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import ZerzeraRE.common.lib.DefaultProps;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;

public enum PacketTypeHandler {
	TILE(PacketTileUpdate.class);

	private Class<? extends ModdedPacket> __class;
	PacketTypeHandler(Class<? extends ModdedPacket> __class) {
		this.__class = __class;
	}
	
	public static ModdedPacket buildPacket(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int selector = bis.read();
		DataInputStream dis = new DataInputStream(bis);

		ModdedPacket packet = null;

		try
		{
			packet = values()[selector].__class.newInstance();
		} 
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}

		packet.readPopulate(dis);

		return packet;
	}
	
	public static Packet populatePacket(ModdedPacket modPack) {
		byte[] data = modPack.populate();
		
		Packet250CustomPayload packet250 = new Packet250CustomPayload();
		packet250.channel = DefaultProps.NET_CHANNEL_NAME;
		packet250.data = data;
		packet250.length = data.length;
		packet250.isChunkDataPacket = modPack.isChunkDataPacket;
		
		return packet250;
	}
}
