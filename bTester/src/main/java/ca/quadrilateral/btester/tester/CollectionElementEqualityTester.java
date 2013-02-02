package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public class CollectionElementEqualityTester extends AbstractTester {
    private static final Logger logger = LoggerFactory.getLogger(CollectionElementEqualityTester.class);
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void executeTestImpl(Object classUnderTest, Method setterMethod, Method getterMethod, PropertyGenerator<?> propertyGenerator) {
        try {
            final Object property = generateProperty(propertyGenerator);
        
            logger.info("Testing method {} for collection equality using property value {}", setterMethod, property);

            setterMethod.invoke(classUnderTest, property);
            
            final Object resultProperty = getterMethod.invoke(classUnderTest);

            if (!Collection.class.isAssignableFrom(property.getClass())) {
                throw new TestException("Property tested by CollectionElementEqualityTester was not a Collection");
            }
            
            final List<?> propertyCollection = new ArrayList((Collection)property);
            final List<?> resultPropertyCollection = new ArrayList((Collection)resultProperty);
            
            Assert.assertEquals("Collections are not the same length", propertyCollection.size(), resultPropertyCollection.size());
            for(Object o : propertyCollection) {
                Assert.assertTrue("Result collection did not contain expected values", resultPropertyCollection.contains(o));
            }
        } catch (Exception e) {
            throw new TestException("Error executing CollectionElementEqualityTester for method " + getterMethod.getName() + " of class " + classUnderTest.getClass(), e);
        }        
    }

}
