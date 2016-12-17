package cz.GravelCZLP.MinecraftBot.Inventory;

import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;

public class Inventory implements IInventory {

	private ItemStack inventoryArray[];
	private ItemStack hotbar[];
	private ItemStack armor[];
	private ItemStack crafting[];
	private ItemStack offhand;
	
	private Bot bot;
	
	@Override
	public void deconstuctItemArrayToIvn(ItemStack[] array, Bot bot) {
		if (array.length < 45) {
			throw new IllegalArgumentException("ItemStack[].lenght < 45");
		}
		this.bot = bot;
		for (int i = 0; i <= 4; i++) {
			crafting[i] = array[i];
		}
		for (int i = 5; i <= 8; i++) {
			int armorInt = 0;
			armor[armorInt] = array[i];
			armorInt++;
		}
		for (int i = 9; i <= 35; i++) {
			int invInt = 0;
			inventoryArray[invInt] = array[i];
			invInt++;
		}
		for (int i = 36; i <= 44; i++) {
			int hotbarInt = 0;
			hotbar[hotbarInt] = array[i];
			hotbarInt++;
		}
		offhand = array[45];
	}
	
	@Override
	public void updateSlot(int slot, ItemStack item) {
		if (slot <= 4) {
			crafting[slot] = item;
		} else if (slot <= 8 && slot > 4) {
			armor[slot] = item;
		} else if (slot <= 9 && slot > 35) {
			inventoryArray[slot] = item;
		} else if (slot <= 36 && slot > 44) {
			hotbar[slot] = item;
		} else if (slot == 45) {
			offhand = item;
		} else {
			throw new IllegalArgumentException("Invalid lenght of slot index(slot:" + slot + ")");
		}
	}
	
	public ItemStack getItemInOffHand() {
		return offhand;
	}
	public ItemStack getItemInhand() {
		return hotbar[bot.currentSlotInHand];
	}
	public ItemStack getItemInInventoryAt(int index) {
		return inventoryArray[index];
	}
	public ItemStack getItemInHotbar(int index) {
		return hotbar[index];
	}
	public ItemStack getItemInArmor(int index) {
		return armor[index];
	}
	public ItemStack getItemInCrafting(int index) {
		return crafting[index];
	}
	
	
	public void setItemInOffhand(ItemStack item) {
		offhand = item;
	}
	public void setItemInHand(ItemStack item) {
		hotbar[bot.currentSlotInHand] = item;
	}
	public void setItemInInventoryAt(int index, ItemStack item) {
		inventoryArray[index] = item;
	}
	public void setItemInArmor(int index, ItemStack item) {
		armor[index] = item;
	}
	public void setItemInHotBar(int index, ItemStack item) {
		hotbar[index] = item;
	}
	public void setItemInCrafting(int index, ItemStack item) {
		crafting[index] = item;
	}
	
	
	public ItemStack[] getInventory() {
		return inventoryArray;
	}
	public ItemStack[] getHotbar() {
		return hotbar;
	}
	public ItemStack[] getCrafting() {
		return crafting;
	}
	public ItemStack[] getArmor() {
		return armor;
	}
	public void moveHotbar(int index) {
		ClientPlayerChangeHeldItemPacket p = new ClientPlayerChangeHeldItemPacket(index);
		bot.getSession().send(p);
	}
}
