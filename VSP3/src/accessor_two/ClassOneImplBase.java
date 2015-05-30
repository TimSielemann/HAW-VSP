package accessor_two;



public abstract class ClassOneImplBase {

	public double methodOne(String param1, double param2) throws SomeException112{
		return param2;
		
	}
	public double methodTwo (String param1, double param2) throws SomeException112, SomeException304{
		return param2;
		
	}
	public static ClassOneImplBase narrowCast(Object rawObjectRef){
		return null;
		
	}

	

}
