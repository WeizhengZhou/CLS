package physics;

import math.Vector4;

public class Electron extends Particle{
	public Electron(Vector4 momentum){
		super(PhysConst.MASS_OF_ELECTRON, momentum);
		assert momentum.norm()>0;//mass is larger than 0
	}
	public double getLorentzGamma(){
		return this.getEnergy()/PhysConst.MASS_OF_ELECTRON;	
	}
}
