package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomBytePropertyGenerator implements PropertyGenerator<Byte> {
	public Byte generateProperty() {
		return (byte)new Random().nextInt();
	}
}
