package cz.GravelCZLP.FarmBot.Core.Factory;

import org.spacehq.mc.auth.exception.request.RequestException;

import cz.GravelCZLP.FarmBot.Core.Factory.PacketWrappers.PacketWrapper1_11;
import cz.GravelCZLP.FarmBot.Versions.IUniversalProtocol;
import cz.GravelCZLP.FarmBot.Versions.Version;

public class ProtocolUniversalFactory {

	public static IUniversalProtocol auth(Version ver, String name, String pass) throws RequestException {
		switch (ver) {
		case VERSION_1_10:
			break;
		case VERSION_1_11:
			return new PacketWrapper1_11(name, pass);
		case VERSION_1_7:
			break;
		case VERSION_1_8:
			break;
		case VERSION_1_9:
			break;
		default:
			break;	
		}
		return null;
	}

	public static IUniversalProtocol auth(Version ver, String name) {
		switch (ver) {
		case VERSION_1_10:
			break;
		case VERSION_1_11:
			return new PacketWrapper1_11(name);
		case VERSION_1_7:
			break;
		case VERSION_1_8:
			break;
		case VERSION_1_9:
			break;
		default:
			break;	
		}
		return null;
	}
	
}
