/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.GLU;

public class GLException
extends RuntimeException {
    private final int mError;

    public GLException(int n) {
        super(GLException.getErrorString(n));
        this.mError = n;
    }

    public GLException(int n, String string2) {
        super(string2);
        this.mError = n;
    }

    private static String getErrorString(int n) {
        String string2 = GLU.gluErrorString(n);
        CharSequence charSequence = string2;
        if (string2 == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unknown error 0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(n));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    int getError() {
        return this.mError;
    }
}

