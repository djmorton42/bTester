package ca.quadrilateral.btester.propertygenerator;

import java.io.Serializable;

import junit.framework.Assert;

import org.junit.Test;

import ca.quadrilateral.btester.exception.AbstractPropertyException;
import ca.quadrilateral.btester.tester.AbstractTester;

public class DefaultObjectPropertyGeneratorTest {
	@Test(expected=AbstractPropertyException.class)
	public void interfaceTypePropertyShouldThrowTestException() throws Exception {
		try {
			new DefaultObjectPropertyGenerator(Serializable.class).generateProperty();
		} catch (AbstractPropertyException e) {
			Assert.assertEquals("Incorrect exception message", "Can not instantiate field of type interface java.io.Serializable as it is abstract (or an interface).  Use the .override method of bTester to specify a concrete runtime type", e.getMessage());
			throw e;
		}
	}
	
	@Test(expected=AbstractPropertyException.class)
	public void abstractTypePropertyShouldThrowTestException() throws Exception {
		try {
			new DefaultObjectPropertyGenerator(AbstractTester.class).generateProperty();
		} catch (AbstractPropertyException e) {
			Assert.assertEquals("Incorrect exception message", "Can not instantiate field of type class ca.quadrilateral.btester.tester.AbstractTester as it is abstract (or an interface).  Use the .override method of bTester to specify a concrete runtime type", e.getMessage());			
			throw e;
		}
	}
}
