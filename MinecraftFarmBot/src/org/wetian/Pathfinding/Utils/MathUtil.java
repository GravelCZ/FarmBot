package org.wetian.Pathfinding.Utils;

public final class MathUtil {

	public static double getDelta(double a, double b) {
		return Math.abs(a - b);
	}

	public static double mix(double firstNumber, double firstPart, double secondNumber, double secondPart) {
		double firstPercentage = firstPart / (firstPart + secondPart);
		double secondPercentage = 1 - firstPercentage;

		return (firstPercentage * firstNumber) + (secondPercentage * secondNumber);
	}

	public static double mix(double firstNumber, double firstPart, double secondNumber) {
		double secondPercentage = 1 - firstPart;

		return (firstPart * firstNumber) + (secondPercentage * secondNumber);
	}

	public static double clampAbs(double number, double maximumAbs) {
		return ((number < 0) ? -1 : 1) * Math.min(Math.abs(number), maximumAbs);
	}

	public static double clamp(double min, double max, double value) {
		if (min > max) {
			throw new IllegalArgumentException(PHR.r("min ({}) was bigger than max ({})", min, max));
		}
		
		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;
	}

	public static double avg(double... values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("can't average calculate average of no numbers");
		}
			
		double sum = 0;
		
		for (double value : values) {
			sum += value;
		}

		return sum / values.length;
	}
	
	public static double remapLinear(double baseStart, double baseEnd, double targetStart, double targetEnd, double valueToRemap) {
		//Validate.isTrue(baseStart != baseEnd, "baseStart can't be equal to baseEnd (" + baseStart + ")");
		//Validate.isTrue(targetStart != targetEnd, "targetStart can't be equal to targetEnd (" + targetStart + ")");

		double proportionFrom1To2 = (valueToRemap - baseStart) / (baseEnd - baseStart);
		
		return targetStart + ((targetEnd - targetStart) * proportionFrom1To2);
	}

	public static boolean isAngleNearRad(double a, double b, double maxD) {
		return isAngleNearDeg(Math.toDegrees(a), Math.toDegrees(b), maxD);
	}

	public static boolean isAngleNearDeg(double a, double b, double maxD) {
		return getAngleDistanceDeg(a, b) < maxD;
	}

	public static double getAngleDistanceDeg(double a, double b) {
		double delta = Math.abs(a - b) % 360;
		
		if(delta > 180) {
			delta = 360 - delta;
		}
			
		return delta;
	}

	public static double round(double numberToRound, int decimalPlaces) {
		if (Double.isNaN(numberToRound)) {
			throw new IllegalArgumentException("can't round NaN");
		}
			
		int factor = 1;
		
		for (int i = 0; i < decimalPlaces; i++) {
			factor *= 10;
		}

		return (double) Math.round(numberToRound * factor) / factor;
	}

	public static double smoothStep(double input) {
		if (input <= 0) {
			return 0;
		}

		if (input >= 1) {
			return 1;
		}

		return (3 * (input * input)) - (2 * (input * input * input));
	}

	public static double smootherStep(double input) {
		if (input <= 0) {
			return 0;
		}

		if (input >= 1) {
			return 1;
		}

		return ((6 * (input * input * input * input * input)) - (15 * (input * input * input * input))) + (10 * (input * input * input));
	}
}
