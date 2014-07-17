package math;
/**
 * A Vector4 class represent a four dimensional vector with Minkowski metric.
 * For example, (t,x,y,z) is a four vector with s^2 = t^2 -x^2 - y^2 - z^2. 
 * @author Weizheng
 *
 */

public class Vector4 {
	private double t = 0.;//time component
	private Vector3 xyz = null;//Spatial component
	
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
	public String toString(){
		String s1 = xyz.toString();
		String s2 = String.format(", t = %.4f", this.t);
		return s1+s2;
	}
	/**
	 * add two vector4
	 * @param other this + other
	 * @return
	 */
	public Vector4 add(Vector4 other){
		return new Vector4(this.t + other.t, this.xyz.add(other.xyz));		
	}
	/**
	 * norm of vector4
	 * @return t^2 - x^2 - y^2 - z^2
	 */
	public double norm(){
		return Math.sqrt(
				Math.pow(this.t, 2) - Math.pow(this.xyz.norm(), 2));
	}
	/**
	 * dot product of two vector 4
	 * @param other another vector4 to dot with
	 * @return
	 */
	public double dot(Vector4 other){
		return this.t * other.t - this.xyz.dot(other.xyz);		
	}
	/**
	 * rotate xyz component of vector4, respect to X axis
	 * @param a rotation angle [rad]
	 */
	public void rotateX(double a){
		xyz.rotateX(a);
	}
	/**
	 * rotate xyz component of vector4, respect to Y axis
	 * @param a rotation angle [rad]
	 */
	public void rotateY(double a){
		xyz.rotateY(a);
	}
	/**
	 * rotate xyz component of vector4, respect to Z axis
	 * @param a rotation angle [rad]
	 */
	public void rotateZ(double a){
		xyz.rotateZ(a);
	}
	/**
	 * boost vector4 in Z direction, using Lorentz transformation
	 * @param gamma Lorentz transformation gamma
	 */
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
 
}
