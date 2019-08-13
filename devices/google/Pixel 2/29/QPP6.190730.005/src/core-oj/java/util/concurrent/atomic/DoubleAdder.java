/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.Striped64;
import java.util.function.DoubleBinaryOperator;

public class DoubleAdder
extends Striped64
implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    public void add(double d) {
        block2 : {
            boolean bl;
            block3 : {
                boolean bl2;
                long l;
                Object object = this.cells;
                if (object == null && this.casBase(l = this.base, Double.doubleToRawLongBits(Double.longBitsToDouble(l) + d))) break block2;
                bl = bl2 = true;
                if (object == null) break block3;
                int n = ((Striped64.Cell[])object).length - 1;
                bl = bl2;
                if (n < 0) break block3;
                object = object[DoubleAdder.getProbe() & n];
                bl = bl2;
                if (object == null) break block3;
                l = ((Striped64.Cell)object).value;
                bl = bl2 = ((Striped64.Cell)object).cas(l, Double.doubleToRawLongBits(Double.longBitsToDouble(l) + d));
                if (bl2) break block2;
            }
            this.doubleAccumulate(d, null, bl);
        }
    }

    @Override
    public double doubleValue() {
        return this.sum();
    }

    @Override
    public float floatValue() {
        return (float)this.sum();
    }

    @Override
    public int intValue() {
        return (int)this.sum();
    }

    @Override
    public long longValue() {
        return (long)this.sum();
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

    public double sum() {
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
                    d2 = d + Double.longBitsToDouble(cell.value);
                }
                ++n2;
                d = d2;
            } while (true);
        }
        return d2;
    }

    public double sumThenReset() {
        Striped64.Cell[] arrcell = this.cells;
        double d = Double.longBitsToDouble(this.base);
        this.base = 0L;
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
                    long l = cell.value;
                    cell.reset();
                    d2 = d + Double.longBitsToDouble(l);
                }
                ++n2;
                d = d2;
            } while (true);
        }
        return d2;
    }

    public String toString() {
        return Double.toString(this.sum());
    }

    private static class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;
        private final double value;

        SerializationProxy(DoubleAdder doubleAdder) {
            this.value = doubleAdder.sum();
        }

        private Object readResolve() {
            DoubleAdder doubleAdder = new DoubleAdder();
            doubleAdder.base = Double.doubleToRawLongBits(this.value);
            return doubleAdder;
        }
    }

}

