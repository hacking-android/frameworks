/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.media.AudioFormat;
import android.speech.tts.AbstractSynthesisCallback;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

class FileSynthesisCallback
extends AbstractSynthesisCallback {
    private static final boolean DBG = false;
    private static final int MAX_AUDIO_BUFFER_SIZE = 8192;
    private static final String TAG = "FileSynthesisRequest";
    private static final short WAV_FORMAT_PCM = 1;
    private static final int WAV_HEADER_LENGTH = 44;
    private int mAudioFormat;
    private int mChannelCount;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;
    private boolean mDone = false;
    private FileChannel mFileChannel;
    private int mSampleRateInHz;
    private boolean mStarted = false;
    private final Object mStateLock = new Object();
    protected int mStatusCode;

    FileSynthesisCallback(FileChannel fileChannel, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, boolean bl) {
        super(bl);
        this.mFileChannel = fileChannel;
        this.mDispatcher = utteranceProgressDispatcher;
        this.mStatusCode = 0;
    }

    private void cleanUp() {
        this.closeFile();
    }

    private void closeFile() {
        this.mFileChannel = null;
    }

    private ByteBuffer makeWavHeader(int n, int n2, int n3, int n4) {
        n2 = AudioFormat.getBytesPerSample(n2);
        short s = (short)(n2 * n3);
        short s2 = (short)(n2 * 8);
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[44]);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(new byte[]{82, 73, 70, 70});
        byteBuffer.putInt(n4 + 44 - 8);
        byteBuffer.put(new byte[]{87, 65, 86, 69});
        byteBuffer.put(new byte[]{102, 109, 116, 32});
        byteBuffer.putInt(16);
        byteBuffer.putShort((short)1);
        byteBuffer.putShort((short)n3);
        byteBuffer.putInt(n);
        byteBuffer.putInt(n * n2 * n3);
        byteBuffer.putShort(s);
        byteBuffer.putShort(s2);
        byteBuffer.put(new byte[]{100, 97, 116, 97});
        byteBuffer.putInt(n4);
        byteBuffer.flip();
        return byteBuffer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int audioAvailable(byte[] arrby, int n, int n2) {
        FileChannel fileChannel;
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mStatusCode == -2) {
                return this.errorCodeOnStop();
            }
            if (this.mStatusCode != 0) {
                return -1;
            }
            if (this.mFileChannel == null) {
                Log.e(TAG, "File not open");
                this.mStatusCode = -5;
                return -1;
            }
            if (!this.mStarted) {
                Log.e(TAG, "Start method was not called");
                return -1;
            }
            fileChannel = this.mFileChannel;
        }
        object = new byte[n2];
        System.arraycopy(arrby, n, object, 0, n2);
        this.mDispatcher.dispatchOnAudioAvailable((byte[])object);
        try {
            fileChannel.write(ByteBuffer.wrap(arrby, n, n2));
            return 0;
        }
        catch (IOException iOException) {
            Log.e(TAG, "Failed to write to output file descriptor", iOException);
            object = this.mStateLock;
            synchronized (object) {
                this.cleanUp();
                this.mStatusCode = -5;
                return -1;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int done() {
        int n;
        int n2;
        Object object;
        int n3;
        Object object2 = this.mStateLock;
        synchronized (object2) {
            if (this.mDone) {
                Log.w(TAG, "Duplicate call to done()");
                return -1;
            }
            if (this.mStatusCode == -2) {
                return this.errorCodeOnStop();
            }
            if (this.mStatusCode != 0 && this.mStatusCode != -2) {
                this.mDispatcher.dispatchOnError(this.mStatusCode);
                return -1;
            }
            if (this.mFileChannel == null) {
                Log.e(TAG, "File not open");
                return -1;
            }
            this.mDone = true;
            object = this.mFileChannel;
            n2 = this.mSampleRateInHz;
            n3 = this.mAudioFormat;
            n = this.mChannelCount;
        }
        try {
            ((FileChannel)object).position(0L);
            int n4 = (int)(((FileChannel)object).size() - 44L);
            ((FileChannel)object).write(this.makeWavHeader(n2, n3, n, n4));
            object = this.mStateLock;
            synchronized (object) {
                this.closeFile();
                this.mDispatcher.dispatchOnSuccess();
            }
        }
        catch (IOException iOException) {
            Log.e(TAG, "Failed to write to output file descriptor", iOException);
            object = this.mStateLock;
            synchronized (object) {
                this.cleanUp();
                return -1;
            }
        }
        return 0;
    }

    @Override
    public void error() {
        this.error(-3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void error(int n) {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mDone) {
                return;
            }
            this.cleanUp();
            this.mStatusCode = n;
            return;
        }
    }

    @Override
    public int getMaxBufferSize() {
        return 8192;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasFinished() {
        Object object = this.mStateLock;
        synchronized (object) {
            return this.mDone;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasStarted() {
        Object object = this.mStateLock;
        synchronized (object) {
            return this.mStarted;
        }
    }

    @Override
    public void rangeStart(int n, int n2, int n3) {
        this.mDispatcher.dispatchOnRangeStart(n, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int start(int n, int n2, int n3) {
        Object object;
        Object object2;
        if (n2 != 3 && n2 != 2 && n2 != 4) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Audio format encoding ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" not supported. Please use one of AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT or AudioFormat.ENCODING_PCM_FLOAT");
            Log.e(TAG, ((StringBuilder)object).toString());
        }
        this.mDispatcher.dispatchOnBeginSynthesis(n, n2, n3);
        object = this.mStateLock;
        synchronized (object) {
            if (this.mStatusCode == -2) {
                return this.errorCodeOnStop();
            }
            if (this.mStatusCode != 0) {
                return -1;
            }
            if (this.mStarted) {
                Log.e(TAG, "Start called twice");
                return -1;
            }
            this.mStarted = true;
            this.mSampleRateInHz = n;
            this.mAudioFormat = n2;
            this.mChannelCount = n3;
            this.mDispatcher.dispatchOnStart();
            object2 = this.mFileChannel;
        }
        try {
            ((FileChannel)object2).write(ByteBuffer.allocate(44));
            return 0;
        }
        catch (IOException iOException) {
            Log.e(TAG, "Failed to write wav header to output file descriptor", iOException);
            object2 = this.mStateLock;
            synchronized (object2) {
                this.cleanUp();
                this.mStatusCode = -5;
                return -1;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void stop() {
        Object object = this.mStateLock;
        synchronized (object) {
            if (this.mDone) {
                return;
            }
            if (this.mStatusCode == -2) {
                return;
            }
            this.mStatusCode = -2;
            this.cleanUp();
            this.mDispatcher.dispatchOnStop();
            return;
        }
    }
}

