package sampler;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.Arrays;

import config.*;
import physics.PhysConst;
import Jama.*;
/**
 * The covariance matrix of Gaussian importance sampling
 * row 0 to row 7 represents, x,y,z,t,xp,yp,p,k, respectively 
 * @author Weizheng
 */

public class CovMatrix {
	
	private double sigmaX;//electron beam
	private double sigmaY;
	private double sigmaZ;
	private double sigmaXp;
	private double sigmaYp;
	private double sigmaP;
		
	
	private double sigmaW;//photon beam size
	private double sigmaL;
	private double sigmaK;	
	
	private double alpha;//collision angle
	
	private double[][] M;//covariance matrix
	private double[][] L;//lower triangular matrix, M = L*L^T;
	/**
	 * Set the Covariance matrix of Gauss Importance Sampling
	 * @param eBeam electron beam
	 * @param lBeam laser beam
	 */
	public CovMatrix(ElectronBeam eBeam, LaserBeam lBeam){
		sigmaX  = eBeam.getSigmaX();
		sigmaY  = eBeam.getSigmaY();
		sigmaZ  = eBeam.getSigmaZ();	
		sigmaXp = eBeam.getSigmaXp();
		sigmaYp = eBeam.getSigmaYp();
		sigmaP  = eBeam.getSigmaP();
		
		sigmaW  = lBeam.getSigmaW();
		sigmaL  = lBeam.getSigmaL();
		sigmaK  = lBeam.getSigmaK();	

		alpha  = CollisionAngles.ALPHA;		
		//compute covariance matrix
		double[][] iM_a = setInvCovMat();
		Matrix iM = new Matrix(iM_a);
		Matrix M_m = iM.inverse();
		M  = M_m.getArray();
			
		CholeskyDecomposition decomposer = new CholeskyDecomposition(M_m);//decompose M = L*L^T;
		Matrix lowerTraMat_m = decomposer.getL();
		L = lowerTraMat_m.getArray();		
	}
	private double[][] setInvCovMat(){//set the covariance matrix
		double[][] iM_a = new double[8][8];	
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				iM_a[i][j] = 0.;
			}
		}
		iM_a[0][0] = 1.0/pow(sigmaX,2) + pow(cos(alpha),2)/pow(sigmaW,2) + pow(sin(alpha),2) /pow(sigmaL,2);	
		iM_a[0][2] = (1.0/pow(sigmaL,2) - 1.0/pow(sigmaW,2)) * sin(alpha)*cos(alpha);
		iM_a[0][3] = PhysConst.SPEED_OF_LIGHT * sin(alpha)/pow(sigmaL,2);
		iM_a[1][1] = 1/pow(sigmaY,2) + 1/pow(sigmaW,2);
		iM_a[2][0] = iM_a[0][2];
		iM_a[2][2] = 1/pow(sigmaZ,2) + pow(sin(alpha),2)/pow(sigmaW,2) + pow(cos(alpha),2)/pow(sigmaL,2) ;
		iM_a[2][3] = -PhysConst.SPEED_OF_LIGHT/pow(sigmaZ,2) + PhysConst.SPEED_OF_LIGHT * cos(alpha)/pow(sigmaL,2);	
		iM_a[3][0] = iM_a[0][3];	
		iM_a[3][2] = iM_a[2][3];
		iM_a[3][3] = pow(PhysConst.SPEED_OF_LIGHT,2) * (1.0/pow(sigmaZ,2) + 1.0/pow(sigmaL,2));
		iM_a[4][4] = 1.0/pow(sigmaXp,2);
		iM_a[5][5] = 1.0/pow(sigmaYp,2);
		iM_a[6][6] = 1.0/pow(sigmaP,2);
		iM_a[7][7] = 1.0/pow(sigmaK,2);  
		return iM_a;
	}
	/**
	 * get the lower triangular matrix decomposition of covariance matrix
	 * @return lower triangular matrix 
	 */
	public double[][] getLowerTraMat(){
		return L;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("The Covariance Matrix = \n" + Arrays.deepToString(M));
		sb.append("The Lower Traingular Matrix = \n" + Arrays.deepToString(L));
		return sb.toString();
	}

}
