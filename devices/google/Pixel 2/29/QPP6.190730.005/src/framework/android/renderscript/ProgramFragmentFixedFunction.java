/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.Float4;
import android.renderscript.Program;
import android.renderscript.ProgramFragment;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class ProgramFragmentFixedFunction
extends ProgramFragment {
    ProgramFragmentFixedFunction(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static class Builder {
        public static final int MAX_TEXTURE = 2;
        int mNumTextures;
        boolean mPointSpriteEnable;
        RenderScript mRS;
        String mShader;
        Slot[] mSlots;
        boolean mVaryingColorEnable;

        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mSlots = new Slot[2];
            this.mPointSpriteEnable = false;
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("varying lowp vec4 varColor;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("varying vec2 varTex0;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("void main() {\n");
            this.mShader = stringBuilder.toString();
            if (this.mVaryingColorEnable) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.mShader);
                stringBuilder.append("  lowp vec4 col = varColor;\n");
                this.mShader = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.mShader);
                stringBuilder.append("  lowp vec4 col = UNI_Color;\n");
                this.mShader = stringBuilder.toString();
            }
            if (this.mNumTextures != 0) {
                if (this.mPointSpriteEnable) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mShader);
                    stringBuilder.append("  vec2 t0 = gl_PointCoord;\n");
                    this.mShader = stringBuilder.toString();
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mShader);
                    stringBuilder.append("  vec2 t0 = varTex0.xy;\n");
                    this.mShader = stringBuilder.toString();
                }
            }
            for (int i = 0; i < this.mNumTextures; ++i) {
                int n = 1.$SwitchMap$android$renderscript$ProgramFragmentFixedFunction$Builder$EnvMode[this.mSlots[i].env.ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) continue;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mShader);
                        stringBuilder.append("  col = texture2D(UNI_Tex0, t0);\n");
                        this.mShader = stringBuilder.toString();
                        continue;
                    }
                    n = 1.$SwitchMap$android$renderscript$ProgramFragmentFixedFunction$Builder$Format[this.mSlots[i].format.ordinal()];
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n != 4) continue;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(this.mShader);
                                stringBuilder.append("  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n");
                                this.mShader = stringBuilder.toString();
                                continue;
                            }
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(this.mShader);
                            stringBuilder.append("  col.rgb *= texture2D(UNI_Tex0, t0).rgb;\n");
                            this.mShader = stringBuilder.toString();
                            continue;
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mShader);
                        stringBuilder.append("  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n");
                        this.mShader = stringBuilder.toString();
                        continue;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mShader);
                    stringBuilder.append("  col.a *= texture2D(UNI_Tex0, t0).a;\n");
                    this.mShader = stringBuilder.toString();
                    continue;
                }
                n = 1.$SwitchMap$android$renderscript$ProgramFragmentFixedFunction$Builder$Format[this.mSlots[i].format.ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) continue;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(this.mShader);
                            stringBuilder.append("  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n");
                            this.mShader = stringBuilder.toString();
                            continue;
                        }
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mShader);
                        stringBuilder.append("  col.rgb = texture2D(UNI_Tex0, t0).rgb;\n");
                        this.mShader = stringBuilder.toString();
                        continue;
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mShader);
                    stringBuilder.append("  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n");
                    this.mShader = stringBuilder.toString();
                    continue;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.mShader);
                stringBuilder.append("  col.a = texture2D(UNI_Tex0, t0).a;\n");
                this.mShader = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("  gl_FragColor = col;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("}\n");
            this.mShader = stringBuilder.toString();
        }

        @UnsupportedAppUsage
        public ProgramFragmentFixedFunction create() {
            int n;
            Object object = new InternalBuilder(this.mRS);
            this.mNumTextures = 0;
            for (n = 0; n < 2; ++n) {
                if (this.mSlots[n] == null) continue;
                ++this.mNumTextures;
            }
            this.buildShaderString();
            ((Program.BaseProgramBuilder)object).setShader(this.mShader);
            Object object2 = null;
            if (!this.mVaryingColorEnable) {
                object2 = new Element.Builder(this.mRS);
                ((Element.Builder)object2).add(Element.F32_4(this.mRS), "Color");
                object2 = new Type.Builder(this.mRS, ((Element.Builder)object2).create());
                ((Type.Builder)object2).setX(1);
                object2 = ((Type.Builder)object2).create();
                ((Program.BaseProgramBuilder)object).addConstant((Type)object2);
            }
            for (n = 0; n < this.mNumTextures; ++n) {
                ((Program.BaseProgramBuilder)object).addTexture(Program.TextureType.TEXTURE_2D);
            }
            object = ((InternalBuilder)object).create();
            ((ProgramFragmentFixedFunction)object).mTextureCount = 2;
            if (!this.mVaryingColorEnable) {
                object2 = Allocation.createTyped(this.mRS, (Type)object2);
                FieldPacker fieldPacker = new FieldPacker(16);
                fieldPacker.addF32(new Float4(1.0f, 1.0f, 1.0f, 1.0f));
                ((Allocation)object2).setFromFieldPacker(0, fieldPacker);
                ((Program)object).bindConstants((Allocation)object2, 0);
            }
            return object;
        }

        public Builder setPointSpriteTexCoordinateReplacement(boolean bl) {
            this.mPointSpriteEnable = bl;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setTexture(EnvMode envMode, Format format, int n) throws IllegalArgumentException {
            if (n >= 0 && n < 2) {
                this.mSlots[n] = new Slot(envMode, format);
                return this;
            }
            throw new IllegalArgumentException("MAX_TEXTURE exceeded.");
        }

        @UnsupportedAppUsage
        public Builder setVaryingColor(boolean bl) {
            this.mVaryingColorEnable = bl;
            return this;
        }

        public static enum EnvMode {
            REPLACE(1),
            MODULATE(2),
            DECAL(3);
            
            int mID;

            private EnvMode(int n2) {
                this.mID = n2;
            }
        }

        public static enum Format {
            ALPHA(1),
            LUMINANCE_ALPHA(2),
            RGB(3),
            RGBA(4);
            
            int mID;

            private Format(int n2) {
                this.mID = n2;
            }
        }

        private class Slot {
            EnvMode env;
            Format format;

            Slot(EnvMode envMode, Format format) {
                this.env = envMode;
                this.format = format;
            }
        }

    }

    static class InternalBuilder
    extends Program.BaseProgramBuilder {
        public InternalBuilder(RenderScript renderScript) {
            super(renderScript);
        }

        public ProgramFragmentFixedFunction create() {
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
            object = new ProgramFragmentFixedFunction(this.mRS.nProgramFragmentCreate(this.mShader, (String[])object, arrl), this.mRS);
            this.initProgram((Program)object);
            return object;
        }
    }

}

