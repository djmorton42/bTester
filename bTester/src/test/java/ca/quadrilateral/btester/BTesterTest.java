/*
bTester License

Copyright 2013 Daniel Morton (djmorton@quadrilateral.ca). All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY Daniel Morton ''AS IS'' AND ANY EXPRESS OR IMPLIED
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
or implied, of Daniel Morton.
 */

package ca.quadrilateral.btester;

import org.junit.Test;

import ca.quadrilateral.btester.exception.NoDefaultConstructorException;

public final class BTesterTest
{
    final class NonStaticInner {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class StaticInner {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class NoDefaultConstructor {
        private String a;
        NoDefaultConstructor(final Object o){}
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class PrivateDefaultConstructor {
        private String a;
        private PrivateDefaultConstructor(){}
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class PackagePrivateMethods {
        private String a;
        String getA() { return this.a; }
        void setA(final String a) { this.a = a; }
    }

    final static class ProtectedMethods {
        private String a;
        protected String getA() { return this.a; }
        protected void setA(final String a) { this.a = a; }
    }


    @Test
    public void testSeesPackagePrivateMethods() {
        new BTester(PackagePrivateMethods.class).test();
    }

    @Test
    public void testSeesProtectedMethods() {
        new BTester(ProtectedMethods.class).test();
    }

    @Test
    public void testCanCreateTopLevel() {
        new BTester(BTesterTest.class).test();
    }

    @Test
    public void testCanCreateNonStaticInner() {
        new BTester(NonStaticInner.class).test();
    }

    @Test
    public void testCanCreateStaticInner() {
        new BTester(StaticInner.class).test();
    }

    @Test
    public void testCanCreateNonTopLevel() {
        new BTester(NonTopLevel.class).test();
    }

    @Test(expected = NoDefaultConstructorException.class)
    public void testCanCreateNoDefaultConstructor()
    {
        new BTester(NoDefaultConstructor.class).test();
    }

    @Test(expected = NoDefaultConstructorException.class)
    public void testCanCreatePrivateDefaultConstructor()
    {
        new BTester(PrivateDefaultConstructor.class).test();
    }
}

final class NonTopLevel {
    private String a;
    String getA() { return this.a; }
    void setA(final String a) { this.a = a; }
}
