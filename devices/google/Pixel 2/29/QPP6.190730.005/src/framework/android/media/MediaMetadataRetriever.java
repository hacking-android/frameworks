/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaDataSource;
import android.media.MediaHTTPService;
import android.net.Uri;
import android.os.IBinder;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MediaMetadataRetriever
implements AutoCloseable {
    private static final int EMBEDDED_PICTURE_TYPE_ANY = 65535;
    public static final int METADATA_KEY_ALBUM = 1;
    public static final int METADATA_KEY_ALBUMARTIST = 13;
    public static final int METADATA_KEY_ARTIST = 2;
    public static final int METADATA_KEY_AUTHOR = 3;
    public static final int METADATA_KEY_BITRATE = 20;
    public static final int METADATA_KEY_BITS_PER_SAMPLE = 39;
    public static final int METADATA_KEY_CAPTURE_FRAMERATE = 25;
    public static final int METADATA_KEY_CD_TRACK_NUMBER = 0;
    public static final int METADATA_KEY_COLOR_RANGE = 37;
    public static final int METADATA_KEY_COLOR_STANDARD = 35;
    public static final int METADATA_KEY_COLOR_TRANSFER = 36;
    public static final int METADATA_KEY_COMPILATION = 15;
    public static final int METADATA_KEY_COMPOSER = 4;
    public static final int METADATA_KEY_DATE = 5;
    public static final int METADATA_KEY_DISC_NUMBER = 14;
    public static final int METADATA_KEY_DURATION = 9;
    public static final int METADATA_KEY_EXIF_LENGTH = 34;
    public static final int METADATA_KEY_EXIF_OFFSET = 33;
    public static final int METADATA_KEY_GENRE = 6;
    public static final int METADATA_KEY_HAS_AUDIO = 16;
    public static final int METADATA_KEY_HAS_IMAGE = 26;
    public static final int METADATA_KEY_HAS_VIDEO = 17;
    public static final int METADATA_KEY_IMAGE_COUNT = 27;
    public static final int METADATA_KEY_IMAGE_HEIGHT = 30;
    public static final int METADATA_KEY_IMAGE_PRIMARY = 28;
    public static final int METADATA_KEY_IMAGE_ROTATION = 31;
    public static final int METADATA_KEY_IMAGE_WIDTH = 29;
    public static final int METADATA_KEY_IS_DRM = 22;
    public static final int METADATA_KEY_LOCATION = 23;
    public static final int METADATA_KEY_MIMETYPE = 12;
    public static final int METADATA_KEY_NUM_TRACKS = 10;
    public static final int METADATA_KEY_SAMPLERATE = 38;
    public static final int METADATA_KEY_TIMED_TEXT_LANGUAGES = 21;
    public static final int METADATA_KEY_TITLE = 7;
    public static final int METADATA_KEY_VIDEO_FRAME_COUNT = 32;
    public static final int METADATA_KEY_VIDEO_HEIGHT = 19;
    public static final int METADATA_KEY_VIDEO_ROTATION = 24;
    public static final int METADATA_KEY_VIDEO_WIDTH = 18;
    public static final int METADATA_KEY_WRITER = 11;
    public static final int METADATA_KEY_YEAR = 8;
    public static final int OPTION_CLOSEST = 3;
    public static final int OPTION_CLOSEST_SYNC = 2;
    public static final int OPTION_NEXT_SYNC = 1;
    public static final int OPTION_PREVIOUS_SYNC = 0;
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
        MediaMetadataRetriever.native_init();
    }

    public MediaMetadataRetriever() {
        this.native_setup();
    }

    private native List<Bitmap> _getFrameAtIndex(int var1, int var2, BitmapParams var3);

    private native Bitmap _getFrameAtTime(long var1, int var3, int var4, int var5);

    private native Bitmap _getImageAtIndex(int var1, BitmapParams var2);

    private native void _setDataSource(MediaDataSource var1) throws IllegalArgumentException;

    private native void _setDataSource(IBinder var1, String var2, String[] var3, String[] var4) throws IllegalArgumentException;

    @UnsupportedAppUsage
    private native byte[] getEmbeddedPicture(int var1);

    private List<Bitmap> getFramesAtIndexInternal(int n, int n2, BitmapParams object) {
        if ("yes".equals(this.extractMetadata(17))) {
            int n3 = Integer.parseInt(this.extractMetadata(32));
            if (n >= 0 && n2 >= 1 && n < n3 && n <= n3 - n2) {
                return this._getFrameAtIndex(n, n2, (BitmapParams)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid frameIndex or numFrames: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalStateException("Does not contail video or image sequences");
    }

    private Bitmap getImageAtIndexInternal(int n, BitmapParams object) {
        if ("yes".equals(this.extractMetadata(26))) {
            String string2 = this.extractMetadata(27);
            if (n < Integer.parseInt(string2)) {
                return this._getImageAtIndex(n, (BitmapParams)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid image index: ");
            ((StringBuilder)object).append(string2);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalStateException("Does not contail still images");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final native void native_finalize();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static native void native_init();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private native void native_setup();

    @Override
    public void close() {
        this.release();
    }

    public native String extractMetadata(int var1);

    protected void finalize() throws Throwable {
        try {
            this.native_finalize();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public byte[] getEmbeddedPicture() {
        return this.getEmbeddedPicture(65535);
    }

    public Bitmap getFrameAtIndex(int n) {
        return this.getFramesAtIndex(n, 1).get(0);
    }

    public Bitmap getFrameAtIndex(int n, BitmapParams bitmapParams) {
        return this.getFramesAtIndex(n, 1, bitmapParams).get(0);
    }

    public Bitmap getFrameAtTime() {
        return this._getFrameAtTime(-1L, 2, -1, -1);
    }

    public Bitmap getFrameAtTime(long l) {
        return this.getFrameAtTime(l, 2);
    }

    public Bitmap getFrameAtTime(long l, int n) {
        if (n >= 0 && n <= 3) {
            return this._getFrameAtTime(l, n, -1, -1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported option: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public List<Bitmap> getFramesAtIndex(int n, int n2) {
        return this.getFramesAtIndexInternal(n, n2, null);
    }

    public List<Bitmap> getFramesAtIndex(int n, int n2, BitmapParams bitmapParams) {
        return this.getFramesAtIndexInternal(n, n2, bitmapParams);
    }

    public Bitmap getImageAtIndex(int n) {
        return this.getImageAtIndexInternal(n, null);
    }

    public Bitmap getImageAtIndex(int n, BitmapParams bitmapParams) {
        return this.getImageAtIndexInternal(n, bitmapParams);
    }

    public Bitmap getPrimaryImage() {
        return this.getImageAtIndexInternal(-1, null);
    }

    public Bitmap getPrimaryImage(BitmapParams bitmapParams) {
        return this.getImageAtIndexInternal(-1, bitmapParams);
    }

    public Bitmap getScaledFrameAtTime(long l, int n, int n2, int n3) {
        if (n >= 0 && n <= 3) {
            if (n2 > 0) {
                if (n3 > 0) {
                    return this._getFrameAtTime(l, n, n2, n3);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid height: ");
                stringBuilder.append(n3);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid width: ");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported option: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public native Bitmap getThumbnailImageAtIndex(int var1, BitmapParams var2, int var3, int var4);

    public native void release();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setDataSource(Context object, Uri uri) throws IllegalArgumentException, SecurityException {
        block16 : {
            if (iOException == null) throw new IllegalArgumentException();
            var3_10 = iOException.getScheme();
            if (var3_10 == null || var3_10.equals("file")) break block16;
            var4_11 = null;
            var6_13 = var5_12 = null;
            var3_10 = var4_11;
            try {
                block14 : {
                    block15 : {
                        object = object.getContentResolver();
                        var6_13 = var5_12;
                        var3_10 = var4_11;
                        object = object.openAssetFileDescriptor((Uri)iOException, "r");
                        if (object == null) ** GOTO lbl56
                        var6_13 = object;
                        var3_10 = object;
                        var5_12 = object.getFileDescriptor();
                        var6_13 = object;
                        var3_10 = object;
                        if (!var5_12.valid()) break block14;
                        var6_13 = object;
                        var3_10 = object;
                        if (object.getDeclaredLength() < 0L) {
                            var6_13 = object;
                            var3_10 = object;
                            this.setDataSource((FileDescriptor)var5_12);
                            break block15;
                        }
                        var6_13 = object;
                        var3_10 = object;
                        this.setDataSource((FileDescriptor)var5_12, object.getStartOffset(), object.getDeclaredLength());
                        {
                            catch (FileNotFoundException fileNotFoundException) {
                                var6_13 = var5_12;
                                var3_10 = var4_11;
                                var6_13 = var5_12;
                                var3_10 = var4_11;
                                throwable = new IllegalArgumentException();
                                var6_13 = var5_12;
                                var3_10 = var4_11;
                                throw throwable;
                            }
                        }
                    }
                    try {
                        object.close();
                        return;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    return;
                }
                var6_13 = object;
                var3_10 = object;
                var6_13 = object;
                var3_10 = object;
                var5_12 = new IllegalArgumentException();
                var6_13 = object;
                var3_10 = object;
                throw var5_12;
lbl56: // 1 sources:
                var6_13 = object;
                var3_10 = object;
                var6_13 = object;
                var3_10 = object;
                var5_12 = new IllegalArgumentException();
                var6_13 = object;
                var3_10 = object;
                throw var5_12;
            }
            catch (Throwable securityException) {
                if (var6_13 == null) throw securityException;
                try {
                    var6_13.close();
                    throw securityException;
                }
                catch (IOException var2_9) {
                    // empty catch block
                }
                throw securityException;
            }
            catch (SecurityException iOException) {
                if (var3_10 != null) {
                    try {
                        var3_10.close();
                    }
                    catch (IOException var1_7) {
                        // empty catch block
                    }
                }
                this.setDataSource(iOException.toString());
                return;
            }
        }
        this.setDataSource(iOException.getPath());
    }

    public void setDataSource(MediaDataSource mediaDataSource) throws IllegalArgumentException {
        this._setDataSource(mediaDataSource);
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IllegalArgumentException {
        this.setDataSource(fileDescriptor, 0L, 0x7FFFFFFFFFFFFFFL);
    }

    public native void setDataSource(FileDescriptor var1, long var2, long var4) throws IllegalArgumentException;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setDataSource(String string2) throws IllegalArgumentException {
        if (string2 == null) throw new IllegalArgumentException();
        FileInputStream fileInputStream = new FileInputStream(string2);
        try {
            this.setDataSource(fileInputStream.getFD(), 0L, 0x7FFFFFFFFFFFFFFL);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    fileInputStream.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        throw new IllegalArgumentException();
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        fileInputStream.close();
    }

    public void setDataSource(String string2, Map<String, String> object2) throws IllegalArgumentException {
        int n = 0;
        String[] arrstring = new String[object2.size()];
        String[] arrstring2 = new String[object2.size()];
        for (Map.Entry<K, V> entry : object2.entrySet()) {
            arrstring[n] = (String)entry.getKey();
            arrstring2[n] = (String)entry.getValue();
            ++n;
        }
        this._setDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(string2), string2, arrstring, arrstring2);
    }

    public static final class BitmapParams {
        private Bitmap.Config inPreferredConfig = Bitmap.Config.ARGB_8888;
        private Bitmap.Config outActualConfig = Bitmap.Config.ARGB_8888;

        public Bitmap.Config getActualConfig() {
            return this.outActualConfig;
        }

        public Bitmap.Config getPreferredConfig() {
            return this.inPreferredConfig;
        }

        public void setPreferredConfig(Bitmap.Config config) {
            if (config != null) {
                this.inPreferredConfig = config;
                return;
            }
            throw new IllegalArgumentException("preferred config can't be null");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Option {
    }

}

