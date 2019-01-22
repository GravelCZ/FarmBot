package cz.GravelCZLP.MinecraftBot.Inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCloseWindowPacket;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;

public class ChestInventory implements IInventory {

	private ItemStack contents[];
	private ItemStack hotbar[];
	private ItemStack inventory[];
	
	private boolean isLargeChest;
	
	@Override
	public void deconstuctItemArrayToIvn(ItemStack[] array, Bot bot) {
		if (array.length == 62) {
			isLargeChest = false;
			for (int i = 0; i <= 26; i++) {
				contents[i] = array[i];
			}
			for (int i = 27; i <= 53; i++) {
				int invInt = 0;
				inventory[invInt] = array[i];
				invInt++;
			}
			for(int i = 54; i <= 62; i++) {
				int hotbarInt = 0;
				hotbar[hotbarInt] = array[i];
				hotbarInt++;
			}
		} else if (array.length == 89) {
			isLargeChest = true;
			for (int i = 0; i <= 53; i++) {
				contents[i] = array[i];
			}
			for (int i = 54; i <= 80; i++) {
				int mainInv = 0;
				inventory[mainInv] = array[i];
				mainInv = 0;
			}
			for (int i = 81; i <= 89; i++) {
				int hotbarInt = 0;
				hotbar[hotbarInt] = array[i];
			}
		} else {
			ClientCloseWindowPacket p = new ClientCloseWindowPacket(bot.getCurrentWindowId());
			bot.getSession().send(p);
		}
	}
	
	@Override
	public void updateSlot(int slot, ItemStack item) {
		if (isLargeChest) {
			if (slot <= 26) {
				contents[slot] = item;
			} else if (slot <= 53 && slot > 27) {
				inventory[slot] = item;
			} else if (slot <= 54 && slot > 62) {
				hotbar[slot] = item;
			}
		} else {
			if (slot <= 53) {
				contents[slot] = item;
			} else if (slot <= 54 && slot > 53) {
				inventory[slot] = item;
			} else if (slot <= 81 && slot > 53) {
				hotbar[slot] = item;
			}
		}
	}
	
	public ItemStack getItemInHotbarAt(int index) {
		return hotbar[index];
	}
	
	public ItemStack getItemInInventoryAt(int index) {
		return inventory[index];
	}
	
	public ItemStack getItemInChestAt(int index) {
		return contents[index];
	}
	
	public ItemStack[] getItemsInChest() {
		return contents;
	}
	public ItemStack[] getItemsInInventory() {
		return inventory;
	}
	public ItemStack[] getItemsInHotbar() {
		return hotbar;
	}
	public void setItemInChestAt(int index, ItemStack item) {
		contents[index] = item;
	}
	public void setItemInInventoryAt(int index, ItemStack item) {
		inventory[index] = item;
	}
	public void setItemInHotBarAt(int index, ItemStack item) {
		hotbar[index] = item;
	}
	public boolean isLargeChest() {
		return isLargeChest;
	}
}
