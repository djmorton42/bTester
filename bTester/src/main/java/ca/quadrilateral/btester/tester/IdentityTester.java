package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public class IdentityTester implements Tester {
	private static final Logger logger = LoggerFactory.getLogger(IdentityTester.class);
	
	public void executeTest(Object classUnderTest, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator) {
		try {
			final Object property = propertyGenerator.generateProperty();
		
			logger.info("Testing method {} for identity using property value {}", setterMethod,  property);
			
			setterMethod.invoke(classUnderTest, property);
			final Object getValue = getterMethod.invoke(classUnderTest);
			Assert.assertTrue("get Property Value " + getValue + " did not equal set Property value " + property, property == getValue);
		} catch (Exception e) {
			throw new TestException("Error executing IdentityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
		}
	}

}
