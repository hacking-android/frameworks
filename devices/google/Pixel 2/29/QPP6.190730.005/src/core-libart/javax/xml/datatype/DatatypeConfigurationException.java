/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.datatype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class DatatypeConfigurationException
extends Exception {
    private static final long serialVersionUID = -1699373159027047238L;
    private Throwable causeOnJDK13OrBelow;
    private transient boolean isJDK14OrAbove;

    public DatatypeConfigurationException() {
        this.isJDK14OrAbove = false;
    }

    public DatatypeConfigurationException(String string) {
        super(string);
        this.isJDK14OrAbove = false;
    }

    public DatatypeConfigurationException(String string, Throwable throwable) {
        super(string);
        this.isJDK14OrAbove = false;
        this.initCauseByReflection(throwable);
    }

    public DatatypeConfigurationException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        super(string);
        this.isJDK14OrAbove = false;
        this.initCauseByReflection(throwable);
    }

    private void initCauseByReflection(Throwable throwable) {
        this.causeOnJDK13OrBelow = throwable;
        try {
            this.getClass().getMethod("initCause", Throwable.class).invoke(this, throwable);
            this.isJDK14OrAbove = true;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void printStackTrace0(PrintWriter printWriter) {
        this.causeOnJDK13OrBelow.printStackTrace(printWriter);
        printWriter.println("------------------------------------------");
        super.printStackTrace(printWriter);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        try {
            object = (Throwable)this.getClass().getMethod("getCause", new Class[0]).invoke(this, new Object[0]);
            if (this.causeOnJDK13OrBelow == null) {
                this.causeOnJDK13OrBelow = object;
            } else if (object == null) {
                this.getClass().getMethod("initCause", Throwable.class).invoke(this, this.causeOnJDK13OrBelow);
            }
            this.isJDK14OrAbove = true;
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void printStackTrace() {
        if (!this.isJDK14OrAbove && this.causeOnJDK13OrBelow != null) {
            this.printStackTrace0(new PrintWriter(System.err, true));
        } else {
            super.printStackTrace();
        }
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        if (!this.isJDK14OrAbove && this.causeOnJDK13OrBelow != null) {
            this.printStackTrace0(new PrintWriter(printStream));
        } else {
            super.printStackTrace(printStream);
        }
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        if (!this.isJDK14OrAbove && this.causeOnJDK13OrBelow != null) {
            this.printStackTrace0(printWriter);
        } else {
            super.printStackTrace(printWriter);
        }
    }
}

