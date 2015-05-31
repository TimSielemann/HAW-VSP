package tests;

import accessor_two.ClassOneImplBase;
import accessor_two.SomeException112;
import accessor_two.SomeException304;

public class ClassOneAccessorTwo extends ClassOneImplBase {
	
	
	
	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		if (param1.equals("")){
			throw new SomeException112("param = ''");
		}
		return param2;
	}

	@Override
	public double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304 {
		if (param1.equals("")){
			throw new SomeException112("param = ''");
		}
		else if (param1.equals("error")){
			throw new SomeException304("param = 'error'");
		}
		return param2;
	}

}
