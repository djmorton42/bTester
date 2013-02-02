package ca.quadrilateral.btester.propertygenerator;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class DefaultMapPropertyGenerator implements PropertyGenerator<Map<?, ?>> {

	@Override
	public Map<?, ?> generateProperty() {
		return new HashMap();
	}

}
