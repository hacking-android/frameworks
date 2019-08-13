/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.EGLObjectHandle;

public class EGLImage
extends EGLObjectHandle {
    private EGLImage(long l) {
        super(l);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof EGLImage)) {
            return false;
        }
        object = (EGLImage)object;
        if (this.getNativeHandle() != ((EGLObjectHandle)object).getNativeHandle()) {
            bl = false;
        }
        return bl;
    }
}

