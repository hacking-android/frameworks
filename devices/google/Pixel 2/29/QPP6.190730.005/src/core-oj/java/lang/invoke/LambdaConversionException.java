/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

public class LambdaConversionException
extends Exception {
    private static final long serialVersionUID = 300L;

    public LambdaConversionException() {
    }

    public LambdaConversionException(String string) {
        super(string);
    }

    public LambdaConversionException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public LambdaConversionException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public LambdaConversionException(Throwable throwable) {
        super(throwable);
    }
}

