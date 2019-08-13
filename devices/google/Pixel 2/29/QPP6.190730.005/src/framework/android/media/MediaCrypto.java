/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaCryptoException;
import java.util.UUID;

public final class MediaCrypto {
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
        MediaCrypto.native_init();
    }

    public MediaCrypto(UUID uUID, byte[] arrby) throws MediaCryptoException {
        this.native_setup(MediaCrypto.getByteArrayFromUUID(uUID), arrby);
    }

    private static final byte[] getByteArrayFromUUID(UUID arrby) {
        long l = arrby.getMostSignificantBits();
        long l2 = arrby.getLeastSignificantBits();
        arrby = new byte[16];
        for (int i = 0; i < 8; ++i) {
            arrby[i] = (byte)(l >>> (7 - i) * 8);
            arrby[i + 8] = (byte)(l2 >>> (7 - i) * 8);
        }
        return arrby;
    }

    public static final boolean isCryptoSchemeSupported(UUID uUID) {
        return MediaCrypto.isCryptoSchemeSupportedNative(MediaCrypto.getByteArrayFromUUID(uUID));
    }

    private static final native boolean isCryptoSchemeSupportedNative(byte[] var0);

    private final native void native_finalize();

    private static final native void native_init();

    private final native void native_setup(byte[] var1, byte[] var2) throws MediaCryptoException;

    protected void finalize() {
        this.native_finalize();
    }

    public final native void release();

    public final native boolean requiresSecureDecoderComponent(String var1);

    public final native void setMediaDrmSession(byte[] var1) throws MediaCryptoException;
}

