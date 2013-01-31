package ca.quadrilateral.btester.propertygenerator;

public class DefaultObjectPropertyGenerator implements PropertyGenerator<Object> {

	private final Class<?> objectClass;
	
	public DefaultObjectPropertyGenerator(final Class<?> objectClass) {
		this.objectClass = objectClass;
	}
	
	public Object generateProperty() {
		try {
			return objectClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating new object of type " + objectClass.getName());
		}
	}

}
