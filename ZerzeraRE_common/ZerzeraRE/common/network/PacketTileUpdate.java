package ZerzeraRE.common.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.NetworkManager;

import cpw.mods.fml.common.network.Player;

public class PacketTileUpdate extends ModdedPacket {
	public int x, y, z;
	public String player;
	
	public PacketTileUpdate() {
		super(PacketTypeHandler.TILE, true);
	}
	
	public void setCoords(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setPlayerName(String player) {
		this.player = player;
	}
	
	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(x);
		data.writeInt(y);
		data.writeInt(z);
		data.writeUTF(player);
	}
	
	@Override
	public void readData(DataInputStream data) throws IOException {
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.player = data.readUTF();
	}

	@Override
	public void execute(NetworkManager manager, Player player) {
		// TODO: Stuff here
	}
}
