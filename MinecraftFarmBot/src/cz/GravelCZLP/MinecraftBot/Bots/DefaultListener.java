package cz.GravelCZLP.MinecraftBot.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.spacehq.mc.protocol.data.game.ResourcePackStatus;
import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.setting.ChatVisibility;
import org.spacehq.mc.protocol.data.game.setting.SkinPart;
import org.spacehq.mc.protocol.data.game.world.block.BlockChangeRecord;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientResourcePackStatusPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import org.spacehq.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerResourcePackSendPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerOpenWindowPacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectingEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionListener;
import org.spacehq.packetlib.packet.Packet;

import cz.GravelCZLP.MinecraftBot.Inventory.ChestInventory;
import cz.GravelCZLP.MinecraftBot.Inventory.Inventory;
import cz.GravelCZLP.MinecraftBot.Utils.EntityLocation;

public class DefaultListener implements SessionListener {

	protected Bot bot;
	
	public DefaultListener(Bot b) {
		this.bot = b;
	}
	
	@Override
	public void connected(ConnectedEvent e) {
		Session s = e.getSession();
		ClientChatPacket p = bot.isRegistered() ? new ClientChatPacket("/login " + bot.getPassword()) : new ClientChatPacket("/register passworld99 passworld99");
		s.send(p);
		
		List<SkinPart> list = new ArrayList<SkinPart>();
		
		for (SkinPart sp : SkinPart.values()) {
			list.add(sp);
		}
		
		ClientSettingsPacket packet = new ClientSettingsPacket("null", 8, ChatVisibility.FULL, true, (SkinPart[])list.toArray(), Hand.MAIN_HAND);
		s.send(packet);
	}

	@Override
	public void disconnected(DisconnectedEvent e) { 
		String why = e.getReason(); 
		bot.getLogger().log(Level.WARNING, "Bot byl Odpojen: " + why);
	}

	@Override
	public void disconnecting(DisconnectingEvent arg0) {}

	@Override
	public void packetReceived(PacketReceivedEvent e) {
		Packet p = e.getPacket();
		Session s = e.getSession();
		if (p instanceof ServerChatPacket) {
			ServerChatPacket packet = (ServerChatPacket) p;
			String msg = packet.getMessage().getFullText();
			bot.getLogger().log(Level.FINE, msg);
		} else if (p instanceof ServerRespawnPacket) {
			ServerRespawnPacket packet = (ServerRespawnPacket) p;
			GameMode mode = packet.getGameMode();
			bot.setGameMode(mode);
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
			bot.setCurrentLoc(new EntityLocation(x, y, z, yaw, pitch));
			ClientTeleportConfirmPacket confirm = new ClientTeleportConfirmPacket(packet.getTeleportId());
			bot.getSession().send(confirm);
		} else if (p instanceof ServerPlayerHealthPacket) {
			ServerPlayerHealthPacket packet = (ServerPlayerHealthPacket) p;
			float health = packet.getHealth();
			float food = packet.getFood();
			float saturation = packet.getSaturation();
			bot.setHealth(health);
			bot.setFood(food);
			bot.setSaturation(saturation);
		}
		//Chunk packets 
		if (p instanceof ServerChunkDataPacket) {
			ServerChunkDataPacket packet = (ServerChunkDataPacket) p;
			bot.chunks.add(packet.getColumn());
		} else if (p instanceof ServerBlockChangePacket) {
			ServerBlockChangePacket packet = (ServerBlockChangePacket) p;
			bot.updateBlock(packet.getRecord());
		} else if (p instanceof ServerMultiBlockChangePacket) {
			ServerMultiBlockChangePacket packet = (ServerMultiBlockChangePacket) p;
			for (BlockChangeRecord data : packet.getRecords()) {
				bot.updateBlock(data);
			}
		}
		//Inventory packets
		if (p instanceof ServerWindowItemsPacket) {
			ServerWindowItemsPacket packet = (ServerWindowItemsPacket) p;
			if (packet.getWindowId() == 0) {
				bot.getInventory().setItems(packet.getItems());
			}
		} else if (p instanceof ServerSetSlotPacket) {
			ServerSetSlotPacket packet = (ServerSetSlotPacket) p;
			if (packet.getWindowId() == 0) {
				bot.getInventory().updateSlot(packet.getSlot(), packet.getItem());
			}
		} else if (p instanceof ServerOpenWindowPacket) {
			ServerOpenWindowPacket packet = (ServerOpenWindowPacket) p;
			Inventory inv = new Inventory(packet.getSlots(), bot);
			bot.currentWindowId = packet.getWindowId();
			bot.currentOpenedInventory = inv;
		} else if (p instanceof ServerWindowItemsPacket) {
			ServerWindowItemsPacket packet = (ServerWindowItemsPacket) p;
			ItemStack[] chestItems = new ItemStack[54];
			for (int i = 0; i < 54; i++) {
				chestItems[i] = packet.getItems()[i];
			}
			ItemStack[] inventoryItems = new ItemStack[27];
			for (int i = 54; i < (54 + 27); i++) {
				int slot = 0;
				inventoryItems[slot] = packet.getItems()[i];
				slot++;
			}
			ItemStack[] hotbar = new ItemStack[8];
			for (int i = (54 + 27); i < 8; i++) {
				int slot = 0;
				hotbar[slot] = packet.getItems()[i];
				slot++;
			}
			bot.currentOpenedInventory.setHotbar(hotbar);
			bot.currentOpenedInventory.setItems(inventoryItems);
			((ChestInventory) bot.currentOpenedInventory).setChestItems(chestItems);
		}
	}

	@Override
	public void packetSent(PacketSentEvent arg0) {}

}
