package physics;

import sampler.*;
import math.*;
import config.*;


public class CLSMain {
	private ElectronBeam eBeam = null;
	private LaserBeam lBeam = null;
	private Sampler sampler= null;
	
	public CLSMain(){
		eBeam = new ElectronBeam();
		lBeam = new LaserBeam();
		sampler = new Sampler(eBeam,lBeam);		
	}
	
	public void compute(){
		
		for(int i=0;i<Collimator.N;i++){
			double xd = (i - Collimator.N/2) * Collimator.STEP;
			for(int j=0;j<Collimator.N;j++){
				double yd = (j - Collimator.N/2) * Collimator.STEP;
				Detector detector = new Detector(xd,yd,Collimator.DISTANCE);
				
				


				
			}
		}
		
	}
	
	

}
