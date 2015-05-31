package tests;

import accessor_one.ClassOneImplBase;
import accessor_one.SomeException112;

public class ClassOneAccessorOne extends ClassOneImplBase {


	public ClassOneAccessorOne() {

	}

	@Override
	public String methodOne(String param1, int param2) throws SomeException112 {
		if (param2 < 0){
			throw new SomeException112("param2 < 0");
		}
		return param1 + param2;
	}

//	//accessor_one ClassTwo 
//	public int methodOne(double param1) throws Exception {
//		if(!(param1<0)){
//		this.betrag+=param1;
//		return this.version++;
//		}else{
//			throw new Exception("Betrag darf nur erhöht werden");
//		}
//		
//	}
	//accessor_one ClassTwo
//	public double methodTwo(){
//		return this.betrag;
//	}
}
