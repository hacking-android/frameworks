/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleBinaryOperator;
import java.util.function.LongBinaryOperator;
import sun.misc.Unsafe;

abstract class Striped64
extends Number {
    private static final long BASE;
    private static final long CELLSBUSY;
    static final int NCPU;
    private static final long PROBE;
    private static final Unsafe U;
    volatile transient long base;
    volatile transient Cell[] cells;
    volatile transient int cellsBusy;

    static {
        NCPU = Runtime.getRuntime().availableProcessors();
        U = Unsafe.getUnsafe();
        try {
            BASE = U.objectFieldOffset(Striped64.class.getDeclaredField("base"));
            CELLSBUSY = U.objectFieldOffset(Striped64.class.getDeclaredField("cellsBusy"));
            PROBE = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    Striped64() {
    }

    static final int advanceProbe(int n) {
        n ^= n << 13;
        n ^= n >>> 17;
        n ^= n << 5;
        U.putInt(Thread.currentThread(), PROBE, n);
        return n;
    }

    private static long apply(DoubleBinaryOperator doubleBinaryOperator, long l, double d) {
        double d2 = Double.longBitsToDouble(l);
        d = doubleBinaryOperator == null ? d2 + d : doubleBinaryOperator.applyAsDouble(d2, d);
        return Double.doubleToRawLongBits(d);
    }

    static final int getProbe() {
        return U.getInt(Thread.currentThread(), PROBE);
    }

    final boolean casBase(long l, long l2) {
        return U.compareAndSwapLong(this, BASE, l, l2);
    }

    final boolean casCellsBusy() {
        return U.compareAndSwapInt(this, CELLSBUSY, 0, 1);
    }

    final void doubleAccumulate(double d, DoubleBinaryOperator arrcell, boolean bl) {
        int n;
        int n2 = n = Striped64.getProbe();
        if (n == 0) {
            ThreadLocalRandom.current();
            n2 = Striped64.getProbe();
            bl = true;
        }
        n = 0;
        boolean bl2 = bl;
        int n3 = n2;
        do {
            block33 : {
                block30 : {
                    long l;
                    block34 : {
                        block26 : {
                            Object object;
                            block27 : {
                                block31 : {
                                    Cell[] arrcell2;
                                    int n4;
                                    block32 : {
                                        block28 : {
                                            block29 : {
                                                block25 : {
                                                    if ((object = this.cells) == null || (n4 = ((Cell[])object).length) <= 0) break block27;
                                                    arrcell2 = object[n4 - 1 & n3];
                                                    if (arrcell2 != null) break block28;
                                                    if (this.cellsBusy != 0) break block29;
                                                    object = new Cell(Double.doubleToRawLongBits(d));
                                                    if (this.cellsBusy != 0 || !this.casCellsBusy()) break block29;
                                                    try {
                                                        arrcell2 = this.cells;
                                                        if (arrcell2 == null) break block25;
                                                    }
                                                    catch (Throwable throwable) {
                                                        this.cellsBusy = 0;
                                                        throw throwable;
                                                    }
                                                    n2 = arrcell2.length;
                                                    if (n2 <= 0 || arrcell2[n2 = n2 - 1 & n3] != null) break block25;
                                                    arrcell2[n2] = object;
                                                    this.cellsBusy = 0;
                                                    break block30;
                                                }
                                                this.cellsBusy = 0;
                                                continue;
                                            }
                                            n2 = 0;
                                            bl = bl2;
                                            break block31;
                                        }
                                        if (bl2) break block32;
                                        bl = true;
                                        n2 = n;
                                        break block31;
                                    }
                                    l = arrcell2.value;
                                    if (arrcell2.cas(l, Striped64.apply((DoubleBinaryOperator)arrcell, l, d))) break block30;
                                    if (n4 < NCPU && this.cells == object) {
                                        if (n == 0) {
                                            n2 = 1;
                                            bl = bl2;
                                        } else {
                                            bl = bl2;
                                            n2 = n;
                                            if (this.cellsBusy == 0) {
                                                bl = bl2;
                                                n2 = n;
                                                if (this.casCellsBusy()) {
                                                    try {
                                                        if (this.cells == object) {
                                                            this.cells = Arrays.copyOf(object, n4 << 1);
                                                        }
                                                        n = 0;
                                                    }
                                                    finally {
                                                        this.cellsBusy = 0;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        n2 = 0;
                                        bl = bl2;
                                    }
                                }
                                n3 = Striped64.advanceProbe(n3);
                                break block33;
                            }
                            if (this.cellsBusy != 0 || this.cells != object || !this.casCellsBusy()) break block34;
                            if (this.cells != object) break block26;
                            arrcell = new Cell[2];
                            arrcell[n3 & 1] = object = new Cell(Double.doubleToRawLongBits(d));
                            this.cells = arrcell;
                        }
                        this.cellsBusy = 0;
                        bl = bl2;
                        n2 = n;
                        break block33;
                        finally {
                            this.cellsBusy = 0;
                        }
                    }
                    l = this.base;
                    bl = bl2;
                    n2 = n;
                    if (!this.casBase(l, Striped64.apply((DoubleBinaryOperator)arrcell, l, d))) break block33;
                }
                return;
            }
            bl2 = bl;
            n = n2;
        } while (true);
    }

    final void longAccumulate(long l, LongBinaryOperator arrcell, boolean bl) {
        int n;
        int n2 = n = Striped64.getProbe();
        if (n == 0) {
            ThreadLocalRandom.current();
            n2 = Striped64.getProbe();
            bl = true;
        }
        n = 0;
        boolean bl2 = bl;
        int n3 = n2;
        do {
            block33 : {
                block30 : {
                    long l2;
                    long l3;
                    block34 : {
                        block26 : {
                            Cell cell;
                            Cell[] arrcell2;
                            block27 : {
                                block31 : {
                                    int n4;
                                    block32 : {
                                        block28 : {
                                            block29 : {
                                                block25 : {
                                                    if ((arrcell2 = this.cells) == null || (n4 = arrcell2.length) <= 0) break block27;
                                                    cell = arrcell2[n4 - 1 & n3];
                                                    if (cell != null) break block28;
                                                    if (this.cellsBusy != 0) break block29;
                                                    cell = new Cell(l);
                                                    if (this.cellsBusy != 0 || !this.casCellsBusy()) break block29;
                                                    try {
                                                        arrcell2 = this.cells;
                                                        if (arrcell2 == null) break block25;
                                                    }
                                                    catch (Throwable throwable) {
                                                        this.cellsBusy = 0;
                                                        throw throwable;
                                                    }
                                                    n2 = arrcell2.length;
                                                    if (n2 <= 0 || arrcell2[n2 = n2 - 1 & n3] != null) break block25;
                                                    arrcell2[n2] = cell;
                                                    this.cellsBusy = 0;
                                                    break block30;
                                                }
                                                this.cellsBusy = 0;
                                                continue;
                                            }
                                            n2 = 0;
                                            bl = bl2;
                                            break block31;
                                        }
                                        if (bl2) break block32;
                                        bl = true;
                                        n2 = n;
                                        break block31;
                                    }
                                    l2 = cell.value;
                                    l3 = arrcell == null ? l2 + l : arrcell.applyAsLong(l2, l);
                                    if (cell.cas(l2, l3)) break block30;
                                    if (n4 < NCPU && this.cells == arrcell2) {
                                        if (n == 0) {
                                            n2 = 1;
                                            bl = bl2;
                                        } else {
                                            bl = bl2;
                                            n2 = n;
                                            if (this.cellsBusy == 0) {
                                                bl = bl2;
                                                n2 = n;
                                                if (this.casCellsBusy()) {
                                                    try {
                                                        if (this.cells == arrcell2) {
                                                            this.cells = Arrays.copyOf(arrcell2, n4 << 1);
                                                        }
                                                        n = 0;
                                                    }
                                                    finally {
                                                        this.cellsBusy = 0;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        n2 = 0;
                                        bl = bl2;
                                    }
                                }
                                n3 = Striped64.advanceProbe(n3);
                                break block33;
                            }
                            if (this.cellsBusy != 0 || this.cells != arrcell2 || !this.casCellsBusy()) break block34;
                            if (this.cells != arrcell2) break block26;
                            arrcell = new Cell[2];
                            arrcell[n3 & 1] = cell = new Cell(l);
                            this.cells = arrcell;
                        }
                        this.cellsBusy = 0;
                        bl = bl2;
                        n2 = n;
                        break block33;
                        finally {
                            this.cellsBusy = 0;
                        }
                    }
                    l2 = this.base;
                    l3 = arrcell == null ? l2 + l : arrcell.applyAsLong(l2, l);
                    bl = bl2;
                    n2 = n;
                    if (!this.casBase(l2, l3)) break block33;
                }
                return;
            }
            bl2 = bl;
            n = n2;
        } while (true);
    }

    static final class Cell {
        private static final Unsafe U = Unsafe.getUnsafe();
        private static final long VALUE;
        volatile long value;

        static {
            try {
                VALUE = U.objectFieldOffset(Cell.class.getDeclaredField("value"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Cell(long l) {
            this.value = l;
        }

        final boolean cas(long l, long l2) {
            return U.compareAndSwapLong(this, VALUE, l, l2);
        }

        final void reset() {
            U.putLongVolatile(this, VALUE, 0L);
        }

        final void reset(long l) {
            U.putLongVolatile(this, VALUE, l);
        }
    }

}

