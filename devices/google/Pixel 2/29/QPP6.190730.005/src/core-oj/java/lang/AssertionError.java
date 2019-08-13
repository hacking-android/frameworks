/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class AssertionError
extends Error {
    private static final long serialVersionUID = -5013299493970297370L;

    public AssertionError() {
    }

    public AssertionError(char c) {
        this(String.valueOf(c));
    }

    public AssertionError(double d) {
        this(String.valueOf(d));
    }

    public AssertionError(float f) {
        this(String.valueOf(f));
    }

    public AssertionError(int n) {
        this(String.valueOf(n));
    }

    public AssertionError(long l) {
        this(String.valueOf(l));
    }

    public AssertionError(Object object) {
        this(String.valueOf(object));
        if (object instanceof Throwable) {
            ((Throwable)((Object)this)).initCause((Throwable)object);
        }
    }

    private AssertionError(String string) {
        super(string);
    }

    public AssertionError(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AssertionError(boolean bl) {
        this(String.valueOf(bl));
    }
}

