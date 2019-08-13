/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public interface RangeValueIterator {
    public boolean next(Element var1);

    public void reset();

    public static class Element {
        public int limit;
        public int start;
        public int value;
    }

}

