package tests;

import accessor_one.ClassTwoImplBase;
import accessor_one.SomeException110;
import accessor_one.SomeException112;

public class ClassTwoAccessorOne extends ClassTwoImplBase {
	
	private double param1 = 0;
	
	@Override
	public int methodOne(double param1) throws SomeException110 {
		if (param1 < 0){
			Werkzeug.printError(this.getClass().getName(), this.toString(),"methodOne", param1,SomeException110.class.getName(),"param1 < 0");
			throw new SomeException110("param1 < 0");
		}
		this.param1 = param1;
		int result = (int) (param1 % 100);
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodOne", param1,result );
		
		return result;
	}

	@Override
	public double methodTwo() throws SomeException112 {
		if (this.param1 == 0){
			Werkzeug.printError(this.getClass().getName(), this.toString(),"methodOne", SomeException110.class.getName(),"param1 not set...");
			throw new SomeException112("param1 not set...");
		}Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodTwo", this.param1 );
		
		return this.param1;
	}

}
