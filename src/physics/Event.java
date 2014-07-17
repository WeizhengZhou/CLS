package physics;
import config.CollisionAngles;
import sampler.Sampler;
import math.Vector4;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
/**
 * An Event class represents the a collision event of Compton scattering
 * @author Weizheng
 */
public class Event {
	private double[] rv = null;//sampled random vector 
	private Vector4 coordinate = null;//the time and location of this event
	private Particle electron = null;//the electron in this event
	private Particle photon = null;//the photon in this event
	private Particle gamma = null;//the gamma generated in this event 
	private Detector detector = null;//the detector to detect the generated gamma
	private Sampler sampler = null;
	
	public Event(Sampler sampler, Detector detector){	
		this.sampler = sampler;
		this.detector = detector;	
		double[] rv = sampler.nextRandomVector();
		this.rv = rv;
		this.coordinate = new Vector4(rv[3],rv[0],rv[1],rv[2]);
		setPhoton();
		setElectron();
		setGamma();			
	}
	private void setPhoton(){
		//k= k * [1, sin(theta_p)*cos(phi_p), sin(theta_p)*sin(phi_p), cos(theta_p)]
		double k = rv[7];
		double alpha = CollisionAngles.ALPHA;
		Vector4 momentum = new Vector4(k, -k * sin(alpha), 0, k * cos(alpha));
		Polarization polarization = null;
		photon = new Photon(momentum, polarization);	
	}
	private void setElectron(){		
		double xp = rv[4];
		double yp = rv[5];
		double p  = rv[6];		
		double px = xp*p;
        double py = yp*p;
        double pz = sqrt(1-pow(xp,2)-pow(yp,2)) * p;
        this.electron = new Electron(new Vector4(p,px,py,pz));
	}
	private void setGamma(){
		
	}
	private void transform(){
		
	}
	private void xSection(){
		
	}	   
}
