/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.transform.SourceLocator;

public class TransformerException
extends Exception {
    private static final long serialVersionUID = 975798773772956428L;
    Throwable containedException;
    SourceLocator locator;

    public TransformerException(String string) {
        super(string);
        this.containedException = null;
        this.locator = null;
    }

    public TransformerException(String string, Throwable throwable) {
        if (string == null || string.length() == 0) {
            string = throwable.toString();
        }
        super(string);
        this.containedException = throwable;
        this.locator = null;
    }

    public TransformerException(String string, SourceLocator sourceLocator) {
        super(string);
        this.containedException = null;
        this.locator = sourceLocator;
    }

    public TransformerException(String string, SourceLocator sourceLocator, Throwable throwable) {
        super(string);
        this.containedException = throwable;
        this.locator = sourceLocator;
    }

    public TransformerException(Throwable throwable) {
        super(throwable.toString());
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
            StringBuilder stringBuilder = new StringBuilder();
            String string = this.locator.getSystemId();
            int n = this.locator.getLineNumber();
            int n2 = this.locator.getColumnNumber();
            if (string != null) {
                stringBuilder.append("; SystemID: ");
                stringBuilder.append(string);
            }
            if (n != 0) {
                stringBuilder.append("; Line#: ");
                stringBuilder.append(n);
            }
            if (n2 != 0) {
                stringBuilder.append("; Column#: ");
                stringBuilder.append(n2);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    public SourceLocator getLocator() {
        return this.locator;
    }

    public String getMessageAndLocation() {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = super.getMessage();
        if (object != null) {
            stringBuilder.append((String)object);
        }
        if ((object = this.locator) != null) {
            object = object.getSystemId();
            int n = this.locator.getLineNumber();
            int n2 = this.locator.getColumnNumber();
            if (object != null) {
                stringBuilder.append("; SystemID: ");
                stringBuilder.append((String)object);
            }
            if (n != 0) {
                stringBuilder.append("; Line#: ");
                stringBuilder.append(n);
            }
            if (n2 != 0) {
                stringBuilder.append("; Column#: ");
                stringBuilder.append(n2);
            }
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Throwable initCause(Throwable throwable) {
        synchronized (this) {
            if (this.containedException != null) {
                throwable = new IllegalStateException("Can't overwrite cause");
                throw throwable;
            }
            if (throwable != this) {
                this.containedException = throwable;
                return this;
            }
            throwable = new IllegalArgumentException("Self-causation not permitted");
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
            return;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public void setLocator(SourceLocator sourceLocator) {
        this.locator = sourceLocator;
    }
}

