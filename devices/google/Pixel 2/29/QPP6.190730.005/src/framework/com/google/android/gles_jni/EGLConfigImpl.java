/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.gles_jni;

import javax.microedition.khronos.egl.EGLConfig;

public class EGLConfigImpl
extends EGLConfig {
    private long mEGLConfig;

    EGLConfigImpl(long l) {
        this.mEGLConfig = l;
    }

    long get() {
        return this.mEGLConfig;
    }
}

