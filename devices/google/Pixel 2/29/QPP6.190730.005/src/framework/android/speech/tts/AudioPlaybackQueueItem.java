/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.ConditionVariable;
import android.speech.tts.PlaybackQueueItem;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

class AudioPlaybackQueueItem
extends PlaybackQueueItem {
    private static final String TAG = "TTS.AudioQueueItem";
    private final TextToSpeechService.AudioOutputParams mAudioParams;
    private final Context mContext;
    private final ConditionVariable mDone;
    private volatile boolean mFinished;
    private MediaPlayer mPlayer;
    private final Uri mUri;

    AudioPlaybackQueueItem(TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, Object object, Context context, Uri uri, TextToSpeechService.AudioOutputParams audioOutputParams) {
        super(utteranceProgressDispatcher, object);
        this.mContext = context;
        this.mUri = uri;
        this.mAudioParams = audioOutputParams;
        this.mDone = new ConditionVariable();
        this.mPlayer = null;
        this.mFinished = false;
    }

    private static final float clip(float f, float f2, float f3) {
        if (f < f2) {
            f = f2;
        } else if (!(f < f3)) {
            f = f3;
        }
        return f;
    }

    private void finish() {
        try {
            this.mPlayer.stop();
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        this.mPlayer.release();
    }

    private static void setupVolume(MediaPlayer mediaPlayer, float f, float f2) {
        float f3;
        float f4 = AudioPlaybackQueueItem.clip(f, 0.0f, 1.0f);
        float f5 = AudioPlaybackQueueItem.clip(f2, -1.0f, 1.0f);
        f = f4;
        f2 = f4;
        if (f5 > 0.0f) {
            f3 = f * (1.0f - f5);
            f4 = f2;
        } else {
            f3 = f;
            f4 = f2;
            if (f5 < 0.0f) {
                f4 = f2 * (1.0f + f5);
                f3 = f;
            }
        }
        mediaPlayer.setVolume(f3, f4);
    }

    @Override
    public void run() {
        TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher = this.getDispatcher();
        utteranceProgressDispatcher.dispatchOnStart();
        int n = this.mAudioParams.mSessionId;
        Context context = this.mContext;
        MediaPlayer.OnCompletionListener onCompletionListener = this.mUri;
        MediaPlayer.OnErrorListener onErrorListener = this.mAudioParams.mAudioAttributes;
        if (n <= 0) {
            n = 0;
        }
        this.mPlayer = MediaPlayer.create(context, (Uri)((Object)onCompletionListener), null, (AudioAttributes)((Object)onErrorListener), n);
        onCompletionListener = this.mPlayer;
        if (onCompletionListener == null) {
            utteranceProgressDispatcher.dispatchOnError(-5);
            return;
        }
        try {
            onErrorListener = new MediaPlayer.OnErrorListener(){

                @Override
                public boolean onError(MediaPlayer object, int n, int n2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Audio playback error: ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(", ");
                    ((StringBuilder)object).append(n2);
                    Log.w(AudioPlaybackQueueItem.TAG, ((StringBuilder)object).toString());
                    AudioPlaybackQueueItem.this.mDone.open();
                    return true;
                }
            };
            ((MediaPlayer)((Object)onCompletionListener)).setOnErrorListener(onErrorListener);
            onErrorListener = this.mPlayer;
            onCompletionListener = new MediaPlayer.OnCompletionListener(){

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    AudioPlaybackQueueItem.this.mFinished = true;
                    AudioPlaybackQueueItem.this.mDone.open();
                }
            };
            ((MediaPlayer)((Object)onErrorListener)).setOnCompletionListener(onCompletionListener);
            AudioPlaybackQueueItem.setupVolume(this.mPlayer, this.mAudioParams.mVolume, this.mAudioParams.mPan);
            this.mPlayer.start();
            this.mDone.block();
            this.finish();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.w(TAG, "MediaPlayer failed", illegalArgumentException);
            this.mDone.open();
        }
        if (this.mFinished) {
            utteranceProgressDispatcher.dispatchOnSuccess();
        } else {
            utteranceProgressDispatcher.dispatchOnStop();
        }
    }

    @Override
    void stop(int n) {
        this.mDone.open();
    }

}

