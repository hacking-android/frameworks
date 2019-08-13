/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLObjectHandle;

public class EGLSurface
extends EGLObjectHandle {
    private EGLSurface(long l) {
        super(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof EGLSurface)) {
            return false;
        }
        object = (EGLSurface)object;
        if (this.getNativeHandle() != ((EGLObjectHandle)object).getNativeHandle()) {
            bl = false;
        }
        return bl;
    }
}

