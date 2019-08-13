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
import android.content.res.FontResourcesParser;
import android.graphics.FontFamily;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.graphics.fonts.SystemFonts;
import android.os.ParcelFileDescriptor;
import android.provider.FontRequest;
import android.provider.FontsContract;
import android.text.FontConfig;
import android.util.Base64;
import android.util.LongSparseArray;
import android.util.LruCache;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import dalvik.annotation.optimization.CriticalNative;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libcore.util.NativeAllocationRegistry;

public class Typeface {
    public static final int BOLD = 1;
    public static final int BOLD_ITALIC = 3;
    public static final Typeface DEFAULT;
    public static final Typeface DEFAULT_BOLD;
    private static final String DEFAULT_FAMILY = "sans-serif";
    private static final int[] EMPTY_AXES;
    public static final int ITALIC = 2;
    public static final Typeface MONOSPACE;
    public static final int NORMAL = 0;
    public static final int RESOLVE_BY_FONT_TABLE = -1;
    public static final Typeface SANS_SERIF;
    public static final Typeface SERIF;
    private static final int STYLE_ITALIC = 1;
    public static final int STYLE_MASK = 3;
    private static final int STYLE_NORMAL = 0;
    private static String TAG;
    static Typeface sDefaultTypeface;
    @UnsupportedAppUsage(trackingBug=123769446L)
    static Typeface[] sDefaults;
    private static final Object sDynamicCacheLock;
    @GuardedBy(value={"sDynamicCacheLock"})
    private static final LruCache<String, Typeface> sDynamicTypefaceCache;
    private static final NativeAllocationRegistry sRegistry;
    private static final Object sStyledCacheLock;
    @GuardedBy(value={"sStyledCacheLock"})
    private static final LongSparseArray<SparseArray<Typeface>> sStyledTypefaceCache;
    @Deprecated
    @UnsupportedAppUsage(trackingBug=123768928L)
    static final Map<String, FontFamily[]> sSystemFallbackMap;
    @UnsupportedAppUsage(trackingBug=123769347L)
    static final Map<String, Typeface> sSystemFontMap;
    private static final Object sWeightCacheLock;
    @GuardedBy(value={"sWeightCacheLock"})
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache;
    @UnsupportedAppUsage
    private int mStyle = 0;
    private int[] mSupportedAxes;
    private int mWeight = 0;
    @UnsupportedAppUsage
    public long native_instance;

    static {
        TAG = "Typeface";
        sRegistry = NativeAllocationRegistry.createMalloced((ClassLoader)Typeface.class.getClassLoader(), (long)Typeface.nativeGetReleaseFunc());
        sStyledTypefaceCache = new LongSparseArray(3);
        sStyledCacheLock = new Object();
        sWeightTypefaceCache = new LongSparseArray(3);
        sWeightCacheLock = new Object();
        sDynamicTypefaceCache = new LruCache(16);
        sDynamicCacheLock = new Object();
        sSystemFallbackMap = Collections.emptyMap();
        int n = 0;
        EMPTY_AXES = new int[0];
        HashMap<String, Typeface> hashMap = new HashMap<String, Typeface>();
        Typeface.initSystemDefaultTypefaces(hashMap, SystemFonts.getRawSystemFallbackMap(), SystemFonts.getAliases());
        sSystemFontMap = Collections.unmodifiableMap(hashMap);
        if (sSystemFontMap.containsKey(DEFAULT_FAMILY)) {
            Typeface.setDefault(sSystemFontMap.get(DEFAULT_FAMILY));
        }
        String string2 = null;
        DEFAULT = Typeface.create(string2, 0);
        DEFAULT_BOLD = Typeface.create(string2, 1);
        SANS_SERIF = Typeface.create(DEFAULT_FAMILY, 0);
        SERIF = Typeface.create("serif", 0);
        MONOSPACE = Typeface.create("monospace", 0);
        sDefaults = new Typeface[]{DEFAULT, DEFAULT_BOLD, Typeface.create(string2, 2), Typeface.create(string2, 3)};
        String[] arrstring = new String[]{"serif", DEFAULT_FAMILY, "cursive", "fantasy", "monospace", "system-ui"};
        int n2 = arrstring.length;
        while (n < n2) {
            string2 = arrstring[n];
            Typeface.registerGenericFamilyNative(string2, hashMap.get(string2));
            ++n;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Typeface(long l) {
        if (l != 0L) {
            this.native_instance = l;
            sRegistry.registerNativeAllocation((Object)this, this.native_instance);
            this.mStyle = Typeface.nativeGetStyle(l);
            this.mWeight = Typeface.nativeGetWeight(l);
            return;
        }
        throw new RuntimeException("native typeface cannot be made");
    }

    static /* synthetic */ Object access$500() {
        return sDynamicCacheLock;
    }

    static /* synthetic */ LruCache access$600() {
        return sDynamicTypefaceCache;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface create(Typeface sparseArray, int n) {
        int n2 = n;
        if ((n & -4) != 0) {
            n2 = 0;
        }
        Object object = sparseArray;
        if (sparseArray == null) {
            object = sDefaultTypeface;
        }
        if (((Typeface)object).mStyle == n2) {
            return object;
        }
        long l = ((Typeface)object).native_instance;
        object = sStyledCacheLock;
        synchronized (object) {
            Typeface typeface;
            sparseArray = sStyledTypefaceCache.get(l);
            if (sparseArray == null) {
                sparseArray = new SparseArray<Typeface>(4);
                sStyledTypefaceCache.put(l, sparseArray);
            } else {
                typeface = (Typeface)sparseArray.get(n2);
                if (typeface != null) {
                    return typeface;
                }
            }
            typeface = new Typeface(Typeface.nativeCreateFromTypeface(l, n2));
            sparseArray.put(n2, typeface);
            return typeface;
        }
    }

    public static Typeface create(Typeface typeface, int n, boolean bl) {
        Preconditions.checkArgumentInRange(n, 0, 1000, "weight");
        Typeface typeface2 = typeface;
        if (typeface == null) {
            typeface2 = sDefaultTypeface;
        }
        return Typeface.createWeightStyle(typeface2, n, bl);
    }

    public static Typeface create(String string2, int n) {
        return Typeface.create(Typeface.getSystemDefaultTypeface(string2), n);
    }

    public static Typeface createFromAsset(AssetManager autoCloseable, String string2) {
        block4 : {
            Preconditions.checkNotNull(string2);
            Preconditions.checkNotNull(autoCloseable);
            Typeface typeface = new Builder((AssetManager)autoCloseable, string2).build();
            if (typeface != null) {
                return typeface;
            }
            try {
                autoCloseable = ((AssetManager)autoCloseable).open(string2);
                if (autoCloseable == null) break block4;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Font asset not found ");
                stringBuilder.append(string2);
                throw new RuntimeException(stringBuilder.toString());
            }
            ((InputStream)autoCloseable).close();
        }
        return DEFAULT;
    }

    @Deprecated
    @UnsupportedAppUsage(trackingBug=123768928L)
    private static Typeface createFromFamilies(FontFamily[] arrfontFamily) {
        long[] arrl = new long[arrfontFamily.length];
        for (int i = 0; i < arrfontFamily.length; ++i) {
            arrl[i] = arrfontFamily[i].mNativePtr;
        }
        return new Typeface(Typeface.nativeCreateFromArray(arrl, -1, -1));
    }

    private static Typeface createFromFamilies(android.graphics.fonts.FontFamily[] arrfontFamily) {
        long[] arrl = new long[arrfontFamily.length];
        for (int i = 0; i < arrfontFamily.length; ++i) {
            arrl[i] = arrfontFamily[i].getNativePtr();
        }
        return new Typeface(Typeface.nativeCreateFromArray(arrl, -1, -1));
    }

    @Deprecated
    @UnsupportedAppUsage(trackingBug=123768395L)
    private static Typeface createFromFamiliesWithDefault(FontFamily[] arrfontFamily, int n, int n2) {
        return Typeface.createFromFamiliesWithDefault(arrfontFamily, DEFAULT_FAMILY, n, n2);
    }

    @Deprecated
    @UnsupportedAppUsage(trackingBug=123768928L)
    private static Typeface createFromFamiliesWithDefault(FontFamily[] arrfontFamily, String arrfontFamily2, int n, int n2) {
        int n3;
        arrfontFamily2 = SystemFonts.getSystemFallback((String)arrfontFamily2);
        long[] arrl = new long[arrfontFamily.length + arrfontFamily2.length];
        for (n3 = 0; n3 < arrfontFamily.length; ++n3) {
            arrl[n3] = arrfontFamily[n3].mNativePtr;
        }
        for (n3 = 0; n3 < arrfontFamily2.length; ++n3) {
            arrl[arrfontFamily.length + n3] = arrfontFamily2[n3].getNativePtr();
        }
        return new Typeface(Typeface.nativeCreateFromArray(arrl, n, n2));
    }

    public static Typeface createFromFile(File file) {
        Object object = new Builder(file).build();
        if (object != null) {
            return object;
        }
        if (file.exists()) {
            return DEFAULT;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Font asset not found ");
        ((StringBuilder)object).append(file.getAbsolutePath());
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public static Typeface createFromFile(String string2) {
        Preconditions.checkNotNull(string2);
        return Typeface.createFromFile(new File(string2));
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static Typeface createFromResources(FontResourcesParser.FamilyResourceEntry var0, AssetManager var1_3, String var2_4) {
        block18 : {
            if (!(var0 instanceof FontResourcesParser.ProviderResourceEntry)) break block18;
            var1_3 = (FontResourcesParser.ProviderResourceEntry)var0;
            var2_4 = var1_3.getCerts();
            var0 = new ArrayList<E>();
            if (var2_4 == null) ** GOTO lbl24
            ** GOTO lbl14
        }
        var5_10 = Typeface.findFromCache((AssetManager)var1_3, var2_4);
        if (var5_10 != null) {
            return var5_10;
        }
        var0 = (FontResourcesParser.FontFamilyFilesResourceEntry)var0;
        try {
            block19 : {
                break block19;
lbl14: // 2 sources:
                for (var3_5 = 0; var3_5 < var2_4.size(); ++var3_5) {
                    var4_7 = var2_4.get(var3_5);
                    var5_9 = new ArrayList<byte[]>();
                    for (var6_11 = 0; var6_11 < var4_7.size(); ++var6_11) {
                        var5_9.add(Base64.decode(var4_7.get(var6_11), 0));
                    }
                    var0.add(var5_9);
                }
lbl24: // 2 sources:
                if ((var0 = FontsContract.getFontSync(new FontRequest(var1_3.getAuthority(), var1_3.getPackage(), var1_3.getQuery(), (List<List<byte[]>>)var0))) != null) return var0;
                return Typeface.DEFAULT;
            }
            var5_10 = var0.getEntries();
            var7_13 = ((FontResourcesParser.FontFileResourceEntry[])var5_10).length;
            var0 = null;
            var3_6 = 0;
            do {
                var6_12 = 1;
                var8_14 = 1;
                if (var3_6 >= var7_13) break;
                var4_8 = var5_10[var3_6];
                var9_15 = new Font.Builder((AssetManager)var1_3, var4_8.getFileName(), false, 0);
                var9_15 = var9_15.setTtcIndex(var4_8.getTtcIndex()).setFontVariationSettings(var4_8.getVariationSettings());
                if (var4_8.getWeight() != -1) {
                    var9_15.setWeight(var4_8.getWeight());
                }
                if (var4_8.getItalic() != -1) {
                    var6_12 = var4_8.getItalic() == 1 ? var8_14 : 0;
                    var9_15.setSlant(var6_12);
                }
                if (var0 == null) {
                    var0 = new FontFamily.Builder(var9_15.build());
                } else {
                    var0.addFont(var9_15.build());
                }
                ++var3_6;
            } while (true);
            if (var0 == null) {
                return Typeface.DEFAULT;
            }
            var4_8 = var0.build();
            var9_15 = new FontStyle(400, 0);
            var0 = var4_8.getFont(0);
            var8_14 = var9_15.getMatchScore(var0.getStyle());
            for (var3_6 = var6_12; var3_6 < var4_8.getSize(); ++var3_6) {
                var5_10 = var4_8.getFont(var3_6);
                var7_13 = var9_15.getMatchScore(var5_10.getStyle());
                var6_12 = var8_14;
                if (var7_13 < var8_14) {
                    var0 = var5_10;
                    var6_12 = var7_13;
                }
                var8_14 = var6_12;
            }
            var5_10 = new CustomFallbackBuilder((android.graphics.fonts.FontFamily)var4_8);
            var0 = var5_10.setStyle(var0.getStyle()).build();
        }
        catch (IOException var0_1) {
            var0 = Typeface.DEFAULT;
        }
        var5_10 = Typeface.sDynamicCacheLock;
        // MONITORENTER : var5_10
        var1_3 = Builder.access$000((AssetManager)var1_3, var2_4, 0, null, -1, -1, "sans-serif");
        Typeface.sDynamicTypefaceCache.put((String)var1_3, (Typeface)var0);
        // MONITOREXIT : var5_10
        return var0;
        catch (IllegalArgumentException var0_2) {
            return null;
        }
    }

    public static Typeface createFromTypefaceWithVariation(Typeface typeface, List<FontVariationAxis> list) {
        if (typeface == null) {
            typeface = DEFAULT;
        }
        return new Typeface(Typeface.nativeCreateFromTypefaceWithVariation(typeface.native_instance, list));
    }

    private static String createProviderUid(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("provider:");
        stringBuilder.append(string2);
        stringBuilder.append("-");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Typeface createWeightStyle(Typeface typeface, int n, boolean bl) {
        int n2 = n << 1 | bl;
        Object object = sWeightCacheLock;
        synchronized (object) {
            Typeface typeface2;
            SparseArray<Typeface> sparseArray = sWeightTypefaceCache.get(typeface.native_instance);
            if (sparseArray == null) {
                sparseArray = new SparseArray<Typeface>(4);
                sWeightTypefaceCache.put(typeface.native_instance, sparseArray);
            } else {
                typeface2 = (Typeface)sparseArray.get(n2);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            typeface2 = new Typeface(Typeface.nativeCreateFromTypefaceWithExactStyle(typeface.native_instance, n, bl));
            sparseArray.put(n2, typeface2);
            return typeface2;
        }
    }

    public static Typeface defaultFromStyle(int n) {
        return sDefaults[n];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Typeface findFromCache(AssetManager object, String string2) {
        Object object2 = sDynamicCacheLock;
        synchronized (object2) {
            object = Builder.createAssetUid((AssetManager)object, string2, 0, null, -1, -1, DEFAULT_FAMILY);
            object = sDynamicTypefaceCache.get((String)object);
            if (object != null) {
                return object;
            }
            return null;
        }
    }

    private static Typeface getSystemDefaultTypeface(String object) {
        block0 : {
            if ((object = sSystemFontMap.get(object)) != null) break block0;
            object = DEFAULT;
        }
        return object;
    }

    @VisibleForTesting
    public static void initSystemDefaultTypefaces(Map<String, Typeface> map, Map<String, android.graphics.fonts.FontFamily[]> object, FontConfig.Alias[] arralias) {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry object2 = object.next();
            map.put((String)object2.getKey(), Typeface.createFromFamilies((android.graphics.fonts.FontFamily[])object2.getValue()));
        }
        for (FontConfig.Alias alias : arralias) {
            if (map.containsKey(alias.getName()) || (object = map.get(alias.getToName())) == null) continue;
            int n = alias.getWeight();
            if (n != 400) {
                object = new Typeface(Typeface.nativeCreateWeightAlias(((Typeface)object).native_instance, n));
            }
            map.put(alias.getName(), (Typeface)object);
        }
    }

    @UnsupportedAppUsage
    private static native long nativeCreateFromArray(long[] var0, int var1, int var2);

    private static native long nativeCreateFromTypeface(long var0, int var2);

    private static native long nativeCreateFromTypefaceWithExactStyle(long var0, int var2, boolean var3);

    private static native long nativeCreateFromTypefaceWithVariation(long var0, List<FontVariationAxis> var2);

    @UnsupportedAppUsage
    private static native long nativeCreateWeightAlias(long var0, int var2);

    @CriticalNative
    private static native long nativeGetReleaseFunc();

    @CriticalNative
    private static native int nativeGetStyle(long var0);

    private static native int[] nativeGetSupportedAxes(long var0);

    @CriticalNative
    private static native int nativeGetWeight(long var0);

    private static native void nativeRegisterGenericFamily(String var0, long var1);

    @CriticalNative
    private static native void nativeSetDefault(long var0);

    private static void registerGenericFamilyNative(String string2, Typeface typeface) {
        if (typeface != null) {
            Typeface.nativeRegisterGenericFamily(string2, typeface.native_instance);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private static void setDefault(Typeface typeface) {
        sDefaultTypeface = typeface;
        Typeface.nativeSetDefault(typeface.native_instance);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Typeface)object;
            if (this.mStyle != ((Typeface)object).mStyle || this.native_instance != ((Typeface)object).native_instance) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getStyle() {
        return this.mStyle;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public int hashCode() {
        long l = this.native_instance;
        return (17 * 31 + (int)(l ^ l >>> 32)) * 31 + this.mStyle;
    }

    public final boolean isBold() {
        int n = this.mStyle;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public final boolean isItalic() {
        boolean bl = (this.mStyle & 2) != 0;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isSupportedAxes(int n) {
        if (this.mSupportedAxes == null) {
            synchronized (this) {
                if (this.mSupportedAxes == null) {
                    this.mSupportedAxes = Typeface.nativeGetSupportedAxes(this.native_instance);
                    if (this.mSupportedAxes == null) {
                        this.mSupportedAxes = EMPTY_AXES;
                    }
                }
            }
        }
        if (Arrays.binarySearch(this.mSupportedAxes, n) < 0) return false;
        return true;
    }

    public static final class Builder {
        public static final int BOLD_WEIGHT = 700;
        public static final int NORMAL_WEIGHT = 400;
        private final AssetManager mAssetManager;
        private String mFallbackFamilyName;
        private final Font.Builder mFontBuilder;
        private int mItalic = -1;
        private final String mPath;
        private int mWeight = -1;

        public Builder(AssetManager assetManager, String string2) {
            this(assetManager, string2, true, 0);
        }

        public Builder(AssetManager assetManager, String string2, boolean bl, int n) {
            this.mFontBuilder = new Font.Builder(assetManager, string2, bl, n);
            this.mAssetManager = assetManager;
            this.mPath = string2;
        }

        public Builder(File file) {
            this.mFontBuilder = new Font.Builder(file);
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(FileDescriptor object) {
            try {
                Font.Builder builder = new Font.Builder(ParcelFileDescriptor.dup((FileDescriptor)object));
                object = builder;
            }
            catch (IOException iOException) {
                object = null;
            }
            this.mFontBuilder = object;
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(String string2) {
            this.mFontBuilder = new Font.Builder(new File(string2));
            this.mAssetManager = null;
            this.mPath = null;
        }

        /*
         * WARNING - void declaration
         */
        private static String createAssetUid(AssetManager object, String object22, int n, FontVariationAxis[] arrfontVariationAxis, int n2, int n3, String string2) {
            void var5_7;
            int n4;
            void var6_8;
            int n5;
            void var3_5;
            SparseArray<String> sparseArray = ((AssetManager)object).getAssignedPackageIdentifiers();
            object = new StringBuilder();
            int n6 = sparseArray.size();
            for (int i = 0; i < n6; ++i) {
                ((StringBuilder)object).append(sparseArray.valueAt(i));
                ((StringBuilder)object).append("-");
            }
            ((StringBuilder)object).append((String)object22);
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(Integer.toString(n4));
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(Integer.toString(n5));
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(Integer.toString((int)var5_7));
            ((StringBuilder)object).append("--");
            ((StringBuilder)object).append((String)var6_8);
            ((StringBuilder)object).append("--");
            if (var3_5 != null) {
                for (void var1_3 : var3_5) {
                    ((StringBuilder)object).append(var1_3.getTag());
                    ((StringBuilder)object).append("-");
                    ((StringBuilder)object).append(Float.toString(var1_3.getStyleValue()));
                }
            }
            return ((StringBuilder)object).toString();
        }

        private Typeface resolveFallbackTypeface() {
            int n;
            Object object = this.mFallbackFamilyName;
            if (object == null) {
                return null;
            }
            object = Typeface.getSystemDefaultTypeface((String)object);
            if (this.mWeight == -1 && this.mItalic == -1) {
                return object;
            }
            int n2 = n = this.mWeight;
            if (n == -1) {
                n2 = ((Typeface)object).mWeight;
            }
            n = this.mItalic;
            boolean bl = false;
            if (n == -1 ? (((Typeface)object).mStyle & 2) != 0 : n == 1) {
                bl = true;
            }
            return Typeface.createWeightStyle((Typeface)object, n2, bl);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public Typeface build() {
            var1_1 = this.mFontBuilder;
            if (var1_1 == null) {
                return this.resolveFallbackTypeface();
            }
            try {
                var2_3 = var1_1.build();
                if (this.mAssetManager == null) {
                    var1_1 = null;
                } else {
                    var3_4 = this.mAssetManager;
                    var4_5 = this.mPath;
                    var5_6 = var2_3.getTtcIndex();
                    var6_7 = var2_3.getAxes();
                    var7_8 = this.mWeight;
                    var8_9 = this.mItalic;
                    var1_1 = this.mFallbackFamilyName == null ? "sans-serif" : this.mFallbackFamilyName;
                    var1_1 = Builder.createAssetUid((AssetManager)var3_4, (String)var4_5, var5_6, var6_7, var7_8, var8_9, (String)var1_1);
                }
                if (var1_1 == null) ** GOTO lbl-1000
                var4_5 = Typeface.access$500();
                // MONITORENTER : var4_5
            }
            catch (IOException | IllegalArgumentException var1_2) {
                return this.resolveFallbackTypeface();
            }
            var3_4 = (Typeface)Typeface.access$600().get(var1_1);
            if (var3_4 != null) {
                // MONITOREXIT : var4_5
                return var3_4;
            }
            // MONITOREXIT : var4_5
lbl-1000: // 2 sources:
            {
                var4_5 = new FontFamily.Builder((Font)var2_3);
                var4_5 = var4_5.build();
                var7_8 = this.mWeight == -1 ? var2_3.getStyle().getWeight() : this.mWeight;
                var8_9 = this.mItalic == -1 ? var2_3.getStyle().getSlant() : this.mItalic;
                var2_3 = new CustomFallbackBuilder((android.graphics.fonts.FontFamily)var4_5);
                var4_5 = new FontStyle(var7_8, var8_9);
                var2_3 = var2_3.setStyle((FontStyle)var4_5);
                if (this.mFallbackFamilyName != null) {
                    var2_3.setSystemFallback(this.mFallbackFamilyName);
                }
                var4_5 = var2_3.build();
                if (var1_1 == null) return var4_5;
                var2_3 = Typeface.access$500();
                // MONITORENTER : var2_3
            }
            Typeface.access$600().put(var1_1, var4_5);
            // MONITOREXIT : var2_3
            return var4_5;
        }

        public Builder setFallback(String string2) {
            this.mFallbackFamilyName = string2;
            return this;
        }

        public Builder setFontVariationSettings(String string2) {
            this.mFontBuilder.setFontVariationSettings(string2);
            return this;
        }

        public Builder setFontVariationSettings(FontVariationAxis[] arrfontVariationAxis) {
            this.mFontBuilder.setFontVariationSettings(arrfontVariationAxis);
            return this;
        }

        public Builder setItalic(boolean bl) {
            this.mItalic = bl ? 1 : 0;
            this.mFontBuilder.setSlant(this.mItalic);
            return this;
        }

        public Builder setTtcIndex(int n) {
            this.mFontBuilder.setTtcIndex(n);
            return this;
        }

        public Builder setWeight(int n) {
            this.mWeight = n;
            this.mFontBuilder.setWeight(n);
            return this;
        }
    }

    public static final class CustomFallbackBuilder {
        private static final int MAX_CUSTOM_FALLBACK = 64;
        private String mFallbackName = null;
        private final ArrayList<android.graphics.fonts.FontFamily> mFamilies = new ArrayList();
        private FontStyle mStyle;

        public CustomFallbackBuilder(android.graphics.fonts.FontFamily fontFamily) {
            Preconditions.checkNotNull(fontFamily);
            this.mFamilies.add(fontFamily);
        }

        public static int getMaxCustomFallbackCount() {
            return 64;
        }

        public CustomFallbackBuilder addCustomFallback(android.graphics.fonts.FontFamily fontFamily) {
            Preconditions.checkNotNull(fontFamily);
            boolean bl = this.mFamilies.size() < CustomFallbackBuilder.getMaxCustomFallbackCount();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Custom fallback limit exceeded(");
            stringBuilder.append(CustomFallbackBuilder.getMaxCustomFallbackCount());
            stringBuilder.append(")");
            Preconditions.checkArgument(bl, stringBuilder.toString());
            this.mFamilies.add(fontFamily);
            return this;
        }

        public Typeface build() {
            int n;
            int n2 = this.mFamilies.size();
            Object object = SystemFonts.getSystemFallback(this.mFallbackName);
            long[] arrl = new long[((android.graphics.fonts.FontFamily[])object).length + n2];
            for (n = 0; n < n2; ++n) {
                arrl[n] = this.mFamilies.get(n).getNativePtr();
            }
            for (n = 0; n < ((android.graphics.fonts.FontFamily[])object).length; ++n) {
                arrl[n + n2] = object[n].getNativePtr();
            }
            object = this.mStyle;
            n = object == null ? 400 : ((FontStyle)object).getWeight();
            object = this.mStyle;
            n2 = object != null && ((FontStyle)object).getSlant() != 0 ? 1 : 0;
            return new Typeface(Typeface.nativeCreateFromArray(arrl, n, n2));
        }

        public CustomFallbackBuilder setStyle(FontStyle fontStyle) {
            this.mStyle = fontStyle;
            return this;
        }

        public CustomFallbackBuilder setSystemFallback(String string2) {
            Preconditions.checkNotNull(string2);
            this.mFallbackName = string2;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Style {
    }

}

