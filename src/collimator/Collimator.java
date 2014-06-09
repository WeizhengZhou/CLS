package collimator;

public class Collimator {	
	
	private final int N;//number of slices to mesh the collimator
	
	private final double RADIUS;//radius of collimator, [m]
	private final double DISTANCE;//distance from IP to collimator [m]
	private final double XERROR;//misalignment of collimator in x, [m]
	private final double YERROR;//misalignment of collimator in y, [m]

	
	public Collimator(int n, double r, double d, double xError, double yError){
		this.N = n;
		this.RADIUS = r;
		this.DISTANCE = d;
		this.XERROR = xError;
		this.YERROR = yError;		
	}
	
}
