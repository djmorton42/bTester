/*
bTester License

Copyright 2013 Daniel Morton (djmorton@quadrilateral.ca) and CONTRIBUTORS. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Daniel Morton and CONTRIBUTORS ''AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Daniel Morton OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Daniel Morton or contributors.
 */

package ca.quadrilateral.btester.discovery;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class ParentInclusiveTestableMethodDiscoverer extends AbstractTestableMethodDiscoverer {

	@Override
	public Collection<Method> discover(final Class<?> clazz) {
		final Set<Method> methods = getMethods(clazz);
		
		super.removePrivateMethods(methods);
		
		return methods;
	}

	@Override
	public Method getAssociatedSetter(final Class<?> clazz, final Method getter) throws NoSuchMethodException {
		final String setterName = super.getSetterEquivalent(getter);
		final Class<?> returnType = getter.getReturnType();
		
		return getMethod(clazz, setterName, returnType);
	}
	
	private Method getMethod(final Class<?> clazz, final String methodName, final Class<?> returnType) throws NoSuchMethodException {
		if (clazz == null) {
			throw new NoSuchMethodException("No method with name " + methodName + " found");
		}
		try {
			return clazz.getDeclaredMethod(methodName, returnType);
		} catch (NoSuchMethodException e) {
			return getMethod(clazz.getSuperclass(), methodName, returnType);
		}
	}

	private Set<Method> getMethods(final Class<?> clazz) {
		final Set<Method> methodSet = new HashSet<Method>();
		
		for(MethodSetWrapper wrapper : getMethodWrappers(clazz)) {
			methodSet.add(wrapper.getMethod());
		}
		
		return methodSet;
	}
	
	private Set<MethodSetWrapper> getMethodWrappers(final Class<?> clazz) {		
		final Set<MethodSetWrapper> wrapperSet = new HashSet<MethodSetWrapper>();
		
		if (clazz != null) { 		
			final Method[] declaredMethods = clazz.getDeclaredMethods();
			for(Method method : declaredMethods) {
				final MethodSetWrapper methodWrapper = new MethodSetWrapper(method);
				if (!wrapperSet.contains(methodWrapper)) {
					wrapperSet.add(methodWrapper);
				}
			}
			
			wrapperSet.addAll(getMethodWrappers(clazz.getSuperclass()));
		}
		
		return wrapperSet;		
	}
	
	private static final class MethodSetWrapper {
		private final Method method;
		
		public MethodSetWrapper(final Method method) {
			this.method = method;
		}
		
		public Method getMethod() {
			return this.method;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || !Method.class.isAssignableFrom(o.getClass())) {
				return false;
			} else {
				final Method other = (Method)o;
				return this.method.getName().equals(other.getName());
			}
		}
		
		@Override
		public int hashCode() {
			return new HashCodeBuilder()
				.append(this.method.getName())
				.toHashCode();
		}
	}
}
