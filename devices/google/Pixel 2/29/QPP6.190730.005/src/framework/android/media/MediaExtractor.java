/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioPresentation;
import android.media.DrmInitData;
import android.media.MediaCas;
import android.media.MediaCodec;
import android.media.MediaDataSource;
import android.media.MediaFormat;
import android.media.MediaHTTPService;
import android.net.Uri;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.PersistableBundle;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class MediaExtractor {
    public static final int SAMPLE_FLAG_ENCRYPTED = 2;
    public static final int SAMPLE_FLAG_PARTIAL_FRAME = 4;
    public static final int SAMPLE_FLAG_SYNC = 1;
    public static final int SEEK_TO_CLOSEST_SYNC = 2;
    public static final int SEEK_TO_NEXT_SYNC = 1;
    public static final int SEEK_TO_PREVIOUS_SYNC = 0;
    private MediaCas mMediaCas;
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
        MediaExtractor.native_init();
    }

    public MediaExtractor() {
        this.native_setup();
    }

    private native Map<String, Object> getFileFormatNative();

    private native Map<String, Object> getTrackFormatNative(int var1);

    private final native void nativeSetDataSource(IBinder var1, String var2, String[] var3, String[] var4) throws IOException;

    private final native void nativeSetMediaCas(IHwBinder var1);

    private final native void native_finalize();

    private native List<AudioPresentation> native_getAudioPresentations(int var1);

    private native PersistableBundle native_getMetrics();

    private static final native void native_init();

    private final native void native_setup();

    private ArrayList<Byte> toByteArray(byte[] arrby) {
        ArrayList<Byte> arrayList = new ArrayList<Byte>(arrby.length);
        for (int i = 0; i < arrby.length; ++i) {
            arrayList.add(i, arrby[i]);
        }
        return arrayList;
    }

    public native boolean advance();

    protected void finalize() {
        this.native_finalize();
    }

    public List<AudioPresentation> getAudioPresentations(int n) {
        return this.native_getAudioPresentations(n);
    }

    public native long getCachedDuration();

    public CasInfo getCasInfo(int n) {
        Map<String, Object> map = this.getTrackFormatNative(n);
        if (map.containsKey("ca-system-id")) {
            Object object;
            n = (Integer)map.get("ca-system-id");
            byte[] arrby = null;
            byte[] arrby2 = null;
            if (map.containsKey("ca-private-data")) {
                object = (byte[])map.get("ca-private-data");
                object.rewind();
                arrby2 = new byte[object.remaining()];
                object.get(arrby2);
            }
            object = arrby;
            if (this.mMediaCas != null) {
                object = arrby;
                if (map.containsKey("ca-session-id")) {
                    arrby = (ByteBuffer)map.get("ca-session-id");
                    arrby.rewind();
                    object = new byte[arrby.remaining()];
                    arrby.get((byte[])object);
                    object = this.mMediaCas.createFromSessionId(this.toByteArray((byte[])object));
                }
            }
            return new CasInfo(n, (MediaCas.Session)object, arrby2);
        }
        return null;
    }

    public DrmInitData getDrmInitData() {
        final Map<String, Object> map = this.getFileFormatNative();
        if (map == null) {
            return null;
        }
        if (map.containsKey("pssh")) {
            Map<UUID, byte[]> object2 = this.getPsshInfo();
            map = new HashMap<String, Object>();
            for (Map.Entry<UUID, byte[]> entry : object2.entrySet()) {
                map.put((String)((Object)entry.getKey()), new DrmInitData.SchemeInitData("cenc", entry.getValue()));
            }
            return new DrmInitData(){

                @Override
                public DrmInitData.SchemeInitData get(UUID uUID) {
                    return (DrmInitData.SchemeInitData)map.get(uUID);
                }
            };
        }
        int n = this.getTrackCount();
        for (int i = 0; i < n; ++i) {
            map = this.getTrackFormatNative(i);
            if (!map.containsKey("crypto-key")) {
                continue;
            }
            map = (ByteBuffer)map.get("crypto-key");
            ((Buffer)((Object)map)).rewind();
            final byte[] arrby = new byte[((Buffer)((Object)map)).remaining()];
            ((ByteBuffer)((Object)map)).get(arrby);
            return new DrmInitData(){

                @Override
                public DrmInitData.SchemeInitData get(UUID uUID) {
                    return new DrmInitData.SchemeInitData("webm", arrby);
                }
            };
        }
        return null;
    }

    public PersistableBundle getMetrics() {
        return this.native_getMetrics();
    }

    public Map<UUID, byte[]> getPsshInfo() {
        HashMap<HashMap<K, V>, byte[]> hashMap = null;
        byte[] arrby = this.getFileFormatNative();
        Serializable serializable = hashMap;
        if (arrby != null) {
            serializable = hashMap;
            if (arrby.containsKey("pssh")) {
                ByteBuffer byteBuffer = (ByteBuffer)arrby.get("pssh");
                byteBuffer.order(ByteOrder.nativeOrder());
                byteBuffer.rewind();
                arrby.remove("pssh");
                hashMap = new HashMap<HashMap<K, V>, byte[]>();
                do {
                    serializable = hashMap;
                    if (byteBuffer.remaining() <= 0) break;
                    byteBuffer.order(ByteOrder.BIG_ENDIAN);
                    serializable = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
                    byteBuffer.order(ByteOrder.nativeOrder());
                    arrby = new byte[byteBuffer.getInt()];
                    byteBuffer.get(arrby);
                    hashMap.put(serializable, arrby);
                } while (true);
            }
        }
        return serializable;
    }

    public native boolean getSampleCryptoInfo(MediaCodec.CryptoInfo var1);

    public native int getSampleFlags();

    public native long getSampleSize();

    public native long getSampleTime();

    public native int getSampleTrackIndex();

    public final native int getTrackCount();

    public MediaFormat getTrackFormat(int n) {
        return new MediaFormat(this.getTrackFormatNative(n));
    }

    public native boolean hasCacheReachedEndOfStream();

    public native int readSampleData(ByteBuffer var1, int var2);

    public final native void release();

    public native void seekTo(long var1, int var3);

    public native void selectTrack(int var1);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setDataSource(Context object, Uri uri, Map<String, String> map) throws IOException {
        block9 : {
            block8 : {
                block7 : {
                    Object object3;
                    Object object2;
                    Object object4;
                    block6 : {
                        object4 = uri.getScheme();
                        if (object4 == null || ((String)object4).equals("file")) break block9;
                        object4 = null;
                        object2 = null;
                        object3 = null;
                        object = ((Context)object).getContentResolver().openAssetFileDescriptor(uri, "r");
                        if (object != null) break block6;
                        if (object == null) return;
                        ((AssetFileDescriptor)object).close();
                        return;
                    }
                    object3 = object;
                    object4 = object;
                    object2 = object;
                    try {
                        if (((AssetFileDescriptor)object).getDeclaredLength() < 0L) {
                            object3 = object;
                            object4 = object;
                            object2 = object;
                            this.setDataSource(((AssetFileDescriptor)object).getFileDescriptor());
                            break block7;
                        }
                        object3 = object;
                        object4 = object;
                        object2 = object;
                        this.setDataSource(((AssetFileDescriptor)object).getFileDescriptor(), ((AssetFileDescriptor)object).getStartOffset(), ((AssetFileDescriptor)object).getDeclaredLength());
                    }
                    catch (Throwable throwable) {
                        if (object3 == null) throw throwable;
                        ((AssetFileDescriptor)object3).close();
                        throw throwable;
                    }
                    catch (IOException iOException) {
                        block10 : {
                            if (object4 == null) break block8;
                            break block10;
                            catch (SecurityException securityException) {
                                if (object2 == null) break block8;
                                object4 = object2;
                            }
                        }
                        ((AssetFileDescriptor)object4).close();
                    }
                }
                ((AssetFileDescriptor)object).close();
                return;
            }
            this.setDataSource(uri.toString(), map);
            return;
        }
        this.setDataSource(uri.getPath());
    }

    public final void setDataSource(AssetFileDescriptor assetFileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0L) {
            this.setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            this.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public final native void setDataSource(MediaDataSource var1) throws IOException;

    public final void setDataSource(FileDescriptor fileDescriptor) throws IOException {
        this.setDataSource(fileDescriptor, 0L, 0x7FFFFFFFFFFFFFFL);
    }

    public final native void setDataSource(FileDescriptor var1, long var2, long var4) throws IOException;

    public final void setDataSource(String string2) throws IOException {
        this.nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(string2), string2, null, null);
    }

    public final void setDataSource(String string2, Map<String, String> object) throws IOException {
        Object object2 = null;
        String[] arrstring = null;
        if (object != null) {
            String[] arrstring2 = new String[object.size()];
            String[] arrstring3 = new String[object.size()];
            int n = 0;
            object = object.entrySet().iterator();
            do {
                object2 = arrstring2;
                arrstring = arrstring3;
                if (!object.hasNext()) break;
                object2 = (Map.Entry)object.next();
                arrstring2[n] = (String)object2.getKey();
                arrstring3[n] = (String)object2.getValue();
                ++n;
            } while (true);
        }
        this.nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(string2), string2, (String[])object2, arrstring);
    }

    public final void setMediaCas(MediaCas mediaCas) {
        this.mMediaCas = mediaCas;
        this.nativeSetMediaCas(mediaCas.getBinder());
    }

    public native void unselectTrack(int var1);

    public static final class CasInfo {
        private final byte[] mPrivateData;
        private final MediaCas.Session mSession;
        private final int mSystemId;

        CasInfo(int n, MediaCas.Session session, byte[] arrby) {
            this.mSystemId = n;
            this.mSession = session;
            this.mPrivateData = arrby;
        }

        public byte[] getPrivateData() {
            return this.mPrivateData;
        }

        public MediaCas.Session getSession() {
            return this.mSession;
        }

        public int getSystemId() {
            return this.mSystemId;
        }
    }

    public static final class MetricsConstants {
        public static final String FORMAT = "android.media.mediaextractor.fmt";
        public static final String MIME_TYPE = "android.media.mediaextractor.mime";
        public static final String TRACKS = "android.media.mediaextractor.ntrk";

        private MetricsConstants() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SampleFlag {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SeekMode {
    }

}

