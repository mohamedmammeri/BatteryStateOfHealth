

public class GrapheModele {
	double SOH,Cm;
	int days;
	
	public GrapheModele(double SOH,double Cm,int days) {
		this.SOH=SOH;
		this.Cm=Cm;
		this.days=days;
		
	}
	

	public double getSOH() {
		return SOH;
	}

	public double getCm() {
		return Cm;
	}

	public int getDays() {
		return days;
	}

}
