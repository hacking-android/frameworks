/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DecimalFormatSymbols
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$TextView
 *  android.widget.-$$Lambda$TextView$jQz3_DIfGrNeNdu_95_wi6UkW4E
 *  libcore.util.EmptyArray
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.widget;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.UndoManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BaseCanvas;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.AsyncTask;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableParcel;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.BoringLayout;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.GetChars;
import android.text.GraphicsOperations;
import android.text.InputFilter;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.PrecomputedText;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.AllCapsTransformationMethod;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.DateKeyListener;
import android.text.method.DateTimeKeyListener;
import android.text.method.DialerKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.LinkMovementMethod;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TextKeyListener;
import android.text.method.TimeKeyListener;
import android.text.method.TransformationMethod;
import android.text.method.TransformationMethod2;
import android.text.method.WordIterator;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ParagraphStyle;
import android.text.style.SpellCheckSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.URLSpan;
import android.text.style.UpdateAppearance;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import android.view.textservice.SpellCheckerSubtype;
import android.view.textservice.TextServicesManager;
import android.widget.-$;
import android.widget.AccessibilityIterators;
import android.widget.Editor;
import android.widget.RemoteViews;
import android.widget.Scroller;
import android.widget.SpellChecker;
import android.widget.Toast;
import android.widget._$$Lambda$TextView$DJlzb7VS7J_1890Kto7GAApQDN0;
import android.widget._$$Lambda$TextView$jQz3_DIfGrNeNdu_95_wi6UkW4E;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FastMath;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.EditableInputConnection;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParserException;

@RemoteViews.RemoteView
public class TextView
extends View
implements ViewTreeObserver.OnPreDrawListener {
    static final int ACCESSIBILITY_ACTION_PROCESS_TEXT_START_ID = 268435712;
    private static final int ACCESSIBILITY_ACTION_SHARE = 268435456;
    private static final int ANIMATED_SCROLL_GAP = 250;
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int CHANGE_WATCHER_PRIORITY = 100;
    static final boolean DEBUG_EXTRACT = false;
    private static final int DECIMAL = 4;
    private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
    private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
    private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
    private static final int DEFAULT_TYPEFACE = -1;
    private static final int DEVICE_PROVISIONED_NO = 1;
    private static final int DEVICE_PROVISIONED_UNKNOWN = 0;
    private static final int DEVICE_PROVISIONED_YES = 2;
    private static final int ELLIPSIZE_END = 3;
    private static final int ELLIPSIZE_MARQUEE = 4;
    private static final int ELLIPSIZE_MIDDLE = 2;
    private static final int ELLIPSIZE_NONE = 0;
    private static final int ELLIPSIZE_NOT_SET = -1;
    private static final int ELLIPSIZE_START = 1;
    private static final Spanned EMPTY_SPANNED;
    private static final int EMS = 1;
    private static final int FLOATING_TOOLBAR_SELECT_ALL_REFRESH_DELAY = 500;
    static final int ID_ASSIST = 16908353;
    static final int ID_AUTOFILL = 16908355;
    static final int ID_COPY = 16908321;
    static final int ID_CUT = 16908320;
    static final int ID_PASTE = 16908322;
    static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    static final int ID_REDO = 16908339;
    static final int ID_REPLACE = 16908340;
    static final int ID_SELECT_ALL = 16908319;
    static final int ID_SHARE = 16908341;
    static final int ID_UNDO = 16908338;
    private static final int KEY_DOWN_HANDLED_BY_KEY_LISTENER = 1;
    private static final int KEY_DOWN_HANDLED_BY_MOVEMENT_METHOD = 2;
    private static final int KEY_EVENT_HANDLED = -1;
    private static final int KEY_EVENT_NOT_HANDLED = 0;
    @UnsupportedAppUsage
    private static final int LINES = 1;
    static final String LOG_TAG = "TextView";
    private static final int MARQUEE_FADE_NORMAL = 0;
    private static final int MARQUEE_FADE_SWITCH_SHOW_ELLIPSIS = 1;
    private static final int MARQUEE_FADE_SWITCH_SHOW_FADE = 2;
    private static final int MONOSPACE = 3;
    private static final int[] MULTILINE_STATE_SET;
    private static final InputFilter[] NO_FILTERS;
    private static final int PIXELS = 2;
    static final int PROCESS_TEXT_REQUEST_CODE = 100;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int SIGNED = 2;
    private static final float[] TEMP_POSITION;
    private static final RectF TEMP_RECTF;
    @VisibleForTesting
    public static final BoringLayout.Metrics UNKNOWN_BORING;
    private static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f;
    static final int VERY_WIDE = 1048576;
    private static final SparseIntArray sAppearanceValues;
    static long sLastCutCopyOrTextChangedTime;
    @UnsupportedAppUsage
    private boolean mAllowTransformationLengthChange;
    private int mAutoLinkMask;
    private float mAutoSizeMaxTextSizeInPx;
    private float mAutoSizeMinTextSizeInPx;
    private float mAutoSizeStepGranularityInPx;
    private int[] mAutoSizeTextSizesInPx;
    private int mAutoSizeTextType;
    @UnsupportedAppUsage
    private BoringLayout.Metrics mBoring;
    private int mBreakStrategy;
    @UnsupportedAppUsage
    private BufferType mBufferType;
    @UnsupportedAppUsage
    private ChangeWatcher mChangeWatcher;
    @UnsupportedAppUsage
    private CharWrapper mCharWrapper;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mCurHintTextColor;
    @ViewDebug.ExportedProperty(category="text")
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mCurTextColor;
    private volatile Locale mCurrentSpellCheckerLocaleCache;
    private Drawable mCursorDrawable;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mCursorDrawableRes;
    private int mDeferScroll;
    @UnsupportedAppUsage
    private int mDesiredHeightAtMeasure;
    private int mDeviceProvisionedState;
    @UnsupportedAppUsage
    Drawables mDrawables;
    @UnsupportedAppUsage
    private Editable.Factory mEditableFactory;
    @UnsupportedAppUsage
    private Editor mEditor;
    private TextUtils.TruncateAt mEllipsize;
    private InputFilter[] mFilters;
    private boolean mFreezesText;
    @ViewDebug.ExportedProperty(category="text")
    @UnsupportedAppUsage
    private int mGravity;
    private boolean mHasPresetAutoSizeValues;
    @UnsupportedAppUsage
    int mHighlightColor;
    @UnsupportedAppUsage
    private final Paint mHighlightPaint;
    private Path mHighlightPath;
    @UnsupportedAppUsage
    private boolean mHighlightPathBogus;
    private CharSequence mHint;
    @UnsupportedAppUsage
    private BoringLayout.Metrics mHintBoring;
    @UnsupportedAppUsage
    private Layout mHintLayout;
    private ColorStateList mHintTextColor;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean mHorizontallyScrolling;
    private int mHyphenationFrequency;
    @UnsupportedAppUsage
    private boolean mIncludePad;
    private int mJustificationMode;
    private int mLastLayoutDirection;
    private long mLastScroll;
    @UnsupportedAppUsage
    private Layout mLayout;
    private ColorStateList mLinkTextColor;
    private boolean mLinksClickable;
    private boolean mListenerChanged;
    @UnsupportedAppUsage(trackingBug=123769451L)
    private ArrayList<TextWatcher> mListeners;
    private boolean mLocalesChanged;
    @UnsupportedAppUsage(trackingBug=124050217L)
    private Marquee mMarquee;
    @UnsupportedAppUsage
    private int mMarqueeFadeMode;
    private int mMarqueeRepeatLimit;
    @UnsupportedAppUsage
    private int mMaxMode;
    @UnsupportedAppUsage
    private int mMaxWidth;
    @UnsupportedAppUsage
    private int mMaxWidthMode;
    @UnsupportedAppUsage
    private int mMaximum;
    @UnsupportedAppUsage
    private int mMinMode;
    @UnsupportedAppUsage
    private int mMinWidth;
    @UnsupportedAppUsage
    private int mMinWidthMode;
    @UnsupportedAppUsage
    private int mMinimum;
    private MovementMethod mMovement;
    private boolean mNeedsAutoSizeText;
    @UnsupportedAppUsage
    private int mOldMaxMode;
    @UnsupportedAppUsage
    private int mOldMaximum;
    private boolean mPreDrawListenerDetached;
    private boolean mPreDrawRegistered;
    private PrecomputedText mPrecomputed;
    private boolean mPreventDefaultMovement;
    @UnsupportedAppUsage
    private boolean mRestartMarquee;
    @UnsupportedAppUsage
    private BoringLayout mSavedHintLayout;
    @UnsupportedAppUsage
    private BoringLayout mSavedLayout;
    @UnsupportedAppUsage
    private Layout mSavedMarqueeModeLayout;
    private Scroller mScroller;
    private int mShadowColor;
    @UnsupportedAppUsage
    private float mShadowDx;
    @UnsupportedAppUsage
    private float mShadowDy;
    @UnsupportedAppUsage
    private float mShadowRadius;
    @UnsupportedAppUsage
    private boolean mSingleLine;
    @UnsupportedAppUsage
    private float mSpacingAdd;
    @UnsupportedAppUsage
    private float mSpacingMult;
    private Spannable mSpannable;
    @UnsupportedAppUsage
    private Spannable.Factory mSpannableFactory;
    private Rect mTempRect;
    private TextPaint mTempTextPaint;
    @ViewDebug.ExportedProperty(category="text")
    @UnsupportedAppUsage
    private CharSequence mText;
    private TextClassificationContext mTextClassificationContext;
    private TextClassifier mTextClassificationSession;
    private TextClassifier mTextClassifier;
    private ColorStateList mTextColor;
    @UnsupportedAppUsage
    private TextDirectionHeuristic mTextDir;
    int mTextEditSuggestionContainerLayout;
    int mTextEditSuggestionHighlightStyle;
    int mTextEditSuggestionItemLayout;
    private int mTextId;
    private UserHandle mTextOperationUser;
    @UnsupportedAppUsage
    private final TextPaint mTextPaint;
    private Drawable mTextSelectHandle;
    private Drawable mTextSelectHandleLeft;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mTextSelectHandleLeftRes;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mTextSelectHandleRes;
    private Drawable mTextSelectHandleRight;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mTextSelectHandleRightRes;
    private boolean mTextSetFromXmlOrResourceId;
    private TransformationMethod mTransformation;
    @UnsupportedAppUsage
    private CharSequence mTransformed;
    boolean mUseFallbackLineSpacing;
    private final boolean mUseInternationalizedInput;
    @UnsupportedAppUsage
    private boolean mUserSetTextScaleX;

    static {
        TEMP_POSITION = new float[2];
        TEMP_RECTF = new RectF();
        NO_FILTERS = new InputFilter[0];
        EMPTY_SPANNED = new SpannedString("");
        MULTILINE_STATE_SET = new int[]{16843597};
        sAppearanceValues = new SparseIntArray();
        sAppearanceValues.put(6, 4);
        sAppearanceValues.put(5, 3);
        sAppearanceValues.put(7, 5);
        sAppearanceValues.put(8, 6);
        sAppearanceValues.put(2, 0);
        sAppearanceValues.put(96, 19);
        sAppearanceValues.put(3, 1);
        sAppearanceValues.put(75, 12);
        sAppearanceValues.put(4, 2);
        sAppearanceValues.put(95, 18);
        sAppearanceValues.put(72, 11);
        sAppearanceValues.put(36, 7);
        sAppearanceValues.put(37, 8);
        sAppearanceValues.put(38, 9);
        sAppearanceValues.put(39, 10);
        sAppearanceValues.put(76, 13);
        sAppearanceValues.put(91, 17);
        sAppearanceValues.put(77, 14);
        sAppearanceValues.put(78, 15);
        sAppearanceValues.put(90, 16);
        UNKNOWN_BORING = new BoringLayout.Metrics();
    }

    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public TextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public TextView(Context object, AttributeSet object2, int n, int n2) {
        block173 : {
            block172 : {
                string2 = "TextView";
                super((Context)object, (AttributeSet)object2, n, n2);
                this.mEditableFactory = Editable.Factory.getInstance();
                this.mSpannableFactory = Spannable.Factory.getInstance();
                this.mMarqueeRepeatLimit = 3;
                this.mLastLayoutDirection = -1;
                this.mMarqueeFadeMode = 0;
                this.mBufferType = BufferType.NORMAL;
                this.mLocalesChanged = false;
                this.mListenerChanged = false;
                this.mGravity = 8388659;
                this.mLinksClickable = true;
                this.mSpacingMult = 1.0f;
                this.mSpacingAdd = 0.0f;
                this.mMaximum = Integer.MAX_VALUE;
                this.mMaxMode = 1;
                this.mMinimum = 0;
                this.mMinMode = 1;
                this.mOldMaximum = this.mMaximum;
                this.mOldMaxMode = this.mMaxMode;
                this.mMaxWidth = Integer.MAX_VALUE;
                this.mMaxWidthMode = 2;
                this.mMinWidth = 0;
                this.mMinWidthMode = 2;
                this.mDesiredHeightAtMeasure = -1;
                this.mIncludePad = true;
                this.mDeferScroll = -1;
                this.mFilters = TextView.NO_FILTERS;
                this.mHighlightColor = 1714664933;
                this.mHighlightPathBogus = true;
                this.mDeviceProvisionedState = 0;
                this.mAutoSizeTextType = 0;
                this.mNeedsAutoSizeText = false;
                this.mAutoSizeStepGranularityInPx = -1.0f;
                this.mAutoSizeMinTextSizeInPx = -1.0f;
                this.mAutoSizeMaxTextSizeInPx = -1.0f;
                this.mAutoSizeTextSizesInPx = EmptyArray.INT;
                this.mHasPresetAutoSizeValues = false;
                this.mTextSetFromXmlOrResourceId = false;
                this.mTextId = 0;
                if (this.getImportantForAutofill() == 0) {
                    this.setImportantForAutofill(1);
                }
                this.setTextInternal("");
                resources = this.getResources();
                compatibilityInfo = resources.getCompatibilityInfo();
                this.mTextPaint = new TextPaint(1);
                this.mTextPaint.density = resources.getDisplayMetrics().density;
                this.mTextPaint.setCompatibilityScaling(compatibilityInfo.applicationScale);
                this.mHighlightPaint = new Paint(1);
                this.mHighlightPaint.setCompatibilityScaling(compatibilityInfo.applicationScale);
                this.mMovement = this.getDefaultMovementMethod();
                this.mTransformation = null;
                textAppearanceAttributes2 = new TextAppearanceAttributes();
                textAppearanceAttributes2.mTextColor = ColorStateList.valueOf(-16777216);
                textAppearanceAttributes2.mTextSize = 15;
                this.mBreakStrategy = 0;
                this.mHyphenationFrequency = 0;
                this.mJustificationMode = 0;
                object4 = object.getTheme();
                object3 = object4.obtainStyledAttributes((AttributeSet)object2, R.styleable.TextViewAppearance, n, n2);
                arrn = R.styleable.TextViewAppearance;
                string3 = "Failure reading input extras";
                this.saveAttributeDataForStyleable((Context)object, arrn, (AttributeSet)object2, (TypedArray)object3, n, n2);
                n7 = object3.getResourceId(0, -1);
                object3.recycle();
                if (n7 != -1) {
                    typedArray = object4.obtainStyledAttributes(n7, R.styleable.TextAppearance);
                    this.saveAttributeDataForStyleable((Context)object, R.styleable.TextAppearance, null, typedArray, 0, n7);
                } else {
                    var7_32 = null;
                }
                if (var7_33 != null) {
                    object3 = textAppearanceAttributes2;
                    this.readTextAppearance((Context)object, (TypedArray)var7_33, (TextAppearanceAttributes)object3, false);
                    object3.mFontFamilyExplicit = false;
                    var7_33.recycle();
                }
                textAppearanceAttributes = textAppearanceAttributes2;
                bl9 = this.getDefaultEditable();
                f2 = -1.0f;
                f3 = -1.0f;
                f = -1.0f;
                typedArray = object4.obtainStyledAttributes((AttributeSet)object2, R.styleable.TextView, n, n2);
                this.saveAttributeDataForStyleable((Context)object, R.styleable.TextView, (AttributeSet)object2, typedArray, n, n2);
                this.readTextAppearance((Context)object, typedArray, textAppearanceAttributes, true);
                n14 = typedArray.getIndexCount();
                n5 = -1;
                n10 = -1;
                n4 = -1;
                bl8 = false;
                bl10 = false;
                bl7 = false;
                drawable3 = null;
                drawable4 = null;
                drawable2 = null;
                drawable5 = null;
                object4 = null;
                object3 = null;
                colorStateList = null;
                typedArray2 = null;
                n3 = 0;
                n7 = -1;
                bl6 = false;
                n6 = -1;
                bl4 = false;
                n13 = 0;
                object5 = null;
                n9 = 0;
                string4 = "";
                n12 = -1;
                var7_34 = null;
                charSequence = null;
                bl2 = false;
                n11 = 0;
                string5 = string3;
                typedArray3 = typedArray2;
                for (n8 = 0; n8 < n14; ++n8) {
                    n15 = typedArray.getIndex(n8);
                    if (n15 != 0) {
                        if (n15 != 67) {
                            if (n15 != 70) {
                                if (n15 != 71) {
                                    if (n15 != 73) {
                                        if (n15 != 74) {
                                            if (n15 != 97) {
                                                if (n15 != 98) {
                                                    switch (n15) {
                                                        default: {
                                                            switch (n15) {
                                                                default: {
                                                                    switch (n15) {
                                                                        default: {
                                                                            switch (n15) {
                                                                                default: {
                                                                                    ** break;
                                                                                }
                                                                                case 94: {
                                                                                    n4 = typedArray.getDimensionPixelSize(n15, -1);
                                                                                    ** break;
                                                                                }
                                                                                case 93: {
                                                                                    n10 = typedArray.getDimensionPixelSize(n15, -1);
                                                                                    ** break;
                                                                                }
                                                                                case 92: 
                                                                            }
                                                                            n5 = typedArray.getDimensionPixelSize(n15, -1);
                                                                            ** break;
                                                                        }
                                                                        case 89: {
                                                                            this.mJustificationMode = typedArray.getInt(n15, 0);
                                                                            ** break;
                                                                        }
                                                                        case 88: {
                                                                            f3 = typedArray.getDimension(n15, -1.0f);
                                                                            ** break;
                                                                        }
                                                                        case 87: {
                                                                            f2 = typedArray.getDimension(n15, -1.0f);
                                                                            ** break;
                                                                        }
                                                                        case 86: {
                                                                            n15 = typedArray.getResourceId(n15, 0);
                                                                            if (n15 <= 0) break;
                                                                            typedArray2 = typedArray.getResources().obtainTypedArray(n15);
                                                                            this.setupAutoSizeUniformPresetSizes(typedArray2);
                                                                            typedArray2.recycle();
                                                                            ** break;
                                                                        }
                                                                        case 85: {
                                                                            f = typedArray.getDimension(n15, -1.0f);
                                                                            ** break;
                                                                        }
                                                                        case 84: {
                                                                            this.mAutoSizeTextType = typedArray.getInt(n15, 0);
                                                                            ** break;
                                                                        }
                                                                        case 83: {
                                                                            this.createEditorIfNeeded();
                                                                            this.mEditor.mAllowUndo = typedArray.getBoolean(n15, true);
                                                                            ** break;
                                                                        }
                                                                        case 82: {
                                                                            this.mHyphenationFrequency = typedArray.getInt(n15, 0);
                                                                            ** break;
                                                                        }
                                                                        case 81: {
                                                                            this.mBreakStrategy = typedArray.getInt(n15, 0);
                                                                            ** break;
                                                                        }
                                                                        case 80: {
                                                                            blendMode = Drawable.parseBlendMode(typedArray.getInt(n15, -1), (BlendMode)var10_60);
                                                                            ** break;
                                                                        }
                                                                        case 79: {
                                                                            colorStateList = typedArray.getColorStateList(n15);
                                                                            ** break;
lbl181: // 15 sources:
                                                                            break;
                                                                        }
                                                                    }
                                                                    break;
                                                                }
                                                                case 64: {
                                                                    this.mTextSelectHandleRes = typedArray.getResourceId(n15, 0);
                                                                    ** break;
                                                                }
                                                                case 63: {
                                                                    this.mTextSelectHandleRightRes = typedArray.getResourceId(n15, 0);
                                                                    ** break;
                                                                }
                                                                case 62: {
                                                                    this.mTextSelectHandleLeftRes = typedArray.getResourceId(n15, 0);
                                                                    ** break;
                                                                }
                                                                case 61: {
                                                                    this.createEditorIfNeeded();
                                                                    this.mEditor.createInputContentTypeIfNeeded();
                                                                    this.mEditor.mInputContentType.imeActionId = typedArray.getInt(n15, this.mEditor.mInputContentType.imeActionId);
                                                                    ** break;
                                                                }
                                                                case 60: {
                                                                    this.createEditorIfNeeded();
                                                                    this.mEditor.createInputContentTypeIfNeeded();
                                                                    this.mEditor.mInputContentType.imeActionLabel = typedArray.getText(n15);
                                                                    ** break;
                                                                }
                                                                case 59: {
                                                                    this.createEditorIfNeeded();
                                                                    this.mEditor.createInputContentTypeIfNeeded();
                                                                    this.mEditor.mInputContentType.imeOptions = typedArray.getInt(n15, this.mEditor.mInputContentType.imeOptions);
                                                                    ** break;
                                                                }
                                                                case 58: {
                                                                    try {
                                                                        this.setInputExtras(typedArray.getResourceId(n15, 0));
                                                                    }
                                                                    catch (IOException iOException) {
                                                                        Log.w(string2, string5, iOException);
                                                                    }
                                                                    catch (XmlPullParserException xmlPullParserException) {
                                                                        Log.w(string2, string5, xmlPullParserException);
                                                                    }
                                                                    break;
                                                                }
                                                                case 57: {
                                                                    this.setPrivateImeOptions(typedArray.getString(n15));
                                                                    ** break;
                                                                }
                                                                case 56: {
                                                                    n13 = typedArray.getInt(n15, 0);
                                                                    ** break;
                                                                }
                                                                case 55: {
                                                                    this.setMarqueeRepeatLimit(typedArray.getInt(n15, this.mMarqueeRepeatLimit));
                                                                    ** break;
                                                                }
                                                                case 54: {
                                                                    this.mSpacingMult = typedArray.getFloat(n15, this.mSpacingMult);
                                                                    ** break;
                                                                }
                                                                case 53: {
                                                                    this.mSpacingAdd = typedArray.getDimensionPixelSize(n15, (int)this.mSpacingAdd);
                                                                    ** break;
                                                                }
                                                                case 52: {
                                                                    n3 = typedArray.getDimensionPixelSize(n15, n3);
                                                                    ** break;
                                                                }
                                                                case 51: {
                                                                    drawable2 = typedArray.getDrawable(n15);
                                                                    ** break;
                                                                }
                                                                case 50: {
                                                                    drawable3 = typedArray.getDrawable(n15);
                                                                    ** break;
                                                                }
                                                                case 49: {
                                                                    drawable5 = typedArray.getDrawable(n15);
                                                                    ** break;
                                                                }
                                                                case 48: {
                                                                    drawable4 = typedArray.getDrawable(n15);
                                                                    ** break;
                                                                }
                                                                case 47: {
                                                                    this.mFreezesText = typedArray.getBoolean(n15, false);
                                                                    ** break;
                                                                }
                                                                case 46: {
                                                                    bl9 = typedArray.getBoolean(n15, bl9);
                                                                    ** break;
                                                                }
                                                                case 45: {
                                                                    bl10 = typedArray.getBoolean(n15, bl10);
                                                                    ** break;
                                                                }
                                                                case 44: {
                                                                    n12 = typedArray.getInt(n15, n12);
                                                                    ** break;
                                                                }
                                                                case 43: {
                                                                    charSequence2 = typedArray.getText(n15);
                                                                    ** break;
                                                                }
                                                                case 42: {
                                                                    bl8 = typedArray.getBoolean(n15, bl8);
                                                                    ** break;
                                                                }
                                                                case 41: {
                                                                    object5 = typedArray.getText(n15);
                                                                    ** break;
                                                                }
                                                                case 40: {
                                                                    n11 = typedArray.getInt(n15, n11);
                                                                    ** break;
lbl273: // 24 sources:
                                                                    break;
                                                                }
                                                            }
                                                            break;
                                                        }
                                                        case 35: {
                                                            n6 = typedArray.getInt(n15, -1);
                                                            ** break;
                                                        }
                                                        case 34: {
                                                            if (typedArray.getBoolean(n15, true)) break;
                                                            this.setIncludeFontPadding(false);
                                                            ** break;
                                                        }
                                                        case 33: {
                                                            bl7 = typedArray.getBoolean(n15, bl7);
                                                            ** break;
                                                        }
                                                        case 32: {
                                                            bl6 = typedArray.getBoolean(n15, bl6);
                                                            ** break;
                                                        }
                                                        case 31: {
                                                            bl4 = typedArray.getBoolean(n15, bl4);
                                                            ** break;
                                                        }
                                                        case 30: {
                                                            if (!typedArray.getBoolean(n15, false)) break;
                                                            this.setHorizontallyScrolling(true);
                                                            ** break;
                                                        }
                                                        case 29: {
                                                            this.setMinEms(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 28: {
                                                            this.setWidth(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 27: {
                                                            this.setEms(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 26: {
                                                            this.setMaxEms(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 25: {
                                                            this.setMinLines(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 24: {
                                                            this.setHeight(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 23: {
                                                            this.setLines(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 22: {
                                                            this.setMaxLines(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 21: {
                                                            if (typedArray.getBoolean(n15, true)) break;
                                                            this.setCursorVisible(false);
                                                            ** break;
                                                        }
                                                        case 20: {
                                                            this.setTextScaleX(typedArray.getFloat(n15, 1.0f));
                                                            ** break;
                                                        }
                                                        case 19: {
                                                            charSequence = typedArray.getText(n15);
                                                            ** break;
                                                        }
                                                        case 18: {
                                                            bl2 = true;
                                                            this.mTextId = typedArray.getResourceId(n15, 0);
                                                            charSequence3 = typedArray.getText(n15);
                                                            ** break;
                                                        }
                                                        case 17: {
                                                            n9 = typedArray.getInt(n15, n9);
                                                            ** break;
                                                        }
                                                        case 16: {
                                                            this.setMinHeight(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 15: {
                                                            this.setMinWidth(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 14: {
                                                            this.setMaxHeight(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 13: {
                                                            this.setMaxWidth(typedArray.getDimensionPixelSize(n15, -1));
                                                            ** break;
                                                        }
                                                        case 12: {
                                                            this.mLinksClickable = typedArray.getBoolean(n15, true);
                                                            ** break;
                                                        }
                                                        case 11: {
                                                            this.mAutoLinkMask = typedArray.getInt(n15, 0);
                                                            ** break;
                                                        }
                                                        case 10: {
                                                            this.setGravity(typedArray.getInt(n15, -1));
                                                            ** break;
                                                        }
                                                        case 9: {
                                                            n7 = typedArray.getInt(n15, n7);
                                                            ** break;
lbl361: // 27 sources:
                                                            break;
                                                        }
                                                    }
                                                    continue;
                                                }
                                                this.mTextEditSuggestionHighlightStyle = typedArray.getResourceId(n15, 0);
                                                continue;
                                            }
                                            this.mTextEditSuggestionContainerLayout = typedArray.getResourceId(n15, 0);
                                            continue;
                                        }
                                        object3 = typedArray.getDrawable(n15);
                                        continue;
                                    }
                                    object4 = typedArray.getDrawable(n15);
                                    continue;
                                }
                                this.mTextEditSuggestionItemLayout = typedArray.getResourceId(n15, 0);
                                continue;
                            }
                            this.mCursorDrawableRes = typedArray.getResourceId(n15, 0);
                            continue;
                        }
                        this.setTextIsSelectable(typedArray.getBoolean(n15, false));
                        continue;
                    }
                    this.setEnabled(typedArray.getBoolean(n15, this.isEnabled()));
                }
                n8 = n7;
                typedArray.recycle();
                bufferType = BufferType.EDITABLE;
                n7 = n13 & 4095;
                bl = n7 == 129;
                bl5 = n7 == 225;
                bl3 = n7 == 18;
                n7 = object.getApplicationInfo().targetSdkVersion;
                bl11 = n7 >= 26;
                this.mUseInternationalizedInput = bl11;
                bl11 = n7 >= 28;
                this.mUseFallbackLineSpacing = bl11;
                if (var7_35 == null) break block172;
                try {
                    object5 = Class.forName(var7_35.toString());
                }
                catch (ClassNotFoundException classNotFoundException) {
                    throw new RuntimeException(classNotFoundException);
                }
                this.createEditorIfNeeded();
                editor2 = this.mEditor;
                object5 = object5.newInstance();
                editor2.mKeyListener = (KeyListener)object5;
                try {
                    editor3 = this.mEditor;
                    n7 = n13 != 0 ? n13 : this.mEditor.mKeyListener.getInputType();
                    editor3.mInputType = n7;
                }
                catch (IncompatibleClassChangeError incompatibleClassChangeError) {
                    this.mEditor.mInputType = 1;
                }
                break block173;
                catch (IllegalAccessException illegalAccessException) {
                    throw new RuntimeException((Throwable)var1_5);
                }
                catch (InstantiationException instantiationException) {
                    throw new RuntimeException((Throwable)var1_7);
                }
                catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
                throw new RuntimeException((Throwable)var1_5);
                catch (InstantiationException instantiationException) {
                    // empty catch block
                }
                throw new RuntimeException((Throwable)var1_7);
            }
            if (object5 != null) {
                this.createEditorIfNeeded();
                this.mEditor.mKeyListener = DigitsKeyListener.getInstance(object5.toString());
                editor4 = this.mEditor;
                n7 = n13 != 0 ? n13 : 1;
                editor4.mInputType = n7;
            } else if (n13 != 0) {
                this.setInputType(n13, true);
                bl6 = TextView.isMultilineInputType(n13) ^ true;
            } else if (bl8) {
                this.createEditorIfNeeded();
                this.mEditor.mKeyListener = DialerKeyListener.getInstance();
                this.mEditor.mInputType = 3;
            } else if (n11 != 0) {
                this.createEditorIfNeeded();
                editor5 = this.mEditor;
                bl10 = (n11 & 2) != 0;
                bl8 = (n11 & 4) != 0;
                editor5.mKeyListener = DigitsKeyListener.getInstance(null, bl10, bl8);
                this.mEditor.mInputType = n7 = this.mEditor.mKeyListener.getInputType();
            } else if (!bl10 && n12 == -1) {
                if (bl9) {
                    this.createEditorIfNeeded();
                    this.mEditor.mKeyListener = TextKeyListener.getInstance();
                    this.mEditor.mInputType = 1;
                } else if (this.isTextSelectable()) {
                    editor6 = this.mEditor;
                    if (editor6 != null) {
                        editor6.mKeyListener = null;
                        editor6.mInputType = 0;
                    }
                    bufferType = BufferType.SPANNABLE;
                    this.setMovementMethod(ArrowKeyMovementMethod.getInstance());
                } else {
                    editor7 = this.mEditor;
                    if (editor7 != null) {
                        editor7.mKeyListener = null;
                    }
                    if (n9 != 0) {
                        if (n9 != 1) {
                            if (n9 == 2) {
                                bufferType = BufferType.EDITABLE;
                            }
                        } else {
                            bufferType = BufferType.SPANNABLE;
                        }
                    } else {
                        bufferType = BufferType.NORMAL;
                    }
                }
            } else {
                n7 = 1;
                if (n12 != 1) {
                    if (n12 != 2) {
                        if (n12 != 3) {
                            capitalize = TextKeyListener.Capitalize.NONE;
                        } else {
                            capitalize = TextKeyListener.Capitalize.CHARACTERS;
                            n7 = 1 | 4096;
                        }
                    } else {
                        capitalize = TextKeyListener.Capitalize.WORDS;
                        n7 = 1 | 8192;
                    }
                } else {
                    capitalize = TextKeyListener.Capitalize.SENTENCES;
                    n7 = 1 | 16384;
                }
                this.createEditorIfNeeded();
                this.mEditor.mKeyListener = TextKeyListener.getInstance(bl10, (TextKeyListener.Capitalize)var7_49);
                this.mEditor.mInputType = n7;
            }
        }
        editor8 = this.mEditor;
        if (editor8 != null) {
            editor8.adjustInputType(bl4, bl, bl5, bl3);
        }
        if (bl7) {
            this.createEditorIfNeeded();
            this.mEditor.mSelectAllOnFocus = true;
            var7_52 = var6_22;
            if (var6_22 == BufferType.NORMAL) {
                bufferType = BufferType.SPANNABLE;
            }
        } else {
            var7_54 = var6_22;
        }
        if (colorStateList != null || var10_60 != null) {
            if (this.mDrawables == null) {
                this.mDrawables = new Drawables((Context)object);
            }
            if (colorStateList != null) {
                drawables = this.mDrawables;
                drawables.mTintList = colorStateList;
                drawables.mHasTint = true;
            }
            if (var10_60 != null) {
                drawables = this.mDrawables;
                drawables.mBlendMode = var10_60;
                drawables.mHasTintMode = true;
            }
        }
        this.setCompoundDrawablesWithIntrinsicBounds(drawable3, drawable4, drawable2, drawable5);
        this.setRelativeDrawablesIfNeeded((Drawable)object4, (Drawable)object3);
        this.setCompoundDrawablePadding(n3);
        this.setInputTypeSingleLine(bl6);
        this.applySingleLine(bl6, bl6, bl6);
        if (bl6 && this.getKeyListener() == null) {
            n7 = n8;
            if (n8 == -1) {
                n7 = 3;
            }
        } else {
            n7 = n8;
        }
        if (n7 != 1) {
            if (n7 != 2) {
                if (n7 != 3) {
                    if (n7 == 4) {
                        if (ViewConfiguration.get((Context)object).isFadingMarqueeEnabled()) {
                            this.setHorizontalFadingEdgeEnabled(true);
                            this.mMarqueeFadeMode = 0;
                        } else {
                            this.setHorizontalFadingEdgeEnabled(false);
                            this.mMarqueeFadeMode = 1;
                        }
                        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    }
                } else {
                    this.setEllipsize(TextUtils.TruncateAt.END);
                }
            } else {
                this.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            }
        } else {
            this.setEllipsize(TextUtils.TruncateAt.START);
        }
        n7 = !(bl4 || bl || bl5 || bl3) ? 0 : 1;
        n9 = n7 == 0 && ((editor = this.mEditor) == null || (editor.mInputType & 4095) != 129) ? 0 : 1;
        if (n9 != 0) {
            textAppearanceAttributes.mTypefaceIndex = 3;
        }
        this.applyTextAppearance(textAppearanceAttributes);
        if (n7 != 0) {
            this.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (n6 >= 0) {
            this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n6)});
        } else {
            this.setFilters(TextView.NO_FILTERS);
        }
        this.setText((CharSequence)var39_93, (BufferType)var7_55);
        if (this.mText == null) {
            this.mText = "";
        }
        if (this.mTransformed == null) {
            this.mTransformed = "";
        }
        if (bl2) {
            this.mTextSetFromXmlOrResourceId = true;
        }
        if (charSequence != null) {
            this.setHint(charSequence);
        }
        object = object.obtainStyledAttributes((AttributeSet)object2, R.styleable.View, n, n2);
        n = this.mMovement == null && this.getKeyListener() == null ? 0 : 1;
        bl6 = n != 0 || this.isClickable();
        bl4 = n != 0 || this.isLongClickable();
        n = this.getFocusable();
        n2 = object.getIndexCount();
        bl7 = bl6;
        bl6 = bl4;
        bl4 = bl7;
        for (n7 = 0; n7 < n2; ++n7) {
            n9 = object.getIndex(n7);
            if (n9 != 19) {
                if (n9 != 30) {
                    if (n9 != 31) continue;
                    bl6 = object.getBoolean(n9, bl6);
                    continue;
                }
                bl4 = object.getBoolean(n9, bl4);
                continue;
            }
            object2 = new TypedValue();
            if (!object.getValue(n9, (TypedValue)object2)) continue;
            if (object2.type == 18) {
                if (object2.data == 0) {
                    n = 0;
                    continue;
                }
                n = 1;
                continue;
            }
            n = object2.data;
        }
        object.recycle();
        if (n != this.getFocusable()) {
            this.setFocusable(n);
        }
        this.setClickable(bl4);
        this.setLongClickable(bl6);
        object = this.mEditor;
        if (object != null) {
            object.prepareCursorControllers();
        }
        if (this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
        }
        if (this.supportsAutoSizeText()) {
            if (this.mAutoSizeTextType == 1) {
                if (!this.mHasPresetAutoSizeValues) {
                    object = this.getResources().getDisplayMetrics();
                    if (f2 == -1.0f) {
                        f2 = TypedValue.applyDimension(2, 12.0f, (DisplayMetrics)object);
                    }
                    if (f3 == -1.0f) {
                        f3 = TypedValue.applyDimension(2, 112.0f, (DisplayMetrics)object);
                    }
                    if (f == -1.0f) {
                        f = 1.0f;
                    }
                    this.validateAndSetAutoSizeTextTypeUniformConfiguration(f2, f3, f);
                }
                this.setupAutoSizeText();
            }
        } else {
            this.mAutoSizeTextType = 0;
        }
        if (n5 >= 0) {
            this.setFirstBaselineToTopHeight(n5);
        }
        if (n10 >= 0) {
            this.setLastBaselineToBottomHeight(n10);
        }
        if (n4 < 0) return;
        this.setLineHeight(n4);
    }

    private void applyCompoundDrawableTint() {
        int[] arrn = this.mDrawables;
        if (arrn == null) {
            return;
        }
        if (arrn.mHasTint || this.mDrawables.mHasTintMode) {
            ColorStateList colorStateList = this.mDrawables.mTintList;
            BlendMode blendMode = this.mDrawables.mBlendMode;
            boolean bl = this.mDrawables.mHasTint;
            boolean bl2 = this.mDrawables.mHasTintMode;
            arrn = this.getDrawableState();
            for (Drawable drawable2 : this.mDrawables.mShowing) {
                if (drawable2 == null || drawable2 == this.mDrawables.mDrawableError) continue;
                drawable2.mutate();
                if (bl) {
                    drawable2.setTintList(colorStateList);
                }
                if (bl2) {
                    drawable2.setTintBlendMode(blendMode);
                }
                if (!drawable2.isStateful()) continue;
                drawable2.setState(arrn);
            }
        }
    }

    private void applySingleLine(boolean bl, boolean bl2, boolean bl3) {
        this.mSingleLine = bl;
        if (bl) {
            this.setLines(1);
            this.setHorizontallyScrolling(true);
            if (bl2) {
                this.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            }
        } else {
            if (bl3) {
                this.setMaxLines(Integer.MAX_VALUE);
            }
            this.setHorizontallyScrolling(false);
            if (bl2) {
                this.setTransformationMethod(null);
            }
        }
    }

    private void applyTextAppearance(TextAppearanceAttributes textAppearanceAttributes) {
        if (textAppearanceAttributes.mTextColor != null) {
            this.setTextColor(textAppearanceAttributes.mTextColor);
        }
        if (textAppearanceAttributes.mTextColorHint != null) {
            this.setHintTextColor(textAppearanceAttributes.mTextColorHint);
        }
        if (textAppearanceAttributes.mTextColorLink != null) {
            this.setLinkTextColor(textAppearanceAttributes.mTextColorLink);
        }
        if (textAppearanceAttributes.mTextColorHighlight != 0) {
            this.setHighlightColor(textAppearanceAttributes.mTextColorHighlight);
        }
        if (textAppearanceAttributes.mTextSize != -1) {
            this.setRawTextSize(textAppearanceAttributes.mTextSize, true);
        }
        if (textAppearanceAttributes.mTextLocales != null) {
            this.setTextLocales(textAppearanceAttributes.mTextLocales);
        }
        if (textAppearanceAttributes.mTypefaceIndex != -1 && !textAppearanceAttributes.mFontFamilyExplicit) {
            textAppearanceAttributes.mFontFamily = null;
        }
        this.setTypefaceFromAttrs(textAppearanceAttributes.mFontTypeface, textAppearanceAttributes.mFontFamily, textAppearanceAttributes.mTypefaceIndex, textAppearanceAttributes.mTextStyle, textAppearanceAttributes.mFontWeight);
        if (textAppearanceAttributes.mShadowColor != 0) {
            this.setShadowLayer(textAppearanceAttributes.mShadowRadius, textAppearanceAttributes.mShadowDx, textAppearanceAttributes.mShadowDy, textAppearanceAttributes.mShadowColor);
        }
        if (textAppearanceAttributes.mAllCaps) {
            this.setTransformationMethod(new AllCapsTransformationMethod(this.getContext()));
        }
        if (textAppearanceAttributes.mHasElegant) {
            this.setElegantTextHeight(textAppearanceAttributes.mElegant);
        }
        if (textAppearanceAttributes.mHasFallbackLineSpacing) {
            this.setFallbackLineSpacing(textAppearanceAttributes.mFallbackLineSpacing);
        }
        if (textAppearanceAttributes.mHasLetterSpacing) {
            this.setLetterSpacing(textAppearanceAttributes.mLetterSpacing);
        }
        if (textAppearanceAttributes.mFontFeatureSettings != null) {
            this.setFontFeatureSettings(textAppearanceAttributes.mFontFeatureSettings);
        }
        if (textAppearanceAttributes.mFontVariationSettings != null) {
            this.setFontVariationSettings(textAppearanceAttributes.mFontVariationSettings);
        }
    }

    @UnsupportedAppUsage
    private void assumeLayout() {
        int n;
        int n2 = n = this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
        if (n < 1) {
            n2 = 0;
        }
        n = n2;
        if (this.mHorizontallyScrolling) {
            n = 1048576;
        }
        BoringLayout.Metrics metrics = UNKNOWN_BORING;
        this.makeNewLayout(n, n2, metrics, metrics, n2, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void autoSizeText() {
        if (!this.isAutoSizeEnabled()) {
            return;
        }
        if (this.mNeedsAutoSizeText) {
            if (this.getMeasuredWidth() <= 0) return;
            if (this.getMeasuredHeight() <= 0) {
                return;
            }
            int n = this.mHorizontallyScrolling ? 1048576 : this.getMeasuredWidth() - this.getTotalPaddingLeft() - this.getTotalPaddingRight();
            int n2 = this.getMeasuredHeight() - this.getExtendedPaddingBottom() - this.getExtendedPaddingTop();
            if (n <= 0) return;
            if (n2 <= 0) {
                return;
            }
            RectF rectF = TEMP_RECTF;
            synchronized (rectF) {
                TEMP_RECTF.setEmpty();
                TextView.TEMP_RECTF.right = n;
                TextView.TEMP_RECTF.bottom = n2;
                float f = this.findLargestTextSizeWhichFits(TEMP_RECTF);
                if (f != this.getTextSize()) {
                    this.setTextSizeInternal(0, f, false);
                    this.makeNewLayout(n, 0, UNKNOWN_BORING, UNKNOWN_BORING, this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight(), false);
                }
            }
        }
        this.mNeedsAutoSizeText = true;
    }

    @UnsupportedAppUsage
    private boolean bringTextIntoView() {
        int n;
        Layout.Alignment alignment;
        Layout layout2 = this.isShowingHint() ? this.mHintLayout : this.mLayout;
        int n2 = 0;
        if ((this.mGravity & 112) == 80) {
            n2 = layout2.getLineCount() - 1;
        }
        Layout.Alignment alignment2 = layout2.getParagraphAlignment(n2);
        int n3 = layout2.getParagraphDirection(n2);
        int n4 = this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
        int n5 = this.mBottom - this.mTop - this.getExtendedPaddingTop() - this.getExtendedPaddingBottom();
        int n6 = layout2.getHeight();
        if (alignment2 == Layout.Alignment.ALIGN_NORMAL) {
            alignment = n3 == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
        } else {
            alignment = alignment2;
            if (alignment2 == Layout.Alignment.ALIGN_OPPOSITE) {
                alignment = n3 == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
            }
        }
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            n = (int)Math.floor(layout2.getLineLeft(n2));
            n2 = (n2 = (int)Math.ceil(layout2.getLineRight(n2))) - n < n4 ? (n2 + n) / 2 - n4 / 2 : (n3 < 0 ? (n2 -= n4) : n);
        } else {
            n2 = alignment == Layout.Alignment.ALIGN_RIGHT ? (int)Math.ceil(layout2.getLineRight(n2)) - n4 : (int)Math.floor(layout2.getLineLeft(n2));
        }
        n = n6 < n5 ? 0 : ((this.mGravity & 112) == 80 ? n6 - n5 : 0);
        if (n2 == this.mScrollX && n == this.mScrollY) {
            return false;
        }
        this.scrollTo(n2, n);
        return true;
    }

    private boolean canMarquee() {
        boolean bl;
        block0 : {
            Layout layout2;
            int n = this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
            bl = false;
            if (n <= 0 || !(this.mLayout.getLineWidth(0) > (float)n) && (this.mMarqueeFadeMode == 0 || (layout2 = this.mSavedMarqueeModeLayout) == null || !(layout2.getLineWidth(0) > (float)n))) break block0;
            bl = true;
        }
        return bl;
    }

    private void changeListenerLocaleTo(Locale object) {
        block13 : {
            if (this.mListenerChanged) {
                return;
            }
            Object object2 = this.mEditor;
            if (object2 == null) break block13;
            object2 = ((Editor)object2).mKeyListener;
            if (object2 instanceof DigitsKeyListener) {
                object = DigitsKeyListener.getInstance((Locale)object, (DigitsKeyListener)object2);
            } else if (object2 instanceof DateKeyListener) {
                object = DateKeyListener.getInstance((Locale)object);
            } else if (object2 instanceof TimeKeyListener) {
                object = TimeKeyListener.getInstance((Locale)object);
            } else {
                if (!(object2 instanceof DateTimeKeyListener)) {
                    return;
                }
                object = DateTimeKeyListener.getInstance((Locale)object);
            }
            boolean bl = TextView.isPasswordInputType(this.mEditor.mInputType);
            this.setKeyListenerOnly((KeyListener)object);
            this.setInputTypeFromEditor();
            if (bl) {
                int n = this.mEditor.mInputType & 15;
                if (n == 1) {
                    object = this.mEditor;
                    ((Editor)object).mInputType |= 128;
                } else if (n == 2) {
                    object = this.mEditor;
                    ((Editor)object).mInputType |= 16;
                }
            }
        }
    }

    @UnsupportedAppUsage
    private void checkForRelayout() {
        if ((this.mLayoutParams.width != -2 || this.mMaxWidthMode == this.mMinWidthMode && this.mMaxWidth == this.mMinWidth) && (this.mHint == null || this.mHintLayout != null) && this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight() > 0) {
            int n = this.mLayout.getHeight();
            int n2 = this.mLayout.getWidth();
            Object object = this.mHintLayout;
            int n3 = object == null ? 0 : ((Layout)object).getWidth();
            object = UNKNOWN_BORING;
            this.makeNewLayout(n2, n3, (BoringLayout.Metrics)object, (BoringLayout.Metrics)object, this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight(), false);
            if (this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
                if (this.mLayoutParams.height != -2 && this.mLayoutParams.height != -1) {
                    this.autoSizeText();
                    this.invalidate();
                    return;
                }
                if (this.mLayout.getHeight() == n && ((object = this.mHintLayout) == null || ((Layout)object).getHeight() == n)) {
                    this.autoSizeText();
                    this.invalidate();
                    return;
                }
            }
            this.requestLayout();
            this.invalidate();
        } else {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    private void checkForResize() {
        boolean bl = false;
        boolean bl2 = false;
        if (this.mLayout != null) {
            if (this.mLayoutParams.width == -2) {
                bl2 = true;
                this.invalidate();
            }
            if (this.mLayoutParams.height == -2) {
                bl = bl2;
                if (this.getDesiredHeight() != this.getHeight()) {
                    bl = true;
                }
            } else {
                bl = bl2;
                if (this.mLayoutParams.height == -1) {
                    bl = bl2;
                    if (this.mDesiredHeightAtMeasure >= 0) {
                        bl = bl2;
                        if (this.getDesiredHeight() != this.mDesiredHeightAtMeasure) {
                            bl = true;
                        }
                    }
                }
            }
        }
        if (bl) {
            this.requestLayout();
        }
    }

    private int[] cleanupAutoSizePresetSizes(int[] arrn) {
        int n = arrn.length;
        if (n == 0) {
            return arrn;
        }
        Arrays.sort(arrn);
        IntArray intArray = new IntArray();
        for (int i = 0; i < n; ++i) {
            int n2 = arrn[i];
            if (n2 <= 0 || intArray.binarySearch(n2) >= 0) continue;
            intArray.add(n2);
        }
        if (n != intArray.size()) {
            arrn = intArray.toArray();
        }
        return arrn;
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = EmptyArray.INT;
        this.mNeedsAutoSizeText = false;
    }

    @UnsupportedAppUsage
    private boolean compressText(float f) {
        if (this.isHardwareAccelerated()) {
            return false;
        }
        if (f > 0.0f && this.mLayout != null && this.getLineCount() == 1 && !this.mUserSetTextScaleX && this.mTextPaint.getTextScaleX() == 1.0f && (f = (this.mLayout.getLineWidth(0) + 1.0f - f) / f) > 0.0f && f <= 0.07f) {
            this.mTextPaint.setTextScaleX(1.0f - f - 0.005f);
            this.post(new Runnable(){

                @Override
                public void run() {
                    TextView.this.requestLayout();
                }
            });
            return true;
        }
        return false;
    }

    private void convertFromViewportToContentCoordinates(Rect rect) {
        int n = this.viewportToContentHorizontalOffset();
        rect.left += n;
        rect.right += n;
        n = this.viewportToContentVerticalOffset();
        rect.top += n;
        rect.bottom += n;
    }

    @UnsupportedAppUsage
    private void createEditorIfNeeded() {
        if (this.mEditor == null) {
            this.mEditor = new Editor(this);
        }
    }

    private static int desired(Layout layout2) {
        int n;
        int n2 = layout2.getLineCount();
        CharSequence charSequence = layout2.getText();
        float f = 0.0f;
        for (n = 0; n < n2 - 1; ++n) {
            if (charSequence.charAt(layout2.getLineEnd(n) - 1) == '\n') continue;
            return -1;
        }
        for (n = 0; n < n2; ++n) {
            f = Math.max(f, layout2.getLineWidth(n));
        }
        return (int)Math.ceil(f);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int doKeyDown(int n, KeyEvent keyEvent, KeyEvent keyEvent2) {
        boolean bl;
        boolean bl2;
        Object object;
        boolean bl3;
        int n2;
        block39 : {
            block33 : {
                block34 : {
                    block35 : {
                        block36 : {
                            block37 : {
                                block38 : {
                                    bl2 = this.isEnabled();
                                    n2 = 0;
                                    if (!bl2) {
                                        return 0;
                                    }
                                    if (keyEvent.getRepeatCount() == 0 && !KeyEvent.isModifierKey(n)) {
                                        this.mPreventDefaultMovement = false;
                                    }
                                    if (n == 4) break block33;
                                    if (n == 23) break block34;
                                    if (n == 61) break block35;
                                    if (n == 66) break block36;
                                    if (n == 112) break block37;
                                    if (n == 124) break block38;
                                    switch (n) {
                                        default: {
                                            break block39;
                                        }
                                        case 279: {
                                            if (keyEvent.hasNoModifiers() && this.canPaste() && this.onTextContextMenuItem(16908322)) {
                                                return -1;
                                            }
                                            break block39;
                                        }
                                        case 278: {
                                            if (keyEvent.hasNoModifiers() && this.canCopy() && this.onTextContextMenuItem(16908321)) {
                                                return -1;
                                            }
                                            break block39;
                                        }
                                        case 277: {
                                            if (keyEvent.hasNoModifiers() && this.canCut() && this.onTextContextMenuItem(16908320)) {
                                                return -1;
                                            }
                                            break block39;
                                        }
                                    }
                                }
                                if (keyEvent.hasModifiers(4096) && this.canCopy() ? this.onTextContextMenuItem(16908321) : keyEvent.hasModifiers(1) && this.canPaste() && this.onTextContextMenuItem(16908322)) {
                                    return -1;
                                }
                                break block39;
                            }
                            if (keyEvent.hasModifiers(1) && this.canCut() && this.onTextContextMenuItem(16908320)) {
                                return -1;
                            }
                            break block39;
                        }
                        if (keyEvent.hasNoModifiers()) {
                            object = this.mEditor;
                            if (object != null && ((Editor)object).mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, 0, keyEvent)) {
                                this.mEditor.mInputContentType.enterDown = true;
                                return -1;
                            }
                            if ((keyEvent.getFlags() & 16) != 0 || this.shouldAdvanceFocusOnEnter()) {
                                if (!this.hasOnClickListeners()) return -1;
                                return 0;
                            }
                        }
                        break block39;
                    }
                    if ((keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) && this.shouldAdvanceFocusOnTab()) {
                        return 0;
                    }
                    break block39;
                }
                if (keyEvent.hasNoModifiers() && this.shouldAdvanceFocusOnEnter()) {
                    return 0;
                }
                break block39;
            }
            object = this.mEditor;
            if (object != null && ((Editor)object).getTextActionMode() != null) {
                this.stopTextActionMode();
                return -1;
            }
        }
        object = this.mEditor;
        if (object != null && ((Editor)object).mKeyListener != null) {
            bl = true;
            bl3 = true;
            if (keyEvent2 != null) {
                try {
                    this.beginBatchEdit();
                    bl2 = this.mEditor.mKeyListener.onKeyOther(this, (Editable)this.mText, keyEvent2);
                    this.hideErrorIfUnchanged();
                    bl = false;
                    if (bl2) {
                        this.endBatchEdit();
                        return -1;
                    }
                }
                catch (Throwable throwable) {
                    this.endBatchEdit();
                    throw throwable;
                }
                catch (AbstractMethodError abstractMethodError) {
                    bl = bl3;
                }
                this.endBatchEdit();
            }
            if (bl) {
                this.beginBatchEdit();
                bl2 = this.mEditor.mKeyListener.onKeyDown(this, (Editable)this.mText, n, keyEvent);
                this.endBatchEdit();
                this.hideErrorIfUnchanged();
                if (bl2) {
                    return 1;
                }
            }
        }
        if ((object = this.mMovement) != null && this.mLayout != null) {
            bl = bl3 = true;
            if (keyEvent2 != null) {
                try {
                    bl2 = object.onKeyOther(this, this.mSpannable, keyEvent2);
                    bl = false;
                    if (bl2) {
                        return -1;
                    }
                }
                catch (AbstractMethodError abstractMethodError) {
                    bl = bl3;
                }
            }
            if (bl && this.mMovement.onKeyDown(this, this.mSpannable, n, keyEvent)) {
                if (keyEvent.getRepeatCount() != 0) return 2;
                if (KeyEvent.isModifierKey(n)) return 2;
                this.mPreventDefaultMovement = true;
                return 2;
            }
            if (keyEvent.getSource() == 257 && this.isDirectionalNavigationKey(n)) {
                return -1;
            }
        }
        if (!this.mPreventDefaultMovement) return n2;
        if (KeyEvent.isModifierKey(n)) return n2;
        return -1;
    }

    private void ensureIterableTextForAccessibilitySelectable() {
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Spannable)) {
            this.setText(charSequence, BufferType.SPANNABLE);
        }
    }

    private int findLargestTextSizeWhichFits(RectF rectF) {
        int n = this.mAutoSizeTextSizesInPx.length;
        if (n != 0) {
            int n2 = 0;
            int n3 = 0 + 1;
            --n;
            while (n3 <= n) {
                n2 = (n3 + n) / 2;
                if (this.suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[n2], rectF)) {
                    int n4 = n2 + 1;
                    n2 = n3;
                    n3 = n4;
                    continue;
                }
                n2 = n = n2 - 1;
            }
            return this.mAutoSizeTextSizesInPx[n2];
        }
        throw new IllegalStateException("No available text sizes to choose from.");
    }

    private void fixFocusableAndClickableSettings() {
        Editor editor;
        if (this.mMovement == null && ((editor = this.mEditor) == null || editor.mKeyListener == null)) {
            this.setFocusable(16);
            this.setClickable(false);
            this.setLongClickable(false);
        } else {
            this.setFocusable(1);
            this.setClickable(true);
            this.setLongClickable(true);
        }
    }

    private int getBottomVerticalOffset(boolean bl) {
        Layout layout2;
        int n = 0;
        int n2 = this.mGravity & 112;
        Layout layout3 = layout2 = this.mLayout;
        if (!bl) {
            layout3 = layout2;
            if (this.mText.length() == 0) {
                layout3 = layout2;
                if (this.mHintLayout != null) {
                    layout3 = this.mHintLayout;
                }
            }
        }
        int n3 = n;
        if (n2 != 80) {
            int n4 = this.getBoxHeight(layout3);
            int n5 = layout3.getHeight();
            n3 = n;
            if (n5 < n4) {
                n3 = n2 == 48 ? n4 - n5 : n4 - n5 >> 1;
            }
        }
        return n3;
    }

    private int getBoxHeight(Layout layout2) {
        Insets insets = TextView.isLayoutModeOptical(this.mParent) ? this.getOpticalInsets() : Insets.NONE;
        int n = layout2 == this.mHintLayout ? this.getCompoundPaddingTop() + this.getCompoundPaddingBottom() : this.getExtendedPaddingTop() + this.getExtendedPaddingBottom();
        return this.getMeasuredHeight() - n + insets.top + insets.bottom;
    }

    private Locale getCustomLocaleForKeyListenerOrNull() {
        if (!this.mUseInternationalizedInput) {
            return null;
        }
        LocaleList localeList = this.getImeHintLocales();
        if (localeList == null) {
            return null;
        }
        return localeList.get(0);
    }

    private int getDesiredHeight() {
        Layout layout2 = this.mLayout;
        boolean bl = true;
        int n = this.getDesiredHeight(layout2, true);
        layout2 = this.mHintLayout;
        if (this.mEllipsize == null) {
            bl = false;
        }
        return Math.max(n, this.getDesiredHeight(layout2, bl));
    }

    private int getDesiredHeight(Layout layout2, boolean bl) {
        int n;
        int n2;
        int n3;
        block11 : {
            Drawables drawables;
            int n4;
            block12 : {
                int n5;
                block10 : {
                    if (layout2 == null) {
                        return 0;
                    }
                    n3 = layout2.getHeight(bl);
                    drawables = this.mDrawables;
                    n2 = n3;
                    if (drawables != null) {
                        n2 = Math.max(Math.max(n3, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
                    }
                    n3 = layout2.getLineCount();
                    n4 = this.getCompoundPaddingTop() + this.getCompoundPaddingBottom();
                    n5 = n2 + n4;
                    if (this.mMaxMode == 1) break block10;
                    n2 = Math.min(n5, this.mMaximum);
                    n = n3;
                    break block11;
                }
                n2 = n5;
                n = n3;
                if (!bl) break block11;
                n2 = n5;
                n = n3;
                if (n3 <= this.mMaximum) break block11;
                if (layout2 instanceof DynamicLayout) break block12;
                n2 = n5;
                n = n3;
                if (!(layout2 instanceof BoringLayout)) break block11;
            }
            n2 = n3 = layout2.getLineTop(this.mMaximum);
            if (drawables != null) {
                n2 = Math.max(Math.max(n3, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
            }
            n2 += n4;
            n = this.mMaximum;
        }
        if (this.mMinMode == 1) {
            n3 = n2;
            if (n < this.mMinimum) {
                n3 = n2 + this.getLineHeight() * (this.mMinimum - n);
            }
        } else {
            n3 = Math.max(n2, this.mMinimum);
        }
        return Math.max(n3, this.getSuggestedMinimumHeight());
    }

    private float getHorizontalFadingEdgeStrength(float f, float f2) {
        int n = this.getHorizontalFadingEdgeLength();
        if (n == 0) {
            return 0.0f;
        }
        if ((f = Math.abs(f - f2)) > (float)n) {
            return 1.0f;
        }
        return f / (float)n;
    }

    private InputMethodManager getInputMethodManager() {
        return this.getContext().getSystemService(InputMethodManager.class);
    }

    private void getInterestingRect(Rect rect, int n) {
        this.convertFromViewportToContentCoordinates(rect);
        if (n == 0) {
            rect.top -= this.getExtendedPaddingTop();
        }
        if (n == this.mLayout.getLineCount() - 1) {
            rect.bottom += this.getExtendedPaddingBottom();
        }
    }

    @UnsupportedAppUsage
    private Layout.Alignment getLayoutAlignment() {
        Layout.Alignment alignment;
        switch (this.getTextAlignment()) {
            default: {
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            }
            case 6: {
                if (this.getLayoutDirection() == 1) {
                    alignment = Layout.Alignment.ALIGN_LEFT;
                    break;
                }
                alignment = Layout.Alignment.ALIGN_RIGHT;
                break;
            }
            case 5: {
                if (this.getLayoutDirection() == 1) {
                    alignment = Layout.Alignment.ALIGN_RIGHT;
                    break;
                }
                alignment = Layout.Alignment.ALIGN_LEFT;
                break;
            }
            case 4: {
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
            }
            case 3: {
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            }
            case 2: {
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
            }
            case 1: {
                int n = this.mGravity & 8388615;
                if (n != 1) {
                    if (n != 3) {
                        if (n != 5) {
                            if (n != 8388611) {
                                if (n != 8388613) {
                                    alignment = Layout.Alignment.ALIGN_NORMAL;
                                    break;
                                }
                                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                                break;
                            }
                            alignment = Layout.Alignment.ALIGN_NORMAL;
                            break;
                        }
                        alignment = Layout.Alignment.ALIGN_RIGHT;
                        break;
                    }
                    alignment = Layout.Alignment.ALIGN_LEFT;
                    break;
                }
                alignment = Layout.Alignment.ALIGN_CENTER;
            }
        }
        return alignment;
    }

    public static int getTextColor(Context object, TypedArray typedArray, int n) {
        if ((object = TextView.getTextColors((Context)object, typedArray)) == null) {
            return n;
        }
        return ((ColorStateList)object).getDefaultColor();
    }

    public static ColorStateList getTextColors(Context object, TypedArray object2) {
        if (object2 != null) {
            TypedArray typedArray = ((Context)object).obtainStyledAttributes(R.styleable.TextView);
            ColorStateList colorStateList = typedArray.getColorStateList(5);
            object2 = colorStateList;
            if (colorStateList == null) {
                int n = typedArray.getResourceId(1, 0);
                object2 = colorStateList;
                if (n != 0) {
                    object = ((Context)object).obtainStyledAttributes(n, R.styleable.TextAppearance);
                    object2 = ((TypedArray)object).getColorStateList(3);
                    ((TypedArray)object).recycle();
                }
            }
            typedArray.recycle();
            return object2;
        }
        throw new NullPointerException();
    }

    @UnsupportedAppUsage
    private CharSequence getTextForAccessibility() {
        if (TextUtils.isEmpty(this.mText)) {
            return this.mHint;
        }
        return TextUtils.trimToParcelableSize(this.mTransformed);
    }

    @UnsupportedAppUsage
    private Locale getTextServicesLocale(boolean bl) {
        this.updateTextServicesLocaleAsync();
        Locale locale = this.mCurrentSpellCheckerLocaleCache == null && !bl ? Locale.getDefault() : this.mCurrentSpellCheckerLocaleCache;
        return locale;
    }

    @UnsupportedAppUsage
    private Path getUpdatedHighlightPath() {
        Path path;
        block12 : {
            Path path2;
            Paint paint;
            int n;
            int n2;
            block13 : {
                path2 = null;
                paint = this.mHighlightPaint;
                n = this.getSelectionStart();
                n2 = this.getSelectionEnd();
                path = path2;
                if (this.mMovement == null) break block12;
                if (this.isFocused()) break block13;
                path = path2;
                if (!this.isPressed()) break block12;
            }
            path = path2;
            if (n >= 0) {
                if (n == n2) {
                    Editor editor = this.mEditor;
                    path = path2;
                    if (editor != null) {
                        path = path2;
                        if (editor.shouldRenderCursor()) {
                            if (this.mHighlightPathBogus) {
                                if (this.mHighlightPath == null) {
                                    this.mHighlightPath = new Path();
                                }
                                this.mHighlightPath.reset();
                                this.mLayout.getCursorPath(n, this.mHighlightPath, this.mText);
                                this.mEditor.updateCursorPosition();
                                this.mHighlightPathBogus = false;
                            }
                            paint.setColor(this.mCurTextColor);
                            paint.setStyle(Paint.Style.STROKE);
                            path = this.mHighlightPath;
                        }
                    }
                } else {
                    if (this.mHighlightPathBogus) {
                        if (this.mHighlightPath == null) {
                            this.mHighlightPath = new Path();
                        }
                        this.mHighlightPath.reset();
                        this.mLayout.getSelectionPath(n, n2, this.mHighlightPath);
                        this.mHighlightPathBogus = false;
                    }
                    paint.setColor(this.mHighlightColor);
                    paint.setStyle(Paint.Style.FILL);
                    path = this.mHighlightPath;
                }
            }
        }
        return path;
    }

    private boolean hasSpannableText() {
        CharSequence charSequence = this.mText;
        boolean bl = charSequence != null && charSequence instanceof Spannable;
        return bl;
    }

    private void invalidateCursor(int n, int n2, int n3) {
        if (n >= 0 || n2 >= 0 || n3 >= 0) {
            this.invalidateRegion(Math.min(Math.min(n, n2), n3), Math.max(Math.max(n, n2), n3), true);
        }
    }

    private boolean isAutoSizeEnabled() {
        boolean bl = this.supportsAutoSizeText() && this.mAutoSizeTextType != 0;
        return bl;
    }

    private boolean isAutofillable() {
        boolean bl = this.getAutofillType() != 0;
        return bl;
    }

    private boolean isDirectionalNavigationKey(int n) {
        switch (n) {
            default: {
                return false;
            }
            case 19: 
            case 20: 
            case 21: 
            case 22: 
        }
        return true;
    }

    private boolean isMarqueeFadeEnabled() {
        TextUtils.TruncateAt truncateAt = this.mEllipsize;
        TextUtils.TruncateAt truncateAt2 = TextUtils.TruncateAt.MARQUEE;
        boolean bl = true;
        if (truncateAt != truncateAt2 || this.mMarqueeFadeMode == 1) {
            bl = false;
        }
        return bl;
    }

    private static boolean isMultilineInputType(int n) {
        boolean bl = (131087 & n) == 131073;
        return bl;
    }

    static boolean isPasswordInputType(int n) {
        boolean bl = (n &= 4095) == 129 || n == 225 || n == 18;
        return bl;
    }

    private boolean isShowingHint() {
        boolean bl = TextUtils.isEmpty(this.mText) && !TextUtils.isEmpty(this.mHint);
        return bl;
    }

    private static boolean isVisiblePasswordInputType(int n) {
        boolean bl = (n & 4095) == 145;
        return bl;
    }

    static /* synthetic */ void lambda$handleClick$1(TextClassification textClassification) {
        if (textClassification != null) {
            if (!textClassification.getActions().isEmpty()) {
                try {
                    textClassification.getActions().get(0).getActionIntent().send();
                }
                catch (PendingIntent.CanceledException canceledException) {
                    Log.e(LOG_TAG, "Error sending PendingIntent", canceledException);
                }
            } else {
                Log.d(LOG_TAG, "No link action to perform");
            }
        } else {
            Log.d(LOG_TAG, "Timeout while classifying text");
        }
    }

    private void notifyListeningManagersAfterTextChanged() {
        AutofillManager autofillManager;
        if (this.isAutofillable() && (autofillManager = this.mContext.getSystemService(AutofillManager.class)) != null) {
            if (Helper.sVerbose) {
                Log.v(LOG_TAG, "notifyAutoFillManagerAfterTextChanged");
            }
            autofillManager.notifyValueChanged(this);
        }
    }

    private int[] parseDimensionArray(TypedArray typedArray) {
        if (typedArray == null) {
            return null;
        }
        int[] arrn = new int[typedArray.length()];
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = typedArray.getDimensionPixelSize(i, 0);
        }
        return arrn;
    }

    private void paste(int n, int n2, boolean bl) {
        ClipData clipData = this.getClipboardManagerForUser().getPrimaryClip();
        if (clipData != null) {
            boolean bl2 = false;
            for (int i = 0; i < clipData.getItemCount(); ++i) {
                CharSequence charSequence;
                if (bl) {
                    charSequence = clipData.getItemAt(i).coerceToStyledText(this.getContext());
                } else {
                    charSequence = clipData.getItemAt(i).coerceToText(this.getContext());
                    if (charSequence instanceof Spanned) {
                        charSequence = charSequence.toString();
                    }
                }
                boolean bl3 = bl2;
                if (charSequence != null) {
                    if (!bl2) {
                        Selection.setSelection(this.mSpannable, n2);
                        ((Editable)this.mText).replace(n, n2, charSequence);
                        bl3 = true;
                    } else {
                        ((Editable)this.mText).insert(this.getSelectionEnd(), "\n");
                        ((Editable)this.mText).insert(this.getSelectionEnd(), charSequence);
                        bl3 = bl2;
                    }
                }
                bl2 = bl3;
            }
            sLastCutCopyOrTextChangedTime = 0L;
        }
    }

    private boolean performAccessibilityActionClick(Bundle object) {
        boolean bl;
        block11 : {
            boolean bl2;
            block12 : {
                block10 : {
                    bl2 = false;
                    if (!this.isEnabled()) {
                        return false;
                    }
                    if (this.isClickable() || this.isLongClickable()) {
                        if (this.isFocusable() && !this.isFocused()) {
                            this.requestFocus();
                        }
                        this.performClick();
                        bl2 = true;
                    }
                    if (this.mMovement != null) break block10;
                    bl = bl2;
                    if (!this.onCheckIsTextEditor()) break block11;
                }
                bl = bl2;
                if (!this.hasSpannableText()) break block11;
                bl = bl2;
                if (this.mLayout == null) break block11;
                if (this.isTextEditable()) break block12;
                bl = bl2;
                if (!this.isTextSelectable()) break block11;
            }
            bl = bl2;
            if (this.isFocused()) {
                object = this.getInputMethodManager();
                this.viewClicked((InputMethodManager)object);
                bl = bl2;
                if (!this.isTextSelectable()) {
                    bl = bl2;
                    if (this.mEditor.mShowSoftInputOnFocus) {
                        bl = bl2;
                        if (object != null) {
                            bl = bl2 | ((InputMethodManager)object).showSoftInput(this, 0);
                        }
                    }
                }
            }
        }
        return bl;
    }

    public static void preloadFontCache() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT);
        paint.measureText("H");
    }

    private void prepareDrawableForDisplay(Drawable drawable2) {
        if (drawable2 == null) {
            return;
        }
        drawable2.setLayoutDirection(this.getLayoutDirection());
        if (drawable2.isStateful()) {
            drawable2.setState(this.getDrawableState());
            drawable2.jumpToCurrentState();
        }
    }

    private void readTextAppearance(Context context, TypedArray typedArray, TextAppearanceAttributes textAppearanceAttributes, boolean bl) {
        int n = typedArray.getIndexCount();
        block24 : for (int i = 0; i < n; ++i) {
            int n2;
            int n3 = n2 = typedArray.getIndex(i);
            if (bl) {
                int n4;
                n3 = n4 = sAppearanceValues.get(n2, -1);
                if (n4 == -1) continue;
            }
            switch (n3) {
                default: {
                    continue block24;
                }
                case 19: {
                    Object object = typedArray.getString(n2);
                    if (object == null || ((LocaleList)(object = LocaleList.forLanguageTags((String)object))).isEmpty()) continue block24;
                    textAppearanceAttributes.mTextLocales = object;
                    continue block24;
                }
                case 18: {
                    textAppearanceAttributes.mFontWeight = typedArray.getInt(n2, textAppearanceAttributes.mFontWeight);
                    continue block24;
                }
                case 17: {
                    textAppearanceAttributes.mHasFallbackLineSpacing = true;
                    textAppearanceAttributes.mFallbackLineSpacing = typedArray.getBoolean(n2, textAppearanceAttributes.mFallbackLineSpacing);
                    continue block24;
                }
                case 16: {
                    textAppearanceAttributes.mFontVariationSettings = typedArray.getString(n2);
                    continue block24;
                }
                case 15: {
                    textAppearanceAttributes.mFontFeatureSettings = typedArray.getString(n2);
                    continue block24;
                }
                case 14: {
                    textAppearanceAttributes.mHasLetterSpacing = true;
                    textAppearanceAttributes.mLetterSpacing = typedArray.getFloat(n2, textAppearanceAttributes.mLetterSpacing);
                    continue block24;
                }
                case 13: {
                    textAppearanceAttributes.mHasElegant = true;
                    textAppearanceAttributes.mElegant = typedArray.getBoolean(n2, textAppearanceAttributes.mElegant);
                    continue block24;
                }
                case 12: {
                    if (!context.isRestricted() && context.canLoadUnsafeResources()) {
                        try {
                            textAppearanceAttributes.mFontTypeface = typedArray.getFont(n2);
                        }
                        catch (Resources.NotFoundException | UnsupportedOperationException runtimeException) {
                            // empty catch block
                        }
                    }
                    if (textAppearanceAttributes.mFontTypeface == null) {
                        textAppearanceAttributes.mFontFamily = typedArray.getString(n2);
                    }
                    textAppearanceAttributes.mFontFamilyExplicit = true;
                    continue block24;
                }
                case 11: {
                    textAppearanceAttributes.mAllCaps = typedArray.getBoolean(n2, textAppearanceAttributes.mAllCaps);
                    continue block24;
                }
                case 10: {
                    textAppearanceAttributes.mShadowRadius = typedArray.getFloat(n2, textAppearanceAttributes.mShadowRadius);
                    continue block24;
                }
                case 9: {
                    textAppearanceAttributes.mShadowDy = typedArray.getFloat(n2, textAppearanceAttributes.mShadowDy);
                    continue block24;
                }
                case 8: {
                    textAppearanceAttributes.mShadowDx = typedArray.getFloat(n2, textAppearanceAttributes.mShadowDx);
                    continue block24;
                }
                case 7: {
                    textAppearanceAttributes.mShadowColor = typedArray.getInt(n2, textAppearanceAttributes.mShadowColor);
                    continue block24;
                }
                case 6: {
                    textAppearanceAttributes.mTextColorLink = typedArray.getColorStateList(n2);
                    continue block24;
                }
                case 5: {
                    textAppearanceAttributes.mTextColorHint = typedArray.getColorStateList(n2);
                    continue block24;
                }
                case 4: {
                    textAppearanceAttributes.mTextColorHighlight = typedArray.getColor(n2, textAppearanceAttributes.mTextColorHighlight);
                    continue block24;
                }
                case 3: {
                    textAppearanceAttributes.mTextColor = typedArray.getColorStateList(n2);
                    continue block24;
                }
                case 2: {
                    textAppearanceAttributes.mTextStyle = typedArray.getInt(n2, textAppearanceAttributes.mTextStyle);
                    continue block24;
                }
                case 1: {
                    textAppearanceAttributes.mTypefaceIndex = typedArray.getInt(n2, textAppearanceAttributes.mTypefaceIndex);
                    if (textAppearanceAttributes.mTypefaceIndex == -1 || textAppearanceAttributes.mFontFamilyExplicit) continue block24;
                    textAppearanceAttributes.mFontFamily = null;
                    continue block24;
                }
                case 0: {
                    textAppearanceAttributes.mTextSize = typedArray.getDimensionPixelSize(n2, textAppearanceAttributes.mTextSize);
                }
            }
        }
    }

    private void registerForPreDraw() {
        if (!this.mPreDrawRegistered) {
            this.getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawRegistered = true;
        }
    }

    private <T> void removeIntersectingNonAdjacentSpans(int n, int n2, Class<T> arrT) {
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Editable)) {
            return;
        }
        charSequence = (Editable)charSequence;
        arrT = charSequence.getSpans(n, n2, arrT);
        int n3 = arrT.length;
        for (int i = 0; i < n3; ++i) {
            int n4 = charSequence.getSpanStart(arrT[i]);
            if (charSequence.getSpanEnd(arrT[i]) == n || n4 == n2) break;
            charSequence.removeSpan(arrT[i]);
        }
    }

    static void removeParcelableSpans(Spannable spannable, int n, int n2) {
        ParcelableSpan[] arrparcelableSpan = spannable.getSpans(n, n2, ParcelableSpan.class);
        n = arrparcelableSpan.length;
        while (n > 0) {
            spannable.removeSpan(arrparcelableSpan[--n]);
        }
    }

    private void requestAutofill() {
        AutofillManager autofillManager = this.mContext.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.requestAutofill(this);
        }
    }

    private void resolveStyleAndSetTypeface(Typeface typeface, int n, int n2) {
        if (n2 >= 0) {
            n2 = Math.min(1000, n2);
            boolean bl = (n & 2) != 0;
            this.setTypeface(Typeface.create(typeface, n2, bl));
        } else {
            this.setTypeface(typeface, n);
        }
    }

    private void restartMarqueeIfNeeded() {
        if (this.mRestartMarquee && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            this.mRestartMarquee = false;
            this.startMarquee();
        }
    }

    private void sendBeforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> arrayList = this.mListeners;
            int n4 = arrayList.size();
            for (int i = 0; i < n4; ++i) {
                arrayList.get(i).beforeTextChanged(charSequence, n, n2, n3);
            }
        }
        this.removeIntersectingNonAdjacentSpans(n, n + n2, SpellCheckSpan.class);
        this.removeIntersectingNonAdjacentSpans(n, n + n2, SuggestionSpan.class);
    }

    private void setFilters(Editable editable, InputFilter[] arrinputFilter) {
        InputFilter[] arrinputFilter2 = this.mEditor;
        if (arrinputFilter2 != null) {
            boolean bl = arrinputFilter2.mUndoInputFilter != null;
            boolean bl2 = this.mEditor.mKeyListener instanceof InputFilter;
            int n = 0;
            if (bl) {
                n = 0 + 1;
            }
            int n2 = n;
            if (bl2) {
                n2 = n + 1;
            }
            if (n2 > 0) {
                arrinputFilter2 = new InputFilter[arrinputFilter.length + n2];
                System.arraycopy(arrinputFilter, 0, arrinputFilter2, 0, arrinputFilter.length);
                n = 0;
                if (bl) {
                    arrinputFilter2[arrinputFilter.length] = this.mEditor.mUndoInputFilter;
                    n = 0 + 1;
                }
                if (bl2) {
                    arrinputFilter2[arrinputFilter.length + n] = (InputFilter)((Object)this.mEditor.mKeyListener);
                }
                editable.setFilters(arrinputFilter2);
                return;
            }
        }
        editable.setFilters(arrinputFilter);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHint = TextUtils.stringOrSpannedString(charSequence);
        if (this.mLayout != null) {
            this.checkForRelayout();
        }
        if (this.mText.length() == 0) {
            this.invalidate();
        }
        if (this.mEditor != null && this.mText.length() == 0 && this.mHint != null) {
            this.mEditor.invalidateTextDisplayList();
        }
    }

    @UnsupportedAppUsage
    private void setInputType(int n, boolean bl) {
        Object object;
        int n2 = n & 15;
        boolean bl2 = true;
        boolean bl3 = true;
        if (n2 == 1) {
            if ((32768 & n) == 0) {
                bl3 = false;
            }
            object = (n & 4096) != 0 ? TextKeyListener.Capitalize.CHARACTERS : ((n & 8192) != 0 ? TextKeyListener.Capitalize.WORDS : ((n & 16384) != 0 ? TextKeyListener.Capitalize.SENTENCES : TextKeyListener.Capitalize.NONE));
            object = TextKeyListener.getInstance(bl3, (TextKeyListener.Capitalize)((Object)object));
        } else if (n2 == 2) {
            Locale locale = this.getCustomLocaleForKeyListenerOrNull();
            bl3 = (n & 4096) != 0;
            if ((n & 8192) == 0) {
                bl2 = false;
            }
            object = DigitsKeyListener.getInstance(locale, bl3, bl2);
            n2 = n;
            if (locale != null) {
                int n3 = object.getInputType();
                n2 = n;
                if ((n3 & 15) != 2) {
                    n2 = n3;
                    if ((n & 16) != 0) {
                        n2 = n3 | 128;
                    }
                }
            }
            n = n2;
        } else if (n2 == 4) {
            object = this.getCustomLocaleForKeyListenerOrNull();
            n2 = n & 4080;
            object = n2 != 16 ? (n2 != 32 ? DateTimeKeyListener.getInstance(object) : TimeKeyListener.getInstance(object)) : DateKeyListener.getInstance(object);
            if (this.mUseInternationalizedInput) {
                n = object.getInputType();
            }
        } else {
            object = n2 == 3 ? DialerKeyListener.getInstance() : TextKeyListener.getInstance();
        }
        this.setRawInputType(n);
        this.mListenerChanged = false;
        if (bl) {
            this.createEditorIfNeeded();
            this.mEditor.mKeyListener = object;
        } else {
            this.setKeyListenerOnly((KeyListener)object);
        }
    }

    private void setInputTypeFromEditor() {
        try {
            this.mEditor.mInputType = this.mEditor.mKeyListener.getInputType();
        }
        catch (IncompatibleClassChangeError incompatibleClassChangeError) {
            this.mEditor.mInputType = 1;
        }
        this.setInputTypeSingleLine(this.mSingleLine);
    }

    private void setInputTypeSingleLine(boolean bl) {
        Editor editor = this.mEditor;
        if (editor != null && (editor.mInputType & 15) == 1) {
            if (bl) {
                editor = this.mEditor;
                editor.mInputType &= -131073;
            } else {
                editor = this.mEditor;
                editor.mInputType |= 131072;
            }
        }
    }

    private void setKeyListenerOnly(KeyListener object) {
        if (this.mEditor == null && object == null) {
            return;
        }
        this.createEditorIfNeeded();
        if (this.mEditor.mKeyListener != object) {
            this.mEditor.mKeyListener = object;
            if (object != null && !((object = this.mText) instanceof Editable)) {
                this.setText((CharSequence)object);
            }
            this.setFilters((Editable)this.mText, this.mFilters);
        }
    }

    private boolean setPrimaryClip(ClipData clipData) {
        ClipboardManager clipboardManager = this.getClipboardManagerForUser();
        try {
            clipboardManager.setPrimaryClip(clipData);
        }
        catch (Throwable throwable) {
            return false;
        }
        sLastCutCopyOrTextChangedTime = SystemClock.uptimeMillis();
        return true;
    }

    @UnsupportedAppUsage
    private void setRawTextSize(float f, boolean bl) {
        if (f != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(f);
            if (bl && this.mLayout != null) {
                this.mNeedsAutoSizeText = false;
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    private void setRelativeDrawablesIfNeeded(Drawable drawable2, Drawable drawable3) {
        boolean bl = drawable2 != null || drawable3 != null;
        if (bl) {
            Object object = this.mDrawables;
            Drawables drawables = object;
            if (object == null) {
                object = new Drawables(this.getContext());
                drawables = object;
                this.mDrawables = object;
            }
            this.mDrawables.mOverride = true;
            object = drawables.mCompoundRect;
            int[] arrn = this.getDrawableState();
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                drawable2.setState(arrn);
                drawable2.copyBounds((Rect)object);
                drawable2.setCallback(this);
                drawables.mDrawableStart = drawable2;
                drawables.mDrawableSizeStart = ((Rect)object).width();
                drawables.mDrawableHeightStart = ((Rect)object).height();
            } else {
                drawables.mDrawableHeightStart = 0;
                drawables.mDrawableSizeStart = 0;
            }
            if (drawable3 != null) {
                drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
                drawable3.setState(arrn);
                drawable3.copyBounds((Rect)object);
                drawable3.setCallback(this);
                drawables.mDrawableEnd = drawable3;
                drawables.mDrawableSizeEnd = ((Rect)object).width();
                drawables.mDrawableHeightEnd = ((Rect)object).height();
            } else {
                drawables.mDrawableHeightEnd = 0;
                drawables.mDrawableSizeEnd = 0;
            }
            this.resetResolvedDrawables();
            this.resolveDrawables();
            this.applyCompoundDrawableTint();
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @UnsupportedAppUsage
    private void setText(CharSequence object, BufferType object2, boolean bl, int n) {
        void var1_17;
        Object object3;
        void var8_39;
        int n2;
        int n3;
        void var3_29;
        void var1_3;
        void var5_31 = object3;
        this.mTextSetFromXmlOrResourceId = false;
        if (object == null) {
            String string2 = "";
        }
        object3 = var1_3;
        if (!this.isSuggestionsEnabled()) {
            object3 = this.removeSuggestionSpans((CharSequence)var1_3);
        }
        if (!this.mUserSetTextScaleX) {
            this.mTextPaint.setTextScaleX(1.0f);
        }
        if (object3 instanceof Spanned && ((Spanned)object3).getSpanStart((Object)TextUtils.TruncateAt.MARQUEE) >= 0) {
            if (ViewConfiguration.get(this.mContext).isFadingMarqueeEnabled()) {
                this.setHorizontalFadingEdgeEnabled(true);
                this.mMarqueeFadeMode = 0;
            } else {
                this.setHorizontalFadingEdgeEnabled(false);
                this.mMarqueeFadeMode = 1;
            }
            this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
        int n4 = this.mFilters.length;
        for (n2 = 0; n2 < n4; ++n2) {
            CharSequence charSequence = this.mFilters[n2].filter((CharSequence)object3, 0, object3.length(), EMPTY_SPANNED, 0, 0);
            if (charSequence == null) continue;
            object3 = charSequence;
        }
        if (var3_29 != false) {
            CharSequence charSequence = this.mText;
            if (charSequence != null) {
                n3 = charSequence.length();
                this.sendBeforeTextChanged(this.mText, 0, n3, object3.length());
            } else {
                this.sendBeforeTextChanged("", 0, 0, object3.length());
            }
        }
        n4 = 0;
        ArrayList<TextWatcher> arrayList = this.mListeners;
        n2 = n4;
        if (arrayList != null) {
            n2 = n4;
            if (arrayList.size() != 0) {
                n2 = 1;
            }
        }
        if (object3 instanceof PrecomputedText) {
            PrecomputedText precomputedText = (PrecomputedText)object3;
        } else {
            Object var1_10 = null;
        }
        if (var5_31 != BufferType.EDITABLE && this.getKeyListener() == null && n2 == 0) {
            void var1_11;
            if (var1_11 != null) {
                if (this.mTextDir == null) {
                    this.mTextDir = this.getTextDirectionHeuristic();
                }
                if ((n4 = var1_11.getParams().checkResultUsable(this.getPaint(), this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency)) == 0) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("PrecomputedText's Parameters don't match the parameters of this TextView.Consider using setTextMetricsParams(precomputedText.getParams()) to override the settings of this TextView: PrecomputedText: ");
                    ((StringBuilder)object3).append(var1_11.getParams());
                    ((StringBuilder)object3).append("TextView: ");
                    ((StringBuilder)object3).append(this.getTextMetricsParams());
                    throw new IllegalArgumentException(((StringBuilder)object3).toString());
                }
                if (n4 == 1) {
                    PrecomputedText.create((CharSequence)var1_11, this.getTextMetricsParams());
                }
                ChangeWatcher[] arrchangeWatcher = object3;
            } else if (var5_31 != BufferType.SPANNABLE && this.mMovement == null) {
                CharSequence charSequence = object3;
                if (!(object3 instanceof CharWrapper)) {
                    CharSequence charSequence2 = TextUtils.stringOrSpannedString((CharSequence)object3);
                }
            } else {
                Spannable spannable = this.mSpannableFactory.newSpannable((CharSequence)object3);
            }
        } else {
            this.createEditorIfNeeded();
            this.mEditor.forgetUndoRedo();
            object3 = this.mEditableFactory.newEditable((CharSequence)object3);
            CharSequence charSequence = object3;
            this.setFilters((Editable)object3, this.mFilters);
            object3 = this.getInputMethodManager();
            if (object3 != null) {
                ((InputMethodManager)object3).restartInput(this);
            }
        }
        void var8_34 = var5_31;
        ChangeWatcher[] arrchangeWatcher = var1_17;
        if (this.mAutoLinkMask != 0) {
            object3 = var5_31 != BufferType.EDITABLE && !(var1_17 instanceof Spannable) ? this.mSpannableFactory.newSpannable((CharSequence)var1_17) : (Spannable)var1_17;
            void var8_35 = var5_31;
            arrchangeWatcher = var1_17;
            if (Linkify.addLinks((Spannable)object3, this.mAutoLinkMask)) {
                void var1_20;
                if (var5_31 == BufferType.EDITABLE) {
                    BufferType bufferType = BufferType.EDITABLE;
                } else {
                    BufferType bufferType = BufferType.SPANNABLE;
                }
                this.setTextInternal((CharSequence)object3);
                void var8_36 = var1_20;
                arrchangeWatcher = object3;
                if (this.mLinksClickable) {
                    void var8_37 = var1_20;
                    arrchangeWatcher = object3;
                    if (!this.textCanBeSelected()) {
                        this.setMovementMethod(LinkMovementMethod.getInstance());
                        arrchangeWatcher = object3;
                        void var8_38 = var1_20;
                    }
                }
            }
        }
        this.mBufferType = var8_39;
        this.setTextInternal((CharSequence)arrchangeWatcher);
        TransformationMethod transformationMethod = this.mTransformation;
        this.mTransformed = transformationMethod == null ? arrchangeWatcher : transformationMethod.getTransformation((CharSequence)arrchangeWatcher, this);
        if (this.mTransformed == null) {
            this.mTransformed = "";
        }
        int n5 = arrchangeWatcher.length();
        if (arrchangeWatcher instanceof Spannable && !this.mAllowTransformationLengthChange) {
            MovementMethod movementMethod;
            Spannable spannable = (Spannable)arrchangeWatcher;
            object3 = spannable.getSpans(0, spannable.length(), ChangeWatcher.class);
            int n6 = ((ChangeWatcher[])object3).length;
            for (n4 = 0; n4 < n6; ++n4) {
                spannable.removeSpan(object3[n4]);
            }
            if (this.mChangeWatcher == null) {
                this.mChangeWatcher = new ChangeWatcher();
            }
            spannable.setSpan(this.mChangeWatcher, 0, n5, 6553618);
            object3 = this.mEditor;
            if (object3 != null) {
                ((Editor)object3).addSpanWatchers(spannable);
            }
            if ((object3 = this.mTransformation) != null) {
                spannable.setSpan(object3, 0, n5, 18);
            }
            if ((movementMethod = this.mMovement) != null) {
                movementMethod.initialize(this, (Spannable)arrchangeWatcher);
                Editor editor = this.mEditor;
                if (editor != null) {
                    editor.mSelectionMoved = false;
                }
            }
        }
        if (this.mLayout != null) {
            this.checkForRelayout();
        }
        this.sendOnTextChanged((CharSequence)arrchangeWatcher, 0, n3, n5);
        this.onTextChanged((CharSequence)arrchangeWatcher, 0, n3, n5);
        this.notifyViewAccessibilityStateChangedIfNeeded(2);
        if (n2 != 0) {
            this.sendAfterTextChanged((Editable)arrchangeWatcher);
        } else {
            this.notifyListeningManagersAfterTextChanged();
        }
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.prepareCursorControllers();
        }
    }

    private void setTextInternal(CharSequence charSequence) {
        this.mText = charSequence;
        boolean bl = charSequence instanceof Spannable;
        Object var3_3 = null;
        Spannable spannable = bl ? (Spannable)charSequence : null;
        this.mSpannable = spannable;
        spannable = var3_3;
        if (charSequence instanceof PrecomputedText) {
            spannable = (PrecomputedText)charSequence;
        }
        this.mPrecomputed = spannable;
    }

    private void setTextSizeInternal(int n, float f, boolean bl) {
        Object object = this.getContext();
        object = object == null ? Resources.getSystem() : ((Context)object).getResources();
        this.setRawTextSize(TypedValue.applyDimension(n, f, ((Resources)object).getDisplayMetrics()), bl);
    }

    private void setTypefaceFromAttrs(Typeface typeface, String string2, int n, int n2, int n3) {
        if (typeface == null && string2 != null) {
            this.resolveStyleAndSetTypeface(Typeface.create(string2, 0), n2, n3);
        } else if (typeface != null) {
            this.resolveStyleAndSetTypeface(typeface, n2, n3);
        } else if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    this.resolveStyleAndSetTypeface(null, n2, n3);
                } else {
                    this.resolveStyleAndSetTypeface(Typeface.MONOSPACE, n2, n3);
                }
            } else {
                this.resolveStyleAndSetTypeface(Typeface.SERIF, n2, n3);
            }
        } else {
            this.resolveStyleAndSetTypeface(Typeface.SANS_SERIF, n2, n3);
        }
    }

    private boolean setupAutoSizeText() {
        if (this.supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int n = (int)Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx) + 1;
                int[] arrn = new int[n];
                for (int i = 0; i < n; ++i) {
                    arrn[i] = Math.round(this.mAutoSizeMinTextSizeInPx + (float)i * this.mAutoSizeStepGranularityInPx);
                }
                this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(arrn);
            }
            this.mNeedsAutoSizeText = true;
        } else {
            this.mNeedsAutoSizeText = false;
        }
        return this.mNeedsAutoSizeText;
    }

    private void setupAutoSizeUniformPresetSizes(TypedArray typedArray) {
        int n = typedArray.length();
        int[] arrn = new int[n];
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                arrn[i] = typedArray.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(arrn);
            this.setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        int n = this.mAutoSizeTextSizesInPx.length;
        boolean bl = n > 0;
        this.mHasPresetAutoSizeValues = bl;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            int[] arrn = this.mAutoSizeTextSizesInPx;
            this.mAutoSizeMinTextSizeInPx = arrn[0];
            this.mAutoSizeMaxTextSizeInPx = arrn[n - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return this.mHasPresetAutoSizeValues;
    }

    private void shareSelectedText() {
        String string2 = this.getSelectedText();
        if (string2 != null && !string2.isEmpty()) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.removeExtra("android.intent.extra.TEXT");
            intent.putExtra("android.intent.extra.TEXT", TextUtils.trimToParcelableSize(string2));
            this.getContext().startActivity(Intent.createChooser(intent, null));
            Selection.setSelection(this.mSpannable, this.getSelectionEnd());
        }
    }

    private boolean shouldAdvanceFocusOnEnter() {
        int n;
        if (this.getKeyListener() == null) {
            return false;
        }
        if (this.mSingleLine) {
            return true;
        }
        Editor editor = this.mEditor;
        return editor != null && (editor.mInputType & 15) == 1 && ((n = this.mEditor.mInputType & 4080) == 32 || n == 48);
    }

    private boolean shouldAdvanceFocusOnTab() {
        int n;
        Editor editor;
        return this.getKeyListener() == null || this.mSingleLine || (editor = this.mEditor) == null || (editor.mInputType & 15) != 1 || (n = this.mEditor.mInputType & 4080) != 262144 && n != 131072;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void startMarquee() {
        if (this.getKeyListener() != null) {
            return;
        }
        if (this.compressText(this.getWidth() - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight())) {
            return;
        }
        Object object = this.mMarquee;
        if ((object == null || ((Marquee)object).isStopped()) && (this.isFocused() || this.isSelected()) && this.getLineCount() == 1 && this.canMarquee()) {
            if (this.mMarqueeFadeMode == 1) {
                this.mMarqueeFadeMode = 2;
                object = this.mLayout;
                this.mLayout = this.mSavedMarqueeModeLayout;
                this.mSavedMarqueeModeLayout = object;
                this.setHorizontalFadingEdgeEnabled(true);
                this.requestLayout();
                this.invalidate();
            }
            if (this.mMarquee == null) {
                this.mMarquee = new Marquee(this);
            }
            this.mMarquee.start(this.mMarqueeRepeatLimit);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void startStopMarquee(boolean bl) {
        if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (bl) {
                this.startMarquee();
            } else {
                this.stopMarquee();
            }
        }
    }

    private void stopMarquee() {
        Object object = this.mMarquee;
        if (object != null && !((Marquee)object).isStopped()) {
            this.mMarquee.stop();
        }
        if (this.mMarqueeFadeMode == 2) {
            this.mMarqueeFadeMode = 1;
            object = this.mSavedMarqueeModeLayout;
            this.mSavedMarqueeModeLayout = this.mLayout;
            this.mLayout = object;
            this.setHorizontalFadingEdgeEnabled(false);
            this.requestLayout();
            this.invalidate();
        }
    }

    private boolean suggestedSizeFitsInSpace(int n, RectF rectF) {
        Object object = this.mTransformed;
        if (object == null) {
            object = this.getText();
        }
        int n2 = this.getMaxLines();
        Object object2 = this.mTempTextPaint;
        if (object2 == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            ((Paint)object2).reset();
        }
        this.mTempTextPaint.set(this.getPaint());
        this.mTempTextPaint.setTextSize(n);
        object2 = StaticLayout.Builder.obtain((CharSequence)object, 0, object.length(), this.mTempTextPaint, Math.round(rectF.right));
        object = ((StaticLayout.Builder)object2).setAlignment(this.getLayoutAlignment()).setLineSpacing(this.getLineSpacingExtra(), this.getLineSpacingMultiplier()).setIncludePad(this.getIncludeFontPadding()).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).setBreakStrategy(this.getBreakStrategy()).setHyphenationFrequency(this.getHyphenationFrequency()).setJustificationMode(this.getJustificationMode());
        n = this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE;
        ((StaticLayout.Builder)object).setMaxLines(n).setTextDirection(this.getTextDirectionHeuristic());
        object = ((StaticLayout.Builder)object2).build();
        if (n2 != -1 && ((StaticLayout)object).getLineCount() > n2) {
            return false;
        }
        return !((float)((Layout)object).getHeight() > rectF.bottom);
    }

    private void unregisterForPreDraw() {
        this.getViewTreeObserver().removeOnPreDrawListener(this);
        this.mPreDrawRegistered = false;
        this.mPreDrawListenerDetached = false;
    }

    private void updateTextColors() {
        int n;
        int n2 = 0;
        Object object = this.getDrawableState();
        int n3 = this.mTextColor.getColorForState((int[])object, 0);
        if (n3 != this.mCurTextColor) {
            this.mCurTextColor = n3;
            n2 = 1;
        }
        ColorStateList colorStateList = this.mLinkTextColor;
        n3 = n2;
        if (colorStateList != null) {
            n = colorStateList.getColorForState((int[])object, 0);
            n3 = n2;
            if (n != this.mTextPaint.linkColor) {
                this.mTextPaint.linkColor = n;
                n3 = 1;
            }
        }
        colorStateList = this.mHintTextColor;
        n2 = n3;
        if (colorStateList != null) {
            n = colorStateList.getColorForState((int[])object, 0);
            n2 = n3;
            if (n != this.mCurHintTextColor) {
                this.mCurHintTextColor = n;
                n2 = n3;
                if (this.mText.length() == 0) {
                    n2 = 1;
                }
            }
        }
        if (n2 != 0) {
            object = this.mEditor;
            if (object != null) {
                ((Editor)object).invalidateTextDisplayList();
            }
            this.invalidate();
        }
    }

    private void updateTextServicesLocaleAsync() {
        AsyncTask.execute(new Runnable(){

            @Override
            public void run() {
                TextView.this.updateTextServicesLocaleLocked();
            }
        });
    }

    @UnsupportedAppUsage
    private void updateTextServicesLocaleLocked() {
        Object object = this.getTextServicesManagerForUser();
        if (object == null) {
            return;
        }
        object = (object = ((TextServicesManager)object).getCurrentSpellCheckerSubtype(true)) != null ? ((SpellCheckerSubtype)object).getLocaleObject() : null;
        this.mCurrentSpellCheckerLocaleCache = object;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float f, float f2, float f3) {
        if (!(f <= 0.0f)) {
            if (!(f2 <= f)) {
                if (!(f3 <= 0.0f)) {
                    this.mAutoSizeTextType = 1;
                    this.mAutoSizeMinTextSizeInPx = f;
                    this.mAutoSizeMaxTextSizeInPx = f2;
                    this.mAutoSizeStepGranularityInPx = f3;
                    this.mHasPresetAutoSizeValues = false;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The auto-size step granularity (");
                stringBuilder.append(f3);
                stringBuilder.append("px) is less or equal to (0px)");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Maximum auto-size text size (");
            stringBuilder.append(f2);
            stringBuilder.append("px) is less or equal to minimum auto-size text size (");
            stringBuilder.append(f);
            stringBuilder.append("px)");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Minimum auto-size text size (");
        stringBuilder.append(f);
        stringBuilder.append("px) is less or equal to (0px)");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, String string2, Bundle arrparcelable) {
        if (arrparcelable == null) {
            return;
        }
        if (string2.equals("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY")) {
            int n = arrparcelable.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX", -1);
            int n2 = arrparcelable.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH", -1);
            if (n2 > 0 && n >= 0 && n < this.mText.length()) {
                arrparcelable = new RectF[n2];
                Object object = new CursorAnchorInfo.Builder();
                this.populateCharacterBounds((CursorAnchorInfo.Builder)object, n, n + n2, this.viewportToContentHorizontalOffset(), this.viewportToContentVerticalOffset());
                object = ((CursorAnchorInfo.Builder)object).setMatrix(null).build();
                for (int i = 0; i < n2; ++i) {
                    RectF rectF;
                    if ((((CursorAnchorInfo)object).getCharacterBoundsFlags(n + i) & 1) != 1 || (rectF = ((CursorAnchorInfo)object).getCharacterBounds(n + i)) == null) continue;
                    this.mapRectFromViewToScreenCoords(rectF, true);
                    arrparcelable[i] = rectF;
                }
                accessibilityNodeInfo.getExtras().putParcelableArray(string2, arrparcelable);
            } else {
                Log.e(LOG_TAG, "Invalid arguments for accessibility character locations");
                return;
            }
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(textWatcher);
    }

    public final void append(CharSequence charSequence) {
        this.append(charSequence, 0, charSequence.length());
    }

    public void append(CharSequence charSequence, int n, int n2) {
        CharSequence charSequence2 = this.mText;
        if (!(charSequence2 instanceof Editable)) {
            this.setText(charSequence2, BufferType.EDITABLE);
        }
        ((Editable)this.mText).append(charSequence, n, n2);
        n = this.mAutoLinkMask;
        if (n != 0 && Linkify.addLinks(this.mSpannable, n) && this.mLinksClickable && !this.textCanBeSelected()) {
            this.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void autofill(AutofillValue object) {
        if (((AutofillValue)object).isText() && this.isTextEditable()) {
            this.setText(((AutofillValue)object).getTextValue(), this.mBufferType, true, 0);
            object = this.getText();
            if (object instanceof Spannable) {
                Selection.setSelection((Spannable)object, object.length());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object);
        stringBuilder.append(" could not be autofilled into ");
        stringBuilder.append(this);
        Log.w(LOG_TAG, stringBuilder.toString());
    }

    public void beginBatchEdit() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.beginBatchEdit();
        }
    }

    public boolean bringPointIntoView(int n) {
        int n2;
        int n3;
        if (this.isLayoutRequested()) {
            this.mDeferScroll = n;
            return false;
        }
        Layout layout2 = this.isShowingHint() ? this.mHintLayout : this.mLayout;
        if (layout2 == null) {
            return false;
        }
        int n4 = layout2.getLineForOffset(n);
        int n5 = 4.$SwitchMap$android$text$Layout$Alignment[layout2.getParagraphAlignment(n4).ordinal()];
        boolean bl = true;
        n5 = n5 != 1 ? (n5 != 2 ? (n5 != 3 ? (n5 != 4 ? 0 : -layout2.getParagraphDirection(n4)) : layout2.getParagraphDirection(n4)) : -1) : 1;
        if (n5 <= 0) {
            bl = false;
        }
        int n6 = (int)layout2.getPrimaryHorizontal(n, bl);
        int n7 = layout2.getLineTop(n4);
        int n8 = layout2.getLineTop(n4 + 1);
        int n9 = (int)Math.floor(layout2.getLineLeft(n4));
        n = (int)Math.ceil(layout2.getLineRight(n4));
        int n10 = layout2.getHeight();
        int n11 = this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
        int n12 = this.mBottom - this.mTop - this.getExtendedPaddingTop() - this.getExtendedPaddingBottom();
        int n13 = n;
        if (!this.mHorizontallyScrolling) {
            n13 = n;
            if (n - n9 > n11) {
                n13 = n;
                if (n > n6) {
                    n13 = Math.max(n6, n9 + n11);
                }
            }
        }
        n = (n8 - n7) / 2;
        int n14 = n12 / 4;
        int n15 = n3 = n;
        if (n3 > n14) {
            n15 = n12 / 4;
        }
        n3 = n;
        if (n > n11 / 4) {
            n3 = n11 / 4;
        }
        n14 = this.mScrollX;
        n = n2 = this.mScrollY;
        if (n7 - n2 < n15) {
            n = n7 - n15;
        }
        n15 = n8 - n > n12 - n15 ? n8 - (n12 - n15) : n;
        n = n15;
        if (n10 - n15 < n12) {
            n = n10 - n12;
        }
        n15 = n;
        if (0 - n > 0) {
            n15 = 0;
        }
        n = n14;
        if (n5 != 0) {
            n = n14;
            if (n6 - n14 < n3) {
                n = n6 - n3;
            }
            if (n6 - n > n11 - n3) {
                n = n6 - (n11 - n3);
            }
        }
        if (n5 < 0) {
            n5 = n;
            if (n9 - n > 0) {
                n5 = n9;
            }
            n = n5;
            if (n13 - n5 < n11) {
                n = n13 - n11;
            }
        } else if (n5 > 0) {
            n5 = n;
            if (n13 - n < n11) {
                n5 = n13 - n11;
            }
            n = n5;
            if (n9 - n5 > 0) {
                n = n9;
            }
        } else if (n13 - n9 <= n11) {
            n = n9 - (n11 - (n13 - n9)) / 2;
        } else if (n6 > n13 - n3) {
            n = n13 - n11;
        } else if (n6 < n9 + n3) {
            n = n9;
        } else if (n9 > n) {
            n = n9;
        } else if (n13 < n + n11) {
            n = n13 - n11;
        } else {
            n5 = n;
            if (n6 - n < n3) {
                n5 = n6 - n3;
            }
            n = n6 - n5 > n11 - n3 ? n6 - (n11 - n3) : n5;
        }
        if (n == this.mScrollX && n15 == this.mScrollY) {
            bl = false;
        } else {
            if (this.mScroller == null) {
                this.scrollTo(n, n15);
            } else {
                long l = AnimationUtils.currentAnimationTimeMillis();
                long l2 = this.mLastScroll;
                n -= this.mScrollX;
                n5 = n15 - this.mScrollY;
                if (l - l2 > 250L) {
                    this.mScroller.startScroll(this.mScrollX, this.mScrollY, n, n5);
                    this.awakenScrollBars(this.mScroller.getDuration());
                    this.invalidate();
                } else {
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }
                    this.scrollBy(n, n5);
                }
                this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
            }
            bl = true;
        }
        boolean bl2 = bl;
        if (this.isFocused()) {
            if (this.mTempRect == null) {
                this.mTempRect = new Rect();
            }
            this.mTempRect.set(n6 - 2, n7, n6 + 2, n8);
            this.getInterestingRect(this.mTempRect, n4);
            this.mTempRect.offset(this.mScrollX, this.mScrollY);
            bl2 = bl;
            if (this.requestRectangleOnScreen(this.mTempRect)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    boolean canCopy() {
        if (this.hasPasswordTransformationMethod()) {
            return false;
        }
        return this.mText.length() > 0 && this.hasSelection() && this.mEditor != null;
    }

    boolean canCut() {
        Editor editor;
        if (this.hasPasswordTransformationMethod()) {
            return false;
        }
        return this.mText.length() > 0 && this.hasSelection() && this.mText instanceof Editable && (editor = this.mEditor) != null && editor.mKeyListener != null;
    }

    @UnsupportedAppUsage
    boolean canPaste() {
        Editor editor;
        boolean bl = this.mText instanceof Editable && (editor = this.mEditor) != null && editor.mKeyListener != null && this.getSelectionStart() >= 0 && this.getSelectionEnd() >= 0 && this.getClipboardManagerForUser().hasPrimaryClip();
        return bl;
    }

    boolean canPasteAsPlainText() {
        if (!this.canPaste()) {
            return false;
        }
        Object object = this.getClipboardManagerForUser().getPrimaryClip();
        ClipDescription clipDescription = ((ClipData)object).getDescription();
        boolean bl = clipDescription.hasMimeType("text/plain");
        object = ((ClipData)object).getItemAt(0).getText();
        if (bl && object instanceof Spanned && TextUtils.hasStyleSpan((Spanned)object)) {
            return true;
        }
        return clipDescription.hasMimeType("text/html");
    }

    boolean canProcessText() {
        if (this.getId() == -1) {
            return false;
        }
        return this.canShare();
    }

    boolean canRedo() {
        Editor editor = this.mEditor;
        boolean bl = editor != null && editor.canRedo();
        return bl;
    }

    boolean canRequestAutofill() {
        if (!this.isAutofillable()) {
            return false;
        }
        AutofillManager autofillManager = this.mContext.getSystemService(AutofillManager.class);
        if (autofillManager != null) {
            return autofillManager.isEnabled();
        }
        return false;
    }

    boolean canSelectAllText() {
        boolean bl = this.canSelectText() && !this.hasPasswordTransformationMethod() && (this.getSelectionStart() != 0 || this.getSelectionEnd() != this.mText.length());
        return bl;
    }

    boolean canSelectText() {
        Editor editor;
        boolean bl = this.mText.length() != 0 && (editor = this.mEditor) != null && editor.hasSelectionController();
        return bl;
    }

    boolean canShare() {
        if (this.getContext().canStartActivityForResult() && this.isDeviceProvisioned()) {
            return this.canCopy();
        }
        return false;
    }

    boolean canUndo() {
        Editor editor = this.mEditor;
        boolean bl = editor != null && editor.canUndo();
        return bl;
    }

    @Override
    public void cancelLongPress() {
        super.cancelLongPress();
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mIgnoreActionUpEvent = true;
        }
    }

    public void clearComposingText() {
        if (this.mText instanceof Spannable) {
            BaseInputConnection.removeComposingSpans(this.mSpannable);
        }
    }

    @Override
    protected int computeHorizontalScrollRange() {
        Layout layout2 = this.mLayout;
        if (layout2 != null) {
            int n = this.mSingleLine && (this.mGravity & 7) == 3 ? (int)layout2.getLineWidth(0) : this.mLayout.getWidth();
            return n;
        }
        return super.computeHorizontalScrollRange();
    }

    @Override
    public void computeScroll() {
        Scroller scroller = this.mScroller;
        if (scroller != null && scroller.computeScrollOffset()) {
            this.mScrollX = this.mScroller.getCurrX();
            this.mScrollY = this.mScroller.getCurrY();
            this.invalidateParentCaches();
            this.postInvalidate();
        }
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return this.getHeight() - this.getCompoundPaddingTop() - this.getCompoundPaddingBottom();
    }

    @Override
    protected int computeVerticalScrollRange() {
        Layout layout2 = this.mLayout;
        if (layout2 != null) {
            return layout2.getHeight();
        }
        return super.computeVerticalScrollRange();
    }

    float convertToLocalHorizontalCoordinate(float f) {
        f = Math.max(0.0f, f - (float)this.getTotalPaddingLeft());
        return Math.min((float)(this.getWidth() - this.getTotalPaddingRight() - 1), f) + (float)this.getScrollX();
    }

    @Override
    public void debug(int n) {
        super.debug(n);
        CharSequence charSequence = TextView.debugIndent(n);
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("frame={");
        ((StringBuilder)charSequence2).append(this.mLeft);
        ((StringBuilder)charSequence2).append(", ");
        ((StringBuilder)charSequence2).append(this.mTop);
        ((StringBuilder)charSequence2).append(", ");
        ((StringBuilder)charSequence2).append(this.mRight);
        ((StringBuilder)charSequence2).append(", ");
        ((StringBuilder)charSequence2).append(this.mBottom);
        ((StringBuilder)charSequence2).append("} scroll={");
        ((StringBuilder)charSequence2).append(this.mScrollX);
        ((StringBuilder)charSequence2).append(", ");
        ((StringBuilder)charSequence2).append(this.mScrollY);
        ((StringBuilder)charSequence2).append("} ");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        if (this.mText != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("mText=\"");
            ((StringBuilder)charSequence).append((Object)this.mText);
            ((StringBuilder)charSequence).append("\" ");
            charSequence = ((StringBuilder)charSequence).toString();
            charSequence2 = charSequence;
            if (this.mLayout != null) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("mLayout width=");
                ((StringBuilder)charSequence2).append(this.mLayout.getWidth());
                ((StringBuilder)charSequence2).append(" height=");
                ((StringBuilder)charSequence2).append(this.mLayout.getHeight());
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("mText=NULL");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        Log.d("View", (String)charSequence2);
    }

    @UnsupportedAppUsage
    protected void deleteText_internal(int n, int n2) {
        ((Editable)this.mText).delete(n, n2);
    }

    public boolean didTouchFocusSelect() {
        Editor editor = this.mEditor;
        boolean bl = editor != null && editor.mTouchFocusSelected;
        return bl;
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable[] arrdrawable = this.mDrawables;
        if (arrdrawable != null) {
            for (Drawable drawable2 : arrdrawable.mShowing) {
                if (drawable2 == null) continue;
                drawable2.setHotspot(f, f2);
            }
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable[] arrdrawable = this.mTextColor;
        if (arrdrawable != null && arrdrawable.isStateful() || (arrdrawable = this.mHintTextColor) != null && arrdrawable.isStateful() || (arrdrawable = this.mLinkTextColor) != null && arrdrawable.isStateful()) {
            this.updateTextColors();
        }
        if (this.mDrawables != null) {
            int[] arrn = this.getDrawableState();
            for (Drawable drawable2 : this.mDrawables.mShowing) {
                if (drawable2 == null || !drawable2.isStateful() || !drawable2.setState(arrn)) continue;
                this.invalidateDrawable(drawable2);
            }
        }
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        Object object = this.getEllipsize();
        Object var3_3 = null;
        object = object == null ? null : ((Enum)object).name();
        viewHierarchyEncoder.addProperty("text:ellipsize", (String)object);
        viewHierarchyEncoder.addProperty("text:textSize", this.getTextSize());
        viewHierarchyEncoder.addProperty("text:scaledTextSize", this.getScaledTextSize());
        viewHierarchyEncoder.addProperty("text:typefaceStyle", this.getTypefaceStyle());
        viewHierarchyEncoder.addProperty("text:selectionStart", this.getSelectionStart());
        viewHierarchyEncoder.addProperty("text:selectionEnd", this.getSelectionEnd());
        viewHierarchyEncoder.addProperty("text:curTextColor", this.mCurTextColor);
        object = this.mText;
        object = object == null ? var3_3 : object.toString();
        viewHierarchyEncoder.addProperty("text:text", (String)object);
        viewHierarchyEncoder.addProperty("text:gravity", this.mGravity);
    }

    public void endBatchEdit() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.endBatchEdit();
        }
    }

    public boolean extractText(ExtractedTextRequest extractedTextRequest, ExtractedText extractedText) {
        this.createEditorIfNeeded();
        return this.mEditor.extractText(extractedTextRequest, extractedText);
    }

    @Override
    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int n) {
        super.findViewsWithText(arrayList, charSequence, n);
        if (!(arrayList.contains(this) || (n & 1) == 0 || TextUtils.isEmpty(charSequence) || TextUtils.isEmpty(this.mText))) {
            charSequence = charSequence.toString().toLowerCase();
            if (this.mText.toString().toLowerCase().contains(charSequence)) {
                arrayList.add(this);
            }
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TextView.class.getName();
    }

    @Override
    public int getAccessibilitySelectionEnd() {
        return this.getSelectionEnd();
    }

    @Override
    public int getAccessibilitySelectionStart() {
        return this.getSelectionStart();
    }

    public final int getAutoLinkMask() {
        return this.mAutoLinkMask;
    }

    public int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    public int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    public int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx);
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    @Override
    public int getAutofillType() {
        return (int)this.isTextEditable();
    }

    @Override
    public AutofillValue getAutofillValue() {
        if (this.isTextEditable()) {
            return AutofillValue.forText(TextUtils.trimToParcelableSize(this.getText()));
        }
        return null;
    }

    @Override
    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return this.getBaselineOffset() + this.mLayout.getLineBaseline(0);
    }

    int getBaselineOffset() {
        int n = 0;
        if ((this.mGravity & 112) != 48) {
            n = this.getVerticalOffset(true);
        }
        int n2 = n;
        if (TextView.isLayoutModeOptical(this.mParent)) {
            n2 = n - this.getOpticalInsets().top;
        }
        return this.getExtendedPaddingTop() + n2;
    }

    @Override
    protected int getBottomPaddingOffset() {
        return (int)Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    final ClipboardManager getClipboardManagerForUser() {
        return this.getServiceManagerForUser(this.getContext().getPackageName(), ClipboardManager.class);
    }

    public int getCompoundDrawablePadding() {
        Drawables drawables = this.mDrawables;
        int n = drawables != null ? drawables.mDrawablePadding : 0;
        return n;
    }

    public BlendMode getCompoundDrawableTintBlendMode() {
        Object object = this.mDrawables;
        object = object != null ? object.mBlendMode : null;
        return object;
    }

    public ColorStateList getCompoundDrawableTintList() {
        Object object = this.mDrawables;
        object = object != null ? ((Drawables)object).mTintList : null;
        return object;
    }

    public PorterDuff.Mode getCompoundDrawableTintMode() {
        Enum enum_ = this.getCompoundDrawableTintBlendMode();
        enum_ = enum_ != null ? BlendMode.blendModeToPorterDuffMode((BlendMode)enum_) : null;
        return enum_;
    }

    public Drawable[] getCompoundDrawables() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return (Drawable[])drawables.mShowing.clone();
        }
        return new Drawable[]{null, null, null, null};
    }

    public Drawable[] getCompoundDrawablesRelative() {
        Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return new Drawable[]{drawables.mDrawableStart, drawables.mShowing[1], drawables.mDrawableEnd, drawables.mShowing[3]};
        }
        return new Drawable[]{null, null, null, null};
    }

    public int getCompoundPaddingBottom() {
        Drawables drawables = this.mDrawables;
        if (drawables != null && drawables.mShowing[3] != null) {
            return this.mPaddingBottom + drawables.mDrawablePadding + drawables.mDrawableSizeBottom;
        }
        return this.mPaddingBottom;
    }

    public int getCompoundPaddingEnd() {
        this.resolveDrawables();
        if (this.getLayoutDirection() != 1) {
            return this.getCompoundPaddingRight();
        }
        return this.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingLeft() {
        Drawables drawables = this.mDrawables;
        if (drawables != null && drawables.mShowing[0] != null) {
            return this.mPaddingLeft + drawables.mDrawablePadding + drawables.mDrawableSizeLeft;
        }
        return this.mPaddingLeft;
    }

    public int getCompoundPaddingRight() {
        Drawables drawables = this.mDrawables;
        if (drawables != null && drawables.mShowing[2] != null) {
            return this.mPaddingRight + drawables.mDrawablePadding + drawables.mDrawableSizeRight;
        }
        return this.mPaddingRight;
    }

    public int getCompoundPaddingStart() {
        this.resolveDrawables();
        if (this.getLayoutDirection() != 1) {
            return this.getCompoundPaddingLeft();
        }
        return this.getCompoundPaddingRight();
    }

    public int getCompoundPaddingTop() {
        Drawables drawables = this.mDrawables;
        if (drawables != null && drawables.mShowing[1] != null) {
            return this.mPaddingTop + drawables.mDrawablePadding + drawables.mDrawableSizeTop;
        }
        return this.mPaddingTop;
    }

    public final int getCurrentHintTextColor() {
        int n = this.mHintTextColor != null ? this.mCurHintTextColor : this.mCurTextColor;
        return n;
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    public ActionMode.Callback getCustomInsertionActionModeCallback() {
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mCustomInsertionActionModeCallback;
        return object;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mCustomSelectionActionModeCallback;
        return object;
    }

    protected boolean getDefaultEditable() {
        return false;
    }

    protected MovementMethod getDefaultMovementMethod() {
        return null;
    }

    public Editable getEditableText() {
        CharSequence charSequence = this.mText;
        charSequence = charSequence instanceof Editable ? (Editable)charSequence : null;
        return charSequence;
    }

    @VisibleForTesting
    public final Editor getEditorForTesting() {
        return this.mEditor;
    }

    @ViewDebug.ExportedProperty
    public TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    public CharSequence getError() {
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mError;
        return object;
    }

    public int getExtendedPaddingBottom() {
        if (this.mMaxMode != 1) {
            return this.getCompoundPaddingBottom();
        }
        if (this.mLayout == null) {
            this.assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return this.getCompoundPaddingBottom();
        }
        int n = this.getCompoundPaddingTop();
        int n2 = this.getCompoundPaddingBottom();
        int n3 = this.getHeight() - n - n2;
        int n4 = this.mLayout.getLineTop(this.mMaximum);
        if (n4 >= n3) {
            return n2;
        }
        n = this.mGravity & 112;
        if (n == 48) {
            return n2 + n3 - n4;
        }
        if (n == 80) {
            return n2;
        }
        return (n3 - n4) / 2 + n2;
    }

    public int getExtendedPaddingTop() {
        if (this.mMaxMode != 1) {
            return this.getCompoundPaddingTop();
        }
        if (this.mLayout == null) {
            this.assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return this.getCompoundPaddingTop();
        }
        int n = this.getCompoundPaddingTop();
        int n2 = this.getCompoundPaddingBottom();
        n2 = this.getHeight() - n - n2;
        int n3 = this.mLayout.getLineTop(this.mMaximum);
        if (n3 >= n2) {
            return n;
        }
        int n4 = this.mGravity & 112;
        if (n4 == 48) {
            return n;
        }
        if (n4 == 80) {
            return n + n2 - n3;
        }
        return (n2 - n3) / 2 + n;
    }

    @Override
    protected int getFadeHeight(boolean bl) {
        Layout layout2 = this.mLayout;
        int n = layout2 != null ? layout2.getHeight() : 0;
        return n;
    }

    @Override
    protected int getFadeTop(boolean bl) {
        if (this.mLayout == null) {
            return 0;
        }
        int n = 0;
        if ((this.mGravity & 112) != 48) {
            n = this.getVerticalOffset(true);
        }
        int n2 = n;
        if (bl) {
            n2 = n + this.getTopPaddingOffset();
        }
        return this.getExtendedPaddingTop() + n2;
    }

    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    public int getFirstBaselineToTopHeight() {
        return this.getPaddingTop() - this.getPaint().getFontMetricsInt().top;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void getFocusedRect(Rect rect) {
        int n;
        if (this.mLayout == null) {
            super.getFocusedRect(rect);
            return;
        }
        int n2 = this.getSelectionEnd();
        if (n2 < 0) {
            super.getFocusedRect(rect);
            return;
        }
        int n3 = this.getSelectionStart();
        if (n3 >= 0 && n3 < n2) {
            int n4 = this.mLayout.getLineForOffset(n3);
            n = this.mLayout.getLineForOffset(n2);
            rect.top = this.mLayout.getLineTop(n4);
            rect.bottom = this.mLayout.getLineBottom(n);
            if (n4 == n) {
                rect.left = (int)this.mLayout.getPrimaryHorizontal(n3);
                rect.right = (int)this.mLayout.getPrimaryHorizontal(n2);
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(n3, n2, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                RectF rectF = TEMP_RECTF;
                // MONITORENTER : rectF
                this.mHighlightPath.computeBounds(TEMP_RECTF, true);
                rect.left = (int)TextView.TEMP_RECTF.left - 1;
                rect.right = (int)TextView.TEMP_RECTF.right + 1;
                // MONITOREXIT : rectF
            }
        } else {
            n = this.mLayout.getLineForOffset(n2);
            rect.top = this.mLayout.getLineTop(n);
            rect.bottom = this.mLayout.getLineBottom(n);
            rect.left = (int)this.mLayout.getPrimaryHorizontal(n2) - 2;
            rect.right = rect.left + 4;
        }
        n3 = this.getCompoundPaddingLeft();
        n2 = n = this.getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            n2 = n + this.getVerticalOffset(false);
        }
        rect.offset(n3, n2);
        n2 = this.getExtendedPaddingBottom();
        rect.bottom += n2;
    }

    public String getFontFeatureSettings() {
        return this.mTextPaint.getFontFeatureSettings();
    }

    public String getFontVariationSettings() {
        return this.mTextPaint.getFontVariationSettings();
    }

    public boolean getFreezesText() {
        return this.mFreezesText;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getHint() {
        return this.mHint;
    }

    @UnsupportedAppUsage
    final Layout getHintLayout() {
        return this.mHintLayout;
    }

    public final ColorStateList getHintTextColors() {
        return this.mHintTextColor;
    }

    public int getHorizontalOffsetForDrawables() {
        return 0;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean getHorizontallyScrolling() {
        return this.mHorizontallyScrolling;
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public int getImeActionId() {
        Editor editor = this.mEditor;
        int n = editor != null && editor.mInputContentType != null ? this.mEditor.mInputContentType.imeActionId : 0;
        return n;
    }

    public CharSequence getImeActionLabel() {
        Object object = this.mEditor;
        object = object != null && ((Editor)object).mInputContentType != null ? this.mEditor.mInputContentType.imeActionLabel : null;
        return object;
    }

    public LocaleList getImeHintLocales() {
        Editor editor = this.mEditor;
        if (editor == null) {
            return null;
        }
        if (editor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeHintLocales;
    }

    public int getImeOptions() {
        Editor editor = this.mEditor;
        int n = editor != null && editor.mInputContentType != null ? this.mEditor.mInputContentType.imeOptions : 0;
        return n;
    }

    public boolean getIncludeFontPadding() {
        return this.mIncludePad;
    }

    public Bundle getInputExtras(boolean bl) {
        if (this.mEditor == null && !bl) {
            return null;
        }
        this.createEditorIfNeeded();
        if (this.mEditor.mInputContentType == null) {
            if (!bl) {
                return null;
            }
            this.mEditor.createInputContentTypeIfNeeded();
        }
        if (this.mEditor.mInputContentType.extras == null) {
            if (!bl) {
                return null;
            }
            this.mEditor.mInputContentType.extras = new Bundle();
        }
        return this.mEditor.mInputContentType.extras;
    }

    public int getInputType() {
        Editor editor = this.mEditor;
        int n = editor == null ? 0 : editor.mInputType;
        return n;
    }

    @UnsupportedAppUsage
    @Override
    public CharSequence getIterableTextForAccessibility() {
        return this.mText;
    }

    @Override
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int n) {
        if (n != 4) {
            if (n == 16 && !TextUtils.isEmpty((Spannable)this.getIterableTextForAccessibility()) && this.getLayout() != null) {
                AccessibilityIterators.PageTextSegmentIterator pageTextSegmentIterator = AccessibilityIterators.PageTextSegmentIterator.getInstance();
                pageTextSegmentIterator.initialize(this);
                return pageTextSegmentIterator;
            }
        } else {
            Spannable spannable = (Spannable)this.getIterableTextForAccessibility();
            if (!TextUtils.isEmpty(spannable) && this.getLayout() != null) {
                AccessibilityIterators.LineTextSegmentIterator lineTextSegmentIterator = AccessibilityIterators.LineTextSegmentIterator.getInstance();
                lineTextSegmentIterator.initialize(spannable, this.getLayout());
                return lineTextSegmentIterator;
            }
        }
        return super.getIteratorForGranularity(n);
    }

    public int getJustificationMode() {
        return this.mJustificationMode;
    }

    public final KeyListener getKeyListener() {
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mKeyListener;
        return object;
    }

    public int getLastBaselineToBottomHeight() {
        return this.getPaddingBottom() + this.getPaint().getFontMetricsInt().bottom;
    }

    public final Layout getLayout() {
        return this.mLayout;
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        Marquee marquee;
        if (this.isMarqueeFadeEnabled() && (marquee = this.mMarquee) != null && !marquee.isStopped()) {
            marquee = this.mMarquee;
            if (marquee.shouldDrawLeftFade()) {
                return this.getHorizontalFadingEdgeStrength(marquee.getScroll(), 0.0f);
            }
            return 0.0f;
        }
        if (this.getLineCount() == 1) {
            float f = this.getLayout().getLineLeft(0);
            if (f > (float)this.mScrollX) {
                return 0.0f;
            }
            return this.getHorizontalFadingEdgeStrength(this.mScrollX, f);
        }
        return super.getLeftFadingEdgeStrength();
    }

    @Override
    protected int getLeftPaddingOffset() {
        return this.getCompoundPaddingLeft() - this.mPaddingLeft + (int)Math.min(0.0f, this.mShadowDx - this.mShadowRadius);
    }

    public float getLetterSpacing() {
        return this.mTextPaint.getLetterSpacing();
    }

    @UnsupportedAppUsage
    int getLineAtCoordinate(float f) {
        f = Math.max(0.0f, f - (float)this.getTotalPaddingTop());
        f = Math.min((float)(this.getHeight() - this.getTotalPaddingBottom() - 1), f);
        float f2 = this.getScrollY();
        return this.getLayout().getLineForVertical((int)(f + f2));
    }

    int getLineAtCoordinateUnclamped(float f) {
        float f2 = this.getTotalPaddingTop();
        float f3 = this.getScrollY();
        return this.getLayout().getLineForVertical((int)(f - f2 + f3));
    }

    public int getLineBounds(int n, Rect rect) {
        int n2;
        Layout layout2 = this.mLayout;
        if (layout2 == null) {
            if (rect != null) {
                rect.set(0, 0, 0, 0);
            }
            return 0;
        }
        int n3 = layout2.getLineBounds(n, rect);
        n = n2 = this.getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            n = n2 + this.getVerticalOffset(true);
        }
        if (rect != null) {
            rect.offset(this.getCompoundPaddingLeft(), n);
        }
        return n3 + n;
    }

    public int getLineCount() {
        Layout layout2 = this.mLayout;
        int n = layout2 != null ? layout2.getLineCount() : 0;
        return n;
    }

    public int getLineHeight() {
        return FastMath.round((float)this.mTextPaint.getFontMetricsInt(null) * this.mSpacingMult + this.mSpacingAdd);
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public final ColorStateList getLinkTextColors() {
        return this.mLinkTextColor;
    }

    public final boolean getLinksClickable() {
        return this.mLinksClickable;
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    public int getMaxEms() {
        int n = this.mMaxWidthMode == 1 ? this.mMaxWidth : -1;
        return n;
    }

    public int getMaxHeight() {
        int n = this.mMaxMode == 2 ? this.mMaximum : -1;
        return n;
    }

    public int getMaxLines() {
        int n = this.mMaxMode == 1 ? this.mMaximum : -1;
        return n;
    }

    public int getMaxWidth() {
        int n = this.mMaxWidthMode == 2 ? this.mMaxWidth : -1;
        return n;
    }

    public int getMinEms() {
        int n = this.mMinWidthMode == 1 ? this.mMinWidth : -1;
        return n;
    }

    public int getMinHeight() {
        int n = this.mMinMode == 2 ? this.mMinimum : -1;
        return n;
    }

    public int getMinLines() {
        int n = this.mMinMode == 1 ? this.mMinimum : -1;
        return n;
    }

    public int getMinWidth() {
        int n = this.mMinWidthMode == 2 ? this.mMinWidth : -1;
        return n;
    }

    public final MovementMethod getMovementMethod() {
        return this.mMovement;
    }

    int getOffsetAtCoordinate(int n, float f) {
        f = this.convertToLocalHorizontalCoordinate(f);
        return this.getLayout().getOffsetForHorizontal(n, f);
    }

    public int getOffsetForPosition(float f, float f2) {
        if (this.getLayout() == null) {
            return -1;
        }
        return this.getOffsetAtCoordinate(this.getLineAtCoordinate(f2), f);
    }

    public TextPaint getPaint() {
        return this.mTextPaint;
    }

    public int getPaintFlags() {
        return this.mTextPaint.getFlags();
    }

    public String getPrivateImeOptions() {
        Object object = this.mEditor;
        object = object != null && ((Editor)object).mInputContentType != null ? this.mEditor.mInputContentType.privateImeOptions : null;
        return object;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        Marquee marquee;
        if (this.isMarqueeFadeEnabled() && (marquee = this.mMarquee) != null && !marquee.isStopped()) {
            marquee = this.mMarquee;
            return this.getHorizontalFadingEdgeStrength(marquee.getMaxFadeScroll(), marquee.getScroll());
        }
        if (this.getLineCount() == 1) {
            float f = this.mScrollX + (this.getWidth() - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight());
            float f2 = this.getLayout().getLineRight(0);
            if (f2 < f) {
                return 0.0f;
            }
            return this.getHorizontalFadingEdgeStrength(f, f2);
        }
        return super.getRightFadingEdgeStrength();
    }

    @Override
    protected int getRightPaddingOffset() {
        return -(this.getCompoundPaddingRight() - this.mPaddingRight) + (int)Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
    }

    @ViewDebug.ExportedProperty(category="text")
    public float getScaledTextSize() {
        return this.mTextPaint.getTextSize() / this.mTextPaint.density;
    }

    String getSelectedText() {
        if (!this.hasSelection()) {
            return null;
        }
        int n = this.getSelectionStart();
        int n2 = this.getSelectionEnd();
        CharSequence charSequence = this.mText;
        charSequence = n > n2 ? charSequence.subSequence(n2, n) : charSequence.subSequence(n, n2);
        return String.valueOf(charSequence);
    }

    @ViewDebug.ExportedProperty(category="text")
    public int getSelectionEnd() {
        return Selection.getSelectionEnd(this.getText());
    }

    @ViewDebug.ExportedProperty(category="text")
    public int getSelectionStart() {
        return Selection.getSelectionStart(this.getText());
    }

    final <T> T getServiceManagerForUser(String string2, Class<T> class_) {
        if (this.mTextOperationUser == null) {
            return this.getContext().getSystemService(class_);
        }
        try {
            string2 = this.getContext().createPackageContextAsUser(string2, 0, this.mTextOperationUser).getSystemService(class_);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
        return (T)string2;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public final boolean getShowSoftInputOnFocus() {
        Editor editor = this.mEditor;
        boolean bl = editor == null || editor.mShowSoftInputOnFocus;
        return bl;
    }

    public Locale getSpellCheckerLocale() {
        return this.getTextServicesLocale(true);
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getText() {
        return this.mText;
    }

    TextClassificationContext getTextClassificationContext() {
        return this.mTextClassificationContext;
    }

    TextClassifier getTextClassificationSession() {
        Object object = this.mTextClassificationSession;
        if (object == null || object.isDestroyed()) {
            TextClassificationManager textClassificationManager = this.mContext.getSystemService(TextClassificationManager.class);
            if (textClassificationManager != null) {
                object = this.isTextEditable() ? "edittext" : (this.isTextSelectable() ? "textview" : "nosel-textview");
                this.mTextClassificationContext = new TextClassificationContext.Builder(this.mContext.getPackageName(), (String)object).build();
                object = this.mTextClassifier;
                this.mTextClassificationSession = object != null ? textClassificationManager.createTextClassificationSession(this.mTextClassificationContext, (TextClassifier)object) : textClassificationManager.createTextClassificationSession(this.mTextClassificationContext);
            } else {
                this.mTextClassificationSession = TextClassifier.NO_OP;
            }
        }
        return this.mTextClassificationSession;
    }

    public TextClassifier getTextClassifier() {
        Object object = this.mTextClassifier;
        if (object == null) {
            object = this.mContext.getSystemService(TextClassificationManager.class);
            if (object != null) {
                return ((TextClassificationManager)object).getTextClassifier();
            }
            return TextClassifier.NO_OP;
        }
        return object;
    }

    public final ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public Drawable getTextCursorDrawable() {
        if (this.mCursorDrawable == null && this.mCursorDrawableRes != 0) {
            this.mCursorDrawable = this.mContext.getDrawable(this.mCursorDrawableRes);
        }
        return this.mCursorDrawable;
    }

    public TextDirectionHeuristic getTextDirectionHeuristic() {
        if (this.hasPasswordTransformationMethod()) {
            return TextDirectionHeuristics.LTR;
        }
        Object object = this.mEditor;
        byte by = 0;
        if (object != null && (((Editor)object).mInputType & 15) == 3) {
            by = Character.getDirectionality(DecimalFormatSymbols.getInstance((Locale)this.getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (by != 1 && by != 2) {
                return TextDirectionHeuristics.LTR;
            }
            return TextDirectionHeuristics.RTL;
        }
        if (this.getLayoutDirection() == 1) {
            by = 1;
        }
        switch (this.getTextDirection()) {
            default: {
                object = by != 0 ? TextDirectionHeuristics.FIRSTSTRONG_RTL : TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
            case 7: {
                return TextDirectionHeuristics.FIRSTSTRONG_RTL;
            }
            case 6: {
                return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
            case 5: {
                return TextDirectionHeuristics.LOCALE;
            }
            case 4: {
                return TextDirectionHeuristics.RTL;
            }
            case 3: {
                return TextDirectionHeuristics.LTR;
            }
            case 2: {
                return TextDirectionHeuristics.ANYRTL_LTR;
            }
        }
        return object;
    }

    public Locale getTextLocale() {
        return this.mTextPaint.getTextLocale();
    }

    public LocaleList getTextLocales() {
        return this.mTextPaint.getTextLocales();
    }

    public PrecomputedText.Params getTextMetricsParams() {
        return new PrecomputedText.Params(new TextPaint(this.mTextPaint), this.getTextDirectionHeuristic(), this.mBreakStrategy, this.mHyphenationFrequency);
    }

    public float getTextScaleX() {
        return this.mTextPaint.getTextScaleX();
    }

    public Drawable getTextSelectHandle() {
        if (this.mTextSelectHandle == null && this.mTextSelectHandleRes != 0) {
            this.mTextSelectHandle = this.mContext.getDrawable(this.mTextSelectHandleRes);
        }
        return this.mTextSelectHandle;
    }

    public Drawable getTextSelectHandleLeft() {
        if (this.mTextSelectHandleLeft == null && this.mTextSelectHandleLeftRes != 0) {
            this.mTextSelectHandleLeft = this.mContext.getDrawable(this.mTextSelectHandleLeftRes);
        }
        return this.mTextSelectHandleLeft;
    }

    public Drawable getTextSelectHandleRight() {
        if (this.mTextSelectHandleRight == null && this.mTextSelectHandleRightRes != 0) {
            this.mTextSelectHandleRight = this.mContext.getDrawable(this.mTextSelectHandleRightRes);
        }
        return this.mTextSelectHandleRight;
    }

    public Locale getTextServicesLocale() {
        return this.getTextServicesLocale(false);
    }

    final TextServicesManager getTextServicesManagerForUser() {
        return this.getServiceManagerForUser("android", TextServicesManager.class);
    }

    @ViewDebug.ExportedProperty(category="text")
    public float getTextSize() {
        return this.mTextPaint.getTextSize();
    }

    @Override
    protected int getTopPaddingOffset() {
        return (int)Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
    }

    public int getTotalPaddingBottom() {
        return this.getExtendedPaddingBottom() + this.getBottomVerticalOffset(true);
    }

    public int getTotalPaddingEnd() {
        return this.getCompoundPaddingEnd();
    }

    public int getTotalPaddingLeft() {
        return this.getCompoundPaddingLeft();
    }

    public int getTotalPaddingRight() {
        return this.getCompoundPaddingRight();
    }

    public int getTotalPaddingStart() {
        return this.getCompoundPaddingStart();
    }

    public int getTotalPaddingTop() {
        return this.getExtendedPaddingTop() + this.getVerticalOffset(true);
    }

    public final TransformationMethod getTransformationMethod() {
        return this.mTransformation;
    }

    @VisibleForTesting
    public CharSequence getTransformed() {
        return this.mTransformed;
    }

    @UnsupportedAppUsage
    CharSequence getTransformedText(int n, int n2) {
        return this.removeSuggestionSpans(this.mTransformed.subSequence(n, n2));
    }

    public Typeface getTypeface() {
        return this.mTextPaint.getTypeface();
    }

    @ViewDebug.ExportedProperty(category="text", mapping={@ViewDebug.IntToString(from=0, to="NORMAL"), @ViewDebug.IntToString(from=1, to="BOLD"), @ViewDebug.IntToString(from=2, to="ITALIC"), @ViewDebug.IntToString(from=3, to="BOLD_ITALIC")})
    public int getTypefaceStyle() {
        Typeface typeface = this.mTextPaint.getTypeface();
        int n = typeface != null ? typeface.getStyle() : 0;
        return n;
    }

    public final UndoManager getUndoManager() {
        throw new UnsupportedOperationException("not implemented");
    }

    public URLSpan[] getUrls() {
        CharSequence charSequence = this.mText;
        if (charSequence instanceof Spanned) {
            return ((Spanned)charSequence).getSpans(0, charSequence.length(), URLSpan.class);
        }
        return new URLSpan[0];
    }

    @UnsupportedAppUsage
    int getVerticalOffset(boolean bl) {
        Layout layout2;
        int n = 0;
        int n2 = this.mGravity & 112;
        Layout layout3 = layout2 = this.mLayout;
        if (!bl) {
            layout3 = layout2;
            if (this.mText.length() == 0) {
                layout3 = layout2;
                if (this.mHintLayout != null) {
                    layout3 = this.mHintLayout;
                }
            }
        }
        int n3 = n;
        if (n2 != 48) {
            int n4 = this.getBoxHeight(layout3);
            int n5 = layout3.getHeight();
            n3 = n;
            if (n5 < n4) {
                n3 = n2 == 80 ? n4 - n5 : n4 - n5 >> 1;
            }
        }
        return n3;
    }

    public WordIterator getWordIterator() {
        Editor editor = this.mEditor;
        if (editor != null) {
            return editor.getWordIterator();
        }
        return null;
    }

    public boolean handleBackInTextActionModeIfNeeded(KeyEvent keyEvent) {
        Object object = this.mEditor;
        if (object != null && ((Editor)object).getTextActionMode() != null) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                object = this.getKeyDispatcherState();
                if (object != null) {
                    ((KeyEvent.DispatcherState)object).startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                object = this.getKeyDispatcherState();
                if (object != null) {
                    ((KeyEvent.DispatcherState)object).handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.stopTextActionMode();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean handleClick(TextLinks.TextLinkSpan textLinkSpan) {
        Preconditions.checkNotNull(textLinkSpan);
        Object object = this.mText;
        if (object instanceof Spanned) {
            object = (Spanned)object;
            int n = object.getSpanStart(textLinkSpan);
            int n2 = object.getSpanEnd(textLinkSpan);
            if (n >= 0 && n2 <= this.mText.length() && n < n2) {
                object = new _$$Lambda$TextView$DJlzb7VS7J_1890Kto7GAApQDN0(this, new TextClassification.Request.Builder(this.mText, n, n2).setDefaultLocales(this.getTextLocales()).build());
                textLinkSpan = _$$Lambda$TextView$jQz3_DIfGrNeNdu_95_wi6UkW4E.INSTANCE;
                CompletableFuture.supplyAsync(object).completeOnTimeout(null, 1L, TimeUnit.SECONDS).thenAccept((Consumer)((Object)textLinkSpan));
                return true;
            }
        }
        return false;
    }

    void handleTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        sLastCutCopyOrTextChangedTime = 0L;
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mInputMethodState;
        if (object == null || ((Editor.InputMethodState)object).mBatchEditNesting == 0) {
            this.updateAfterEdit();
        }
        if (object != null) {
            ((Editor.InputMethodState)object).mContentChanged = true;
            if (((Editor.InputMethodState)object).mChangedStart < 0) {
                ((Editor.InputMethodState)object).mChangedStart = n;
                ((Editor.InputMethodState)object).mChangedEnd = n + n2;
            } else {
                ((Editor.InputMethodState)object).mChangedStart = Math.min(((Editor.InputMethodState)object).mChangedStart, n);
                ((Editor.InputMethodState)object).mChangedEnd = Math.max(((Editor.InputMethodState)object).mChangedEnd, n + n2 - ((Editor.InputMethodState)object).mChangedDelta);
            }
            ((Editor.InputMethodState)object).mChangedDelta += n3 - n2;
        }
        this.resetErrorChangedFlag();
        this.sendOnTextChanged(charSequence, n, n2, n3);
        this.onTextChanged(charSequence, n, n2, n3);
    }

    @Override
    public boolean hasOverlappingRendering() {
        boolean bl = this.getBackground() != null && this.getBackground().getCurrent() != null || this.mSpannable != null || this.hasSelection() || this.isHorizontalFadingEdgeEnabled() || this.mShadowColor != 0;
        return bl;
    }

    boolean hasPasswordTransformationMethod() {
        return this.mTransformation instanceof PasswordTransformationMethod;
    }

    public boolean hasSelection() {
        int n = this.getSelectionStart();
        int n2 = this.getSelectionEnd();
        boolean bl = n >= 0 && n2 > 0 && n != n2;
        return bl;
    }

    public void hideErrorIfUnchanged() {
        Editor editor = this.mEditor;
        if (editor != null && editor.mError != null && !this.mEditor.mErrorWasChanged) {
            this.setError(null, null);
        }
    }

    public void hideFloatingToolbar(int n) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.hideFloatingToolbar(n);
        }
    }

    void invalidateCursor() {
        int n = this.getSelectionEnd();
        this.invalidateCursor(n, n, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void invalidateCursorPath() {
        if (this.mHighlightPathBogus) {
            this.invalidateCursor();
            return;
        }
        int n = this.getCompoundPaddingLeft();
        int n2 = this.getExtendedPaddingTop() + this.getVerticalOffset(true);
        if (this.mEditor.mDrawableForCursor != null) {
            Rect rect = this.mEditor.mDrawableForCursor.getBounds();
            this.invalidate(rect.left + n, rect.top + n2, rect.right + n, rect.bottom + n2);
            return;
        }
        RectF rectF = TEMP_RECTF;
        synchronized (rectF) {
            float f;
            float f2 = f = (float)Math.ceil(this.mTextPaint.getStrokeWidth());
            if (f < 1.0f) {
                f2 = 1.0f;
            }
            this.mHighlightPath.computeBounds(TEMP_RECTF, false);
            this.invalidate((int)Math.floor((float)n + TextView.TEMP_RECTF.left - (f2 /= 2.0f)), (int)Math.floor((float)n2 + TextView.TEMP_RECTF.top - f2), (int)Math.ceil((float)n + TextView.TEMP_RECTF.right + f2), (int)Math.ceil((float)n2 + TextView.TEMP_RECTF.bottom + f2));
            return;
        }
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        if (this.verifyDrawable(drawable2)) {
            Rect rect = drawable2.getBounds();
            int n4 = this.mScrollX;
            n2 = this.mScrollY;
            Drawables drawables = this.mDrawables;
            int n5 = n4;
            int n6 = n2;
            if (drawables != null) {
                if (drawable2 == drawables.mShowing[0]) {
                    int n7 = this.getCompoundPaddingTop();
                    n6 = this.getCompoundPaddingBottom();
                    n3 = this.mBottom;
                    n = this.mTop;
                    n5 = n4 + this.mPaddingLeft;
                    n6 = n2 + ((n3 - n - n6 - n7 - drawables.mDrawableHeightLeft) / 2 + n7);
                    n = 1;
                } else if (drawable2 == drawables.mShowing[2]) {
                    n = this.getCompoundPaddingTop();
                    int n8 = this.getCompoundPaddingBottom();
                    n3 = this.mBottom;
                    n6 = this.mTop;
                    n5 = n4 + (this.mRight - this.mLeft - this.mPaddingRight - drawables.mDrawableSizeRight);
                    n6 = n2 + ((n3 - n6 - n8 - n - drawables.mDrawableHeightRight) / 2 + n);
                    n = 1;
                } else if (drawable2 == drawables.mShowing[1]) {
                    n = this.getCompoundPaddingLeft();
                    n5 = this.getCompoundPaddingRight();
                    n5 = n4 + ((this.mRight - this.mLeft - n5 - n - drawables.mDrawableWidthTop) / 2 + n);
                    n6 = n2 + this.mPaddingTop;
                    n = 1;
                } else {
                    n = n3;
                    n5 = n4;
                    n6 = n2;
                    if (drawable2 == drawables.mShowing[3]) {
                        n5 = this.getCompoundPaddingLeft();
                        n = this.getCompoundPaddingRight();
                        n5 = n4 + ((this.mRight - this.mLeft - n - n5 - drawables.mDrawableWidthBottom) / 2 + n5);
                        n6 = n2 + (this.mBottom - this.mTop - this.mPaddingBottom - drawables.mDrawableSizeBottom);
                        n = 1;
                    }
                }
            }
            n2 = n;
            if (n != 0) {
                this.invalidate(rect.left + n5, rect.top + n6, rect.right + n5, rect.bottom + n6);
                n2 = n;
            }
        }
        if (n2 == 0) {
            super.invalidateDrawable(drawable2);
        }
    }

    void invalidateRegion(int n, int n2, boolean bl) {
        Object object = this.mLayout;
        if (object == null) {
            this.invalidate();
        } else {
            int n3;
            int n4 = ((Layout)object).getLineForOffset(n);
            int n5 = n3 = this.mLayout.getLineTop(n4);
            if (n4 > 0) {
                n5 = n3 - this.mLayout.getLineDescent(n4 - 1);
            }
            int n6 = n == n2 ? n4 : this.mLayout.getLineForOffset(n2);
            int n7 = this.mLayout.getLineBottom(n6);
            int n8 = n5;
            n3 = n7;
            if (bl) {
                object = this.mEditor;
                n8 = n5;
                n3 = n7;
                if (object != null) {
                    n8 = n5;
                    n3 = n7;
                    if (((Editor)object).mDrawableForCursor != null) {
                        object = this.mEditor.mDrawableForCursor.getBounds();
                        n8 = Math.min(n5, ((Rect)object).top);
                        n3 = Math.max(n7, ((Rect)object).bottom);
                    }
                }
            }
            n5 = this.getCompoundPaddingLeft();
            n7 = this.getExtendedPaddingTop() + this.getVerticalOffset(true);
            if (n4 == n6 && !bl) {
                n6 = (int)this.mLayout.getPrimaryHorizontal(n);
                n = (int)((double)this.mLayout.getPrimaryHorizontal(n2) + 1.0);
                n2 = n6 + n5;
                n += n5;
            } else {
                n2 = n5;
                n = this.getWidth() - this.getCompoundPaddingRight();
            }
            this.invalidate(this.mScrollX + n2, n7 + n8, this.mScrollX + n, n7 + n3);
        }
    }

    @Override
    public boolean isAccessibilitySelectionExtendable() {
        return true;
    }

    public boolean isAllCaps() {
        TransformationMethod transformationMethod = this.getTransformationMethod();
        boolean bl = transformationMethod != null && transformationMethod instanceof AllCapsTransformationMethod;
        return bl;
    }

    public boolean isCursorVisible() {
        Editor editor = this.mEditor;
        boolean bl = editor == null ? true : editor.mCursorVisible;
        return bl;
    }

    boolean isDeviceProvisioned() {
        int n = this.mDeviceProvisionedState;
        boolean bl = true;
        if (n == 0) {
            n = Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0 ? 2 : 1;
            this.mDeviceProvisionedState = n;
        }
        if (this.mDeviceProvisionedState != 2) {
            bl = false;
        }
        return bl;
    }

    public boolean isElegantTextHeight() {
        return this.mTextPaint.isElegantTextHeight();
    }

    public boolean isFallbackLineSpacing() {
        return this.mUseFallbackLineSpacing;
    }

    public final boolean isHorizontallyScrollable() {
        return this.mHorizontallyScrolling;
    }

    boolean isInBatchEditMode() {
        Object object = this.mEditor;
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = ((Editor)object).mInputMethodState;
        if (object != null) {
            if (((Editor.InputMethodState)object).mBatchEditNesting > 0) {
                bl = true;
            }
            return bl;
        }
        return this.mEditor.mInBatchEditControllers;
    }

    public boolean isInExtractedMode() {
        return false;
    }

    public boolean isInputMethodTarget() {
        InputMethodManager inputMethodManager = this.getInputMethodManager();
        boolean bl = inputMethodManager != null && inputMethodManager.isActive(this);
        return bl;
    }

    @Override
    protected boolean isPaddingOffsetRequired() {
        boolean bl = this.mShadowRadius != 0.0f || this.mDrawables != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isPositionVisible(float f, float f2) {
        float[] arrf = TEMP_POSITION;
        synchronized (arrf) {
            float[] arrf2 = TEMP_POSITION;
            arrf2[0] = f;
            arrf2[1] = f2;
            Object object = this;
            do {
                if (object == null) {
                    return true;
                }
                if (object != this) {
                    arrf2[0] = arrf2[0] - (float)((View)object).getScrollX();
                    arrf2[1] = arrf2[1] - (float)((View)object).getScrollY();
                }
                if (arrf2[0] < 0.0f) return false;
                if (arrf2[1] < 0.0f) return false;
                if (arrf2[0] > (float)((View)object).getWidth()) return false;
                if (arrf2[1] > (float)((View)object).getHeight()) break;
                if (!((View)object).getMatrix().isIdentity()) {
                    ((View)object).getMatrix().mapPoints(arrf2);
                }
                arrf2[0] = arrf2[0] + (float)((View)object).getLeft();
                arrf2[1] = arrf2[1] + (float)((View)object).getTop();
                if (!((object = ((View)object).getParent()) instanceof View)) return true;
                object = (View)object;
            } while (true);
            return false;
        }
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    public boolean isSuggestionsEnabled() {
        Editor editor = this.mEditor;
        boolean bl = false;
        if (editor == null) {
            return false;
        }
        if ((editor.mInputType & 15) != 1) {
            return false;
        }
        if ((this.mEditor.mInputType & 524288) > 0) {
            return false;
        }
        int n = this.mEditor.mInputType & 4080;
        if (n == 0 || n == 48 || n == 80 || n == 64 || n == 160) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    boolean isTextEditable() {
        boolean bl = this.mText instanceof Editable && this.onCheckIsTextEditor() && this.isEnabled();
        return bl;
    }

    public boolean isTextSelectable() {
        Editor editor = this.mEditor;
        boolean bl = editor == null ? false : editor.mTextIsSelectable;
        return bl;
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable[] arrdrawable = this.mDrawables;
        if (arrdrawable != null) {
            for (Drawable drawable2 : arrdrawable.mShowing) {
                if (drawable2 == null) continue;
                drawable2.jumpToCurrentState();
            }
        }
    }

    public /* synthetic */ TextClassification lambda$handleClick$0$TextView(TextClassification.Request request) {
        return this.getTextClassifier().classifyText(request);
    }

    public int length() {
        return this.mText.length();
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public void makeNewLayout(int n, int n2, BoringLayout.Metrics object, BoringLayout.Metrics object2, int n3, boolean bl) {
        Object object3;
        this.stopMarquee();
        this.mOldMaximum = this.mMaximum;
        this.mOldMaxMode = this.mMaxMode;
        this.mHighlightPathBogus = true;
        if (n < 0) {
            n = 0;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        Layout.Alignment alignment = this.getLayoutAlignment();
        boolean bl2 = this.mSingleLine && this.mLayout != null && (alignment == Layout.Alignment.ALIGN_NORMAL || alignment == Layout.Alignment.ALIGN_OPPOSITE);
        int n4 = 0;
        if (bl2) {
            n4 = this.mLayout.getParagraphDirection(0);
        }
        boolean bl3 = this.mEllipsize != null && this.getKeyListener() == null;
        int n5 = this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 0 ? 1 : 0;
        Object object4 = this.mEllipsize;
        if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode == 1) {
            object4 = TextUtils.TruncateAt.END_SMALL;
        }
        if (this.mTextDir == null) {
            this.mTextDir = this.getTextDirectionHeuristic();
        }
        boolean bl4 = object4 == this.mEllipsize;
        this.mLayout = this.makeSingleLayout(n, (BoringLayout.Metrics)object, n3, alignment, bl3, (TextUtils.TruncateAt)((Object)object4), bl4);
        if (n5 != 0) {
            object3 = object4 == TextUtils.TruncateAt.MARQUEE ? TextUtils.TruncateAt.END : TextUtils.TruncateAt.MARQUEE;
            bl4 = object4 != this.mEllipsize;
            this.mSavedMarqueeModeLayout = this.makeSingleLayout(n, (BoringLayout.Metrics)object, n3, alignment, bl3, (TextUtils.TruncateAt)((Object)object3), bl4);
        }
        n5 = n4;
        object = alignment;
        n4 = this.mEllipsize != null ? 1 : 0;
        this.mHintLayout = null;
        if (this.mHint != null) {
            if (n4 != 0) {
                n2 = n;
            }
            if (object2 == UNKNOWN_BORING && (object2 = BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, this.mHintBoring)) != null) {
                this.mHintBoring = object2;
            }
            if (object2 != null) {
                if (((BoringLayout.Metrics)object2).width <= n2 && (n4 == 0 || ((BoringLayout.Metrics)object2).width <= n3)) {
                    object4 = this.mSavedHintLayout;
                    this.mHintLayout = object4 != null ? ((BoringLayout)object4).replaceOrMake(this.mHint, this.mTextPaint, n2, (Layout.Alignment)((Object)object), this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object2, this.mIncludePad) : BoringLayout.make(this.mHint, this.mTextPaint, n2, (Layout.Alignment)((Object)object), this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object2, this.mIncludePad);
                    this.mSavedHintLayout = (BoringLayout)this.mHintLayout;
                } else {
                    object4 = object;
                    if (n4 != 0 && ((BoringLayout.Metrics)object2).width <= n2) {
                        object3 = this.mSavedHintLayout;
                        this.mHintLayout = object3 != null ? ((BoringLayout)object3).replaceOrMake(this.mHint, this.mTextPaint, n2, (Layout.Alignment)((Object)object4), this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object2, this.mIncludePad, this.mEllipsize, n3) : BoringLayout.make(this.mHint, this.mTextPaint, n2, (Layout.Alignment)((Object)object4), this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object2, this.mIncludePad, this.mEllipsize, n3);
                    }
                }
            }
            n = 0;
            if (this.mHintLayout == null) {
                object2 = this.mHint;
                object = StaticLayout.Builder.obtain((CharSequence)object2, n, object2.length(), this.mTextPaint, n2).setAlignment((Layout.Alignment)((Object)object)).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode);
                n2 = this.mMaxMode;
                bl3 = true;
                n2 = n2 == 1 ? this.mMaximum : Integer.MAX_VALUE;
                object = ((StaticLayout.Builder)object).setMaxLines(n2);
                if (n4 != 0) {
                    ((StaticLayout.Builder)object).setEllipsize(this.mEllipsize).setEllipsizedWidth(n3);
                }
                this.mHintLayout = ((StaticLayout.Builder)object).build();
            } else {
                bl3 = true;
            }
        } else {
            n = 0;
            bl3 = true;
        }
        if (bl || bl2 && n5 != this.mLayout.getParagraphDirection(n)) {
            this.registerForPreDraw();
        }
        if (this.mEllipsize == TextUtils.TruncateAt.MARQUEE && !this.compressText(n3)) {
            n = this.mLayoutParams.height;
            if (n != -2 && n != -1) {
                this.startMarquee();
            } else {
                this.mRestartMarquee = bl3;
            }
        }
        if ((object = this.mEditor) != null) {
            ((Editor)object).prepareCursorControllers();
        }
    }

    protected Layout makeSingleLayout(int n, BoringLayout.Metrics object, int n2, Layout.Alignment alignment, boolean bl, TextUtils.TruncateAt truncateAt, boolean bl2) {
        Object object2 = null;
        if (this.useDynamicLayout()) {
            object2 = DynamicLayout.Builder.obtain(this.mText, this.mTextPaint, n).setDisplayText(this.mTransformed).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode);
            object = this.getKeyListener() == null ? truncateAt : null;
            object = ((DynamicLayout.Builder)object2).setEllipsize((TextUtils.TruncateAt)((Object)object)).setEllipsizedWidth(n2).build();
        } else {
            if (object == UNKNOWN_BORING && (object = BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, this.mBoring)) != null) {
                this.mBoring = object;
            }
            if (object != null) {
                if (((BoringLayout.Metrics)object).width <= n && (truncateAt == null || ((BoringLayout.Metrics)object).width <= n2)) {
                    object = bl2 && (object2 = this.mSavedLayout) != null ? ((BoringLayout)object2).replaceOrMake(this.mTransformed, this.mTextPaint, n, alignment, this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object, this.mIncludePad) : BoringLayout.make(this.mTransformed, this.mTextPaint, n, alignment, this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object, this.mIncludePad);
                    if (bl2) {
                        this.mSavedLayout = (BoringLayout)object;
                    }
                } else {
                    object = bl && ((BoringLayout.Metrics)object).width <= n ? (bl2 && (object2 = this.mSavedLayout) != null ? ((BoringLayout)object2).replaceOrMake(this.mTransformed, this.mTextPaint, n, alignment, this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object, this.mIncludePad, truncateAt, n2) : BoringLayout.make(this.mTransformed, this.mTextPaint, n, alignment, this.mSpacingMult, this.mSpacingAdd, (BoringLayout.Metrics)object, this.mIncludePad, truncateAt, n2)) : object2;
                }
            } else {
                object = object2;
            }
        }
        object2 = object;
        if (object == null) {
            object = this.mTransformed;
            object = StaticLayout.Builder.obtain((CharSequence)object, 0, object.length(), this.mTextPaint, n).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode);
            n = this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE;
            object = ((StaticLayout.Builder)object).setMaxLines(n);
            if (bl) {
                ((StaticLayout.Builder)object).setEllipsize(truncateAt).setEllipsizedWidth(n2);
            }
            object2 = ((StaticLayout.Builder)object).build();
        }
        return object2;
    }

    public boolean moveCursorToVisibleOffset() {
        int n;
        if (!(this.mText instanceof Spannable)) {
            return false;
        }
        int n2 = this.getSelectionStart();
        if (n2 != this.getSelectionEnd()) {
            return false;
        }
        int n3 = this.mLayout.getLineForOffset(n2);
        int n4 = this.mLayout.getLineTop(n3);
        int n5 = this.mLayout.getLineTop(n3 + 1);
        int n6 = this.mBottom - this.mTop - this.getExtendedPaddingTop() - this.getExtendedPaddingBottom();
        int n7 = n = (n5 - n4) / 2;
        if (n > n6 / 4) {
            n7 = n6 / 4;
        }
        if (n4 < (n = this.mScrollY) + n7) {
            n3 = this.mLayout.getLineForVertical(n + n7 + (n5 - n4));
        } else if (n5 > n6 + n - n7) {
            n3 = this.mLayout.getLineForVertical(n6 + n - n7 - (n5 - n4));
        }
        n5 = this.mRight;
        int n8 = this.mLeft;
        n4 = this.getCompoundPaddingLeft();
        n = this.getCompoundPaddingRight();
        n6 = this.mScrollX;
        n7 = this.mLayout.getOffsetForHorizontal(n3, n6);
        n = this.mLayout.getOffsetForHorizontal(n3, n5 - n8 - n4 - n + n6);
        n3 = n7 < n ? n7 : n;
        if (n7 <= n) {
            n7 = n;
        }
        n = n2;
        if (n >= n3) {
            n3 = n;
            if (n > n7) {
                n3 = n7;
            }
        }
        if (n3 != n2) {
            Selection.setSelection(this.mSpannable, n3);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    @VisibleForTesting
    public void nullLayouts() {
        Object object = this.mLayout;
        if (object instanceof BoringLayout && this.mSavedLayout == null) {
            this.mSavedLayout = (BoringLayout)object;
        }
        if ((object = this.mHintLayout) instanceof BoringLayout && this.mSavedHintLayout == null) {
            this.mSavedHintLayout = (BoringLayout)object;
        }
        this.mHintLayout = null;
        this.mLayout = null;
        this.mSavedMarqueeModeLayout = null;
        this.mHintBoring = null;
        this.mBoring = null;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).prepareCursorControllers();
        }
    }

    @Override
    public void onActivityResult(int n, int n2, Intent object) {
        if (n == 100) {
            if (n2 == -1 && object != null) {
                if ((object = ((Intent)object).getCharSequenceExtra("android.intent.extra.PROCESS_TEXT")) != null) {
                    if (this.isTextEditable()) {
                        this.replaceSelectionWithText((CharSequence)object);
                        object = this.mEditor;
                        if (object != null) {
                            ((Editor)object).refreshTextActionMode();
                        }
                    } else if (object.length() > 0) {
                        Toast.makeText(this.getContext(), String.valueOf(object), 1).show();
                    }
                }
            } else {
                object = this.mSpannable;
                if (object != null) {
                    Selection.setSelection((Spannable)object, this.getSelectionEnd());
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onAttachedToWindow();
        }
        if (this.mPreDrawListenerDetached) {
            this.getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawListenerDetached = false;
        }
    }

    public void onBeginBatchEdit() {
    }

    @Override
    public boolean onCheckIsTextEditor() {
        Editor editor = this.mEditor;
        boolean bl = editor != null && editor.mInputType != 0;
        return bl;
    }

    public void onCommitCompletion(CompletionInfo completionInfo) {
    }

    public void onCommitCorrection(CorrectionInfo correctionInfo) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onCommitCorrection(correctionInfo);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.mLocalesChanged) {
            this.mTextPaint.setTextLocales(LocaleList.getDefault());
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    @Override
    protected void onCreateContextMenu(ContextMenu contextMenu) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onCreateContextMenu(contextMenu);
        }
    }

    @Override
    protected int[] onCreateDrawableState(int n) {
        int[] arrn;
        if (this.mSingleLine) {
            arrn = super.onCreateDrawableState(n);
        } else {
            arrn = super.onCreateDrawableState(n + 1);
            TextView.mergeDrawableStates(arrn, MULTILINE_STATE_SET);
        }
        if (this.isTextSelectable()) {
            int n2 = arrn.length;
            for (n = 0; n < n2; ++n) {
                if (arrn[n] != 16842919) continue;
                int[] arrn2 = new int[n2 - 1];
                System.arraycopy(arrn, 0, arrn2, 0, n);
                System.arraycopy(arrn, n + 1, arrn2, n, n2 - n - 1);
                return arrn2;
            }
        }
        return arrn;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (this.onCheckIsTextEditor() && this.isEnabled()) {
            this.mEditor.createInputMethodStateIfNeeded();
            editorInfo.inputType = this.getInputType();
            if (this.mEditor.mInputContentType != null) {
                editorInfo.imeOptions = this.mEditor.mInputContentType.imeOptions;
                editorInfo.privateImeOptions = this.mEditor.mInputContentType.privateImeOptions;
                editorInfo.actionLabel = this.mEditor.mInputContentType.imeActionLabel;
                editorInfo.actionId = this.mEditor.mInputContentType.imeActionId;
                editorInfo.extras = this.mEditor.mInputContentType.extras;
                editorInfo.hintLocales = this.mEditor.mInputContentType.imeHintLocales;
            } else {
                editorInfo.imeOptions = 0;
                editorInfo.hintLocales = null;
            }
            if (this.focusSearch(130) != null) {
                editorInfo.imeOptions |= 134217728;
            }
            if (this.focusSearch(33) != null) {
                editorInfo.imeOptions |= 67108864;
            }
            if ((editorInfo.imeOptions & 255) == 0) {
                editorInfo.imeOptions = (editorInfo.imeOptions & 134217728) != 0 ? (editorInfo.imeOptions |= 5) : (editorInfo.imeOptions |= 6);
                if (!this.shouldAdvanceFocusOnEnter()) {
                    editorInfo.imeOptions |= 1073741824;
                }
            }
            if (TextView.isMultilineInputType(editorInfo.inputType)) {
                editorInfo.imeOptions |= 1073741824;
            }
            editorInfo.hintText = this.mHint;
            editorInfo.targetInputMethodUser = this.mTextOperationUser;
            if (this.mText instanceof Editable) {
                EditableInputConnection editableInputConnection = new EditableInputConnection(this);
                editorInfo.initialSelStart = this.getSelectionStart();
                editorInfo.initialSelEnd = this.getSelectionEnd();
                editorInfo.initialCapsMode = editableInputConnection.getCursorCapsMode(this.getInputType());
                return editableInputConnection;
            }
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindowInternal() {
        if (this.mPreDrawRegistered) {
            this.getViewTreeObserver().removeOnPreDrawListener(this);
            this.mPreDrawListenerDetached = true;
        }
        this.resetResolvedDrawables();
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onDetachedFromWindow();
        }
        super.onDetachedFromWindowInternal();
    }

    @Override
    public boolean onDragEvent(DragEvent object) {
        int n = ((DragEvent)object).getAction();
        boolean bl = true;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 5) {
                        return true;
                    }
                    this.requestFocus();
                    return true;
                }
                Editor editor = this.mEditor;
                if (editor != null) {
                    editor.onDrop((DragEvent)object);
                }
                return true;
            }
            if (this.mText instanceof Spannable) {
                n = this.getOffsetForPosition(((DragEvent)object).getX(), ((DragEvent)object).getY());
                Selection.setSelection(this.mSpannable, n);
            }
            return true;
        }
        object = this.mEditor;
        if (object == null || !((Editor)object).hasInsertionController()) {
            bl = false;
        }
        return bl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Object object;
        int n;
        this.restartMarqueeIfNeeded();
        super.onDraw(canvas);
        int n2 = this.getCompoundPaddingLeft();
        int n3 = this.getCompoundPaddingTop();
        int n4 = this.getCompoundPaddingRight();
        int n5 = this.getCompoundPaddingBottom();
        int n6 = this.mScrollX;
        int n7 = this.mScrollY;
        int n8 = this.mRight;
        int n9 = this.mLeft;
        int n10 = this.mBottom;
        int n11 = this.mTop;
        boolean bl = this.isLayoutRtl();
        int n12 = this.getHorizontalOffsetForDrawables();
        int n13 = bl ? 0 : n12;
        if (!bl) {
            n12 = 0;
        }
        Object object2 = this.mDrawables;
        if (object2 != null) {
            n = n10 - n11 - n5 - n3;
            n4 = n8 - n9 - n4 - n2;
            if (((Drawables)object2).mShowing[0] != null) {
                canvas.save();
                canvas.translate(this.mPaddingLeft + n6 + n13, n7 + n3 + (n - ((Drawables)object2).mDrawableHeightLeft) / 2);
                ((Drawables)object2).mShowing[0].draw(canvas);
                canvas.restore();
            }
            if (((Drawables)object2).mShowing[2] != null) {
                canvas.save();
                canvas.translate(n6 + n8 - n9 - this.mPaddingRight - ((Drawables)object2).mDrawableSizeRight - n12, n7 + n3 + (n - ((Drawables)object2).mDrawableHeightRight) / 2);
                ((Drawables)object2).mShowing[2].draw(canvas);
                canvas.restore();
            }
            if (((Drawables)object2).mShowing[1] != null) {
                canvas.save();
                canvas.translate(n6 + n2 + (n4 - ((Drawables)object2).mDrawableWidthTop) / 2, this.mPaddingTop + n7);
                ((Drawables)object2).mShowing[1].draw(canvas);
                canvas.restore();
            }
            if (((Drawables)object2).mShowing[3] != null) {
                canvas.save();
                canvas.translate(n6 + n2 + (n4 - ((Drawables)object2).mDrawableWidthBottom) / 2, n7 + n10 - n11 - this.mPaddingBottom - ((Drawables)object2).mDrawableSizeBottom);
                ((Drawables)object2).mShowing[3].draw(canvas);
                canvas.restore();
            }
        }
        n13 = this.mCurTextColor;
        if (this.mLayout == null) {
            this.assumeLayout();
        }
        object2 = this.mLayout;
        if (this.mHint != null && this.mText.length() == 0) {
            if (this.mHintTextColor != null) {
                n13 = this.mCurHintTextColor;
            }
            object2 = this.mHintLayout;
        }
        this.mTextPaint.setColor(n13);
        this.mTextPaint.drawableState = this.getDrawableState();
        canvas.save();
        n4 = this.getExtendedPaddingTop();
        n13 = this.getExtendedPaddingBottom();
        n12 = this.mBottom;
        int n14 = this.mTop;
        n = this.mLayout.getHeight();
        float f = n2 + n6;
        float f2 = n7 == 0 ? 0.0f : (float)(n4 + n7);
        float f3 = n8 - n9 - this.getCompoundPaddingRight() + n6;
        if (n7 == n - (n12 - n14 - n5 - n3)) {
            n13 = 0;
        }
        float f4 = n10 - n11 + n7 - n13;
        float f5 = this.mShadowRadius;
        if (f5 != 0.0f) {
            float f6 = Math.min(0.0f, this.mShadowDx - f5);
            float f7 = Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            f5 = Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            f4 += Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
            f3 += f7;
            f5 = f2 + f5;
            f2 = f + f6;
        } else {
            f5 = f2;
            f2 = f;
        }
        canvas.clipRect(f2, f5, f3, f4);
        n12 = this.mGravity;
        n13 = 0;
        if ((n12 & 112) != 48) {
            n13 = this.getVerticalOffset(false);
            n12 = this.getVerticalOffset(true);
        } else {
            n12 = 0;
        }
        canvas.translate(n2, n4 + n13);
        n2 = this.getLayoutDirection();
        n2 = Gravity.getAbsoluteGravity(this.mGravity, n2);
        if (this.isMarqueeFadeEnabled()) {
            if (!this.mSingleLine && this.getLineCount() == 1 && this.canMarquee() && (n2 & 7) != 3) {
                n10 = this.mRight;
                n11 = this.mLeft;
                n2 = this.getCompoundPaddingLeft();
                n7 = this.getCompoundPaddingRight();
                f4 = this.mLayout.getLineRight(0);
                f2 = n10 - n11 - (n2 + n7);
                canvas.translate((float)((Layout)object2).getParagraphDirection(0) * (f4 - f2), 0.0f);
            }
            if ((object = this.mMarquee) != null && ((Marquee)object).isRunning()) {
                f2 = -this.mMarquee.getScroll();
                canvas.translate((float)((Layout)object2).getParagraphDirection(0) * f2, 0.0f);
            }
        }
        n13 = n12 - n13;
        Object object3 = object = this.getUpdatedHighlightPath();
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onDraw(canvas, (Layout)object2, (Path)object3, this.mHighlightPaint, n13);
        } else {
            ((Layout)object2).draw(canvas, (Path)object3, this.mHighlightPaint, n13);
        }
        object3 = this.mMarquee;
        if (object3 != null && ((Marquee)object3).shouldDrawGhost()) {
            f2 = this.mMarquee.getGhostOffset();
            canvas.translate((float)((Layout)object2).getParagraphDirection(0) * f2, 0.0f);
            ((Layout)object2).draw(canvas, (Path)object, this.mHighlightPaint, n13);
        }
        canvas.restore();
    }

    public void onEditorAction(int n) {
        Object object = this.mEditor;
        object = object == null ? null : ((Editor)object).mInputContentType;
        if (object != null) {
            if (((Editor.InputContentType)object).onEditorActionListener != null && ((Editor.InputContentType)object).onEditorActionListener.onEditorAction(this, n, null)) {
                return;
            }
            if (n == 5) {
                object = this.focusSearch(2);
                if (object != null && !((View)object).requestFocus(2)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (n == 7) {
                object = this.focusSearch(1);
                if (object != null && !((View)object).requestFocus(1)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (n == 6) {
                object = this.getInputMethodManager();
                if (object != null && ((InputMethodManager)object).isActive(this)) {
                    ((InputMethodManager)object).hideSoftInputFromWindow(this.getWindowToken(), 0);
                }
                return;
            }
        }
        if ((object = this.getViewRootImpl()) != null) {
            long l = SystemClock.uptimeMillis();
            ((ViewRootImpl)object).dispatchKeyFromIme(new KeyEvent(l, l, 0, 66, 0, 0, -1, 0, 22));
            ((ViewRootImpl)object).dispatchKeyFromIme(new KeyEvent(SystemClock.uptimeMillis(), l, 1, 66, 0, 0, -1, 0, 22));
        }
    }

    public void onEndBatchEdit() {
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        if (this.isTemporarilyDetached()) {
            super.onFocusChanged(bl, n, rect);
            return;
        }
        Object object = this.mEditor;
        if (object != null) {
            ((Editor)object).onFocusChanged(bl, n);
        }
        if (bl && (object = this.mSpannable) != null) {
            MetaKeyKeyListener.resetMetaState((Spannable)object);
        }
        this.startStopMarquee(bl);
        object = this.mTransformation;
        if (object != null) {
            object.onFocusChanged(this, this.mText, bl, n, rect);
        }
        super.onFocusChanged(bl, n, rect);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod != null && this.mText instanceof Spannable && this.mLayout != null) {
            try {
                boolean bl = movementMethod.onGenericMotionEvent(this, this.mSpannable, motionEvent);
                if (bl) {
                    return true;
                }
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setPassword(this.hasPasswordTransformationMethod());
        if (accessibilityEvent.getEventType() == 8192) {
            accessibilityEvent.setFromIndex(Selection.getSelectionStart(this.mText));
            accessibilityEvent.setToIndex(Selection.getSelectionEnd(this.mText));
            accessibilityEvent.setItemCount(this.mText.length());
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        Editor object2;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setPassword(this.hasPasswordTransformationMethod());
        accessibilityNodeInfo.setText(this.getTextForAccessibility());
        accessibilityNodeInfo.setHintText(this.mHint);
        accessibilityNodeInfo.setShowingHintText(this.isShowingHint());
        if (this.mBufferType == BufferType.EDITABLE) {
            accessibilityNodeInfo.setEditable(true);
            if (this.isEnabled()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            }
        }
        if ((object2 = this.mEditor) != null) {
            accessibilityNodeInfo.setInputType(object2.mInputType);
            if (this.mEditor.mError != null) {
                accessibilityNodeInfo.setContentInvalid(true);
                accessibilityNodeInfo.setError(this.mEditor.mError);
            }
        }
        if (!TextUtils.isEmpty(this.mText)) {
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(31);
            accessibilityNodeInfo.addAction(131072);
            accessibilityNodeInfo.setAvailableExtraData(Arrays.asList("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY"));
        }
        if (this.isFocused()) {
            if (this.canCopy()) {
                accessibilityNodeInfo.addAction(16384);
            }
            if (this.canPaste()) {
                accessibilityNodeInfo.addAction(32768);
            }
            if (this.canCut()) {
                accessibilityNodeInfo.addAction(65536);
            }
            if (this.canShare()) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(268435456, this.getResources().getString(17041006)));
            }
            if (this.canProcessText()) {
                this.mEditor.mProcessTextIntentActionsHandler.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            }
        }
        for (InputFilter inputFilter : this.mFilters) {
            if (!(inputFilter instanceof InputFilter.LengthFilter)) continue;
            accessibilityNodeInfo.setMaxTextLength(((InputFilter.LengthFilter)inputFilter).getMax());
        }
        if (!this.isSingleLine()) {
            accessibilityNodeInfo.setMultiLine(true);
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.doKeyDown(n, keyEvent, null) == 0) {
            return super.onKeyDown(n, keyEvent);
        }
        return true;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        KeyEvent keyEvent2 = KeyEvent.changeAction(keyEvent, 0);
        int n3 = this.doKeyDown(n, keyEvent2, keyEvent);
        if (n3 == 0) {
            return super.onKeyMultiple(n, n2, keyEvent);
        }
        if (n3 == -1) {
            return true;
        }
        --n2;
        keyEvent = KeyEvent.changeAction(keyEvent, 1);
        if (n3 == 1) {
            this.mEditor.mKeyListener.onKeyUp(this, (Editable)this.mText, n, keyEvent);
            while (--n2 > 0) {
                this.mEditor.mKeyListener.onKeyDown(this, (Editable)this.mText, n, keyEvent2);
                this.mEditor.mKeyListener.onKeyUp(this, (Editable)this.mText, n, keyEvent);
            }
            this.hideErrorIfUnchanged();
        } else if (n3 == 2) {
            this.mMovement.onKeyUp(this, this.mSpannable, n, keyEvent);
            while (--n2 > 0) {
                this.mMovement.onKeyDown(this, this.mSpannable, n, keyEvent2);
                this.mMovement.onKeyUp(this, this.mSpannable, n, keyEvent);
            }
        }
        return true;
    }

    @Override
    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        if (n == 4 && this.handleBackInTextActionModeIfNeeded(keyEvent)) {
            return true;
        }
        return super.onKeyPreIme(n, keyEvent);
    }

    @Override
    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        if (keyEvent.hasModifiers(4096)) {
            if (n != 29) {
                if (n != 31) {
                    if (n != 50) {
                        if (n != 52) {
                            if (n == 54 && this.canUndo()) {
                                return this.onTextContextMenuItem(16908338);
                            }
                        } else if (this.canCut()) {
                            return this.onTextContextMenuItem(16908320);
                        }
                    } else if (this.canPaste()) {
                        return this.onTextContextMenuItem(16908322);
                    }
                } else if (this.canCopy()) {
                    return this.onTextContextMenuItem(16908321);
                }
            } else if (this.canSelectText()) {
                return this.onTextContextMenuItem(16908319);
            }
        } else if (keyEvent.hasModifiers(4097)) {
            if (n != 50) {
                if (n == 54 && this.canRedo()) {
                    return this.onTextContextMenuItem(16908339);
                }
            } else if (this.canPaste()) {
                return this.onTextContextMenuItem(16908337);
            }
        }
        return super.onKeyShortcut(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (!this.isEnabled()) {
            return super.onKeyUp(n, keyEvent);
        }
        if (!KeyEvent.isModifierKey(n)) {
            this.mPreventDefaultMovement = false;
        }
        if (n != 23) {
            if (n == 66 && keyEvent.hasNoModifiers()) {
                Object object = this.mEditor;
                if (object != null && ((Editor)object).mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.enterDown) {
                    this.mEditor.mInputContentType.enterDown = false;
                    if (this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, 0, keyEvent)) {
                        return true;
                    }
                }
                if (((keyEvent.getFlags() & 16) != 0 || this.shouldAdvanceFocusOnEnter()) && !this.hasOnClickListeners()) {
                    object = this.focusSearch(130);
                    if (object != null) {
                        if (((View)object).requestFocus(130)) {
                            super.onKeyUp(n, keyEvent);
                            return true;
                        }
                        throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                    }
                    if ((keyEvent.getFlags() & 16) != 0 && (object = this.getInputMethodManager()) != null && ((InputMethodManager)object).isActive(this)) {
                        ((InputMethodManager)object).hideSoftInputFromWindow(this.getWindowToken(), 0);
                    }
                }
                return super.onKeyUp(n, keyEvent);
            }
            Object object = this.mEditor;
            if (object != null && ((Editor)object).mKeyListener != null && this.mEditor.mKeyListener.onKeyUp(this, (Editable)this.mText, n, keyEvent)) {
                return true;
            }
            object = this.mMovement;
            if (object != null && this.mLayout != null && object.onKeyUp(this, this.mSpannable, n, keyEvent)) {
                return true;
            }
            return super.onKeyUp(n, keyEvent);
        }
        if (keyEvent.hasNoModifiers() && !this.hasOnClickListeners() && this.mMovement != null && this.mText instanceof Editable && this.mLayout != null && this.onCheckIsTextEditor()) {
            InputMethodManager inputMethodManager = this.getInputMethodManager();
            this.viewClicked(inputMethodManager);
            if (inputMethodManager != null && this.getShowSoftInputOnFocus()) {
                inputMethodManager.showSoftInput(this, 0);
            }
        }
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mDeferScroll >= 0) {
            n = this.mDeferScroll;
            this.mDeferScroll = -1;
            this.bringPointIntoView(Math.min(n, this.mText.length()));
        }
        this.autoSizeText();
    }

    void onLocaleChanged() {
        this.mEditor.onLocaleChanged();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        Object object;
        int n5 = View.MeasureSpec.getMode(n);
        int n6 = View.MeasureSpec.getMode(n2);
        int n7 = View.MeasureSpec.getSize(n);
        int n8 = View.MeasureSpec.getSize(n2);
        Object object2 = UNKNOWN_BORING;
        Object object3 = UNKNOWN_BORING;
        if (this.mTextDir == null) {
            this.mTextDir = this.getTextDirectionHeuristic();
        }
        n = -1;
        int n9 = 0;
        float f = n5 == Integer.MIN_VALUE ? (float)n7 : Float.MAX_VALUE;
        if (n5 == 1073741824) {
            object = object3;
            n2 = -1;
            n9 = 0;
            n = n7;
        } else {
            object = this.mLayout;
            n2 = n;
            if (object != null) {
                n2 = n;
                if (this.mEllipsize == null) {
                    n2 = TextView.desired((Layout)object);
                }
            }
            if (n2 < 0) {
                object2 = object = BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, this.mBoring);
                n = n9;
                if (object != null) {
                    this.mBoring = object;
                    object2 = object;
                    n = n9;
                }
            } else {
                n = 1;
            }
            if (object2 != null && object2 != UNKNOWN_BORING) {
                n4 = ((BoringLayout.Metrics)object2).width;
            } else {
                n9 = n2;
                if (n2 < 0) {
                    object = this.mTransformed;
                    n9 = (int)Math.ceil(Layout.getDesiredWidthWithLimit((CharSequence)object, 0, object.length(), this.mTextPaint, this.mTextDir, f));
                }
                n4 = n9;
                n2 = n9;
            }
            object = this.mDrawables;
            n9 = n4;
            if (object != null) {
                n9 = Math.max(Math.max(n4, ((Drawables)object).mDrawableWidthTop), ((Drawables)object).mDrawableWidthBottom);
            }
            object = object3;
            n3 = n9;
            if (this.mHint != null) {
                n3 = -1;
                object = this.mHintLayout;
                n4 = n3;
                if (object != null) {
                    n4 = n3;
                    if (this.mEllipsize == null) {
                        n4 = TextView.desired((Layout)object);
                    }
                }
                if (n4 < 0) {
                    object3 = object = BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, this.mHintBoring);
                    if (object != null) {
                        this.mHintBoring = object;
                        object3 = object;
                    }
                }
                if (object3 != null && object3 != UNKNOWN_BORING) {
                    n4 = ((BoringLayout.Metrics)object3).width;
                } else {
                    n3 = n4;
                    if (n4 < 0) {
                        object = this.mHint;
                        n3 = (int)Math.ceil(Layout.getDesiredWidthWithLimit((CharSequence)object, 0, object.length(), this.mTextPaint, this.mTextDir, f));
                    }
                    n4 = n3;
                }
                object = object3;
                n3 = n9;
                if (n4 > n9) {
                    n3 = n4;
                    object = object3;
                }
            }
            n9 = n3 + (this.getCompoundPaddingLeft() + this.getCompoundPaddingRight());
            n9 = this.mMaxWidthMode == 1 ? Math.min(n9, this.mMaxWidth * this.getLineHeight()) : Math.min(n9, this.mMaxWidth);
            n9 = this.mMinWidthMode == 1 ? Math.max(n9, this.mMinWidth * this.getLineHeight()) : Math.max(n9, this.mMinWidth);
            n4 = Math.max(n9, this.getSuggestedMinimumWidth());
            if (n5 == Integer.MIN_VALUE) {
                n4 = Math.min(n7, n4);
                n9 = n;
                n = n4;
            } else {
                n9 = n;
                n = n4;
            }
        }
        n4 = n7 = n - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight();
        if (this.mHorizontallyScrolling) {
            n4 = 1048576;
        }
        n3 = (object3 = this.mHintLayout) == null ? n4 : ((Layout)object3).getWidth();
        object3 = this.mLayout;
        if (object3 == null) {
            this.makeNewLayout(n4, n4, (BoringLayout.Metrics)object2, (BoringLayout.Metrics)object, n - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight(), false);
        } else {
            n3 = ((Layout)object3).getWidth() == n4 && n3 == n4 && this.mLayout.getEllipsizedWidth() == n - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight() ? 0 : 1;
            n2 = this.mHint == null && this.mEllipsize == null && n4 > this.mLayout.getWidth() && (this.mLayout instanceof BoringLayout || n9 != 0 && n2 >= 0 && n2 <= n4) ? 1 : 0;
            n9 = this.mMaxMode == this.mOldMaxMode && this.mMaximum == this.mOldMaximum ? 0 : 1;
            if (n3 != 0 || n9 != 0) {
                if (n9 == 0 && n2 != 0) {
                    this.mLayout.increaseWidthTo(n4);
                } else {
                    this.makeNewLayout(n4, n4, (BoringLayout.Metrics)object2, (BoringLayout.Metrics)object, n - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight(), false);
                }
            }
        }
        if (n6 == 1073741824) {
            this.mDesiredHeightAtMeasure = -1;
            n2 = n8;
        } else {
            n2 = n9 = this.getDesiredHeight();
            this.mDesiredHeightAtMeasure = n9;
            if (n6 == Integer.MIN_VALUE) {
                n2 = Math.min(n9, n8);
            }
        }
        n9 = n4 = n2 - this.getCompoundPaddingTop() - this.getCompoundPaddingBottom();
        if (this.mMaxMode == 1) {
            n8 = this.mLayout.getLineCount();
            n3 = this.mMaximum;
            n9 = n4;
            if (n8 > n3) {
                n9 = Math.min(n4, this.mLayout.getLineTop(n3));
            }
        }
        if (this.mMovement == null && this.mLayout.getWidth() <= n7 && this.mLayout.getHeight() <= n9) {
            this.scrollTo(0, 0);
        } else {
            this.registerForPreDraw();
        }
        this.setMeasuredDimension(n, n2);
    }

    @Override
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        CharSequence charSequence = this.getTextForAccessibility();
        if (!TextUtils.isEmpty(charSequence)) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    @Override
    public boolean onPreDraw() {
        Editor editor;
        if (this.mLayout == null) {
            this.assumeLayout();
        }
        if (this.mMovement != null) {
            int n = this.getSelectionEnd();
            editor = this.mEditor;
            int n2 = n;
            if (editor != null) {
                n2 = n;
                if (editor.mSelectionModifierCursorController != null) {
                    n2 = n;
                    if (this.mEditor.mSelectionModifierCursorController.isSelectionStartDragged()) {
                        n2 = this.getSelectionStart();
                    }
                }
            }
            n = n2;
            if (n2 < 0) {
                n = n2;
                if ((this.mGravity & 112) == 80) {
                    n = this.mText.length();
                }
            }
            if (n >= 0) {
                this.bringPointIntoView(n);
            }
        } else {
            this.bringTextIntoView();
        }
        editor = this.mEditor;
        if (editor != null && editor.mCreatedWithASelection) {
            this.mEditor.refreshTextActionMode();
            this.mEditor.mCreatedWithASelection = false;
        }
        this.unregisterForPreDraw();
        return true;
    }

    public boolean onPrivateIMECommand(String string2, Bundle bundle) {
        return false;
    }

    @Override
    protected void onProvideStructure(ViewStructure viewStructure, int n, int n2) {
        block33 : {
            Object object;
            int n3;
            int n4;
            Object object2;
            block35 : {
                int n5;
                int n6;
                Layout layout2;
                int n7;
                int n8;
                block37 : {
                    int n9;
                    block36 : {
                        block34 : {
                            block32 : {
                                super.onProvideStructure(viewStructure, n, n2);
                                n2 = !this.hasPasswordTransformationMethod() && !TextView.isPasswordInputType(this.getInputType()) ? 0 : 1;
                                if (n == 1) {
                                    if (n == 1) {
                                        viewStructure.setDataIsSensitive(this.mTextSetFromXmlOrResourceId ^ true);
                                    }
                                    if (this.mTextId != 0) {
                                        try {
                                            viewStructure.setTextIdEntry(this.getResources().getResourceEntryName(this.mTextId));
                                        }
                                        catch (Resources.NotFoundException notFoundException) {
                                            if (!Helper.sVerbose) break block32;
                                            object2 = new StringBuilder();
                                            object2.append("onProvideAutofillStructure(): cannot set name for text id ");
                                            object2.append(this.mTextId);
                                            object2.append(": ");
                                            object2.append(notFoundException.getMessage());
                                            Log.v(LOG_TAG, object2.toString());
                                        }
                                    }
                                }
                            }
                            if (n2 != 0 && n != 1) break block33;
                            if (this.mLayout == null) {
                                this.assumeLayout();
                            }
                            if ((n9 = (layout2 = this.mLayout).getLineCount()) > 1) break block34;
                            object = this.getText();
                            if (n == 1) {
                                viewStructure.setText((CharSequence)object);
                            } else {
                                viewStructure.setText((CharSequence)object, this.getSelectionStart(), this.getSelectionEnd());
                            }
                            break block35;
                        }
                        object = new int[2];
                        this.getLocationInWindow((int[])object);
                        n4 = object[1];
                        object = this;
                        object2 = this.getParent();
                        while (object2 instanceof View) {
                            object = (View)object2;
                            object2 = ((View)object).getParent();
                        }
                        n3 = ((View)object).getHeight();
                        if (n4 >= 0) {
                            n2 = this.getLineAtCoordinateUnclamped(0.0f);
                            n4 = this.getLineAtCoordinateUnclamped(n3 - 1);
                        } else {
                            n2 = this.getLineAtCoordinateUnclamped(-n4);
                            n4 = this.getLineAtCoordinateUnclamped(n3 - 1 - n4);
                        }
                        n8 = n2 - (n4 - n2) / 2;
                        if (n8 < 0) {
                            n8 = 0;
                        }
                        n3 = n6 = n4 + (n4 - n2) / 2;
                        if (n6 >= n9) {
                            n3 = n9 - 1;
                        }
                        n8 = layout2.getLineStart(n8);
                        int n10 = layout2.getLineEnd(n3);
                        n5 = this.getSelectionStart();
                        n7 = this.getSelectionEnd();
                        n9 = n10;
                        n6 = n8;
                        if (n5 < n7) {
                            n3 = n8;
                            if (n5 < n8) {
                                n3 = n5;
                            }
                            n9 = n10;
                            n6 = n3;
                            if (n7 > n10) {
                                n9 = n7;
                                n6 = n3;
                            }
                        }
                        object2 = this.getText();
                        if (n6 > 0) break block36;
                        object = object2;
                        if (n9 >= object2.length()) break block37;
                    }
                    object = object2.subSequence(n6, n9);
                }
                if (n == 1) {
                    viewStructure.setText((CharSequence)object);
                } else {
                    viewStructure.setText((CharSequence)object, n5 - n6, n7 - n6);
                    object = new int[n4 - n2 + 1];
                    object2 = new int[n4 - n2 + 1];
                    n8 = this.getBaselineOffset();
                    for (n3 = n2; n3 <= n4; ++n3) {
                        object[n3 - n2] = layout2.getLineStart(n3);
                        object2[n3 - n2] = layout2.getLineBaseline(n3) + n8;
                    }
                    viewStructure.setTextLines((int[])object, (int[])object2);
                }
            }
            if (n == 0) {
                n3 = 0;
                n4 = this.getTypefaceStyle();
                if ((n4 & 1) != 0) {
                    n3 = false | true;
                }
                n2 = n3;
                if ((n4 & 2) != 0) {
                    n2 = n3 | 2;
                }
                n4 = this.mTextPaint.getFlags();
                n3 = n2;
                if ((n4 & 32) != 0) {
                    n3 = n2 | 1;
                }
                n2 = n3;
                if ((n4 & 8) != 0) {
                    n2 = n3 | 4;
                }
                n3 = n2;
                if ((n4 & 16) != 0) {
                    n3 = n2 | 8;
                }
                viewStructure.setTextStyle(this.getTextSize(), this.getCurrentTextColor(), 1, n3);
            }
            if (n == 1) {
                viewStructure.setMinTextEms(this.getMinEms());
                viewStructure.setMaxTextEms(this.getMaxEms());
                n3 = -1;
                object = this.getFilters();
                n4 = ((Object)object).length;
                n2 = 0;
                do {
                    n = n3;
                    if (n2 >= n4) break;
                    object2 = object[n2];
                    if (object2 instanceof InputFilter.LengthFilter) {
                        n = ((InputFilter.LengthFilter)object2).getMax();
                        break;
                    }
                    ++n2;
                } while (true);
                viewStructure.setMaxTextLength(n);
            }
        }
        viewStructure.setHint(this.getHint());
        viewStructure.setInputType(this.getInputType());
    }

    @Override
    public void onResolveDrawables(int n) {
        if (this.mLastLayoutDirection == n) {
            return;
        }
        this.mLastLayoutDirection = n;
        Drawables drawables = this.mDrawables;
        if (drawables != null && drawables.resolveWithLayoutDirection(n)) {
            this.prepareDrawableForDisplay(this.mDrawables.mShowing[0]);
            this.prepareDrawableForDisplay(this.mDrawables.mShowing[2]);
            this.applyCompoundDrawableTint();
        }
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        int n2;
        if (this.mSpannable != null && this.mLinksClickable && this.mSpannable.getSpans(n2 = this.getOffsetForPosition(motionEvent.getX(n), motionEvent.getY(n)), n2, ClickableSpan.class).length > 0) {
            return PointerIcon.getSystemIcon(this.mContext, 1002);
        }
        if (!this.isTextSelectable() && !this.isTextEditable()) {
            return super.onResolvePointerIcon(motionEvent, n);
        }
        return PointerIcon.getSystemIcon(this.mContext, 1008);
    }

    @Override
    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        SavedState savedState = (SavedState)object;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.text != null) {
            this.setText(savedState.text);
        }
        if (savedState.selStart >= 0 && savedState.selEnd >= 0 && this.mSpannable != null) {
            int n = this.mText.length();
            if (savedState.selStart <= n && savedState.selEnd <= n) {
                Selection.setSelection(this.mSpannable, savedState.selStart, savedState.selEnd);
                if (savedState.frozenWithFocus) {
                    this.createEditorIfNeeded();
                    this.mEditor.mFrozenWithFocus = true;
                }
            } else {
                object = "";
                if (savedState.text != null) {
                    object = "(restored) ";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Saved cursor position ");
                stringBuilder.append(savedState.selStart);
                stringBuilder.append("/");
                stringBuilder.append(savedState.selEnd);
                stringBuilder.append(" out of range for ");
                stringBuilder.append((String)object);
                stringBuilder.append("text ");
                stringBuilder.append((Object)this.mText);
                Log.e(LOG_TAG, stringBuilder.toString());
            }
        }
        if (savedState.error != null) {
            this.post(new Runnable(savedState.error){
                final /* synthetic */ CharSequence val$error;
                {
                    this.val$error = charSequence;
                }

                @Override
                public void run() {
                    if (TextView.this.mEditor == null || !TextView.access$100((TextView)TextView.this).mErrorWasChanged) {
                        TextView.this.setError(this.val$error);
                    }
                }
            });
        }
        if (savedState.editorState != null) {
            this.createEditorIfNeeded();
            this.mEditor.restoreInstanceState(savedState.editorState);
        }
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        TextDirectionHeuristic textDirectionHeuristic = this.getTextDirectionHeuristic();
        if (this.mTextDir != textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            if (this.mLayout != null) {
                this.checkForRelayout();
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable parcelable;
        Object object;
        int n;
        boolean bl;
        boolean bl2;
        int n2;
        block11 : {
            int n3;
            int n4;
            block12 : {
                parcelable = super.onSaveInstanceState();
                bl = this.getFreezesText();
                boolean bl3 = false;
                n2 = -1;
                n = -1;
                bl2 = bl3;
                if (this.mText == null) break block11;
                n4 = this.getSelectionStart();
                n3 = this.getSelectionEnd();
                if (n4 >= 0) break block12;
                bl2 = bl3;
                n2 = n4;
                n = n3;
                if (n3 < 0) break block11;
            }
            bl2 = true;
            n = n3;
            n2 = n4;
        }
        if (!bl && !bl2) {
            return parcelable;
        }
        parcelable = new SavedState(parcelable);
        if (bl) {
            object = this.mText;
            if (object instanceof Spanned) {
                object = new SpannableStringBuilder((CharSequence)object);
                if (this.mEditor != null) {
                    this.removeMisspelledSpans((Spannable)object);
                    object.removeSpan(this.mEditor.mSuggestionRangeSpan);
                }
                ((SavedState)parcelable).text = object;
            } else {
                ((SavedState)parcelable).text = object.toString();
            }
        }
        if (bl2) {
            ((SavedState)parcelable).selStart = n2;
            ((SavedState)parcelable).selEnd = n;
        }
        if (this.isFocused() && n2 >= 0 && n >= 0) {
            ((SavedState)parcelable).frozenWithFocus = true;
        }
        ((SavedState)parcelable).error = this.getError();
        object = this.mEditor;
        if (object != null) {
            ((SavedState)parcelable).editorState = ((Editor)object).saveInstanceState();
        }
        return parcelable;
    }

    @Override
    public void onScreenStateChanged(int n) {
        super.onScreenStateChanged(n);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onScreenStateChanged(n);
        }
    }

    @Override
    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onScrollChanged();
        }
    }

    protected void onSelectionChanged(int n, int n2) {
        this.sendAccessibilityEvent(8192);
    }

    protected void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public boolean onTextContextMenuItem(int n) {
        int n2 = 0;
        int n3 = this.mText.length();
        if (this.isFocused()) {
            int n4 = this.getSelectionStart();
            n3 = this.getSelectionEnd();
            n2 = Math.max(0, Math.min(n4, n3));
            n3 = Math.max(0, Math.max(n4, n3));
        }
        if (n != 16908355) {
            switch (n) {
                default: {
                    switch (n) {
                        default: {
                            return false;
                        }
                        case 16908341: {
                            this.shareSelectedText();
                            return true;
                        }
                        case 16908340: {
                            Editor editor = this.mEditor;
                            if (editor != null) {
                                editor.replace();
                            }
                            return true;
                        }
                        case 16908339: {
                            Editor editor = this.mEditor;
                            if (editor != null) {
                                editor.redo();
                            }
                            return true;
                        }
                        case 16908338: {
                            Editor editor = this.mEditor;
                            if (editor != null) {
                                editor.undo();
                            }
                            return true;
                        }
                        case 16908337: 
                    }
                    this.paste(n2, n3, false);
                    return true;
                }
                case 16908322: {
                    this.paste(n2, n3, true);
                    return true;
                }
                case 16908321: {
                    n2 = this.getSelectionStart();
                    n = this.getSelectionEnd();
                    if (this.setPrimaryClip(ClipData.newPlainText(null, this.getTransformedText(Math.max(0, Math.min(n2, n)), Math.max(0, Math.max(n2, n)))))) {
                        this.stopTextActionMode();
                    } else {
                        Toast.makeText(this.getContext(), 17039998, 0).show();
                    }
                    return true;
                }
                case 16908320: {
                    if (this.setPrimaryClip(ClipData.newPlainText(null, this.getTransformedText(n2, n3)))) {
                        this.deleteText_internal(n2, n3);
                    } else {
                        Toast.makeText(this.getContext(), 17039998, 0).show();
                    }
                    return true;
                }
                case 16908319: 
            }
            boolean bl = this.hasSelection();
            this.selectAllText();
            Editor editor = this.mEditor;
            if (editor != null && bl) {
                editor.invalidateActionModeAsync();
            }
            return true;
        }
        this.requestAutofill();
        this.stopTextActionMode();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent object) {
        boolean bl;
        block15 : {
            int n;
            block16 : {
                Object object2;
                block17 : {
                    int n2 = ((MotionEvent)object).getActionMasked();
                    object2 = this.mEditor;
                    if (object2 != null) {
                        object2.onTouchEvent((MotionEvent)object);
                        if (this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isDragAcceleratorActive()) {
                            return true;
                        }
                    }
                    bl = super.onTouchEvent((MotionEvent)object);
                    object2 = this.mEditor;
                    if (object2 != null && object2.mDiscardNextActionUp && n2 == 1) {
                        object = this.mEditor;
                        ((Editor)object).mDiscardNextActionUp = false;
                        if (((Editor)object).mIsInsertionActionModeStartPending) {
                            this.mEditor.startInsertionActionMode();
                            this.mEditor.mIsInsertionActionModeStartPending = false;
                        }
                        return bl;
                    }
                    boolean bl2 = n2 == 1 && ((object2 = this.mEditor) == null || !object2.mIgnoreActionUpEvent) && this.isFocused();
                    if (this.mMovement == null && !this.onCheckIsTextEditor() || !this.isEnabled() || !(this.mText instanceof Spannable) || this.mLayout == null) break block15;
                    n = 0;
                    object2 = this.mMovement;
                    if (object2 != null) {
                        n = false | object2.onTouchEvent(this, this.mSpannable, (MotionEvent)object);
                    }
                    boolean bl3 = this.isTextSelectable();
                    n2 = n;
                    if (bl2) {
                        n2 = n;
                        if (this.mLinksClickable) {
                            n2 = n;
                            if (this.mAutoLinkMask != 0) {
                                n2 = n;
                                if (bl3) {
                                    object2 = this.mSpannable.getSpans(this.getSelectionStart(), this.getSelectionEnd(), ClickableSpan.class);
                                    n2 = n;
                                    if (((ClickableSpan[])object2).length > 0) {
                                        object2[0].onClick(this);
                                        n2 = 1;
                                    }
                                }
                            }
                        }
                    }
                    n = n2;
                    if (!bl2) break block16;
                    if (this.isTextEditable()) break block17;
                    n = n2;
                    if (!bl3) break block16;
                }
                object2 = this.getInputMethodManager();
                this.viewClicked((InputMethodManager)object2);
                if (this.isTextEditable() && this.mEditor.mShowSoftInputOnFocus && object2 != null) {
                    object2.showSoftInput(this, 0);
                }
                this.mEditor.onTouchUpEvent((MotionEvent)object);
                n = 1;
            }
            if (n != 0) {
                return true;
            }
        }
        return bl;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        Spannable spannable;
        MovementMethod movementMethod = this.mMovement;
        if (movementMethod != null && (spannable = this.mSpannable) != null && this.mLayout != null && movementMethod.onTrackballEvent(this, spannable, motionEvent)) {
            return true;
        }
        return super.onTrackballEvent(motionEvent);
    }

    @Override
    protected void onVisibilityChanged(View object, int n) {
        super.onVisibilityChanged((View)object, n);
        object = this.mEditor;
        if (object != null && n != 0) {
            ((Editor)object).hideCursorAndSpanControllers();
            this.stopTextActionMode();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.onWindowFocusChanged(bl);
        }
        this.startStopMarquee(bl);
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle object) {
        Object object2 = this.mEditor;
        if (object2 != null && ((Editor)object2).mProcessTextIntentActionsHandler.performAccessibilityAction(n)) {
            return true;
        }
        if (n != 16) {
            if (n != 256 && n != 512) {
                if (n != 16384) {
                    if (n != 32768) {
                        if (n != 65536) {
                            if (n != 131072) {
                                if (n != 2097152) {
                                    if (n != 268435456) {
                                        return super.performAccessibilityActionInternal(n, (Bundle)object);
                                    }
                                    return this.isFocused() && this.canShare() && this.onTextContextMenuItem(16908341);
                                }
                                if (this.isEnabled() && this.mBufferType == BufferType.EDITABLE) {
                                    object = object != null ? ((Bundle)object).getCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE") : null;
                                    this.setText((CharSequence)object);
                                    object = this.mText;
                                    if (object != null && (n = object.length()) > 0) {
                                        Selection.setSelection(this.mSpannable, n);
                                    }
                                    return true;
                                }
                                return false;
                            }
                            this.ensureIterableTextForAccessibilitySelectable();
                            object2 = this.getIterableTextForAccessibility();
                            if (object2 == null) {
                                return false;
                            }
                            n = object != null ? ((BaseBundle)object).getInt("ACTION_ARGUMENT_SELECTION_START_INT", -1) : -1;
                            int n2 = object != null ? ((BaseBundle)object).getInt("ACTION_ARGUMENT_SELECTION_END_INT", -1) : -1;
                            if (this.getSelectionStart() != n || this.getSelectionEnd() != n2) {
                                if (n == n2 && n2 == -1) {
                                    Selection.removeSelection((Spannable)object2);
                                    return true;
                                }
                                if (n >= 0 && n <= n2 && n2 <= object2.length()) {
                                    Selection.setSelection((Spannable)object2, n, n2);
                                    object = this.mEditor;
                                    if (object != null) {
                                        ((Editor)object).startSelectionActionModeAsync(false);
                                    }
                                    return true;
                                }
                            }
                            return false;
                        }
                        return this.isFocused() && this.canCut() && this.onTextContextMenuItem(16908320);
                    }
                    return this.isFocused() && this.canPaste() && this.onTextContextMenuItem(16908322);
                }
                return this.isFocused() && this.canCopy() && this.onTextContextMenuItem(16908321);
            }
            this.ensureIterableTextForAccessibilitySelectable();
            return super.performAccessibilityActionInternal(n, (Bundle)object);
        }
        return this.performAccessibilityActionClick((Bundle)object);
    }

    @Override
    public boolean performLongClick() {
        boolean bl = false;
        boolean bl2 = false;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mIsBeingLongClicked = true;
        }
        if (super.performLongClick()) {
            bl = true;
            bl2 = true;
        }
        editor = this.mEditor;
        boolean bl3 = bl;
        if (editor != null) {
            bl3 = bl | editor.performLongClick(bl);
            this.mEditor.mIsBeingLongClicked = false;
        }
        if (bl3) {
            if (!bl2) {
                this.performHapticFeedback(0);
            }
            if ((editor = this.mEditor) != null) {
                editor.mDiscardNextActionUp = true;
            }
        } else {
            MetricsLogger.action(this.mContext, 629, 0);
        }
        return bl3;
    }

    public void populateCharacterBounds(CursorAnchorInfo.Builder builder, int n, int n2, float f, float f2) {
        int n3 = this.mLayout.getLineForOffset(n);
        int n4 = this.mLayout.getLineForOffset(n2 - 1);
        for (int i = n3; i <= n4; ++i) {
            int n5 = this.mLayout.getLineStart(i);
            int n6 = this.mLayout.getLineEnd(i);
            int n7 = Math.max(n5, n);
            int n8 = Math.min(n6, n2);
            n6 = this.mLayout.getParagraphDirection(i);
            boolean bl = true;
            if (n6 != 1) {
                bl = false;
            }
            float[] arrf = new float[n8 - n7];
            this.mLayout.getPaint().getTextWidths(this.mTransformed, n7, n8, arrf);
            float f3 = this.mLayout.getLineTop(i);
            float f4 = this.mLayout.getLineBottom(i);
            for (int j = n7; j < n8; ++j) {
                float f5;
                boolean bl2;
                float f6;
                float f7;
                float f8;
                block15 : {
                    block14 : {
                        f7 = arrf[j - n7];
                        bl2 = this.mLayout.isRtlCharAt(j);
                        f5 = this.mLayout.getPrimaryHorizontal(j);
                        f8 = this.mLayout.getSecondaryHorizontal(j);
                        if (bl) {
                            if (bl2) {
                                f5 = f8 - f7;
                            } else {
                                f8 = f5;
                                f7 = f5 + f7;
                                f5 = f8;
                                f8 = f7;
                            }
                        } else if (!bl2) {
                            f5 = f8;
                            f8 += f7;
                        } else {
                            f7 = f5 - f7;
                            f8 = f5;
                            f5 = f7;
                        }
                        f7 = f8 + f;
                        f6 = f3 + f2;
                        f8 = f4 + f2;
                        boolean bl3 = this.isPositionVisible(f5 += f, f6);
                        boolean bl4 = this.isPositionVisible(f7, f8);
                        n5 = 0;
                        if (bl3 || bl4) {
                            n5 = false | true;
                        }
                        if (!bl3) break block14;
                        n6 = n5;
                        if (bl4) break block15;
                    }
                    n6 = n5 | 2;
                }
                if (bl2) {
                    n6 |= 4;
                }
                builder.addCharacterBounds(j, f5, f6, f7, f8, n6);
            }
        }
    }

    void removeAdjacentSuggestionSpans(int n) {
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Editable)) {
            return;
        }
        charSequence = (Editable)charSequence;
        SuggestionSpan[] arrsuggestionSpan = charSequence.getSpans(n, n, SuggestionSpan.class);
        int n2 = arrsuggestionSpan.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = charSequence.getSpanStart(arrsuggestionSpan[i]);
            int n4 = charSequence.getSpanEnd(arrsuggestionSpan[i]);
            if (n4 != n && n3 != n || !SpellChecker.haveWordBoundariesChanged((Editable)charSequence, n, n, n3, n4)) continue;
            charSequence.removeSpan(arrsuggestionSpan[i]);
        }
    }

    void removeMisspelledSpans(Spannable spannable) {
        SuggestionSpan[] arrsuggestionSpan = spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
        for (int i = 0; i < arrsuggestionSpan.length; ++i) {
            int n = arrsuggestionSpan[i].getFlags();
            if ((n & 1) == 0 || (n & 2) == 0) continue;
            spannable.removeSpan(arrsuggestionSpan[i]);
        }
    }

    CharSequence removeSuggestionSpans(CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        if (charSequence instanceof Spanned) {
            Spannable spannable = charSequence instanceof Spannable ? (Spannable)charSequence : this.mSpannableFactory.newSpannable(charSequence);
            SuggestionSpan[] arrsuggestionSpan = spannable.getSpans(0, charSequence.length(), SuggestionSpan.class);
            if (arrsuggestionSpan.length == 0) {
                return charSequence;
            }
            charSequence = spannable;
            int n = 0;
            do {
                charSequence2 = charSequence;
                if (n >= arrsuggestionSpan.length) break;
                spannable.removeSpan(arrsuggestionSpan[n]);
                ++n;
            } while (true);
        }
        return charSequence2;
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        int n;
        ArrayList<TextWatcher> arrayList = this.mListeners;
        if (arrayList != null && (n = arrayList.indexOf(textWatcher)) >= 0) {
            this.mListeners.remove(n);
        }
    }

    void replaceSelectionWithText(CharSequence charSequence) {
        ((Editable)this.mText).replace(this.getSelectionStart(), this.getSelectionEnd(), charSequence);
    }

    protected void replaceText_internal(int n, int n2, CharSequence charSequence) {
        ((Editable)this.mText).replace(n, n2, charSequence);
    }

    public boolean requestActionMode(TextLinks.TextLinkSpan textLinkSpan) {
        Preconditions.checkNotNull(textLinkSpan);
        CharSequence charSequence = this.mText;
        if (!(charSequence instanceof Spanned)) {
            return false;
        }
        int n = ((Spanned)charSequence).getSpanStart(textLinkSpan);
        int n2 = ((Spanned)this.mText).getSpanEnd(textLinkSpan);
        if (n >= 0 && n2 <= this.mText.length() && n < n2) {
            this.createEditorIfNeeded();
            this.mEditor.startLinkActionModeAsync(n, n2);
            return true;
        }
        return false;
    }

    public void resetErrorChangedFlag() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.mErrorWasChanged = false;
        }
    }

    @Override
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        this.mLastLayoutDirection = -1;
    }

    boolean selectAllText() {
        if (this.mEditor != null) {
            this.hideFloatingToolbar(500);
        }
        int n = this.mText.length();
        Spannable spannable = this.mSpannable;
        boolean bl = false;
        Selection.setSelection(spannable, 0, n);
        if (n > 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void sendAccessibilityEventInternal(int n) {
        Editor editor;
        if (n == 32768 && (editor = this.mEditor) != null) {
            editor.mProcessTextIntentActionsHandler.initializeAccessibilityActions();
        }
        super.sendAccessibilityEventInternal(n);
    }

    void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16);
        accessibilityEvent.setFromIndex(n);
        accessibilityEvent.setRemovedCount(n2);
        accessibilityEvent.setAddedCount(n3);
        accessibilityEvent.setBeforeText(charSequence);
        this.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    void sendAfterTextChanged(Editable editable) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> arrayList = this.mListeners;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).afterTextChanged(editable);
            }
        }
        this.notifyListeningManagersAfterTextChanged();
        this.hideErrorIfUnchanged();
    }

    void sendOnTextChanged(CharSequence object, int n, int n2, int n3) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> arrayList = this.mListeners;
            int n4 = arrayList.size();
            for (int i = 0; i < n4; ++i) {
                arrayList.get(i).onTextChanged((CharSequence)object, n, n2, n3);
            }
        }
        if ((object = this.mEditor) != null) {
            ((Editor)object).sendOnTextChanged(n, n2, n3);
        }
    }

    @Override
    public void setAccessibilitySelection(int n, int n2) {
        if (this.getAccessibilitySelectionStart() == n && this.getAccessibilitySelectionEnd() == n2) {
            return;
        }
        Object object = this.getIterableTextForAccessibility();
        if (Math.min(n, n2) >= 0 && Math.max(n, n2) <= object.length()) {
            Selection.setSelection((Spannable)object, n, n2);
        } else {
            Selection.removeSelection((Spannable)object);
        }
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).hideCursorAndSpanControllers();
            this.mEditor.stopTextActionMode();
        }
    }

    public void setAllCaps(boolean bl) {
        if (bl) {
            this.setTransformationMethod(new AllCapsTransformationMethod(this.getContext()));
        } else {
            this.setTransformationMethod(null);
        }
    }

    @RemotableViewMethod
    public final void setAutoLinkMask(int n) {
        this.mAutoLinkMask = n;
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int n, int n2, int n3, int n4) {
        if (this.supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(n4, n, displayMetrics), TypedValue.applyDimension(n4, n2, displayMetrics), TypedValue.applyDimension(n4, n3, displayMetrics));
            if (this.setupAutoSizeText()) {
                this.autoSizeText();
                this.invalidate();
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] arrn, int n) {
        if (this.supportsAutoSizeText()) {
            int n2 = arrn.length;
            if (n2 > 0) {
                Object object;
                int[] arrn2 = new int[n2];
                if (n == 0) {
                    object = Arrays.copyOf(arrn, n2);
                } else {
                    DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
                    int n3 = 0;
                    do {
                        object = arrn2;
                        if (n3 >= n2) break;
                        arrn2[n3] = Math.round(TypedValue.applyDimension(n, arrn[n3], displayMetrics));
                        ++n3;
                    } while (true);
                }
                this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes((int[])object);
                if (!this.setupAutoSizeUniformPresetSizesConfiguration()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("None of the preset sizes is valid: ");
                    ((StringBuilder)object).append(Arrays.toString(arrn));
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (this.setupAutoSizeText()) {
                this.autoSizeText();
                this.invalidate();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAutoSizeTextTypeWithDefaults(int n) {
        if (!this.supportsAutoSizeText()) return;
        if (n == 0) {
            this.clearAutoSizeConfiguration();
            return;
        }
        if (n == 1) {
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
            if (!this.setupAutoSizeText()) return;
            this.autoSizeText();
            this.invalidate();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown auto-size text type: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setBreakStrategy(int n) {
        this.mBreakStrategy = n;
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    @RemotableViewMethod
    public void setCompoundDrawablePadding(int n) {
        Drawables drawables = this.mDrawables;
        if (n == 0) {
            if (drawables != null) {
                drawables.mDrawablePadding = n;
            }
        } else {
            Drawables drawables2 = drawables;
            if (drawables == null) {
                drawables2 = drawables = new Drawables(this.getContext());
                this.mDrawables = drawables;
            }
            drawables2.mDrawablePadding = n;
        }
        this.invalidate();
        this.requestLayout();
    }

    public void setCompoundDrawableTintBlendMode(BlendMode blendMode) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(this.getContext());
        }
        Drawables drawables = this.mDrawables;
        drawables.mBlendMode = blendMode;
        drawables.mHasTintMode = true;
        this.applyCompoundDrawableTint();
    }

    public void setCompoundDrawableTintList(ColorStateList colorStateList) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(this.getContext());
        }
        Drawables drawables = this.mDrawables;
        drawables.mTintList = colorStateList;
        drawables.mHasTint = true;
        this.applyCompoundDrawableTint();
    }

    public void setCompoundDrawableTintMode(PorterDuff.Mode enum_) {
        enum_ = enum_ != null ? BlendMode.fromValue(((PorterDuff.Mode)enum_).nativeInt) : null;
        this.setCompoundDrawableTintBlendMode((BlendMode)enum_);
    }

    public void setCompoundDrawables(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        int[] arrn;
        int[] arrn2 = this.mDrawables;
        if (arrn2 != null) {
            if (arrn2.mDrawableStart != null) {
                arrn2.mDrawableStart.setCallback(null);
            }
            arrn2.mDrawableStart = null;
            if (arrn2.mDrawableEnd != null) {
                arrn2.mDrawableEnd.setCallback(null);
            }
            arrn2.mDrawableEnd = null;
            arrn2.mDrawableHeightStart = 0;
            arrn2.mDrawableSizeStart = 0;
            arrn2.mDrawableHeightEnd = 0;
            arrn2.mDrawableSizeEnd = 0;
        }
        int n = drawable2 == null && drawable3 == null && drawable4 == null && drawable5 == null ? 0 : 1;
        if (n == 0) {
            arrn = arrn2;
            if (arrn2 != null) {
                if (!arrn2.hasMetadata()) {
                    this.mDrawables = null;
                    arrn = arrn2;
                } else {
                    for (n = arrn2.mShowing.length - 1; n >= 0; --n) {
                        if (arrn2.mShowing[n] != null) {
                            arrn2.mShowing[n].setCallback(null);
                        }
                        arrn2.mShowing[n] = null;
                    }
                    arrn2.mDrawableHeightLeft = 0;
                    arrn2.mDrawableSizeLeft = 0;
                    arrn2.mDrawableHeightRight = 0;
                    arrn2.mDrawableSizeRight = 0;
                    arrn2.mDrawableWidthTop = 0;
                    arrn2.mDrawableSizeTop = 0;
                    arrn2.mDrawableWidthBottom = 0;
                    arrn2.mDrawableSizeBottom = 0;
                    arrn = arrn2;
                }
            }
        } else {
            arrn = arrn2;
            if (arrn2 == null) {
                arrn = arrn2 = new Drawables(this.getContext());
                this.mDrawables = arrn2;
            }
            this.mDrawables.mOverride = false;
            if (arrn.mShowing[0] != drawable2 && arrn.mShowing[0] != null) {
                arrn.mShowing[0].setCallback(null);
            }
            arrn.mShowing[0] = drawable2;
            if (arrn.mShowing[1] != drawable3 && arrn.mShowing[1] != null) {
                arrn.mShowing[1].setCallback(null);
            }
            arrn.mShowing[1] = drawable3;
            if (arrn.mShowing[2] != drawable4 && arrn.mShowing[2] != null) {
                arrn.mShowing[2].setCallback(null);
            }
            arrn.mShowing[2] = drawable4;
            if (arrn.mShowing[3] != drawable5 && arrn.mShowing[3] != null) {
                arrn.mShowing[3].setCallback(null);
            }
            arrn.mShowing[3] = drawable5;
            Rect rect = arrn.mCompoundRect;
            arrn2 = this.getDrawableState();
            if (drawable2 != null) {
                drawable2.setState(arrn2);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                arrn.mDrawableSizeLeft = rect.width();
                arrn.mDrawableHeightLeft = rect.height();
            } else {
                arrn.mDrawableHeightLeft = 0;
                arrn.mDrawableSizeLeft = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(arrn2);
                drawable4.copyBounds(rect);
                drawable4.setCallback(this);
                arrn.mDrawableSizeRight = rect.width();
                arrn.mDrawableHeightRight = rect.height();
            } else {
                arrn.mDrawableHeightRight = 0;
                arrn.mDrawableSizeRight = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(arrn2);
                drawable3.copyBounds(rect);
                drawable3.setCallback(this);
                arrn.mDrawableSizeTop = rect.height();
                arrn.mDrawableWidthTop = rect.width();
            } else {
                arrn.mDrawableWidthTop = 0;
                arrn.mDrawableSizeTop = 0;
            }
            if (drawable5 != null) {
                drawable5.setState(arrn2);
                drawable5.copyBounds(rect);
                drawable5.setCallback(this);
                arrn.mDrawableSizeBottom = rect.height();
                arrn.mDrawableWidthBottom = rect.width();
            } else {
                arrn.mDrawableWidthBottom = 0;
                arrn.mDrawableSizeBottom = 0;
            }
        }
        if (arrn != null) {
            arrn.mDrawableLeftInitial = drawable2;
            arrn.mDrawableRightInitial = drawable4;
        }
        this.resetResolvedDrawables();
        this.resolveDrawables();
        this.applyCompoundDrawableTint();
        this.invalidate();
        this.requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelative(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        Object object;
        Object object2 = this.mDrawables;
        if (object2 != null) {
            if (((Drawables)object2).mShowing[0] != null) {
                ((Drawables)object2).mShowing[0].setCallback(null);
            }
            object = ((Drawables)object2).mShowing;
            ((Drawables)object2).mDrawableLeftInitial = null;
            object[0] = null;
            if (((Drawables)object2).mShowing[2] != null) {
                ((Drawables)object2).mShowing[2].setCallback(null);
            }
            object = ((Drawables)object2).mShowing;
            ((Drawables)object2).mDrawableRightInitial = null;
            object[2] = null;
            ((Drawables)object2).mDrawableHeightLeft = 0;
            ((Drawables)object2).mDrawableSizeLeft = 0;
            ((Drawables)object2).mDrawableHeightRight = 0;
            ((Drawables)object2).mDrawableSizeRight = 0;
        }
        boolean bl = drawable2 != null || drawable3 != null || drawable4 != null || drawable5 != null;
        if (!bl) {
            if (object2 != null) {
                if (!((Drawables)object2).hasMetadata()) {
                    this.mDrawables = null;
                } else {
                    if (((Drawables)object2).mDrawableStart != null) {
                        ((Drawables)object2).mDrawableStart.setCallback(null);
                    }
                    ((Drawables)object2).mDrawableStart = null;
                    if (((Drawables)object2).mShowing[1] != null) {
                        ((Drawables)object2).mShowing[1].setCallback(null);
                    }
                    object2.mShowing[1] = null;
                    if (((Drawables)object2).mDrawableEnd != null) {
                        ((Drawables)object2).mDrawableEnd.setCallback(null);
                    }
                    ((Drawables)object2).mDrawableEnd = null;
                    if (((Drawables)object2).mShowing[3] != null) {
                        ((Drawables)object2).mShowing[3].setCallback(null);
                    }
                    object2.mShowing[3] = null;
                    ((Drawables)object2).mDrawableHeightStart = 0;
                    ((Drawables)object2).mDrawableSizeStart = 0;
                    ((Drawables)object2).mDrawableHeightEnd = 0;
                    ((Drawables)object2).mDrawableSizeEnd = 0;
                    ((Drawables)object2).mDrawableWidthTop = 0;
                    ((Drawables)object2).mDrawableSizeTop = 0;
                    ((Drawables)object2).mDrawableWidthBottom = 0;
                    ((Drawables)object2).mDrawableSizeBottom = 0;
                }
            }
        } else {
            object = object2;
            if (object2 == null) {
                object2 = new Drawables(this.getContext());
                object = object2;
                this.mDrawables = object2;
            }
            this.mDrawables.mOverride = true;
            if (object.mDrawableStart != drawable2 && object.mDrawableStart != null) {
                object.mDrawableStart.setCallback(null);
            }
            object.mDrawableStart = drawable2;
            if (object.mShowing[1] != drawable3 && object.mShowing[1] != null) {
                object.mShowing[1].setCallback(null);
            }
            object.mShowing[1] = drawable3;
            if (object.mDrawableEnd != drawable4 && object.mDrawableEnd != null) {
                object.mDrawableEnd.setCallback(null);
            }
            object.mDrawableEnd = drawable4;
            if (object.mShowing[3] != drawable5 && object.mShowing[3] != null) {
                object.mShowing[3].setCallback(null);
            }
            object.mShowing[3] = drawable5;
            object2 = object.mCompoundRect;
            int[] arrn = this.getDrawableState();
            if (drawable2 != null) {
                drawable2.setState(arrn);
                drawable2.copyBounds((Rect)object2);
                drawable2.setCallback(this);
                object.mDrawableSizeStart = ((Rect)object2).width();
                object.mDrawableHeightStart = ((Rect)object2).height();
            } else {
                object.mDrawableHeightStart = 0;
                object.mDrawableSizeStart = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(arrn);
                drawable4.copyBounds((Rect)object2);
                drawable4.setCallback(this);
                object.mDrawableSizeEnd = ((Rect)object2).width();
                object.mDrawableHeightEnd = ((Rect)object2).height();
            } else {
                object.mDrawableHeightEnd = 0;
                object.mDrawableSizeEnd = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(arrn);
                drawable3.copyBounds((Rect)object2);
                drawable3.setCallback(this);
                object.mDrawableSizeTop = ((Rect)object2).height();
                object.mDrawableWidthTop = ((Rect)object2).width();
            } else {
                object.mDrawableWidthTop = 0;
                object.mDrawableSizeTop = 0;
            }
            if (drawable5 != null) {
                drawable5.setState(arrn);
                drawable5.copyBounds((Rect)object2);
                drawable5.setCallback(this);
                object.mDrawableSizeBottom = ((Rect)object2).height();
                object.mDrawableWidthBottom = ((Rect)object2).width();
            } else {
                object.mDrawableWidthBottom = 0;
                object.mDrawableSizeBottom = 0;
            }
        }
        this.resetResolvedDrawables();
        this.resolveDrawables();
        this.invalidate();
        this.requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int n, int n2, int n3, int n4) {
        Context context = this.getContext();
        Drawable drawable2 = null;
        Drawable drawable3 = n != 0 ? context.getDrawable(n) : null;
        Drawable drawable4 = n2 != 0 ? context.getDrawable(n2) : null;
        Drawable drawable5 = n3 != 0 ? context.getDrawable(n3) : null;
        if (n4 != 0) {
            drawable2 = context.getDrawable(n4);
        }
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable3, drawable4, drawable5, drawable2);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable5 != null) {
            drawable5.setBounds(0, 0, drawable5.getIntrinsicWidth(), drawable5.getIntrinsicHeight());
        }
        this.setCompoundDrawablesRelative(drawable2, drawable3, drawable4, drawable5);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(int n, int n2, int n3, int n4) {
        Context context = this.getContext();
        Drawable drawable2 = null;
        Drawable drawable3 = n != 0 ? context.getDrawable(n) : null;
        Drawable drawable4 = n2 != 0 ? context.getDrawable(n2) : null;
        Drawable drawable5 = n3 != 0 ? context.getDrawable(n3) : null;
        if (n4 != 0) {
            drawable2 = context.getDrawable(n4);
        }
        this.setCompoundDrawablesWithIntrinsicBounds(drawable3, drawable4, drawable5, drawable2);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable5 != null) {
            drawable5.setBounds(0, 0, drawable5.getIntrinsicWidth(), drawable5.getIntrinsicHeight());
        }
        this.setCompoundDrawables(drawable2, drawable3, drawable4, drawable5);
    }

    protected void setCursorPosition_internal(int n, int n2) {
        Selection.setSelection((Editable)this.mText, n, n2);
    }

    @RemotableViewMethod
    public void setCursorVisible(boolean bl) {
        if (bl && this.mEditor == null) {
            return;
        }
        this.createEditorIfNeeded();
        if (this.mEditor.mCursorVisible != bl) {
            this.mEditor.mCursorVisible = bl;
            this.invalidate();
            this.mEditor.makeBlink();
            this.mEditor.prepareCursorControllers();
        }
    }

    public void setCustomInsertionActionModeCallback(ActionMode.Callback callback) {
        this.createEditorIfNeeded();
        this.mEditor.mCustomInsertionActionModeCallback = callback;
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        this.createEditorIfNeeded();
        this.mEditor.mCustomSelectionActionModeCallback = callback;
    }

    public final void setEditableFactory(Editable.Factory factory) {
        this.mEditableFactory = factory;
        this.setText(this.mText);
    }

    public void setElegantTextHeight(boolean bl) {
        if (bl != this.mTextPaint.isElegantTextHeight()) {
            this.mTextPaint.setElegantTextHeight(bl);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.mEllipsize != truncateAt) {
            this.mEllipsize = truncateAt;
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setEms(int n) {
        this.mMinWidth = n;
        this.mMaxWidth = n;
        this.mMinWidthMode = 1;
        this.mMaxWidthMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    @Override
    public void setEnabled(boolean bl) {
        Object object;
        if (bl == this.isEnabled()) {
            return;
        }
        if (!bl && (object = this.getInputMethodManager()) != null && ((InputMethodManager)object).isActive(this)) {
            ((InputMethodManager)object).hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
        super.setEnabled(bl);
        if (bl && (object = this.getInputMethodManager()) != null) {
            ((InputMethodManager)object).restartInput(this);
        }
        if ((object = this.mEditor) != null) {
            ((Editor)object).invalidateTextDisplayList();
            this.mEditor.prepareCursorControllers();
            this.mEditor.makeBlink();
        }
    }

    @RemotableViewMethod
    public void setError(CharSequence charSequence) {
        if (charSequence == null) {
            this.setError(null, null);
        } else {
            Drawable drawable2 = this.getContext().getDrawable(17302859);
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            this.setError(charSequence, drawable2);
        }
    }

    public void setError(CharSequence charSequence, Drawable drawable2) {
        this.createEditorIfNeeded();
        this.mEditor.setError(charSequence, drawable2);
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public void setExtractedText(ExtractedText extractedText) {
        int n;
        int n2;
        int n3;
        int n4;
        Spannable spannable = this.getEditableText();
        if (extractedText.text != null) {
            if (spannable == null) {
                this.setText(extractedText.text, BufferType.EDITABLE);
            } else {
                n2 = spannable.length();
                if (extractedText.partialStartOffset >= 0) {
                    n4 = spannable.length();
                    n = n2 = extractedText.partialStartOffset;
                    if (n2 > n4) {
                        n = n4;
                    }
                    n2 = n3 = extractedText.partialEndOffset;
                    if (n3 > n4) {
                        n2 = n4;
                    }
                } else {
                    n = 0;
                }
                TextView.removeParcelableSpans(spannable, n, n2);
                if (TextUtils.equals(spannable.subSequence(n, n2), extractedText.text)) {
                    if (extractedText.text instanceof Spanned) {
                        TextUtils.copySpansFrom((Spanned)extractedText.text, 0, n2 - n, Object.class, spannable, n);
                    }
                } else {
                    spannable.replace(n, n2, extractedText.text);
                }
            }
        }
        spannable = (Spannable)this.getText();
        n4 = spannable.length();
        n2 = extractedText.selectionStart;
        if (n2 < 0) {
            n = 0;
        } else {
            n = n2;
            if (n2 > n4) {
                n = n4;
            }
        }
        n3 = extractedText.selectionEnd;
        if (n3 < 0) {
            n2 = 0;
        } else {
            n2 = n3;
            if (n3 > n4) {
                n2 = n4;
            }
        }
        Selection.setSelection(spannable, n, n2);
        if ((extractedText.flags & 2) != 0) {
            MetaKeyKeyListener.startSelecting(this, spannable);
        } else {
            MetaKeyKeyListener.stopSelecting(this, spannable);
        }
        this.setHintInternal(extractedText.hint);
    }

    public void setExtracting(ExtractedTextRequest extractedTextRequest) {
        if (this.mEditor.mInputMethodState != null) {
            this.mEditor.mInputMethodState.mExtractedTextRequest = extractedTextRequest;
        }
        this.mEditor.hideCursorAndSpanControllers();
        this.stopTextActionMode();
        if (this.mEditor.mSelectionModifierCursorController != null) {
            this.mEditor.mSelectionModifierCursorController.resetTouchOffsets();
        }
    }

    public void setFallbackLineSpacing(boolean bl) {
        if (this.mUseFallbackLineSpacing != bl) {
            this.mUseFallbackLineSpacing = bl;
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setFilters(InputFilter[] arrinputFilter) {
        if (arrinputFilter != null) {
            this.mFilters = arrinputFilter;
            CharSequence charSequence = this.mText;
            if (charSequence instanceof Editable) {
                this.setFilters((Editable)charSequence, arrinputFilter);
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setFirstBaselineToTopHeight(int n) {
        Preconditions.checkArgumentNonnegative(n);
        Paint.FontMetricsInt fontMetricsInt = this.getPaint().getFontMetricsInt();
        int n2 = this.getIncludeFontPadding() ? fontMetricsInt.top : fontMetricsInt.ascent;
        if (n > Math.abs(n2)) {
            n2 = -n2;
            this.setPadding(this.getPaddingLeft(), n - n2, this.getPaddingRight(), this.getPaddingBottom());
        }
    }

    @RemotableViewMethod
    public void setFontFeatureSettings(String string2) {
        if (string2 != this.mTextPaint.getFontFeatureSettings()) {
            this.mTextPaint.setFontFeatureSettings(string2);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public boolean setFontVariationSettings(String string2) {
        String string3 = this.mTextPaint.getFontVariationSettings();
        if (!(string2 == string3 || string2 != null && string2.equals(string3))) {
            boolean bl = this.mTextPaint.setFontVariationSettings(string2);
            if (bl && this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
            return bl;
        }
        return true;
    }

    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setFrame();
        }
        this.restartMarqueeIfNeeded();
        return bl;
    }

    @RemotableViewMethod
    public void setFreezesText(boolean bl) {
        this.mFreezesText = bl;
    }

    public void setGravity(int n) {
        int n2 = n;
        if ((n & 8388615) == 0) {
            n2 = n | 8388611;
        }
        n = n2;
        if ((n2 & 112) == 0) {
            n = n2 | 48;
        }
        n2 = 0;
        if ((n & 8388615) != (8388615 & this.mGravity)) {
            n2 = 1;
        }
        if (n != this.mGravity) {
            this.invalidate();
        }
        this.mGravity = n;
        Object object = this.mLayout;
        if (object != null && n2 != 0) {
            n2 = ((Layout)object).getWidth();
            object = this.mHintLayout;
            n = object == null ? 0 : ((Layout)object).getWidth();
            object = UNKNOWN_BORING;
            this.makeNewLayout(n2, n, (BoringLayout.Metrics)object, (BoringLayout.Metrics)object, this.mRight - this.mLeft - this.getCompoundPaddingLeft() - this.getCompoundPaddingRight(), true);
        }
    }

    @RemotableViewMethod
    public void setHeight(int n) {
        this.mMinimum = n;
        this.mMaximum = n;
        this.mMinMode = 2;
        this.mMaxMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setHighlightColor(int n) {
        if (this.mHighlightColor != n) {
            this.mHighlightColor = n;
            this.invalidate();
        }
    }

    @RemotableViewMethod
    public final void setHint(int n) {
        this.setHint(this.getContext().getResources().getText(n));
    }

    @RemotableViewMethod
    public final void setHint(CharSequence charSequence) {
        this.setHintInternal(charSequence);
        if (this.mEditor != null && this.isInputMethodTarget()) {
            this.mEditor.reportExtractedText();
        }
    }

    @RemotableViewMethod
    public final void setHintTextColor(int n) {
        this.mHintTextColor = ColorStateList.valueOf(n);
        this.updateTextColors();
    }

    public final void setHintTextColor(ColorStateList colorStateList) {
        this.mHintTextColor = colorStateList;
        this.updateTextColors();
    }

    public void setHorizontallyScrolling(boolean bl) {
        if (this.mHorizontallyScrolling != bl) {
            this.mHorizontallyScrolling = bl;
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setHyphenationFrequency(int n) {
        this.mHyphenationFrequency = n;
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setImeActionLabel(CharSequence charSequence, int n) {
        this.createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeActionLabel = charSequence;
        this.mEditor.mInputContentType.imeActionId = n;
    }

    public void setImeHintLocales(LocaleList object) {
        this.createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeHintLocales = object;
        if (this.mUseInternationalizedInput) {
            object = object == null ? null : ((LocaleList)object).get(0);
            this.changeListenerLocaleTo((Locale)object);
        }
    }

    public void setImeOptions(int n) {
        this.createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeOptions = n;
    }

    public void setIncludeFontPadding(boolean bl) {
        if (this.mIncludePad != bl) {
            this.mIncludePad = bl;
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setInputExtras(int n) throws XmlPullParserException, IOException {
        this.createEditorIfNeeded();
        XmlResourceParser xmlResourceParser = this.getResources().getXml(n);
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.extras = new Bundle();
        this.getResources().parseBundleExtras(xmlResourceParser, this.mEditor.mInputContentType.extras);
    }

    public void setInputType(int n) {
        boolean bl;
        InputMethodManager inputMethodManager;
        boolean bl2;
        boolean bl3;
        block9 : {
            boolean bl4;
            block11 : {
                boolean bl5;
                boolean bl6;
                block10 : {
                    block8 : {
                        bl5 = TextView.isPasswordInputType(this.getInputType());
                        bl6 = TextView.isVisiblePasswordInputType(this.getInputType());
                        this.setInputType(n, false);
                        bl = TextView.isPasswordInputType(n);
                        bl3 = TextView.isVisiblePasswordInputType(n);
                        bl4 = false;
                        bl2 = false;
                        if (!bl) break block8;
                        this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        this.setTypefaceFromAttrs(null, null, 3, 0, -1);
                        bl2 = bl4;
                        break block9;
                    }
                    if (!bl3) break block10;
                    if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                        bl2 = true;
                    }
                    this.setTypefaceFromAttrs(null, null, 3, 0, -1);
                    break block9;
                }
                if (bl5) break block11;
                bl2 = bl4;
                if (!bl6) break block9;
            }
            this.setTypefaceFromAttrs(null, null, -1, 0, -1);
            bl2 = bl4;
            if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                bl2 = true;
            }
        }
        bl3 = TextView.isMultilineInputType(n) ^ true;
        if (this.mSingleLine != bl3 || bl2) {
            this.applySingleLine(bl3, bl ^ true, true);
        }
        if (!this.isSuggestionsEnabled()) {
            this.setTextInternal(this.removeSuggestionSpans(this.mText));
        }
        if ((inputMethodManager = this.getInputMethodManager()) != null) {
            inputMethodManager.restartInput(this);
        }
    }

    public void setJustificationMode(int n) {
        this.mJustificationMode = n;
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setKeyListener(KeyListener object) {
        this.mListenerChanged = true;
        this.setKeyListenerOnly((KeyListener)object);
        this.fixFocusableAndClickableSettings();
        if (object != null) {
            this.createEditorIfNeeded();
            this.setInputTypeFromEditor();
        } else {
            object = this.mEditor;
            if (object != null) {
                ((Editor)object).mInputType = 0;
            }
        }
        object = this.getInputMethodManager();
        if (object != null) {
            ((InputMethodManager)object).restartInput(this);
        }
    }

    public void setLastBaselineToBottomHeight(int n) {
        Preconditions.checkArgumentNonnegative(n);
        Paint.FontMetricsInt fontMetricsInt = this.getPaint().getFontMetricsInt();
        int n2 = this.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
        if (n > Math.abs(n2)) {
            this.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), n - n2);
        }
    }

    @RemotableViewMethod
    public void setLetterSpacing(float f) {
        if (f != this.mTextPaint.getLetterSpacing()) {
            this.mTextPaint.setLetterSpacing(f);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setLineHeight(int n) {
        Preconditions.checkArgumentNonnegative(n);
        int n2 = this.getPaint().getFontMetricsInt(null);
        if (n != n2) {
            this.setLineSpacing(n - n2, 1.0f);
        }
    }

    public void setLineSpacing(float f, float f2) {
        if (this.mSpacingAdd != f || this.mSpacingMult != f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setLines(int n) {
        this.mMinimum = n;
        this.mMaximum = n;
        this.mMinMode = 1;
        this.mMaxMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public final void setLinkTextColor(int n) {
        this.mLinkTextColor = ColorStateList.valueOf(n);
        this.updateTextColors();
    }

    public final void setLinkTextColor(ColorStateList colorStateList) {
        this.mLinkTextColor = colorStateList;
        this.updateTextColors();
    }

    @RemotableViewMethod
    public final void setLinksClickable(boolean bl) {
        this.mLinksClickable = bl;
    }

    public void setMarqueeRepeatLimit(int n) {
        this.mMarqueeRepeatLimit = n;
    }

    @RemotableViewMethod
    public void setMaxEms(int n) {
        this.mMaxWidth = n;
        this.mMaxWidthMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMaxHeight(int n) {
        this.mMaximum = n;
        this.mMaxMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMaxLines(int n) {
        this.mMaximum = n;
        this.mMaxMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
        this.mMaxWidthMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMinEms(int n) {
        this.mMinWidth = n;
        this.mMinWidthMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMinHeight(int n) {
        this.mMinimum = n;
        this.mMinMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMinLines(int n) {
        this.mMinimum = n;
        this.mMinMode = 1;
        this.requestLayout();
        this.invalidate();
    }

    @RemotableViewMethod
    public void setMinWidth(int n) {
        this.mMinWidth = n;
        this.mMinWidthMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    public final void setMovementMethod(MovementMethod object) {
        if (this.mMovement != object) {
            this.mMovement = object;
            if (object != null && this.mSpannable == null) {
                this.setText(this.mText);
            }
            this.fixFocusableAndClickableSettings();
            object = this.mEditor;
            if (object != null) {
                ((Editor)object).prepareCursorControllers();
            }
        }
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this.createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.onEditorActionListener = onEditorActionListener;
    }

    @Override
    public void setPadding(int n, int n2, int n3, int n4) {
        if (n != this.mPaddingLeft || n3 != this.mPaddingRight || n2 != this.mPaddingTop || n4 != this.mPaddingBottom) {
            this.nullLayouts();
        }
        super.setPadding(n, n2, n3, n4);
        this.invalidate();
    }

    @Override
    public void setPaddingRelative(int n, int n2, int n3, int n4) {
        if (n != this.getPaddingStart() || n3 != this.getPaddingEnd() || n2 != this.mPaddingTop || n4 != this.mPaddingBottom) {
            this.nullLayouts();
        }
        super.setPaddingRelative(n, n2, n3, n4);
        this.invalidate();
    }

    @RemotableViewMethod
    public void setPaintFlags(int n) {
        if (this.mTextPaint.getFlags() != n) {
            this.mTextPaint.setFlags(n);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setPrivateImeOptions(String string2) {
        this.createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.privateImeOptions = string2;
    }

    public void setRawInputType(int n) {
        if (n == 0 && this.mEditor == null) {
            return;
        }
        this.createEditorIfNeeded();
        this.mEditor.mInputType = n;
    }

    public void setScroller(Scroller scroller) {
        this.mScroller = scroller;
    }

    @RemotableViewMethod
    public void setSelectAllOnFocus(boolean bl) {
        CharSequence charSequence;
        this.createEditorIfNeeded();
        this.mEditor.mSelectAllOnFocus = bl;
        if (bl && !((charSequence = this.mText) instanceof Spannable)) {
            this.setText(charSequence, BufferType.SPANNABLE);
        }
    }

    @Override
    public void setSelected(boolean bl) {
        boolean bl2 = this.isSelected();
        super.setSelected(bl);
        if (bl != bl2 && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (bl) {
                this.startMarquee();
            } else {
                this.stopMarquee();
            }
        }
    }

    public void setShadowLayer(float f, float f2, float f3, int n) {
        this.mTextPaint.setShadowLayer(f, f2, f3, n);
        this.mShadowRadius = f;
        this.mShadowDx = f2;
        this.mShadowDy = f3;
        this.mShadowColor = n;
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.invalidateTextDisplayList();
            this.mEditor.invalidateHandlesAndActionMode();
        }
        this.invalidate();
    }

    @RemotableViewMethod
    public final void setShowSoftInputOnFocus(boolean bl) {
        this.createEditorIfNeeded();
        this.mEditor.mShowSoftInputOnFocus = bl;
    }

    public void setSingleLine() {
        this.setSingleLine(true);
    }

    @RemotableViewMethod
    public void setSingleLine(boolean bl) {
        this.setInputTypeSingleLine(bl);
        this.applySingleLine(bl, true, true);
    }

    protected void setSpan_internal(Object object, int n, int n2, int n3) {
        ((Editable)this.mText).setSpan(object, n, n2, n3);
    }

    public final void setSpannableFactory(Spannable.Factory factory) {
        this.mSpannableFactory = factory;
        this.setText(this.mText);
    }

    @RemotableViewMethod
    public final void setText(int n) {
        this.setText(this.getContext().getResources().getText(n));
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = n;
    }

    public final void setText(int n, BufferType bufferType) {
        this.setText(this.getContext().getResources().getText(n), bufferType);
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = n;
    }

    @RemotableViewMethod
    public final void setText(CharSequence charSequence) {
        this.setText(charSequence, this.mBufferType);
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        this.setText(charSequence, bufferType, true, 0);
        charSequence = this.mCharWrapper;
        if (charSequence != null) {
            ((CharWrapper)charSequence).mChars = null;
        }
    }

    public final void setText(char[] object, int n, int n2) {
        int n3 = 0;
        if (n >= 0 && n2 >= 0 && n + n2 <= ((char[])object).length) {
            CharSequence charSequence = this.mText;
            if (charSequence != null) {
                n3 = charSequence.length();
                this.sendBeforeTextChanged(this.mText, 0, n3, n2);
            } else {
                this.sendBeforeTextChanged("", 0, 0, n2);
            }
            charSequence = this.mCharWrapper;
            if (charSequence == null) {
                this.mCharWrapper = new CharWrapper((char[])object, n, n2);
            } else {
                ((CharWrapper)charSequence).set((char[])object, n, n2);
            }
            this.setText(this.mCharWrapper, this.mBufferType, false, n3);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n2);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public void setTextAppearance(int n) {
        this.setTextAppearance(this.mContext, n);
    }

    @Deprecated
    public void setTextAppearance(Context context, int n) {
        TypedArray typedArray = context.obtainStyledAttributes(n, R.styleable.TextAppearance);
        TextAppearanceAttributes textAppearanceAttributes = new TextAppearanceAttributes();
        this.readTextAppearance(context, typedArray, textAppearanceAttributes, false);
        typedArray.recycle();
        this.applyTextAppearance(textAppearanceAttributes);
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    @RemotableViewMethod
    public void setTextColor(int n) {
        this.mTextColor = ColorStateList.valueOf(n);
        this.updateTextColors();
    }

    @RemotableViewMethod
    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.mTextColor = colorStateList;
            this.updateTextColors();
            return;
        }
        throw new NullPointerException();
    }

    public void setTextCursorDrawable(int n) {
        Drawable drawable2 = n != 0 ? this.mContext.getDrawable(n) : null;
        this.setTextCursorDrawable(drawable2);
    }

    public void setTextCursorDrawable(Drawable object) {
        this.mCursorDrawable = object;
        this.mCursorDrawableRes = 0;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).loadCursorDrawable();
        }
    }

    public void setTextIsSelectable(boolean bl) {
        if (!bl && this.mEditor == null) {
            return;
        }
        this.createEditorIfNeeded();
        if (this.mEditor.mTextIsSelectable == bl) {
            return;
        }
        this.mEditor.mTextIsSelectable = bl;
        this.setFocusableInTouchMode(bl);
        this.setFocusable(16);
        this.setClickable(bl);
        this.setLongClickable(bl);
        Object object = bl ? ArrowKeyMovementMethod.getInstance() : null;
        this.setMovementMethod((MovementMethod)object);
        CharSequence charSequence = this.mText;
        object = bl ? BufferType.SPANNABLE : BufferType.NORMAL;
        this.setText(charSequence, (BufferType)((Object)object));
        this.mEditor.prepareCursorControllers();
    }

    @RemotableViewMethod
    public final void setTextKeepState(CharSequence charSequence) {
        this.setTextKeepState(charSequence, this.mBufferType);
    }

    public final void setTextKeepState(CharSequence charSequence, BufferType bufferType) {
        int n = this.getSelectionStart();
        int n2 = this.getSelectionEnd();
        int n3 = charSequence.length();
        this.setText(charSequence, bufferType);
        if ((n >= 0 || n2 >= 0) && (charSequence = this.mSpannable) != null) {
            Selection.setSelection((Spannable)charSequence, Math.max(0, Math.min(n, n3)), Math.max(0, Math.min(n2, n3)));
        }
    }

    public void setTextLocale(Locale locale) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocale(locale);
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setTextLocales(LocaleList localeList) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocales(localeList);
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    public void setTextMetricsParams(PrecomputedText.Params params) {
        this.mTextPaint.set(params.getTextPaint());
        this.mUserSetTextScaleX = true;
        this.mTextDir = params.getTextDirection();
        this.mBreakStrategy = params.getBreakStrategy();
        this.mHyphenationFrequency = params.getHyphenationFrequency();
        if (this.mLayout != null) {
            this.nullLayouts();
            this.requestLayout();
            this.invalidate();
        }
    }

    public final void setTextOperationUser(UserHandle object) {
        if (Objects.equals(this.mTextOperationUser, object)) {
            return;
        }
        if (object != null && !Process.myUserHandle().equals(object) && this.getContext().checkSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("INTERACT_ACROSS_USERS_FULL is required. userId=");
            stringBuilder.append(((UserHandle)object).getIdentifier());
            stringBuilder.append(" callingUserId");
            stringBuilder.append(UserHandle.myUserId());
            throw new SecurityException(stringBuilder.toString());
        }
        this.mTextOperationUser = object;
        this.mCurrentSpellCheckerLocaleCache = null;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).onTextOperationUserChanged();
        }
    }

    @RemotableViewMethod
    public void setTextScaleX(float f) {
        if (f != this.mTextPaint.getTextScaleX()) {
            this.mUserSetTextScaleX = true;
            this.mTextPaint.setTextScaleX(f);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandle(int n) {
        boolean bl = n != 0;
        Preconditions.checkArgument(bl, "The text select handle should be a valid drawable resource id.");
        this.setTextSelectHandle(this.mContext.getDrawable(n));
    }

    @RemotableViewMethod
    public void setTextSelectHandle(Drawable object) {
        Preconditions.checkNotNull(object, "The text select handle should not be null.");
        this.mTextSelectHandle = object;
        this.mTextSelectHandleRes = 0;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(int n) {
        boolean bl = n != 0;
        Preconditions.checkArgument(bl, "The text select left handle should be a valid drawable resource id.");
        this.setTextSelectHandleLeft(this.mContext.getDrawable(n));
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(Drawable object) {
        Preconditions.checkNotNull(object, "The left text select handle should not be null.");
        this.mTextSelectHandleLeft = object;
        this.mTextSelectHandleLeftRes = 0;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(int n) {
        boolean bl = n != 0;
        Preconditions.checkArgument(bl, "The text select right handle should be a valid drawable resource id.");
        this.setTextSelectHandleRight(this.mContext.getDrawable(n));
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(Drawable object) {
        Preconditions.checkNotNull(object, "The right text select handle should not be null.");
        this.mTextSelectHandleRight = object;
        this.mTextSelectHandleRightRes = 0;
        object = this.mEditor;
        if (object != null) {
            ((Editor)object).loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSize(float f) {
        this.setTextSize(2, f);
    }

    public void setTextSize(int n, float f) {
        if (!this.isAutoSizeEnabled()) {
            this.setTextSizeInternal(n, f, true);
        }
    }

    public final void setTransformationMethod(TransformationMethod transformationMethod) {
        Spannable spannable;
        TransformationMethod transformationMethod2 = this.mTransformation;
        if (transformationMethod == transformationMethod2) {
            return;
        }
        if (transformationMethod2 != null && (spannable = this.mSpannable) != null) {
            spannable.removeSpan(transformationMethod2);
        }
        this.mTransformation = transformationMethod;
        if (transformationMethod instanceof TransformationMethod2) {
            transformationMethod = (TransformationMethod2)transformationMethod;
            boolean bl = !this.isTextSelectable() && !(this.mText instanceof Editable);
            this.mAllowTransformationLengthChange = bl;
            transformationMethod.setLengthChangesAllowed(this.mAllowTransformationLengthChange);
        } else {
            this.mAllowTransformationLengthChange = false;
        }
        this.setText(this.mText);
        if (this.hasPasswordTransformationMethod()) {
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        this.mTextDir = this.getTextDirectionHeuristic();
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            if (this.mLayout != null) {
                this.nullLayouts();
                this.requestLayout();
                this.invalidate();
            }
        }
    }

    public void setTypeface(Typeface object, int n) {
        float f = 0.0f;
        boolean bl = false;
        if (n > 0) {
            object = object == null ? Typeface.defaultFromStyle(n) : Typeface.create((Typeface)object, n);
            this.setTypeface((Typeface)object);
            int n2 = object != null ? ((Typeface)object).getStyle() : 0;
            n = n2 & n;
            object = this.mTextPaint;
            if ((n & 1) != 0) {
                bl = true;
            }
            ((Paint)object).setFakeBoldText(bl);
            object = this.mTextPaint;
            if ((n & 2) != 0) {
                f = -0.25f;
            }
            ((Paint)object).setTextSkewX(f);
        } else {
            this.mTextPaint.setFakeBoldText(false);
            this.mTextPaint.setTextSkewX(0.0f);
            this.setTypeface((Typeface)object);
        }
    }

    public final void setUndoManager(UndoManager undoManager, String string2) {
        throw new UnsupportedOperationException("not implemented");
    }

    @RemotableViewMethod
    public void setWidth(int n) {
        this.mMinWidth = n;
        this.mMaxWidth = n;
        this.mMinWidthMode = 2;
        this.mMaxWidthMode = 2;
        this.requestLayout();
        this.invalidate();
    }

    @Override
    public boolean showContextMenu() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setContextMenuAnchor(Float.NaN, Float.NaN);
        }
        return super.showContextMenu();
    }

    @Override
    public boolean showContextMenu(float f, float f2) {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.setContextMenuAnchor(f, f2);
        }
        return super.showContextMenu(f, f2);
    }

    void spanChange(Spanned object, Object object2, int n, int n2, int n3, int n4) {
        Editor editor;
        Object object3;
        int n5;
        int n6;
        int n7;
        block39 : {
            int n8;
            int n9;
            block40 : {
                block37 : {
                    block38 : {
                        n6 = 0;
                        n8 = -1;
                        n5 = -1;
                        object3 = this.mEditor;
                        object3 = object3 == null ? null : ((Editor)object3).mInputMethodState;
                        if (object2 != Selection.SELECTION_END) break block37;
                        n9 = 1;
                        n7 = n2;
                        if (n >= 0) break block38;
                        n6 = n9;
                        n5 = n7;
                        if (n2 < 0) break block37;
                    }
                    this.invalidateCursor(Selection.getSelectionStart((CharSequence)object), n, n2);
                    this.checkForResize();
                    this.registerForPreDraw();
                    editor = this.mEditor;
                    n6 = n9;
                    n5 = n7;
                    if (editor != null) {
                        editor.makeBlink();
                        n5 = n7;
                        n6 = n9;
                    }
                }
                n7 = n6;
                n6 = n8;
                if (object2 != Selection.SELECTION_START) break block39;
                n9 = 1;
                n8 = n2;
                if (n >= 0) break block40;
                n7 = n9;
                n6 = n8;
                if (n2 < 0) break block39;
            }
            this.invalidateCursor(Selection.getSelectionEnd((CharSequence)object), n, n2);
            n6 = n8;
            n7 = n9;
        }
        if (n7 != 0) {
            this.mHighlightPathBogus = true;
            if (this.mEditor != null && !this.isFocused()) {
                this.mEditor.mSelectionMoved = true;
            }
            if ((object.getSpanFlags(object2) & 512) == 0) {
                n7 = n6;
                if (n6 < 0) {
                    n7 = Selection.getSelectionStart((CharSequence)object);
                }
                n6 = n5;
                if (n5 < 0) {
                    n6 = Selection.getSelectionEnd((CharSequence)object);
                }
                if ((editor = this.mEditor) != null) {
                    editor.refreshTextActionMode();
                    if (!this.hasSelection() && this.mEditor.getTextActionMode() == null && this.hasTransientState()) {
                        this.setHasTransientState(false);
                    }
                }
                this.onSelectionChanged(n7, n6);
            }
        }
        if (object2 instanceof UpdateAppearance || object2 instanceof ParagraphStyle || object2 instanceof CharacterStyle) {
            if (object3 != null && ((Editor.InputMethodState)object3).mBatchEditNesting != 0) {
                ((Editor.InputMethodState)object3).mContentChanged = true;
            } else {
                this.invalidate();
                this.mHighlightPathBogus = true;
                this.checkForResize();
            }
            editor = this.mEditor;
            if (editor != null) {
                if (n >= 0) {
                    editor.invalidateTextDisplayList(this.mLayout, n, n3);
                }
                if (n2 >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, n2, n4);
                }
                this.mEditor.invalidateHandlesAndActionMode();
            }
        }
        if (MetaKeyKeyListener.isMetaTracker((CharSequence)object, object2)) {
            this.mHighlightPathBogus = true;
            if (object3 != null && MetaKeyKeyListener.isSelectingMetaTracker((CharSequence)object, object2)) {
                ((Editor.InputMethodState)object3).mSelectionModeChanged = true;
            }
            if (Selection.getSelectionStart((CharSequence)object) >= 0) {
                if (object3 != null && ((Editor.InputMethodState)object3).mBatchEditNesting != 0) {
                    ((Editor.InputMethodState)object3).mCursorChanged = true;
                } else {
                    this.invalidateCursor();
                }
            }
        }
        if (object2 instanceof ParcelableSpan && object3 != null && ((Editor.InputMethodState)object3).mExtractedTextRequest != null) {
            if (((Editor.InputMethodState)object3).mBatchEditNesting != 0) {
                if (n >= 0) {
                    if (((Editor.InputMethodState)object3).mChangedStart > n) {
                        ((Editor.InputMethodState)object3).mChangedStart = n;
                    }
                    if (((Editor.InputMethodState)object3).mChangedStart > n3) {
                        ((Editor.InputMethodState)object3).mChangedStart = n3;
                    }
                }
                if (n2 >= 0) {
                    if (((Editor.InputMethodState)object3).mChangedStart > n2) {
                        ((Editor.InputMethodState)object3).mChangedStart = n2;
                    }
                    if (((Editor.InputMethodState)object3).mChangedStart > n4) {
                        ((Editor.InputMethodState)object3).mChangedStart = n4;
                    }
                }
            } else {
                ((Editor.InputMethodState)object3).mContentChanged = true;
            }
        }
        if ((object = this.mEditor) != null && ((Editor)object).mSpellChecker != null && n2 < 0 && object2 instanceof SpellCheckSpan) {
            this.mEditor.mSpellChecker.onSpellCheckSpanRemoved((SpellCheckSpan)object2);
        }
    }

    void startActivityAsTextOperationUserIfNecessary(Intent intent) {
        if (this.mTextOperationUser != null) {
            this.getContext().startActivityAsUser(intent, this.mTextOperationUser);
        } else {
            this.getContext().startActivity(intent);
        }
    }

    @UnsupportedAppUsage
    protected void stopTextActionMode() {
        Editor editor = this.mEditor;
        if (editor != null) {
            editor.stopTextActionMode();
        }
    }

    protected boolean supportsAutoSizeText() {
        return true;
    }

    boolean textCanBeSelected() {
        block2 : {
            boolean bl;
            block4 : {
                block3 : {
                    MovementMethod movementMethod = this.mMovement;
                    boolean bl2 = false;
                    if (movementMethod == null || !movementMethod.canSelectArbitrarily()) break block2;
                    if (this.isTextEditable()) break block3;
                    bl = bl2;
                    if (!this.isTextSelectable()) break block4;
                    bl = bl2;
                    if (!(this.mText instanceof Spannable)) break block4;
                    bl = bl2;
                    if (!this.isEnabled()) break block4;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    void updateAfterEdit() {
        this.invalidate();
        int n = this.getSelectionStart();
        if (n >= 0 || (this.mGravity & 112) == 80) {
            this.registerForPreDraw();
        }
        this.checkForResize();
        if (n >= 0) {
            this.mHighlightPathBogus = true;
            Editor editor = this.mEditor;
            if (editor != null) {
                editor.makeBlink();
            }
            this.bringPointIntoView(n);
        }
    }

    @VisibleForTesting
    public boolean useDynamicLayout() {
        boolean bl = this.isTextSelectable() || this.mSpannable != null && this.mPrecomputed == null;
        return bl;
    }

    boolean usesNoOpTextClassifier() {
        boolean bl = this.getTextClassifier() == TextClassifier.NO_OP;
        return bl;
    }

    @Override
    protected boolean verifyDrawable(Drawable drawable2) {
        Drawable[] arrdrawable;
        boolean bl = super.verifyDrawable(drawable2);
        if (!bl && (arrdrawable = this.mDrawables) != null) {
            arrdrawable = arrdrawable.mShowing;
            int n = arrdrawable.length;
            for (int i = 0; i < n; ++i) {
                if (drawable2 != arrdrawable[i]) continue;
                return true;
            }
        }
        return bl;
    }

    protected void viewClicked(InputMethodManager inputMethodManager) {
        if (inputMethodManager != null) {
            inputMethodManager.viewClicked(this);
        }
    }

    int viewportToContentHorizontalOffset() {
        return this.getCompoundPaddingLeft() - this.mScrollX;
    }

    @UnsupportedAppUsage
    int viewportToContentVerticalOffset() {
        int n;
        int n2 = n = this.getExtendedPaddingTop() - this.mScrollY;
        if ((this.mGravity & 112) != 48) {
            n2 = n + this.getVerticalOffset(false);
        }
        return n2;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutoSizeTextType {
    }

    public static enum BufferType {
        NORMAL,
        SPANNABLE,
        EDITABLE;
        
    }

    private class ChangeWatcher
    implements TextWatcher,
    SpanWatcher {
        private CharSequence mBeforeText;

        private ChangeWatcher() {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            TextView.this.sendAfterTextChanged(editable);
            if (MetaKeyKeyListener.getMetaState((CharSequence)editable, 2048) != 0) {
                MetaKeyKeyListener.stopSelecting(TextView.this, editable);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            if (AccessibilityManager.getInstance(TextView.this.mContext).isEnabled() && TextView.this.mTransformed != null) {
                this.mBeforeText = TextView.this.mTransformed.toString();
            }
            TextView.this.sendBeforeTextChanged(charSequence, n, n2, n3);
        }

        @Override
        public void onSpanAdded(Spannable spannable, Object object, int n, int n2) {
            TextView.this.spanChange(spannable, object, -1, n, -1, n2);
        }

        @Override
        public void onSpanChanged(Spannable spannable, Object object, int n, int n2, int n3, int n4) {
            TextView.this.spanChange(spannable, object, n, n3, n2, n4);
        }

        @Override
        public void onSpanRemoved(Spannable spannable, Object object, int n, int n2) {
            TextView.this.spanChange(spannable, object, n, -1, n2, -1);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            TextView.this.handleTextChanged(charSequence, n, n2, n3);
            if (AccessibilityManager.getInstance(TextView.this.mContext).isEnabled() && (TextView.this.isFocused() || TextView.this.isSelected() && TextView.this.isShown())) {
                TextView.this.sendAccessibilityEventTypeViewTextChanged(this.mBeforeText, n, n2, n3);
                this.mBeforeText = null;
            }
        }
    }

    private static class CharWrapper
    implements CharSequence,
    GetChars,
    GraphicsOperations {
        private char[] mChars;
        private int mLength;
        private int mStart;

        public CharWrapper(char[] arrc, int n, int n2) {
            this.mChars = arrc;
            this.mStart = n;
            this.mLength = n2;
        }

        @Override
        public char charAt(int n) {
            return this.mChars[this.mStart + n];
        }

        @Override
        public void drawText(BaseCanvas baseCanvas, int n, int n2, float f, float f2, Paint paint) {
            baseCanvas.drawText(this.mChars, n + this.mStart, n2 - n, f, f2, paint);
        }

        @Override
        public void drawTextRun(BaseCanvas baseCanvas, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
            char[] arrc = this.mChars;
            int n5 = this.mStart;
            baseCanvas.drawTextRun(arrc, n + n5, n2 - n, n3 + n5, n4 - n3, f, f2, bl, paint);
        }

        @Override
        public void getChars(int n, int n2, char[] object, int n3) {
            int n4;
            if (n >= 0 && n2 >= 0 && n <= (n4 = this.mLength) && n2 <= n4) {
                System.arraycopy(this.mChars, this.mStart + n, object, n3, n2 - n);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n2);
            throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
        }

        @Override
        public float getTextRunAdvances(int n, int n2, int n3, int n4, boolean bl, float[] arrf, int n5, Paint paint) {
            char[] arrc = this.mChars;
            int n6 = this.mStart;
            return paint.getTextRunAdvances(arrc, n + n6, n2 - n, n3 + n6, n4 - n3, bl, arrf, n5);
        }

        @Override
        public int getTextRunCursor(int n, int n2, boolean bl, int n3, int n4, Paint paint) {
            char[] arrc = this.mChars;
            int n5 = this.mStart;
            return paint.getTextRunCursor(arrc, n + n5, n2 - n, bl, n3 + n5, n4);
        }

        @Override
        public int getTextWidths(int n, int n2, float[] arrf, Paint paint) {
            return paint.getTextWidths(this.mChars, this.mStart + n, n2 - n, arrf);
        }

        @Override
        public int length() {
            return this.mLength;
        }

        @Override
        public float measureText(int n, int n2, Paint paint) {
            return paint.measureText(this.mChars, this.mStart + n, n2 - n);
        }

        void set(char[] arrc, int n, int n2) {
            this.mChars = arrc;
            this.mStart = n;
            this.mLength = n2;
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            int n3;
            if (n >= 0 && n2 >= 0 && n <= (n3 = this.mLength) && n2 <= n3) {
                return new String(this.mChars, this.mStart + n, n2 - n);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }

        @Override
        public String toString() {
            return new String(this.mChars, this.mStart, this.mLength);
        }
    }

    static class Drawables {
        static final int BOTTOM = 3;
        static final int DRAWABLE_LEFT = 1;
        static final int DRAWABLE_NONE = -1;
        static final int DRAWABLE_RIGHT = 0;
        static final int LEFT = 0;
        static final int RIGHT = 2;
        static final int TOP = 1;
        BlendMode mBlendMode;
        final Rect mCompoundRect = new Rect();
        Drawable mDrawableEnd;
        Drawable mDrawableError;
        int mDrawableHeightEnd;
        int mDrawableHeightError;
        int mDrawableHeightLeft;
        int mDrawableHeightRight;
        int mDrawableHeightStart;
        int mDrawableHeightTemp;
        Drawable mDrawableLeftInitial;
        int mDrawablePadding;
        Drawable mDrawableRightInitial;
        int mDrawableSaved = -1;
        int mDrawableSizeBottom;
        int mDrawableSizeEnd;
        int mDrawableSizeError;
        int mDrawableSizeLeft;
        int mDrawableSizeRight;
        int mDrawableSizeStart;
        int mDrawableSizeTemp;
        int mDrawableSizeTop;
        Drawable mDrawableStart;
        Drawable mDrawableTemp;
        int mDrawableWidthBottom;
        int mDrawableWidthTop;
        boolean mHasTint;
        boolean mHasTintMode;
        boolean mIsRtlCompatibilityMode;
        boolean mOverride;
        final Drawable[] mShowing = new Drawable[4];
        ColorStateList mTintList;

        public Drawables(Context context) {
            boolean bl = context.getApplicationInfo().targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            this.mIsRtlCompatibilityMode = bl;
            this.mOverride = false;
        }

        private void applyErrorDrawableIfNeeded(int n) {
            int n2 = this.mDrawableSaved;
            if (n2 != 0) {
                if (n2 == 1) {
                    this.mShowing[0] = this.mDrawableTemp;
                    this.mDrawableSizeLeft = this.mDrawableSizeTemp;
                    this.mDrawableHeightLeft = this.mDrawableHeightTemp;
                }
            } else {
                this.mShowing[2] = this.mDrawableTemp;
                this.mDrawableSizeRight = this.mDrawableSizeTemp;
                this.mDrawableHeightRight = this.mDrawableHeightTemp;
            }
            Drawable drawable2 = this.mDrawableError;
            if (drawable2 != null) {
                if (n != 1) {
                    this.mDrawableSaved = 0;
                    Drawable[] arrdrawable = this.mShowing;
                    this.mDrawableTemp = arrdrawable[2];
                    this.mDrawableSizeTemp = this.mDrawableSizeRight;
                    this.mDrawableHeightTemp = this.mDrawableHeightRight;
                    arrdrawable[2] = drawable2;
                    this.mDrawableSizeRight = this.mDrawableSizeError;
                    this.mDrawableHeightRight = this.mDrawableHeightError;
                } else {
                    this.mDrawableSaved = 1;
                    Drawable[] arrdrawable = this.mShowing;
                    this.mDrawableTemp = arrdrawable[0];
                    this.mDrawableSizeTemp = this.mDrawableSizeLeft;
                    this.mDrawableHeightTemp = this.mDrawableHeightLeft;
                    arrdrawable[0] = drawable2;
                    this.mDrawableSizeLeft = this.mDrawableSizeError;
                    this.mDrawableHeightLeft = this.mDrawableHeightError;
                }
            }
        }

        public boolean hasMetadata() {
            boolean bl = this.mDrawablePadding != 0 || this.mHasTintMode || this.mHasTint;
            return bl;
        }

        public boolean resolveWithLayoutDirection(int n) {
            Drawable[] arrdrawable = this.mShowing;
            boolean bl = false;
            Drawable drawable2 = arrdrawable[0];
            Drawable drawable3 = arrdrawable[2];
            arrdrawable[0] = this.mDrawableLeftInitial;
            arrdrawable[2] = this.mDrawableRightInitial;
            if (this.mIsRtlCompatibilityMode) {
                Drawable drawable4 = this.mDrawableStart;
                if (drawable4 != null && arrdrawable[0] == null) {
                    arrdrawable[0] = drawable4;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                }
                if ((drawable4 = this.mDrawableEnd) != null && (arrdrawable = this.mShowing)[2] == null) {
                    arrdrawable[2] = drawable4;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else if (n != 1) {
                if (this.mOverride) {
                    arrdrawable[0] = this.mDrawableStart;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                    arrdrawable[2] = this.mDrawableEnd;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else if (this.mOverride) {
                arrdrawable[2] = this.mDrawableStart;
                this.mDrawableSizeRight = this.mDrawableSizeStart;
                this.mDrawableHeightRight = this.mDrawableHeightStart;
                arrdrawable[0] = this.mDrawableEnd;
                this.mDrawableSizeLeft = this.mDrawableSizeEnd;
                this.mDrawableHeightLeft = this.mDrawableHeightEnd;
            }
            this.applyErrorDrawableIfNeeded(n);
            arrdrawable = this.mShowing;
            if (arrdrawable[0] != drawable2 || arrdrawable[2] != drawable3) {
                bl = true;
            }
            return bl;
        }

        public void setErrorDrawable(Drawable object, TextView textView) {
            int[] arrn = this.mDrawableError;
            if (arrn != object && arrn != null) {
                arrn.setCallback(null);
            }
            this.mDrawableError = object;
            if (this.mDrawableError != null) {
                object = this.mCompoundRect;
                arrn = textView.getDrawableState();
                this.mDrawableError.setState(arrn);
                this.mDrawableError.copyBounds((Rect)object);
                this.mDrawableError.setCallback(textView);
                this.mDrawableSizeError = ((Rect)object).width();
                this.mDrawableHeightError = ((Rect)object).height();
            } else {
                this.mDrawableHeightError = 0;
                this.mDrawableSizeError = 0;
            }
        }
    }

    private static final class Marquee {
        private static final int MARQUEE_DELAY = 1200;
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DP_PER_SECOND = 30;
        private static final byte MARQUEE_RUNNING = 2;
        private static final byte MARQUEE_STARTING = 1;
        private static final byte MARQUEE_STOPPED = 0;
        private final Choreographer mChoreographer;
        private float mFadeStop;
        private float mGhostOffset;
        private float mGhostStart;
        private long mLastAnimationMs;
        private float mMaxFadeScroll;
        private float mMaxScroll;
        private final float mPixelsPerMs;
        private int mRepeatLimit;
        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback(){

            @Override
            public void doFrame(long l) {
                if (Marquee.this.mStatus == 2) {
                    if (Marquee.this.mRepeatLimit >= 0) {
                        Marquee.access$910(Marquee.this);
                    }
                    Marquee marquee = Marquee.this;
                    marquee.start(marquee.mRepeatLimit);
                }
            }
        };
        private float mScroll;
        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback(){

            @Override
            public void doFrame(long l) {
                Marquee.this.mStatus = (byte)2;
                Marquee marquee = Marquee.this;
                marquee.mLastAnimationMs = marquee.mChoreographer.getFrameTime();
                Marquee.this.tick();
            }
        };
        private byte mStatus = (byte)(false ? 1 : 0);
        private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback(){

            @Override
            public void doFrame(long l) {
                Marquee.this.tick();
            }
        };
        private final WeakReference<TextView> mView;

        Marquee(TextView textView) {
            this.mPixelsPerMs = 30.0f * textView.getContext().getResources().getDisplayMetrics().density / 1000.0f;
            this.mView = new WeakReference<TextView>(textView);
            this.mChoreographer = Choreographer.getInstance();
        }

        static /* synthetic */ int access$910(Marquee marquee) {
            int n = marquee.mRepeatLimit;
            marquee.mRepeatLimit = n - 1;
            return n;
        }

        private void resetScroll() {
            this.mScroll = 0.0f;
            TextView textView = (TextView)this.mView.get();
            if (textView != null) {
                textView.invalidate();
            }
        }

        float getGhostOffset() {
            return this.mGhostOffset;
        }

        float getMaxFadeScroll() {
            return this.mMaxFadeScroll;
        }

        float getScroll() {
            return this.mScroll;
        }

        boolean isRunning() {
            boolean bl = this.mStatus == 2;
            return bl;
        }

        boolean isStopped() {
            boolean bl = this.mStatus == 0;
            return bl;
        }

        boolean shouldDrawGhost() {
            boolean bl = this.mStatus == 2 && this.mScroll > this.mGhostStart;
            return bl;
        }

        boolean shouldDrawLeftFade() {
            boolean bl = this.mScroll <= this.mFadeStop;
            return bl;
        }

        void start(int n) {
            if (n == 0) {
                this.stop();
                return;
            }
            this.mRepeatLimit = n;
            TextView textView = (TextView)this.mView.get();
            if (textView != null && textView.mLayout != null) {
                this.mStatus = (byte)(true ? 1 : 0);
                this.mScroll = 0.0f;
                n = textView.getWidth() - textView.getCompoundPaddingLeft() - textView.getCompoundPaddingRight();
                float f = textView.mLayout.getLineWidth(0);
                float f2 = (float)n / 3.0f;
                float f3 = this.mGhostStart = f - (float)n + f2;
                this.mMaxScroll = (float)n + f3;
                this.mGhostOffset = f + f2;
                this.mFadeStop = (float)n / 6.0f + f;
                this.mMaxFadeScroll = f3 + f + f;
                textView.invalidate();
                this.mChoreographer.postFrameCallback(this.mStartCallback);
            }
        }

        void stop() {
            this.mStatus = (byte)(false ? 1 : 0);
            this.mChoreographer.removeFrameCallback(this.mStartCallback);
            this.mChoreographer.removeFrameCallback(this.mRestartCallback);
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            this.resetScroll();
        }

        void tick() {
            if (this.mStatus != 2) {
                return;
            }
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            TextView textView = (TextView)this.mView.get();
            if (textView != null && (textView.isFocused() || textView.isSelected())) {
                long l = this.mChoreographer.getFrameTime();
                long l2 = this.mLastAnimationMs;
                this.mLastAnimationMs = l;
                float f = l - l2;
                float f2 = this.mPixelsPerMs;
                this.mScroll += f * f2;
                f = this.mScroll;
                f2 = this.mMaxScroll;
                if (f > f2) {
                    this.mScroll = f2;
                    this.mChoreographer.postFrameCallbackDelayed(this.mRestartCallback, 1200L);
                } else {
                    this.mChoreographer.postFrameCallback(this.mTickCallback);
                }
                textView.invalidate();
            }
        }

    }

    public static interface OnEditorActionListener {
        public boolean onEditorAction(TextView var1, int var2, KeyEvent var3);
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        ParcelableParcel editorState;
        CharSequence error;
        boolean frozenWithFocus;
        int selEnd = -1;
        int selStart = -1;
        @UnsupportedAppUsage
        CharSequence text;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.selStart = parcel.readInt();
            this.selEnd = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.frozenWithFocus = bl;
            this.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() != 0) {
                this.error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.editorState = (ParcelableParcel)ParcelableParcel.CREATOR.createFromParcel(parcel);
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("TextView.SavedState{");
            ((StringBuilder)charSequence).append(Integer.toHexString(System.identityHashCode(this)));
            ((StringBuilder)charSequence).append(" start=");
            ((StringBuilder)charSequence).append(this.selStart);
            ((StringBuilder)charSequence).append(" end=");
            ((StringBuilder)charSequence).append(this.selEnd);
            CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = charSequence2;
            if (this.text != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(" text=");
                ((StringBuilder)charSequence).append((Object)this.text);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("}");
            return ((StringBuilder)charSequence2).toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.selStart);
            parcel.writeInt(this.selEnd);
            parcel.writeInt((int)this.frozenWithFocus);
            TextUtils.writeToParcel(this.text, parcel, n);
            if (this.error == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                TextUtils.writeToParcel(this.error, parcel, n);
            }
            if (this.editorState == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.editorState.writeToParcel(parcel, n);
            }
        }

    }

    private static class TextAppearanceAttributes {
        boolean mAllCaps = false;
        boolean mElegant = false;
        boolean mFallbackLineSpacing = false;
        String mFontFamily = null;
        boolean mFontFamilyExplicit = false;
        String mFontFeatureSettings = null;
        Typeface mFontTypeface = null;
        String mFontVariationSettings = null;
        int mFontWeight = -1;
        boolean mHasElegant = false;
        boolean mHasFallbackLineSpacing = false;
        boolean mHasLetterSpacing = false;
        float mLetterSpacing = 0.0f;
        int mShadowColor = 0;
        float mShadowDx = 0.0f;
        float mShadowDy = 0.0f;
        float mShadowRadius = 0.0f;
        ColorStateList mTextColor = null;
        int mTextColorHighlight = 0;
        ColorStateList mTextColorHint = null;
        ColorStateList mTextColorLink = null;
        LocaleList mTextLocales = null;
        int mTextSize = -1;
        int mTextStyle = 0;
        int mTypefaceIndex = -1;

        private TextAppearanceAttributes() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TextAppearanceAttributes {\n    mTextColorHighlight:");
            stringBuilder.append(this.mTextColorHighlight);
            stringBuilder.append("\n    mTextColor:");
            stringBuilder.append(this.mTextColor);
            stringBuilder.append("\n    mTextColorHint:");
            stringBuilder.append(this.mTextColorHint);
            stringBuilder.append("\n    mTextColorLink:");
            stringBuilder.append(this.mTextColorLink);
            stringBuilder.append("\n    mTextSize:");
            stringBuilder.append(this.mTextSize);
            stringBuilder.append("\n    mTextLocales:");
            stringBuilder.append(this.mTextLocales);
            stringBuilder.append("\n    mFontFamily:");
            stringBuilder.append(this.mFontFamily);
            stringBuilder.append("\n    mFontTypeface:");
            stringBuilder.append(this.mFontTypeface);
            stringBuilder.append("\n    mFontFamilyExplicit:");
            stringBuilder.append(this.mFontFamilyExplicit);
            stringBuilder.append("\n    mTypefaceIndex:");
            stringBuilder.append(this.mTypefaceIndex);
            stringBuilder.append("\n    mTextStyle:");
            stringBuilder.append(this.mTextStyle);
            stringBuilder.append("\n    mFontWeight:");
            stringBuilder.append(this.mFontWeight);
            stringBuilder.append("\n    mAllCaps:");
            stringBuilder.append(this.mAllCaps);
            stringBuilder.append("\n    mShadowColor:");
            stringBuilder.append(this.mShadowColor);
            stringBuilder.append("\n    mShadowDx:");
            stringBuilder.append(this.mShadowDx);
            stringBuilder.append("\n    mShadowDy:");
            stringBuilder.append(this.mShadowDy);
            stringBuilder.append("\n    mShadowRadius:");
            stringBuilder.append(this.mShadowRadius);
            stringBuilder.append("\n    mHasElegant:");
            stringBuilder.append(this.mHasElegant);
            stringBuilder.append("\n    mElegant:");
            stringBuilder.append(this.mElegant);
            stringBuilder.append("\n    mHasFallbackLineSpacing:");
            stringBuilder.append(this.mHasFallbackLineSpacing);
            stringBuilder.append("\n    mFallbackLineSpacing:");
            stringBuilder.append(this.mFallbackLineSpacing);
            stringBuilder.append("\n    mHasLetterSpacing:");
            stringBuilder.append(this.mHasLetterSpacing);
            stringBuilder.append("\n    mLetterSpacing:");
            stringBuilder.append(this.mLetterSpacing);
            stringBuilder.append("\n    mFontFeatureSettings:");
            stringBuilder.append(this.mFontFeatureSettings);
            stringBuilder.append("\n    mFontVariationSettings:");
            stringBuilder.append(this.mFontVariationSettings);
            stringBuilder.append("\n}");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface XMLTypefaceAttr {
    }

}

