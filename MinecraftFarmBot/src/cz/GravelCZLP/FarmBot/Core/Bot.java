package cz.GravelCZLP.FarmBot.Core;

import java.net.Proxy;
import java.util.logging.Logger;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import cz.GravelCZLP.FarmBot.Core.Exceptions.UnknownVersionException;
import cz.GravelCZLP.FarmBot.Versions.IUniversalProtocol;

public class Bot {

	private final Proxy proxy;
	private final Logger logger;
	private final IUniversalProtocol protocol;
	
	private Session session;
	private EntityLocation currentLoc;
	private float health = -1;
	private float food = -1;
	
	private boolean isAutoRegister;
	
	public Bot(IUniversalProtocol protocol) {
		this(protocol, Proxy.NO_PROXY);
	}
	
	public Bot(IUniversalProtocol protocol, Proxy proxy) {
		this.protocol = protocol;
		this.proxy = proxy;
		
		this.logger = Logger.getLogger("FarmBot");
	}
	
	public void connect(String host, int port) throws UnknownVersionException {
		connect(host, port, 5000);
	}
	
	public void connect(String host, int port, int timeout) throws UnknownVersionException {
		Client client = new Client(host, port, protocol.getProtocol(), new TcpSessionFactory(proxy));
		this.session = client.getSession();
		
		client.getSession().setConnectTimeout(timeout);
		client.getSession().setReadTimeout(timeout);
		client.getSession().setWriteTimeout(timeout);
		
		switch (protocol.getVersion()) {
		case VERSION_1_10:
			
			break;
		case VERSION_1_11:
			
			break;
		case VERSION_1_7:
			
			break;
		case VERSION_1_8:
			
			break;
		case VERSION_1_9:
			
			break;
		default:
			throw new UnknownVersionException("Version:" + protocol.getVersion().name() + " is not a valid version");
		}
		client.getSession().connect();
	}
	
	public void sendMessage(String message) {
		if (session != null) {
			ClientChatPacket packet = new ClientChatPacket(message);
			session.send(packet);
		}
	}

    public boolean isOnline() {
        return session != null && session.isConnected();
    }

    public Session getSession() {
        return session;
    }

    public EntityLocation getLocation() {
        return currentLoc;
    }

    public void setLocation(EntityLocation currentLoc) {
        this.currentLoc = currentLoc;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getFood() {
        return food;
    }

    public void setFood(float food) {
        this.food = food;
    }

    public Logger getLogger() {
        return logger;
    }

    public GameProfile getGameProfile() {
        return protocol.getGameProfile();
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void disconnect() {
        if (session != null) {
            session.disconnect("Disconnect");
        }
    }
    
    public boolean isAutoRegister() {
    	return isAutoRegister;
    }
}
