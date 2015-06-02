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
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodOne", param1, 0);
		return 0;
	}

	@Override
	public double methodTwo() throws SomeException112 {
		// TODO Auto-generated method stub
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodTwo", 100);
		
		return 100;
	}

}
