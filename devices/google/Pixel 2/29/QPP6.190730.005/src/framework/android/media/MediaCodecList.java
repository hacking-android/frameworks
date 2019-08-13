/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public final class MediaCodecList {
    public static final int ALL_CODECS = 1;
    public static final int REGULAR_CODECS = 0;
    private static final String TAG = "MediaCodecList";
    private static MediaCodecInfo[] sAllCodecInfos;
    private static Map<String, Object> sGlobalSettings;
    private static Object sInitLock;
    private static MediaCodecInfo[] sRegularCodecInfos;
    private MediaCodecInfo[] mCodecInfos;

    static {
        sInitLock = new Object();
        System.loadLibrary("media_jni");
        MediaCodecList.native_init();
    }

    private MediaCodecList() {
        this(0);
    }

    public MediaCodecList(int n) {
        MediaCodecList.initCodecList();
        this.mCodecInfos = n == 0 ? sRegularCodecInfos : sAllCodecInfos;
    }

    static final native int findCodecByName(String var0);

    private String findCodecForFormat(boolean bl, MediaFormat mediaFormat) {
        String string2 = mediaFormat.getString("mime");
        for (MediaCodecInfo illegalArgumentException : this.mCodecInfos) {
            if (illegalArgumentException.isEncoder() != bl) continue;
            MediaCodecInfo.CodecCapabilities codecCapabilities = illegalArgumentException.getCapabilitiesForType(string2);
            if (codecCapabilities == null) continue;
            try {
                if (!codecCapabilities.isFormatSupported(mediaFormat)) continue;
                String string3 = illegalArgumentException.getName();
                return string3;
            }
            catch (IllegalArgumentException illegalArgumentException2) {
                // empty catch block
            }
        }
        return null;
    }

    static final native int getAttributes(int var0);

    static final native String getCanonicalName(int var0);

    static final native MediaCodecInfo.CodecCapabilities getCodecCapabilities(int var0, String var1);

    public static final int getCodecCount() {
        MediaCodecList.initCodecList();
        return sRegularCodecInfos.length;
    }

    public static final MediaCodecInfo getCodecInfoAt(int n) {
        MediaCodecInfo[] arrmediaCodecInfo;
        MediaCodecList.initCodecList();
        if (n >= 0 && n <= (arrmediaCodecInfo = sRegularCodecInfos).length) {
            return arrmediaCodecInfo[n];
        }
        throw new IllegalArgumentException();
    }

    static final native String getCodecName(int var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static final Map<String, Object> getGlobalSettings() {
        Object object = sInitLock;
        synchronized (object) {
            if (sGlobalSettings == null) {
                sGlobalSettings = MediaCodecList.native_getGlobalSettings();
            }
            return sGlobalSettings;
        }
    }

    public static MediaCodecInfo getInfoFor(String string2) {
        MediaCodecList.initCodecList();
        return sAllCodecInfos[MediaCodecList.findCodecByName(string2)];
    }

    private static MediaCodecInfo getNewCodecInfoAt(int n) {
        String[] arrstring = MediaCodecList.getSupportedTypes(n);
        MediaCodecInfo.CodecCapabilities[] arrcodecCapabilities = new MediaCodecInfo.CodecCapabilities[arrstring.length];
        int n2 = 0;
        int n3 = arrstring.length;
        int n4 = 0;
        while (n4 < n3) {
            arrcodecCapabilities[n2] = MediaCodecList.getCodecCapabilities(n, arrstring[n4]);
            ++n4;
            ++n2;
        }
        return new MediaCodecInfo(MediaCodecList.getCodecName(n), MediaCodecList.getCanonicalName(n), MediaCodecList.getAttributes(n), arrcodecCapabilities);
    }

    static final native String[] getSupportedTypes(int var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static final void initCodecList() {
        Object object = sInitLock;
        synchronized (object) {
            if (sRegularCodecInfos == null) {
                int n = MediaCodecList.native_getCodecCount();
                ArrayList<MediaCodecInfo> arrayList = new ArrayList<MediaCodecInfo>();
                ArrayList<MediaCodecInfo> arrayList2 = new ArrayList<MediaCodecInfo>();
                for (int i = 0; i < n; ++i) {
                    try {
                        MediaCodecInfo mediaCodecInfo = MediaCodecList.getNewCodecInfoAt(i);
                        arrayList2.add(mediaCodecInfo);
                        mediaCodecInfo = mediaCodecInfo.makeRegular();
                        if (mediaCodecInfo == null) continue;
                        arrayList.add(mediaCodecInfo);
                        continue;
                    }
                    catch (Exception exception) {
                        Log.e("MediaCodecList", "Could not get codec capabilities", exception);
                    }
                }
                sRegularCodecInfos = arrayList.toArray(new MediaCodecInfo[arrayList.size()]);
                sAllCodecInfos = arrayList2.toArray(new MediaCodecInfo[arrayList2.size()]);
            }
            return;
        }
    }

    private static final native int native_getCodecCount();

    static final native Map<String, Object> native_getGlobalSettings();

    private static final native void native_init();

    public final String findDecoderForFormat(MediaFormat mediaFormat) {
        return this.findCodecForFormat(false, mediaFormat);
    }

    public final String findEncoderForFormat(MediaFormat mediaFormat) {
        return this.findCodecForFormat(true, mediaFormat);
    }

    public final MediaCodecInfo[] getCodecInfos() {
        MediaCodecInfo[] arrmediaCodecInfo = this.mCodecInfos;
        return Arrays.copyOf(arrmediaCodecInfo, arrmediaCodecInfo.length);
    }
}

