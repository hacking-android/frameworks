/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.List;

public class EncoderCapabilities {
    private static final String TAG = "EncoderCapabilities";

    static {
        System.loadLibrary("media_jni");
        EncoderCapabilities.native_init();
    }

    private EncoderCapabilities() {
    }

    public static List<AudioEncoderCap> getAudioEncoders() {
        int n = EncoderCapabilities.native_get_num_audio_encoders();
        if (n == 0) {
            return null;
        }
        ArrayList<AudioEncoderCap> arrayList = new ArrayList<AudioEncoderCap>();
        for (int i = 0; i < n; ++i) {
            arrayList.add(EncoderCapabilities.native_get_audio_encoder_cap(i));
        }
        return arrayList;
    }

    public static int[] getOutputFileFormats() {
        int n = EncoderCapabilities.native_get_num_file_formats();
        if (n == 0) {
            return null;
        }
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = EncoderCapabilities.native_get_file_format(i);
        }
        return arrn;
    }

    @UnsupportedAppUsage
    public static List<VideoEncoderCap> getVideoEncoders() {
        int n = EncoderCapabilities.native_get_num_video_encoders();
        if (n == 0) {
            return null;
        }
        ArrayList<VideoEncoderCap> arrayList = new ArrayList<VideoEncoderCap>();
        for (int i = 0; i < n; ++i) {
            arrayList.add(EncoderCapabilities.native_get_video_encoder_cap(i));
        }
        return arrayList;
    }

    private static final native AudioEncoderCap native_get_audio_encoder_cap(int var0);

    private static final native int native_get_file_format(int var0);

    private static final native int native_get_num_audio_encoders();

    private static final native int native_get_num_file_formats();

    private static final native int native_get_num_video_encoders();

    private static final native VideoEncoderCap native_get_video_encoder_cap(int var0);

    private static final native void native_init();

    public static class AudioEncoderCap {
        public final int mCodec;
        public final int mMaxBitRate;
        public final int mMaxChannels;
        public final int mMaxSampleRate;
        public final int mMinBitRate;
        public final int mMinChannels;
        public final int mMinSampleRate;

        private AudioEncoderCap(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
            this.mCodec = n;
            this.mMinBitRate = n2;
            this.mMaxBitRate = n3;
            this.mMinSampleRate = n4;
            this.mMaxSampleRate = n5;
            this.mMinChannels = n6;
            this.mMaxChannels = n7;
        }
    }

    public static class VideoEncoderCap {
        @UnsupportedAppUsage
        public final int mCodec;
        public final int mMaxBitRate;
        @UnsupportedAppUsage
        public final int mMaxFrameHeight;
        public final int mMaxFrameRate;
        @UnsupportedAppUsage
        public final int mMaxFrameWidth;
        public final int mMinBitRate;
        @UnsupportedAppUsage
        public final int mMinFrameHeight;
        public final int mMinFrameRate;
        @UnsupportedAppUsage
        public final int mMinFrameWidth;

        private VideoEncoderCap(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
            this.mCodec = n;
            this.mMinBitRate = n2;
            this.mMaxBitRate = n3;
            this.mMinFrameRate = n4;
            this.mMaxFrameRate = n5;
            this.mMinFrameWidth = n6;
            this.mMaxFrameWidth = n7;
            this.mMinFrameHeight = n8;
            this.mMaxFrameHeight = n9;
        }
    }

}

