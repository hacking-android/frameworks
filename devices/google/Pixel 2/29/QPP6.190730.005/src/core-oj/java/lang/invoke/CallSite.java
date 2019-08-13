/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public abstract class CallSite {
    private static MethodHandle GET_TARGET = null;
    private static final long TARGET_OFFSET;
    MethodHandle target;

    static {
        try {
            TARGET_OFFSET = MethodHandleStatics.UNSAFE.objectFieldOffset(CallSite.class.getDeclaredField("target"));
            return;
        }
        catch (Exception exception) {
            throw new Error(exception);
        }
    }

    CallSite(MethodHandle methodHandle) {
        methodHandle.type();
        this.target = methodHandle;
        this.initializeGetTarget();
    }

    CallSite(MethodType methodType) {
        this.target = MethodHandles.throwException(methodType.returnType(), IllegalStateException.class);
        this.target = MethodHandles.insertArguments(this.target, 0, new IllegalStateException("uninitialized call site"));
        if (methodType.parameterCount() > 0) {
            this.target = MethodHandles.dropArguments(this.target, 0, methodType.ptypes());
        }
        this.initializeGetTarget();
    }

    CallSite(MethodType object, MethodHandle methodHandle) throws Throwable {
        this((MethodType)object);
        object = (MethodHandle)methodHandle.invokeWithArguments((ConstantCallSite)this);
        this.checkTargetChange(this.target, (MethodHandle)object);
        this.target = object;
        this.initializeGetTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initializeGetTarget() {
        synchronized (CallSite.class) {
            Object object = GET_TARGET;
            if (object == null) {
                try {
                    GET_TARGET = MethodHandles.Lookup.IMPL_LOOKUP.findVirtual(CallSite.class, "getTarget", MethodType.methodType(MethodHandle.class));
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    object = new InternalError(reflectiveOperationException);
                    throw object;
                }
            }
            return;
        }
    }

    private static WrongMethodTypeException wrongTargetType(MethodHandle methodHandle, MethodType methodType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(methodHandle));
        stringBuilder.append(" should be of type ");
        stringBuilder.append(methodType);
        return new WrongMethodTypeException(stringBuilder.toString());
    }

    void checkTargetChange(MethodHandle object, MethodHandle methodHandle) {
        object = ((MethodHandle)object).type();
        if (methodHandle.type().equals(object)) {
            return;
        }
        throw CallSite.wrongTargetType(methodHandle, (MethodType)object);
    }

    public abstract MethodHandle dynamicInvoker();

    public abstract MethodHandle getTarget();

    MethodHandle getTargetVolatile() {
        return (MethodHandle)MethodHandleStatics.UNSAFE.getObjectVolatile(this, TARGET_OFFSET);
    }

    MethodHandle makeDynamicInvoker() {
        MethodHandle methodHandle = GET_TARGET.bindTo(this);
        return MethodHandles.foldArguments(MethodHandles.exactInvoker(this.type()), methodHandle);
    }

    public abstract void setTarget(MethodHandle var1);

    void setTargetNormal(MethodHandle methodHandle) {
        this.target = methodHandle;
    }

    void setTargetVolatile(MethodHandle methodHandle) {
        MethodHandleStatics.UNSAFE.putObjectVolatile(this, TARGET_OFFSET, methodHandle);
    }

    public MethodType type() {
        return this.target.type();
    }
}

