/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.Striped64;
import java.util.function.LongBinaryOperator;

public class LongAdder
extends Striped64
implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    public void add(long l) {
        block2 : {
            boolean bl;
            block3 : {
                boolean bl2;
                long l2;
                Object object = this.cells;
                if (object == null && this.casBase(l2 = this.base, l2 + l)) break block2;
                bl = bl2 = true;
                if (object == null) break block3;
                int n = ((Striped64.Cell[])object).length - 1;
                bl = bl2;
                if (n < 0) break block3;
                object = object[LongAdder.getProbe() & n];
                bl = bl2;
                if (object == null) break block3;
                l2 = ((Striped64.Cell)object).value;
                bl = bl2 = ((Striped64.Cell)object).cas(l2, l2 + l);
                if (bl2) break block2;
            }
            this.longAccumulate(l, null, bl);
        }
    }

    public void decrement() {
        this.add(-1L);
    }

    @Override
    public double doubleValue() {
        return this.sum();
    }

    @Override
    public float floatValue() {
        return this.sum();
    }

    public void increment() {
        this.add(1L);
    }

    @Override
    public int intValue() {
        return (int)this.sum();
    }

    @Override
    public long longValue() {
        return this.sum();
    }

    public void reset() {
        Striped64.Cell[] arrcell = this.cells;
        this.base = 0L;
        if (arrcell != null) {
            for (Striped64.Cell cell : arrcell) {
                if (cell == null) continue;
                cell.reset();
            }
        }
    }

    public long sum() {
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
                    l2 = l + cell.value;
                }
                ++n2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    public long sumThenReset() {
        Striped64.Cell[] arrcell = this.cells;
        long l = this.base;
        this.base = 0L;
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
                    l2 = l + cell.value;
                    cell.reset();
                }
                ++n2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    public String toString() {
        return Long.toString(this.sum());
    }

    private static class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;
        private final long value;

        SerializationProxy(LongAdder longAdder) {
            this.value = longAdder.sum();
        }

        private Object readResolve() {
            LongAdder longAdder = new LongAdder();
            longAdder.base = this.value;
            return longAdder;
        }
    }

}

