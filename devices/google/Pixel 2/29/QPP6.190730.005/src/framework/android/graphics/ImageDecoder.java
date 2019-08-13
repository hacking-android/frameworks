/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.NinePatch;
import android.graphics.PostProcessor;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import libcore.io.IoUtils;

public final class ImageDecoder
implements AutoCloseable {
    public static final int ALLOCATOR_DEFAULT = 0;
    public static final int ALLOCATOR_HARDWARE = 3;
    public static final int ALLOCATOR_SHARED_MEMORY = 2;
    public static final int ALLOCATOR_SOFTWARE = 1;
    @Deprecated
    public static final int ERROR_SOURCE_ERROR = 3;
    @Deprecated
    public static final int ERROR_SOURCE_EXCEPTION = 1;
    @Deprecated
    public static final int ERROR_SOURCE_INCOMPLETE = 2;
    public static final int MEMORY_POLICY_DEFAULT = 1;
    public static final int MEMORY_POLICY_LOW_RAM = 0;
    public static int sApiLevel;
    private int mAllocator = 0;
    private final boolean mAnimated;
    private AssetFileDescriptor mAssetFd;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private boolean mConserveMemory = false;
    private Rect mCropRect;
    private boolean mDecodeAsAlphaMask = false;
    private ColorSpace mDesiredColorSpace = null;
    private int mDesiredHeight;
    private int mDesiredWidth;
    private final int mHeight;
    private InputStream mInputStream;
    private final boolean mIsNinePatch;
    private boolean mMutable = false;
    private long mNativePtr;
    private OnPartialImageListener mOnPartialImageListener;
    private Rect mOutPaddingRect;
    private boolean mOwnsInputStream;
    private PostProcessor mPostProcessor;
    private Source mSource;
    private byte[] mTempStorage;
    private boolean mUnpremultipliedRequired = false;
    private final int mWidth;

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

    private ImageDecoder(long l, int n, int n2, boolean bl, boolean bl2) {
        this.mNativePtr = l;
        this.mWidth = n;
        this.mHeight = n2;
        this.mDesiredWidth = n;
        this.mDesiredHeight = n2;
        this.mAnimated = bl;
        this.mIsNinePatch = bl2;
        this.mCloseGuard.open("close");
    }

    private void callHeaderDecoded(OnHeaderDecodedListener onHeaderDecodedListener, Source source) {
        if (onHeaderDecodedListener != null) {
            ImageInfo imageInfo = new ImageInfo(this);
            try {
                onHeaderDecodedListener.onHeaderDecoded(this, imageInfo, source);
            }
            finally {
                imageInfo.mDecoder = null;
            }
        }
    }

    private boolean checkForExtended() {
        ColorSpace colorSpace = this.mDesiredColorSpace;
        boolean bl = false;
        if (colorSpace == null) {
            return false;
        }
        if (colorSpace == ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB) || this.mDesiredColorSpace == ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB)) {
            bl = true;
        }
        return bl;
    }

    private void checkState(boolean bl) {
        if (this.mNativePtr != 0L) {
            ImageDecoder.checkSubset(this.mDesiredWidth, this.mDesiredHeight, this.mCropRect);
            if (!bl && this.mAllocator == 3) {
                if (!this.mMutable) {
                    if (this.mDecodeAsAlphaMask) {
                        throw new IllegalStateException("Cannot make HARDWARE Alpha mask Bitmap!");
                    }
                } else {
                    throw new IllegalStateException("Cannot make mutable HARDWARE Bitmap!");
                }
            }
            if (this.mPostProcessor != null && this.mUnpremultipliedRequired) {
                throw new IllegalStateException("Cannot draw to unpremultiplied pixels!");
            }
            return;
        }
        throw new IllegalStateException("Cannot use closed ImageDecoder!");
    }

    private static void checkSubset(int n, int n2, Rect rect) {
        if (rect == null) {
            return;
        }
        if (rect.left >= 0 && rect.top >= 0 && rect.right <= n && rect.bottom <= n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subset ");
        stringBuilder.append(rect);
        stringBuilder.append(" not contained by scaled image bounds: (");
        stringBuilder.append(n);
        stringBuilder.append(" x ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }

    private int computeDensity(Source source) {
        if (this.requestedResize()) {
            return 0;
        }
        int n = source.getDensity();
        if (n == 0) {
            return n;
        }
        if (this.mIsNinePatch && this.mPostProcessor == null) {
            return n;
        }
        Resources resources = source.getResources();
        if (resources != null && resources.getDisplayMetrics().noncompatDensityDpi == n) {
            return n;
        }
        int n2 = source.computeDstDensity();
        if (n == n2) {
            return n;
        }
        if (n < n2 && sApiLevel >= 28) {
            return n;
        }
        float f = (float)n2 / (float)n;
        this.setTargetSize(Math.max((int)((float)this.mWidth * f + 0.5f), 1), Math.max((int)((float)this.mHeight * f + 0.5f), 1));
        return n2;
    }

    private static ImageDecoder createFromAsset(AssetManager.AssetInputStream assetInputStream, Source object) throws IOException {
        block4 : {
            block2 : {
                try {
                    object = ImageDecoder.nCreate(assetInputStream.getNativeAsset(), (Source)object);
                    if (object != null) break block2;
                }
                catch (Throwable throwable) {
                    block3 : {
                        if (false) break block3;
                        IoUtils.closeQuietly((AutoCloseable)assetInputStream);
                    }
                    throw throwable;
                }
                IoUtils.closeQuietly((AutoCloseable)assetInputStream);
                break block4;
            }
            ((ImageDecoder)object).mInputStream = assetInputStream;
            ((ImageDecoder)object).mOwnsInputStream = true;
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ImageDecoder createFromAssetFileDescriptor(AssetFileDescriptor assetFileDescriptor, Source object) throws IOException {
        Throwable throwable22;
        block5 : {
            block4 : {
                FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
                long l = assetFileDescriptor.getStartOffset();
                try {
                    try {
                        Os.lseek((FileDescriptor)fileDescriptor, (long)l, (int)OsConstants.SEEK_SET);
                        ImageDecoder imageDecoder = ImageDecoder.nCreate(fileDescriptor, (Source)object);
                        object = imageDecoder;
                    }
                    catch (ErrnoException errnoException) {
                        FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
                        object = ImageDecoder.createFromStream(fileInputStream, true, (Source)object);
                    }
                    if (object != null) break block4;
                }
                catch (Throwable throwable22) {
                    break block5;
                }
                IoUtils.closeQuietly((AutoCloseable)assetFileDescriptor);
                return object;
            }
            ((ImageDecoder)object).mAssetFd = assetFileDescriptor;
            return object;
        }
        if (false) throw throwable22;
        IoUtils.closeQuietly((AutoCloseable)assetFileDescriptor);
        throw throwable22;
    }

    private static ImageDecoder createFromFile(File object, Source object2) throws IOException {
        block6 : {
            block5 : {
                object = new FileInputStream((File)object);
                FileDescriptor fileDescriptor = ((FileInputStream)object).getFD();
                try {
                    Os.lseek((FileDescriptor)fileDescriptor, (long)0L, (int)OsConstants.SEEK_CUR);
                }
                catch (ErrnoException errnoException) {
                    return ImageDecoder.createFromStream((InputStream)object, true, (Source)object2);
                }
                try {
                    object2 = ImageDecoder.nCreate(fileDescriptor, (Source)object2);
                    if (object2 != null) break block5;
                }
                catch (Throwable throwable) {
                    if (!false) {
                        IoUtils.closeQuietly((AutoCloseable)object);
                    }
                    throw throwable;
                }
                IoUtils.closeQuietly((AutoCloseable)object);
                break block6;
            }
            ((ImageDecoder)object2).mInputStream = object;
            ((ImageDecoder)object2).mOwnsInputStream = true;
        }
        return object2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static ImageDecoder createFromStream(InputStream inputStream, boolean bl, Source object) throws IOException {
        byte[] arrby;
        block2 : {
            arrby = new byte[16384];
            try {
                object = ImageDecoder.nCreate(inputStream, arrby, (Source)object);
                if (object != null) break block2;
                if (!bl) return object;
            }
            catch (Throwable throwable) {
                if (false || !bl) throw throwable;
                IoUtils.closeQuietly((AutoCloseable)inputStream);
                throw throwable;
            }
            IoUtils.closeQuietly((AutoCloseable)inputStream);
            return object;
        }
        ((ImageDecoder)object).mInputStream = inputStream;
        ((ImageDecoder)object).mOwnsInputStream = bl;
        ((ImageDecoder)object).mTempStorage = arrby;
        return object;
    }

    public static Source createSource(ContentResolver contentResolver, Uri uri) {
        return new ContentResolverSource(contentResolver, uri, null);
    }

    public static Source createSource(ContentResolver contentResolver, Uri uri, Resources resources) {
        return new ContentResolverSource(contentResolver, uri, resources);
    }

    public static Source createSource(AssetManager assetManager, String string2) {
        return new AssetSource(assetManager, string2);
    }

    public static Source createSource(Resources resources, int n) {
        return new ResourceSource(resources, n);
    }

    public static Source createSource(Resources resources, InputStream inputStream) {
        return new InputStreamSource(resources, inputStream, Bitmap.getDefaultDensity());
    }

    public static Source createSource(Resources resources, InputStream inputStream, int n) {
        return new InputStreamSource(resources, inputStream, n);
    }

    public static Source createSource(File file) {
        return new FileSource(file);
    }

    public static Source createSource(ByteBuffer byteBuffer) {
        return new ByteBufferSource(byteBuffer);
    }

    public static Source createSource(Callable<AssetFileDescriptor> callable) {
        return new CallableSource(callable);
    }

    public static Source createSource(byte[] arrby) {
        return ImageDecoder.createSource(arrby, 0, arrby.length);
    }

    public static Source createSource(byte[] arrby, int n, int n2) throws ArrayIndexOutOfBoundsException {
        if (arrby != null) {
            if (n >= 0 && n2 >= 0 && n < arrby.length && n + n2 <= arrby.length) {
                return new ByteArraySource(arrby, n, n2);
            }
            throw new ArrayIndexOutOfBoundsException("invalid offset/length!");
        }
        throw new NullPointerException("null byte[] in createSource!");
    }

    public static Bitmap decodeBitmap(Source source) throws IOException {
        return ImageDecoder.decodeBitmapImpl(source, null);
    }

    public static Bitmap decodeBitmap(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        if (onHeaderDecodedListener != null) {
            return ImageDecoder.decodeBitmapImpl(source, onHeaderDecodedListener);
        }
        throw new IllegalArgumentException("listener cannot be null! Use decodeBitmap(Source) to not have a listener");
    }

    private static Bitmap decodeBitmapImpl(Source object, OnHeaderDecodedListener arrby) throws IOException {
        Bitmap bitmap;
        ImageDecoder imageDecoder;
        block7 : {
            imageDecoder = ((Source)object).createImageDecoder();
            try {
                imageDecoder.mSource = object;
                imageDecoder.callHeaderDecoded((OnHeaderDecodedListener)arrby, (Source)object);
                int n = imageDecoder.computeDensity((Source)object);
                bitmap = imageDecoder.decodeBitmapInternal();
                bitmap.setDensity(n);
                object = imageDecoder.mOutPaddingRect;
                if (object == null) break block7;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (imageDecoder != null) {
                        ImageDecoder.$closeResource(throwable, imageDecoder);
                    }
                    throw throwable2;
                }
            }
            arrby = bitmap.getNinePatchChunk();
            if (arrby == null) break block7;
            if (!NinePatch.isNinePatchChunk(arrby)) break block7;
            ImageDecoder.nGetPadding(imageDecoder.mNativePtr, (Rect)object);
        }
        ImageDecoder.$closeResource(null, imageDecoder);
        return bitmap;
    }

    private Bitmap decodeBitmapInternal() throws IOException {
        boolean bl = false;
        this.checkState(false);
        long l = this.mNativePtr;
        if (this.mPostProcessor != null) {
            bl = true;
        }
        return ImageDecoder.nDecodeBitmap(l, this, bl, this.mDesiredWidth, this.mDesiredHeight, this.mCropRect, this.mMutable, this.mAllocator, this.mUnpremultipliedRequired, this.mConserveMemory, this.mDecodeAsAlphaMask, this.getColorSpacePtr(), this.checkForExtended());
    }

    public static Drawable decodeDrawable(Source source) throws IOException {
        return ImageDecoder.decodeDrawableImpl(source, null);
    }

    public static Drawable decodeDrawable(Source source, OnHeaderDecodedListener onHeaderDecodedListener) throws IOException {
        if (onHeaderDecodedListener != null) {
            return ImageDecoder.decodeDrawableImpl(source, onHeaderDecodedListener);
        }
        throw new IllegalArgumentException("listener cannot be null! Use decodeDrawable(Source) to not have a listener");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Drawable decodeDrawableImpl(Source var0, OnHeaderDecodedListener var1_2) throws IOException {
        block8 : {
            block10 : {
                block9 : {
                    var2_4 = var0.createImageDecoder();
                    try {
                        var2_4.mSource = var0;
                        var2_4.callHeaderDecoded((OnHeaderDecodedListener)var1_2, (Source)var0);
                        if (var2_4.mUnpremultipliedRequired) ** GOTO lbl48
                        if (var2_4.mMutable) break block8;
                        var3_5 = var2_4.computeDensity((Source)var0);
                        if (!var2_4.mAnimated) break block9;
                        var1_2 = var2_4.mPostProcessor == null ? null : var2_4;
                        var2_4.checkState(true);
                        var4_6 = new AnimatedImageDrawable(var2_4.mNativePtr, (ImageDecoder)var1_2, var2_4.mDesiredWidth, var2_4.mDesiredHeight, var2_4.getColorSpacePtr(), var2_4.checkForExtended(), var3_5, var0.computeDstDensity(), var2_4.mCropRect, var2_4.mInputStream, var2_4.mAssetFd);
                        var2_4.mInputStream = null;
                        var2_4.mAssetFd = null;
                    }
                    catch (Throwable var1_3) {
                        try {
                            throw var1_3;
                        }
                        catch (Throwable var0_1) {
                            if (var2_4 == null) throw var0_1;
                            ImageDecoder.$closeResource(var1_3, var2_4);
                            throw var0_1;
                        }
                    }
                    ImageDecoder.$closeResource(null, var2_4);
                    return var4_6;
                }
                var1_2 = var2_4.decodeBitmapInternal();
                var1_2.setDensity(var3_5);
                var5_8 = var0.getResources();
                var4_7 = var1_2.getNinePatchChunk();
                if (var4_7 == null || !NinePatch.isNinePatchChunk(var4_7)) break block10;
                var6_9 = new Rect();
                var1_2.getOpticalInsets(var6_9);
                var0 = var2_4.mOutPaddingRect;
                if (var0 == null) {
                    var0 = new Rect();
                }
                ImageDecoder.nGetPadding(var2_4.mNativePtr, (Rect)var0);
                var0 = new NinePatchDrawable(var5_8, (Bitmap)var1_2, var4_7, (Rect)var0, var6_9, null);
                ImageDecoder.$closeResource(null, var2_4);
                return var0;
            }
            var0 = new BitmapDrawable(var5_8, (Bitmap)var1_2);
            ImageDecoder.$closeResource(null, var2_4);
            return var0;
        }
        var0 = new IllegalStateException("Cannot decode a mutable Drawable!");
        throw var0;
lbl48: // 1 sources:
        var0 = new IllegalStateException("Cannot decode a Drawable with unpremultiplied pixels!");
        throw var0;
    }

    private ColorSpace getColorSpace() {
        return ImageDecoder.nGetColorSpace(this.mNativePtr);
    }

    private long getColorSpacePtr() {
        ColorSpace colorSpace = this.mDesiredColorSpace;
        if (colorSpace == null) {
            return 0L;
        }
        return colorSpace.getNativeInstance();
    }

    private String getMimeType() {
        return ImageDecoder.nGetMimeType(this.mNativePtr);
    }

    private int getTargetDimension(int n, int n2, int n3) {
        if (n2 >= n) {
            return 1;
        }
        int n4 = n / n2;
        if (n3 == n4) {
            return n3;
        }
        if (Math.abs(n3 * n2 - n) < n2) {
            return n3;
        }
        return n4;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean isMimeTypeSupported(String var0) {
        block24 : {
            Objects.requireNonNull(var0);
            var0 = var0.toLowerCase(Locale.US);
            switch (var0.hashCode()) {
                case 2111234748: {
                    if (!var0.equals("image/x-canon-cr2")) break;
                    var1_1 = 10;
                    break block24;
                }
                case 2099152524: {
                    if (!var0.equals("image/x-nikon-nrw")) break;
                    var1_1 = 13;
                    break block24;
                }
                case 2099152104: {
                    if (!var0.equals("image/x-nikon-nef")) break;
                    var1_1 = 12;
                    break block24;
                }
                case 1378106698: {
                    if (!var0.equals("image/x-olympus-orf")) break;
                    var1_1 = 14;
                    break block24;
                }
                case 1146342924: {
                    if (!var0.equals("image/x-ico")) break;
                    var1_1 = 7;
                    break block24;
                }
                case 741270252: {
                    if (!var0.equals("image/vnd.wap.wbmp")) break;
                    var1_1 = 8;
                    break block24;
                }
                case -332763809: {
                    if (!var0.equals("image/x-pentax-pef")) break;
                    var1_1 = 17;
                    break block24;
                }
                case -879258763: {
                    if (!var0.equals("image/png")) break;
                    var1_1 = 0;
                    break block24;
                }
                case -879267568: {
                    if (!var0.equals("image/gif")) break;
                    var1_1 = 3;
                    break block24;
                }
                case -879272239: {
                    if (!var0.equals("image/bmp")) break;
                    var1_1 = 6;
                    break block24;
                }
                case -985160897: {
                    if (!var0.equals("image/x-panasonic-rw2")) break;
                    var1_1 = 16;
                    break block24;
                }
                case -1423313290: {
                    if (!var0.equals("image/x-adobe-dng")) break;
                    var1_1 = 11;
                    break block24;
                }
                case -1487018032: {
                    if (!var0.equals("image/webp")) break;
                    var1_1 = 2;
                    break block24;
                }
                case -1487394660: {
                    if (!var0.equals("image/jpeg")) break;
                    var1_1 = 1;
                    break block24;
                }
                case -1487464690: {
                    if (!var0.equals("image/heif")) break;
                    var1_1 = 4;
                    break block24;
                }
                case -1487464693: {
                    if (!var0.equals("image/heic")) break;
                    var1_1 = 5;
                    break block24;
                }
                case -1594371159: {
                    if (!var0.equals("image/x-sony-arw")) break;
                    var1_1 = 9;
                    break block24;
                }
                case -1635437028: {
                    if (!var0.equals("image/x-samsung-srw")) break;
                    var1_1 = 18;
                    break block24;
                }
                case -1875291391: {
                    if (!var0.equals("image/x-fuji-raf")) break;
                    var1_1 = 15;
                    break block24;
                }
            }
            ** break;
lbl82: // 1 sources:
            var1_1 = -1;
        }
        switch (var1_1) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
        }
        return true;
    }

    private static native void nClose(long var0);

    private static native ImageDecoder nCreate(long var0, Source var2) throws IOException;

    private static native ImageDecoder nCreate(FileDescriptor var0, Source var1) throws IOException;

    private static native ImageDecoder nCreate(InputStream var0, byte[] var1, Source var2) throws IOException;

    private static native ImageDecoder nCreate(ByteBuffer var0, int var1, int var2, Source var3) throws IOException;

    private static native ImageDecoder nCreate(byte[] var0, int var1, int var2, Source var3) throws IOException;

    private static native Bitmap nDecodeBitmap(long var0, ImageDecoder var2, boolean var3, int var4, int var5, Rect var6, boolean var7, int var8, boolean var9, boolean var10, boolean var11, long var12, boolean var14) throws IOException;

    private static native ColorSpace nGetColorSpace(long var0);

    private static native String nGetMimeType(long var0);

    private static native void nGetPadding(long var0, Rect var2);

    private static native Size nGetSampledSize(long var0, int var2);

    private void onPartialImage(int n, Throwable object) throws DecodeException {
        DecodeException decodeException = new DecodeException(n, (Throwable)object, this.mSource);
        object = this.mOnPartialImageListener;
        if (object != null && object.onPartialImage(decodeException)) {
            return;
        }
        throw decodeException;
    }

    @UnsupportedAppUsage
    private int postProcessAndRelease(Canvas canvas) {
        try {
            int n = this.mPostProcessor.onPostProcess(canvas);
            return n;
        }
        finally {
            canvas.release();
        }
    }

    private boolean requestedResize() {
        boolean bl = this.mWidth != this.mDesiredWidth || this.mHeight != this.mDesiredHeight;
        return bl;
    }

    @Override
    public void close() {
        this.mCloseGuard.close();
        if (!this.mClosed.compareAndSet(false, true)) {
            return;
        }
        ImageDecoder.nClose(this.mNativePtr);
        this.mNativePtr = 0L;
        if (this.mOwnsInputStream) {
            IoUtils.closeQuietly((AutoCloseable)this.mInputStream);
        }
        IoUtils.closeQuietly((AutoCloseable)this.mAssetFd);
        this.mInputStream = null;
        this.mAssetFd = null;
        this.mTempStorage = null;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mInputStream = null;
            this.mAssetFd = null;
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getAllocator() {
        return this.mAllocator;
    }

    @Deprecated
    public boolean getAsAlphaMask() {
        return this.getDecodeAsAlphaMask();
    }

    @Deprecated
    public boolean getConserveMemory() {
        return this.mConserveMemory;
    }

    public Rect getCrop() {
        return this.mCropRect;
    }

    @Deprecated
    public boolean getDecodeAsAlphaMask() {
        return this.mDecodeAsAlphaMask;
    }

    public int getMemorySizePolicy() {
        return this.mConserveMemory ^ true;
    }

    @Deprecated
    public boolean getMutable() {
        return this.isMutableRequired();
    }

    public OnPartialImageListener getOnPartialImageListener() {
        return this.mOnPartialImageListener;
    }

    public PostProcessor getPostProcessor() {
        return this.mPostProcessor;
    }

    @Deprecated
    public boolean getRequireUnpremultiplied() {
        return this.isUnpremultipliedRequired();
    }

    public Size getSampledSize(int n) {
        if (n > 0) {
            long l = this.mNativePtr;
            if (l != 0L) {
                return ImageDecoder.nGetSampledSize(l, n);
            }
            throw new IllegalStateException("ImageDecoder is closed!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sampleSize must be positive! provided ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean isDecodeAsAlphaMaskEnabled() {
        return this.mDecodeAsAlphaMask;
    }

    public boolean isMutableRequired() {
        return this.mMutable;
    }

    public boolean isUnpremultipliedRequired() {
        return this.mUnpremultipliedRequired;
    }

    public void setAllocator(int n) {
        if (n >= 0 && n <= 3) {
            this.mAllocator = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid allocator ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Deprecated
    public ImageDecoder setAsAlphaMask(boolean bl) {
        this.setDecodeAsAlphaMask(bl);
        return this;
    }

    @Deprecated
    public void setConserveMemory(boolean bl) {
        this.mConserveMemory = bl;
    }

    public void setCrop(Rect rect) {
        this.mCropRect = rect;
    }

    @Deprecated
    public ImageDecoder setDecodeAsAlphaMask(boolean bl) {
        this.setDecodeAsAlphaMaskEnabled(bl);
        return this;
    }

    public void setDecodeAsAlphaMaskEnabled(boolean bl) {
        this.mDecodeAsAlphaMask = bl;
    }

    public void setMemorySizePolicy(int n) {
        boolean bl = n == 0;
        this.mConserveMemory = bl;
    }

    @Deprecated
    public ImageDecoder setMutable(boolean bl) {
        this.setMutableRequired(bl);
        return this;
    }

    public void setMutableRequired(boolean bl) {
        this.mMutable = bl;
    }

    public void setOnPartialImageListener(OnPartialImageListener onPartialImageListener) {
        this.mOnPartialImageListener = onPartialImageListener;
    }

    public void setOutPaddingRect(Rect rect) {
        this.mOutPaddingRect = rect;
    }

    public void setPostProcessor(PostProcessor postProcessor) {
        this.mPostProcessor = postProcessor;
    }

    @Deprecated
    public ImageDecoder setRequireUnpremultiplied(boolean bl) {
        this.setUnpremultipliedRequired(bl);
        return this;
    }

    @Deprecated
    public ImageDecoder setResize(int n) {
        this.setTargetSampleSize(n);
        return this;
    }

    @Deprecated
    public ImageDecoder setResize(int n, int n2) {
        this.setTargetSize(n, n2);
        return this;
    }

    public void setTargetColorSpace(ColorSpace colorSpace) {
        this.mDesiredColorSpace = colorSpace;
    }

    public void setTargetSampleSize(int n) {
        Size size = this.getSampledSize(n);
        this.setTargetSize(this.getTargetDimension(this.mWidth, n, size.getWidth()), this.getTargetDimension(this.mHeight, n, size.getHeight()));
    }

    public void setTargetSize(int n, int n2) {
        if (n > 0 && n2 > 0) {
            this.mDesiredWidth = n;
            this.mDesiredHeight = n2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dimensions must be positive! provided (");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setUnpremultipliedRequired(boolean bl) {
        this.mUnpremultipliedRequired = bl;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Allocator {
    }

    public static class AssetInputStreamSource
    extends Source {
        private AssetManager.AssetInputStream mAssetInputStream;
        private final int mDensity;
        private final Resources mResources;

        public AssetInputStreamSource(AssetManager.AssetInputStream assetInputStream, Resources resources, TypedValue typedValue) {
            this.mAssetInputStream = assetInputStream;
            this.mResources = resources;
            this.mDensity = typedValue.density == 0 ? 160 : (typedValue.density != 65535 ? typedValue.density : 0);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            synchronized (this) {
                if (this.mAssetInputStream != null) {
                    AutoCloseable autoCloseable = this.mAssetInputStream;
                    this.mAssetInputStream = null;
                    return ImageDecoder.createFromAsset(autoCloseable, this);
                }
                IOException iOException = new IOException("Cannot reuse AssetInputStreamSource");
                throw iOException;
            }
        }

        @Override
        public int getDensity() {
            return this.mDensity;
        }

        @Override
        public Resources getResources() {
            return this.mResources;
        }
    }

    private static class AssetSource
    extends Source {
        private final AssetManager mAssets;
        private final String mFileName;

        AssetSource(AssetManager assetManager, String string2) {
            this.mAssets = assetManager;
            this.mFileName = string2;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            return ImageDecoder.createFromAsset((AssetManager.AssetInputStream)this.mAssets.open(this.mFileName), this);
        }
    }

    private static class ByteArraySource
    extends Source {
        private final byte[] mData;
        private final int mLength;
        private final int mOffset;

        ByteArraySource(byte[] arrby, int n, int n2) {
            this.mData = arrby;
            this.mOffset = n;
            this.mLength = n2;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            return ImageDecoder.nCreate(this.mData, this.mOffset, this.mLength, this);
        }
    }

    private static class ByteBufferSource
    extends Source {
        private final ByteBuffer mBuffer;

        ByteBufferSource(ByteBuffer byteBuffer) {
            this.mBuffer = byteBuffer;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            if (!this.mBuffer.isDirect() && this.mBuffer.hasArray()) {
                int n = this.mBuffer.arrayOffset();
                int n2 = this.mBuffer.position();
                int n3 = this.mBuffer.limit();
                int n4 = this.mBuffer.position();
                return ImageDecoder.nCreate(this.mBuffer.array(), n + n2, n3 - n4, this);
            }
            ByteBuffer byteBuffer = this.mBuffer.slice();
            return ImageDecoder.nCreate(byteBuffer, byteBuffer.position(), byteBuffer.limit(), this);
        }
    }

    private static class CallableSource
    extends Source {
        private final Callable<AssetFileDescriptor> mCallable;

        CallableSource(Callable<AssetFileDescriptor> callable) {
            this.mCallable = callable;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            AssetFileDescriptor assetFileDescriptor;
            try {
                assetFileDescriptor = this.mCallable.call();
            }
            catch (Exception exception) {
                if (exception instanceof IOException) {
                    throw (IOException)exception;
                }
                throw new IOException(exception);
            }
            return ImageDecoder.createFromAssetFileDescriptor(assetFileDescriptor, this);
        }
    }

    private static class ContentResolverSource
    extends Source {
        private final ContentResolver mResolver;
        private final Resources mResources;
        private final Uri mUri;

        ContentResolverSource(ContentResolver contentResolver, Uri uri, Resources resources) {
            this.mResolver = contentResolver;
            this.mUri = uri;
            this.mResources = resources;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            AssetFileDescriptor assetFileDescriptor;
            try {
                assetFileDescriptor = this.mUri.getScheme() == "content" ? this.mResolver.openTypedAssetFileDescriptor(this.mUri, "image/*", null) : this.mResolver.openAssetFileDescriptor(this.mUri, "r");
            }
            catch (FileNotFoundException fileNotFoundException) {
                InputStream inputStream = this.mResolver.openInputStream(this.mUri);
                if (inputStream != null) {
                    return ImageDecoder.createFromStream(inputStream, true, this);
                }
                throw new FileNotFoundException(this.mUri.toString());
            }
            return ImageDecoder.createFromAssetFileDescriptor(assetFileDescriptor, this);
        }

        @Override
        Resources getResources() {
            return this.mResources;
        }
    }

    public static final class DecodeException
    extends IOException {
        public static final int SOURCE_EXCEPTION = 1;
        public static final int SOURCE_INCOMPLETE = 2;
        public static final int SOURCE_MALFORMED_DATA = 3;
        final int mError;
        final Source mSource;

        DecodeException(int n, String string2, Throwable throwable, Source source) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(DecodeException.errorMessage(n, throwable));
            super(stringBuilder.toString(), throwable);
            this.mError = n;
            this.mSource = source;
        }

        DecodeException(int n, Throwable throwable, Source source) {
            super(DecodeException.errorMessage(n, throwable), throwable);
            this.mError = n;
            this.mSource = source;
        }

        private static String errorMessage(int n, Throwable throwable) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return "";
                    }
                    return "Input contained an error.";
                }
                return "Input was incomplete.";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception in input: ");
            stringBuilder.append(throwable);
            return stringBuilder.toString();
        }

        public int getError() {
            return this.mError;
        }

        public Source getSource() {
            return this.mSource;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Error {
        }

    }

    private static class FileSource
    extends Source {
        private final File mFile;

        FileSource(File file) {
            this.mFile = file;
        }

        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            return ImageDecoder.createFromFile(this.mFile, this);
        }
    }

    public static class ImageInfo {
        private ImageDecoder mDecoder;
        private final Size mSize;

        private ImageInfo(ImageDecoder imageDecoder) {
            this.mSize = new Size(imageDecoder.mWidth, imageDecoder.mHeight);
            this.mDecoder = imageDecoder;
        }

        public ColorSpace getColorSpace() {
            return this.mDecoder.getColorSpace();
        }

        public String getMimeType() {
            return this.mDecoder.getMimeType();
        }

        public Size getSize() {
            return this.mSize;
        }

        public boolean isAnimated() {
            return this.mDecoder.mAnimated;
        }
    }

    @Deprecated
    public static class IncompleteException
    extends IOException {
    }

    private static class InputStreamSource
    extends Source {
        final int mInputDensity;
        InputStream mInputStream;
        final Resources mResources;

        InputStreamSource(Resources resources, InputStream inputStream, int n) {
            if (inputStream != null) {
                this.mResources = resources;
                this.mInputStream = inputStream;
                this.mInputDensity = n;
                return;
            }
            throw new IllegalArgumentException("The InputStream cannot be null");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            synchronized (this) {
                if (this.mInputStream != null) {
                    AutoCloseable autoCloseable = this.mInputStream;
                    this.mInputStream = null;
                    return ImageDecoder.createFromStream(autoCloseable, false, this);
                }
                IOException iOException = new IOException("Cannot reuse InputStreamSource");
                throw iOException;
            }
        }

        @Override
        public int getDensity() {
            return this.mInputDensity;
        }

        @Override
        public Resources getResources() {
            return this.mResources;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MemoryPolicy {
    }

    public static interface OnHeaderDecodedListener {
        public void onHeaderDecoded(ImageDecoder var1, ImageInfo var2, Source var3);
    }

    public static interface OnPartialImageListener {
        public boolean onPartialImage(DecodeException var1);
    }

    private static class ResourceSource
    extends Source {
        private Object mLock = new Object();
        int mResDensity;
        final int mResId;
        final Resources mResources;

        ResourceSource(Resources resources, int n) {
            this.mResources = resources;
            this.mResId = n;
            this.mResDensity = 0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ImageDecoder createImageDecoder() throws IOException {
            TypedValue typedValue = new TypedValue();
            InputStream inputStream = this.mResources.openRawResource(this.mResId, typedValue);
            Object object = this.mLock;
            synchronized (object) {
                if (typedValue.density == 0) {
                    this.mResDensity = 160;
                } else if (typedValue.density != 65535) {
                    this.mResDensity = typedValue.density;
                }
                return ImageDecoder.createFromAsset((AssetManager.AssetInputStream)inputStream, this);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getDensity() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mResDensity;
            }
        }

        @Override
        public Resources getResources() {
            return this.mResources;
        }
    }

    public static abstract class Source {
        private Source() {
        }

        final int computeDstDensity() {
            Resources resources = this.getResources();
            if (resources == null) {
                return Bitmap.getDefaultDensity();
            }
            return resources.getDisplayMetrics().densityDpi;
        }

        abstract ImageDecoder createImageDecoder() throws IOException;

        int getDensity() {
            return 0;
        }

        Resources getResources() {
            return null;
        }
    }

}

