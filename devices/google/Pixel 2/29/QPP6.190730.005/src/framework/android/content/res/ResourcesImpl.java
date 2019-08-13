/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.res.-$
 *  android.content.res.-$$Lambda
 *  android.content.res.-$$Lambda$ResourcesImpl
 *  android.content.res.-$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90
 *  android.content.res.-$$Lambda$ResourcesImpl$h3PTRX185BeQl8SVC2_w9arp5Og
 *  android.icu.text.PluralRules
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.res.-$;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.ComplexColor;
import android.content.res.Configuration;
import android.content.res.ConfigurationBoundResourceCache;
import android.content.res.ConstantState;
import android.content.res.DrawableCache;
import android.content.res.GradientColor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlBlock;
import android.content.res.XmlResourceParser;
import android.content.res._$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90;
import android.content.res._$$Lambda$ResourcesImpl$h3PTRX185BeQl8SVC2_w9arp5Og;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.icu.text.PluralRules;
import android.os.Build;
import android.os.LocaleList;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.DisplayAdjustments;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ResourcesImpl {
    private static final boolean DEBUG_CONFIG = false;
    private static final boolean DEBUG_LOAD = false;
    private static final int ID_OTHER = 16777220;
    static final String TAG = "Resources";
    static final String TAG_PRELOAD = "Resources.preload";
    public static final boolean TRACE_FOR_DETAILED_PRELOAD = SystemProperties.getBoolean("debug.trace_resource_preload", false);
    @UnsupportedAppUsage
    private static final boolean TRACE_FOR_MISS_PRELOAD = false;
    @UnsupportedAppUsage
    private static final boolean TRACE_FOR_PRELOAD = false;
    private static final int XML_BLOCK_CACHE_SIZE = 4;
    private static int sPreloadTracingNumLoadedDrawables;
    private static boolean sPreloaded;
    @UnsupportedAppUsage
    private static final LongSparseArray<Drawable.ConstantState> sPreloadedColorDrawables;
    @UnsupportedAppUsage
    private static final LongSparseArray<ConstantState<ComplexColor>> sPreloadedComplexColors;
    @UnsupportedAppUsage
    private static final LongSparseArray<Drawable.ConstantState>[] sPreloadedDrawables;
    private static final Object sSync;
    @UnsupportedAppUsage
    private final Object mAccessLock = new Object();
    @UnsupportedAppUsage
    private final ConfigurationBoundResourceCache<Animator> mAnimatorCache = new ConfigurationBoundResourceCache();
    @UnsupportedAppUsage
    final AssetManager mAssets;
    private final int[] mCachedXmlBlockCookies = new int[4];
    private final String[] mCachedXmlBlockFiles = new String[4];
    private final XmlBlock[] mCachedXmlBlocks = new XmlBlock[4];
    @UnsupportedAppUsage
    private final DrawableCache mColorDrawableCache = new DrawableCache();
    private final ConfigurationBoundResourceCache<ComplexColor> mComplexColorCache = new ConfigurationBoundResourceCache();
    @UnsupportedAppUsage
    private final Configuration mConfiguration = new Configuration();
    private final DisplayAdjustments mDisplayAdjustments;
    @UnsupportedAppUsage
    private final DrawableCache mDrawableCache = new DrawableCache();
    private int mLastCachedXmlBlockIndex = -1;
    private final ThreadLocal<LookupStack> mLookupStack = ThreadLocal.withInitial(_$$Lambda$ResourcesImpl$h3PTRX185BeQl8SVC2_w9arp5Og.INSTANCE);
    private final DisplayMetrics mMetrics = new DisplayMetrics();
    private PluralRules mPluralRule;
    private long mPreloadTracingPreloadStartTime;
    private long mPreloadTracingStartBitmapCount;
    private long mPreloadTracingStartBitmapSize;
    @UnsupportedAppUsage
    private boolean mPreloading;
    @UnsupportedAppUsage
    private final ConfigurationBoundResourceCache<StateListAnimator> mStateListAnimatorCache = new ConfigurationBoundResourceCache();
    private final Configuration mTmpConfig = new Configuration();

    static {
        sSync = new Object();
        sPreloadedColorDrawables = new LongSparseArray();
        sPreloadedComplexColors = new LongSparseArray();
        sPreloadedDrawables = new LongSparseArray[2];
        ResourcesImpl.sPreloadedDrawables[0] = new LongSparseArray();
        ResourcesImpl.sPreloadedDrawables[1] = new LongSparseArray();
    }

    @UnsupportedAppUsage
    public ResourcesImpl(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration, DisplayAdjustments displayAdjustments) {
        this.mAssets = assetManager;
        this.mMetrics.setToDefaults();
        this.mDisplayAdjustments = displayAdjustments;
        this.mConfiguration.setToDefaults();
        this.updateConfiguration(configuration, displayMetrics, displayAdjustments.getCompatibilityInfo());
    }

    private static String adjustLanguageTag(String charSequence) {
        String string2;
        CharSequence charSequence2;
        int n = charSequence.indexOf(45);
        if (n == -1) {
            string2 = "";
        } else {
            charSequence2 = charSequence.substring(0, n);
            string2 = charSequence.substring(n);
            charSequence = charSequence2;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(Locale.adjustLanguageCode((String)charSequence));
        ((StringBuilder)charSequence2).append(string2);
        return ((StringBuilder)charSequence2).toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int attrForQuantityCode(String string2) {
        switch (string2.hashCode()) {
            default: {
                return 16777220;
            }
            case 3735208: {
                if (!string2.equals("zero")) return 16777220;
                return 16777221;
            }
            case 3343967: {
                if (!string2.equals("many")) return 16777220;
                return 16777225;
            }
            case 115276: {
                if (!string2.equals("two")) return 16777220;
                return 16777223;
            }
            case 110182: {
                if (!string2.equals("one")) return 16777220;
                return 16777222;
            }
            case 101272: {
                if (!string2.equals("few")) return 16777220;
                return 16777224;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cacheDrawable(TypedValue object, boolean bl, DrawableCache drawableCache, Resources.Theme theme, boolean bl2, long l, Drawable object2) {
        if ((object2 = ((Drawable)object2).getConstantState()) == null) {
            return;
        }
        if (!this.mPreloading) {
            object = this.mAccessLock;
            synchronized (object) {
                drawableCache.put(l, theme, object2, bl2);
                return;
            }
        }
        int n = ((Drawable.ConstantState)object2).getChangingConfigurations();
        if (bl) {
            if (!this.verifyPreloadConfig(n, 0, ((TypedValue)object).resourceId, "drawable")) return;
            sPreloadedColorDrawables.put(l, (Drawable.ConstantState)object2);
            return;
        }
        if (!this.verifyPreloadConfig(n, 8192, ((TypedValue)object).resourceId, "drawable")) return;
        if ((n & 8192) == 0) {
            sPreloadedDrawables[0].put(l, (Drawable.ConstantState)object2);
            sPreloadedDrawables[1].put(l, (Drawable.ConstantState)object2);
            return;
        }
        sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].put(l, (Drawable.ConstantState)object2);
    }

    private Drawable decodeImageDrawable(AssetManager.AssetInputStream object, Resources resources, TypedValue typedValue) {
        object = new ImageDecoder.AssetInputStreamSource((AssetManager.AssetInputStream)object, resources, typedValue);
        try {
            object = ImageDecoder.decodeDrawable((ImageDecoder.Source)object, (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$ResourcesImpl$99dm2ENnzo9b0SIUjUj2Kl3pi90.INSTANCE);
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    static int getAttributeSetSourceResId(AttributeSet attributeSet) {
        if (attributeSet != null && attributeSet instanceof XmlBlock.Parser) {
            return ((XmlBlock.Parser)attributeSet).getSourceResId();
        }
        return 0;
    }

    private ColorStateList getColorStateListFromInt(TypedValue typedValue, long l) {
        ConstantState<ComplexColor> constantState = sPreloadedComplexColors.get(l);
        if (constantState != null) {
            return (ColorStateList)constantState.newInstance();
        }
        constantState = ColorStateList.valueOf(typedValue.data);
        if (this.mPreloading && this.verifyPreloadConfig(typedValue.changingConfigurations, 0, typedValue.resourceId, "color")) {
            sPreloadedComplexColors.put(l, ((ColorStateList)((Object)constantState)).getConstantState());
        }
        return constantState;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private PluralRules getPluralRule() {
        Object object = sSync;
        synchronized (object) {
            if (this.mPluralRule != null) return this.mPluralRule;
            this.mPluralRule = PluralRules.forLocale((Locale)this.mConfiguration.getLocales().get(0));
            return this.mPluralRule;
        }
    }

    static /* synthetic */ void lambda$decodeImageDrawable$1(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }

    static /* synthetic */ LookupStack lambda$new$0() {
        return new LookupStack();
    }

    private Drawable loadColorOrXmlDrawable(Resources object, TypedValue typedValue, int n, int n2, String string2) {
        try {
            ColorStateListDrawable colorStateListDrawable = new ColorStateListDrawable(this.loadColorStateList((Resources)object, typedValue, n, null));
            return colorStateListDrawable;
        }
        catch (Resources.NotFoundException notFoundException) {
            try {
                object = this.loadXmlDrawable((Resources)object, typedValue, n, n2, string2);
                return object;
            }
            catch (Exception exception) {
                throw notFoundException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ComplexColor loadComplexColorForCookie(Resources object, TypedValue object2, int n, Resources.Theme theme) {
        block9 : {
            if (((TypedValue)object2).string == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Can't convert to ComplexColor: type=0x");
                ((StringBuilder)object).append(((TypedValue)object2).type);
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            String string2 = ((TypedValue)object2).string.toString();
            Object var6_7 = null;
            Trace.traceBegin(8192L, string2);
            if (!string2.endsWith(".xml")) {
                Trace.traceEnd(8192L);
                object = new StringBuilder();
                ((StringBuilder)object).append("File ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" from drawable resource ID #0x");
                ((StringBuilder)object).append(Integer.toHexString(n));
                ((StringBuilder)object).append(": .xml extension required");
                throw new Resources.NotFoundException(((StringBuilder)object).toString());
            }
            try {
                int n2;
                XmlResourceParser xmlResourceParser = this.loadXmlResourceParser(string2, n, ((TypedValue)object2).assetCookie, "ComplexColor");
                AttributeSet attributeSet = Xml.asAttributeSet(xmlResourceParser);
                while ((n2 = xmlResourceParser.next()) != 2 && n2 != 1) {
                }
                if (n2 != 2) break block9;
                String string3 = xmlResourceParser.getName();
                if (string3.equals("gradient")) {
                    object2 = GradientColor.createFromXmlInner((Resources)object, xmlResourceParser, attributeSet, theme);
                } else {
                    object2 = var6_7;
                    if (string3.equals("selector")) {
                        object2 = ColorStateList.createFromXmlInner((Resources)object, xmlResourceParser, attributeSet, theme);
                    }
                }
                xmlResourceParser.close();
            }
            catch (Exception exception) {
                Trace.traceEnd(8192L);
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("File ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" from ComplexColor resource ID #0x");
                ((StringBuilder)object2).append(Integer.toHexString(n));
                object2 = new Resources.NotFoundException(((StringBuilder)object2).toString());
                ((Throwable)object2).initCause(exception);
                throw object2;
            }
            Trace.traceEnd(8192L);
            return object2;
        }
        object = new XmlPullParserException("No start tag found");
        throw object;
    }

    private ComplexColor loadComplexColorFromName(Resources resources, Resources.Theme theme, TypedValue typedValue, int n) {
        ConfigurationBoundResourceCache<ComplexColor> configurationBoundResourceCache = this.mComplexColorCache;
        long l = (long)typedValue.assetCookie << 32 | (long)typedValue.data;
        ComplexColor complexColor = configurationBoundResourceCache.getInstance(l, resources, theme);
        if (complexColor != null) {
            return complexColor;
        }
        ConstantState<ComplexColor> constantState = sPreloadedComplexColors.get(l);
        if (constantState != null) {
            complexColor = constantState.newInstance(resources, theme);
        }
        constantState = complexColor;
        if (complexColor == null) {
            constantState = this.loadComplexColorForCookie(resources, typedValue, n, theme);
        }
        if (constantState != null) {
            ((ComplexColor)((Object)constantState)).setBaseChangingConfigurations(typedValue.changingConfigurations);
            if (this.mPreloading) {
                if (this.verifyPreloadConfig(((ComplexColor)((Object)constantState)).getChangingConfigurations(), 0, typedValue.resourceId, "color")) {
                    sPreloadedComplexColors.put(l, ((ComplexColor)((Object)constantState)).getConstantState());
                }
            } else {
                configurationBoundResourceCache.put(l, theme, (ComplexColor)((Object)((ComplexColor)((Object)constantState)).getConstantState()));
            }
        }
        return constantState;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Drawable loadDrawableForCookie(Resources var1_1, TypedValue var2_11, int var3_12, int var4_13) {
        block21 : {
            block20 : {
                block19 : {
                    block18 : {
                        if (var2_11.string == null) {
                            var1_1 = new StringBuilder();
                            var1_1.append("Resource \"");
                            var1_1.append(this.getResourceName(var3_12));
                            var1_1.append("\" (");
                            var1_1.append(Integer.toHexString(var3_12));
                            var1_1.append(") is not a Drawable (color or path): ");
                            var1_1.append(var2_11);
                            throw new Resources.NotFoundException(var1_1.toString());
                        }
                        var5_14 = var2_11.string.toString();
                        if (ResourcesImpl.TRACE_FOR_DETAILED_PRELOAD) {
                            var6_15 = System.nanoTime();
                            var8_16 = Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                            var9_17 = Bitmap.sPreloadTracingTotalBitmapsSize;
                            var11_18 = ResourcesImpl.sPreloadTracingNumLoadedDrawables;
                        } else {
                            var6_15 = 0L;
                            var8_16 = 0;
                            var9_17 = 0L;
                            var11_18 = 0;
                        }
                        Trace.traceBegin(8192L, var5_14);
                        var12_19 = this.mLookupStack.get();
                        if (var12_19.contains(var3_12)) ** GOTO lbl113
                        var12_19.push(var3_12);
                        var13_20 = var5_14.endsWith(".xml");
                        if (!var13_20) break block18;
                        try {
                            var13_20 = var5_14.startsWith("res/color/");
                            if (!var13_20) ** GOTO lbl42
                        }
                        catch (Throwable var1_3) {}
                        try {
                            var1_1 = this.loadColorOrXmlDrawable((Resources)var1_1, (TypedValue)var2_11, var3_12, var4_13, var5_14);
                            break block19;
lbl42: // 1 sources:
                            var1_1 = this.loadXmlDrawable((Resources)var1_1, (TypedValue)var2_11, var3_12, var4_13, var5_14);
                            break block19;
                        }
                        catch (Throwable var1_2) {
                            break block20;
                        }
                        break block20;
                    }
                    try {
                        var1_1 = this.decodeImageDrawable((AssetManager.AssetInputStream)this.mAssets.openNonAsset(var2_11.assetCookie, var5_14, 2), (Resources)var1_1, (TypedValue)var2_11);
                    }
                    catch (Throwable var1_5) {
                        break block20;
                    }
                }
                try {
                    var12_19.pop();
                }
                catch (Exception | StackOverflowError var1_4) {
                    break block21;
                }
                Trace.traceEnd(8192L);
                if (ResourcesImpl.TRACE_FOR_DETAILED_PRELOAD == false) return var1_1;
                if (var3_12 >>> 24 != 1) return var1_1;
                var14_21 = this.getResourceName(var3_12);
                if (var14_21 == null) return var1_1;
                var15_22 = System.nanoTime();
                var17_23 = Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                var18_24 = Bitmap.sPreloadTracingTotalBitmapsSize;
                var20_25 = ResourcesImpl.sPreloadTracingNumLoadedDrawables;
                var4_13 = 1;
                ResourcesImpl.sPreloadTracingNumLoadedDrawables = var20_25 + 1;
                if (Process.myUid() != 0) {
                    var4_13 = 0;
                }
                var12_19 = new StringBuilder();
                var2_11 = var4_13 != 0 ? "Preloaded FW drawable #" : "Loaded non-preloaded FW drawable #";
                var12_19.append((String)var2_11);
                var12_19.append(Integer.toHexString(var3_12));
                var12_19.append(" ");
                var12_19.append(var14_21);
                var12_19.append(" ");
                var12_19.append(var5_14);
                var12_19.append(" ");
                var12_19.append(var1_1.getClass().getCanonicalName());
                var12_19.append(" #nested_drawables= ");
                var12_19.append(var20_25 - var11_18);
                var12_19.append(" #bitmaps= ");
                var12_19.append(var17_23 - var8_16);
                var12_19.append(" total_bitmap_size= ");
                var12_19.append(var18_24 - var9_17);
                var12_19.append(" in[us] ");
                var12_19.append((var15_22 - var6_15) / 1000L);
                Log.d("Resources.preload", var12_19.toString());
                return var1_1;
                catch (Throwable var1_6) {
                    // empty catch block
                }
            }
            try {
                var12_19.pop();
                throw var1_7;
lbl113: // 1 sources:
                var1_1 = new Exception("Recursive reference in drawable");
                throw var1_1;
            }
            catch (Exception | StackOverflowError var1_8) {}
            break block21;
            catch (Exception | StackOverflowError var1_9) {
                // empty catch block
            }
        }
        Trace.traceEnd(8192L);
        var2_11 = new StringBuilder();
        var2_11.append("File ");
        var2_11.append(var5_14);
        var2_11.append(" from drawable resource ID #0x");
        var2_11.append(Integer.toHexString(var3_12));
        var2_11 = new Resources.NotFoundException(var2_11.toString());
        var2_11.initCause((Throwable)var1_10);
        throw var2_11;
    }

    private Drawable loadXmlDrawable(Resources object, TypedValue object2, int n, int n2, String string2) throws IOException, XmlPullParserException {
        object2 = this.loadXmlResourceParser(string2, n, ((TypedValue)object2).assetCookie, "drawable");
        try {
            object = Drawable.createFromXmlForDensity((Resources)object, (XmlPullParser)object2, n2, null);
            if (object2 != null) {
                object2.close();
            }
            return object;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (object2 != null) {
                    try {
                        object2.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                }
                throw throwable2;
            }
        }
    }

    private boolean verifyPreloadConfig(int n, int n2, int n3, String string2) {
        if ((-1073745921 & n & n2) != 0) {
            String string3;
            try {
                string3 = this.getResourceName(n3);
            }
            catch (Resources.NotFoundException notFoundException) {
                string3 = "?";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Preloaded ");
            stringBuilder.append(string2);
            stringBuilder.append(" resource #0x");
            stringBuilder.append(Integer.toHexString(n3));
            stringBuilder.append(" (");
            stringBuilder.append(string3);
            stringBuilder.append(") that varies with configuration!!");
            Log.w(TAG, stringBuilder.toString());
            return false;
        }
        return true;
    }

    public int calcConfigChanges(Configuration configuration) {
        int n;
        if (configuration == null) {
            return -1;
        }
        this.mTmpConfig.setTo(configuration);
        int n2 = n = configuration.densityDpi;
        if (n == 0) {
            n2 = this.mMetrics.noncompatDensityDpi;
        }
        this.mDisplayAdjustments.getCompatibilityInfo().applyToConfiguration(n2, this.mTmpConfig);
        if (this.mTmpConfig.getLocales().isEmpty()) {
            this.mTmpConfig.setLocales(LocaleList.getDefault());
        }
        return this.mConfiguration.updateFrom(this.mTmpConfig);
    }

    void finishPreloading() {
        if (this.mPreloading) {
            if (TRACE_FOR_DETAILED_PRELOAD) {
                long l = SystemClock.uptimeMillis();
                long l2 = this.mPreloadTracingPreloadStartTime;
                long l3 = Bitmap.sPreloadTracingTotalBitmapsSize;
                long l4 = this.mPreloadTracingStartBitmapSize;
                long l5 = Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                long l6 = this.mPreloadTracingStartBitmapCount;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Preload finished, ");
                stringBuilder.append(l5 - l6);
                stringBuilder.append(" bitmaps of ");
                stringBuilder.append(l3 - l4);
                stringBuilder.append(" bytes in ");
                stringBuilder.append(l - l2);
                stringBuilder.append(" ms");
                Log.d(TAG_PRELOAD, stringBuilder.toString());
            }
            this.mPreloading = false;
            this.flushLayoutCache();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flushLayoutCache() {
        XmlBlock[] arrxmlBlock = this.mCachedXmlBlocks;
        synchronized (arrxmlBlock) {
            Arrays.fill(this.mCachedXmlBlockCookies, 0);
            Arrays.fill(this.mCachedXmlBlockFiles, null);
            Object[] arrobject = this.mCachedXmlBlocks;
            int n = 0;
            do {
                if (n >= 4) {
                    Arrays.fill(arrobject, null);
                    return;
                }
                XmlBlock xmlBlock = arrobject[n];
                if (xmlBlock != null) {
                    xmlBlock.close();
                }
                ++n;
            } while (true);
        }
    }

    ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
        return this.mAnimatorCache;
    }

    @UnsupportedAppUsage
    public AssetManager getAssets() {
        return this.mAssets;
    }

    CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public DisplayAdjustments getDisplayAdjustments() {
        return this.mDisplayAdjustments;
    }

    @UnsupportedAppUsage
    DisplayMetrics getDisplayMetrics() {
        return this.mMetrics;
    }

    int getIdentifier(String string2, String string3, String string4) {
        if (string2 != null) {
            try {
                int n = Integer.parseInt(string2);
                return n;
            }
            catch (Exception exception) {
                return this.mAssets.getResourceIdentifier(string2, string3, string4);
            }
        }
        throw new NullPointerException("name is null");
    }

    String getLastResourceResolution() throws Resources.NotFoundException {
        String string2 = this.mAssets.getLastResourceResolution();
        if (string2 != null) {
            return string2;
        }
        throw new Resources.NotFoundException("Associated AssetManager hasn't resolved a resource");
    }

    LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
        return sPreloadedDrawables[0];
    }

    CharSequence getQuantityText(int n, int n2) throws Resources.NotFoundException {
        PluralRules pluralRules = this.getPluralRule();
        CharSequence charSequence = this.mAssets.getResourceBagText(n, ResourcesImpl.attrForQuantityCode(pluralRules.select((double)n2)));
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = this.mAssets.getResourceBagText(n, 16777220);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Plural resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        ((StringBuilder)charSequence).append(" quantity=");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(" item=");
        ((StringBuilder)charSequence).append(pluralRules.select((double)n2));
        throw new Resources.NotFoundException(((StringBuilder)charSequence).toString());
    }

    String getResourceEntryName(int n) throws Resources.NotFoundException {
        CharSequence charSequence = this.mAssets.getResourceEntryName(n);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to find resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)charSequence).toString());
    }

    String getResourceName(int n) throws Resources.NotFoundException {
        CharSequence charSequence = this.mAssets.getResourceName(n);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to find resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)charSequence).toString());
    }

    String getResourcePackageName(int n) throws Resources.NotFoundException {
        CharSequence charSequence = this.mAssets.getResourcePackageName(n);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to find resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)charSequence).toString());
    }

    String getResourceTypeName(int n) throws Resources.NotFoundException {
        CharSequence charSequence = this.mAssets.getResourceTypeName(n);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unable to find resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)charSequence).toString());
    }

    Configuration[] getSizeConfigurations() {
        return this.mAssets.getSizeConfigurations();
    }

    ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
        return this.mStateListAnimatorCache;
    }

    @UnsupportedAppUsage
    void getValue(int n, TypedValue object, boolean bl) throws Resources.NotFoundException {
        if (this.mAssets.getResourceValue(n, 0, (TypedValue)object, bl)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    void getValue(String string2, TypedValue object, boolean bl) throws Resources.NotFoundException {
        int n = this.getIdentifier(string2, "string", null);
        if (n != 0) {
            this.getValue(n, (TypedValue)object, bl);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("String resource name ");
        ((StringBuilder)object).append(string2);
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    void getValueForDensity(int n, int n2, TypedValue object, boolean bl) throws Resources.NotFoundException {
        if (this.mAssets.getResourceValue(n, n2, (TypedValue)object, bl)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    ColorStateList loadColorStateList(Resources object, TypedValue typedValue, int n, Resources.Theme theme) throws Resources.NotFoundException {
        long l = typedValue.assetCookie;
        long l2 = typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return this.getColorStateListFromInt(typedValue, l << 32 | l2);
        }
        if ((object = this.loadComplexColorFromName((Resources)object, theme, typedValue, n)) != null && object instanceof ColorStateList) {
            return (ColorStateList)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't find ColorStateList from drawable resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    ComplexColor loadComplexColor(Resources object, TypedValue object2, int n, Resources.Theme theme) {
        long l = ((TypedValue)object2).assetCookie;
        long l2 = ((TypedValue)object2).data;
        if (((TypedValue)object2).type >= 28 && ((TypedValue)object2).type <= 31) {
            return this.getColorStateListFromInt((TypedValue)object2, l << 32 | l2);
        }
        String string2 = ((TypedValue)object2).string.toString();
        if (string2.endsWith(".xml")) {
            try {
                object = this.loadComplexColorFromName((Resources)object, theme, (TypedValue)object2, n);
                return object;
            }
            catch (Exception exception) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("File ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" from complex color resource ID #0x");
                ((StringBuilder)object2).append(Integer.toHexString(n));
                object2 = new Resources.NotFoundException(((StringBuilder)object2).toString());
                ((Throwable)object2).initCause(exception);
                throw object2;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("File ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" from drawable resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(": .xml extension required");
        throw new Resources.NotFoundException(((StringBuilder)object).toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Drawable loadDrawable(Resources var1_1, TypedValue var2_5, int var3_7, int var4_8, Resources.Theme var5_9) throws Resources.NotFoundException {
        block16 : {
            block15 : {
                block14 : {
                    var6_10 = var4_8 == 0 || var2_5.density == this.mMetrics.densityDpi;
                    if (var4_8 > 0 && var2_5.density > 0 && var2_5.density != 65535) {
                        var2_5.density = var2_5.density == var4_8 ? this.mMetrics.densityDpi : var2_5.density * this.mMetrics.densityDpi / var4_8;
                    }
                    if (var2_5.type >= 28 && var2_5.type <= 31) {
                        var7_11 = this.mColorDrawableCache;
                        var8_12 = var2_5.data;
                        var10_13 = true;
                        break block14;
                    }
                    var7_11 = this.mDrawableCache;
                    var11_14 = var2_5.assetCookie;
                    var8_12 = var2_5.data;
                    var10_13 = false;
                    var8_12 = var11_14 << 32 | var8_12;
                }
                if (!this.mPreloading && var6_10 && (var13_15 = var7_11.getInstance(var8_12, var1_1, (Resources.Theme)var5_9)) != null) {
                    var13_15.setChangingConfigurations(var2_5.changingConfigurations);
                    return var13_15;
                }
                var13_15 = var10_13 != false ? ResourcesImpl.sPreloadedColorDrawables.get(var8_12) : ResourcesImpl.sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(var8_12);
                if (var13_15 == null) ** GOTO lbl38
                if (!ResourcesImpl.TRACE_FOR_DETAILED_PRELOAD || var3_7 >>> 24 != 1) ** GOTO lbl36
                if (Process.myUid() != 0 && (var14_16 = this.getResourceName(var3_7)) != null) {
                    var15_17 = new StringBuilder();
                    var15_17.append("Hit preloaded FW drawable #");
                    var15_17.append(Integer.toHexString(var3_7));
                    var15_17.append(" ");
                    var15_17.append(var14_16);
                    Log.d("Resources.preload", var15_17.toString());
                }
lbl36: // 4 sources:
                var13_15 = var13_15.newDrawable(var1_1);
                break block15;
lbl38: // 1 sources:
                var13_15 = var10_13 != false ? new ColorDrawable(var2_5.data) : this.loadDrawableForCookie(var1_1, (TypedValue)var2_5, var3_7, var4_8);
            }
            var4_8 = var13_15 instanceof DrawableContainer != false ? 1 : 0;
            var16_18 = var13_15 != null && var13_15.canApplyTheme() != false;
            var15_17 = var13_15;
            if (var16_18) {
                var15_17 = var13_15;
                if (var5_9 != null) {
                    var15_17 = var13_15.mutate();
                    var15_17.applyTheme((Resources.Theme)var5_9);
                    var15_17.clearMutated();
                }
            }
            if (var15_17 == null) return var15_17;
            var15_17.setChangingConfigurations(var2_5.changingConfigurations);
            if (var6_10 == false) return var15_17;
            try {
                this.cacheDrawable((TypedValue)var2_5, var10_13, var7_11, (Resources.Theme)var5_9, var16_18, var8_12, (Drawable)var15_17);
                var2_5 = var15_17;
                if (var4_8 == 0) return var2_5;
                var5_9 = var15_17.getConstantState();
                var2_5 = var15_17;
                if (var5_9 == null) return var2_5;
                return var5_9.newDrawable(var1_1);
            }
            catch (Exception var1_2) {}
            break block16;
            catch (Exception var1_3) {
                // empty catch block
            }
        }
        try {
            var2_5 = this.getResourceName(var3_7);
        }
        catch (Resources.NotFoundException var2_6) {
            var2_5 = "(missing name)";
        }
        var5_9 = new StringBuilder();
        var5_9.append("Drawable ");
        var5_9.append((String)var2_5);
        var5_9.append(" with resource ID #0x");
        var5_9.append(Integer.toHexString(var3_7));
        var1_4 = new Resources.NotFoundException(var5_9.toString(), var1_4);
        var1_4.setStackTrace(new StackTraceElement[0]);
        throw var1_4;
    }

    /*
     * Exception decompiling
     */
    public Typeface loadFont(Resources var1_1, TypedValue var2_5, int var3_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    XmlResourceParser loadXmlResourceParser(String object, int n, int n2, String string2) throws Resources.NotFoundException {
        Object object2;
        block9 : {
            String[] arrstring;
            XmlBlock[] arrxmlBlock;
            Object object3;
            if (n == 0) break block9;
            try {
                object2 = this.mCachedXmlBlocks;
                synchronized (object2) {
                    object3 = this.mCachedXmlBlockCookies;
                    arrstring = this.mCachedXmlBlockFiles;
                    arrxmlBlock = this.mCachedXmlBlocks;
                }
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("File ");
                stringBuilder.append((String)object);
                stringBuilder.append(" from xml type ");
                stringBuilder.append(string2);
                stringBuilder.append(" resource ID #0x");
                stringBuilder.append(Integer.toHexString(n));
                object = new Resources.NotFoundException(stringBuilder.toString());
                ((Throwable)object).initCause(exception);
                throw object;
            }
            {
                int n3;
                int n4 = arrstring.length;
                for (n3 = 0; n3 < n4; ++n3) {
                    if (object3[n3] != n2 || arrstring[n3] == null || !arrstring[n3].equals(object)) continue;
                    return arrxmlBlock[n3].newParser(n);
                }
                XmlBlock xmlBlock = this.mAssets.openXmlBlockAsset(n2, (String)object);
                if (xmlBlock != null) {
                    this.mLastCachedXmlBlockIndex = n3 = (this.mLastCachedXmlBlockIndex + 1) % n4;
                    XmlBlock xmlBlock2 = arrxmlBlock[n3];
                    if (xmlBlock2 != null) {
                        xmlBlock2.close();
                    }
                    object3[n3] = n2;
                    arrstring[n3] = object;
                    arrxmlBlock[n3] = xmlBlock;
                    return xmlBlock.newParser(n);
                }
            }
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("File ");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" from xml type ");
        ((StringBuilder)object2).append(string2);
        ((StringBuilder)object2).append(" resource ID #0x");
        ((StringBuilder)object2).append(Integer.toHexString(n));
        throw new Resources.NotFoundException(((StringBuilder)object2).toString());
    }

    ThemeImpl newThemeImpl() {
        return new ThemeImpl();
    }

    ThemeImpl newThemeImpl(Resources.ThemeKey themeKey) {
        ThemeImpl themeImpl = new ThemeImpl();
        themeImpl.mKey.setTo(themeKey);
        themeImpl.rebase();
        return themeImpl;
    }

    InputStream openRawResource(int n, TypedValue object) throws Resources.NotFoundException {
        this.getValue(n, (TypedValue)object, true);
        try {
            InputStream inputStream = this.mAssets.openNonAsset(((TypedValue)object).assetCookie, ((TypedValue)object).string.toString(), 2);
            return inputStream;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("File ");
            object = ((TypedValue)object).string == null ? "(null)" : ((TypedValue)object).string.toString();
            stringBuilder.append((String)object);
            stringBuilder.append(" from drawable resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            object = new Resources.NotFoundException(stringBuilder.toString());
            ((Throwable)object).initCause(exception);
            throw object;
        }
    }

    AssetFileDescriptor openRawResourceFd(int n, TypedValue typedValue) throws Resources.NotFoundException {
        this.getValue(n, typedValue, true);
        try {
            AssetFileDescriptor assetFileDescriptor = this.mAssets.openNonAssetFd(typedValue.assetCookie, typedValue.string.toString());
            return assetFileDescriptor;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("File ");
            stringBuilder.append(typedValue.string.toString());
            stringBuilder.append(" from drawable resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            throw new Resources.NotFoundException(stringBuilder.toString(), exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void startPreloading() {
        Object object = sSync;
        synchronized (object) {
            if (sPreloaded) {
                IllegalStateException illegalStateException = new IllegalStateException("Resources already preloaded");
                throw illegalStateException;
            }
            sPreloaded = true;
            this.mPreloading = true;
            this.mConfiguration.densityDpi = DisplayMetrics.DENSITY_DEVICE;
            this.updateConfiguration(null, null, null);
            if (TRACE_FOR_DETAILED_PRELOAD) {
                this.mPreloadTracingPreloadStartTime = SystemClock.uptimeMillis();
                this.mPreloadTracingStartBitmapSize = Bitmap.sPreloadTracingTotalBitmapsSize;
                this.mPreloadTracingStartBitmapCount = Bitmap.sPreloadTracingNumInstantiatedBitmaps;
                Log.d(TAG_PRELOAD, "Preload starting");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void updateConfiguration(Configuration object, DisplayMetrics object2, CompatibilityInfo object3) {
        block20 : {
            int n;
            int n2;
            block19 : {
                Trace.traceBegin(8192L, "ResourcesImpl#updateConfiguration");
                try {
                    Object object4 = this.mAccessLock;
                    // MONITORENTER : object4
                    if (object3 == null) break block19;
                }
                catch (Throwable throwable) {
                    Trace.traceEnd(8192L);
                    throw throwable;
                }
                this.mDisplayAdjustments.setCompatibilityInfo((CompatibilityInfo)object3);
            }
            if (object2 != null) {
                this.mMetrics.setTo((DisplayMetrics)object2);
            }
            this.mDisplayAdjustments.getCompatibilityInfo().applyToDisplayMetrics(this.mMetrics);
            int n3 = this.calcConfigChanges((Configuration)object);
            object2 = object = this.mConfiguration.getLocales();
            if (((LocaleList)object).isEmpty()) {
                object2 = LocaleList.getDefault();
                this.mConfiguration.setLocales((LocaleList)object2);
            }
            if ((n3 & 4) != 0 && ((LocaleList)object2).size() > 1) {
                object = object3 = this.mAssets.getNonSystemLocales();
                if (LocaleList.isPseudoLocalesOnly((String[])object3)) {
                    object = object3 = this.mAssets.getLocales();
                    if (LocaleList.isPseudoLocalesOnly((String[])object3)) {
                        object = null;
                    }
                }
                if (object != null && (object = ((LocaleList)object2).getFirstMatchWithEnglishSupported((String[])object)) != null && object != ((LocaleList)object2).get(0)) {
                    object3 = this.mConfiguration;
                    LocaleList localeList = new LocaleList((Locale)object, (LocaleList)object2);
                    ((Configuration)object3).setLocales(localeList);
                }
            }
            if (this.mConfiguration.densityDpi != 0) {
                this.mMetrics.densityDpi = this.mConfiguration.densityDpi;
                this.mMetrics.density = (float)this.mConfiguration.densityDpi * 0.00625f;
            }
            object = this.mMetrics;
            float f = this.mMetrics.density;
            float f2 = this.mConfiguration.fontScale != 0.0f ? this.mConfiguration.fontScale : 1.0f;
            ((DisplayMetrics)object).scaledDensity = f * f2;
            if (this.mMetrics.widthPixels >= this.mMetrics.heightPixels) {
                n = this.mMetrics.widthPixels;
                n2 = this.mMetrics.heightPixels;
            } else {
                n = this.mMetrics.heightPixels;
                n2 = this.mMetrics.widthPixels;
            }
            int n4 = this.mConfiguration.keyboardHidden == 1 && this.mConfiguration.hardKeyboardHidden == 2 ? 3 : this.mConfiguration.keyboardHidden;
            this.mAssets.setConfiguration(this.mConfiguration.mcc, this.mConfiguration.mnc, ResourcesImpl.adjustLanguageTag(this.mConfiguration.getLocales().get(0).toLanguageTag()), this.mConfiguration.orientation, this.mConfiguration.touchscreen, this.mConfiguration.densityDpi, this.mConfiguration.keyboard, n4, this.mConfiguration.navigation, n, n2, this.mConfiguration.smallestScreenWidthDp, this.mConfiguration.screenWidthDp, this.mConfiguration.screenHeightDp, this.mConfiguration.screenLayout, this.mConfiguration.uiMode, this.mConfiguration.colorMode, Build.VERSION.RESOURCES_SDK_INT);
            this.mDrawableCache.onConfigurationChange(n3);
            this.mColorDrawableCache.onConfigurationChange(n3);
            this.mComplexColorCache.onConfigurationChange(n3);
            this.mAnimatorCache.onConfigurationChange(n3);
            this.mStateListAnimatorCache.onConfigurationChange(n3);
            this.flushLayoutCache();
            // MONITOREXIT : object4
            object = sSync;
            // MONITORENTER : object
            if (this.mPluralRule == null) break block20;
            this.mPluralRule = PluralRules.forLocale((Locale)this.mConfiguration.getLocales().get(0));
        }
        // MONITOREXIT : object
        Trace.traceEnd(8192L);
    }

    private static class LookupStack {
        private int[] mIds = new int[4];
        private int mSize = 0;

        private LookupStack() {
        }

        public boolean contains(int n) {
            for (int i = 0; i < this.mSize; ++i) {
                if (this.mIds[i] != n) continue;
                return true;
            }
            return false;
        }

        public void pop() {
            --this.mSize;
        }

        public void push(int n) {
            this.mIds = GrowingArrayUtils.append(this.mIds, this.mSize, n);
            ++this.mSize;
        }
    }

    public class ThemeImpl {
        private final AssetManager mAssets;
        private final Resources.ThemeKey mKey = new Resources.ThemeKey();
        private final long mTheme;
        private int mThemeResId = 0;

        ThemeImpl() {
            this.mAssets = ResourcesImpl.this.mAssets;
            this.mTheme = this.mAssets.createTheme();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void applyStyle(int n, boolean bl) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                this.mAssets.applyStyleToTheme(this.mTheme, n, bl);
                this.mThemeResId = n;
                this.mKey.append(n, bl);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void dump(int n, String string2, String string3) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                this.mAssets.dumpTheme(this.mTheme, n, string2, string3);
                return;
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.mAssets.releaseTheme(this.mTheme);
        }

        int[] getAllAttributes() {
            return this.mAssets.getStyleAttributes(this.getAppliedStyleResId());
        }

        int getAppliedStyleResId() {
            return this.mThemeResId;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int[] getAttributeResolutionStack(int n, int n2, int n3) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                return this.mAssets.getAttributeResolutionStack(this.mTheme, n, n2, n3);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        int getChangingConfigurations() {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                return ActivityInfo.activityInfoConfigNativeToJava(AssetManager.nativeThemeGetChangingConfigurations(this.mTheme));
            }
        }

        Resources.ThemeKey getKey() {
            return this.mKey;
        }

        long getNativeTheme() {
            return this.mTheme;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        String[] getTheme() {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                int n = this.mKey.mCount;
                String[] arrstring = new String[n * 2];
                int n2 = 0;
                --n;
                while (n2 < arrstring.length) {
                    int n3 = this.mKey.mResId[n];
                    boolean bl = this.mKey.mForce[n];
                    try {
                        arrstring[n2] = ResourcesImpl.this.getResourceName(n3);
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        arrstring[n2] = Integer.toHexString(n2);
                    }
                    String string2 = bl ? "forced" : "not forced";
                    arrstring[n2 + 1] = string2;
                    n2 += 2;
                    --n;
                }
                return arrstring;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        TypedArray obtainStyledAttributes(Resources.Theme theme, AttributeSet attributeSet, int[] arrn, int n, int n2) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                void var4_5;
                XmlBlock.Parser parser;
                void var3_4;
                void var5_6;
                int n3 = ((void)var3_4).length;
                TypedArray typedArray = TypedArray.obtain(theme.getResources(), n3);
                parser = parser;
                this.mAssets.applyStyle(this.mTheme, (int)var4_5, (int)var5_6, parser, (int[])var3_4, typedArray.mDataAddress, typedArray.mIndicesAddress);
                try {
                    typedArray.mTheme = theme;
                    typedArray.mXml = parser;
                    return typedArray;
                }
                catch (Throwable throwable) {}
                throw throwable;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void rebase() {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                AssetManager.nativeThemeClear(this.mTheme);
                int n = 0;
                while (n < this.mKey.mCount) {
                    int n2 = this.mKey.mResId[n];
                    boolean bl = this.mKey.mForce[n];
                    this.mAssets.applyStyleToTheme(this.mTheme, n2, bl);
                    ++n;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        boolean resolveAttribute(int n, TypedValue typedValue, boolean bl) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                return this.mAssets.getThemeValue(this.mTheme, n, typedValue, bl);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        TypedArray resolveAttributes(Resources.Theme object, int[] arrn, int[] arrn2) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                int n = arrn2.length;
                if (arrn != null && n == arrn.length) {
                    TypedArray typedArray = TypedArray.obtain(((Resources.Theme)object).getResources(), n);
                    this.mAssets.resolveAttrs(this.mTheme, 0, 0, arrn, arrn2, typedArray.mData, typedArray.mIndices);
                    typedArray.mTheme = object;
                    typedArray.mXml = null;
                    return typedArray;
                }
                object = new IllegalArgumentException("Base attribute values must the same length as attrs");
                throw object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void setTo(ThemeImpl themeImpl) {
            Resources.ThemeKey themeKey = this.mKey;
            synchronized (themeKey) {
                Resources.ThemeKey themeKey2 = themeImpl.mKey;
                synchronized (themeKey2) {
                    this.mAssets.setThemeTo(this.mTheme, themeImpl.mAssets, themeImpl.mTheme);
                    this.mThemeResId = themeImpl.mThemeResId;
                    this.mKey.setTo(themeImpl.getKey());
                    return;
                }
            }
        }
    }

}

