/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

public abstract class TestCase
extends Assert
implements Test {
    private String fName;

    public TestCase() {
        this.fName = null;
    }

    public TestCase(String string) {
        this.fName = string;
    }

    @Override
    public int countTestCases() {
        return 1;
    }

    protected TestResult createResult() {
        return new TestResult();
    }

    public String getName() {
        return this.fName;
    }

    public TestResult run() {
        TestResult testResult = this.createResult();
        this.run(testResult);
        return testResult;
    }

    @Override
    public void run(TestResult testResult) {
        testResult.run(this);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void runBare() throws Throwable {
        Throwable throwable;
        block7 : {
            throwable = null;
            Throwable throwable2 = null;
            this.setUp();
            this.runTest();
            try {
                this.tearDown();
                throwable = throwable2;
            }
            catch (Throwable throwable3) {
                if (!false) {
                    throwable = throwable3;
                }
                break block7;
            }
            catch (Throwable throwable4) {
                try {
                    this.tearDown();
                }
                catch (Throwable throwable5) {}
            }
        }
        if (throwable != null) throw throwable;
    }

    protected void runTest() throws Throwable {
        Object object;
        TestCase.assertNotNull("TestCase.fName cannot be null", this.fName);
        Object object2 = null;
        try {
            object2 = object = this.getClass().getMethod(this.fName, null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Method \"");
            ((StringBuilder)object).append(this.fName);
            ((StringBuilder)object).append("\" not found");
            TestCase.fail(((StringBuilder)object).toString());
        }
        if (!Modifier.isPublic(((Method)object2).getModifiers())) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Method \"");
            ((StringBuilder)object).append(this.fName);
            ((StringBuilder)object).append("\" should be public");
            TestCase.fail(((StringBuilder)object).toString());
        }
        try {
            ((Method)object2).invoke(this, new Object[0]);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.fillInStackTrace();
            throw illegalAccessException;
        }
        catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.fillInStackTrace();
            throw invocationTargetException.getTargetException();
        }
    }

    public void setName(String string) {
        this.fName = string;
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append("(");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

