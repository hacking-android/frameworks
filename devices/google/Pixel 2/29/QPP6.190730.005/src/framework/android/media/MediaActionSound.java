/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

public class MediaActionSound {
    public static final int FOCUS_COMPLETE = 1;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    private static final String[] SOUND_DIRS = new String[]{"/product/media/audio/ui/", "/system/media/audio/ui/"};
    private static final String[] SOUND_FILES = new String[]{"camera_click.ogg", "camera_focus.ogg", "VideoRecord.ogg", "VideoStop.ogg"};
    public static final int START_VIDEO_RECORDING = 2;
    private static final int STATE_LOADED = 3;
    private static final int STATE_LOADING = 1;
    private static final int STATE_LOADING_PLAY_REQUESTED = 2;
    private static final int STATE_NOT_LOADED = 0;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final String TAG = "MediaActionSound";
    private SoundPool.OnLoadCompleteListener mLoadCompleteListener = new SoundPool.OnLoadCompleteListener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onLoadComplete(SoundPool object, int n, int n2) {
            SoundState soundState;
            Object object2;
            block10 : {
                object2 = MediaActionSound.this.mSounds;
                int n3 = ((SoundState[])object2).length;
                int n4 = 0;
                while (n4 < n3) {
                    soundState = object2[n4];
                    if (soundState.id != n) {
                        ++n4;
                        continue;
                    }
                    break block10;
                }
                return;
            }
            n = 0;
            synchronized (soundState) {
                if (n2 != 0) {
                    soundState.state = 0;
                    soundState.id = 0;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("OnLoadCompleteListener() error: ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" loading sound: ");
                    ((StringBuilder)object).append(soundState.name);
                    Log.e(MediaActionSound.TAG, ((StringBuilder)object).toString());
                    return;
                }
                n2 = soundState.state;
                if (n2 != 1) {
                    if (n2 != 2) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("OnLoadCompleteListener() called in wrong state: ");
                        ((StringBuilder)object2).append(soundState.state);
                        ((StringBuilder)object2).append(" for sound: ");
                        ((StringBuilder)object2).append(soundState.name);
                        Log.e(MediaActionSound.TAG, ((StringBuilder)object2).toString());
                    } else {
                        n = soundState.id;
                        soundState.state = 3;
                    }
                } else {
                    soundState.state = 3;
                }
            }
            if (n == 0) return;
            ((SoundPool)object).play(n, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    };
    private SoundPool mSoundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setFlags(1).setContentType(4).build()).build();
    private SoundState[] mSounds;

    public MediaActionSound() {
        SoundState[] arrsoundState;
        this.mSoundPool.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSounds = new SoundState[SOUND_FILES.length];
        for (int i = 0; i < (arrsoundState = this.mSounds).length; ++i) {
            arrsoundState[i] = new SoundState(i);
        }
    }

    private int loadSound(SoundState soundState) {
        String string2 = SOUND_FILES[soundState.name];
        for (String string3 : SOUND_DIRS) {
            SoundPool soundPool = this.mSoundPool;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(string2);
            int n = soundPool.load(stringBuilder.toString(), 1);
            if (n <= 0) continue;
            soundState.state = 1;
            soundState.id = n;
            return n;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void load(int n) {
        if (n >= 0 && n < SOUND_FILES.length) {
            SoundState soundState = this.mSounds[n];
            synchronized (soundState) {
                if (soundState.state != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("load() called in wrong state: ");
                    stringBuilder.append(soundState);
                    stringBuilder.append(" for sound: ");
                    stringBuilder.append(n);
                    Log.e(TAG, stringBuilder.toString());
                } else if (this.loadSound(soundState) <= 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("load() error loading sound: ");
                    stringBuilder.append(n);
                    Log.e(TAG, stringBuilder.toString());
                }
                return;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown sound requested: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void play(int n) {
        if (n >= 0 && n < SOUND_FILES.length) {
            SoundState soundState = this.mSounds[n];
            // MONITORENTER : soundState
            int n2 = soundState.state;
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("play() called in wrong state: ");
                        stringBuilder.append(soundState.state);
                        stringBuilder.append(" for sound: ");
                        stringBuilder.append(n);
                        Log.e(TAG, stringBuilder.toString());
                        return;
                    }
                    this.mSoundPool.play(soundState.id, 1.0f, 1.0f, 0, 0, 1.0f);
                    return;
                }
            } else {
                this.loadSound(soundState);
                if (this.loadSound(soundState) <= 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("play() error loading sound: ");
                    stringBuilder.append(n);
                    Log.e(TAG, stringBuilder.toString());
                    return;
                }
            }
            soundState.state = 2;
            // MONITOREXIT : soundState
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown sound requested: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void release() {
        if (this.mSoundPool != null) {
            for (SoundState soundState : this.mSounds) {
                synchronized (soundState) {
                    soundState.state = 0;
                    soundState.id = 0;
                }
            }
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }

    private class SoundState {
        public int id;
        public final int name;
        public int state;

        public SoundState(int n) {
            this.name = n;
            this.id = 0;
            this.state = 0;
        }
    }

}

