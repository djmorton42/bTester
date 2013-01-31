package ca.quadrilateral.btester;

import java.lang.reflect.Method;

import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;
import ca.quadrilateral.btester.tester.Tester;

public class PropertyTestConfiguration {
	private Method getterMethod;
	private Method setterMethod;
	private Tester tester;
	private PropertyGenerator<?> generator;
	
	public PropertyTestConfiguration(final Method getterMethod, final Method setterMethod, final Tester tester, final PropertyGenerator<?> generator) {
		this.getterMethod = getterMethod;
		this.setterMethod = setterMethod;
		this.tester = tester;
		this.generator = generator;
	}
	public Method getGetterMethod() {
		return getterMethod;
	}
	public void setGetterMethod(Method getterMethod) {
		this.getterMethod = getterMethod;
	}
	public Method getSetterMethod() {
		return setterMethod;
	}
	public void setSetterMethod(Method setterMethod) {
		this.setterMethod = setterMethod;
	}
	public Tester getTester() {
		return tester;
	}
	public void setTester(Tester tester) {
		this.tester = tester;
	}
	public PropertyGenerator<?> getGenerator() {
		return generator;
	}
	public void setGenerator(PropertyGenerator<?> generator) {
		this.generator = generator;
	}

	
	
}
