/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLObjectHandle;

public class EGLConfig
extends EGLObjectHandle {
    private EGLConfig(long l) {
        super(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof EGLConfig)) {
            return false;
        }
        object = (EGLConfig)object;
        if (this.getNativeHandle() != ((EGLObjectHandle)object).getNativeHandle()) {
            bl = false;
        }
        return bl;
    }
}

