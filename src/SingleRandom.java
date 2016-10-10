//Ryan Potter and Patrick Eddy
import java.util.Random;

/**
 * A singleton class to ensure a unique output from random calls .
 * @author Ryan and Patrick
 *
 */
public class SingleRandom {
	public static Random rand = new Random();
	
	/**
	 * gets the next int within 0 and the bound.
	 * @param bound upper bound of the next int.
	 * @return the next int.
	 */
	public static int nextInt(int bound) {
		return rand.nextInt(bound);
	}
}
