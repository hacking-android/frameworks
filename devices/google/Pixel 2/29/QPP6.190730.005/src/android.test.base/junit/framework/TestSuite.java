/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class TestSuite
implements Test {
    private String fName;
    private Vector<Test> fTests = new Vector(10);

    public TestSuite() {
    }

    public TestSuite(Class<?> class_) {
        this.addTestsFromTestCase(class_);
    }

    public TestSuite(Class<? extends TestCase> class_, String string) {
        this((Class<?>)class_);
        this.setName(string);
    }

    public TestSuite(String string) {
        this.setName(string);
    }

    public TestSuite(Class<?> ... arrclass) {
        int n = arrclass.length;
        for (int i = 0; i < n; ++i) {
            this.addTest(this.testCaseForClass(arrclass[i]));
        }
    }

    public TestSuite(Class<? extends TestCase>[] arrclass, String string) {
        this(arrclass);
        this.setName(string);
    }

    private void addTestMethod(Method method, List<String> object, Class<?> class_) {
        String string = method.getName();
        if (object.contains(string)) {
            return;
        }
        if (!this.isPublicTestMethod(method)) {
            if (this.isTestMethod(method)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Test method isn't public: ");
                ((StringBuilder)object).append(method.getName());
                ((StringBuilder)object).append("(");
                ((StringBuilder)object).append(class_.getCanonicalName());
                ((StringBuilder)object).append(")");
                this.addTest(TestSuite.warning(((StringBuilder)object).toString()));
            }
            return;
        }
        object.add((String)string);
        this.addTest(TestSuite.createTest(class_, string));
    }

    private void addTestsFromTestCase(Class<?> class_) {
        this.fName = class_.getName();
        try {
            TestSuite.getTestConstructor(class_);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(class_.getName());
            stringBuilder.append(" has no public constructor TestCase(String name) or TestCase()");
            this.addTest(TestSuite.warning(stringBuilder.toString()));
            return;
        }
        if (!Modifier.isPublic(class_.getModifiers())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Class ");
            stringBuilder.append(class_.getName());
            stringBuilder.append(" is not public");
            this.addTest(TestSuite.warning(stringBuilder.toString()));
            return;
        }
        Serializable serializable = class_;
        ArrayList<String> arrayList = new ArrayList<String>();
        while (Test.class.isAssignableFrom((Class<?>)serializable)) {
            Method[] arrmethod = ((Class)serializable).getDeclaredMethods();
            int n = arrmethod.length;
            for (int i = 0; i < n; ++i) {
                this.addTestMethod(arrmethod[i], arrayList, class_);
            }
            serializable = ((Class)serializable).getSuperclass();
        }
        if (this.fTests.size() == 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No tests found in ");
            ((StringBuilder)serializable).append(class_.getName());
            this.addTest(TestSuite.warning(((StringBuilder)serializable).toString()));
        }
        return;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Test createTest(Class<?> object, String string) {
        Object object2;
        block8 : {
            try {
                object2 = TestSuite.getTestConstructor(object);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Class ");
                stringBuilder.append(((Class)object).getName());
                stringBuilder.append(" has no public constructor TestCase(String name) or TestCase()");
                return TestSuite.warning(stringBuilder.toString());
            }
            if (((Constructor)object2).getParameterTypes().length != 0) break block8;
            object = object2 = ((Constructor)object2).newInstance(new Object[0]);
            if (!(object2 instanceof TestCase)) return (Test)object;
            ((TestCase)object2).setName(string);
            object = object2;
            return (Test)object;
        }
        try {
            object = ((Constructor)object2).newInstance(string);
        }
        catch (IllegalAccessException illegalAccessException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Cannot access test case: ");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(" (");
            ((StringBuilder)object2).append(TestSuite.exceptionToString(illegalAccessException));
            ((StringBuilder)object2).append(")");
            return TestSuite.warning(((StringBuilder)object2).toString());
        }
        catch (InvocationTargetException invocationTargetException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Exception in constructor: ");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(" (");
            ((StringBuilder)object2).append(TestSuite.exceptionToString(invocationTargetException.getTargetException()));
            ((StringBuilder)object2).append(")");
            return TestSuite.warning(((StringBuilder)object2).toString());
        }
        catch (InstantiationException instantiationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot instantiate test case: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" (");
            ((StringBuilder)object).append(TestSuite.exceptionToString(instantiationException));
            ((StringBuilder)object).append(")");
            return TestSuite.warning(((StringBuilder)object).toString());
        }
        return (Test)object;
    }

    private static String exceptionToString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static Constructor<?> getTestConstructor(Class<?> class_) throws NoSuchMethodException {
        try {
            Constructor<?> constructor = class_.getConstructor(String.class);
            return constructor;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return class_.getConstructor(new Class[0]);
        }
    }

    private boolean isPublicTestMethod(Method method) {
        boolean bl = this.isTestMethod(method) && Modifier.isPublic(method.getModifiers());
        return bl;
    }

    private boolean isTestMethod(Method method) {
        boolean bl = method.getParameterTypes().length == 0 && method.getName().startsWith("test") && method.getReturnType().equals(Void.TYPE);
        return bl;
    }

    private Test testCaseForClass(Class<?> class_) {
        if (TestCase.class.isAssignableFrom(class_)) {
            return new TestSuite((Class<?>)class_.asSubclass(TestCase.class));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getCanonicalName());
        stringBuilder.append(" does not extend TestCase");
        return TestSuite.warning(stringBuilder.toString());
    }

    public static Test warning(final String string) {
        return new TestCase("warning"){

            @Override
            protected void runTest() {
                1.fail(string);
            }
        };
    }

    public void addTest(Test test) {
        this.fTests.add(test);
    }

    public void addTestSuite(Class<? extends TestCase> class_) {
        this.addTest(new TestSuite((Class<?>)class_));
    }

    @Override
    public int countTestCases() {
        int n = 0;
        Iterator<Test> iterator = this.fTests.iterator();
        while (iterator.hasNext()) {
            n += iterator.next().countTestCases();
        }
        return n;
    }

    public String getName() {
        return this.fName;
    }

    @Override
    public void run(TestResult testResult) {
        for (Test test : this.fTests) {
            if (testResult.shouldStop()) break;
            this.runTest(test, testResult);
        }
    }

    public void runTest(Test test, TestResult testResult) {
        test.run(testResult);
    }

    public void setName(String string) {
        this.fName = string;
    }

    public Test testAt(int n) {
        return this.fTests.get(n);
    }

    public int testCount() {
        return this.fTests.size();
    }

    public Enumeration<Test> tests() {
        return this.fTests.elements();
    }

    public String toString() {
        if (this.getName() != null) {
            return this.getName();
        }
        return super.toString();
    }

}

