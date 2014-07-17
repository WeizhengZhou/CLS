package physics;

import math.Vector3;
import math.Vector4;
/**
 * A Particle class represents a particle, with position and momentum
 * @author Weizheng
 *
 */
public abstract class Particle {	
	private final double mass;//mass of the particle
	private Vector4 momentum = null;//four-momentum of the particle
	public Particle(double mass, Vector4 momentum){
		this.mass = mass;
		this.momentum = momentum;
	}
	public double getEnergy(){
		return momentum.getT();
	}
	public Vector3 getMomntum(){
		return momentum.getXYZ();
	}
	public double getMass(){
		return this.mass;
	}
	public void rotateX(double a){
		this.momentum.rotateX(a);
	}
	public void rotateY(double a){
		this.momentum.rotateY(a);	
	}
	public void rotateZ(double a){
		this.momentum.rotateZ(a);	
	}
	public void boostZ(double gamma){
		this.momentum.boostZ(gamma);
	}	
	

}
