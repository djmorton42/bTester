package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomShortPropertyGenerator implements PropertyGenerator<Short> {
	public Short generateProperty() {
		return (short)new Random().nextInt();
	}
}
