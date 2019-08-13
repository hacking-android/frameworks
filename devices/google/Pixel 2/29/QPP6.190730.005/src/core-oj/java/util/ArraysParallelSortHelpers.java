/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Comparator;
import java.util.DualPivotQuicksort;
import java.util.TimSort;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinTask;

class ArraysParallelSortHelpers {
    ArraysParallelSortHelpers() {
    }

    static final class EmptyCompleter
    extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;

        EmptyCompleter(CountedCompleter<?> countedCompleter) {
            super(countedCompleter);
        }

        @Override
        public final void compute() {
        }
    }

    static final class FJByte {
        FJByte() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final byte[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final byte[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, byte[] arrby, byte[] arrby2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrby;
                this.w = arrby2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    byte[] arrby = this.a;
                    byte[] arrby2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrby == null || arrby2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            byte by;
                            int n9;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        by = arrby[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (by <= arrby[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n9 = n + n2;
                                n10 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n9 && n2 < n10) {
                                    n7 = arrby[n8];
                                    n = arrby[n2];
                                    if (n7 <= n) {
                                        ++n8;
                                    } else {
                                        ++n2;
                                        n7 = n;
                                    }
                                    arrby2[n4] = (byte)n7;
                                    ++n4;
                                }
                                if (n2 < n10) {
                                    System.arraycopy(arrby, n2, arrby2, n4, n10 - n2);
                                } else if (n8 < n9) {
                                    System.arraycopy(arrby, n8, arrby2, n4, n9 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            by = arrby[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (by <= arrby[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrby, arrby2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final byte[] a;
            final int base;
            final int gran;
            final int size;
            final byte[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, byte[] arrby, byte[] arrby2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrby;
                this.w = arrby2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                byte[] arrby = this.a;
                byte[] arrby2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    Relay relay = new Relay(new Merger(countedCompleter, arrby2, arrby, n3, n5, n3 + n5, n2 - n5, n, n4));
                    countedCompleter = new Relay(new Merger(relay, arrby, arrby2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(countedCompleter, arrby, arrby2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(countedCompleter, arrby, arrby2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(relay, arrby, arrby2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrby, arrby2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrby, n, n + n2 - 1);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJChar {
        FJChar() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final char[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final char[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, char[] arrc, char[] arrc2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrc;
                this.w = arrc2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    char[] arrc = this.a;
                    char[] arrc2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrc == null || arrc2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            int n9;
                            char c;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        c = arrc[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (c <= arrc[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n9 = n + n2;
                                n10 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n9 && n2 < n10) {
                                    n7 = arrc[n8];
                                    n = arrc[n2];
                                    if (n7 <= n) {
                                        ++n8;
                                    } else {
                                        ++n2;
                                        n7 = n;
                                    }
                                    arrc2[n4] = (char)n7;
                                    ++n4;
                                }
                                if (n2 < n10) {
                                    System.arraycopy((Object)arrc, n2, (Object)arrc2, n4, n10 - n2);
                                } else if (n8 < n9) {
                                    System.arraycopy((Object)arrc, n8, (Object)arrc2, n4, n9 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            c = arrc[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (c <= arrc[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrc, arrc2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final char[] a;
            final int base;
            final int gran;
            final int size;
            final char[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, char[] arrc, char[] arrc2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrc;
                this.w = arrc2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                char[] arrc = this.a;
                char[] arrc2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    countedCompleter = new Relay(new Merger(countedCompleter, arrc2, arrc, n3, n5, n3 + n5, n2 - n5, n, n4));
                    Relay relay = new Relay(new Merger(countedCompleter, arrc, arrc2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(relay, arrc, arrc2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(relay, arrc, arrc2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(countedCompleter, arrc, arrc2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrc, arrc2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrc, n, n + n2 - 1, arrc2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJDouble {
        FJDouble() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final double[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final double[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, double[] arrd, double[] arrd2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrd;
                this.w = arrd2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    double[] arrd = this.a;
                    double[] arrd2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrd == null || arrd2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            double d;
                            int n9;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        d = arrd[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (d <= arrd[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n7 = n + n2;
                                n9 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n7 && n2 < n9) {
                                    d = arrd[n8];
                                    double d2 = arrd[n2];
                                    if (d <= d2) {
                                        ++n8;
                                    } else {
                                        ++n2;
                                        d = d2;
                                    }
                                    arrd2[n4] = d;
                                    ++n4;
                                }
                                if (n2 < n9) {
                                    System.arraycopy((Object)arrd, n2, (Object)arrd2, n4, n9 - n2);
                                } else if (n8 < n7) {
                                    System.arraycopy((Object)arrd, n8, (Object)arrd2, n4, n7 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            d = arrd[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (d <= arrd[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrd, arrd2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final double[] a;
            final int base;
            final int gran;
            final int size;
            final double[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, double[] arrd, double[] arrd2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrd;
                this.w = arrd2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                double[] arrd = this.a;
                double[] arrd2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    Relay relay = new Relay(new Merger(countedCompleter, arrd2, arrd, n3, n5, n3 + n5, n2 - n5, n, n4));
                    countedCompleter = new Relay(new Merger(relay, arrd, arrd2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(countedCompleter, arrd, arrd2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(countedCompleter, arrd, arrd2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(relay, arrd, arrd2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrd, arrd2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrd, n, n + n2 - 1, arrd2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJFloat {
        FJFloat() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final float[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final float[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, float[] arrf, float[] arrf2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrf;
                this.w = arrf2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    float[] arrf = this.a;
                    float[] arrf2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrf == null || arrf2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            int n9;
                            float f;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        f = arrf[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (f <= arrf[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n7 = n + n2;
                                n9 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n7 && n2 < n9) {
                                    f = arrf[n8];
                                    float f2 = arrf[n2];
                                    if (f <= f2) {
                                        ++n8;
                                    } else {
                                        ++n2;
                                        f = f2;
                                    }
                                    arrf2[n4] = f;
                                    ++n4;
                                }
                                if (n2 < n9) {
                                    System.arraycopy((Object)arrf, n2, (Object)arrf2, n4, n9 - n2);
                                } else if (n8 < n7) {
                                    System.arraycopy((Object)arrf, n8, (Object)arrf2, n4, n7 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            f = arrf[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (f <= arrf[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrf, arrf2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final float[] a;
            final int base;
            final int gran;
            final int size;
            final float[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, float[] arrf, float[] arrf2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrf;
                this.w = arrf2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                float[] arrf = this.a;
                float[] arrf2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    Relay relay = new Relay(new Merger(countedCompleter, arrf2, arrf, n3, n5, n3 + n5, n2 - n5, n, n4));
                    countedCompleter = new Relay(new Merger(relay, arrf, arrf2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(countedCompleter, arrf, arrf2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(countedCompleter, arrf, arrf2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(relay, arrf, arrf2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrf, arrf2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrf, n, n + n2 - 1, arrf2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJInt {
        FJInt() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final int[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final int[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, int[] arrn, int[] arrn2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrn;
                this.w = arrn2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    int[] arrn = this.a;
                    int[] arrn2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrn == null || arrn2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            int n9;
                            int n10;
                            int n11;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n10 = n2 >>> 1;
                                        n9 = arrn[n10 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n11 = n7 + n8 >>> 1;
                                            if (n9 <= arrn[n11 + n3]) {
                                                n8 = n11;
                                                continue;
                                            }
                                            n7 = n11 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n10;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n10 = n + n2;
                                n11 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n10 && n2 < n11) {
                                    n = arrn[n8];
                                    n7 = arrn[n2];
                                    if (n <= n7) {
                                        ++n8;
                                        n7 = n;
                                    } else {
                                        ++n2;
                                    }
                                    arrn2[n4] = n7;
                                    ++n4;
                                }
                                if (n2 < n11) {
                                    System.arraycopy((Object)arrn, n2, (Object)arrn2, n4, n11 - n2);
                                } else if (n8 < n10) {
                                    System.arraycopy((Object)arrn, n8, (Object)arrn2, n4, n10 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n10 = n4 >>> 1;
                            n9 = arrn[n10 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n11 = n7 + n8 >>> 1;
                                if (n9 <= arrn[n11 + n]) {
                                    n8 = n11;
                                    continue;
                                }
                                n7 = n11 + 1;
                            }
                            n7 = n10;
                        }
                        Merger merger = new Merger(this, arrn, arrn2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final int[] a;
            final int base;
            final int gran;
            final int size;
            final int[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, int[] arrn, int[] arrn2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrn;
                this.w = arrn2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                int[] arrn = this.a;
                int[] arrn2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    countedCompleter = new Relay(new Merger(countedCompleter, arrn2, arrn, n3, n5, n3 + n5, n2 - n5, n, n4));
                    Relay relay = new Relay(new Merger(countedCompleter, arrn, arrn2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(relay, arrn, arrn2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(relay, arrn, arrn2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(countedCompleter, arrn, arrn2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrn, arrn2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrn, n, n + n2 - 1, arrn2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJLong {
        FJLong() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final long[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final long[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, long[] arrl, long[] arrl2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrl;
                this.w = arrl2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    long[] arrl = this.a;
                    long[] arrl2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrl == null || arrl2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            long l;
                            int n9;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        l = arrl[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (l <= arrl[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n7 = n + n2;
                                n9 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n7 && n2 < n9) {
                                    l = arrl[n8];
                                    long l2 = arrl[n2];
                                    if (l <= l2) {
                                        ++n8;
                                    } else {
                                        ++n2;
                                        l = l2;
                                    }
                                    arrl2[n4] = l;
                                    ++n4;
                                }
                                if (n2 < n9) {
                                    System.arraycopy((Object)arrl, n2, (Object)arrl2, n4, n9 - n2);
                                } else if (n8 < n7) {
                                    System.arraycopy((Object)arrl, n8, (Object)arrl2, n4, n7 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            l = arrl[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (l <= arrl[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrl, arrl2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final long[] a;
            final int base;
            final int gran;
            final int size;
            final long[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, long[] arrl, long[] arrl2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrl;
                this.w = arrl2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                long[] arrl = this.a;
                long[] arrl2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    countedCompleter = new Relay(new Merger(countedCompleter, arrl2, arrl, n3, n5, n3 + n5, n2 - n5, n, n4));
                    Relay relay = new Relay(new Merger(countedCompleter, arrl, arrl2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(relay, arrl, arrl2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(relay, arrl, arrl2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(countedCompleter, arrl, arrl2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrl, arrl2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrl, n, n + n2 - 1, arrl2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJObject {
        FJObject() {
        }

        static final class Merger<T>
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a;
            Comparator<? super T> comparator;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final T[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, T[] arrT, T[] arrT2, int n, int n2, int n3, int n4, int n5, int n6, Comparator<? super T> comparator) {
                super(countedCompleter);
                this.a = arrT;
                this.w = arrT2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
                this.comparator = comparator;
            }

            @Override
            public final void compute() {
                block14 : {
                    Comparator<T> comparator = this.comparator;
                    T[] arrT = this.a;
                    T[] arrT2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrT == null || arrT2 == null || n < 0 || n3 < 0 || n5 < 0 || comparator == null) break block14;
                    do {
                        Object object;
                        int n7;
                        int n8;
                        block17 : {
                            int n9;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n7 = n4;
                                        n9 = n2 >>> 1;
                                        object = arrT[n9 + n];
                                        n8 = 0;
                                        while (n8 < n7) {
                                            n10 = n8 + n7 >>> 1;
                                            if (comparator.compare(object, arrT[n10 + n3]) <= 0) {
                                                n7 = n10;
                                                continue;
                                            }
                                            n8 = n10 + 1;
                                        }
                                        n8 = n7;
                                        n7 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n7 = n + n2;
                                n8 = n3 + n4;
                                n2 = n;
                                n4 = n3;
                                while (n2 < n7 && n4 < n8) {
                                    object = arrT[n2];
                                    T t = arrT[n4];
                                    if (comparator.compare(object, t) <= 0) {
                                        n3 = n2 + 1;
                                    } else {
                                        ++n4;
                                        object = t;
                                        n3 = n2;
                                    }
                                    arrT2[n5] = object;
                                    ++n5;
                                    n2 = n3;
                                }
                                if (n4 < n8) {
                                    System.arraycopy(arrT, n4, arrT2, n5, n8 - n4);
                                } else if (n2 < n7) {
                                    System.arraycopy(arrT, n2, arrT2, n5, n7 - n2);
                                }
                                this.tryComplete();
                                return;
                            }
                            n7 = n2;
                            n9 = n4 >>> 1;
                            object = arrT[n9 + n3];
                            n8 = 0;
                            while (n8 < n7) {
                                n10 = n8 + n7 >>> 1;
                                if (comparator.compare(object, arrT[n10 + n]) <= 0) {
                                    n7 = n10;
                                    continue;
                                }
                                n8 = n10 + 1;
                            }
                            n8 = n9;
                        }
                        object = new Merger<T>(this, arrT, arrT2, n + n7, n2 - n7, n3 + n8, n4 - n8, n5 + n7 + n8, n6, comparator);
                        n4 = n8;
                        n2 = n7;
                        this.addToPendingCount(1);
                        ((ForkJoinTask)object).fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter<T>
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a;
            final int base;
            Comparator<? super T> comparator;
            final int gran;
            final int size;
            final T[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, T[] arrT, T[] arrT2, int n, int n2, int n3, int n4, Comparator<? super T> comparator) {
                super(countedCompleter);
                this.a = arrT;
                this.w = arrT2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
                this.comparator = comparator;
            }

            @Override
            public final void compute() {
                Comparator<? super T> comparator = this.comparator;
                T[] arrT = this.a;
                T[] arrT2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    countedCompleter = new Relay(new Merger<T>(countedCompleter, arrT2, arrT, n3, n5, n3 + n5, n2 - n5, n, n4, comparator));
                    Relay relay = new Relay(new Merger<T>(countedCompleter, arrT, arrT2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4, comparator));
                    new Sorter<T>(relay, arrT, arrT2, n + n7, n2 - n7, n3 + n7, n4, comparator).fork();
                    new Sorter<T>(relay, arrT, arrT2, n + n5, n6, n3 + n5, n4, comparator).fork();
                    countedCompleter = new Relay(new Merger<T>(countedCompleter, arrT, arrT2, n, n6, n + n6, n5 - n6, n3, n4, comparator));
                    new Sorter<T>(countedCompleter, arrT, arrT2, n + n6, n5 - n6, n3 + n6, n4, comparator).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                TimSort.sort(arrT, n, n + n2, comparator, arrT2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class FJShort {
        FJShort() {
        }

        static final class Merger
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final short[] a;
            final int gran;
            final int lbase;
            final int lsize;
            final int rbase;
            final int rsize;
            final short[] w;
            final int wbase;

            Merger(CountedCompleter<?> countedCompleter, short[] arrs, short[] arrs2, int n, int n2, int n3, int n4, int n5, int n6) {
                super(countedCompleter);
                this.a = arrs;
                this.w = arrs2;
                this.lbase = n;
                this.lsize = n2;
                this.rbase = n3;
                this.rsize = n4;
                this.wbase = n5;
                this.gran = n6;
            }

            @Override
            public final void compute() {
                block14 : {
                    short[] arrs = this.a;
                    short[] arrs2 = this.w;
                    int n = this.lbase;
                    int n2 = this.lsize;
                    int n3 = this.rbase;
                    int n4 = this.rsize;
                    int n5 = this.wbase;
                    int n6 = this.gran;
                    if (arrs == null || arrs2 == null || n < 0 || n3 < 0 || n5 < 0) break block14;
                    do {
                        int n7;
                        int n8;
                        block17 : {
                            int n9;
                            short s;
                            int n10;
                            block18 : {
                                block16 : {
                                    block15 : {
                                        if (n2 < n4) break block15;
                                        if (n2 <= n6) break block16;
                                        n8 = n4;
                                        n9 = n2 >>> 1;
                                        s = arrs[n9 + n];
                                        n7 = 0;
                                        while (n7 < n8) {
                                            n10 = n7 + n8 >>> 1;
                                            if (s <= arrs[n10 + n3]) {
                                                n8 = n10;
                                                continue;
                                            }
                                            n7 = n10 + 1;
                                        }
                                        n7 = n8;
                                        n8 = n9;
                                        break block17;
                                    }
                                    if (n4 > n6) break block18;
                                }
                                n9 = n + n2;
                                n10 = n3 + n4;
                                n4 = n5;
                                n2 = n3;
                                n8 = n;
                                while (n8 < n9 && n2 < n10) {
                                    n = arrs[n8];
                                    n7 = arrs[n2];
                                    if (n <= n7) {
                                        ++n8;
                                        n7 = n;
                                    } else {
                                        ++n2;
                                    }
                                    arrs2[n4] = (short)n7;
                                    ++n4;
                                }
                                if (n2 < n10) {
                                    System.arraycopy((Object)arrs, n2, (Object)arrs2, n4, n10 - n2);
                                } else if (n8 < n9) {
                                    System.arraycopy((Object)arrs, n8, (Object)arrs2, n4, n9 - n8);
                                }
                                this.tryComplete();
                                return;
                            }
                            n8 = n2;
                            n9 = n4 >>> 1;
                            s = arrs[n9 + n3];
                            n7 = 0;
                            while (n7 < n8) {
                                n10 = n7 + n8 >>> 1;
                                if (s <= arrs[n10 + n]) {
                                    n8 = n10;
                                    continue;
                                }
                                n7 = n10 + 1;
                            }
                            n7 = n9;
                        }
                        Merger merger = new Merger(this, arrs, arrs2, n + n8, n2 - n8, n3 + n7, n4 - n7, n5 + n8 + n7, n6);
                        n4 = n7;
                        n2 = n8;
                        this.addToPendingCount(1);
                        merger.fork();
                    } while (true);
                }
                throw new IllegalStateException();
            }
        }

        static final class Sorter
        extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final short[] a;
            final int base;
            final int gran;
            final int size;
            final short[] w;
            final int wbase;

            Sorter(CountedCompleter<?> countedCompleter, short[] arrs, short[] arrs2, int n, int n2, int n3, int n4) {
                super(countedCompleter);
                this.a = arrs;
                this.w = arrs2;
                this.base = n;
                this.size = n2;
                this.wbase = n3;
                this.gran = n4;
            }

            @Override
            public final void compute() {
                short[] arrs = this.a;
                short[] arrs2 = this.w;
                int n = this.base;
                int n2 = this.size;
                int n3 = this.wbase;
                int n4 = this.gran;
                CountedCompleter countedCompleter = this;
                while (n2 > n4) {
                    int n5 = n2 >>> 1;
                    int n6 = n5 >>> 1;
                    int n7 = n5 + n6;
                    countedCompleter = new Relay(new Merger(countedCompleter, arrs2, arrs, n3, n5, n3 + n5, n2 - n5, n, n4));
                    Relay relay = new Relay(new Merger(countedCompleter, arrs, arrs2, n + n5, n6, n + n7, n2 - n7, n3 + n5, n4));
                    new Sorter(relay, arrs, arrs2, n + n7, n2 - n7, n3 + n7, n4).fork();
                    new Sorter(relay, arrs, arrs2, n + n5, n6, n3 + n5, n4).fork();
                    countedCompleter = new Relay(new Merger(countedCompleter, arrs, arrs2, n, n6, n + n6, n5 - n6, n3, n4));
                    new Sorter(countedCompleter, arrs, arrs2, n + n6, n5 - n6, n3 + n6, n4).fork();
                    countedCompleter = new EmptyCompleter(countedCompleter);
                    n2 = n6;
                }
                DualPivotQuicksort.sort(arrs, n, n + n2 - 1, arrs2, n3, n2);
                countedCompleter.tryComplete();
            }
        }

    }

    static final class Relay
    extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;
        final CountedCompleter<?> task;

        Relay(CountedCompleter<?> countedCompleter) {
            super(null, 1);
            this.task = countedCompleter;
        }

        @Override
        public final void compute() {
        }

        @Override
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            this.task.compute();
        }
    }

}

