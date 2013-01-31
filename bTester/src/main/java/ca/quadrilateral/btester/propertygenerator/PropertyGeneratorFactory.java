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
