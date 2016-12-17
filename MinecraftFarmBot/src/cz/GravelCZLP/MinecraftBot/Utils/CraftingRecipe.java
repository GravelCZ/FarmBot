package cz.GravelCZLP.MinecraftBot.Utils;

public class CraftingRecipe {

	public enum CraftingType {
		WORKBENCH,
		FURNACE;
		//only the two needed
	}
	
	private String l1, l2, l3;
	private CraftingType type;
	
	public CraftingRecipe(String line1, String line2, String line3, CraftingType t) {
		type = t;
		l1 = line1;
		l2 = line2;
		l3 = line3;
	}
	
	public void replace(char c, int id) {
		l1.replaceAll(String.valueOf(c), String.valueOf(id));
		l2.replaceAll(String.valueOf(c), String.valueOf(id));
		l3.replaceAll(String.valueOf(c), String.valueOf(id));
	}
	public CraftingType getCraftingType() {
		return type;
	}
	
	public String[] getLines() {
		return new String[] { l1, l2,l3 };
	}
}
