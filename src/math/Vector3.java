package math;


/**
 * A three-dimensional vector 
 * Support representation of Cartesian and Spherical coordinate system
 * Support vector addition, norm, dot, cross, etc
 * Support 3D rotation
 * @author Weizheng
 *
 */		
public class Vector3 {
	
	private double x = 0.;//Cartesian coordinate system (x,y,z)
	private double y = 0.;
	private double z = 0.;
	private double r = 0.;//Spherical coordinate system (r,theta,phi)
	private double theta = 0.;
	private double phi = 0.;
	
	public Vector3(){}
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;			
		this.updateRThetaPhi();
	}	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	public double getR() {
		return r;
	}
	public double getTheta() {
		return theta;
	}
	public double getPhi() {
		return phi;
	}
	/**
	 * update the Spherical coordinates,
	 * should be called after each mutation
	 */
	private void updateRThetaPhi(){
		//rho = x^2 + y^2;
		double rho = Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y, 2));		
        this.r     = this.norm();                   
        this.theta = Math.atan2(rho,this.z);//sin(theta), cos(theta)
        this.phi   = Math.atan2(this.y, this.x);//sin(phi), cos(phi)
	}
	public String toString(){
		String s = String.format("(r,theta,phi) = (%.4f,%.4f,%.4f),  (x,y,z) = (%.4f,%.4f,%.4f)",
				                  x,y,z,r,theta,phi);
		return s;
	}

	/**
	 * return sqrt(x^2+y^2+z^2)
	 * @return
	 */
	public double norm(){
		return Math.sqrt(Math.pow(this.x, 2) + 
				         Math.pow(this.y, 2) + 
				         Math.pow(this.z, 2));
	}
	/**
	 * @return unit vector of this vector
	 */
	public Vector3 unit(){
		double r = this.norm();
		if(r == 0) 
			return new Vector3(0,0,0);
		else
			return new Vector3(this.x/r,this.y/r,this.z/r);	
	}
	/**
	 * vector addition
	 * @param other
	 * @return addition of two vectors
	 */
	public Vector3 add(Vector3 other){
		return new Vector3(this.x + other.x,
				           this.y + other.y,
				           this.z + other.z);
	}
	/**
	 * vector dot product
	 * @param other vector to dot with
	 * @return this dot other 
	 */
	public double dot(Vector3 other){
		return this.x * other.x +
			   this.y * other.y + 
			   this.z * other.z;	
	}
    /**
     * vector cross product
     * @param other vector to cross with
     * @return
     */
	public Vector3 cross(Vector3 other){
		double nx = this.y * other.z - this.z * other.y;
		double ny = this.z * other.x - this.x * other.z;
		double nz = this.x * other.y - this.y * other.x;
		return new Vector3(nx,ny,nz);
	}
	/**
	 * rotate an angle a around X axis
	 * @param a rotation angle
	 */
	public void rotateX(double a){
		double cosA = Math.cos(a);
		double sinA = Math.sin(a);
		double ny = cosA * this.y - sinA * this.z;
		double nz = sinA * this.y + cosA * this.z; 		
		this.y = ny;
		this.z = nz;	
		this.updateRThetaPhi();//update spherical coordinates 
	}
	/**
	 * rotate an angle a around Y axis
	 * @param a rotation angle
	 */
	public void rotateY(double a){
		double cosA = Math.cos(a);
		double sinA = Math.sin(a);
		double nx =  cosA * this.x + sinA * this.z;
		double nz = -sinA * this.x + cosA * this.z; 
		this.x = nx;
		this.z = nz;
		this.updateRThetaPhi();//update spherical coordinates 
	}
	/**
	 * rotate an angle a around Z axis
	 * @param a rotation angle
	 */
	public void rotateZ(double a){
		double cosA = Math.cos(a);
		double sinA = Math.sin(a);	
		double nx = cosA * this.x - sinA * this.y;
		double ny = sinA * this.x + cosA * this.y; 
		this.x = nx;
		this.y = ny;
		this.updateRThetaPhi();//update spherical coordinates 
	}	
	public static void main(String[] args){
		Vector3 v = new Vector3(0,0,2);
		System.out.println(v);
		v.rotateY(Math.PI);
		System.out.println(v);
		System.out.println(v.unit());
	}
}
