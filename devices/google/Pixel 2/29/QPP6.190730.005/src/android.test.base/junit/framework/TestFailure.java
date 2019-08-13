/*
 * Decompiled with CFR 0.145.
 */
package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class TestFailure {
    protected Test fFailedTest;
    protected Throwable fThrownException;

    public TestFailure(Test test, Throwable throwable) {
        this.fFailedTest = test;
        this.fThrownException = throwable;
    }

    public String exceptionMessage() {
        return this.thrownException().getMessage();
    }

    public Test failedTest() {
        return this.fFailedTest;
    }

    public boolean isFailure() {
        return this.thrownException() instanceof AssertionFailedError;
    }

    public Throwable thrownException() {
        return this.fThrownException;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fFailedTest);
        stringBuilder.append(": ");
        stringBuilder.append(this.fThrownException.getMessage());
        stringBuffer.append(stringBuilder.toString());
        return stringBuffer.toString();
    }

    public String trace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        this.thrownException().printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
}

