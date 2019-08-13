/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.Rect;
import android.graphics.Region;

public class RegionIterator {
    private long mNativeIter;

    public RegionIterator(Region region) {
        this.mNativeIter = RegionIterator.nativeConstructor(region.ni());
    }

    private static native long nativeConstructor(long var0);

    private static native void nativeDestructor(long var0);

    private static native boolean nativeNext(long var0, Rect var2);

    protected void finalize() throws Throwable {
        RegionIterator.nativeDestructor(this.mNativeIter);
        this.mNativeIter = 0L;
    }

    public final boolean next(Rect rect) {
        if (rect != null) {
            return RegionIterator.nativeNext(this.mNativeIter, rect);
        }
        throw new NullPointerException("The Rect must be provided");
    }
}

