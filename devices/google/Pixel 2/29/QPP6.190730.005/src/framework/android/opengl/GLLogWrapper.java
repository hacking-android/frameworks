/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import android.opengl.GLWrapperBase;
import java.io.IOException;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL10Ext;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

class GLLogWrapper
extends GLWrapperBase {
    private static final int FORMAT_FIXED = 2;
    private static final int FORMAT_FLOAT = 1;
    private static final int FORMAT_INT = 0;
    private int mArgCount;
    boolean mColorArrayEnabled;
    private PointerInfo mColorPointer = new PointerInfo();
    private Writer mLog;
    private boolean mLogArgumentNames;
    boolean mNormalArrayEnabled;
    private PointerInfo mNormalPointer = new PointerInfo();
    StringBuilder mStringBuilder;
    private PointerInfo mTexCoordPointer = new PointerInfo();
    boolean mTextureCoordArrayEnabled;
    boolean mVertexArrayEnabled;
    private PointerInfo mVertexPointer = new PointerInfo();

    public GLLogWrapper(GL gL, Writer writer, boolean bl) {
        super(gL);
        this.mLog = writer;
        this.mLogArgumentNames = bl;
    }

    private void arg(String string2, float f) {
        this.arg(string2, Float.toString(f));
    }

    private void arg(String string2, int n) {
        this.arg(string2, Integer.toString(n));
    }

    private void arg(String string2, int n, FloatBuffer floatBuffer) {
        this.arg(string2, this.toString(n, floatBuffer));
    }

    private void arg(String string2, int n, IntBuffer intBuffer) {
        this.arg(string2, this.toString(n, 0, intBuffer));
    }

    private void arg(String string2, int n, ShortBuffer shortBuffer) {
        this.arg(string2, this.toString(n, shortBuffer));
    }

    private void arg(String string2, int n, float[] arrf, int n2) {
        this.arg(string2, this.toString(n, arrf, n2));
    }

    private void arg(String string2, int n, int[] arrn, int n2) {
        this.arg(string2, this.toString(n, 0, arrn, n2));
    }

    private void arg(String string2, int n, short[] arrs, int n2) {
        this.arg(string2, this.toString(n, arrs, n2));
    }

    private void arg(String string2, String string3) {
        int n = this.mArgCount;
        this.mArgCount = n + 1;
        if (n > 0) {
            this.log(", ");
        }
        if (this.mLogArgumentNames) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("=");
            this.log(stringBuilder.toString());
        }
        this.log(string3);
    }

    private void arg(String string2, boolean bl) {
        this.arg(string2, Boolean.toString(bl));
    }

    private void argPointer(int n, int n2, int n3, Buffer buffer) {
        this.arg("size", n);
        this.arg("type", this.getPointerTypeName(n2));
        this.arg("stride", n3);
        this.arg("pointer", buffer.toString());
    }

    private void begin(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append('(');
        this.log(stringBuilder.toString());
        this.mArgCount = 0;
    }

    private void bindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.bindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.bindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.bindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.bindByteBuffer();
        }
    }

    private void checkError() {
        int n = this.mgl.glGetError();
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("glError: ");
            stringBuilder.append(Integer.toString(n));
            this.logLine(stringBuilder.toString());
        }
    }

    private void doArrayElement(StringBuilder stringBuilder, boolean bl, String object, PointerInfo pointerInfo, int n) {
        if (!bl) {
            return;
        }
        stringBuilder.append(" ");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append((String)object);
        stringBuilder2.append(":{");
        stringBuilder.append(stringBuilder2.toString());
        if (pointerInfo != null && pointerInfo.mTempByteBuffer != null) {
            if (pointerInfo.mStride < 0) {
                stringBuilder.append("invalid stride");
                return;
            }
            int n2 = pointerInfo.getStride();
            object = pointerInfo.mTempByteBuffer;
            int n3 = pointerInfo.mSize;
            int n4 = pointerInfo.mType;
            int n5 = pointerInfo.sizeof(n4);
            n2 *= n;
            for (n = 0; n < n3; ++n) {
                if (n > 0) {
                    stringBuilder.append(", ");
                }
                if (n4 != 5126) {
                    if (n4 != 5132) {
                        switch (n4) {
                            default: {
                                stringBuilder.append("?");
                                break;
                            }
                            case 5122: {
                                stringBuilder.append(Integer.toString(((ByteBuffer)object).asShortBuffer().get(n2 / 2)));
                                break;
                            }
                            case 5121: {
                                stringBuilder.append(Integer.toString(((ByteBuffer)object).get(n2) & 255));
                                break;
                            }
                            case 5120: {
                                stringBuilder.append(Integer.toString(((ByteBuffer)object).get(n2)));
                                break;
                            }
                        }
                    } else {
                        stringBuilder.append(Integer.toString(((ByteBuffer)object).asIntBuffer().get(n2 / 4)));
                    }
                } else {
                    stringBuilder.append(Float.toString(((ByteBuffer)object).asFloatBuffer().get(n2 / 4)));
                }
                n2 += n5;
            }
            stringBuilder.append("}");
            return;
        }
        stringBuilder.append("undefined }");
    }

    private void doElement(StringBuilder stringBuilder, int n, int n2) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(" [");
        stringBuilder2.append(n);
        stringBuilder2.append(" : ");
        stringBuilder2.append(n2);
        stringBuilder2.append("] =");
        stringBuilder.append(stringBuilder2.toString());
        this.doArrayElement(stringBuilder, this.mVertexArrayEnabled, "v", this.mVertexPointer, n2);
        this.doArrayElement(stringBuilder, this.mNormalArrayEnabled, "n", this.mNormalPointer, n2);
        this.doArrayElement(stringBuilder, this.mColorArrayEnabled, "c", this.mColorPointer, n2);
        this.doArrayElement(stringBuilder, this.mTextureCoordArrayEnabled, "t", this.mTexCoordPointer, n2);
        stringBuilder.append("\n");
    }

    private void end() {
        this.log(");\n");
        this.flush();
    }

    private void endLogIndices() {
        this.log(this.mStringBuilder.toString());
        this.unbindArrays();
    }

    private void flush() {
        try {
            this.mLog.flush();
        }
        catch (IOException iOException) {
            this.mLog = null;
        }
    }

    private void formattedAppend(StringBuilder stringBuilder, int n, int n2) {
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    stringBuilder.append((float)n / 65536.0f);
                }
            } else {
                stringBuilder.append(Float.intBitsToFloat(n));
            }
        } else {
            stringBuilder.append(n);
        }
    }

    private String getBeginMode(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 6: {
                return "GL_TRIANGLE_FAN";
            }
            case 5: {
                return "GL_TRIANGLE_STRIP";
            }
            case 4: {
                return "GL_TRIANGLES";
            }
            case 3: {
                return "GL_LINE_STRIP";
            }
            case 2: {
                return "GL_LINE_LOOP";
            }
            case 1: {
                return "GL_LINES";
            }
            case 0: 
        }
        return "GL_POINTS";
    }

    private String getCap(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        return GLLogWrapper.getHex(n);
                                    }
                                    case 32928: {
                                        return "GL_SAMPLE_COVERAGE";
                                    }
                                    case 32927: {
                                        return "GL_SAMPLE_ALPHA_TO_ONE";
                                    }
                                    case 32926: {
                                        return "GL_SAMPLE_ALPHA_TO_COVERAGE";
                                    }
                                    case 32925: 
                                }
                                return "GL_MULTISAMPLE";
                            }
                            case 32886: {
                                return "GL_COLOR_ARRAY";
                            }
                            case 32885: {
                                return "GL_NORMAL_ARRAY";
                            }
                            case 32884: 
                        }
                        return "GL_VERTEX_ARRAY";
                    }
                    case 16391: {
                        return "GL_LIGHT7";
                    }
                    case 16390: {
                        return "GL_LIGHT6";
                    }
                    case 16389: {
                        return "GL_LIGHT5";
                    }
                    case 16388: {
                        return "GL_LIGHT4";
                    }
                    case 16387: {
                        return "GL_LIGHT3";
                    }
                    case 16386: {
                        return "GL_LIGHT2";
                    }
                    case 16385: {
                        return "GL_LIGHT1";
                    }
                    case 16384: 
                }
                return "GL_LIGHT0";
            }
            case 32888: {
                return "GL_TEXTURE_COORD_ARRAY";
            }
            case 32826: {
                return "GL_RESCALE_NORMAL";
            }
            case 3553: {
                return "GL_TEXTURE_2D";
            }
            case 3089: {
                return "GL_SCISSOR_TEST";
            }
            case 3058: {
                return "GL_COLOR_LOGIC_OP";
            }
            case 3042: {
                return "GL_BLEND";
            }
            case 3024: {
                return "GL_DITHER";
            }
            case 3008: {
                return "GL_ALPHA_TEST";
            }
            case 2977: {
                return "GL_NORMALIZE";
            }
            case 2960: {
                return "GL_STENCIL_TEST";
            }
            case 2929: {
                return "GL_DEPTH_TEST";
            }
            case 2912: {
                return "GL_FOG";
            }
            case 2903: {
                return "GL_COLOR_MATERIAL";
            }
            case 2896: {
                return "GL_LIGHTING";
            }
            case 2884: {
                return "GL_CULL_FACE";
            }
            case 2848: {
                return "GL_LINE_SMOOTH";
            }
            case 2832: 
        }
        return "GL_POINT_SMOOTH";
    }

    private String getClearBufferMask(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = n;
        if ((n & 256) != 0) {
            stringBuilder.append("GL_DEPTH_BUFFER_BIT");
            n2 = n & -257;
        }
        n = n2;
        if ((n2 & 1024) != 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append("GL_STENCIL_BUFFER_BIT");
            n = n2 & -1025;
        }
        n2 = n;
        if ((n & 16384) != 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append("GL_COLOR_BUFFER_BIT");
            n2 = n & -16385;
        }
        if (n2 != 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append(GLLogWrapper.getHex(n2));
        }
        return stringBuilder.toString();
    }

    private String getClientState(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 32888: {
                return "GL_TEXTURE_COORD_ARRAY";
            }
            case 32886: {
                return "GL_COLOR_ARRAY";
            }
            case 32885: {
                return "GL_NORMAL_ARRAY";
            }
            case 32884: 
        }
        return "GL_VERTEX_ARRAY";
    }

    public static String getErrorString(int n) {
        if (n != 0) {
            switch (n) {
                default: {
                    return GLLogWrapper.getHex(n);
                }
                case 1285: {
                    return "GL_OUT_OF_MEMORY";
                }
                case 1284: {
                    return "GL_STACK_UNDERFLOW";
                }
                case 1283: {
                    return "GL_STACK_OVERFLOW";
                }
                case 1282: {
                    return "GL_INVALID_OPERATION";
                }
                case 1281: {
                    return "GL_INVALID_VALUE";
                }
                case 1280: 
            }
            return "GL_INVALID_ENUM";
        }
        return "GL_NO_ERROR";
    }

    private String getFaceName(int n) {
        if (n != 1032) {
            return GLLogWrapper.getHex(n);
        }
        return "GL_FRONT_AND_BACK";
    }

    private String getFactor(int n) {
        if (n != 0) {
            if (n != 1) {
                switch (n) {
                    default: {
                        return GLLogWrapper.getHex(n);
                    }
                    case 776: {
                        return "GL_SRC_ALPHA_SATURATE";
                    }
                    case 775: {
                        return "GL_ONE_MINUS_DST_COLOR";
                    }
                    case 774: {
                        return "GL_DST_COLOR";
                    }
                    case 773: {
                        return "GL_ONE_MINUS_DST_ALPHA";
                    }
                    case 772: {
                        return "GL_DST_ALPHA";
                    }
                    case 771: {
                        return "GL_ONE_MINUS_SRC_ALPHA";
                    }
                    case 770: {
                        return "GL_SRC_ALPHA";
                    }
                    case 769: {
                        return "GL_ONE_MINUS_SRC_COLOR";
                    }
                    case 768: 
                }
                return "GL_SRC_COLOR";
            }
            return "GL_ONE";
        }
        return "GL_ZERO";
    }

    private String getFogPName(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 2918: {
                return "GL_FOG_COLOR";
            }
            case 2917: {
                return "GL_FOG_MODE";
            }
            case 2916: {
                return "GL_FOG_END";
            }
            case 2915: {
                return "GL_FOG_START";
            }
            case 2914: 
        }
        return "GL_FOG_DENSITY";
    }

    private int getFogParamCount(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 2918: {
                return 4;
            }
            case 2917: {
                return 1;
            }
            case 2916: {
                return 1;
            }
            case 2915: {
                return 1;
            }
            case 2914: 
        }
        return 1;
    }

    private static String getHex(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x");
        stringBuilder.append(Integer.toHexString(n));
        return stringBuilder.toString();
    }

    private String getHintMode(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 4354: {
                return "GL_NICEST";
            }
            case 4353: {
                return "GL_FASTEST";
            }
            case 4352: 
        }
        return "GL_DONT_CARE";
    }

    private String getHintTarget(int n) {
        if (n != 33170) {
            switch (n) {
                default: {
                    return GLLogWrapper.getHex(n);
                }
                case 3156: {
                    return "GL_FOG_HINT";
                }
                case 3155: {
                    return "GL_POLYGON_SMOOTH_HINT";
                }
                case 3154: {
                    return "GL_LINE_SMOOTH_HINT";
                }
                case 3153: {
                    return "GL_POINT_SMOOTH_HINT";
                }
                case 3152: 
            }
            return "GL_PERSPECTIVE_CORRECTION_HINT";
        }
        return "GL_GENERATE_MIPMAP_HINT";
    }

    private String getIndexType(int n) {
        if (n != 5121) {
            if (n != 5123) {
                return GLLogWrapper.getHex(n);
            }
            return "GL_UNSIGNED_SHORT";
        }
        return "GL_UNSIGNED_BYTE";
    }

    private int getIntegerStateFormat(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 35213: 
            case 35214: 
            case 35215: 
        }
        return 1;
    }

    private String getIntegerStateName(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 35215: {
                return "GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES";
            }
            case 35214: {
                return "GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES";
            }
            case 35213: {
                return "GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES";
            }
            case 34467: {
                return "GL_COMPRESSED_TEXTURE_FORMATS";
            }
            case 34466: {
                return "GL_NUM_COMPRESSED_TEXTURE_FORMATS";
            }
            case 34018: {
                return "GL_MAX_TEXTURE_UNITS";
            }
            case 33902: {
                return "GL_ALIASED_LINE_WIDTH_RANGE";
            }
            case 33901: {
                return "GL_ALIASED_POINT_SIZE_RANGE";
            }
            case 33001: {
                return "GL_MAX_ELEMENTS_INDICES";
            }
            case 33000: {
                return "GL_MAX_ELEMENTS_VERTICES";
            }
            case 3415: {
                return "GL_STENCIL_BITS";
            }
            case 3414: {
                return "GL_DEPTH_BITS";
            }
            case 3413: {
                return "GL_ALPHA_BITS";
            }
            case 3412: {
                return "GL_BLUE_BITS";
            }
            case 3411: {
                return "GL_GREEN_BITS";
            }
            case 3410: {
                return "GL_RED_BITS";
            }
            case 3408: {
                return "GL_SUBPIXEL_BITS";
            }
            case 3386: {
                return "GL_MAX_VIEWPORT_DIMS";
            }
            case 3385: {
                return "GL_MAX_TEXTURE_STACK_DEPTH";
            }
            case 3384: {
                return "GL_MAX_PROJECTION_STACK_DEPTH";
            }
            case 3382: {
                return "GL_MAX_MODELVIEW_STACK_DEPTH";
            }
            case 3379: {
                return "GL_MAX_TEXTURE_SIZE";
            }
            case 3377: {
                return "GL_MAX_LIGHTS";
            }
            case 2850: {
                return "GL_SMOOTH_LINE_WIDTH_RANGE";
            }
            case 2834: 
        }
        return "GL_SMOOTH_POINT_SIZE_RANGE";
    }

    private int getIntegerStateSize(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 35213: 
            case 35214: 
            case 35215: {
                return 16;
            }
            case 34467: {
                int[] arrn = new int[1];
                this.mgl.glGetIntegerv(34466, arrn, 0);
                return arrn[0];
            }
            case 34466: {
                return 1;
            }
            case 34018: {
                return 1;
            }
            case 33902: {
                return 2;
            }
            case 33901: {
                return 2;
            }
            case 33001: {
                return 1;
            }
            case 33000: {
                return 1;
            }
            case 3415: {
                return 1;
            }
            case 3414: {
                return 1;
            }
            case 3413: {
                return 1;
            }
            case 3412: {
                return 1;
            }
            case 3411: {
                return 1;
            }
            case 3410: {
                return 1;
            }
            case 3408: {
                return 1;
            }
            case 3386: {
                return 2;
            }
            case 3385: {
                return 1;
            }
            case 3384: {
                return 1;
            }
            case 3382: {
                return 1;
            }
            case 3379: {
                return 1;
            }
            case 3377: {
                return 1;
            }
            case 2850: {
                return 2;
            }
            case 2834: 
        }
        return 2;
    }

    private String getLightModelPName(int n) {
        if (n != 2898) {
            if (n != 2899) {
                return GLLogWrapper.getHex(n);
            }
            return "GL_LIGHT_MODEL_AMBIENT";
        }
        return "GL_LIGHT_MODEL_TWO_SIDE";
    }

    private int getLightModelParamCount(int n) {
        if (n != 2898) {
            if (n != 2899) {
                return 0;
            }
            return 4;
        }
        return 1;
    }

    private String getLightName(int n) {
        if (n >= 16384 && n <= 16391) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("GL_LIGHT");
            stringBuilder.append(Integer.toString(n));
            return stringBuilder.toString();
        }
        return GLLogWrapper.getHex(n);
    }

    private String getLightPName(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 4617: {
                return "GL_QUADRATIC_ATTENUATION";
            }
            case 4616: {
                return "GL_LINEAR_ATTENUATION";
            }
            case 4615: {
                return "GL_CONSTANT_ATTENUATION";
            }
            case 4614: {
                return "GL_SPOT_CUTOFF";
            }
            case 4613: {
                return "GL_SPOT_EXPONENT";
            }
            case 4612: {
                return "GL_SPOT_DIRECTION";
            }
            case 4611: {
                return "GL_POSITION";
            }
            case 4610: {
                return "GL_SPECULAR";
            }
            case 4609: {
                return "GL_DIFFUSE";
            }
            case 4608: 
        }
        return "GL_AMBIENT";
    }

    private int getLightParamCount(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 4617: {
                return 1;
            }
            case 4616: {
                return 1;
            }
            case 4615: {
                return 1;
            }
            case 4614: {
                return 1;
            }
            case 4613: {
                return 1;
            }
            case 4612: {
                return 3;
            }
            case 4611: {
                return 4;
            }
            case 4610: {
                return 4;
            }
            case 4609: {
                return 4;
            }
            case 4608: 
        }
        return 4;
    }

    private String getMaterialPName(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        return GLLogWrapper.getHex(n);
                    }
                    case 5634: {
                        return "GL_AMBIENT_AND_DIFFUSE";
                    }
                    case 5633: {
                        return "GL_SHININESS";
                    }
                    case 5632: 
                }
                return "GL_EMISSION";
            }
            case 4610: {
                return "GL_SPECULAR";
            }
            case 4609: {
                return "GL_DIFFUSE";
            }
            case 4608: 
        }
        return "GL_AMBIENT";
    }

    private int getMaterialParamCount(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        return 0;
                    }
                    case 5634: {
                        return 4;
                    }
                    case 5633: {
                        return 1;
                    }
                    case 5632: 
                }
                return 4;
            }
            case 4610: {
                return 4;
            }
            case 4609: {
                return 4;
            }
            case 4608: 
        }
        return 4;
    }

    private String getMatrixMode(int n) {
        switch (n) {
            default: {
                return GLLogWrapper.getHex(n);
            }
            case 5890: {
                return "GL_TEXTURE";
            }
            case 5889: {
                return "GL_PROJECTION";
            }
            case 5888: 
        }
        return "GL_MODELVIEW";
    }

    private String getPointerTypeName(int n) {
        if (n != 5126) {
            if (n != 5132) {
                switch (n) {
                    default: {
                        return GLLogWrapper.getHex(n);
                    }
                    case 5122: {
                        return "GL_SHORT";
                    }
                    case 5121: {
                        return "GL_UNSIGNED_BYTE";
                    }
                    case 5120: 
                }
                return "GL_BYTE";
            }
            return "GL_FIXED";
        }
        return "GL_FLOAT";
    }

    private String getShadeModel(int n) {
        if (n != 7424) {
            if (n != 7425) {
                return GLLogWrapper.getHex(n);
            }
            return "GL_SMOOTH";
        }
        return "GL_FLAT";
    }

    private String getTextureEnvPName(int n) {
        if (n != 8704) {
            if (n != 8705) {
                return GLLogWrapper.getHex(n);
            }
            return "GL_TEXTURE_ENV_COLOR";
        }
        return "GL_TEXTURE_ENV_MODE";
    }

    private int getTextureEnvParamCount(int n) {
        if (n != 8704) {
            if (n != 8705) {
                return 0;
            }
            return 4;
        }
        return 1;
    }

    private String getTextureEnvParamName(float f) {
        int n = (int)f;
        if (f == (float)n) {
            if (n != 260) {
                if (n != 3042) {
                    if (n != 7681) {
                        if (n != 34160) {
                            if (n != 8448) {
                                if (n != 8449) {
                                    return GLLogWrapper.getHex(n);
                                }
                                return "GL_DECAL";
                            }
                            return "GL_MODULATE";
                        }
                        return "GL_COMBINE";
                    }
                    return "GL_REPLACE";
                }
                return "GL_BLEND";
            }
            return "GL_ADD";
        }
        return Float.toString(f);
    }

    private String getTextureEnvTarget(int n) {
        if (n != 8960) {
            return GLLogWrapper.getHex(n);
        }
        return "GL_TEXTURE_ENV";
    }

    private String getTexturePName(int n) {
        if (n != 33169) {
            if (n != 35741) {
                switch (n) {
                    default: {
                        return GLLogWrapper.getHex(n);
                    }
                    case 10243: {
                        return "GL_TEXTURE_WRAP_T";
                    }
                    case 10242: {
                        return "GL_TEXTURE_WRAP_S";
                    }
                    case 10241: {
                        return "GL_TEXTURE_MIN_FILTER";
                    }
                    case 10240: 
                }
                return "GL_TEXTURE_MAG_FILTER";
            }
            return "GL_TEXTURE_CROP_RECT_OES";
        }
        return "GL_GENERATE_MIPMAP";
    }

    private String getTextureParamName(float f) {
        int n = (int)f;
        if (f == (float)n) {
            if (n != 9728) {
                if (n != 9729) {
                    if (n != 10497) {
                        if (n != 33071) {
                            switch (n) {
                                default: {
                                    return GLLogWrapper.getHex(n);
                                }
                                case 9987: {
                                    return "GL_LINEAR_MIPMAP_LINEAR";
                                }
                                case 9986: {
                                    return "GL_NEAREST_MIPMAP_LINEAR";
                                }
                                case 9985: {
                                    return "GL_LINEAR_MIPMAP_NEAREST";
                                }
                                case 9984: 
                            }
                            return "GL_NEAREST_MIPMAP_NEAREST";
                        }
                        return "GL_CLAMP_TO_EDGE";
                    }
                    return "GL_REPEAT";
                }
                return "GL_LINEAR";
            }
            return "GL_NEAREST";
        }
        return Float.toString(f);
    }

    private String getTextureTarget(int n) {
        if (n != 3553) {
            return GLLogWrapper.getHex(n);
        }
        return "GL_TEXTURE_2D";
    }

    private void log(String string2) {
        try {
            this.mLog.write(string2);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void logLine(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append('\n');
        this.log(stringBuilder.toString());
    }

    private void returns(int n) {
        this.returns(Integer.toString(n));
    }

    private void returns(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(") returns ");
        stringBuilder.append(string2);
        stringBuilder.append(";\n");
        this.log(stringBuilder.toString());
        this.flush();
    }

    private void startLogIndices() {
        this.mStringBuilder = new StringBuilder();
        this.mStringBuilder.append("\n");
        this.bindArrays();
    }

    private ByteBuffer toByteBuffer(int n, Buffer buffer) {
        block24 : {
            block18 : {
                int n2;
                block23 : {
                    block22 : {
                        block21 : {
                            block20 : {
                                block19 : {
                                    block17 : {
                                        n2 = n < 0 ? 1 : 0;
                                        if (!(buffer instanceof ByteBuffer)) break block17;
                                        ByteBuffer byteBuffer = (ByteBuffer)buffer;
                                        int n3 = byteBuffer.position();
                                        if (n2 != 0) {
                                            n = byteBuffer.limit() - n3;
                                        }
                                        buffer = ByteBuffer.allocate(n).order(byteBuffer.order());
                                        for (n2 = 0; n2 < n; ++n2) {
                                            ((ByteBuffer)buffer).put(byteBuffer.get());
                                        }
                                        byteBuffer.position(n3);
                                        break block18;
                                    }
                                    if (!(buffer instanceof CharBuffer)) break block19;
                                    CharBuffer charBuffer = (CharBuffer)buffer;
                                    int n4 = charBuffer.position();
                                    if (n2 != 0) {
                                        n = (charBuffer.limit() - n4) * 2;
                                    }
                                    buffer = ByteBuffer.allocate(n).order(charBuffer.order());
                                    CharBuffer charBuffer2 = ((ByteBuffer)buffer).asCharBuffer();
                                    for (n2 = 0; n2 < n / 2; ++n2) {
                                        charBuffer2.put(charBuffer.get());
                                    }
                                    charBuffer.position(n4);
                                    break block18;
                                }
                                if (!(buffer instanceof ShortBuffer)) break block20;
                                ShortBuffer shortBuffer = (ShortBuffer)buffer;
                                int n5 = shortBuffer.position();
                                if (n2 != 0) {
                                    n = (shortBuffer.limit() - n5) * 2;
                                }
                                buffer = ByteBuffer.allocate(n).order(shortBuffer.order());
                                ShortBuffer shortBuffer2 = ((ByteBuffer)buffer).asShortBuffer();
                                for (n2 = 0; n2 < n / 2; ++n2) {
                                    shortBuffer2.put(shortBuffer.get());
                                }
                                shortBuffer.position(n5);
                                break block18;
                            }
                            if (!(buffer instanceof IntBuffer)) break block21;
                            IntBuffer intBuffer = (IntBuffer)buffer;
                            int n6 = intBuffer.position();
                            if (n2 != 0) {
                                n = (intBuffer.limit() - n6) * 4;
                            }
                            buffer = ByteBuffer.allocate(n).order(intBuffer.order());
                            IntBuffer intBuffer2 = ((ByteBuffer)buffer).asIntBuffer();
                            for (n2 = 0; n2 < n / 4; ++n2) {
                                intBuffer2.put(intBuffer.get());
                            }
                            intBuffer.position(n6);
                            break block18;
                        }
                        if (!(buffer instanceof FloatBuffer)) break block22;
                        FloatBuffer floatBuffer = (FloatBuffer)buffer;
                        int n7 = floatBuffer.position();
                        if (n2 != 0) {
                            n = (floatBuffer.limit() - n7) * 4;
                        }
                        buffer = ByteBuffer.allocate(n).order(floatBuffer.order());
                        FloatBuffer floatBuffer2 = ((ByteBuffer)buffer).asFloatBuffer();
                        for (n2 = 0; n2 < n / 4; ++n2) {
                            floatBuffer2.put(floatBuffer.get());
                        }
                        floatBuffer.position(n7);
                        break block18;
                    }
                    if (!(buffer instanceof DoubleBuffer)) break block23;
                    DoubleBuffer doubleBuffer = (DoubleBuffer)buffer;
                    int n8 = doubleBuffer.position();
                    if (n2 != 0) {
                        n = (doubleBuffer.limit() - n8) * 8;
                    }
                    buffer = ByteBuffer.allocate(n).order(doubleBuffer.order());
                    DoubleBuffer doubleBuffer2 = ((ByteBuffer)buffer).asDoubleBuffer();
                    for (n2 = 0; n2 < n / 8; ++n2) {
                        doubleBuffer2.put(doubleBuffer.get());
                    }
                    doubleBuffer.position(n8);
                    break block18;
                }
                if (!(buffer instanceof LongBuffer)) break block24;
                LongBuffer longBuffer = (LongBuffer)buffer;
                int n9 = longBuffer.position();
                if (n2 != 0) {
                    n = (longBuffer.limit() - n9) * 8;
                }
                buffer = ByteBuffer.allocate(n).order(longBuffer.order());
                LongBuffer longBuffer2 = ((ByteBuffer)buffer).asLongBuffer();
                for (n2 = 0; n2 < n / 8; ++n2) {
                    longBuffer2.put(longBuffer.get());
                }
                longBuffer.position(n9);
            }
            buffer.rewind();
            ((ByteBuffer)buffer).order(ByteOrder.nativeOrder());
            return buffer;
        }
        throw new RuntimeException("Unimplemented Buffer subclass.");
    }

    private char[] toCharIndices(int n, int n2, Buffer buffer) {
        char[] arrc = new char[n];
        if (n2 != 5121) {
            if (n2 == 5123) {
                buffer = buffer instanceof CharBuffer ? (CharBuffer)buffer : this.toByteBuffer(n * 2, buffer).asCharBuffer();
                n = buffer.position();
                buffer.position(0);
                ((CharBuffer)buffer).get(arrc);
                buffer.position(n);
            }
        } else {
            buffer = this.toByteBuffer(n, buffer);
            byte[] arrby = ((ByteBuffer)buffer).array();
            int n3 = ((ByteBuffer)buffer).arrayOffset();
            for (n2 = 0; n2 < n; ++n2) {
                arrc[n2] = (char)(arrby[n3 + n2] & 255);
            }
        }
        return arrc;
    }

    private String toString(int n, int n2, IntBuffer intBuffer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(i);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            this.formattedAppend(stringBuilder, intBuffer.get(i), n2);
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, int n2, int[] arrn, int n3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        int n4 = arrn.length;
        for (int i = 0; i < n; ++i) {
            int n5 = n3 + i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(n5);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            if (n5 >= 0 && n5 < n4) {
                this.formattedAppend(stringBuilder, arrn[n5], n2);
            } else {
                stringBuilder.append("out of bounds");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, FloatBuffer floatBuffer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(i);
            stringBuilder2.append("] = ");
            stringBuilder2.append(floatBuffer.get(i));
            stringBuilder2.append('\n');
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, ShortBuffer shortBuffer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < n; ++i) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(i);
            stringBuilder2.append("] = ");
            stringBuilder2.append(shortBuffer.get(i));
            stringBuilder2.append('\n');
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, float[] arrf, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        int n3 = arrf.length;
        for (int i = 0; i < n; ++i) {
            int n4 = n2 + i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("[");
            stringBuilder2.append(n4);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            if (n4 >= 0 && n4 < n3) {
                stringBuilder.append(arrf[n4]);
            } else {
                stringBuilder.append("out of bounds");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String toString(int n, short[] arrs, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        int n3 = arrs.length;
        for (int i = 0; i < n; ++i) {
            int n4 = n2 + i;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" [");
            stringBuilder2.append(n4);
            stringBuilder2.append("] = ");
            stringBuilder.append(stringBuilder2.toString());
            if (n4 >= 0 && n4 < n3) {
                stringBuilder.append(arrs[n4]);
            } else {
                stringBuilder.append("out of bounds");
            }
            stringBuilder.append('\n');
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private void unbindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.unbindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.unbindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.unbindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.unbindByteBuffer();
        }
    }

    @Override
    public void glActiveTexture(int n) {
        this.begin("glActiveTexture");
        this.arg("texture", n);
        this.end();
        this.mgl.glActiveTexture(n);
        this.checkError();
    }

    @Override
    public void glAlphaFunc(int n, float f) {
        this.begin("glAlphaFunc");
        this.arg("func", n);
        this.arg("ref", f);
        this.end();
        this.mgl.glAlphaFunc(n, f);
        this.checkError();
    }

    @Override
    public void glAlphaFuncx(int n, int n2) {
        this.begin("glAlphaFuncx");
        this.arg("func", n);
        this.arg("ref", n2);
        this.end();
        this.mgl.glAlphaFuncx(n, n2);
        this.checkError();
    }

    @Override
    public void glBindBuffer(int n, int n2) {
        this.begin("glBindBuffer");
        this.arg("target", n);
        this.arg("buffer", n2);
        this.end();
        this.mgl11.glBindBuffer(n, n2);
        this.checkError();
    }

    @Override
    public void glBindFramebufferOES(int n, int n2) {
        this.begin("glBindFramebufferOES");
        this.arg("target", n);
        this.arg("framebuffer", n2);
        this.end();
        this.mgl11ExtensionPack.glBindFramebufferOES(n, n2);
        this.checkError();
    }

    @Override
    public void glBindRenderbufferOES(int n, int n2) {
        this.begin("glBindRenderbufferOES");
        this.arg("target", n);
        this.arg("renderbuffer", n2);
        this.end();
        this.mgl11ExtensionPack.glBindRenderbufferOES(n, n2);
        this.checkError();
    }

    @Override
    public void glBindTexture(int n, int n2) {
        this.begin("glBindTexture");
        this.arg("target", this.getTextureTarget(n));
        this.arg("texture", n2);
        this.end();
        this.mgl.glBindTexture(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendEquation(int n) {
        this.begin("glBlendEquation");
        this.arg("mode", n);
        this.end();
        this.mgl11ExtensionPack.glBlendEquation(n);
        this.checkError();
    }

    @Override
    public void glBlendEquationSeparate(int n, int n2) {
        this.begin("glBlendEquationSeparate");
        this.arg("modeRGB", n);
        this.arg("modeAlpha", n2);
        this.end();
        this.mgl11ExtensionPack.glBlendEquationSeparate(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendFunc(int n, int n2) {
        this.begin("glBlendFunc");
        this.arg("sfactor", this.getFactor(n));
        this.arg("dfactor", this.getFactor(n2));
        this.end();
        this.mgl.glBlendFunc(n, n2);
        this.checkError();
    }

    @Override
    public void glBlendFuncSeparate(int n, int n2, int n3, int n4) {
        this.begin("glBlendFuncSeparate");
        this.arg("srcRGB", n);
        this.arg("dstRGB", n2);
        this.arg("srcAlpha", n3);
        this.arg("dstAlpha", n4);
        this.end();
        this.mgl11ExtensionPack.glBlendFuncSeparate(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glBufferData(int n, int n2, Buffer buffer, int n3) {
        this.begin("glBufferData");
        this.arg("target", n);
        this.arg("size", n2);
        this.arg("data", buffer.toString());
        this.arg("usage", n3);
        this.end();
        this.mgl11.glBufferData(n, n2, buffer, n3);
        this.checkError();
    }

    @Override
    public void glBufferSubData(int n, int n2, int n3, Buffer buffer) {
        this.begin("glBufferSubData");
        this.arg("target", n);
        this.arg("offset", n2);
        this.arg("size", n3);
        this.arg("data", buffer.toString());
        this.end();
        this.mgl11.glBufferSubData(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public int glCheckFramebufferStatusOES(int n) {
        this.begin("glCheckFramebufferStatusOES");
        this.arg("target", n);
        this.end();
        n = this.mgl11ExtensionPack.glCheckFramebufferStatusOES(n);
        this.checkError();
        return n;
    }

    @Override
    public void glClear(int n) {
        this.begin("glClear");
        this.arg("mask", this.getClearBufferMask(n));
        this.end();
        this.mgl.glClear(n);
        this.checkError();
    }

    @Override
    public void glClearColor(float f, float f2, float f3, float f4) {
        this.begin("glClearColor");
        this.arg("red", f);
        this.arg("green", f2);
        this.arg("blue", f3);
        this.arg("alpha", f4);
        this.end();
        this.mgl.glClearColor(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glClearColorx(int n, int n2, int n3, int n4) {
        this.begin("glClearColor");
        this.arg("red", n);
        this.arg("green", n2);
        this.arg("blue", n3);
        this.arg("alpha", n4);
        this.end();
        this.mgl.glClearColorx(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glClearDepthf(float f) {
        this.begin("glClearDepthf");
        this.arg("depth", f);
        this.end();
        this.mgl.glClearDepthf(f);
        this.checkError();
    }

    @Override
    public void glClearDepthx(int n) {
        this.begin("glClearDepthx");
        this.arg("depth", n);
        this.end();
        this.mgl.glClearDepthx(n);
        this.checkError();
    }

    @Override
    public void glClearStencil(int n) {
        this.begin("glClearStencil");
        this.arg("s", n);
        this.end();
        this.mgl.glClearStencil(n);
        this.checkError();
    }

    @Override
    public void glClientActiveTexture(int n) {
        this.begin("glClientActiveTexture");
        this.arg("texture", n);
        this.end();
        this.mgl.glClientActiveTexture(n);
        this.checkError();
    }

    @Override
    public void glClipPlanef(int n, FloatBuffer floatBuffer) {
        this.begin("glClipPlanef");
        this.arg("plane", n);
        this.arg("equation", 4, floatBuffer);
        this.end();
        this.mgl11.glClipPlanef(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glClipPlanef(int n, float[] arrf, int n2) {
        this.begin("glClipPlanef");
        this.arg("plane", n);
        this.arg("equation", 4, arrf, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl11.glClipPlanef(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glClipPlanex(int n, IntBuffer intBuffer) {
        this.begin("glClipPlanef");
        this.arg("plane", n);
        this.arg("equation", 4, intBuffer);
        this.end();
        this.mgl11.glClipPlanex(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glClipPlanex(int n, int[] arrn, int n2) {
        this.begin("glClipPlanex");
        this.arg("plane", n);
        this.arg("equation", 4, arrn, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl11.glClipPlanex(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glColor4f(float f, float f2, float f3, float f4) {
        this.begin("glColor4f");
        this.arg("red", f);
        this.arg("green", f2);
        this.arg("blue", f3);
        this.arg("alpha", f4);
        this.end();
        this.mgl.glColor4f(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glColor4ub(byte by, byte by2, byte by3, byte by4) {
        this.begin("glColor4ub");
        this.arg("red", by);
        this.arg("green", by2);
        this.arg("blue", by3);
        this.arg("alpha", by4);
        this.end();
        this.mgl11.glColor4ub(by, by2, by3, by4);
        this.checkError();
    }

    @Override
    public void glColor4x(int n, int n2, int n3, int n4) {
        this.begin("glColor4x");
        this.arg("red", n);
        this.arg("green", n2);
        this.arg("blue", n3);
        this.arg("alpha", n4);
        this.end();
        this.mgl.glColor4x(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glColorMask(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.begin("glColorMask");
        this.arg("red", bl);
        this.arg("green", bl2);
        this.arg("blue", bl3);
        this.arg("alpha", bl4);
        this.end();
        this.mgl.glColorMask(bl, bl2, bl3, bl4);
        this.checkError();
    }

    @Override
    public void glColorPointer(int n, int n2, int n3, int n4) {
        this.begin("glColorPointer");
        this.arg("size", n);
        this.arg("type", n2);
        this.arg("stride", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11.glColorPointer(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glColorPointer(int n, int n2, int n3, Buffer buffer) {
        this.begin("glColorPointer");
        this.argPointer(n, n2, n3, buffer);
        this.end();
        this.mColorPointer = new PointerInfo(n, n2, n3, buffer);
        this.mgl.glColorPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glCompressedTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, Buffer buffer) {
        this.begin("glCompressedTexImage2D");
        this.arg("target", this.getTextureTarget(n));
        this.arg("level", n2);
        this.arg("internalformat", n3);
        this.arg("width", n4);
        this.arg("height", n5);
        this.arg("border", n6);
        this.arg("imageSize", n7);
        this.arg("data", buffer.toString());
        this.end();
        this.mgl.glCompressedTexImage2D(n, n2, n3, n4, n5, n6, n7, buffer);
        this.checkError();
    }

    @Override
    public void glCompressedTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.begin("glCompressedTexSubImage2D");
        this.arg("target", this.getTextureTarget(n));
        this.arg("level", n2);
        this.arg("xoffset", n3);
        this.arg("yoffset", n4);
        this.arg("width", n5);
        this.arg("height", n6);
        this.arg("format", n7);
        this.arg("imageSize", n8);
        this.arg("data", buffer.toString());
        this.end();
        this.mgl.glCompressedTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glCopyTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.begin("glCopyTexImage2D");
        this.arg("target", this.getTextureTarget(n));
        this.arg("level", n2);
        this.arg("internalformat", n3);
        this.arg("x", n4);
        this.arg("y", n5);
        this.arg("width", n6);
        this.arg("height", n7);
        this.arg("border", n8);
        this.end();
        this.mgl.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.checkError();
    }

    @Override
    public void glCopyTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.begin("glCopyTexSubImage2D");
        this.arg("target", this.getTextureTarget(n));
        this.arg("level", n2);
        this.arg("xoffset", n3);
        this.arg("yoffset", n4);
        this.arg("x", n5);
        this.arg("y", n6);
        this.arg("width", n7);
        this.arg("height", n8);
        this.end();
        this.mgl.glCopyTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8);
        this.checkError();
    }

    @Override
    public void glCullFace(int n) {
        this.begin("glCullFace");
        this.arg("mode", n);
        this.end();
        this.mgl.glCullFace(n);
        this.checkError();
    }

    @Override
    public void glCurrentPaletteMatrixOES(int n) {
        this.begin("glCurrentPaletteMatrixOES");
        this.arg("matrixpaletteindex", n);
        this.end();
        this.mgl11Ext.glCurrentPaletteMatrixOES(n);
        this.checkError();
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer intBuffer) {
        this.begin("glDeleteBuffers");
        this.arg("n", n);
        this.arg("buffers", intBuffer.toString());
        this.end();
        this.mgl11.glDeleteBuffers(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteBuffers(int n, int[] arrn, int n2) {
        this.begin("glDeleteBuffers");
        this.arg("n", n);
        this.arg("buffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glDeleteBuffers(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteFramebuffersOES(int n, IntBuffer intBuffer) {
        this.begin("glDeleteFramebuffersOES");
        this.arg("n", n);
        this.arg("framebuffers", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteFramebuffersOES(int n, int[] arrn, int n2) {
        this.begin("glDeleteFramebuffersOES");
        this.arg("n", n);
        this.arg("framebuffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteRenderbuffersOES(int n, IntBuffer intBuffer) {
        this.begin("glDeleteRenderbuffersOES");
        this.arg("n", n);
        this.arg("renderbuffers", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteRenderbuffersOES(int n, int[] arrn, int n2) {
        this.begin("glDeleteRenderbuffersOES");
        this.arg("n", n);
        this.arg("renderbuffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDeleteTextures(int n, IntBuffer intBuffer) {
        this.begin("glDeleteTextures");
        this.arg("n", n);
        this.arg("textures", n, intBuffer);
        this.end();
        this.mgl.glDeleteTextures(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glDeleteTextures(int n, int[] arrn, int n2) {
        this.begin("glDeleteTextures");
        this.arg("n", n);
        this.arg("textures", n, arrn, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl.glDeleteTextures(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glDepthFunc(int n) {
        this.begin("glDepthFunc");
        this.arg("func", n);
        this.end();
        this.mgl.glDepthFunc(n);
        this.checkError();
    }

    @Override
    public void glDepthMask(boolean bl) {
        this.begin("glDepthMask");
        this.arg("flag", bl);
        this.end();
        this.mgl.glDepthMask(bl);
        this.checkError();
    }

    @Override
    public void glDepthRangef(float f, float f2) {
        this.begin("glDepthRangef");
        this.arg("near", f);
        this.arg("far", f2);
        this.end();
        this.mgl.glDepthRangef(f, f2);
        this.checkError();
    }

    @Override
    public void glDepthRangex(int n, int n2) {
        this.begin("glDepthRangex");
        this.arg("near", n);
        this.arg("far", n2);
        this.end();
        this.mgl.glDepthRangex(n, n2);
        this.checkError();
    }

    @Override
    public void glDisable(int n) {
        this.begin("glDisable");
        this.arg("cap", this.getCap(n));
        this.end();
        this.mgl.glDisable(n);
        this.checkError();
    }

    @Override
    public void glDisableClientState(int n) {
        this.begin("glDisableClientState");
        this.arg("array", this.getClientState(n));
        this.end();
        switch (n) {
            default: {
                break;
            }
            case 32888: {
                this.mTextureCoordArrayEnabled = false;
                break;
            }
            case 32886: {
                this.mColorArrayEnabled = false;
                break;
            }
            case 32885: {
                this.mNormalArrayEnabled = false;
                break;
            }
            case 32884: {
                this.mVertexArrayEnabled = false;
            }
        }
        this.mgl.glDisableClientState(n);
        this.checkError();
    }

    @Override
    public void glDrawArrays(int n, int n2, int n3) {
        this.begin("glDrawArrays");
        this.arg("mode", n);
        this.arg("first", n2);
        this.arg("count", n3);
        this.startLogIndices();
        for (int i = 0; i < n3; ++i) {
            this.doElement(this.mStringBuilder, i, n2 + i);
        }
        this.endLogIndices();
        this.end();
        this.mgl.glDrawArrays(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glDrawElements(int n, int n2, int n3, int n4) {
        this.begin("glDrawElements");
        this.arg("mode", n);
        this.arg("count", n2);
        this.arg("type", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11.glDrawElements(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glDrawElements(int n, int n2, int n3, Buffer buffer) {
        this.begin("glDrawElements");
        this.arg("mode", this.getBeginMode(n));
        this.arg("count", n2);
        this.arg("type", this.getIndexType(n3));
        char[] arrc = this.toCharIndices(n2, n3, buffer);
        int n4 = arrc.length;
        this.startLogIndices();
        for (int i = 0; i < n4; ++i) {
            this.doElement(this.mStringBuilder, i, arrc[i]);
        }
        this.endLogIndices();
        this.end();
        this.mgl.glDrawElements(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glDrawTexfOES(float f, float f2, float f3, float f4, float f5) {
        this.begin("glDrawTexfOES");
        this.arg("x", f);
        this.arg("y", f2);
        this.arg("z", f3);
        this.arg("width", f4);
        this.arg("height", f5);
        this.end();
        this.mgl11Ext.glDrawTexfOES(f, f2, f3, f4, f5);
        this.checkError();
    }

    @Override
    public void glDrawTexfvOES(FloatBuffer floatBuffer) {
        this.begin("glDrawTexfvOES");
        this.arg("coords", 5, floatBuffer);
        this.end();
        this.mgl11Ext.glDrawTexfvOES(floatBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexfvOES(float[] arrf, int n) {
        this.begin("glDrawTexfvOES");
        this.arg("coords", 5, arrf, n);
        this.arg("offset", n);
        this.end();
        this.mgl11Ext.glDrawTexfvOES(arrf, n);
        this.checkError();
    }

    @Override
    public void glDrawTexiOES(int n, int n2, int n3, int n4, int n5) {
        this.begin("glDrawTexiOES");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("z", n3);
        this.arg("width", n4);
        this.arg("height", n5);
        this.end();
        this.mgl11Ext.glDrawTexiOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glDrawTexivOES(IntBuffer intBuffer) {
        this.begin("glDrawTexivOES");
        this.arg("coords", 5, intBuffer);
        this.end();
        this.mgl11Ext.glDrawTexivOES(intBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexivOES(int[] arrn, int n) {
        this.begin("glDrawTexivOES");
        this.arg("coords", 5, arrn, n);
        this.arg("offset", n);
        this.end();
        this.mgl11Ext.glDrawTexivOES(arrn, n);
        this.checkError();
    }

    @Override
    public void glDrawTexsOES(short s, short s2, short s3, short s4, short s5) {
        this.begin("glDrawTexsOES");
        this.arg("x", s);
        this.arg("y", s2);
        this.arg("z", s3);
        this.arg("width", s4);
        this.arg("height", s5);
        this.end();
        this.mgl11Ext.glDrawTexsOES(s, s2, s3, s4, s5);
        this.checkError();
    }

    @Override
    public void glDrawTexsvOES(ShortBuffer shortBuffer) {
        this.begin("glDrawTexsvOES");
        this.arg("coords", 5, shortBuffer);
        this.end();
        this.mgl11Ext.glDrawTexsvOES(shortBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexsvOES(short[] arrs, int n) {
        this.begin("glDrawTexsvOES");
        this.arg("coords", 5, arrs, n);
        this.arg("offset", n);
        this.end();
        this.mgl11Ext.glDrawTexsvOES(arrs, n);
        this.checkError();
    }

    @Override
    public void glDrawTexxOES(int n, int n2, int n3, int n4, int n5) {
        this.begin("glDrawTexxOES");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("z", n3);
        this.arg("width", n4);
        this.arg("height", n5);
        this.end();
        this.mgl11Ext.glDrawTexxOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glDrawTexxvOES(IntBuffer intBuffer) {
        this.begin("glDrawTexxvOES");
        this.arg("coords", 5, intBuffer);
        this.end();
        this.mgl11Ext.glDrawTexxvOES(intBuffer);
        this.checkError();
    }

    @Override
    public void glDrawTexxvOES(int[] arrn, int n) {
        this.begin("glDrawTexxvOES");
        this.arg("coords", 5, arrn, n);
        this.arg("offset", n);
        this.end();
        this.mgl11Ext.glDrawTexxvOES(arrn, n);
        this.checkError();
    }

    @Override
    public void glEnable(int n) {
        this.begin("glEnable");
        this.arg("cap", this.getCap(n));
        this.end();
        this.mgl.glEnable(n);
        this.checkError();
    }

    @Override
    public void glEnableClientState(int n) {
        this.begin("glEnableClientState");
        this.arg("array", this.getClientState(n));
        this.end();
        switch (n) {
            default: {
                break;
            }
            case 32888: {
                this.mTextureCoordArrayEnabled = true;
                break;
            }
            case 32886: {
                this.mColorArrayEnabled = true;
                break;
            }
            case 32885: {
                this.mNormalArrayEnabled = true;
                break;
            }
            case 32884: {
                this.mVertexArrayEnabled = true;
            }
        }
        this.mgl.glEnableClientState(n);
        this.checkError();
    }

    @Override
    public void glFinish() {
        this.begin("glFinish");
        this.end();
        this.mgl.glFinish();
        this.checkError();
    }

    @Override
    public void glFlush() {
        this.begin("glFlush");
        this.end();
        this.mgl.glFlush();
        this.checkError();
    }

    @Override
    public void glFogf(int n, float f) {
        this.begin("glFogf");
        this.arg("pname", n);
        this.arg("param", f);
        this.end();
        this.mgl.glFogf(n, f);
        this.checkError();
    }

    @Override
    public void glFogfv(int n, FloatBuffer floatBuffer) {
        this.begin("glFogfv");
        this.arg("pname", this.getFogPName(n));
        this.arg("params", this.getFogParamCount(n), floatBuffer);
        this.end();
        this.mgl.glFogfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glFogfv(int n, float[] arrf, int n2) {
        this.begin("glFogfv");
        this.arg("pname", this.getFogPName(n));
        this.arg("params", this.getFogParamCount(n), arrf, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl.glFogfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glFogx(int n, int n2) {
        this.begin("glFogx");
        this.arg("pname", this.getFogPName(n));
        this.arg("param", n2);
        this.end();
        this.mgl.glFogx(n, n2);
        this.checkError();
    }

    @Override
    public void glFogxv(int n, IntBuffer intBuffer) {
        this.begin("glFogxv");
        this.arg("pname", this.getFogPName(n));
        this.arg("params", this.getFogParamCount(n), intBuffer);
        this.end();
        this.mgl.glFogxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glFogxv(int n, int[] arrn, int n2) {
        this.begin("glFogxv");
        this.arg("pname", this.getFogPName(n));
        this.arg("params", this.getFogParamCount(n), arrn, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl.glFogxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glFramebufferRenderbufferOES(int n, int n2, int n3, int n4) {
        this.begin("glFramebufferRenderbufferOES");
        this.arg("target", n);
        this.arg("attachment", n2);
        this.arg("renderbuffertarget", n3);
        this.arg("renderbuffer", n4);
        this.end();
        this.mgl11ExtensionPack.glFramebufferRenderbufferOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glFramebufferTexture2DOES(int n, int n2, int n3, int n4, int n5) {
        this.begin("glFramebufferTexture2DOES");
        this.arg("target", n);
        this.arg("attachment", n2);
        this.arg("textarget", n3);
        this.arg("texture", n4);
        this.arg("level", n5);
        this.end();
        this.mgl11ExtensionPack.glFramebufferTexture2DOES(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glFrontFace(int n) {
        this.begin("glFrontFace");
        this.arg("mode", n);
        this.end();
        this.mgl.glFrontFace(n);
        this.checkError();
    }

    @Override
    public void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6) {
        this.begin("glFrustumf");
        this.arg("left", f);
        this.arg("right", f2);
        this.arg("bottom", f3);
        this.arg("top", f4);
        this.arg("near", f5);
        this.arg("far", f6);
        this.end();
        this.mgl.glFrustumf(f, f2, f3, f4, f5, f6);
        this.checkError();
    }

    @Override
    public void glFrustumx(int n, int n2, int n3, int n4, int n5, int n6) {
        this.begin("glFrustumx");
        this.arg("left", n);
        this.arg("right", n2);
        this.arg("bottom", n3);
        this.arg("top", n4);
        this.arg("near", n5);
        this.arg("far", n6);
        this.end();
        this.mgl.glFrustumx(n, n2, n3, n4, n5, n6);
        this.checkError();
    }

    @Override
    public void glGenBuffers(int n, IntBuffer intBuffer) {
        this.begin("glGenBuffers");
        this.arg("n", n);
        this.arg("buffers", intBuffer.toString());
        this.end();
        this.mgl11.glGenBuffers(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenBuffers(int n, int[] arrn, int n2) {
        this.begin("glGenBuffers");
        this.arg("n", n);
        this.arg("buffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGenBuffers(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenFramebuffersOES(int n, IntBuffer intBuffer) {
        this.begin("glGenFramebuffersOES");
        this.arg("n", n);
        this.arg("framebuffers", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenFramebuffersOES(int n, int[] arrn, int n2) {
        this.begin("glGenFramebuffersOES");
        this.arg("n", n);
        this.arg("framebuffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenRenderbuffersOES(int n, IntBuffer intBuffer) {
        this.begin("glGenRenderbuffersOES");
        this.arg("n", n);
        this.arg("renderbuffers", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGenRenderbuffersOES(int n, int[] arrn, int n2) {
        this.begin("glGenRenderbuffersOES");
        this.arg("n", n);
        this.arg("renderbuffers", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glGenTextures(int n, IntBuffer intBuffer) {
        this.begin("glGenTextures");
        this.arg("n", n);
        this.arg("textures", intBuffer.toString());
        this.mgl.glGenTextures(n, intBuffer);
        this.returns(this.toString(n, 0, intBuffer));
        this.checkError();
    }

    @Override
    public void glGenTextures(int n, int[] arrn, int n2) {
        this.begin("glGenTextures");
        this.arg("n", n);
        this.arg("textures", Arrays.toString(arrn));
        this.arg("offset", n2);
        this.mgl.glGenTextures(n, arrn, n2);
        this.returns(this.toString(n, 0, arrn, n2));
        this.checkError();
    }

    @Override
    public void glGenerateMipmapOES(int n) {
        this.begin("glGenerateMipmapOES");
        this.arg("target", n);
        this.end();
        this.mgl11ExtensionPack.glGenerateMipmapOES(n);
        this.checkError();
    }

    @Override
    public void glGetBooleanv(int n, IntBuffer intBuffer) {
        this.begin("glGetBooleanv");
        this.arg("pname", n);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetBooleanv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetBooleanv(int n, boolean[] arrbl, int n2) {
        this.begin("glGetBooleanv");
        this.arg("pname", n);
        this.arg("params", arrbl.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGetBooleanv(n, arrbl, n2);
        this.checkError();
    }

    @Override
    public void glGetBufferParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetBufferParameteriv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetBufferParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetBufferParameteriv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetBufferParameteriv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetBufferParameteriv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetClipPlanef(int n, FloatBuffer floatBuffer) {
        this.begin("glGetClipPlanef");
        this.arg("pname", n);
        this.arg("eqn", floatBuffer.toString());
        this.end();
        this.mgl11.glGetClipPlanef(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetClipPlanef(int n, float[] arrf, int n2) {
        this.begin("glGetClipPlanef");
        this.arg("pname", n);
        this.arg("eqn", arrf.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGetClipPlanef(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glGetClipPlanex(int n, IntBuffer intBuffer) {
        this.begin("glGetClipPlanex");
        this.arg("pname", n);
        this.arg("eqn", intBuffer.toString());
        this.end();
        this.mgl11.glGetClipPlanex(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetClipPlanex(int n, int[] arrn, int n2) {
        this.begin("glGetClipPlanex");
        this.arg("pname", n);
        this.arg("eqn", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGetClipPlanex(n, arrn, n2);
    }

    @Override
    public int glGetError() {
        this.begin("glGetError");
        int n = this.mgl.glGetError();
        this.returns(n);
        return n;
    }

    @Override
    public void glGetFixedv(int n, IntBuffer intBuffer) {
        this.begin("glGetFixedv");
        this.arg("pname", n);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetFixedv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetFixedv(int n, int[] arrn, int n2) {
        this.begin("glGetFixedv");
        this.arg("pname", n);
        this.arg("params", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGetFixedv(n, arrn, n2);
    }

    @Override
    public void glGetFloatv(int n, FloatBuffer floatBuffer) {
        this.begin("glGetFloatv");
        this.arg("pname", n);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glGetFloatv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetFloatv(int n, float[] arrf, int n2) {
        this.begin("glGetFloatv");
        this.arg("pname", n);
        this.arg("params", arrf.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glGetFloatv(n, arrf, n2);
    }

    @Override
    public void glGetFramebufferAttachmentParameterivOES(int n, int n2, int n3, IntBuffer intBuffer) {
        this.begin("glGetFramebufferAttachmentParameterivOES");
        this.arg("target", n);
        this.arg("attachment", n2);
        this.arg("pname", n3);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(n, n2, n3, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetFramebufferAttachmentParameterivOES(int n, int n2, int n3, int[] arrn, int n4) {
        this.begin("glGetFramebufferAttachmentParameterivOES");
        this.arg("target", n);
        this.arg("attachment", n2);
        this.arg("pname", n3);
        this.arg("params", arrn.toString());
        this.arg("offset", n4);
        this.end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(n, n2, n3, arrn, n4);
        this.checkError();
    }

    @Override
    public void glGetIntegerv(int n, IntBuffer intBuffer) {
        this.begin("glGetIntegerv");
        this.arg("pname", this.getIntegerStateName(n));
        this.arg("params", intBuffer.toString());
        this.mgl.glGetIntegerv(n, intBuffer);
        this.returns(this.toString(this.getIntegerStateSize(n), this.getIntegerStateFormat(n), intBuffer));
        this.checkError();
    }

    @Override
    public void glGetIntegerv(int n, int[] arrn, int n2) {
        this.begin("glGetIntegerv");
        this.arg("pname", this.getIntegerStateName(n));
        this.arg("params", Arrays.toString(arrn));
        this.arg("offset", n2);
        this.mgl.glGetIntegerv(n, arrn, n2);
        this.returns(this.toString(this.getIntegerStateSize(n), this.getIntegerStateFormat(n), arrn, n2));
        this.checkError();
    }

    @Override
    public void glGetLightfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glGetLightfv");
        this.arg("light", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glGetLightfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetLightfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glGetLightfv");
        this.arg("light", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetLightfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetLightxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetLightxv");
        this.arg("light", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetLightxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetLightxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetLightxv");
        this.arg("light", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetLightxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetMaterialfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glGetMaterialfv");
        this.arg("face", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glGetMaterialfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetMaterialfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glGetMaterialfv");
        this.arg("face", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetMaterialfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetMaterialxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetMaterialxv");
        this.arg("face", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetMaterialxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetMaterialxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetMaterialxv");
        this.arg("face", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetMaterialxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetPointerv(int n, Buffer[] arrbuffer) {
        this.begin("glGetPointerv");
        this.arg("pname", n);
        this.arg("params", arrbuffer.toString());
        this.end();
        this.mgl11.glGetPointerv(n, arrbuffer);
        this.checkError();
    }

    @Override
    public void glGetRenderbufferParameterivOES(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetRenderbufferParameterivOES");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetRenderbufferParameterivOES(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetRenderbufferParameterivOES");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public String glGetString(int n) {
        this.begin("glGetString");
        this.arg("name", n);
        String string2 = this.mgl.glGetString(n);
        this.returns(string2);
        this.checkError();
        return string2;
    }

    @Override
    public void glGetTexEnviv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexEnviv");
        this.arg("env", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetTexEnviv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexEnviv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexEnviv");
        this.arg("env", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexEnvxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexEnviv");
        this.arg("env", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetTexEnvxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexEnvxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexEnviv");
        this.arg("env", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGenfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glGetTexGenfv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGetTexGenfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGenfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glGetTexGenfv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glGetTexGenfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGeniv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexGeniv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGetTexGeniv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGeniv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexGeniv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glGetTexGeniv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexGenxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexGenxv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glGetTexGenxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexGenxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexGenxv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glGetTexGenxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameterfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glGetTexParameterfv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glGetTexParameterfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameterfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glGetTexParameterfv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetTexParameterfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexParameteriv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetTexParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameteriv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexParameteriv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glGetTexParameterxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glGetTexParameterxv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glGetTexParameterxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glGetTexParameterxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glGetTexParameterxv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glGetTexParameterxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glHint(int n, int n2) {
        this.begin("glHint");
        this.arg("target", this.getHintTarget(n));
        this.arg("mode", this.getHintMode(n2));
        this.end();
        this.mgl.glHint(n, n2);
        this.checkError();
    }

    @Override
    public boolean glIsBuffer(int n) {
        this.begin("glIsBuffer");
        this.arg("buffer", n);
        this.end();
        boolean bl = this.mgl11.glIsBuffer(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsEnabled(int n) {
        this.begin("glIsEnabled");
        this.arg("cap", n);
        this.end();
        boolean bl = this.mgl11.glIsEnabled(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsFramebufferOES(int n) {
        this.begin("glIsFramebufferOES");
        this.arg("framebuffer", n);
        this.end();
        boolean bl = this.mgl11ExtensionPack.glIsFramebufferOES(n);
        this.checkError();
        return bl;
    }

    @Override
    public boolean glIsRenderbufferOES(int n) {
        this.begin("glIsRenderbufferOES");
        this.arg("renderbuffer", n);
        this.end();
        this.mgl11ExtensionPack.glIsRenderbufferOES(n);
        this.checkError();
        return false;
    }

    @Override
    public boolean glIsTexture(int n) {
        this.begin("glIsTexture");
        this.arg("texture", n);
        this.end();
        boolean bl = this.mgl11.glIsTexture(n);
        this.checkError();
        return bl;
    }

    @Override
    public void glLightModelf(int n, float f) {
        this.begin("glLightModelf");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("param", f);
        this.end();
        this.mgl.glLightModelf(n, f);
        this.checkError();
    }

    @Override
    public void glLightModelfv(int n, FloatBuffer floatBuffer) {
        this.begin("glLightModelfv");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("params", this.getLightModelParamCount(n), floatBuffer);
        this.end();
        this.mgl.glLightModelfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glLightModelfv(int n, float[] arrf, int n2) {
        this.begin("glLightModelfv");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("params", this.getLightModelParamCount(n), arrf, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl.glLightModelfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glLightModelx(int n, int n2) {
        this.begin("glLightModelx");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("param", n2);
        this.end();
        this.mgl.glLightModelx(n, n2);
        this.checkError();
    }

    @Override
    public void glLightModelxv(int n, IntBuffer intBuffer) {
        this.begin("glLightModelfv");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("params", this.getLightModelParamCount(n), intBuffer);
        this.end();
        this.mgl.glLightModelxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glLightModelxv(int n, int[] arrn, int n2) {
        this.begin("glLightModelxv");
        this.arg("pname", this.getLightModelPName(n));
        this.arg("params", this.getLightModelParamCount(n), arrn, n2);
        this.arg("offset", n2);
        this.end();
        this.mgl.glLightModelxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glLightf(int n, int n2, float f) {
        this.begin("glLightf");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("param", f);
        this.end();
        this.mgl.glLightf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glLightfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glLightfv");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("params", this.getLightParamCount(n2), floatBuffer);
        this.end();
        this.mgl.glLightfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glLightfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glLightfv");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("params", this.getLightParamCount(n2), arrf, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glLightfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glLightx(int n, int n2, int n3) {
        this.begin("glLightx");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("param", n3);
        this.end();
        this.mgl.glLightx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glLightxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glLightxv");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("params", this.getLightParamCount(n2), intBuffer);
        this.end();
        this.mgl.glLightxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glLightxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glLightxv");
        this.arg("light", this.getLightName(n));
        this.arg("pname", this.getLightPName(n2));
        this.arg("params", this.getLightParamCount(n2), arrn, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glLightxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glLineWidth(float f) {
        this.begin("glLineWidth");
        this.arg("width", f);
        this.end();
        this.mgl.glLineWidth(f);
        this.checkError();
    }

    @Override
    public void glLineWidthx(int n) {
        this.begin("glLineWidthx");
        this.arg("width", n);
        this.end();
        this.mgl.glLineWidthx(n);
        this.checkError();
    }

    @Override
    public void glLoadIdentity() {
        this.begin("glLoadIdentity");
        this.end();
        this.mgl.glLoadIdentity();
        this.checkError();
    }

    @Override
    public void glLoadMatrixf(FloatBuffer floatBuffer) {
        this.begin("glLoadMatrixf");
        this.arg("m", 16, floatBuffer);
        this.end();
        this.mgl.glLoadMatrixf(floatBuffer);
        this.checkError();
    }

    @Override
    public void glLoadMatrixf(float[] arrf, int n) {
        this.begin("glLoadMatrixf");
        this.arg("m", 16, arrf, n);
        this.arg("offset", n);
        this.end();
        this.mgl.glLoadMatrixf(arrf, n);
        this.checkError();
    }

    @Override
    public void glLoadMatrixx(IntBuffer intBuffer) {
        this.begin("glLoadMatrixx");
        this.arg("m", 16, intBuffer);
        this.end();
        this.mgl.glLoadMatrixx(intBuffer);
        this.checkError();
    }

    @Override
    public void glLoadMatrixx(int[] arrn, int n) {
        this.begin("glLoadMatrixx");
        this.arg("m", 16, arrn, n);
        this.arg("offset", n);
        this.end();
        this.mgl.glLoadMatrixx(arrn, n);
        this.checkError();
    }

    @Override
    public void glLoadPaletteFromModelViewMatrixOES() {
        this.begin("glLoadPaletteFromModelViewMatrixOES");
        this.end();
        this.mgl11Ext.glLoadPaletteFromModelViewMatrixOES();
        this.checkError();
    }

    @Override
    public void glLogicOp(int n) {
        this.begin("glLogicOp");
        this.arg("opcode", n);
        this.end();
        this.mgl.glLogicOp(n);
        this.checkError();
    }

    @Override
    public void glMaterialf(int n, int n2, float f) {
        this.begin("glMaterialf");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("param", f);
        this.end();
        this.mgl.glMaterialf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glMaterialfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glMaterialfv");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("params", this.getMaterialParamCount(n2), floatBuffer);
        this.end();
        this.mgl.glMaterialfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glMaterialfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glMaterialfv");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("params", this.getMaterialParamCount(n2), arrf, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glMaterialfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glMaterialx(int n, int n2, int n3) {
        this.begin("glMaterialx");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("param", n3);
        this.end();
        this.mgl.glMaterialx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glMaterialxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glMaterialxv");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("params", this.getMaterialParamCount(n2), intBuffer);
        this.end();
        this.mgl.glMaterialxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glMaterialxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glMaterialxv");
        this.arg("face", this.getFaceName(n));
        this.arg("pname", this.getMaterialPName(n2));
        this.arg("params", this.getMaterialParamCount(n2), arrn, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glMaterialxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glMatrixIndexPointerOES(int n, int n2, int n3, int n4) {
        this.begin("glMatrixIndexPointerOES");
        this.arg("size", n);
        this.arg("type", n2);
        this.arg("stride", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11Ext.glMatrixIndexPointerOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glMatrixIndexPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.begin("glMatrixIndexPointerOES");
        this.argPointer(n, n2, n3, buffer);
        this.end();
        this.mgl11Ext.glMatrixIndexPointerOES(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glMatrixMode(int n) {
        this.begin("glMatrixMode");
        this.arg("mode", this.getMatrixMode(n));
        this.end();
        this.mgl.glMatrixMode(n);
        this.checkError();
    }

    @Override
    public void glMultMatrixf(FloatBuffer floatBuffer) {
        this.begin("glMultMatrixf");
        this.arg("m", 16, floatBuffer);
        this.end();
        this.mgl.glMultMatrixf(floatBuffer);
        this.checkError();
    }

    @Override
    public void glMultMatrixf(float[] arrf, int n) {
        this.begin("glMultMatrixf");
        this.arg("m", 16, arrf, n);
        this.arg("offset", n);
        this.end();
        this.mgl.glMultMatrixf(arrf, n);
        this.checkError();
    }

    @Override
    public void glMultMatrixx(IntBuffer intBuffer) {
        this.begin("glMultMatrixx");
        this.arg("m", 16, intBuffer);
        this.end();
        this.mgl.glMultMatrixx(intBuffer);
        this.checkError();
    }

    @Override
    public void glMultMatrixx(int[] arrn, int n) {
        this.begin("glMultMatrixx");
        this.arg("m", 16, arrn, n);
        this.arg("offset", n);
        this.end();
        this.mgl.glMultMatrixx(arrn, n);
        this.checkError();
    }

    @Override
    public void glMultiTexCoord4f(int n, float f, float f2, float f3, float f4) {
        this.begin("glMultiTexCoord4f");
        this.arg("target", n);
        this.arg("s", f);
        this.arg("t", f2);
        this.arg("r", f3);
        this.arg("q", f4);
        this.end();
        this.mgl.glMultiTexCoord4f(n, f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glMultiTexCoord4x(int n, int n2, int n3, int n4, int n5) {
        this.begin("glMultiTexCoord4x");
        this.arg("target", n);
        this.arg("s", n2);
        this.arg("t", n3);
        this.arg("r", n4);
        this.arg("q", n5);
        this.end();
        this.mgl.glMultiTexCoord4x(n, n2, n3, n4, n5);
        this.checkError();
    }

    @Override
    public void glNormal3f(float f, float f2, float f3) {
        this.begin("glNormal3f");
        this.arg("nx", f);
        this.arg("ny", f2);
        this.arg("nz", f3);
        this.end();
        this.mgl.glNormal3f(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glNormal3x(int n, int n2, int n3) {
        this.begin("glNormal3x");
        this.arg("nx", n);
        this.arg("ny", n2);
        this.arg("nz", n3);
        this.end();
        this.mgl.glNormal3x(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glNormalPointer(int n, int n2, int n3) {
        this.begin("glNormalPointer");
        this.arg("type", n);
        this.arg("stride", n2);
        this.arg("offset", n3);
        this.end();
        this.mgl11.glNormalPointer(n, n2, n3);
    }

    @Override
    public void glNormalPointer(int n, int n2, Buffer buffer) {
        this.begin("glNormalPointer");
        this.arg("type", n);
        this.arg("stride", n2);
        this.arg("pointer", buffer.toString());
        this.end();
        this.mNormalPointer = new PointerInfo(3, n, n2, buffer);
        this.mgl.glNormalPointer(n, n2, buffer);
        this.checkError();
    }

    @Override
    public void glOrthof(float f, float f2, float f3, float f4, float f5, float f6) {
        this.begin("glOrthof");
        this.arg("left", f);
        this.arg("right", f2);
        this.arg("bottom", f3);
        this.arg("top", f4);
        this.arg("near", f5);
        this.arg("far", f6);
        this.end();
        this.mgl.glOrthof(f, f2, f3, f4, f5, f6);
        this.checkError();
    }

    @Override
    public void glOrthox(int n, int n2, int n3, int n4, int n5, int n6) {
        this.begin("glOrthox");
        this.arg("left", n);
        this.arg("right", n2);
        this.arg("bottom", n3);
        this.arg("top", n4);
        this.arg("near", n5);
        this.arg("far", n6);
        this.end();
        this.mgl.glOrthox(n, n2, n3, n4, n5, n6);
        this.checkError();
    }

    @Override
    public void glPixelStorei(int n, int n2) {
        this.begin("glPixelStorei");
        this.arg("pname", n);
        this.arg("param", n2);
        this.end();
        this.mgl.glPixelStorei(n, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterf(int n, float f) {
        this.begin("glPointParameterf");
        this.arg("pname", n);
        this.arg("param", f);
        this.end();
        this.mgl11.glPointParameterf(n, f);
        this.checkError();
    }

    @Override
    public void glPointParameterfv(int n, FloatBuffer floatBuffer) {
        this.begin("glPointParameterfv");
        this.arg("pname", n);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glPointParameterfv(n, floatBuffer);
        this.checkError();
    }

    @Override
    public void glPointParameterfv(int n, float[] arrf, int n2) {
        this.begin("glPointParameterfv");
        this.arg("pname", n);
        this.arg("params", arrf.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glPointParameterfv(n, arrf, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterx(int n, int n2) {
        this.begin("glPointParameterfv");
        this.arg("pname", n);
        this.arg("param", n2);
        this.end();
        this.mgl11.glPointParameterx(n, n2);
        this.checkError();
    }

    @Override
    public void glPointParameterxv(int n, IntBuffer intBuffer) {
        this.begin("glPointParameterxv");
        this.arg("pname", n);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glPointParameterxv(n, intBuffer);
        this.checkError();
    }

    @Override
    public void glPointParameterxv(int n, int[] arrn, int n2) {
        this.begin("glPointParameterxv");
        this.arg("pname", n);
        this.arg("params", arrn.toString());
        this.arg("offset", n2);
        this.end();
        this.mgl11.glPointParameterxv(n, arrn, n2);
        this.checkError();
    }

    @Override
    public void glPointSize(float f) {
        this.begin("glPointSize");
        this.arg("size", f);
        this.end();
        this.mgl.glPointSize(f);
        this.checkError();
    }

    @Override
    public void glPointSizePointerOES(int n, int n2, Buffer buffer) {
        this.begin("glPointSizePointerOES");
        this.arg("type", n);
        this.arg("stride", n2);
        this.arg("params", buffer.toString());
        this.end();
        this.mgl11.glPointSizePointerOES(n, n2, buffer);
        this.checkError();
    }

    @Override
    public void glPointSizex(int n) {
        this.begin("glPointSizex");
        this.arg("size", n);
        this.end();
        this.mgl.glPointSizex(n);
        this.checkError();
    }

    @Override
    public void glPolygonOffset(float f, float f2) {
        this.begin("glPolygonOffset");
        this.arg("factor", f);
        this.arg("units", f2);
        this.end();
        this.mgl.glPolygonOffset(f, f2);
        this.checkError();
    }

    @Override
    public void glPolygonOffsetx(int n, int n2) {
        this.begin("glPolygonOffsetx");
        this.arg("factor", n);
        this.arg("units", n2);
        this.end();
        this.mgl.glPolygonOffsetx(n, n2);
        this.checkError();
    }

    @Override
    public void glPopMatrix() {
        this.begin("glPopMatrix");
        this.end();
        this.mgl.glPopMatrix();
        this.checkError();
    }

    @Override
    public void glPushMatrix() {
        this.begin("glPushMatrix");
        this.end();
        this.mgl.glPushMatrix();
        this.checkError();
    }

    @Override
    public int glQueryMatrixxOES(IntBuffer intBuffer, IntBuffer intBuffer2) {
        this.begin("glQueryMatrixxOES");
        this.arg("mantissa", intBuffer.toString());
        this.arg("exponent", intBuffer2.toString());
        this.end();
        int n = this.mgl10Ext.glQueryMatrixxOES(intBuffer, intBuffer2);
        this.returns(this.toString(16, 2, intBuffer));
        this.returns(this.toString(16, 0, intBuffer2));
        this.checkError();
        return n;
    }

    @Override
    public int glQueryMatrixxOES(int[] arrn, int n, int[] arrn2, int n2) {
        this.begin("glQueryMatrixxOES");
        this.arg("mantissa", Arrays.toString(arrn));
        this.arg("exponent", Arrays.toString(arrn2));
        this.end();
        int n3 = this.mgl10Ext.glQueryMatrixxOES(arrn, n, arrn2, n2);
        this.returns(this.toString(16, 2, arrn, n));
        this.returns(this.toString(16, 0, arrn2, n2));
        this.checkError();
        return n3;
    }

    @Override
    public void glReadPixels(int n, int n2, int n3, int n4, int n5, int n6, Buffer buffer) {
        this.begin("glReadPixels");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("width", n3);
        this.arg("height", n4);
        this.arg("format", n5);
        this.arg("type", n6);
        this.arg("pixels", buffer.toString());
        this.end();
        this.mgl.glReadPixels(n, n2, n3, n4, n5, n6, buffer);
        this.checkError();
    }

    @Override
    public void glRenderbufferStorageOES(int n, int n2, int n3, int n4) {
        this.begin("glRenderbufferStorageOES");
        this.arg("target", n);
        this.arg("internalformat", n2);
        this.arg("width", n3);
        this.arg("height", n4);
        this.end();
        this.mgl11ExtensionPack.glRenderbufferStorageOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glRotatef(float f, float f2, float f3, float f4) {
        this.begin("glRotatef");
        this.arg("angle", f);
        this.arg("x", f2);
        this.arg("y", f3);
        this.arg("z", f4);
        this.end();
        this.mgl.glRotatef(f, f2, f3, f4);
        this.checkError();
    }

    @Override
    public void glRotatex(int n, int n2, int n3, int n4) {
        this.begin("glRotatex");
        this.arg("angle", n);
        this.arg("x", n2);
        this.arg("y", n3);
        this.arg("z", n4);
        this.end();
        this.mgl.glRotatex(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glSampleCoverage(float f, boolean bl) {
        this.begin("glSampleCoveragex");
        this.arg("value", f);
        this.arg("invert", bl);
        this.end();
        this.mgl.glSampleCoverage(f, bl);
        this.checkError();
    }

    @Override
    public void glSampleCoveragex(int n, boolean bl) {
        this.begin("glSampleCoveragex");
        this.arg("value", n);
        this.arg("invert", bl);
        this.end();
        this.mgl.glSampleCoveragex(n, bl);
        this.checkError();
    }

    @Override
    public void glScalef(float f, float f2, float f3) {
        this.begin("glScalef");
        this.arg("x", f);
        this.arg("y", f2);
        this.arg("z", f3);
        this.end();
        this.mgl.glScalef(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glScalex(int n, int n2, int n3) {
        this.begin("glScalex");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("z", n3);
        this.end();
        this.mgl.glScalex(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glScissor(int n, int n2, int n3, int n4) {
        this.begin("glScissor");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("width", n3);
        this.arg("height", n4);
        this.end();
        this.mgl.glScissor(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glShadeModel(int n) {
        this.begin("glShadeModel");
        this.arg("mode", this.getShadeModel(n));
        this.end();
        this.mgl.glShadeModel(n);
        this.checkError();
    }

    @Override
    public void glStencilFunc(int n, int n2, int n3) {
        this.begin("glStencilFunc");
        this.arg("func", n);
        this.arg("ref", n2);
        this.arg("mask", n3);
        this.end();
        this.mgl.glStencilFunc(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glStencilMask(int n) {
        this.begin("glStencilMask");
        this.arg("mask", n);
        this.end();
        this.mgl.glStencilMask(n);
        this.checkError();
    }

    @Override
    public void glStencilOp(int n, int n2, int n3) {
        this.begin("glStencilOp");
        this.arg("fail", n);
        this.arg("zfail", n2);
        this.arg("zpass", n3);
        this.end();
        this.mgl.glStencilOp(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexCoordPointer(int n, int n2, int n3, int n4) {
        this.begin("glTexCoordPointer");
        this.arg("size", n);
        this.arg("type", n2);
        this.arg("stride", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11.glTexCoordPointer(n, n2, n3, n4);
    }

    @Override
    public void glTexCoordPointer(int n, int n2, int n3, Buffer buffer) {
        this.begin("glTexCoordPointer");
        this.argPointer(n, n2, n3, buffer);
        this.end();
        this.mTexCoordPointer = new PointerInfo(n, n2, n3, buffer);
        this.mgl.glTexCoordPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glTexEnvf(int n, int n2, float f) {
        this.begin("glTexEnvf");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("param", this.getTextureEnvParamName(f));
        this.end();
        this.mgl.glTexEnvf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexEnvfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glTexEnvfv");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("params", this.getTextureEnvParamCount(n2), floatBuffer);
        this.end();
        this.mgl.glTexEnvfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnvfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glTexEnvfv");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("params", this.getTextureEnvParamCount(n2), arrf, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glTexEnvfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvi(int n, int n2, int n3) {
        this.begin("glTexEnvi");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("param", n3);
        this.end();
        this.mgl11.glTexEnvi(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexEnviv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexEnviv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glTexEnviv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnviv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexEnviv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glTexEnviv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvx(int n, int n2, int n3) {
        this.begin("glTexEnvx");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("param", n3);
        this.end();
        this.mgl.glTexEnvx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexEnvxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexEnvxv");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("params", this.getTextureEnvParamCount(n2), intBuffer);
        this.end();
        this.mgl.glTexEnvxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexEnvxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexEnvxv");
        this.arg("target", this.getTextureEnvTarget(n));
        this.arg("pname", this.getTextureEnvPName(n2));
        this.arg("params", this.getTextureEnvParamCount(n2), arrn, n3);
        this.arg("offset", n3);
        this.end();
        this.mgl.glTexEnvxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexGenf(int n, int n2, float f) {
        this.begin("glTexGenf");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("param", f);
        this.end();
        this.mgl11ExtensionPack.glTexGenf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexGenfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glTexGenfv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glTexGenfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexGenfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glTexGenfv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glTexGenfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexGeni(int n, int n2, int n3) {
        this.begin("glTexGeni");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("param", n3);
        this.end();
        this.mgl11ExtensionPack.glTexGeni(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexGeniv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexGeniv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glTexGeniv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexGeniv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexGeniv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glTexGeniv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexGenx(int n, int n2, int n3) {
        this.begin("glTexGenx");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("param", n3);
        this.end();
        this.mgl11ExtensionPack.glTexGenx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexGenxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexGenxv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11ExtensionPack.glTexGenxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexGenxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexGenxv");
        this.arg("coord", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11ExtensionPack.glTexGenxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.begin("glTexImage2D");
        this.arg("target", n);
        this.arg("level", n2);
        this.arg("internalformat", n3);
        this.arg("width", n4);
        this.arg("height", n5);
        this.arg("border", n6);
        this.arg("format", n7);
        this.arg("type", n8);
        this.arg("pixels", buffer.toString());
        this.end();
        this.mgl.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glTexParameterf(int n, int n2, float f) {
        this.begin("glTexParameterf");
        this.arg("target", this.getTextureTarget(n));
        this.arg("pname", this.getTexturePName(n2));
        this.arg("param", this.getTextureParamName(f));
        this.end();
        this.mgl.glTexParameterf(n, n2, f);
        this.checkError();
    }

    @Override
    public void glTexParameterfv(int n, int n2, FloatBuffer floatBuffer) {
        this.begin("glTexParameterfv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", floatBuffer.toString());
        this.end();
        this.mgl11.glTexParameterfv(n, n2, floatBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameterfv(int n, int n2, float[] arrf, int n3) {
        this.begin("glTexParameterfv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrf.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glTexParameterfv(n, n2, arrf, n3);
        this.checkError();
    }

    @Override
    public void glTexParameteri(int n, int n2, int n3) {
        this.begin("glTexParameterxv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("param", n3);
        this.end();
        this.mgl11.glTexParameteri(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexParameteriv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexParameteriv");
        this.arg("target", this.getTextureTarget(n));
        this.arg("pname", this.getTexturePName(n2));
        this.arg("params", 4, intBuffer);
        this.end();
        this.mgl11.glTexParameteriv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameteriv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexParameteriv");
        this.arg("target", this.getTextureTarget(n));
        this.arg("pname", this.getTexturePName(n2));
        this.arg("params", 4, arrn, n3);
        this.end();
        this.mgl11.glTexParameteriv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexParameterx(int n, int n2, int n3) {
        this.begin("glTexParameterx");
        this.arg("target", this.getTextureTarget(n));
        this.arg("pname", this.getTexturePName(n2));
        this.arg("param", n3);
        this.end();
        this.mgl.glTexParameterx(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glTexParameterxv(int n, int n2, IntBuffer intBuffer) {
        this.begin("glTexParameterxv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", intBuffer.toString());
        this.end();
        this.mgl11.glTexParameterxv(n, n2, intBuffer);
        this.checkError();
    }

    @Override
    public void glTexParameterxv(int n, int n2, int[] arrn, int n3) {
        this.begin("glTexParameterxv");
        this.arg("target", n);
        this.arg("pname", n2);
        this.arg("params", arrn.toString());
        this.arg("offset", n3);
        this.end();
        this.mgl11.glTexParameterxv(n, n2, arrn, n3);
        this.checkError();
    }

    @Override
    public void glTexSubImage2D(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, Buffer buffer) {
        this.begin("glTexSubImage2D");
        this.arg("target", this.getTextureTarget(n));
        this.arg("level", n2);
        this.arg("xoffset", n3);
        this.arg("yoffset", n4);
        this.arg("width", n5);
        this.arg("height", n6);
        this.arg("format", n7);
        this.arg("type", n8);
        this.arg("pixels", buffer.toString());
        this.end();
        this.mgl.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, buffer);
        this.checkError();
    }

    @Override
    public void glTranslatef(float f, float f2, float f3) {
        this.begin("glTranslatef");
        this.arg("x", f);
        this.arg("y", f2);
        this.arg("z", f3);
        this.end();
        this.mgl.glTranslatef(f, f2, f3);
        this.checkError();
    }

    @Override
    public void glTranslatex(int n, int n2, int n3) {
        this.begin("glTranslatex");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("z", n3);
        this.end();
        this.mgl.glTranslatex(n, n2, n3);
        this.checkError();
    }

    @Override
    public void glVertexPointer(int n, int n2, int n3, int n4) {
        this.begin("glVertexPointer");
        this.arg("size", n);
        this.arg("type", n2);
        this.arg("stride", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11.glVertexPointer(n, n2, n3, n4);
    }

    @Override
    public void glVertexPointer(int n, int n2, int n3, Buffer buffer) {
        this.begin("glVertexPointer");
        this.argPointer(n, n2, n3, buffer);
        this.end();
        this.mVertexPointer = new PointerInfo(n, n2, n3, buffer);
        this.mgl.glVertexPointer(n, n2, n3, buffer);
        this.checkError();
    }

    @Override
    public void glViewport(int n, int n2, int n3, int n4) {
        this.begin("glViewport");
        this.arg("x", n);
        this.arg("y", n2);
        this.arg("width", n3);
        this.arg("height", n4);
        this.end();
        this.mgl.glViewport(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glWeightPointerOES(int n, int n2, int n3, int n4) {
        this.begin("glWeightPointerOES");
        this.arg("size", n);
        this.arg("type", n2);
        this.arg("stride", n3);
        this.arg("offset", n4);
        this.end();
        this.mgl11Ext.glWeightPointerOES(n, n2, n3, n4);
        this.checkError();
    }

    @Override
    public void glWeightPointerOES(int n, int n2, int n3, Buffer buffer) {
        this.begin("glWeightPointerOES");
        this.argPointer(n, n2, n3, buffer);
        this.end();
        this.mgl11Ext.glWeightPointerOES(n, n2, n3, buffer);
        this.checkError();
    }

    private class PointerInfo {
        public Buffer mPointer;
        public int mSize;
        public int mStride;
        public ByteBuffer mTempByteBuffer;
        public int mType;

        public PointerInfo() {
        }

        public PointerInfo(int n, int n2, int n3, Buffer buffer) {
            this.mSize = n;
            this.mType = n2;
            this.mStride = n3;
            this.mPointer = buffer;
        }

        public void bindByteBuffer() {
            Buffer buffer = this.mPointer;
            buffer = buffer == null ? null : GLLogWrapper.this.toByteBuffer(-1, buffer);
            this.mTempByteBuffer = buffer;
        }

        public int getStride() {
            int n = this.mStride;
            if (n <= 0) {
                n = this.sizeof(this.mType) * this.mSize;
            }
            return n;
        }

        public int sizeof(int n) {
            if (n != 5126) {
                if (n != 5132) {
                    switch (n) {
                        default: {
                            return 0;
                        }
                        case 5122: {
                            return 2;
                        }
                        case 5121: {
                            return 1;
                        }
                        case 5120: 
                    }
                    return 1;
                }
                return 4;
            }
            return 4;
        }

        public void unbindByteBuffer() {
            this.mTempByteBuffer = null;
        }
    }

}

