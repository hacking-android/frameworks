/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.media.audiopolicy.AudioMixingRule;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public class AudioMix {
    private static final int CALLBACK_FLAGS_ALL = 1;
    public static final int CALLBACK_FLAG_NOTIFY_ACTIVITY = 1;
    public static final int MIX_STATE_DISABLED = -1;
    public static final int MIX_STATE_IDLE = 0;
    public static final int MIX_STATE_MIXING = 1;
    public static final int MIX_TYPE_INVALID = -1;
    public static final int MIX_TYPE_PLAYERS = 0;
    public static final int MIX_TYPE_RECORDERS = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_BYTES_PER_SAMPLE = 2;
    private static final int PRIVILEDGED_CAPTURE_MAX_CHANNEL_NUMBER = 1;
    private static final int PRIVILEDGED_CAPTURE_MAX_SAMPLE_RATE = 16000;
    public static final int ROUTE_FLAG_LOOP_BACK = 2;
    public static final int ROUTE_FLAG_LOOP_BACK_RENDER = 3;
    public static final int ROUTE_FLAG_RENDER = 1;
    private static final int ROUTE_FLAG_SUPPORTED = 3;
    @UnsupportedAppUsage
    int mCallbackFlags;
    @UnsupportedAppUsage
    String mDeviceAddress;
    @UnsupportedAppUsage
    final int mDeviceSystemType;
    @UnsupportedAppUsage
    private AudioFormat mFormat;
    int mMixState = -1;
    @UnsupportedAppUsage
    private int mMixType = -1;
    @UnsupportedAppUsage
    private int mRouteFlags;
    @UnsupportedAppUsage
    private AudioMixingRule mRule;

    private AudioMix(AudioMixingRule object, AudioFormat audioFormat, int n, int n2, int n3, String string2) {
        this.mRule = object;
        this.mFormat = audioFormat;
        this.mRouteFlags = n;
        this.mMixType = ((AudioMixingRule)object).getTargetMixType();
        this.mCallbackFlags = n2;
        this.mDeviceSystemType = n3;
        object = string2 == null ? new String("") : string2;
        this.mDeviceAddress = object;
    }

    public static String canBeUsedForPrivilegedCapture(AudioFormat object) {
        int n = ((AudioFormat)object).getSampleRate();
        if (n <= 16000 && n > 0) {
            n = ((AudioFormat)object).getChannelCount();
            if (n <= 1 && n > 0) {
                n = ((AudioFormat)object).getEncoding();
                if (AudioFormat.isPublicEncoding(n) && AudioFormat.isEncodingLinearPcm(n)) {
                    if (AudioFormat.getBytesPerSample(n) > 2) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Privileged audio capture encoding ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" can not be over ");
                        ((StringBuilder)object).append(2);
                        ((StringBuilder)object).append(" bytes per sample");
                        return ((StringBuilder)object).toString();
                    }
                    return null;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Privileged audio capture encoding ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("is not linear");
                return ((StringBuilder)object).toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Privileged audio capture channel count ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" can not be over ");
            ((StringBuilder)object).append(1);
            return ((StringBuilder)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Privileged audio capture sample rate ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" can not be over ");
        ((StringBuilder)object).append(16000);
        ((StringBuilder)object).append("kHz");
        return ((StringBuilder)object).toString();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AudioMix)object;
            if (this.mRouteFlags != ((AudioMix)object).mRouteFlags || this.mRule != ((AudioMix)object).mRule || this.mMixType != ((AudioMix)object).mMixType || this.mFormat != ((AudioMix)object).mFormat) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public AudioFormat getFormat() {
        return this.mFormat;
    }

    public int getMixState() {
        return this.mMixState;
    }

    public int getMixType() {
        return this.mMixType;
    }

    public String getRegistration() {
        return this.mDeviceAddress;
    }

    public int getRouteFlags() {
        return this.mRouteFlags;
    }

    public AudioMixingRule getRule() {
        return this.mRule;
    }

    public int hashCode() {
        return Objects.hash(this.mRouteFlags, this.mRule, this.mMixType, this.mFormat);
    }

    public boolean isAffectingUsage(int n) {
        return this.mRule.isAffectingUsage(n);
    }

    public boolean isRoutedToDevice(int n, String string2) {
        if ((this.mRouteFlags & 1) != 1) {
            return false;
        }
        if (n != this.mDeviceSystemType) {
            return false;
        }
        return string2.equals(this.mDeviceAddress);
    }

    void setRegistration(String string2) {
        this.mDeviceAddress = string2;
    }

    public static class Builder {
        private int mCallbackFlags = 0;
        private String mDeviceAddress = null;
        private int mDeviceSystemType = 0;
        private AudioFormat mFormat = null;
        private int mRouteFlags = 0;
        private AudioMixingRule mRule = null;

        Builder() {
        }

        public Builder(AudioMixingRule audioMixingRule) throws IllegalArgumentException {
            if (audioMixingRule != null) {
                this.mRule = audioMixingRule;
                return;
            }
            throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public AudioMix build() throws IllegalArgumentException {
            String string2;
            int n;
            if (this.mRule == null) throw new IllegalArgumentException("Illegal null AudioMixingRule");
            if (this.mRouteFlags == 0) {
                this.mRouteFlags = 2;
            }
            if (this.mFormat == null) {
                int n2;
                n = n2 = AudioSystem.getPrimaryOutputSamplingRate();
                if (n2 <= 0) {
                    n = 44100;
                }
                this.mFormat = new AudioFormat.Builder().setSampleRate(n).build();
            }
            if ((n = this.mDeviceSystemType) != 0 && n != 32768 && n != -2147483392) {
                if ((this.mRouteFlags & 1) == 0) throw new IllegalArgumentException("Can't have audio device without flag ROUTE_FLAG_RENDER");
                if (this.mRule.getTargetMixType() != 0) {
                    throw new IllegalArgumentException("Unsupported device on non-playback mix");
                }
            } else {
                n = this.mRouteFlags;
                if ((n & 3) == 1) throw new IllegalArgumentException("Can't have flag ROUTE_FLAG_RENDER without an audio device");
                if ((n & 2) == 2) {
                    if (this.mRule.getTargetMixType() == 0) {
                        this.mDeviceSystemType = 32768;
                    } else {
                        if (this.mRule.getTargetMixType() != 1) throw new IllegalArgumentException("Unknown mixing rule type");
                        this.mDeviceSystemType = -2147483392;
                    }
                }
            }
            if (!this.mRule.allowPrivilegedPlaybackCapture() || (string2 = AudioMix.canBeUsedForPrivilegedCapture(this.mFormat)) == null) return new AudioMix(this.mRule, this.mFormat, this.mRouteFlags, this.mCallbackFlags, this.mDeviceSystemType, this.mDeviceAddress);
            throw new IllegalArgumentException(string2);
        }

        Builder setCallbackFlags(int n) throws IllegalArgumentException {
            if (n != 0 && (n & 1) == 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal callback flags 0x");
                stringBuilder.append(Integer.toHexString(n).toUpperCase());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mCallbackFlags = n;
            return this;
        }

        Builder setDevice(int n, String string2) {
            this.mDeviceSystemType = n;
            this.mDeviceAddress = string2;
            return this;
        }

        public Builder setDevice(AudioDeviceInfo audioDeviceInfo) throws IllegalArgumentException {
            if (audioDeviceInfo != null) {
                if (audioDeviceInfo.isSink()) {
                    this.mDeviceSystemType = AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
                    this.mDeviceAddress = audioDeviceInfo.getAddress();
                    return this;
                }
                throw new IllegalArgumentException("Unsupported device type on mix, not a sink");
            }
            throw new IllegalArgumentException("Illegal null AudioDeviceInfo argument");
        }

        public Builder setFormat(AudioFormat audioFormat) throws IllegalArgumentException {
            if (audioFormat != null) {
                this.mFormat = audioFormat;
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        }

        Builder setMixingRule(AudioMixingRule audioMixingRule) throws IllegalArgumentException {
            if (audioMixingRule != null) {
                this.mRule = audioMixingRule;
                return this;
            }
            throw new IllegalArgumentException("Illegal null AudioMixingRule argument");
        }

        public Builder setRouteFlags(int n) throws IllegalArgumentException {
            if (n != 0) {
                if ((n & 3) != 0) {
                    if ((n & -4) == 0) {
                        this.mRouteFlags = n;
                        return this;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown route flags 0x");
                    stringBuilder.append(Integer.toHexString(n));
                    stringBuilder.append("when configuring an AudioMix");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid route flags 0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append("when configuring an AudioMix");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new IllegalArgumentException("Illegal empty route flags");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RouteFlags {
    }

}

