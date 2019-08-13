/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util.function.pooled;

import android.os.Message;
import android.text.TextUtils;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HeptPredicate;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.HexPredicate;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.NonaFunction;
import com.android.internal.util.function.NonaPredicate;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.OctPredicate;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuadPredicate;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.TriPredicate;
import com.android.internal.util.function.pooled.ArgumentPlaceholder;
import com.android.internal.util.function.pooled.OmniFunction;
import com.android.internal.util.function.pooled.PooledConsumer;
import com.android.internal.util.function.pooled.PooledFunction;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.function.pooled.PooledRunnable;
import com.android.internal.util.function.pooled.PooledSupplier;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class PooledLambdaImpl<R>
extends OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> {
    private static final boolean DEBUG = false;
    private static final int FLAG_ACQUIRED_FROM_MESSAGE_CALLBACKS_POOL = 2048;
    private static final int FLAG_RECYCLED = 512;
    private static final int FLAG_RECYCLE_ON_USE = 1024;
    private static final String LOG_TAG = "PooledLambdaImpl";
    static final int MASK_EXPOSED_AS = 520192;
    static final int MASK_FUNC_TYPE = 66584576;
    private static final int MAX_ARGS = 9;
    private static final int MAX_POOL_SIZE = 50;
    static final Pool sMessageCallbacksPool;
    static final Pool sPool;
    Object[] mArgs = null;
    long mConstValue;
    int mFlags = 0;
    Object mFunc;

    static {
        sPool = new Pool(new Object());
        sMessageCallbacksPool = new Pool(Message.sPoolSync);
    }

    private PooledLambdaImpl() {
    }

    static <E extends PooledLambda> E acquire(Pool object, Object object2, int n, int n2, int n3, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10, Object object11) {
        object = PooledLambdaImpl.acquire((Pool)object);
        ((PooledLambdaImpl)object).mFunc = Preconditions.checkNotNull(object2);
        ((PooledLambdaImpl)object).setFlags(66584576, LambdaType.encode(n, n3));
        ((PooledLambdaImpl)object).setFlags(520192, LambdaType.encode(n2, n3));
        if (ArrayUtils.size(((PooledLambdaImpl)object).mArgs) < n) {
            ((PooledLambdaImpl)object).mArgs = new Object[n];
        }
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 0, object3);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 1, object4);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 2, object5);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 3, object6);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 4, object7);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 5, object8);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 6, object9);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 7, object10);
        PooledLambdaImpl.setIfInBounds(((PooledLambdaImpl)object).mArgs, 8, object11);
        return (E)object;
    }

    static PooledLambdaImpl acquire(Pool pool) {
        PooledLambdaImpl<R> pooledLambdaImpl;
        PooledLambdaImpl<R> pooledLambdaImpl2 = pooledLambdaImpl = (PooledLambdaImpl<R>)pool.acquire();
        if (pooledLambdaImpl == null) {
            pooledLambdaImpl2 = new PooledLambdaImpl<R>();
        }
        pooledLambdaImpl2.mFlags &= -513;
        int n = pool == sMessageCallbacksPool ? 1 : 0;
        pooledLambdaImpl2.setFlags(2048, n);
        return pooledLambdaImpl2;
    }

    static PooledLambdaImpl acquireConstSupplier(int n) {
        PooledLambdaImpl pooledLambdaImpl = PooledLambdaImpl.acquire(sPool);
        n = LambdaType.encode(15, n);
        pooledLambdaImpl.setFlags(66584576, n);
        pooledLambdaImpl.setFlags(520192, n);
        return pooledLambdaImpl;
    }

    private void checkNotRecycled() {
        if (!this.isRecycled()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Instance is recycled: ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private String commaSeparateFirstN(Object[] arrobject, int n) {
        if (arrobject == null) {
            return "";
        }
        return TextUtils.join((CharSequence)",", Arrays.copyOf(arrobject, n));
    }

    private R doInvoke() {
        int n;
        block33 : {
            block34 : {
                block35 : {
                    int n2 = this.getFlags(66584576);
                    int n3 = LambdaType.decodeArgCount(n2);
                    n = LambdaType.decodeReturnType(n2);
                    if (n3 == 15) break block33;
                    switch (n3) {
                        default: {
                            break;
                        }
                        case 9: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((NonaFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7), this.popArg(8));
                                }
                                return ((NonaPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7), this.popArg(8));
                            }
                            ((NonaConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7), this.popArg(8));
                            return null;
                        }
                        case 8: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((OctFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7));
                                }
                                return ((OctPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7));
                            }
                            ((OctConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6), this.popArg(7));
                            return null;
                        }
                        case 7: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((HeptFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6));
                                }
                                return ((HeptPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6));
                            }
                            ((HeptConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5), this.popArg(6));
                            return null;
                        }
                        case 6: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((HexFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5));
                                }
                                return ((HexPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5));
                            }
                            ((HexConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4), this.popArg(5));
                            return null;
                        }
                        case 5: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((QuintFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4));
                                }
                                return ((QuintPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4));
                            }
                            ((QuintConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3), this.popArg(4));
                            return null;
                        }
                        case 4: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((QuadFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3));
                                }
                                return ((QuadPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3));
                            }
                            ((QuadConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2), this.popArg(3));
                            return null;
                        }
                        case 3: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((TriFunction)this.mFunc).apply(this.popArg(0), this.popArg(1), this.popArg(2));
                                }
                                return ((TriPredicate)this.mFunc).test(this.popArg(0), this.popArg(1), this.popArg(2));
                            }
                            ((TriConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1), this.popArg(2));
                            return null;
                        }
                        case 2: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((BiFunction)this.mFunc).apply(this.popArg(0), this.popArg(1));
                                }
                                return ((BiPredicate)this.mFunc).test(this.popArg(0), this.popArg(1));
                            }
                            ((BiConsumer)this.mFunc).accept(this.popArg(0), this.popArg(1));
                            return null;
                        }
                        case 1: {
                            if (n != 1) {
                                if (n != 2) {
                                    if (n != 3) break;
                                    return ((Function)this.mFunc).apply(this.popArg(0));
                                }
                                return ((Predicate)this.mFunc).test(this.popArg(0));
                            }
                            ((Consumer)this.mFunc).accept(this.popArg(0));
                            return null;
                        }
                        case 0: {
                            if (n == 1) break block34;
                            if (n == 2 || n == 3) break block35;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown function type: ");
                    stringBuilder.append(LambdaType.toString(n2));
                    throw new IllegalStateException(stringBuilder.toString());
                }
                return (R)((Supplier)this.mFunc).get();
            }
            ((Runnable)this.mFunc).run();
            return null;
        }
        if (n != 4) {
            if (n != 5) {
                if (n != 6) {
                    return (R)this.mFunc;
                }
                return this.getAsDouble();
            }
            return this.getAsLong();
        }
        return this.getAsInt();
    }

    private void doRecycle() {
        Pool pool = (this.mFlags & 2048) != 0 ? sMessageCallbacksPool : sPool;
        this.mFunc = null;
        Object[] arrobject = this.mArgs;
        if (arrobject != null) {
            Arrays.fill(arrobject, null);
        }
        this.mFlags = 512;
        this.mConstValue = 0L;
        pool.release(this);
    }

    private boolean fillInArg(Object object) {
        int n = ArrayUtils.size(this.mArgs);
        for (int i = 0; i < n; ++i) {
            if (this.mArgs[i] != ArgumentPlaceholder.INSTANCE) continue;
            this.mArgs[i] = object;
            this.mFlags = (int)((long)this.mFlags | BitUtils.bitAt(i));
            return true;
        }
        if (object != null && object != ArgumentPlaceholder.INSTANCE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No more arguments expected for provided arg ");
            stringBuilder.append(object);
            stringBuilder.append(" among ");
            stringBuilder.append(Arrays.toString(this.mArgs));
            throw new IllegalStateException(stringBuilder.toString());
        }
        return false;
    }

    private String getFuncTypeAsString() {
        if (this.isRecycled()) {
            return "<recycled>";
        }
        if (this.isConstSupplier()) {
            return "supplier";
        }
        String string2 = LambdaType.toString(this.getFlags(520192));
        if (string2.endsWith("Consumer")) {
            return "consumer";
        }
        if (string2.endsWith("Function")) {
            return "function";
        }
        if (string2.endsWith("Predicate")) {
            return "predicate";
        }
        if (string2.endsWith("Supplier")) {
            return "supplier";
        }
        if (string2.endsWith("Runnable")) {
            return "runnable";
        }
        return string2;
    }

    private static String hashCodeHex(Object object) {
        return Integer.toHexString(Objects.hashCode(object));
    }

    private boolean isConstSupplier() {
        boolean bl = LambdaType.decodeArgCount(this.getFlags(66584576)) == 15;
        return bl;
    }

    private boolean isInvocationArgAtIndex(int n) {
        int n2 = this.mFlags;
        boolean bl = true;
        if ((n2 & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    private boolean isRecycleOnUse() {
        boolean bl = (this.mFlags & 1024) != 0;
        return bl;
    }

    private boolean isRecycled() {
        boolean bl = (this.mFlags & 512) != 0;
        return bl;
    }

    private static int mask(int n, int n2) {
        return n2 << Integer.numberOfTrailingZeros(n) & n;
    }

    private Object popArg(int n) {
        Object object = this.mArgs[n];
        if (this.isInvocationArgAtIndex(n)) {
            this.mArgs[n] = ArgumentPlaceholder.INSTANCE;
            this.mFlags = (int)((long)this.mFlags & BitUtils.bitAt(n));
        }
        return object;
    }

    private static void setIfInBounds(Object[] arrobject, int n, Object object) {
        if (n < ArrayUtils.size(arrobject)) {
            arrobject[n] = object;
        }
    }

    private static int unmask(int n, int n2) {
        return (n2 & n) / (1 << Integer.numberOfTrailingZeros(n));
    }

    @Override
    public <V> OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, V> andThen(Function<? super R, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getAsDouble() {
        return Double.longBitsToDouble(this.mConstValue);
    }

    @Override
    public int getAsInt() {
        return (int)this.mConstValue;
    }

    @Override
    public long getAsLong() {
        return this.mConstValue;
    }

    int getFlags(int n) {
        return PooledLambdaImpl.unmask(n, this.mFlags);
    }

    @Override
    R invoke(Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        int n;
        int n2;
        this.checkNotRecycled();
        if (!(this.fillInArg(object) && this.fillInArg(object2) && this.fillInArg(object3) && this.fillInArg(object4) && this.fillInArg(object5) && this.fillInArg(object6) && this.fillInArg(object7) && this.fillInArg(object8) && !this.fillInArg(object9))) {
            // empty if block
        }
        if ((n2 = LambdaType.decodeArgCount(this.getFlags(66584576))) != 15) {
            for (n = 0; n < n2; ++n) {
                if (this.mArgs[n] != ArgumentPlaceholder.INSTANCE) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Missing argument #");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" among ");
                ((StringBuilder)object).append(Arrays.toString(this.mArgs));
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        }
        try {
            object = this.doInvoke();
            return (R)object;
        }
        finally {
            if (this.isRecycleOnUse()) {
                this.doRecycle();
            }
            if (!this.isRecycled()) {
                n2 = ArrayUtils.size(this.mArgs);
                for (n = 0; n < n2; ++n) {
                    this.popArg(n);
                }
            }
        }
    }

    @Override
    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> negate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void recycle() {
        if (!this.isRecycled()) {
            this.doRecycle();
        }
    }

    @Override
    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> recycleOnUse() {
        this.mFlags |= 1024;
        return this;
    }

    void setFlags(int n, int n2) {
        this.mFlags &= n;
        this.mFlags |= PooledLambdaImpl.mask(n, n2);
    }

    public String toString() {
        if (this.isRecycled()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<recycled PooledLambda@");
            stringBuilder.append(PooledLambdaImpl.hashCodeHex(this));
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.isConstSupplier()) {
            stringBuilder.append(this.getFuncTypeAsString());
            stringBuilder.append("(");
            stringBuilder.append(this.doInvoke());
            stringBuilder.append(")");
        } else {
            Object object = this.mFunc;
            if (object instanceof PooledLambdaImpl) {
                stringBuilder.append(object);
            } else {
                stringBuilder.append(this.getFuncTypeAsString());
                stringBuilder.append("@");
                stringBuilder.append(PooledLambdaImpl.hashCodeHex(object));
            }
            stringBuilder.append("(");
            stringBuilder.append(this.commaSeparateFirstN(this.mArgs, LambdaType.decodeArgCount(this.getFlags(66584576))));
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    static class LambdaType {
        public static final int MASK = 127;
        public static final int MASK_ARG_COUNT = 15;
        public static final int MASK_BIT_COUNT = 7;
        public static final int MASK_RETURN_TYPE = 112;

        LambdaType() {
        }

        private static String argCountPrefix(int n) {
            if (n != 15) {
                switch (n) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(n);
                        stringBuilder.append("arg");
                        return stringBuilder.toString();
                    }
                    case 9: {
                        return "Nona";
                    }
                    case 8: {
                        return "Oct";
                    }
                    case 7: {
                        return "Hept";
                    }
                    case 6: {
                        return "Hex";
                    }
                    case 5: {
                        return "Quint";
                    }
                    case 4: {
                        return "Quad";
                    }
                    case 3: {
                        return "Tri";
                    }
                    case 2: {
                        return "Bi";
                    }
                    case 1: {
                        return "";
                    }
                    case 0: 
                }
                return "";
            }
            return "";
        }

        static int decodeArgCount(int n) {
            return n & 15;
        }

        static int decodeReturnType(int n) {
            return PooledLambdaImpl.unmask(112, n);
        }

        static int encode(int n, int n2) {
            return PooledLambdaImpl.mask(15, n) | PooledLambdaImpl.mask(112, n2);
        }

        static String toString(int n) {
            int n2 = LambdaType.decodeArgCount(n);
            n = LambdaType.decodeReturnType(n);
            if (n2 == 0) {
                if (n == 1) {
                    return "Runnable";
                }
                if (n == 3 || n == 2) {
                    return "Supplier";
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(LambdaType.argCountPrefix(n2));
            stringBuilder.append(ReturnType.lambdaSuffix(n));
            return stringBuilder.toString();
        }

        static class ReturnType {
            public static final int BOOLEAN = 2;
            public static final int DOUBLE = 6;
            public static final int INT = 4;
            public static final int LONG = 5;
            public static final int OBJECT = 3;
            public static final int VOID = 1;

            ReturnType() {
            }

            static String lambdaSuffix(int n) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ReturnType.prefix(n));
                stringBuilder.append(ReturnType.suffix(n));
                return stringBuilder.toString();
            }

            private static String prefix(int n) {
                if (n != 4) {
                    if (n != 5) {
                        if (n != 6) {
                            return "";
                        }
                        return "Double";
                    }
                    return "Long";
                }
                return "Int";
            }

            private static String suffix(int n) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return "Supplier";
                        }
                        return "Function";
                    }
                    return "Predicate";
                }
                return "Consumer";
            }

            static String toString(int n) {
                switch (n) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(n);
                        return stringBuilder.toString();
                    }
                    case 6: {
                        return "DOUBLE";
                    }
                    case 5: {
                        return "LONG";
                    }
                    case 4: {
                        return "INT";
                    }
                    case 3: {
                        return "OBJECT";
                    }
                    case 2: {
                        return "BOOLEAN";
                    }
                    case 1: 
                }
                return "VOID";
            }
        }

    }

    static class Pool
    extends Pools.SynchronizedPool<PooledLambdaImpl> {
        public Pool(Object object) {
            super(50, object);
        }
    }

}

