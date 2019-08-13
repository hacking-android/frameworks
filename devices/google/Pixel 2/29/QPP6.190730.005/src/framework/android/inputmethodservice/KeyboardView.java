/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.R;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class KeyboardView
extends View
implements View.OnClickListener {
    private static final int DEBOUNCE_TIME = 70;
    private static final boolean DEBUG = false;
    private static final int DELAY_AFTER_PREVIEW = 70;
    private static final int DELAY_BEFORE_PREVIEW = 0;
    private static final int[] KEY_DELETE = new int[]{-5};
    private static final int LONGPRESS_TIMEOUT;
    private static final int[] LONG_PRESSABLE_STATE_SET;
    private static int MAX_NEARBY_KEYS = 0;
    private static final int MSG_LONGPRESS = 4;
    private static final int MSG_REMOVE_PREVIEW = 2;
    private static final int MSG_REPEAT = 3;
    private static final int MSG_SHOW_PREVIEW = 1;
    private static final int MULTITAP_INTERVAL = 800;
    private static final int NOT_A_KEY = -1;
    private static final int REPEAT_INTERVAL = 50;
    private static final int REPEAT_START_DELAY = 400;
    private boolean mAbortKey;
    private AccessibilityManager mAccessibilityManager;
    private AudioManager mAudioManager;
    private float mBackgroundDimAmount;
    private Bitmap mBuffer;
    private Canvas mCanvas;
    private Rect mClipRegion = new Rect(0, 0, 0, 0);
    private final int[] mCoordinates = new int[2];
    private int mCurrentKey = -1;
    private int mCurrentKeyIndex = -1;
    private long mCurrentKeyTime;
    private Rect mDirtyRect = new Rect();
    private boolean mDisambiguateSwipe;
    private int[] mDistances = new int[MAX_NEARBY_KEYS];
    private int mDownKey = -1;
    private long mDownTime;
    private boolean mDrawPending;
    private GestureDetector mGestureDetector;
    Handler mHandler;
    private boolean mHeadsetRequiredToHearPasswordsAnnounced;
    private boolean mInMultiTap;
    private Keyboard.Key mInvalidatedKey;
    @UnsupportedAppUsage
    private Drawable mKeyBackground;
    private int[] mKeyIndices = new int[12];
    private int mKeyTextColor;
    private int mKeyTextSize;
    private Keyboard mKeyboard;
    private OnKeyboardActionListener mKeyboardActionListener;
    private boolean mKeyboardChanged;
    private Keyboard.Key[] mKeys;
    @UnsupportedAppUsage
    private int mLabelTextSize;
    private int mLastCodeX;
    private int mLastCodeY;
    private int mLastKey;
    private long mLastKeyTime;
    private long mLastMoveTime;
    private int mLastSentIndex;
    private long mLastTapTime;
    private int mLastX;
    private int mLastY;
    private KeyboardView mMiniKeyboard;
    private Map<Keyboard.Key, View> mMiniKeyboardCache;
    private View mMiniKeyboardContainer;
    private int mMiniKeyboardOffsetX;
    private int mMiniKeyboardOffsetY;
    private boolean mMiniKeyboardOnScreen;
    private int mOldPointerCount = 1;
    private float mOldPointerX;
    private float mOldPointerY;
    private Rect mPadding;
    private Paint mPaint;
    private PopupWindow mPopupKeyboard;
    private int mPopupLayout;
    private View mPopupParent;
    private int mPopupPreviewX;
    private int mPopupPreviewY;
    private int mPopupX;
    private int mPopupY;
    private boolean mPossiblePoly;
    private boolean mPreviewCentered = false;
    private int mPreviewHeight;
    private StringBuilder mPreviewLabel = new StringBuilder(1);
    private int mPreviewOffset;
    private PopupWindow mPreviewPopup;
    @UnsupportedAppUsage
    private TextView mPreviewText;
    private int mPreviewTextSizeLarge;
    private boolean mProximityCorrectOn;
    private int mProximityThreshold;
    private int mRepeatKeyIndex = -1;
    private int mShadowColor;
    private float mShadowRadius;
    private boolean mShowPreview = true;
    private boolean mShowTouchPoints = true;
    private int mStartX;
    private int mStartY;
    private int mSwipeThreshold;
    private SwipeTracker mSwipeTracker = new SwipeTracker();
    private int mTapCount;
    private int mVerticalCorrection;

    static {
        LONG_PRESSABLE_STATE_SET = new int[]{16843324};
        LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
        MAX_NEARBY_KEYS = 12;
    }

    public KeyboardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17956959);
    }

    public KeyboardView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public KeyboardView(Context context, AttributeSet object, int n, int n2) {
        super(context, (AttributeSet)object, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes((AttributeSet)object, R.styleable.KeyboardView, n, n2);
        object = (LayoutInflater)context.getSystemService("layout_inflater");
        n2 = 0;
        int n3 = typedArray.getIndexCount();
        block13 : for (n = 0; n < n3; ++n) {
            int n4 = typedArray.getIndex(n);
            switch (n4) {
                default: {
                    continue block13;
                }
                case 10: {
                    this.mPopupLayout = typedArray.getResourceId(n4, 0);
                    continue block13;
                }
                case 9: {
                    this.mVerticalCorrection = typedArray.getDimensionPixelOffset(n4, 0);
                    continue block13;
                }
                case 8: {
                    this.mPreviewHeight = typedArray.getDimensionPixelSize(n4, 80);
                    continue block13;
                }
                case 7: {
                    this.mPreviewOffset = typedArray.getDimensionPixelOffset(n4, 0);
                    continue block13;
                }
                case 6: {
                    n2 = typedArray.getResourceId(n4, 0);
                    continue block13;
                }
                case 5: {
                    this.mKeyTextColor = typedArray.getColor(n4, -16777216);
                    continue block13;
                }
                case 4: {
                    this.mLabelTextSize = typedArray.getDimensionPixelSize(n4, 14);
                    continue block13;
                }
                case 3: {
                    this.mKeyTextSize = typedArray.getDimensionPixelSize(n4, 18);
                    continue block13;
                }
                case 2: {
                    this.mKeyBackground = typedArray.getDrawable(n4);
                    continue block13;
                }
                case 1: {
                    this.mShadowRadius = typedArray.getFloat(n4, 0.0f);
                    continue block13;
                }
                case 0: {
                    this.mShadowColor = typedArray.getColor(n4, 0);
                }
            }
        }
        this.mBackgroundDimAmount = this.mContext.obtainStyledAttributes(R.styleable.Theme).getFloat(2, 0.5f);
        this.mPreviewPopup = new PopupWindow(context);
        if (n2 != 0) {
            this.mPreviewText = (TextView)((LayoutInflater)object).inflate(n2, null);
            this.mPreviewTextSizeLarge = (int)this.mPreviewText.getTextSize();
            this.mPreviewPopup.setContentView(this.mPreviewText);
            this.mPreviewPopup.setBackgroundDrawable(null);
        } else {
            this.mShowPreview = false;
        }
        this.mPreviewPopup.setTouchable(false);
        this.mPopupKeyboard = new PopupWindow(context);
        this.mPopupKeyboard.setBackgroundDrawable(null);
        this.mPopupParent = this;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize((float)false);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setAlpha(255);
        this.mPadding = new Rect(0, 0, 0, 0);
        this.mMiniKeyboardCache = new HashMap<Keyboard.Key, View>();
        this.mKeyBackground.getPadding(this.mPadding);
        this.mSwipeThreshold = (int)(this.getResources().getDisplayMetrics().density * 500.0f);
        this.mDisambiguateSwipe = this.getResources().getBoolean(17891545);
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mAudioManager = (AudioManager)context.getSystemService("audio");
        this.resetMultiTap();
    }

    static /* synthetic */ int access$1000(KeyboardView keyboardView) {
        return keyboardView.mStartX;
    }

    static /* synthetic */ int access$1100(KeyboardView keyboardView) {
        return keyboardView.mStartY;
    }

    static /* synthetic */ void access$1200(KeyboardView keyboardView, int n, int n2, int n3, long l) {
        keyboardView.detectAndSendKey(n, n2, n3, l);
    }

    static /* synthetic */ boolean access$500(KeyboardView keyboardView) {
        return keyboardView.mPossiblePoly;
    }

    static /* synthetic */ SwipeTracker access$600(KeyboardView keyboardView) {
        return keyboardView.mSwipeTracker;
    }

    static /* synthetic */ int access$700(KeyboardView keyboardView) {
        return keyboardView.mSwipeThreshold;
    }

    static /* synthetic */ boolean access$800(KeyboardView keyboardView) {
        return keyboardView.mDisambiguateSwipe;
    }

    static /* synthetic */ int access$900(KeyboardView keyboardView) {
        return keyboardView.mDownKey;
    }

    private CharSequence adjustCase(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (this.mKeyboard.isShifted()) {
            charSequence2 = charSequence;
            if (charSequence != null) {
                charSequence2 = charSequence;
                if (charSequence.length() < 3) {
                    charSequence2 = charSequence;
                    if (Character.isLowerCase(charSequence.charAt(0))) {
                        charSequence2 = charSequence.toString().toUpperCase();
                    }
                }
            }
        }
        return charSequence2;
    }

    private void checkMultiTap(long l, int n) {
        if (n == -1) {
            return;
        }
        Keyboard.Key key = this.mKeys[n];
        if (key.codes.length > 1) {
            this.mInMultiTap = true;
            if (l < this.mLastTapTime + 800L && n == this.mLastSentIndex) {
                this.mTapCount = (this.mTapCount + 1) % key.codes.length;
                return;
            }
            this.mTapCount = -1;
            return;
        }
        if (l > this.mLastTapTime + 800L || n != this.mLastSentIndex) {
            this.resetMultiTap();
        }
    }

    private void computeProximityThreshold(Keyboard arrkey) {
        int n;
        if (arrkey == null) {
            return;
        }
        arrkey = this.mKeys;
        if (arrkey == null) {
            return;
        }
        int n2 = arrkey.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            Keyboard.Key key = arrkey[n];
            n3 += Math.min(key.width, key.height) + key.gap;
        }
        if (n3 >= 0 && n2 != 0) {
            n = this.mProximityThreshold = (int)((float)n3 * 1.4f / (float)n2);
            this.mProximityThreshold = n * n;
            return;
        }
    }

    private void detectAndSendKey(int n, int n2, int n3, long l) {
        Object[] arrobject;
        if (n != -1 && n < (arrobject = this.mKeys).length) {
            Keyboard.Key key = arrobject[n];
            if (key.text != null) {
                this.mKeyboardActionListener.onText(key.text);
                this.mKeyboardActionListener.onRelease(-1);
            } else {
                int n4 = key.codes[0];
                arrobject = new int[MAX_NEARBY_KEYS];
                Arrays.fill((int[])arrobject, -1);
                this.getKeyIndices(n2, n3, (int[])arrobject);
                n2 = n4;
                if (this.mInMultiTap) {
                    if (this.mTapCount != -1) {
                        this.mKeyboardActionListener.onKey(-5, KEY_DELETE);
                    } else {
                        this.mTapCount = 0;
                    }
                    n2 = key.codes[this.mTapCount];
                }
                this.mKeyboardActionListener.onKey(n2, (int[])arrobject);
                this.mKeyboardActionListener.onRelease(n2);
            }
            this.mLastSentIndex = n;
            this.mLastTapTime = l;
        }
    }

    private void dismissPopupKeyboard() {
        if (this.mPopupKeyboard.isShowing()) {
            this.mPopupKeyboard.dismiss();
            this.mMiniKeyboardOnScreen = false;
            this.invalidateAllKeys();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getKeyIndices(int n, int n2, int[] arrn) {
        Keyboard.Key[] arrkey = this.mKeys;
        int n3 = -1;
        int n4 = -1;
        int n5 = this.mProximityThreshold + 1;
        Arrays.fill(this.mDistances, Integer.MAX_VALUE);
        int[] arrn2 = this.mKeyboard.getNearestKeys(n, n2);
        int n6 = arrn2.length;
        int n7 = 0;
        do {
            int n8;
            int n9;
            block5 : {
                int[] arrn3;
                int n10;
                int n11;
                Keyboard.Key key;
                block10 : {
                    block6 : {
                        block9 : {
                            block8 : {
                                boolean bl;
                                block7 : {
                                    n8 = n2;
                                    n10 = n;
                                    if (n7 >= n6) break block6;
                                    key = arrkey[arrn2[n7]];
                                    n11 = 0;
                                    bl = key.isInside(n10, n8);
                                    n9 = n3;
                                    if (bl) {
                                        n9 = arrn2[n7];
                                    }
                                    n3 = n11;
                                    if (!this.mProximityCorrectOn) break block7;
                                    n3 = n8 = (n10 = key.squaredDistanceFrom(n10, n8));
                                    if (n10 < this.mProximityThreshold) break block8;
                                    n3 = n8;
                                }
                                if (!bl) break block9;
                            }
                            if (key.codes[0] <= 32) break block9;
                            n11 = key.codes.length;
                            n8 = n4;
                            n4 = n5;
                            if (n3 < n5) {
                                n4 = n3;
                                n8 = arrn2[n7];
                            }
                            if (arrn != null) break block10;
                            n5 = n4;
                            break block5;
                        }
                        n8 = n4;
                        break block5;
                    }
                    n = n3;
                    if (n3 != -1) return n;
                    return n4;
                }
                for (n5 = 0; n5 < (arrn3 = this.mDistances).length; ++n5) {
                    if (arrn3[n5] <= n3) continue;
                    System.arraycopy(arrn3, n5, arrn3, n5 + n11, arrn3.length - n5 - n11);
                    System.arraycopy(arrn, n5, arrn, n5 + n11, arrn.length - n5 - n11);
                    for (n10 = 0; n10 < n11; ++n10) {
                        arrn[n5 + n10] = key.codes[n10];
                        this.mDistances[n5 + n10] = n3;
                    }
                    n5 = n4;
                    break block5;
                }
                n5 = n4;
            }
            ++n7;
            n3 = n9;
            n4 = n8;
        } while (true);
    }

    private CharSequence getPreviewText(Keyboard.Key arrn) {
        if (this.mInMultiTap) {
            StringBuilder stringBuilder = this.mPreviewLabel;
            int n = 0;
            stringBuilder.setLength(0);
            stringBuilder = this.mPreviewLabel;
            arrn = arrn.codes;
            int n2 = this.mTapCount;
            if (n2 >= 0) {
                n = n2;
            }
            stringBuilder.append((char)arrn[n]);
            return this.adjustCase(this.mPreviewLabel);
        }
        return this.adjustCase(arrn.label);
    }

    private void initGestureDetector() {
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener(){

                /*
                 * Unable to fully structure code
                 * Enabled aggressive block sorting
                 * Lifted jumps to return sites
                 */
                @Override
                public boolean onFling(MotionEvent var1_1, MotionEvent var2_2, float var3_3, float var4_4) {
                    block10 : {
                        block11 : {
                            block12 : {
                                block8 : {
                                    block9 : {
                                        if (KeyboardView.access$500(KeyboardView.this)) {
                                            return false;
                                        }
                                        var5_5 = Math.abs(var3_3);
                                        var6_6 = Math.abs(var4_4);
                                        var7_7 = var2_2.getX() - var1_1.getX();
                                        var8_8 = var2_2.getY() - var1_1.getY();
                                        var9_9 = KeyboardView.this.getWidth() / 2;
                                        var10_10 = KeyboardView.this.getHeight() / 2;
                                        KeyboardView.access$600(KeyboardView.this).computeCurrentVelocity(1000);
                                        var11_11 = KeyboardView.access$600(KeyboardView.this).getXVelocity();
                                        var12_12 = KeyboardView.access$600(KeyboardView.this).getYVelocity();
                                        var13_13 = 0;
                                        if (!(var3_3 > (float)KeyboardView.access$700(KeyboardView.this)) || !(var6_6 < var5_5) || !(var7_7 > (float)var9_9)) break block8;
                                        if (!KeyboardView.access$800(KeyboardView.this) || !(var11_11 < var3_3 / 4.0f)) break block9;
                                        var9_9 = 1;
                                        break block10;
                                    }
                                    KeyboardView.this.swipeRight();
                                    return true;
                                }
                                if (!(var3_3 < (float)(-KeyboardView.access$700(KeyboardView.this))) || !(var6_6 < var5_5) || !(var7_7 < (float)(-var9_9))) break block11;
                                if (!KeyboardView.access$800(KeyboardView.this) || !(var11_11 > var3_3 / 4.0f)) break block12;
                                var9_9 = 1;
                                break block10;
                            }
                            KeyboardView.this.swipeLeft();
                            return true;
                        }
                        if (!(var4_4 < (float)(-KeyboardView.access$700(KeyboardView.this))) || !(var5_5 < var6_6) || !(var8_8 < (float)(-var10_10))) ** GOTO lbl35
                        if (KeyboardView.access$800(KeyboardView.this) && var12_12 > var4_4 / 4.0f) {
                            var9_9 = 1;
                        } else {
                            KeyboardView.this.swipeUp();
                            return true;
lbl35: // 1 sources:
                            var9_9 = var13_13;
                            if (var4_4 > (float)KeyboardView.access$700(KeyboardView.this)) {
                                var9_9 = var13_13;
                                if (var5_5 < var6_6 / 2.0f) {
                                    var9_9 = var13_13;
                                    if (var8_8 > (float)var10_10) {
                                        if (KeyboardView.access$800(KeyboardView.this) && var12_12 < var4_4 / 4.0f) {
                                            var9_9 = 1;
                                        } else {
                                            KeyboardView.this.swipeDown();
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (var9_9 == 0) return false;
                    var2_2 = KeyboardView.this;
                    KeyboardView.access$1200((KeyboardView)var2_2, KeyboardView.access$900((KeyboardView)var2_2), KeyboardView.access$1000(KeyboardView.this), KeyboardView.access$1100(KeyboardView.this), var1_1.getEventTime());
                    return false;
                }
            });
            this.mGestureDetector.setIsLongpressEnabled(false);
        }
    }

    private void onBufferDraw() {
        Keyboard.Key[] arrkey;
        if (this.mBuffer == null || this.mKeyboardChanged) {
            arrkey = this.mBuffer;
            if (arrkey == null || this.mKeyboardChanged && (arrkey.getWidth() != this.getWidth() || this.mBuffer.getHeight() != this.getHeight())) {
                this.mBuffer = Bitmap.createBitmap(Math.max(1, this.getWidth()), Math.max(1, this.getHeight()), Bitmap.Config.ARGB_8888);
                this.mCanvas = new Canvas(this.mBuffer);
            }
            this.invalidateAllKeys();
            this.mKeyboardChanged = false;
        }
        if (this.mKeyboard == null) {
            return;
        }
        this.mCanvas.save();
        Canvas canvas = this.mCanvas;
        canvas.clipRect(this.mDirtyRect);
        Paint paint = this.mPaint;
        Drawable drawable2 = this.mKeyBackground;
        Object object = this.mClipRegion;
        Rect rect = this.mPadding;
        int n = this.mPaddingLeft;
        int n2 = this.mPaddingTop;
        arrkey = this.mKeys;
        Keyboard.Key key = this.mInvalidatedKey;
        paint.setColor(this.mKeyTextColor);
        boolean bl = key != null && canvas.getClipBounds((Rect)object) && key.x + n - 1 <= ((Rect)object).left && key.y + n2 - 1 <= ((Rect)object).top && key.x + key.width + n + 1 >= ((Rect)object).right && key.y + key.height + n2 + 1 >= ((Rect)object).bottom;
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        for (Keyboard.Key key2 : arrkey) {
            if (bl && key != key2) continue;
            drawable2.setState(key2.getCurrentDrawableState());
            object = key2.label == null ? null : this.adjustCase(key2.label).toString();
            Rect rect2 = drawable2.getBounds();
            if (key2.width != rect2.right || key2.height != rect2.bottom) {
                drawable2.setBounds(0, 0, key2.width, key2.height);
            }
            canvas.translate(key2.x + n, key2.y + n2);
            drawable2.draw(canvas);
            if (object != null) {
                if (((String)object).length() > 1 && key2.codes.length < 2) {
                    paint.setTextSize(this.mLabelTextSize);
                    paint.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    paint.setTextSize(this.mKeyTextSize);
                    paint.setTypeface(Typeface.DEFAULT);
                }
                paint.setShadowLayer(this.mShadowRadius, 0.0f, 0.0f, this.mShadowColor);
                canvas.drawText((String)object, (key2.width - rect.left - rect.right) / 2 + rect.left, (float)((key2.height - rect.top - rect.bottom) / 2) + (paint.getTextSize() - paint.descent()) / 2.0f + (float)rect.top, paint);
                paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            } else if (key2.icon != null) {
                int n3 = (key2.width - rect.left - rect.right - key2.icon.getIntrinsicWidth()) / 2 + rect.left;
                int n4 = (key2.height - rect.top - rect.bottom - key2.icon.getIntrinsicHeight()) / 2 + rect.top;
                canvas.translate(n3, n4);
                key2.icon.setBounds(0, 0, key2.icon.getIntrinsicWidth(), key2.icon.getIntrinsicHeight());
                key2.icon.draw(canvas);
                canvas.translate(-n3, -n4);
            }
            canvas.translate(-key2.x - n, -key2.y - n2);
        }
        this.mInvalidatedKey = null;
        if (this.mMiniKeyboardOnScreen) {
            paint.setColor((int)(this.mBackgroundDimAmount * 255.0f) << 24);
            canvas.drawRect(0.0f, 0.0f, this.getWidth(), this.getHeight(), paint);
        }
        this.mCanvas.restore();
        this.mDrawPending = false;
        this.mDirtyRect.setEmpty();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean onModifiedTouchEvent(MotionEvent var1_1, boolean var2_2) {
        block29 : {
            block28 : {
                block26 : {
                    block27 : {
                        var3_3 = (int)var1_1.getX() - this.mPaddingLeft;
                        var4_4 = (int)var1_1.getY() - this.mPaddingTop;
                        var5_5 = this.mVerticalCorrection;
                        var6_6 = var4_4;
                        if (var4_4 >= -var5_5) {
                            var6_6 = var4_4 + var5_5;
                        }
                        var4_4 = var1_1.getAction();
                        var7_7 = var1_1.getEventTime();
                        var9_8 = this.getKeyIndices(var3_3, var6_6, null);
                        this.mPossiblePoly = var2_2;
                        if (var4_4 == 0) {
                            this.mSwipeTracker.clear();
                        }
                        this.mSwipeTracker.addMovement((MotionEvent)var1_1);
                        if (this.mAbortKey && var4_4 != 0 && var4_4 != 3) {
                            return true;
                        }
                        if (this.mGestureDetector.onTouchEvent((MotionEvent)var1_1)) {
                            this.showPreview(-1);
                            this.mHandler.removeMessages(3);
                            this.mHandler.removeMessages(4);
                            return true;
                        }
                        if (this.mMiniKeyboardOnScreen && var4_4 != 3) {
                            return true;
                        }
                        if (var4_4 == 0) break block26;
                        if (var4_4 == 1) break block27;
                        if (var4_4 != 2) {
                            if (var4_4 == 3) {
                                this.removeMessages();
                                this.dismissPopupKeyboard();
                                this.mAbortKey = true;
                                this.showPreview(-1);
                                this.invalidateKey(this.mCurrentKey);
                            }
                        } else {
                            if (var9_8 != -1) {
                                var4_4 = this.mCurrentKey;
                                if (var4_4 == -1) {
                                    this.mCurrentKey = var9_8;
                                    this.mCurrentKeyTime = var7_7 - this.mDownTime;
                                    var4_4 = 0;
                                } else if (var9_8 == var4_4) {
                                    this.mCurrentKeyTime += var7_7 - this.mLastMoveTime;
                                    var4_4 = 1;
                                } else {
                                    var4_4 = var5_5 = 0;
                                    if (this.mRepeatKeyIndex == -1) {
                                        this.resetMultiTap();
                                        this.mLastKey = this.mCurrentKey;
                                        this.mLastCodeX = this.mLastX;
                                        this.mLastCodeY = this.mLastY;
                                        this.mLastKeyTime = this.mCurrentKeyTime + var7_7 - this.mLastMoveTime;
                                        this.mCurrentKey = var9_8;
                                        this.mCurrentKeyTime = 0L;
                                        var4_4 = var5_5;
                                    }
                                }
                            } else {
                                var4_4 = 0;
                            }
                            if (var4_4 == 0) {
                                this.mHandler.removeMessages(4);
                                if (var9_8 != -1) {
                                    var1_1 = this.mHandler.obtainMessage(4, var1_1);
                                    this.mHandler.sendMessageDelayed((Message)var1_1, KeyboardView.LONGPRESS_TIMEOUT);
                                }
                            }
                            this.showPreview(this.mCurrentKey);
                            this.mLastMoveTime = var7_7;
                        }
                        break block28;
                    }
                    this.removeMessages();
                    if (var9_8 == this.mCurrentKey) {
                        this.mCurrentKeyTime += var7_7 - this.mLastMoveTime;
                    } else {
                        this.resetMultiTap();
                        this.mLastKey = this.mCurrentKey;
                        this.mLastKeyTime = this.mCurrentKeyTime + var7_7 - this.mLastMoveTime;
                        this.mCurrentKey = var9_8;
                        this.mCurrentKeyTime = 0L;
                    }
                    var10_9 = this.mCurrentKeyTime;
                    if (var10_9 < this.mLastKeyTime && var10_9 < 70L && (var4_4 = this.mLastKey) != -1) {
                        this.mCurrentKey = var4_4;
                        var4_4 = this.mLastCodeX;
                        var6_6 = this.mLastCodeY;
                    } else {
                        var4_4 = var3_3;
                    }
                    this.showPreview(-1);
                    Arrays.fill(this.mKeyIndices, -1);
                    if (this.mRepeatKeyIndex == -1 && !this.mMiniKeyboardOnScreen && !this.mAbortKey) {
                        this.detectAndSendKey(this.mCurrentKey, var4_4, var6_6, var7_7);
                    }
                    this.invalidateKey(var9_8);
                    this.mRepeatKeyIndex = -1;
                    break block29;
                }
                var4_4 = 0;
                this.mAbortKey = false;
                this.mStartX = var3_3;
                this.mStartY = var6_6;
                this.mLastCodeX = var3_3;
                this.mLastCodeY = var6_6;
                this.mLastKeyTime = 0L;
                this.mCurrentKeyTime = 0L;
                this.mLastKey = -1;
                this.mCurrentKey = var9_8;
                this.mDownKey = var9_8;
                this.mLastMoveTime = this.mDownTime = var1_1.getEventTime();
                this.checkMultiTap(var7_7, var9_8);
                var12_10 = this.mKeyboardActionListener;
                if (var9_8 != -1) {
                    var4_4 = this.mKeys[var9_8].codes[0];
                }
                var12_10.onPress(var4_4);
                var4_4 = this.mCurrentKey;
                if (var4_4 < 0 || !this.mKeys[var4_4].repeatable) ** GOTO lbl-1000
                this.mRepeatKeyIndex = this.mCurrentKey;
                var12_10 = this.mHandler.obtainMessage(3);
                this.mHandler.sendMessageDelayed((Message)var12_10, 400L);
                this.repeatKey();
                if (this.mAbortKey) {
                    this.mRepeatKeyIndex = -1;
                } else lbl-1000: // 2 sources:
                {
                    if (this.mCurrentKey != -1) {
                        var1_1 = this.mHandler.obtainMessage(4, var1_1);
                        this.mHandler.sendMessageDelayed((Message)var1_1, KeyboardView.LONGPRESS_TIMEOUT);
                    }
                    this.showPreview(var9_8);
                }
            }
            var4_4 = var3_3;
        }
        this.mLastX = var4_4;
        this.mLastY = var6_6;
        return true;
    }

    @UnsupportedAppUsage
    private boolean openPopupIfRequired(MotionEvent arrkey) {
        if (this.mPopupLayout == 0) {
            return false;
        }
        int n = this.mCurrentKey;
        if (n >= 0 && n < (arrkey = this.mKeys).length) {
            boolean bl = this.onLongPress(arrkey[n]);
            if (bl) {
                this.mAbortKey = true;
                this.showPreview(-1);
            }
            return bl;
        }
        return false;
    }

    private void removeMessages() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler.removeMessages(1);
        }
    }

    @UnsupportedAppUsage
    private boolean repeatKey() {
        Keyboard.Key key = this.mKeys[this.mRepeatKeyIndex];
        this.detectAndSendKey(this.mCurrentKey, key.x, key.y, this.mLastTapTime);
        return true;
    }

    private void resetMultiTap() {
        this.mLastSentIndex = -1;
        this.mTapCount = 0;
        this.mLastTapTime = -1L;
        this.mInMultiTap = false;
    }

    private void sendAccessibilityEventForUnicodeCharacter(int n, int n2) {
        if (this.mAccessibilityManager.isEnabled()) {
            String string2;
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(n);
            this.onInitializeAccessibilityEvent(accessibilityEvent);
            if (n2 != 10) {
                switch (n2) {
                    default: {
                        string2 = String.valueOf((char)n2);
                        break;
                    }
                    case -1: {
                        string2 = this.mContext.getString(17040157);
                        break;
                    }
                    case -2: {
                        string2 = this.mContext.getString(17040156);
                        break;
                    }
                    case -3: {
                        string2 = this.mContext.getString(17040152);
                        break;
                    }
                    case -4: {
                        string2 = this.mContext.getString(17040154);
                        break;
                    }
                    case -5: {
                        string2 = this.mContext.getString(17040153);
                        break;
                    }
                    case -6: {
                        string2 = this.mContext.getString(17040151);
                        break;
                    }
                }
            } else {
                string2 = this.mContext.getString(17040155);
            }
            accessibilityEvent.getText().add(string2);
            this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        }
    }

    @UnsupportedAppUsage
    private void showKey(int n) {
        PopupWindow popupWindow = this.mPreviewPopup;
        Object object = this.mKeys;
        if (n >= 0 && n < this.mKeys.length) {
            Object object2;
            Keyboard.Key key = object[n];
            if (key.icon != null) {
                object2 = this.mPreviewText;
                object = key.iconPreview != null ? key.iconPreview : key.icon;
                ((TextView)object2).setCompoundDrawables(null, null, null, (Drawable)object);
                this.mPreviewText.setText(null);
            } else {
                this.mPreviewText.setCompoundDrawables(null, null, null, null);
                this.mPreviewText.setText(this.getPreviewText(key));
                if (key.label.length() > 1 && key.codes.length < 2) {
                    this.mPreviewText.setTextSize(0, this.mKeyTextSize);
                    this.mPreviewText.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    this.mPreviewText.setTextSize(0, this.mPreviewTextSizeLarge);
                    this.mPreviewText.setTypeface(Typeface.DEFAULT);
                }
            }
            this.mPreviewText.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            n = Math.max(this.mPreviewText.getMeasuredWidth(), key.width + this.mPreviewText.getPaddingLeft() + this.mPreviewText.getPaddingRight());
            int n2 = this.mPreviewHeight;
            object = this.mPreviewText.getLayoutParams();
            if (object != null) {
                object.width = n;
                object.height = n2;
            }
            if (!this.mPreviewCentered) {
                this.mPopupPreviewX = key.x - this.mPreviewText.getPaddingLeft() + this.mPaddingLeft;
                this.mPopupPreviewY = key.y - n2 + this.mPreviewOffset;
            } else {
                this.mPopupPreviewX = 160 - this.mPreviewText.getMeasuredWidth() / 2;
                this.mPopupPreviewY = -this.mPreviewText.getMeasuredHeight();
            }
            this.mHandler.removeMessages(2);
            this.getLocationInWindow(this.mCoordinates);
            object = this.mCoordinates;
            object[0] = object[0] + this.mMiniKeyboardOffsetX;
            object[1] = object[1] + this.mMiniKeyboardOffsetY;
            object2 = this.mPreviewText.getBackground();
            object = key.popupResId != 0 ? LONG_PRESSABLE_STATE_SET : EMPTY_STATE_SET;
            ((Drawable)object2).setState((int[])object);
            int n3 = this.mPopupPreviewX;
            object = this.mCoordinates;
            this.mPopupPreviewX = n3 + object[0];
            this.mPopupPreviewY += object[1];
            this.getLocationOnScreen((int[])object);
            if (this.mPopupPreviewY + this.mCoordinates[1] < 0) {
                this.mPopupPreviewX = key.x + key.width <= this.getWidth() / 2 ? (this.mPopupPreviewX += (int)((double)key.width * 2.5)) : (this.mPopupPreviewX -= (int)((double)key.width * 2.5));
                this.mPopupPreviewY += n2;
            }
            if (popupWindow.isShowing()) {
                popupWindow.update(this.mPopupPreviewX, this.mPopupPreviewY, n, n2);
            } else {
                popupWindow.setWidth(n);
                popupWindow.setHeight(n2);
                popupWindow.showAtLocation(this.mPopupParent, 0, this.mPopupPreviewX, this.mPopupPreviewY);
            }
            this.mPreviewText.setVisibility(0);
            return;
        }
    }

    private void showPreview(int n) {
        Object object;
        int n2 = this.mCurrentKeyIndex;
        Object object2 = this.mPreviewPopup;
        this.mCurrentKeyIndex = n;
        Keyboard.Key[] arrkey = this.mKeys;
        int n3 = this.mCurrentKeyIndex;
        if (n2 != n3) {
            if (n2 != -1 && arrkey.length > n2) {
                object = arrkey[n2];
                boolean bl = n3 == -1;
                ((Keyboard.Key)object).onReleased(bl);
                this.invalidateKey(n2);
                n3 = ((Keyboard.Key)object).codes[0];
                this.sendAccessibilityEventForUnicodeCharacter(256, n3);
                this.sendAccessibilityEventForUnicodeCharacter(65536, n3);
            }
            if ((n3 = this.mCurrentKeyIndex) != -1 && arrkey.length > n3) {
                object = arrkey[n3];
                ((Keyboard.Key)object).onPressed();
                this.invalidateKey(this.mCurrentKeyIndex);
                n3 = ((Keyboard.Key)object).codes[0];
                this.sendAccessibilityEventForUnicodeCharacter(128, n3);
                this.sendAccessibilityEventForUnicodeCharacter(32768, n3);
            }
        }
        if (n2 != this.mCurrentKeyIndex && this.mShowPreview) {
            this.mHandler.removeMessages(1);
            if (((PopupWindow)object2).isShowing() && n == -1) {
                object = this.mHandler;
                ((Handler)object).sendMessageDelayed(((Handler)object).obtainMessage(2), 70L);
            }
            if (n != -1) {
                if (((PopupWindow)object2).isShowing() && this.mPreviewText.getVisibility() == 0) {
                    this.showKey(n);
                } else {
                    object2 = this.mHandler;
                    ((Handler)object2).sendMessageDelayed(((Handler)object2).obtainMessage(1, n, 0), 0L);
                }
            }
        }
    }

    public void closing() {
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
        this.removeMessages();
        this.dismissPopupKeyboard();
        this.mBuffer = null;
        this.mCanvas = null;
        this.mMiniKeyboardCache.clear();
    }

    public Keyboard getKeyboard() {
        return this.mKeyboard;
    }

    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return this.mKeyboardActionListener;
    }

    public boolean handleBack() {
        if (this.mPopupKeyboard.isShowing()) {
            this.dismissPopupKeyboard();
            return true;
        }
        return false;
    }

    public void invalidateAllKeys() {
        this.mDirtyRect.union(0, 0, this.getWidth(), this.getHeight());
        this.mDrawPending = true;
        this.invalidate();
    }

    public void invalidateKey(int n) {
        Object object = this.mKeys;
        if (object == null) {
            return;
        }
        if (n >= 0 && n < ((Keyboard.Key[])object).length) {
            this.mInvalidatedKey = object = object[n];
            this.mDirtyRect.union(object.x + this.mPaddingLeft, object.y + this.mPaddingTop, object.x + object.width + this.mPaddingLeft, object.y + object.height + this.mPaddingTop);
            this.onBufferDraw();
            this.invalidate(object.x + this.mPaddingLeft, object.y + this.mPaddingTop, object.x + object.width + this.mPaddingLeft, object.y + object.height + this.mPaddingTop);
            return;
        }
    }

    public boolean isPreviewEnabled() {
        return this.mShowPreview;
    }

    public boolean isProximityCorrectionEnabled() {
        return this.mProximityCorrectOn;
    }

    public boolean isShifted() {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard != null) {
            return keyboard.isShifted();
        }
        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.initGestureDetector();
        if (this.mHandler == null) {
            this.mHandler = new Handler(){

                @Override
                public void handleMessage(Message message) {
                    int n = message.what;
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                if (n == 4) {
                                    KeyboardView.this.openPopupIfRequired((MotionEvent)message.obj);
                                }
                            } else if (KeyboardView.this.repeatKey()) {
                                this.sendMessageDelayed(Message.obtain((Handler)this, 3), 50L);
                            }
                        } else {
                            KeyboardView.this.mPreviewText.setVisibility(4);
                        }
                    } else {
                        KeyboardView.this.showKey(message.arg1);
                    }
                }
            };
        }
    }

    @Override
    public void onClick(View view) {
        this.dismissPopupKeyboard();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.closing();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawPending || this.mBuffer == null || this.mKeyboardChanged) {
            this.onBufferDraw();
        }
        canvas.drawBitmap(this.mBuffer, 0.0f, 0.0f, null);
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (this.mAccessibilityManager.isTouchExplorationEnabled() && motionEvent.getPointerCount() == 1) {
            int n = motionEvent.getAction();
            if (n != 7) {
                if (n != 9) {
                    if (n == 10) {
                        motionEvent.setAction(1);
                    }
                } else {
                    motionEvent.setAction(0);
                }
            } else {
                motionEvent.setAction(2);
            }
            return this.onTouchEvent(motionEvent);
        }
        return true;
    }

    protected boolean onLongPress(Keyboard.Key object) {
        int n = ((Keyboard.Key)object).popupResId;
        if (n != 0) {
            this.mMiniKeyboardContainer = this.mMiniKeyboardCache.get(object);
            Object object2 = this.mMiniKeyboardContainer;
            if (object2 == null) {
                this.mMiniKeyboardContainer = ((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(this.mPopupLayout, null);
                this.mMiniKeyboard = (KeyboardView)this.mMiniKeyboardContainer.findViewById(16908326);
                object2 = this.mMiniKeyboardContainer.findViewById(16908327);
                if (object2 != null) {
                    ((View)object2).setOnClickListener(this);
                }
                this.mMiniKeyboard.setOnKeyboardActionListener(new OnKeyboardActionListener(){

                    @Override
                    public void onKey(int n, int[] arrn) {
                        KeyboardView.this.mKeyboardActionListener.onKey(n, arrn);
                        KeyboardView.this.dismissPopupKeyboard();
                    }

                    @Override
                    public void onPress(int n) {
                        KeyboardView.this.mKeyboardActionListener.onPress(n);
                    }

                    @Override
                    public void onRelease(int n) {
                        KeyboardView.this.mKeyboardActionListener.onRelease(n);
                    }

                    @Override
                    public void onText(CharSequence charSequence) {
                        KeyboardView.this.mKeyboardActionListener.onText(charSequence);
                        KeyboardView.this.dismissPopupKeyboard();
                    }

                    @Override
                    public void swipeDown() {
                    }

                    @Override
                    public void swipeLeft() {
                    }

                    @Override
                    public void swipeRight() {
                    }

                    @Override
                    public void swipeUp() {
                    }
                });
                object2 = ((Keyboard.Key)object).popupCharacters != null ? new Keyboard(this.getContext(), n, ((Keyboard.Key)object).popupCharacters, -1, this.getPaddingLeft() + this.getPaddingRight()) : new Keyboard(this.getContext(), n);
                this.mMiniKeyboard.setKeyboard((Keyboard)object2);
                this.mMiniKeyboard.setPopupParent(this);
                this.mMiniKeyboardContainer.measure(View.MeasureSpec.makeMeasureSpec(this.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(this.getHeight(), Integer.MIN_VALUE));
                this.mMiniKeyboardCache.put((Keyboard.Key)object, this.mMiniKeyboardContainer);
            } else {
                this.mMiniKeyboard = (KeyboardView)((View)object2).findViewById(16908326);
            }
            this.getLocationInWindow(this.mCoordinates);
            this.mPopupX = ((Keyboard.Key)object).x + this.mPaddingLeft;
            this.mPopupY = ((Keyboard.Key)object).y + this.mPaddingTop;
            this.mPopupX = this.mPopupX + ((Keyboard.Key)object).width - this.mMiniKeyboardContainer.getMeasuredWidth();
            this.mPopupY -= this.mMiniKeyboardContainer.getMeasuredHeight();
            int n2 = this.mPopupX + this.mMiniKeyboardContainer.getPaddingRight() + this.mCoordinates[0];
            int n3 = this.mPopupY + this.mMiniKeyboardContainer.getPaddingBottom() + this.mCoordinates[1];
            object = this.mMiniKeyboard;
            n = n2 < 0 ? 0 : n2;
            ((KeyboardView)object).setPopupOffset(n, n3);
            this.mMiniKeyboard.setShifted(this.isShifted());
            this.mPopupKeyboard.setContentView(this.mMiniKeyboardContainer);
            this.mPopupKeyboard.setWidth(this.mMiniKeyboardContainer.getMeasuredWidth());
            this.mPopupKeyboard.setHeight(this.mMiniKeyboardContainer.getMeasuredHeight());
            this.mPopupKeyboard.showAtLocation(this, 0, n2, n3);
            this.mMiniKeyboardOnScreen = true;
            this.invalidateAllKeys();
            return true;
        }
        return false;
    }

    @Override
    public void onMeasure(int n, int n2) {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard == null) {
            this.setMeasuredDimension(this.mPaddingLeft + this.mPaddingRight, this.mPaddingTop + this.mPaddingBottom);
        } else {
            int n3;
            n2 = n3 = keyboard.getMinWidth() + this.mPaddingLeft + this.mPaddingRight;
            if (View.MeasureSpec.getSize(n) < n3 + 10) {
                n2 = View.MeasureSpec.getSize(n);
            }
            this.setMeasuredDimension(n2, this.mKeyboard.getHeight() + this.mPaddingTop + this.mPaddingBottom);
        }
    }

    @Override
    public void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        Keyboard keyboard = this.mKeyboard;
        if (keyboard != null) {
            keyboard.resize(n, n2);
        }
        this.mBuffer = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        int n = motionEvent.getPointerCount();
        int n2 = motionEvent.getAction();
        long l = motionEvent.getEventTime();
        if (n != this.mOldPointerCount) {
            if (n == 1) {
                MotionEvent motionEvent2 = MotionEvent.obtain(l, l, 0, motionEvent.getX(), motionEvent.getY(), motionEvent.getMetaState());
                bl = this.onModifiedTouchEvent(motionEvent2, false);
                motionEvent2.recycle();
                if (n2 == 1) {
                    bl = this.onModifiedTouchEvent(motionEvent, true);
                }
            } else {
                motionEvent = MotionEvent.obtain(l, l, 1, this.mOldPointerX, this.mOldPointerY, motionEvent.getMetaState());
                bl = this.onModifiedTouchEvent(motionEvent, true);
                motionEvent.recycle();
            }
        } else if (n == 1) {
            bl = this.onModifiedTouchEvent(motionEvent, false);
            this.mOldPointerX = motionEvent.getX();
            this.mOldPointerY = motionEvent.getY();
        } else {
            bl = true;
        }
        this.mOldPointerCount = n;
        return bl;
    }

    public void setKeyboard(Keyboard keyboard) {
        if (this.mKeyboard != null) {
            this.showPreview(-1);
        }
        this.removeMessages();
        this.mKeyboard = keyboard;
        List<Keyboard.Key> list = this.mKeyboard.getKeys();
        this.mKeys = list.toArray(new Keyboard.Key[list.size()]);
        this.requestLayout();
        this.mKeyboardChanged = true;
        this.invalidateAllKeys();
        this.computeProximityThreshold(keyboard);
        this.mMiniKeyboardCache.clear();
        this.mAbortKey = true;
    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener onKeyboardActionListener) {
        this.mKeyboardActionListener = onKeyboardActionListener;
    }

    public void setPopupOffset(int n, int n2) {
        this.mMiniKeyboardOffsetX = n;
        this.mMiniKeyboardOffsetY = n2;
        if (this.mPreviewPopup.isShowing()) {
            this.mPreviewPopup.dismiss();
        }
    }

    public void setPopupParent(View view) {
        this.mPopupParent = view;
    }

    public void setPreviewEnabled(boolean bl) {
        this.mShowPreview = bl;
    }

    public void setProximityCorrectionEnabled(boolean bl) {
        this.mProximityCorrectOn = bl;
    }

    public boolean setShifted(boolean bl) {
        Keyboard keyboard = this.mKeyboard;
        if (keyboard != null && keyboard.setShifted(bl)) {
            this.invalidateAllKeys();
            return true;
        }
        return false;
    }

    public void setVerticalCorrection(int n) {
    }

    protected void swipeDown() {
        this.mKeyboardActionListener.swipeDown();
    }

    protected void swipeLeft() {
        this.mKeyboardActionListener.swipeLeft();
    }

    protected void swipeRight() {
        this.mKeyboardActionListener.swipeRight();
    }

    protected void swipeUp() {
        this.mKeyboardActionListener.swipeUp();
    }

    public static interface OnKeyboardActionListener {
        public void onKey(int var1, int[] var2);

        public void onPress(int var1);

        public void onRelease(int var1);

        public void onText(CharSequence var1);

        public void swipeDown();

        public void swipeLeft();

        public void swipeRight();

        public void swipeUp();
    }

    private static class SwipeTracker {
        static final int LONGEST_PAST_TIME = 200;
        static final int NUM_PAST = 4;
        final long[] mPastTime = new long[4];
        final float[] mPastX = new float[4];
        final float[] mPastY = new float[4];
        float mXVelocity;
        float mYVelocity;

        private SwipeTracker() {
        }

        private void addPoint(float f, float f2, long l) {
            int n;
            int n2 = -1;
            long[] arrl = this.mPastTime;
            for (n = 0; n < 4 && arrl[n] != 0L; ++n) {
                if (arrl[n] >= l - 200L) continue;
                n2 = n;
            }
            int n3 = n2;
            if (n == 4) {
                n3 = n2;
                if (n2 < 0) {
                    n3 = 0;
                }
            }
            n2 = n3;
            if (n3 == n) {
                n2 = n3 - 1;
            }
            float[] arrf = this.mPastX;
            float[] arrf2 = this.mPastY;
            n3 = n;
            if (n2 >= 0) {
                n3 = n2 + 1;
                int n4 = 4 - n2 - 1;
                System.arraycopy(arrf, n3, arrf, 0, n4);
                System.arraycopy(arrf2, n3, arrf2, 0, n4);
                System.arraycopy(arrl, n3, arrl, 0, n4);
                n3 = n - (n2 + 1);
            }
            arrf[n3] = f;
            arrf2[n3] = f2;
            arrl[n3] = l;
            n = n3 + 1;
            if (n < 4) {
                arrl[n] = 0L;
            }
        }

        public void addMovement(MotionEvent motionEvent) {
            long l = motionEvent.getEventTime();
            int n = motionEvent.getHistorySize();
            for (int i = 0; i < n; ++i) {
                this.addPoint(motionEvent.getHistoricalX(i), motionEvent.getHistoricalY(i), motionEvent.getHistoricalEventTime(i));
            }
            this.addPoint(motionEvent.getX(), motionEvent.getY(), l);
        }

        public void clear() {
            this.mPastTime[0] = 0L;
        }

        public void computeCurrentVelocity(int n) {
            this.computeCurrentVelocity(n, Float.MAX_VALUE);
        }

        public void computeCurrentVelocity(int n, float f) {
            int n2;
            float[] arrf = this.mPastX;
            float[] arrf2 = this.mPastY;
            long[] arrl = this.mPastTime;
            float f2 = arrf[0];
            float f3 = arrf2[0];
            long l = arrl[0];
            float f4 = 0.0f;
            float f5 = 0.0f;
            for (n2 = 0; n2 < 4 && arrl[n2] != 0L; ++n2) {
            }
            for (int i = 1; i < n2; ++i) {
                int n3 = (int)(arrl[i] - l);
                if (n3 == 0) continue;
                float f6 = (arrf[i] - f2) / (float)n3 * (float)n;
                f4 = f4 == 0.0f ? f6 : (f4 + f6) * 0.5f;
                f6 = (arrf2[i] - f3) / (float)n3 * (float)n;
                f5 = f5 == 0.0f ? f6 : (f5 + f6) * 0.5f;
            }
            f4 = f4 < 0.0f ? Math.max(f4, -f) : Math.min(f4, f);
            this.mXVelocity = f4;
            f = f5 < 0.0f ? Math.max(f5, -f) : Math.min(f5, f);
            this.mYVelocity = f;
        }

        public float getXVelocity() {
            return this.mXVelocity;
        }

        public float getYVelocity() {
            return this.mYVelocity;
        }
    }

}

