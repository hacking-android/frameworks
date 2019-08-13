/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.BaseObj;
import android.renderscript.RenderScript;

public class ProgramStore
extends BaseObj {
    BlendDstFunc mBlendDst;
    BlendSrcFunc mBlendSrc;
    boolean mColorMaskA;
    boolean mColorMaskB;
    boolean mColorMaskG;
    boolean mColorMaskR;
    DepthFunc mDepthFunc;
    boolean mDepthMask;
    boolean mDither;

    ProgramStore(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    @UnsupportedAppUsage
    public static ProgramStore BLEND_ALPHA_DEPTH_NONE(RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH == null) {
            Builder builder = new Builder(renderScript);
            builder.setDepthFunc(DepthFunc.ALWAYS);
            builder.setBlendFunc(BlendSrcFunc.SRC_ALPHA, BlendDstFunc.ONE_MINUS_SRC_ALPHA);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(false);
            renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH = builder.create();
        }
        return renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH;
    }

    public static ProgramStore BLEND_ALPHA_DEPTH_TEST(RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST == null) {
            Builder builder = new Builder(renderScript);
            builder.setDepthFunc(DepthFunc.LESS);
            builder.setBlendFunc(BlendSrcFunc.SRC_ALPHA, BlendDstFunc.ONE_MINUS_SRC_ALPHA);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(true);
            renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST = builder.create();
        }
        return renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST;
    }

    public static ProgramStore BLEND_NONE_DEPTH_NONE(RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH == null) {
            Builder builder = new Builder(renderScript);
            builder.setDepthFunc(DepthFunc.ALWAYS);
            builder.setBlendFunc(BlendSrcFunc.ONE, BlendDstFunc.ZERO);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(false);
            renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH = builder.create();
        }
        return renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH;
    }

    public static ProgramStore BLEND_NONE_DEPTH_TEST(RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST == null) {
            Builder builder = new Builder(renderScript);
            builder.setDepthFunc(DepthFunc.LESS);
            builder.setBlendFunc(BlendSrcFunc.ONE, BlendDstFunc.ZERO);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(true);
            renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST = builder.create();
        }
        return renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST;
    }

    public BlendDstFunc getBlendDstFunc() {
        return this.mBlendDst;
    }

    public BlendSrcFunc getBlendSrcFunc() {
        return this.mBlendSrc;
    }

    public DepthFunc getDepthFunc() {
        return this.mDepthFunc;
    }

    public boolean isColorMaskAlphaEnabled() {
        return this.mColorMaskA;
    }

    public boolean isColorMaskBlueEnabled() {
        return this.mColorMaskB;
    }

    public boolean isColorMaskGreenEnabled() {
        return this.mColorMaskG;
    }

    public boolean isColorMaskRedEnabled() {
        return this.mColorMaskR;
    }

    public boolean isDepthMaskEnabled() {
        return this.mDepthMask;
    }

    public boolean isDitherEnabled() {
        return this.mDither;
    }

    public static enum BlendDstFunc {
        ZERO(0),
        ONE(1),
        SRC_COLOR(2),
        ONE_MINUS_SRC_COLOR(3),
        SRC_ALPHA(4),
        ONE_MINUS_SRC_ALPHA(5),
        DST_ALPHA(6),
        ONE_MINUS_DST_ALPHA(7);
        
        int mID;

        private BlendDstFunc(int n2) {
            this.mID = n2;
        }
    }

    public static enum BlendSrcFunc {
        ZERO(0),
        ONE(1),
        DST_COLOR(2),
        ONE_MINUS_DST_COLOR(3),
        SRC_ALPHA(4),
        ONE_MINUS_SRC_ALPHA(5),
        DST_ALPHA(6),
        ONE_MINUS_DST_ALPHA(7),
        SRC_ALPHA_SATURATE(8);
        
        int mID;

        private BlendSrcFunc(int n2) {
            this.mID = n2;
        }
    }

    public static class Builder {
        BlendDstFunc mBlendDst;
        BlendSrcFunc mBlendSrc;
        boolean mColorMaskA;
        boolean mColorMaskB;
        boolean mColorMaskG;
        boolean mColorMaskR;
        DepthFunc mDepthFunc;
        boolean mDepthMask;
        boolean mDither;
        RenderScript mRS;

        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mDepthFunc = DepthFunc.ALWAYS;
            this.mDepthMask = false;
            this.mColorMaskR = true;
            this.mColorMaskG = true;
            this.mColorMaskB = true;
            this.mColorMaskA = true;
            this.mBlendSrc = BlendSrcFunc.ONE;
            this.mBlendDst = BlendDstFunc.ZERO;
        }

        @UnsupportedAppUsage
        public ProgramStore create() {
            this.mRS.validate();
            ProgramStore programStore = new ProgramStore(this.mRS.nProgramStoreCreate(this.mColorMaskR, this.mColorMaskG, this.mColorMaskB, this.mColorMaskA, this.mDepthMask, this.mDither, this.mBlendSrc.mID, this.mBlendDst.mID, this.mDepthFunc.mID), this.mRS);
            programStore.mDepthFunc = this.mDepthFunc;
            programStore.mDepthMask = this.mDepthMask;
            programStore.mColorMaskR = this.mColorMaskR;
            programStore.mColorMaskG = this.mColorMaskG;
            programStore.mColorMaskB = this.mColorMaskB;
            programStore.mColorMaskA = this.mColorMaskA;
            programStore.mBlendSrc = this.mBlendSrc;
            programStore.mBlendDst = this.mBlendDst;
            programStore.mDither = this.mDither;
            return programStore;
        }

        @UnsupportedAppUsage
        public Builder setBlendFunc(BlendSrcFunc blendSrcFunc, BlendDstFunc blendDstFunc) {
            this.mBlendSrc = blendSrcFunc;
            this.mBlendDst = blendDstFunc;
            return this;
        }

        public Builder setColorMaskEnabled(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            this.mColorMaskR = bl;
            this.mColorMaskG = bl2;
            this.mColorMaskB = bl3;
            this.mColorMaskA = bl4;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setDepthFunc(DepthFunc depthFunc) {
            this.mDepthFunc = depthFunc;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setDepthMaskEnabled(boolean bl) {
            this.mDepthMask = bl;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setDitherEnabled(boolean bl) {
            this.mDither = bl;
            return this;
        }
    }

    public static enum DepthFunc {
        ALWAYS(0),
        LESS(1),
        LESS_OR_EQUAL(2),
        GREATER(3),
        GREATER_OR_EQUAL(4),
        EQUAL(5),
        NOT_EQUAL(6);
        
        int mID;

        private DepthFunc(int n2) {
            this.mID = n2;
        }
    }

}

