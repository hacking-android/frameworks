/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class MutableCallSite
extends CallSite {
    public MutableCallSite(MethodHandle methodHandle) {
        super(methodHandle);
    }

    public MutableCallSite(MethodType methodType) {
        super(methodType);
    }

    @Override
    public final MethodHandle dynamicInvoker() {
        return this.makeDynamicInvoker();
    }

    @Override
    public final MethodHandle getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(MethodHandle methodHandle) {
        this.checkTargetChange(this.target, methodHandle);
        this.setTargetNormal(methodHandle);
    }
}

