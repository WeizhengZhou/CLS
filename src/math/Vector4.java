package math;

public class Vector4 {
	private double t = 0.;
	private Vector3 xyz = null;
	
	public Vector4(){
		this.t = 0;
		this.xyz = new Vector3();
	}
	public Vector4(double t, double x, double y, double z){
		this.t = t;
		this.xyz = new Vector3(x,y,z);
	}
	public Vector4(double t, Vector3 xyz){
		this.t = t;
		this.xyz = xyz;	
	}
	public double getT(){
		return this.t;
	}
	public Vector3 getXYZ(){
		return this.xyz;
	}

	public Vector4 add(Vector4 other){
		return new Vector4(this.t + other.t, this.xyz.add(other.xyz));		
	}

	public double norm(){
		return Math.sqrt(
				Math.pow(this.t, 2) - Math.pow(this.xyz.norm(), 2));
	}
	public double dot(Vector4 other){
		return this.t * other.t - this.xyz.dot(other.xyz);
		
	}
	public void rotateX(double a){
		xyz.rotateX(a);
	}
	public void rotateY(double a){
		xyz.rotateY(a);
	}
	public void rotateZ(double a){
		xyz.rotateZ(a);
	}
    public void boostZ(double gamma){
		
		assert gamma>=1:gamma;	

		double beta =  Math.sqrt(1-1/Math.pow(gamma, 2));
		
		double t = this.t;
		double x = xyz.getX();
		double y = xyz.getY();
		double z = xyz.getZ();
		
		double nt =  gamma * t        - gamma * beta * z;
		double nz = -gamma * beta * t + gamma * z ;	
		
		this.t = nt;
		this.xyz = new Vector3(x,y,nz);
	}
    public String toString(){
    	String s1 = xyz.toString();
    	String s2 = String.format(", t = %.4f", this.t);
    	return s1+s2;
    }

}
