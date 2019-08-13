/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$Drawable
 *  android.graphics.drawable.-$$Lambda$Drawable$KZt6g0-IxKV2yrq1V3HrWrb1kXg
 *  android.graphics.drawable.-$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.NinePatch;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.-$;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.DrawableInflater;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable._$$Lambda$Drawable$KZt6g0_IxKV2yrq1V3HrWrb1kXg;
import android.graphics.drawable._$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import com.android.internal.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class Drawable {
    static final BlendMode DEFAULT_BLEND_MODE;
    static final PorterDuff.Mode DEFAULT_TINT_MODE;
    private static final Rect ZERO_BOUNDS_RECT;
    private Rect mBounds = ZERO_BOUNDS_RECT;
    @UnsupportedAppUsage
    private WeakReference<Callback> mCallback = null;
    private int mChangingConfigurations = 0;
    private int mLayoutDirection;
    private int mLevel = 0;
    private boolean mSetBlendModeInvoked = false;
    private boolean mSetTintModeInvoked = false;
    @UnsupportedAppUsage
    protected int mSrcDensityOverride = 0;
    private int[] mStateSet = StateSet.WILD_CARD;
    private boolean mVisible = true;

    static {
        ZERO_BOUNDS_RECT = new Rect();
        DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
        DEFAULT_BLEND_MODE = BlendMode.SRC_IN;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Drawable createFromPath(String object) {
        if (object == null) {
            return null;
        }
        Trace.traceBegin(8192L, (String)object);
        FileInputStream fileInputStream = new FileInputStream((String)object);
        object = Drawable.getBitmapDrawable(null, null, fileInputStream);
        fileInputStream.close();
        return object;
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                try {
                    fileInputStream.close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (Throwable throwable4) {
                        throw throwable4;
                    }
                    catch (IOException iOException) {
                        return null;
                    }
                    finally {
                    }
                }
            }
        }
        finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable createFromResourceStream(Resources object, TypedValue typedValue, InputStream inputStream, String string2) {
        String string3 = string2 != null ? string2 : "Unknown drawable";
        Trace.traceBegin(8192L, string3);
        try {
            object = Drawable.createFromResourceStream((Resources)object, typedValue, inputStream, string2, null);
            return object;
        }
        finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable createFromResourceStream(Resources resources, TypedValue arrby, InputStream object, String string2, BitmapFactory.Options object2) {
        block5 : {
            Bitmap bitmap;
            block7 : {
                block6 : {
                    if (object == null) {
                        return null;
                    }
                    if (object2 == null) {
                        return Drawable.getBitmapDrawable(resources, (TypedValue)arrby, (InputStream)object);
                    }
                    Rect rect = new Rect();
                    object2.inScreenDensity = Drawable.resolveDensity(resources, 0);
                    bitmap = BitmapFactory.decodeResourceStream(resources, (TypedValue)arrby, (InputStream)object, rect, (BitmapFactory.Options)object2);
                    if (bitmap == null) break block5;
                    object2 = bitmap.getNinePatchChunk();
                    if (object2 == null) break block6;
                    arrby = object2;
                    object = rect;
                    if (NinePatch.isNinePatchChunk(object2)) break block7;
                }
                arrby = null;
                object = null;
            }
            object2 = new Rect();
            bitmap.getOpticalInsets((Rect)object2);
            return Drawable.drawableFromBitmap(resources, bitmap, arrby, (Rect)object, (Rect)object2, string2);
        }
        return null;
    }

    public static Drawable createFromStream(InputStream object, String string2) {
        String string3 = string2 != null ? string2 : "Unknown drawable";
        Trace.traceBegin(8192L, string3);
        try {
            object = Drawable.createFromResourceStream(null, null, (InputStream)object, string2);
            return object;
        }
        finally {
            Trace.traceEnd(8192L);
        }
    }

    public static Drawable createFromXml(Resources resources, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return Drawable.createFromXml(resources, xmlPullParser, null);
    }

    public static Drawable createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        return Drawable.createFromXmlForDensity(resources, xmlPullParser, 0, theme);
    }

    public static Drawable createFromXmlForDensity(Resources object, XmlPullParser xmlPullParser, int n, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n2;
        AttributeSet attributeSet = Xml.asAttributeSet(xmlPullParser);
        while ((n2 = xmlPullParser.next()) != 2 && n2 != 1) {
        }
        if (n2 == 2) {
            if ((object = Drawable.createFromXmlInnerForDensity((Resources)object, xmlPullParser, attributeSet, n, theme)) != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown initial tag: ");
            ((StringBuilder)object).append(xmlPullParser.getName());
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new XmlPullParserException("No start tag found");
    }

    public static Drawable createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        return Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, null);
    }

    public static Drawable createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        return Drawable.createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, 0, theme);
    }

    static Drawable createFromXmlInnerForDensity(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, int n, Resources.Theme theme) throws XmlPullParserException, IOException {
        return resources.getDrawableInflater().inflateFromXmlForDensity(xmlPullParser.getName(), xmlPullParser, attributeSet, n, theme);
    }

    private static Drawable drawableFromBitmap(Resources resources, Bitmap bitmap, byte[] arrby, Rect rect, Rect rect2, String string2) {
        if (arrby != null) {
            return new NinePatchDrawable(resources, bitmap, arrby, rect, rect2, string2);
        }
        return new BitmapDrawable(resources, bitmap);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static Drawable getBitmapDrawable(Resources var0, TypedValue var1_2, InputStream var2_3) {
        if (var1_2 == null) ** GOTO lbl11
        var3_4 = 0;
        try {
            if (var1_2.density == 0) {
                var3_4 = 160;
            } else if (var1_2.density != 65535) {
                var3_4 = var1_2.density;
            }
            var0 = ImageDecoder.createSource((Resources)var0, var2_3, var3_4);
            return ImageDecoder.decodeDrawable((ImageDecoder.Source)var0, (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw.INSTANCE);
lbl11: // 1 sources:
            var0 = ImageDecoder.createSource((Resources)var0, var2_3);
            return ImageDecoder.decodeDrawable((ImageDecoder.Source)var0, (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$Drawable$bbJz2VgQAwkXlE27mR8nPMYacEw.INSTANCE);
        }
        catch (IOException var0_1) {
            var1_2 = new StringBuilder();
            var1_2.append("Unable to decode stream: ");
            var1_2.append(var0_1);
            Log.e("Drawable", var1_2.toString());
            return null;
        }
    }

    static /* synthetic */ boolean lambda$getBitmapDrawable$0(ImageDecoder.DecodeException decodeException) {
        boolean bl = decodeException.getError() == 2;
        return bl;
    }

    static /* synthetic */ void lambda$getBitmapDrawable$1(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        imageDecoder.setOnPartialImageListener((ImageDecoder.OnPartialImageListener)_$$Lambda$Drawable$KZt6g0_IxKV2yrq1V3HrWrb1kXg.INSTANCE);
    }

    protected static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, arrn);
        }
        return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
    }

    @UnsupportedAppUsage
    public static BlendMode parseBlendMode(int n, BlendMode blendMode) {
        if (n != 3) {
            if (n != 5) {
                if (n != 9) {
                    switch (n) {
                        default: {
                            return blendMode;
                        }
                        case 16: {
                            return BlendMode.PLUS;
                        }
                        case 15: {
                            return BlendMode.SCREEN;
                        }
                        case 14: 
                    }
                    return BlendMode.MODULATE;
                }
                return BlendMode.SRC_ATOP;
            }
            return BlendMode.SRC_IN;
        }
        return BlendMode.SRC_OVER;
    }

    @UnsupportedAppUsage
    public static PorterDuff.Mode parseTintMode(int n, PorterDuff.Mode mode) {
        if (n != 3) {
            if (n != 5) {
                if (n != 9) {
                    switch (n) {
                        default: {
                            return mode;
                        }
                        case 16: {
                            return PorterDuff.Mode.ADD;
                        }
                        case 15: {
                            return PorterDuff.Mode.SCREEN;
                        }
                        case 14: 
                    }
                    return PorterDuff.Mode.MULTIPLY;
                }
                return PorterDuff.Mode.SRC_ATOP;
            }
            return PorterDuff.Mode.SRC_IN;
        }
        return PorterDuff.Mode.SRC_OVER;
    }

    static int resolveDensity(Resources resources, int n) {
        block1 : {
            if (resources != null) {
                n = resources.getDisplayMetrics().densityDpi;
            }
            if (n != 0) break block1;
            n = 160;
        }
        return n;
    }

    public static int resolveOpacity(int n, int n2) {
        if (n == n2) {
            return n;
        }
        if (n != 0 && n2 != 0) {
            if (n != -3 && n2 != -3) {
                if (n != -2 && n2 != -2) {
                    return -1;
                }
                return -2;
            }
            return -3;
        }
        return 0;
    }

    static void rethrowAsRuntimeException(Exception exception) throws RuntimeException {
        exception = new RuntimeException(exception);
        exception.setStackTrace(new StackTraceElement[0]);
        throw exception;
    }

    static float scaleFromDensity(float f, int n, int n2) {
        return (float)n2 * f / (float)n;
    }

    static int scaleFromDensity(int n, int n2, int n3, boolean bl) {
        if (n != 0 && n2 != n3) {
            float f = (float)(n * n3) / (float)n2;
            if (!bl) {
                return (int)f;
            }
            n2 = Math.round(f);
            if (n2 != 0) {
                return n2;
            }
            if (n > 0) {
                return 1;
            }
            return -1;
        }
        return n;
    }

    public void applyTheme(Resources.Theme theme) {
    }

    public boolean canApplyTheme() {
        return false;
    }

    public void clearColorFilter() {
        this.setColorFilter(null);
    }

    public void clearMutated() {
    }

    public final Rect copyBounds() {
        return new Rect(this.mBounds);
    }

    public final void copyBounds(Rect rect) {
        rect.set(this.mBounds);
    }

    public abstract void draw(Canvas var1);

    public int getAlpha() {
        return 255;
    }

    public final Rect getBounds() {
        if (this.mBounds == ZERO_BOUNDS_RECT) {
            this.mBounds = new Rect();
        }
        return this.mBounds;
    }

    public Callback getCallback() {
        WeakReference<Callback> weakReference = this.mCallback;
        weakReference = weakReference != null ? (Callback)weakReference.get() : null;
        return weakReference;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public ColorFilter getColorFilter() {
        return null;
    }

    public ConstantState getConstantState() {
        return null;
    }

    public Drawable getCurrent() {
        return this;
    }

    public Rect getDirtyBounds() {
        return this.getBounds();
    }

    public void getHotspotBounds(Rect rect) {
        rect.set(this.getBounds());
    }

    public int getIntrinsicHeight() {
        return -1;
    }

    public int getIntrinsicWidth() {
        return -1;
    }

    public int getLayoutDirection() {
        return this.mLayoutDirection;
    }

    public final int getLevel() {
        return this.mLevel;
    }

    public int getMinimumHeight() {
        int n = this.getIntrinsicHeight();
        if (n <= 0) {
            n = 0;
        }
        return n;
    }

    public int getMinimumWidth() {
        int n = this.getIntrinsicWidth();
        if (n <= 0) {
            n = 0;
        }
        return n;
    }

    @Deprecated
    public abstract int getOpacity();

    public Insets getOpticalInsets() {
        return Insets.NONE;
    }

    public void getOutline(Outline outline) {
        outline.setRect(this.getBounds());
        outline.setAlpha(0.0f);
    }

    public boolean getPadding(Rect rect) {
        rect.set(0, 0, 0, 0);
        return false;
    }

    public int[] getState() {
        return this.mStateSet;
    }

    public Region getTransparentRegion() {
        return null;
    }

    public boolean hasFocusStateSpecified() {
        return false;
    }

    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        this.inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        object = Drawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.Drawable);
        this.mVisible = ((TypedArray)object).getBoolean(0, this.mVisible);
        ((TypedArray)object).recycle();
    }

    @UnsupportedAppUsage
    void inflateWithAttributes(Resources resources, XmlPullParser xmlPullParser, TypedArray typedArray, int n) throws XmlPullParserException, IOException {
        this.mVisible = typedArray.getBoolean(n, this.mVisible);
    }

    public void invalidateSelf() {
        Callback callback = this.getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public boolean isAutoMirrored() {
        return false;
    }

    public boolean isFilterBitmap() {
        return false;
    }

    public boolean isProjected() {
        return false;
    }

    public boolean isStateful() {
        return false;
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public void jumpToCurrentState() {
    }

    public Drawable mutate() {
        return this;
    }

    protected void onBoundsChange(Rect rect) {
    }

    public boolean onLayoutDirectionChanged(int n) {
        return false;
    }

    protected boolean onLevelChange(int n) {
        return false;
    }

    protected boolean onStateChange(int[] arrn) {
        return false;
    }

    public void scheduleSelf(Runnable runnable, long l) {
        Callback callback = this.getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, l);
        }
    }

    public abstract void setAlpha(int var1);

    public void setAutoMirrored(boolean bl) {
    }

    public void setBounds(int n, int n2, int n3, int n4) {
        Rect rect;
        Rect rect2 = rect = this.mBounds;
        if (rect == ZERO_BOUNDS_RECT) {
            this.mBounds = rect2 = new Rect();
        }
        if (rect2.left != n || rect2.top != n2 || rect2.right != n3 || rect2.bottom != n4) {
            if (!rect2.isEmpty()) {
                this.invalidateSelf();
            }
            this.mBounds.set(n, n2, n3, n4);
            this.onBoundsChange(this.mBounds);
        }
    }

    public void setBounds(Rect rect) {
        this.setBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public final void setCallback(Callback weakReference) {
        weakReference = weakReference != null ? new WeakReference<Callback>((Callback)((Object)weakReference)) : null;
        this.mCallback = weakReference;
    }

    public void setChangingConfigurations(int n) {
        this.mChangingConfigurations = n;
    }

    @Deprecated
    public void setColorFilter(int n, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        if (this.getColorFilter() instanceof PorterDuffColorFilter && (porterDuffColorFilter = (PorterDuffColorFilter)this.getColorFilter()).getColor() == n && porterDuffColorFilter.getMode() == mode) {
            return;
        }
        this.setColorFilter(new PorterDuffColorFilter(n, mode));
    }

    public abstract void setColorFilter(ColorFilter var1);

    @Deprecated
    public void setDither(boolean bl) {
    }

    public void setFilterBitmap(boolean bl) {
    }

    public void setHotspot(float f, float f2) {
    }

    public void setHotspotBounds(int n, int n2, int n3, int n4) {
    }

    public final boolean setLayoutDirection(int n) {
        if (this.mLayoutDirection != n) {
            this.mLayoutDirection = n;
            return this.onLayoutDirectionChanged(n);
        }
        return false;
    }

    public final boolean setLevel(int n) {
        if (this.mLevel != n) {
            this.mLevel = n;
            return this.onLevelChange(n);
        }
        return false;
    }

    final void setSrcDensityOverride(int n) {
        this.mSrcDensityOverride = n;
    }

    public boolean setState(int[] arrn) {
        if (!Arrays.equals(this.mStateSet, arrn)) {
            this.mStateSet = arrn;
            return this.onStateChange(arrn);
        }
        return false;
    }

    public void setTint(int n) {
        this.setTintList(ColorStateList.valueOf(n));
    }

    public void setTintBlendMode(BlendMode enum_) {
        if (!this.mSetBlendModeInvoked) {
            this.mSetBlendModeInvoked = true;
            if ((enum_ = BlendMode.blendModeToPorterDuffMode((BlendMode)enum_)) == null) {
                enum_ = DEFAULT_TINT_MODE;
            }
            this.setTintMode((PorterDuff.Mode)enum_);
            this.mSetBlendModeInvoked = false;
        }
    }

    public void setTintList(ColorStateList colorStateList) {
    }

    public void setTintMode(PorterDuff.Mode enum_) {
        if (!this.mSetTintModeInvoked) {
            this.mSetTintModeInvoked = true;
            if ((enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null) == null) {
                enum_ = DEFAULT_BLEND_MODE;
            }
            this.setTintBlendMode((BlendMode)enum_);
            this.mSetTintModeInvoked = false;
        }
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        bl2 = this.mVisible != bl;
        if (bl2) {
            this.mVisible = bl;
            this.invalidateSelf();
        }
        return bl2;
    }

    public void setXfermode(Xfermode xfermode) {
    }

    public void unscheduleSelf(Runnable runnable) {
        Callback callback = this.getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    BlendModeColorFilter updateBlendModeFilter(BlendModeColorFilter blendModeColorFilter, ColorStateList colorStateList, BlendMode blendMode) {
        if (colorStateList != null && blendMode != null) {
            int n = colorStateList.getColorForState(this.getState(), 0);
            if (blendModeColorFilter != null && blendModeColorFilter.getColor() == n && blendModeColorFilter.getMode() == blendMode) {
                return blendModeColorFilter;
            }
            return new BlendModeColorFilter(n, blendMode);
        }
        return null;
    }

    @UnsupportedAppUsage
    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter porterDuffColorFilter, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList != null && mode != null) {
            int n = colorStateList.getColorForState(this.getState(), 0);
            if (porterDuffColorFilter != null && porterDuffColorFilter.getColor() == n && porterDuffColorFilter.getMode() == mode) {
                return porterDuffColorFilter;
            }
            return new PorterDuffColorFilter(n, mode);
        }
        return null;
    }

    public static interface Callback {
        public void invalidateDrawable(Drawable var1);

        public void scheduleDrawable(Drawable var1, Runnable var2, long var3);

        public void unscheduleDrawable(Drawable var1, Runnable var2);
    }

    public static abstract class ConstantState {
        public boolean canApplyTheme() {
            return false;
        }

        public abstract int getChangingConfigurations();

        public abstract Drawable newDrawable();

        public Drawable newDrawable(Resources resources) {
            return this.newDrawable();
        }

        public Drawable newDrawable(Resources resources, Resources.Theme theme) {
            return this.newDrawable(resources);
        }
    }

}

