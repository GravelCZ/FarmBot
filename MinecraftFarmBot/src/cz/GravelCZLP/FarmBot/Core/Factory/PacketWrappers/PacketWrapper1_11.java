package cz.GravelCZLP.FarmBot.Core.Factory.PacketWrappers;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.packet.PacketProtocol;

import cz.GravelCZLP.FarmBot.Versions.IUniversalProtocol;
import cz.GravelCZLP.FarmBot.Versions.Version;

public class PacketWrapper1_11 extends MinecraftProtocol implements IUniversalProtocol {
	
	public PacketWrapper1_11(String name, String pass) throws RequestException {
		super(name, pass);
	}
	
	public PacketWrapper1_11(String name) {
		super(name);
	}

	@Override
	public GameProfile getGameProfile() {
		return super.getProfile();
	}

	@Override
	public PacketProtocol getProtocol() {
		return this;
	}

	@Override
	public Version getVersion() {
		return Version.VERSION_1_11;
	}

}
