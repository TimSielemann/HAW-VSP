package tests;

import accessor_two.ClassOneImplBase;
import accessor_two.SomeException112;
import accessor_two.SomeException304;

public class ClassOneAccessorTwo extends ClassOneImplBase {
	
	
	
	@Override
	public double methodOne(String param1, double param2)
			throws SomeException112 {
		if (param1.equals("")){
			Werkzeug.printError(this.getClass().getName(), this.toString(),"methodOne", param1, param2,SomeException112.class.getName(),"param = ''");
			throw new SomeException112("param = ''");
		}
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodOne", param1, param2, param2);
		
		return param2;
	}

	@Override
	public double methodTwo(String param1, double param2)
			throws SomeException112, SomeException304 {
		if (param1.equals("")){
			Werkzeug.printError(this.getClass().getName(), this.toString(),"methodTwo", param1, param2,SomeException112.class.getName(),"param = ''");
			throw new SomeException112("param = ''");
		}
		else if (param1.equals("error")){
			Werkzeug.printError(this.getClass().getName(), this.toString(),"methodTwo", param1, param2,SomeException304.class.getName(),"param = 'error'");
			throw new SomeException304("param = 'error'");
		}
		Werkzeug.printResult(this.getClass().getName(), this.toString(), "methodTwo", param1, param2, param2);
		return param2;
	}

}
