/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import java.io.PrintStream;
import java.io.PrintWriter;

public class XPathException
extends Exception {
    private static final long serialVersionUID = -1837080260374986980L;
    private final Throwable cause;

    public XPathException(String string) {
        super(string);
        if (string != null) {
            this.cause = null;
            return;
        }
        throw new NullPointerException("message == null");
    }

    public XPathException(Throwable throwable) {
        String string = throwable == null ? null : throwable.toString();
        super(string);
        this.cause = throwable;
        if (throwable != null) {
            return;
        }
        throw new NullPointerException("cause == null");
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        if (this.getCause() != null) {
            this.getCause().printStackTrace(printStream);
            printStream.println("--------------- linked to ------------------");
        }
        super.printStackTrace(printStream);
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        if (this.getCause() != null) {
            this.getCause().printStackTrace(printWriter);
            printWriter.println("--------------- linked to ------------------");
        }
        super.printStackTrace(printWriter);
    }
}

