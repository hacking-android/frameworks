/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$MediaCodecInfo
 *  android.media.-$$Lambda$MediaCodecInfo$VideoCapabilities
 *  android.media.-$$Lambda$MediaCodecInfo$VideoCapabilities$DpgwEn-gVFZT9EtP3qcxpiA2G0M
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.-$;
import android.media.AudioSystem;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.Utils;
import android.media._$$Lambda$MediaCodecInfo$VideoCapabilities$DpgwEn_gVFZT9EtP3qcxpiA2G0M;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public final class MediaCodecInfo {
    private static final Range<Integer> BITRATE_RANGE;
    private static final int DEFAULT_MAX_SUPPORTED_INSTANCES = 32;
    private static final int ERROR_NONE_SUPPORTED = 4;
    private static final int ERROR_UNRECOGNIZED = 1;
    private static final int ERROR_UNSUPPORTED = 2;
    private static final int FLAG_IS_ENCODER = 1;
    private static final int FLAG_IS_HARDWARE_ACCELERATED = 8;
    private static final int FLAG_IS_SOFTWARE_ONLY = 4;
    private static final int FLAG_IS_VENDOR = 2;
    private static final Range<Integer> FRAME_RATE_RANGE;
    private static final int MAX_SUPPORTED_INSTANCES_LIMIT = 256;
    private static final Range<Integer> POSITIVE_INTEGERS;
    private static final Range<Long> POSITIVE_LONGS;
    private static final Range<Rational> POSITIVE_RATIONALS;
    private static final Range<Integer> SIZE_RANGE;
    private static final String TAG = "MediaCodecInfo";
    private String mCanonicalName;
    private Map<String, CodecCapabilities> mCaps;
    private int mFlags;
    private String mName;

    static {
        Integer n = 1;
        POSITIVE_INTEGERS = Range.create(n, Integer.MAX_VALUE);
        POSITIVE_LONGS = Range.create(1L, Long.MAX_VALUE);
        POSITIVE_RATIONALS = Range.create(new Rational(1, Integer.MAX_VALUE), new Rational(Integer.MAX_VALUE, 1));
        SIZE_RANGE = Range.create(n, 32768);
        n = 0;
        FRAME_RATE_RANGE = Range.create(n, 960);
        BITRATE_RANGE = Range.create(n, 500000000);
    }

    /*
     * WARNING - void declaration
     */
    MediaCodecInfo(String object2, String string2, int n, CodecCapabilities[] arrcodecCapabilities) {
        void var4_6;
        int n2;
        void var2_4;
        this.mName = object2;
        this.mCanonicalName = var2_4;
        this.mFlags = n2;
        this.mCaps = new HashMap<String, CodecCapabilities>();
        for (void var1_3 : var4_6) {
            this.mCaps.put(var1_3.getMimeType(), (CodecCapabilities)var1_3);
        }
    }

    private static int checkPowerOfTwo(int n, String string2) {
        if ((n - 1 & n) == 0) {
            return n;
        }
        throw new IllegalArgumentException(string2);
    }

    public final String getCanonicalName() {
        return this.mCanonicalName;
    }

    public final CodecCapabilities getCapabilitiesForType(String object) {
        if ((object = this.mCaps.get(object)) != null) {
            return ((CodecCapabilities)object).dup();
        }
        throw new IllegalArgumentException("codec does not support type");
    }

    public final String getName() {
        return this.mName;
    }

    public final String[] getSupportedTypes() {
        Object[] arrobject = this.mCaps.keySet();
        arrobject = arrobject.toArray(new String[arrobject.size()]);
        Arrays.sort(arrobject);
        return arrobject;
    }

    public final boolean isAlias() {
        return this.mName.equals(this.mCanonicalName) ^ true;
    }

    public final boolean isEncoder() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public final boolean isHardwareAccelerated() {
        boolean bl = (this.mFlags & 8) != 0;
        return bl;
    }

    public final boolean isSoftwareOnly() {
        boolean bl = (this.mFlags & 4) != 0;
        return bl;
    }

    public final boolean isVendor() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    public MediaCodecInfo makeRegular() {
        ArrayList<CodecCapabilities> arrayList = new ArrayList<CodecCapabilities>();
        for (CodecCapabilities codecCapabilities : this.mCaps.values()) {
            if (!codecCapabilities.isRegular()) continue;
            arrayList.add(codecCapabilities);
        }
        if (arrayList.size() == 0) {
            return null;
        }
        if (arrayList.size() == this.mCaps.size()) {
            return this;
        }
        return new MediaCodecInfo(this.mName, this.mCanonicalName, this.mFlags, arrayList.toArray(new CodecCapabilities[arrayList.size()]));
    }

    public static final class AudioCapabilities {
        private static final int MAX_INPUT_CHANNEL_COUNT = 30;
        private static final String TAG = "AudioCapabilities";
        private Range<Integer> mBitrateRange;
        private int mMaxInputChannelCount;
        private CodecCapabilities mParent;
        private Range<Integer>[] mSampleRateRanges;
        private int[] mSampleRates;

        private AudioCapabilities() {
        }

        private void applyLevelLimits() {
            int[] arrn = null;
            Range<Integer> range = null;
            Range<Integer> range2 = null;
            int n = 30;
            String string2 = this.mParent.getMimeType();
            boolean bl = string2.equalsIgnoreCase("audio/mpeg");
            Object object = 8000;
            Integer n2 = 1;
            if (bl) {
                arrn = new int[]{8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000};
                range2 = Range.create(object, 320000);
                n = 2;
            } else if (string2.equalsIgnoreCase("audio/3gpp")) {
                arrn = new int[]{8000};
                range2 = Range.create(4750, 12200);
                n = 1;
            } else if (string2.equalsIgnoreCase("audio/amr-wb")) {
                arrn = new int[]{16000};
                range2 = Range.create(6600, 23850);
                n = 1;
            } else if (string2.equalsIgnoreCase("audio/mp4a-latm")) {
                arrn = new int[]{7350, 8000, 11025, 12000, 16000, 22050, 24000, 32000, 44100, 48000, 64000, 88200, 96000};
                range2 = Range.create(object, 510000);
                n = 48;
            } else if (string2.equalsIgnoreCase("audio/vorbis")) {
                range2 = Range.create(32000, 500000);
                range = Range.create(object, 192000);
                n = 255;
            } else if (string2.equalsIgnoreCase("audio/opus")) {
                range2 = Range.create(6000, 510000);
                arrn = new int[]{8000, 12000, 16000, 24000, 48000};
                n = 255;
            } else if (string2.equalsIgnoreCase("audio/raw")) {
                range = Range.create(n2, 96000);
                range2 = Range.create(n2, 10000000);
                n = AudioSystem.OUT_CHANNEL_COUNT_MAX;
            } else if (string2.equalsIgnoreCase("audio/flac")) {
                range = Range.create(n2, 655350);
                n = 255;
            } else if (!string2.equalsIgnoreCase("audio/g711-alaw") && !string2.equalsIgnoreCase("audio/g711-mlaw")) {
                if (string2.equalsIgnoreCase("audio/gsm")) {
                    arrn = new int[]{8000};
                    range2 = Range.create(13000, 13000);
                    n = 1;
                } else if (string2.equalsIgnoreCase("audio/ac3")) {
                    n = 6;
                } else if (string2.equalsIgnoreCase("audio/eac3")) {
                    n = 16;
                } else if (string2.equalsIgnoreCase("audio/eac3-joc")) {
                    arrn = new int[]{48000};
                    range2 = Range.create(32000, 6144000);
                    n = 16;
                } else if (string2.equalsIgnoreCase("audio/ac4")) {
                    arrn = new int[]{44100, 48000, 96000, 192000};
                    range2 = Range.create(16000, 2688000);
                    n = 24;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported mime ");
                    ((StringBuilder)object).append(string2);
                    Log.w(TAG, ((StringBuilder)object).toString());
                    object = this.mParent;
                    ((CodecCapabilities)object).mError |= 2;
                }
            } else {
                arrn = new int[]{8000};
                range2 = Range.create(64000, 64000);
            }
            if (arrn != null) {
                this.limitSampleRates(arrn);
            } else if (range != null) {
                this.limitSampleRates(new Range[]{range});
            }
            this.applyLimits(n, range2);
        }

        private void applyLimits(int n, Range<Integer> range) {
            this.mMaxInputChannelCount = Range.create(1, this.mMaxInputChannelCount).clamp(n);
            if (range != null) {
                this.mBitrateRange = this.mBitrateRange.intersect(range);
            }
        }

        public static AudioCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            AudioCapabilities audioCapabilities = new AudioCapabilities();
            audioCapabilities.init(mediaFormat, codecCapabilities);
            return audioCapabilities;
        }

        private void createDiscreteSampleRates() {
            Range<Integer>[] arrrange;
            this.mSampleRates = new int[this.mSampleRateRanges.length];
            for (int i = 0; i < (arrrange = this.mSampleRateRanges).length; ++i) {
                this.mSampleRates[i] = arrrange[i].getLower();
            }
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            this.initWithPlatformLimits();
            this.applyLevelLimits();
            this.parseFromInfo(mediaFormat);
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = Range.create(0, Integer.MAX_VALUE);
            this.mMaxInputChannelCount = 30;
            int n = SystemProperties.getInt("ro.mediacodec.min_sample_rate", 7350);
            this.mSampleRateRanges = new Range[]{Range.create(n, SystemProperties.getInt("ro.mediacodec.max_sample_rate", 192000))};
            this.mSampleRates = null;
        }

        private void limitSampleRates(int[] arrn) {
            Arrays.sort(arrn);
            ArrayList<Range<Integer>> arrayList = new ArrayList<Range<Integer>>();
            for (int n : arrn) {
                if (!this.supports(n, null)) continue;
                arrayList.add(Range.create(n, n));
            }
            this.mSampleRateRanges = arrayList.toArray(new Range[arrayList.size()]);
            this.createDiscreteSampleRates();
        }

        private void limitSampleRates(Range<Integer>[] object2) {
            Utils.sortDistinctRanges(object2);
            for (Range<Integer> range : this.mSampleRateRanges = Utils.intersectSortedDistinctRanges(this.mSampleRateRanges, object2)) {
                if (range.getLower().equals(range.getUpper())) continue;
                this.mSampleRates = null;
                return;
            }
            this.createDiscreteSampleRates();
        }

        private void parseFromInfo(MediaFormat mediaFormat) {
            int n;
            Object object;
            int n2 = 30;
            Range range = POSITIVE_INTEGERS;
            if (mediaFormat.containsKey("sample-rate-ranges")) {
                String[] arrstring = mediaFormat.getString("sample-rate-ranges").split(",");
                object = new Range[arrstring.length];
                for (n = 0; n < arrstring.length; ++n) {
                    object[n] = Utils.parseIntRange(arrstring[n], null);
                }
                this.limitSampleRates((Range<Integer>[])object);
            }
            if (mediaFormat.containsKey("max-channel-count")) {
                n = Utils.parseIntSafely(mediaFormat.getString("max-channel-count"), 30);
            } else {
                n = n2;
                if ((this.mParent.mError & 2) != 0) {
                    n = 0;
                }
            }
            object = range;
            if (mediaFormat.containsKey("bitrate-range")) {
                object = range.intersect(Utils.parseIntRange(mediaFormat.getString("bitrate-range"), range));
            }
            this.applyLimits(n, (Range<Integer>)object);
        }

        private boolean supports(Integer n, Integer n2) {
            if (n2 != null && (n2 < 1 || n2 > this.mMaxInputChannelCount)) {
                return false;
            }
            return n == null || Utils.binarySearchDistinctRanges(this.mSampleRateRanges, n) >= 0;
        }

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public void getDefaultFormat(MediaFormat mediaFormat) {
            int[] arrn;
            if (this.mBitrateRange.getLower().equals(this.mBitrateRange.getUpper())) {
                mediaFormat.setInteger("bitrate", this.mBitrateRange.getLower());
            }
            if (this.mMaxInputChannelCount == 1) {
                mediaFormat.setInteger("channel-count", 1);
            }
            if ((arrn = this.mSampleRates) != null && arrn.length == 1) {
                mediaFormat.setInteger("sample-rate", arrn[0]);
            }
        }

        public int getMaxInputChannelCount() {
            return this.mMaxInputChannelCount;
        }

        public Range<Integer>[] getSupportedSampleRateRanges() {
            Range<Integer>[] arrrange = this.mSampleRateRanges;
            return Arrays.copyOf(arrrange, arrrange.length);
        }

        public int[] getSupportedSampleRates() {
            Object object = this.mSampleRates;
            object = object != null ? Arrays.copyOf(object, ((int[])object).length) : null;
            return object;
        }

        public boolean isSampleRateSupported(int n) {
            return this.supports(n, null);
        }

        public boolean supportsFormat(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            if (!this.supports((Integer)map.get("sample-rate"), (Integer)map.get("channel-count"))) {
                return false;
            }
            return CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }
    }

    public static final class CodecCapabilities {
        public static final int COLOR_Format12bitRGB444 = 3;
        public static final int COLOR_Format16bitARGB1555 = 5;
        public static final int COLOR_Format16bitARGB4444 = 4;
        public static final int COLOR_Format16bitBGR565 = 7;
        public static final int COLOR_Format16bitRGB565 = 6;
        public static final int COLOR_Format18BitBGR666 = 41;
        public static final int COLOR_Format18bitARGB1665 = 9;
        public static final int COLOR_Format18bitRGB666 = 8;
        public static final int COLOR_Format19bitARGB1666 = 10;
        public static final int COLOR_Format24BitABGR6666 = 43;
        public static final int COLOR_Format24BitARGB6666 = 42;
        public static final int COLOR_Format24bitARGB1887 = 13;
        public static final int COLOR_Format24bitBGR888 = 12;
        public static final int COLOR_Format24bitRGB888 = 11;
        public static final int COLOR_Format25bitARGB1888 = 14;
        public static final int COLOR_Format32bitABGR8888 = 2130747392;
        public static final int COLOR_Format32bitARGB8888 = 16;
        public static final int COLOR_Format32bitBGRA8888 = 15;
        public static final int COLOR_Format8bitRGB332 = 2;
        public static final int COLOR_FormatCbYCrY = 27;
        public static final int COLOR_FormatCrYCbY = 28;
        public static final int COLOR_FormatL16 = 36;
        public static final int COLOR_FormatL2 = 33;
        public static final int COLOR_FormatL24 = 37;
        public static final int COLOR_FormatL32 = 38;
        public static final int COLOR_FormatL4 = 34;
        public static final int COLOR_FormatL8 = 35;
        public static final int COLOR_FormatMonochrome = 1;
        public static final int COLOR_FormatRGBAFlexible = 2134288520;
        public static final int COLOR_FormatRGBFlexible = 2134292616;
        public static final int COLOR_FormatRawBayer10bit = 31;
        public static final int COLOR_FormatRawBayer8bit = 30;
        public static final int COLOR_FormatRawBayer8bitcompressed = 32;
        public static final int COLOR_FormatSurface = 2130708361;
        public static final int COLOR_FormatYCbYCr = 25;
        public static final int COLOR_FormatYCrYCb = 26;
        public static final int COLOR_FormatYUV411PackedPlanar = 18;
        public static final int COLOR_FormatYUV411Planar = 17;
        public static final int COLOR_FormatYUV420Flexible = 2135033992;
        public static final int COLOR_FormatYUV420PackedPlanar = 20;
        public static final int COLOR_FormatYUV420PackedSemiPlanar = 39;
        public static final int COLOR_FormatYUV420Planar = 19;
        public static final int COLOR_FormatYUV420SemiPlanar = 21;
        public static final int COLOR_FormatYUV422Flexible = 2135042184;
        public static final int COLOR_FormatYUV422PackedPlanar = 23;
        public static final int COLOR_FormatYUV422PackedSemiPlanar = 40;
        public static final int COLOR_FormatYUV422Planar = 22;
        public static final int COLOR_FormatYUV422SemiPlanar = 24;
        public static final int COLOR_FormatYUV444Flexible = 2135181448;
        public static final int COLOR_FormatYUV444Interleaved = 29;
        public static final int COLOR_QCOM_FormatYUV420SemiPlanar = 2141391872;
        public static final int COLOR_TI_FormatYUV420PackedSemiPlanar = 2130706688;
        public static final String FEATURE_AdaptivePlayback = "adaptive-playback";
        public static final String FEATURE_DynamicTimestamp = "dynamic-timestamp";
        public static final String FEATURE_FrameParsing = "frame-parsing";
        public static final String FEATURE_IntraRefresh = "intra-refresh";
        public static final String FEATURE_MultipleFrames = "multiple-frames";
        public static final String FEATURE_PartialFrame = "partial-frame";
        public static final String FEATURE_SecurePlayback = "secure-playback";
        public static final String FEATURE_TunneledPlayback = "tunneled-playback";
        private static final String TAG = "CodecCapabilities";
        private static final Feature[] decoderFeatures = new Feature[]{new Feature("adaptive-playback", 1, true), new Feature("secure-playback", 2, false), new Feature("tunneled-playback", 4, false), new Feature("partial-frame", 8, false), new Feature("frame-parsing", 16, false), new Feature("multiple-frames", 32, false), new Feature("dynamic-timestamp", 64, false)};
        private static final Feature[] encoderFeatures = new Feature[]{new Feature("intra-refresh", 1, false), new Feature("multiple-frames", 2, false), new Feature("dynamic-timestamp", 4, false)};
        public int[] colorFormats;
        private AudioCapabilities mAudioCaps;
        private MediaFormat mCapabilitiesInfo;
        private MediaFormat mDefaultFormat;
        private EncoderCapabilities mEncoderCaps;
        int mError;
        private int mFlagsRequired;
        private int mFlagsSupported;
        private int mFlagsVerified;
        private int mMaxSupportedInstances;
        private String mMime;
        private VideoCapabilities mVideoCaps;
        public CodecProfileLevel[] profileLevels;

        public CodecCapabilities() {
        }

        CodecCapabilities(CodecProfileLevel[] object, int[] object2, boolean bl, MediaFormat object3, MediaFormat object4) {
            Map<String, Object> map = ((MediaFormat)object4).getMap();
            this.colorFormats = object2;
            int n = 0;
            this.mFlagsVerified = 0;
            this.mDefaultFormat = object3;
            this.mCapabilitiesInfo = object4;
            this.mMime = this.mDefaultFormat.getString("mime");
            int n2 = ((CodecProfileLevel[])object).length;
            boolean bl2 = true;
            object2 = object;
            if (n2 == 0) {
                object2 = object;
                if (this.mMime.equalsIgnoreCase("video/x-vnd.on2.vp9")) {
                    object = new CodecProfileLevel();
                    ((CodecProfileLevel)object).profile = 1;
                    ((CodecProfileLevel)object).level = VideoCapabilities.equivalentVP9Level((MediaFormat)object4);
                    object2 = new CodecProfileLevel[]{object};
                }
            }
            this.profileLevels = object2;
            if (this.mMime.toLowerCase().startsWith("audio/")) {
                this.mAudioCaps = AudioCapabilities.create((MediaFormat)object4, this);
                this.mAudioCaps.getDefaultFormat(this.mDefaultFormat);
            } else if (this.mMime.toLowerCase().startsWith("video/") || this.mMime.equalsIgnoreCase("image/vnd.android.heic")) {
                this.mVideoCaps = VideoCapabilities.create((MediaFormat)object4, this);
            }
            if (bl) {
                this.mEncoderCaps = EncoderCapabilities.create((MediaFormat)object4, this);
                this.mEncoderCaps.getDefaultFormat(this.mDefaultFormat);
            }
            object = MediaCodecList.getGlobalSettings();
            this.mMaxSupportedInstances = Utils.parseIntSafely(object.get("max-concurrent-instances"), 32);
            n2 = Utils.parseIntSafely(map.get("max-concurrent-instances"), this.mMaxSupportedInstances);
            this.mMaxSupportedInstances = Range.create(1, 256).clamp(n2);
            object = this.getValidFeatures();
            n2 = ((Object)object).length;
            bl = bl2;
            while (n < n2) {
                object2 = object[n];
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("feature-");
                ((StringBuilder)object3).append(((Feature)object2).mName);
                object3 = ((StringBuilder)object3).toString();
                object4 = (Integer)map.get(object3);
                if (object4 != null) {
                    if ((Integer)object4 > 0) {
                        int n3 = this.mFlagsRequired;
                        this.mFlagsRequired = ((Feature)object2).mValue | n3;
                    }
                    this.mFlagsSupported |= ((Feature)object2).mValue;
                    object2 = this.mDefaultFormat;
                    bl = true;
                    ((MediaFormat)object2).setInteger((String)object3, 1);
                }
                ++n;
            }
        }

        CodecCapabilities(CodecProfileLevel[] arrcodecProfileLevel, int[] arrn, boolean bl, Map<String, Object> map, Map<String, Object> map2) {
            this(arrcodecProfileLevel, arrn, bl, new MediaFormat(map), new MediaFormat(map2));
        }

        private boolean checkFeature(String string2, int n) {
            Feature[] arrfeature = this.getValidFeatures();
            int n2 = arrfeature.length;
            boolean bl = false;
            for (int i = 0; i < n2; ++i) {
                Feature feature = arrfeature[i];
                if (!feature.mName.equals(string2)) continue;
                if ((feature.mValue & n) != 0) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }

        public static CodecCapabilities createFromProfileLevel(String object, int n, int n2) {
            CodecProfileLevel codecProfileLevel = new CodecProfileLevel();
            codecProfileLevel.profile = n;
            codecProfileLevel.level = n2;
            MediaFormat mediaFormat = new MediaFormat();
            mediaFormat.setString("mime", (String)object);
            object = new MediaFormat();
            object = new CodecCapabilities(new CodecProfileLevel[]{codecProfileLevel}, new int[0], true, mediaFormat, (MediaFormat)object);
            if (((CodecCapabilities)object).mError != 0) {
                return null;
            }
            return object;
        }

        private Feature[] getValidFeatures() {
            if (!this.isEncoder()) {
                return decoderFeatures;
            }
            return encoderFeatures;
        }

        private boolean isAudio() {
            boolean bl = this.mAudioCaps != null;
            return bl;
        }

        private boolean isEncoder() {
            boolean bl = this.mEncoderCaps != null;
            return bl;
        }

        private boolean isVideo() {
            boolean bl = this.mVideoCaps != null;
            return bl;
        }

        private static boolean supportsBitrate(Range<Integer> range, MediaFormat object) {
            object = ((MediaFormat)object).getMap();
            Integer n = (Integer)object.get("max-bitrate");
            Integer n2 = (Integer)object.get("bitrate");
            if (n2 == null) {
                object = n;
            } else {
                object = n2;
                if (n != null) {
                    object = Math.max(n2, n);
                }
            }
            if (object != null && (Integer)object > 0) {
                return range.contains((Integer)object);
            }
            return true;
        }

        private boolean supportsProfileLevel(int n, Integer n2) {
            CodecProfileLevel[] arrcodecProfileLevel = this.profileLevels;
            int n3 = arrcodecProfileLevel.length;
            boolean bl = false;
            for (int i = 0; i < n3; ++i) {
                CodecProfileLevel codecProfileLevel = arrcodecProfileLevel[i];
                if (codecProfileLevel.profile != n) continue;
                if (n2 != null && !this.mMime.equalsIgnoreCase("audio/mp4a-latm")) {
                    if (this.mMime.equalsIgnoreCase("video/3gpp") && codecProfileLevel.level != n2 && codecProfileLevel.level == 16 && n2 > 1 || this.mMime.equalsIgnoreCase("video/mp4v-es") && codecProfileLevel.level != n2 && codecProfileLevel.level == 4 && n2 > 1) continue;
                    if (this.mMime.equalsIgnoreCase("video/hevc")) {
                        boolean bl2 = (codecProfileLevel.level & 44739242) != 0;
                        boolean bl3 = (44739242 & n2) != 0;
                        if (bl3 && !bl2) continue;
                    }
                    if (codecProfileLevel.level < n2) continue;
                    if (CodecCapabilities.createFromProfileLevel(this.mMime, n, codecProfileLevel.level) != null) {
                        if (CodecCapabilities.createFromProfileLevel(this.mMime, n, n2) != null) {
                            bl = true;
                        }
                        return bl;
                    }
                    return true;
                }
                return true;
            }
            return false;
        }

        public CodecCapabilities dup() {
            CodecCapabilities codecCapabilities = new CodecCapabilities();
            Object[] arrobject = this.profileLevels;
            codecCapabilities.profileLevels = Arrays.copyOf(arrobject, arrobject.length);
            arrobject = this.colorFormats;
            codecCapabilities.colorFormats = Arrays.copyOf((int[])arrobject, arrobject.length);
            codecCapabilities.mMime = this.mMime;
            codecCapabilities.mMaxSupportedInstances = this.mMaxSupportedInstances;
            codecCapabilities.mFlagsRequired = this.mFlagsRequired;
            codecCapabilities.mFlagsSupported = this.mFlagsSupported;
            codecCapabilities.mFlagsVerified = this.mFlagsVerified;
            codecCapabilities.mAudioCaps = this.mAudioCaps;
            codecCapabilities.mVideoCaps = this.mVideoCaps;
            codecCapabilities.mEncoderCaps = this.mEncoderCaps;
            codecCapabilities.mDefaultFormat = this.mDefaultFormat;
            codecCapabilities.mCapabilitiesInfo = this.mCapabilitiesInfo;
            return codecCapabilities;
        }

        public AudioCapabilities getAudioCapabilities() {
            return this.mAudioCaps;
        }

        public MediaFormat getDefaultFormat() {
            return this.mDefaultFormat;
        }

        public EncoderCapabilities getEncoderCapabilities() {
            return this.mEncoderCaps;
        }

        public int getMaxSupportedInstances() {
            return this.mMaxSupportedInstances;
        }

        public String getMimeType() {
            return this.mMime;
        }

        public VideoCapabilities getVideoCapabilities() {
            return this.mVideoCaps;
        }

        public final boolean isFeatureRequired(String string2) {
            return this.checkFeature(string2, this.mFlagsRequired);
        }

        public final boolean isFeatureSupported(String string2) {
            return this.checkFeature(string2, this.mFlagsSupported);
        }

        public final boolean isFormatSupported(MediaFormat mediaFormat) {
            Object object;
            Object object3 = mediaFormat.getMap();
            Object object4 = (Feature[])object3.get("mime");
            if (object4 != null && !this.mMime.equalsIgnoreCase((String)object4)) {
                return false;
            }
            for (Feature object22 : this.getValidFeatures()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("feature-");
                ((StringBuilder)object).append(object22.mName);
                object = (Integer)object3.get(((StringBuilder)object).toString());
                if (object == null || ((Integer)object != 1 || this.isFeatureSupported(object22.mName)) && ((Integer)object != 0 || !this.isFeatureRequired(object22.mName))) continue;
                return false;
            }
            object4 = (Integer)object3.get("profile");
            Integer n = (Integer)object3.get("level");
            if (object4 != null) {
                if (!this.supportsProfileLevel((Integer)object4, n)) {
                    return false;
                }
                object = this.profileLevels;
                int n2 = ((CodecProfileLevel[])object).length;
                int n22 = 0;
                for (int i = 0; i < n2; ++i) {
                    Object object2 = object[i];
                    int n3 = n22;
                    if (((CodecProfileLevel)object2).profile == (Integer)object4) {
                        n3 = n22;
                        if (((CodecProfileLevel)object2).level > n22) {
                            n3 = ((CodecProfileLevel)object2).level;
                        }
                    }
                    n22 = n3;
                }
                object4 = CodecCapabilities.createFromProfileLevel(this.mMime, (Integer)object4, n22);
                object3 = new HashMap<String, Object>((Map<String, Object>)object3);
                object3.remove("profile");
                object3 = new MediaFormat((Map<String, Object>)object3);
                if (object4 != null && !((CodecCapabilities)object4).isFormatSupported((MediaFormat)object3)) {
                    return false;
                }
            }
            if ((object3 = this.mAudioCaps) != null && !((AudioCapabilities)object3).supportsFormat(mediaFormat)) {
                return false;
            }
            object3 = this.mVideoCaps;
            if (object3 != null && !((VideoCapabilities)object3).supportsFormat(mediaFormat)) {
                return false;
            }
            object3 = this.mEncoderCaps;
            return object3 == null || ((EncoderCapabilities)object3).supportsFormat(mediaFormat);
        }

        public boolean isRegular() {
            for (Feature feature : this.getValidFeatures()) {
                if (feature.mDefault || !this.isFeatureRequired(feature.mName)) continue;
                return false;
            }
            return true;
        }

        public String[] validFeatures() {
            Feature[] arrfeature = this.getValidFeatures();
            String[] arrstring = new String[arrfeature.length];
            for (int i = 0; i < arrstring.length; ++i) {
                arrstring[i] = arrfeature[i].mName;
            }
            return arrstring;
        }
    }

    public static final class CodecProfileLevel {
        public static final int AACObjectELD = 39;
        public static final int AACObjectERLC = 17;
        public static final int AACObjectERScalable = 20;
        public static final int AACObjectHE = 5;
        public static final int AACObjectHE_PS = 29;
        public static final int AACObjectLC = 2;
        public static final int AACObjectLD = 23;
        public static final int AACObjectLTP = 4;
        public static final int AACObjectMain = 1;
        public static final int AACObjectSSR = 3;
        public static final int AACObjectScalable = 6;
        public static final int AACObjectXHE = 42;
        public static final int AV1Level2 = 1;
        public static final int AV1Level21 = 2;
        public static final int AV1Level22 = 4;
        public static final int AV1Level23 = 8;
        public static final int AV1Level3 = 16;
        public static final int AV1Level31 = 32;
        public static final int AV1Level32 = 64;
        public static final int AV1Level33 = 128;
        public static final int AV1Level4 = 256;
        public static final int AV1Level41 = 512;
        public static final int AV1Level42 = 1024;
        public static final int AV1Level43 = 2048;
        public static final int AV1Level5 = 4096;
        public static final int AV1Level51 = 8192;
        public static final int AV1Level52 = 16384;
        public static final int AV1Level53 = 32768;
        public static final int AV1Level6 = 65536;
        public static final int AV1Level61 = 131072;
        public static final int AV1Level62 = 262144;
        public static final int AV1Level63 = 524288;
        public static final int AV1Level7 = 1048576;
        public static final int AV1Level71 = 2097152;
        public static final int AV1Level72 = 4194304;
        public static final int AV1Level73 = 8388608;
        public static final int AV1ProfileMain10 = 2;
        public static final int AV1ProfileMain10HDR10 = 4096;
        public static final int AV1ProfileMain10HDR10Plus = 8192;
        public static final int AV1ProfileMain8 = 1;
        public static final int AVCLevel1 = 1;
        public static final int AVCLevel11 = 4;
        public static final int AVCLevel12 = 8;
        public static final int AVCLevel13 = 16;
        public static final int AVCLevel1b = 2;
        public static final int AVCLevel2 = 32;
        public static final int AVCLevel21 = 64;
        public static final int AVCLevel22 = 128;
        public static final int AVCLevel3 = 256;
        public static final int AVCLevel31 = 512;
        public static final int AVCLevel32 = 1024;
        public static final int AVCLevel4 = 2048;
        public static final int AVCLevel41 = 4096;
        public static final int AVCLevel42 = 8192;
        public static final int AVCLevel5 = 16384;
        public static final int AVCLevel51 = 32768;
        public static final int AVCLevel52 = 65536;
        public static final int AVCLevel6 = 131072;
        public static final int AVCLevel61 = 262144;
        public static final int AVCLevel62 = 524288;
        public static final int AVCProfileBaseline = 1;
        public static final int AVCProfileConstrainedBaseline = 65536;
        public static final int AVCProfileConstrainedHigh = 524288;
        public static final int AVCProfileExtended = 4;
        public static final int AVCProfileHigh = 8;
        public static final int AVCProfileHigh10 = 16;
        public static final int AVCProfileHigh422 = 32;
        public static final int AVCProfileHigh444 = 64;
        public static final int AVCProfileMain = 2;
        public static final int DolbyVisionLevelFhd24 = 4;
        public static final int DolbyVisionLevelFhd30 = 8;
        public static final int DolbyVisionLevelFhd60 = 16;
        public static final int DolbyVisionLevelHd24 = 1;
        public static final int DolbyVisionLevelHd30 = 2;
        public static final int DolbyVisionLevelUhd24 = 32;
        public static final int DolbyVisionLevelUhd30 = 64;
        public static final int DolbyVisionLevelUhd48 = 128;
        public static final int DolbyVisionLevelUhd60 = 256;
        public static final int DolbyVisionProfileDvavPen = 2;
        public static final int DolbyVisionProfileDvavPer = 1;
        public static final int DolbyVisionProfileDvavSe = 512;
        public static final int DolbyVisionProfileDvheDen = 8;
        public static final int DolbyVisionProfileDvheDer = 4;
        public static final int DolbyVisionProfileDvheDtb = 128;
        public static final int DolbyVisionProfileDvheDth = 64;
        public static final int DolbyVisionProfileDvheDtr = 16;
        public static final int DolbyVisionProfileDvheSt = 256;
        public static final int DolbyVisionProfileDvheStn = 32;
        public static final int H263Level10 = 1;
        public static final int H263Level20 = 2;
        public static final int H263Level30 = 4;
        public static final int H263Level40 = 8;
        public static final int H263Level45 = 16;
        public static final int H263Level50 = 32;
        public static final int H263Level60 = 64;
        public static final int H263Level70 = 128;
        public static final int H263ProfileBackwardCompatible = 4;
        public static final int H263ProfileBaseline = 1;
        public static final int H263ProfileH320Coding = 2;
        public static final int H263ProfileHighCompression = 32;
        public static final int H263ProfileHighLatency = 256;
        public static final int H263ProfileISWV2 = 8;
        public static final int H263ProfileISWV3 = 16;
        public static final int H263ProfileInterlace = 128;
        public static final int H263ProfileInternet = 64;
        public static final int HEVCHighTierLevel1 = 2;
        public static final int HEVCHighTierLevel2 = 8;
        public static final int HEVCHighTierLevel21 = 32;
        public static final int HEVCHighTierLevel3 = 128;
        public static final int HEVCHighTierLevel31 = 512;
        public static final int HEVCHighTierLevel4 = 2048;
        public static final int HEVCHighTierLevel41 = 8192;
        public static final int HEVCHighTierLevel5 = 32768;
        public static final int HEVCHighTierLevel51 = 131072;
        public static final int HEVCHighTierLevel52 = 524288;
        public static final int HEVCHighTierLevel6 = 2097152;
        public static final int HEVCHighTierLevel61 = 8388608;
        public static final int HEVCHighTierLevel62 = 33554432;
        private static final int HEVCHighTierLevels = 44739242;
        public static final int HEVCMainTierLevel1 = 1;
        public static final int HEVCMainTierLevel2 = 4;
        public static final int HEVCMainTierLevel21 = 16;
        public static final int HEVCMainTierLevel3 = 64;
        public static final int HEVCMainTierLevel31 = 256;
        public static final int HEVCMainTierLevel4 = 1024;
        public static final int HEVCMainTierLevel41 = 4096;
        public static final int HEVCMainTierLevel5 = 16384;
        public static final int HEVCMainTierLevel51 = 65536;
        public static final int HEVCMainTierLevel52 = 262144;
        public static final int HEVCMainTierLevel6 = 1048576;
        public static final int HEVCMainTierLevel61 = 4194304;
        public static final int HEVCMainTierLevel62 = 16777216;
        public static final int HEVCProfileMain = 1;
        public static final int HEVCProfileMain10 = 2;
        public static final int HEVCProfileMain10HDR10 = 4096;
        public static final int HEVCProfileMain10HDR10Plus = 8192;
        public static final int HEVCProfileMainStill = 4;
        public static final int MPEG2LevelH14 = 2;
        public static final int MPEG2LevelHL = 3;
        public static final int MPEG2LevelHP = 4;
        public static final int MPEG2LevelLL = 0;
        public static final int MPEG2LevelML = 1;
        public static final int MPEG2Profile422 = 2;
        public static final int MPEG2ProfileHigh = 5;
        public static final int MPEG2ProfileMain = 1;
        public static final int MPEG2ProfileSNR = 3;
        public static final int MPEG2ProfileSimple = 0;
        public static final int MPEG2ProfileSpatial = 4;
        public static final int MPEG4Level0 = 1;
        public static final int MPEG4Level0b = 2;
        public static final int MPEG4Level1 = 4;
        public static final int MPEG4Level2 = 8;
        public static final int MPEG4Level3 = 16;
        public static final int MPEG4Level3b = 24;
        public static final int MPEG4Level4 = 32;
        public static final int MPEG4Level4a = 64;
        public static final int MPEG4Level5 = 128;
        public static final int MPEG4Level6 = 256;
        public static final int MPEG4ProfileAdvancedCoding = 4096;
        public static final int MPEG4ProfileAdvancedCore = 8192;
        public static final int MPEG4ProfileAdvancedRealTime = 1024;
        public static final int MPEG4ProfileAdvancedScalable = 16384;
        public static final int MPEG4ProfileAdvancedSimple = 32768;
        public static final int MPEG4ProfileBasicAnimated = 256;
        public static final int MPEG4ProfileCore = 4;
        public static final int MPEG4ProfileCoreScalable = 2048;
        public static final int MPEG4ProfileHybrid = 512;
        public static final int MPEG4ProfileMain = 8;
        public static final int MPEG4ProfileNbit = 16;
        public static final int MPEG4ProfileScalableTexture = 32;
        public static final int MPEG4ProfileSimple = 1;
        public static final int MPEG4ProfileSimpleFBA = 128;
        public static final int MPEG4ProfileSimpleFace = 64;
        public static final int MPEG4ProfileSimpleScalable = 2;
        public static final int VP8Level_Version0 = 1;
        public static final int VP8Level_Version1 = 2;
        public static final int VP8Level_Version2 = 4;
        public static final int VP8Level_Version3 = 8;
        public static final int VP8ProfileMain = 1;
        public static final int VP9Level1 = 1;
        public static final int VP9Level11 = 2;
        public static final int VP9Level2 = 4;
        public static final int VP9Level21 = 8;
        public static final int VP9Level3 = 16;
        public static final int VP9Level31 = 32;
        public static final int VP9Level4 = 64;
        public static final int VP9Level41 = 128;
        public static final int VP9Level5 = 256;
        public static final int VP9Level51 = 512;
        public static final int VP9Level52 = 1024;
        public static final int VP9Level6 = 2048;
        public static final int VP9Level61 = 4096;
        public static final int VP9Level62 = 8192;
        public static final int VP9Profile0 = 1;
        public static final int VP9Profile1 = 2;
        public static final int VP9Profile2 = 4;
        public static final int VP9Profile2HDR = 4096;
        public static final int VP9Profile2HDR10Plus = 16384;
        public static final int VP9Profile3 = 8;
        public static final int VP9Profile3HDR = 8192;
        public static final int VP9Profile3HDR10Plus = 32768;
        public int level;
        public int profile;

        public boolean equals(Object object) {
            boolean bl = false;
            if (object == null) {
                return false;
            }
            if (object instanceof CodecProfileLevel) {
                object = (CodecProfileLevel)object;
                boolean bl2 = bl;
                if (((CodecProfileLevel)object).profile == this.profile) {
                    bl2 = bl;
                    if (((CodecProfileLevel)object).level == this.level) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            return false;
        }

        public int hashCode() {
            return Long.hashCode((long)this.profile << 32 | (long)this.level);
        }
    }

    public static final class EncoderCapabilities {
        public static final int BITRATE_MODE_CBR = 2;
        public static final int BITRATE_MODE_CQ = 0;
        public static final int BITRATE_MODE_VBR = 1;
        private static final Feature[] bitrates = new Feature[]{new Feature("VBR", 1, true), new Feature("CBR", 2, false), new Feature("CQ", 0, false)};
        private int mBitControl;
        private Range<Integer> mComplexityRange;
        private Integer mDefaultComplexity;
        private Integer mDefaultQuality;
        private CodecCapabilities mParent;
        private Range<Integer> mQualityRange;
        private String mQualityScale;

        private EncoderCapabilities() {
        }

        private void applyLevelLimits() {
            String string2 = this.mParent.getMimeType();
            if (string2.equalsIgnoreCase("audio/flac")) {
                this.mComplexityRange = Range.create(0, 8);
                this.mBitControl = 1;
            } else if (string2.equalsIgnoreCase("audio/3gpp") || string2.equalsIgnoreCase("audio/amr-wb") || string2.equalsIgnoreCase("audio/g711-alaw") || string2.equalsIgnoreCase("audio/g711-mlaw") || string2.equalsIgnoreCase("audio/gsm")) {
                this.mBitControl = 4;
            }
        }

        public static EncoderCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            EncoderCapabilities encoderCapabilities = new EncoderCapabilities();
            encoderCapabilities.init(mediaFormat, codecCapabilities);
            return encoderCapabilities;
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities object) {
            this.mParent = object;
            object = 0;
            this.mComplexityRange = Range.create(object, object);
            this.mQualityRange = Range.create(object, object);
            this.mBitControl = 2;
            this.applyLevelLimits();
            this.parseFromInfo(mediaFormat);
        }

        private static int parseBitrateMode(String string2) {
            for (Feature feature : bitrates) {
                if (!feature.mName.equalsIgnoreCase(string2)) continue;
                return feature.mValue;
            }
            return 0;
        }

        private void parseFromInfo(MediaFormat arrstring) {
            Map<String, Object> map = arrstring.getMap();
            if (arrstring.containsKey("complexity-range")) {
                this.mComplexityRange = Utils.parseIntRange(arrstring.getString("complexity-range"), this.mComplexityRange);
            }
            if (arrstring.containsKey("quality-range")) {
                this.mQualityRange = Utils.parseIntRange(arrstring.getString("quality-range"), this.mQualityRange);
            }
            if (arrstring.containsKey("feature-bitrate-modes")) {
                for (String string2 : arrstring.getString("feature-bitrate-modes").split(",")) {
                    this.mBitControl |= 1 << EncoderCapabilities.parseBitrateMode(string2);
                }
            }
            try {
                this.mDefaultComplexity = Integer.parseInt((String)map.get("complexity-default"));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            try {
                this.mDefaultQuality = Integer.parseInt((String)map.get("quality-default"));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            this.mQualityScale = (String)map.get("quality-scale");
        }

        private boolean supports(Integer n, Integer arrcodecProfileLevel, Integer n2) {
            boolean bl;
            boolean bl2 = bl = true;
            if (true) {
                bl2 = bl;
                if (n != null) {
                    bl2 = this.mComplexityRange.contains(n);
                }
            }
            bl = bl2;
            if (bl2) {
                bl = bl2;
                if (arrcodecProfileLevel != null) {
                    bl = this.mQualityRange.contains((Integer)arrcodecProfileLevel);
                }
            }
            bl2 = bl;
            if (bl) {
                bl2 = bl;
                if (n2 != null) {
                    arrcodecProfileLevel = this.mParent.profileLevels;
                    int n3 = arrcodecProfileLevel.length;
                    bl = false;
                    int n4 = 0;
                    do {
                        n = n2;
                        if (n4 >= n3) break;
                        if (arrcodecProfileLevel[n4].profile == n2) {
                            n = null;
                            break;
                        }
                        ++n4;
                    } while (true);
                    if (n == null) {
                        bl = true;
                    }
                    bl2 = bl;
                }
            }
            return bl2;
        }

        public Range<Integer> getComplexityRange() {
            return this.mComplexityRange;
        }

        public void getDefaultFormat(MediaFormat mediaFormat) {
            Object object;
            if (!this.mQualityRange.getUpper().equals(this.mQualityRange.getLower()) && (object = this.mDefaultQuality) != null) {
                mediaFormat.setInteger("quality", (Integer)object);
            }
            if (!this.mComplexityRange.getUpper().equals(this.mComplexityRange.getLower()) && (object = this.mDefaultComplexity) != null) {
                mediaFormat.setInteger("complexity", (Integer)object);
            }
            for (Feature feature : bitrates) {
                if ((this.mBitControl & 1 << feature.mValue) == 0) continue;
                mediaFormat.setInteger("bitrate-mode", feature.mValue);
                break;
            }
        }

        public Range<Integer> getQualityRange() {
            return this.mQualityRange;
        }

        public boolean isBitrateModeSupported(int n) {
            Feature[] arrfeature = bitrates;
            int n2 = arrfeature.length;
            for (int i = 0; i < n2; ++i) {
                if (n != arrfeature[i].mValue) continue;
                i = this.mBitControl;
                boolean bl = true;
                if ((i & 1 << n) == 0) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public boolean supportsFormat(MediaFormat object) {
            Object object2;
            Integer n;
            Map<String, Object> map = ((MediaFormat)object).getMap();
            Object object3 = this.mParent.getMimeType();
            object = (Integer)map.get("bitrate-mode");
            if (object != null && !this.isBitrateModeSupported((Integer)object)) {
                return false;
            }
            object = object2 = (Integer)map.get("complexity");
            if ("audio/flac".equalsIgnoreCase((String)object3)) {
                n = (Integer)map.get("flac-compression-level");
                if (object2 == null) {
                    object = n;
                } else {
                    object = object2;
                    if (n != null) {
                        if (((Integer)object2).equals(n)) {
                            object = object2;
                        } else {
                            throw new IllegalArgumentException("conflicting values for complexity and flac-compression-level");
                        }
                    }
                }
            }
            n = (Integer)map.get("profile");
            object2 = n;
            if ("audio/mp4a-latm".equalsIgnoreCase((String)object3)) {
                object3 = (Integer)map.get("aac-profile");
                if (n == null) {
                    object2 = object3;
                } else {
                    object2 = n;
                    if (object3 != null) {
                        if (((Integer)object3).equals(n)) {
                            object2 = n;
                        } else {
                            throw new IllegalArgumentException("conflicting values for profile and aac-profile");
                        }
                    }
                }
            }
            return this.supports((Integer)object, (Integer)map.get("quality"), (Integer)object2);
        }
    }

    private static class Feature {
        public boolean mDefault;
        public String mName;
        public int mValue;

        public Feature(String string2, int n, boolean bl) {
            this.mName = string2;
            this.mValue = n;
            this.mDefault = bl;
        }
    }

    public static final class VideoCapabilities {
        private static final String TAG = "VideoCapabilities";
        private boolean mAllowMbOverride;
        private Range<Rational> mAspectRatioRange;
        private Range<Integer> mBitrateRange;
        private Range<Rational> mBlockAspectRatioRange;
        private Range<Integer> mBlockCountRange;
        private int mBlockHeight;
        private int mBlockWidth;
        private Range<Long> mBlocksPerSecondRange;
        private Range<Integer> mFrameRateRange;
        private int mHeightAlignment;
        private Range<Integer> mHeightRange;
        private Range<Integer> mHorizontalBlockRange;
        private Map<Size, Range<Long>> mMeasuredFrameRates;
        private CodecCapabilities mParent;
        private List<PerformancePoint> mPerformancePoints;
        private int mSmallerDimensionUpperLimit;
        private Range<Integer> mVerticalBlockRange;
        private int mWidthAlignment;
        private Range<Integer> mWidthRange;

        private VideoCapabilities() {
        }

        private void applyAlignment(int n, int n2) {
            MediaCodecInfo.checkPowerOfTwo(n, "widthAlignment must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(n2, "heightAlignment must be a power of two");
            if (n > this.mBlockWidth || n2 > this.mBlockHeight) {
                this.applyBlockLimits(Math.max(n, this.mBlockWidth), Math.max(n2, this.mBlockHeight), POSITIVE_INTEGERS, POSITIVE_LONGS, POSITIVE_RATIONALS);
            }
            this.mWidthAlignment = Math.max(n, this.mWidthAlignment);
            this.mHeightAlignment = Math.max(n2, this.mHeightAlignment);
            this.mWidthRange = Utils.alignRange(this.mWidthRange, this.mWidthAlignment);
            this.mHeightRange = Utils.alignRange(this.mHeightRange, this.mHeightAlignment);
        }

        private void applyBlockLimits(int n, int n2, Range<Integer> range, Range<Long> range2, Range<Rational> range3) {
            MediaCodecInfo.checkPowerOfTwo(n, "blockWidth must be a power of two");
            MediaCodecInfo.checkPowerOfTwo(n2, "blockHeight must be a power of two");
            int n3 = Math.max(n, this.mBlockWidth);
            int n4 = Math.max(n2, this.mBlockHeight);
            int n5 = n3 * n4 / this.mBlockWidth / this.mBlockHeight;
            if (n5 != 1) {
                this.mBlockCountRange = Utils.factorRange(this.mBlockCountRange, n5);
                this.mBlocksPerSecondRange = Utils.factorRange(this.mBlocksPerSecondRange, (long)n5);
                this.mBlockAspectRatioRange = Utils.scaleRange(this.mBlockAspectRatioRange, n4 / this.mBlockHeight, n3 / this.mBlockWidth);
                this.mHorizontalBlockRange = Utils.factorRange(this.mHorizontalBlockRange, n3 / this.mBlockWidth);
                this.mVerticalBlockRange = Utils.factorRange(this.mVerticalBlockRange, n4 / this.mBlockHeight);
            }
            n5 = n3 * n4 / n / n2;
            Range<Integer> range4 = range;
            Range<Long> range5 = range2;
            Range<Rational> range6 = range3;
            if (n5 != 1) {
                range4 = Utils.factorRange(range, n5);
                range5 = Utils.factorRange(range2, (long)n5);
                range6 = Utils.scaleRange(range3, n4 / n2, n3 / n);
            }
            this.mBlockCountRange = this.mBlockCountRange.intersect(range4);
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(range5);
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(range6);
            this.mBlockWidth = n3;
            this.mBlockHeight = n4;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private void applyLevelLimits() {
            block178 : {
                block184 : {
                    block182 : {
                        block180 : {
                            block176 : {
                                block174 : {
                                    block172 : {
                                        block170 : {
                                            block183 : {
                                                block181 : {
                                                    block179 : {
                                                        block177 : {
                                                            block175 : {
                                                                block173 : {
                                                                    block171 : {
                                                                        block169 : {
                                                                            var1_1 = this.mParent.profileLevels;
                                                                            var2_2 = this.mParent.getMimeType();
                                                                            var3_3 = var2_2.equalsIgnoreCase("video/avc");
                                                                            var4_4 = "Unrecognized level ";
                                                                            var5_5 = "Unrecognized profile ";
                                                                            var6_6 = " for ";
                                                                            var7_7 = "VideoCapabilities";
                                                                            var8_14 = 1;
                                                                            if (!var3_3) break block169;
                                                                            var9_21 = ((CodecProfileLevel[])var1_1).length;
                                                                            var10_27 = 4;
                                                                            var11_34 = 99;
                                                                            var12_40 = 64000;
                                                                            var13_47 = 1485L;
                                                                            var15_54 = 396;
                                                                            var17_62 = var1_1;
                                                                            var1_1 = var8_14;
                                                                            var8_15 = var4_4;
                                                                            break block170;
                                                                        }
                                                                        var21_77 = "Unrecognized level ";
                                                                        var17_63 = var8_14;
                                                                        var7_8 = "VideoCapabilities";
                                                                        var3_3 = var2_2.equalsIgnoreCase("video/mpeg2");
                                                                        var4_4 = "/";
                                                                        var28_124 = "Unrecognized profile/level ";
                                                                        if (!var3_3) break block171;
                                                                        var8_16 = var1_1;
                                                                        var18_65 = var8_16.length;
                                                                        var27_118 = 4;
                                                                        var19_69 = 99;
                                                                        var26_111 = 64000;
                                                                        var13_48 = 1485L;
                                                                        var15_55 = 11;
                                                                        var16_59 = 15;
                                                                        var11_35 = 9;
                                                                        var1_1 = var7_8;
                                                                        var7_9 = var8_16;
                                                                        var8_17 = var28_124;
                                                                        var21_78 = var6_6;
                                                                        break block172;
                                                                    }
                                                                    var28_124 = " for ";
                                                                    var6_6 = "/";
                                                                    var8_18 = "Unrecognized profile/level ";
                                                                    if (!var2_2.equalsIgnoreCase("video/mp4v-es")) break block173;
                                                                    var21_79 /* !! */  = var1_1;
                                                                    var15_56 = var21_79 /* !! */ .length;
                                                                    var29_126 = 99;
                                                                    var27_119 = 4;
                                                                    var25_108 = 11;
                                                                    var19_70 = 9;
                                                                    var18_66 = 15;
                                                                    var13_49 = 1485L;
                                                                    var26_112 = 64000;
                                                                    var1_1 = var28_124;
                                                                    var4_4 = var6_6;
                                                                    var6_6 = var5_5;
                                                                    break block174;
                                                                }
                                                                var5_5 = var8_18;
                                                                var8_19 = var7_8;
                                                                var4_4 = "Unrecognized profile ";
                                                                var7_10 = var6_6;
                                                                if (!var2_2.equalsIgnoreCase("video/3gpp")) break block175;
                                                                var15_57 = 9;
                                                                var21_80 /* !! */  = var1_1;
                                                                var29_127 = var21_80 /* !! */ .length;
                                                                var11_37 = 4;
                                                                var9_24 = 16;
                                                                var16_61 = 11;
                                                                var31_130 = 99;
                                                                var18_67 = 64000;
                                                                var13_50 = 1485L;
                                                                var32_131 = 11;
                                                                var30_129 = 9;
                                                                var19_71 = 15;
                                                                var1_1 = var8_19;
                                                                var8_20 /* !! */  = var21_80 /* !! */ ;
                                                                break block176;
                                                            }
                                                            if (!var2_2.equalsIgnoreCase("video/x-vnd.on2.vp8")) break block177;
                                                            var24_102 = ((CodecProfileLevel[])var1_1).length;
                                                            var20_72 = 4;
                                                            for (var23_97 = 0; var23_97 < var24_102; var20_72 &= -5, ++var23_97) {
                                                                var7_12 = var1_1[var23_97];
                                                                var22_93 = var7_12.level;
                                                                if (var22_93 != 1 && var22_93 != 2 && var22_93 != 4 && var22_93 != 8) {
                                                                    var6_6 = new StringBuilder();
                                                                    var6_6.append(var21_77);
                                                                    var6_6.append(var7_12.level);
                                                                    var6_6.append((String)var28_124);
                                                                    var6_6.append(var2_2);
                                                                    Log.w(var8_19, var6_6.toString());
                                                                    var20_72 |= 1;
                                                                }
                                                                if (var7_12.profile == 1) continue;
                                                                var6_6 = new StringBuilder();
                                                                var6_6.append((String)var4_4);
                                                                var6_6.append(var7_12.profile);
                                                                var6_6.append((String)var28_124);
                                                                var6_6.append(var2_2);
                                                                Log.w(var8_19, var6_6.toString());
                                                                var20_72 |= 1;
                                                            }
                                                            this.applyMacroBlockLimits(32767, 32767, Integer.MAX_VALUE, Integer.MAX_VALUE, 16, 16, 1, 1);
                                                            var1_1 = var17_63;
                                                            var23_97 = 100000000;
                                                            break block178;
                                                        }
                                                        var7_13 = var21_77;
                                                        if (!var2_2.equalsIgnoreCase("video/x-vnd.on2.vp9")) break block179;
                                                        var26_114 = 36864;
                                                        var12_44 = ((CodecProfileLevel[])var1_1).length;
                                                        var22_94 = 4;
                                                        var27_121 = 512;
                                                        var10_31 = 200000;
                                                        var34_133 = 829440L;
                                                        break block180;
                                                    }
                                                    if (!var2_2.equalsIgnoreCase("video/hevc")) break block181;
                                                    var13_52 = 576 * 15;
                                                    var12_45 = ((CodecProfileLevel[])var1_1).length;
                                                    var10_32 = 576;
                                                    var22_95 = 128000;
                                                    var24_104 = 4;
                                                    break block182;
                                                }
                                                if (!var2_2.equalsIgnoreCase("video/av01")) break block183;
                                                var27_123 = 36864;
                                                var12_46 = ((CodecProfileLevel[])var1_1).length;
                                                var10_33 = 200000;
                                                var22_96 = 4;
                                                var26_116 = 512;
                                                var34_134 = 829440L;
                                                break block184;
                                            }
                                            var1_1 = new StringBuilder();
                                            var1_1.append("Unsupported mime ");
                                            var1_1.append(var2_2);
                                            Log.w(var8_19, var1_1.toString());
                                            var20_72 = 4 | 2;
                                            var23_97 = 64000;
                                            var1_1 = var17_63;
                                            break block178;
                                        }
                                        for (var16_58 = 0; var16_58 < var9_21; ++var16_58) {
                                            block189 : {
                                                block185 : {
                                                    block186 : {
                                                        block187 : {
                                                            block188 : {
                                                                var4_4 = var17_62[var16_58];
                                                                var18_64 = 1;
                                                                var19_68 = 1;
                                                                var20_72 = var4_4.level;
                                                                if (var20_72 != 1) {
                                                                    if (var20_72 != 2) {
                                                                        switch (var20_72) {
                                                                            default: {
                                                                                var21_74 = new StringBuilder();
                                                                                var21_74.append(var8_15);
                                                                                var21_74.append(var4_4.level);
                                                                                var21_74.append(" for ");
                                                                                var21_74.append(var2_2);
                                                                                Log.w(var7_7, var21_74.toString());
                                                                                var10_27 |= 1;
                                                                                var22_89 = 0;
                                                                                var23_97 = 0;
                                                                                var24_98 = 0;
                                                                                var20_72 = 0;
                                                                                ** break;
                                                                            }
                                                                            case 524288: {
                                                                                var22_89 = 16711680;
                                                                                var23_97 = 800000;
                                                                                var24_98 = 696320;
                                                                                var20_72 = 139264;
                                                                                ** break;
                                                                            }
                                                                            case 262144: {
                                                                                var22_89 = 8355840;
                                                                                var23_97 = 480000;
                                                                                var24_98 = 696320;
                                                                                var20_72 = 139264;
                                                                                ** break;
                                                                            }
                                                                            case 131072: {
                                                                                var22_89 = 4177920;
                                                                                var23_97 = 240000;
                                                                                var24_98 = 696320;
                                                                                var20_72 = 139264;
                                                                                ** break;
                                                                            }
                                                                            case 65536: {
                                                                                var22_89 = 2073600;
                                                                                var23_97 = 240000;
                                                                                var24_98 = 184320;
                                                                                var20_72 = 36864;
                                                                                ** break;
                                                                            }
                                                                            case 32768: {
                                                                                var22_89 = 983040;
                                                                                var23_97 = 240000;
                                                                                var24_98 = 184320;
                                                                                var20_72 = 36864;
                                                                                ** break;
                                                                            }
                                                                            case 16384: {
                                                                                var22_89 = 589824;
                                                                                var23_97 = 135000;
                                                                                var24_98 = 110400;
                                                                                var20_72 = 22080;
                                                                                ** break;
                                                                            }
                                                                            case 8192: {
                                                                                var22_89 = 522240;
                                                                                var23_97 = 50000;
                                                                                var24_98 = 34816;
                                                                                var20_72 = 8704;
                                                                                ** break;
                                                                            }
                                                                            case 4096: {
                                                                                var22_89 = 245760;
                                                                                var23_97 = 50000;
                                                                                var24_98 = 32768;
                                                                                var20_72 = 8192;
                                                                                ** break;
                                                                            }
                                                                            case 2048: {
                                                                                var22_89 = 245760;
                                                                                var23_97 = 20000;
                                                                                var24_98 = 32768;
                                                                                var20_72 = 8192;
                                                                                ** break;
                                                                            }
                                                                            case 1024: {
                                                                                var22_89 = 216000;
                                                                                var23_97 = 20000;
                                                                                var24_98 = 20480;
                                                                                var20_72 = 5120;
                                                                                ** break;
                                                                            }
                                                                            case 512: {
                                                                                var22_89 = 108000;
                                                                                var23_97 = 14000;
                                                                                var24_98 = 18000;
                                                                                var20_72 = 3600;
                                                                                ** break;
                                                                            }
                                                                            case 256: {
                                                                                var22_89 = 40500;
                                                                                var23_97 = 10000;
                                                                                var24_98 = 8100;
                                                                                var20_72 = 1620;
                                                                                ** break;
                                                                            }
                                                                            case 128: {
                                                                                var22_89 = 20250;
                                                                                var23_97 = 4000;
                                                                                var24_98 = 8100;
                                                                                var20_72 = 1620;
                                                                                ** break;
                                                                            }
                                                                            case 64: {
                                                                                var22_89 = 19800;
                                                                                var23_97 = 4000;
                                                                                var24_98 = 4752;
                                                                                var20_72 = 792;
                                                                                ** break;
                                                                            }
                                                                            case 32: {
                                                                                var22_89 = 11880;
                                                                                var23_97 = 2000;
                                                                                var24_98 = 2376;
                                                                                var20_72 = 396;
                                                                                ** break;
                                                                            }
                                                                            case 16: {
                                                                                var22_89 = 11880;
                                                                                var23_97 = 768;
                                                                                var24_98 = 2376;
                                                                                var20_72 = 396;
                                                                                ** break;
                                                                            }
                                                                            case 8: {
                                                                                var22_89 = 6000;
                                                                                var23_97 = 384;
                                                                                var24_98 = 2376;
                                                                                var20_72 = 396;
                                                                                ** break;
                                                                            }
                                                                            case 4: 
                                                                        }
                                                                        var22_89 = 3000;
                                                                        var23_97 = 192;
                                                                        var24_98 = 900;
                                                                        var20_72 = 396;
                                                                        ** break;
lbl293: // 19 sources:
                                                                    } else {
                                                                        var22_89 = 1485;
                                                                        var23_97 = 128;
                                                                        var24_98 = 396;
                                                                        var20_72 = 99;
                                                                    }
                                                                } else {
                                                                    var22_89 = 1485;
                                                                    var23_97 = 64;
                                                                    var24_98 = 396;
                                                                    var20_72 = 99;
                                                                }
                                                                var25_106 = var4_4.profile;
                                                                var26_110 = var10_27;
                                                                var27_117 = var19_68;
                                                                if (var25_106 == 1) break block185;
                                                                var26_110 = var10_27;
                                                                var27_117 = var19_68;
                                                                if (var25_106 == 2) break block185;
                                                                if (var25_106 == 4) break block186;
                                                                if (var25_106 == 8) break block187;
                                                                if (var25_106 == 16) break block188;
                                                                if (var25_106 == 32 || var25_106 == 64) break block186;
                                                                var26_110 = var10_27;
                                                                var27_117 = var19_68;
                                                                if (var25_106 == 65536) break block185;
                                                                if (var25_106 == 524288) break block187;
                                                                var21_75 = new StringBuilder();
                                                                var21_75.append("Unrecognized profile ");
                                                                var21_75.append(var4_4.profile);
                                                                var21_75.append(" for ");
                                                                var21_75.append(var2_2);
                                                                Log.w(var7_7, var21_75.toString());
                                                                var10_27 |= 1;
                                                                var23_97 *= 1000;
                                                                break block189;
                                                            }
                                                            var23_97 *= 3000;
                                                            break block189;
                                                        }
                                                        var23_97 *= 1250;
                                                        break block189;
                                                    }
                                                    var21_76 = new StringBuilder();
                                                    var21_76.append("Unsupported profile ");
                                                    var21_76.append(var4_4.profile);
                                                    var21_76.append(" for ");
                                                    var21_76.append(var2_2);
                                                    Log.w(var7_7, var21_76.toString());
                                                    var26_110 = var10_27 | 2;
                                                    var27_117 = 0;
                                                }
                                                var23_97 *= 1000;
                                                var18_64 = var27_117;
                                                var10_27 = var26_110;
                                            }
                                            var27_117 = var10_27;
                                            if (var18_64 != 0) {
                                                var27_117 = var10_27 & -5;
                                            }
                                            var13_47 = Math.max((long)var22_89, var13_47);
                                            var11_34 = Math.max(var20_72, var11_34);
                                            var12_40 = Math.max(var23_97, var12_40);
                                            var15_54 = Math.max(var15_54, var24_98);
                                            var10_27 = var27_117;
                                        }
                                        var20_72 = (int)Math.sqrt(var11_34 * 8);
                                        var23_97 = var12_40;
                                        this.applyMacroBlockLimits(var20_72, var20_72, var11_34, var13_47, 16, 16, 1, 1);
                                        var20_72 = var10_27;
                                        break block178;
                                    }
                                    for (var25_107 = 0; var25_107 < var18_65; ++var25_107) {
                                        var6_6 = var7_9[var25_107];
                                        var29_125 = true;
                                        var20_72 = var6_6.profile;
                                        if (var20_72 != 0) {
                                            if (var20_72 != 1) {
                                                if (var20_72 != 2 && var20_72 != 3 && var20_72 != 4 && var20_72 != 5) {
                                                    var5_5 = new StringBuilder();
                                                    var5_5.append("Unrecognized profile ");
                                                    var5_5.append(var6_6.profile);
                                                    var5_5.append(var21_78);
                                                    var5_5.append(var2_2);
                                                    Log.w((String)var1_1, var5_5.toString());
                                                    var27_118 |= 1;
                                                    var23_97 = 0;
                                                    var10_28 = 0;
                                                    var20_72 = 0;
                                                    var24_99 = 0;
                                                    var22_90 = 0;
                                                    var12_41 = 0;
                                                } else {
                                                    var5_5 = new StringBuilder();
                                                    var5_5.append("Unsupported profile ");
                                                    var5_5.append(var6_6.profile);
                                                    var5_5.append(var21_78);
                                                    var5_5.append(var2_2);
                                                    Log.i((String)var1_1, var5_5.toString());
                                                    var27_118 |= 2;
                                                    var29_125 = false;
                                                    var23_97 = 0;
                                                    var10_28 = 0;
                                                    var20_72 = 0;
                                                    var24_99 = 0;
                                                    var22_90 = 0;
                                                    var12_41 = 0;
                                                }
                                            } else {
                                                var20_72 = var6_6.level;
                                                if (var20_72 != 0) {
                                                    if (var20_72 != 1) {
                                                        if (var20_72 != 2) {
                                                            if (var20_72 != 3) {
                                                                if (var20_72 != 4) {
                                                                    var5_5 = new StringBuilder();
                                                                    var5_5.append(var8_17);
                                                                    var5_5.append(var6_6.profile);
                                                                    var5_5.append((String)var4_4);
                                                                    var5_5.append(var6_6.level);
                                                                    var5_5.append(var21_78);
                                                                    var5_5.append(var2_2);
                                                                    Log.w((String)var1_1, var5_5.toString());
                                                                    var27_118 |= 1;
                                                                    var23_97 = 0;
                                                                    var10_28 = 0;
                                                                    var20_72 = 0;
                                                                    var24_99 = 0;
                                                                    var22_90 = 0;
                                                                    var12_41 = 0;
                                                                } else {
                                                                    var23_97 = 489600;
                                                                    var10_28 = 8160;
                                                                    var20_72 = 80000;
                                                                    var24_99 = 60;
                                                                    var22_90 = 120;
                                                                    var12_41 = 68;
                                                                }
                                                            } else {
                                                                var23_97 = 244800;
                                                                var10_28 = 8160;
                                                                var20_72 = 80000;
                                                                var24_99 = 60;
                                                                var22_90 = 120;
                                                                var12_41 = 68;
                                                            }
                                                        } else {
                                                            var23_97 = 183600;
                                                            var10_28 = 6120;
                                                            var20_72 = 60000;
                                                            var24_99 = 60;
                                                            var22_90 = 90;
                                                            var12_41 = 68;
                                                        }
                                                    } else {
                                                        var23_97 = 40500;
                                                        var10_28 = 1620;
                                                        var20_72 = 15000;
                                                        var24_99 = 30;
                                                        var22_90 = 45;
                                                        var12_41 = 36;
                                                    }
                                                } else {
                                                    var23_97 = 11880;
                                                    var10_28 = 396;
                                                    var20_72 = 4000;
                                                    var24_99 = 30;
                                                    var22_90 = 22;
                                                    var12_41 = 18;
                                                }
                                            }
                                        } else if (var6_6.level != 1) {
                                            var5_5 = new StringBuilder();
                                            var5_5.append(var8_17);
                                            var5_5.append(var6_6.profile);
                                            var5_5.append((String)var4_4);
                                            var5_5.append(var6_6.level);
                                            var5_5.append(var21_78);
                                            var5_5.append(var2_2);
                                            Log.w((String)var1_1, var5_5.toString());
                                            var27_118 |= 1;
                                            var23_97 = 0;
                                            var10_28 = 0;
                                            var20_72 = 0;
                                            var24_99 = 0;
                                            var22_90 = 0;
                                            var12_41 = 0;
                                        } else {
                                            var23_97 = 40500;
                                            var10_28 = 1620;
                                            var20_72 = 15000;
                                            var24_99 = 30;
                                            var22_90 = 45;
                                            var12_41 = 36;
                                        }
                                        var9_22 = var27_118;
                                        if (var29_125) {
                                            var9_22 = var27_118 & -5;
                                        }
                                        var13_48 = Math.max((long)var23_97, var13_48);
                                        var19_69 = Math.max(var10_28, var19_69);
                                        var26_111 = Math.max(var20_72 * 1000, var26_111);
                                        var15_55 = Math.max(var22_90, var15_55);
                                        var11_35 = Math.max(var12_41, var11_35);
                                        var16_59 = Math.max(var24_99, var16_59);
                                        var27_118 = var9_22;
                                    }
                                    this.applyMacroBlockLimits(var15_55, var11_35, var19_69, var13_48, 16, 16, 1, 1);
                                    this.mFrameRateRange = this.mFrameRateRange.intersect(12, var16_59);
                                    var23_97 = var26_111;
                                    var20_72 = var27_118;
                                    var1_1 = var17_63;
                                    break block178;
                                }
                                for (var16_60 = 0; var16_60 < var15_56; ++var16_60) {
                                    var5_5 = var21_79 /* !! */ [var16_60];
                                    var11_36 = false;
                                    var30_128 = true;
                                    var20_72 = var5_5.profile;
                                    if (var20_72 != 1) {
                                        if (var20_72 != 2) {
                                            switch (var20_72) {
                                                default: {
                                                    var28_124 = new StringBuilder();
                                                    var28_124.append((String)var6_6);
                                                    var28_124.append(var5_5.profile);
                                                    var28_124.append((String)var1_1);
                                                    var28_124.append(var2_2);
                                                    Log.w(var7_8, var28_124.toString());
                                                    var27_119 |= 1;
                                                    var23_97 = 0;
                                                    var24_100 = 0;
                                                    var22_91 = 0;
                                                    var10_29 = 0;
                                                    var20_72 = 0;
                                                    var12_42 = 0;
                                                    ** break;
                                                }
                                                case 32768: {
                                                    var20_72 = var5_5.level;
                                                    if (var20_72 != 1 && var20_72 != 4) {
                                                        if (var20_72 != 8) {
                                                            if (var20_72 != 16) {
                                                                if (var20_72 != 24) {
                                                                    if (var20_72 != 32) {
                                                                        if (var20_72 != 128) {
                                                                            var28_124 = new StringBuilder();
                                                                            var28_124.append(var8_18);
                                                                            var28_124.append(var5_5.profile);
                                                                            var28_124.append((String)var4_4);
                                                                            var28_124.append(var5_5.level);
                                                                            var28_124.append((String)var1_1);
                                                                            var28_124.append(var2_2);
                                                                            Log.w(var7_8, var28_124.toString());
                                                                            var27_119 |= 1;
                                                                            var23_97 = 0;
                                                                            var24_100 = 0;
                                                                            var12_42 = 0;
                                                                            var22_91 = 0;
                                                                            var20_72 = 0;
                                                                            var10_29 = 0;
                                                                            ** break;
                                                                        }
                                                                        var12_42 = 1620;
                                                                        var24_100 = 30;
                                                                        var22_91 = 45;
                                                                        var10_29 = 36;
                                                                        var20_72 = 48600;
                                                                        var23_97 = 8000;
                                                                        ** break;
                                                                    }
                                                                    var12_42 = 792;
                                                                    var24_100 = 30;
                                                                    var22_91 = 44;
                                                                    var10_29 = 36;
                                                                    var20_72 = 23760;
                                                                    var23_97 = 3000;
                                                                    ** break;
                                                                }
                                                                var12_42 = 396;
                                                                var24_100 = 30;
                                                                var22_91 = 22;
                                                                var10_29 = 18;
                                                                var20_72 = 11880;
                                                                var23_97 = 1500;
                                                                ** break;
                                                            }
                                                            var12_42 = 396;
                                                            var24_100 = 30;
                                                            var22_91 = 22;
                                                            var10_29 = 18;
                                                            var20_72 = 11880;
                                                            var23_97 = 768;
                                                            ** break;
                                                        }
                                                        var12_42 = 396;
                                                        var24_100 = 30;
                                                        var22_91 = 22;
                                                        var10_29 = 18;
                                                        var20_72 = 5940;
                                                        var23_97 = 384;
                                                        ** break;
                                                    }
                                                    var12_42 = 99;
                                                    var24_100 = 30;
                                                    var22_91 = 11;
                                                    var10_29 = 9;
                                                    var20_72 = 2970;
                                                    var23_97 = 128;
                                                    ** break;
                                                }
                                                case 4: 
                                                case 8: 
                                                case 16: 
                                                case 32: 
                                                case 64: 
                                                case 128: 
                                                case 256: 
                                                case 512: 
                                                case 1024: 
                                                case 2048: 
                                                case 4096: 
                                                case 8192: 
                                                case 16384: 
                                            }
                                        }
                                        var28_124 = new StringBuilder();
                                        var28_124.append("Unsupported profile ");
                                        var28_124.append(var5_5.profile);
                                        var28_124.append((String)var1_1);
                                        var28_124.append(var2_2);
                                        Log.i(var7_8, var28_124.toString());
                                        var27_119 |= 2;
                                        var30_128 = false;
                                        var23_97 = 0;
                                        var24_100 = 0;
                                        var12_42 = 0;
                                        var22_91 = 0;
                                        var20_72 = 0;
                                        var10_29 = 0;
                                        ** break;
lbl652: // 9 sources:
                                    } else {
                                        var20_72 = var5_5.level;
                                        if (var20_72 != 1) {
                                            if (var20_72 != 2) {
                                                if (var20_72 != 4) {
                                                    if (var20_72 != 8) {
                                                        if (var20_72 != 16) {
                                                            if (var20_72 != 64) {
                                                                if (var20_72 != 128) {
                                                                    if (var20_72 != 256) {
                                                                        var28_124 = new StringBuilder();
                                                                        var28_124.append(var8_18);
                                                                        var28_124.append(var5_5.profile);
                                                                        var28_124.append((String)var4_4);
                                                                        var28_124.append(var5_5.level);
                                                                        var28_124.append((String)var1_1);
                                                                        var28_124.append(var2_2);
                                                                        Log.w(var7_8, var28_124.toString());
                                                                        var27_119 |= 1;
                                                                        var23_97 = 0;
                                                                        var24_100 = 0;
                                                                        var12_42 = 0;
                                                                        var22_91 = 0;
                                                                        var20_72 = 0;
                                                                        var10_29 = 0;
                                                                    } else {
                                                                        var12_42 = 3600;
                                                                        var24_100 = 30;
                                                                        var22_91 = 80;
                                                                        var10_29 = 45;
                                                                        var20_72 = 108000;
                                                                        var23_97 = 12000;
                                                                    }
                                                                } else {
                                                                    var12_42 = 1620;
                                                                    var24_100 = 30;
                                                                    var22_91 = 45;
                                                                    var10_29 = 36;
                                                                    var20_72 = 40500;
                                                                    var23_97 = 8000;
                                                                }
                                                            } else {
                                                                var12_42 = 1200;
                                                                var24_100 = 30;
                                                                var22_91 = 40;
                                                                var10_29 = 30;
                                                                var20_72 = 36000;
                                                                var23_97 = 4000;
                                                            }
                                                        } else {
                                                            var12_42 = 396;
                                                            var24_100 = 30;
                                                            var22_91 = 22;
                                                            var10_29 = 18;
                                                            var20_72 = 11880;
                                                            var23_97 = 384;
                                                        }
                                                    } else {
                                                        var12_42 = 396;
                                                        var24_100 = 30;
                                                        var22_91 = 22;
                                                        var10_29 = 18;
                                                        var20_72 = 5940;
                                                        var23_97 = 128;
                                                    }
                                                } else {
                                                    var12_42 = 99;
                                                    var24_100 = 30;
                                                    var22_91 = 11;
                                                    var10_29 = 9;
                                                    var20_72 = 1485;
                                                    var23_97 = 64;
                                                }
                                            } else {
                                                var11_36 = true;
                                                var12_42 = 99;
                                                var24_100 = 15;
                                                var22_91 = 11;
                                                var10_29 = 9;
                                                var20_72 = 1485;
                                                var23_97 = 128;
                                            }
                                        } else {
                                            var11_36 = true;
                                            var12_42 = 99;
                                            var24_100 = 15;
                                            var22_91 = 11;
                                            var10_29 = 9;
                                            var20_72 = 1485;
                                            var23_97 = 64;
                                        }
                                    }
                                    var9_23 = var27_119;
                                    if (var30_128) {
                                        var9_23 = var27_119 & -5;
                                    }
                                    var13_49 = Math.max((long)var20_72, var13_49);
                                    var29_126 = Math.max(var12_42, var29_126);
                                    var26_112 = Math.max(var23_97 * 1000, var26_112);
                                    if (var11_36) {
                                        var23_97 = Math.max(var22_91, var25_108);
                                        var20_72 = Math.max(var10_29, var19_70);
                                        var24_100 = Math.max(var24_100, var18_66);
                                    } else {
                                        var20_72 = (int)Math.sqrt(var12_42 * 2);
                                        var23_97 = Math.max(var20_72, var25_108);
                                        var20_72 = Math.max(var20_72, var19_70);
                                        var24_100 = Math.max(Math.max(var24_100, 60), var18_66);
                                    }
                                    var18_66 = var24_100;
                                    var25_108 = var23_97;
                                    var19_70 = var20_72;
                                    var27_119 = var9_23;
                                }
                                this.applyMacroBlockLimits(var25_108, var19_70, var29_126, var13_49, 16, 16, 1, 1);
                                this.mFrameRateRange = this.mFrameRateRange.intersect(12, var18_66);
                                var23_97 = var26_112;
                                var20_72 = var27_119;
                                var1_1 = var17_63;
                                break block178;
                            }
                            for (var25_109 = 0; var25_109 < var29_127; var11_37 &= -5, ++var25_109) {
                                var21_82 = var8_20 /* !! */ [var25_109];
                                var10_30 = 0;
                                var12_43 = var16_61;
                                var27_120 = var15_57;
                                var26_113 = 0;
                                var20_72 = var21_82.level;
                                if (var20_72 != 1) {
                                    if (var20_72 != 2) {
                                        if (var20_72 != 4) {
                                            if (var20_72 != 8) {
                                                if (var20_72 != 16) {
                                                    if (var20_72 != 32) {
                                                        if (var20_72 != 64) {
                                                            if (var20_72 != 128) {
                                                                var6_6 = new StringBuilder();
                                                                var6_6.append((String)var5_5);
                                                                var6_6.append(var21_82.profile);
                                                                var6_6.append(var7_10);
                                                                var6_6.append(var21_82.level);
                                                                var6_6.append((String)var28_124);
                                                                var6_6.append(var2_2);
                                                                Log.w((String)var1_1, var6_6.toString());
                                                                var11_37 |= 1;
                                                                var20_72 = 0;
                                                                var22_92 = 0;
                                                                var23_97 = 0;
                                                                var24_101 = 0;
                                                            } else {
                                                                var12_43 = 1;
                                                                var27_120 = 1;
                                                                var9_24 = 4;
                                                                var10_30 = 256;
                                                                var20_72 = 45 * 36 * 50;
                                                                var22_92 = 45;
                                                                var23_97 = 36;
                                                                var24_101 = 60;
                                                            }
                                                        } else {
                                                            var12_43 = 1;
                                                            var27_120 = 1;
                                                            var9_24 = 4;
                                                            var10_30 = 128;
                                                            var20_72 = 45 * 18 * 50;
                                                            var22_92 = 45;
                                                            var23_97 = 18;
                                                            var24_101 = 60;
                                                        }
                                                    } else {
                                                        var12_43 = 1;
                                                        var27_120 = 1;
                                                        var9_24 = 4;
                                                        var10_30 = 64;
                                                        var20_72 = 22 * 18 * 50;
                                                        var22_92 = 22;
                                                        var23_97 = 18;
                                                        var24_101 = 60;
                                                    }
                                                } else {
                                                    var20_72 = var21_82.profile != 1 && var21_82.profile != 4 ? 0 : 1;
                                                    var26_113 = var20_72;
                                                    if (var26_113 == 0) {
                                                        var12_43 = 1;
                                                        var27_120 = 1;
                                                        var9_24 = 4;
                                                    }
                                                    var10_30 = 2;
                                                    var20_72 = 11 * 9 * 15;
                                                    var22_92 = 11;
                                                    var23_97 = 9;
                                                    var24_101 = 15;
                                                }
                                            } else {
                                                var26_113 = 1;
                                                var10_30 = 32;
                                                var20_72 = 22 * 18 * 30;
                                                var22_92 = 22;
                                                var23_97 = 18;
                                                var24_101 = 30;
                                            }
                                        } else {
                                            var26_113 = 1;
                                            var10_30 = 6;
                                            var20_72 = 22 * 18 * 30;
                                            var22_92 = 22;
                                            var23_97 = 18;
                                            var24_101 = 30;
                                        }
                                    } else {
                                        var26_113 = 1;
                                        var10_30 = 2;
                                        var20_72 = 22 * 18 * 15;
                                        var22_92 = 22;
                                        var23_97 = 18;
                                        var24_101 = 30;
                                    }
                                } else {
                                    var26_113 = 1;
                                    var10_30 = 1;
                                    var20_72 = 11 * 9 * 15;
                                    var22_92 = 11;
                                    var23_97 = 9;
                                    var24_101 = 15;
                                }
                                var33_132 = var21_82.profile;
                                if (var33_132 != 1 && var33_132 != 2 && var33_132 != 4 && var33_132 != 8 && var33_132 != 16 && var33_132 != 32 && var33_132 != 64 && var33_132 != 128 && var33_132 != 256) {
                                    var6_6 = new StringBuilder();
                                    var6_6.append((String)var4_4);
                                    var6_6.append(var21_82.profile);
                                    var6_6.append((String)var28_124);
                                    var6_6.append(var2_2);
                                    Log.w((String)var1_1, var6_6.toString());
                                    var11_37 |= 1;
                                }
                                if (var26_113 != 0) {
                                    var27_120 = 11;
                                    var12_43 = 9;
                                } else {
                                    this.mAllowMbOverride = true;
                                    var26_113 = var12_43;
                                    var12_43 = var27_120;
                                    var27_120 = var26_113;
                                }
                                var13_50 = Math.max((long)var20_72, var13_50);
                                var31_130 = Math.max(var22_92 * var23_97, var31_130);
                                var18_67 = Math.max(64000 * var10_30, var18_67);
                                var32_131 = Math.max(var22_92, var32_131);
                                var30_129 = Math.max(var23_97, var30_129);
                                var19_71 = Math.max(var24_101, var19_71);
                                var16_61 = Math.min(var27_120, var16_61);
                                var15_57 = Math.min(var12_43, var15_57);
                            }
                            if (!this.mAllowMbOverride) {
                                this.mBlockAspectRatioRange = Range.create(new Rational(11, 9), new Rational(11, 9));
                            }
                            this.applyMacroBlockLimits(var16_61, var15_57, var32_131, var30_129, var31_130, var13_50, 16, 16, var9_24, var9_24);
                            this.mFrameRateRange = Range.create(var17_63, var19_71);
                            var23_97 = var18_67;
                            var20_72 = var11_37;
                            var1_1 = var17_63;
                            break block178;
                        }
                        for (var9_25 = 0; var9_25 < var12_44; var22_94 &= -5, ++var9_25) {
                            var21_84 = var1_1[var9_25];
                            var20_72 = var21_84.level;
                            if (var20_72 != 1) {
                                if (var20_72 != 2) {
                                    switch (var20_72) {
                                        default: {
                                            var6_6 = new StringBuilder();
                                            var6_6.append(var7_13);
                                            var6_6.append(var21_84.level);
                                            var6_6.append((String)var28_124);
                                            var6_6.append(var2_2);
                                            Log.w(var8_19, var6_6.toString());
                                            var22_94 |= 1;
                                            var23_97 = 0;
                                            var24_103 = 0;
                                            var13_51 = 0L;
                                            var20_72 = 0;
                                            ** break;
                                        }
                                        case 8192: {
                                            var23_97 = 35651584;
                                            var24_103 = 480000;
                                            var13_51 = 4706009088L;
                                            var20_72 = 16832;
                                            ** break;
                                        }
                                        case 4096: {
                                            var23_97 = 35651584;
                                            var24_103 = 240000;
                                            var13_51 = 2353004544L;
                                            var20_72 = 16832;
                                            ** break;
                                        }
                                        case 2048: {
                                            var23_97 = 35651584;
                                            var24_103 = 180000;
                                            var13_51 = 1176502272L;
                                            var20_72 = 16832;
                                            ** break;
                                        }
                                        case 1024: {
                                            var23_97 = 8912896;
                                            var24_103 = 180000;
                                            var13_51 = 1176502272L;
                                            var20_72 = 8384;
                                            ** break;
                                        }
                                        case 512: {
                                            var23_97 = 8912896;
                                            var24_103 = 120000;
                                            var13_51 = 588251136L;
                                            var20_72 = 8384;
                                            ** break;
                                        }
                                        case 256: {
                                            var23_97 = 8912896;
                                            var24_103 = 60000;
                                            var13_51 = 311951360L;
                                            var20_72 = 8384;
                                            ** break;
                                        }
                                        case 128: {
                                            var23_97 = 2228224;
                                            var24_103 = 30000;
                                            var13_51 = 0x9900000L;
                                            var20_72 = 4160;
                                            ** break;
                                        }
                                        case 64: {
                                            var23_97 = 2228224;
                                            var24_103 = 18000;
                                            var13_51 = 83558400L;
                                            var20_72 = 4160;
                                            ** break;
                                        }
                                        case 32: {
                                            var23_97 = 983040;
                                            var24_103 = 12000;
                                            var13_51 = 36864000L;
                                            var20_72 = 2752;
                                            ** break;
                                        }
                                        case 16: {
                                            var23_97 = 552960;
                                            var24_103 = 7200;
                                            var13_51 = 20736000L;
                                            var20_72 = 2048;
                                            ** break;
                                        }
                                        case 8: {
                                            var23_97 = 245760;
                                            var24_103 = 3600;
                                            var13_51 = 9216000L;
                                            var20_72 = 1344;
                                            ** break;
                                        }
                                        case 4: 
                                    }
                                    var23_97 = 122880;
                                    var24_103 = 1800;
                                    var13_51 = 4608000L;
                                    var20_72 = 960;
                                    ** break;
lbl1007: // 13 sources:
                                } else {
                                    var23_97 = 73728;
                                    var24_103 = 800;
                                    var13_51 = 2764800L;
                                    var20_72 = 768;
                                }
                            } else {
                                var23_97 = 36864;
                                var24_103 = 200;
                                var13_51 = 829440L;
                                var20_72 = 512;
                            }
                            var11_38 = var21_84.profile;
                            if (var11_38 != 1 && var11_38 != 2 && var11_38 != 4 && var11_38 != 8 && var11_38 != 4096 && var11_38 != 8192 && var11_38 != 16384 && var11_38 != 32768) {
                                var6_6 = new StringBuilder();
                                var6_6.append((String)var4_4);
                                var6_6.append(var21_84.profile);
                                var6_6.append((String)var28_124);
                                var6_6.append(var2_2);
                                Log.w(var8_19, var6_6.toString());
                                var22_94 |= 1;
                            }
                            var34_133 = Math.max(var13_51, var34_133);
                            var26_114 = Math.max(var23_97, var26_114);
                            var10_31 = Math.max(var24_103 * 1000, var10_31);
                            var27_121 = Math.max(var20_72, var27_121);
                        }
                        var1_1 = var17_63;
                        var20_72 = Utils.divUp(var27_121, 8);
                        this.applyMacroBlockLimits(var20_72, var20_72, Utils.divUp(var26_114, 64), Utils.divUp(var34_133, 64L), 8, 8, 1, 1);
                        var23_97 = var10_31;
                        var20_72 = var22_94;
                        break block178;
                    }
                    for (var27_122 = 0; var27_122 < var12_45; var24_104 &= -5, ++var27_122) {
                        var21_86 = var1_1[var27_122];
                        var20_72 = 0;
                        var23_97 = 0;
                        var26_115 = var21_86.level;
                        if (var26_115 != 1 && var26_115 != 2) {
                            switch (var26_115) {
                                default: {
                                    var6_6 = new StringBuilder();
                                    var6_6.append(var7_13);
                                    var6_6.append(var21_86.level);
                                    var6_6.append((String)var28_124);
                                    var6_6.append(var2_2);
                                    Log.w(var8_19, var6_6.toString());
                                    var24_104 |= 1;
                                    var36_135 = 0.0;
                                    ** break;
                                }
                                case 33554432: {
                                    var36_135 = 120.0;
                                    var20_72 = 35651584;
                                    var23_97 = 800000;
                                    ** break;
                                }
                                case 16777216: {
                                    var36_135 = 120.0;
                                    var20_72 = 35651584;
                                    var23_97 = 240000;
                                    ** break;
                                }
                                case 8388608: {
                                    var36_135 = 60.0;
                                    var20_72 = 35651584;
                                    var23_97 = 480000;
                                    ** break;
                                }
                                case 4194304: {
                                    var36_135 = 60.0;
                                    var20_72 = 35651584;
                                    var23_97 = 120000;
                                    ** break;
                                }
                                case 2097152: {
                                    var36_135 = 30.0;
                                    var20_72 = 35651584;
                                    var23_97 = 240000;
                                    ** break;
                                }
                                case 1048576: {
                                    var36_135 = 30.0;
                                    var20_72 = 35651584;
                                    var23_97 = 60000;
                                    ** break;
                                }
                                case 524288: {
                                    var36_135 = 120.0;
                                    var20_72 = 8912896;
                                    var23_97 = 240000;
                                    ** break;
                                }
                                case 262144: {
                                    var36_135 = 120.0;
                                    var20_72 = 8912896;
                                    var23_97 = 60000;
                                    ** break;
                                }
                                case 131072: {
                                    var36_135 = 60.0;
                                    var20_72 = 8912896;
                                    var23_97 = 160000;
                                    ** break;
                                }
                                case 65536: {
                                    var36_135 = 60.0;
                                    var20_72 = 8912896;
                                    var23_97 = 40000;
                                    ** break;
                                }
                                case 32768: {
                                    var36_135 = 30.0;
                                    var20_72 = 8912896;
                                    var23_97 = 100000;
                                    ** break;
                                }
                                case 16384: {
                                    var36_135 = 30.0;
                                    var20_72 = 8912896;
                                    var23_97 = 25000;
                                    ** break;
                                }
                                case 8192: {
                                    var36_135 = 60.0;
                                    var20_72 = 2228224;
                                    var23_97 = 50000;
                                    ** break;
                                }
                                case 4096: {
                                    var36_135 = 60.0;
                                    var20_72 = 2228224;
                                    var23_97 = 20000;
                                    ** break;
                                }
                                case 2048: {
                                    var36_135 = 30.0;
                                    var20_72 = 2228224;
                                    var23_97 = 30000;
                                    ** break;
                                }
                                case 1024: {
                                    var36_135 = 30.0;
                                    var20_72 = 2228224;
                                    var23_97 = 12000;
                                    ** break;
                                }
                                case 256: 
                                case 512: {
                                    var36_135 = 33.75;
                                    var20_72 = 983040;
                                    var23_97 = 10000;
                                    ** break;
                                }
                                case 64: 
                                case 128: {
                                    var36_135 = 30.0;
                                    var20_72 = 552960;
                                    var23_97 = 6000;
                                    ** break;
                                }
                                case 16: 
                                case 32: {
                                    var36_135 = 30.0;
                                    var20_72 = 245760;
                                    var23_97 = 3000;
                                    ** break;
                                }
                                case 4: 
                                case 8: 
                            }
                            var36_135 = 30.0;
                            var20_72 = 122880;
                            var23_97 = 1500;
                            ** break;
lbl1165: // 21 sources:
                        } else {
                            var36_135 = 15.0;
                            var20_72 = 36864;
                            var23_97 = 128;
                        }
                        if ((var26_115 = var21_86.profile) != 1 && var26_115 != 2 && var26_115 != 4 && var26_115 != 4096 && var26_115 != 8192) {
                            var6_6 = new StringBuilder();
                            var6_6.append((String)var4_4);
                            var6_6.append(var21_86.profile);
                            var6_6.append((String)var28_124);
                            var6_6.append(var2_2);
                            Log.w(var8_19, var6_6.toString());
                            var24_104 |= 1;
                        }
                        var13_52 = Math.max((long)((int)((double)(var20_72 >>= 6) * var36_135)), var13_52);
                        var10_32 = Math.max(var20_72, var10_32);
                        var22_95 = Math.max(var23_97 * 1000, var22_95);
                    }
                    var20_72 = (int)Math.sqrt(var10_32 * 8);
                    var23_97 = var22_95;
                    this.applyMacroBlockLimits(var20_72, var20_72, var10_32, var13_52, 8, 8, 1, 1);
                    var20_72 = var24_104;
                    var1_1 = var17_63;
                    break block178;
                }
                for (var9_26 = 0; var9_26 < var12_46; var22_96 &= -5, ++var9_26) {
                    var21_88 = var1_1[var9_26];
                    var20_72 = var21_88.level;
                    if (var20_72 != 1) {
                        if (var20_72 != 2) {
                            switch (var20_72) {
                                default: {
                                    var6_6 = new StringBuilder();
                                    var6_6.append(var7_13);
                                    var6_6.append(var21_88.level);
                                    var6_6.append((String)var28_124);
                                    var6_6.append(var2_2);
                                    Log.w(var8_19, var6_6.toString());
                                    var22_96 |= 1;
                                    var20_72 = 0;
                                    var13_53 = 0L;
                                    var24_105 = 0;
                                    var23_97 = 0;
                                    ** break;
                                }
                                case 524288: {
                                    var20_72 = 16384;
                                    var24_105 = 160000;
                                    var23_97 = 35651584;
                                    var13_53 = 4706009088L;
                                    ** break;
                                }
                                case 262144: {
                                    var20_72 = 16384;
                                    var24_105 = 160000;
                                    var23_97 = 35651584;
                                    var13_53 = 4379443200L;
                                    ** break;
                                }
                                case 131072: {
                                    var20_72 = 16384;
                                    var24_105 = 100000;
                                    var23_97 = 35651584;
                                    var13_53 = 2189721600L;
                                    ** break;
                                }
                                case 65536: {
                                    var20_72 = 16384;
                                    var24_105 = 60000;
                                    var23_97 = 35651584;
                                    var13_53 = 1176502272L;
                                    ** break;
                                }
                                case 32768: {
                                    var20_72 = 8192;
                                    var24_105 = 60000;
                                    var23_97 = 8912896;
                                    var13_53 = 1176502272L;
                                    ** break;
                                }
                                case 16384: {
                                    var20_72 = 8192;
                                    var24_105 = 60000;
                                    var23_97 = 8912896;
                                    var13_53 = 1094860800L;
                                    ** break;
                                }
                                case 8192: {
                                    var20_72 = 8192;
                                    var24_105 = 40000;
                                    var23_97 = 8912896;
                                    var13_53 = 547430400L;
                                    ** break;
                                }
                                case 4096: {
                                    var20_72 = 8192;
                                    var24_105 = 30000;
                                    var23_97 = 8912896;
                                    var13_53 = 273715200L;
                                    ** break;
                                }
                                case 512: 
                                case 1024: 
                                case 2048: {
                                    var20_72 = 6144;
                                    var24_105 = 20000;
                                    var23_97 = 2359296;
                                    var13_53 = 155713536L;
                                    ** break;
                                }
                                case 256: {
                                    var20_72 = 6144;
                                    var24_105 = 12000;
                                    var23_97 = 2359296;
                                    var13_53 = 77856768L;
                                    ** break;
                                }
                                case 32: 
                                case 64: 
                                case 128: {
                                    var20_72 = 5504;
                                    var24_105 = 10000;
                                    var23_97 = 1065024;
                                    var13_53 = 39938400L;
                                    ** break;
                                }
                                case 16: {
                                    var20_72 = 4352;
                                    var24_105 = 6000;
                                    var23_97 = 665856;
                                    var13_53 = 24969600L;
                                    ** break;
                                }
                                case 4: 
                                case 8: 
                            }
                        }
                        var20_72 = 2816;
                        var24_105 = 3000;
                        var23_97 = 278784;
                        var13_53 = 10454400L;
                        ** break;
lbl1295: // 14 sources:
                    } else {
                        var20_72 = 2048;
                        var24_105 = 1500;
                        var23_97 = 147456;
                        var13_53 = 5529600L;
                    }
                    if ((var11_39 = var21_88.profile) != 1 && var11_39 != 2 && var11_39 != 4096 && var11_39 != 8192) {
                        var6_6 = new StringBuilder();
                        var6_6.append((String)var4_4);
                        var6_6.append(var21_88.profile);
                        var6_6.append((String)var28_124);
                        var6_6.append(var2_2);
                        Log.w(var8_19, var6_6.toString());
                        var22_96 |= 1;
                    }
                    var34_134 = Math.max(var13_53, var34_134);
                    var27_123 = Math.max(var23_97, var27_123);
                    var10_33 = Math.max(var24_105 * 1000, var10_33);
                    var26_116 = Math.max(var20_72, var26_116);
                }
                var20_72 = Utils.divUp(var26_116, 8);
                this.applyMacroBlockLimits(var20_72, var20_72, Utils.divUp(var27_123, 64), Utils.divUp(var34_134, 64L), 8, 8, 1, 1);
                var23_97 = var10_33;
                var20_72 = var22_96;
                var1_1 = var17_63;
            }
            this.mBitrateRange = Range.create(var1_1, var23_97);
            var1_1 = this.mParent;
            var1_1.mError |= var20_72;
        }

        private void applyMacroBlockLimits(int n, int n2, int n3, int n4, int n5, long l, int n6, int n7, int n8, int n9) {
            this.applyAlignment(n8, n9);
            this.applyBlockLimits(n6, n7, Range.create(1, n5), Range.create(1L, l), Range.create(new Rational(1, n4), new Rational(n3, 1)));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Utils.divUp(n, this.mBlockWidth / n6), n3 / (this.mBlockWidth / n6));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Utils.divUp(n2, this.mBlockHeight / n7), n4 / (this.mBlockHeight / n7));
        }

        private void applyMacroBlockLimits(int n, int n2, int n3, long l, int n4, int n5, int n6, int n7) {
            this.applyMacroBlockLimits(1, 1, n, n2, n3, l, n4, n5, n6, n7);
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public static VideoCapabilities create(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            VideoCapabilities videoCapabilities = new VideoCapabilities();
            videoCapabilities.init(mediaFormat, codecCapabilities);
            return videoCapabilities;
        }

        public static int equivalentVP9Level(MediaFormat range) {
            range = ((MediaFormat)((Object)range)).getMap();
            Object object = Utils.parseSize(range.get("block-size"), new Size(8, 8));
            int n = ((Size)object).getWidth() * ((Size)object).getHeight();
            object = Utils.parseIntRange(range.get("block-count-range"), null);
            int n2 = 0;
            int n3 = object == null ? 0 : (Integer)((Range)object).getUpper() * n;
            object = Utils.parseLongRange(range.get("blocks-per-second-range"), null);
            long l = object == null ? 0L : (long)n * ((Range)object).getUpper();
            object = VideoCapabilities.parseWidthHeightRanges(range.get("size-range"));
            n = object == null ? 0 : Math.max((Integer)((Range)((Pair)object).first).getUpper(), (Integer)((Range)((Pair)object).second).getUpper());
            if ((range = Utils.parseIntRange(range.get("bitrate-range"), null)) != null) {
                n2 = Utils.divUp(range.getUpper(), 1000);
            }
            if (l <= 829440L && n3 <= 36864 && n2 <= 200 && n <= 512) {
                return 1;
            }
            if (l <= 2764800L && n3 <= 73728 && n2 <= 800 && n <= 768) {
                return 2;
            }
            if (l <= 4608000L && n3 <= 122880 && n2 <= 1800 && n <= 960) {
                return 4;
            }
            if (l <= 9216000L && n3 <= 245760 && n2 <= 3600 && n <= 1344) {
                return 8;
            }
            if (l <= 20736000L && n3 <= 552960 && n2 <= 7200 && n <= 2048) {
                return 16;
            }
            if (l <= 36864000L && n3 <= 983040 && n2 <= 12000 && n <= 2752) {
                return 32;
            }
            if (l <= 83558400L && n3 <= 2228224 && n2 <= 18000 && n <= 4160) {
                return 64;
            }
            if (l <= 0x9900000L && n3 <= 2228224 && n2 <= 30000 && n <= 4160) {
                return 128;
            }
            if (l <= 311951360L && n3 <= 8912896 && n2 <= 60000 && n <= 8384) {
                return 256;
            }
            if (l <= 588251136L && n3 <= 8912896 && n2 <= 120000 && n <= 8384) {
                return 512;
            }
            if (l <= 1176502272L && n3 <= 8912896 && n2 <= 180000 && n <= 8384) {
                return 1024;
            }
            if (l <= 1176502272L && n3 <= 35651584 && n2 <= 180000 && n <= 16832) {
                return 2048;
            }
            if (l <= 2353004544L && n3 <= 35651584 && n2 <= 240000 && n <= 16832) {
                return 4096;
            }
            if (l <= 4706009088L && n3 <= 35651584 && n2 <= 480000 && n <= 16832) {
                return 8192;
            }
            return 8192;
        }

        private Range<Double> estimateFrameRatesFor(int n, int n2) {
            Object object = this.findClosestSize(n, n2);
            Range<Long> range = this.mMeasuredFrameRates.get(object);
            object = (double)this.getBlockCount(((Size)object).getWidth(), ((Size)object).getHeight()) / (double)Math.max(this.getBlockCount(n, n2), 1);
            return Range.create((double)range.getLower().longValue() * (Double)object, (double)range.getUpper().longValue() * (Double)object);
        }

        private Size findClosestSize(int n, int n2) {
            int n3 = this.getBlockCount(n, n2);
            Size size = null;
            n = Integer.MAX_VALUE;
            for (Size size2 : this.mMeasuredFrameRates.keySet()) {
                int n4 = Math.abs(n3 - this.getBlockCount(size2.getWidth(), size2.getHeight()));
                n2 = n;
                if (n4 < n) {
                    n2 = n4;
                    size = size2;
                }
                n = n2;
            }
            return size;
        }

        private int getBlockCount(int n, int n2) {
            return Utils.divUp(n, this.mBlockWidth) * Utils.divUp(n2, this.mBlockHeight);
        }

        private Map<Size, Range<Long>> getMeasuredFrameRates(Map<String, Object> map) {
            HashMap<Size, Range<Long>> hashMap = new HashMap<Size, Range<Long>>();
            for (String string2 : map.keySet()) {
                Range<Long> range;
                if (!string2.startsWith("measured-frame-rate-")) continue;
                string2.substring("measured-frame-rate-".length());
                Object object = string2.split("-");
                if (((String[])object).length != 5 || (object = Utils.parseSize(object[3], null)) == null || ((Size)object).getWidth() * ((Size)object).getHeight() <= 0 || (range = Utils.parseLongRange(map.get(string2), null)) == null || range.getLower() < 0L || range.getUpper() < 0L) continue;
                hashMap.put((Size)object, range);
            }
            return hashMap;
        }

        private List<PerformancePoint> getPerformancePoints(Map<String, Object> map) {
            Vector<Object> vector = new Vector<Object>();
            String string2 = "performance-point-";
            Set<String> set = map.keySet();
            for (String string3 : set) {
                Range<Long> range;
                if (!string3.startsWith("performance-point-")) continue;
                if (string3.substring("performance-point-".length()).equals("none") && vector.size() == 0) {
                    return Collections.unmodifiableList(vector);
                }
                Object object = string3.split("-");
                if (((String[])object).length != 4 || (object = Utils.parseSize(object[2], null)) == null || ((Size)object).getWidth() * ((Size)object).getHeight() <= 0 || (range = Utils.parseLongRange(map.get(string3), null)) == null || range.getLower() < 0L || range.getUpper() < 0L) continue;
                PerformancePoint performancePoint = new PerformancePoint(((Size)object).getWidth(), ((Size)object).getHeight(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                object = new PerformancePoint(((Size)object).getHeight(), ((Size)object).getWidth(), range.getLower().intValue(), range.getUpper().intValue(), new Size(this.mBlockWidth, this.mBlockHeight));
                vector.add(performancePoint);
                if (performancePoint.covers((PerformancePoint)object)) continue;
                vector.add(object);
            }
            if (vector.size() == 0) {
                return null;
            }
            vector.sort((Comparator<Object>)_$$Lambda$MediaCodecInfo$VideoCapabilities$DpgwEn_gVFZT9EtP3qcxpiA2G0M.INSTANCE);
            return Collections.unmodifiableList(vector);
        }

        private void init(MediaFormat mediaFormat, CodecCapabilities codecCapabilities) {
            this.mParent = codecCapabilities;
            this.initWithPlatformLimits();
            this.applyLevelLimits();
            this.parseFromInfo(mediaFormat);
            this.updateLimits();
        }

        private void initWithPlatformLimits() {
            this.mBitrateRange = BITRATE_RANGE;
            this.mWidthRange = SIZE_RANGE;
            this.mHeightRange = SIZE_RANGE;
            this.mFrameRateRange = FRAME_RATE_RANGE;
            this.mHorizontalBlockRange = SIZE_RANGE;
            this.mVerticalBlockRange = SIZE_RANGE;
            this.mBlockCountRange = POSITIVE_INTEGERS;
            this.mBlocksPerSecondRange = POSITIVE_LONGS;
            this.mBlockAspectRatioRange = POSITIVE_RATIONALS;
            this.mAspectRatioRange = POSITIVE_RATIONALS;
            this.mWidthAlignment = 2;
            this.mHeightAlignment = 2;
            this.mBlockWidth = 2;
            this.mBlockHeight = 2;
            this.mSmallerDimensionUpperLimit = (Integer)SIZE_RANGE.getUpper();
        }

        static /* synthetic */ int lambda$getPerformancePoints$0(PerformancePoint performancePoint, PerformancePoint performancePoint2) {
            int n = performancePoint.getMaxMacroBlocks();
            int n2 = performancePoint2.getMaxMacroBlocks();
            int n3 = -1;
            if (n != n2) {
                if (performancePoint.getMaxMacroBlocks() >= performancePoint2.getMaxMacroBlocks()) {
                    n3 = 1;
                }
            } else if (performancePoint.getMaxMacroBlockRate() != performancePoint2.getMaxMacroBlockRate()) {
                if (performancePoint.getMaxMacroBlockRate() >= performancePoint2.getMaxMacroBlockRate()) {
                    n3 = 1;
                }
            } else if (performancePoint.getMaxFrameRate() != performancePoint2.getMaxFrameRate()) {
                if (performancePoint.getMaxFrameRate() >= performancePoint2.getMaxFrameRate()) {
                    n3 = 1;
                }
            } else {
                n3 = 0;
            }
            return -n3;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void parseFromInfo(MediaFormat var1_1) {
            var2_2 = var1_1.getMap();
            var3_5 = new Size(this.mBlockWidth, this.mBlockHeight);
            var4_7 = new Size(this.mWidthAlignment, this.mHeightAlignment);
            var5_8 = null;
            var1_1 = null;
            var6_9 = Utils.parseSize(var2_2.get("block-size"), (Size)var3_5);
            var7_10 = Utils.parseSize(var2_2.get("alignment"), (Size)var4_7);
            var8_11 = Utils.parseIntRange(var2_2.get("block-count-range"), null);
            var9_12 = Utils.parseLongRange(var2_2.get("blocks-per-second-range"), null);
            this.mMeasuredFrameRates = this.getMeasuredFrameRates((Map<String, Object>)var2_2);
            this.mPerformancePoints = this.getPerformancePoints((Map<String, Object>)var2_2);
            var4_7 = VideoCapabilities.parseWidthHeightRanges(var2_2.get("size-range"));
            if (var4_7 != null) {
                var5_8 = (Range<Object>)var4_7.first;
                var1_1 = (Range)var4_7.second;
            }
            if (!var2_2.containsKey("feature-can-swap-width-height")) ** GOTO lbl28
            if (var5_8 != null) {
                this.mSmallerDimensionUpperLimit = Math.min((Integer)var5_8.getUpper(), (Integer)var1_1.getUpper());
                var5_8 = var1_1 = var5_8.extend((Object)var1_1);
                var4_7 = var1_1;
            } else {
                Log.w("VideoCapabilities", "feature can-swap-width-height is best used with size-range");
                this.mSmallerDimensionUpperLimit = Math.min(this.mWidthRange.getUpper(), this.mHeightRange.getUpper());
                var4_7 = this.mWidthRange.extend((Integer)this.mHeightRange);
                this.mHeightRange = var4_7;
                this.mWidthRange = var4_7;
lbl28: // 2 sources:
                var4_7 = var1_1;
            }
            var10_13 = Utils.parseRationalRange(var2_2.get("block-aspect-ratio-range"), null);
            var11_14 = Utils.parseRationalRange(var2_2.get("pixel-aspect-ratio-range"), null);
            var1_1 = Utils.parseIntRange(var2_2.get("frame-rate-range"), null);
            if (var1_1 != null) {
                try {
                    var3_5 = var1_1.intersect(MediaCodecInfo.access$500());
                    var1_1 = var3_5;
                }
                catch (IllegalArgumentException var3_6) {
                    var3_5 = new StringBuilder();
                    var3_5.append("frame rate range (");
                    var3_5.append(var1_1);
                    var3_5.append(") is out of limits: ");
                    var3_5.append(MediaCodecInfo.access$500());
                    Log.w("VideoCapabilities", var3_5.toString());
                    var1_1 = null;
                }
            }
            var3_5 = var4_7;
            var4_7 = Utils.parseIntRange(var2_2.get("bitrate-range"), null);
            if (var4_7 != null) {
                try {
                    var4_7 = var2_2 = var4_7.intersect(MediaCodecInfo.access$300());
                }
                catch (IllegalArgumentException var2_3) {
                    var2_4 = new StringBuilder();
                    var2_4.append("bitrate range (");
                    var2_4.append(var4_7);
                    var2_4.append(") is out of limits: ");
                    var2_4.append(MediaCodecInfo.access$300());
                    Log.w("VideoCapabilities", var2_4.toString());
                    var4_7 = null;
                }
            }
            MediaCodecInfo.access$200(var6_9.getWidth(), "block-size width must be power of two");
            MediaCodecInfo.access$200(var6_9.getHeight(), "block-size height must be power of two");
            MediaCodecInfo.access$200(var7_10.getWidth(), "alignment width must be power of two");
            MediaCodecInfo.access$200(var7_10.getHeight(), "alignment height must be power of two");
            this.applyMacroBlockLimits(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, var6_9.getWidth(), var6_9.getHeight(), var7_10.getWidth(), var7_10.getHeight());
            if ((this.mParent.mError & 2) == 0 && !this.mAllowMbOverride) {
                if (var5_8 != null) {
                    this.mWidthRange = this.mWidthRange.intersect(var5_8);
                }
                if (var3_5 != null) {
                    this.mHeightRange = this.mHeightRange.intersect((Range<Integer>)var3_5);
                }
                if (var8_11 != null) {
                    this.mBlockCountRange = this.mBlockCountRange.intersect(Utils.factorRange(var8_11, this.mBlockWidth * this.mBlockHeight / var6_9.getWidth() / var6_9.getHeight()));
                }
                if (var9_12 != null) {
                    this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect(Utils.factorRange(var9_12, (long)(this.mBlockWidth * this.mBlockHeight / var6_9.getWidth() / var6_9.getHeight())));
                }
                if (var11_14 != null) {
                    this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(Utils.scaleRange(var11_14, this.mBlockHeight / var6_9.getHeight(), this.mBlockWidth / var6_9.getWidth()));
                }
                if (var10_13 != null) {
                    this.mAspectRatioRange = this.mAspectRatioRange.intersect(var10_13);
                }
                if (var1_1 != null) {
                    this.mFrameRateRange = this.mFrameRateRange.intersect(var1_1);
                }
                if (var4_7 != null) {
                    this.mBitrateRange = this.mBitrateRange.intersect((Range<Integer>)var4_7);
                }
            } else {
                if (var5_8 != null) {
                    this.mWidthRange = MediaCodecInfo.access$400().intersect(var5_8);
                }
                if (var3_5 != null) {
                    this.mHeightRange = MediaCodecInfo.access$400().intersect(var3_5);
                }
                if (var8_11 != null) {
                    this.mBlockCountRange = MediaCodecInfo.access$000().intersect(Utils.factorRange(var8_11, this.mBlockWidth * this.mBlockHeight / var6_9.getWidth() / var6_9.getHeight()));
                }
                if (var9_12 != null) {
                    this.mBlocksPerSecondRange = MediaCodecInfo.access$600().intersect(Utils.factorRange(var9_12, (long)(this.mBlockWidth * this.mBlockHeight / var6_9.getWidth() / var6_9.getHeight())));
                }
                if (var11_14 != null) {
                    this.mBlockAspectRatioRange = MediaCodecInfo.access$700().intersect(Utils.scaleRange(var11_14, this.mBlockHeight / var6_9.getHeight(), this.mBlockWidth / var6_9.getWidth()));
                }
                if (var10_13 != null) {
                    this.mAspectRatioRange = MediaCodecInfo.access$700().intersect(var10_13);
                }
                if (var1_1 != null) {
                    this.mFrameRateRange = MediaCodecInfo.access$500().intersect(var1_1);
                }
                if (var4_7 != null) {
                    this.mBitrateRange = (this.mParent.mError & 2) != 0 ? MediaCodecInfo.access$300().intersect(var4_7) : this.mBitrateRange.intersect((Range<Integer>)var4_7);
                }
            }
            this.updateLimits();
        }

        private static Pair<Range<Integer>, Range<Integer>> parseWidthHeightRanges(Object object) {
            Pair<Object, Object> pair = Utils.parseSizeRange(object);
            if (pair != null) {
                try {
                    pair = Pair.create(Range.create(((Size)pair.first).getWidth(), ((Size)pair.second).getWidth()), Range.create(((Size)pair.first).getHeight(), ((Size)pair.second).getHeight()));
                    return pair;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("could not parse size range '");
                    stringBuilder.append(object);
                    stringBuilder.append("'");
                    Log.w(TAG, stringBuilder.toString());
                }
            }
            return null;
        }

        private boolean supports(Integer n, Integer n2, Number number) {
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = bl;
            if (true) {
                bl3 = bl;
                if (n != null) {
                    bl3 = this.mWidthRange.contains(n) && n % this.mWidthAlignment == 0;
                }
            }
            bl = bl3;
            if (bl3) {
                bl = bl3;
                if (n2 != null) {
                    bl3 = this.mHeightRange.contains(n2) && n2 % this.mHeightAlignment == 0;
                    bl = bl3;
                }
            }
            bl3 = bl;
            if (bl) {
                bl3 = bl;
                if (number != null) {
                    bl3 = this.mFrameRateRange.contains((Integer)((Object)Utils.intRangeFor(number.doubleValue())));
                }
            }
            bl = bl3;
            if (bl3) {
                bl = bl3;
                if (n2 != null) {
                    bl = bl3;
                    if (n != null) {
                        boolean bl4 = Math.min(n2, n) <= this.mSmallerDimensionUpperLimit;
                        int n3 = Utils.divUp(n, this.mBlockWidth);
                        int n4 = Utils.divUp(n2, this.mBlockHeight);
                        int n5 = n3 * n4;
                        bl3 = bl4 && this.mBlockCountRange.contains(n5) && this.mBlockAspectRatioRange.contains(new Rational(n3, n4)) && this.mAspectRatioRange.contains(new Rational(n, n2)) ? bl2 : false;
                        bl = bl3;
                        if (bl3) {
                            bl = bl3;
                            if (number != null) {
                                double d = n5;
                                double d2 = number.doubleValue();
                                bl = this.mBlocksPerSecondRange.contains((Long)((Object)Utils.longRangeFor(d * d2)));
                            }
                        }
                    }
                }
            }
            return bl;
        }

        private void updateLimits() {
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Utils.factorRange(this.mWidthRange, this.mBlockWidth));
            this.mHorizontalBlockRange = this.mHorizontalBlockRange.intersect(Range.create(this.mBlockCountRange.getLower() / this.mVerticalBlockRange.getUpper(), this.mBlockCountRange.getUpper() / this.mVerticalBlockRange.getLower()));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Utils.factorRange(this.mHeightRange, this.mBlockHeight));
            this.mVerticalBlockRange = this.mVerticalBlockRange.intersect(Range.create(this.mBlockCountRange.getLower() / this.mHorizontalBlockRange.getUpper(), this.mBlockCountRange.getUpper() / this.mHorizontalBlockRange.getLower()));
            this.mBlockCountRange = this.mBlockCountRange.intersect(Range.create(this.mHorizontalBlockRange.getLower() * this.mVerticalBlockRange.getLower(), this.mHorizontalBlockRange.getUpper() * this.mVerticalBlockRange.getUpper()));
            this.mBlockAspectRatioRange = this.mBlockAspectRatioRange.intersect(new Rational(this.mHorizontalBlockRange.getLower(), this.mVerticalBlockRange.getUpper()), new Rational(this.mHorizontalBlockRange.getUpper(), this.mVerticalBlockRange.getLower()));
            this.mWidthRange = this.mWidthRange.intersect((this.mHorizontalBlockRange.getLower() - 1) * this.mBlockWidth + this.mWidthAlignment, this.mHorizontalBlockRange.getUpper() * this.mBlockWidth);
            this.mHeightRange = this.mHeightRange.intersect((this.mVerticalBlockRange.getLower() - 1) * this.mBlockHeight + this.mHeightAlignment, this.mVerticalBlockRange.getUpper() * this.mBlockHeight);
            this.mAspectRatioRange = this.mAspectRatioRange.intersect(new Rational(this.mWidthRange.getLower(), this.mHeightRange.getUpper()), new Rational(this.mWidthRange.getUpper(), this.mHeightRange.getLower()));
            this.mSmallerDimensionUpperLimit = Math.min(this.mSmallerDimensionUpperLimit, Math.min(this.mWidthRange.getUpper(), this.mHeightRange.getUpper()));
            this.mBlocksPerSecondRange = this.mBlocksPerSecondRange.intersect((long)this.mBlockCountRange.getLower().intValue() * (long)this.mFrameRateRange.getLower().intValue(), (long)this.mBlockCountRange.getUpper().intValue() * (long)this.mFrameRateRange.getUpper().intValue());
            this.mFrameRateRange = this.mFrameRateRange.intersect((int)(this.mBlocksPerSecondRange.getLower() / (long)this.mBlockCountRange.getUpper().intValue()), (int)((double)this.mBlocksPerSecondRange.getUpper().longValue() / (double)this.mBlockCountRange.getLower().intValue()));
        }

        public boolean areSizeAndRateSupported(int n, int n2, double d) {
            return this.supports(n, n2, d);
        }

        public Range<Double> getAchievableFrameRatesFor(int n, int n2) {
            if (this.supports(n, n2, null)) {
                Map<Size, Range<Long>> map = this.mMeasuredFrameRates;
                if (map != null && map.size() > 0) {
                    return this.estimateFrameRatesFor(n, n2);
                }
                Log.w(TAG, "Codec did not publish any measurement data.");
                return null;
            }
            throw new IllegalArgumentException("unsupported size");
        }

        public Range<Rational> getAspectRatioRange(boolean bl) {
            Range<Rational> range = bl ? this.mBlockAspectRatioRange : this.mAspectRatioRange;
            return range;
        }

        public Range<Integer> getBitrateRange() {
            return this.mBitrateRange;
        }

        public Range<Integer> getBlockCountRange() {
            return this.mBlockCountRange;
        }

        public Size getBlockSize() {
            return new Size(this.mBlockWidth, this.mBlockHeight);
        }

        public Range<Long> getBlocksPerSecondRange() {
            return this.mBlocksPerSecondRange;
        }

        public int getHeightAlignment() {
            return this.mHeightAlignment;
        }

        public int getSmallerDimensionUpperLimit() {
            return this.mSmallerDimensionUpperLimit;
        }

        public Range<Integer> getSupportedFrameRates() {
            return this.mFrameRateRange;
        }

        public Range<Double> getSupportedFrameRatesFor(int n, int n2) {
            Range<Integer> range = this.mHeightRange;
            if (this.supports(n, n2, null)) {
                n = Utils.divUp(n, this.mBlockWidth) * Utils.divUp(n2, this.mBlockHeight);
                return Range.create(Math.max((double)this.mBlocksPerSecondRange.getLower().longValue() / (double)n, (double)this.mFrameRateRange.getLower().intValue()), Math.min((double)this.mBlocksPerSecondRange.getUpper().longValue() / (double)n, (double)this.mFrameRateRange.getUpper().intValue()));
            }
            throw new IllegalArgumentException("unsupported size");
        }

        public Range<Integer> getSupportedHeights() {
            return this.mHeightRange;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Range<Integer> getSupportedHeightsFor(int n) {
            try {
                Range<Integer> range = this.mHeightRange;
                if (this.mWidthRange.contains(n) && n % this.mWidthAlignment == 0) {
                    Range<Integer> range2;
                    int n2 = Utils.divUp(n, this.mBlockWidth);
                    int n3 = Math.max(Utils.divUp(this.mBlockCountRange.getLower(), n2), (int)Math.ceil((double)n2 / this.mBlockAspectRatioRange.getUpper().doubleValue()));
                    n2 = Math.min(this.mBlockCountRange.getUpper() / n2, (int)((double)n2 / this.mBlockAspectRatioRange.getLower().doubleValue()));
                    range = range2 = range.intersect((n3 - 1) * this.mBlockHeight + this.mHeightAlignment, this.mBlockHeight * n2);
                    if (n > this.mSmallerDimensionUpperLimit) {
                        range = range2.intersect(1, this.mSmallerDimensionUpperLimit);
                    }
                    return range.intersect((int)Math.ceil((double)n / this.mAspectRatioRange.getUpper().doubleValue()), (int)((double)n / this.mAspectRatioRange.getLower().doubleValue()));
                }
                range = new Range<Integer>("unsupported width");
                throw range;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("could not get supported heights for ");
                stringBuilder.append(n);
                Log.v(TAG, stringBuilder.toString());
                throw new IllegalArgumentException("unsupported width");
            }
        }

        public List<PerformancePoint> getSupportedPerformancePoints() {
            return this.mPerformancePoints;
        }

        public Range<Integer> getSupportedWidths() {
            return this.mWidthRange;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Range<Integer> getSupportedWidthsFor(int n) {
            try {
                Range<Integer> range = this.mWidthRange;
                if (this.mHeightRange.contains(n) && n % this.mHeightAlignment == 0) {
                    Range<Integer> range2;
                    int n2 = Utils.divUp(n, this.mBlockHeight);
                    int n3 = Math.max(Utils.divUp(this.mBlockCountRange.getLower(), n2), (int)Math.ceil(this.mBlockAspectRatioRange.getLower().doubleValue() * (double)n2));
                    n2 = Math.min(this.mBlockCountRange.getUpper() / n2, (int)(this.mBlockAspectRatioRange.getUpper().doubleValue() * (double)n2));
                    range = range2 = range.intersect((n3 - 1) * this.mBlockWidth + this.mWidthAlignment, this.mBlockWidth * n2);
                    if (n > this.mSmallerDimensionUpperLimit) {
                        range = range2.intersect(1, this.mSmallerDimensionUpperLimit);
                    }
                    return range.intersect((int)Math.ceil(this.mAspectRatioRange.getLower().doubleValue() * (double)n), (int)(this.mAspectRatioRange.getUpper().doubleValue() * (double)n));
                }
                range = new Range<Integer>("unsupported height");
                throw range;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("could not get supported widths for ");
                stringBuilder.append(n);
                Log.v(TAG, stringBuilder.toString());
                throw new IllegalArgumentException("unsupported height");
            }
        }

        public int getWidthAlignment() {
            return this.mWidthAlignment;
        }

        public boolean isSizeSupported(int n, int n2) {
            return this.supports(n, n2, null);
        }

        public boolean supportsFormat(MediaFormat mediaFormat) {
            Map<String, Object> map = mediaFormat.getMap();
            if (!this.supports((Integer)map.get("width"), (Integer)map.get("height"), (Number)map.get("frame-rate"))) {
                return false;
            }
            return CodecCapabilities.supportsBitrate(this.mBitrateRange, mediaFormat);
        }

        public static final class PerformancePoint {
            public static final PerformancePoint FHD_100;
            public static final PerformancePoint FHD_120;
            public static final PerformancePoint FHD_200;
            public static final PerformancePoint FHD_24;
            public static final PerformancePoint FHD_240;
            public static final PerformancePoint FHD_25;
            public static final PerformancePoint FHD_30;
            public static final PerformancePoint FHD_50;
            public static final PerformancePoint FHD_60;
            public static final PerformancePoint HD_100;
            public static final PerformancePoint HD_120;
            public static final PerformancePoint HD_200;
            public static final PerformancePoint HD_24;
            public static final PerformancePoint HD_240;
            public static final PerformancePoint HD_25;
            public static final PerformancePoint HD_30;
            public static final PerformancePoint HD_50;
            public static final PerformancePoint HD_60;
            public static final PerformancePoint SD_24;
            public static final PerformancePoint SD_25;
            public static final PerformancePoint SD_30;
            public static final PerformancePoint SD_48;
            public static final PerformancePoint SD_50;
            public static final PerformancePoint SD_60;
            public static final PerformancePoint UHD_100;
            public static final PerformancePoint UHD_120;
            public static final PerformancePoint UHD_200;
            public static final PerformancePoint UHD_24;
            public static final PerformancePoint UHD_240;
            public static final PerformancePoint UHD_25;
            public static final PerformancePoint UHD_30;
            public static final PerformancePoint UHD_50;
            public static final PerformancePoint UHD_60;
            private Size mBlockSize;
            private int mHeight;
            private int mMaxFrameRate;
            private long mMaxMacroBlockRate;
            private int mWidth;

            static {
                SD_24 = new PerformancePoint(720, 480, 24);
                SD_25 = new PerformancePoint(720, 576, 25);
                SD_30 = new PerformancePoint(720, 480, 30);
                SD_48 = new PerformancePoint(720, 480, 48);
                SD_50 = new PerformancePoint(720, 576, 50);
                SD_60 = new PerformancePoint(720, 480, 60);
                HD_24 = new PerformancePoint(1280, 720, 24);
                HD_25 = new PerformancePoint(1280, 720, 25);
                HD_30 = new PerformancePoint(1280, 720, 30);
                HD_50 = new PerformancePoint(1280, 720, 50);
                HD_60 = new PerformancePoint(1280, 720, 60);
                HD_100 = new PerformancePoint(1280, 720, 100);
                HD_120 = new PerformancePoint(1280, 720, 120);
                HD_200 = new PerformancePoint(1280, 720, 200);
                HD_240 = new PerformancePoint(1280, 720, 240);
                FHD_24 = new PerformancePoint(1920, 1080, 24);
                FHD_25 = new PerformancePoint(1920, 1080, 25);
                FHD_30 = new PerformancePoint(1920, 1080, 30);
                FHD_50 = new PerformancePoint(1920, 1080, 50);
                FHD_60 = new PerformancePoint(1920, 1080, 60);
                FHD_100 = new PerformancePoint(1920, 1080, 100);
                FHD_120 = new PerformancePoint(1920, 1080, 120);
                FHD_200 = new PerformancePoint(1920, 1080, 200);
                FHD_240 = new PerformancePoint(1920, 1080, 240);
                UHD_24 = new PerformancePoint(3840, 2160, 24);
                UHD_25 = new PerformancePoint(3840, 2160, 25);
                UHD_30 = new PerformancePoint(3840, 2160, 30);
                UHD_50 = new PerformancePoint(3840, 2160, 50);
                UHD_60 = new PerformancePoint(3840, 2160, 60);
                UHD_100 = new PerformancePoint(3840, 2160, 100);
                UHD_120 = new PerformancePoint(3840, 2160, 120);
                UHD_200 = new PerformancePoint(3840, 2160, 200);
                UHD_240 = new PerformancePoint(3840, 2160, 240);
            }

            public PerformancePoint(int n, int n2, int n3) {
                this(n, n2, n3, n3, new Size(16, 16));
            }

            public PerformancePoint(int n, int n2, int n3, int n4, Size size) {
                MediaCodecInfo.checkPowerOfTwo(size.getWidth(), "block width");
                MediaCodecInfo.checkPowerOfTwo(size.getHeight(), "block height");
                this.mBlockSize = new Size(Utils.divUp(size.getWidth(), 16), Utils.divUp(size.getHeight(), 16));
                this.mWidth = (int)(Utils.divUp(Math.max(1L, (long)n), (long)Math.max(size.getWidth(), 16)) * (long)this.mBlockSize.getWidth());
                this.mHeight = (int)(Utils.divUp(Math.max(1L, (long)n2), (long)Math.max(size.getHeight(), 16)) * (long)this.mBlockSize.getHeight());
                this.mMaxFrameRate = Math.max(1, Math.max(n3, n4));
                this.mMaxMacroBlockRate = Math.max(1, n3) * this.getMaxMacroBlocks();
            }

            public PerformancePoint(PerformancePoint performancePoint, Size size) {
                this(performancePoint.mWidth * 16, performancePoint.mHeight * 16, (int)Utils.divUp(performancePoint.mMaxMacroBlockRate, (long)performancePoint.getMaxMacroBlocks()), performancePoint.mMaxFrameRate, new Size(Math.max(size.getWidth(), performancePoint.mBlockSize.getWidth() * 16), Math.max(size.getHeight(), performancePoint.mBlockSize.getHeight() * 16)));
            }

            private int align(int n, int n2) {
                return Utils.divUp(n, n2) * n2;
            }

            private void checkPowerOfTwo2(int n, String string2) {
                if (n != 0 && (n - 1 & n) == 0) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" (");
                stringBuilder.append(n);
                stringBuilder.append(") must be a power of 2");
                throw new IllegalArgumentException(stringBuilder.toString());
            }

            private Size getCommonBlockSize(PerformancePoint performancePoint) {
                return new Size(Math.max(this.mBlockSize.getWidth(), performancePoint.mBlockSize.getWidth()) * 16, Math.max(this.mBlockSize.getHeight(), performancePoint.mBlockSize.getHeight()) * 16);
            }

            private int saturateLongToInt(long l) {
                if (l < Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
                if (l > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                return (int)l;
            }

            public boolean covers(PerformancePoint performancePoint) {
                Size size = this.getCommonBlockSize(performancePoint);
                PerformancePoint performancePoint2 = new PerformancePoint(this, size);
                performancePoint = new PerformancePoint(performancePoint, size);
                boolean bl = performancePoint2.getMaxMacroBlocks() >= performancePoint.getMaxMacroBlocks() && performancePoint2.mMaxFrameRate >= performancePoint.mMaxFrameRate && performancePoint2.mMaxMacroBlockRate >= performancePoint.mMaxMacroBlockRate;
                return bl;
            }

            public boolean covers(MediaFormat mediaFormat) {
                return this.covers(new PerformancePoint(mediaFormat.getInteger("width", 0), mediaFormat.getInteger("height", 0), Math.round((float)Math.ceil(mediaFormat.getNumber("frame-rate", 0).doubleValue()))));
            }

            public boolean equals(Object object) {
                boolean bl = object instanceof PerformancePoint;
                boolean bl2 = false;
                if (bl) {
                    PerformancePoint performancePoint = (PerformancePoint)object;
                    Object object2 = this.getCommonBlockSize(performancePoint);
                    object = new PerformancePoint(this, (Size)object2);
                    object2 = new PerformancePoint(performancePoint, (Size)object2);
                    bl = bl2;
                    if (((PerformancePoint)object).getMaxMacroBlocks() == ((PerformancePoint)object2).getMaxMacroBlocks()) {
                        bl = bl2;
                        if (((PerformancePoint)object).mMaxFrameRate == ((PerformancePoint)object2).mMaxFrameRate) {
                            bl = bl2;
                            if (((PerformancePoint)object).mMaxMacroBlockRate == ((PerformancePoint)object2).mMaxMacroBlockRate) {
                                bl = true;
                            }
                        }
                    }
                    return bl;
                }
                return false;
            }

            public int getMaxFrameRate() {
                return this.mMaxFrameRate;
            }

            public long getMaxMacroBlockRate() {
                return this.mMaxMacroBlockRate;
            }

            public int getMaxMacroBlocks() {
                return this.saturateLongToInt((long)this.mWidth * (long)this.mHeight);
            }

            public int hashCode() {
                return this.mMaxFrameRate;
            }

            public String toString() {
                CharSequence charSequence;
                CharSequence charSequence2;
                block5 : {
                    int n;
                    int n2;
                    block4 : {
                        n2 = this.mBlockSize.getWidth() * 16;
                        n = this.mBlockSize.getHeight() * 16;
                        int n3 = (int)Utils.divUp(this.mMaxMacroBlockRate, (long)this.getMaxMacroBlocks());
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append(this.mWidth * 16);
                        ((StringBuilder)charSequence2).append("x");
                        ((StringBuilder)charSequence2).append(this.mHeight * 16);
                        ((StringBuilder)charSequence2).append("@");
                        ((StringBuilder)charSequence2).append(n3);
                        charSequence = ((StringBuilder)charSequence2).toString();
                        charSequence2 = charSequence;
                        if (n3 < this.mMaxFrameRate) {
                            charSequence2 = new StringBuilder();
                            ((StringBuilder)charSequence2).append((String)charSequence);
                            ((StringBuilder)charSequence2).append(", max ");
                            ((StringBuilder)charSequence2).append(this.mMaxFrameRate);
                            ((StringBuilder)charSequence2).append("fps");
                            charSequence2 = ((StringBuilder)charSequence2).toString();
                        }
                        if (n2 > 16) break block4;
                        charSequence = charSequence2;
                        if (n <= 16) break block5;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(", ");
                    ((StringBuilder)charSequence).append(n2);
                    ((StringBuilder)charSequence).append("x");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" blocks");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("PerformancePoint(");
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(")");
                return ((StringBuilder)charSequence2).toString();
            }
        }

    }

}

