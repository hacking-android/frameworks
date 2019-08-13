/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class Exception
extends Throwable {
    static final long serialVersionUID = -3387516993124229948L;

    public Exception() {
    }

    public Exception(String string) {
        super(string);
    }

    public Exception(String string, Throwable throwable) {
        super(string, throwable);
    }

    protected Exception(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public Exception(Throwable throwable) {
        super(throwable);
    }
}

