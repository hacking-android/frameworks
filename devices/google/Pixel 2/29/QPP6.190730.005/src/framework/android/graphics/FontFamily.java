/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.CriticalNative
 *  libcore.util.NativeAllocationRegistry
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.fonts.FontVariationAxis;
import android.text.TextUtils;
import dalvik.annotation.optimization.CriticalNative;
import java.nio.ByteBuffer;
import libcore.util.NativeAllocationRegistry;

@Deprecated
public class FontFamily {
    private static String TAG = "FontFamily";
    private static final NativeAllocationRegistry sBuilderRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)FontFamily.class.getClassLoader(), (long)FontFamily.nGetBuilderReleaseFunc());
    private static final NativeAllocationRegistry sFamilyRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)FontFamily.class.getClassLoader(), (long)FontFamily.nGetFamilyReleaseFunc());
    private long mBuilderPtr;
    private Runnable mNativeBuilderCleaner;
    @UnsupportedAppUsage(trackingBug=123768928L)
    public long mNativePtr;

    @UnsupportedAppUsage(trackingBug=123768928L)
    public FontFamily() {
        this.mBuilderPtr = FontFamily.nInitBuilder(null, 0);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation((Object)this, this.mBuilderPtr);
    }

    @UnsupportedAppUsage(trackingBug=123768928L)
    public FontFamily(String[] object, int n) {
        object = object != null && ((String[])object).length != 0 ? (((String[])object).length == 1 ? object[0] : TextUtils.join((CharSequence)",", object)) : null;
        this.mBuilderPtr = FontFamily.nInitBuilder((String)object, n);
        this.mNativeBuilderCleaner = sBuilderRegistry.registerNativeAllocation((Object)this, this.mBuilderPtr);
    }

    @CriticalNative
    private static native void nAddAxisValue(long var0, int var2, float var3);

    private static boolean nAddFont(long l, ByteBuffer byteBuffer, int n) {
        return FontFamily.nAddFont(l, byteBuffer, n, -1, -1);
    }

    private static native boolean nAddFont(long var0, ByteBuffer var2, int var3, int var4, int var5);

    private static native boolean nAddFontFromAssetManager(long var0, AssetManager var2, String var3, int var4, boolean var5, int var6, int var7, int var8);

    private static native boolean nAddFontWeightStyle(long var0, ByteBuffer var2, int var3, int var4, int var5);

    @CriticalNative
    private static native long nCreateFamily(long var0);

    @CriticalNative
    private static native long nGetBuilderReleaseFunc();

    @CriticalNative
    private static native long nGetFamilyReleaseFunc();

    private static native long nInitBuilder(String var0, int var1);

    @UnsupportedAppUsage(trackingBug=123768928L)
    public void abortCreation() {
        if (this.mBuilderPtr != 0L) {
            this.mNativeBuilderCleaner.run();
            this.mBuilderPtr = 0L;
            return;
        }
        throw new IllegalStateException("This FontFamily is already frozen or abandoned");
    }

    /*
     * Exception decompiling
     */
    @UnsupportedAppUsage(trackingBug=123768928L)
    public boolean addFont(String var1_1, int var2_2, FontVariationAxis[] var3_3, int var4_7, int var5_8) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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

    @UnsupportedAppUsage(trackingBug=123768928L)
    public boolean addFontFromAssetManager(AssetManager assetManager, String string2, int n, boolean bl, int n2, int n3, int n4, FontVariationAxis[] arrfontVariationAxis) {
        if (this.mBuilderPtr != 0L) {
            if (arrfontVariationAxis != null) {
                for (FontVariationAxis fontVariationAxis : arrfontVariationAxis) {
                    FontFamily.nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                }
            }
            return FontFamily.nAddFontFromAssetManager(this.mBuilderPtr, assetManager, string2, n, bl, n2, n3, n4);
        }
        throw new IllegalStateException("Unable to call addFontFromAsset after freezing.");
    }

    @UnsupportedAppUsage(trackingBug=123768928L)
    public boolean addFontFromBuffer(ByteBuffer byteBuffer, int n, FontVariationAxis[] arrfontVariationAxis, int n2, int n3) {
        if (this.mBuilderPtr != 0L) {
            if (arrfontVariationAxis != null) {
                for (FontVariationAxis fontVariationAxis : arrfontVariationAxis) {
                    FontFamily.nAddAxisValue(this.mBuilderPtr, fontVariationAxis.getOpenTypeTagValue(), fontVariationAxis.getStyleValue());
                }
            }
            return FontFamily.nAddFontWeightStyle(this.mBuilderPtr, byteBuffer, n, n2, n3);
        }
        throw new IllegalStateException("Unable to call addFontWeightStyle after freezing.");
    }

    @UnsupportedAppUsage(trackingBug=123768928L)
    public boolean freeze() {
        long l = this.mBuilderPtr;
        if (l != 0L) {
            this.mNativePtr = FontFamily.nCreateFamily(l);
            this.mNativeBuilderCleaner.run();
            this.mBuilderPtr = 0L;
            l = this.mNativePtr;
            if (l != 0L) {
                sFamilyRegistry.registerNativeAllocation((Object)this, l);
            }
            boolean bl = this.mNativePtr != 0L;
            return bl;
        }
        throw new IllegalStateException("This FontFamily is already frozen");
    }
}

