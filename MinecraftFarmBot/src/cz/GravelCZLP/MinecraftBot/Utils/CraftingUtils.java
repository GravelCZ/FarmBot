package cz.GravelCZLP.MinecraftBot.Utils;

import cz.GravelCZLP.MinecraftBot.Utils.CraftingRecipe.CraftingType;

public class CraftingUtils {

	public static enum CraftableMaterials {
		//. is empty slot
		// <space> is for sep
		// D is item/material
		// S is stick
		SWORD(". D .", ". D .", ". S ."),
		PICKAXE("D D D", ". S .", ". S ."),
		SHOWEL(". D .", ". S .", ". S ."),
		AXE("D D .", "D S .", ". S ."),
		
		HELMET("D D D", "D . D", ". . ."),
		CHESTPLATE("D . D", "D D D", "D D D"),
		LEGGINGS("D D D", "D . D", "D . D"),
		BOOTS(". . .", "D . D", "D . D");
		
		private String l1, l2, l3;
		
		private CraftableMaterials(String l1, String l2, String l3) {
			this.l1 = l1;
			this.l2 = l2;
			this.l3 = l3;
		}
		
		public String[] getLines() {
			return new String[] { l1, l2, l3 };
		}
	}
	
	public static CraftingRecipe getRecipe(CraftableMaterials m, int materialId) {
		CraftingRecipe recipe = new CraftingRecipe(m.getLines()[0], m.getLines()[1], m.getLines()[2], CraftingType.WORKBENCH);
		recipe.replace('D', materialId);
		recipe.replace('S', 280);
		return recipe;
	}
	
}
