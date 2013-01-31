package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomIntegerPropertyGenerator implements PropertyGenerator<Integer> {
	public Integer generateProperty() {
		return new Random().nextInt();
	}
}
