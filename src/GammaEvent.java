/*
 * A collision event, with given (x,y,z,t,xp,yp,p,k,alpha,xd,yd)
 */
import static java.lang.Math.*;
public class GammaEvent {
	private double x,y,z,t,xp,yp,p,k;
	private double xd,yd,D;
	private double alpha,tau;	
	private double thetaP,phiP;
	private double lorentzGamma, velocityBeta;
	private double[] fourMomentumP = new double[4];
	private double[] fourMomentumK = new double[4];
	private double[] fourMomentumKp = new double[4];
	private double gammaEnergy;
	private double detectTime;
	private double stokesPara3;
	private double lorInvX,lorInvY;
	private double dSigma_dxd_dyd;
	
	public void setEventPara(double[] MultiNormalVector, double[] detect, double aAlpha, double aTau ){
		x  = MultiNormalVector[0];
		y  = MultiNormalVector[1];
		z  = MultiNormalVector[2];
		t  = MultiNormalVector[3];
		xp = MultiNormalVector[4];
		yp = MultiNormalVector[5];
		p  = MultiNormalVector[6];
		k  = MultiNormalVector[7];	
		xd = detect[0];
		yd = detect[1];
		D = detect[2];	
		alpha = aAlpha;
		tau   = aTau;	
	}
	public void compute(){
		//Keep the sequences Unchanged !!!
		computeThetaP();
		computePhiP();
		computeLorentzGamma();
		computeVelocityBeta();
		computeFourMomentumP();
		computeFourMomentumK();
		computeFourMomentumKp();
		computeDetectTime();
		computeLorInvX();
		computeLorInvY();
		computeStokesPara3();
		compute_dSigma_dxd_dyd();		    
	}
    
	public double getGammaEnergy(){
		return gammaEnergy;		
	}
	public double getDetectTime(){
	    return detectTime;	
	}
	public double get_dSigma_dxd_dyd(){
		return dSigma_dxd_dyd;
	}	
	private double fourVectorDot(double[] a, double[] b){
		return a[0]*b[0] - a[1]*b[1]- a[2]*b[2]- a[3]*b[3];
	}
	private double[] threeVectorCross(double[] a, double[] b){
		double[] c = new double[3];
		c[0] = a[1]*b[2] - a[2]*b[1];
		c[1] = a[2]*b[0] - a[0]*b[2];
		c[2] = a[0]*b[1] - a[1]*b[0];
		return c;
	}
	private double threeVectorNorm(double[] a){
		return sqrt(pow(a[0],2) + pow(a[1],2) +pow(a[2],2));
	}
	private double threeVectorDot(double[] a, double[] b){
		return a[0]*b[0]+ a[1]*b[1]+ a[2]*b[2];
	}
	private double[] threeVectorAdd(double[] a, double[] b){
		return new double[]{a[0]+b[0], a[1]+b[1], a[2]+b[2]};
	}
	private double computeThetaP(){
		double nominator = pow((xd-x),2) + pow((yd-y),2);
		double dominator = pow((xd-x),2) + pow((yd-y),2) + pow((D-z),2);
		thetaP = asin(sqrt(nominator / dominator));
		return thetaP;
	}
	private double computePhiP(){
		phiP = atan((yd-y)/(xd-x));
		return phiP;
	}
	private double computeLorentzGamma(){
		lorentzGamma = sqrt(pow(p,2)+pow(PhysConst.me,2))/PhysConst.me;
		return lorentzGamma;
	}
	private double computeVelocityBeta(){
		velocityBeta = sqrt(1 - 1/pow(lorentzGamma,2));
		return velocityBeta;
	}
	private double[] computeFourMomentumP(){
		fourMomentumP[0] = lorentzGamma * PhysConst.me;
	    fourMomentumP[1] = lorentzGamma * PhysConst.me * velocityBeta * xp;
	    fourMomentumP[2] = lorentzGamma * PhysConst.me * velocityBeta * yp;
	    fourMomentumP[3] = lorentzGamma * PhysConst.me * velocityBeta * sqrt(1 - pow(xp,2) - pow(yp,2));
	    return fourMomentumP;
	}
	private double[] computeFourMomentumK(){
		fourMomentumK[0] = k;
		fourMomentumK[1] = -k*sin(alpha);
		fourMomentumK[2] = 0;
		fourMomentumK[3] = -k*cos(alpha);
		return fourMomentumK;
	}
	private double[] computeFourMomentumKp(){	
		double[] unitFourMomentumKp = new double[4];
		unitFourMomentumKp[0] = 1;
		unitFourMomentumKp[1] = sin(thetaP)*cos(phiP);
		unitFourMomentumKp[2] = sin(thetaP)*sin(phiP);
		unitFourMomentumKp[3] = cos(thetaP);	
		
		double nominator = fourVectorDot(fourMomentumP,fourMomentumK);
		double dominator = fourVectorDot(fourMomentumP,unitFourMomentumKp) +fourVectorDot(fourMomentumK,unitFourMomentumKp);
		gammaEnergy = nominator / dominator; 
		for(int i=0;i<4;i++)
			fourMomentumKp[i] = gammaEnergy * unitFourMomentumKp[i];
		return fourMomentumKp; 
	}
	private double computeDetectTime(){
		double distance = sqrt(pow((xd-x), 2) + pow((yd-y), 2) +pow((D-z), 2));
		detectTime = distance / PhysConst.c + t;
		return detectTime;
	}
	private double computeLorInvX(){
		lorInvX = 2*fourVectorDot(fourMomentumP,fourMomentumK) / pow(PhysConst.me,2);
		return lorInvX;
	}
	private double computeLorInvY(){
		lorInvY = 2*fourVectorDot(fourMomentumP,fourMomentumKp) / pow(PhysConst.me,2);
		return lorInvY;
	}
	private double computeStokesPara3(){
		//compute three vector k and k'
		double[] threeMomentumK = {fourMomentumK[1],fourMomentumK[2],fourMomentumK[3]};
		double[] threeMomentumKp = {fourMomentumKp[1],fourMomentumKp[2],fourMomentumKp[3]};
		
		//compute unit vector x in scattering plane
		double[] vectorX = threeVectorCross(threeMomentumK,threeMomentumKp);
		double vectorXNorm = threeVectorNorm(vectorX);
		double[] unitVectorX = {vectorX[0]/vectorXNorm,vectorX[1]/vectorXNorm,vectorX[2]/vectorXNorm};
		
		//compute unit vector x in scattering plane
		double[] vectorY = threeVectorCross(threeMomentumK,unitVectorX);
		double vectorYNorm = threeVectorNorm(vectorY);
		double[] unitVectorY = {vectorY[0]/vectorYNorm,vectorY[1]/vectorYNorm,vectorY[2]/vectorYNorm};
		
		//express the polarization vector in xyz frame, epsilon = cos(tau) * unitVectorXP + sin(tau)*unitVectorYP
		//double[] unitVectorXP = {-cos(alpha),0,sin(alpha)};
		//double[] unitVectorYP = {0,1,0}; 
		double[] epsilon =  {-cos(alpha)*cos(tau), sin(tau), sin(alpha)*cos(tau)};
		double epsilon_x = threeVectorDot(epsilon,unitVectorX);
		double epsilon_y = threeVectorDot(epsilon,unitVectorY);
	    stokesPara3 = pow(epsilon_x,2) - pow(epsilon_y,2);
	    return stokesPara3;
	}
	private double computeWeight(){
//		//compute sqrt((ve-vp)^2 - (ve \cross vp)^2)
//		double[] ve = {velocityBeta*xp, velocityBeta*yp, velocityBeta * sqrt(1-pow(xp,2)- pow(yp,2))};
//		double[] vp = {-sin(alpha),0,-cos(alpha)};
//	
////		double relativeVelocity = sqrt(threeVectorDot(threeVectorAdd(ve,-vp)));
		return 0;
	}
    private double compute_dSigma_dxd_dyd(){

		dSigma_dxd_dyd = (1/pow(D,2)) * (4.0 * pow(PhysConst.re,2) / pow(lorInvX,2)) * 
				         (2.0 * pow(gammaEnergy/ PhysConst.me,2)) *   
				        ((1 - stokesPara3) * (pow((1/lorInvX - 1/lorInvY),2) + (1/lorInvX - 1/lorInvY )) 
				                        + 0.25*(lorInvX/lorInvY + lorInvY/lorInvX));	
		return dSigma_dxd_dyd ;
	}
    public static void main(String[] args){
		double[] sigma = {3E-4, 1E-6, 2.4E-2, 6.67E-5, 1E-6, 6E-3, 4.4E-4, 1E-2, 1E-6};
		double[] mu = {0.42E9, 2.27};
		double alpha = 0.0;	
		double tau =0.0;
		Sampler sampler = new Sampler(mu,sigma,alpha);
	
		GammaEvent event = new GammaEvent();
		double[] MultiNormalVector = sampler.nextMultiNormalVector();
		double[] detect = {0.000,0.00,52.8};
		event.setEventPara(MultiNormalVector, detect, alpha, tau);
		event.compute();
		double dSigma_dxd_dyd = event.get_dSigma_dxd_dyd();		
//		System.out.println("This is a test event:");
//		System.out.println("The input:");
//		System.out.println("Sampled vector: ");
//		for(double a:MultiNormalVector)
//			System.out.printf("%4.2e	",a);
//		System.out.println("");
//		System.out.printf("GammaEnergy %4.2e	\n", event.getGammaEnergy());
//		System.out.printf("DetectionTime %4.2e	\n",  event.getDetectTime());
//		System.out.printf("dSigma %4.2e	 \n",  event.get_dSigma_dxd_dyd());
			
    }
}
