/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.xml.transform.SourceLocator;
import org.apache.xml.res.XMLMessages;

public class DTMException
extends RuntimeException {
    static final long serialVersionUID = -775576419181334734L;
    Throwable containedException;
    SourceLocator locator;

    public DTMException(String string) {
        super(string);
        this.containedException = null;
        this.locator = null;
    }

    public DTMException(String string, Throwable throwable) {
        if (string == null || string.length() == 0) {
            string = throwable.getMessage();
        }
        super(string);
        this.containedException = throwable;
        this.locator = null;
    }

    public DTMException(String string, SourceLocator sourceLocator) {
        super(string);
        this.containedException = null;
        this.locator = sourceLocator;
    }

    public DTMException(String string, SourceLocator sourceLocator, Throwable throwable) {
        super(string);
        this.containedException = throwable;
        this.locator = sourceLocator;
    }

    public DTMException(Throwable throwable) {
        super(throwable.getMessage());
        this.containedException = throwable;
        this.locator = null;
    }

    @Override
    public Throwable getCause() {
        Throwable throwable;
        block0 : {
            throwable = this.containedException;
            if (throwable != this) break block0;
            throwable = null;
        }
        return throwable;
    }

    public Throwable getException() {
        return this.containedException;
    }

    public String getLocationAsString() {
        if (this.locator != null) {
            StringBuffer stringBuffer = new StringBuffer();
            String string = this.locator.getSystemId();
            int n = this.locator.getLineNumber();
            int n2 = this.locator.getColumnNumber();
            if (string != null) {
                stringBuffer.append("; SystemID: ");
                stringBuffer.append(string);
            }
            if (n != 0) {
                stringBuffer.append("; Line#: ");
                stringBuffer.append(n);
            }
            if (n2 != 0) {
                stringBuffer.append("; Column#: ");
                stringBuffer.append(n2);
            }
            return stringBuffer.toString();
        }
        return null;
    }

    public SourceLocator getLocator() {
        return this.locator;
    }

    public String getMessageAndLocation() {
        StringBuffer stringBuffer = new StringBuffer();
        Object object = super.getMessage();
        if (object != null) {
            stringBuffer.append((String)object);
        }
        if ((object = this.locator) != null) {
            object = object.getSystemId();
            int n = this.locator.getLineNumber();
            int n2 = this.locator.getColumnNumber();
            if (object != null) {
                stringBuffer.append("; SystemID: ");
                stringBuffer.append((String)object);
            }
            if (n != 0) {
                stringBuffer.append("; Line#: ");
                stringBuffer.append(n);
            }
            if (n2 != 0) {
                stringBuffer.append("; Column#: ");
                stringBuffer.append(n2);
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public Throwable initCause(Throwable throwable) {
        synchronized (this) {
            block7 : {
                if (this.containedException != null || throwable == null) break block7;
                throwable = new IllegalStateException(XMLMessages.createXMLMessage("ER_CANNOT_OVERWRITE_CAUSE", null));
                throw throwable;
            }
            if (throwable != this) {
                this.containedException = throwable;
                return this;
            }
            throwable = new IllegalArgumentException(XMLMessages.createXMLMessage("ER_SELF_CAUSATION_NOT_PERMITTED", null));
            throw throwable;
        }
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(new PrintWriter(System.err, true));
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        this.printStackTrace(new PrintWriter(printStream));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void printStackTrace(PrintWriter object) {
        Object object2 = object;
        if (object == null) {
            object2 = new PrintWriter(System.err, true);
        }
        try {
            object = this.getLocationAsString();
            if (object != null) {
                ((PrintWriter)object2).println((String)object);
            }
            super.printStackTrace((PrintWriter)object2);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        int n = 0;
        try {
            Throwable.class.getMethod("getCause", new Class[]{null});
            n = 1;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        if (n != 0) return;
        object = this.getException();
        n = 0;
        while (n < 10) {
            Object object3;
            if (object == null) return;
            ((PrintWriter)object2).println("---------");
            try {
                if (object instanceof DTMException && (object3 = ((DTMException)object).getLocationAsString()) != null) {
                    ((PrintWriter)object2).println((String)object3);
                }
                ((Throwable)object).printStackTrace((PrintWriter)object2);
            }
            catch (Throwable throwable) {
                ((PrintWriter)object2).println("Could not print stack trace...");
            }
            try {
                object3 = object.getClass().getMethod("getException", new Class[]{null});
                if (object3 != null) {
                    if (object == (object3 = (Throwable)((Method)object3).invoke(object, new Object[]{null}))) {
                        return;
                    }
                    object = object3;
                } else {
                    object = null;
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {
                object = null;
            }
            catch (IllegalAccessException illegalAccessException) {
                object = null;
            }
            catch (InvocationTargetException invocationTargetException) {
                object = null;
            }
            ++n;
        }
    }

    public void setLocator(SourceLocator sourceLocator) {
        this.locator = sourceLocator;
    }
}

