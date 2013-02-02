package ca.quadrilateral.btester.propertygenerator;

import java.lang.reflect.Array;

public class DefaultArrayPropertyGenerator implements PropertyGenerator<Object> {
	private final Class<?> arrayComponentType;
	
	public DefaultArrayPropertyGenerator(final Class<?> arrayComponentType) {
		this.arrayComponentType = arrayComponentType;
	}
	
	@Override
	public Object generateProperty() {
		return Array.newInstance(arrayComponentType, 10);
	}
}
