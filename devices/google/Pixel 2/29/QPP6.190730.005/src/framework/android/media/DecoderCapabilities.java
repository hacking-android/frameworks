/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.List;

public class DecoderCapabilities {
    static {
        System.loadLibrary("media_jni");
        DecoderCapabilities.native_init();
    }

    private DecoderCapabilities() {
    }

    @UnsupportedAppUsage
    public static List<AudioDecoder> getAudioDecoders() {
        ArrayList<AudioDecoder> arrayList = new ArrayList<AudioDecoder>();
        int n = DecoderCapabilities.native_get_num_audio_decoders();
        for (int i = 0; i < n; ++i) {
            arrayList.add(AudioDecoder.values()[DecoderCapabilities.native_get_audio_decoder_type(i)]);
        }
        return arrayList;
    }

    @UnsupportedAppUsage
    public static List<VideoDecoder> getVideoDecoders() {
        ArrayList<VideoDecoder> arrayList = new ArrayList<VideoDecoder>();
        int n = DecoderCapabilities.native_get_num_video_decoders();
        for (int i = 0; i < n; ++i) {
            arrayList.add(VideoDecoder.values()[DecoderCapabilities.native_get_video_decoder_type(i)]);
        }
        return arrayList;
    }

    private static final native int native_get_audio_decoder_type(int var0);

    private static final native int native_get_num_audio_decoders();

    private static final native int native_get_num_video_decoders();

    private static final native int native_get_video_decoder_type(int var0);

    private static final native void native_init();

    public static enum AudioDecoder {
        AUDIO_DECODER_WMA;
        
    }

    public static enum VideoDecoder {
        VIDEO_DECODER_WMV;
        
    }

}

