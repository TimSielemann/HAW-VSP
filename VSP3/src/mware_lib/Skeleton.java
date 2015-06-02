package mware_lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Allgemeines Skeleton für den Aufruf an einem Entfernten Objekt
 * @author Tim
 * 
 * Komponente: mware_lib
 */
public class Skeleton {
	
	private Object object;
	
	public Skeleton(Object object){
		this.object = object;
	}
	
	/**
	 * Ruft über Reflections die übergebene Methode anhand ihres Namens und ihrer Parameter auf
	 * @param method
	 * @param params
	 * @return
	 */
	public Object doMethodCall(String method, Object[] params){
		try {
			Method m = getMethod(method, params);// object.getClass().getMethod(method, classes);
			if (m != null){
				return m.invoke(object, params);				
			}
			else {
				return new MWareException("Funktion " + method + " mit Paramaetern " + params + " nicht verfügbar" );
			}
		} catch (SecurityException e) {
			return new MWareException("Funktion " + method + " mit Paramaetern " + params + " nicht verfügbar", e );
		} catch (IllegalAccessException e) {
			return new MWareException("Funktion " + method + " mit Paramaetern " + params + " nicht verfügbar", e );
		} catch (IllegalArgumentException e) {
			return new MWareException("Funktion " + method + " mit Paramaetern " + params + " nicht verfügbar", e );
		} catch (InvocationTargetException e) {
			return e.getCause();
		}
		
	}
	
	/**
	 * Sucht die richtige Methode anhand des Namens und der Parameter
	 * @param methodName
	 * @param params
	 * @return
	 */
	private Method getMethod(String methodName, Object[] params) {
		for (Method method : object.getClass().getMethods()) {
		      Class<?>[] paramTypes = method.getParameterTypes();
		      //Wenn Methodenname und alle Methodenparameter verfügbar sind
		      if (methodName.equals(method.getName()) && allParamsSameType(paramTypes, params))
		    	  return method;
		 }
		return null;
	}
	
	/**
	 * Überprüft ob alle Parameter den selben Namen haben
	 * @param paramTypes
	 * @param params
	 * @return
	 */
	private boolean allParamsSameType(Class<?>[] paramTypes, Object[] params){
		if (params == null && paramTypes == null){
			return true;
		}
		if (params == null || paramTypes == null || paramTypes.length != params.length){
			return false;
		}
		for (int i = 0; i < params.length; ++i) {
	        if (!paramTypes[i].isAssignableFrom(params[i].getClass()) && !(paramTypes[i].isPrimitive() && getWrapperFor(paramTypes[i]).isAssignableFrom(params[i].getClass()))) {
	          return false;
	        }
	     }
		return true;
	}
	
	/**
	 * Holt die Wrappeklasse für die primitiven Datentypen
	 * @param class1
	 * @return
	 */
	private Class<? extends Object> getWrapperFor(Class<?> class1) {
		Class<? extends Object> wrapperclass = Util.PRIMITIVESMAP.get(class1);
		if (wrapperclass == null){
			return class1;
		}
		return wrapperclass;
	}
}
