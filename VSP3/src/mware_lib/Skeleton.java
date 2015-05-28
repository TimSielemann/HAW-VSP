package mware_lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Skeleton {
	
	private Object object;
	
	public Skeleton(Object object){
		this.object = object;
	}
	
	public Object doMethodCall(String method, Object[] params){

		try {
			Method m = getMethod(method, params);// object.getClass().getMethod(method, classes);
			if (m != null){
				return m.invoke(object, params);				
			}
			else {
				return new Exception("MWARE_LIB Function not available");
			}
		} catch (SecurityException e) {
			return e;
		} catch (IllegalAccessException e) {
			return e;
		} catch (IllegalArgumentException e) {
			return e;
		} catch (InvocationTargetException e) {
			return e;
		}
		
	}

	private Method getMethod(String methodName, Object[] params) {
		methodLoop: for (Method method : object.getClass().getMethods()) {
		    if (!methodName.equals(method.getName())) {
		        continue;
		      }
		      Class<?>[] paramTypes = method.getParameterTypes();
		      if (params == null && paramTypes == null) {
		        return method;
		      } else if (params == null || paramTypes == null
		          || paramTypes.length != params.length) {
		        continue;
		      }

		      for (int i = 0; i < params.length; ++i) {
		        if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
		          continue methodLoop;
		        }
		      }
		      return method;
		    }
		return null;
	}
}
