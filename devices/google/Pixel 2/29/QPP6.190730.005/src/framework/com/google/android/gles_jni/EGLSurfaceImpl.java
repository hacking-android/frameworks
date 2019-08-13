/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import javax.microedition.khronos.egl.EGLSurface;

public class EGLSurfaceImpl
extends EGLSurface {
    long mEGLSurface;

    public EGLSurfaceImpl() {
        this.mEGLSurface = 0L;
    }

    public EGLSurfaceImpl(long l) {
        this.mEGLSurface = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (EGLSurfaceImpl)object;
            if (this.mEGLSurface != ((EGLSurfaceImpl)object).mEGLSurface) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        long l = this.mEGLSurface;
        return 17 * 31 + (int)(l ^ l >>> 32);
    }
}

