/*
 * Decompiled with CFR 0.145.
 */
package javax.microedition.khronos.opengles;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL;

public interface GL10
extends GL {
    public static final int GL_ADD = 260;
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 33901;
    public static final int GL_ALPHA = 6406;
    public static final int GL_ALPHA_BITS = 3413;
    public static final int GL_ALPHA_TEST = 3008;
    public static final int GL_ALWAYS = 519;
    public static final int GL_AMBIENT = 4608;
    public static final int GL_AMBIENT_AND_DIFFUSE = 5634;
    public static final int GL_AND = 5377;
    public static final int GL_AND_INVERTED = 5380;
    public static final int GL_AND_REVERSE = 5378;
    public static final int GL_BACK = 1029;
    public static final int GL_BLEND = 3042;
    public static final int GL_BLUE_BITS = 3412;
    public static final int GL_BYTE = 5120;
    public static final int GL_CCW = 2305;
    public static final int GL_CLAMP_TO_EDGE = 33071;
    public static final int GL_CLEAR = 5376;
    public static final int GL_COLOR_ARRAY = 32886;
    public static final int GL_COLOR_BUFFER_BIT = 16384;
    public static final int GL_COLOR_LOGIC_OP = 3058;
    public static final int GL_COLOR_MATERIAL = 2903;
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 34467;
    public static final int GL_CONSTANT_ATTENUATION = 4615;
    public static final int GL_COPY = 5379;
    public static final int GL_COPY_INVERTED = 5388;
    public static final int GL_CULL_FACE = 2884;
    public static final int GL_CW = 2304;
    public static final int GL_DECAL = 8449;
    public static final int GL_DECR = 7683;
    public static final int GL_DEPTH_BITS = 3414;
    public static final int GL_DEPTH_BUFFER_BIT = 256;
    public static final int GL_DEPTH_TEST = 2929;
    public static final int GL_DIFFUSE = 4609;
    public static final int GL_DITHER = 3024;
    public static final int GL_DONT_CARE = 4352;
    public static final int GL_DST_ALPHA = 772;
    public static final int GL_DST_COLOR = 774;
    public static final int GL_EMISSION = 5632;
    public static final int GL_EQUAL = 514;
    public static final int GL_EQUIV = 5385;
    public static final int GL_EXP = 2048;
    public static final int GL_EXP2 = 2049;
    public static final int GL_EXTENSIONS = 7939;
    public static final int GL_FALSE = 0;
    public static final int GL_FASTEST = 4353;
    public static final int GL_FIXED = 5132;
    public static final int GL_FLAT = 7424;
    public static final int GL_FLOAT = 5126;
    public static final int GL_FOG = 2912;
    public static final int GL_FOG_COLOR = 2918;
    public static final int GL_FOG_DENSITY = 2914;
    public static final int GL_FOG_END = 2916;
    public static final int GL_FOG_HINT = 3156;
    public static final int GL_FOG_MODE = 2917;
    public static final int GL_FOG_START = 2915;
    public static final int GL_FRONT = 1028;
    public static final int GL_FRONT_AND_BACK = 1032;
    public static final int GL_GEQUAL = 518;
    public static final int GL_GREATER = 516;
    public static final int GL_GREEN_BITS = 3411;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT_OES = 35739;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE_OES = 35738;
    public static final int GL_INCR = 7682;
    public static final int GL_INVALID_ENUM = 1280;
    public static final int GL_INVALID_OPERATION = 1282;
    public static final int GL_INVALID_VALUE = 1281;
    public static final int GL_INVERT = 5386;
    public static final int GL_KEEP = 7680;
    public static final int GL_LEQUAL = 515;
    public static final int GL_LESS = 513;
    public static final int GL_LIGHT0 = 16384;
    public static final int GL_LIGHT1 = 16385;
    public static final int GL_LIGHT2 = 16386;
    public static final int GL_LIGHT3 = 16387;
    public static final int GL_LIGHT4 = 16388;
    public static final int GL_LIGHT5 = 16389;
    public static final int GL_LIGHT6 = 16390;
    public static final int GL_LIGHT7 = 16391;
    public static final int GL_LIGHTING = 2896;
    public static final int GL_LIGHT_MODEL_AMBIENT = 2899;
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 2898;
    public static final int GL_LINEAR = 9729;
    public static final int GL_LINEAR_ATTENUATION = 4616;
    public static final int GL_LINEAR_MIPMAP_LINEAR = 9987;
    public static final int GL_LINEAR_MIPMAP_NEAREST = 9985;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_LOOP = 2;
    public static final int GL_LINE_SMOOTH = 2848;
    public static final int GL_LINE_SMOOTH_HINT = 3154;
    public static final int GL_LINE_STRIP = 3;
    public static final int GL_LUMINANCE = 6409;
    public static final int GL_LUMINANCE_ALPHA = 6410;
    public static final int GL_MAX_ELEMENTS_INDICES = 33001;
    public static final int GL_MAX_ELEMENTS_VERTICES = 33000;
    public static final int GL_MAX_LIGHTS = 3377;
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 3382;
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    public static final int GL_MAX_TEXTURE_SIZE = 3379;
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    public static final int GL_MAX_TEXTURE_UNITS = 34018;
    public static final int GL_MAX_VIEWPORT_DIMS = 3386;
    public static final int GL_MODELVIEW = 5888;
    public static final int GL_MODULATE = 8448;
    public static final int GL_MULTISAMPLE = 32925;
    public static final int GL_NAND = 5390;
    public static final int GL_NEAREST = 9728;
    public static final int GL_NEAREST_MIPMAP_LINEAR = 9986;
    public static final int GL_NEAREST_MIPMAP_NEAREST = 9984;
    public static final int GL_NEVER = 512;
    public static final int GL_NICEST = 4354;
    public static final int GL_NOOP = 5381;
    public static final int GL_NOR = 5384;
    public static final int GL_NORMALIZE = 2977;
    public static final int GL_NORMAL_ARRAY = 32885;
    public static final int GL_NOTEQUAL = 517;
    public static final int GL_NO_ERROR = 0;
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 34466;
    public static final int GL_ONE = 1;
    public static final int GL_ONE_MINUS_DST_ALPHA = 773;
    public static final int GL_ONE_MINUS_DST_COLOR = 775;
    public static final int GL_ONE_MINUS_SRC_ALPHA = 771;
    public static final int GL_ONE_MINUS_SRC_COLOR = 769;
    public static final int GL_OR = 5383;
    public static final int GL_OR_INVERTED = 5389;
    public static final int GL_OR_REVERSE = 5387;
    public static final int GL_OUT_OF_MEMORY = 1285;
    public static final int GL_PACK_ALIGNMENT = 3333;
    public static final int GL_PALETTE4_R5_G6_B5_OES = 35730;
    public static final int GL_PALETTE4_RGB5_A1_OES = 35732;
    public static final int GL_PALETTE4_RGB8_OES = 35728;
    public static final int GL_PALETTE4_RGBA4_OES = 35731;
    public static final int GL_PALETTE4_RGBA8_OES = 35729;
    public static final int GL_PALETTE8_R5_G6_B5_OES = 35735;
    public static final int GL_PALETTE8_RGB5_A1_OES = 35737;
    public static final int GL_PALETTE8_RGB8_OES = 35733;
    public static final int GL_PALETTE8_RGBA4_OES = 35736;
    public static final int GL_PALETTE8_RGBA8_OES = 35734;
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 3152;
    public static final int GL_POINTS = 0;
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 33064;
    public static final int GL_POINT_SIZE = 2833;
    public static final int GL_POINT_SMOOTH = 2832;
    public static final int GL_POINT_SMOOTH_HINT = 3153;
    public static final int GL_POLYGON_OFFSET_FILL = 32823;
    public static final int GL_POLYGON_SMOOTH_HINT = 3155;
    public static final int GL_POSITION = 4611;
    public static final int GL_PROJECTION = 5889;
    public static final int GL_QUADRATIC_ATTENUATION = 4617;
    public static final int GL_RED_BITS = 3410;
    public static final int GL_RENDERER = 7937;
    public static final int GL_REPEAT = 10497;
    public static final int GL_REPLACE = 7681;
    public static final int GL_RESCALE_NORMAL = 32826;
    public static final int GL_RGB = 6407;
    public static final int GL_RGBA = 6408;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 32926;
    public static final int GL_SAMPLE_ALPHA_TO_ONE = 32927;
    public static final int GL_SAMPLE_COVERAGE = 32928;
    public static final int GL_SCISSOR_TEST = 3089;
    public static final int GL_SET = 5391;
    public static final int GL_SHININESS = 5633;
    public static final int GL_SHORT = 5122;
    public static final int GL_SMOOTH = 7425;
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
    public static final int GL_SPECULAR = 4610;
    public static final int GL_SPOT_CUTOFF = 4614;
    public static final int GL_SPOT_DIRECTION = 4612;
    public static final int GL_SPOT_EXPONENT = 4613;
    public static final int GL_SRC_ALPHA = 770;
    public static final int GL_SRC_ALPHA_SATURATE = 776;
    public static final int GL_SRC_COLOR = 768;
    public static final int GL_STACK_OVERFLOW = 1283;
    public static final int GL_STACK_UNDERFLOW = 1284;
    public static final int GL_STENCIL_BITS = 3415;
    public static final int GL_STENCIL_BUFFER_BIT = 1024;
    public static final int GL_STENCIL_TEST = 2960;
    public static final int GL_SUBPIXEL_BITS = 3408;
    public static final int GL_TEXTURE = 5890;
    public static final int GL_TEXTURE0 = 33984;
    public static final int GL_TEXTURE1 = 33985;
    public static final int GL_TEXTURE10 = 33994;
    public static final int GL_TEXTURE11 = 33995;
    public static final int GL_TEXTURE12 = 33996;
    public static final int GL_TEXTURE13 = 33997;
    public static final int GL_TEXTURE14 = 33998;
    public static final int GL_TEXTURE15 = 33999;
    public static final int GL_TEXTURE16 = 34000;
    public static final int GL_TEXTURE17 = 34001;
    public static final int GL_TEXTURE18 = 34002;
    public static final int GL_TEXTURE19 = 34003;
    public static final int GL_TEXTURE2 = 33986;
    public static final int GL_TEXTURE20 = 34004;
    public static final int GL_TEXTURE21 = 34005;
    public static final int GL_TEXTURE22 = 34006;
    public static final int GL_TEXTURE23 = 34007;
    public static final int GL_TEXTURE24 = 34008;
    public static final int GL_TEXTURE25 = 34009;
    public static final int GL_TEXTURE26 = 34010;
    public static final int GL_TEXTURE27 = 34011;
    public static final int GL_TEXTURE28 = 34012;
    public static final int GL_TEXTURE29 = 34013;
    public static final int GL_TEXTURE3 = 33987;
    public static final int GL_TEXTURE30 = 34014;
    public static final int GL_TEXTURE31 = 34015;
    public static final int GL_TEXTURE4 = 33988;
    public static final int GL_TEXTURE5 = 33989;
    public static final int GL_TEXTURE6 = 33990;
    public static final int GL_TEXTURE7 = 33991;
    public static final int GL_TEXTURE8 = 33992;
    public static final int GL_TEXTURE9 = 33993;
    public static final int GL_TEXTURE_2D = 3553;
    public static final int GL_TEXTURE_COORD_ARRAY = 32888;
    public static final int GL_TEXTURE_ENV = 8960;
    public static final int GL_TEXTURE_ENV_COLOR = 8705;
    public static final int GL_TEXTURE_ENV_MODE = 8704;
    public static final int GL_TEXTURE_MAG_FILTER = 10240;
    public static final int GL_TEXTURE_MIN_FILTER = 10241;
    public static final int GL_TEXTURE_WRAP_S = 10242;
    public static final int GL_TEXTURE_WRAP_T = 10243;
    public static final int GL_TRIANGLES = 4;
    public static final int GL_TRIANGLE_FAN = 6;
    public static final int GL_TRIANGLE_STRIP = 5;
    public static final int GL_TRUE = 1;
    public static final int GL_UNPACK_ALIGNMENT = 3317;
    public static final int GL_UNSIGNED_BYTE = 5121;
    public static final int GL_UNSIGNED_SHORT = 5123;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 33635;
    public static final int GL_VENDOR = 7936;
    public static final int GL_VERSION = 7938;
    public static final int GL_VERTEX_ARRAY = 32884;
    public static final int GL_XOR = 5382;
    public static final int GL_ZERO = 0;

    public void glActiveTexture(int var1);

    public void glAlphaFunc(int var1, float var2);

    public void glAlphaFuncx(int var1, int var2);

    public void glBindTexture(int var1, int var2);

    public void glBlendFunc(int var1, int var2);

    public void glClear(int var1);

    public void glClearColor(float var1, float var2, float var3, float var4);

    public void glClearColorx(int var1, int var2, int var3, int var4);

    public void glClearDepthf(float var1);

    public void glClearDepthx(int var1);

    public void glClearStencil(int var1);

    public void glClientActiveTexture(int var1);

    public void glColor4f(float var1, float var2, float var3, float var4);

    public void glColor4x(int var1, int var2, int var3, int var4);

    public void glColorMask(boolean var1, boolean var2, boolean var3, boolean var4);

    public void glColorPointer(int var1, int var2, int var3, Buffer var4);

    public void glCompressedTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, Buffer var8);

    public void glCompressedTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    public void glCopyTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    public void glCopyTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    public void glCullFace(int var1);

    public void glDeleteTextures(int var1, IntBuffer var2);

    public void glDeleteTextures(int var1, int[] var2, int var3);

    public void glDepthFunc(int var1);

    public void glDepthMask(boolean var1);

    public void glDepthRangef(float var1, float var2);

    public void glDepthRangex(int var1, int var2);

    public void glDisable(int var1);

    public void glDisableClientState(int var1);

    public void glDrawArrays(int var1, int var2, int var3);

    public void glDrawElements(int var1, int var2, int var3, Buffer var4);

    public void glEnable(int var1);

    public void glEnableClientState(int var1);

    public void glFinish();

    public void glFlush();

    public void glFogf(int var1, float var2);

    public void glFogfv(int var1, FloatBuffer var2);

    public void glFogfv(int var1, float[] var2, int var3);

    public void glFogx(int var1, int var2);

    public void glFogxv(int var1, IntBuffer var2);

    public void glFogxv(int var1, int[] var2, int var3);

    public void glFrontFace(int var1);

    public void glFrustumf(float var1, float var2, float var3, float var4, float var5, float var6);

    public void glFrustumx(int var1, int var2, int var3, int var4, int var5, int var6);

    public void glGenTextures(int var1, IntBuffer var2);

    public void glGenTextures(int var1, int[] var2, int var3);

    public int glGetError();

    public void glGetIntegerv(int var1, IntBuffer var2);

    public void glGetIntegerv(int var1, int[] var2, int var3);

    public String glGetString(int var1);

    public void glHint(int var1, int var2);

    public void glLightModelf(int var1, float var2);

    public void glLightModelfv(int var1, FloatBuffer var2);

    public void glLightModelfv(int var1, float[] var2, int var3);

    public void glLightModelx(int var1, int var2);

    public void glLightModelxv(int var1, IntBuffer var2);

    public void glLightModelxv(int var1, int[] var2, int var3);

    public void glLightf(int var1, int var2, float var3);

    public void glLightfv(int var1, int var2, FloatBuffer var3);

    public void glLightfv(int var1, int var2, float[] var3, int var4);

    public void glLightx(int var1, int var2, int var3);

    public void glLightxv(int var1, int var2, IntBuffer var3);

    public void glLightxv(int var1, int var2, int[] var3, int var4);

    public void glLineWidth(float var1);

    public void glLineWidthx(int var1);

    public void glLoadIdentity();

    public void glLoadMatrixf(FloatBuffer var1);

    public void glLoadMatrixf(float[] var1, int var2);

    public void glLoadMatrixx(IntBuffer var1);

    public void glLoadMatrixx(int[] var1, int var2);

    public void glLogicOp(int var1);

    public void glMaterialf(int var1, int var2, float var3);

    public void glMaterialfv(int var1, int var2, FloatBuffer var3);

    public void glMaterialfv(int var1, int var2, float[] var3, int var4);

    public void glMaterialx(int var1, int var2, int var3);

    public void glMaterialxv(int var1, int var2, IntBuffer var3);

    public void glMaterialxv(int var1, int var2, int[] var3, int var4);

    public void glMatrixMode(int var1);

    public void glMultMatrixf(FloatBuffer var1);

    public void glMultMatrixf(float[] var1, int var2);

    public void glMultMatrixx(IntBuffer var1);

    public void glMultMatrixx(int[] var1, int var2);

    public void glMultiTexCoord4f(int var1, float var2, float var3, float var4, float var5);

    public void glMultiTexCoord4x(int var1, int var2, int var3, int var4, int var5);

    public void glNormal3f(float var1, float var2, float var3);

    public void glNormal3x(int var1, int var2, int var3);

    public void glNormalPointer(int var1, int var2, Buffer var3);

    public void glOrthof(float var1, float var2, float var3, float var4, float var5, float var6);

    public void glOrthox(int var1, int var2, int var3, int var4, int var5, int var6);

    public void glPixelStorei(int var1, int var2);

    public void glPointSize(float var1);

    public void glPointSizex(int var1);

    public void glPolygonOffset(float var1, float var2);

    public void glPolygonOffsetx(int var1, int var2);

    public void glPopMatrix();

    public void glPushMatrix();

    public void glReadPixels(int var1, int var2, int var3, int var4, int var5, int var6, Buffer var7);

    public void glRotatef(float var1, float var2, float var3, float var4);

    public void glRotatex(int var1, int var2, int var3, int var4);

    public void glSampleCoverage(float var1, boolean var2);

    public void glSampleCoveragex(int var1, boolean var2);

    public void glScalef(float var1, float var2, float var3);

    public void glScalex(int var1, int var2, int var3);

    public void glScissor(int var1, int var2, int var3, int var4);

    public void glShadeModel(int var1);

    public void glStencilFunc(int var1, int var2, int var3);

    public void glStencilMask(int var1);

    public void glStencilOp(int var1, int var2, int var3);

    public void glTexCoordPointer(int var1, int var2, int var3, Buffer var4);

    public void glTexEnvf(int var1, int var2, float var3);

    public void glTexEnvfv(int var1, int var2, FloatBuffer var3);

    public void glTexEnvfv(int var1, int var2, float[] var3, int var4);

    public void glTexEnvx(int var1, int var2, int var3);

    public void glTexEnvxv(int var1, int var2, IntBuffer var3);

    public void glTexEnvxv(int var1, int var2, int[] var3, int var4);

    public void glTexImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    public void glTexParameterf(int var1, int var2, float var3);

    public void glTexParameterx(int var1, int var2, int var3);

    public void glTexSubImage2D(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, Buffer var9);

    public void glTranslatef(float var1, float var2, float var3);

    public void glTranslatex(int var1, int var2, int var3);

    public void glVertexPointer(int var1, int var2, int var3, Buffer var4);

    public void glViewport(int var1, int var2, int var3, int var4);
}

