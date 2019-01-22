package org.wetian.Pathfinding.Eluvator;

import org.bukkit.Material;

@SuppressWarnings("deprecation")
public class MaterialEvaluator {

	private static boolean[] canStandOn;
	private static boolean[] canStandIn;
	
	private static boolean ready = false;

	public static void prepareEvaluation() { //FIXME: implment materials,
		int maxId = -1;
		
		for (Material mat : Material.values()) {
			if (mat.getId() > maxId) {
				maxId = mat.getId();
			}
		}

		canStandOn = new boolean[maxId + 1]; 
		canStandIn = new boolean[maxId + 1]; 

		for (Material mat : Material.values()) {
			int id = mat.getId();

			canStandOn[id] = mat.isSolid();
			canStandIn[id] = !mat.isSolid();

			if (mat.name().contains("FENCE")) {
				canStandOn[id] = false;
			}
			
			if (mat.name().contains("WATER")) {
	//			canStandOn[id] = true;
	//			canStandIn[id] = true;
			}

			if (mat == Material.SIGN_POST) {
				canStandIn[id] = true;
				canStandOn[id] = false;
			}
		}

		ready = true;
	}

	public static boolean canStandOn(int materialID) {
		if (!ready) {
			throw new IllegalStateException("Path has to be anabled before usage!");
		}
			
		return canStandOn[materialID];
	}

	public static boolean canStandIn(int materialID) {
		if (!ready) {
			throw new IllegalStateException("Path has to be anabled before usage!");
		}
			
		return canStandIn[materialID];
	}
}
