/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.location.Location;
import android.media.Image;
import android.os.SystemClock;
import android.util.Log;
import android.util.Size;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public final class DngCreator
implements AutoCloseable {
    private static final int BYTES_PER_RGB_PIX = 3;
    private static final int DEFAULT_PIXEL_STRIDE = 2;
    private static final String GPS_DATE_FORMAT_STR = "yyyy:MM:dd";
    private static final String GPS_LAT_REF_NORTH = "N";
    private static final String GPS_LAT_REF_SOUTH = "S";
    private static final String GPS_LONG_REF_EAST = "E";
    private static final String GPS_LONG_REF_WEST = "W";
    public static final int MAX_THUMBNAIL_DIMENSION = 256;
    private static final String TAG = "DngCreator";
    private static final int TAG_ORIENTATION_UNKNOWN = 9;
    private static final String TIFF_DATETIME_FORMAT = "yyyy:MM:dd HH:mm:ss";
    private static final DateFormat sExifGPSDateStamp = new SimpleDateFormat("yyyy:MM:dd", Locale.US);
    private final Calendar mGPSTimeStampCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    private long mNativeContext;

    static {
        sExifGPSDateStamp.setTimeZone(TimeZone.getTimeZone("UTC"));
        DngCreator.nativeClassInit();
    }

    public DngCreator(CameraCharacteristics cameraCharacteristics, CaptureResult captureResult) {
        if (cameraCharacteristics != null && captureResult != null) {
            long l;
            Object object;
            long l2 = System.currentTimeMillis();
            int n = cameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE);
            if (n == 1) {
                l = l2 - SystemClock.elapsedRealtime();
            } else if (n == 0) {
                l = l2 - SystemClock.uptimeMillis();
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Sensor timestamp source is unexpected: ");
                ((StringBuilder)object).append(n);
                Log.w(TAG, ((StringBuilder)object).toString());
                l = l2 - SystemClock.uptimeMillis();
            }
            object = captureResult.get(CaptureResult.SENSOR_TIMESTAMP);
            if (object != null) {
                l2 = (Long)object / 1000000L + l;
            }
            object = new SimpleDateFormat(TIFF_DATETIME_FORMAT, Locale.US);
            ((DateFormat)object).setTimeZone(TimeZone.getDefault());
            object = ((Format)object).format(l2);
            this.nativeInit(cameraCharacteristics.getNativeCopy(), captureResult.getNativeCopy(), (String)object);
            return;
        }
        throw new IllegalArgumentException("Null argument to DngCreator constructor");
    }

    private static void colorToRgb(int n, int n2, byte[] arrby) {
        arrby[n2] = (byte)Color.red(n);
        arrby[n2 + 1] = (byte)Color.green(n);
        arrby[n2 + 2] = (byte)Color.blue(n);
    }

    private static ByteBuffer convertToRGB(Bitmap bitmap) {
        int n = bitmap.getWidth();
        int n2 = bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(n * 3 * n2);
        int[] arrn = new int[n];
        byte[] arrby = new byte[n * 3];
        for (int i = 0; i < n2; ++i) {
            bitmap.getPixels(arrn, 0, n, 0, i, n, 1);
            for (int j = 0; j < n; ++j) {
                DngCreator.colorToRgb(arrn[j], j * 3, arrby);
            }
            byteBuffer.put(arrby);
        }
        byteBuffer.rewind();
        return byteBuffer;
    }

    private static ByteBuffer convertToRGB(Image object) {
        int n = ((Image)object).getWidth();
        int n2 = ((Image)object).getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(n * 3 * n2);
        byte[] arrby = ((Image)object).getPlanes()[0];
        byte[] arrby2 = ((Image)object).getPlanes()[1];
        byte[] arrby3 = ((Image)object).getPlanes()[2];
        ByteBuffer byteBuffer2 = arrby.getBuffer();
        ByteBuffer byteBuffer3 = arrby2.getBuffer();
        object = arrby3.getBuffer();
        byteBuffer2.rewind();
        byteBuffer3.rewind();
        ((Buffer)object).rewind();
        int n3 = arrby.getRowStride();
        int n4 = arrby3.getRowStride();
        int n5 = arrby2.getRowStride();
        int n6 = arrby.getPixelStride();
        int n7 = arrby3.getPixelStride();
        int n8 = arrby2.getPixelStride();
        byte[] arrby4 = arrby2 = new byte[3];
        arrby4[0] = 0;
        arrby4[1] = 0;
        arrby4[2] = 0;
        arrby3 = new byte[(n - 1) * n6 + 1];
        byte[] arrby5 = new byte[(n / 2 - 1) * n8 + 1];
        byte[] arrby6 = new byte[(n / 2 - 1) * n7 + 1];
        arrby = new byte[n * 3];
        for (int i = 0; i < n2; ++i) {
            int n9 = i / 2;
            byteBuffer2.position(n3 * i);
            byteBuffer2.get(arrby3);
            byteBuffer3.position(n5 * n9);
            byteBuffer3.get(arrby5);
            ((Buffer)object).position(n4 * n9);
            ((ByteBuffer)object).get(arrby6);
            for (n9 = 0; n9 < n; ++n9) {
                int n10 = n9 / 2;
                arrby2[0] = arrby3[n6 * n9];
                arrby2[1] = arrby5[n8 * n10];
                arrby2[2] = arrby6[n7 * n10];
                DngCreator.yuvToRgb(arrby2, n9 * 3, arrby);
            }
            byteBuffer.put(arrby);
        }
        byteBuffer2.rewind();
        byteBuffer3.rewind();
        ((Buffer)object).rewind();
        byteBuffer.rewind();
        return byteBuffer;
    }

    private static native void nativeClassInit();

    private synchronized native void nativeDestroy();

    private synchronized native void nativeInit(CameraMetadataNative var1, CameraMetadataNative var2, String var3);

    private synchronized native void nativeSetDescription(String var1);

    private synchronized native void nativeSetGpsTags(int[] var1, String var2, int[] var3, String var4, String var5, int[] var6);

    private synchronized native void nativeSetOrientation(int var1);

    private synchronized native void nativeSetThumbnail(ByteBuffer var1, int var2, int var3);

    private synchronized native void nativeWriteImage(OutputStream var1, int var2, int var3, ByteBuffer var4, int var5, int var6, long var7, boolean var9) throws IOException;

    private synchronized native void nativeWriteInputStream(OutputStream var1, InputStream var2, int var3, int var4, long var5) throws IOException;

    private static int[] toExifLatLong(double d) {
        d = Math.abs(d);
        int n = (int)d;
        d = (d - (double)n) * 60.0;
        int n2 = (int)d;
        return new int[]{n, 1, n2, 1, (int)((d - (double)n2) * 6000.0), 100};
    }

    private void writeByteBuffer(int n, int n2, ByteBuffer object, OutputStream outputStream, int n3, int n4, long l) throws IOException {
        if (n > 0 && n2 > 0) {
            long l2;
            long l3 = ((Buffer)object).capacity();
            if (l3 >= (l2 = (long)n4 * (long)n2 + l)) {
                int n5 = n3 * n;
                if (n5 <= n4) {
                    ((Buffer)object).clear();
                    this.nativeWriteImage(outputStream, n, n2, (ByteBuffer)object, n4, n3, l, ((ByteBuffer)object).isDirect());
                    ((Buffer)object).clear();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid image pixel stride, row byte width ");
                ((StringBuilder)object).append(n5);
                ((StringBuilder)object).append(" is too large, expecting ");
                ((StringBuilder)object).append(n4);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Image size ");
            ((StringBuilder)object).append(l3);
            ((StringBuilder)object).append(" is too small (must be larger than ");
            ((StringBuilder)object).append(l2);
            ((StringBuilder)object).append(")");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Image with invalid width, height: (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(",");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(") passed to write");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static void yuvToRgb(byte[] arrby, int n, byte[] arrby2) {
        float f = arrby[0] & 255;
        float f2 = arrby[1] & 255;
        float f3 = arrby[2] & 255;
        arrby2[n] = (byte)Math.max(0.0f, Math.min(255.0f, (f3 - 128.0f) * 1.402f + f));
        arrby2[n + 1] = (byte)Math.max(0.0f, Math.min(255.0f, f - (f2 - 128.0f) * 0.34414f - (f3 - 128.0f) * 0.71414f));
        arrby2[n + 2] = (byte)Math.max(0.0f, Math.min(255.0f, (f2 - 128.0f) * 1.772f + f));
    }

    @Override
    public void close() {
        this.nativeDestroy();
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public DngCreator setDescription(String string2) {
        if (string2 != null) {
            this.nativeSetDescription(string2);
            return this;
        }
        throw new IllegalArgumentException("Null description passed to setDescription.");
    }

    public DngCreator setLocation(Location object) {
        if (object != null) {
            double d = ((Location)object).getLatitude();
            double d2 = ((Location)object).getLongitude();
            long l = ((Location)object).getTime();
            int[] arrn = DngCreator.toExifLatLong(d);
            int[] arrn2 = DngCreator.toExifLatLong(d2);
            object = d >= 0.0 ? "N" : "S";
            String string2 = d2 >= 0.0 ? "E" : "W";
            String string3 = sExifGPSDateStamp.format(l);
            this.mGPSTimeStampCalendar.setTimeInMillis(l);
            this.nativeSetGpsTags(arrn, (String)object, arrn2, string2, string3, new int[]{this.mGPSTimeStampCalendar.get(11), 1, this.mGPSTimeStampCalendar.get(12), 1, this.mGPSTimeStampCalendar.get(13), 1});
            return this;
        }
        throw new IllegalArgumentException("Null location passed to setLocation");
    }

    public DngCreator setOrientation(int n) {
        if (n >= 0 && n <= 8) {
            int n2 = n;
            if (n == 0) {
                n2 = 9;
            }
            this.nativeSetOrientation(n2);
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Orientation ");
        stringBuilder.append(n);
        stringBuilder.append(" is not a valid EXIF orientation value");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DngCreator setThumbnail(Bitmap object) {
        if (object != null) {
            int n = ((Bitmap)object).getWidth();
            int n2 = ((Bitmap)object).getHeight();
            if (n <= 256 && n2 <= 256) {
                this.nativeSetThumbnail(DngCreator.convertToRGB((Bitmap)object), n, n2);
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Thumbnail dimensions width,height (");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(",");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(") too large, dimensions must be smaller than ");
            ((StringBuilder)object).append(256);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Null argument to setThumbnail");
    }

    public DngCreator setThumbnail(Image object) {
        if (object != null) {
            int n = ((Image)object).getFormat();
            if (n == 35) {
                n = ((Image)object).getWidth();
                int n2 = ((Image)object).getHeight();
                if (n <= 256 && n2 <= 256) {
                    this.nativeSetThumbnail(DngCreator.convertToRGB((Image)object), n, n2);
                    return this;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Thumbnail dimensions width,height (");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(",");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(") too large, dimensions must be smaller than ");
                ((StringBuilder)object).append(256);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported Image format ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Null argument to setThumbnail");
    }

    public void writeByteBuffer(OutputStream outputStream, Size size, ByteBuffer byteBuffer, long l) throws IOException {
        if (outputStream != null) {
            if (size != null) {
                if (byteBuffer != null) {
                    if (l >= 0L) {
                        int n = size.getWidth();
                        this.writeByteBuffer(n, size.getHeight(), byteBuffer, outputStream, 2, n * 2, l);
                        return;
                    }
                    throw new IllegalArgumentException("Negative offset passed to writeByteBuffer");
                }
                throw new IllegalArgumentException("Null pixels passed to writeByteBuffer");
            }
            throw new IllegalArgumentException("Null size passed to writeByteBuffer");
        }
        throw new IllegalArgumentException("Null dngOutput passed to writeByteBuffer");
    }

    public void writeImage(OutputStream object, Image image) throws IOException {
        if (object != null) {
            if (image != null) {
                int n = image.getFormat();
                if (n == 32) {
                    Image.Plane[] arrplane = image.getPlanes();
                    if (arrplane != null && arrplane.length > 0) {
                        ByteBuffer byteBuffer = arrplane[0].getBuffer();
                        this.writeByteBuffer(image.getWidth(), image.getHeight(), byteBuffer, (OutputStream)object, arrplane[0].getPixelStride(), arrplane[0].getRowStride(), 0L);
                        return;
                    }
                    throw new IllegalArgumentException("Image with no planes passed to writeImage");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported image format ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("Null pixels to writeImage");
        }
        throw new IllegalArgumentException("Null dngOutput to writeImage");
    }

    public void writeInputStream(OutputStream object, Size size, InputStream inputStream, long l) throws IOException {
        if (object != null) {
            if (size != null) {
                if (inputStream != null) {
                    if (l >= 0L) {
                        int n = size.getWidth();
                        int n2 = size.getHeight();
                        if (n > 0 && n2 > 0) {
                            this.nativeWriteInputStream((OutputStream)object, inputStream, n, n2, l);
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Size with invalid width, height: (");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(",");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(") passed to writeInputStream");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    throw new IllegalArgumentException("Negative offset passed to writeInputStream");
                }
                throw new IllegalArgumentException("Null pixels passed to writeInputStream");
            }
            throw new IllegalArgumentException("Null size passed to writeInputStream");
        }
        throw new IllegalArgumentException("Null dngOutput passed to writeInputStream");
    }
}

