/*
 * 
 */

import java.io.*;
import java.util.Random;
import static java.lang.Math.*;
import Jama.*;

public class Sampler {
	//electron centriod energy 
	private double muP;
	//photon centriod energy
	private double muK;
	
	//electron beam size
	private double sigmaX;
	private double sigmaY;
	private double sigmaZ;
	private double sigmaXp;
	private double sigmaYp;
	private double sigmaP;
	
	//photon beam size
	private double sigmaW;
	private double sigmaL;
	private double sigmaK;	
	private double alpha;
	private double[][] covMat = new double[8][8];
	private double[][] A = new double[8][8];
	
	public Sampler(double[] mu, double[] sigma, double a){
		sigmaX = sigma[0];
		sigmaY = sigma[1];
		sigmaZ = sigma[2];	
		sigmaXp = sigma[3];
		sigmaYp = sigma[4];
		sigmaP = sigma[5];
		sigmaW = sigma[6];
		sigmaL = sigma[7];
		sigmaK = sigma[8];	
		muP = mu[0];
		muK = mu[1];
		alpha  = a;
		computeCovMat();
		computeCholDecompA();
	}
	public double[][] getCovMat(){
		return covMat;
	}
	public double[][] getCholDecompA(){	
		return A;
	}
	private void computeCovMat(){

		//array for inverse of Covriance matrix
		//the matrix is arraged as (x,y,z,c*t,x',y',p,k)
		double[][] invCovMat_a = new double[8][8];	
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				invCovMat_a[i][j] = 0.;
			}
		}
		invCovMat_a[0][0] = 1.0/pow(sigmaX,2) + pow(cos(alpha),2)/pow(sigmaW,2) + pow(sin(alpha),2) /pow(sigmaL,2);	
		invCovMat_a[0][2] = (1.0/pow(sigmaL,2) - 1.0/pow(sigmaW,2)) * sin(alpha)*cos(alpha);
		invCovMat_a[2][0] = invCovMat_a[0][2];
		invCovMat_a[0][3] = sin(alpha)/pow(sigmaL,2);
		invCovMat_a[3][0] = invCovMat_a[0][3];	
		invCovMat_a[1][1] = 1/pow(sigmaY,2);
		invCovMat_a[2][2] = 1/pow(sigmaZ,2) + pow(cos(alpha),2)/pow(sigmaL,2) + pow(sin(alpha),2)/pow(sigmaW,2);
		invCovMat_a[2][3] = -1.0/pow(sigmaZ,2) + cos(alpha)/pow(sigmaL,2);
		invCovMat_a[3][2] = invCovMat_a[2][3];
		invCovMat_a[3][3] = 1.0/pow(sigmaZ,2) + 1.0/pow(sigmaL,2);
		invCovMat_a[4][4] = 1.0/pow(sigmaXp,2);
		invCovMat_a[5][5] = 1.0/pow(sigmaYp,2);
		invCovMat_a[6][6] = 1.0/pow(sigmaP,2);
		invCovMat_a[7][7] = 1.0/pow(sigmaK,2);  		
		//transfer invCovMat_a array to matrix invCovMat
		Matrix invCovMat = new Matrix(invCovMat_a);
		//inverse invCovMat to CovMat
		Matrix CovMat_m = invCovMat.inverse();
		covMat  = CovMat_m.getArray();
	}
    private void computeCholDecompA(){
		Matrix covMat_m = new Matrix(covMat);
		CholeskyDecomposition decomposer = new CholeskyDecomposition(covMat_m);
		Matrix A_m = decomposer.getL();
		A = A_m.getArray();			
	}

	public double[] nextMultiNormalVector(){	
		double[] eta = new double[8];
		Random rd = new Random();
		//eta as the normal vector
		for(int i=0;i<8;i++){
			eta[i] = rd.nextGaussian();
		}
		// MultiNormalVector = A*eta
		double[] MultiNormalVector = new double[8];
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				MultiNormalVector[i] += A[i][j]*eta[j]; 				
			}
			if(i==3) {
				//since we have sampled  (x,y,z,c*t,x',y',p,k)
				//in order to get t, the fourth element should divid speedOfLight
				MultiNormalVector[i] = MultiNormalVector[i]/PhysConst.c;
			}
			if(i==6){
				//
				MultiNormalVector[i] = (1+MultiNormalVector[i]) * muP;
			}
			if(i==7){
				MultiNormalVector[i] = (1 + MultiNormalVector[i]) * muK;
			}
		}
		return MultiNormalVector;	
	}

	public static void main(String[] args){
		
		double[] sigma = {3E-4, 1E-6, 2.4E-2, 6.67E-5, 1E-6, 6E-3, 4.4E-4, 1E-2, 1E-6};
		double[] mu = {0.42E9, 2.27};
		double alpha = 0.0;	
		Sampler sampler = new Sampler(mu, sigma, alpha);
		double[][] M = sampler.getCovMat();
		System.out.println("The Covirance Matrix is:");
		sampler.printMatrix(M);
		
		double[][] A = sampler.getCholDecompA();
		System.out.println("The decomposed Matrix A is");
		sampler.printMatrix(A);
	
	    try {
	    	 
			File file = new File("/Users/Weizheng/Documents/JavaWorkPlace/CLS_MCIntegrator/output.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			else{
				file.delete();
				file.createNewFile();
			}
 
			PrintWriter fw = new PrintWriter(file);
			
			long startTime = System.currentTimeMillis();

			for(int i=0;i<1E3;i++){
				double[] MultiNormalVector = sampler.nextMultiNormalVector();
//				for(double a:MultiNormalVector ){
//					fw.printf("%4.2e ", a);
//				}
//				fw.println("");		
			}		   
			fw.close();
			long endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
 
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void printMatrix(double[][] matrix){
		int n = matrix.length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.printf("%4.2e ",matrix[i][j]);
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
