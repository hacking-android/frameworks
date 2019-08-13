/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.MediaFile;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import java.io.File;
import java.io.IOException;
import java.util.function.ToIntFunction;
import libcore.io.IoUtils;

public class ThumbnailUtils {
    private static final int OPTIONS_NONE = 0;
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    private static final int OPTIONS_SCALE_UP = 1;
    private static final String TAG = "ThumbnailUtils";
    @Deprecated
    @UnsupportedAppUsage
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void closeSilently(ParcelFileDescriptor parcelFileDescriptor) {
        IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static int computeInitialSampleSize(BitmapFactory.Options options, int n, int n2) {
        return 1;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static int computeSampleSize(BitmapFactory.Options options, int n, int n2) {
        return 1;
    }

    private static Size convertKind(int n) {
        if (n == 3) {
            return Point.convert(MediaStore.ThumbnailConstants.MICRO_SIZE);
        }
        if (n == 2) {
            return Point.convert(MediaStore.ThumbnailConstants.FULL_SCREEN_SIZE);
        }
        if (n == 1) {
            return Point.convert(MediaStore.ThumbnailConstants.MINI_SIZE);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported kind: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Exception decompiling
     */
    public static Bitmap createAudioThumbnail(File var0, Size var1_3, CancellationSignal var2_4) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Deprecated
    public static Bitmap createAudioThumbnail(String object, int n) {
        try {
            File file = new File((String)object);
            object = ThumbnailUtils.createAudioThumbnail(file, ThumbnailUtils.convertKind(n), null);
            return object;
        }
        catch (IOException iOException) {
            Log.w(TAG, iOException);
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Bitmap createImageThumbnail(File object, Size object2, CancellationSignal cancellationSignal) throws IOException {
        int n;
        byte[] arrby;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        Resizer resizer = new Resizer((Size)object2, cancellationSignal);
        Object object3 = MediaFile.getMimeTypeForFile(((File)object).getName());
        Object object4 = null;
        int n2 = 0;
        if (MediaFile.isExifMimeType((String)object3)) {
            arrby = new ExifInterface((File)object);
            n = arrby.getAttributeInt("Orientation", 0);
            if (n != 3) {
                if (n != 6) {
                    if (n == 8) {
                        n2 = 270;
                    }
                } else {
                    n2 = 90;
                }
            } else {
                n2 = 180;
            }
        } else {
            arrby = null;
        }
        if (((String)object3).equals("image/heif") || ((String)object3).equals("image/heif-sequence") || ((String)object3).equals("image/heic") || ((String)object3).equals("image/heic-sequence")) {
            object3 = new MediaMetadataRetriever();
            ((MediaMetadataRetriever)object3).setDataSource(((File)object).getAbsolutePath());
            object4 = new MediaMetadataRetriever.BitmapParams();
            object4 = ((MediaMetadataRetriever)object3).getThumbnailImageAtIndex(-1, (MediaMetadataRetriever.BitmapParams)object4, ((Size)object2).getWidth(), ((Size)object2).getWidth() * ((Size)object2).getHeight());
            ThumbnailUtils.$closeResource(null, (AutoCloseable)object3);
        }
        object2 = object4;
        if (object4 == null) {
            object2 = object4;
            if (arrby != null) {
                arrby = arrby.getThumbnailBytes();
                object2 = object4;
                if (arrby != null) {
                    try {
                        object2 = ImageDecoder.decodeBitmap(ImageDecoder.createSource(arrby), resizer);
                    }
                    catch (ImageDecoder.DecodeException decodeException) {
                        Log.w(TAG, decodeException);
                        object2 = object4;
                    }
                }
            }
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (object2 == null) {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource((File)object), resizer);
        }
        object = object2;
        if (n2 == 0) return object;
        n = ((Bitmap)object2).getWidth();
        int n3 = ((Bitmap)object2).getHeight();
        object = new Matrix();
        ((Matrix)object).setRotate(n2, n / 2, n3 / 2);
        return Bitmap.createBitmap((Bitmap)object2, 0, 0, n, n3, (Matrix)object, false);
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    ThumbnailUtils.$closeResource(throwable, (AutoCloseable)object3);
                    throw throwable2;
                }
                catch (RuntimeException runtimeException) {
                    throw new IOException("Failed to create thumbnail", runtimeException);
                }
            }
        }
    }

    @Deprecated
    public static Bitmap createImageThumbnail(String object, int n) {
        try {
            File file = new File((String)object);
            object = ThumbnailUtils.createImageThumbnail(file, ThumbnailUtils.convertKind(n), null);
            return object;
        }
        catch (IOException iOException) {
            Log.w(TAG, iOException);
            return null;
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static void createThumbnailFromEXIF(String string2, int n, int n2, SizedThumbnailBitmap sizedThumbnailBitmap) {
    }

    /*
     * Exception decompiling
     */
    public static Bitmap createVideoThumbnail(File var0, Size var1_3, CancellationSignal var2_5) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Deprecated
    public static Bitmap createVideoThumbnail(String object, int n) {
        try {
            File file = new File((String)object);
            object = ThumbnailUtils.createVideoThumbnail(file, ThumbnailUtils.convertKind(n), null);
            return object;
        }
        catch (IOException iOException) {
            Log.w(TAG, iOException);
            return null;
        }
    }

    public static Bitmap extractThumbnail(Bitmap bitmap, int n, int n2) {
        return ThumbnailUtils.extractThumbnail(bitmap, n, n2, 0);
    }

    public static Bitmap extractThumbnail(Bitmap bitmap, int n, int n2, int n3) {
        if (bitmap == null) {
            return null;
        }
        float f = bitmap.getWidth() < bitmap.getHeight() ? (float)n / (float)bitmap.getWidth() : (float)n2 / (float)bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(f, f);
        return ThumbnailUtils.transform(matrix, bitmap, n, n2, n3 | 1);
    }

    static /* synthetic */ boolean lambda$createAudioThumbnail$0(File object, String string2) {
        object = string2.toLowerCase();
        boolean bl = ((String)object).endsWith(".jpg") || ((String)object).endsWith(".png");
        return bl;
    }

    static /* synthetic */ int lambda$createAudioThumbnail$1(File object) {
        if (((String)(object = ((File)object).getName().toLowerCase())).equals("albumart.jpg")) {
            return 4;
        }
        if (((String)object).startsWith("albumart") && ((String)object).endsWith(".jpg")) {
            return 3;
        }
        if (((String)object).contains("albumart") && ((String)object).endsWith(".jpg")) {
            return 2;
        }
        return ((String)object).endsWith(".jpg");
    }

    static /* synthetic */ int lambda$createAudioThumbnail$2(ToIntFunction toIntFunction, File file, File file2) {
        return toIntFunction.applyAsInt(file) - toIntFunction.applyAsInt(file2);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static ParcelFileDescriptor makeInputStream(Uri parcelable, ContentResolver contentResolver) {
        try {
            parcelable = contentResolver.openFileDescriptor((Uri)parcelable, "r");
            return parcelable;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static Bitmap transform(Matrix object, Bitmap bitmap, int n, int n2, int n3) {
        float f;
        Bitmap bitmap2;
        int n4 = 1;
        int n5 = (n3 & 1) != 0 ? 1 : 0;
        n3 = (n3 & 2) != 0 ? n4 : 0;
        int n6 = bitmap.getWidth() - n;
        n4 = bitmap.getHeight() - n2;
        if (n5 == 0 && (n6 < 0 || n4 < 0)) {
            object = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas((Bitmap)object);
            n5 = Math.max(0, n6 / 2);
            n4 = Math.max(0, n4 / 2);
            Rect rect = new Rect(n5, n4, Math.min(n, bitmap.getWidth()) + n5, Math.min(n2, bitmap.getHeight()) + n4);
            n4 = (n - rect.width()) / 2;
            n5 = (n2 - rect.height()) / 2;
            canvas.drawBitmap(bitmap, rect, new Rect(n4, n5, n - n4, n2 - n5), null);
            if (n3 != 0) {
                bitmap.recycle();
            }
            canvas.setBitmap(null);
            return object;
        }
        float f2 = bitmap.getWidth();
        if (f2 / (f = (float)bitmap.getHeight()) > (float)n / (float)n2) {
            f2 = (float)n2 / f;
            if (!(f2 < 0.9f) && !(f2 > 1.0f)) {
                object = null;
            } else {
                ((Matrix)object).setScale(f2, f2);
            }
        } else if (!((f2 = (float)n / f2) < 0.9f) && !(f2 > 1.0f)) {
            object = null;
        } else {
            ((Matrix)object).setScale(f2, f2);
        }
        object = object != null ? Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), (Matrix)object, true) : bitmap;
        if (n3 != 0 && object != bitmap) {
            bitmap.recycle();
        }
        if ((bitmap2 = Bitmap.createBitmap((Bitmap)object, (n5 = Math.max(0, ((Bitmap)object).getWidth() - n)) / 2, (n4 = Math.max(0, ((Bitmap)object).getHeight() - n2)) / 2, n, n2)) != object && (n3 != 0 || object != bitmap)) {
            ((Bitmap)object).recycle();
        }
        return bitmap2;
    }

    private static class Resizer
    implements ImageDecoder.OnHeaderDecodedListener {
        private final CancellationSignal signal;
        private final Size size;

        public Resizer(Size size, CancellationSignal cancellationSignal) {
            this.size = size;
            this.signal = cancellationSignal;
        }

        @Override
        public void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source object) {
            object = this.signal;
            if (object != null) {
                ((CancellationSignal)object).throwIfCanceled();
            }
            imageDecoder.setAllocator(1);
            int n = Math.max(imageInfo.getSize().getWidth() / this.size.getWidth(), imageInfo.getSize().getHeight() / this.size.getHeight());
            if (n > 1) {
                imageDecoder.setTargetSampleSize(n);
            }
        }
    }

    @Deprecated
    private static class SizedThumbnailBitmap {
        public Bitmap mBitmap;
        public byte[] mThumbnailData;
        public int mThumbnailHeight;
        public int mThumbnailWidth;

        private SizedThumbnailBitmap() {
        }
    }

}

