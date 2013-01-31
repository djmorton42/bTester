package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;

import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public interface Tester {
	void executeTest(Object classUnderTetst, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator);
}
