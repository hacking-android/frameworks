/*
 * Decompiled with CFR 0.145.
 */
package android.opengl;

import java.nio.Buffer;

public class ETC1 {
    public static final int DECODED_BLOCK_SIZE = 48;
    public static final int ENCODED_BLOCK_SIZE = 8;
    public static final int ETC1_RGB8_OES = 36196;
    public static final int ETC_PKM_HEADER_SIZE = 16;

    public static native void decodeBlock(Buffer var0, Buffer var1);

    public static native void decodeImage(Buffer var0, Buffer var1, int var2, int var3, int var4, int var5);

    public static native void encodeBlock(Buffer var0, int var1, Buffer var2);

    public static native void encodeImage(Buffer var0, int var1, int var2, int var3, int var4, Buffer var5);

    public static native void formatHeader(Buffer var0, int var1, int var2);

    public static native int getEncodedDataSize(int var0, int var1);

    public static native int getHeight(Buffer var0);

    public static native int getWidth(Buffer var0);

    public static native boolean isValid(Buffer var0);
}

