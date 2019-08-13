/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.util.ArrayMap;
import android.util.LongSparseArray;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

abstract class ThemedResourceCache<T> {
    private LongSparseArray<WeakReference<T>> mNullThemedEntries;
    @UnsupportedAppUsage
    private ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> mThemedEntries;
    private LongSparseArray<WeakReference<T>> mUnthemedEntries;

    ThemedResourceCache() {
    }

    private LongSparseArray<WeakReference<T>> getThemedLocked(Resources.Theme longSparseArray, boolean bl) {
        Cloneable cloneable;
        if (longSparseArray == null) {
            if (this.mNullThemedEntries == null && bl) {
                this.mNullThemedEntries = new LongSparseArray(1);
            }
            return this.mNullThemedEntries;
        }
        if (this.mThemedEntries == null) {
            if (bl) {
                this.mThemedEntries = new ArrayMap(1);
            } else {
                return null;
            }
        }
        Resources.ThemeKey themeKey = ((Resources.Theme)((Object)longSparseArray)).getKey();
        longSparseArray = cloneable = this.mThemedEntries.get(themeKey);
        if (cloneable == null) {
            longSparseArray = cloneable;
            if (bl) {
                longSparseArray = new LongSparseArray(1);
                cloneable = themeKey.clone();
                this.mThemedEntries.put((Resources.ThemeKey)cloneable, longSparseArray);
            }
        }
        return longSparseArray;
    }

    private LongSparseArray<WeakReference<T>> getUnthemedLocked(boolean bl) {
        if (this.mUnthemedEntries == null && bl) {
            this.mUnthemedEntries = new LongSparseArray(1);
        }
        return this.mUnthemedEntries;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean prune(int n) {
        synchronized (this) {
            ArrayMap<Resources.ThemeKey, LongSparseArray<WeakReference<T>>> arrayMap = this.mThemedEntries;
            boolean bl = true;
            if (arrayMap != null) {
                for (int i = this.mThemedEntries.size() - 1; i >= 0; --i) {
                    if (!this.pruneEntriesLocked(this.mThemedEntries.valueAt(i), n)) continue;
                    this.mThemedEntries.removeAt(i);
                }
            }
            this.pruneEntriesLocked(this.mNullThemedEntries, n);
            this.pruneEntriesLocked(this.mUnthemedEntries, n);
            if (this.mThemedEntries != null) return false;
            if (this.mNullThemedEntries != null) return false;
            if (this.mUnthemedEntries != null) return false;
            return bl;
        }
    }

    private boolean pruneEntriesLocked(LongSparseArray<WeakReference<T>> longSparseArray, int n) {
        boolean bl = true;
        if (longSparseArray == null) {
            return true;
        }
        for (int i = longSparseArray.size() - 1; i >= 0; --i) {
            WeakReference<T> weakReference = longSparseArray.valueAt(i);
            if (weakReference != null && !this.pruneEntryLocked(weakReference.get(), n)) continue;
            longSparseArray.removeAt(i);
        }
        if (longSparseArray.size() != 0) {
            bl = false;
        }
        return bl;
    }

    private boolean pruneEntryLocked(T t, int n) {
        boolean bl = t == null || n != 0 && this.shouldInvalidateEntry(t, n);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public T get(long l, Resources.Theme longSparseArray) {
        synchronized (this) {
            longSparseArray = this.getThemedLocked((Resources.Theme)((Object)longSparseArray), false);
            if (longSparseArray != null && (longSparseArray = (WeakReference)longSparseArray.get(l)) != null) {
                longSparseArray = ((Reference)((Object)longSparseArray)).get();
                return (T)longSparseArray;
            }
            longSparseArray = this.getUnthemedLocked(false);
            if (longSparseArray != null && (longSparseArray = (WeakReference)longSparseArray.get(l)) != null) {
                longSparseArray = ((Reference)((Object)longSparseArray)).get();
                return (T)longSparseArray;
            }
            return null;
        }
    }

    @UnsupportedAppUsage
    public void onConfigurationChange(int n) {
        this.prune(n);
    }

    public void put(long l, Resources.Theme theme, T t) {
        this.put(l, theme, t, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void put(long l, Resources.Theme longSparseArray, T t, boolean bl) {
        if (t == null) {
            return;
        }
        synchronized (this) {
            longSparseArray = !bl ? this.getUnthemedLocked(true) : this.getThemedLocked((Resources.Theme)((Object)longSparseArray), true);
            if (longSparseArray != null) {
                WeakReference<T> weakReference = new WeakReference<T>(t);
                longSparseArray.put(l, weakReference);
            }
            return;
        }
    }

    protected abstract boolean shouldInvalidateEntry(T var1, int var2);
}

