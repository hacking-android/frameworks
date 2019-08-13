/*
 * Decompiled with CFR 0.145.
 */
package android.test;

@Deprecated
public interface PerformanceTestCase {
    public boolean isPerformanceOnly();

    public int startPerformance(Intermediates var1);

    public static interface Intermediates {
        public void addIntermediate(String var1);

        public void addIntermediate(String var1, long var2);

        public void finishTiming(boolean var1);

        public void setInternalIterations(int var1);

        public void startTiming(boolean var1);
    }

}

