/*
 * Decompiled with CFR 0.145.
 */
package javax.microedition.khronos.opengles;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL;

public interface GL11Ext
extends GL {
    public static final int GL_MATRIX_INDEX_ARRAY_BUFFER_BINDING_OES = 35742;
    public static final int GL_MATRIX_INDEX_ARRAY_OES = 34884;
    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_OES = 34889;
    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_OES = 34886;
    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_OES = 34888;
    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_OES = 34887;
    public static final int GL_MATRIX_PALETTE_OES = 34880;
    public static final int GL_MAX_PALETTE_MATRICES_OES = 34882;
    public static final int GL_MAX_VERTEX_UNITS_OES = 34468;
    public static final int GL_TEXTURE_CROP_RECT_OES = 35741;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_OES = 34974;
    public static final int GL_WEIGHT_ARRAY_OES = 34477;
    public static final int GL_WEIGHT_ARRAY_POINTER_OES = 34476;
    public static final int GL_WEIGHT_ARRAY_SIZE_OES = 34475;
    public static final int GL_WEIGHT_ARRAY_STRIDE_OES = 34474;
    public static final int GL_WEIGHT_ARRAY_TYPE_OES = 34473;

    public void glCurrentPaletteMatrixOES(int var1);

    public void glDrawTexfOES(float var1, float var2, float var3, float var4, float var5);

    public void glDrawTexfvOES(FloatBuffer var1);

    public void glDrawTexfvOES(float[] var1, int var2);

    public void glDrawTexiOES(int var1, int var2, int var3, int var4, int var5);

    public void glDrawTexivOES(IntBuffer var1);

    public void glDrawTexivOES(int[] var1, int var2);

    public void glDrawTexsOES(short var1, short var2, short var3, short var4, short var5);

    public void glDrawTexsvOES(ShortBuffer var1);

    public void glDrawTexsvOES(short[] var1, int var2);

    public void glDrawTexxOES(int var1, int var2, int var3, int var4, int var5);

    public void glDrawTexxvOES(IntBuffer var1);

    public void glDrawTexxvOES(int[] var1, int var2);

    public void glEnable(int var1);

    public void glEnableClientState(int var1);

    public void glLoadPaletteFromModelViewMatrixOES();

    public void glMatrixIndexPointerOES(int var1, int var2, int var3, int var4);

    public void glMatrixIndexPointerOES(int var1, int var2, int var3, Buffer var4);

    public void glTexParameterfv(int var1, int var2, float[] var3, int var4);

    public void glWeightPointerOES(int var1, int var2, int var3, int var4);

    public void glWeightPointerOES(int var1, int var2, int var3, Buffer var4);
}

