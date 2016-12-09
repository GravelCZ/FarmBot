package cz.GravelCZLP.MinecraftBot.Inventory;

import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.window.WindowAction;
import org.spacehq.mc.protocol.data.game.window.WindowActionParam;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;

public class Inventory {

	protected int slots;
	protected ItemStack items[];
	protected ItemStack hotbar[];
	
	protected Bot bot;
	
	public Inventory(int slots, ItemStack[] items, Bot bot) {
		this.slots = slots;
		this.items = items;
		this.bot = bot;
	}
	public Inventory(int slots, Bot bot) {
		this(slots, null, bot);
	}
	public int getSlots() {
		return slots;
	}
	public void setSlots(int slots) {
		this.slots = slots;
	}
	public ItemStack[] getItems() {
		return items;
	}
	public void setItems(ItemStack[] items) {
		this.items = items;
	}
	public void updateSlot(int slot, ItemStack item) {
		items[slot] = item;
	}
	public ItemStack getItemAt(int slot) {
		return items[slot];
	}
	public boolean hasFreeSpace() {
		boolean freeSpace = false;
		for (ItemStack item : items) {
			if (item == null) {
				freeSpace = true;
				break;
			}
		}
		return freeSpace;
	}
	public int[] getFreeSlots() {
		int[] allSlots = new int[slots];
		int[] freeSlots = new int[slots];
		int currentSlot = 0;
		for (int s : allSlots) {
			if (getItemAt(s) == null) {
				freeSlots[currentSlot] = s;
				currentSlot++;
			}
		}
		return freeSlots;
	}
	public ItemStack[] getHotbar() {
		return hotbar;
	}
	public void setHotbar(ItemStack[] hotbar) {
		this.hotbar = hotbar;
	}
	
	public void moveInvHotbar(int windowId, int actionId, int slot, ItemStack clicked, WindowAction action, WindowActionParam param) {
		ClientWindowActionPacket packet = new ClientWindowActionPacket(windowId, actionId, slot, clicked, action, param);
		bot.getSession().send(packet);
	}
	public void moveHotbar(int index) {
		ClientPlayerChangeHeldItemPacket p = new ClientPlayerChangeHeldItemPacket(index);
		bot.getSession().send(p);
	}
}
