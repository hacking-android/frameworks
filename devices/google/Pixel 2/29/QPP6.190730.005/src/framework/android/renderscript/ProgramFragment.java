/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Element;
import android.renderscript.Program;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class ProgramFragment
extends Program {
    ProgramFragment(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static class Builder
    extends Program.BaseProgramBuilder {
        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            super(renderScript);
        }

        @UnsupportedAppUsage
        public ProgramFragment create() {
            int n;
            int n2;
            this.mRS.validate();
            long[] arrl = new long[(this.mInputCount + this.mOutputCount + this.mConstantCount + this.mTextureCount) * 2];
            Object object = new String[this.mTextureCount];
            int n3 = 0;
            for (n2 = 0; n2 < this.mInputCount; ++n2) {
                n = n3 + 1;
                arrl[n3] = Program.ProgramParam.INPUT.mID;
                n3 = n + 1;
                arrl[n] = this.mInputs[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mOutputCount; ++n2) {
                n = n3 + 1;
                arrl[n3] = Program.ProgramParam.OUTPUT.mID;
                n3 = n + 1;
                arrl[n] = this.mOutputs[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mConstantCount; ++n2) {
                n = n3 + 1;
                arrl[n3] = Program.ProgramParam.CONSTANT.mID;
                n3 = n + 1;
                arrl[n] = this.mConstants[n2].getID(this.mRS);
            }
            for (n2 = 0; n2 < this.mTextureCount; ++n2) {
                n = n3 + 1;
                arrl[n3] = Program.ProgramParam.TEXTURE_TYPE.mID;
                n3 = n + 1;
                arrl[n] = this.mTextureTypes[n2].mID;
                object[n2] = this.mTextureNames[n2];
            }
            object = new ProgramFragment(this.mRS.nProgramFragmentCreate(this.mShader, (String[])object, arrl), this.mRS);
            this.initProgram((Program)object);
            return object;
        }
    }

}

