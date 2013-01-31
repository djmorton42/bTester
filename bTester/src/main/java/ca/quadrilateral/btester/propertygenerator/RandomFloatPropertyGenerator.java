package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomFloatPropertyGenerator implements PropertyGenerator<Float> {
	public Float generateProperty() {
		return new Random().nextFloat();
	}
}
