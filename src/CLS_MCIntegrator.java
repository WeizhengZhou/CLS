import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import collimator.Collimator;


public class CLS_MCIntegrator {
	public void compute(){
		int Nd = 51;
		int Ns = 1000;
		int Ne = 100;
		int Nt = 100;
		
		Collimator collimator = new Collimator();
		double detectStep = 2*collimator.radius /(Nd -1);
		
	}
	public static void main(String[] args){
		double[] mu = {0.42E9, 2.27};
		double[] sigma = {3E-4, 1E-6, 2.4E-2, 6.67E-5, 1E-6, 6E-3, 4.4E-4, 1E-2, 1E-6};
		double alpha = 0.0;	
		double tau = 0.0;	
		
		Collimator collimator = new Collimator();
		Sampler sampler = new Sampler(mu,sigma,alpha);
		GammaEvent event = new GammaEvent();
		
//		double[][] M = sampler.getCovMat();
//		System.out.println("The Covirance Matrix is:");
//		sampler.printMatrix(M);
		
//		double[][] A = sampler.getCholDecompA();
//		System.out.println("The decomposed Matrix A is");
//		sampler.printMatrix(A);
	
	    try {    	 
			File file = new File("/Users/Weizheng/Documents/JavaWorkPlace/CLS_MCIntegrator/output.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			else{
				file.delete();
				file.createNewFile();
			}
 
			PrintWriter fw = new PrintWriter(file);
			long startTime = System.currentTimeMillis();
			
			double[] detect = {0.,0.,52.8};
	
			for(int i=0;i<1E4;i++){	
				double[] MultiNormalVector = sampler.nextMultiNormalVector();
				
				event.setEventPara(MultiNormalVector, detect, alpha, tau);
				event.compute();
				double gammaEnergy = event.getGammaEnergy();
				double detectTime = event.getDetectTime();
				double dSigma_dxd_dyd = event.get_dSigma_dxd_dyd();	
				
				for(double a:MultiNormalVector ){
					fw.printf("%4.2e ", a);
				}
				fw.printf("%4.2e ", gammaEnergy);
				fw.printf("%4.2e ", detectTime);
				fw.printf("%4.2e ", dSigma_dxd_dyd);
				fw.println("");		
			}		   
			fw.close();
			
			long endTime = System.currentTimeMillis();
			System.out.println("That took " + (endTime - startTime) + " milliseconds");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
