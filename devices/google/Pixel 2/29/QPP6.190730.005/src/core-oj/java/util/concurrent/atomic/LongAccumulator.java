/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.Striped64;
import java.util.function.LongBinaryOperator;

public class LongAccumulator
extends Striped64
implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;
    private final LongBinaryOperator function;
    private final long identity;

    public LongAccumulator(LongBinaryOperator longBinaryOperator, long l) {
        this.function = longBinaryOperator;
        this.identity = l;
        this.base = l;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new SerializationProxy(this.get(), this.function, this.identity);
    }

    public void accumulate(long l) {
        block3 : {
            boolean bl;
            block4 : {
                LongBinaryOperator longBinaryOperator;
                long l2;
                boolean bl2;
                long l3;
                Object object = this.cells;
                if (object == null && ((l3 = (longBinaryOperator = this.function).applyAsLong(l2 = this.base, l)) == l2 || this.casBase(l2, l3))) break block3;
                bl = bl2 = true;
                if (object == null) break block4;
                int n = ((Striped64.Cell[])object).length;
                boolean bl3 = true;
                bl = bl2;
                if (--n < 0) break block4;
                object = object[LongAccumulator.getProbe() & n];
                bl = bl2;
                if (object == null) break block4;
                longBinaryOperator = this.function;
                l3 = ((Striped64.Cell)object).value;
                l2 = longBinaryOperator.applyAsLong(l3, l);
                bl = l2 != l3 && !((Striped64.Cell)object).cas(l3, l2) ? false : bl3;
                bl3 = bl;
                if (bl) break block3;
                bl = bl3;
            }
            this.longAccumulate(l, this.function, bl);
        }
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return this.get();
    }

    public long get() {
        long l;
        Striped64.Cell[] arrcell = this.cells;
        long l2 = l = this.base;
        if (arrcell != null) {
            int n = arrcell.length;
            int n2 = 0;
            do {
                l2 = l;
                if (n2 >= n) break;
                Striped64.Cell cell = arrcell[n2];
                l2 = l;
                if (cell != null) {
                    l2 = this.function.applyAsLong(l, cell.value);
                }
                ++n2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    public long getThenReset() {
        Striped64.Cell[] arrcell = this.cells;
        long l = this.base;
        this.base = this.identity;
        long l2 = l;
        if (arrcell != null) {
            int n = arrcell.length;
            int n2 = 0;
            do {
                l2 = l;
                if (n2 >= n) break;
                Striped64.Cell cell = arrcell[n2];
                l2 = l;
                if (cell != null) {
                    l2 = cell.value;
                    cell.reset(this.identity);
                    l2 = this.function.applyAsLong(l, l2);
                }
                ++n2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    @Override
    public long longValue() {
        return this.get();
    }

    public void reset() {
        Striped64.Cell[] arrcell = this.cells;
        this.base = this.identity;
        if (arrcell != null) {
            for (Striped64.Cell cell : arrcell) {
                if (cell == null) continue;
                cell.reset(this.identity);
            }
        }
    }

    public String toString() {
        return Long.toString(this.get());
    }

    private static class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;
        private final LongBinaryOperator function;
        private final long identity;
        private final long value;

        SerializationProxy(long l, LongBinaryOperator longBinaryOperator, long l2) {
            this.value = l;
            this.function = longBinaryOperator;
            this.identity = l2;
        }

        private Object readResolve() {
            LongAccumulator longAccumulator = new LongAccumulator(this.function, this.identity);
            longAccumulator.base = this.value;
            return longAccumulator;
        }
    }

}

