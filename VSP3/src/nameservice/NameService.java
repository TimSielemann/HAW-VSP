package nameservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mware_lib.EntferntesReferenzmodul;
import mware_lib.Kommunikationsmodul;
import mware_lib.Skeleton;

public class NameService implements INameService{
	
	private Map<String, Object> map;
	private Kommunikationsmodul kommModul;
	
	public NameService(int port) throws IOException{
		map = new HashMap<String, Object>();
		EntferntesReferenzmodul.getInstance().put("nameservice", new Skeleton(this));
		kommModul = new Kommunikationsmodul(port);
		
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length < 1){
			args =new String[]{ ""+25645 };
		}
		new NameService(Integer.parseInt(args[0]));
	}

	
	public void rebind(Object servant, String name) {
		this.map.put(name, servant);
	}

	
	public Object resolve(String name) {		
		return this.map.get(name);
	}

}
