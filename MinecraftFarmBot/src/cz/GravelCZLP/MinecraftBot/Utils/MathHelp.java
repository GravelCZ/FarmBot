package cz.GravelCZLP.MinecraftBot.Utils;

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
	public static double square(double num) {
		return num * num;
	}
}
