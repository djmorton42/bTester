package ca.quadrilateral.btester.propertygenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DefaultCollectionPropertyGenerator implements PropertyGenerator<Collection<?>> {

	private final Class<?> objectClass;
	
	public DefaultCollectionPropertyGenerator(final Class<?> objectClass) {
		this.objectClass = objectClass;
	}
	
	@Override
	public Collection<?> generateProperty() {
	    List<Object> objects = Arrays.asList(new Object(), new Object(), new Object());
		
		if (Set.class.isAssignableFrom(objectClass)) {
			return new HashSet(objects);
		} else if (List.class.isAssignableFrom(objectClass)) {
			return new ArrayList(objects);
		} else if (Queue.class.isAssignableFrom(objectClass)) {
			return new PriorityQueue(objects);
		} else if (Collection.class.equals(objectClass)) {
		    return new ArrayList(objects);
		} else {
			throw new UnsupportedOperationException("Generating properties of collections of type " + objectClass + " is not yet supported.");
		}
		
	}

}
