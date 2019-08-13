/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapFactory {
    private static final int DECODE_BUFFER_SIZE = 16384;

    public static Bitmap decodeByteArray(byte[] arrby, int n, int n2) {
        return BitmapFactory.decodeByteArray(arrby, n, n2, null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bitmap decodeByteArray(byte[] object, int n, int n2, Options options) {
        void var2_5;
        void var1_4;
        void var3_6;
        if ((var1_4 | var2_5) < 0) throw new ArrayIndexOutOfBoundsException();
        if (((byte[])object).length < var1_4 + var2_5) throw new ArrayIndexOutOfBoundsException();
        Options.validate((Options)var3_6);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            Bitmap bitmap = BitmapFactory.nativeDecodeByteArray(object, (int)var1_4, (int)var2_5, (Options)var3_6, Options.nativeInBitmap((Options)var3_6), Options.nativeColorSpace((Options)var3_6));
            if (bitmap == null && var3_6 != null && var3_6.inBitmap != null) {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Problem decoding into existing bitmap");
                throw illegalArgumentException;
            }
            BitmapFactory.setDensityFromOptions(bitmap, (Options)var3_6);
            return bitmap;
        }
        finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeFile(String string2) {
        return BitmapFactory.decodeFile(string2, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Bitmap decodeFile(String object, Options object2) {
        Throwable throwable2222;
        Options.validate((Options)object2);
        Object var2_6 = null;
        Object var3_7 = null;
        Object var4_8 = null;
        InputStream inputStream = null;
        InputStream inputStream2 = null;
        Object object3 = inputStream2;
        Object object4 = inputStream;
        object3 = inputStream2;
        object4 = inputStream;
        FileInputStream fileInputStream = new FileInputStream((String)object);
        object3 = object = fileInputStream;
        object4 = object;
        object2 = object4 = (object2 = BitmapFactory.decodeStream((InputStream)object, null, (Options)object2));
        ((InputStream)object).close();
        return object4;
        {
            catch (IOException iOException) {
                return object2;
            }
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            object3 = object4;
            {
                object3 = object4;
                object = new StringBuilder();
                object3 = object4;
                ((StringBuilder)object).append("Unable to decode stream: ");
                object3 = object4;
                ((StringBuilder)object).append(exception);
                object3 = object4;
                Log.e("BitmapFactory", ((StringBuilder)object).toString());
                object = var3_7;
                if (object4 == null) return object;
                object2 = var2_6;
            }
            {
                ((InputStream)object4).close();
                return var4_8;
            }
        }
        if (object3 == null) throw throwable2222;
        try {
            ((InputStream)object3).close();
            throw throwable2222;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw throwable2222;
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Bitmap decodeFileDescriptor(FileDescriptor var0, Rect var1_3, Options var2_6) {
        block13 : {
            Options.validate(var2_6);
            Trace.traceBegin(2L, "decodeFileDescriptor");
            if (BitmapFactory.nativeIsSeekable((FileDescriptor)var0)) {
                var0 = BitmapFactory.nativeDecodeFileDescriptor((FileDescriptor)var0, var1_3, var2_6, Options.nativeInBitmap(var2_6), Options.nativeColorSpace(var2_6));
                break block13;
            }
            var3_7 = new FileInputStream((FileDescriptor)var0);
            {
                catch (Throwable var0_2) {
                    throw var0_2;
                }
            }
            try {
                var0 = BitmapFactory.decodeStreamInternal(var3_7, var1_3, var2_6);
            }
            catch (Throwable var0_1) {
                try {
                    var3_7.close();
                }
                catch (Throwable var1_5) {}
                throw var0_1;
            }
            try {
                var3_7.close();
            }
            catch (Throwable var1_4) {
                // empty catch block
            }
        }
        if (var0 != null || var2_6 == null) ** GOTO lbl30
        try {
            if (var2_6.inBitmap != null) {
                var0 = new IllegalArgumentException("Problem decoding into existing bitmap");
                throw var0;
            }
lbl30: // 3 sources:
            BitmapFactory.setDensityFromOptions((Bitmap)var0, var2_6);
            return var0;
        }
        finally {
            Trace.traceEnd(2L);
        }
    }

    public static Bitmap decodeResource(Resources resources, int n) {
        return BitmapFactory.decodeResource(resources, n, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Bitmap decodeResource(Resources object, int n, Options options) {
        block8 : {
            InputStream inputStream;
            Options.validate(options);
            Object var3_6 = null;
            Object var4_7 = null;
            InputStream inputStream2 = null;
            InputStream inputStream3 = inputStream = null;
            Object object2 = inputStream2;
            inputStream3 = inputStream;
            object2 = inputStream2;
            TypedValue typedValue = new TypedValue();
            inputStream3 = inputStream;
            object2 = inputStream2;
            inputStream3 = inputStream = ((Resources)object).openRawResource(n, typedValue);
            object2 = inputStream;
            object = object2 = (object = BitmapFactory.decodeResourceStream((Resources)object, typedValue, inputStream, null, options));
            if (inputStream == null) break block8;
            object = object2;
            inputStream.close();
            object = object2;
            {
                catch (IOException iOException) {}
            }
            catch (Throwable throwable) {
                if (inputStream3 == null) throw throwable;
                try {
                    inputStream3.close();
                    throw throwable;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                throw throwable;
            }
            catch (Exception exception) {
                object = var3_6;
                if (object2 == null) break block8;
                object = var4_7;
                ((InputStream)object2).close();
                object = var3_6;
            }
        }
        if (object != null) return object;
        if (options == null) return object;
        if (options.inBitmap != null) throw new IllegalArgumentException("Problem decoding into existing bitmap");
        return object;
    }

    public static Bitmap decodeResourceStream(Resources resources, TypedValue typedValue, InputStream inputStream, Rect rect, Options options) {
        Options.validate(options);
        Options options2 = options;
        if (options == null) {
            options2 = new Options();
        }
        if (options2.inDensity == 0 && typedValue != null) {
            int n = typedValue.density;
            if (n == 0) {
                options2.inDensity = 160;
            } else if (n != 65535) {
                options2.inDensity = n;
            }
        }
        if (options2.inTargetDensity == 0 && resources != null) {
            options2.inTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        return BitmapFactory.decodeStream(inputStream, rect, options2);
    }

    public static Bitmap decodeStream(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream, null, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bitmap decodeStream(InputStream object, Rect rect, Options options) {
        if (object == null) {
            return null;
        }
        Options.validate(options);
        Trace.traceBegin(2L, "decodeBitmap");
        try {
            object = object instanceof AssetManager.AssetInputStream ? BitmapFactory.nativeDecodeAsset(((AssetManager.AssetInputStream)object).getNativeAsset(), rect, options, Options.nativeInBitmap(options), Options.nativeColorSpace(options)) : BitmapFactory.decodeStreamInternal((InputStream)object, rect, options);
            if (object == null && options != null && options.inBitmap != null) {
                object = new IllegalArgumentException("Problem decoding into existing bitmap");
                throw object;
            }
            BitmapFactory.setDensityFromOptions((Bitmap)object, options);
            return object;
        }
        finally {
            Trace.traceEnd(2L);
        }
    }

    private static Bitmap decodeStreamInternal(InputStream inputStream, Rect rect, Options options) {
        byte[] arrby = null;
        if (options != null) {
            arrby = options.inTempStorage;
        }
        byte[] arrby2 = arrby;
        if (arrby == null) {
            arrby2 = new byte[16384];
        }
        return BitmapFactory.nativeDecodeStream(inputStream, arrby2, rect, options, Options.nativeInBitmap(options), Options.nativeColorSpace(options));
    }

    @UnsupportedAppUsage
    private static native Bitmap nativeDecodeAsset(long var0, Rect var2, Options var3, long var4, long var6);

    @UnsupportedAppUsage
    private static native Bitmap nativeDecodeByteArray(byte[] var0, int var1, int var2, Options var3, long var4, long var6);

    @UnsupportedAppUsage
    private static native Bitmap nativeDecodeFileDescriptor(FileDescriptor var0, Rect var1, Options var2, long var3, long var5);

    @UnsupportedAppUsage
    private static native Bitmap nativeDecodeStream(InputStream var0, byte[] var1, Rect var2, Options var3, long var4, long var6);

    private static native boolean nativeIsSeekable(FileDescriptor var0);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void setDensityFromOptions(Bitmap bitmap, Options options) {
        if (bitmap == null || options == null) return;
        int n = options.inDensity;
        if (n != 0) {
            bitmap.setDensity(n);
            int n2 = options.inTargetDensity;
            if (n2 == 0 || n == n2 || n == options.inScreenDensity) return;
            byte[] arrby = bitmap.getNinePatchChunk();
            n = arrby != null && NinePatch.isNinePatchChunk(arrby) ? 1 : 0;
            if (!options.inScaled && n == 0) return;
            bitmap.setDensity(n2);
            return;
        } else {
            if (options.inBitmap == null) return;
            bitmap.setDensity(Bitmap.getDefaultDensity());
        }
    }

    public static class Options {
        public Bitmap inBitmap;
        public int inDensity;
        public boolean inDither;
        @Deprecated
        public boolean inInputShareable;
        public boolean inJustDecodeBounds;
        public boolean inMutable;
        @Deprecated
        public boolean inPreferQualityOverSpeed;
        public ColorSpace inPreferredColorSpace = null;
        public Bitmap.Config inPreferredConfig = Bitmap.Config.ARGB_8888;
        public boolean inPremultiplied = true;
        @Deprecated
        public boolean inPurgeable;
        public int inSampleSize;
        public boolean inScaled = true;
        public int inScreenDensity;
        public int inTargetDensity;
        public byte[] inTempStorage;
        @Deprecated
        public boolean mCancel;
        public ColorSpace outColorSpace;
        public Bitmap.Config outConfig;
        public int outHeight;
        public String outMimeType;
        public int outWidth;

        static long nativeColorSpace(Options object) {
            if (object != null && (object = ((Options)object).inPreferredColorSpace) != null) {
                return ((ColorSpace)object).getNativeInstance();
            }
            return 0L;
        }

        static long nativeInBitmap(Options object) {
            if (object != null && (object = ((Options)object).inBitmap) != null) {
                return ((Bitmap)object).getNativeInstance();
            }
            return 0L;
        }

        static void validate(Options object) {
            if (object == null) {
                return;
            }
            Bitmap bitmap = ((Options)object).inBitmap;
            if (bitmap != null) {
                if (bitmap.getConfig() != Bitmap.Config.HARDWARE) {
                    if (((Options)object).inBitmap.isRecycled()) {
                        throw new IllegalArgumentException("Cannot reuse a recycled Bitmap");
                    }
                } else {
                    throw new IllegalArgumentException("Bitmaps with Config.HARDWARE are always immutable");
                }
            }
            if (((Options)object).inMutable && ((Options)object).inPreferredConfig == Bitmap.Config.HARDWARE) {
                throw new IllegalArgumentException("Bitmaps with Config.HARDWARE cannot be decoded into - they are immutable");
            }
            object = ((Options)object).inPreferredColorSpace;
            if (object != null) {
                if (object instanceof ColorSpace.Rgb) {
                    if (((ColorSpace.Rgb)object).getTransferParameters() == null) {
                        throw new IllegalArgumentException("The destination color space must use an ICC parametric transfer function");
                    }
                } else {
                    throw new IllegalArgumentException("The destination color space must use the RGB color model");
                }
            }
        }

        @Deprecated
        public void requestCancelDecode() {
            this.mCancel = true;
        }
    }

}

