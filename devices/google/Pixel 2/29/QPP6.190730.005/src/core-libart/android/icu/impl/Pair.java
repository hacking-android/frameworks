/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public class Pair<F, S> {
    public final F first;
    public final S second;

    protected Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public static <F, S> Pair<F, S> of(F f, S s) {
        if (f != null && s != null) {
            return new Pair<F, S>(f, s);
        }
        throw new IllegalArgumentException("Pair.of requires non null values.");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Pair)) {
            return false;
        }
        object = (Pair)object;
        if (!this.first.equals(((Pair)object).first) || !this.second.equals(((Pair)object).second)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.first.hashCode() * 37 + this.second.hashCode();
    }
}

