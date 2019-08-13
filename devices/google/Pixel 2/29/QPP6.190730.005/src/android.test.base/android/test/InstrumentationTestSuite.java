/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.Instrumentation
 */
package android.test;

import android.app.Instrumentation;
import android.test.InstrumentationTestCase;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

@Deprecated
public class InstrumentationTestSuite
extends TestSuite {
    private final Instrumentation mInstrumentation;

    public InstrumentationTestSuite(Instrumentation instrumentation) {
        this.mInstrumentation = instrumentation;
    }

    public InstrumentationTestSuite(Class class_, Instrumentation instrumentation) {
        super((Class<?>)class_);
        this.mInstrumentation = instrumentation;
    }

    public InstrumentationTestSuite(String string, Instrumentation instrumentation) {
        super(string);
        this.mInstrumentation = instrumentation;
    }

    public void addTestSuite(Class class_) {
        this.addTest(new InstrumentationTestSuite(class_, this.mInstrumentation));
    }

    @Override
    public void runTest(Test test, TestResult testResult) {
        if (test instanceof InstrumentationTestCase) {
            ((InstrumentationTestCase)test).injectInstrumentation(this.mInstrumentation);
        }
        super.runTest(test, testResult);
    }
}

