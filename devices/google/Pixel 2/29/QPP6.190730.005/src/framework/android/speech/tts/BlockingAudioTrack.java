/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioTrack;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

class BlockingAudioTrack {
    private static final boolean DBG = false;
    private static final long MAX_PROGRESS_WAIT_MS = 2500L;
    private static final long MAX_SLEEP_TIME_MS = 2500L;
    private static final int MIN_AUDIO_BUFFER_SIZE = 8192;
    private static final long MIN_SLEEP_TIME_MS = 20L;
    private static final String TAG = "TTS.BlockingAudioTrack";
    private int mAudioBufferSize;
    private final int mAudioFormat;
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private AudioTrack mAudioTrack;
    private Object mAudioTrackLock = new Object();
    private final int mBytesPerFrame;
    private int mBytesWritten = 0;
    private final int mChannelCount;
    private boolean mIsShortUtterance;
    private final int mSampleRateInHz;
    private int mSessionId;
    private volatile boolean mStopped;

    BlockingAudioTrack(TextToSpeechService.AudioOutputParams audioOutputParams, int n, int n2, int n3) {
        this.mAudioParams = audioOutputParams;
        this.mSampleRateInHz = n;
        this.mAudioFormat = n2;
        this.mChannelCount = n3;
        this.mBytesPerFrame = AudioFormat.getBytesPerSample(this.mAudioFormat) * this.mChannelCount;
        this.mIsShortUtterance = false;
        this.mAudioBufferSize = 0;
        this.mBytesWritten = 0;
        this.mAudioTrack = null;
        this.mStopped = false;
    }

    private void blockUntilCompletion(AudioTrack audioTrack) {
        int n;
        int n2 = this.mBytesWritten / this.mBytesPerFrame;
        int n3 = -1;
        long l = 0L;
        while ((n = audioTrack.getPlaybackHeadPosition()) < n2 && audioTrack.getPlayState() == 3 && !this.mStopped) {
            long l2 = BlockingAudioTrack.clip((n2 - n) * 1000 / audioTrack.getSampleRate(), 20L, 2500L);
            if (n == n3) {
                long l3;
                l = l3 = l + l2;
                if (l3 > 2500L) {
                    Log.w(TAG, "Waited unsuccessfully for 2500ms for AudioTrack to make progress, Aborting");
                    break;
                }
            } else {
                l = 0L;
            }
            n3 = n;
            try {
                Thread.sleep(l2);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
                break;
            }
        }
    }

    private void blockUntilDone(AudioTrack audioTrack) {
        if (this.mBytesWritten <= 0) {
            return;
        }
        if (this.mIsShortUtterance) {
            this.blockUntilEstimatedCompletion();
        } else {
            this.blockUntilCompletion(audioTrack);
        }
    }

    private void blockUntilEstimatedCompletion() {
        long l = this.mBytesWritten / this.mBytesPerFrame * 1000 / this.mSampleRateInHz;
        try {
            Thread.sleep(l);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private static final float clip(float f, float f2, float f3) {
        if (f < f2) {
            f = f2;
        } else if (!(f < f3)) {
            f = f3;
        }
        return f;
    }

    private static final long clip(long l, long l2, long l3) {
        if (l < l2) {
            l = l2;
        } else if (l >= l3) {
            l = l3;
        }
        return l;
    }

    private AudioTrack createStreamingAudioTrack() {
        int n = BlockingAudioTrack.getChannelConfig(this.mChannelCount);
        int n2 = Math.max(8192, AudioTrack.getMinBufferSize(this.mSampleRateInHz, n, this.mAudioFormat));
        Object object = new AudioFormat.Builder().setChannelMask(n).setEncoding(this.mAudioFormat).setSampleRate(this.mSampleRateInHz).build();
        if (((AudioTrack)(object = new AudioTrack(this.mAudioParams.mAudioAttributes, (AudioFormat)object, n2, 1, this.mAudioParams.mSessionId))).getState() != 1) {
            Log.w(TAG, "Unable to create audio track.");
            ((AudioTrack)object).release();
            return null;
        }
        this.mAudioBufferSize = n2;
        BlockingAudioTrack.setupVolume((AudioTrack)object, this.mAudioParams.mVolume, this.mAudioParams.mPan);
        return object;
    }

    static int getChannelConfig(int n) {
        if (n == 1) {
            return 4;
        }
        if (n == 2) {
            return 12;
        }
        return 0;
    }

    private static void setupVolume(AudioTrack audioTrack, float f, float f2) {
        float f3;
        float f4 = BlockingAudioTrack.clip(f, 0.0f, 1.0f);
        float f5 = BlockingAudioTrack.clip(f2, -1.0f, 1.0f);
        f = f4;
        f2 = f4;
        if (f5 > 0.0f) {
            f4 = f * (1.0f - f5);
            f3 = f2;
        } else {
            f4 = f;
            f3 = f2;
            if (f5 < 0.0f) {
                f3 = f2 * (1.0f + f5);
                f4 = f;
            }
        }
        if (audioTrack.setStereoVolume(f4, f3) != 0) {
            Log.e(TAG, "Failed to set volume");
        }
    }

    private static int writeToAudioTrack(AudioTrack audioTrack, byte[] arrby) {
        int n;
        int n2;
        if (audioTrack.getPlayState() != 3) {
            audioTrack.play();
        }
        for (n = 0; n < arrby.length && (n2 = audioTrack.write(arrby, n, arrby.length)) > 0; n += n2) {
        }
        return n;
    }

    long getAudioLengthMs(int n) {
        return n / this.mBytesPerFrame * 1000 / this.mSampleRateInHz;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean init() {
        AudioTrack audioTrack = this.createStreamingAudioTrack();
        Object object = this.mAudioTrackLock;
        synchronized (object) {
            this.mAudioTrack = audioTrack;
            return audioTrack != null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setNotificationMarkerPosition(int n) {
        Object object = this.mAudioTrackLock;
        synchronized (object) {
            if (this.mAudioTrack != null) {
                this.mAudioTrack.setNotificationMarkerPosition(n);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPlaybackPositionUpdateListener(AudioTrack.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        Object object = this.mAudioTrackLock;
        synchronized (object) {
            if (this.mAudioTrack != null) {
                this.mAudioTrack.setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() {
        Object object = this.mAudioTrackLock;
        synchronized (object) {
            if (this.mAudioTrack != null) {
                this.mAudioTrack.stop();
            }
            this.mStopped = true;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void waitAndRelease() {
        Object object = this.mAudioTrackLock;
        // MONITORENTER : object
        AudioTrack audioTrack = this.mAudioTrack;
        // MONITOREXIT : object
        if (audioTrack == null) {
            return;
        }
        if (this.mBytesWritten < this.mAudioBufferSize && !this.mStopped) {
            this.mIsShortUtterance = true;
            audioTrack.stop();
        }
        if (!this.mStopped) {
            this.blockUntilDone(this.mAudioTrack);
        }
        object = this.mAudioTrackLock;
        // MONITORENTER : object
        this.mAudioTrack = null;
        // MONITOREXIT : object
        audioTrack.release();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int write(byte[] arrby) {
        AudioTrack audioTrack;
        Object object = this.mAudioTrackLock;
        synchronized (object) {
            audioTrack = this.mAudioTrack;
        }
        if (audioTrack == null) return -1;
        if (this.mStopped) {
            return -1;
        }
        int n = BlockingAudioTrack.writeToAudioTrack(audioTrack, arrby);
        this.mBytesWritten += n;
        return n;
    }
}

