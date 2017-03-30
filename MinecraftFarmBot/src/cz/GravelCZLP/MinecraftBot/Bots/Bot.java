package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.statistic.Statistic;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import cz.GravelCZLP.MinecraftBot.Bots.Utils.MoveUtil;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Exporb;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Painting;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Inventory.IInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.WorkBenchInventory;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingRecipe;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingUtils;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingUtils.CraftableMaterials;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.Border;
import cz.GravelCZLP.MinecraftBot.World.World;

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
	
	private boolean reducedDebugInfo;
	private int maxPlayers;
	
	private World currentWorld;
	
    private boolean invincible;
    private boolean canFly;
    private boolean flying;
    private boolean creative;
    private float flySpeed;
    private float walkSpeed;
	
	private IInventory openedInventory;
	private int currentWindowId;
	
	private Map<Statistic, Integer> stats;

	public int currentSlotInHand;
	
	public EntityLocation lastDeathLocation;
	
	public List<Player> nearbyPlayers = new ArrayList<Player>();
	public List<cz.GravelCZLP.MinecraftBot.Entites.Object> nearbyObjects = new ArrayList<cz.GravelCZLP.MinecraftBot.Entites.Object>();
	public List<Painting> nearbyPaintings = new ArrayList<Painting>();
	public List<Mob> nearbyMobs = new ArrayList<Mob>();
	public List<Exporb> nerbyXPs = new ArrayList<Exporb>();
	public List<Entity> allEntities = new ArrayList<Entity>();
	
	private float experience;
	private int level;
	private int totalExperience;
	
	private Border border;
	
	private boolean isSleeping;
	
	public MoveUtil moveUtil;
	
	public Bot(String host, int port, MinecraftProtocol p) {
		Client c = new Client(host, port, p, new TcpSessionFactory());
		name = p.getProfile().getName();
		session = c.getSession();
		addListener(new DefaultListener(this));
		addListener(new EntityPacketsListener(this));
		logger = Logger.getLogger(name);
		moveUtil = new MoveUtil(this);
	}
	
	public void craft(CraftableMaterials mat, int id) {
		WorkBenchInventory inv = null;
		if (openedInventory instanceof WorkBenchInventory) {
			inv = (WorkBenchInventory) openedInventory;
		}
		CraftingRecipe recipe = CraftingUtils.getRecipe(mat, id);
		inv.craft(recipe);
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

	public void setCurrentLoc(Position pos) {
		EntityLocation newLoc = new EntityLocation(pos);
		newLoc.setYaw(currentLoc.getYaw());
		newLoc.setPitch(currentLoc.getPitch());
		currentLoc = newLoc;
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
	public void setName(String name) {
		this.name = name;
	}

	public IInventory getInventory() {
		return openedInventory;
	}

	public void setInventory(IInventory inv) {
		this.openedInventory = inv;
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

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}
	public Map<Statistic, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<Statistic, Integer> stats) {
		this.stats = stats;
	}

	public World getCurrentWorld() {
		return currentWorld;
	}
	
	public void resetWorld() {
		currentWorld = null;
		currentWorld = new World();
	}
	
	public void newWorld() {
		currentWorld = new World();
	}

	public int getCurrentWindowId() {
		return currentWindowId;
	}
	
	public void setCurrentWindowId(int i) {
		currentWindowId = i;
	}
	public void setSleeping(boolean s) {
		this.isSleeping = s;
	}
	public boolean isSleeping() {
		return this.isSleeping;
	}

	public boolean isOnFire() {
		return false;
	}
}
