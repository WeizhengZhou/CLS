package config;

import physics.PhysConst;

public class LaserBeam {
	private double wavelength = 545E-9;//wavelength of laser, [m]
	private double energy = PhysConst.HBAR_TIMES_C / wavelength;
	private double ZR = 4.5;//Rayleigh range, [m]
	
	private double sigmaW = Math.sqrt(wavelength*ZR/4/Math.PI);
	private double sigmaK = 0.;
	private double sigmaT = 80E-12;//RMS length, [s]
	private double sigmaL = PhysConst.SPEED_OF_LIGHT * sigmaT;
	
	public double getEnergy() {
		return energy;
	}
	public double getSigmaW() {
		return sigmaW;
	}
	public double getSigmaK() {
		return sigmaK;
	}
	public double getSigmaL() {
		return sigmaL;
	}
	
	

}
