package mware_lib;

import java.io.Serializable;

public class RequestObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8095215012620511694L;
	private String objectRefName;
	private String methodName;
	private Object[] params;
	
	public RequestObject(String objectRefName, String methodName,
			Object[] params) {
		super();
		this.objectRefName = objectRefName;
		this.methodName = methodName;
		this.params = params;
	}

	public String getObjectRefName() {
		return objectRefName;
	}

	public void setObjectRefName(String objectRefName) {
		this.objectRefName = objectRefName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
	
	
}
