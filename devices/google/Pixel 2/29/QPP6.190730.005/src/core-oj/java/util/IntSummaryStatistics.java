/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.function.IntConsumer;

public class IntSummaryStatistics
implements IntConsumer {
    private long count;
    private int max = Integer.MIN_VALUE;
    private int min = Integer.MAX_VALUE;
    private long sum;

    @Override
    public void accept(int n) {
        ++this.count;
        this.sum += (long)n;
        this.min = Math.min(this.min, n);
        this.max = Math.max(this.max, n);
    }

    public void combine(IntSummaryStatistics intSummaryStatistics) {
        this.count += intSummaryStatistics.count;
        this.sum += intSummaryStatistics.sum;
        this.min = Math.min(this.min, intSummaryStatistics.min);
        this.max = Math.max(this.max, intSummaryStatistics.max);
    }

    public final double getAverage() {
        double d = this.getCount() > 0L ? (double)this.getSum() / (double)this.getCount() : 0.0;
        return d;
    }

    public final long getCount() {
        return this.count;
    }

    public final int getMax() {
        return this.max;
    }

    public final int getMin() {
        return this.min;
    }

    public final long getSum() {
        return this.sum;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", this.getClass().getSimpleName(), this.getCount(), this.getSum(), this.getMin(), this.getAverage(), this.getMax());
    }
}

