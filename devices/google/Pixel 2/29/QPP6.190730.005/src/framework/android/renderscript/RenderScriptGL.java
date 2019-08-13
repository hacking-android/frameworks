/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.renderscript.BaseObj;
import android.renderscript.ProgramFragment;
import android.renderscript.ProgramRaster;
import android.renderscript.ProgramStore;
import android.renderscript.ProgramVertex;
import android.renderscript.RSDriverException;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;

public class RenderScriptGL
extends RenderScript {
    int mHeight;
    SurfaceConfig mSurfaceConfig;
    int mWidth;

    @UnsupportedAppUsage
    public RenderScriptGL(Context context, SurfaceConfig surfaceConfig) {
        super(context);
        this.mSurfaceConfig = new SurfaceConfig(surfaceConfig);
        int n = context.getApplicationInfo().targetSdkVersion;
        this.mWidth = 0;
        this.mHeight = 0;
        long l = this.nDeviceCreate();
        int n2 = context.getResources().getDisplayMetrics().densityDpi;
        this.mContext = this.nContextCreateGL(l, 0, n, this.mSurfaceConfig.mColorMin, this.mSurfaceConfig.mColorPref, this.mSurfaceConfig.mAlphaMin, this.mSurfaceConfig.mAlphaPref, this.mSurfaceConfig.mDepthMin, this.mSurfaceConfig.mDepthPref, this.mSurfaceConfig.mStencilMin, this.mSurfaceConfig.mStencilPref, this.mSurfaceConfig.mSamplesMin, this.mSurfaceConfig.mSamplesPref, this.mSurfaceConfig.mSamplesQ, n2);
        if (this.mContext != 0L) {
            this.mMessageThread = new RenderScript.MessageThread(this);
            this.mMessageThread.start();
            return;
        }
        throw new RSDriverException("Failed to create RS context.");
    }

    public void bindProgramFragment(ProgramFragment programFragment) {
        this.validate();
        this.nContextBindProgramFragment((int)this.safeID(programFragment));
    }

    @UnsupportedAppUsage
    public void bindProgramRaster(ProgramRaster programRaster) {
        this.validate();
        this.nContextBindProgramRaster((int)this.safeID(programRaster));
    }

    @UnsupportedAppUsage
    public void bindProgramStore(ProgramStore programStore) {
        this.validate();
        this.nContextBindProgramStore((int)this.safeID(programStore));
    }

    @UnsupportedAppUsage
    public void bindProgramVertex(ProgramVertex programVertex) {
        this.validate();
        this.nContextBindProgramVertex((int)this.safeID(programVertex));
    }

    @UnsupportedAppUsage
    public void bindRootScript(Script script) {
        this.validate();
        this.nContextBindRootScript((int)this.safeID(script));
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void pause() {
        this.validate();
        this.nContextPause();
    }

    public void resume() {
        this.validate();
        this.nContextResume();
    }

    @UnsupportedAppUsage
    public void setSurface(SurfaceHolder surfaceHolder, int n, int n2) {
        this.validate();
        Surface surface = null;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
        }
        this.mWidth = n;
        this.mHeight = n2;
        this.nContextSetSurface(n, n2, surface);
    }

    public void setSurfaceTexture(SurfaceTexture surfaceTexture, int n, int n2) {
        this.validate();
        Surface surface = null;
        if (surfaceTexture != null) {
            surface = new Surface(surfaceTexture);
        }
        this.mWidth = n;
        this.mHeight = n2;
        this.nContextSetSurface(n, n2, surface);
    }

    public static class SurfaceConfig {
        int mAlphaMin = 0;
        int mAlphaPref = 0;
        int mColorMin = 8;
        int mColorPref = 8;
        int mDepthMin = 0;
        int mDepthPref = 0;
        int mSamplesMin = 1;
        int mSamplesPref = 1;
        float mSamplesQ = 1.0f;
        int mStencilMin = 0;
        int mStencilPref = 0;

        @UnsupportedAppUsage
        public SurfaceConfig() {
        }

        public SurfaceConfig(SurfaceConfig surfaceConfig) {
            this.mDepthMin = surfaceConfig.mDepthMin;
            this.mDepthPref = surfaceConfig.mDepthPref;
            this.mStencilMin = surfaceConfig.mStencilMin;
            this.mStencilPref = surfaceConfig.mStencilPref;
            this.mColorMin = surfaceConfig.mColorMin;
            this.mColorPref = surfaceConfig.mColorPref;
            this.mAlphaMin = surfaceConfig.mAlphaMin;
            this.mAlphaPref = surfaceConfig.mAlphaPref;
            this.mSamplesMin = surfaceConfig.mSamplesMin;
            this.mSamplesPref = surfaceConfig.mSamplesPref;
            this.mSamplesQ = surfaceConfig.mSamplesQ;
        }

        private void validateRange(int n, int n2, int n3, int n4) {
            if (n >= n3 && n <= n4) {
                if (n2 >= n) {
                    return;
                }
                throw new RSIllegalArgumentException("preferred must be >= Minimum.");
            }
            throw new RSIllegalArgumentException("Minimum value provided out of range.");
        }

        public void setAlpha(int n, int n2) {
            this.validateRange(n, n2, 0, 8);
            this.mAlphaMin = n;
            this.mAlphaPref = n2;
        }

        public void setColor(int n, int n2) {
            this.validateRange(n, n2, 5, 8);
            this.mColorMin = n;
            this.mColorPref = n2;
        }

        @UnsupportedAppUsage
        public void setDepth(int n, int n2) {
            this.validateRange(n, n2, 0, 24);
            this.mDepthMin = n;
            this.mDepthPref = n2;
        }

        public void setSamples(int n, int n2, float f) {
            this.validateRange(n, n2, 1, 32);
            if (!(f < 0.0f) && !(f > 1.0f)) {
                this.mSamplesMin = n;
                this.mSamplesPref = n2;
                this.mSamplesQ = f;
                return;
            }
            throw new RSIllegalArgumentException("Quality out of 0-1 range.");
        }
    }

}

