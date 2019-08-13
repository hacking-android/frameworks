/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

public abstract class VirtualMachineError
extends Error {
    private static final long serialVersionUID = 4161983926571568670L;

    public VirtualMachineError() {
    }

    public VirtualMachineError(String string) {
        super(string);
    }

    public VirtualMachineError(String string, Throwable throwable) {
        super(string, throwable);
    }

    public VirtualMachineError(Throwable throwable) {
        super(throwable);
    }
}

