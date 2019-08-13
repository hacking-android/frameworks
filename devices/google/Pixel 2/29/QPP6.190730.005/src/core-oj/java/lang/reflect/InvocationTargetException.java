/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

public class InvocationTargetException
extends ReflectiveOperationException {
    private static final long serialVersionUID = 4085088731926701167L;
    private Throwable target;

    protected InvocationTargetException() {
        super((Throwable)null);
    }

    public InvocationTargetException(Throwable throwable) {
        super((Throwable)null);
        this.target = throwable;
    }

    public InvocationTargetException(Throwable throwable, String string) {
        super(string, null);
        this.target = throwable;
    }

    @Override
    public Throwable getCause() {
        return this.target;
    }

    public Throwable getTargetException() {
        return this.target;
    }
}

