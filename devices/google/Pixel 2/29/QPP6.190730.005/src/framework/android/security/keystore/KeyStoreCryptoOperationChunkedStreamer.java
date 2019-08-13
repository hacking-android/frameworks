/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import android.os.IBinder;
import android.security.KeyStore;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.OperationResult;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyStoreConnectException;
import android.security.keystore.KeyStoreCryptoOperationStreamer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.ProviderException;
import libcore.util.EmptyArray;

class KeyStoreCryptoOperationChunkedStreamer
implements KeyStoreCryptoOperationStreamer {
    private static final int DEFAULT_MAX_CHUNK_SIZE = 65536;
    private byte[] mBuffered = EmptyArray.BYTE;
    private int mBufferedLength;
    private int mBufferedOffset;
    private long mConsumedInputSizeBytes;
    private final Stream mKeyStoreStream;
    private final int mMaxChunkSize;
    private long mProducedOutputSizeBytes;

    public KeyStoreCryptoOperationChunkedStreamer(Stream stream) {
        this(stream, 65536);
    }

    public KeyStoreCryptoOperationChunkedStreamer(Stream stream, int n) {
        this.mKeyStoreStream = stream;
        this.mMaxChunkSize = n;
    }

    @Override
    public byte[] doFinal(byte[] arrby, int n, int n2, byte[] object, byte[] arrby2) throws KeyStoreException {
        if (n2 == 0) {
            arrby = EmptyArray.BYTE;
            n = 0;
        }
        arrby = ArrayUtils.concat(this.update(arrby, n, n2), this.flush());
        if ((object = this.mKeyStoreStream.finish((byte[])object, arrby2)) != null) {
            if (object.resultCode == 1) {
                this.mProducedOutputSizeBytes += (long)object.output.length;
                return ArrayUtils.concat(arrby, object.output);
            }
            throw KeyStore.getKeyStoreException(object.resultCode);
        }
        throw new KeyStoreConnectException();
    }

    public byte[] flush() throws KeyStoreException {
        int n;
        Object object;
        if (this.mBufferedLength <= 0) {
            return EmptyArray.BYTE;
        }
        Object object2 = null;
        while ((n = this.mBufferedLength) > 0) {
            object = ArrayUtils.subarray(this.mBuffered, this.mBufferedOffset, n);
            OperationResult operationResult = this.mKeyStoreStream.update((byte[])object);
            if (operationResult != null) {
                if (operationResult.resultCode == 1) {
                    if (operationResult.inputConsumed <= 0) break;
                    if (operationResult.inputConsumed >= ((Object)object).length) {
                        this.mBuffered = EmptyArray.BYTE;
                        this.mBufferedOffset = 0;
                        this.mBufferedLength = 0;
                    } else {
                        this.mBuffered = object;
                        this.mBufferedOffset = operationResult.inputConsumed;
                        this.mBufferedLength = ((Object)object).length - operationResult.inputConsumed;
                    }
                    if (operationResult.inputConsumed <= ((Object)object).length) {
                        object = object2;
                        if (operationResult.output != null) {
                            object = object2;
                            if (operationResult.output.length > 0) {
                                object = object2;
                                if (object2 == null) {
                                    if (this.mBufferedLength == 0) {
                                        this.mProducedOutputSizeBytes += (long)operationResult.output.length;
                                        return operationResult.output;
                                    }
                                    object = new ByteArrayOutputStream();
                                }
                                try {
                                    ((OutputStream)object).write(operationResult.output);
                                }
                                catch (IOException iOException) {
                                    throw new ProviderException("Failed to buffer output", iOException);
                                }
                            }
                        }
                        object2 = object;
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Keystore consumed more input than provided. Provided: ");
                    ((StringBuilder)object2).append(((Object)object).length);
                    ((StringBuilder)object2).append(", consumed: ");
                    ((StringBuilder)object2).append(operationResult.inputConsumed);
                    throw new KeyStoreException(-1000, ((StringBuilder)object2).toString());
                }
                throw KeyStore.getKeyStoreException(operationResult.resultCode);
            }
            throw new KeyStoreConnectException();
        }
        if (this.mBufferedLength > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Keystore failed to consume last ");
            if (this.mBufferedLength != 1) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(this.mBufferedLength);
                ((StringBuilder)object2).append(" bytes");
                object2 = ((StringBuilder)object2).toString();
            } else {
                object2 = "byte";
            }
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" of input");
            throw new KeyStoreException(-21, ((StringBuilder)object).toString());
        }
        object2 = object2 != null ? ((ByteArrayOutputStream)object2).toByteArray() : EmptyArray.BYTE;
        this.mProducedOutputSizeBytes += (long)((byte[])object2).length;
        return object2;
    }

    @Override
    public long getConsumedInputSizeBytes() {
        return this.mConsumedInputSizeBytes;
    }

    @Override
    public long getProducedOutputSizeBytes() {
        return this.mProducedOutputSizeBytes;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public byte[] update(byte[] object, int n, int n2) throws KeyStoreException {
        if (n2 == 0) {
            return EmptyArray.BYTE;
        }
        Object object2 = null;
        int n3 = n;
        while (n2 > 0) {
            Object object3;
            int n4 = this.mBufferedLength;
            n = this.mMaxChunkSize;
            if (n4 + n2 > n) {
                object3 = ArrayUtils.concat(this.mBuffered, this.mBufferedOffset, n4, (byte[])object, n3, n -= n4);
            } else if (n4 == 0 && n3 == 0 && n2 == ((Object)object).length) {
                object3 = object;
                n = ((Object)object).length;
            } else {
                object3 = ArrayUtils.concat(this.mBuffered, this.mBufferedOffset, this.mBufferedLength, (byte[])object, n3, n2);
                n = n2;
            }
            n3 += n;
            n2 -= n;
            this.mConsumedInputSizeBytes += (long)n;
            OperationResult operationResult = this.mKeyStoreStream.update((byte[])object3);
            if (operationResult == null) throw new KeyStoreConnectException();
            if (operationResult.resultCode != 1) throw KeyStore.getKeyStoreException(operationResult.resultCode);
            if (operationResult.inputConsumed == ((byte[])object3).length) {
                this.mBuffered = EmptyArray.BYTE;
                this.mBufferedOffset = 0;
                this.mBufferedLength = 0;
            } else if (operationResult.inputConsumed <= 0) {
                if (n2 > 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Keystore consumed nothing from max-sized chunk: ");
                    ((StringBuilder)object).append(((byte[])object3).length);
                    ((StringBuilder)object).append(" bytes");
                    throw new KeyStoreException(-1000, ((StringBuilder)object).toString());
                }
                this.mBuffered = object3;
                this.mBufferedOffset = 0;
                this.mBufferedLength = ((byte[])object3).length;
            } else {
                if (operationResult.inputConsumed >= ((byte[])object3).length) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Keystore consumed more input than provided. Provided: ");
                    ((StringBuilder)object).append(((byte[])object3).length);
                    ((StringBuilder)object).append(", consumed: ");
                    ((StringBuilder)object).append(operationResult.inputConsumed);
                    throw new KeyStoreException(-1000, ((StringBuilder)object).toString());
                }
                this.mBuffered = object3;
                this.mBufferedOffset = operationResult.inputConsumed;
                this.mBufferedLength = ((byte[])object3).length - operationResult.inputConsumed;
            }
            object3 = object2;
            if (operationResult.output != null) {
                object3 = object2;
                if (operationResult.output.length > 0) {
                    if (this.mBufferedLength + n2 > 0) {
                        object3 = object2;
                        if (object2 == null) {
                            object3 = new ByteArrayOutputStream();
                        }
                        try {
                            object3.write(operationResult.output);
                        }
                        catch (IOException iOException) {
                            throw new ProviderException("Failed to buffer output", iOException);
                        }
                    } else {
                        if (object2 == null) {
                            object = operationResult.output;
                        } else {
                            ((OutputStream)object2).write(operationResult.output);
                            object = ((ByteArrayOutputStream)object2).toByteArray();
                        }
                        this.mProducedOutputSizeBytes += (long)((Object)object).length;
                        return object;
                        catch (IOException iOException) {
                            throw new ProviderException("Failed to buffer output", iOException);
                        }
                    }
                }
            }
            object2 = object3;
        }
        object = object2 == null ? EmptyArray.BYTE : ((ByteArrayOutputStream)object2).toByteArray();
        this.mProducedOutputSizeBytes += (long)((Object)object).length;
        return object;
    }

    public static class MainDataStream
    implements Stream {
        private final KeyStore mKeyStore;
        private final IBinder mOperationToken;

        public MainDataStream(KeyStore keyStore, IBinder iBinder) {
            this.mKeyStore = keyStore;
            this.mOperationToken = iBinder;
        }

        @Override
        public OperationResult finish(byte[] arrby, byte[] arrby2) {
            return this.mKeyStore.finish(this.mOperationToken, null, arrby, arrby2);
        }

        @Override
        public OperationResult update(byte[] arrby) {
            return this.mKeyStore.update(this.mOperationToken, null, arrby);
        }
    }

    static interface Stream {
        public OperationResult finish(byte[] var1, byte[] var2);

        public OperationResult update(byte[] var1);
    }

}

