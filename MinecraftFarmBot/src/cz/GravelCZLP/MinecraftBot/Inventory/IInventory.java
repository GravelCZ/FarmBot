package cz.GravelCZLP.MinecraftBot.Inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;

public interface IInventory {

	public void deconstuctItemArrayToIvn(ItemStack[] array, Bot bot);
	
	public void updateSlot(int slot, ItemStack item);
	
}
