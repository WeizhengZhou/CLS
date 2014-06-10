package config;

import physics.PhysConst;

/**
 * Electron beam at the interaction point
 * @author Weizheng
 *
 */

public class ElectronBeam {
	private double energy = 400E6;//beam average energy, [eV]
	private double current = 300E-3;//beam current, [A]
	private double f0 = 2.7898E6;//repetition rate, [Hz]

	private double betaX = 4.5;//horizontal beta function, [m]
	private double betaY = 4.5;//vertical beta function, [m]
	private double epsilonX = 20E-9;//horizontal emittance, [m*rad]
	private double epsilonY = 0.;//vertical emittance, [m*rad]
	private double sigmaX = Math.sqrt(betaX * epsilonX);
	private double sigmaY = Math.sqrt(betaY * epsilonY);
	private double sigmaXp = Math.sqrt(epsilonX / betaX);
	private double sigmaYp = Math.sqrt(epsilonY / betaY);
	
	private double sigmaP_over_P = 6E-3;//relative energy spread,
	private double sigmaP = sigmaP_over_P * energy;//energy spread,
	
	private double sigmaT = 12.7E-12;//longitudinal RMS, [s]
	private double sigmaZ = sigmaT * PhysConst.SPEED_OF_LIGHT;
	
	
	public double getEnergy() {
		return energy;
	}
	public double getSigmaX() {
		return sigmaX;
	}
	public double getSigmaY() {
		return sigmaY;
	}
	public double getSigmaZ() {
		return sigmaZ;
	}
	public double getSigmaXp() {
		return sigmaXp;
	}
	public double getSigmaYp() {
		return sigmaYp;
	}
	public double getSigmaP() {
		return sigmaP;
	}
	

}
