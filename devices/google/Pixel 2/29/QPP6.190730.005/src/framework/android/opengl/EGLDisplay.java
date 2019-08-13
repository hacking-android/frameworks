/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLObjectHandle;

public class EGLDisplay
extends EGLObjectHandle {
    private EGLDisplay(long l) {
        super(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof EGLDisplay)) {
            return false;
        }
        object = (EGLDisplay)object;
        if (this.getNativeHandle() != ((EGLObjectHandle)object).getNativeHandle()) {
            bl = false;
        }
        return bl;
    }
}

