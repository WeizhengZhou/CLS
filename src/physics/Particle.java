package physics;

import math.Vector3;
import math.Vector4;

public abstract class Particle {
	
	private final double mass;
	private Vector4 momentum = null;
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
