/*
bTester License

Copyright 2013 Daniel Morton (djmorton@quadrilateral.ca) and CONTRIBUTORS. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Daniel Morton and CONTRIBUTORS ''AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Daniel Morton OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Daniel Morton or contributors.
 */

package ca.quadrilateral.btester;

import java.lang.reflect.Method;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import ca.quadrilateral.btester.exception.NoDefaultConstructorException;
import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;
import ca.quadrilateral.btester.tester.AbstractTester;
import ca.quadrilateral.btester.tester.Tester;

public final class BTesterTest
{
    final class NonStaticInner {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class StaticInner {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class NoDefaultConstructor {
        private String a;
        NoDefaultConstructor(final Object o){}
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class PrivateDefaultConstructor {
        private String a;
        private PrivateDefaultConstructor(){}
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class PackagePrivateMethods {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class ProtectedMethods {
        private String a;
        protected String getA() { return this.a; }
        protected void setA(final String a) { this.a = a; }
    }
    
    final static class PrivateMethods {
    	private String a;
    	private String b;
    	
    	String getA() { return this.a; }
    	void setA(final String a) { 
    		this.a = a;
    		setB(getB() + a);
    	}
    	
    	private String getB() { return this.b; }
    	private void setB(final String b) { this.b = b; }
    }

    private static class DoubleBTester extends AbstractTester {
        @Override
    	protected void executeTestImpl(final Object classUnderTest, final Method setterMethod, final Method getterMethod, final PropertyGenerator<?> propertyGenerator) {
    		try {
    			final String property = (String)generateProperty(propertyGenerator);
    			final String doubleProperty = property + property;
    		
    			setterMethod.invoke(classUnderTest, property);
    			Assert.assertTrue("get Property Value did not equal set Property value", doubleProperty.equals(getterMethod.invoke(classUnderTest)));
    		} catch (Exception e) {
    			throw new TestException("Error executing EqualityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
    		}
    	}
    }

    
    @Test
    public void testHandlesAbstractSuperMethods() {
    	final Collection<String> testedProperties = new BTester(SubClass.class).test();
    	Assert.assertTrue("Abstract superclass public property 'a' should have been tested", testedProperties.contains("a"));
    	Assert.assertTrue("Abstract superclass protected property 'c' should have been tested", testedProperties.contains("c"));
    	Assert.assertTrue("Subclass property 'b' should have been tested", testedProperties.contains("b"));
    }

    @Test
    public void testDoesNotSeePrivateMethods() {
    	final Collection<String> testedProperties = new BTester(PrivateMethods.class).test();
    	Assert.assertTrue("Package private property 'a' should have been tested", testedProperties.contains("a"));
    	Assert.assertTrue("Private property 'b' should not have been tested", !testedProperties.contains("b"));
    }
    
    @Test
    public void overridenMethodShouldBeTestedInsteadOfSuperMethod() {
    	final Collection<String> testedProperties = new BTester(SubSubClass.class).override("b", new DoubleBTester()).test();
    	Assert.assertTrue("Abstract superclass public property 'a' should have been tested", testedProperties.contains("a"));
    	Assert.assertTrue("Abstract superclass protected property 'c' should have been tested", testedProperties.contains("c"));
    	Assert.assertTrue("SubSubclass property 'b' should have been tested", testedProperties.contains("b"));    			
    }
    
    
    @Test
    public void testSeesPackagePrivateMethods() {
        new BTester(PackagePrivateMethods.class).test();
    }

    @Test
    public void testSeesProtectedMethods() {
        new BTester(ProtectedMethods.class).test();
    }

    @Test
    public void testCanCreateTopLevel() {
        new BTester(BTesterTest.class).test();
    }

    @Test
    public void testCanCreateNonStaticInner() {
        new BTester(NonStaticInner.class).test();
    }

    @Test
    public void testCanCreateStaticInner() {
        new BTester(StaticInner.class).test();
    }

    @Test
    public void testCanCreateNonTopLevel() {
        new BTester(NonTopLevel.class).test();
    }

    @Test(expected = NoDefaultConstructorException.class)
    public void testCanCreateNoDefaultConstructor()
    {
        new BTester(NoDefaultConstructor.class).test();
    }

    @Test(expected = NoDefaultConstructorException.class)
    public void testCanCreatePrivateDefaultConstructor()
    {
        new BTester(PrivateDefaultConstructor.class).test();
    }
}

final class NonTopLevel {
    private String a;
    String getA() { return this.a; }
    void setA(final String a) { this.a = a; }
}
