package cap.sim.utility;

import java.util.Random;

public class NumberUtility {

	public static int getRandom(int min,int max)
	{
		Random rand = new Random();
		return rand.nextInt(max) + min;
	}
	public static double getRandom(double mean,double desiredStandardDeviation)
	{
		Random r = new Random();
		return r.nextGaussian()*desiredStandardDeviation+mean;
	}
}
