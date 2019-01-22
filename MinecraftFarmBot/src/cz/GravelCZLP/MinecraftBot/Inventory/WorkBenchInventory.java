package cz.GravelCZLP.MinecraftBot.Inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Utils.CraftingRecipe;

public class WorkBenchInventory implements IInventory, ICraftable {

	private ItemStack workbenchGUI[];
	private ItemStack inventory[];
	private ItemStack hotbar[];
	private ItemStack craftingOutput;
	
	private Bot bot;
	
	@Override
	public void deconstuctItemArrayToIvn(ItemStack[] array, Bot bot) {
		this.bot = bot;
		for (int i = 1; i <= 9; i++) {
			workbenchGUI[i] = array[i];
		}
		craftingOutput = array[0];
		for (int i = 10; i <= 36; i++) {
			int invInt = 0;
			inventory[invInt] = array[i];
			invInt++;
		}
		for (int i = 37; i <= 45; i++) {
			int hotbarInt = 0;
			hotbar[hotbarInt] = array[i];
			hotbarInt++;
		}
	}

	@Override
	public void updateSlot(int slot, ItemStack item) {
		if (slot == 0) {
			craftingOutput = item;
		} else if (slot <= 9 && slot > 0) {
			workbenchGUI[slot] = item;
		} else if (slot <= 10 && slot > 9) {
			inventory[slot] = item;
		} else if (slot <= 37 && slot > 37) {
			hotbar[slot] = item;
		} else {
			throw new IllegalArgumentException("Invalid slot lenght(" + slot + ")");
		}
	}

	public ItemStack getItemAtWorkbenchGUI(int index) {
		return workbenchGUI[index];
	}
	public ItemStack getItemInInventory(int index) {
		return inventory[index];
	}
	public ItemStack getItemInHotbar(int index) {
		return hotbar[index];
	}
	public ItemStack getCurrentItemInhand() {
		return hotbar[bot.currentSlotInHand];
	}
	public ItemStack getOutput() {
		return craftingOutput;
	}
	
	
	public void setItemInWorkBenchGUI(int index, ItemStack item) {
		workbenchGUI[index] = item;
	}
	public void setItemInInventory(int index, ItemStack item) {
		inventory[index] = item;
	}
	public void setItemInHotbar(int index, ItemStack item) {
		hotbar[index] = item;
	}
	public void setItemInOutput(ItemStack item) {
		craftingOutput = item;
	}
	
	
	public ItemStack[] getInventoryArray() {
		return inventory;
	}
	public ItemStack[] getCraftingArray() {
		return workbenchGUI;
	}
	public ItemStack[] getHotbarArray() {
		return hotbar;
	}
	
	@Override
	public void craft(CraftingRecipe recipe) {
		String[] lines = recipe.getLines();
		String[] separatedLine1 = lines[0].split(" ");
		String[] separatedLine2 = lines[1].split(" ");
		String[] separatedLine3 = lines[2].split(" ");
		
		int line1Slot1 = getSlotWithItem(Integer.parseInt(separatedLine1[0]));
		int line1Slot2 = getSlotWithItem(Integer.parseInt(separatedLine1[1]));
		int line1Slot3 = getSlotWithItem(Integer.parseInt(separatedLine1[2]));
		
		int line2Slot1 = getSlotWithItem(Integer.parseInt(separatedLine2[0]));
		int line2Slot2 = getSlotWithItem(Integer.parseInt(separatedLine2[1]));
		int line2Slot3 = getSlotWithItem(Integer.parseInt(separatedLine2[2]));
		
		int line3Slot1 = getSlotWithItem(Integer.parseInt(separatedLine3[0]));
		int line3Slot2 = getSlotWithItem(Integer.parseInt(separatedLine3[1]));
		int line3Slot3 = getSlotWithItem(Integer.parseInt(separatedLine3[2]));
		
		int[] line1 = new int[] {line1Slot1, line1Slot2, line1Slot3};
		int[] line2 = new int[] {line2Slot1, line2Slot2, line2Slot3};
		int[] line3 = new int[] {line3Slot1, line3Slot2, line3Slot3};
		
		//loop 1st line
		for (int i = 0; i < line1.length; i++) {
			
		}
		
		//loop 2nd line
		for (int i = 0; i < line2.length; i++) {
			
		}
		
		//loop 3rd line
		for (int i = 0; i < line3.length; i++) {
			
		}
	}
	
	public ItemStack getItemFromID(String id) {
		return null;
	}
	
	public int getSlotWithItem(int type) {
		for (int slot = 0; slot <= inventory.length; slot++) {
			ItemStack currentItem = inventory[slot];
			if (currentItem == null) {
				continue;
			}
			if (currentItem.getId() == type) {
				return slot;
			}
		}
		return -1;
	}
	
	public int getSlotWithItemAndAmount(int type, int amount) {
		for (int slot = 0; slot <= inventory.length; slot++) {
			ItemStack currentItem = inventory[slot];
			if (currentItem == null) {
				continue;
			}
			if (currentItem.getId() == type && currentItem.getAmount() >= amount) {
				return slot;
			}
		}
		return -1;
	}
	
	public int getSlotWithItemAndData(int type, int data) {
		for (int slot = 0; slot < inventory.length; slot++) {
			ItemStack currentItem = inventory[slot];
			if (currentItem == null) {
				continue;
			}
			if (currentItem.getId() == type && currentItem.getData() == data) {
				return slot;
			}
		}
		return -1;
	}
	
	public int getSlotWithItemAndDataAndAmount(int type, int data, int amount) {
		for (int slot = 0; slot < inventory.length; slot++) {
			ItemStack currentItem = inventory[slot];
			if (currentItem == null) {
				continue;
			}
			if (currentItem.getId() == type && currentItem.getAmount() == amount && currentItem.getData() == data) {
				return slot;
			}
		}
		return -1;
	}
} 
