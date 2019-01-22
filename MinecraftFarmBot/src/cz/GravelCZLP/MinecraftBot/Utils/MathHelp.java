package cz.GravelCZLP.MinecraftBot.Utils;

import cz.GravelCZLP.MinecraftBot.World.Vector3D;

public class MathHelp {

	public static double warpDegrees(double value) {
        if ((value %= 360.0) >= 180.0) {
            value -= 360.0;
        }
        if (value < -180.0) {
            value += 360.0;
        }
        return value;
	}
	
	public static boolean between(double num, double a, double b, double off) {
		if (a <= b) {
			return num + off >= a && num - off <= b;
		}
		return num + off >= b && num - off <= a;
	}
	
	public static double square(double num) {
		return num * num;
	}

	public static Vector3D vectorDirection(EntityLocation currentLoc) {
		double x = -Math.cos(Math.toRadians(currentLoc.getPitch())) * Math.sin(Math.toRadians(currentLoc.getYaw()));
		double y = -Math.sin(Math.toRadians(currentLoc.getPitch()));
		double z = Math.cos(Math.toRadians(currentLoc.getPitch())) - Math.cos(Math.toRadians(currentLoc.getYaw()));
		return new Vector3D(x, y, z);
	}
}
