/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.UResourceBundle;
import android.icu.util.UResourceTypeMismatchException;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.NoSuchElementException;

public class UResourceBundleIterator {
    private UResourceBundle bundle;
    private int index = 0;
    private int size = 0;

    public UResourceBundleIterator(UResourceBundle uResourceBundle) {
        this.bundle = uResourceBundle;
        this.size = this.bundle.getSize();
    }

    @UnsupportedAppUsage
    public boolean hasNext() {
        boolean bl = this.index < this.size;
        return bl;
    }

    @UnsupportedAppUsage
    public UResourceBundle next() throws NoSuchElementException {
        int n = this.index;
        if (n < this.size) {
            UResourceBundle uResourceBundle = this.bundle;
            this.index = n + 1;
            return uResourceBundle.get(n);
        }
        throw new NoSuchElementException();
    }

    public String nextString() throws NoSuchElementException, UResourceTypeMismatchException {
        int n = this.index;
        if (n < this.size) {
            UResourceBundle uResourceBundle = this.bundle;
            this.index = n + 1;
            return uResourceBundle.getString(n);
        }
        throw new NoSuchElementException();
    }

    public void reset() {
        this.index = 0;
    }
}

