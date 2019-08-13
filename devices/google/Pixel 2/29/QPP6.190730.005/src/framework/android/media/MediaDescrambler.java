/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.hardware.cas.V1_0.IDescramblerBase;
import android.media.MediaCas;
import android.media.MediaCasException;
import android.media.MediaCasStateException;
import android.media.MediaCodec;
import android.os.IHwBinder;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class MediaDescrambler
implements AutoCloseable {
    public static final byte SCRAMBLE_CONTROL_EVEN_KEY = 2;
    public static final byte SCRAMBLE_CONTROL_ODD_KEY = 3;
    public static final byte SCRAMBLE_CONTROL_RESERVED = 1;
    public static final byte SCRAMBLE_CONTROL_UNSCRAMBLED = 0;
    public static final byte SCRAMBLE_FLAG_PES_HEADER = 1;
    private static final String TAG = "MediaDescrambler";
    private IDescramblerBase mIDescrambler;
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
        MediaDescrambler.native_init();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public MediaDescrambler(int n) throws MediaCasException.UnsupportedCasException {
        Throwable throwable2222;
        block5 : {
            block6 : {
                block4 : {
                    this.mIDescrambler = MediaCas.getService().createDescrambler(n);
                    if (this.mIDescrambler != null) break block4;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported CA_system_id ");
                    stringBuilder.append(n);
                    throw new MediaCasException.UnsupportedCasException(stringBuilder.toString());
                    {
                        catch (Throwable throwable2222) {
                            break block5;
                        }
                        catch (Exception exception) {}
                        {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Failed to create descrambler: ");
                            stringBuilder2.append(exception);
                            Log.e(TAG, stringBuilder2.toString());
                            this.mIDescrambler = null;
                            if (this.mIDescrambler == null) break block6;
                        }
                    }
                }
                this.native_setup(this.mIDescrambler.asBinder());
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported CA_system_id ");
            stringBuilder.append(n);
            throw new MediaCasException.UnsupportedCasException(stringBuilder.toString());
        }
        if (this.mIDescrambler != null) throw throwable2222;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported CA_system_id ");
        stringBuilder.append(n);
        throw new MediaCasException.UnsupportedCasException(stringBuilder.toString());
    }

    private final void cleanupAndRethrowIllegalState() {
        this.mIDescrambler = null;
        throw new IllegalStateException();
    }

    private final native int native_descramble(byte var1, byte var2, int var3, int[] var4, int[] var5, ByteBuffer var6, int var7, int var8, ByteBuffer var9, int var10, int var11) throws RemoteException;

    private static final native void native_init();

    private final native void native_release();

    private final native void native_setup(IHwBinder var1);

    private final void validateInternalStates() {
        if (this.mIDescrambler != null) {
            return;
        }
        throw new IllegalStateException();
    }

    @Override
    public void close() {
        IDescramblerBase iDescramblerBase = this.mIDescrambler;
        if (iDescramblerBase != null) {
            try {
                iDescramblerBase.release();
            }
            catch (Throwable throwable) {
                this.mIDescrambler = null;
                throw throwable;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            this.mIDescrambler = null;
        }
        this.native_release();
    }

    public final int descramble(ByteBuffer object, ByteBuffer byteBuffer, MediaCodec.CryptoInfo cryptoInfo) {
        this.validateInternalStates();
        if (cryptoInfo.numSubSamples > 0) {
            if (cryptoInfo.numBytesOfClearData == null && cryptoInfo.numBytesOfEncryptedData == null) {
                throw new IllegalArgumentException("Invalid CryptoInfo: clearData and encryptedData size arrays are both null!");
            }
            if (cryptoInfo.numBytesOfClearData != null && cryptoInfo.numBytesOfClearData.length < cryptoInfo.numSubSamples) {
                throw new IllegalArgumentException("Invalid CryptoInfo: numBytesOfClearData is too small!");
            }
            if (cryptoInfo.numBytesOfEncryptedData != null && cryptoInfo.numBytesOfEncryptedData.length < cryptoInfo.numSubSamples) {
                throw new IllegalArgumentException("Invalid CryptoInfo: numBytesOfEncryptedData is too small!");
            }
            if (cryptoInfo.key != null && cryptoInfo.key.length == 16) {
                try {
                    int n = this.native_descramble(cryptoInfo.key[0], cryptoInfo.key[1], cryptoInfo.numSubSamples, cryptoInfo.numBytesOfClearData, cryptoInfo.numBytesOfEncryptedData, (ByteBuffer)object, ((Buffer)object).position(), ((Buffer)object).limit(), byteBuffer, byteBuffer.position(), byteBuffer.limit());
                    return n;
                }
                catch (RemoteException remoteException) {
                    this.cleanupAndRethrowIllegalState();
                }
                catch (ServiceSpecificException serviceSpecificException) {
                    MediaCasStateException.throwExceptionIfNeeded(serviceSpecificException.errorCode, serviceSpecificException.getMessage());
                }
                return -1;
            }
            throw new IllegalArgumentException("Invalid CryptoInfo: key array is invalid!");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid CryptoInfo: invalid numSubSamples=");
        ((StringBuilder)object).append(cryptoInfo.numSubSamples);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    protected void finalize() {
        this.close();
    }

    IHwBinder getBinder() {
        this.validateInternalStates();
        return this.mIDescrambler.asBinder();
    }

    public final boolean requiresSecureDecoderComponent(String string2) {
        this.validateInternalStates();
        try {
            boolean bl = this.mIDescrambler.requiresSecureDecoderComponent(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
            return true;
        }
    }

    public final void setMediaCasSession(MediaCas.Session session) {
        this.validateInternalStates();
        try {
            MediaCasStateException.throwExceptionIfNeeded(this.mIDescrambler.setMediaCasSession(session.mSessionId));
        }
        catch (RemoteException remoteException) {
            this.cleanupAndRethrowIllegalState();
        }
    }
}

