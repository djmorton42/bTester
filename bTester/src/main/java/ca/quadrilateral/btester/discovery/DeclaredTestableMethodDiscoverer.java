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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DeclaredTestableMethodDiscoverer extends AbstractTestableMethodDiscoverer {

	@Override
	public Collection<Method> discover(final Class<?> clazz) {
		final Collection<Method> methods = new ArrayList<Method>(Arrays.asList(clazz.getDeclaredMethods()));
		
		super.removePrivateMethods(methods);
		
		return methods;
	}

	@Override
	public Method getAssociatedSetter(final Class<?> clazz, final Method getter) throws NoSuchMethodException {
		return clazz.getDeclaredMethod(super.getSetterEquivalent(getter), getter.getReturnType());
	}

}