/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media._$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv_csQxBIPlfc;
import android.media._$$Lambda$MediaFormat$FilteredMappedKeySet$S0dX0CM54Hgdu801GLdPbYKEcds;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MediaFormat {
    public static final int COLOR_RANGE_FULL = 1;
    public static final int COLOR_RANGE_LIMITED = 2;
    public static final int COLOR_STANDARD_BT2020 = 6;
    public static final int COLOR_STANDARD_BT601_NTSC = 4;
    public static final int COLOR_STANDARD_BT601_PAL = 2;
    public static final int COLOR_STANDARD_BT709 = 1;
    public static final int COLOR_TRANSFER_HLG = 7;
    public static final int COLOR_TRANSFER_LINEAR = 1;
    public static final int COLOR_TRANSFER_SDR_VIDEO = 3;
    public static final int COLOR_TRANSFER_ST2084 = 6;
    public static final String KEY_AAC_DRC_ATTENUATION_FACTOR = "aac-drc-cut-level";
    public static final String KEY_AAC_DRC_BOOST_FACTOR = "aac-drc-boost-level";
    public static final String KEY_AAC_DRC_EFFECT_TYPE = "aac-drc-effect-type";
    public static final String KEY_AAC_DRC_HEAVY_COMPRESSION = "aac-drc-heavy-compression";
    public static final String KEY_AAC_DRC_TARGET_REFERENCE_LEVEL = "aac-target-ref-level";
    public static final String KEY_AAC_ENCODED_TARGET_LEVEL = "aac-encoded-target-level";
    public static final String KEY_AAC_MAX_OUTPUT_CHANNEL_COUNT = "aac-max-output-channel_count";
    public static final String KEY_AAC_PROFILE = "aac-profile";
    public static final String KEY_AAC_SBR_MODE = "aac-sbr-mode";
    public static final String KEY_AUDIO_SESSION_ID = "audio-session-id";
    public static final String KEY_BITRATE_MODE = "bitrate-mode";
    public static final String KEY_BIT_RATE = "bitrate";
    public static final String KEY_CAPTURE_RATE = "capture-rate";
    public static final String KEY_CA_PRIVATE_DATA = "ca-private-data";
    public static final String KEY_CA_SESSION_ID = "ca-session-id";
    public static final String KEY_CA_SYSTEM_ID = "ca-system-id";
    public static final String KEY_CHANNEL_COUNT = "channel-count";
    public static final String KEY_CHANNEL_MASK = "channel-mask";
    public static final String KEY_COLOR_FORMAT = "color-format";
    public static final String KEY_COLOR_RANGE = "color-range";
    public static final String KEY_COLOR_STANDARD = "color-standard";
    public static final String KEY_COLOR_TRANSFER = "color-transfer";
    public static final String KEY_COMPLEXITY = "complexity";
    public static final String KEY_CREATE_INPUT_SURFACE_SUSPENDED = "create-input-buffers-suspended";
    public static final String KEY_DURATION = "durationUs";
    public static final String KEY_FEATURE_ = "feature-";
    public static final String KEY_FLAC_COMPRESSION_LEVEL = "flac-compression-level";
    public static final String KEY_FRAME_RATE = "frame-rate";
    public static final String KEY_GRID_COLUMNS = "grid-cols";
    public static final String KEY_GRID_ROWS = "grid-rows";
    public static final String KEY_HAPTIC_CHANNEL_COUNT = "haptic-channel-count";
    public static final String KEY_HDR10_PLUS_INFO = "hdr10-plus-info";
    public static final String KEY_HDR_STATIC_INFO = "hdr-static-info";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_INTRA_REFRESH_PERIOD = "intra-refresh-period";
    public static final String KEY_IS_ADTS = "is-adts";
    public static final String KEY_IS_AUTOSELECT = "is-autoselect";
    public static final String KEY_IS_DEFAULT = "is-default";
    public static final String KEY_IS_FORCED_SUBTITLE = "is-forced-subtitle";
    public static final String KEY_IS_TIMED_TEXT = "is-timed-text";
    public static final String KEY_I_FRAME_INTERVAL = "i-frame-interval";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_LATENCY = "latency";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_MAX_BIT_RATE = "max-bitrate";
    public static final String KEY_MAX_B_FRAMES = "max-bframes";
    public static final String KEY_MAX_FPS_TO_ENCODER = "max-fps-to-encoder";
    public static final String KEY_MAX_HEIGHT = "max-height";
    public static final String KEY_MAX_INPUT_SIZE = "max-input-size";
    public static final String KEY_MAX_PTS_GAP_TO_ENCODER = "max-pts-gap-to-encoder";
    public static final String KEY_MAX_WIDTH = "max-width";
    public static final String KEY_MIME = "mime";
    public static final String KEY_OPERATING_RATE = "operating-rate";
    public static final String KEY_OUTPUT_REORDER_DEPTH = "output-reorder-depth";
    public static final String KEY_PCM_ENCODING = "pcm-encoding";
    public static final String KEY_PREPEND_HEADER_TO_SYNC_FRAMES = "prepend-sps-pps-to-idr-frames";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_PUSH_BLANK_BUFFERS_ON_STOP = "push-blank-buffers-on-shutdown";
    public static final String KEY_QUALITY = "quality";
    public static final String KEY_REPEAT_PREVIOUS_FRAME_AFTER = "repeat-previous-frame-after";
    public static final String KEY_ROTATION = "rotation-degrees";
    public static final String KEY_SAMPLE_RATE = "sample-rate";
    public static final String KEY_SLICE_HEIGHT = "slice-height";
    public static final String KEY_STRIDE = "stride";
    public static final String KEY_TEMPORAL_LAYERING = "ts-schema";
    public static final String KEY_TILE_HEIGHT = "tile-height";
    public static final String KEY_TILE_WIDTH = "tile-width";
    public static final String KEY_TRACK_ID = "track-id";
    public static final String KEY_WIDTH = "width";
    public static final String MIMETYPE_AUDIO_AAC = "audio/mp4a-latm";
    public static final String MIMETYPE_AUDIO_AC3 = "audio/ac3";
    public static final String MIMETYPE_AUDIO_AC4 = "audio/ac4";
    public static final String MIMETYPE_AUDIO_AMR_NB = "audio/3gpp";
    public static final String MIMETYPE_AUDIO_AMR_WB = "audio/amr-wb";
    public static final String MIMETYPE_AUDIO_EAC3 = "audio/eac3";
    public static final String MIMETYPE_AUDIO_EAC3_JOC = "audio/eac3-joc";
    public static final String MIMETYPE_AUDIO_FLAC = "audio/flac";
    public static final String MIMETYPE_AUDIO_G711_ALAW = "audio/g711-alaw";
    public static final String MIMETYPE_AUDIO_G711_MLAW = "audio/g711-mlaw";
    public static final String MIMETYPE_AUDIO_MPEG = "audio/mpeg";
    public static final String MIMETYPE_AUDIO_MSGSM = "audio/gsm";
    public static final String MIMETYPE_AUDIO_OPUS = "audio/opus";
    public static final String MIMETYPE_AUDIO_QCELP = "audio/qcelp";
    public static final String MIMETYPE_AUDIO_RAW = "audio/raw";
    public static final String MIMETYPE_AUDIO_SCRAMBLED = "audio/scrambled";
    public static final String MIMETYPE_AUDIO_VORBIS = "audio/vorbis";
    public static final String MIMETYPE_IMAGE_ANDROID_HEIC = "image/vnd.android.heic";
    public static final String MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final String MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final String MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final String MIMETYPE_TEXT_VTT = "text/vtt";
    public static final String MIMETYPE_VIDEO_AV1 = "video/av01";
    public static final String MIMETYPE_VIDEO_AVC = "video/avc";
    public static final String MIMETYPE_VIDEO_DOLBY_VISION = "video/dolby-vision";
    public static final String MIMETYPE_VIDEO_H263 = "video/3gpp";
    public static final String MIMETYPE_VIDEO_HEVC = "video/hevc";
    public static final String MIMETYPE_VIDEO_MPEG2 = "video/mpeg2";
    public static final String MIMETYPE_VIDEO_MPEG4 = "video/mp4v-es";
    public static final String MIMETYPE_VIDEO_RAW = "video/raw";
    public static final String MIMETYPE_VIDEO_SCRAMBLED = "video/scrambled";
    public static final String MIMETYPE_VIDEO_VP8 = "video/x-vnd.on2.vp8";
    public static final String MIMETYPE_VIDEO_VP9 = "video/x-vnd.on2.vp9";
    public static final int TYPE_BYTE_BUFFER = 5;
    public static final int TYPE_FLOAT = 3;
    public static final int TYPE_INTEGER = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_NULL = 0;
    public static final int TYPE_STRING = 4;
    @UnsupportedAppUsage
    private Map<String, Object> mMap;

    public MediaFormat() {
        this.mMap = new HashMap<String, Object>();
    }

    public MediaFormat(MediaFormat mediaFormat) {
        this();
        this.mMap.putAll(mediaFormat.mMap);
    }

    MediaFormat(Map<String, Object> map) {
        this.mMap = map;
    }

    public static final MediaFormat createAudioFormat(String string2, int n, int n2) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString(KEY_MIME, string2);
        mediaFormat.setInteger(KEY_SAMPLE_RATE, n);
        mediaFormat.setInteger(KEY_CHANNEL_COUNT, n2);
        return mediaFormat;
    }

    public static final MediaFormat createSubtitleFormat(String string2, String string3) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString(KEY_MIME, string2);
        mediaFormat.setString(KEY_LANGUAGE, string3);
        return mediaFormat;
    }

    public static final MediaFormat createVideoFormat(String string2, int n, int n2) {
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString(KEY_MIME, string2);
        mediaFormat.setInteger(KEY_WIDTH, n);
        mediaFormat.setInteger(KEY_HEIGHT, n2);
        return mediaFormat;
    }

    public final boolean containsFeature(String string2) {
        Map<String, Object> map = this.mMap;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_FEATURE_);
        stringBuilder.append(string2);
        return map.containsKey(stringBuilder.toString());
    }

    public final boolean containsKey(String string2) {
        return this.mMap.containsKey(string2);
    }

    public final ByteBuffer getByteBuffer(String string2) {
        return (ByteBuffer)this.mMap.get(string2);
    }

    public final ByteBuffer getByteBuffer(String object, ByteBuffer byteBuffer) {
        block0 : {
            if ((object = this.getByteBuffer((String)object)) != null) break block0;
            object = byteBuffer;
        }
        return object;
    }

    public boolean getFeatureEnabled(String object) {
        Map<String, Object> map = this.mMap;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_FEATURE_);
        stringBuilder.append((String)object);
        object = (Integer)map.get(stringBuilder.toString());
        if (object != null) {
            boolean bl = (Integer)object != 0;
            return bl;
        }
        throw new IllegalArgumentException("feature is not specified");
    }

    public final Set<String> getFeatures() {
        return new PrefixedKeySetWithPrefixRemoved(KEY_FEATURE_);
    }

    public final float getFloat(String string2) {
        return ((Float)this.mMap.get(string2)).floatValue();
    }

    public final float getFloat(String string2, float f) {
        try {
            float f2 = this.getFloat(string2);
            return f2;
        }
        catch (NullPointerException nullPointerException) {
            return f;
        }
    }

    public final int getInteger(String string2) {
        return (Integer)this.mMap.get(string2);
    }

    public final int getInteger(String string2, int n) {
        try {
            int n2 = this.getInteger(string2);
            return n2;
        }
        catch (NullPointerException nullPointerException) {
            return n;
        }
    }

    public final Set<String> getKeys() {
        return new UnprefixedKeySet(KEY_FEATURE_);
    }

    public final long getLong(String string2) {
        return (Long)this.mMap.get(string2);
    }

    public final long getLong(String string2, long l) {
        try {
            long l2 = this.getLong(string2);
            return l2;
        }
        catch (NullPointerException nullPointerException) {
            return l;
        }
    }

    @UnsupportedAppUsage
    Map<String, Object> getMap() {
        return this.mMap;
    }

    public final Number getNumber(String string2) {
        return (Number)this.mMap.get(string2);
    }

    public final Number getNumber(String object, Number number) {
        block0 : {
            if ((object = this.getNumber((String)object)) != null) break block0;
            object = number;
        }
        return object;
    }

    public final String getString(String string2) {
        return (String)this.mMap.get(string2);
    }

    public final String getString(String string2, String string3) {
        if ((string2 = this.getString(string2)) != null) {
            string3 = string2;
        }
        return string3;
    }

    public final int getValueTypeForKey(String object) {
        if ((object = this.mMap.get(object)) == null) {
            return 0;
        }
        if (object instanceof Integer) {
            return 1;
        }
        if (object instanceof Long) {
            return 2;
        }
        if (object instanceof Float) {
            return 3;
        }
        if (object instanceof String) {
            return 4;
        }
        if (object instanceof ByteBuffer) {
            return 5;
        }
        throw new RuntimeException("invalid value for key");
    }

    public final void removeFeature(String string2) {
        Map<String, Object> map = this.mMap;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_FEATURE_);
        stringBuilder.append(string2);
        map.remove(stringBuilder.toString());
    }

    public final void removeKey(String string2) {
        if (!string2.startsWith(KEY_FEATURE_)) {
            this.mMap.remove(string2);
        }
    }

    public final void setByteBuffer(String string2, ByteBuffer byteBuffer) {
        this.mMap.put(string2, byteBuffer);
    }

    public void setFeatureEnabled(String string2, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(KEY_FEATURE_);
        stringBuilder.append(string2);
        this.setInteger(stringBuilder.toString(), (int)bl);
    }

    public final void setFloat(String string2, float f) {
        this.mMap.put(string2, new Float(f));
    }

    public final void setInteger(String string2, int n) {
        this.mMap.put(string2, n);
    }

    public final void setLong(String string2, long l) {
        this.mMap.put(string2, l);
    }

    public final void setString(String string2, String string3) {
        this.mMap.put(string2, string3);
    }

    public String toString() {
        return this.mMap.toString();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorRange {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorStandard {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorTransfer {
    }

    private abstract class FilteredMappedKeySet
    extends AbstractSet<String> {
        private Set<String> mKeys;

        public FilteredMappedKeySet() {
            this.mKeys = MediaFormat.this.mMap.keySet();
        }

        @Override
        public boolean contains(Object object) {
            boolean bl = object instanceof String;
            boolean bl2 = false;
            if (bl) {
                object = this.mapItemToKey((String)object);
                bl = bl2;
                if (this.keepKey((String)object)) {
                    bl = bl2;
                    if (this.mKeys.contains(object)) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        @Override
        public Iterator<String> iterator() {
            return new KeyIterator();
        }

        protected abstract boolean keepKey(String var1);

        public /* synthetic */ boolean lambda$size$0$MediaFormat$FilteredMappedKeySet(String string2) {
            return this.keepKey(string2);
        }

        protected abstract String mapItemToKey(String var1);

        protected abstract String mapKeyToItem(String var1);

        @Override
        public boolean remove(Object object) {
            if (object instanceof String && this.keepKey((String)(object = this.mapItemToKey((String)object))) && this.mKeys.remove(object)) {
                MediaFormat.this.mMap.remove(object);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return (int)this.mKeys.stream().filter(new _$$Lambda$MediaFormat$FilteredMappedKeySet$S0dX0CM54Hgdu801GLdPbYKEcds(this)).count();
        }

        private class KeyIterator
        implements Iterator<String> {
            Iterator<String> mIterator;
            String mLast;

            public KeyIterator() {
                this.mIterator = FilteredMappedKeySet.this.mKeys.stream().filter(new _$$Lambda$MediaFormat$FilteredMappedKeySet$KeyIterator$3C8D_OYFyxgHLBDv_csQxBIPlfc(this)).collect(Collectors.toList()).iterator();
            }

            @Override
            public boolean hasNext() {
                return this.mIterator.hasNext();
            }

            public /* synthetic */ boolean lambda$new$0$MediaFormat$FilteredMappedKeySet$KeyIterator(String string2) {
                return FilteredMappedKeySet.this.keepKey(string2);
            }

            @Override
            public String next() {
                this.mLast = this.mIterator.next();
                return FilteredMappedKeySet.this.mapKeyToItem(this.mLast);
            }

            @Override
            public void remove() {
                this.mIterator.remove();
                MediaFormat.this.mMap.remove(this.mLast);
            }
        }

    }

    private class PrefixedKeySetWithPrefixRemoved
    extends FilteredMappedKeySet {
        private String mPrefix;
        private int mPrefixLength;

        public PrefixedKeySetWithPrefixRemoved(String string2) {
            this.mPrefix = string2;
            this.mPrefixLength = string2.length();
        }

        @Override
        protected boolean keepKey(String string2) {
            return string2.startsWith(this.mPrefix);
        }

        @Override
        protected String mapItemToKey(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mPrefix);
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }

        @Override
        protected String mapKeyToItem(String string2) {
            return string2.substring(this.mPrefixLength);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Type {
    }

    private class UnprefixedKeySet
    extends FilteredMappedKeySet {
        private String mPrefix;

        public UnprefixedKeySet(String string2) {
            this.mPrefix = string2;
        }

        @Override
        protected boolean keepKey(String string2) {
            return string2.startsWith(this.mPrefix) ^ true;
        }

        @Override
        protected String mapItemToKey(String string2) {
            return string2;
        }

        @Override
        protected String mapKeyToItem(String string2) {
            return string2;
        }
    }

}

