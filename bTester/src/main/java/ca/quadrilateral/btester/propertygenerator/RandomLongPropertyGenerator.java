package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomLongPropertyGenerator implements PropertyGenerator<Long> {
	public Long generateProperty() {
		return new Random().nextLong();
	}
}
