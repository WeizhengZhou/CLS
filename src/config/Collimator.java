package config;


public class Collimator {	
	public static final int N = 100;//number of slices to mesh the collimator	
	public static final double RADIUS = 0.01;//radius of collimator, [m]
	public static final double STEP = 2 * RADIUS / N;
	public static final double DISTANCE = 50.0;//distance from IP to collimator [m]
	public static final double XERROR = 0.;//misalignment of collimator in x, [m]
	public static final double YERROR = 0.;//misalignment of collimator in y, [m]
	
}
