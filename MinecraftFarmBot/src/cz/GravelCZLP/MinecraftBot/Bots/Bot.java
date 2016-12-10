package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.chunk.Column;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.world.WorldType;
import org.spacehq.mc.protocol.data.game.world.block.BlockFace;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Exporb;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Painting;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Inventory.Inventory;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.Border;

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
	
	private int selfId;
	
	private int dimension;
	private boolean hardcore;
	private Difficulty difficulty;
	private boolean reducedDebugInfo;
	private int maxPlayers;
	private WorldType worldType;
	
    private boolean invincible;
    private boolean canFly;
    private boolean flying;
    private boolean creative;
    private float flySpeed;
    private float walkSpeed;
	
	public int currentWindowId;
	public Inventory currentOpenedInventory;
	
	public int currentSlotInHand;
	
	public List<Player> nearbyPlayers = new ArrayList<Player>();
	public List<cz.GravelCZLP.MinecraftBot.Entites.Object> nearbyObjects = new ArrayList<cz.GravelCZLP.MinecraftBot.Entites.Object>();
	public List<Painting> nearbyPaintings = new ArrayList<Painting>();
	public List<Mob> nearbyMobs = new ArrayList<Mob>();
	public List<Exporb> nerbyXPs = new ArrayList<Exporb>();
	public List<Entity> allEntities = new ArrayList<Entity>();
	
	public List<Column> chunks = new ArrayList<Column>();
	
	private Inventory inventory;
	
	private float experience;
	private int level;
	private int totalExperience;
	
	private Border border;
	
	public Bot(String host, int port, MinecraftProtocol p) {
		Client c = new Client(host, port, p, new TcpSessionFactory());
		name = p.getProfile().getName();
		session = c.getSession();
		addListener(new DefaultListener(this));
		addListener(new EntityPacketsListener(this));
		logger = Logger.getLogger(name);
		startHealthLoop();
	}
	
	long lastCheck;
	
	public void startHealthLoop() {
		while ((System.currentTimeMillis() + 500) > lastCheck) {
			if (health <= 0) {
				setAlive(false);
				
			}
			if (health <= 4) {
				ServerChatPacket home = new ServerChatPacket("/home");
				session.send(home);
				ClientPlayerPositionRotationPacket down = new ClientPlayerPositionRotationPacket(true, currentLoc.getX(), currentLoc.getY(), currentLoc.getZ(), 0, 90);
				session.send(down);
				boolean foundBukket = false;
				for (int i = 0; i < inventory.getHotbar().length; i++) {
					ItemStack item = inventory.getHotbar()[i];
					if (item.getId() == 326) {
						ClientPlayerChangeHeldItemPacket held = new ClientPlayerChangeHeldItemPacket(i);
						session.send(held);;
						foundBukket = true;
						break;
					}
				}
				if (!foundBukket) {
					session.disconnect("");
				}
				ClientPlayerPlaceBlockPacket water = new ClientPlayerPlaceBlockPacket(new Position((int)currentLoc.getX(), (int)currentLoc.getY(), (int)currentLoc.getZ()), BlockFace.UP, Hand.MAIN_HAND, 0.5F, 0.5F, 0.5F);
				session.send(water);
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < inventory.getHotbar().length; i++) {
					ItemStack item = inventory.getHotbar()[i];
					if (item.getId() == 325) {
						ClientPlayerChangeHeldItemPacket held = new ClientPlayerChangeHeldItemPacket(i);
						session.send(held);
						break;
					}
				}
				ClientPlayerUseItemPacket use = new ClientPlayerUseItemPacket(Hand.MAIN_HAND);
				session.send(use);
			}
		}
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
	
	/*public void updateBlock(BlockChangeRecord rec) {
		int chunkX = rec.getPosition().getX() / 16;
		int chunkY = rec.getPosition().getY() / 16;
		int chunkZ = rec.getPosition().getZ() / 16;
		
		int blockX = rec.getPosition().getX() % 16;
		int blockY = rec.getPosition().getY() % 16;
		int blockZ = rec.getPosition().getZ() % 16;
	}
	
	public BlockState getBlockState(int x, int y, int z) {
		int blockX = x % 16;
		int blockY = y % 16;
		int blockZ = z % 16;
		return null;
	}*/
	
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

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isCanFly() {
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public boolean isCreative() {
		return creative;
	}

	public void setCreative(boolean creative) {
		this.creative = creative;
	}

	public float getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(float flySpeed) {
		this.flySpeed = flySpeed;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(float walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(int totalExperience) {
		this.totalExperience = totalExperience;
	}

	public int getSelfId() {
		return selfId;
	}

	public void setSelfId(int selfId) {
		this.selfId = selfId;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public boolean isHardcore() {
		return hardcore;
	}

	public void setHardcore(boolean hardcore) {
		this.hardcore = hardcore;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isReducedDebugInfo() {
		return reducedDebugInfo;
	}

	public void setReducedDebugInfo(boolean reducedDebugInfo) {
		this.reducedDebugInfo = reducedDebugInfo;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public WorldType getWorldType() {
		return worldType;
	}

	public void setWorldType(WorldType worldType) {
		this.worldType = worldType;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}
}
