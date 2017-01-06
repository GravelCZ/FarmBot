package cz.GravelCZLP.MinecraftBot.Utils;

import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.entity.player.Hand;
import org.spacehq.mc.protocol.data.game.entity.player.PlayerAction;
import org.spacehq.mc.protocol.data.game.world.block.BlockFace;
import org.spacehq.mc.protocol.data.game.world.block.BlockState;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientCloseWindowPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Inventory.Inventory;

public class SaveBotutils {

	public void instantHome(Bot bot) {
		ClientChatPacket packet = new ClientChatPacket("/home");
		bot.getSession().send(packet);
	}
	
	boolean hasFinished = false;
	
	public void placeWater(Bot bot) {
		Thread placeWaterThread = new Thread(new ThreadGroup("BotThreads:" + bot.getName()), new Runnable() {	
			@Override
			public void run() {
				ClientCloseWindowPacket closeWindow = new ClientCloseWindowPacket(bot.getCurrentWindowId());
				ClientPlayerChangeHeldItemPacket heldWaterBucket = null;
				Inventory inv = (Inventory) bot.getInventory();
				boolean foundBucket = false;
				for (int slot = 0; slot <= inv.getHotbar().length; slot++) {
					ItemStack s = inv.getItemInHotbar(slot);
					if (s.getId() == 326) {
						heldWaterBucket = new ClientPlayerChangeHeldItemPacket(slot);
						foundBucket = true;
						break;
					}
				}
				if (!foundBucket) {
					findClostestWater(bot);
				}
				BlockState state = bot.getCurrentWorld().getBlock(bot.getCurrentLoc());
				boolean onGround = true;
				if (state.getId() == 0) {
					onGround = false;
				}
				ClientPlayerPositionRotationPacket down = new ClientPlayerPositionRotationPacket(onGround, bot.getCurrentLoc().getX(), bot.getCurrentLoc().getY(),bot.getCurrentLoc().getZ(), 0, 90);
				bot.getSession().send(closeWindow);
				bot.getSession().send(heldWaterBucket);
				bot.getSession().send(down);
				ClientPlayerPlaceBlockPacket placeWater = new ClientPlayerPlaceBlockPacket(bot.getCurrentLoc().toPosition(), BlockFace.UP, Hand.MAIN_HAND, 0.5F, 0.5F, 0.5F);
				bot.getSession().send(placeWater);
				try {
					Thread.sleep(400L);
				} catch (InterruptedException e) { e.printStackTrace(); }
				ClientPlayerActionPacket action = new ClientPlayerActionPacket(PlayerAction.RELEASE_USE_ITEM, bot.getCurrentLoc().toPosition(), BlockFace.UP);
				bot.getSession().send(action);
				hasFinished = true;
			}
		}, "Place Water Thread");
		placeWaterThread.start();
		if (hasFinished) {
			try {
				placeWaterThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void findClostestWater(Bot bot) {
		//TODO:
	}
	
	public void decideAction(Bot bot) {
		if(bot.isOnFire()) {
			placeWater(bot);
		} else {
			ClientChatPacket packet = new ClientChatPacket("/home");
			bot.getSession().send(packet);
			bot.disconnect();
			bot.getLogger().warning("Bot: " + bot.getName() + " has disconnected because of low health!");
			bot.getLogger().info("Last known Info: " + bot.getCurrentLoc().toString());
		}
	}
	public void startThreadOfSafety(final Bot bot) {
		Thread safeThread = new Thread(new ThreadGroup("BotThreads:" + bot.getName()), new Runnable() {
			@Override
			public void run() {
				if (bot.getHealth() <= 4) {
					decideAction(bot);
				}
			}
		}, "Bot Safe Live Thread");
	}
}
