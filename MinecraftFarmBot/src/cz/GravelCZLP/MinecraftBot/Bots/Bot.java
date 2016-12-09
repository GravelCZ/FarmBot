package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Exporb;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Painting;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public abstract class Bot {

	private Session session;
	private EntityLocation currentLoc;
	private float health;
	private float food;
	private float saturation;
	private String name;
	private boolean registered;
	private boolean isAlive;
	private GameMode gamemode;
	private String password;
	private Logger logger;
	
	public List<Player> nearbyPlayers = new ArrayList<Player>();
	public List<cz.GravelCZLP.MinecraftBot.Entites.Object> nearbyObjects = new ArrayList<cz.GravelCZLP.MinecraftBot.Entites.Object>();
	public List<Painting> nearbyPaintings = new ArrayList<Painting>();
	public List<Mob> nearbyMobs = new ArrayList<Mob>();
	public List<Exporb> nerbyXPs = new ArrayList<Exporb>();
	public List<Entity> allEntities = new ArrayList<Entity>();
	
	public Bot(String host, int port, MinecraftProtocol p) {
		Client c = new Client(host, port, p, new TcpSessionFactory());
		name = p.getProfile().getName();
		session = c.getSession();
		addListener(new DefaultListener(this));
		addListener(new EntityPacketsListener(this));
		logger = Logger.getLogger(name);
	}
	
	public void addListener(SessionListener l) {
		session.addListener(l);
	}
	
	public void connect() {
		session.connect();
	}
	
	public void disconnect() {
		session.disconnect("Disconnected!");
	}
	
	public abstract BotType getType();
	
	public String getName() {
		return name;
	}

	public EntityLocation getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(EntityLocation currentLoc) {
		this.currentLoc = currentLoc;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Logger getLogger() {
		return logger;
	}
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean a) {
		isAlive = a;
	}

	public void setGameMode(GameMode mode) {
		gamemode = mode;
	}
	
	public GameMode getGameMode() {
		return gamemode;
	}

	public Session getSession() {
		return session;
	}

	public float getHealth() {
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

	public float getSaturation() {
		return saturation;
	}

	public void setSaturation(float saturation) {
		this.saturation = saturation;
	}

	public GameMode getGamemode() {
		return gamemode;
	}

	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}

	public void setName(String name) {
		this.name = name;
	}
}
