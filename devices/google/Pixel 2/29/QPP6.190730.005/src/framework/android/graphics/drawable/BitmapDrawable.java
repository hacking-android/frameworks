/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC-rMNbn4Pyg
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw
 *  android.graphics.drawable.-$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.-$;
import android.graphics.drawable.Drawable;
import android.graphics.drawable._$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC_rMNbn4Pyg;
import android.graphics.drawable._$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw;
import android.graphics.drawable._$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import com.android.internal.R;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class BitmapDrawable
extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 6;
    private static final int TILE_MODE_CLAMP = 0;
    private static final int TILE_MODE_DISABLED = -1;
    private static final int TILE_MODE_MIRROR = 2;
    private static final int TILE_MODE_REPEAT = 1;
    private static final int TILE_MODE_UNDEFINED = -2;
    private int mBitmapHeight;
    @UnsupportedAppUsage
    private BitmapState mBitmapState;
    private int mBitmapWidth;
    private BlendModeColorFilter mBlendModeFilter;
    private final Rect mDstRect = new Rect();
    private boolean mDstRectAndInsetsDirty = true;
    private Matrix mMirrorMatrix;
    private boolean mMutated;
    private Insets mOpticalInsets = Insets.NONE;
    @UnsupportedAppUsage
    private int mTargetDensity = 160;

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
    public BitmapDrawable() {
        this.init(new BitmapState((Bitmap)null), null);
    }

    @Deprecated
    public BitmapDrawable(Resources resources) {
        this.init(new BitmapState((Bitmap)null), resources);
    }

    public BitmapDrawable(Resources resources, Bitmap bitmap) {
        this.init(new BitmapState(bitmap), resources);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BitmapDrawable(Resources object, InputStream inputStream) {
        try {
            Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource((Resources)object, inputStream), (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$BitmapDrawable$T1BUUqQwU4Z6Ve8DJHFuQvYohkY.INSTANCE);
            this.init(new BitmapState(bitmap), (Resources)object);
            if (this.mBitmapState.mBitmap != null) return;
            object = new StringBuilder();
        }
        catch (Throwable throwable) {
            this.init(new BitmapState(null), (Resources)object);
            if (this.mBitmapState.mBitmap != null) throw throwable;
            object = new StringBuilder();
            ((StringBuilder)object).append("BitmapDrawable cannot decode ");
            ((StringBuilder)object).append(inputStream);
            Log.w("BitmapDrawable", ((StringBuilder)object).toString());
            throw throwable;
        }
        catch (Exception exception) {
            this.init(new BitmapState(null), (Resources)object);
            if (this.mBitmapState.mBitmap != null) return;
            object = new StringBuilder();
        }
        ((StringBuilder)object).append("BitmapDrawable cannot decode ");
        ((StringBuilder)object).append(inputStream);
        Log.w("BitmapDrawable", ((StringBuilder)object).toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public BitmapDrawable(Resources object, String string2) {
        Bitmap bitmap;
        Bitmap bitmap2 = null;
        Bitmap bitmap3 = bitmap = null;
        Bitmap bitmap4 = bitmap2;
        bitmap3 = bitmap;
        bitmap4 = bitmap2;
        FileInputStream fileInputStream = new FileInputStream(string2);
        bitmap3 = bitmap = (bitmap3 = ImageDecoder.decodeBitmap(ImageDecoder.createSource((Resources)object, fileInputStream), (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$BitmapDrawable$23eAuhdkgEf5MIRJC_rMNbn4Pyg.INSTANCE));
        bitmap4 = bitmap;
        BitmapDrawable.$closeResource(null, fileInputStream);
        this.init(new BitmapState(bitmap), (Resources)object);
        if (this.mBitmapState.mBitmap != null) return;
        object = new StringBuilder();
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                bitmap3 = bitmap;
                bitmap4 = bitmap2;
                try {
                    BitmapDrawable.$closeResource(throwable, fileInputStream);
                    bitmap3 = bitmap;
                    bitmap4 = bitmap2;
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    this.init(new BitmapState(bitmap3), (Resources)object);
                    if (this.mBitmapState.mBitmap != null) throw throwable3;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("BitmapDrawable cannot decode ");
                    ((StringBuilder)object).append(string2);
                    Log.w("BitmapDrawable", ((StringBuilder)object).toString());
                    throw throwable3;
                }
                catch (Exception exception) {
                    this.init(new BitmapState(bitmap4), (Resources)object);
                    if (this.mBitmapState.mBitmap != null) return;
                    object = new StringBuilder();
                }
            }
        }
        ((StringBuilder)object).append("BitmapDrawable cannot decode ");
        ((StringBuilder)object).append(string2);
        Log.w("BitmapDrawable", ((StringBuilder)object).toString());
    }

    @Deprecated
    public BitmapDrawable(Bitmap bitmap) {
        this.init(new BitmapState(bitmap), null);
    }

    private BitmapDrawable(BitmapState bitmapState, Resources resources) {
        this.init(bitmapState, resources);
    }

    @Deprecated
    public BitmapDrawable(InputStream inputStream) {
        this(null, inputStream);
    }

    @Deprecated
    public BitmapDrawable(String string2) {
        this(null, string2);
    }

    private void computeBitmapSize() {
        Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap != null) {
            this.mBitmapWidth = bitmap.getScaledWidth(this.mTargetDensity);
            this.mBitmapHeight = bitmap.getScaledHeight(this.mTargetDensity);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
        }
    }

    private Matrix getOrCreateMirrorMatrix() {
        if (this.mMirrorMatrix == null) {
            this.mMirrorMatrix = new Matrix();
        }
        return this.mMirrorMatrix;
    }

    private void init(BitmapState bitmapState, Resources resources) {
        this.mBitmapState = bitmapState;
        this.updateLocalState(resources);
        bitmapState = this.mBitmapState;
        if (bitmapState != null && resources != null) {
            bitmapState.mTargetDensity = this.mTargetDensity;
        }
    }

    static /* synthetic */ void lambda$new$0(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }

    static /* synthetic */ void lambda$new$1(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$2(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
    }

    private boolean needMirroring() {
        boolean bl = this.isAutoMirrored();
        boolean bl2 = true;
        if (!bl || this.getLayoutDirection() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    private static Shader.TileMode parseTileMode(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return Shader.TileMode.MIRROR;
            }
            return Shader.TileMode.REPEAT;
        }
        return Shader.TileMode.CLAMP;
    }

    private void updateDstRectAndInsetsIfDirty() {
        if (this.mDstRectAndInsetsDirty) {
            if (this.mBitmapState.mTileModeX == null && this.mBitmapState.mTileModeY == null) {
                Rect rect = this.getBounds();
                int n = this.getLayoutDirection();
                Gravity.apply(this.mBitmapState.mGravity, this.mBitmapWidth, this.mBitmapHeight, rect, this.mDstRect, n);
                this.mOpticalInsets = Insets.of(this.mDstRect.left - rect.left, this.mDstRect.top - rect.top, rect.right - this.mDstRect.right, rect.bottom - this.mDstRect.bottom);
            } else {
                this.copyBounds(this.mDstRect);
                this.mOpticalInsets = Insets.NONE;
            }
        }
        this.mDstRectAndInsetsDirty = false;
    }

    private void updateLocalState(Resources resources) {
        this.mTargetDensity = BitmapDrawable.resolveDensity(resources, this.mBitmapState.mTargetDensity);
        this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, this.mBitmapState.mBlendMode);
        this.computeBitmapSize();
    }

    private void updateShaderMatrix(Bitmap object, Paint paint, Shader shader, boolean bl) {
        int n = ((Bitmap)object).getDensity();
        int n2 = this.mTargetDensity;
        boolean bl2 = n != 0 && n != n2;
        if (!bl2 && !bl) {
            this.mMirrorMatrix = null;
            shader.setLocalMatrix(Matrix.IDENTITY_MATRIX);
        } else {
            object = this.getOrCreateMirrorMatrix();
            ((Matrix)object).reset();
            if (bl) {
                ((Matrix)object).setTranslate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                ((Matrix)object).setScale(-1.0f, 1.0f);
            }
            if (bl2) {
                float f = (float)n2 / (float)n;
                ((Matrix)object).postScale(f, f);
            }
            shader.setLocalMatrix((Matrix)object);
        }
        paint.setShader(shader);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void updateStateFromTypedArray(TypedArray typedArray, int n) throws XmlPullParserException {
        block18 : {
            block19 : {
                block17 : {
                    resources = typedArray.getResources();
                    bitmapState = this.mBitmapState;
                    bitmapState.mChangingConfigurations |= typedArray.getChangingConfigurations();
                    bitmapState.mThemeAttrs = typedArray.extractThemeAttrs();
                    bitmapState.mSrcDensityOverride = n;
                    bitmapState.mTargetDensity = Drawable.resolveDensity(resources, 0);
                    n2 = typedArray.getResourceId(1, 0);
                    if (n2 == 0) break block18;
                    object2 = new TypedValue();
                    resources.getValueForDensity(n2, n, (TypedValue)object2, true);
                    if (n > 0 && object2.density > 0 && object2.density != 65535) {
                        object2.density = object2.density == n ? resources.getDisplayMetrics().densityDpi : object2.density * resources.getDisplayMetrics().densityDpi / n;
                    }
                    n = 0;
                    if (object2.density == 0) {
                        n = 160;
                    } else if (object2.density != 65535) {
                        n = object2.density;
                    }
                    object /* !! */  = object3 = null;
                    object2 = resources.openRawResource(n2, (TypedValue)object2);
                    object3 = object /* !! */  = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resources, (InputStream)object2, n), (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$BitmapDrawable$LMqt8JvxZ4giSOIRAtlCKDg39Jw.INSTANCE);
                    if (object2 == null) break block17;
                    object /* !! */  = object3;
                    BitmapDrawable.$closeResource(null, (AutoCloseable)object2);
                }
                object /* !! */  = object3;
                break block19;
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        if (object2 == null) ** GOTO lbl39
                        object /* !! */  = object3;
                        try {
                            BitmapDrawable.$closeResource(throwable, (AutoCloseable)object2);
lbl39: // 2 sources:
                            object /* !! */  = object3;
                            throw throwable2;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
            }
            if (object /* !! */  == null) {
                object /* !! */  = new StringBuilder();
                object /* !! */ .append(typedArray.getPositionDescription());
                object /* !! */ .append(": <bitmap> requires a valid 'src' attribute");
                throw new XmlPullParserException(object /* !! */ .toString());
            }
            bitmapState.mBitmap = object /* !! */ ;
        }
        bl = bitmapState.mBitmap != null ? bitmapState.mBitmap.hasMipMap() : false;
        this.setMipMap(typedArray.getBoolean(8, bl));
        bitmapState.mAutoMirrored = typedArray.getBoolean(9, bitmapState.mAutoMirrored);
        bitmapState.mBaseAlpha = typedArray.getFloat(7, bitmapState.mBaseAlpha);
        n = typedArray.getInt(10, -1);
        if (n != -1) {
            bitmapState.mBlendMode = Drawable.parseBlendMode(n, BlendMode.SRC_IN);
        }
        if ((object /* !! */  = typedArray.getColorStateList(5)) != null) {
            bitmapState.mTint = object /* !! */ ;
        }
        object /* !! */  = this.mBitmapState.mPaint;
        object /* !! */ .setAntiAlias(typedArray.getBoolean(2, object /* !! */ .isAntiAlias()));
        object /* !! */ .setFilterBitmap(typedArray.getBoolean(3, object /* !! */ .isFilterBitmap()));
        object /* !! */ .setDither(typedArray.getBoolean(4, object /* !! */ .isDither()));
        this.setGravity(typedArray.getInt(0, bitmapState.mGravity));
        n = typedArray.getInt(6, -2);
        if (n != -2) {
            object /* !! */  = BitmapDrawable.parseTileMode(n);
            this.setTileModeXY((Shader.TileMode)object /* !! */ , (Shader.TileMode)object /* !! */ );
        }
        if ((n = typedArray.getInt(11, -2)) != -2) {
            this.setTileModeX(BitmapDrawable.parseTileMode(n));
        }
        if ((n = typedArray.getInt(12, -2)) == -2) return;
        this.setTileModeY(BitmapDrawable.parseTileMode(n));
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        Object object = this.mBitmapState;
        if (((BitmapState)object).mBitmap == null && (((BitmapState)object).mThemeAttrs == null || ((BitmapState)object).mThemeAttrs[1] == 0)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(typedArray.getPositionDescription());
            ((StringBuilder)object).append(": <bitmap> requires a valid 'src' attribute");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void applyTheme(Resources.Theme var1_1) {
        block8 : {
            super.applyTheme(var1_1);
            var2_3 = this.mBitmapState;
            if (var2_3 == null) {
                return;
            }
            if (var2_3.mThemeAttrs != null) {
                var3_4 = var1_1.resolveAttributes(var2_3.mThemeAttrs, R.styleable.BitmapDrawable);
                this.updateStateFromTypedArray(var3_4, var2_3.mSrcDensityOverride);
lbl9: // 2 sources:
                do {
                    var3_4.recycle();
                    break block8;
                    break;
                } while (true);
                {
                    catch (Throwable var1_2) {
                    }
                    catch (XmlPullParserException var4_5) {}
                    {
                        BitmapDrawable.rethrowAsRuntimeException((Exception)var4_5);
                        ** continue;
                    }
                }
                var3_4.recycle();
                throw var1_2;
            }
        }
        if (var2_3.mTint != null && var2_3.mTint.canApplyTheme()) {
            var2_3.mTint = var2_3.mTint.obtainForTheme(var1_1);
        }
        this.updateLocalState(var1_1.getResources());
    }

    @Override
    public boolean canApplyTheme() {
        BitmapState bitmapState = this.mBitmapState;
        boolean bl = bitmapState != null && bitmapState.canApplyTheme();
        return bl;
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        int n;
        Object object;
        boolean bl;
        Bitmap bitmap = this.mBitmapState.mBitmap;
        if (bitmap == null) {
            return;
        }
        BitmapState bitmapState = this.mBitmapState;
        Paint paint = bitmapState.mPaint;
        if (bitmapState.mRebuildShader) {
            object = bitmapState.mTileModeX;
            Shader.TileMode tileMode = bitmapState.mTileModeY;
            if (object == null && tileMode == null) {
                paint.setShader(null);
            } else {
                if (object == null) {
                    object = Shader.TileMode.CLAMP;
                }
                if (tileMode == null) {
                    tileMode = Shader.TileMode.CLAMP;
                }
                paint.setShader(new BitmapShader(bitmap, (Shader.TileMode)((Object)object), tileMode));
            }
            bitmapState.mRebuildShader = false;
        }
        if (bitmapState.mBaseAlpha != 1.0f) {
            object = this.getPaint();
            n = ((Paint)object).getAlpha();
            ((Paint)object).setAlpha((int)((float)n * bitmapState.mBaseAlpha + 0.5f));
        } else {
            n = -1;
        }
        if (this.mBlendModeFilter != null && paint.getColorFilter() == null) {
            paint.setColorFilter(this.mBlendModeFilter);
            bl = true;
        } else {
            bl = false;
        }
        this.updateDstRectAndInsetsIfDirty();
        object = paint.getShader();
        boolean bl2 = this.needMirroring();
        if (object == null) {
            if (bl2) {
                canvas.save();
                canvas.translate(this.mDstRect.right - this.mDstRect.left, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            canvas.drawBitmap(bitmap, null, this.mDstRect, paint);
            if (bl2) {
                canvas.restore();
            }
        } else {
            this.updateShaderMatrix(bitmap, paint, (Shader)object, bl2);
            canvas.drawRect(this.mDstRect, paint);
        }
        if (bl) {
            paint.setColorFilter(null);
        }
        if (n >= 0) {
            paint.setAlpha(n);
        }
    }

    @Override
    public int getAlpha() {
        return this.mBitmapState.mPaint.getAlpha();
    }

    public final Bitmap getBitmap() {
        return this.mBitmapState.mBitmap;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mBitmapState.getChangingConfigurations();
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mBitmapState.mPaint.getColorFilter();
    }

    @Override
    public final Drawable.ConstantState getConstantState() {
        BitmapState bitmapState = this.mBitmapState;
        bitmapState.mChangingConfigurations |= this.getChangingConfigurations();
        return this.mBitmapState;
    }

    public int getGravity() {
        return this.mBitmapState.mGravity;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override
    public int getOpacity() {
        int n;
        block1 : {
            int n2 = this.mBitmapState.mGravity;
            n = -3;
            if (n2 != 119) {
                return -3;
            }
            Bitmap bitmap = this.mBitmapState.mBitmap;
            if (bitmap == null || bitmap.hasAlpha() || this.mBitmapState.mPaint.getAlpha() < 255) break block1;
            n = -1;
        }
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        this.updateDstRectAndInsetsIfDirty();
        return this.mOpticalInsets;
    }

    @Override
    public void getOutline(Outline outline) {
        this.updateDstRectAndInsetsIfDirty();
        outline.setRect(this.mDstRect);
        boolean bl = this.mBitmapState.mBitmap != null && !this.mBitmapState.mBitmap.hasAlpha();
        float f = bl ? (float)this.getAlpha() / 255.0f : 0.0f;
        outline.setAlpha(f);
    }

    public final Paint getPaint() {
        return this.mBitmapState.mPaint;
    }

    public Shader.TileMode getTileModeX() {
        return this.mBitmapState.mTileModeX;
    }

    public Shader.TileMode getTileModeY() {
        return this.mBitmapState.mTileModeY;
    }

    @UnsupportedAppUsage
    public ColorStateList getTint() {
        return this.mBitmapState.mTint;
    }

    @UnsupportedAppUsage
    public PorterDuff.Mode getTintMode() {
        return BlendMode.blendModeToPorterDuffMode(this.mBitmapState.mBlendMode);
    }

    public boolean hasAntiAlias() {
        return this.mBitmapState.mPaint.isAntiAlias();
    }

    @Override
    public boolean hasFocusStateSpecified() {
        boolean bl = this.mBitmapState.mTint != null && this.mBitmapState.mTint.hasFocusStateSpecified();
        return bl;
    }

    public boolean hasMipMap() {
        boolean bl = this.mBitmapState.mBitmap != null && this.mBitmapState.mBitmap.hasMipMap();
        return bl;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser object, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, (XmlPullParser)object, attributeSet, theme);
        object = BitmapDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.BitmapDrawable);
        this.updateStateFromTypedArray((TypedArray)object, this.mSrcDensityOverride);
        this.verifyRequiredAttributes((TypedArray)object);
        ((TypedArray)object).recycle();
        this.updateLocalState(resources);
    }

    @Override
    public final boolean isAutoMirrored() {
        return this.mBitmapState.mAutoMirrored;
    }

    @Override
    public boolean isFilterBitmap() {
        return this.mBitmapState.mPaint.isFilterBitmap();
    }

    @Override
    public boolean isStateful() {
        boolean bl = this.mBitmapState.mTint != null && this.mBitmapState.mTint.isStateful() || super.isStateful();
        return bl;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mBitmapState = new BitmapState(this.mBitmapState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect parcelable) {
        this.mDstRectAndInsetsDirty = true;
        parcelable = this.mBitmapState.mBitmap;
        Shader shader = this.mBitmapState.mPaint.getShader();
        if (parcelable != null && shader != null) {
            this.updateShaderMatrix((Bitmap)parcelable, this.mBitmapState.mPaint, shader, this.needMirroring());
        }
    }

    @Override
    protected boolean onStateChange(int[] object) {
        object = this.mBitmapState;
        if (object.mTint != null && object.mBlendMode != null) {
            this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, object.mTint, object.mBlendMode);
            return true;
        }
        return false;
    }

    @Override
    public void setAlpha(int n) {
        if (n != this.mBitmapState.mPaint.getAlpha()) {
            this.mBitmapState.mPaint.setAlpha(n);
            this.invalidateSelf();
        }
    }

    public void setAntiAlias(boolean bl) {
        this.mBitmapState.mPaint.setAntiAlias(bl);
        this.invalidateSelf();
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        if (this.mBitmapState.mAutoMirrored != bl) {
            this.mBitmapState.mAutoMirrored = bl;
            this.invalidateSelf();
        }
    }

    @UnsupportedAppUsage
    public void setBitmap(Bitmap bitmap) {
        if (this.mBitmapState.mBitmap != bitmap) {
            this.mBitmapState.mBitmap = bitmap;
            this.computeBitmapSize();
            this.invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mBitmapState.mPaint.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    @Override
    public void setDither(boolean bl) {
        this.mBitmapState.mPaint.setDither(bl);
        this.invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean bl) {
        this.mBitmapState.mPaint.setFilterBitmap(bl);
        this.invalidateSelf();
    }

    public void setGravity(int n) {
        if (this.mBitmapState.mGravity != n) {
            this.mBitmapState.mGravity = n;
            this.mDstRectAndInsetsDirty = true;
            this.invalidateSelf();
        }
    }

    public void setMipMap(boolean bl) {
        if (this.mBitmapState.mBitmap != null) {
            this.mBitmapState.mBitmap.setHasMipMap(bl);
            this.invalidateSelf();
        }
    }

    public void setTargetDensity(int n) {
        if (this.mTargetDensity != n) {
            if (n == 0) {
                n = 160;
            }
            this.mTargetDensity = n;
            if (this.mBitmapState.mBitmap != null) {
                this.computeBitmapSize();
            }
            this.invalidateSelf();
        }
    }

    public void setTargetDensity(Canvas canvas) {
        this.setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(DisplayMetrics displayMetrics) {
        this.setTargetDensity(displayMetrics.densityDpi);
    }

    public void setTileModeX(Shader.TileMode tileMode) {
        this.setTileModeXY(tileMode, this.mBitmapState.mTileModeY);
    }

    public void setTileModeXY(Shader.TileMode tileMode, Shader.TileMode tileMode2) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTileModeX != tileMode || bitmapState.mTileModeY != tileMode2) {
            bitmapState.mTileModeX = tileMode;
            bitmapState.mTileModeY = tileMode2;
            bitmapState.mRebuildShader = true;
            this.mDstRectAndInsetsDirty = true;
            this.invalidateSelf();
        }
    }

    public final void setTileModeY(Shader.TileMode tileMode) {
        this.setTileModeXY(this.mBitmapState.mTileModeX, tileMode);
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mBlendMode != blendMode) {
            bitmapState.mBlendMode = blendMode;
            this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, this.mBitmapState.mTint, blendMode);
            this.invalidateSelf();
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        BitmapState bitmapState = this.mBitmapState;
        if (bitmapState.mTint != colorStateList) {
            bitmapState.mTint = colorStateList;
            this.mBlendModeFilter = this.updateBlendModeFilter(this.mBlendModeFilter, colorStateList, this.mBitmapState.mBlendMode);
            this.invalidateSelf();
        }
    }

    @Override
    public void setXfermode(Xfermode xfermode) {
        this.mBitmapState.mPaint.setXfermode(xfermode);
        this.invalidateSelf();
    }

    static final class BitmapState
    extends Drawable.ConstantState {
        boolean mAutoMirrored = false;
        float mBaseAlpha = 1.0f;
        Bitmap mBitmap = null;
        BlendMode mBlendMode = Drawable.DEFAULT_BLEND_MODE;
        int mChangingConfigurations;
        int mGravity = 119;
        final Paint mPaint;
        boolean mRebuildShader;
        int mSrcDensityOverride = 0;
        int mTargetDensity = 160;
        int[] mThemeAttrs = null;
        Shader.TileMode mTileModeX = null;
        Shader.TileMode mTileModeY = null;
        ColorStateList mTint = null;

        BitmapState(Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mPaint = new Paint(6);
        }

        BitmapState(BitmapState bitmapState) {
            this.mBitmap = bitmapState.mBitmap;
            this.mTint = bitmapState.mTint;
            this.mBlendMode = bitmapState.mBlendMode;
            this.mThemeAttrs = bitmapState.mThemeAttrs;
            this.mChangingConfigurations = bitmapState.mChangingConfigurations;
            this.mGravity = bitmapState.mGravity;
            this.mTileModeX = bitmapState.mTileModeX;
            this.mTileModeY = bitmapState.mTileModeY;
            this.mSrcDensityOverride = bitmapState.mSrcDensityOverride;
            this.mTargetDensity = bitmapState.mTargetDensity;
            this.mBaseAlpha = bitmapState.mBaseAlpha;
            this.mPaint = new Paint(bitmapState.mPaint);
            this.mRebuildShader = bitmapState.mRebuildShader;
            this.mAutoMirrored = bitmapState.mAutoMirrored;
        }

        @Override
        public boolean canApplyTheme() {
            ColorStateList colorStateList;
            boolean bl = this.mThemeAttrs != null || (colorStateList = this.mTint) != null && colorStateList.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            ColorStateList colorStateList = this.mTint;
            int n2 = colorStateList != null ? colorStateList.getChangingConfigurations() : 0;
            return n | n2;
        }

        @Override
        public Drawable newDrawable() {
            return new BitmapDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new BitmapDrawable(this, resources);
        }
    }

}

