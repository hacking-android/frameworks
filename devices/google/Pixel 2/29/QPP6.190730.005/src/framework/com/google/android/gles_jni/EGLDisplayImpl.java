/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import javax.microedition.khronos.egl.EGLDisplay;

public class EGLDisplayImpl
extends EGLDisplay {
    long mEGLDisplay;

    public EGLDisplayImpl(long l) {
        this.mEGLDisplay = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (EGLDisplayImpl)object;
            if (this.mEGLDisplay != ((EGLDisplayImpl)object).mEGLDisplay) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        long l = this.mEGLDisplay;
        return 17 * 31 + (int)(l ^ l >>> 32);
    }
}

