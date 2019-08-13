/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.VMRuntime
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.ComplexColor;
import android.content.res.GradientColor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.Log;
import android.util.PathParser;
import android.util.Property;
import android.util.Xml;
import com.android.internal.R;
import com.android.internal.util.VirtualRefBasePtr;
import dalvik.annotation.optimization.FastNative;
import dalvik.system.VMRuntime;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawable
extends Drawable {
    private static final String LOGTAG = VectorDrawable.class.getSimpleName();
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private BlendModeColorFilter mBlendModeColorFilter;
    private ColorFilter mColorFilter;
    private boolean mDpiScaledDirty = true;
    private int mDpiScaledHeight = 0;
    private Insets mDpiScaledInsets = Insets.NONE;
    private int mDpiScaledWidth = 0;
    private boolean mMutated;
    private int mTargetDensity;
    @UnsupportedAppUsage
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds = new Rect();
    private VectorDrawableState mVectorState;

    public VectorDrawable() {
        this(new VectorDrawableState(null), null);
    }

    private VectorDrawable(VectorDrawableState vectorDrawableState, Resources resources) {
        this.mVectorState = vectorDrawableState;
        this.updateLocalState(resources);
    }

    public static VectorDrawable create(Resources object, int n) {
        block6 : {
            XmlResourceParser xmlResourceParser = object.getXml(n);
            AttributeSet attributeSet = Xml.asAttributeSet(xmlResourceParser);
            while ((n = xmlResourceParser.next()) != 2 && n != 1) {
            }
            if (n != 2) break block6;
            VectorDrawable vectorDrawable = new VectorDrawable();
            vectorDrawable.inflate((Resources)object, xmlResourceParser, attributeSet);
            return vectorDrawable;
        }
        try {
            object = new XmlPullParserException("No start tag found");
            throw object;
        }
        catch (IOException iOException) {
            Log.e(LOGTAG, "parser error", iOException);
        }
        catch (XmlPullParserException xmlPullParserException) {
            Log.e(LOGTAG, "parser error", xmlPullParserException);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void inflateChildElements(Resources var1_1, XmlPullParser var2_2, AttributeSet var3_3, Resources.Theme var4_4) throws XmlPullParserException, IOException {
        var5_5 = this.mVectorState;
        var6_6 = true;
        var7_7 = new Stack<Object>();
        var7_7.push(var5_5.mRootGroup);
        var8_8 = var2_2.getEventType();
        var9_9 = var2_2.getDepth();
        while (var8_8 != 1 && (var2_2.getDepth() >= var9_9 + 1 || var8_8 != 3)) {
            block11 : {
                block9 : {
                    block12 : {
                        block10 : {
                            if (var8_8 != 2) break block9;
                            var10_10 = var2_2.getName();
                            var11_11 = (VGroup)var7_7.peek();
                            if (!"path".equals(var10_10)) break block10;
                            var10_10 = new VFullPath();
                            var10_10.inflate((Resources)var1_1, var3_3, var4_4);
                            var11_11.addChild((VObject)var10_10);
                            if (var10_10.getPathName() != null) {
                                var5_5.mVGTargetsMap.put(var10_10.getPathName(), var10_10);
                            }
                            var12_12 = false;
                            var5_5.mChangingConfigurations |= var10_10.mChangingConfigurations;
                            break block11;
                        }
                        if (!"clip-path".equals(var10_10)) break block12;
                        var10_10 = new VClipPath();
                        var10_10.inflate((Resources)var1_1, var3_3, var4_4);
                        var11_11.addChild((VObject)var10_10);
                        if (var10_10.getPathName() != null) {
                            var5_5.mVGTargetsMap.put(var10_10.getPathName(), var10_10);
                        }
                        var5_5.mChangingConfigurations |= var10_10.mChangingConfigurations;
                        ** GOTO lbl-1000
                    }
                    if ("group".equals(var10_10)) {
                        var10_10 = new VGroup();
                        var10_10.inflate((Resources)var1_1, var3_3, var4_4);
                        var11_11.addChild((VObject)var10_10);
                        var7_7.push(var10_10);
                        if (var10_10.getGroupName() != null) {
                            var5_5.mVGTargetsMap.put(var10_10.getGroupName(), var10_10);
                        }
                        var5_5.mChangingConfigurations |= VGroup.access$100((VGroup)var10_10);
                        var12_12 = var6_6;
                    } else lbl-1000: // 2 sources:
                    {
                        var12_12 = var6_6;
                    }
                    break block11;
                }
                var12_12 = var6_6;
                if (var8_8 == 3) {
                    var12_12 = var6_6;
                    if ("group".equals(var2_2.getName())) {
                        var7_7.pop();
                        var12_12 = var6_6;
                    }
                }
            }
            var8_8 = var2_2.next();
            var6_6 = var12_12;
        }
        if (var6_6 == false) return;
        var1_1 = new StringBuffer();
        if (var1_1.length() > 0) {
            var1_1.append(" or ");
        }
        var1_1.append("path");
        var2_2 = new StringBuilder();
        var2_2.append("no ");
        var2_2.append(var1_1);
        var2_2.append(" defined");
        throw new XmlPullParserException(var2_2.toString());
    }

    @FastNative
    private static native void nAddChild(long var0, long var2);

    @FastNative
    private static native long nCreateClipPath();

    @FastNative
    private static native long nCreateClipPath(long var0);

    @FastNative
    private static native long nCreateFullPath();

    @FastNative
    private static native long nCreateFullPath(long var0);

    @FastNative
    private static native long nCreateGroup();

    @FastNative
    private static native long nCreateGroup(long var0);

    @FastNative
    private static native long nCreateTree(long var0);

    @FastNative
    private static native long nCreateTreeFromCopy(long var0, long var2);

    private static native int nDraw(long var0, long var2, long var4, Rect var6, boolean var7, boolean var8);

    @FastNative
    private static native float nGetFillAlpha(long var0);

    @FastNative
    private static native int nGetFillColor(long var0);

    private static native boolean nGetFullPathProperties(long var0, byte[] var2, int var3);

    private static native boolean nGetGroupProperties(long var0, float[] var2, int var3);

    @FastNative
    private static native float nGetPivotX(long var0);

    @FastNative
    private static native float nGetPivotY(long var0);

    @FastNative
    private static native float nGetRootAlpha(long var0);

    @FastNative
    private static native float nGetRotation(long var0);

    @FastNative
    private static native float nGetScaleX(long var0);

    @FastNative
    private static native float nGetScaleY(long var0);

    @FastNative
    private static native float nGetStrokeAlpha(long var0);

    @FastNative
    private static native int nGetStrokeColor(long var0);

    @FastNative
    private static native float nGetStrokeWidth(long var0);

    @FastNative
    private static native float nGetTranslateX(long var0);

    @FastNative
    private static native float nGetTranslateY(long var0);

    @FastNative
    private static native float nGetTrimPathEnd(long var0);

    @FastNative
    private static native float nGetTrimPathOffset(long var0);

    @FastNative
    private static native float nGetTrimPathStart(long var0);

    @FastNative
    private static native void nSetAllowCaching(long var0, boolean var2);

    @FastNative
    private static native void nSetAntiAlias(long var0, boolean var2);

    @FastNative
    private static native void nSetFillAlpha(long var0, float var2);

    @FastNative
    private static native void nSetFillColor(long var0, int var2);

    private static native void nSetName(long var0, String var2);

    @FastNative
    private static native void nSetPathData(long var0, long var2);

    private static native void nSetPathString(long var0, String var2, int var3);

    @FastNative
    private static native void nSetPivotX(long var0, float var2);

    @FastNative
    private static native void nSetPivotY(long var0, float var2);

    @FastNative
    private static native void nSetRendererViewportSize(long var0, float var2, float var3);

    @FastNative
    private static native boolean nSetRootAlpha(long var0, float var2);

    @FastNative
    private static native void nSetRotation(long var0, float var2);

    @FastNative
    private static native void nSetScaleX(long var0, float var2);

    @FastNative
    private static native void nSetScaleY(long var0, float var2);

    @FastNative
    private static native void nSetStrokeAlpha(long var0, float var2);

    @FastNative
    private static native void nSetStrokeColor(long var0, int var2);

    @FastNative
    private static native void nSetStrokeWidth(long var0, float var2);

    @FastNative
    private static native void nSetTranslateX(long var0, float var2);

    @FastNative
    private static native void nSetTranslateY(long var0, float var2);

    @FastNative
    private static native void nSetTrimPathEnd(long var0, float var2);

    @FastNative
    private static native void nSetTrimPathOffset(long var0, float var2);

    @FastNative
    private static native void nSetTrimPathStart(long var0, float var2);

    @FastNative
    private static native void nUpdateFullPathFillGradient(long var0, long var2);

    @FastNative
    private static native void nUpdateFullPathProperties(long var0, float var2, int var3, float var4, int var5, float var6, float var7, float var8, float var9, float var10, int var11, int var12, int var13);

    @FastNative
    private static native void nUpdateFullPathStrokeGradient(long var0, long var2);

    @FastNative
    private static native void nUpdateGroupProperties(long var0, float var2, float var3, float var4, float var5, float var6, float var7, float var8);

    private boolean needMirroring() {
        boolean bl = this.isAutoMirrored();
        boolean bl2 = true;
        if (!bl || this.getLayoutDirection() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    private void updateColorFilters(BlendMode blendMode, ColorStateList colorStateList) {
        PorterDuff.Mode mode = BlendMode.blendModeToPorterDuffMode(blendMode);
        this.mTintFilter = this.updateTintFilter(this.mTintFilter, colorStateList, mode);
        this.mBlendModeColorFilter = this.updateBlendModeFilter(this.mBlendModeColorFilter, colorStateList, blendMode);
    }

    private void updateLocalState(Resources resources) {
        int n = Drawable.resolveDensity(resources, this.mVectorState.mDensity);
        if (this.mTargetDensity != n) {
            this.mTargetDensity = n;
            this.mDpiScaledDirty = true;
        }
        this.updateColorFilters(this.mVectorState.mBlendMode, this.mVectorState.mTint);
    }

    private void updateStateFromTypedArray(TypedArray object) throws XmlPullParserException {
        ColorStateList colorStateList;
        Object object2 = this.mVectorState;
        ((VectorDrawableState)object2).mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
        ((VectorDrawableState)object2).mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
        int n = ((TypedArray)object).getInt(6, -1);
        if (n != -1) {
            ((VectorDrawableState)object2).mBlendMode = Drawable.parseBlendMode(n, BlendMode.SRC_IN);
        }
        if ((colorStateList = ((TypedArray)object).getColorStateList(1)) != null) {
            ((VectorDrawableState)object2).mTint = colorStateList;
        }
        ((VectorDrawableState)object2).mAutoMirrored = ((TypedArray)object).getBoolean(5, ((VectorDrawableState)object2).mAutoMirrored);
        ((VectorDrawableState)object2).setViewportSize(((TypedArray)object).getFloat(7, ((VectorDrawableState)object2).mViewportWidth), ((TypedArray)object).getFloat(8, ((VectorDrawableState)object2).mViewportHeight));
        if (!(((VectorDrawableState)object2).mViewportWidth <= 0.0f)) {
            if (!(((VectorDrawableState)object2).mViewportHeight <= 0.0f)) {
                ((VectorDrawableState)object2).mBaseWidth = ((TypedArray)object).getDimensionPixelSize(3, ((VectorDrawableState)object2).mBaseWidth);
                ((VectorDrawableState)object2).mBaseHeight = ((TypedArray)object).getDimensionPixelSize(2, ((VectorDrawableState)object2).mBaseHeight);
                if (((VectorDrawableState)object2).mBaseWidth > 0) {
                    if (((VectorDrawableState)object2).mBaseHeight > 0) {
                        ((VectorDrawableState)object2).mOpticalInsets = Insets.of(((TypedArray)object).getDimensionPixelOffset(9, object2.mOpticalInsets.left), ((TypedArray)object).getDimensionPixelOffset(10, object2.mOpticalInsets.top), ((TypedArray)object).getDimensionPixelOffset(11, object2.mOpticalInsets.right), ((TypedArray)object).getDimensionPixelOffset(12, object2.mOpticalInsets.bottom));
                        ((VectorDrawableState)object2).setAlpha(((TypedArray)object).getFloat(4, ((VectorDrawableState)object2).getAlpha()));
                        object = ((TypedArray)object).getString(0);
                        if (object != null) {
                            ((VectorDrawableState)object2).mRootName = object;
                            ((VectorDrawableState)object2).mVGTargetsMap.put((String)object, object2);
                        }
                        return;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(((TypedArray)object).getPositionDescription());
                    ((StringBuilder)object2).append("<vector> tag requires height > 0");
                    throw new XmlPullParserException(((StringBuilder)object2).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(((TypedArray)object).getPositionDescription());
                ((StringBuilder)object2).append("<vector> tag requires width > 0");
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(((TypedArray)object).getPositionDescription());
            ((StringBuilder)object2).append("<vector> tag requires viewportHeight > 0");
            throw new XmlPullParserException(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(((TypedArray)object).getPositionDescription());
        ((StringBuilder)object2).append("<vector> tag requires viewportWidth > 0");
        throw new XmlPullParserException(((StringBuilder)object2).toString());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void applyTheme(Resources.Theme object) {
        Throwable throwable2222;
        Object object2;
        super.applyTheme((Resources.Theme)object);
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState == null) {
            return;
        }
        boolean bl = this.mVectorState.setDensity(Drawable.resolveDensity(((Resources.Theme)object).getResources(), 0));
        this.mDpiScaledDirty |= bl;
        if (vectorDrawableState.mThemeAttrs != null) {
            object2 = ((Resources.Theme)object).resolveAttributes(vectorDrawableState.mThemeAttrs, R.styleable.VectorDrawable);
            vectorDrawableState.mCacheDirty = true;
            this.updateStateFromTypedArray((TypedArray)object2);
            ((TypedArray)object2).recycle();
            this.mDpiScaledDirty = true;
        }
        if (vectorDrawableState.mTint != null && vectorDrawableState.mTint.canApplyTheme()) {
            vectorDrawableState.mTint = vectorDrawableState.mTint.obtainForTheme((Resources.Theme)object);
        }
        if ((object2 = this.mVectorState) != null && ((VectorDrawableState)object2).canApplyTheme()) {
            this.mVectorState.applyTheme((Resources.Theme)object);
        }
        this.updateLocalState(((Resources.Theme)object).getResources());
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (XmlPullParserException xmlPullParserException) {}
            {
                object = new RuntimeException(xmlPullParserException);
                throw object;
            }
        }
        ((TypedArray)object2).recycle();
        throw throwable2222;
    }

    @Override
    public boolean canApplyTheme() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        boolean bl = vectorDrawableState != null && vectorDrawableState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    void computeVectorSize() {
        Insets insets = this.mVectorState.mOpticalInsets;
        int n = this.mTargetDensity;
        int n2 = this.mVectorState.mDensity;
        if (n != n2) {
            this.mDpiScaledWidth = Drawable.scaleFromDensity(this.mVectorState.mBaseWidth, n2, n, true);
            this.mDpiScaledHeight = Drawable.scaleFromDensity(this.mVectorState.mBaseHeight, n2, n, true);
            int n3 = Drawable.scaleFromDensity(insets.left, n2, n, false);
            int n4 = Drawable.scaleFromDensity(insets.right, n2, n, false);
            this.mDpiScaledInsets = Insets.of(n3, Drawable.scaleFromDensity(insets.top, n2, n, false), n4, Drawable.scaleFromDensity(insets.bottom, n2, n, false));
        } else {
            this.mDpiScaledWidth = this.mVectorState.mBaseWidth;
            this.mDpiScaledHeight = this.mVectorState.mBaseHeight;
            this.mDpiScaledInsets = insets;
        }
        this.mDpiScaledDirty = false;
    }

    @Override
    public void draw(Canvas canvas) {
        this.copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() > 0 && this.mTmpBounds.height() > 0) {
            int n;
            ColorFilter colorFilter = this.mColorFilter;
            if (colorFilter == null) {
                colorFilter = this.mBlendModeColorFilter;
            }
            long l = colorFilter == null ? 0L : colorFilter.getNativeInstance();
            boolean bl = this.mVectorState.canReuseCache();
            int n2 = VectorDrawable.nDraw(this.mVectorState.getNativeRenderer(), canvas.getNativeCanvasWrapper(), l, this.mTmpBounds, this.needMirroring(), bl);
            if (n2 == 0) {
                return;
            }
            if (canvas.isHardwareAccelerated()) {
                n = (n2 - this.mVectorState.mLastHWCachePixelCount) * 4;
                this.mVectorState.mLastHWCachePixelCount = n2;
            } else {
                n = (n2 - this.mVectorState.mLastSWCachePixelCount) * 4;
                this.mVectorState.mLastSWCachePixelCount = n2;
            }
            if (n > 0) {
                VMRuntime.getRuntime().registerNativeAllocation(n);
            } else if (n < 0) {
                VMRuntime.getRuntime().registerNativeFree(-n);
            }
            return;
        }
    }

    @Override
    public int getAlpha() {
        return (int)(this.mVectorState.getAlpha() * 255.0f);
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        this.mVectorState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mVectorState;
    }

    @Override
    public int getIntrinsicHeight() {
        if (this.mDpiScaledDirty) {
            this.computeVectorSize();
        }
        return this.mDpiScaledHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        if (this.mDpiScaledDirty) {
            this.computeVectorSize();
        }
        return this.mDpiScaledWidth;
    }

    public long getNativeTree() {
        return this.mVectorState.getNativeRenderer();
    }

    @Override
    public int getOpacity() {
        int n = this.getAlpha() == 0 ? -2 : -3;
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        if (this.mDpiScaledDirty) {
            this.computeVectorSize();
        }
        return this.mDpiScaledInsets;
    }

    public float getPixelSize() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState != null && vectorDrawableState.mBaseWidth != 0 && this.mVectorState.mBaseHeight != 0 && this.mVectorState.mViewportHeight != 0.0f && this.mVectorState.mViewportWidth != 0.0f) {
            float f = this.mVectorState.mBaseWidth;
            float f2 = this.mVectorState.mBaseHeight;
            float f3 = this.mVectorState.mViewportWidth;
            float f4 = this.mVectorState.mViewportHeight;
            return Math.min(f3 / f, f4 / f2);
        }
        return 1.0f;
    }

    @UnsupportedAppUsage
    Object getTargetByName(String string2) {
        return this.mVectorState.mVGTargetsMap.get(string2);
    }

    @Override
    public boolean hasFocusStateSpecified() {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        boolean bl = vectorDrawableState != null && vectorDrawableState.hasFocusStateSpecified();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        try {
            Object object;
            Object object2;
            Trace.traceBegin(8192L, "VectorDrawable#inflate");
            if (this.mVectorState.mRootGroup != null || this.mVectorState.mNativeTree != null) {
                if (this.mVectorState.mRootGroup != null) {
                    VMRuntime.getRuntime().registerNativeFree(this.mVectorState.mRootGroup.getNativeSize());
                    this.mVectorState.mRootGroup.setTree(null);
                }
                object2 = this.mVectorState;
                ((VectorDrawableState)object2).mRootGroup = object = new VGroup();
                if (this.mVectorState.mNativeTree != null) {
                    VMRuntime.getRuntime().registerNativeFree(316);
                    this.mVectorState.mNativeTree.release();
                }
                this.mVectorState.createNativeTree(this.mVectorState.mRootGroup);
            }
            object = this.mVectorState;
            ((VectorDrawableState)object).setDensity(Drawable.resolveDensity(resources, 0));
            object2 = VectorDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.VectorDrawable);
            this.updateStateFromTypedArray((TypedArray)object2);
            ((TypedArray)object2).recycle();
            this.mDpiScaledDirty = true;
            ((VectorDrawableState)object).mCacheDirty = true;
            this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
            ((VectorDrawableState)object).onTreeConstructionFinished();
            this.updateLocalState(resources);
            return;
        }
        finally {
            Trace.traceEnd(8192L);
        }
    }

    @Override
    public boolean isAutoMirrored() {
        return this.mVectorState.mAutoMirrored;
    }

    @Override
    public boolean isStateful() {
        VectorDrawableState vectorDrawableState;
        boolean bl = super.isStateful() || (vectorDrawableState = this.mVectorState) != null && vectorDrawableState.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mVectorState = new VectorDrawableState(this.mVectorState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        VectorDrawableState vectorDrawableState;
        boolean bl = false;
        if (this.isStateful()) {
            this.mutate();
        }
        if ((vectorDrawableState = this.mVectorState).onStateChange(arrn)) {
            bl = true;
            vectorDrawableState.mCacheDirty = true;
        }
        boolean bl2 = bl;
        if (vectorDrawableState.mTint != null) {
            bl2 = bl;
            if (vectorDrawableState.mBlendMode != null) {
                this.updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
                bl2 = true;
            }
        }
        return bl2;
    }

    @UnsupportedAppUsage
    void setAllowCaching(boolean bl) {
        VectorDrawable.nSetAllowCaching(this.mVectorState.getNativeRenderer(), bl);
    }

    @Override
    public void setAlpha(int n) {
        if (this.mVectorState.setAlpha((float)n / 255.0f)) {
            this.invalidateSelf();
        }
    }

    public void setAntiAlias(boolean bl) {
        VectorDrawable.nSetAntiAlias(this.mVectorState.mNativeTree.get(), bl);
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        if (this.mVectorState.mAutoMirrored != bl) {
            this.mVectorState.mAutoMirrored = bl;
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.invalidateSelf();
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mBlendMode != blendMode) {
            vectorDrawableState.mBlendMode = blendMode;
            this.updateColorFilters(vectorDrawableState.mBlendMode, vectorDrawableState.mTint);
            this.invalidateSelf();
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        VectorDrawableState vectorDrawableState = this.mVectorState;
        if (vectorDrawableState.mTint != colorStateList) {
            vectorDrawableState.mTint = colorStateList;
            this.updateColorFilters(this.mVectorState.mBlendMode, colorStateList);
            this.invalidateSelf();
        }
    }

    private static class VClipPath
    extends VPath {
        private static final int NATIVE_ALLOCATION_SIZE = 120;
        private final long mNativePtr;

        public VClipPath() {
            this.mNativePtr = VectorDrawable.nCreateClipPath();
        }

        public VClipPath(VClipPath vClipPath) {
            super(vClipPath);
            this.mNativePtr = VectorDrawable.nCreateClipPath(vClipPath.mNativePtr);
        }

        private void updateStateFromTypedArray(TypedArray object) {
            this.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
            String string2 = ((TypedArray)object).getString(0);
            if (string2 != null) {
                this.mPathName = string2;
                VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
            }
            if ((object = ((TypedArray)object).getString(1)) != null) {
                this.mPathData = new PathParser.PathData((String)object);
                VectorDrawable.nSetPathString(this.mNativePtr, (String)object, ((String)object).length());
            }
        }

        @Override
        public void applyTheme(Resources.Theme theme) {
        }

        @Override
        public boolean canApplyTheme() {
            return false;
        }

        @Override
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override
        int getNativeSize() {
            return 120;
        }

        @Override
        public boolean hasFocusStateSpecified() {
            return false;
        }

        @Override
        public void inflate(Resources object, AttributeSet attributeSet, Resources.Theme theme) {
            object = Drawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.VectorDrawableClipPath);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
        }

        @Override
        public boolean isStateful() {
            return false;
        }

        @Override
        public boolean onStateChange(int[] arrn) {
            return false;
        }
    }

    static class VFullPath
    extends VPath {
        private static final Property<VFullPath, Float> FILL_ALPHA;
        private static final int FILL_ALPHA_INDEX = 4;
        private static final Property<VFullPath, Integer> FILL_COLOR;
        private static final int FILL_COLOR_INDEX = 3;
        private static final int FILL_TYPE_INDEX = 11;
        private static final int NATIVE_ALLOCATION_SIZE = 264;
        private static final Property<VFullPath, Float> STROKE_ALPHA;
        private static final int STROKE_ALPHA_INDEX = 2;
        private static final Property<VFullPath, Integer> STROKE_COLOR;
        private static final int STROKE_COLOR_INDEX = 1;
        private static final int STROKE_LINE_CAP_INDEX = 8;
        private static final int STROKE_LINE_JOIN_INDEX = 9;
        private static final int STROKE_MITER_LIMIT_INDEX = 10;
        private static final Property<VFullPath, Float> STROKE_WIDTH;
        private static final int STROKE_WIDTH_INDEX = 0;
        private static final int TOTAL_PROPERTY_COUNT = 12;
        private static final Property<VFullPath, Float> TRIM_PATH_END;
        private static final int TRIM_PATH_END_INDEX = 6;
        private static final Property<VFullPath, Float> TRIM_PATH_OFFSET;
        private static final int TRIM_PATH_OFFSET_INDEX = 7;
        private static final Property<VFullPath, Float> TRIM_PATH_START;
        private static final int TRIM_PATH_START_INDEX = 5;
        private static final HashMap<String, Integer> sPropertyIndexMap;
        private static final HashMap<String, Property> sPropertyMap;
        ComplexColor mFillColors = null;
        private final long mNativePtr;
        private byte[] mPropertyData;
        ComplexColor mStrokeColors = null;
        private int[] mThemeAttrs;

        static {
            sPropertyIndexMap = new HashMap<String, Integer>(){
                {
                    this.put("strokeWidth", 0);
                    this.put("strokeColor", 1);
                    this.put("strokeAlpha", 2);
                    this.put("fillColor", 3);
                    this.put("fillAlpha", 4);
                    this.put("trimPathStart", 5);
                    this.put("trimPathEnd", 6);
                    this.put("trimPathOffset", 7);
                }
            };
            STROKE_WIDTH = new FloatProperty<VFullPath>("strokeWidth"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getStrokeWidth());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setStrokeWidth(f);
                }
            };
            STROKE_COLOR = new IntProperty<VFullPath>("strokeColor"){

                @Override
                public Integer get(VFullPath vFullPath) {
                    return vFullPath.getStrokeColor();
                }

                @Override
                public void setValue(VFullPath vFullPath, int n) {
                    vFullPath.setStrokeColor(n);
                }
            };
            STROKE_ALPHA = new FloatProperty<VFullPath>("strokeAlpha"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getStrokeAlpha());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setStrokeAlpha(f);
                }
            };
            FILL_COLOR = new IntProperty<VFullPath>("fillColor"){

                @Override
                public Integer get(VFullPath vFullPath) {
                    return vFullPath.getFillColor();
                }

                @Override
                public void setValue(VFullPath vFullPath, int n) {
                    vFullPath.setFillColor(n);
                }
            };
            FILL_ALPHA = new FloatProperty<VFullPath>("fillAlpha"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getFillAlpha());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setFillAlpha(f);
                }
            };
            TRIM_PATH_START = new FloatProperty<VFullPath>("trimPathStart"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathStart());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathStart(f);
                }
            };
            TRIM_PATH_END = new FloatProperty<VFullPath>("trimPathEnd"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathEnd());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathEnd(f);
                }
            };
            TRIM_PATH_OFFSET = new FloatProperty<VFullPath>("trimPathOffset"){

                @Override
                public Float get(VFullPath vFullPath) {
                    return Float.valueOf(vFullPath.getTrimPathOffset());
                }

                @Override
                public void setValue(VFullPath vFullPath, float f) {
                    vFullPath.setTrimPathOffset(f);
                }
            };
            sPropertyMap = new HashMap<String, Property>(){
                {
                    this.put("strokeWidth", STROKE_WIDTH);
                    this.put("strokeColor", STROKE_COLOR);
                    this.put("strokeAlpha", STROKE_ALPHA);
                    this.put("fillColor", FILL_COLOR);
                    this.put("fillAlpha", FILL_ALPHA);
                    this.put("trimPathStart", TRIM_PATH_START);
                    this.put("trimPathEnd", TRIM_PATH_END);
                    this.put("trimPathOffset", TRIM_PATH_OFFSET);
                }
            };
        }

        public VFullPath() {
            this.mNativePtr = VectorDrawable.nCreateFullPath();
        }

        public VFullPath(VFullPath vFullPath) {
            super(vFullPath);
            this.mNativePtr = VectorDrawable.nCreateFullPath(vFullPath.mNativePtr);
            this.mThemeAttrs = vFullPath.mThemeAttrs;
            this.mStrokeColors = vFullPath.mStrokeColors;
            this.mFillColors = vFullPath.mFillColors;
        }

        private boolean canComplexColorApplyTheme(ComplexColor complexColor) {
            boolean bl = complexColor != null && complexColor.canApplyTheme();
            return bl;
        }

        private void updateStateFromTypedArray(TypedArray typedArray) {
            if (this.mPropertyData == null) {
                this.mPropertyData = new byte[48];
            }
            if (VectorDrawable.nGetFullPathProperties(this.mNativePtr, this.mPropertyData, 48)) {
                Object object = ByteBuffer.wrap(this.mPropertyData);
                ((ByteBuffer)object).order(ByteOrder.nativeOrder());
                float f = ((ByteBuffer)object).getFloat(0);
                int n = ((ByteBuffer)object).getInt(4);
                float f2 = ((ByteBuffer)object).getFloat(8);
                int n2 = ((ByteBuffer)object).getInt(12);
                float f3 = ((ByteBuffer)object).getFloat(16);
                float f4 = ((ByteBuffer)object).getFloat(20);
                float f5 = ((ByteBuffer)object).getFloat(24);
                float f6 = ((ByteBuffer)object).getFloat(28);
                int n3 = ((ByteBuffer)object).getInt(32);
                int n4 = ((ByteBuffer)object).getInt(36);
                float f7 = ((ByteBuffer)object).getFloat(40);
                int n5 = ((ByteBuffer)object).getInt(44);
                Object object2 = null;
                object = null;
                Object var16_16 = null;
                Object var17_17 = null;
                this.mChangingConfigurations |= typedArray.getChangingConfigurations();
                this.mThemeAttrs = typedArray.extractThemeAttrs();
                Object object3 = typedArray.getString(0);
                if (object3 != null) {
                    this.mPathName = object3;
                    VectorDrawable.nSetName(this.mNativePtr, this.mPathName);
                }
                if ((object3 = typedArray.getString(2)) != null) {
                    this.mPathData = new PathParser.PathData((String)object3);
                    VectorDrawable.nSetPathString(this.mNativePtr, (String)object3, ((String)object3).length());
                }
                if ((object3 = typedArray.getComplexColor(1)) != null) {
                    if (object3 instanceof GradientColor) {
                        this.mFillColors = object3;
                        object = ((GradientColor)object3).getShader();
                    } else {
                        this.mFillColors = !((ComplexColor)object3).isStateful() && !((ComplexColor)object3).canApplyTheme() ? null : object3;
                    }
                    n2 = ((ComplexColor)object3).getDefaultColor();
                    object2 = object;
                }
                if ((object3 = typedArray.getComplexColor(3)) != null) {
                    if (object3 instanceof GradientColor) {
                        this.mStrokeColors = object3;
                        object = ((GradientColor)object3).getShader();
                    } else if (!((ComplexColor)object3).isStateful() && !((ComplexColor)object3).canApplyTheme()) {
                        this.mStrokeColors = null;
                        object = var17_17;
                    } else {
                        this.mStrokeColors = object3;
                        object = var17_17;
                    }
                    n = ((ComplexColor)object3).getDefaultColor();
                } else {
                    object = var16_16;
                }
                long l = this.mNativePtr;
                long l2 = 0L;
                long l3 = object2 != null ? ((Shader)object2).getNativeInstance() : 0L;
                VectorDrawable.nUpdateFullPathFillGradient(l, l3);
                l = this.mNativePtr;
                l3 = l2;
                if (object != null) {
                    l3 = ((Shader)object).getNativeInstance();
                }
                VectorDrawable.nUpdateFullPathStrokeGradient(l, l3);
                f3 = typedArray.getFloat(12, f3);
                n3 = typedArray.getInt(8, n3);
                n4 = typedArray.getInt(9, n4);
                f7 = typedArray.getFloat(10, f7);
                f2 = typedArray.getFloat(11, f2);
                f = typedArray.getFloat(4, f);
                f5 = typedArray.getFloat(6, f5);
                f6 = typedArray.getFloat(7, f6);
                f4 = typedArray.getFloat(5, f4);
                n5 = typedArray.getInt(13, n5);
                VectorDrawable.nUpdateFullPathProperties(this.mNativePtr, f, n, f2, n2, f3, f4, f5, f6, f7, n3, n4, n5);
                return;
            }
            throw new RuntimeException("Error: inconsistent property count");
        }

        @Override
        public void applyTheme(Resources.Theme object) {
            Object object2 = this.mThemeAttrs;
            if (object2 != null) {
                object2 = ((Resources.Theme)object).resolveAttributes((int[])object2, R.styleable.VectorDrawablePath);
                this.updateStateFromTypedArray((TypedArray)object2);
                ((TypedArray)object2).recycle();
            }
            boolean bl = this.canComplexColorApplyTheme(this.mFillColors);
            boolean bl2 = this.canComplexColorApplyTheme(this.mStrokeColors);
            if (bl) {
                this.mFillColors = this.mFillColors.obtainForTheme((Resources.Theme)object);
                object2 = this.mFillColors;
                if (object2 instanceof GradientColor) {
                    VectorDrawable.nUpdateFullPathFillGradient(this.mNativePtr, ((GradientColor)object2).getShader().getNativeInstance());
                } else if (object2 instanceof ColorStateList) {
                    VectorDrawable.nSetFillColor(this.mNativePtr, ((ComplexColor)object2).getDefaultColor());
                }
            }
            if (bl2) {
                this.mStrokeColors = this.mStrokeColors.obtainForTheme((Resources.Theme)object);
                object = this.mStrokeColors;
                if (object instanceof GradientColor) {
                    VectorDrawable.nUpdateFullPathStrokeGradient(this.mNativePtr, ((GradientColor)object).getShader().getNativeInstance());
                } else if (object instanceof ColorStateList) {
                    VectorDrawable.nSetStrokeColor(this.mNativePtr, ((ComplexColor)object).getDefaultColor());
                }
            }
        }

        @Override
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            boolean bl = this.canComplexColorApplyTheme(this.mFillColors);
            boolean bl2 = this.canComplexColorApplyTheme(this.mStrokeColors);
            return bl || bl2;
            {
            }
        }

        float getFillAlpha() {
            float f = this.isTreeValid() ? VectorDrawable.nGetFillAlpha(this.mNativePtr) : 0.0f;
            return f;
        }

        int getFillColor() {
            int n = this.isTreeValid() ? VectorDrawable.nGetFillColor(this.mNativePtr) : 0;
            return n;
        }

        @Override
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override
        int getNativeSize() {
            return 264;
        }

        @Override
        Property getProperty(String string2) {
            Property property = super.getProperty(string2);
            if (property != null) {
                return property;
            }
            if (sPropertyMap.containsKey(string2)) {
                return sPropertyMap.get(string2);
            }
            return null;
        }

        int getPropertyIndex(String string2) {
            if (!sPropertyIndexMap.containsKey(string2)) {
                return -1;
            }
            return sPropertyIndexMap.get(string2);
        }

        float getStrokeAlpha() {
            float f = this.isTreeValid() ? VectorDrawable.nGetStrokeAlpha(this.mNativePtr) : 0.0f;
            return f;
        }

        int getStrokeColor() {
            int n = this.isTreeValid() ? VectorDrawable.nGetStrokeColor(this.mNativePtr) : 0;
            return n;
        }

        float getStrokeWidth() {
            float f = this.isTreeValid() ? VectorDrawable.nGetStrokeWidth(this.mNativePtr) : 0.0f;
            return f;
        }

        float getTrimPathEnd() {
            float f = this.isTreeValid() ? VectorDrawable.nGetTrimPathEnd(this.mNativePtr) : 0.0f;
            return f;
        }

        float getTrimPathOffset() {
            float f = this.isTreeValid() ? VectorDrawable.nGetTrimPathOffset(this.mNativePtr) : 0.0f;
            return f;
        }

        float getTrimPathStart() {
            float f = this.isTreeValid() ? VectorDrawable.nGetTrimPathStart(this.mNativePtr) : 0.0f;
            return f;
        }

        @Override
        public boolean hasFocusStateSpecified() {
            ComplexColor complexColor = this.mStrokeColors;
            boolean bl = complexColor != null && complexColor instanceof ColorStateList && ((ColorStateList)complexColor).hasFocusStateSpecified() && (complexColor = this.mFillColors) != null && complexColor instanceof ColorStateList && ((ColorStateList)complexColor).hasFocusStateSpecified();
            return bl;
        }

        @Override
        public void inflate(Resources object, AttributeSet attributeSet, Resources.Theme theme) {
            object = Drawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.VectorDrawablePath);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
        }

        @Override
        public boolean isStateful() {
            boolean bl = this.mStrokeColors != null || this.mFillColors != null;
            return bl;
        }

        @Override
        public boolean onStateChange(int[] arrn) {
            int n;
            int n2;
            boolean bl;
            boolean bl2 = false;
            ComplexColor complexColor = this.mStrokeColors;
            boolean bl3 = true;
            boolean bl4 = bl2;
            if (complexColor != null) {
                bl4 = bl2;
                if (complexColor instanceof ColorStateList) {
                    n2 = this.getStrokeColor();
                    bl = n2 != (n = ((ColorStateList)this.mStrokeColors).getColorForState(arrn, n2));
                    bl4 = bl2 = false | bl;
                    if (n2 != n) {
                        VectorDrawable.nSetStrokeColor(this.mNativePtr, n);
                        bl4 = bl2;
                    }
                }
            }
            complexColor = this.mFillColors;
            bl2 = bl4;
            if (complexColor != null) {
                bl2 = bl4;
                if (complexColor instanceof ColorStateList) {
                    n = this.getFillColor();
                    bl = n != (n2 = ((ColorStateList)this.mFillColors).getColorForState(arrn, n)) ? bl3 : false;
                    bl2 = bl4 |= bl;
                    if (n != n2) {
                        VectorDrawable.nSetFillColor(this.mNativePtr, n2);
                        bl2 = bl4;
                    }
                }
            }
            return bl2;
        }

        void setFillAlpha(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetFillAlpha(this.mNativePtr, f);
            }
        }

        void setFillColor(int n) {
            this.mFillColors = null;
            if (this.isTreeValid()) {
                VectorDrawable.nSetFillColor(this.mNativePtr, n);
            }
        }

        void setStrokeAlpha(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetStrokeAlpha(this.mNativePtr, f);
            }
        }

        void setStrokeColor(int n) {
            this.mStrokeColors = null;
            if (this.isTreeValid()) {
                VectorDrawable.nSetStrokeColor(this.mNativePtr, n);
            }
        }

        void setStrokeWidth(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetStrokeWidth(this.mNativePtr, f);
            }
        }

        void setTrimPathEnd(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetTrimPathEnd(this.mNativePtr, f);
            }
        }

        void setTrimPathOffset(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetTrimPathOffset(this.mNativePtr, f);
            }
        }

        void setTrimPathStart(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetTrimPathStart(this.mNativePtr, f);
            }
        }

    }

    static class VGroup
    extends VObject {
        private static final int NATIVE_ALLOCATION_SIZE = 100;
        private static final Property<VGroup, Float> PIVOT_X;
        private static final int PIVOT_X_INDEX = 1;
        private static final Property<VGroup, Float> PIVOT_Y;
        private static final int PIVOT_Y_INDEX = 2;
        private static final Property<VGroup, Float> ROTATION;
        private static final int ROTATION_INDEX = 0;
        private static final Property<VGroup, Float> SCALE_X;
        private static final int SCALE_X_INDEX = 3;
        private static final Property<VGroup, Float> SCALE_Y;
        private static final int SCALE_Y_INDEX = 4;
        private static final int TRANSFORM_PROPERTY_COUNT = 7;
        private static final Property<VGroup, Float> TRANSLATE_X;
        private static final int TRANSLATE_X_INDEX = 5;
        private static final Property<VGroup, Float> TRANSLATE_Y;
        private static final int TRANSLATE_Y_INDEX = 6;
        private static final HashMap<String, Integer> sPropertyIndexMap;
        private static final HashMap<String, Property> sPropertyMap;
        private int mChangingConfigurations;
        private final ArrayList<VObject> mChildren = new ArrayList();
        private String mGroupName = null;
        private boolean mIsStateful;
        private final long mNativePtr;
        private int[] mThemeAttrs;
        private float[] mTransform;

        static {
            sPropertyIndexMap = new HashMap<String, Integer>(){
                {
                    this.put("translateX", 5);
                    this.put("translateY", 6);
                    this.put("scaleX", 3);
                    this.put("scaleY", 4);
                    this.put("pivotX", 1);
                    this.put("pivotY", 2);
                    this.put("rotation", 0);
                }
            };
            TRANSLATE_X = new FloatProperty<VGroup>("translateX"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getTranslateX());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setTranslateX(f);
                }
            };
            TRANSLATE_Y = new FloatProperty<VGroup>("translateY"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getTranslateY());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setTranslateY(f);
                }
            };
            SCALE_X = new FloatProperty<VGroup>("scaleX"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getScaleX());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setScaleX(f);
                }
            };
            SCALE_Y = new FloatProperty<VGroup>("scaleY"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getScaleY());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setScaleY(f);
                }
            };
            PIVOT_X = new FloatProperty<VGroup>("pivotX"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getPivotX());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setPivotX(f);
                }
            };
            PIVOT_Y = new FloatProperty<VGroup>("pivotY"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getPivotY());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setPivotY(f);
                }
            };
            ROTATION = new FloatProperty<VGroup>("rotation"){

                @Override
                public Float get(VGroup vGroup) {
                    return Float.valueOf(vGroup.getRotation());
                }

                @Override
                public void setValue(VGroup vGroup, float f) {
                    vGroup.setRotation(f);
                }
            };
            sPropertyMap = new HashMap<String, Property>(){
                {
                    this.put("translateX", TRANSLATE_X);
                    this.put("translateY", TRANSLATE_Y);
                    this.put("scaleX", SCALE_X);
                    this.put("scaleY", SCALE_Y);
                    this.put("pivotX", PIVOT_X);
                    this.put("pivotY", PIVOT_Y);
                    this.put("rotation", ROTATION);
                }
            };
        }

        public VGroup() {
            this.mNativePtr = VectorDrawable.nCreateGroup();
        }

        public VGroup(VGroup vObject, ArrayMap<String, Object> arrayMap) {
            this.mIsStateful = vObject.mIsStateful;
            this.mThemeAttrs = vObject.mThemeAttrs;
            this.mGroupName = vObject.mGroupName;
            this.mChangingConfigurations = vObject.mChangingConfigurations;
            Object object = this.mGroupName;
            if (object != null) {
                arrayMap.put((String)object, this);
            }
            this.mNativePtr = VectorDrawable.nCreateGroup(vObject.mNativePtr);
            object = vObject.mChildren;
            for (int i = 0; i < ((ArrayList)object).size(); ++i) {
                block8 : {
                    block7 : {
                        block6 : {
                            vObject = (VObject)((ArrayList)object).get(i);
                            if (vObject instanceof VGroup) {
                                this.addChild(new VGroup((VGroup)vObject, arrayMap));
                                continue;
                            }
                            if (!(vObject instanceof VFullPath)) break block6;
                            vObject = new VFullPath((VFullPath)vObject);
                            break block7;
                        }
                        if (!(vObject instanceof VClipPath)) break block8;
                        vObject = new VClipPath((VClipPath)vObject);
                    }
                    this.addChild(vObject);
                    if (((VPath)vObject).mPathName == null) continue;
                    arrayMap.put(((VPath)vObject).mPathName, vObject);
                    continue;
                }
                throw new IllegalStateException("Unknown object in the tree!");
            }
        }

        static /* synthetic */ int access$100(VGroup vGroup) {
            return vGroup.mChangingConfigurations;
        }

        static int getPropertyIndex(String string2) {
            if (sPropertyIndexMap.containsKey(string2)) {
                return sPropertyIndexMap.get(string2);
            }
            return -1;
        }

        public void addChild(VObject vObject) {
            VectorDrawable.nAddChild(this.mNativePtr, vObject.getNativePtr());
            this.mChildren.add(vObject);
            this.mIsStateful |= vObject.isStateful();
        }

        @Override
        public void applyTheme(Resources.Theme theme) {
            Object object = this.mThemeAttrs;
            if (object != null) {
                object = theme.resolveAttributes((int[])object, R.styleable.VectorDrawableGroup);
                this.updateStateFromTypedArray((TypedArray)object);
                ((TypedArray)object).recycle();
            }
            object = this.mChildren;
            int n = ((ArrayList)object).size();
            for (int i = 0; i < n; ++i) {
                VObject vObject = (VObject)((ArrayList)object).get(i);
                if (!vObject.canApplyTheme()) continue;
                vObject.applyTheme(theme);
                this.mIsStateful |= vObject.isStateful();
            }
        }

        @Override
        public boolean canApplyTheme() {
            if (this.mThemeAttrs != null) {
                return true;
            }
            ArrayList<VObject> arrayList = this.mChildren;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                if (!arrayList.get(i).canApplyTheme()) continue;
                return true;
            }
            return false;
        }

        public String getGroupName() {
            return this.mGroupName;
        }

        @Override
        public long getNativePtr() {
            return this.mNativePtr;
        }

        @Override
        int getNativeSize() {
            int n = 100;
            for (int i = 0; i < this.mChildren.size(); ++i) {
                n += this.mChildren.get(i).getNativeSize();
            }
            return n;
        }

        public float getPivotX() {
            float f = this.isTreeValid() ? VectorDrawable.nGetPivotX(this.mNativePtr) : 0.0f;
            return f;
        }

        public float getPivotY() {
            float f = this.isTreeValid() ? VectorDrawable.nGetPivotY(this.mNativePtr) : 0.0f;
            return f;
        }

        @Override
        Property getProperty(String string2) {
            if (sPropertyMap.containsKey(string2)) {
                return sPropertyMap.get(string2);
            }
            return null;
        }

        public float getRotation() {
            float f = this.isTreeValid() ? VectorDrawable.nGetRotation(this.mNativePtr) : 0.0f;
            return f;
        }

        public float getScaleX() {
            float f = this.isTreeValid() ? VectorDrawable.nGetScaleX(this.mNativePtr) : 0.0f;
            return f;
        }

        public float getScaleY() {
            float f = this.isTreeValid() ? VectorDrawable.nGetScaleY(this.mNativePtr) : 0.0f;
            return f;
        }

        public float getTranslateX() {
            float f = this.isTreeValid() ? VectorDrawable.nGetTranslateX(this.mNativePtr) : 0.0f;
            return f;
        }

        public float getTranslateY() {
            float f = this.isTreeValid() ? VectorDrawable.nGetTranslateY(this.mNativePtr) : 0.0f;
            return f;
        }

        @Override
        public boolean hasFocusStateSpecified() {
            boolean bl = false;
            ArrayList<VObject> arrayList = this.mChildren;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                VObject vObject = arrayList.get(i);
                boolean bl2 = bl;
                if (vObject.isStateful()) {
                    bl2 = bl | vObject.hasFocusStateSpecified();
                }
                bl = bl2;
            }
            return bl;
        }

        @Override
        public void inflate(Resources object, AttributeSet attributeSet, Resources.Theme theme) {
            object = Drawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.VectorDrawableGroup);
            this.updateStateFromTypedArray((TypedArray)object);
            ((TypedArray)object).recycle();
        }

        @Override
        public boolean isStateful() {
            return this.mIsStateful;
        }

        @Override
        public boolean onStateChange(int[] arrn) {
            boolean bl = false;
            ArrayList<VObject> arrayList = this.mChildren;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                VObject vObject = arrayList.get(i);
                boolean bl2 = bl;
                if (vObject.isStateful()) {
                    bl2 = bl | vObject.onStateChange(arrn);
                }
                bl = bl2;
            }
            return bl;
        }

        @UnsupportedAppUsage
        public void setPivotX(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetPivotX(this.mNativePtr, f);
            }
        }

        @UnsupportedAppUsage
        public void setPivotY(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetPivotY(this.mNativePtr, f);
            }
        }

        @UnsupportedAppUsage
        public void setRotation(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetRotation(this.mNativePtr, f);
            }
        }

        public void setScaleX(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetScaleX(this.mNativePtr, f);
            }
        }

        public void setScaleY(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetScaleY(this.mNativePtr, f);
            }
        }

        @UnsupportedAppUsage
        public void setTranslateX(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetTranslateX(this.mNativePtr, f);
            }
        }

        @UnsupportedAppUsage
        public void setTranslateY(float f) {
            if (this.isTreeValid()) {
                VectorDrawable.nSetTranslateY(this.mNativePtr, f);
            }
        }

        @Override
        public void setTree(VirtualRefBasePtr virtualRefBasePtr) {
            super.setTree(virtualRefBasePtr);
            for (int i = 0; i < this.mChildren.size(); ++i) {
                this.mChildren.get(i).setTree(virtualRefBasePtr);
            }
        }

        void updateStateFromTypedArray(TypedArray object) {
            this.mChangingConfigurations |= ((TypedArray)object).getChangingConfigurations();
            this.mThemeAttrs = ((TypedArray)object).extractThemeAttrs();
            if (this.mTransform == null) {
                this.mTransform = new float[7];
            }
            if (VectorDrawable.nGetGroupProperties(this.mNativePtr, this.mTransform, 7)) {
                float f = ((TypedArray)object).getFloat(5, this.mTransform[0]);
                float f2 = ((TypedArray)object).getFloat(1, this.mTransform[1]);
                float f3 = ((TypedArray)object).getFloat(2, this.mTransform[2]);
                float f4 = ((TypedArray)object).getFloat(3, this.mTransform[3]);
                float f5 = ((TypedArray)object).getFloat(4, this.mTransform[4]);
                float f6 = ((TypedArray)object).getFloat(6, this.mTransform[5]);
                float f7 = ((TypedArray)object).getFloat(7, this.mTransform[6]);
                if ((object = ((TypedArray)object).getString(0)) != null) {
                    this.mGroupName = object;
                    VectorDrawable.nSetName(this.mNativePtr, this.mGroupName);
                }
                VectorDrawable.nUpdateGroupProperties(this.mNativePtr, f, f2, f3, f4, f5, f6, f7);
                return;
            }
            throw new RuntimeException("Error: inconsistent property count");
        }

    }

    static abstract class VObject {
        VirtualRefBasePtr mTreePtr = null;

        VObject() {
        }

        abstract void applyTheme(Resources.Theme var1);

        abstract boolean canApplyTheme();

        abstract long getNativePtr();

        abstract int getNativeSize();

        abstract Property getProperty(String var1);

        abstract boolean hasFocusStateSpecified();

        abstract void inflate(Resources var1, AttributeSet var2, Resources.Theme var3);

        abstract boolean isStateful();

        boolean isTreeValid() {
            VirtualRefBasePtr virtualRefBasePtr = this.mTreePtr;
            boolean bl = virtualRefBasePtr != null && virtualRefBasePtr.get() != 0L;
            return bl;
        }

        abstract boolean onStateChange(int[] var1);

        void setTree(VirtualRefBasePtr virtualRefBasePtr) {
            this.mTreePtr = virtualRefBasePtr;
        }
    }

    static abstract class VPath
    extends VObject {
        private static final Property<VPath, PathParser.PathData> PATH_DATA = new Property<VPath, PathParser.PathData>(PathParser.PathData.class, "pathData"){

            @Override
            public PathParser.PathData get(VPath vPath) {
                return vPath.getPathData();
            }

            @Override
            public void set(VPath vPath, PathParser.PathData pathData) {
                vPath.setPathData(pathData);
            }
        };
        int mChangingConfigurations;
        protected PathParser.PathData mPathData;
        String mPathName;

        public VPath() {
            this.mPathData = null;
        }

        public VPath(VPath object) {
            Object var2_2 = null;
            this.mPathData = null;
            this.mPathName = ((VPath)object).mPathName;
            this.mChangingConfigurations = ((VPath)object).mChangingConfigurations;
            object = ((VPath)object).mPathData;
            object = object == null ? var2_2 : new PathParser.PathData((PathParser.PathData)object);
            this.mPathData = object;
        }

        public PathParser.PathData getPathData() {
            return this.mPathData;
        }

        public String getPathName() {
            return this.mPathName;
        }

        @Override
        Property getProperty(String string2) {
            if (PATH_DATA.getName().equals(string2)) {
                return PATH_DATA;
            }
            return null;
        }

        public void setPathData(PathParser.PathData pathData) {
            this.mPathData.setPathData(pathData);
            if (this.isTreeValid()) {
                VectorDrawable.nSetPathData(this.getNativePtr(), this.mPathData.getNativePtr());
            }
        }

    }

    static class VectorDrawableState
    extends Drawable.ConstantState {
        static final Property<VectorDrawableState, Float> ALPHA = new FloatProperty<VectorDrawableState>("alpha"){

            @Override
            public Float get(VectorDrawableState vectorDrawableState) {
                return Float.valueOf(vectorDrawableState.getAlpha());
            }

            @Override
            public void setValue(VectorDrawableState vectorDrawableState, float f) {
                vectorDrawableState.setAlpha(f);
            }
        };
        private static final int NATIVE_ALLOCATION_SIZE = 316;
        private int mAllocationOfAllNodes = 0;
        boolean mAutoMirrored;
        int mBaseHeight = 0;
        int mBaseWidth = 0;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        BlendMode mCachedBlendMode;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        int mChangingConfigurations;
        int mDensity = 160;
        int mLastHWCachePixelCount = 0;
        int mLastSWCachePixelCount = 0;
        VirtualRefBasePtr mNativeTree = null;
        Insets mOpticalInsets = Insets.NONE;
        VGroup mRootGroup;
        String mRootName = null;
        int[] mThemeAttrs;
        ColorStateList mTint = null;
        final ArrayMap<String, Object> mVGTargetsMap = new ArrayMap();
        float mViewportHeight = 0.0f;
        float mViewportWidth = 0.0f;

        public VectorDrawableState(VectorDrawableState object) {
            if (object != null) {
                this.mThemeAttrs = ((VectorDrawableState)object).mThemeAttrs;
                this.mChangingConfigurations = ((VectorDrawableState)object).mChangingConfigurations;
                this.mTint = ((VectorDrawableState)object).mTint;
                this.mBlendMode = ((VectorDrawableState)object).mBlendMode;
                this.mAutoMirrored = ((VectorDrawableState)object).mAutoMirrored;
                this.mRootGroup = new VGroup(((VectorDrawableState)object).mRootGroup, this.mVGTargetsMap);
                this.createNativeTreeFromCopy((VectorDrawableState)object, this.mRootGroup);
                this.mBaseWidth = ((VectorDrawableState)object).mBaseWidth;
                this.mBaseHeight = ((VectorDrawableState)object).mBaseHeight;
                this.setViewportSize(((VectorDrawableState)object).mViewportWidth, ((VectorDrawableState)object).mViewportHeight);
                this.mOpticalInsets = ((VectorDrawableState)object).mOpticalInsets;
                this.mRootName = ((VectorDrawableState)object).mRootName;
                this.mDensity = ((VectorDrawableState)object).mDensity;
                object = ((VectorDrawableState)object).mRootName;
                if (object != null) {
                    this.mVGTargetsMap.put((String)object, this);
                }
            } else {
                this.mRootGroup = new VGroup();
                this.createNativeTree(this.mRootGroup);
            }
            this.onTreeConstructionFinished();
        }

        private void applyDensityScaling(int n, int n2) {
            this.mBaseWidth = Drawable.scaleFromDensity(this.mBaseWidth, n, n2, true);
            this.mBaseHeight = Drawable.scaleFromDensity(this.mBaseHeight, n, n2, true);
            this.mOpticalInsets = Insets.of(Drawable.scaleFromDensity(this.mOpticalInsets.left, n, n2, false), Drawable.scaleFromDensity(this.mOpticalInsets.top, n, n2, false), Drawable.scaleFromDensity(this.mOpticalInsets.right, n, n2, false), Drawable.scaleFromDensity(this.mOpticalInsets.bottom, n, n2, false));
        }

        private void createNativeTree(VGroup vGroup) {
            this.mNativeTree = new VirtualRefBasePtr(VectorDrawable.nCreateTree(vGroup.mNativePtr));
            VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        private void createNativeTreeFromCopy(VectorDrawableState vectorDrawableState, VGroup vGroup) {
            this.mNativeTree = new VirtualRefBasePtr(VectorDrawable.nCreateTreeFromCopy(vectorDrawableState.mNativeTree.get(), vGroup.mNativePtr));
            VMRuntime.getRuntime().registerNativeAllocation(316);
        }

        public void applyTheme(Resources.Theme theme) {
            this.mRootGroup.applyTheme(theme);
        }

        @Override
        public boolean canApplyTheme() {
            Object object;
            boolean bl = this.mThemeAttrs != null || (object = this.mRootGroup) != null && ((VGroup)object).canApplyTheme() || (object = this.mTint) != null && ((ColorStateList)object).canApplyTheme() || super.canApplyTheme();
            return bl;
        }

        public boolean canReuseCache() {
            if (!this.mCacheDirty && this.mCachedThemeAttrs == this.mThemeAttrs && this.mCachedTint == this.mTint && this.mCachedBlendMode == this.mBlendMode && this.mCachedAutoMirrored == this.mAutoMirrored) {
                return true;
            }
            this.updateCacheStates();
            return false;
        }

        public void finalize() throws Throwable {
            Object.super.finalize();
            int n = this.mLastHWCachePixelCount;
            int n2 = this.mLastSWCachePixelCount;
            VMRuntime.getRuntime().registerNativeFree(this.mAllocationOfAllNodes + 316 + (n * 4 + n2 * 4));
        }

        public float getAlpha() {
            return VectorDrawable.nGetRootAlpha(this.mNativeTree.get());
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            int n2 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            return n | n2;
        }

        long getNativeRenderer() {
            VirtualRefBasePtr virtualRefBasePtr = this.mNativeTree;
            if (virtualRefBasePtr == null) {
                return 0L;
            }
            return virtualRefBasePtr.get();
        }

        Property getProperty(String string2) {
            if (ALPHA.getName().equals(string2)) {
                return ALPHA;
            }
            return null;
        }

        public boolean hasFocusStateSpecified() {
            Object object = this.mTint;
            boolean bl = object != null && ((ColorStateList)object).hasFocusStateSpecified() || (object = this.mRootGroup) != null && ((VGroup)object).hasFocusStateSpecified();
            return bl;
        }

        public boolean isStateful() {
            Object object = this.mTint;
            boolean bl = object != null && ((ColorStateList)object).isStateful() || (object = this.mRootGroup) != null && ((VGroup)object).isStateful();
            return bl;
        }

        @Override
        public Drawable newDrawable() {
            return new VectorDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new VectorDrawable(this, resources);
        }

        public boolean onStateChange(int[] arrn) {
            return this.mRootGroup.onStateChange(arrn);
        }

        void onTreeConstructionFinished() {
            this.mRootGroup.setTree(this.mNativeTree);
            this.mAllocationOfAllNodes = this.mRootGroup.getNativeSize();
            VMRuntime.getRuntime().registerNativeAllocation(this.mAllocationOfAllNodes);
        }

        public boolean setAlpha(float f) {
            return VectorDrawable.nSetRootAlpha(this.mNativeTree.get(), f);
        }

        public final boolean setDensity(int n) {
            if (this.mDensity != n) {
                int n2 = this.mDensity;
                this.mDensity = n;
                this.applyDensityScaling(n2, n);
                return true;
            }
            return false;
        }

        void setViewportSize(float f, float f2) {
            this.mViewportWidth = f;
            this.mViewportHeight = f2;
            VectorDrawable.nSetRendererViewportSize(this.getNativeRenderer(), f, f2);
        }

        public void updateCacheStates() {
            this.mCachedThemeAttrs = this.mThemeAttrs;
            this.mCachedTint = this.mTint;
            this.mCachedBlendMode = this.mBlendMode;
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

    }

}

