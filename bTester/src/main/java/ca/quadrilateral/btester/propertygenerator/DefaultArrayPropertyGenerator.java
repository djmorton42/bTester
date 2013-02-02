package ca.quadrilateral.btester.propertygenerator;

import java.lang.reflect.Array;

public class DefaultArrayPropertyGenerator implements PropertyGenerator<Object> {
    private static final Integer ARRAY_ELEMENT_COUNT = 10;
	private final Class<?> arrayComponentType;
	
	public DefaultArrayPropertyGenerator(final Class<?> arrayComponentType) {
		this.arrayComponentType = arrayComponentType;
	}
	
	@Override
	public Object generateProperty() {
	    final PropertyGenerator<?> arrayContentPropertyGenerator = new PropertyGeneratorFactory().getPropertyGenerator(arrayComponentType);
	    
	    Object arrayObject = Array.newInstance(arrayComponentType, ARRAY_ELEMENT_COUNT);
	    if (arrayComponentType.isPrimitive()) {
	        if (Integer.TYPE.equals(arrayComponentType)) {
	            int[] array = (int[])arrayObject;
	            for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
	               array[i] = (Integer)arrayContentPropertyGenerator.generateProperty();
	            }
	        } else if (Long.TYPE.equals(arrayComponentType)) {
                long[] array = (long[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Long)arrayContentPropertyGenerator.generateProperty();
                }	            
	        } else if (Short.TYPE.equals(arrayComponentType)) {
               short[] array = (short[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Short)arrayContentPropertyGenerator.generateProperty();
                }
	        } else if (Byte.TYPE.equals(arrayComponentType)) {
                byte[] array = (byte[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Byte)arrayContentPropertyGenerator.generateProperty();
                }	            
	        } else if (Character.TYPE.equals(arrayComponentType)) {
                char[] array = (char[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Character)arrayContentPropertyGenerator.generateProperty();
                }	            
	        } else if (Double.TYPE.equals(arrayComponentType)) {
                double[] array = (double[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Double)arrayContentPropertyGenerator.generateProperty();
                }	            
	        } else if (Float.TYPE.equals(arrayComponentType)) {
                float[] array = (float[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Float)arrayContentPropertyGenerator.generateProperty();
                }	            
	        } else if (Boolean.TYPE.equals(arrayComponentType)) {
                boolean[] array = (boolean[])arrayObject;
                for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
                   array[i] = (Boolean)arrayContentPropertyGenerator.generateProperty();
                }	            
	        }
	    } else {
	        Object[] array = (Object[])arrayObject;
	        for(int i = 0; i < ARRAY_ELEMENT_COUNT; i++) {
	            array[i] = arrayContentPropertyGenerator.generateProperty();
	        }
	    }
	    
		return arrayObject;
	}
}
