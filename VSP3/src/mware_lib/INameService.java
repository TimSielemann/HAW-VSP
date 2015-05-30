package mware_lib;

public interface INameService {
	
	public static final String NAMESERVICENAME = "nameservice";
	
	
	void rebind(Object servant, String name);
	
	Object resolve(String name);
	
}
