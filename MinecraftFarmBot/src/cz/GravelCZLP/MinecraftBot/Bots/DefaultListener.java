package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.Map;
import java.util.logging.Level;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.data.game.ResourcePackStatus;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.setting.ChatVisibility;
import com.github.steveice10.mc.protocol.data.game.setting.SkinPart;
import com.github.steveice10.mc.protocol.data.game.statistic.Statistic;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientResourcePackStatusPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDifficultyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerResourcePackSendPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerStatisticsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerUseBedPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerOpenWindowPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowPropertyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUnloadChunkPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerWorldBorderPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.ConnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectingEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.PacketSendingEvent;
import com.github.steveice10.packetlib.event.session.PacketSentEvent;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.packet.Packet;

import cz.GravelCZLP.MinecraftBot.Entites.Player;
import cz.GravelCZLP.MinecraftBot.Inventory.ChestInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.IInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.PlayerInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.WorkBenchInventory;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;
import cz.GravelCZLP.MinecraftBot.World.Border;
import cz.GravelCZLP.MinecraftBot.World.World.ChunkCoordinates;

public class DefaultListener implements SessionListener {

	protected Bot bot;
	
	public DefaultListener(Bot b) {
		this.bot = b;
	}
	
	@Override
	public void connected(ConnectedEvent e) {
	}

	@Override
	public void disconnected(DisconnectedEvent e) { 
		String why = e.getReason(); 
		bot.getLogger().log(Level.WARNING, "Bot was disconnected: " + why);
	}

	@Override
	public void disconnecting(DisconnectingEvent e) {
		e.getCause().printStackTrace();
	}

	@Override
	public void packetReceived(PacketReceivedEvent e) {
		Packet p = e.getPacket();
		Session s = e.getSession();
		if (p instanceof ServerJoinGamePacket) {
			bot.newWorld();
			
			ClientChatPacket pa = bot.isRegistered() ? new ClientChatPacket("/login " + bot.getPassword()) : new ClientChatPacket("/register passworld99 passworld99");
			s.send(pa);
			
			ClientSettingsPacket packet = new ClientSettingsPacket("en_US", 8, ChatVisibility.FULL, false, SkinPart.values(), Hand.MAIN_HAND);
			s.send(packet);
		} else if (p instanceof ServerChatPacket) {
			ServerChatPacket packet = (ServerChatPacket) p;
			String msg = packet.getMessage().getFullText();
			bot.getLogger().log(Level.FINE, msg);
		} else if (p instanceof ServerRespawnPacket) {
			ServerRespawnPacket packet = (ServerRespawnPacket) p;
			bot.setGameMode(packet.getGameMode());
			if (bot.getCurrentWorld().getDimension() != packet.getDimension()) {
				bot.getCurrentWorld().setDimension(packet.getDimension());
			}
			bot.getCurrentWorld().setDifficulty(packet.getDifficulty());
			bot.getCurrentWorld().setWorldType(packet.getWorldType());
		} else if (p instanceof ServerResourcePackSendPacket) {
			ClientResourcePackStatusPacket packet1 = new ClientResourcePackStatusPacket(ResourcePackStatus.ACCEPTED);
			ClientResourcePackStatusPacket packet2 = new ClientResourcePackStatusPacket(ResourcePackStatus.SUCCESSFULLY_LOADED);
			s.send(packet1);
			s.send(packet2);
		} else if (p instanceof ServerPlayerPositionRotationPacket) {
			ServerPlayerPositionRotationPacket packet = (ServerPlayerPositionRotationPacket) p;
			double x = packet.getX();
			double y = packet.getY();
			double z = packet.getZ();
			float yaw = packet.getYaw();
			float pitch = packet.getPitch();
			bot.setCurrentLoc(new EntityLocation(x, y, z, yaw, pitch, bot.getCurrentWorld()));
			ClientTeleportConfirmPacket confirm = new ClientTeleportConfirmPacket(packet.getTeleportId());
			bot.getSession().send(confirm);
			ClientPlayerPositionRotationPacket confirm2 = new ClientPlayerPositionRotationPacket(
														true, bot.getCurrentLoc().getX(), 
															bot.getCurrentLoc().getY(),
															bot.getCurrentLoc().getZ(),
															bot.getCurrentLoc().getYaw(), 
															bot.getCurrentLoc().getPitch());
			bot.getSession().send(confirm2);
			ClientRequestPacket p2 = new ClientRequestPacket(ClientRequest.STATS);
			bot.getSession().send(p2);
		} else if (p instanceof ServerPlayerHealthPacket) {
			ServerPlayerHealthPacket packet = (ServerPlayerHealthPacket) p;
			float health = packet.getHealth();
			if (health  <= 0) {
				ClientRequestPacket p2 = new ClientRequestPacket(ClientRequest.RESPAWN);
				bot.getSession().send(p2);
			}
			float food = packet.getFood();
			float saturation = packet.getSaturation();
			bot.setHealth(health);
			bot.setFood(food);
			bot.setSaturation(saturation);
		} else if (p instanceof ServerJoinGamePacket) {
			ServerJoinGamePacket packet = (ServerJoinGamePacket) p;
			
			bot.setSelfId(packet.getEntityId());
			
			bot.setReducedDebugInfo(packet.getReducedDebugInfo());
			bot.setMaxPlayers(packet.getMaxPlayers());
			bot.setGameMode(packet.getGameMode());
			
			bot.newWorld();
			bot.getCurrentWorld().setHardcore(packet.getHardcore());
			bot.getCurrentWorld().setDimension(packet.getDimension());
			bot.getCurrentWorld().setDifficulty(packet.getDifficulty());
			bot.getCurrentWorld().setWorldType(packet.getWorldType());
		} else if (p instanceof ServerDifficultyPacket) {
			ServerDifficultyPacket packet = (ServerDifficultyPacket) p;
			bot.getCurrentWorld().setDifficulty(packet.getDifficulty());
		} else if (p instanceof ServerRespawnPacket) {
			ServerRespawnPacket packet = (ServerRespawnPacket) p;
			if (bot.getCurrentWorld().getDimension() != packet.getDimension()) {
				bot.resetWorld();
				bot.getCurrentWorld().setDimension(packet.getDimension());
			} else {
				ClientChatPacket home = new ClientChatPacket("/home");
				bot.getSession().send(home);
			}
			bot.getCurrentWorld().setDifficulty(packet.getDifficulty());
			bot.setGameMode(packet.getGameMode());
			bot.getCurrentWorld().setWorldType(packet.getWorldType());
		} else if (p instanceof ServerStatisticsPacket) {
			ServerStatisticsPacket packet = (ServerStatisticsPacket) p;
			Map<Statistic, Integer> map = packet.getStatistics();
			bot.setStats(map);
		} else if (p instanceof ServerPlayerUseBedPacket) {
			ServerPlayerUseBedPacket packet = (ServerPlayerUseBedPacket) p;
			if (packet.getEntityId() == bot.getSelfId()) {
				bot.setSleeping(true);
				bot.setCurrentLoc(packet.getPosition(), bot.getCurrentWorld());
				return; // the only return ? :D so lonely :D
			} else {
				for (Player player : bot.nearbyPlayers) {
					if (player.getEntityId() == packet.getEntityId()) {
						player.setSleeping(true);	
					}
				}
			}
		}
		//Chunk packets 
		if (p instanceof ServerMultiBlockChangePacket) {
			ServerMultiBlockChangePacket packet = (ServerMultiBlockChangePacket) p;
			for (BlockChangeRecord data : packet.getRecords()) {
				bot.getCurrentWorld().setBlock(data.getPosition(), data.getBlock());
			}
		} else if (p instanceof ServerUnloadChunkPacket) {
			ServerUnloadChunkPacket packet = (ServerUnloadChunkPacket) p;
			ChunkCoordinates coords = new ChunkCoordinates(packet.getX(), packet.getZ());
			bot.getCurrentWorld().unloadColumn(coords);
		} else if (p instanceof ServerChunkDataPacket) {
			ServerChunkDataPacket data = (ServerChunkDataPacket) p;
			int x = data.getColumn().getX();
			int z = data.getColumn().getZ();
			ChunkCoordinates coords = new ChunkCoordinates(x, z);
			bot.getCurrentWorld().setBiomeData(coords, data.getColumn().getBiomeData());
			bot.getCurrentWorld().setTileEntities(coords, data.getColumn().getTileEntities());
			bot.getCurrentWorld().addChunkColumn(coords, data.getColumn());
		} else if (p instanceof ServerUpdateTimePacket) {
			ServerUpdateTimePacket packet = (ServerUpdateTimePacket) p;
			bot.getCurrentWorld().setAge(packet.getWorldAge());
			bot.getCurrentWorld().setTime(packet.getTime());
		} else if (p instanceof ServerBlockChangePacket) {
			ServerBlockChangePacket packet = (ServerBlockChangePacket) p;
			bot.getCurrentWorld().setBlock(packet.getRecord().getPosition(), packet.getRecord().getBlock());
		}
		
		if (p instanceof ServerWorldBorderPacket) {
			ServerWorldBorderPacket packet = (ServerWorldBorderPacket) p;
			switch (packet.getAction()) {
			case INITIALIZE:
				Border border = new Border(packet.getRadius(), packet.getOldRadius(), packet.getNewRadius(), packet.getSpeed(), packet.getCenterX(), packet.getCenterY(), packet.getPortalTeleportBoundary(), packet.getWarningTime(), packet.getWarningBlocks());
				bot.setBorder(border);
				break;
			case LERP_SIZE:
				Border b = bot.getBorder();
				b.setOldRadius(packet.getOldRadius());
				b.setNewRadius(packet.getNewRadius());
				b.setSpeed(packet.getSpeed());
				break;
			case SET_CENTER:
				Border b1 = bot.getBorder();
				b1.setCenterX(packet.getCenterX());
				b1.setCenterY(packet.getCenterY());
				break;
			case SET_SIZE:
				Border b2 = bot.getBorder();
				b2.setRadius(packet.getRadius());
				break;
			case SET_WARNING_BLOCKS:
				Border b3 = bot.getBorder();
				b3.setWarningBlocks(packet.getWarningBlocks());
				break;
			case SET_WARNING_TIME:
				Border b4 = bot.getBorder();
				b4.setWarningTime(packet.getWarningTime());
				break;
			default:
				break;
			}
		}
		
		//Inventory packets
		if (p instanceof ServerWindowItemsPacket) {
			ServerWindowItemsPacket packet = (ServerWindowItemsPacket) p;
			
			if (packet.getWindowId() == 0) {
				if (bot.getPlayerInventory() == null) {
					bot.setPlayerInventory(new PlayerInventory());
				}
				bot.getPlayerInventory().deconstuctItemArrayToIvn(packet.getItems(), bot);
			} else if (packet.getWindowId() == 1) {
				
			}
		} else if (p instanceof ServerSetSlotPacket) {
			ServerSetSlotPacket packet = (ServerSetSlotPacket) p;
			if (packet.getWindowId() == 0) {
				bot.getPlayerInventory().updateSlot(packet.getSlot(), packet.getItem());
			} else if (packet.getWindowId() == -1) {
				bot.getOpenedInventory().updateSlot(packet.getSlot(), packet.getItem());
			}
		} else if (p instanceof ServerOpenWindowPacket) {
			ServerOpenWindowPacket packet = (ServerOpenWindowPacket) p;
			IInventory inv = null;
			switch (packet.getType()) {
			case CHEST:
				inv = new ChestInventory();
				break;
			case CRAFTING_TABLE:
				inv = new WorkBenchInventory();
				break;
			case FURNACE:
				break;
			case GENERIC_INVENTORY:
				inv = new PlayerInventory();
				break;
			default:
				break;
			}
			bot.setOpendedInventory(inv);
			bot.setCurrentWindowId(packet.getWindowId());
		} else if (p instanceof ServerWindowPropertyPacket) {
			ServerWindowPropertyPacket packet = (ServerWindowPropertyPacket) p;
			
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}

	@Override
	public void packetSending(PacketSendingEvent event) {
		
	}

}
