/*
 * Decompiled with CFR 0.145.
 */
package javax.microedition.khronos.egl;

import com.google.android.gles_jni.EGLImpl;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.opengles.GL;

public abstract class EGLContext {
    private static final EGL EGL_INSTANCE = new EGLImpl();

    public static EGL getEGL() {
        return EGL_INSTANCE;
    }

    public abstract GL getGL();
}

