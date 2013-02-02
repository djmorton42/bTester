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

package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomBooleanPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomBytePropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomCharacterPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomDoublePropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomFloatPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomIntegerPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomLongPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomShortPropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.RandomStringPropertyGenerator;

public class ArrayElementEqualityTester extends AbstractTester {
    private static final Logger logger = LoggerFactory.getLogger(EqualityTester.class);
    
    @Override
    protected void executeTestImpl(Object classUnderTest, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator) {
        try {
            final Object property = generateProperty(propertyGenerator);
        
            logger.info("Testing method {} for equality using property value {}", setterMethod, property);

            setterMethod.invoke(classUnderTest, property);
            
            final Object retrievedValue = getterMethod.invoke(classUnderTest);
            
            if (property.getClass().isArray()) {
                final Class<?> componentType = property.getClass().getComponentType();
                if (componentType.isPrimitive()) {
                    if (Integer.TYPE.equals(componentType)) {
                        final int[] propertyValueArray = (int[])property;
                        final int[] retrievedValueArray = (int[])retrievedValue;
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Long.TYPE.equals(componentType)) {
                        final long[] propertyValueArray = (long[])property;
                        final long[] retrievedValueArray = (long[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Short.TYPE.equals(componentType)) {
                        final short[] propertyValueArray = (short[])property;
                        final short[] retrievedValueArray = (short[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Byte.TYPE.equals(componentType)) {
                        final byte[] propertyValueArray = (byte[])property;
                        final byte[] retrievedValueArray = (byte[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Character.TYPE.equals(componentType)) {
                        final char[] propertyValueArray = (char[])property;
                        final char[] retrievedValueArray = (char[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Double.TYPE.equals(componentType)) {
                        final double[] propertyValueArray = (double[])property;
                        final double[] retrievedValueArray = (double[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Float.TYPE.equals(componentType)) {
                        final float[] propertyValueArray = (float[])property;
                        final float[] retrievedValueArray = (float[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    } else if (Boolean.TYPE.equals(componentType)) {
                        final boolean[] propertyValueArray = (boolean[])property;
                        final boolean[] retrievedValueArray = (boolean[])retrievedValue;
                        
                        Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                    }
                } else {
                    final Object[] propertyValueArray = (Object[])property;
                    final Object[] retrievedValueArray = (Object[])retrievedValue;
                    Assert.assertTrue("Arrays should be equal", Arrays.equals(propertyValueArray, retrievedValueArray));
                }
            } else {
                throw new TestException("ArrayElementEqualityTester may only be used on arrays");
            }
        } catch (Exception e) {
            throw new TestException("Error executing EqualityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
        }
    }
}
