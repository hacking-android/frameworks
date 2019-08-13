/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.Matrix4f;
import android.renderscript.Program;
import android.renderscript.ProgramVertex;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class ProgramVertexFixedFunction
extends ProgramVertex {
    ProgramVertexFixedFunction(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    @UnsupportedAppUsage
    public void bindConstants(Constants constants) {
        this.mRS.validate();
        this.bindConstants(constants.getAllocation(), 0);
    }

    public static class Builder {
        RenderScript mRS;
        String mShader;
        boolean mTextureMatrixEnable;

        @UnsupportedAppUsage
        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("varying vec4 varColor;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("varying vec2 varTex0;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("void main() {\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("  gl_Position = UNI_MVP * ATTRIB_position;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("  gl_PointSize = 1.0;\n");
            this.mShader = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("  varColor = ATTRIB_color;\n");
            this.mShader = stringBuilder.toString();
            if (this.mTextureMatrixEnable) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.mShader);
                stringBuilder.append("  varTex0 = (UNI_TexMatrix * vec4(ATTRIB_texture0, 0.0, 1.0)).xy;\n");
                this.mShader = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(this.mShader);
                stringBuilder.append("  varTex0 = ATTRIB_texture0;\n");
                this.mShader = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.mShader);
            stringBuilder.append("}\n");
            this.mShader = stringBuilder.toString();
        }

        static Type getConstantInputType(RenderScript object) {
            Element.Builder builder = new Element.Builder((RenderScript)object);
            builder.add(Element.MATRIX4X4((RenderScript)object), "MV");
            builder.add(Element.MATRIX4X4((RenderScript)object), "P");
            builder.add(Element.MATRIX4X4((RenderScript)object), "TexMatrix");
            builder.add(Element.MATRIX4X4((RenderScript)object), "MVP");
            object = new Type.Builder((RenderScript)object, builder.create());
            ((Type.Builder)object).setX(1);
            return ((Type.Builder)object).create();
        }

        @UnsupportedAppUsage
        public ProgramVertexFixedFunction create() {
            this.buildShaderString();
            InternalBuilder internalBuilder = new InternalBuilder(this.mRS);
            internalBuilder.setShader(this.mShader);
            internalBuilder.addConstant(Builder.getConstantInputType(this.mRS));
            Element.Builder builder = new Element.Builder(this.mRS);
            builder.add(Element.F32_4(this.mRS), "position");
            builder.add(Element.F32_4(this.mRS), "color");
            builder.add(Element.F32_3(this.mRS), "normal");
            builder.add(Element.F32_2(this.mRS), "texture0");
            internalBuilder.addInput(builder.create());
            return internalBuilder.create();
        }

        public Builder setTextureMatrixEnable(boolean bl) {
            this.mTextureMatrixEnable = bl;
            return this;
        }
    }

    public static class Constants {
        static final int MODELVIEW_OFFSET = 0;
        static final int PROJECTION_OFFSET = 16;
        static final int TEXTURE_OFFSET = 32;
        Allocation mAlloc;
        private FieldPacker mIOBuffer;
        Matrix4f mModel;
        Matrix4f mProjection;
        Matrix4f mTexture;

        @UnsupportedAppUsage
        public Constants(RenderScript renderScript) {
            Type type = Builder.getConstantInputType(renderScript);
            this.mAlloc = Allocation.createTyped(renderScript, type);
            this.mIOBuffer = new FieldPacker(type.getElement().getBytesSize() * type.getCount());
            this.mModel = new Matrix4f();
            this.mProjection = new Matrix4f();
            this.mTexture = new Matrix4f();
            this.setModelview(new Matrix4f());
            this.setProjection(new Matrix4f());
            this.setTexture(new Matrix4f());
        }

        private void addToBuffer(int n, Matrix4f object) {
            this.mIOBuffer.reset(n);
            for (n = 0; n < 16; ++n) {
                this.mIOBuffer.addF32(((Matrix4f)object).mMat[n]);
            }
            object = this.mIOBuffer;
            ((FieldPacker)object).reset(((FieldPacker)object).getData().length);
            this.mAlloc.setFromFieldPacker(0, this.mIOBuffer);
        }

        public void destroy() {
            this.mAlloc.destroy();
            this.mAlloc = null;
        }

        Allocation getAllocation() {
            return this.mAlloc;
        }

        public void setModelview(Matrix4f matrix4f) {
            this.mModel.load(matrix4f);
            this.addToBuffer(0, matrix4f);
        }

        @UnsupportedAppUsage
        public void setProjection(Matrix4f matrix4f) {
            this.mProjection.load(matrix4f);
            this.addToBuffer(64, matrix4f);
        }

        public void setTexture(Matrix4f matrix4f) {
            this.mTexture.load(matrix4f);
            this.addToBuffer(128, matrix4f);
        }
    }

    static class InternalBuilder
    extends Program.BaseProgramBuilder {
        public InternalBuilder(RenderScript renderScript) {
            super(renderScript);
        }

        public InternalBuilder addInput(Element element) throws IllegalStateException {
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

        public ProgramVertexFixedFunction create() {
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
            object = new ProgramVertexFixedFunction(this.mRS.nProgramVertexCreate(this.mShader, (String[])object, arrl), this.mRS);
            this.initProgram((Program)object);
            return object;
        }
    }

}

