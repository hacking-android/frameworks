/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Element;
import android.renderscript.Program;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class ProgramVertex
extends Program {
    ProgramVertex(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public Element getInput(int n) {
        if (n >= 0 && n < this.mInputs.length) {
            return this.mInputs[n];
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public int getInputCount() {
        int n = this.mInputs != null ? this.mInputs.length : 0;
        return n;
    }

    public static class Builder
    extends Program.BaseProgramBuilder {
        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            super(renderScript);
        }

        @UnsupportedAppUsage
        public Builder addInput(Element element) throws IllegalStateException {
            if (this.mInputCount < 8) {
                if (!element.isComplex()) {
                    Element[] arrelement = this.mInputs;
                    int n = this.mInputCount;
                    this.mInputCount = n + 1;
                    arrelement[n] = element;
                    return this;
                }
                throw new RSIllegalArgumentException("Complex elements not allowed.");
            }
            throw new RSIllegalArgumentException("Max input count exceeded.");
        }

        @UnsupportedAppUsage
        public ProgramVertex create() {
            int n;
            int n2;
            this.mRS.validate();
            Object object = new long[(this.mInputCount + this.mOutputCount + this.mConstantCount + this.mTextureCount) * 2];
            String[] arrstring = new String[this.mTextureCount];
            int n3 = 0;
            for (n2 = 0; n2 < this.mInputCount; ++n2) {
                n = n3 + 1;
                object[n3] = Program.ProgramParam.INPUT.mID;
                n3 = n + 1;
                object[n] = this.mInputs[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mOutputCount; ++n2) {
                n = n3 + 1;
                object[n3] = Program.ProgramParam.OUTPUT.mID;
                n3 = n + 1;
                object[n] = this.mOutputs[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mConstantCount; ++n2) {
                n = n3 + 1;
                object[n3] = Program.ProgramParam.CONSTANT.mID;
                n3 = n + 1;
                object[n] = this.mConstants[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mTextureCount; ++n2) {
                n = n3 + 1;
                object[n3] = Program.ProgramParam.TEXTURE_TYPE.mID;
                n3 = n + 1;
                object[n] = this.mTextureTypes[n2].mID;
                arrstring[n2] = this.mTextureNames[n2];
            }
            object = new ProgramVertex(this.mRS.nProgramVertexCreate(this.mShader, arrstring, (long[])object), this.mRS);
            this.initProgram((Program)object);
            return object;
        }
    }

}

