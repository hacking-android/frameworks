/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.MimeUtils
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import java.util.HashMap;
import libcore.net.MimeUtils;

public class MediaFile {
    @Deprecated
    @UnsupportedAppUsage
    private static final int FIRST_AUDIO_FILE_TYPE = 1;
    @Deprecated
    @UnsupportedAppUsage
    private static final int LAST_AUDIO_FILE_TYPE = 10;
    @Deprecated
    @UnsupportedAppUsage
    private static final HashMap<String, MediaFileType> sFileTypeMap = new HashMap();
    @Deprecated
    @UnsupportedAppUsage
    private static final HashMap<String, Integer> sFileTypeToFormatMap = new HashMap();
    @UnsupportedAppUsage
    private static final HashMap<Integer, String> sFormatToMimeTypeMap;
    @UnsupportedAppUsage
    private static final HashMap<String, Integer> sMimeTypeToFormatMap;

    static {
        sMimeTypeToFormatMap = new HashMap();
        sFormatToMimeTypeMap = new HashMap();
        MediaFile.addFileType(12297, "audio/mpeg");
        MediaFile.addFileType(12296, "audio/x-wav");
        MediaFile.addFileType(47361, "audio/x-ms-wma");
        MediaFile.addFileType(47362, "audio/ogg");
        MediaFile.addFileType(47363, "audio/aac");
        MediaFile.addFileType(47366, "audio/flac");
        MediaFile.addFileType(12295, "audio/x-aiff");
        MediaFile.addFileType(47491, "audio/mpeg");
        MediaFile.addFileType(12299, "video/mpeg");
        MediaFile.addFileType(47490, "video/mp4");
        MediaFile.addFileType(47492, "video/3gpp");
        MediaFile.addFileType(47492, "video/3gpp2");
        MediaFile.addFileType(12298, "video/avi");
        MediaFile.addFileType(47489, "video/x-ms-wmv");
        MediaFile.addFileType(12300, "video/x-ms-asf");
        MediaFile.addFileType(14337, "image/jpeg");
        MediaFile.addFileType(14343, "image/gif");
        MediaFile.addFileType(14347, "image/png");
        MediaFile.addFileType(14340, "image/x-ms-bmp");
        MediaFile.addFileType(14354, "image/heif");
        MediaFile.addFileType(14353, "image/x-adobe-dng");
        MediaFile.addFileType(14349, "image/tiff");
        MediaFile.addFileType(14349, "image/x-canon-cr2");
        MediaFile.addFileType(14349, "image/x-nikon-nrw");
        MediaFile.addFileType(14349, "image/x-sony-arw");
        MediaFile.addFileType(14349, "image/x-panasonic-rw2");
        MediaFile.addFileType(14349, "image/x-olympus-orf");
        MediaFile.addFileType(14349, "image/x-pentax-pef");
        MediaFile.addFileType(14349, "image/x-samsung-srw");
        MediaFile.addFileType(14338, "image/tiff");
        MediaFile.addFileType(14338, "image/x-nikon-nef");
        MediaFile.addFileType(14351, "image/jp2");
        MediaFile.addFileType(14352, "image/jpx");
        MediaFile.addFileType(47633, "audio/x-mpegurl");
        MediaFile.addFileType(47636, "audio/x-scpls");
        MediaFile.addFileType(47632, "application/vnd.ms-wpl");
        MediaFile.addFileType(47635, "video/x-ms-asf");
        MediaFile.addFileType(12292, "text/plain");
        MediaFile.addFileType(12293, "text/html");
        MediaFile.addFileType(47746, "text/xml");
        MediaFile.addFileType(47747, "application/msword");
        MediaFile.addFileType(47747, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MediaFile.addFileType(47749, "application/vnd.ms-excel");
        MediaFile.addFileType(47749, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MediaFile.addFileType(47750, "application/vnd.ms-powerpoint");
        MediaFile.addFileType(47750, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }

    private static void addFileType(int n, String string2) {
        if (!sMimeTypeToFormatMap.containsKey(string2)) {
            sMimeTypeToFormatMap.put(string2, n);
        }
        if (!sFormatToMimeTypeMap.containsKey(n)) {
            sFormatToMimeTypeMap.put(n, string2);
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    static void addFileType(String string2, int n, String string3) {
    }

    public static String getFileExtension(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.lastIndexOf(46);
        if (n >= 0) {
            return string2.substring(n + 1);
        }
        return null;
    }

    @UnsupportedAppUsage
    public static String getFileTitle(String string2) {
        int n = string2.lastIndexOf(47);
        String string3 = string2;
        if (n >= 0) {
            string3 = string2;
            if (++n < string2.length()) {
                string3 = string2.substring(n);
            }
        }
        n = string3.lastIndexOf(46);
        string2 = string3;
        if (n > 0) {
            string2 = string3.substring(0, n);
        }
        return string2;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static MediaFileType getFileType(String string2) {
        return null;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static int getFileTypeForMimeType(String string2) {
        return 0;
    }

    public static int getFormatCode(String string2, String string3) {
        int n = MediaFile.getFormatCodeForMimeType(string3);
        if (n != 12288) {
            return n;
        }
        return MediaFile.getFormatCodeForFile(string2);
    }

    public static int getFormatCodeForFile(String string2) {
        return MediaFile.getFormatCodeForMimeType(MediaFile.getMimeTypeForFile(string2));
    }

    public static int getFormatCodeForMimeType(String object) {
        if (object == null) {
            return 12288;
        }
        Object object2 = sMimeTypeToFormatMap.get(object);
        if (object2 != null) {
            return (Integer)object2;
        }
        object2 = MediaFile.normalizeMimeType((String)object);
        object = sMimeTypeToFormatMap.get(object2);
        if (object != null) {
            return (Integer)object;
        }
        if (((String)object2).startsWith("audio/")) {
            return 47360;
        }
        if (((String)object2).startsWith("video/")) {
            return 47488;
        }
        if (((String)object2).startsWith("image/")) {
            return 14336;
        }
        return 12288;
    }

    public static String getMimeType(String string2, int n) {
        if (!"application/octet-stream".equals(string2 = MediaFile.getMimeTypeForFile(string2))) {
            return string2;
        }
        return MediaFile.getMimeTypeForFormatCode(n);
    }

    @UnsupportedAppUsage
    public static String getMimeTypeForFile(String string2) {
        if ((string2 = MimeUtils.guessMimeTypeFromExtension((String)MediaFile.getFileExtension(string2))) == null) {
            string2 = "application/octet-stream";
        }
        return string2;
    }

    public static String getMimeTypeForFormatCode(int n) {
        String string2 = sFormatToMimeTypeMap.get(n);
        if (string2 == null) {
            string2 = "application/octet-stream";
        }
        return string2;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isAudioFileType(int n) {
        return false;
    }

    public static boolean isAudioMimeType(String string2) {
        return MediaFile.normalizeMimeType(string2).startsWith("audio/");
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isDrmFileType(int n) {
        return false;
    }

    public static boolean isDrmMimeType(String string2) {
        return MediaFile.normalizeMimeType(string2).equals("application/x-android-drm-fl");
    }

    public static boolean isExifMimeType(String string2) {
        return MediaFile.isImageMimeType(string2);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isImageFileType(int n) {
        return false;
    }

    public static boolean isImageMimeType(String string2) {
        return MediaFile.normalizeMimeType(string2).startsWith("image/");
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isPlayListFileType(int n) {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isPlayListMimeType(String string2) {
        string2 = MediaFile.normalizeMimeType(string2);
        switch (string2.hashCode()) {
            default: {
                return false;
            }
            case 1872259501: {
                if (!string2.equals("application/vnd.ms-wpl")) return false;
                return true;
            }
            case 264230524: {
                if (!string2.equals("audio/x-mpegurl")) return false;
                return true;
            }
            case -432766831: {
                if (!string2.equals("audio/mpegurl")) return false;
                return true;
            }
            case -622808459: {
                if (!string2.equals("application/vnd.apple.mpegurl")) return false;
                return true;
            }
            case -979095690: {
                if (!string2.equals("application/x-mpegurl")) return false;
                return true;
            }
            case -1165508903: {
                if (!string2.equals("audio/x-scpls")) return false;
                return true;
            }
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isVideoFileType(int n) {
        return false;
    }

    public static boolean isVideoMimeType(String string2) {
        return MediaFile.normalizeMimeType(string2).startsWith("video/");
    }

    private static String normalizeMimeType(String string2) {
        String string3 = MimeUtils.guessExtensionFromMimeType((String)string2);
        if (string3 != null && (string3 = MimeUtils.guessMimeTypeFromExtension((String)string3)) != null) {
            return string3;
        }
        if (string2 == null) {
            string2 = "application/octet-stream";
        }
        return string2;
    }

    @Deprecated
    public static class MediaFileType {
        @UnsupportedAppUsage
        public final int fileType;
        @UnsupportedAppUsage
        public final String mimeType;

        MediaFileType(int n, String string2) {
            this.fileType = n;
            this.mimeType = string2;
        }
    }

}

