/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.BaseObj;
import android.renderscript.RenderScript;

public class ProgramRaster
extends BaseObj {
    CullMode mCullMode = CullMode.BACK;
    boolean mPointSprite = false;

    ProgramRaster(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ProgramRaster CULL_BACK(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_BACK == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.BACK);
            renderScript.mProgramRaster_CULL_BACK = builder.create();
        }
        return renderScript.mProgramRaster_CULL_BACK;
    }

    public static ProgramRaster CULL_FRONT(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_FRONT == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.FRONT);
            renderScript.mProgramRaster_CULL_FRONT = builder.create();
        }
        return renderScript.mProgramRaster_CULL_FRONT;
    }

    public static ProgramRaster CULL_NONE(RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_NONE == null) {
            Builder builder = new Builder(renderScript);
            builder.setCullMode(CullMode.NONE);
            renderScript.mProgramRaster_CULL_NONE = builder.create();
        }
        return renderScript.mProgramRaster_CULL_NONE;
    }

    public CullMode getCullMode() {
        return this.mCullMode;
    }

    public boolean isPointSpriteEnabled() {
        return this.mPointSprite;
    }

    public static class Builder {
        CullMode mCullMode;
        boolean mPointSprite;
        RenderScript mRS;

        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mPointSprite = false;
            this.mCullMode = CullMode.BACK;
        }

        @UnsupportedAppUsage
        public ProgramRaster create() {
            this.mRS.validate();
            ProgramRaster programRaster = new ProgramRaster(this.mRS.nProgramRasterCreate(this.mPointSprite, this.mCullMode.mID), this.mRS);
            programRaster.mPointSprite = this.mPointSprite;
            programRaster.mCullMode = this.mCullMode;
            return programRaster;
        }

        public Builder setCullMode(CullMode cullMode) {
            this.mCullMode = cullMode;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setPointSpriteEnabled(boolean bl) {
            this.mPointSprite = bl;
            return this;
        }
    }

    public static enum CullMode {
        BACK(0),
        FRONT(1),
        NONE(2);
        
        int mID;

        private CullMode(int n2) {
            this.mID = n2;
        }
    }

}

