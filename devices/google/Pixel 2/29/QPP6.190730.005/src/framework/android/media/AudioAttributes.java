/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.media.audiopolicy.AudioProductStrategy;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class AudioAttributes
implements Parcelable {
    public static final int ALLOW_CAPTURE_BY_ALL = 1;
    public static final int ALLOW_CAPTURE_BY_NONE = 3;
    public static final int ALLOW_CAPTURE_BY_SYSTEM = 2;
    private static final int ALL_PARCEL_FLAGS = 1;
    private static final int ATTR_PARCEL_IS_NULL_BUNDLE = -1977;
    private static final int ATTR_PARCEL_IS_VALID_BUNDLE = 1980;
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final Parcelable.Creator<AudioAttributes> CREATOR;
    private static final int FLAG_ALL = 6143;
    private static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    @SystemApi
    public static final int FLAG_BEACON = 8;
    @SystemApi
    public static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
    @SystemApi
    public static final int FLAG_BYPASS_MUTE = 128;
    public static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;
    @SystemApi
    public static final int FLAG_HW_HOTWORD = 32;
    public static final int FLAG_LOW_LATENCY = 256;
    public static final int FLAG_MUTE_HAPTIC = 2048;
    public static final int FLAG_NO_MEDIA_PROJECTION = 1024;
    public static final int FLAG_NO_SYSTEM_CAPTURE = 4096;
    public static final int FLAG_SCO = 4;
    public static final int FLAG_SECURE = 2;
    public static final int FLATTEN_TAGS = 1;
    public static final int[] SDK_USAGES;
    public static final int SUPPRESSIBLE_ALARM = 4;
    public static final int SUPPRESSIBLE_CALL = 2;
    public static final int SUPPRESSIBLE_MEDIA = 5;
    public static final int SUPPRESSIBLE_NEVER = 3;
    public static final int SUPPRESSIBLE_NOTIFICATION = 1;
    public static final int SUPPRESSIBLE_SYSTEM = 6;
    public static final SparseIntArray SUPPRESSIBLE_USAGES;
    private static final String TAG = "AudioAttributes";
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    public static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    private Bundle mBundle;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mContentType;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mFlags;
    @UnsupportedAppUsage
    private String mFormattedTags;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mSource;
    private HashSet<String> mTags;
    @UnsupportedAppUsage
    private int mUsage;

    static {
        SUPPRESSIBLE_USAGES = new SparseIntArray();
        SUPPRESSIBLE_USAGES.put(5, 1);
        SUPPRESSIBLE_USAGES.put(6, 2);
        SUPPRESSIBLE_USAGES.put(7, 2);
        SUPPRESSIBLE_USAGES.put(8, 1);
        SUPPRESSIBLE_USAGES.put(9, 1);
        SUPPRESSIBLE_USAGES.put(10, 1);
        SUPPRESSIBLE_USAGES.put(11, 3);
        SUPPRESSIBLE_USAGES.put(2, 3);
        SUPPRESSIBLE_USAGES.put(3, 3);
        SUPPRESSIBLE_USAGES.put(4, 4);
        SUPPRESSIBLE_USAGES.put(1, 5);
        SUPPRESSIBLE_USAGES.put(12, 5);
        SUPPRESSIBLE_USAGES.put(14, 5);
        SUPPRESSIBLE_USAGES.put(16, 5);
        SUPPRESSIBLE_USAGES.put(0, 5);
        SUPPRESSIBLE_USAGES.put(13, 6);
        SDK_USAGES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16};
        CREATOR = new Parcelable.Creator<AudioAttributes>(){

            @Override
            public AudioAttributes createFromParcel(Parcel parcel) {
                return new AudioAttributes(parcel);
            }

            public AudioAttributes[] newArray(int n) {
                return new AudioAttributes[n];
            }
        };
    }

    private AudioAttributes() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
    }

    private AudioAttributes(Parcel parcel) {
        int n = 0;
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
        this.mUsage = parcel.readInt();
        this.mContentType = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mFlags = parcel.readInt();
        if ((parcel.readInt() & 1) == 1) {
            n = 1;
        }
        this.mTags = new HashSet();
        if (n != 0) {
            this.mFormattedTags = new String(parcel.readString());
            this.mTags.add(this.mFormattedTags);
        } else {
            String[] arrstring = parcel.readStringArray();
            for (n = arrstring.length - 1; n >= 0; --n) {
                this.mTags.add(arrstring[n]);
            }
            this.mFormattedTags = TextUtils.join((CharSequence)";", this.mTags);
        }
        n = parcel.readInt();
        if (n != -1977) {
            if (n != 1980) {
                Log.e(TAG, "Illegal value unmarshalling AudioAttributes, can't initialize bundle");
            } else {
                this.mBundle = new Bundle(parcel.readBundle());
            }
        } else {
            this.mBundle = null;
        }
    }

    static /* synthetic */ int access$400(AudioAttributes audioAttributes) {
        return audioAttributes.mSource;
    }

    static /* synthetic */ int access$500(AudioAttributes audioAttributes) {
        return audioAttributes.mFlags;
    }

    static /* synthetic */ int access$576(AudioAttributes audioAttributes, int n) {
        audioAttributes.mFlags = n = audioAttributes.mFlags | n;
        return n;
    }

    static /* synthetic */ Bundle access$700(AudioAttributes audioAttributes) {
        return audioAttributes.mBundle;
    }

    static /* synthetic */ int access$800(int n) {
        return AudioAttributes.usageForStreamType(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int capturePolicyToFlags(int n, int n2) {
        if (n == 1) return n2 & -5121;
        if (n == 2) return (n2 | 1024) & -4097;
        if (n != 3) throw new IllegalArgumentException("Unknown allow playback capture policy");
        return n2 | 5120;
    }

    @UnsupportedAppUsage
    public static int toLegacyStreamType(AudioAttributes audioAttributes) {
        return AudioAttributes.toVolumeStreamType(false, audioAttributes);
    }

    private static int toVolumeStreamType(boolean bl, AudioAttributes audioAttributes) {
        int n = audioAttributes.getFlags();
        int n2 = 1;
        if ((n & 1) == 1) {
            if (!bl) {
                n2 = 7;
            }
            return n2;
        }
        int n3 = audioAttributes.getAllFlags();
        n2 = 0;
        n = 0;
        if ((n3 & 4) == 4) {
            n2 = bl ? n : 6;
            return n2;
        }
        n3 = audioAttributes.getAllFlags();
        n = 3;
        if ((n3 & 8) == 8) {
            n2 = bl ? n : 9;
            return n2;
        }
        if (AudioProductStrategy.getAudioProductStrategies().size() > 0) {
            return AudioProductStrategy.getLegacyStreamTypeForStrategyWithAudioAttributes(audioAttributes);
        }
        switch (audioAttributes.getUsage()) {
            default: {
                if (bl) break;
                return 3;
            }
            case 13: {
                return 1;
            }
            case 11: {
                return 10;
            }
            case 6: {
                return 2;
            }
            case 5: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                if (!bl) {
                    n2 = 8;
                }
                return n2;
            }
            case 2: {
                return 0;
            }
            case 1: 
            case 12: 
            case 14: 
            case 16: {
                return 3;
            }
            case 0: {
                return 3;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown usage value ");
        stringBuilder.append(audioAttributes.getUsage());
        stringBuilder.append(" in audio attributes");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int usageForStreamType(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 10: {
                return 11;
            }
            case 8: {
                return 3;
            }
            case 6: {
                return 2;
            }
            case 5: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 3: {
                return 1;
            }
            case 2: {
                return 6;
            }
            case 1: 
            case 7: {
                return 13;
            }
            case 0: 
        }
        return 2;
    }

    public static String usageToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown usage ");
                stringBuilder.append(n);
                return new String(stringBuilder.toString());
            }
            case 16: {
                return new String("USAGE_ASSISTANT");
            }
            case 14: {
                return new String("USAGE_GAME");
            }
            case 13: {
                return new String("USAGE_ASSISTANCE_SONIFICATION");
            }
            case 12: {
                return new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
            }
            case 11: {
                return new String("USAGE_ASSISTANCE_ACCESSIBILITY");
            }
            case 10: {
                return new String("USAGE_NOTIFICATION_EVENT");
            }
            case 9: {
                return new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
            }
            case 8: {
                return new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
            }
            case 7: {
                return new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
            }
            case 6: {
                return new String("USAGE_NOTIFICATION_RINGTONE");
            }
            case 5: {
                return new String("USAGE_NOTIFICATION");
            }
            case 4: {
                return new String("USAGE_ALARM");
            }
            case 3: {
                return new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
            }
            case 2: {
                return new String("USAGE_VOICE_COMMUNICATION");
            }
            case 1: {
                return new String("USAGE_MEDIA");
            }
            case 0: 
        }
        return new String("USAGE_UNKNOWN");
    }

    public boolean areHapticChannelsMuted() {
        boolean bl = (this.mFlags & 2048) != 0;
        return bl;
    }

    public String contentTypeToString() {
        int n = this.mContentType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("unknown content type ");
                            stringBuilder.append(this.mContentType);
                            return new String(stringBuilder.toString());
                        }
                        return new String("CONTENT_TYPE_SONIFICATION");
                    }
                    return new String("CONTENT_TYPE_MOVIE");
                }
                return new String("CONTENT_TYPE_MUSIC");
            }
            return new String("CONTENT_TYPE_SPEECH");
        }
        return new String("CONTENT_TYPE_UNKNOWN");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AudioAttributes)object;
            if (this.mContentType != ((AudioAttributes)object).mContentType || this.mFlags != ((AudioAttributes)object).mFlags || this.mSource != ((AudioAttributes)object).mSource || this.mUsage != ((AudioAttributes)object).mUsage || !this.mFormattedTags.equals(((AudioAttributes)object).mFormattedTags)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @SystemApi
    public int getAllFlags() {
        return this.mFlags & 6143;
    }

    public int getAllowedCapturePolicy() {
        int n = this.mFlags;
        if ((n & 4096) == 4096) {
            return 3;
        }
        if ((n & 1024) == 1024) {
            return 2;
        }
        return 1;
    }

    @SystemApi
    public Bundle getBundle() {
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return bundle;
        }
        return new Bundle(bundle);
    }

    @SystemApi
    public int getCapturePreset() {
        return this.mSource;
    }

    public int getContentType() {
        return this.mContentType;
    }

    public int getFlags() {
        return this.mFlags & 273;
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(this.mTags);
    }

    public int getUsage() {
        return this.mUsage;
    }

    public int getVolumeControlStream() {
        return AudioAttributes.toVolumeStreamType(true, this);
    }

    public int hashCode() {
        return Objects.hash(this.mContentType, this.mFlags, this.mSource, this.mUsage, this.mFormattedTags, this.mBundle);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AudioAttributes: usage=");
        stringBuilder.append(this.usageToString());
        stringBuilder.append(" content=");
        stringBuilder.append(this.contentTypeToString());
        stringBuilder.append(" flags=0x");
        stringBuilder.append(Integer.toHexString(this.mFlags).toUpperCase());
        stringBuilder.append(" tags=");
        stringBuilder.append(this.mFormattedTags);
        stringBuilder.append(" bundle=");
        Object object = this.mBundle;
        object = object == null ? "null" : ((Bundle)object).toString();
        stringBuilder.append((String)object);
        return new String(stringBuilder.toString());
    }

    public String usageToString() {
        return AudioAttributes.usageToString(this.mUsage);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUsage);
        parcel.writeInt(this.mContentType);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(n & 1);
        if ((n & 1) == 0) {
            String[] arrstring = new String[this.mTags.size()];
            this.mTags.toArray(arrstring);
            parcel.writeStringArray(arrstring);
        } else if ((n & 1) == 1) {
            parcel.writeString(this.mFormattedTags);
        }
        if (this.mBundle == null) {
            parcel.writeInt(-1977);
        } else {
            parcel.writeInt(1980);
            parcel.writeBundle(this.mBundle);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1159641169921L, this.mUsage);
        protoOutputStream.write(1159641169922L, this.mContentType);
        protoOutputStream.write(1120986464259L, this.mFlags);
        String[] arrstring = this.mFormattedTags.split(";");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            String string2 = arrstring[i].trim();
            if (string2 == "") continue;
            protoOutputStream.write(2237677961220L, string2);
        }
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AttributeContentType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AttributeUsage {
    }

    public static class Builder {
        private Bundle mBundle;
        private int mContentType = 0;
        private int mFlags = 0;
        private boolean mMuteHapticChannels = true;
        private int mSource = -1;
        private HashSet<String> mTags = new HashSet();
        private int mUsage = 0;

        public Builder() {
        }

        public Builder(AudioAttributes audioAttributes) {
            this.mUsage = audioAttributes.mUsage;
            this.mContentType = audioAttributes.mContentType;
            this.mFlags = audioAttributes.getAllFlags();
            this.mTags = (HashSet)audioAttributes.mTags.clone();
            this.mMuteHapticChannels = audioAttributes.areHapticChannelsMuted();
        }

        @SystemApi
        public Builder addBundle(Bundle bundle) {
            if (bundle != null) {
                Bundle bundle2 = this.mBundle;
                if (bundle2 == null) {
                    this.mBundle = new Bundle(bundle);
                } else {
                    bundle2.putAll(bundle);
                }
                return this;
            }
            throw new IllegalArgumentException("Illegal null bundle");
        }

        @UnsupportedAppUsage
        public Builder addTag(String string2) {
            this.mTags.add(string2);
            return this;
        }

        public AudioAttributes build() {
            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.mContentType = this.mContentType;
            audioAttributes.mUsage = this.mUsage;
            audioAttributes.mSource = this.mSource;
            audioAttributes.mFlags = this.mFlags;
            if (this.mMuteHapticChannels) {
                AudioAttributes.access$576(audioAttributes, 2048);
            }
            audioAttributes.mTags = (HashSet)this.mTags.clone();
            audioAttributes.mFormattedTags = TextUtils.join((CharSequence)";", this.mTags);
            Bundle bundle = this.mBundle;
            if (bundle != null) {
                audioAttributes.mBundle = new Bundle(bundle);
            }
            return audioAttributes;
        }

        public Builder replaceFlags(int n) {
            this.mFlags = n & 6143;
            return this;
        }

        public Builder setAllowedCapturePolicy(int n) {
            this.mFlags = AudioAttributes.capturePolicyToFlags(n, this.mFlags);
            return this;
        }

        @SystemApi
        public Builder setCapturePreset(int n) {
            if (n != 0 && n != 1 && n != 5 && n != 6 && n != 7 && n != 9 && n != 10) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid capture preset ");
                stringBuilder.append(n);
                stringBuilder.append(" for AudioAttributes");
                Log.e(AudioAttributes.TAG, stringBuilder.toString());
            } else {
                this.mSource = n;
            }
            return this;
        }

        public Builder setContentType(int n) {
            this.mContentType = n != 0 && n != 1 && n != 2 && n != 3 && n != 4 ? 0 : n;
            return this;
        }

        public Builder setFlags(int n) {
            this.mFlags |= n & 6143;
            return this;
        }

        public Builder setHapticChannelsMuted(boolean bl) {
            this.mMuteHapticChannels = bl;
            return this;
        }

        @SystemApi
        public Builder setInternalCapturePreset(int n) {
            if (n != 1999 && n != 8 && n != 1998 && n != 3 && n != 2 && n != 4 && n != 1997) {
                this.setCapturePreset(n);
            } else {
                this.mSource = n;
            }
            return this;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @UnsupportedAppUsage
        public Builder setInternalLegacyStreamType(int var1_1) {
            block14 : {
                this.mContentType = 0;
                this.mUsage = 0;
                if (AudioProductStrategy.getAudioProductStrategies().size() > 0 && (var2_2 = AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(var1_1)) != null) {
                    this.mUsage = AudioAttributes.access$000((AudioAttributes)var2_2);
                    this.mContentType = AudioAttributes.access$100((AudioAttributes)var2_2);
                    this.mFlags = AudioAttributes.access$500((AudioAttributes)var2_2);
                    this.mMuteHapticChannels = var2_2.areHapticChannelsMuted();
                    this.mTags = AudioAttributes.access$200((AudioAttributes)var2_2);
                    this.mBundle = AudioAttributes.access$700((AudioAttributes)var2_2);
                    this.mSource = AudioAttributes.access$400((AudioAttributes)var2_2);
                }
                if (this.mContentType != 0) break block14;
                switch (var1_1) {
                    default: {
                        var2_2 = new StringBuilder();
                        var2_2.append("Invalid stream type ");
                        var2_2.append(var1_1);
                        var2_2.append(" for AudioAttributes");
                        Log.e("AudioAttributes", var2_2.toString());
                        ** break;
                    }
                    case 10: {
                        this.mContentType = 1;
                        ** break;
                    }
                    case 9: {
                        this.mContentType = 4;
                        this.mFlags |= 8;
                        ** break;
                    }
                    case 8: {
                        this.mContentType = 4;
                        ** break;
                    }
                    case 7: {
                        this.mFlags = 1 | this.mFlags;
                        ** GOTO lbl53
                    }
                    case 6: {
                        this.mContentType = 1;
                        this.mFlags |= 4;
                        ** break;
                    }
                    case 5: {
                        this.mContentType = 4;
                        ** break;
                    }
                    case 4: {
                        this.mContentType = 4;
                        ** break;
                    }
                    case 3: {
                        this.mContentType = 2;
                        ** break;
                    }
                    case 2: {
                        this.mContentType = 4;
                        ** break;
                    }
lbl53: // 2 sources:
                    case 1: {
                        this.mContentType = 4;
                        ** break;
                    }
                    case 0: 
                }
                this.mContentType = 1;
            }
            if (this.mUsage != 0) return this;
            this.mUsage = AudioAttributes.access$800(var1_1);
            return this;
        }

        public Builder setLegacyStreamType(int n) {
            if (n != 10) {
                this.setInternalLegacyStreamType(n);
                return this;
            }
            throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
        }

        public Builder setUsage(int n) {
            switch (n) {
                default: {
                    this.mUsage = 0;
                    break;
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: {
                    this.mUsage = n;
                }
            }
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CapturePolicy {
    }

}

