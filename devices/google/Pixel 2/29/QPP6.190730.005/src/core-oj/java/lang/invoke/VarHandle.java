/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import sun.misc.Unsafe;

public abstract class VarHandle {
    private static final int ALL_MODES_BIT_MASK;
    private static final int ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
    private static final int BITWISE_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
    private static final int NUMERIC_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
    private static final int READ_ACCESS_MODES_BIT_MASK;
    private static final Unsafe UNSAFE;
    private static final int WRITE_ACCESS_MODES_BIT_MASK;
    private final int accessModesBitMask;
    private final Class<?> coordinateType0;
    private final Class<?> coordinateType1;
    private final Class<?> varType;

    static {
        UNSAFE = Unsafe.getUnsafe();
        if (AccessMode.values().length <= 32) {
            READ_ACCESS_MODES_BIT_MASK = VarHandle.accessTypesToBitMask(EnumSet.of(AccessType.GET));
            WRITE_ACCESS_MODES_BIT_MASK = VarHandle.accessTypesToBitMask(EnumSet.of(AccessType.SET));
            ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK = VarHandle.accessTypesToBitMask(EnumSet.of(AccessType.COMPARE_AND_EXCHANGE, AccessType.COMPARE_AND_SWAP, AccessType.GET_AND_UPDATE));
            NUMERIC_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK = VarHandle.accessTypesToBitMask(EnumSet.of(AccessType.GET_AND_UPDATE_NUMERIC));
            BITWISE_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK = VarHandle.accessTypesToBitMask(EnumSet.of(AccessType.GET_AND_UPDATE_BITWISE));
            ALL_MODES_BIT_MASK = READ_ACCESS_MODES_BIT_MASK | WRITE_ACCESS_MODES_BIT_MASK | ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK | NUMERIC_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK | BITWISE_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
            return;
        }
        throw new InternalError("accessModes overflow");
    }

    VarHandle(Class<?> serializable, Class<?> class_, boolean bl, Class<?> class_2, Class<?> class_3) {
        this.varType = Objects.requireNonNull(serializable);
        this.coordinateType0 = Objects.requireNonNull(class_2);
        this.coordinateType1 = Objects.requireNonNull(class_3);
        Objects.requireNonNull(class_);
        class_2 = class_.getComponentType();
        if (class_2 != serializable && class_2 != Byte.TYPE) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unsupported backingArrayType: ");
            ((StringBuilder)serializable).append(class_);
            throw new InternalError(((StringBuilder)serializable).toString());
        }
        this.accessModesBitMask = class_.getComponentType() == serializable ? VarHandle.alignedAccessModesBitMask(serializable, bl) : VarHandle.unalignedAccessModesBitMask(serializable);
    }

    VarHandle(Class<?> class_, boolean bl) {
        this.varType = Objects.requireNonNull(class_);
        this.coordinateType0 = null;
        this.coordinateType1 = null;
        this.accessModesBitMask = VarHandle.alignedAccessModesBitMask(class_, bl);
    }

    VarHandle(Class<?> class_, boolean bl, Class<?> class_2) {
        this.varType = Objects.requireNonNull(class_);
        this.coordinateType0 = Objects.requireNonNull(class_2);
        this.coordinateType1 = null;
        this.accessModesBitMask = VarHandle.alignedAccessModesBitMask(class_, bl);
    }

    static int accessTypesToBitMask(EnumSet<AccessType> enumSet) {
        int n = 0;
        for (AccessMode accessMode : AccessMode.values()) {
            int n2 = n;
            if (enumSet.contains((Object)accessMode.at)) {
                n2 = n | 1 << accessMode.ordinal();
            }
            n = n2;
        }
        return n;
    }

    public static void acquireFence() {
        UNSAFE.loadFence();
    }

    static int alignedAccessModesBitMask(Class<?> class_, boolean bl) {
        int n;
        int n2 = n = ALL_MODES_BIT_MASK;
        if (bl) {
            n2 = n & READ_ACCESS_MODES_BIT_MASK;
        }
        n = n2;
        if (class_ != Byte.TYPE) {
            n = n2;
            if (class_ != Short.TYPE) {
                n = n2;
                if (class_ != Character.TYPE) {
                    n = n2;
                    if (class_ != Integer.TYPE) {
                        n = n2;
                        if (class_ != Long.TYPE) {
                            n = n2;
                            if (class_ != Float.TYPE) {
                                n = n2;
                                if (class_ != Double.TYPE) {
                                    n = n2 & NUMERIC_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
                                }
                            }
                        }
                    }
                }
            }
        }
        n2 = n;
        if (class_ != Boolean.TYPE) {
            n2 = n;
            if (class_ != Byte.TYPE) {
                n2 = n;
                if (class_ != Short.TYPE) {
                    n2 = n;
                    if (class_ != Character.TYPE) {
                        n2 = n;
                        if (class_ != Integer.TYPE) {
                            n2 = n;
                            if (class_ != Long.TYPE) {
                                n2 = n & BITWISE_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
                            }
                        }
                    }
                }
            }
        }
        return n2;
    }

    public static void fullFence() {
        UNSAFE.fullFence();
    }

    public static void loadLoadFence() {
        UNSAFE.loadFence();
    }

    public static void releaseFence() {
        UNSAFE.storeFence();
    }

    public static void storeStoreFence() {
        UNSAFE.storeFence();
    }

    static int unalignedAccessModesBitMask(Class<?> class_) {
        int n;
        block11 : {
            int n2;
            block10 : {
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                n2 = READ_ACCESS_MODES_BIT_MASK | WRITE_ACCESS_MODES_BIT_MASK;
                                if (class_ == Integer.TYPE || class_ == Long.TYPE || class_ == Float.TYPE) break block6;
                                n = n2;
                                if (class_ != Double.TYPE) break block7;
                            }
                            n = n2 | ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
                        }
                        if (class_ == Integer.TYPE) break block8;
                        n2 = n;
                        if (class_ != Long.TYPE) break block9;
                    }
                    n2 = n | NUMERIC_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
                }
                if (class_ == Integer.TYPE) break block10;
                n = n2;
                if (class_ != Long.TYPE) break block11;
            }
            n = n2 | BITWISE_ATOMIC_UPDATE_ACCESS_MODES_BIT_MASK;
        }
        return n;
    }

    public final MethodType accessModeType(AccessMode accessMode) {
        if (this.coordinateType1 == null) {
            return accessMode.at.accessModeType(this.coordinateType0, this.varType, new Class[0]);
        }
        return accessMode.at.accessModeType(this.coordinateType0, this.varType, this.coordinateType1);
    }

    @MethodHandle.PolymorphicSignature
    public final native Object compareAndExchange(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object compareAndExchangeAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object compareAndExchangeRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native boolean compareAndSet(Object ... var1);

    public final List<Class<?>> coordinateTypes() {
        Class<?> class_ = this.coordinateType0;
        if (class_ == null) {
            return Collections.EMPTY_LIST;
        }
        Class<?> class_2 = this.coordinateType1;
        if (class_2 == null) {
            return Collections.singletonList(class_);
        }
        return Collections.unmodifiableList(Arrays.asList(class_, class_2));
    }

    @MethodHandle.PolymorphicSignature
    public final native Object get(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndAdd(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndAddAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndAddRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseAnd(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseAndAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseAndRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseOr(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseOrAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseOrRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseXor(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseXorAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndBitwiseXorRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndSet(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndSetAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getAndSetRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getOpaque(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native Object getVolatile(Object ... var1);

    public final boolean isAccessModeSupported(AccessMode accessMode) {
        int n = accessMode.ordinal();
        boolean bl = true;
        if ((this.accessModesBitMask & (n = 1 << n)) != n) {
            bl = false;
        }
        return bl;
    }

    @MethodHandle.PolymorphicSignature
    public final native void set(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native void setOpaque(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native void setRelease(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native void setVolatile(Object ... var1);

    public final MethodHandle toMethodHandle(AccessMode accessMode) {
        return MethodHandles.varHandleExactInvoker(accessMode, this.accessModeType(accessMode)).bindTo(this);
    }

    public final Class<?> varType() {
        return this.varType;
    }

    @MethodHandle.PolymorphicSignature
    public final native boolean weakCompareAndSet(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native boolean weakCompareAndSetAcquire(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native boolean weakCompareAndSetPlain(Object ... var1);

    @MethodHandle.PolymorphicSignature
    public final native boolean weakCompareAndSetRelease(Object ... var1);

    public static final class AccessMode
    extends Enum<AccessMode> {
        private static final /* synthetic */ AccessMode[] $VALUES;
        public static final /* enum */ AccessMode COMPARE_AND_EXCHANGE;
        public static final /* enum */ AccessMode COMPARE_AND_EXCHANGE_ACQUIRE;
        public static final /* enum */ AccessMode COMPARE_AND_EXCHANGE_RELEASE;
        public static final /* enum */ AccessMode COMPARE_AND_SET;
        public static final /* enum */ AccessMode GET;
        public static final /* enum */ AccessMode GET_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_ADD;
        public static final /* enum */ AccessMode GET_AND_ADD_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_ADD_RELEASE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_AND;
        public static final /* enum */ AccessMode GET_AND_BITWISE_AND_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_AND_RELEASE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_OR;
        public static final /* enum */ AccessMode GET_AND_BITWISE_OR_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_OR_RELEASE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_XOR;
        public static final /* enum */ AccessMode GET_AND_BITWISE_XOR_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_BITWISE_XOR_RELEASE;
        public static final /* enum */ AccessMode GET_AND_SET;
        public static final /* enum */ AccessMode GET_AND_SET_ACQUIRE;
        public static final /* enum */ AccessMode GET_AND_SET_RELEASE;
        public static final /* enum */ AccessMode GET_OPAQUE;
        public static final /* enum */ AccessMode GET_VOLATILE;
        public static final /* enum */ AccessMode SET;
        public static final /* enum */ AccessMode SET_OPAQUE;
        public static final /* enum */ AccessMode SET_RELEASE;
        public static final /* enum */ AccessMode SET_VOLATILE;
        public static final /* enum */ AccessMode WEAK_COMPARE_AND_SET;
        public static final /* enum */ AccessMode WEAK_COMPARE_AND_SET_ACQUIRE;
        public static final /* enum */ AccessMode WEAK_COMPARE_AND_SET_PLAIN;
        public static final /* enum */ AccessMode WEAK_COMPARE_AND_SET_RELEASE;
        static final Map<String, AccessMode> methodNameToAccessMode;
        final AccessType at;
        final String methodName;

        static {
            AccessMode[] arraccessMode = AccessType.GET;
            GET = new AccessMode("get", (AccessType)arraccessMode);
            SET = new AccessMode("set", AccessType.SET);
            GET_VOLATILE = new AccessMode("getVolatile", AccessType.GET);
            SET_VOLATILE = new AccessMode("setVolatile", AccessType.SET);
            GET_ACQUIRE = new AccessMode("getAcquire", AccessType.GET);
            SET_RELEASE = new AccessMode("setRelease", AccessType.SET);
            GET_OPAQUE = new AccessMode("getOpaque", AccessType.GET);
            SET_OPAQUE = new AccessMode("setOpaque", AccessType.SET);
            COMPARE_AND_SET = new AccessMode("compareAndSet", AccessType.COMPARE_AND_SWAP);
            COMPARE_AND_EXCHANGE = new AccessMode("compareAndExchange", AccessType.COMPARE_AND_EXCHANGE);
            COMPARE_AND_EXCHANGE_ACQUIRE = new AccessMode("compareAndExchangeAcquire", AccessType.COMPARE_AND_EXCHANGE);
            COMPARE_AND_EXCHANGE_RELEASE = new AccessMode("compareAndExchangeRelease", AccessType.COMPARE_AND_EXCHANGE);
            WEAK_COMPARE_AND_SET_PLAIN = new AccessMode("weakCompareAndSetPlain", AccessType.COMPARE_AND_SWAP);
            WEAK_COMPARE_AND_SET = new AccessMode("weakCompareAndSet", AccessType.COMPARE_AND_SWAP);
            WEAK_COMPARE_AND_SET_ACQUIRE = new AccessMode("weakCompareAndSetAcquire", AccessType.COMPARE_AND_SWAP);
            WEAK_COMPARE_AND_SET_RELEASE = new AccessMode("weakCompareAndSetRelease", AccessType.COMPARE_AND_SWAP);
            GET_AND_SET = new AccessMode("getAndSet", AccessType.GET_AND_UPDATE);
            GET_AND_SET_ACQUIRE = new AccessMode("getAndSetAcquire", AccessType.GET_AND_UPDATE);
            GET_AND_SET_RELEASE = new AccessMode("getAndSetRelease", AccessType.GET_AND_UPDATE);
            GET_AND_ADD = new AccessMode("getAndAdd", AccessType.GET_AND_UPDATE_NUMERIC);
            GET_AND_ADD_ACQUIRE = new AccessMode("getAndAddAcquire", AccessType.GET_AND_UPDATE_NUMERIC);
            GET_AND_ADD_RELEASE = new AccessMode("getAndAddRelease", AccessType.GET_AND_UPDATE_NUMERIC);
            GET_AND_BITWISE_OR = new AccessMode("getAndBitwiseOr", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_OR_RELEASE = new AccessMode("getAndBitwiseOrRelease", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_OR_ACQUIRE = new AccessMode("getAndBitwiseOrAcquire", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_AND = new AccessMode("getAndBitwiseAnd", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_AND_RELEASE = new AccessMode("getAndBitwiseAndRelease", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_AND_ACQUIRE = new AccessMode("getAndBitwiseAndAcquire", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_XOR = new AccessMode("getAndBitwiseXor", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_XOR_RELEASE = new AccessMode("getAndBitwiseXorRelease", AccessType.GET_AND_UPDATE_BITWISE);
            GET_AND_BITWISE_XOR_ACQUIRE = new AccessMode("getAndBitwiseXorAcquire", AccessType.GET_AND_UPDATE_BITWISE);
            $VALUES = new AccessMode[]{GET, SET, GET_VOLATILE, SET_VOLATILE, GET_ACQUIRE, SET_RELEASE, GET_OPAQUE, SET_OPAQUE, COMPARE_AND_SET, COMPARE_AND_EXCHANGE, COMPARE_AND_EXCHANGE_ACQUIRE, COMPARE_AND_EXCHANGE_RELEASE, WEAK_COMPARE_AND_SET_PLAIN, WEAK_COMPARE_AND_SET, WEAK_COMPARE_AND_SET_ACQUIRE, WEAK_COMPARE_AND_SET_RELEASE, GET_AND_SET, GET_AND_SET_ACQUIRE, GET_AND_SET_RELEASE, GET_AND_ADD, GET_AND_ADD_ACQUIRE, GET_AND_ADD_RELEASE, GET_AND_BITWISE_OR, GET_AND_BITWISE_OR_RELEASE, GET_AND_BITWISE_OR_ACQUIRE, GET_AND_BITWISE_AND, GET_AND_BITWISE_AND_RELEASE, GET_AND_BITWISE_AND_ACQUIRE, GET_AND_BITWISE_XOR, GET_AND_BITWISE_XOR_RELEASE, GET_AND_BITWISE_XOR_ACQUIRE};
            methodNameToAccessMode = new HashMap<String, AccessMode>(AccessMode.values().length);
            for (AccessMode accessMode : AccessMode.values()) {
                methodNameToAccessMode.put(accessMode.methodName, accessMode);
            }
        }

        private AccessMode(String string2, AccessType accessType) {
            this.methodName = string2;
            this.at = accessType;
        }

        public static AccessMode valueFromMethodName(String string) {
            Object object = methodNameToAccessMode.get(string);
            if (object != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No AccessMode value for method name ");
            ((StringBuilder)object).append(string);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public static AccessMode valueOf(String string) {
            return Enum.valueOf(AccessMode.class, string);
        }

        public static AccessMode[] values() {
            return (AccessMode[])$VALUES.clone();
        }

        public String methodName() {
            return this.methodName;
        }
    }

    static enum AccessType {
        GET,
        SET,
        COMPARE_AND_SWAP,
        COMPARE_AND_EXCHANGE,
        GET_AND_UPDATE,
        GET_AND_UPDATE_BITWISE,
        GET_AND_UPDATE_NUMERIC;
        

        private static Class<?>[] allocateParameters(int n, Class<?> class_, Class<?> ... arrclass) {
            int n2 = class_ != null ? 1 : 0;
            return new Class[n2 + arrclass.length + n];
        }

        private static int fillParameters(Class<?>[] arrclass, Class<?> class_, Class<?> ... arrclass2) {
            int n = 0;
            if (class_ != null) {
                arrclass[0] = class_;
                n = 0 + 1;
            }
            int n2 = 0;
            while (n2 < arrclass2.length) {
                arrclass[n] = arrclass2[n2];
                ++n2;
                ++n;
            }
            return n;
        }

        MethodType accessModeType(Class<?> class_, Class<?> class_2, Class<?> ... arrclass) {
            switch (this) {
                default: {
                    throw new InternalError("Unknown AccessType");
                }
                case GET_AND_UPDATE: 
                case GET_AND_UPDATE_BITWISE: 
                case GET_AND_UPDATE_NUMERIC: {
                    Class<?>[] arrclass2 = AccessType.allocateParameters(1, class_, arrclass);
                    arrclass2[AccessType.fillParameters(arrclass2, class_, arrclass)] = class_2;
                    return MethodType.methodType(class_2, arrclass2);
                }
                case COMPARE_AND_EXCHANGE: {
                    Class<?>[] arrclass3 = AccessType.allocateParameters(2, class_, arrclass);
                    int n = AccessType.fillParameters(arrclass3, class_, arrclass);
                    arrclass3[n] = class_2;
                    arrclass3[n + 1] = class_2;
                    return MethodType.methodType(class_2, arrclass3);
                }
                case COMPARE_AND_SWAP: {
                    Class<?>[] arrclass4 = AccessType.allocateParameters(2, class_, arrclass);
                    int n = AccessType.fillParameters(arrclass4, class_, arrclass);
                    arrclass4[n] = class_2;
                    arrclass4[n + 1] = class_2;
                    return MethodType.methodType(Boolean.TYPE, arrclass4);
                }
                case SET: {
                    Class<?>[] arrclass5 = AccessType.allocateParameters(1, class_, arrclass);
                    arrclass5[AccessType.fillParameters(arrclass5, class_, arrclass)] = class_2;
                    return MethodType.methodType(Void.TYPE, arrclass5);
                }
                case GET: 
            }
            Class<?>[] arrclass6 = AccessType.allocateParameters(0, class_, arrclass);
            AccessType.fillParameters(arrclass6, class_, arrclass);
            return MethodType.methodType(class_2, arrclass6);
        }
    }

}

