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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.btester.exception.NoDefaultConstructorException;
import ca.quadrilateral.btester.exception.TestException;
import ca.quadrilateral.btester.propertygenerator.PropertyGenerator;
import ca.quadrilateral.btester.propertygenerator.PropertyGeneratorFactory;
import ca.quadrilateral.btester.tester.Tester;
import ca.quadrilateral.btester.tester.TesterFactory;

public class BTester {	
    private static final Logger logger = LoggerFactory.getLogger(BTester.class);

    private final List<GetterSetterPair> getterSetterPairs = new ArrayList<GetterSetterPair>();
    private final Class<?> clazzUnderTest;

    private final Map<String, GetterSetterPair> propertyGetterSetterMap = new HashMap<String, GetterSetterPair>();
    private final Map<String, PropertyTestConfiguration> propertyConfigMap = new HashMap<String, PropertyTestConfiguration>();
    private final Set<String> ignoreList = new HashSet<String>();	

    public BTester(final Class<?> clazz) {
        this.clazzUnderTest = clazz;

        final Method[] methods = clazz.getDeclaredMethods();

        for(Method method : methods) {
            // JavaBean spec doesn't consider privates as bean members.
            if(Modifier.isPrivate(method.getModifiers())){
                continue;
            }

            final String name = method.getName();
            if (name.startsWith("is") || name.startsWith("get")) {
                try { 
                    final Method setter = clazz.getDeclaredMethod(getSetterEquivalent(method), method.getReturnType());
                    final GetterSetterPair getterSetterPair = new GetterSetterPair(method, setter);
                    getterSetterPairs.add(new GetterSetterPair(method, setter));
                    propertyGetterSetterMap.put(getPropertyName(method), getterSetterPair);
                } catch (NoSuchMethodException nsme) {
                    logger.info("Method {} does not have an associated setter... Ignoring...", method);
                }
            }
        }
    }

    public BTester override(final String property, final PropertyGenerator<?> propertyGenerator) {
        final GetterSetterPair getterSetterPair = propertyGetterSetterMap.get(property);

        if (getterSetterPair == null) {
            throw new IllegalArgumentException("Property " + property + " does not exist or does not have accessor and mutator methods on class type " + clazzUnderTest);
        }

        propertyConfigMap.put(property, new PropertyTestConfiguration(getterSetterPair.getGetter(), getterSetterPair.getSetter(), null, propertyGenerator));

        return this;
    }

    public BTester override(final String property, final Tester tester) {
        final GetterSetterPair getterSetterPair = propertyGetterSetterMap.get(property);

        if (getterSetterPair == null) {
            throw new IllegalArgumentException("Property " + property + " does not exist or does not have accessor and mutator methods on class type " + clazzUnderTest);
        }

        propertyConfigMap.put(property, new PropertyTestConfiguration(getterSetterPair.getGetter(), getterSetterPair.getSetter(), tester, null));

        return this;
    }

    public BTester override(final String property, final PropertyGenerator<?> propertyGenerator, final Tester tester) {
        final GetterSetterPair getterSetterPair = propertyGetterSetterMap.get(property);

        if (getterSetterPair == null) {
            throw new IllegalArgumentException("Property " + property + " does not exist or does not have accessor and mutator methods on class type " + clazzUnderTest);
        }

        propertyConfigMap.put(property, new PropertyTestConfiguration(getterSetterPair.getGetter(), getterSetterPair.getSetter(), tester, propertyGenerator));

        return this;
    }

    public BTester override(final String property, final Class<?> runtimeType) {
        final GetterSetterPair getterSetterPair = propertyGetterSetterMap.get(property);

        if (getterSetterPair == null) {
            throw new IllegalArgumentException("Property " + property + " does not exist or does not have accessor and mutator methods on class type " + clazzUnderTest);
        }

        propertyConfigMap.put(property, new PropertyTestConfiguration(getterSetterPair.getGetter(), getterSetterPair.getSetter(), null, null, runtimeType));

        return this;	
    }

    public BTester ignore(final String property) {
        final GetterSetterPair getterSetterPair = propertyGetterSetterMap.get(property);

        if (getterSetterPair == null) {
            throw new IllegalArgumentException("Property " + property + " does not exist or does not have accessor and mutator methods on class type " + clazzUnderTest);
        }

        ignoreList.add(property);

        return this;
    }

    private String getSetterEquivalent(final Method getterMethod) {
        if (getterMethod.getName().startsWith("is")) {
            return "set" + getterMethod.getName().substring(2);
        } else {
            return "set" + getterMethod.getName().substring(3);
        }
    }

    private String getPropertyName(final Method getterMethod) {
        final String getterMethodName = getterMethod.getName();
        if (getterMethodName.startsWith("is")) {
            return getterMethodName.substring(2,3).toLowerCase() + getterMethodName.substring(3);
        } else {
            return getterMethodName.substring(3,4).toLowerCase() + getterMethodName.substring(4);
        }
    }

    /**
     *
     * @throws NoDefaultConstructorException If no default constructor can be found for the class under interrogation.
     * @throws TestException If any other exception is encountered during test execution.
     */
    public void test() {
        logger.info("Executing bean test on class " + this.clazzUnderTest);

        final PropertyGeneratorFactory propertyGeneratorFactory = new PropertyGeneratorFactory();
        final TesterFactory testerFactory = new TesterFactory();
        final Object classToTest = newInstance(this.clazzUnderTest);

        for(GetterSetterPair getterSetterPair : getterSetterPairs) {
            final String propertyToTest = getterSetterPair.getProperty();
            if (!ignoreList.contains(propertyToTest)) {
                PropertyGenerator<?> propertyGenerator = getPropertyGenerator(propertyGeneratorFactory, getterSetterPair);
                Tester tester = getTester(testerFactory, getterSetterPair);
                tester.executeTest(classToTest, getterSetterPair.getSetter(), getterSetterPair.getGetter(), propertyGenerator);
            }
        }

        logger.info("Completed bean test on class " + this.clazzUnderTest);
    }

    private <T> T newInstance(final Class<T> clazz) throws NoDefaultConstructorException, TestException {
        // Check for a non-static inner class.
        final Class<?> outerClazz = clazz.getDeclaringClass();
        if(outerClazz != null && !Modifier.isStatic(clazz.getModifiers())){
            final Object outer = this.tryNewInstance(outerClazz);
            return this.tryNewInstance(clazz, outer);
        }

        // Check for a local member class (pretty useless).
        final Class<?> enclosingClazz = clazz.getEnclosingClass();
        if(enclosingClazz != null && clazz.isLocalClass()){
            final Object enclosing = this.tryNewInstance(enclosingClazz);
            return this.tryNewInstance(clazz, enclosing);
        }

        return this.tryNewInstance(clazz);
    }

    private <T> T tryNewInstance(final Class<T> clazz, final Object ... args) throws NoDefaultConstructorException, TestException  {
        try{
            Class<?>[] argTypes = null; 
            if(args != null){
                argTypes = new Class<?>[args.length];
                for(int i = 0; i < args.length; i++){
                    argTypes[i] = args[i].getClass();
                }
            }
            return clazz.getDeclaredConstructor(argTypes).newInstance(args);
        } catch(final NoSuchMethodException ex) {
            // Not there.
            throw new NoDefaultConstructorException(clazz, ex);
        } catch(final IllegalAccessException ex) {
            // Probably private.
            throw new NoDefaultConstructorException(clazz, ex);

        } catch(final InstantiationException ex) {
            throw new TestException("Could not invoke defalut constructor for " + clazz, ex);
        } catch(final InvocationTargetException ex) {
            throw new TestException("Generic exception while invoking default constructor for " + clazz + ". See nested exception.", ex);
        }
    }

    private Tester getTester(final TesterFactory testerFactory, final GetterSetterPair getterSetterPair) {
        final String property = getterSetterPair.getProperty();
        final PropertyTestConfiguration propertyTestConfiguration = propertyConfigMap.get(property);
        if (propertyTestConfiguration != null) {
            final Tester tester = propertyTestConfiguration.getTester();
            if (tester != null) {
                return tester;
            }
        }
        return testerFactory.getTester(getterSetterPair.getReturnType());		
    }

    private PropertyGenerator<?> getPropertyGenerator(final PropertyGeneratorFactory propertyGeneratorFactory, GetterSetterPair getterSetterPair) {
        final String property = getterSetterPair.getProperty();
        final PropertyTestConfiguration propertyTestConfiguration = propertyConfigMap.get(property);
        if (propertyTestConfiguration != null) {
            final PropertyGenerator<?> generator = propertyTestConfiguration.getGenerator();
            if (generator != null) {
                return generator;
            }
        }
        return propertyGeneratorFactory.getPropertyGenerator(getRuntimeTypeForPropertyGenerator(getterSetterPair, propertyTestConfiguration));
    }
    
    private Class<?> getRuntimeTypeForPropertyGenerator(final GetterSetterPair getterSetterPair, final PropertyTestConfiguration propertyTestConfiguration) {
        if (propertyTestConfiguration != null) {
            return propertyTestConfiguration.getRuntimePropertyType();
        } else {
            return getterSetterPair.getGetter().getReturnType();
        }
    }
}
