package nameservice;

public interface INameService {
	
	void rebind(Object servant, String name);
	
	Object resolve(String name, int i);
	
}
