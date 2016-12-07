package cz.GravelCZLP.FarmBot.Versions;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.packetlib.packet.PacketProtocol;

public interface IUniversalProtocol {

	GameProfile getGameProfile();
	
	PacketProtocol getProtocol();
	
	Version getVersion();
	
}
