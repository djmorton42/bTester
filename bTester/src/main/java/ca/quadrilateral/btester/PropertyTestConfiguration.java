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

import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;
import ca.quadrilateral.btester.tester.Tester;

public class PropertyTestConfiguration {
    private Method getterMethod;
    private Method setterMethod;
    private Tester tester;
    private PropertyGenerator<?> generator;
    private Class<?> runtimePropertyType;

    public PropertyTestConfiguration(final Method getterMethod, final Method setterMethod, final Tester tester, final PropertyGenerator<?> generator) {
        this(getterMethod, setterMethod, tester, generator, null);
    }

    public PropertyTestConfiguration(final Method getterMethod, final Method setterMethod, final Tester tester, final PropertyGenerator<?> generator, final Class<?> runtimePropertyType) {
        this.getterMethod = getterMethod;
        this.setterMethod = setterMethod;
        this.tester = tester;
        this.generator = generator;
        this.runtimePropertyType = runtimePropertyType == null ? getterMethod.getReturnType() : runtimePropertyType;
    }

    public Class<?> getRuntimePropertyType() {
        return this.runtimePropertyType;
    }
    
    public void setRuntimePropertyType(final Class<?> runtimePropertyType) {
        this.runtimePropertyType = runtimePropertyType;
    }

    public Method getGetterMethod() {
        return getterMethod;
    }
    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }
    public Method getSetterMethod() {
        return setterMethod;
    }
    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }
    public Tester getTester() {
        return tester;
    }
    public void setTester(Tester tester) {
        this.tester = tester;
    }
    public PropertyGenerator<?> getGenerator() {
        return generator;
    }
    public void setGenerator(PropertyGenerator<?> generator) {
        this.generator = generator;
    }



}
