/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class VolatileCallSite
extends CallSite {
    public VolatileCallSite(MethodHandle methodHandle) {
        super(methodHandle);
    }

    public VolatileCallSite(MethodType methodType) {
        super(methodType);
    }

    @Override
    public final MethodHandle dynamicInvoker() {
        return this.makeDynamicInvoker();
    }

    @Override
    public final MethodHandle getTarget() {
        return this.getTargetVolatile();
    }

    @Override
    public void setTarget(MethodHandle methodHandle) {
        this.checkTargetChange(this.getTargetVolatile(), methodHandle);
        this.setTargetVolatile(methodHandle);
    }
}

