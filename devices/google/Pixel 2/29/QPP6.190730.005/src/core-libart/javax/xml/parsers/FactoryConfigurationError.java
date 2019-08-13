/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.parsers;

public class FactoryConfigurationError
extends Error {
    private Exception exception;

    public FactoryConfigurationError() {
        this.exception = null;
    }

    public FactoryConfigurationError(Exception exception) {
        super(exception.toString());
        this.exception = exception;
    }

    public FactoryConfigurationError(Exception exception, String string) {
        super(string);
        this.exception = exception;
    }

    public FactoryConfigurationError(String string) {
        super(string);
        this.exception = null;
    }

    public Exception getException() {
        return this.exception;
    }

    @Override
    public String getMessage() {
        Exception exception;
        String string = super.getMessage();
        if (string == null && (exception = this.exception) != null) {
            return exception.getMessage();
        }
        return string;
    }
}

