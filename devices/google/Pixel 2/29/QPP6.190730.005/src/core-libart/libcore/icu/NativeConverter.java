/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import libcore.util.NativeAllocationRegistry;

public final class NativeConverter {
    private static final NativeAllocationRegistry registry = new NativeAllocationRegistry(NativeConverter.class.getClassLoader(), NativeConverter.getNativeFinalizer(), NativeConverter.getNativeSize());

    public static native Charset charsetForName(String var0);

    public static native void closeConverter(long var0);

    public static native boolean contains(String var0, String var1);

    public static native int decode(long var0, byte[] var2, int var3, char[] var4, int var5, int[] var6, boolean var7);

    public static native int encode(long var0, char[] var2, int var3, byte[] var4, int var5, int[] var6, boolean var7);

    public static native String[] getAvailableCharsetNames();

    public static native float getAveBytesPerChar(long var0);

    public static native float getAveCharsPerByte(long var0);

    public static native int getMaxBytesPerChar(long var0);

    public static native int getMinBytesPerChar(long var0);

    public static native long getNativeFinalizer();

    public static native long getNativeSize();

    public static native byte[] getSubstitutionBytes(long var0);

    public static native long openConverter(String var0);

    public static void registerConverter(Object object, long l) {
        registry.registerNativeAllocation(object, l);
    }

    public static native void resetByteToChar(long var0);

    public static native void resetCharToByte(long var0);

    private static native void setCallbackDecode(long var0, int var2, int var3, String var4);

    public static void setCallbackDecode(long l, CharsetDecoder charsetDecoder) {
        NativeConverter.setCallbackDecode(l, NativeConverter.translateCodingErrorAction(charsetDecoder.malformedInputAction()), NativeConverter.translateCodingErrorAction(charsetDecoder.unmappableCharacterAction()), charsetDecoder.replacement());
    }

    private static native void setCallbackEncode(long var0, int var2, int var3, byte[] var4);

    public static void setCallbackEncode(long l, CharsetEncoder charsetEncoder) {
        NativeConverter.setCallbackEncode(l, NativeConverter.translateCodingErrorAction(charsetEncoder.malformedInputAction()), NativeConverter.translateCodingErrorAction(charsetEncoder.unmappableCharacterAction()), charsetEncoder.replacement());
    }

    private static int translateCodingErrorAction(CodingErrorAction codingErrorAction) {
        if (codingErrorAction == CodingErrorAction.REPORT) {
            return 0;
        }
        if (codingErrorAction == CodingErrorAction.IGNORE) {
            return 1;
        }
        if (codingErrorAction == CodingErrorAction.REPLACE) {
            return 2;
        }
        throw new AssertionError();
    }
}

