package ca.quadrilateral.btester.propertygenerator;

import org.apache.commons.lang.RandomStringUtils;

public class RandomCharacterPropertyGenerator implements PropertyGenerator<Character> {
	public Character generateProperty() {
		return RandomStringUtils.randomAlphanumeric(1).charAt(0);
	}
}
