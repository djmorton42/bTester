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

package ca.quadrilateral.btester.propertygenerator;

import java.util.HashMap;
import java.util.Map;

import ca.quadrilateral.btester.exception.TestException;

public class PropertyGeneratorFactory {
	public static Map<Class<?>, Class<?>> propertyGeneratorClassMap = new HashMap<Class<?>, Class<?>>();
	
	static {
		propertyGeneratorClassMap.put(Integer.class, RandomIntegerPropertyGenerator.class);
		propertyGeneratorClassMap.put(Integer.TYPE, RandomIntegerPropertyGenerator.class);
		
		propertyGeneratorClassMap.put(Long.class, RandomLongPropertyGenerator.class);
		propertyGeneratorClassMap.put(Long.TYPE, RandomLongPropertyGenerator.class);
		propertyGeneratorClassMap.put(Short.class, RandomShortPropertyGenerator.class);
		propertyGeneratorClassMap.put(Short.TYPE, RandomShortPropertyGenerator.class);
		propertyGeneratorClassMap.put(Byte.class, RandomBytePropertyGenerator.class);
		propertyGeneratorClassMap.put(Byte.TYPE, RandomBytePropertyGenerator.class);

		propertyGeneratorClassMap.put(Character.class, RandomCharacterPropertyGenerator.class);
		propertyGeneratorClassMap.put(Character.TYPE, RandomCharacterPropertyGenerator.class);

		propertyGeneratorClassMap.put(Double.class, RandomDoublePropertyGenerator.class);
		propertyGeneratorClassMap.put(Double.TYPE, RandomDoublePropertyGenerator.class);
		propertyGeneratorClassMap.put(Float.class, RandomFloatPropertyGenerator.class);
		propertyGeneratorClassMap.put(Float.TYPE, RandomFloatPropertyGenerator.class);
		
		propertyGeneratorClassMap.put(Boolean.class, RandomBooleanPropertyGenerator.class);
		propertyGeneratorClassMap.put(Boolean.TYPE, RandomBooleanPropertyGenerator.class);
		propertyGeneratorClassMap.put(String.class, RandomStringPropertyGenerator.class);
	}	
	
	public PropertyGenerator<?> getPropertyGenerator(Class<?> propertyType)  {
		Class<?> generatorClass = propertyGeneratorClassMap.get(propertyType);
		if (generatorClass == null) {
			return new DefaultObjectPropertyGenerator(propertyType);
		} else {		
			try {
			return (PropertyGenerator<?>) generatorClass.newInstance();
			} catch (Exception e) {
				throw new TestException("Error generating PropertyGenerator for type " + propertyType, e);
			}
		}
	}
}
