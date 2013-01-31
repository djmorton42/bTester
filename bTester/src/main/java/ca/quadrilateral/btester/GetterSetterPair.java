package ca.quadrilateral.btester;

import java.lang.reflect.Method;

public class GetterSetterPair {
	private final Method getter;
	private final Method setter;
	private final Class<?> returnType;
	private final String property;

	public GetterSetterPair(final Method getter, final Method setter) {
		this.getter = getter;
		this.setter = setter;
		this.returnType = getter.getReturnType();

        final String setterName = setter.getName();
 

		this.property = setterName.substring(3,4).toLowerCase() + setterName.substring(4);
	}

	public Method getGetter() {
		return this.getter;
	}
	
	public Method getSetter() {
		return this.setter;
	}
	
	public Class<?> getReturnType() {
		return this.returnType;
	}
	public String getProperty() {
		return this.property;
	}
	
}
