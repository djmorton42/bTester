package ca.quadrilateral.btester.tester;

import java.util.HashMap;
import java.util.Map;

import ca.quadrilateral.btester.exception.TestException;

public class TesterFactory {
	public static Map<Class<?>, Class<?>> testerClassMap = new HashMap<Class<?>, Class<?>>();
	
	static {
		testerClassMap.put(Integer.class, IdentityTester.class);
		testerClassMap.put(Integer.TYPE, EqualityTester.class);
		
		testerClassMap.put(Long.class, IdentityTester.class);
		testerClassMap.put(Long.TYPE, EqualityTester.class);
		testerClassMap.put(Short.class, IdentityTester.class);
		testerClassMap.put(Short.TYPE, EqualityTester.class);
		testerClassMap.put(Byte.class, IdentityTester.class);
		testerClassMap.put(Byte.TYPE, EqualityTester.class);

		testerClassMap.put(Character.class, IdentityTester.class);
		testerClassMap.put(Character.TYPE, EqualityTester.class);

		testerClassMap.put(Double.class, IdentityTester.class);
		testerClassMap.put(Double.TYPE, EqualityTester.class);
		testerClassMap.put(Float.class, IdentityTester.class);
		testerClassMap.put(Float.TYPE, EqualityTester.class);
		
		testerClassMap.put(Boolean.class, IdentityTester.class);
		testerClassMap.put(Boolean.TYPE, EqualityTester.class);
		testerClassMap.put(String.class, IdentityTester.class);
	}	
	
	public Tester getTester(Class<?> propertyType) {
		final Class<?> tester = testerClassMap.get(propertyType);
		if (tester == null) {
			return new IdentityTester();
		} else {
			try {
				return (Tester) tester.newInstance();
			} catch (Exception e) {
				throw new TestException("Error generating Tester for type " + propertyType, e);
			}
		}
	}
}
