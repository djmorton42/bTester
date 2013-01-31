/*
bTester License

Copyright 2013 Daniel Morton (djmorton@quadrilateral.ca). All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Daniel Morton ''AS IS'' AND ANY EXPRESS OR IMPLIED
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
or implied, of Daniel Morton.
*/

package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public class IdentityTester extends AbstractTester {
	private static final Logger logger = LoggerFactory.getLogger(IdentityTester.class);
	
	public void executeTest(Object classUnderTest, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator) {
		try {
			final Object property = generateProperty(propertyGenerator);
		
			logger.info("Testing method {} for identity using property value {}", setterMethod,  property);
			
			setterMethod.invoke(classUnderTest, property);
			final Object getValue = getterMethod.invoke(classUnderTest);
			Assert.assertTrue("get Property Value " + getValue + " did not equal set Property value " + property, property == getValue);
		} catch (Exception e) {
			throw new TestException("Error executing IdentityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
		}
	}

}
