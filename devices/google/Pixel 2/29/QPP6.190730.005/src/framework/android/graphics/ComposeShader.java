/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Xfermode;

public class ComposeShader
extends Shader {
    private long mNativeInstanceShaderA;
    private long mNativeInstanceShaderB;
    private int mPorterDuffMode;
    Shader mShaderA;
    Shader mShaderB;

    private ComposeShader(Shader shader, Shader shader2, int n) {
        if (shader != null && shader2 != null) {
            this.mShaderA = shader;
            this.mShaderB = shader2;
            this.mPorterDuffMode = n;
            return;
        }
        throw new IllegalArgumentException("Shader parameters must not be null");
    }

    public ComposeShader(Shader shader, Shader shader2, BlendMode blendMode) {
        this(shader, shader2, blendMode.getXfermode().porterDuffMode);
    }

    public ComposeShader(Shader shader, Shader shader2, PorterDuff.Mode mode) {
        this(shader, shader2, mode.nativeInt);
    }

    @Deprecated
    public ComposeShader(Shader shader, Shader shader2, Xfermode xfermode) {
        this(shader, shader2, xfermode.porterDuffMode);
    }

    private static native long nativeCreate(long var0, long var2, long var4, int var6);

    @Override
    long createNativeInstance(long l) {
        this.mNativeInstanceShaderA = this.mShaderA.getNativeInstance();
        this.mNativeInstanceShaderB = this.mShaderB.getNativeInstance();
        return ComposeShader.nativeCreate(l, this.mShaderA.getNativeInstance(), this.mShaderB.getNativeInstance(), this.mPorterDuffMode);
    }

    @Override
    protected void verifyNativeInstance() {
        if (this.mShaderA.getNativeInstance() != this.mNativeInstanceShaderA || this.mShaderB.getNativeInstance() != this.mNativeInstanceShaderB) {
            this.discardNativeInstance();
        }
    }
}

