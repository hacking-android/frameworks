/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

public interface ValueIterator {
    public boolean next(Element var1);

    public void reset();

    public void setRange(int var1, int var2);

    public static final class Element {
        public int integer;
        public Object value;
    }

}

