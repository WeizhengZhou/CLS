package sampler;

import java.awt.Event;
import java.util.Random;

import config.ElectronBeam;
import config.LaserBeam;

/**
 * A sampler can sample a multivariate random vector X,
 * where X ~ exp[-0.5(X-mu)^T Sigma ^-1 (X-mu)]
 * X has a form, (x,y,z,t,xp,yp,p,k)
 * @author Weizheng
 *
 */
public class Sampler {
	
	// multiNormalVector = mu + L*eta
	private double[] mu = new double[8];
	private double[][] L = null;
	/**
	 * constructor of a sampler object
	 * @param eBeam an ElectronBeam object
	 * @param lBeam an LaserBeam object
	 */
	public Sampler(ElectronBeam eBeam, LaserBeam lBeam){
		//set the covariance matrix, based on eBeam and lBeam parameters 
		CovMatrix covMat = new CovMatrix(eBeam,lBeam);
		//cholesky decomposition of covariance matrix, and get the lower traingular matrix
		L = covMat.getLowerTraMat();
		//set the mu vector
		for(int i=0;i<8;i++){
			mu[0] = 0.;
		}
		mu[6] = eBeam.getEnergy();
		mu[7] = lBeam.getEnergy();
		
	}
	/**
	 * sample the next random vector, X = mu + L*eta
	 * @return a 8-dimensinoal random vector has a form (x,y,z,t,xp,yp,p,k)
	 */
	public double[] nextRandomVector(){		
		
		double[] multiNormalVector = new double[8];
		

		double[] eta = new double[8];//eta as a standard normal vector, N(0,1)
		Random rd = new Random();
		for(int i=0;i<8;i++){
			eta[i] = rd.nextGaussian();
		}
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				multiNormalVector[i] += L[i][j]*eta[j]; 				
			}
			multiNormalVector[i] += mu[i];
		}
		
		return multiNormalVector;	
	}
}
