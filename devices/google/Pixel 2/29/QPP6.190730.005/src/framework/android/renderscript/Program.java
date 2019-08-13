/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Sampler;
import android.renderscript.Type;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Program
extends BaseObj {
    static final int MAX_CONSTANT = 8;
    static final int MAX_INPUT = 8;
    static final int MAX_OUTPUT = 8;
    static final int MAX_TEXTURE = 8;
    Type[] mConstants;
    Element[] mInputs;
    Element[] mOutputs;
    String mShader;
    int mTextureCount;
    String[] mTextureNames;
    TextureType[] mTextures;

    Program(long l, RenderScript renderScript) {
        super(l, renderScript);
        this.guard.open("destroy");
    }

    public void bindConstants(Allocation allocation, int n) {
        if (n >= 0 && n < this.mConstants.length) {
            if (allocation != null && allocation.getType().getID(this.mRS) != this.mConstants[n].getID(this.mRS)) {
                throw new IllegalArgumentException("Allocation type does not match slot type.");
            }
            long l = allocation != null ? allocation.getID(this.mRS) : 0L;
            this.mRS.nProgramBindConstants(this.getID(this.mRS), n, l);
            return;
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public void bindSampler(Sampler sampler, int n) throws IllegalArgumentException {
        this.mRS.validate();
        if (n >= 0 && n < this.mTextureCount) {
            long l = sampler != null ? sampler.getID(this.mRS) : 0L;
            this.mRS.nProgramBindSampler(this.getID(this.mRS), n, l);
            return;
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public void bindTexture(Allocation allocation, int n) throws IllegalArgumentException {
        this.mRS.validate();
        if (n >= 0 && n < this.mTextureCount) {
            if (allocation != null && allocation.getType().hasFaces() && this.mTextures[n] != TextureType.TEXTURE_CUBE) {
                throw new IllegalArgumentException("Cannot bind cubemap to 2d texture slot");
            }
            long l = allocation != null ? allocation.getID(this.mRS) : 0L;
            this.mRS.nProgramBindTexture(this.getID(this.mRS), n, l);
            return;
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public Type getConstant(int n) {
        Type[] arrtype;
        if (n >= 0 && n < (arrtype = this.mConstants).length) {
            return arrtype[n];
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public int getConstantCount() {
        Type[] arrtype = this.mConstants;
        int n = arrtype != null ? arrtype.length : 0;
        return n;
    }

    public int getTextureCount() {
        return this.mTextureCount;
    }

    public String getTextureName(int n) {
        if (n >= 0 && n < this.mTextureCount) {
            return this.mTextureNames[n];
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public TextureType getTextureType(int n) {
        if (n >= 0 && n < this.mTextureCount) {
            return this.mTextures[n];
        }
        throw new IllegalArgumentException("Slot ID out of range.");
    }

    public static class BaseProgramBuilder {
        @UnsupportedAppUsage
        int mConstantCount;
        @UnsupportedAppUsage
        Type[] mConstants;
        @UnsupportedAppUsage
        int mInputCount;
        @UnsupportedAppUsage
        Element[] mInputs;
        @UnsupportedAppUsage
        int mOutputCount;
        @UnsupportedAppUsage
        Element[] mOutputs;
        @UnsupportedAppUsage
        RenderScript mRS;
        @UnsupportedAppUsage
        String mShader;
        @UnsupportedAppUsage
        int mTextureCount;
        String[] mTextureNames;
        TextureType[] mTextureTypes;
        Type[] mTextures;

        @UnsupportedAppUsage
        protected BaseProgramBuilder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mInputs = new Element[8];
            this.mOutputs = new Element[8];
            this.mConstants = new Type[8];
            this.mInputCount = 0;
            this.mOutputCount = 0;
            this.mConstantCount = 0;
            this.mTextureCount = 0;
            this.mTextureTypes = new TextureType[8];
            this.mTextureNames = new String[8];
        }

        public BaseProgramBuilder addConstant(Type type) throws IllegalStateException {
            if (this.mConstantCount < 8) {
                if (!type.getElement().isComplex()) {
                    Type[] arrtype = this.mConstants;
                    int n = this.mConstantCount;
                    arrtype[n] = type;
                    this.mConstantCount = n + 1;
                    return this;
                }
                throw new RSIllegalArgumentException("Complex elements not allowed.");
            }
            throw new RSIllegalArgumentException("Max input count exceeded.");
        }

        public BaseProgramBuilder addTexture(TextureType textureType) throws IllegalArgumentException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tex");
            stringBuilder.append(this.mTextureCount);
            this.addTexture(textureType, stringBuilder.toString());
            return this;
        }

        public BaseProgramBuilder addTexture(TextureType textureType, String string2) throws IllegalArgumentException {
            int n = this.mTextureCount;
            if (n < 8) {
                this.mTextureTypes[n] = textureType;
                this.mTextureNames[n] = string2;
                this.mTextureCount = n + 1;
                return this;
            }
            throw new IllegalArgumentException("Max texture count exceeded.");
        }

        public int getCurrentConstantIndex() {
            return this.mConstantCount - 1;
        }

        public int getCurrentTextureIndex() {
            return this.mTextureCount - 1;
        }

        protected void initProgram(Program program) {
            int n;
            program.mInputs = new Element[this.mInputCount];
            System.arraycopy(this.mInputs, 0, program.mInputs, 0, this.mInputCount);
            program.mOutputs = new Element[this.mOutputCount];
            System.arraycopy(this.mOutputs, 0, program.mOutputs, 0, this.mOutputCount);
            program.mConstants = new Type[this.mConstantCount];
            System.arraycopy(this.mConstants, 0, program.mConstants, 0, this.mConstantCount);
            program.mTextureCount = n = this.mTextureCount;
            program.mTextures = new TextureType[n];
            System.arraycopy(this.mTextureTypes, 0, program.mTextures, 0, this.mTextureCount);
            program.mTextureNames = new String[this.mTextureCount];
            System.arraycopy(this.mTextureNames, 0, program.mTextureNames, 0, this.mTextureCount);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public BaseProgramBuilder setShader(Resources var1_1, int var2_5) {
            var3_10 = var1_1.openRawResource(var2_9);
            try {
                var1_2 = new byte[1024];
                var2_9 = 0;
                ** GOTO lbl10
            }
            catch (Throwable var1_7) {
                try {
                    var3_10.close();
                    throw var1_7;
                }
                catch (IOException var1_8) {
                    throw new Resources.NotFoundException();
                }
lbl10: // 1 sources:
                do {
                    var4_11 = ((void)var1_3).length - var2_9;
                    var5_12 = var1_3;
                    var6_13 = var4_11;
                    if (var4_11 == 0) {
                        var5_12 = new byte[((void)var1_3).length * 2];
                        System.arraycopy(var1_3, 0, var5_12, 0, ((void)var1_3).length);
                        var6_13 = var5_12.length - var2_9;
                    }
                    if ((var6_13 = var3_10.read(var5_12, var2_9, var6_13)) <= 0) {
                        var3_10.close();
                        try {
                            this.mShader = var1_4 = new String(var5_12, 0, var2_9, "UTF-8");
                            return this;
                        }
                        catch (UnsupportedEncodingException var1_5) {
                            Log.e("RenderScript shader creation", "Could not decode shader string");
                        }
                        return this;
                    }
                    var2_9 += var6_13;
                    var1_6 = var5_12;
                } while (true);
            }
        }

        public BaseProgramBuilder setShader(String string2) {
            this.mShader = string2;
            return this;
        }
    }

    static enum ProgramParam {
        INPUT(0),
        OUTPUT(1),
        CONSTANT(2),
        TEXTURE_TYPE(3);
        
        int mID;

        private ProgramParam(int n2) {
            this.mID = n2;
        }
    }

    public static enum TextureType {
        TEXTURE_2D(0),
        TEXTURE_CUBE(1);
        
        int mID;

        private TextureType(int n2) {
            this.mID = n2;
        }
    }

}

