package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.entity.player.PlayerAction;
import com.github.steveice10.mc.protocol.data.game.statistic.Statistic;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockFace;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;

import cz.GravelCZLP.MinecraftBot.Bots.Utils.MoveUtil;
import cz.GravelCZLP.MinecraftBot.Entites.Entity;
import cz.GravelCZLP.MinecraftBot.Entites.Exporb;
import cz.GravelCZLP.MinecraftBot.Entites.Mob;
import cz.GravelCZLP.MinecraftBot.Entites.Painting;
import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Inventory.IInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.PlayerInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.WorkBenchInventory;
import cz.GravelCZLP.MinecraftBot.Managers.BotManager.BotType;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingRecipe;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingUtils;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingUtils.CraftableMaterials;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.Border;
import cz.GravelCZLP.MinecraftBot.World.World;

public abstract class Bot implements Runnable {

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
	private PlayerInventory playerInventory;
	private int currentWindowId;

	private Map<Statistic, Integer> stats;

	public int currentSlotInHand;

	public EntityLocation lastDeathLocation;

	public List<Player> nearbyPlayers = new ArrayList<Player>();
	public List<cz.GravelCZLP.MinecraftBot.Entites.Object> nearbyObjects = new ArrayList<cz.GravelCZLP.MinecraftBot.Entites.Object>();
	public List<Painting> nearbyPaintings = new ArrayList<Painting>();
	public List<Mob> nearbyMobs = new ArrayList<Mob>();
	public List<Exporb> nerbyXPs = new ArrayList<Exporb>();

	private float experience;
	private int level;
	private int totalExperience;

	private Border border;

	private boolean isSleeping;

	public MoveUtil moveUtil;

	private Thread thisThread = new Thread(this);
	
	private boolean isWorldFullyLoaded = false;

	public Bot(String host, int port, MinecraftProtocol p) {
		Client c = new Client(host, port, p, new TcpSessionFactory());

		name = p.getProfile().getName();
		session = c.getSession();

		addListener(new DefaultListener(this));
		addListener(new EntityPacketsListener(this));

		logger = Logger.getLogger(name);

		moveUtil = new MoveUtil(this);
		thisThread.setName("Bot: " + p.getProfile().getName());
	}

	public void craft(CraftableMaterials mat, int id) {
		WorkBenchInventory inv = null;
		if (openedInventory instanceof WorkBenchInventory) {
			inv = (WorkBenchInventory) openedInventory;
		}
		CraftingRecipe recipe = CraftingUtils.getRecipe(mat, id);
		inv.craft(recipe);
	}

	public void drop(boolean stack, boolean all) {
		PlayerAction action = null;
		if (stack) {
			action = PlayerAction.DROP_ITEM_STACK;
		} else {
			action = PlayerAction.DROP_ITEM;
		}
		if (all) {
			for (int i = 0; i < 8; i++) {
				playerInventory.moveHotbar(i);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ClientPlayerActionPacket p2 = new ClientPlayerActionPacket(action, getCurrentLoc().toPosition(), BlockFace.DOWN);
				session.send(p2);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			ClientPlayerActionPacket p = new ClientPlayerActionPacket(action, getCurrentLoc().toPosition(), BlockFace.DOWN);
			session.send(p);
		}
	}
	
	@Override
	public void run() {
		/*while (session.isConnected()) {

			if (getCurrentLoc() != null) {
				if (!nearbyPlayers.isEmpty()) {
					Iterator<Player> iter = nearbyPlayers.iterator();

					while (iter.hasNext()) {
						Player p = iter.next();
						double dx = p.getLocation().getX() - getCurrentLoc().getX();
						double dy = p.getLocation().getY() - getCurrentLoc().getY();
						double dz = p.getLocation().getZ() - getCurrentLoc().getZ();
						double dist = Math.sqrt(MathHelp.square(dx) + MathHelp.square(dy) + MathHelp.square(dz));
						if (dist < 20) {
							GuardUtils.faceEntity(p, this);
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		}*/
	}
	
	public void addListener(SessionListener l) {
		session.addListener(l);
	}

	public void connect() {
		session.connect();
		thisThread.start();
	}

	public void disconnect() {
		session.disconnect("Disconnected!");
	}

	public abstract BotType getType();

	public List<Entity> getAllEntities() {
		ArrayList<Entity> allEnts = new ArrayList<>();
		allEnts.addAll(nearbyPlayers);
		allEnts.addAll(nearbyObjects);
		allEnts.addAll(nearbyPaintings);
		allEnts.addAll(nearbyMobs);
		allEnts.addAll(nerbyXPs);
		return Collections.unmodifiableList(allEnts);
	}
	
	public String getName() {
		return name;
	}

	public EntityLocation getCurrentLoc() {
		return currentLoc;
	}

	public void setCurrentLoc(EntityLocation currentLoc) {
		this.currentLoc = currentLoc;
	}

	public void setCurrentLoc(Position pos, World w) {
		EntityLocation newLoc = new EntityLocation(pos, w);
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

	public PlayerInventory getPlayerInventory() {
		return playerInventory;
	}
	
	public void setPlayerInventory(PlayerInventory inv) {
		this.playerInventory = inv;
	}
	
	public IInventory getOpenedInventory() {
		return openedInventory;
	}

	public void setOpendedInventory(IInventory inv) {
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
	
	public boolean isWorldFullyLoaded() {
		return isWorldFullyLoaded;
	}

	public void setWorldFullyLoaded(boolean isWorldFullyLoaded) {
		this.isWorldFullyLoaded = isWorldFullyLoaded;
	}
}
