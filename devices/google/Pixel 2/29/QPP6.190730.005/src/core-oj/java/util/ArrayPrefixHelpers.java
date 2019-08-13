/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

class ArrayPrefixHelpers {
    static final int CUMULATE = 1;
    static final int FINISHED = 4;
    static final int MIN_PARTITION = 16;
    static final int SUMMED = 2;

    private ArrayPrefixHelpers() {
    }

    static final class CumulateTask<T>
    extends CountedCompleter<Void> {
        private static final long serialVersionUID = 5293554502939613543L;
        final T[] array;
        final int fence;
        final BinaryOperator<T> function;
        final int hi;
        T in;
        CumulateTask<T> left;
        final int lo;
        final int origin;
        T out;
        CumulateTask<T> right;
        final int threshold;

        public CumulateTask(CumulateTask<T> cumulateTask, BinaryOperator<T> binaryOperator, T[] arrT, int n, int n2) {
            super(cumulateTask);
            this.function = binaryOperator;
            this.array = arrT;
            this.origin = n;
            this.lo = n;
            this.fence = n2;
            this.hi = n2;
            n2 = (n2 - n) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            n = 16;
            if (n2 > 16) {
                n = n2;
            }
            this.threshold = n;
        }

        CumulateTask(CumulateTask<T> cumulateTask, BinaryOperator<T> binaryOperator, T[] arrT, int n, int n2, int n3, int n4, int n5) {
            super(cumulateTask);
            this.function = binaryOperator;
            this.array = arrT;
            this.origin = n;
            this.fence = n2;
            this.threshold = n3;
            this.lo = n4;
            this.hi = n5;
        }

        @Override
        public final void compute() {
            T[] arrT;
            BinaryOperator<T> binaryOperator = this.function;
            if (binaryOperator != null && (arrT = this.array) != null) {
                int n;
                int n2;
                int n3 = this.threshold;
                int n4 = this.origin;
                int n5 = this.fence;
                CumulateTask<Object> cumulateTask = this;
                block0 : while ((n2 = cumulateTask.lo) >= 0 && (n = cumulateTask.hi) <= arrT.length) {
                    int n6;
                    int n7;
                    CumulateTask<T> cumulateTask2;
                    CumulateTask<Object> cumulateTask3;
                    CumulateTask<Object> cumulateTask4;
                    if (n - n2 > n3) {
                        cumulateTask3 = cumulateTask.left;
                        CumulateTask<T> cumulateTask5 = cumulateTask.right;
                        if (cumulateTask3 == null) {
                            n7 = n2 + n >>> 1;
                            cumulateTask4 = new CumulateTask<T>(cumulateTask, binaryOperator, arrT, n4, n5, n3, n7, n);
                            cumulateTask.right = cumulateTask4;
                            cumulateTask3 = new CumulateTask<T>(cumulateTask, binaryOperator, arrT, n4, n5, n3, n2, n7);
                            cumulateTask.left = cumulateTask3;
                            cumulateTask = cumulateTask3;
                        } else {
                            block30 : {
                                T t;
                                cumulateTask3.in = t = cumulateTask.in;
                                cumulateTask4 = null;
                                cumulateTask2 = null;
                                Object var15_15 = null;
                                if (cumulateTask5 != null) {
                                    cumulateTask = cumulateTask3.out;
                                    if (n2 != n4) {
                                        cumulateTask = binaryOperator.apply(t, cumulateTask);
                                    }
                                    cumulateTask5.in = cumulateTask;
                                    do {
                                        if (((n7 = cumulateTask5.getPendingCount()) & 1) == 0) continue;
                                        cumulateTask = var15_15;
                                        break block30;
                                    } while (!cumulateTask5.compareAndSetPendingCount(n7, n7 | 1));
                                    cumulateTask = cumulateTask5;
                                } else {
                                    cumulateTask = var15_15;
                                }
                            }
                            while (((n7 = cumulateTask3.getPendingCount()) & 1) == 0) {
                                if (!cumulateTask3.compareAndSetPendingCount(n7, n7 | 1)) continue;
                                cumulateTask4 = cumulateTask2;
                                if (cumulateTask != null) {
                                    cumulateTask4 = cumulateTask;
                                }
                                cumulateTask = cumulateTask3;
                                break;
                            }
                            if (cumulateTask == null) break;
                        }
                        if (cumulateTask4 == null) continue;
                        cumulateTask4.fork();
                        continue;
                    }
                    while (((n6 = cumulateTask.getPendingCount()) & 4) == 0) {
                        n7 = (n6 & 1) != 0 ? 4 : (n2 > n4 ? 2 : 6);
                        if (!cumulateTask.compareAndSetPendingCount(n6, n6 | n7)) continue;
                        if (n7 != 2) {
                            if (n2 == n4) {
                                cumulateTask4 = arrT[n4];
                                n2 = n4 + 1;
                            } else {
                                cumulateTask4 = cumulateTask.in;
                            }
                            while (n2 < n) {
                                cumulateTask3 = binaryOperator.apply(cumulateTask4, arrT[n2]);
                                cumulateTask4 = cumulateTask3;
                                arrT[n2] = cumulateTask3;
                                ++n2;
                            }
                        } else if (n < n5) {
                            cumulateTask4 = arrT[n2];
                            ++n2;
                            while (n2 < n) {
                                cumulateTask4 = binaryOperator.apply(cumulateTask4, arrT[n2]);
                                ++n2;
                            }
                        } else {
                            cumulateTask4 = cumulateTask.in;
                        }
                        cumulateTask.out = cumulateTask4;
                        n2 = n3;
                        do {
                            if ((cumulateTask4 = (CumulateTask)cumulateTask.getCompleter()) == null) {
                                if ((n7 & 4) == 0) break block0;
                                cumulateTask.quietlyComplete();
                                break block0;
                            }
                            n3 = cumulateTask4.getPendingCount();
                            if ((n3 & n7 & 4) != 0) {
                                cumulateTask = cumulateTask4;
                                n = n7;
                            } else if ((n3 & n7 & 2) != 0) {
                                cumulateTask3 = cumulateTask4.left;
                                if (cumulateTask3 != null && (cumulateTask2 = cumulateTask4.right) != null) {
                                    cumulateTask3 = cumulateTask3.out;
                                    if (cumulateTask2.hi != n5) {
                                        cumulateTask3 = binaryOperator.apply(cumulateTask3, cumulateTask2.out);
                                    }
                                    cumulateTask4.out = cumulateTask3;
                                }
                                if ((n6 = n3 | n7 | (n = (n3 & 1) == 0 && cumulateTask4.lo == n4 ? 1 : 0)) == n3 || cumulateTask4.compareAndSetPendingCount(n3, n6)) {
                                    n3 = 2;
                                    cumulateTask3 = cumulateTask4;
                                    n7 = n3;
                                    cumulateTask = cumulateTask3;
                                    if (n != 0) {
                                        cumulateTask4.fork();
                                        cumulateTask = cumulateTask3;
                                        n7 = n3;
                                    }
                                }
                                n = n7;
                            } else {
                                n = n7;
                                if (cumulateTask4.compareAndSetPendingCount(n3, n3 | n7)) break block0;
                            }
                            n7 = n;
                        } while (true);
                    }
                    break block0;
                }
                return;
            }
            throw new NullPointerException();
        }
    }

    static final class DoubleCumulateTask
    extends CountedCompleter<Void> {
        private static final long serialVersionUID = -586947823794232033L;
        final double[] array;
        final int fence;
        final DoubleBinaryOperator function;
        final int hi;
        double in;
        DoubleCumulateTask left;
        final int lo;
        final int origin;
        double out;
        DoubleCumulateTask right;
        final int threshold;

        public DoubleCumulateTask(DoubleCumulateTask doubleCumulateTask, DoubleBinaryOperator doubleBinaryOperator, double[] arrd, int n, int n2) {
            super(doubleCumulateTask);
            this.function = doubleBinaryOperator;
            this.array = arrd;
            this.origin = n;
            this.lo = n;
            this.fence = n2;
            this.hi = n2;
            n2 = (n2 - n) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            n = 16;
            if (n2 > 16) {
                n = n2;
            }
            this.threshold = n;
        }

        DoubleCumulateTask(DoubleCumulateTask doubleCumulateTask, DoubleBinaryOperator doubleBinaryOperator, double[] arrd, int n, int n2, int n3, int n4, int n5) {
            super(doubleCumulateTask);
            this.function = doubleBinaryOperator;
            this.array = arrd;
            this.origin = n;
            this.fence = n2;
            this.threshold = n3;
            this.lo = n4;
            this.hi = n5;
        }

        @Override
        public final void compute() {
            double[] arrd;
            DoubleBinaryOperator doubleBinaryOperator = this.function;
            if (doubleBinaryOperator != null && (arrd = this.array) != null) {
                int n;
                int n2;
                int n3 = this.threshold;
                int n4 = this.origin;
                int n5 = this.fence;
                DoubleCumulateTask doubleCumulateTask = this;
                block0 : while ((n2 = doubleCumulateTask.lo) >= 0 && (n = doubleCumulateTask.hi) <= arrd.length) {
                    DoubleCumulateTask doubleCumulateTask2;
                    DoubleCumulateTask doubleCumulateTask3;
                    double d;
                    int n6;
                    int n7;
                    double d2;
                    DoubleCumulateTask doubleCumulateTask4;
                    if (n - n2 > n3) {
                        doubleCumulateTask4 = doubleCumulateTask.left;
                        DoubleCumulateTask doubleCumulateTask5 = doubleCumulateTask.right;
                        if (doubleCumulateTask4 == null) {
                            n6 = n2 + n >>> 1;
                            doubleCumulateTask.right = doubleCumulateTask2 = new DoubleCumulateTask(doubleCumulateTask, doubleBinaryOperator, arrd, n4, n5, n3, n6, n);
                            doubleCumulateTask.left = doubleCumulateTask4 = new DoubleCumulateTask(doubleCumulateTask, doubleBinaryOperator, arrd, n4, n5, n3, n2, n6);
                            doubleCumulateTask = doubleCumulateTask4;
                        } else {
                            doubleCumulateTask4.in = d = doubleCumulateTask.in;
                            doubleCumulateTask2 = null;
                            doubleCumulateTask3 = null;
                            doubleCumulateTask = null;
                            if (doubleCumulateTask5 != null) {
                                d2 = doubleCumulateTask4.out;
                                if (n2 != n4) {
                                    d2 = doubleBinaryOperator.applyAsDouble(d, d2);
                                }
                                doubleCumulateTask5.in = d2;
                                while (((n6 = doubleCumulateTask5.getPendingCount()) & 1) == 0) {
                                    if (!doubleCumulateTask5.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                    doubleCumulateTask = doubleCumulateTask5;
                                    break;
                                }
                            }
                            while (((n6 = doubleCumulateTask4.getPendingCount()) & 1) == 0) {
                                if (!doubleCumulateTask4.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                doubleCumulateTask2 = doubleCumulateTask3;
                                if (doubleCumulateTask != null) {
                                    doubleCumulateTask2 = doubleCumulateTask;
                                }
                                doubleCumulateTask = doubleCumulateTask4;
                                break;
                            }
                            if (doubleCumulateTask == null) break;
                        }
                        if (doubleCumulateTask2 == null) continue;
                        doubleCumulateTask2.fork();
                        continue;
                    }
                    while (((n7 = doubleCumulateTask.getPendingCount()) & 4) == 0) {
                        n6 = (n7 & 1) != 0 ? 4 : (n2 > n4 ? 2 : 6);
                        if (!doubleCumulateTask.compareAndSetPendingCount(n7, n7 | n6)) continue;
                        if (n6 != 2) {
                            if (n2 == n4) {
                                d2 = arrd[n4];
                                n2 = n4 + 1;
                            } else {
                                d2 = doubleCumulateTask.in;
                            }
                            while (n2 < n) {
                                d2 = d = doubleBinaryOperator.applyAsDouble(d2, arrd[n2]);
                                arrd[n2] = d;
                                ++n2;
                            }
                        } else if (n < n5) {
                            d2 = arrd[n2];
                            ++n2;
                            while (n2 < n) {
                                d2 = doubleBinaryOperator.applyAsDouble(d2, arrd[n2]);
                                ++n2;
                            }
                        } else {
                            d2 = doubleCumulateTask.in;
                        }
                        doubleCumulateTask.out = d2;
                        n2 = n3;
                        do {
                            if ((doubleCumulateTask2 = (DoubleCumulateTask)doubleCumulateTask.getCompleter()) == null) {
                                if ((n6 & 4) == 0) break block0;
                                doubleCumulateTask.quietlyComplete();
                                break block0;
                            }
                            n7 = doubleCumulateTask2.getPendingCount();
                            if ((n7 & n6 & 4) != 0) {
                                doubleCumulateTask = doubleCumulateTask2;
                                n3 = n6;
                            } else if ((n7 & n6 & 2) != 0) {
                                int n8;
                                doubleCumulateTask3 = doubleCumulateTask2.left;
                                if (doubleCumulateTask3 != null && (doubleCumulateTask4 = doubleCumulateTask2.right) != null) {
                                    d = doubleCumulateTask3.out;
                                    if (doubleCumulateTask4.hi != n5) {
                                        d = doubleBinaryOperator.applyAsDouble(d, doubleCumulateTask4.out);
                                    }
                                    doubleCumulateTask2.out = d;
                                }
                                if ((n8 = n7 | n6 | (n3 = (n7 & 1) == 0 && doubleCumulateTask2.lo == n4 ? 1 : 0)) == n7 || doubleCumulateTask2.compareAndSetPendingCount(n7, n8)) {
                                    n7 = 2;
                                    doubleCumulateTask4 = doubleCumulateTask2;
                                    n6 = n7;
                                    doubleCumulateTask = doubleCumulateTask4;
                                    if (n3 != 0) {
                                        doubleCumulateTask2.fork();
                                        doubleCumulateTask = doubleCumulateTask4;
                                        n6 = n7;
                                    }
                                }
                                n3 = n6;
                            } else {
                                n3 = n6;
                                if (doubleCumulateTask2.compareAndSetPendingCount(n7, n7 | n6)) break block0;
                            }
                            n6 = n3;
                        } while (true);
                    }
                    break block0;
                }
                return;
            }
            throw new NullPointerException();
        }
    }

    static final class IntCumulateTask
    extends CountedCompleter<Void> {
        private static final long serialVersionUID = 3731755594596840961L;
        final int[] array;
        final int fence;
        final IntBinaryOperator function;
        final int hi;
        int in;
        IntCumulateTask left;
        final int lo;
        final int origin;
        int out;
        IntCumulateTask right;
        final int threshold;

        public IntCumulateTask(IntCumulateTask intCumulateTask, IntBinaryOperator intBinaryOperator, int[] arrn, int n, int n2) {
            super(intCumulateTask);
            this.function = intBinaryOperator;
            this.array = arrn;
            this.origin = n;
            this.lo = n;
            this.fence = n2;
            this.hi = n2;
            n = (n2 - n) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            n2 = 16;
            if (n <= 16) {
                n = n2;
            }
            this.threshold = n;
        }

        IntCumulateTask(IntCumulateTask intCumulateTask, IntBinaryOperator intBinaryOperator, int[] arrn, int n, int n2, int n3, int n4, int n5) {
            super(intCumulateTask);
            this.function = intBinaryOperator;
            this.array = arrn;
            this.origin = n;
            this.fence = n2;
            this.threshold = n3;
            this.lo = n4;
            this.hi = n5;
        }

        @Override
        public final void compute() {
            int[] arrn;
            IntBinaryOperator intBinaryOperator = this.function;
            if (intBinaryOperator != null && (arrn = this.array) != null) {
                int n;
                int n2;
                int n3 = this.threshold;
                int n4 = this.origin;
                int n5 = this.fence;
                IntCumulateTask intCumulateTask = this;
                block0 : while ((n2 = intCumulateTask.lo) >= 0 && (n = intCumulateTask.hi) <= arrn.length) {
                    IntCumulateTask intCumulateTask2;
                    IntCumulateTask intCumulateTask3;
                    int n6;
                    IntCumulateTask intCumulateTask4;
                    if (n - n2 > n3) {
                        intCumulateTask4 = intCumulateTask.left;
                        IntCumulateTask intCumulateTask5 = intCumulateTask.right;
                        if (intCumulateTask4 == null) {
                            n6 = n2 + n >>> 1;
                            intCumulateTask.right = intCumulateTask2 = new IntCumulateTask(intCumulateTask, intBinaryOperator, arrn, n4, n5, n3, n6, n);
                            intCumulateTask.left = intCumulateTask4 = new IntCumulateTask(intCumulateTask, intBinaryOperator, arrn, n4, n5, n3, n2, n6);
                            intCumulateTask = intCumulateTask4;
                        } else {
                            intCumulateTask4.in = n = intCumulateTask.in;
                            intCumulateTask2 = null;
                            intCumulateTask3 = null;
                            intCumulateTask = null;
                            if (intCumulateTask5 != null) {
                                n6 = intCumulateTask4.out;
                                if (n2 != n4) {
                                    n6 = intBinaryOperator.applyAsInt(n, n6);
                                }
                                intCumulateTask5.in = n6;
                                while (((n6 = intCumulateTask5.getPendingCount()) & 1) == 0) {
                                    if (!intCumulateTask5.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                    intCumulateTask = intCumulateTask5;
                                    break;
                                }
                            }
                            while (((n6 = intCumulateTask4.getPendingCount()) & 1) == 0) {
                                if (!intCumulateTask4.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                intCumulateTask2 = intCumulateTask3;
                                if (intCumulateTask != null) {
                                    intCumulateTask2 = intCumulateTask;
                                }
                                intCumulateTask = intCumulateTask4;
                                break;
                            }
                            if (intCumulateTask == null) break;
                        }
                        if (intCumulateTask2 == null) continue;
                        intCumulateTask2.fork();
                        continue;
                    }
                    while (((n3 = intCumulateTask.getPendingCount()) & 4) == 0) {
                        n6 = (n3 & 1) != 0 ? 4 : (n2 > n4 ? 2 : 6);
                        if (!intCumulateTask.compareAndSetPendingCount(n3, n3 | n6)) continue;
                        if (n6 != 2) {
                            if (n2 == n4) {
                                n3 = arrn[n4];
                                n2 = n4 + 1;
                            } else {
                                n3 = intCumulateTask.in;
                            }
                            while (n2 < n) {
                                int n7;
                                n3 = n7 = intBinaryOperator.applyAsInt(n3, arrn[n2]);
                                arrn[n2] = n7;
                                ++n2;
                            }
                        } else if (n < n5) {
                            n3 = arrn[n2];
                            ++n2;
                            while (n2 < n) {
                                n3 = intBinaryOperator.applyAsInt(n3, arrn[n2]);
                                ++n2;
                            }
                        } else {
                            n3 = intCumulateTask.in;
                        }
                        intCumulateTask.out = n3;
                        do {
                            if ((intCumulateTask2 = (IntCumulateTask)intCumulateTask.getCompleter()) == null) {
                                if ((n6 & 4) == 0) break block0;
                                intCumulateTask.quietlyComplete();
                                break block0;
                            }
                            n2 = intCumulateTask2.getPendingCount();
                            if ((n2 & n6 & 4) != 0) {
                                intCumulateTask = intCumulateTask2;
                                n3 = n6;
                            } else if ((n2 & n6 & 2) != 0) {
                                intCumulateTask3 = intCumulateTask2.left;
                                if (intCumulateTask3 != null && (intCumulateTask4 = intCumulateTask2.right) != null) {
                                    n3 = intCumulateTask3.out;
                                    if (intCumulateTask4.hi != n5) {
                                        n3 = intBinaryOperator.applyAsInt(n3, intCumulateTask4.out);
                                    }
                                    intCumulateTask2.out = n3;
                                }
                                if ((n = n2 | n6 | (n3 = (n2 & 1) == 0 && intCumulateTask2.lo == n4 ? 1 : 0)) == n2 || intCumulateTask2.compareAndSetPendingCount(n2, n)) {
                                    n2 = 2;
                                    intCumulateTask4 = intCumulateTask2;
                                    n6 = n2;
                                    intCumulateTask = intCumulateTask4;
                                    if (n3 != 0) {
                                        intCumulateTask2.fork();
                                        intCumulateTask = intCumulateTask4;
                                        n6 = n2;
                                    }
                                }
                                n3 = n6;
                            } else {
                                n3 = n6;
                                if (intCumulateTask2.compareAndSetPendingCount(n2, n2 | n6)) break block0;
                            }
                            n6 = n3;
                        } while (true);
                    }
                    break block0;
                }
                return;
            }
            throw new NullPointerException();
        }
    }

    static final class LongCumulateTask
    extends CountedCompleter<Void> {
        private static final long serialVersionUID = -5074099945909284273L;
        final long[] array;
        final int fence;
        final LongBinaryOperator function;
        final int hi;
        long in;
        LongCumulateTask left;
        final int lo;
        final int origin;
        long out;
        LongCumulateTask right;
        final int threshold;

        public LongCumulateTask(LongCumulateTask longCumulateTask, LongBinaryOperator longBinaryOperator, long[] arrl, int n, int n2) {
            super(longCumulateTask);
            this.function = longBinaryOperator;
            this.array = arrl;
            this.origin = n;
            this.lo = n;
            this.fence = n2;
            this.hi = n2;
            n2 = (n2 - n) / (ForkJoinPool.getCommonPoolParallelism() << 3);
            n = 16;
            if (n2 > 16) {
                n = n2;
            }
            this.threshold = n;
        }

        LongCumulateTask(LongCumulateTask longCumulateTask, LongBinaryOperator longBinaryOperator, long[] arrl, int n, int n2, int n3, int n4, int n5) {
            super(longCumulateTask);
            this.function = longBinaryOperator;
            this.array = arrl;
            this.origin = n;
            this.fence = n2;
            this.threshold = n3;
            this.lo = n4;
            this.hi = n5;
        }

        @Override
        public final void compute() {
            long[] arrl;
            LongBinaryOperator longBinaryOperator = this.function;
            if (longBinaryOperator != null && (arrl = this.array) != null) {
                int n;
                int n2;
                int n3 = this.threshold;
                int n4 = this.origin;
                int n5 = this.fence;
                LongCumulateTask longCumulateTask = this;
                block0 : while ((n2 = longCumulateTask.lo) >= 0 && (n = longCumulateTask.hi) <= arrl.length) {
                    long l;
                    LongCumulateTask longCumulateTask2;
                    int n6;
                    LongCumulateTask longCumulateTask3;
                    int n7;
                    LongCumulateTask longCumulateTask4;
                    long l2;
                    if (n - n2 > n3) {
                        longCumulateTask2 = longCumulateTask.left;
                        LongCumulateTask longCumulateTask5 = longCumulateTask.right;
                        if (longCumulateTask2 == null) {
                            n6 = n2 + n >>> 1;
                            longCumulateTask.right = longCumulateTask3 = new LongCumulateTask(longCumulateTask, longBinaryOperator, arrl, n4, n5, n3, n6, n);
                            longCumulateTask.left = longCumulateTask2 = new LongCumulateTask(longCumulateTask, longBinaryOperator, arrl, n4, n5, n3, n2, n6);
                            longCumulateTask = longCumulateTask2;
                        } else {
                            longCumulateTask2.in = l2 = longCumulateTask.in;
                            longCumulateTask3 = null;
                            longCumulateTask4 = null;
                            longCumulateTask = null;
                            if (longCumulateTask5 != null) {
                                l = longCumulateTask2.out;
                                if (n2 != n4) {
                                    l = longBinaryOperator.applyAsLong(l2, l);
                                }
                                longCumulateTask5.in = l;
                                while (((n6 = longCumulateTask5.getPendingCount()) & 1) == 0) {
                                    if (!longCumulateTask5.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                    longCumulateTask = longCumulateTask5;
                                    break;
                                }
                            }
                            while (((n6 = longCumulateTask2.getPendingCount()) & 1) == 0) {
                                if (!longCumulateTask2.compareAndSetPendingCount(n6, n6 | 1)) continue;
                                longCumulateTask3 = longCumulateTask4;
                                if (longCumulateTask != null) {
                                    longCumulateTask3 = longCumulateTask;
                                }
                                longCumulateTask = longCumulateTask2;
                                break;
                            }
                            if (longCumulateTask == null) break;
                        }
                        if (longCumulateTask3 == null) continue;
                        longCumulateTask3.fork();
                        continue;
                    }
                    while (((n7 = longCumulateTask.getPendingCount()) & 4) == 0) {
                        n6 = (n7 & 1) != 0 ? 4 : (n2 > n4 ? 2 : 6);
                        if (!longCumulateTask.compareAndSetPendingCount(n7, n7 | n6)) continue;
                        if (n6 != 2) {
                            if (n2 == n4) {
                                l = arrl[n4];
                                n2 = n4 + 1;
                            } else {
                                l = longCumulateTask.in;
                            }
                            while (n2 < n) {
                                l = l2 = longBinaryOperator.applyAsLong(l, arrl[n2]);
                                arrl[n2] = l2;
                                ++n2;
                            }
                        } else if (n < n5) {
                            l = arrl[n2];
                            ++n2;
                            while (n2 < n) {
                                l = longBinaryOperator.applyAsLong(l, arrl[n2]);
                                ++n2;
                            }
                        } else {
                            l = longCumulateTask.in;
                        }
                        longCumulateTask.out = l;
                        n2 = n3;
                        do {
                            if ((longCumulateTask3 = (LongCumulateTask)longCumulateTask.getCompleter()) == null) {
                                if ((n6 & 4) == 0) break block0;
                                longCumulateTask.quietlyComplete();
                                break block0;
                            }
                            n7 = longCumulateTask3.getPendingCount();
                            if ((n7 & n6 & 4) != 0) {
                                longCumulateTask = longCumulateTask3;
                                n3 = n6;
                            } else if ((n7 & n6 & 2) != 0) {
                                int n8;
                                longCumulateTask2 = longCumulateTask3.left;
                                if (longCumulateTask2 != null && (longCumulateTask4 = longCumulateTask3.right) != null) {
                                    l2 = longCumulateTask2.out;
                                    if (longCumulateTask4.hi != n5) {
                                        l2 = longBinaryOperator.applyAsLong(l2, longCumulateTask4.out);
                                    }
                                    longCumulateTask3.out = l2;
                                }
                                if ((n8 = n7 | n6 | (n3 = (n7 & 1) == 0 && longCumulateTask3.lo == n4 ? 1 : 0)) == n7 || longCumulateTask3.compareAndSetPendingCount(n7, n8)) {
                                    n7 = 2;
                                    longCumulateTask2 = longCumulateTask3;
                                    n6 = n7;
                                    longCumulateTask = longCumulateTask2;
                                    if (n3 != 0) {
                                        longCumulateTask3.fork();
                                        longCumulateTask = longCumulateTask2;
                                        n6 = n7;
                                    }
                                }
                                n3 = n6;
                            } else {
                                n3 = n6;
                                if (longCumulateTask3.compareAndSetPendingCount(n7, n7 | n6)) break block0;
                            }
                            n6 = n3;
                        } while (true);
                    }
                    break block0;
                }
                return;
            }
            throw new NullPointerException();
        }
    }

}

