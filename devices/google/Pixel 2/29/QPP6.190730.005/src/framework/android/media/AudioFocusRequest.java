/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;

public final class AudioFocusRequest {
    private static final AudioAttributes FOCUS_DEFAULT_ATTR = new AudioAttributes.Builder().setUsage(1).build();
    public static final String KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING = "a11y_force_ducking";
    private final AudioAttributes mAttr;
    private final int mFlags;
    private final int mFocusGain;
    private final AudioManager.OnAudioFocusChangeListener mFocusListener;
    private final Handler mListenerHandler;

    private AudioFocusRequest(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler, AudioAttributes audioAttributes, int n, int n2) {
        this.mFocusListener = onAudioFocusChangeListener;
        this.mListenerHandler = handler;
        this.mFocusGain = n;
        this.mAttr = audioAttributes;
        this.mFlags = n2;
    }

    static /* synthetic */ AudioAttributes access$000() {
        return FOCUS_DEFAULT_ATTR;
    }

    static final boolean isValidFocusGain(int n) {
        return n == 1 || n == 2 || n == 3 || n == 4;
    }

    public boolean acceptsDelayedFocusGain() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAttr;
    }

    int getFlags() {
        return this.mFlags;
    }

    public int getFocusGain() {
        return this.mFocusGain;
    }

    public AudioManager.OnAudioFocusChangeListener getOnAudioFocusChangeListener() {
        return this.mFocusListener;
    }

    public Handler getOnAudioFocusChangeListenerHandler() {
        return this.mListenerHandler;
    }

    @SystemApi
    public boolean locksFocus() {
        boolean bl = (this.mFlags & 4) == 4;
        return bl;
    }

    public boolean willPauseWhenDucked() {
        boolean bl = (this.mFlags & 2) == 2;
        return bl;
    }

    public static final class Builder {
        private boolean mA11yForceDucking = false;
        private AudioAttributes mAttr = AudioFocusRequest.access$000();
        private boolean mDelayedFocus = false;
        private int mFocusGain;
        private AudioManager.OnAudioFocusChangeListener mFocusListener;
        private boolean mFocusLocked = false;
        private Handler mListenerHandler;
        private boolean mPausesOnDuck = false;

        public Builder(int n) {
            this.setFocusGain(n);
        }

        public Builder(AudioFocusRequest audioFocusRequest) {
            if (audioFocusRequest != null) {
                this.mAttr = audioFocusRequest.mAttr;
                this.mFocusListener = audioFocusRequest.mFocusListener;
                this.mListenerHandler = audioFocusRequest.mListenerHandler;
                this.mFocusGain = audioFocusRequest.mFocusGain;
                this.mPausesOnDuck = audioFocusRequest.willPauseWhenDucked();
                this.mDelayedFocus = audioFocusRequest.acceptsDelayedFocusGain();
                return;
            }
            throw new IllegalArgumentException("Illegal null AudioFocusRequest");
        }

        public AudioFocusRequest build() {
            if (!this.mDelayedFocus && !this.mPausesOnDuck || this.mFocusListener != null) {
                if (this.mA11yForceDucking) {
                    Bundle bundle = this.mAttr.getBundle() == null ? new Bundle() : this.mAttr.getBundle();
                    bundle.putBoolean(AudioFocusRequest.KEY_ACCESSIBILITY_FORCE_FOCUS_DUCKING, true);
                    this.mAttr = new AudioAttributes.Builder(this.mAttr).addBundle(bundle).build();
                }
                boolean bl = this.mDelayedFocus;
                int n = 0;
                int n2 = this.mPausesOnDuck ? 2 : 0;
                if (this.mFocusLocked) {
                    n = 4;
                }
                return new AudioFocusRequest(this.mFocusListener, this.mListenerHandler, this.mAttr, this.mFocusGain, bl | false | n2 | n);
            }
            throw new IllegalStateException("Can't use delayed focus or pause on duck without a listener");
        }

        public Builder setAcceptsDelayedFocusGain(boolean bl) {
            this.mDelayedFocus = bl;
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) {
            if (audioAttributes != null) {
                this.mAttr = audioAttributes;
                return this;
            }
            throw new NullPointerException("Illegal null AudioAttributes");
        }

        public Builder setFocusGain(int n) {
            if (AudioFocusRequest.isValidFocusGain(n)) {
                this.mFocusGain = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal audio focus gain type ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder setForceDucking(boolean bl) {
            this.mA11yForceDucking = bl;
            return this;
        }

        @SystemApi
        public Builder setLocksFocus(boolean bl) {
            this.mFocusLocked = bl;
            return this;
        }

        public Builder setOnAudioFocusChangeListener(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
            if (onAudioFocusChangeListener != null) {
                this.mFocusListener = onAudioFocusChangeListener;
                this.mListenerHandler = null;
                return this;
            }
            throw new NullPointerException("Illegal null focus listener");
        }

        public Builder setOnAudioFocusChangeListener(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler) {
            if (onAudioFocusChangeListener != null && handler != null) {
                this.mFocusListener = onAudioFocusChangeListener;
                this.mListenerHandler = handler;
                return this;
            }
            throw new NullPointerException("Illegal null focus listener or handler");
        }

        Builder setOnAudioFocusChangeListenerInt(AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, Handler handler) {
            this.mFocusListener = onAudioFocusChangeListener;
            this.mListenerHandler = handler;
            return this;
        }

        public Builder setWillPauseWhenDucked(boolean bl) {
            this.mPausesOnDuck = bl;
            return this;
        }
    }

}

