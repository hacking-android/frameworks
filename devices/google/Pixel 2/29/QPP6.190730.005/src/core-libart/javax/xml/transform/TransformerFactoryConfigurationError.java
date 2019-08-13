/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.transform;

public class TransformerFactoryConfigurationError
extends Error {
    private Exception exception;

    public TransformerFactoryConfigurationError() {
        this.exception = null;
    }

    public TransformerFactoryConfigurationError(Exception exception) {
        super(exception.toString());
        this.exception = exception;
    }

    public TransformerFactoryConfigurationError(Exception exception, String string) {
        super(string);
        this.exception = exception;
    }

    public TransformerFactoryConfigurationError(String string) {
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

