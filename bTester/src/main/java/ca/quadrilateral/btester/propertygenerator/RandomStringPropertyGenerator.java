package ca.quadrilateral.btester.propertygenerator;

import org.apache.commons.lang.RandomStringUtils;

public class RandomStringPropertyGenerator implements PropertyGenerator<String> {

	public String generateProperty() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

}
