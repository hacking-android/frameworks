/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLObjectHandle;

public class EGLSync
extends EGLObjectHandle {
    private EGLSync(long l) {
        super(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof EGLSync)) {
            return false;
        }
        object = (EGLSync)object;
        if (this.getNativeHandle() != ((EGLObjectHandle)object).getNativeHandle()) {
            bl = false;
        }
        return bl;
    }
}

