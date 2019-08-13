/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.Striped64;
import java.util.function.DoubleBinaryOperator;

public class DoubleAccumulator
extends Striped64
implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;
    private final DoubleBinaryOperator function;
    private final long identity;

    public DoubleAccumulator(DoubleBinaryOperator doubleBinaryOperator, double d) {
        long l;
        this.function = doubleBinaryOperator;
        this.identity = l = Double.doubleToRawLongBits(d);
        this.base = l;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new SerializationProxy(this.get(), this.function, this.identity);
    }

    public void accumulate(double d) {
        block3 : {
            boolean bl;
            block4 : {
                long l;
                boolean bl2;
                long l2;
                DoubleBinaryOperator doubleBinaryOperator;
                Object object = this.cells;
                if (object == null && ((l2 = Double.doubleToRawLongBits((doubleBinaryOperator = this.function).applyAsDouble(Double.longBitsToDouble(l = this.base), d))) == l || this.casBase(l, l2))) break block3;
                bl = bl2 = true;
                if (object == null) break block4;
                int n = ((Striped64.Cell[])object).length;
                boolean bl3 = true;
                bl = bl2;
                if (--n < 0) break block4;
                object = object[DoubleAccumulator.getProbe() & n];
                bl = bl2;
                if (object == null) break block4;
                doubleBinaryOperator = this.function;
                l = ((Striped64.Cell)object).value;
                l2 = Double.doubleToRawLongBits(doubleBinaryOperator.applyAsDouble(Double.longBitsToDouble(l), d));
                bl = l2 != l && !((Striped64.Cell)object).cas(l, l2) ? false : bl3;
                bl3 = bl;
                if (bl) break block3;
                bl = bl3;
            }
            this.doubleAccumulate(d, this.function, bl);
        }
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return (float)this.get();
    }

    public double get() {
        double d;
        Striped64.Cell[] arrcell = this.cells;
        double d2 = d = Double.longBitsToDouble(this.base);
        if (arrcell != null) {
            int n = arrcell.length;
            int n2 = 0;
            do {
                d2 = d;
                if (n2 >= n) break;
                Striped64.Cell cell = arrcell[n2];
                d2 = d;
                if (cell != null) {
                    d2 = this.function.applyAsDouble(d, Double.longBitsToDouble(cell.value));
                }
                ++n2;
                d = d2;
            } while (true);
        }
        return d2;
    }

    public double getThenReset() {
        Striped64.Cell[] arrcell = this.cells;
        double d = Double.longBitsToDouble(this.base);
        this.base = this.identity;
        double d2 = d;
        if (arrcell != null) {
            int n = arrcell.length;
            int n2 = 0;
            do {
                d2 = d;
                if (n2 >= n) break;
                Striped64.Cell cell = arrcell[n2];
                d2 = d;
                if (cell != null) {
                    d2 = Double.longBitsToDouble(cell.value);
                    cell.reset(this.identity);
                    d2 = this.function.applyAsDouble(d, d2);
                }
                ++n2;
                d = d2;
            } while (true);
        }
        return d2;
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    @Override
    public long longValue() {
        return (long)this.get();
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
        return Double.toString(this.get());
    }

    private static class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;
        private final DoubleBinaryOperator function;
        private final long identity;
        private final double value;

        SerializationProxy(double d, DoubleBinaryOperator doubleBinaryOperator, long l) {
            this.value = d;
            this.function = doubleBinaryOperator;
            this.identity = l;
        }

        private Object readResolve() {
            double d = Double.longBitsToDouble(this.identity);
            DoubleAccumulator doubleAccumulator = new DoubleAccumulator(this.function, d);
            doubleAccumulator.base = Double.doubleToRawLongBits(this.value);
            return doubleAccumulator;
        }
    }

}

