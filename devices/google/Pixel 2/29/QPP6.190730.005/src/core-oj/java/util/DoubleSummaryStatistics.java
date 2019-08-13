/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.function.DoubleConsumer;

public class DoubleSummaryStatistics
implements DoubleConsumer {
    private long count;
    private double max = Double.NEGATIVE_INFINITY;
    private double min = Double.POSITIVE_INFINITY;
    private double simpleSum;
    private double sum;
    private double sumCompensation;

    private void sumWithCompensation(double d) {
        double d2 = d - this.sumCompensation;
        d = this.sum;
        double d3 = d + d2;
        this.sumCompensation = d3 - d - d2;
        this.sum = d3;
    }

    @Override
    public void accept(double d) {
        ++this.count;
        this.simpleSum += d;
        this.sumWithCompensation(d);
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    public void combine(DoubleSummaryStatistics doubleSummaryStatistics) {
        this.count += doubleSummaryStatistics.count;
        this.simpleSum += doubleSummaryStatistics.simpleSum;
        this.sumWithCompensation(doubleSummaryStatistics.sum);
        this.sumWithCompensation(doubleSummaryStatistics.sumCompensation);
        this.min = Math.min(this.min, doubleSummaryStatistics.min);
        this.max = Math.max(this.max, doubleSummaryStatistics.max);
    }

    public final double getAverage() {
        double d = this.getCount() > 0L ? this.getSum() / (double)this.getCount() : 0.0;
        return d;
    }

    public final long getCount() {
        return this.count;
    }

    public final double getMax() {
        return this.max;
    }

    public final double getMin() {
        return this.min;
    }

    public final double getSum() {
        double d = this.sum + this.sumCompensation;
        if (Double.isNaN(d) && Double.isInfinite(this.simpleSum)) {
            return this.simpleSum;
        }
        return d;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", this.getClass().getSimpleName(), this.getCount(), this.getSum(), this.getMin(), this.getAverage(), this.getMax());
    }
}

