/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.EmulatedStackFrame
 */
package java.lang.invoke;

import dalvik.system.EmulatedStackFrame;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.Transformers;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;

public abstract class MethodHandle {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int IGET = 9;
    public static final int INVOKE_CALLSITE_TRANSFORM = 6;
    public static final int INVOKE_DIRECT = 2;
    public static final int INVOKE_INTERFACE = 4;
    public static final int INVOKE_STATIC = 3;
    public static final int INVOKE_SUPER = 1;
    public static final int INVOKE_TRANSFORM = 5;
    public static final int INVOKE_VAR_HANDLE = 7;
    public static final int INVOKE_VAR_HANDLE_EXACT = 8;
    public static final int INVOKE_VIRTUAL = 0;
    public static final int IPUT = 10;
    public static final int SGET = 11;
    public static final int SPUT = 12;
    protected final long artFieldOrMethod;
    private MethodHandle cachedSpreadInvoker;
    protected final int handleKind;
    private MethodType nominalType;
    private final MethodType type;

    protected MethodHandle(long l, int n, MethodType methodType) {
        this.artFieldOrMethod = l;
        this.handleKind = n;
        this.type = methodType;
    }

    private MethodType asSpreaderChecks(Class<?> serializable, int n) {
        this.spreadArrayChecks((Class<?>)serializable, n);
        int n2 = this.type().parameterCount();
        if (n2 >= n && n >= 0) {
            boolean bl;
            boolean bl2;
            Class<?> class_ = serializable.getComponentType();
            MethodType methodType = this.type();
            boolean bl3 = true;
            boolean bl4 = false;
            int n3 = n2 - n;
            do {
                bl = bl3;
                bl2 = bl4;
                if (n3 >= n2) break;
                Class<?> class_2 = methodType.parameterType(n3);
                if (class_2 != class_) {
                    bl = false;
                    bl3 = false;
                    if (!MethodType.canConvert(class_, class_2)) {
                        bl2 = true;
                        break;
                    }
                }
                ++n3;
            } while (true);
            if (bl) {
                return methodType;
            }
            serializable = methodType.asSpreaderType((Class<?>)serializable, n);
            if (!bl2) {
                return serializable;
            }
            this.asType((MethodType)serializable);
            throw MethodHandleStatics.newInternalError("should not return", null);
        }
        throw MethodHandleStatics.newIllegalArgumentException("bad spread array length");
    }

    private void spreadArrayChecks(Class<?> class_, int n) {
        Class<?> class_2 = class_.getComponentType();
        if (class_2 != null) {
            if ((n & 127) != n) {
                if ((n & 255) == n) {
                    if (class_2 == Long.TYPE || class_2 == Double.TYPE) {
                        throw MethodHandleStatics.newIllegalArgumentException("array length is not legal for long[] or double[]", n);
                    }
                } else {
                    throw MethodHandleStatics.newIllegalArgumentException("array length is not legal", n);
                }
            }
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("not an array type", class_);
    }

    private void transformInternal(EmulatedStackFrame emulatedStackFrame) throws Throwable {
        this.transform(emulatedStackFrame);
    }

    public MethodHandle asCollector(Class<?> class_, int n) {
        this.asCollectorChecks(class_, n);
        return new Transformers.Collector(this, class_, n);
    }

    boolean asCollectorChecks(Class<?> class_, int n) {
        this.spreadArrayChecks(class_, n);
        n = this.type().parameterCount();
        if (n != 0) {
            Class<?> class_2 = this.type().parameterType(n - 1);
            if (class_2 == class_) {
                return true;
            }
            if (class_2.isAssignableFrom(class_)) {
                return false;
            }
        }
        throw MethodHandleStatics.newIllegalArgumentException("array type not assignable to trailing argument", this, class_);
    }

    public MethodHandle asFixedArity() {
        MethodHandle methodHandle;
        MethodHandle methodHandle2 = methodHandle = this;
        if (methodHandle.isVarargsCollector()) {
            methodHandle2 = ((Transformers.VarargsCollector)methodHandle).asFixedArity();
        }
        return methodHandle2;
    }

    public MethodHandle asSpreader(Class<?> class_, int n) {
        MethodType methodType = this.asSpreaderChecks(class_, n);
        int n2 = methodType.parameterCount();
        return new Transformers.Spreader(this, methodType.dropParameterTypes(n2 - n, n2).appendParameterTypes(class_), n);
    }

    public MethodHandle asType(MethodType methodType) {
        if (methodType == this.type) {
            return this;
        }
        return this.asTypeUncached(methodType);
    }

    MethodHandle asTypeUncached(MethodType methodType) {
        if (this.type.isConvertibleTo(methodType)) {
            MethodHandle methodHandle = this.duplicate();
            methodHandle.nominalType = methodType;
            return methodHandle;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cannot convert ");
        stringBuilder.append(this);
        stringBuilder.append(" to ");
        stringBuilder.append(methodType);
        throw new WrongMethodTypeException(stringBuilder.toString());
    }

    public MethodHandle asVarargsCollector(Class<?> class_) {
        class_.getClass();
        boolean bl = this.asCollectorChecks(class_, 0);
        if (this.isVarargsCollector() && bl) {
            return this;
        }
        return new Transformers.VarargsCollector(this);
    }

    public MethodHandle bindTo(Object object) {
        return new Transformers.BindTo(this, this.type.leadingReferenceParameter().cast(object));
    }

    protected MethodHandle duplicate() {
        try {
            MethodHandle methodHandle = (MethodHandle)this.clone();
            return methodHandle;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError((Object)"Subclass of Transformer is not cloneable");
        }
    }

    public int getHandleKind() {
        return this.handleKind;
    }

    @PolymorphicSignature
    public final native Object invoke(Object ... var1) throws Throwable;

    @PolymorphicSignature
    public final native Object invokeExact(Object ... var1) throws Throwable;

    public Object invokeWithArguments(List<?> list) throws Throwable {
        return this.invokeWithArguments(list.toArray());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object invokeWithArguments(Object ... arrobject) throws Throwable {
        synchronized (this) {
            if (this.cachedSpreadInvoker == null) {
                this.cachedSpreadInvoker = MethodHandles.spreadInvoker(this.type(), 0);
            }
            MethodHandle methodHandle = this.cachedSpreadInvoker;
            return methodHandle.invoke(this, arrobject);
        }
    }

    public boolean isVarargsCollector() {
        return false;
    }

    String standardString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MethodHandle");
        stringBuilder.append(this.type);
        return stringBuilder.toString();
    }

    public String toString() {
        return this.standardString();
    }

    protected void transform(EmulatedStackFrame emulatedStackFrame) throws Throwable {
        throw new AssertionError((Object)"MethodHandle.transform should never be called.");
    }

    public MethodType type() {
        MethodType methodType = this.nominalType;
        if (methodType != null) {
            return methodType;
        }
        return this.type;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD})
    public static @interface PolymorphicSignature {
    }

}

