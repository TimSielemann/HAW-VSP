package tests;

import accessor_one.ClassTwoImplBase;
import accessor_one.SomeException110;
import accessor_one.SomeException112;

public class ClassTwoAccessorOne extends ClassTwoImplBase {
	
	private double param1 = 0;
	
	@Override
	public int methodOne(double param1) throws SomeException110 {
		if (param1 < 0){
			throw new SomeException110("param1 < 0");
		}
		this.param1 = param1;
		return (int) (param1 % 100);
	}

	@Override
	public double methodTwo() throws SomeException112 {
		if (this.param1 == 0){
			throw new SomeException112("param1 not set...");
		}
		return this.param1;
	}

}
