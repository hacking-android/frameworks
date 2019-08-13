/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import junit.framework.AssertionFailedError;
import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestFailure;
import junit.framework.TestListener;

public class TestResult {
    protected Vector<TestFailure> fErrors = new Vector();
    protected Vector<TestFailure> fFailures = new Vector();
    protected Vector<TestListener> fListeners = new Vector();
    protected int fRunTests = 0;
    private boolean fStop = false;

    private List<TestListener> cloneListeners() {
        synchronized (this) {
            ArrayList<TestListener> arrayList = new ArrayList<TestListener>();
            arrayList.addAll(this.fListeners);
            return arrayList;
        }
    }

    public void addError(Test test, Throwable throwable) {
        synchronized (this) {
            Object object = this.fErrors;
            TestFailure testFailure = new TestFailure(test, throwable);
            ((Vector)object).add((TestFailure)testFailure);
            object = this.cloneListeners().iterator();
            while (object.hasNext()) {
                ((TestListener)object.next()).addError(test, throwable);
            }
            return;
        }
    }

    public void addFailure(Test test, AssertionFailedError assertionFailedError) {
        synchronized (this) {
            Object object = this.fFailures;
            TestFailure testFailure = new TestFailure(test, (Throwable)((Object)assertionFailedError));
            ((Vector)object).add((TestFailure)testFailure);
            object = this.cloneListeners().iterator();
            while (object.hasNext()) {
                ((TestListener)object.next()).addFailure(test, assertionFailedError);
            }
            return;
        }
    }

    public void addListener(TestListener testListener) {
        synchronized (this) {
            this.fListeners.add(testListener);
            return;
        }
    }

    public void endTest(Test test) {
        Iterator<TestListener> iterator = this.cloneListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().endTest(test);
        }
    }

    public int errorCount() {
        synchronized (this) {
            int n = this.fErrors.size();
            return n;
        }
    }

    public Enumeration<TestFailure> errors() {
        synchronized (this) {
            Enumeration<TestFailure> enumeration = Collections.enumeration(this.fErrors);
            return enumeration;
        }
    }

    public int failureCount() {
        synchronized (this) {
            int n = this.fFailures.size();
            return n;
        }
    }

    public Enumeration<TestFailure> failures() {
        synchronized (this) {
            Enumeration<TestFailure> enumeration = Collections.enumeration(this.fFailures);
            return enumeration;
        }
    }

    public void removeListener(TestListener testListener) {
        synchronized (this) {
            this.fListeners.remove(testListener);
            return;
        }
    }

    protected void run(final TestCase testCase) {
        this.startTest(testCase);
        this.runProtected(testCase, new Protectable(){

            @Override
            public void protect() throws Throwable {
                testCase.runBare();
            }
        });
        this.endTest(testCase);
    }

    public int runCount() {
        synchronized (this) {
            int n = this.fRunTests;
            return n;
        }
    }

    public void runProtected(Test test, Protectable protectable) {
        try {
            protectable.protect();
        }
        catch (Throwable throwable) {
            this.addError(test, throwable);
        }
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
        catch (AssertionFailedError assertionFailedError) {
            this.addFailure(test, assertionFailedError);
        }
    }

    public boolean shouldStop() {
        synchronized (this) {
            boolean bl = this.fStop;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startTest(Test test) {
        int n = test.countTestCases();
        synchronized (this) {
            this.fRunTests += n;
        }
        Iterator<TestListener> iterator = this.cloneListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().startTest(test);
        }
        return;
    }

    public void stop() {
        synchronized (this) {
            this.fStop = true;
            return;
        }
    }

    public boolean wasSuccessful() {
        synchronized (this) {
            int n;
            boolean bl = this.failureCount() == 0 && (n = this.errorCount()) == 0;
            return bl;
        }
    }

}

