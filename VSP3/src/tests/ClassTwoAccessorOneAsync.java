package tests;

import accessor_one.ClassTwoImplBase;
import accessor_one.SomeException110;
import accessor_one.SomeException112;

public class ClassTwoAccessorOneAsync extends ClassTwoImplBase {

	@Override
	public int methodOne(double param1) throws SomeException110 {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public double methodTwo() throws SomeException112 {
		// TODO Auto-generated method stub
		return 100;
	}

}
