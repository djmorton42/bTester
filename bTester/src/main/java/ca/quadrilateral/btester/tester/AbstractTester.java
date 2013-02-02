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

package ca.quadrilateral.btester.tester;

import java.lang.reflect.Method;

import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;

/**
 * An abstract implementation that performs pre- and post-test activities. Subclasses must implement
 * {@link #executeTestImpl(Object, Method, Method, PropertyGenerator)}, which is guaranteed only to be executed when
 * the methods are guaranteed to be accessible.
 * <p>
 * Because the JavaBeans specification allows for package-private members, some methods must be flagged as accessible.
 * While the method could, in theory, be set accessible globally, this would seem to exhibit side effects. As such,
 * this implementation toggles the accessibility of both getter and setter methods while executing the test.
 */
public abstract class AbstractTester implements Tester {
    @Override
    public Object generateProperty(final PropertyGenerator<?> propertyGenerator) {
        return propertyGenerator.generateProperty();
    }

    @Override
	public final void executeTest(final Object classUnderTest, final Method setterMethod, final Method getterMethod, final PropertyGenerator<?> propertyGenerator) {
        try {
            setterMethod.setAccessible(true);
            getterMethod.setAccessible(true);

            this.executeTestImpl(classUnderTest, setterMethod, getterMethod, propertyGenerator);
        } finally {
            getterMethod.setAccessible(false);
            setterMethod.setAccessible(false);
        }
    }

	protected abstract void executeTestImpl(final Object classUnderTest, final Method setterMethod, final Method getterMethod, final PropertyGenerator<?> propertyGenerator);
}
