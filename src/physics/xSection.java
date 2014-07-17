package physics;

import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
public class xSection {
	/**
	 * compute the cross section using Klein-Nishina formula
	 * @param electron electron in event
	 * @param photon photon in event
	 * @param gamma gamma in event
	 * @return cross section in [m^2]
	 */
	public double klein_nishina(Electron electron, Photon photon, Photon gamma){
		double kl = photon.getEnergy();//energy of incident photon
		double theta = gamma.getMomntum().getTheta();//scattered angle of gamma
		double phi = gamma.getMomntum().getPhi();//scattered anzimutal angle of gamma
		double me = PhysConst.MASS_OF_ELECTRON;//electron mass
		double kg_over_kl = me/(me + kl * (1 + Math.cos(theta)));//temp
		double kl_over_kg = 1 / kg_over_kl;//temp
		double dSigma_dOmega = 0.5 * pow(kg_over_kl,2) * (kl_over_kg + kg_over_kl -
				2 * pow(sin(theta),2) * pow(cos(phi),2));    
		return dSigma_dOmega;
	}
}
