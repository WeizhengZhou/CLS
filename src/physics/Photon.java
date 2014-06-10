package physics;

import math.Vector4;

public class Photon extends Particle{
	private Polarization polarization = null;
	
	public Photon(Vector4 momentum, Polarization polarization){	
		super(0,momentum);
		this.polarization = polarization;
		assert momentum.norm() == 0;
	}
	@Override
	public void rotateX(double a){
		super.rotateX(a);
		polarization.rotateX(a);
	}
	@Override
    public void rotateY(double a){
		super.rotateY(a);
		polarization.rotateY(a);
		
	}
	@Override
    public void rotateZ(double a){
		super.rotateZ(a);
		polarization.rotateZ(a);
	}
	@Override
	public void boostZ(double gamma){
		super.boostZ(gamma);
		polarization.boostZ(gamma);
	}
	

}
