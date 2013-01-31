package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomDoublePropertyGenerator implements PropertyGenerator<Double> {
	public Double generateProperty() {
		return new Random().nextDouble();
	}
}
