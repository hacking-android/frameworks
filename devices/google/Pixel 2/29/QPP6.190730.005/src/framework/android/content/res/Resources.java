/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.res.-$
 *  android.content.res.-$$Lambda
 *  android.content.res.-$$Lambda$Resources
 *  android.content.res.-$$Lambda$Resources$4msWUw7LKsgLexLZjIfWa4oguq4
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.res.-$;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.ComplexColor;
import android.content.res.Configuration;
import android.content.res.ConfigurationBoundResourceCache;
import android.content.res.ResourcesImpl;
import android.content.res.TypedArray;
import android.content.res.XmlBlock;
import android.content.res.XmlResourceParser;
import android.content.res._$$Lambda$Resources$4msWUw7LKsgLexLZjIfWa4oguq4;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableInflater;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pools;
import android.util.TypedValue;
import android.view.DisplayAdjustments;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

public class Resources {
    public static final int ID_NULL = 0;
    private static final int MIN_THEME_REFS_FLUSH_SIZE = 32;
    static final String TAG = "Resources";
    @UnsupportedAppUsage
    static Resources mSystem;
    private static final Object sSync;
    @UnsupportedAppUsage
    final ClassLoader mClassLoader;
    @UnsupportedAppUsage
    private DrawableInflater mDrawableInflater;
    @UnsupportedAppUsage
    private ResourcesImpl mResourcesImpl;
    private final ArrayList<WeakReference<Theme>> mThemeRefs = new ArrayList();
    private int mThemeRefsNextFlushSize = 32;
    @UnsupportedAppUsage
    private TypedValue mTmpValue = new TypedValue();
    private final Object mTmpValueLock = new Object();
    @UnsupportedAppUsage
    final Pools.SynchronizedPool<TypedArray> mTypedArrayPool = new Pools.SynchronizedPool(5);

    static {
        sSync = new Object();
        mSystem = null;
    }

    @UnsupportedAppUsage
    private Resources() {
        this(null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        this.mResourcesImpl = new ResourcesImpl(AssetManager.getSystem(), displayMetrics, configuration, new DisplayAdjustments());
    }

    @Deprecated
    public Resources(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration) {
        this(null);
        this.mResourcesImpl = new ResourcesImpl(assetManager, displayMetrics, configuration, new DisplayAdjustments());
    }

    @UnsupportedAppUsage
    public Resources(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        this.mClassLoader = classLoader;
    }

    public static int getAttributeSetSourceResId(AttributeSet attributeSet) {
        return ResourcesImpl.getAttributeSetSourceResId(attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Resources getSystem() {
        Object object = sSync;
        synchronized (object) {
            Resources resources;
            Resources resources2 = resources = mSystem;
            if (resources == null) {
                mSystem = resources2 = new Resources();
            }
            return resources2;
        }
    }

    static /* synthetic */ boolean lambda$newTheme$0(WeakReference weakReference) {
        boolean bl = weakReference.get() == null;
        return bl;
    }

    public static TypedArray obtainAttributes(Resources resources, Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private TypedValue obtainTempTypedValue() {
        TypedValue typedValue = null;
        Object object = this.mTmpValueLock;
        // MONITORENTER : object
        if (this.mTmpValue != null) {
            typedValue = this.mTmpValue;
            this.mTmpValue = null;
        }
        // MONITOREXIT : object
        if (typedValue != null) return typedValue;
        return new TypedValue();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseTempTypedValue(TypedValue typedValue) {
        Object object = this.mTmpValueLock;
        synchronized (object) {
            if (this.mTmpValue == null) {
                this.mTmpValue = typedValue;
            }
            return;
        }
    }

    public static boolean resourceHasPackage(int n) {
        boolean bl = n >>> 24 != 0;
        return bl;
    }

    @UnsupportedAppUsage
    public static int selectDefaultTheme(int n, int n2) {
        return Resources.selectSystemTheme(n, n2, 16973829, 16973931, 16974120, 16974143);
    }

    public static int selectSystemTheme(int n, int n2, int n3, int n4, int n5, int n6) {
        if (n != 0) {
            return n;
        }
        if (n2 < 11) {
            return n3;
        }
        if (n2 < 14) {
            return n4;
        }
        if (n2 < 24) {
            return n5;
        }
        return n6;
    }

    @UnsupportedAppUsage
    public static void updateSystemConfiguration(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo) {
        Resources resources = mSystem;
        if (resources != null) {
            resources.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
        }
    }

    @VisibleForTesting
    public int calcConfigChanges(Configuration configuration) {
        return this.mResourcesImpl.calcConfigChanges(configuration);
    }

    public final void finishPreloading() {
        this.mResourcesImpl.finishPreloading();
    }

    public final void flushLayoutCache() {
        this.mResourcesImpl.flushLayoutCache();
    }

    public XmlResourceParser getAnimation(int n) throws NotFoundException {
        return this.loadXmlResourceParser(n, "anim");
    }

    public ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
        return this.mResourcesImpl.getAnimatorCache();
    }

    public final AssetManager getAssets() {
        return this.mResourcesImpl.getAssets();
    }

    public boolean getBoolean(int n) throws NotFoundException {
        TypedValue typedValue;
        Object object;
        block5 : {
            boolean bl;
            typedValue = this.obtainTempTypedValue();
            try {
                object = this.mResourcesImpl;
                bl = true;
            }
            catch (Throwable throwable) {
                this.releaseTempTypedValue(typedValue);
                throw throwable;
            }
            ((ResourcesImpl)object).getValue(n, typedValue, true);
            if (typedValue.type < 16 || typedValue.type > 31) break block5;
            n = typedValue.data;
            if (n == 0) {
                bl = false;
            }
            this.releaseTempTypedValue(typedValue);
            return bl;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        ((StringBuilder)object).append(" type #0x");
        ((StringBuilder)object).append(Integer.toHexString(typedValue.type));
        ((StringBuilder)object).append(" is not valid");
        NotFoundException notFoundException = new NotFoundException(((StringBuilder)object).toString());
        throw notFoundException;
    }

    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    @Deprecated
    public int getColor(int n) throws NotFoundException {
        return this.getColor(n, null);
    }

    public int getColor(int n, Theme object) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            Object object2 = this.mResourcesImpl;
            ((ResourcesImpl)object2).getValue(n, typedValue, true);
            if (typedValue.type >= 16 && typedValue.type <= 31) {
                n = typedValue.data;
                return n;
            }
            if (typedValue.type == 3) {
                n = ((ResourcesImpl)object2).loadColorStateList(this, typedValue, n, (Theme)object).getDefaultColor();
                return n;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Resource ID #0x");
            ((StringBuilder)object2).append(Integer.toHexString(n));
            ((StringBuilder)object2).append(" type #0x");
            ((StringBuilder)object2).append(Integer.toHexString(typedValue.type));
            ((StringBuilder)object2).append(" is not valid");
            object = new NotFoundException(((StringBuilder)object2).toString());
            throw object;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    @Deprecated
    public ColorStateList getColorStateList(int n) throws NotFoundException {
        ColorStateList colorStateList = this.getColorStateList(n, null);
        if (colorStateList != null && colorStateList.canApplyTheme()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ColorStateList ");
            stringBuilder.append(this.getResourceName(n));
            stringBuilder.append(" has unresolved theme attributes! Consider using Resources.getColorStateList(int, Theme) or Context.getColorStateList(int).");
            Log.w(TAG, stringBuilder.toString(), new RuntimeException());
        }
        return colorStateList;
    }

    public ColorStateList getColorStateList(int n, Theme object) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(n, typedValue, true);
            object = resourcesImpl.loadColorStateList(this, typedValue, n, (Theme)object);
            return object;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    @UnsupportedAppUsage
    public CompatibilityInfo getCompatibilityInfo() {
        return this.mResourcesImpl.getCompatibilityInfo();
    }

    public Configuration getConfiguration() {
        return this.mResourcesImpl.getConfiguration();
    }

    public float getDimension(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            Object object = this.mResourcesImpl;
            ((ResourcesImpl)object).getValue(n, typedValue, true);
            if (typedValue.type == 5) {
                float f = TypedValue.complexToDimension(typedValue.data, ((ResourcesImpl)object).getDisplayMetrics());
                return f;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(" type #0x");
            stringBuilder.append(Integer.toHexString(typedValue.type));
            stringBuilder.append(" is not valid");
            object = new NotFoundException(stringBuilder.toString());
            throw object;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public int getDimensionPixelOffset(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            Object object = this.mResourcesImpl;
            ((ResourcesImpl)object).getValue(n, typedValue, true);
            if (typedValue.type == 5) {
                n = TypedValue.complexToDimensionPixelOffset(typedValue.data, ((ResourcesImpl)object).getDisplayMetrics());
                return n;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Resource ID #0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(" type #0x");
            ((StringBuilder)object).append(Integer.toHexString(typedValue.type));
            ((StringBuilder)object).append(" is not valid");
            NotFoundException notFoundException = new NotFoundException(((StringBuilder)object).toString());
            throw notFoundException;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public int getDimensionPixelSize(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            Object object = this.mResourcesImpl;
            ((ResourcesImpl)object).getValue(n, typedValue, true);
            if (typedValue.type == 5) {
                n = TypedValue.complexToDimensionPixelSize(typedValue.data, ((ResourcesImpl)object).getDisplayMetrics());
                return n;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Resource ID #0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            ((StringBuilder)object).append(" type #0x");
            ((StringBuilder)object).append(Integer.toHexString(typedValue.type));
            ((StringBuilder)object).append(" is not valid");
            NotFoundException notFoundException = new NotFoundException(((StringBuilder)object).toString());
            throw notFoundException;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    @UnsupportedAppUsage
    public DisplayAdjustments getDisplayAdjustments() {
        return this.mResourcesImpl.getDisplayAdjustments();
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mResourcesImpl.getDisplayMetrics();
    }

    @Deprecated
    public Drawable getDrawable(int n) throws NotFoundException {
        Drawable drawable2 = this.getDrawable(n, null);
        if (drawable2 != null && drawable2.canApplyTheme()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Drawable ");
            stringBuilder.append(this.getResourceName(n));
            stringBuilder.append(" has unresolved theme attributes! Consider using Resources.getDrawable(int, Theme) or Context.getDrawable(int).");
            Log.w(TAG, stringBuilder.toString(), new RuntimeException());
        }
        return drawable2;
    }

    public Drawable getDrawable(int n, Theme theme) throws NotFoundException {
        return this.getDrawableForDensity(n, 0, theme);
    }

    @Deprecated
    public Drawable getDrawableForDensity(int n, int n2) throws NotFoundException {
        return this.getDrawableForDensity(n, n2, null);
    }

    public Drawable getDrawableForDensity(int n, int n2, Theme object) {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValueForDensity(n, n2, typedValue, true);
            object = resourcesImpl.loadDrawable(this, typedValue, n, n2, (Theme)object);
            return object;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    @UnsupportedAppUsage
    public final DrawableInflater getDrawableInflater() {
        if (this.mDrawableInflater == null) {
            this.mDrawableInflater = new DrawableInflater(this, this.mClassLoader);
        }
        return this.mDrawableInflater;
    }

    public float getFloat(int n) {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(n, typedValue, true);
            if (typedValue.type == 4) {
                float f = typedValue.getFloat();
                return f;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(" type #0x");
            stringBuilder.append(Integer.toHexString(typedValue.type));
            stringBuilder.append(" is not valid");
            NotFoundException notFoundException = new NotFoundException(stringBuilder.toString());
            throw notFoundException;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public Typeface getFont(int n) throws NotFoundException {
        Object object = this.obtainTempTypedValue();
        try {
            Object object2 = this.mResourcesImpl;
            ((ResourcesImpl)object2).getValue(n, (TypedValue)object, true);
            object2 = ((ResourcesImpl)object2).loadFont(this, (TypedValue)object, n);
            if (object2 != null) {
                this.releaseTempTypedValue((TypedValue)object);
                return object2;
            }
            this.releaseTempTypedValue((TypedValue)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("Font resource ID #0x");
        }
        catch (Throwable throwable) {
            this.releaseTempTypedValue((TypedValue)object);
            throw throwable;
        }
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new NotFoundException(((StringBuilder)object).toString());
    }

    Typeface getFont(TypedValue typedValue, int n) throws NotFoundException {
        return this.mResourcesImpl.loadFont(this, typedValue, n);
    }

    public float getFraction(int n, int n2, int n3) {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(n, typedValue, true);
            if (typedValue.type == 6) {
                float f = TypedValue.complexToFraction(typedValue.data, n2, n3);
                return f;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(" type #0x");
            stringBuilder.append(Integer.toHexString(typedValue.type));
            stringBuilder.append(" is not valid");
            NotFoundException notFoundException = new NotFoundException(stringBuilder.toString());
            throw notFoundException;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public int getIdentifier(String string2, String string3, String string4) {
        return this.mResourcesImpl.getIdentifier(string2, string3, string4);
    }

    @UnsupportedAppUsage
    public ResourcesImpl getImpl() {
        return this.mResourcesImpl;
    }

    public int[] getIntArray(int n) throws NotFoundException {
        Object object = this.mResourcesImpl.getAssets().getResourceIntArray(n);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Int array resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new NotFoundException(((StringBuilder)object).toString());
    }

    public int getInteger(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(n, typedValue, true);
            if (typedValue.type >= 16 && typedValue.type <= 31) {
                n = typedValue.data;
                return n;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resource ID #0x");
            stringBuilder.append(Integer.toHexString(n));
            stringBuilder.append(" type #0x");
            stringBuilder.append(Integer.toHexString(typedValue.type));
            stringBuilder.append(" is not valid");
            NotFoundException notFoundException = new NotFoundException(stringBuilder.toString());
            throw notFoundException;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public String getLastResourceResolution() throws NotFoundException {
        return this.mResourcesImpl.getLastResourceResolution();
    }

    public XmlResourceParser getLayout(int n) throws NotFoundException {
        return this.loadXmlResourceParser(n, "layout");
    }

    @Deprecated
    public Movie getMovie(int n) throws NotFoundException {
        InputStream inputStream = this.openRawResource(n);
        Movie movie = Movie.decodeStream(inputStream);
        try {
            inputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return movie;
    }

    @UnsupportedAppUsage
    public LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
        return this.mResourcesImpl.getPreloadedDrawables();
    }

    public String getQuantityString(int n, int n2) throws NotFoundException {
        return this.getQuantityText(n, n2).toString();
    }

    public String getQuantityString(int n, int n2, Object ... arrobject) throws NotFoundException {
        String string2 = this.getQuantityText(n, n2).toString();
        return String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), string2, arrobject);
    }

    public CharSequence getQuantityText(int n, int n2) throws NotFoundException {
        return this.mResourcesImpl.getQuantityText(n, n2);
    }

    public String getResourceEntryName(int n) throws NotFoundException {
        return this.mResourcesImpl.getResourceEntryName(n);
    }

    public String getResourceName(int n) throws NotFoundException {
        return this.mResourcesImpl.getResourceName(n);
    }

    public String getResourcePackageName(int n) throws NotFoundException {
        return this.mResourcesImpl.getResourcePackageName(n);
    }

    public String getResourceTypeName(int n) throws NotFoundException {
        return this.mResourcesImpl.getResourceTypeName(n);
    }

    public Configuration[] getSizeConfigurations() {
        return this.mResourcesImpl.getSizeConfigurations();
    }

    public ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
        return this.mResourcesImpl.getStateListAnimatorCache();
    }

    public String getString(int n) throws NotFoundException {
        return this.getText(n).toString();
    }

    public String getString(int n, Object ... arrobject) throws NotFoundException {
        String string2 = this.getString(n);
        return String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), string2, arrobject);
    }

    public String[] getStringArray(int n) throws NotFoundException {
        Object object = this.mResourcesImpl.getAssets().getResourceStringArray(n);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("String array resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new NotFoundException(((StringBuilder)object).toString());
    }

    public CharSequence getText(int n) throws NotFoundException {
        CharSequence charSequence = this.mResourcesImpl.getAssets().getResourceText(n);
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("String resource ID #0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(n));
        throw new NotFoundException(((StringBuilder)charSequence).toString());
    }

    public CharSequence getText(int n, CharSequence charSequence) {
        block0 : {
            CharSequence charSequence2 = n != 0 ? this.mResourcesImpl.getAssets().getResourceText(n) : null;
            if (charSequence2 == null) break block0;
            charSequence = charSequence2;
        }
        return charSequence;
    }

    public CharSequence[] getTextArray(int n) throws NotFoundException {
        Object object = this.mResourcesImpl.getAssets().getResourceTextArray(n);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Text array resource ID #0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        throw new NotFoundException(((StringBuilder)object).toString());
    }

    public void getValue(int n, TypedValue typedValue, boolean bl) throws NotFoundException {
        this.mResourcesImpl.getValue(n, typedValue, bl);
    }

    public void getValue(String string2, TypedValue typedValue, boolean bl) throws NotFoundException {
        this.mResourcesImpl.getValue(string2, typedValue, bl);
    }

    public void getValueForDensity(int n, int n2, TypedValue typedValue, boolean bl) throws NotFoundException {
        this.mResourcesImpl.getValueForDensity(n, n2, typedValue, bl);
    }

    public XmlResourceParser getXml(int n) throws NotFoundException {
        return this.loadXmlResourceParser(n, "xml");
    }

    ColorStateList loadColorStateList(TypedValue typedValue, int n, Theme theme) throws NotFoundException {
        return this.mResourcesImpl.loadColorStateList(this, typedValue, n, theme);
    }

    public ComplexColor loadComplexColor(TypedValue typedValue, int n, Theme theme) {
        return this.mResourcesImpl.loadComplexColor(this, typedValue, n, theme);
    }

    @UnsupportedAppUsage
    Drawable loadDrawable(TypedValue typedValue, int n, int n2, Theme theme) throws NotFoundException {
        return this.mResourcesImpl.loadDrawable(this, typedValue, n, n2, theme);
    }

    @UnsupportedAppUsage
    XmlResourceParser loadXmlResourceParser(int n, String object) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            Object object2 = this.mResourcesImpl;
            ((ResourcesImpl)object2).getValue(n, typedValue, true);
            if (typedValue.type == 3) {
                object = ((ResourcesImpl)object2).loadXmlResourceParser(typedValue.string.toString(), n, typedValue.assetCookie, (String)object);
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Resource ID #0x");
            ((StringBuilder)object2).append(Integer.toHexString(n));
            ((StringBuilder)object2).append(" type #0x");
            ((StringBuilder)object2).append(Integer.toHexString(typedValue.type));
            ((StringBuilder)object2).append(" is not valid");
            object = new NotFoundException(((StringBuilder)object2).toString());
            throw object;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    @UnsupportedAppUsage
    XmlResourceParser loadXmlResourceParser(String string2, int n, int n2, String string3) throws NotFoundException {
        return this.mResourcesImpl.loadXmlResourceParser(string2, n, n2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final Theme newTheme() {
        Theme theme = new Theme();
        theme.setImpl(this.mResourcesImpl.newThemeImpl());
        ArrayList<WeakReference<Theme>> arrayList = this.mThemeRefs;
        synchronized (arrayList) {
            ArrayList<WeakReference<Theme>> arrayList2 = this.mThemeRefs;
            WeakReference<Theme> weakReference = new WeakReference<Theme>(theme);
            arrayList2.add(weakReference);
            if (this.mThemeRefs.size() > this.mThemeRefsNextFlushSize) {
                this.mThemeRefs.removeIf((Predicate<WeakReference<Theme>>)_$$Lambda$Resources$4msWUw7LKsgLexLZjIfWa4oguq4.INSTANCE);
                this.mThemeRefsNextFlushSize = Math.max(32, this.mThemeRefs.size() * 2);
            }
            return theme;
        }
    }

    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] arrn) {
        TypedArray typedArray = TypedArray.obtain(this, arrn.length);
        attributeSet = (XmlBlock.Parser)attributeSet;
        this.mResourcesImpl.getAssets().retrieveAttributes((XmlBlock.Parser)attributeSet, arrn, typedArray.mData, typedArray.mIndices);
        typedArray.mXml = attributeSet;
        return typedArray;
    }

    public TypedArray obtainTypedArray(int n) throws NotFoundException {
        ResourcesImpl resourcesImpl = this.mResourcesImpl;
        int n2 = resourcesImpl.getAssets().getResourceArraySize(n);
        if (n2 >= 0) {
            TypedArray typedArray = TypedArray.obtain(this, n2);
            typedArray.mLength = resourcesImpl.getAssets().getResourceArray(n, typedArray.mData);
            typedArray.mIndices[0] = 0;
            return typedArray;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Array resource ID #0x");
        stringBuilder.append(Integer.toHexString(n));
        throw new NotFoundException(stringBuilder.toString());
    }

    public InputStream openRawResource(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            InputStream inputStream = this.openRawResource(n, typedValue);
            return inputStream;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public InputStream openRawResource(int n, TypedValue typedValue) throws NotFoundException {
        return this.mResourcesImpl.openRawResource(n, typedValue);
    }

    public AssetFileDescriptor openRawResourceFd(int n) throws NotFoundException {
        TypedValue typedValue = this.obtainTempTypedValue();
        try {
            AssetFileDescriptor assetFileDescriptor = this.mResourcesImpl.openRawResourceFd(n, typedValue);
            return assetFileDescriptor;
        }
        finally {
            this.releaseTempTypedValue(typedValue);
        }
    }

    public void parseBundleExtra(String string2, AttributeSet attributeSet, Bundle object) throws XmlPullParserException {
        TypedArray typedArray;
        block4 : {
            block5 : {
                block10 : {
                    block7 : {
                        String string3;
                        TypedValue typedValue;
                        block9 : {
                            block8 : {
                                boolean bl;
                                block6 : {
                                    typedArray = this.obtainAttributes(attributeSet, R.styleable.Extra);
                                    bl = false;
                                    string3 = typedArray.getString(0);
                                    if (string3 == null) break block4;
                                    typedValue = typedArray.peekValue(1);
                                    if (typedValue == null) break block5;
                                    if (typedValue.type != 3) break block6;
                                    ((Bundle)object).putCharSequence(string3, typedValue.coerceToString());
                                    break block7;
                                }
                                if (typedValue.type != 18) break block8;
                                if (typedValue.data != 0) {
                                    bl = true;
                                }
                                ((BaseBundle)object).putBoolean(string3, bl);
                                break block7;
                            }
                            if (typedValue.type < 16 || typedValue.type > 31) break block9;
                            ((BaseBundle)object).putInt(string3, typedValue.data);
                            break block7;
                        }
                        if (typedValue.type != 4) break block10;
                        ((Bundle)object).putFloat(string3, typedValue.getFloat());
                    }
                    typedArray.recycle();
                    return;
                }
                typedArray.recycle();
                object = new StringBuilder();
                ((StringBuilder)object).append("<");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("> only supports string, integer, float, color, and boolean at ");
                ((StringBuilder)object).append(attributeSet.getPositionDescription());
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            typedArray.recycle();
            object = new StringBuilder();
            ((StringBuilder)object).append("<");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("> requires an android:value or android:resource attribute at ");
            ((StringBuilder)object).append(attributeSet.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        typedArray.recycle();
        object = new StringBuilder();
        ((StringBuilder)object).append("<");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("> requires an android:name attribute at ");
        ((StringBuilder)object).append(attributeSet.getPositionDescription());
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlResourceParser.getDepth();
        while ((n = xmlResourceParser.next()) != 1 && (n != 3 || xmlResourceParser.getDepth() > n2)) {
            if (n == 3 || n == 4) continue;
            if (xmlResourceParser.getName().equals("extra")) {
                this.parseBundleExtra("extra", xmlResourceParser, bundle);
                XmlUtils.skipCurrentTag(xmlResourceParser);
                continue;
            }
            XmlUtils.skipCurrentTag(xmlResourceParser);
        }
    }

    public void preloadFonts(int n) {
        int n2;
        TypedArray typedArray = this.obtainTypedArray(n);
        try {
            n2 = typedArray.length();
        }
        catch (Throwable throwable) {
            typedArray.recycle();
            throw throwable;
        }
        for (n = 0; n < n2; ++n) {
            typedArray.getFont(n);
        }
        typedArray.recycle();
        return;
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        if (compatibilityInfo != null) {
            this.mResourcesImpl.updateConfiguration(null, null, compatibilityInfo);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setImpl(ResourcesImpl weakReference) {
        if (weakReference == this.mResourcesImpl) {
            return;
        }
        this.mResourcesImpl = weakReference;
        ArrayList<WeakReference<Theme>> arrayList = this.mThemeRefs;
        synchronized (arrayList) {
            int n = this.mThemeRefs.size();
            int n2 = 0;
            while (n2 < n) {
                weakReference = this.mThemeRefs.get(n2);
                weakReference = weakReference != null ? (Theme)weakReference.get() : null;
                if (weakReference != null) {
                    ((Theme)((Object)weakReference)).setImpl(this.mResourcesImpl.newThemeImpl(((Theme)((Object)weakReference)).getKey()));
                }
                ++n2;
            }
            return;
        }
    }

    public final void startPreloading() {
        this.mResourcesImpl.startPreloading();
    }

    @Deprecated
    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        this.updateConfiguration(configuration, displayMetrics, null);
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo) {
        this.mResourcesImpl.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
    }

    public static class NotFoundException
    extends RuntimeException {
        public NotFoundException() {
        }

        public NotFoundException(String string2) {
            super(string2);
        }

        public NotFoundException(String string2, Exception exception) {
            super(string2, exception);
        }
    }

    public final class Theme {
        @UnsupportedAppUsage
        private ResourcesImpl.ThemeImpl mThemeImpl;

        private Theme() {
        }

        private String getResourceNameFromHexString(String string2) {
            return Resources.this.getResourceName(Integer.parseInt(string2, 16));
        }

        public void applyStyle(int n, boolean bl) {
            this.mThemeImpl.applyStyle(n, bl);
        }

        public void dump(int n, String string2, String string3) {
            this.mThemeImpl.dump(n, string2, string3);
        }

        public void encode(ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.beginObject(this);
            String[] arrstring = this.getTheme();
            for (int i = 0; i < arrstring.length; i += 2) {
                viewHierarchyEncoder.addProperty(arrstring[i], arrstring[i + 1]);
            }
            viewHierarchyEncoder.endObject();
        }

        public int[] getAllAttributes() {
            return this.mThemeImpl.getAllAttributes();
        }

        int getAppliedStyleResId() {
            return this.mThemeImpl.getAppliedStyleResId();
        }

        public int[] getAttributeResolutionStack(int n, int n2, int n3) {
            int[] arrn = this.mThemeImpl.getAttributeResolutionStack(n, n2, n3);
            if (arrn == null) {
                return new int[0];
            }
            return arrn;
        }

        public int getChangingConfigurations() {
            return this.mThemeImpl.getChangingConfigurations();
        }

        public Drawable getDrawable(int n) throws NotFoundException {
            return Resources.this.getDrawable(n, this);
        }

        public int getExplicitStyle(AttributeSet object) {
            if (object == null) {
                return 0;
            }
            int n = object.getStyleAttribute();
            if (n == 0) {
                return 0;
            }
            object = this.getResources().getResourceTypeName(n);
            if ("attr".equals(object)) {
                object = new TypedValue();
                if (this.resolveAttribute(n, (TypedValue)object, true)) {
                    return ((TypedValue)object).resourceId;
                }
            } else if ("style".equals(object)) {
                return n;
            }
            return 0;
        }

        public ThemeKey getKey() {
            return this.mThemeImpl.getKey();
        }

        long getNativeTheme() {
            return this.mThemeImpl.getNativeTheme();
        }

        public Resources getResources() {
            return Resources.this;
        }

        @ViewDebug.ExportedProperty(category="theme", hasAdjacentMapping=true)
        public String[] getTheme() {
            return this.mThemeImpl.getTheme();
        }

        public TypedArray obtainStyledAttributes(int n, int[] arrn) throws NotFoundException {
            return this.mThemeImpl.obtainStyledAttributes(this, null, arrn, 0, n);
        }

        public TypedArray obtainStyledAttributes(AttributeSet attributeSet, int[] arrn, int n, int n2) {
            return this.mThemeImpl.obtainStyledAttributes(this, attributeSet, arrn, n, n2);
        }

        public TypedArray obtainStyledAttributes(int[] arrn) {
            return this.mThemeImpl.obtainStyledAttributes(this, null, arrn, 0, 0);
        }

        public void rebase() {
            this.mThemeImpl.rebase();
        }

        public boolean resolveAttribute(int n, TypedValue typedValue, boolean bl) {
            return this.mThemeImpl.resolveAttribute(n, typedValue, bl);
        }

        @UnsupportedAppUsage
        public TypedArray resolveAttributes(int[] arrn, int[] arrn2) {
            return this.mThemeImpl.resolveAttributes(this, arrn, arrn2);
        }

        void setImpl(ResourcesImpl.ThemeImpl themeImpl) {
            this.mThemeImpl = themeImpl;
        }

        public void setTo(Theme theme) {
            this.mThemeImpl.setTo(theme.mThemeImpl);
        }
    }

    static class ThemeKey
    implements Cloneable {
        int mCount;
        boolean[] mForce;
        private int mHashCode = 0;
        int[] mResId;

        ThemeKey() {
        }

        public void append(int n, boolean bl) {
            if (this.mResId == null) {
                this.mResId = new int[4];
            }
            if (this.mForce == null) {
                this.mForce = new boolean[4];
            }
            this.mResId = GrowingArrayUtils.append(this.mResId, this.mCount, n);
            this.mForce = GrowingArrayUtils.append(this.mForce, this.mCount, bl);
            ++this.mCount;
            this.mHashCode = (this.mHashCode * 31 + n) * 31 + bl;
        }

        public ThemeKey clone() {
            ThemeKey themeKey = new ThemeKey();
            themeKey.mResId = this.mResId;
            themeKey.mForce = this.mForce;
            themeKey.mCount = this.mCount;
            themeKey.mHashCode = this.mHashCode;
            return themeKey;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass() && this.hashCode() == object.hashCode()) {
                object = (ThemeKey)object;
                if (this.mCount != ((ThemeKey)object).mCount) {
                    return false;
                }
                int n = this.mCount;
                for (int i = 0; i < n; ++i) {
                    if (this.mResId[i] == ((ThemeKey)object).mResId[i] && this.mForce[i] == ((ThemeKey)object).mForce[i]) {
                        continue;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public void setTo(ThemeKey themeKey) {
            Object object = themeKey.mResId;
            Object var3_3 = null;
            object = object == null ? null : (int[])object.clone();
            this.mResId = object;
            object = themeKey.mForce;
            object = object == null ? var3_3 : (boolean[])object.clone();
            this.mForce = object;
            this.mCount = themeKey.mCount;
        }
    }

}

