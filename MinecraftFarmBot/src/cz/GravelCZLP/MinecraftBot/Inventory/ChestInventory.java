package cz.GravelCZLP.MinecraftBot.Inventory;

import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;

public class ChestInventory extends Inventory {

	private int chestSlots;
	private ItemStack chestItems[];
	
	public ChestInventory(int slots, Bot bot) {
		super(slots, bot);
	}

	public ChestInventory(int slots, ItemStack[] items, Bot bot) {
		super(slots, items, bot);
	}

	public int getChestSlots() {
		return chestSlots;
	}

	public void setChestSlots(int chestSlots) {
		this.chestSlots = chestSlots;
	}

	public ItemStack[] getChestItems() {
		return chestItems;
	}

	public void setChestItems(ItemStack[] chestItems) {
		this.chestItems = chestItems;
	}
	
	public void setChestItemAt(int slot, ItemStack item) {
		chestItems[slot] = item;
	}
	public ItemStack getChestItemAt(int slot) {
		return chestItems[slot];
	}
}
