/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import com.google.android.gles_jni.GLImpl;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL;

public class EGLContextImpl
extends EGLContext {
    long mEGLContext;
    private GLImpl mGLContext;

    public EGLContextImpl(long l) {
        this.mEGLContext = l;
        this.mGLContext = new GLImpl();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (EGLContextImpl)object;
            if (this.mEGLContext != ((EGLContextImpl)object).mEGLContext) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public GL getGL() {
        return this.mGLContext;
    }

    public int hashCode() {
        long l = this.mEGLContext;
        return 17 * 31 + (int)(l ^ l >>> 32);
    }
}

