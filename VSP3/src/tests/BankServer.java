package tests;

public class BankServer {
	private String name;
	private int version;
	private double betrag;

	public BankServer(String name, double betrag, int version) {
		this.name = name;
		this.betrag = betrag;
		this.version = version;
	}
//accessor_one ClassOne
	public String methodOne(String param1, int param2) throws Exception {

		if (this.version < param2) {
			this.version = param2;
			this.name = param1;
			return "OK. Version auf Nummer " + this.version + " geändert";
		} else {
			throw new Exception("Falsche Versionsnummmer");
		}
	}
	//accessor_one ClassTwo 
	public int methodOne(double param1) throws Exception {
		if(!(param1<0)){
		this.betrag+=param1;
		return this.version++;
		}else{
			throw new Exception("Betrag darf nur erhöht werden");
		}
		
	}
	//accessor_one ClassTwo
	public double methodTwo(){
		return this.betrag;
	}
}
