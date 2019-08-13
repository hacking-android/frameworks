/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public class InternalError
extends VirtualMachineError {
    private static final long serialVersionUID = -9062593416125562365L;

    public InternalError() {
    }

    public InternalError(String string) {
        super(string);
    }

    public InternalError(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InternalError(Throwable throwable) {
        super(throwable);
    }
}

