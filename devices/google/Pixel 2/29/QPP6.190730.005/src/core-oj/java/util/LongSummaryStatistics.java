/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class LongSummaryStatistics
implements LongConsumer,
IntConsumer {
    private long count;
    private long max = Long.MIN_VALUE;
    private long min = Long.MAX_VALUE;
    private long sum;

    @Override
    public void accept(int n) {
        this.accept((long)n);
    }

    @Override
    public void accept(long l) {
        ++this.count;
        this.sum += l;
        this.min = Math.min(this.min, l);
        this.max = Math.max(this.max, l);
    }

    public void combine(LongSummaryStatistics longSummaryStatistics) {
        this.count += longSummaryStatistics.count;
        this.sum += longSummaryStatistics.sum;
        this.min = Math.min(this.min, longSummaryStatistics.min);
        this.max = Math.max(this.max, longSummaryStatistics.max);
    }

    public final double getAverage() {
        double d = this.getCount() > 0L ? (double)this.getSum() / (double)this.getCount() : 0.0;
        return d;
    }

    public final long getCount() {
        return this.count;
    }

    public final long getMax() {
        return this.max;
    }

    public final long getMin() {
        return this.min;
    }

    public final long getSum() {
        return this.sum;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", this.getClass().getSimpleName(), this.getCount(), this.getSum(), this.getMin(), this.getAverage(), this.getMax());
    }
}

