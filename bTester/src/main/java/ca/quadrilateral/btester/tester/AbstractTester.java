package ca.quadrilateral.btester.tester;

import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

public abstract class AbstractTester implements Tester {
    @Override
    public Object generateProperty(final PropertyGenerator<?> propertyGenerator) {
        return propertyGenerator.generateProperty();
    }
}
