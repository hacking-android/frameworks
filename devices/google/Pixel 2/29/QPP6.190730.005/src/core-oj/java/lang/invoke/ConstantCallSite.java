/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class ConstantCallSite
extends CallSite {
    private final boolean isFrozen;

    public ConstantCallSite(MethodHandle methodHandle) {
        super(methodHandle);
        this.isFrozen = true;
    }

    protected ConstantCallSite(MethodType methodType, MethodHandle methodHandle) throws Throwable {
        super(methodType, methodHandle);
        this.isFrozen = true;
    }

    @Override
    public final MethodHandle dynamicInvoker() {
        return this.getTarget();
    }

    @Override
    public final MethodHandle getTarget() {
        if (this.isFrozen) {
            return this.target;
        }
        throw new IllegalStateException();
    }

    @Override
    public final void setTarget(MethodHandle methodHandle) {
        throw new UnsupportedOperationException();
    }
}

