package ca.quadrilateral.btester.propertygenerator;

import java.util.Random;

public class RandomBooleanPropertyGenerator implements PropertyGenerator<Boolean> {

	public Boolean generateProperty() {
		return new Random().nextBoolean();
	}

}
