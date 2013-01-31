package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public class EqualityTester implements Tester {
	private static final Logger logger = LoggerFactory.getLogger(EqualityTester.class);
	
	public void executeTest(Object classUnderTest, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator) {
		try {
			final Object property = propertyGenerator.generateProperty();
		
			logger.info("Testing method {} for equality using property value {}", setterMethod, property);

			setterMethod.invoke(classUnderTest, property);
			Assert.assertTrue("get Property Value did not equal set Property value", property.equals(getterMethod.invoke(classUnderTest)));
		} catch (Exception e) {
			throw new TestException("Error executing EqualityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
		}
	}
}
