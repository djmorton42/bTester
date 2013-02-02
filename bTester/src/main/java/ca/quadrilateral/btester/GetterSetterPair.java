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

package ca.quadrilateral.btester;

import java.lang.reflect.Method;

import ca.quadrilateral.btester.exception.TestException;

/**
 * An immutable getter/setter tuple. This tuple provides access to the underlying class {@link Method} and provides
 * other higher-level bean information.
 */
public class GetterSetterPair {
	private final Method getter;
	private final Method setter;
	private final Class<?> returnType;
	private final String property;

	public GetterSetterPair(final Method getter, final Method setter) {
		this.getter = getter;
		this.setter = setter;
		this.returnType = getter.getReturnType();

        final String setterName = setter.getName();

		this.property = setterName.substring(3,4).toLowerCase() + setterName.substring(4);
	}
	
	public GetterSetterPair(final Method getter, final Method setter, final Class<?> returnType) {
		this.getter = getter;
		this.setter = setter;
		this.returnType = returnType == null ? getter.getReturnType() : returnType;
		
		if (returnType != null) {
			if (!getter.getReturnType().isAssignableFrom(returnType)) {
				throw new TestException("The forced return type " + returnType.getName() + " can not be used for property " + setter.getName() + " because it is incompatible with the method return type " + getter.getReturnType());				
			}
		}

        final String setterName = setter.getName();

		this.property = setterName.substring(3,4).toLowerCase() + setterName.substring(4);
		
	}

	public Method getGetter() {
		return this.getter;
	}
	
	public Method getSetter() {
		return this.setter;
	}
	
	public Class<?> getReturnType() {
		return this.returnType;
	}

	public String getProperty() {
		return this.property;
	}
}
