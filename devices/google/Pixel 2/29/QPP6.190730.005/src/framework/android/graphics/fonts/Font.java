/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics.fonts;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.fonts.FontFileUtil;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.util.TypedValue;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

public final class Font {
    private static final int NOT_SPECIFIED = -1;
    private static final int STYLE_ITALIC = 1;
    private static final int STYLE_NORMAL = 0;
    private static final String TAG = "Font";
    private final FontVariationAxis[] mAxes;
    private final ByteBuffer mBuffer;
    private final File mFile;
    private final FontStyle mFontStyle;
    private final String mLocaleList;
    private final long mNativePtr;
    private final int mTtcIndex;

    private Font(long l, ByteBuffer byteBuffer, File file, FontStyle fontStyle, int n, FontVariationAxis[] arrfontVariationAxis, String string2) {
        this.mBuffer = byteBuffer;
        this.mFile = file;
        this.mFontStyle = fontStyle;
        this.mNativePtr = l;
        this.mTtcIndex = n;
        this.mAxes = arrfontVariationAxis;
        this.mLocaleList = string2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object != null && object instanceof Font) {
            object = (Font)object;
            if (!(this.mFontStyle.equals(((Font)object).mFontStyle) && ((Font)object).mTtcIndex == this.mTtcIndex && Arrays.equals(((Font)object).mAxes, this.mAxes) && ((Font)object).mBuffer.equals(this.mBuffer))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public FontVariationAxis[] getAxes() {
        Object object = this.mAxes;
        object = object == null ? null : (FontVariationAxis[])object.clone();
        return object;
    }

    public ByteBuffer getBuffer() {
        return this.mBuffer;
    }

    public File getFile() {
        return this.mFile;
    }

    public LocaleList getLocaleList() {
        return LocaleList.forLanguageTags(this.mLocaleList);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public FontStyle getStyle() {
        return this.mFontStyle;
    }

    public int getTtcIndex() {
        return this.mTtcIndex;
    }

    public int hashCode() {
        return Objects.hash(this.mFontStyle, this.mTtcIndex, Arrays.hashCode(this.mAxes), this.mBuffer);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Font {path=");
        stringBuilder.append(this.mFile);
        stringBuilder.append(", style=");
        stringBuilder.append(this.mFontStyle);
        stringBuilder.append(", ttcIndex=");
        stringBuilder.append(this.mTtcIndex);
        stringBuilder.append(", axes=");
        stringBuilder.append(FontVariationAxis.toFontVariationSettings(this.mAxes));
        stringBuilder.append(", localeList=");
        stringBuilder.append(this.mLocaleList);
        stringBuilder.append(", buffer=");
        stringBuilder.append(this.mBuffer);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final class Builder {
        private static final NativeAllocationRegistry sAssetByteBufferRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)ByteBuffer.class.getClassLoader(), (long)Builder.nGetReleaseNativeAssetFunc());
        private static final NativeAllocationRegistry sFontRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Font.class.getClassLoader(), (long)Builder.nGetReleaseNativeFont());
        private FontVariationAxis[] mAxes;
        private ByteBuffer mBuffer;
        private IOException mException;
        private File mFile;
        private int mItalic;
        private String mLocaleList;
        private int mTtcIndex;
        private int mWeight;

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

        public Builder(AssetManager assetManager, String string2) {
            this(assetManager, string2, true, 0);
        }

        public Builder(AssetManager object, String string2, boolean bl, int n) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            long l = Builder.nGetNativeAsset((AssetManager)object, string2, bl, n);
            if (l == 0L) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to open ");
                ((StringBuilder)object).append(string2);
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            object = Builder.nGetAssetBuffer(l);
            sAssetByteBufferRegistry.registerNativeAllocation(object, l);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" not found");
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            this.mBuffer = object;
        }

        public Builder(Resources object, int n) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            TypedValue typedValue = new TypedValue();
            ((Resources)object).getValue(n, typedValue, true);
            if (typedValue.string == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" not found");
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            String string2 = typedValue.string.toString();
            if (string2.toLowerCase().endsWith(".xml")) {
                object = new StringBuilder();
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" must be font file.");
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            long l = Builder.nGetNativeAsset(((Resources)object).getAssets(), string2, false, typedValue.assetCookie);
            if (l == 0L) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to open ");
                ((StringBuilder)object).append(string2);
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            object = Builder.nGetAssetBuffer(l);
            sAssetByteBufferRegistry.registerNativeAllocation(object, l);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" not found");
                this.mException = new FileNotFoundException(((StringBuilder)object).toString());
                return;
            }
            this.mBuffer = object;
        }

        public Builder(ParcelFileDescriptor parcelFileDescriptor) {
            this(parcelFileDescriptor, 0L, -1L);
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public Builder(ParcelFileDescriptor closeable, long l, long l2) {
            void var1_6;
            block13 : {
                FileInputStream fileInputStream;
                block12 : {
                    block11 : {
                        this.mLocaleList = "";
                        this.mWeight = -1;
                        this.mItalic = -1;
                        this.mTtcIndex = 0;
                        this.mAxes = null;
                        fileInputStream = new FileInputStream(((ParcelFileDescriptor)closeable).getFileDescriptor());
                        closeable = fileInputStream.getChannel();
                        if (l2 != -1L) break block11;
                        l2 = ((FileChannel)closeable).size();
                        l2 -= l;
                    }
                    try {
                        this.mBuffer = ((FileChannel)closeable).map(FileChannel.MapMode.READ_ONLY, l, l2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        Builder.$closeResource(null, fileInputStream);
                        return;
                    }
                    catch (IOException iOException) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                try {
                    throw closeable;
                }
                catch (Throwable throwable) {
                    try {
                        Builder.$closeResource((Throwable)((Object)closeable), fileInputStream);
                        throw throwable;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
            this.mException = var1_6;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public Builder(File file) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            Preconditions.checkNotNull(file, "path can not be null");
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();
            this.mBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fileChannel.size());
            Builder.$closeResource(null, fileInputStream);
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        Builder.$closeResource(throwable, fileInputStream);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        this.mException = iOException;
                    }
                }
            }
            this.mFile = file;
        }

        public Builder(ByteBuffer byteBuffer) {
            this.mLocaleList = "";
            this.mWeight = -1;
            this.mItalic = -1;
            this.mTtcIndex = 0;
            this.mAxes = null;
            Preconditions.checkNotNull(byteBuffer, "buffer can not be null");
            if (byteBuffer.isDirect()) {
                this.mBuffer = byteBuffer;
                return;
            }
            throw new IllegalArgumentException("Only direct buffer can be used as the source of font data.");
        }

        public Builder(ByteBuffer byteBuffer, File file, String string2) {
            this(byteBuffer);
            this.mFile = file;
            this.mLocaleList = string2;
        }

        @CriticalNative
        private static native void nAddAxis(long var0, int var2, float var3);

        private static native long nBuild(long var0, ByteBuffer var2, String var3, int var4, boolean var5, int var6);

        private static native ByteBuffer nGetAssetBuffer(long var0);

        private static native long nGetNativeAsset(AssetManager var0, String var1, boolean var2, int var3);

        @CriticalNative
        private static native long nGetReleaseNativeAssetFunc();

        @CriticalNative
        private static native long nGetReleaseNativeFont();

        private static native long nInitBuilder();

        public Font build() throws IOException {
            Object object = this.mException;
            if (object == null) {
                int n = this.mWeight;
                int n2 = 0;
                if (n == -1 || this.mItalic == -1) {
                    n = FontFileUtil.analyzeStyle(this.mBuffer, this.mTtcIndex, this.mAxes);
                    if (FontFileUtil.isSuccess(n)) {
                        if (this.mWeight == -1) {
                            this.mWeight = FontFileUtil.unpackWeight(n);
                        }
                        if (this.mItalic == -1) {
                            this.mItalic = FontFileUtil.unpackItalic(n) ? 1 : 0;
                        }
                    } else {
                        this.mWeight = 400;
                        this.mItalic = 0;
                    }
                }
                int n3 = Math.min(1000, this.mWeight);
                n = 1;
                this.mWeight = Math.max(1, n3);
                boolean bl = this.mItalic == 1;
                if (this.mItalic != 1) {
                    n = 0;
                }
                long l = Builder.nInitBuilder();
                Object object2 = this.mAxes;
                if (object2 != null) {
                    n3 = ((FontVariationAxis[])object2).length;
                    while (n2 < n3) {
                        object = object2[n2];
                        Builder.nAddAxis(l, ((FontVariationAxis)object).getOpenTypeTagValue(), ((FontVariationAxis)object).getStyleValue());
                        ++n2;
                    }
                }
                object2 = this.mBuffer.asReadOnlyBuffer();
                object = this.mFile;
                object = object == null ? "" : ((File)object).getAbsolutePath();
                l = Builder.nBuild(l, (ByteBuffer)object2, (String)object, this.mWeight, bl, this.mTtcIndex);
                object = new Font(l, (ByteBuffer)object2, this.mFile, new FontStyle(this.mWeight, n), this.mTtcIndex, this.mAxes, this.mLocaleList);
                sFontRegistry.registerNativeAllocation(object, l);
                return object;
            }
            throw new IOException("Failed to read font contents", (Throwable)object);
        }

        public Builder setFontVariationSettings(String string2) {
            this.mAxes = FontVariationAxis.fromFontVariationSettings(string2);
            return this;
        }

        public Builder setFontVariationSettings(FontVariationAxis[] object) {
            object = object == null ? null : (FontVariationAxis[])object.clone();
            this.mAxes = object;
            return this;
        }

        public Builder setSlant(int n) {
            n = n == 0 ? 0 : 1;
            this.mItalic = n;
            return this;
        }

        public Builder setTtcIndex(int n) {
            this.mTtcIndex = n;
            return this;
        }

        public Builder setWeight(int n) {
            boolean bl = true;
            if (1 > n || n > 1000) {
                bl = false;
            }
            Preconditions.checkArgument(bl);
            this.mWeight = n;
            return this;
        }
    }

}

