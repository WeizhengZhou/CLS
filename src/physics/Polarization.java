package physics;

import math.Vector3;

/**
 * An object to represent the polarization of a Photon
 * intrinsically,P,L,A,E and B are used to to represent the Photon 
 * @author Weizheng
 *
 */
		
public class Polarization {
	
	private double P = 0; // degree of polarization
	private double L = 0;//degree of linear polarization
	private double A = 0.;//degree of circular polarization
	private Vector3 E = null;
	private Vector3 B = null;
	/**
	 * constructor of a polarization object
	 * Stokes parameters to EM field and degrees of polarization
	 * @param stokes parameter
	 * @param e1 local frame's e1 axis
	 * @param e2 local frame's e2 axis
	 * @param e3 local frame's e3 axis, e3//k
	 */
	public Polarization(Vector3 xi, Vector3 e1, Vector3 e2, Vector3 e3){	
		this.L = Math.sqrt(Math.pow(xi.getX(), 2) + Math.pow(xi.getZ(), 2));
		this.A = xi.getY();
		this.P = Math.sqrt(Math.pow(L, 2) + Math.pow(A, 2));
		
		double tau = 0.5 * Math.atan2(xi.getX(),xi.getZ());
		this.E = new Vector3(Math.cos(tau) * e1.getX() + Math.sin(tau) * e2.getX(),
				                  Math.cos(tau) * e1.getY() + Math.sin(tau) * e2.getY(),
				                  Math.cos(tau) * e1.getZ() + Math.sin(tau) * e2.getZ());
		this.B = e3.cross(this.E);
	}
	
	public void rotateX(double a){
		this.E.rotateX(a);
		this.B.rotateX(a);	
	}
	public void rotateY(double a){
		this.E.rotateY(a);
		this.B.rotateY(a);	
	}
	public void rotateZ(double a){
		this.E.rotateZ(a);
		this.B.rotateZ(a);	
	}
	public void boostZ(double gamma){
		
		double beta =  Math.sqrt(1-1/Math.pow(gamma, 2));
		
		double nEx = gamma * (E.getX() - beta * B.getY());
		double nEy = gamma * (E.getY() + beta * B.getX());
		double nEz = E.getZ();
		
		double nBx = gamma * (B.getX() + beta * E.getY());
		double nBy = gamma * (B.getY() - beta * E.getX());
		double nBz = B.getZ();
		
		this.E = new Vector3(nEx,nEy,nEz);
		this.B = new Vector3(nBx,nBy,nBz);	
	}
	/**
	 * compute the Stokes parameter 
	 * EM field and degrees of polarization to Stokes parameters
	 * @param e1 local frame's e1 axis
	 * @param e2 local frame's e2 axis
	 * @param e3 local frame's e3 axis
	 * @return the Stokes parameter in given frame
	 */
	public Vector3 getStokesPara(Vector3 e1, Vector3 e2, Vector3 e3){
		
		double E1 = this.E.dot(e1);
		double E2 = this.E.dot(e2);
		double tau = Math.atan2(E2,E1);
		double xi1 = P * L * Math.sin(2*tau);
		double xi2 = P * A;
		double xi3 = P * L * Math.cos(2*tau);	
		
		return new Vector3(xi1,xi2,xi3);
	}

}
