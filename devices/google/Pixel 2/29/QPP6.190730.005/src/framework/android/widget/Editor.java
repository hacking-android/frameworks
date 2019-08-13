/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.R;
import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.UndoManager;
import android.content.UndoOperation;
import android.content.UndoOwner;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableParcel;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.WordIterator;
import android.text.style.EasyEditSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Magnifier;
import android.widget.PopupWindow;
import android.widget.SelectionActionModeHelper;
import android.widget.SpellChecker;
import android.widget.TextView;
import android.widget._$$Lambda$DZXn7FbDDFyBvNjI_iG9_hfa7kw;
import android.widget._$$Lambda$Editor$MagnifierMotionAnimator$E_RaelOMgCHAzvKgSSZE_hDYeIg;
import android.widget._$$Lambda$Editor$TdqUlJ6RRep0wXYHaRH51nTa08I;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.EditableInputConnection;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Editor {
    static final int BLINK = 500;
    private static final boolean DEBUG_UNDO = false;
    private static final int DRAG_SHADOW_MAX_TEXT_LENGTH = 20;
    static final int EXTRACT_NOTHING = -2;
    static final int EXTRACT_UNKNOWN = -1;
    private static final boolean FLAG_USE_MAGNIFIER = true;
    public static final int HANDLE_TYPE_SELECTION_END = 1;
    public static final int HANDLE_TYPE_SELECTION_START = 0;
    private static final float LINE_SLOP_MULTIPLIER_FOR_HANDLEVIEWS = 0.5f;
    private static final int MENU_ITEM_ORDER_ASSIST = 0;
    private static final int MENU_ITEM_ORDER_AUTOFILL = 10;
    private static final int MENU_ITEM_ORDER_COPY = 5;
    private static final int MENU_ITEM_ORDER_CUT = 4;
    private static final int MENU_ITEM_ORDER_PASTE = 6;
    private static final int MENU_ITEM_ORDER_PASTE_AS_PLAIN_TEXT = 11;
    private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
    private static final int MENU_ITEM_ORDER_REDO = 3;
    private static final int MENU_ITEM_ORDER_REPLACE = 9;
    private static final int MENU_ITEM_ORDER_SECONDARY_ASSIST_ACTIONS_START = 50;
    private static final int MENU_ITEM_ORDER_SELECT_ALL = 8;
    private static final int MENU_ITEM_ORDER_SHARE = 7;
    private static final int MENU_ITEM_ORDER_UNDO = 2;
    private static final String TAG = "Editor";
    private static final int TAP_STATE_DOUBLE_TAP = 2;
    private static final int TAP_STATE_FIRST_TAP = 1;
    private static final int TAP_STATE_INITIAL = 0;
    private static final int TAP_STATE_TRIPLE_CLICK = 3;
    private static final String UNDO_OWNER_TAG = "Editor";
    private static final int UNSET_LINE = -1;
    private static final int UNSET_X_VALUE = -1;
    boolean mAllowUndo = true;
    private Blink mBlink;
    private float mContextMenuAnchorX;
    private float mContextMenuAnchorY;
    private CorrectionHighlighter mCorrectionHighlighter;
    @UnsupportedAppUsage
    boolean mCreatedWithASelection;
    private final CursorAnchorInfoNotifier mCursorAnchorInfoNotifier = new CursorAnchorInfoNotifier();
    boolean mCursorVisible = true;
    ActionMode.Callback mCustomInsertionActionModeCallback;
    ActionMode.Callback mCustomSelectionActionModeCallback;
    boolean mDiscardNextActionUp;
    Drawable mDrawableForCursor = null;
    CharSequence mError;
    private ErrorPopup mErrorPopup;
    boolean mErrorWasChanged;
    boolean mFrozenWithFocus;
    private final boolean mHapticTextHandleEnabled;
    boolean mIgnoreActionUpEvent;
    boolean mInBatchEditControllers;
    InputContentType mInputContentType;
    InputMethodState mInputMethodState;
    int mInputType = 0;
    private Runnable mInsertionActionModeRunnable;
    @UnsupportedAppUsage
    private boolean mInsertionControllerEnabled;
    private InsertionPointCursorController mInsertionPointCursorController;
    boolean mIsBeingLongClicked;
    boolean mIsInsertionActionModeStartPending = false;
    KeyListener mKeyListener;
    private int mLastButtonState;
    private float mLastDownPositionX;
    private float mLastDownPositionY;
    private long mLastTouchUpTime = 0L;
    private float mLastUpPositionX;
    private float mLastUpPositionY;
    private final MagnifierMotionAnimator mMagnifierAnimator;
    private final ViewTreeObserver.OnDrawListener mMagnifierOnDrawListener = new ViewTreeObserver.OnDrawListener(){

        @Override
        public void onDraw() {
            if (Editor.this.mMagnifierAnimator != null) {
                Editor.this.mTextView.post(Editor.this.mUpdateMagnifierRunnable);
            }
        }
    };
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    private final MenuItem.OnMenuItemClickListener mOnContextMenuItemClickListener = new MenuItem.OnMenuItemClickListener(){

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                return true;
            }
            return Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
        }
    };
    private PositionListener mPositionListener;
    private boolean mPreserveSelection;
    final ProcessTextIntentActionsHandler mProcessTextIntentActionsHandler;
    private boolean mRenderCursorRegardlessTiming;
    private boolean mRequestingLinkActionMode;
    private boolean mRestartActionModeOnNextRefresh;
    boolean mSelectAllOnFocus;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Drawable mSelectHandleCenter;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Drawable mSelectHandleLeft;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Drawable mSelectHandleRight;
    private SelectionActionModeHelper mSelectionActionModeHelper;
    @UnsupportedAppUsage
    private boolean mSelectionControllerEnabled;
    SelectionModifierCursorController mSelectionModifierCursorController;
    boolean mSelectionMoved;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private long mShowCursor;
    private boolean mShowErrorAfterAttach;
    private final Runnable mShowFloatingToolbar = new Runnable(){

        @Override
        public void run() {
            if (Editor.this.mTextActionMode != null) {
                Editor.this.mTextActionMode.hide(0L);
            }
        }
    };
    @UnsupportedAppUsage
    boolean mShowSoftInputOnFocus = true;
    private Runnable mShowSuggestionRunnable;
    private SpanController mSpanController;
    SpellChecker mSpellChecker;
    private final SuggestionHelper mSuggestionHelper = new SuggestionHelper();
    SuggestionRangeSpan mSuggestionRangeSpan;
    private SuggestionsPopupWindow mSuggestionsPopupWindow;
    private int mTapState = 0;
    private Rect mTempRect;
    private ActionMode mTextActionMode;
    boolean mTextIsSelectable;
    private TextRenderNode[] mTextRenderNodes;
    private final TextView mTextView;
    boolean mTouchFocusSelected;
    final UndoInputFilter mUndoInputFilter = new UndoInputFilter(this);
    private final UndoManager mUndoManager = new UndoManager();
    private UndoOwner mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    private final Runnable mUpdateMagnifierRunnable = new Runnable(){

        @Override
        public void run() {
            Editor.this.mMagnifierAnimator.update();
        }
    };
    private boolean mUpdateWordIteratorText;
    private WordIterator mWordIterator;
    private WordIterator mWordIteratorWithText;

    Editor(TextView textView) {
        textView = this.mTextView = textView;
        textView.setFilters(textView.getFilters());
        this.mProcessTextIntentActionsHandler = new ProcessTextIntentActionsHandler(this);
        this.mHapticTextHandleEnabled = this.mTextView.getContext().getResources().getBoolean(17891439);
        this.mMagnifierAnimator = new MagnifierMotionAnimator(Magnifier.createBuilderWithOldMagnifierDefaults(this.mTextView).build());
    }

    private void chooseSize(PopupWindow popupWindow, CharSequence object, TextView textView) {
        int n = textView.getPaddingLeft();
        int n2 = textView.getPaddingRight();
        int n3 = textView.getPaddingTop();
        int n4 = textView.getPaddingBottom();
        int n5 = this.mTextView.getResources().getDimensionPixelSize(17105457);
        object = StaticLayout.Builder.obtain((CharSequence)object, 0, object.length(), textView.getPaint(), n5).setUseLineSpacingFromFallbacks(textView.mUseFallbackLineSpacing).build();
        float f = 0.0f;
        for (n5 = 0; n5 < ((StaticLayout)object).getLineCount(); ++n5) {
            f = Math.max(f, ((Layout)object).getLineWidth(n5));
        }
        popupWindow.setWidth((int)Math.ceil(f) + (n + n2));
        popupWindow.setHeight(((Layout)object).getHeight() + (n3 + n4));
    }

    private int clampHorizontalPosition(Drawable drawable2, float f) {
        float f2 = Math.max(0.5f, f - 0.5f);
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        int n = 0;
        if (drawable2 != null) {
            drawable2.getPadding(this.mTempRect);
            n = drawable2.getIntrinsicWidth();
        } else {
            this.mTempRect.setEmpty();
        }
        int n2 = this.mTextView.getScrollX();
        f = f2 - (float)n2;
        int n3 = this.mTextView.getWidth() - this.mTextView.getCompoundPaddingLeft() - this.mTextView.getCompoundPaddingRight();
        n = f >= (float)n3 - 1.0f ? n3 + n2 - (n - this.mTempRect.right) : (!(Math.abs(f) <= 1.0f || TextUtils.isEmpty(this.mTextView.getText()) && (float)(1048576 - n2) <= (float)n3 + 1.0f && f2 <= 1.0f) ? (int)f2 - this.mTempRect.left : n2 - this.mTempRect.left);
        return n;
    }

    private void discardTextDisplayLists() {
        if (this.mTextRenderNodes != null) {
            Object object;
            for (int i = 0; i < ((TextRenderNode[])(object = this.mTextRenderNodes)).length; ++i) {
                if ((object = object[i] != null ? object[i].renderNode : null) == null || !((RenderNode)object).hasDisplayList()) continue;
                ((RenderNode)object).discardDisplayList();
            }
        }
    }

    private void downgradeEasyCorrectionSpans() {
        SuggestionSpan[] arrsuggestionSpan = this.mTextView.getText();
        if (arrsuggestionSpan instanceof Spannable) {
            arrsuggestionSpan = (Spannable)arrsuggestionSpan;
            arrsuggestionSpan = arrsuggestionSpan.getSpans(0, arrsuggestionSpan.length(), SuggestionSpan.class);
            for (int i = 0; i < arrsuggestionSpan.length; ++i) {
                int n = arrsuggestionSpan[i].getFlags();
                if ((n & 1) == 0 || (n & 2) != 0) continue;
                arrsuggestionSpan[i].setFlags(n & -2);
            }
        }
    }

    private void drawCursor(Canvas canvas, int n) {
        Drawable drawable2;
        boolean bl = n != 0;
        if (bl) {
            canvas.translate(0.0f, n);
        }
        if ((drawable2 = this.mDrawableForCursor) != null) {
            drawable2.draw(canvas);
        }
        if (bl) {
            canvas.translate(0.0f, -n);
        }
    }

    private void drawHardwareAccelerated(Canvas canvas, Layout layout2, Path path, Paint paint, int n) {
        TextRenderNode[] arrtextRenderNode = this;
        long l = layout2.getLineRangeForDraw(canvas);
        int n2 = TextUtils.unpackRangeStartFromLong(l);
        int n3 = TextUtils.unpackRangeEndFromLong(l);
        if (n3 < 0) {
            return;
        }
        layout2.drawBackground(canvas, path, paint, n, n2, n3);
        if (layout2 instanceof DynamicLayout) {
            ArraySet<Integer> arraySet;
            int n4;
            int[] arrn;
            int[] arrn2;
            DynamicLayout dynamicLayout;
            int n5;
            int n6;
            int n7;
            int n8;
            block12 : {
                TextRenderNode[] arrtextRenderNode2;
                if (arrtextRenderNode.mTextRenderNodes == null) {
                    arrtextRenderNode.mTextRenderNodes = ArrayUtils.emptyArray(TextRenderNode.class);
                }
                dynamicLayout = (DynamicLayout)layout2;
                arrn = dynamicLayout.getBlockEndLines();
                arrn2 = dynamicLayout.getBlockIndices();
                n8 = dynamicLayout.getNumberOfBlocks();
                n6 = dynamicLayout.getIndexFirstChangedBlock();
                arraySet = dynamicLayout.getBlocksAlwaysNeedToBeRedrawn();
                boolean bl = true;
                if (arraySet != null) {
                    for (n4 = 0; n4 < arraySet.size(); ++n4) {
                        n5 = dynamicLayout.getBlockIndex(arraySet.valueAt(n4));
                        if (n5 == -1 || (arrtextRenderNode2 = arrtextRenderNode.mTextRenderNodes)[n5] == null) continue;
                        arrtextRenderNode2[n5].needsToBeShifted = true;
                    }
                }
                n4 = n5 = Arrays.binarySearch(arrn, 0, n8, n2);
                if (n5 < 0) {
                    n4 = -(n5 + 1);
                }
                n5 = 0;
                n7 = n8;
                for (n4 = Math.min((int)n6, (int)n4); n4 < n7; ++n4) {
                    int n9 = arrn2[n4];
                    if (n4 >= n6 && n9 != -1 && (arrtextRenderNode2 = arrtextRenderNode.mTextRenderNodes)[n9] != null) {
                        arrtextRenderNode2[n9].needsToBeShifted = bl;
                    }
                    if (arrn[n4] < n2) continue;
                    int n10 = n4;
                    n5 = n9 = this.drawHardwareAcceleratedInner(canvas, layout2, path, paint, n, arrn, arrn2, n10, n7, n5);
                    if (arrn[n10] < n3) continue;
                    n4 = Math.max(n6, n10 + 1);
                    n5 = n9;
                    break block12;
                }
                n4 = n8;
            }
            if (arraySet != null) {
                n2 = 0;
                n8 = n5;
                for (n5 = n2; n5 < arraySet.size(); ++n5) {
                    n2 = arraySet.valueAt(n5);
                    n6 = dynamicLayout.getBlockIndex(n2);
                    if (n6 != -1 && (arrtextRenderNode = this.mTextRenderNodes)[n6] != null && !arrtextRenderNode[n6].needsToBeShifted) continue;
                    n8 = Editor.super.drawHardwareAcceleratedInner(canvas, layout2, path, paint, n, arrn, arrn2, n2, n7, n8);
                }
                n = n4;
            } else {
                n = n4;
            }
            dynamicLayout.setIndexFirstChangedBlock(n);
        } else {
            layout2.drawText(canvas, n2, n3);
        }
    }

    private int drawHardwareAcceleratedInner(Canvas canvas, Layout layout2, Path object, Paint object2, int n, int[] arrn, int[] arrn2, int n2, int n3, int n4) {
        int n5 = arrn[n2];
        int n6 = arrn2[n2];
        n = n6 == -1 ? 1 : 0;
        if (n != 0) {
            arrn2[n2] = n3 = this.getAvailableDisplayListIndex(arrn2, n3, n4);
            object = this.mTextRenderNodes;
            if (object[n3] != null) {
                object[n3].isDirty = true;
            }
            n4 = n3 + 1;
        } else {
            n3 = n6;
        }
        object = this.mTextRenderNodes;
        if (object[n3] == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Text ");
            ((StringBuilder)object2).append(n3);
            object[n3] = new TextRenderNode(((StringBuilder)object2).toString());
        }
        boolean bl = this.mTextRenderNodes[n3].needsRecord();
        object = this.mTextRenderNodes[n3].renderNode;
        if (this.mTextRenderNodes[n3].needsToBeShifted || bl) {
            float f;
            float f2;
            n2 = n2 == 0 ? 0 : arrn[n2 - 1] + 1;
            int n7 = layout2.getLineTop(n2);
            int n8 = layout2.getLineBottom(n5);
            int n9 = this.mTextView.getWidth();
            if (this.mTextView.getHorizontallyScrolling()) {
                f2 = Float.MAX_VALUE;
                f = Float.MIN_VALUE;
                for (n6 = n2; n6 <= n5; ++n6) {
                    f2 = Math.min(f2, layout2.getLineLeft(n6));
                    f = Math.max(f, layout2.getLineRight(n6));
                }
                n6 = (int)f2;
                n = (int)(0.5f + f);
            } else {
                n6 = 0;
                n = n9;
            }
            if (bl) {
                object2 = ((RenderNode)object).beginRecording(n - n6, n8 - n7);
                f2 = -n6;
                f = -n7;
                try {
                    ((Canvas)object2).translate(f2, f);
                    layout2.drawText((Canvas)object2, n2, n5);
                    this.mTextRenderNodes[n3].isDirty = false;
                }
                finally {
                    ((RenderNode)object).endRecording();
                    ((RenderNode)object).setClipToBounds(false);
                }
            }
            ((RenderNode)object).setLeftTopRightBottom(n6, n7, n, n8);
            this.mTextRenderNodes[n3].needsToBeShifted = false;
        }
        ((RecordingCanvas)canvas).drawRenderNode((RenderNode)object);
        return n4;
    }

    private void ensureNoSelectionIfNonSelectable() {
        if (!this.mTextView.textCanBeSelected() && this.mTextView.hasSelection()) {
            Selection.setSelection((Spannable)this.mTextView.getText(), this.mTextView.length(), this.mTextView.length());
        }
    }

    private boolean extractTextInternal(ExtractedTextRequest extractedTextRequest, int n, int n2, int n3, ExtractedText extractedText) {
        if (extractedTextRequest != null && extractedText != null) {
            CharSequence charSequence = this.mTextView.getText();
            if (charSequence == null) {
                return false;
            }
            if (n != -2) {
                int n4 = charSequence.length();
                if (n < 0) {
                    extractedText.partialEndOffset = -1;
                    extractedText.partialStartOffset = -1;
                    n3 = 0;
                    n2 = n4;
                } else {
                    int n5 = n;
                    int n6 = n2 += n3;
                    if (charSequence instanceof Spanned) {
                        Spanned spanned = (Spanned)charSequence;
                        ParcelableSpan[] arrparcelableSpan = spanned.getSpans(n, n2, ParcelableSpan.class);
                        int n7 = arrparcelableSpan.length;
                        do {
                            n5 = n;
                            n6 = n2;
                            if (n7 <= 0) break;
                            n5 = spanned.getSpanStart(arrparcelableSpan[--n7]);
                            n6 = n;
                            if (n5 < n) {
                                n6 = n5;
                            }
                            n = spanned.getSpanEnd(arrparcelableSpan[n7]);
                            n5 = n2;
                            if (n > n2) {
                                n5 = n;
                            }
                            n = n6;
                            n2 = n5;
                        } while (true);
                    }
                    extractedText.partialStartOffset = n5;
                    extractedText.partialEndOffset = n6 - n3;
                    if (n5 > n4) {
                        n = n4;
                    } else {
                        n = n5;
                        if (n5 < 0) {
                            n = 0;
                        }
                    }
                    if (n6 > n4) {
                        n2 = n4;
                        n3 = n;
                    } else {
                        n3 = n;
                        n2 = n6;
                        if (n6 < 0) {
                            n2 = 0;
                            n3 = n;
                        }
                    }
                }
                extractedText.text = (extractedTextRequest.flags & 1) != 0 ? charSequence.subSequence(n3, n2) : TextUtils.substring(charSequence, n3, n2);
            } else {
                extractedText.partialStartOffset = 0;
                extractedText.partialEndOffset = 0;
                extractedText.text = "";
            }
            extractedText.flags = 0;
            if (MetaKeyKeyListener.getMetaState(charSequence, 2048) != 0) {
                extractedText.flags |= 2;
            }
            if (this.mTextView.isSingleLine()) {
                extractedText.flags |= 1;
            }
            extractedText.startOffset = 0;
            extractedText.selectionStart = this.mTextView.getSelectionStart();
            extractedText.selectionEnd = this.mTextView.getSelectionEnd();
            extractedText.hint = this.mTextView.getHint();
            return true;
        }
        return false;
    }

    private boolean extractedTextModeWillBeStarted() {
        boolean bl = this.mTextView.isInExtractedMode();
        boolean bl2 = false;
        if (!bl) {
            InputMethodManager inputMethodManager = this.getInputMethodManager();
            bl = bl2;
            if (inputMethodManager != null) {
                bl = bl2;
                if (inputMethodManager.isFullscreenMode()) {
                    bl = true;
                }
            }
            return bl;
        }
        return false;
    }

    private SuggestionSpan findEquivalentSuggestionSpan(SuggestionSpanInfo suggestionSpanInfo) {
        Editable editable = (Editable)this.mTextView.getText();
        if (editable.getSpanStart(suggestionSpanInfo.mSuggestionSpan) >= 0) {
            return suggestionSpanInfo.mSuggestionSpan;
        }
        for (SuggestionSpan suggestionSpan : editable.getSpans(suggestionSpanInfo.mSpanStart, suggestionSpanInfo.mSpanEnd, SuggestionSpan.class)) {
            if (editable.getSpanStart(suggestionSpan) != suggestionSpanInfo.mSpanStart || editable.getSpanEnd(suggestionSpan) != suggestionSpanInfo.mSpanEnd || !suggestionSpan.equals(suggestionSpanInfo.mSuggestionSpan)) continue;
            return suggestionSpan;
        }
        return null;
    }

    private int getAvailableDisplayListIndex(int[] arrn, int n, int n2) {
        int n3 = this.mTextRenderNodes.length;
        while (n2 < n3) {
            boolean bl;
            boolean bl2 = false;
            int n4 = 0;
            do {
                bl = bl2;
                if (n4 >= n) break;
                if (arrn[n4] == n2) {
                    bl = true;
                    break;
                }
                ++n4;
            } while (true);
            if (bl) {
                ++n2;
                continue;
            }
            return n2;
        }
        this.mTextRenderNodes = GrowingArrayUtils.append(this.mTextRenderNodes, n3, null);
        return n3;
    }

    private long getCharClusterRange(int n) {
        if (n < this.mTextView.getText().length()) {
            n = this.getNextCursorOffset(n, true);
            return TextUtils.packRangeInLong(this.getNextCursorOffset(n, false), n);
        }
        if (n - 1 >= 0) {
            n = this.getNextCursorOffset(n, false);
            return TextUtils.packRangeInLong(n, this.getNextCursorOffset(n, true));
        }
        return TextUtils.packRangeInLong(n, n);
    }

    private int getCurrentLineAdjustedForSlop(Layout layout2, int n, float f) {
        int n2 = this.mTextView.getLineAtCoordinate(f);
        if (layout2 != null && n <= layout2.getLineCount() && layout2.getLineCount() > 0 && n >= 0) {
            if (Math.abs(n2 - n) >= 2) {
                return n2;
            }
            float f2 = this.mTextView.viewportToContentVerticalOffset();
            n2 = layout2.getLineCount();
            float f3 = (float)this.mTextView.getLineHeight() * 0.5f;
            float f4 = layout2.getLineTop(0);
            f4 = Math.max((float)layout2.getLineTop(n) + f2 - f3, f4 + f2 + f3);
            float f5 = layout2.getLineBottom(n2 - 1);
            f3 = Math.min((float)layout2.getLineBottom(n) + f2 + f3, f5 + f2 - f3);
            if (f <= f4) {
                n = Math.max(n - 1, 0);
            } else if (f >= f3) {
                n = Math.min(n + 1, n2 - 1);
            }
            return n;
        }
        return n2;
    }

    private int getErrorX() {
        float f = this.mTextView.getResources().getDisplayMetrics().density;
        TextView.Drawables drawables = this.mTextView.mDrawables;
        int n = this.mTextView.getLayoutDirection();
        int n2 = 0;
        int n3 = 0;
        if (n != 1) {
            if (drawables != null) {
                n3 = drawables.mDrawableSizeRight;
            }
            n2 = -n3 / 2;
            n3 = (int)(25.0f * f + 0.5f);
            n3 = this.mTextView.getWidth() - this.mErrorPopup.getWidth() - this.mTextView.getPaddingRight() + (n2 + n3);
        } else {
            n3 = n2;
            if (drawables != null) {
                n3 = drawables.mDrawableSizeLeft;
            }
            n2 = n3 / 2;
            n3 = (int)(25.0f * f + 0.5f);
            n3 = this.mTextView.getPaddingLeft() + (n2 - n3);
        }
        return n3;
    }

    private int getErrorY() {
        int n = this.mTextView.getCompoundPaddingTop();
        int n2 = this.mTextView.getBottom();
        int n3 = this.mTextView.getTop();
        int n4 = this.mTextView.getCompoundPaddingBottom();
        TextView.Drawables drawables = this.mTextView.mDrawables;
        int n5 = this.mTextView.getLayoutDirection();
        int n6 = 0;
        int n7 = 0;
        if (n5 != 1) {
            if (drawables != null) {
                n7 = drawables.mDrawableHeightRight;
            }
        } else {
            n7 = n6;
            if (drawables != null) {
                n7 = drawables.mDrawableHeightLeft;
            }
        }
        n6 = (n2 - n3 - n4 - n - n7) / 2;
        float f = this.mTextView.getResources().getDisplayMetrics().density;
        return n6 + n + n7 - this.mTextView.getHeight() - (int)(2.0f * f + 0.5f);
    }

    private InputMethodManager getInputMethodManager() {
        return this.mTextView.getContext().getSystemService(InputMethodManager.class);
    }

    private InsertionPointCursorController getInsertionController() {
        if (!this.mInsertionControllerEnabled) {
            return null;
        }
        if (this.mInsertionPointCursorController == null) {
            this.mInsertionPointCursorController = new InsertionPointCursorController();
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mInsertionPointCursorController);
        }
        return this.mInsertionPointCursorController;
    }

    private int getLastTapPosition() {
        int n;
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null && (n = selectionModifierCursorController.getMinTouchOffset()) >= 0) {
            int n2 = n;
            if (n > this.mTextView.getText().length()) {
                n2 = this.mTextView.getText().length();
            }
            return n2;
        }
        return -1;
    }

    private long getLastTouchOffsets() {
        SelectionModifierCursorController selectionModifierCursorController = this.getSelectionController();
        return TextUtils.packRangeInLong(selectionModifierCursorController.getMinTouchOffset(), selectionModifierCursorController.getMaxTouchOffset());
    }

    private int getNextCursorOffset(int n, boolean bl) {
        Layout layout2 = this.mTextView.getLayout();
        if (layout2 == null) {
            return n;
        }
        n = bl == layout2.isRtlCharAt(n) ? layout2.getOffsetToLeftOf(n) : layout2.getOffsetToRightOf(n);
        return n;
    }

    private long getParagraphsRange(int n, int n2) {
        Layout layout2 = this.mTextView.getLayout();
        if (layout2 == null) {
            return TextUtils.packRangeInLong(-1, -1);
        }
        CharSequence charSequence = this.mTextView.getText();
        for (n = layout2.getLineForOffset((int)n); n > 0 && charSequence.charAt(layout2.getLineEnd(n - 1) - 1) != '\n'; --n) {
        }
        for (n2 = layout2.getLineForOffset((int)n2); n2 < layout2.getLineCount() - 1 && charSequence.charAt(layout2.getLineEnd(n2) - 1) != '\n'; ++n2) {
        }
        return TextUtils.packRangeInLong(layout2.getLineStart(n), layout2.getLineEnd(n2));
    }

    private PositionListener getPositionListener() {
        if (this.mPositionListener == null) {
            this.mPositionListener = new PositionListener();
        }
        return this.mPositionListener;
    }

    private SelectionActionModeHelper getSelectionActionModeHelper() {
        if (this.mSelectionActionModeHelper == null) {
            this.mSelectionActionModeHelper = new SelectionActionModeHelper(this);
        }
        return this.mSelectionActionModeHelper;
    }

    private View.DragShadowBuilder getTextThumbnailBuilder(int n, int n2) {
        TextView textView = (TextView)View.inflate(this.mTextView.getContext(), 17367308, null);
        if (textView != null) {
            int n3 = n2;
            if (n2 - n > 20) {
                n3 = TextUtils.unpackRangeEndFromLong(this.getCharClusterRange(n + 20));
            }
            textView.setText(this.mTextView.getTransformedText(n, n3));
            textView.setTextColor(this.mTextView.getTextColors());
            textView.setTextAppearance(16);
            textView.setGravity(17);
            textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            n = View.MeasureSpec.makeMeasureSpec(0, 0);
            textView.measure(n, n);
            textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
            textView.invalidate();
            return new View.DragShadowBuilder(textView);
        }
        throw new IllegalArgumentException("Unable to inflate text drag thumbnail");
    }

    private int getWordEnd(int n) {
        int n2 = this.getWordIteratorWithText().nextBoundary(n);
        n2 = this.getWordIteratorWithText().isAfterPunctuation(n2) ? this.getWordIteratorWithText().getPunctuationEnd(n) : this.getWordIteratorWithText().getNextWordEndOnTwoWordBoundary(n);
        if (n2 == -1) {
            return n;
        }
        return n2;
    }

    private WordIterator getWordIteratorWithText() {
        if (this.mWordIteratorWithText == null) {
            this.mWordIteratorWithText = new WordIterator(this.mTextView.getTextServicesLocale());
            this.mUpdateWordIteratorText = true;
        }
        if (this.mUpdateWordIteratorText) {
            CharSequence charSequence = this.mTextView.getText();
            this.mWordIteratorWithText.setCharSequence(charSequence, 0, charSequence.length());
            this.mUpdateWordIteratorText = false;
        }
        return this.mWordIteratorWithText;
    }

    private int getWordStart(int n) {
        int n2 = this.getWordIteratorWithText().prevBoundary(n);
        n2 = this.getWordIteratorWithText().isOnPunctuation(n2) ? this.getWordIteratorWithText().getPunctuationBeginning(n) : this.getWordIteratorWithText().getPrevWordBeginningOnTwoWordsBoundary(n);
        if (n2 == -1) {
            return n;
        }
        return n2;
    }

    private void hideCursorControllers() {
        if (this.mSuggestionsPopupWindow != null && (this.mTextView.isInExtractedMode() || !this.mSuggestionsPopupWindow.isShowingUp())) {
            this.mSuggestionsPopupWindow.hide();
        }
        this.hideInsertionPointCursorController();
    }

    private void hideError() {
        ErrorPopup errorPopup = this.mErrorPopup;
        if (errorPopup != null && errorPopup.isShowing()) {
            this.mErrorPopup.dismiss();
        }
        this.mShowErrorAfterAttach = false;
    }

    private void hideSpanControllers() {
        SpanController spanController = this.mSpanController;
        if (spanController != null) {
            spanController.hide();
        }
    }

    private void invalidateActionMode() {
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    private boolean isCursorInsideEasyCorrectionSpan() {
        SuggestionSpan[] arrsuggestionSpan = ((Spannable)this.mTextView.getText()).getSpans(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), SuggestionSpan.class);
        for (int i = 0; i < arrsuggestionSpan.length; ++i) {
            if ((arrsuggestionSpan[i].getFlags() & 1) == 0) continue;
            return true;
        }
        return false;
    }

    private boolean isCursorVisible() {
        boolean bl = this.mCursorVisible && this.mTextView.isTextEditable();
        return bl;
    }

    private boolean isOffsetVisible(int n) {
        Object object = this.mTextView.getLayout();
        if (object == null) {
            return false;
        }
        int n2 = ((Layout)object).getLineBottom(((Layout)object).getLineForOffset(n));
        n = (int)((Layout)object).getPrimaryHorizontal(n);
        object = this.mTextView;
        return ((TextView)object).isPositionVisible(((TextView)object).viewportToContentHorizontalOffset() + n, this.mTextView.viewportToContentVerticalOffset() + n2);
    }

    private boolean isPositionOnText(float f, float f2) {
        Layout layout2 = this.mTextView.getLayout();
        if (layout2 == null) {
            return false;
        }
        int n = this.mTextView.getLineAtCoordinate(f2);
        if ((f = this.mTextView.convertToLocalHorizontalCoordinate(f)) < layout2.getLineLeft(n)) {
            return false;
        }
        return !(f > layout2.getLineRight(n));
    }

    private static boolean isValidRange(CharSequence charSequence, int n, int n2) {
        boolean bl = n >= 0 && n <= n2 && n2 <= charSequence.length();
        return bl;
    }

    private boolean needsToSelectAllToSelectWordOrParagraph() {
        if (this.mTextView.hasPasswordTransformationMethod()) {
            return true;
        }
        int n = this.mTextView.getInputType();
        int n2 = n & 15;
        return n2 == 2 || n2 == 3 || n2 == 4 || (n &= 4080) == 16 || n == 32 || n == 208 || n == 176;
        {
        }
    }

    private void replaceWithSuggestion(SuggestionInfo arrsuggestionSpan) {
        SuggestionSpan suggestionSpan = this.findEquivalentSuggestionSpan(arrsuggestionSpan.mSuggestionSpanInfo);
        if (suggestionSpan == null) {
            return;
        }
        Editable editable = (Editable)this.mTextView.getText();
        int n = editable.getSpanStart(suggestionSpan);
        int n2 = editable.getSpanEnd(suggestionSpan);
        if (n >= 0 && n2 > n) {
            Object object;
            int n3;
            int n4;
            String string2 = TextUtils.substring(editable, n, n2);
            SuggestionSpan[] arrsuggestionSpan2 = editable.getSpans(n, n2, SuggestionSpan.class);
            int n5 = arrsuggestionSpan2.length;
            int[] arrn = new int[n5];
            int[] arrn2 = new int[n5];
            int[] arrn3 = new int[n5];
            for (n3 = 0; n3 < n5; ++n3) {
                object = arrsuggestionSpan2[n3];
                arrn[n3] = editable.getSpanStart(object);
                arrn2[n3] = editable.getSpanEnd(object);
                arrn3[n3] = editable.getSpanFlags(object);
                n4 = ((SuggestionSpan)object).getFlags();
                if ((n4 & 2) == 0) continue;
                ((SuggestionSpan)object).setFlags(n4 & -3 & -2);
            }
            n3 = arrsuggestionSpan.mSuggestionStart;
            n4 = arrsuggestionSpan.mSuggestionEnd;
            object = arrsuggestionSpan.mText.subSequence(n3, n4).toString();
            this.mTextView.replaceText_internal(n, n2, (CharSequence)object);
            suggestionSpan.getSuggestions()[arrsuggestionSpan.mSuggestionIndex] = string2;
            int n6 = ((String)object).length() - (n2 - n);
            n4 = 0;
            n3 = n5;
            arrsuggestionSpan = arrsuggestionSpan2;
            for (n5 = n4; n5 < n3; ++n5) {
                if (arrn[n5] > n || arrn2[n5] < n2) continue;
                this.mTextView.setSpan_internal(arrsuggestionSpan[n5], arrn[n5], arrn2[n5] + n6, arrn3[n5]);
            }
            n3 = n2 + n6;
            this.mTextView.setCursorPosition_internal(n3, n3);
            return;
        }
    }

    private void resumeBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.uncancel();
            this.makeBlink();
        }
    }

    private boolean selectCurrentParagraph() {
        int n;
        if (!this.mTextView.canSelectText()) {
            return false;
        }
        if (this.needsToSelectAllToSelectWordOrParagraph()) {
            return this.mTextView.selectAllText();
        }
        long l = this.getLastTouchOffsets();
        int n2 = TextUtils.unpackRangeStartFromLong(l = this.getParagraphsRange(TextUtils.unpackRangeStartFromLong(l), TextUtils.unpackRangeEndFromLong(l)));
        if (n2 < (n = TextUtils.unpackRangeEndFromLong(l))) {
            Selection.setSelection((Spannable)this.mTextView.getText(), n2, n);
            return true;
        }
        return false;
    }

    private boolean selectCurrentWordAndStartDrag() {
        Runnable runnable = this.mInsertionActionModeRunnable;
        if (runnable != null) {
            this.mTextView.removeCallbacks(runnable);
        }
        if (this.extractedTextModeWillBeStarted()) {
            return false;
        }
        if (!this.checkField()) {
            return false;
        }
        if (!this.mTextView.hasSelection() && !this.selectCurrentWord()) {
            return false;
        }
        this.stopTextActionModeWithPreservingSelection();
        this.getSelectionController().enterDrag(2);
        return true;
    }

    private void sendUpdateSelection() {
        InputMethodManager inputMethodManager;
        Object object = this.mInputMethodState;
        if (object != null && ((InputMethodState)object).mBatchEditNesting <= 0 && (inputMethodManager = this.getInputMethodManager()) != null) {
            int n;
            int n2;
            int n3 = this.mTextView.getSelectionStart();
            int n4 = this.mTextView.getSelectionEnd();
            if (this.mTextView.getText() instanceof Spannable) {
                object = (Spannable)this.mTextView.getText();
                n = EditableInputConnection.getComposingSpanStart((Spannable)object);
                n2 = EditableInputConnection.getComposingSpanEnd((Spannable)object);
            } else {
                n = -1;
                n2 = -1;
            }
            inputMethodManager.updateSelection(this.mTextView, n3, n4, n, n2);
        }
    }

    private void setErrorIcon(Drawable drawable2) {
        TextView.Drawables drawables;
        TextView.Drawables drawables2 = drawables = this.mTextView.mDrawables;
        if (drawables == null) {
            TextView textView = this.mTextView;
            drawables2 = drawables = new TextView.Drawables(textView.getContext());
            textView.mDrawables = drawables;
        }
        drawables2.setErrorDrawable(drawable2, this.mTextView);
        this.mTextView.resetResolvedDrawables();
        this.mTextView.invalidate();
        this.mTextView.requestLayout();
    }

    private boolean shouldBlink() {
        boolean bl = this.isCursorVisible();
        boolean bl2 = false;
        if (bl && this.mTextView.isFocused()) {
            int n = this.mTextView.getSelectionStart();
            if (n < 0) {
                return false;
            }
            int n2 = this.mTextView.getSelectionEnd();
            if (n2 < 0) {
                return false;
            }
            if (n == n2) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    private boolean shouldFilterOutTouchEvent(MotionEvent motionEvent) {
        if (!motionEvent.isFromSource(8194)) {
            return false;
        }
        boolean bl = ((this.mLastButtonState ^ motionEvent.getButtonState()) & 1) != 0;
        int n = motionEvent.getActionMasked();
        if (!(n != 0 && n != 1 || bl)) {
            return true;
        }
        return n == 2 && !motionEvent.isButtonPressed(1);
    }

    private boolean shouldOfferToShowSuggestions() {
        int n;
        CharSequence charSequence = this.mTextView.getText();
        if (!(charSequence instanceof Spannable)) {
            return false;
        }
        int n2 = this.mTextView.getSelectionStart();
        SuggestionSpan[] arrsuggestionSpan = (charSequence = (Spannable)charSequence).getSpans(n2, n = this.mTextView.getSelectionEnd(), SuggestionSpan.class);
        if (arrsuggestionSpan.length == 0) {
            return false;
        }
        if (n2 == n) {
            for (n = 0; n < arrsuggestionSpan.length; ++n) {
                if (arrsuggestionSpan[n].getSuggestions().length <= 0) continue;
                return true;
            }
            return false;
        }
        int n3 = this.mTextView.getText().length();
        int n4 = 0;
        int n5 = this.mTextView.getText().length();
        int n6 = 0;
        n = 0;
        for (int i = 0; i < arrsuggestionSpan.length; ++i) {
            int n7 = charSequence.getSpanStart(arrsuggestionSpan[i]);
            int n8 = charSequence.getSpanEnd(arrsuggestionSpan[i]);
            n3 = Math.min(n3, n7);
            n4 = Math.max(n4, n8);
            int n9 = n5;
            int n10 = n6;
            int n11 = n;
            if (n2 >= n7) {
                if (n2 > n8) {
                    n9 = n5;
                    n10 = n6;
                    n11 = n;
                } else {
                    n = n == 0 && arrsuggestionSpan[i].getSuggestions().length <= 0 ? 0 : 1;
                    n9 = Math.min(n5, n7);
                    n10 = Math.max(n6, n8);
                    n11 = n;
                }
            }
            n5 = n9;
            n6 = n10;
            n = n11;
        }
        if (n == 0) {
            return false;
        }
        if (n5 >= n6) {
            return false;
        }
        return n3 >= n5 && n4 <= n6;
        {
        }
    }

    private void showError() {
        Object object;
        if (this.mTextView.getWindowToken() == null) {
            this.mShowErrorAfterAttach = true;
            return;
        }
        if (this.mErrorPopup == null) {
            object = (TextView)LayoutInflater.from(this.mTextView.getContext()).inflate(17367319, null);
            float f = this.mTextView.getResources().getDisplayMetrics().density;
            this.mErrorPopup = new ErrorPopup((TextView)object, (int)(200.0f * f + 0.5f), (int)(50.0f * f + 0.5f));
            this.mErrorPopup.setFocusable(false);
            this.mErrorPopup.setInputMethodMode(1);
        }
        object = (TextView)this.mErrorPopup.getContentView();
        this.chooseSize(this.mErrorPopup, this.mError, (TextView)object);
        ((TextView)object).setText(this.mError);
        this.mErrorPopup.showAsDropDown(this.mTextView, this.getErrorX(), this.getErrorY(), 51);
        object = this.mErrorPopup;
        ((ErrorPopup)object).fixDirection(((PopupWindow)object).isAboveAnchor());
    }

    private void showFloatingToolbar() {
        if (this.mTextActionMode != null) {
            int n = ViewConfiguration.getDoubleTapTimeout();
            this.mTextView.postDelayed(this.mShowFloatingToolbar, n);
            this.invalidateActionModeAsync();
        }
    }

    private void startDragAndDrop() {
        this.getSelectionActionModeHelper().onSelectionDrag();
        if (this.mTextView.isInExtractedMode()) {
            return;
        }
        int n = this.mTextView.getSelectionStart();
        int n2 = this.mTextView.getSelectionEnd();
        ClipData clipData = ClipData.newPlainText(null, this.mTextView.getTransformedText(n, n2));
        DragLocalState dragLocalState = new DragLocalState(this.mTextView, n, n2);
        this.mTextView.startDragAndDrop(clipData, this.getTextThumbnailBuilder(n, n2), dragLocalState, 256);
        this.stopTextActionMode();
        if (this.hasSelectionController()) {
            this.getSelectionController().resetTouchOffsets();
        }
    }

    private void stopTextActionModeWithPreservingSelection() {
        if (this.mTextActionMode != null) {
            this.mRestartActionModeOnNextRefresh = true;
        }
        this.mPreserveSelection = true;
        this.stopTextActionMode();
        this.mPreserveSelection = false;
    }

    private void suspendBlink() {
        Blink blink = this.mBlink;
        if (blink != null) {
            blink.cancel();
        }
    }

    private boolean touchPositionIsInSelection() {
        int n = this.mTextView.getSelectionStart();
        int n2 = this.mTextView.getSelectionEnd();
        boolean bl = false;
        if (n == n2) {
            return false;
        }
        int n3 = n;
        int n4 = n2;
        if (n > n2) {
            n3 = n2;
            n4 = n;
            Selection.setSelection((Spannable)this.mTextView.getText(), n3, n4);
        }
        SelectionModifierCursorController selectionModifierCursorController = this.getSelectionController();
        n = selectionModifierCursorController.getMinTouchOffset();
        n2 = selectionModifierCursorController.getMaxTouchOffset();
        boolean bl2 = bl;
        if (n >= n3) {
            bl2 = bl;
            if (n2 < n4) {
                bl2 = true;
            }
        }
        return bl2;
    }

    private void updateCursorPosition(int n, int n2, float f) {
        this.loadCursorDrawable();
        int n3 = this.clampHorizontalPosition(this.mDrawableForCursor, f);
        int n4 = this.mDrawableForCursor.getIntrinsicWidth();
        this.mDrawableForCursor.setBounds(n3, n - this.mTempRect.top, n3 + n4, this.mTempRect.bottom + n2);
    }

    private void updateFloatingToolbarVisibility(MotionEvent motionEvent) {
        block0 : {
            block1 : {
                block2 : {
                    if (this.mTextActionMode == null) break block0;
                    int n = motionEvent.getActionMasked();
                    if (n == 1) break block1;
                    if (n == 2) break block2;
                    if (n == 3) break block1;
                    break block0;
                }
                this.hideFloatingToolbar(-1);
                break block0;
            }
            this.showFloatingToolbar();
        }
    }

    private void updateSpellCheckSpans(int n, int n2, boolean bl) {
        this.mTextView.removeAdjacentSuggestionSpans(n);
        this.mTextView.removeAdjacentSuggestionSpans(n2);
        if (this.mTextView.isTextEditable() && this.mTextView.isSuggestionsEnabled() && !this.mTextView.isInExtractedMode()) {
            SpellChecker spellChecker;
            if (this.mSpellChecker == null && bl) {
                this.mSpellChecker = new SpellChecker(this.mTextView);
            }
            if ((spellChecker = this.mSpellChecker) != null) {
                spellChecker.spellCheck(n, n2);
            }
        }
    }

    private void updateTapState(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            boolean bl = motionEvent.isFromSource(8194);
            int n2 = this.mTapState;
            this.mTapState = (n2 == 1 || n2 == 2 && bl) && SystemClock.uptimeMillis() - this.mLastTouchUpTime <= (long)ViewConfiguration.getDoubleTapTimeout() ? (this.mTapState == 1 ? 2 : 3) : 1;
        }
        if (n == 1) {
            this.mLastTouchUpTime = SystemClock.uptimeMillis();
        }
    }

    public void addSpanWatchers(Spannable spannable) {
        int n = spannable.length();
        KeyListener keyListener = this.mKeyListener;
        if (keyListener != null) {
            spannable.setSpan(keyListener, 0, n, 18);
        }
        if (this.mSpanController == null) {
            this.mSpanController = new SpanController();
        }
        spannable.setSpan(this.mSpanController, 0, n, 18);
    }

    void adjustInputType(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = this.mInputType;
        if ((n & 15) == 1) {
            if (bl || bl2) {
                this.mInputType = this.mInputType & -4081 | 128;
            }
            if (bl3) {
                this.mInputType = this.mInputType & -4081 | 224;
            }
        } else if ((n & 15) == 2 && bl4) {
            this.mInputType = n & -4081 | 16;
        }
    }

    public void beginBatchEdit() {
        this.mInBatchEditControllers = true;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int n;
            inputMethodState.mBatchEditNesting = n = inputMethodState.mBatchEditNesting + 1;
            if (n == 1) {
                inputMethodState.mCursorChanged = false;
                inputMethodState.mChangedDelta = 0;
                if (inputMethodState.mContentChanged) {
                    inputMethodState.mChangedStart = 0;
                    inputMethodState.mChangedEnd = this.mTextView.getText().length();
                } else {
                    inputMethodState.mChangedStart = -1;
                    inputMethodState.mChangedEnd = -1;
                    inputMethodState.mContentChanged = false;
                }
                this.mUndoInputFilter.beginBatchEdit();
                this.mTextView.onBeginBatchEdit();
            }
        }
    }

    boolean canRedo() {
        boolean bl;
        block3 : {
            block2 : {
                bl = true;
                UndoOwner undoOwner = this.mUndoOwner;
                if (!this.mAllowUndo) break block2;
                if (this.mUndoManager.countRedos(new UndoOwner[]{undoOwner}) > 0) break block3;
            }
            bl = false;
        }
        return bl;
    }

    boolean canUndo() {
        boolean bl;
        block3 : {
            block2 : {
                bl = true;
                UndoOwner undoOwner = this.mUndoOwner;
                if (!this.mAllowUndo) break block2;
                if (this.mUndoManager.countUndos(new UndoOwner[]{undoOwner}) > 0) break block3;
            }
            bl = false;
        }
        return bl;
    }

    boolean checkField() {
        if (this.mTextView.canSelectText() && this.mTextView.requestFocus()) {
            return true;
        }
        Log.w("TextView", "TextView does not support text selection. Selection cancelled.");
        return false;
    }

    void createInputContentTypeIfNeeded() {
        if (this.mInputContentType == null) {
            this.mInputContentType = new InputContentType();
        }
    }

    void createInputMethodStateIfNeeded() {
        if (this.mInputMethodState == null) {
            this.mInputMethodState = new InputMethodState();
        }
    }

    public void endBatchEdit() {
        this.mInBatchEditControllers = false;
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null) {
            int n;
            inputMethodState.mBatchEditNesting = n = inputMethodState.mBatchEditNesting - 1;
            if (n == 0) {
                this.finishBatchEdit(inputMethodState);
            }
        }
    }

    void ensureEndedBatchEdit() {
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState != null && inputMethodState.mBatchEditNesting != 0) {
            inputMethodState.mBatchEditNesting = 0;
            this.finishBatchEdit(inputMethodState);
        }
    }

    boolean extractText(ExtractedTextRequest extractedTextRequest, ExtractedText extractedText) {
        return this.extractTextInternal(extractedTextRequest, -1, -1, -1, extractedText);
    }

    void finishBatchEdit(InputMethodState object) {
        this.mTextView.onEndBatchEdit();
        this.mUndoInputFilter.endBatchEdit();
        if (!((InputMethodState)object).mContentChanged && !((InputMethodState)object).mSelectionModeChanged) {
            if (((InputMethodState)object).mCursorChanged) {
                this.mTextView.invalidateCursor();
            }
        } else {
            this.mTextView.updateAfterEdit();
            this.reportExtractedText();
        }
        this.sendUpdateSelection();
        if (this.mTextActionMode != null && (object = this.mTextView.hasSelection() ? this.getSelectionController() : this.getInsertionController()) != null && !object.isActive() && !object.isCursorBeingModified()) {
            object.show();
        }
    }

    void forgetUndoRedo() {
        UndoOwner[] arrundoOwner = new UndoOwner[]{this.mUndoOwner};
        this.mUndoManager.forgetUndos(arrundoOwner, -1);
        this.mUndoManager.forgetRedos(arrundoOwner, -1);
    }

    @VisibleForTesting
    public Drawable getCursorDrawable() {
        return this.mDrawableForCursor;
    }

    float getLastUpPositionX() {
        return this.mLastUpPositionX;
    }

    float getLastUpPositionY() {
        return this.mLastUpPositionY;
    }

    SelectionModifierCursorController getSelectionController() {
        if (!this.mSelectionControllerEnabled) {
            return null;
        }
        if (this.mSelectionModifierCursorController == null) {
            this.mSelectionModifierCursorController = new SelectionModifierCursorController();
            this.mTextView.getViewTreeObserver().addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
        }
        return this.mSelectionModifierCursorController;
    }

    ActionMode getTextActionMode() {
        return this.mTextActionMode;
    }

    TextView getTextView() {
        return this.mTextView;
    }

    public WordIterator getWordIterator() {
        if (this.mWordIterator == null) {
            this.mWordIterator = new WordIterator(this.mTextView.getTextServicesLocale());
        }
        return this.mWordIterator;
    }

    boolean hasInsertionController() {
        return this.mInsertionControllerEnabled;
    }

    boolean hasSelectionController() {
        return this.mSelectionControllerEnabled;
    }

    void hideCursorAndSpanControllers() {
        this.hideCursorControllers();
        this.hideSpanControllers();
    }

    void hideFloatingToolbar(int n) {
        if (this.mTextActionMode != null) {
            this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
            this.mTextActionMode.hide(n);
        }
    }

    void hideInsertionPointCursorController() {
        InsertionPointCursorController insertionPointCursorController = this.mInsertionPointCursorController;
        if (insertionPointCursorController != null) {
            insertionPointCursorController.hide();
        }
    }

    void invalidateActionModeAsync() {
        this.getSelectionActionModeHelper().invalidateActionModeAsync();
    }

    void invalidateHandlesAndActionMode() {
        CursorController cursorController = this.mSelectionModifierCursorController;
        if (cursorController != null) {
            ((SelectionModifierCursorController)cursorController).invalidateHandles();
        }
        if ((cursorController = this.mInsertionPointCursorController) != null) {
            ((InsertionPointCursorController)cursorController).invalidateHandle();
        }
        if (this.mTextActionMode != null) {
            this.invalidateActionMode();
        }
    }

    @UnsupportedAppUsage
    void invalidateTextDisplayList() {
        if (this.mTextRenderNodes != null) {
            TextRenderNode[] arrtextRenderNode;
            for (int i = 0; i < (arrtextRenderNode = this.mTextRenderNodes).length; ++i) {
                if (arrtextRenderNode[i] == null) continue;
                arrtextRenderNode[i].isDirty = true;
            }
        }
    }

    void invalidateTextDisplayList(Layout arrn, int n, int n2) {
        if (this.mTextRenderNodes != null && arrn instanceof DynamicLayout) {
            int n3 = arrn.getLineForOffset(n);
            int n4 = arrn.getLineForOffset(n2);
            DynamicLayout dynamicLayout = (DynamicLayout)arrn;
            arrn = dynamicLayout.getBlockEndLines();
            int[] arrn2 = dynamicLayout.getBlockIndices();
            int n5 = dynamicLayout.getNumberOfBlocks();
            n = 0;
            do {
                n2 = ++n;
                if (n >= n5) break;
                if (arrn[n] < n3) continue;
                n2 = n;
                break;
            } while (true);
            while (n2 < n5) {
                n = arrn2[n2];
                if (n != -1) {
                    this.mTextRenderNodes[n].isDirty = true;
                }
                if (arrn[n2] >= n4) break;
                ++n2;
            }
        }
    }

    public /* synthetic */ void lambda$startActionModeInternal$0$Editor() {
        this.stopTextActionMode();
    }

    void loadCursorDrawable() {
        if (this.mDrawableForCursor == null) {
            this.mDrawableForCursor = this.mTextView.getTextCursorDrawable();
        }
    }

    void loadHandleDrawables(boolean bl) {
        if (this.mSelectHandleCenter == null || bl) {
            this.mSelectHandleCenter = this.mTextView.getTextSelectHandle();
            if (this.hasInsertionController()) {
                this.getInsertionController().reloadHandleDrawable();
            }
        }
        if (this.mSelectHandleLeft == null || this.mSelectHandleRight == null || bl) {
            this.mSelectHandleLeft = this.mTextView.getTextSelectHandleLeft();
            this.mSelectHandleRight = this.mTextView.getTextSelectHandleRight();
            if (this.hasSelectionController()) {
                this.getSelectionController().reloadHandleDrawables();
            }
        }
    }

    void makeBlink() {
        if (this.shouldBlink()) {
            this.mShowCursor = SystemClock.uptimeMillis();
            if (this.mBlink == null) {
                this.mBlink = new Blink();
            }
            this.mTextView.removeCallbacks(this.mBlink);
            this.mTextView.postDelayed(this.mBlink, 500L);
        } else {
            Blink blink = this.mBlink;
            if (blink != null) {
                this.mTextView.removeCallbacks(blink);
            }
        }
    }

    void onAttachedToWindow() {
        ViewTreeObserver viewTreeObserver;
        if (this.mShowErrorAfterAttach) {
            this.showError();
            this.mShowErrorAfterAttach = false;
        }
        if ((viewTreeObserver = this.mTextView.getViewTreeObserver()).isAlive()) {
            CursorController cursorController = this.mInsertionPointCursorController;
            if (cursorController != null) {
                viewTreeObserver.addOnTouchModeChangeListener(cursorController);
            }
            if ((cursorController = this.mSelectionModifierCursorController) != null) {
                ((SelectionModifierCursorController)cursorController).resetTouchOffsets();
                viewTreeObserver.addOnTouchModeChangeListener(this.mSelectionModifierCursorController);
            }
            viewTreeObserver.addOnDrawListener(this.mMagnifierOnDrawListener);
        }
        this.updateSpellCheckSpans(0, this.mTextView.getText().length(), true);
        if (this.mTextView.hasSelection()) {
            this.refreshTextActionMode();
        }
        this.getPositionListener().addSubscriber(this.mCursorAnchorInfoNotifier, true);
        this.resumeBlink();
    }

    public void onCommitCorrection(CorrectionInfo correctionInfo) {
        CorrectionHighlighter correctionHighlighter = this.mCorrectionHighlighter;
        if (correctionHighlighter == null) {
            this.mCorrectionHighlighter = new CorrectionHighlighter();
        } else {
            correctionHighlighter.invalidate(false);
        }
        this.mCorrectionHighlighter.highlight(correctionInfo);
        this.mUndoInputFilter.freezeLastEdit();
    }

    void onCreateContextMenu(ContextMenu contextMenu) {
        if (!(this.mIsBeingLongClicked || Float.isNaN(this.mContextMenuAnchorX) || Float.isNaN(this.mContextMenuAnchorY))) {
            int n;
            int n2 = this.mTextView.getOffsetForPosition(this.mContextMenuAnchorX, this.mContextMenuAnchorY);
            if (n2 == -1) {
                return;
            }
            this.stopTextActionModeWithPreservingSelection();
            if (this.mTextView.canSelectText() && (n = this.mTextView.hasSelection() && n2 >= this.mTextView.getSelectionStart() && n2 <= this.mTextView.getSelectionEnd() ? 1 : 0) == 0) {
                Selection.setSelection((Spannable)this.mTextView.getText(), n2);
                this.stopTextActionMode();
            }
            if (this.shouldOfferToShowSuggestions()) {
                SuggestionInfo[] arrsuggestionInfo = new SuggestionInfo[5];
                for (n = 0; n < arrsuggestionInfo.length; ++n) {
                    arrsuggestionInfo[n] = new SuggestionInfo();
                }
                SubMenu subMenu = contextMenu.addSubMenu(0, 0, 9, 17040921);
                n2 = this.mSuggestionHelper.getSuggestionInfo(arrsuggestionInfo, null);
                for (n = 0; n < n2; ++n) {
                    final SuggestionInfo suggestionInfo = arrsuggestionInfo[n];
                    subMenu.add(0, 0, n, suggestionInfo.mText).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Editor.this.replaceWithSuggestion(suggestionInfo);
                            return true;
                        }
                    });
                }
            }
            contextMenu.add(0, 16908338, 2, 17041152).setAlphabeticShortcut('z').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setEnabled(this.mTextView.canUndo());
            contextMenu.add(0, 16908339, 3, 17040903).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setEnabled(this.mTextView.canRedo());
            contextMenu.add(0, 16908320, 4, 17039363).setAlphabeticShortcut('x').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setEnabled(this.mTextView.canCut());
            contextMenu.add(0, 16908321, 5, 17039361).setAlphabeticShortcut('c').setOnMenuItemClickListener(this.mOnContextMenuItemClickListener).setEnabled(this.mTextView.canCopy());
            contextMenu.add(0, 16908322, 6, 17039371).setAlphabeticShortcut('v').setEnabled(this.mTextView.canPaste()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(0, 16908337, 11, 17039385).setEnabled(this.mTextView.canPasteAsPlainText()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(0, 16908341, 7, 17041006).setEnabled(this.mTextView.canShare()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(0, 16908319, 8, 17039373).setAlphabeticShortcut('a').setEnabled(this.mTextView.canSelectAllText()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            contextMenu.add(0, 16908355, 10, 17039386).setEnabled(this.mTextView.canRequestAutofill()).setOnMenuItemClickListener(this.mOnContextMenuItemClickListener);
            this.mPreserveSelection = true;
            return;
        }
    }

    void onDetachedFromWindow() {
        this.getPositionListener().removeSubscriber(this.mCursorAnchorInfoNotifier);
        if (this.mError != null) {
            this.hideError();
        }
        this.suspendBlink();
        Object object = this.mInsertionPointCursorController;
        if (object != null) {
            ((InsertionPointCursorController)object).onDetached();
        }
        if ((object = this.mSelectionModifierCursorController) != null) {
            ((SelectionModifierCursorController)object).onDetached();
        }
        if ((object = this.mShowSuggestionRunnable) != null) {
            this.mTextView.removeCallbacks((Runnable)object);
        }
        if ((object = this.mInsertionActionModeRunnable) != null) {
            this.mTextView.removeCallbacks((Runnable)object);
        }
        this.mTextView.removeCallbacks(this.mShowFloatingToolbar);
        this.discardTextDisplayLists();
        object = this.mSpellChecker;
        if (object != null) {
            ((SpellChecker)object).closeSession();
            this.mSpellChecker = null;
        }
        if (((ViewTreeObserver)(object = this.mTextView.getViewTreeObserver())).isAlive()) {
            ((ViewTreeObserver)object).removeOnDrawListener(this.mMagnifierOnDrawListener);
        }
        this.hideCursorAndSpanControllers();
        this.stopTextActionModeWithPreservingSelection();
    }

    void onDraw(Canvas canvas, Layout layout2, Path object, Paint paint, int n) {
        Object object2;
        int n2 = this.mTextView.getSelectionStart();
        int n3 = this.mTextView.getSelectionEnd();
        Object object3 = this.mInputMethodState;
        if (object3 != null && ((InputMethodState)object3).mBatchEditNesting == 0 && (object2 = this.getInputMethodManager()) != null && ((InputMethodManager)object2).isActive(this.mTextView) && (((InputMethodState)object3).mContentChanged || ((InputMethodState)object3).mSelectionModeChanged)) {
            this.reportExtractedText();
        }
        if ((object3 = this.mCorrectionHighlighter) != null) {
            ((CorrectionHighlighter)object3).draw(canvas, n);
        }
        object3 = object;
        if (object != null) {
            object3 = object;
            if (n2 == n3) {
                object3 = object;
                if (this.mDrawableForCursor != null) {
                    this.drawCursor(canvas, n);
                    object3 = null;
                }
            }
        }
        object2 = this.mSelectionActionModeHelper;
        object = object3;
        if (object2 != null) {
            ((SelectionActionModeHelper)object2).onDraw(canvas);
            object = object3;
            if (this.mSelectionActionModeHelper.isDrawingHighlight()) {
                object = null;
            }
        }
        if (this.mTextView.canHaveDisplayList() && canvas.isHardwareAccelerated()) {
            this.drawHardwareAccelerated(canvas, layout2, (Path)object, paint, n);
        } else {
            layout2.draw(canvas, (Path)object, paint, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onDrop(DragEvent object) {
        int n;
        int n2;
        ClipData clipData;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        Object object2 = DragAndDropPermissions.obtain((DragEvent)object);
        if (object2 != null) {
            ((DragAndDropPermissions)object2).takeTransient();
        }
        try {
            clipData = ((DragEvent)object).getClipData();
            n2 = clipData.getItemCount();
        }
        catch (Throwable throwable) {
            if (object2 == null) throw throwable;
            ((DragAndDropPermissions)object2).release();
            throw throwable;
        }
        for (n = 0; n < n2; ++n) {
            spannableStringBuilder.append(clipData.getItemAt(n).coerceToStyledText(this.mTextView.getContext()));
        }
        if (object2 != null) {
            ((DragAndDropPermissions)object2).release();
        }
        this.mTextView.beginBatchEdit();
        this.mUndoInputFilter.freezeLastEdit();
        int n3 = this.mTextView.getOffsetForPosition(((DragEvent)object).getX(), ((DragEvent)object).getY());
        object2 = ((DragEvent)object).getLocalState();
        object = null;
        if (object2 instanceof DragLocalState) {
            object = (DragLocalState)object2;
        }
        if ((n = object != null && ((DragLocalState)object).sourceTextView == this.mTextView ? 1 : 0) != 0 && n3 >= ((DragLocalState)object).start && n3 < (n2 = ((DragLocalState)object).end)) {
            this.mTextView.endBatchEdit();
            this.mUndoInputFilter.freezeLastEdit();
            return;
        }
        int n4 = this.mTextView.getText().length();
        Selection.setSelection((Spannable)this.mTextView.getText(), n3);
        this.mTextView.replaceText_internal(n3, n3, spannableStringBuilder);
        if (n == 0) return;
        int n5 = ((DragLocalState)object).start;
        int n6 = ((DragLocalState)object).end;
        n2 = n5;
        n = n6;
        if (n3 <= n5) {
            n = this.mTextView.getText().length() - n4;
            n2 = n5 + n;
            n = n6 + n;
        }
        this.mTextView.deleteText_internal(n2, n);
        n = Math.max(0, n2 - 1);
        n2 = Math.min(this.mTextView.getText().length(), n2 + 1);
        if (n2 <= n + 1) return;
        object = this.mTextView.getTransformedText(n, n2);
        if (!Character.isSpaceChar(object.charAt(0))) return;
        if (!Character.isSpaceChar(object.charAt(1))) return;
        this.mTextView.deleteText_internal(n, n + 1);
        return;
    }

    void onFocusChanged(boolean bl, int n) {
        this.mShowCursor = SystemClock.uptimeMillis();
        this.ensureEndedBatchEdit();
        if (bl) {
            int n2 = this.mTextView.getSelectionStart();
            int n3 = this.mTextView.getSelectionEnd();
            int n4 = this.mSelectAllOnFocus && n2 == 0 && n3 == this.mTextView.getText().length() ? 1 : 0;
            bl = this.mFrozenWithFocus && this.mTextView.hasSelection() && n4 == 0;
            this.mCreatedWithASelection = bl;
            if (!this.mFrozenWithFocus || n2 < 0 || n3 < 0) {
                MovementMethod movementMethod;
                n4 = this.getLastTapPosition();
                if (n4 >= 0) {
                    Selection.setSelection((Spannable)this.mTextView.getText(), n4);
                }
                if ((movementMethod = this.mTextView.getMovementMethod()) != null) {
                    TextView textView = this.mTextView;
                    movementMethod.onTakeFocus(textView, (Spannable)textView.getText(), n);
                }
                if ((this.mTextView.isInExtractedMode() || this.mSelectionMoved) && n2 >= 0 && n3 >= 0) {
                    Selection.setSelection((Spannable)this.mTextView.getText(), n2, n3);
                }
                if (this.mSelectAllOnFocus) {
                    this.mTextView.selectAllText();
                }
                this.mTouchFocusSelected = true;
            }
            this.mFrozenWithFocus = false;
            this.mSelectionMoved = false;
            if (this.mError != null) {
                this.showError();
            }
            this.makeBlink();
        } else {
            if (this.mError != null) {
                this.hideError();
            }
            this.mTextView.onEndBatchEdit();
            if (this.mTextView.isInExtractedMode()) {
                this.hideCursorAndSpanControllers();
                this.stopTextActionModeWithPreservingSelection();
            } else {
                this.hideCursorAndSpanControllers();
                if (this.mTextView.isTemporarilyDetached()) {
                    this.stopTextActionModeWithPreservingSelection();
                } else {
                    this.stopTextActionMode();
                }
                this.downgradeEasyCorrectionSpans();
            }
            SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
            if (selectionModifierCursorController != null) {
                selectionModifierCursorController.resetTouchOffsets();
            }
            this.ensureNoSelectionIfNonSelectable();
        }
    }

    void onLocaleChanged() {
        this.mWordIterator = null;
        this.mWordIteratorWithText = null;
    }

    void onScreenStateChanged(int n) {
        if (n != 0) {
            if (n == 1) {
                this.resumeBlink();
            }
        } else {
            this.suspendBlink();
        }
    }

    void onScrollChanged() {
        Object object = this.mPositionListener;
        if (object != null) {
            ((PositionListener)object).onScrollChanged();
        }
        if ((object = this.mTextActionMode) != null) {
            ((ActionMode)object).invalidateContentRect();
        }
    }

    final void onTextOperationUserChanged() {
        SpellChecker spellChecker = this.mSpellChecker;
        if (spellChecker != null) {
            spellChecker.resetSession();
        }
    }

    void onTouchEvent(MotionEvent motionEvent) {
        Runnable runnable;
        boolean bl = this.shouldFilterOutTouchEvent(motionEvent);
        this.mLastButtonState = motionEvent.getButtonState();
        if (bl) {
            if (motionEvent.getActionMasked() == 1) {
                this.mDiscardNextActionUp = true;
            }
            return;
        }
        this.updateTapState(motionEvent);
        this.updateFloatingToolbarVisibility(motionEvent);
        if (this.hasSelectionController()) {
            this.getSelectionController().onTouchEvent(motionEvent);
        }
        if ((runnable = this.mShowSuggestionRunnable) != null) {
            this.mTextView.removeCallbacks(runnable);
            this.mShowSuggestionRunnable = null;
        }
        if (motionEvent.getActionMasked() == 1) {
            this.mLastUpPositionX = motionEvent.getX();
            this.mLastUpPositionY = motionEvent.getY();
        }
        if (motionEvent.getActionMasked() == 0) {
            this.mLastDownPositionX = motionEvent.getX();
            this.mLastDownPositionY = motionEvent.getY();
            this.mTouchFocusSelected = false;
            this.mIgnoreActionUpEvent = false;
        }
    }

    void onTouchUpEvent(MotionEvent object) {
        if (this.getSelectionActionModeHelper().resetSelection(this.getTextView().getOffsetForPosition(((MotionEvent)object).getX(), ((MotionEvent)object).getY()))) {
            return;
        }
        int n = this.mSelectAllOnFocus && this.mTextView.didTouchFocusSelect() ? 1 : 0;
        this.hideCursorAndSpanControllers();
        this.stopTextActionMode();
        CharSequence charSequence = this.mTextView.getText();
        if (n == 0 && charSequence.length() > 0) {
            n = this.mTextView.getOffsetForPosition(((MotionEvent)object).getX(), ((MotionEvent)object).getY());
            boolean bl = true ^ this.mRequestingLinkActionMode;
            if (bl) {
                Selection.setSelection((Spannable)charSequence, n);
                object = this.mSpellChecker;
                if (object != null) {
                    ((SpellChecker)object).onSelectionChanged();
                }
            }
            if (!this.extractedTextModeWillBeStarted()) {
                if (this.isCursorInsideEasyCorrectionSpan()) {
                    object = this.mInsertionActionModeRunnable;
                    if (object != null) {
                        this.mTextView.removeCallbacks((Runnable)object);
                    }
                    this.mShowSuggestionRunnable = new _$$Lambda$DZXn7FbDDFyBvNjI_iG9_hfa7kw(this);
                    this.mTextView.postDelayed(this.mShowSuggestionRunnable, ViewConfiguration.getDoubleTapTimeout());
                } else if (this.hasInsertionController()) {
                    if (bl) {
                        this.getInsertionController().show();
                    } else {
                        this.getInsertionController().hide();
                    }
                }
            }
        }
    }

    void onWindowFocusChanged(boolean bl) {
        if (bl) {
            Blink blink = this.mBlink;
            if (blink != null) {
                blink.uncancel();
                this.makeBlink();
            }
            if (this.mTextView.hasSelection() && !this.extractedTextModeWillBeStarted()) {
                this.refreshTextActionMode();
            }
        } else {
            Object object = this.mBlink;
            if (object != null) {
                ((Blink)object).cancel();
            }
            if ((object = this.mInputContentType) != null) {
                ((InputContentType)object).enterDown = false;
            }
            this.hideCursorAndSpanControllers();
            this.stopTextActionModeWithPreservingSelection();
            object = this.mSuggestionsPopupWindow;
            if (object != null) {
                ((SuggestionsPopupWindow)object).onParentLostFocus();
            }
            this.ensureEndedBatchEdit();
            this.ensureNoSelectionIfNonSelectable();
        }
    }

    public boolean performLongClick(boolean bl) {
        boolean bl2 = bl;
        if (!bl) {
            bl2 = bl;
            if (!this.isPositionOnText(this.mLastDownPositionX, this.mLastDownPositionY)) {
                bl2 = bl;
                if (this.mInsertionControllerEnabled) {
                    int n = this.mTextView.getOffsetForPosition(this.mLastDownPositionX, this.mLastDownPositionY);
                    Selection.setSelection((Spannable)this.mTextView.getText(), n);
                    this.getInsertionController().show();
                    this.mIsInsertionActionModeStartPending = true;
                    bl2 = true;
                    MetricsLogger.action(this.mTextView.getContext(), 629, 0);
                }
            }
        }
        bl = bl2;
        if (!bl2) {
            bl = bl2;
            if (this.mTextActionMode != null) {
                if (this.touchPositionIsInSelection()) {
                    this.startDragAndDrop();
                    MetricsLogger.action(this.mTextView.getContext(), 629, 2);
                } else {
                    this.stopTextActionMode();
                    this.selectCurrentWordAndStartDrag();
                    MetricsLogger.action(this.mTextView.getContext(), 629, 1);
                }
                bl = true;
            }
        }
        bl2 = bl;
        if (!bl) {
            bl2 = bl = this.selectCurrentWordAndStartDrag();
            if (bl) {
                MetricsLogger.action(this.mTextView.getContext(), 629, 1);
                bl2 = bl;
            }
        }
        return bl2;
    }

    void prepareCursorControllers() {
        boolean bl = false;
        Object object = this.mTextView.getRootView().getLayoutParams();
        boolean bl2 = object instanceof WindowManager.LayoutParams;
        boolean bl3 = true;
        if (bl2) {
            object = (WindowManager.LayoutParams)object;
            bl = ((WindowManager.LayoutParams)object).type < 1000 || ((WindowManager.LayoutParams)object).type > 1999;
        }
        bl = bl && this.mTextView.getLayout() != null;
        bl2 = bl && this.isCursorVisible();
        this.mInsertionControllerEnabled = bl2;
        bl2 = bl && this.mTextView.textCanBeSelected() ? bl3 : false;
        this.mSelectionControllerEnabled = bl2;
        if (!this.mInsertionControllerEnabled) {
            this.hideInsertionPointCursorController();
            object = this.mInsertionPointCursorController;
            if (object != null) {
                ((InsertionPointCursorController)object).onDetached();
                this.mInsertionPointCursorController = null;
            }
        }
        if (!this.mSelectionControllerEnabled) {
            this.stopTextActionMode();
            object = this.mSelectionModifierCursorController;
            if (object != null) {
                ((SelectionModifierCursorController)object).onDetached();
                this.mSelectionModifierCursorController = null;
            }
        }
    }

    void redo() {
        if (!this.mAllowUndo) {
            return;
        }
        UndoOwner undoOwner = this.mUndoOwner;
        this.mUndoManager.redo(new UndoOwner[]{undoOwner}, 1);
    }

    void refreshTextActionMode() {
        if (this.extractedTextModeWillBeStarted()) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        boolean bl = this.mTextView.hasSelection();
        SelectionModifierCursorController selectionModifierCursorController = this.getSelectionController();
        Object object = this.getInsertionController();
        if (selectionModifierCursorController != null && selectionModifierCursorController.isCursorBeingModified() || object != null && ((InsertionPointCursorController)object).isCursorBeingModified()) {
            this.mRestartActionModeOnNextRefresh = false;
            return;
        }
        if (bl) {
            this.hideInsertionPointCursorController();
            if (this.mTextActionMode == null) {
                if (this.mRestartActionModeOnNextRefresh) {
                    this.startSelectionActionModeAsync(false);
                }
            } else if (selectionModifierCursorController != null && selectionModifierCursorController.isActive()) {
                this.mTextActionMode.invalidateContentRect();
            } else {
                this.stopTextActionModeWithPreservingSelection();
                this.startSelectionActionModeAsync(false);
            }
        } else if (object != null && ((InsertionPointCursorController)object).isActive()) {
            object = this.mTextActionMode;
            if (object != null) {
                ((ActionMode)object).invalidateContentRect();
            }
        } else {
            this.stopTextActionMode();
        }
        this.mRestartActionModeOnNextRefresh = false;
    }

    void replace() {
        if (this.mSuggestionsPopupWindow == null) {
            this.mSuggestionsPopupWindow = new SuggestionsPopupWindow();
        }
        this.hideCursorAndSpanControllers();
        this.mSuggestionsPopupWindow.show();
        int n = (this.mTextView.getSelectionStart() + this.mTextView.getSelectionEnd()) / 2;
        Selection.setSelection((Spannable)this.mTextView.getText(), n);
    }

    boolean reportExtractedText() {
        InputMethodState inputMethodState = this.mInputMethodState;
        if (inputMethodState == null) {
            return false;
        }
        boolean bl = inputMethodState.mContentChanged;
        if (!bl && !inputMethodState.mSelectionModeChanged) {
            return false;
        }
        inputMethodState.mContentChanged = false;
        inputMethodState.mSelectionModeChanged = false;
        ExtractedTextRequest extractedTextRequest = inputMethodState.mExtractedTextRequest;
        if (extractedTextRequest == null) {
            return false;
        }
        InputMethodManager inputMethodManager = this.getInputMethodManager();
        if (inputMethodManager == null) {
            return false;
        }
        if (inputMethodState.mChangedStart < 0 && !bl) {
            inputMethodState.mChangedStart = -2;
        }
        if (this.extractTextInternal(extractedTextRequest, inputMethodState.mChangedStart, inputMethodState.mChangedEnd, inputMethodState.mChangedDelta, inputMethodState.mExtractedText)) {
            inputMethodManager.updateExtractedText(this.mTextView, extractedTextRequest.token, inputMethodState.mExtractedText);
            inputMethodState.mChangedStart = -1;
            inputMethodState.mChangedEnd = -1;
            inputMethodState.mChangedDelta = 0;
            inputMethodState.mContentChanged = false;
            return true;
        }
        return false;
    }

    void restoreInstanceState(ParcelableParcel parcelableParcel) {
        Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.restoreInstanceState(parcel, parcelableParcel.getClassLoader());
        this.mUndoInputFilter.restoreInstanceState(parcel);
        this.mUndoOwner = this.mUndoManager.getOwner("Editor", this);
    }

    ParcelableParcel saveInstanceState() {
        ParcelableParcel parcelableParcel = new ParcelableParcel(this.getClass().getClassLoader());
        Parcel parcel = parcelableParcel.getParcel();
        this.mUndoManager.saveInstanceState(parcel);
        this.mUndoInputFilter.saveInstanceState(parcel);
        return parcelableParcel;
    }

    boolean selectCurrentWord() {
        block6 : {
            block7 : {
                int n;
                boolean bl;
                int n2;
                block9 : {
                    int n3;
                    long l;
                    block10 : {
                        Object object;
                        block8 : {
                            boolean bl2 = this.mTextView.canSelectText();
                            bl = false;
                            if (!bl2) {
                                return false;
                            }
                            if (this.needsToSelectAllToSelectWordOrParagraph()) {
                                return this.mTextView.selectAllText();
                            }
                            l = this.getLastTouchOffsets();
                            n3 = TextUtils.unpackRangeStartFromLong(l);
                            n2 = TextUtils.unpackRangeEndFromLong(l);
                            if (n3 < 0 || n3 > this.mTextView.getText().length()) break block6;
                            if (n2 < 0 || n2 > this.mTextView.getText().length()) break block7;
                            object = ((Spanned)this.mTextView.getText()).getSpans(n3, n2, URLSpan.class);
                            if (((URLSpan[])object).length < 1) break block8;
                            object = object[0];
                            n = ((Spanned)this.mTextView.getText()).getSpanStart(object);
                            n2 = ((Spanned)this.mTextView.getText()).getSpanEnd(object);
                            break block9;
                        }
                        object = this.getWordIterator();
                        ((WordIterator)object).setCharSequence(this.mTextView.getText(), n3, n2);
                        int n4 = ((WordIterator)object).getBeginning(n3);
                        int n5 = ((WordIterator)object).getEnd(n2);
                        if (n4 == -1 || n5 == -1) break block10;
                        n = n4;
                        n2 = n5;
                        if (n4 != n5) break block9;
                    }
                    l = this.getCharClusterRange(n3);
                    n = TextUtils.unpackRangeStartFromLong(l);
                    n2 = TextUtils.unpackRangeEndFromLong(l);
                }
                Selection.setSelection((Spannable)this.mTextView.getText(), n, n2);
                if (n2 > n) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }
        return false;
    }

    void sendOnTextChanged(int n, int n2, int n3) {
        this.getSelectionActionModeHelper().onTextChanged(n, n + n2);
        this.updateSpellCheckSpans(n, n + n3, false);
        this.mUpdateWordIteratorText = true;
        this.hideCursorControllers();
        SelectionModifierCursorController selectionModifierCursorController = this.mSelectionModifierCursorController;
        if (selectionModifierCursorController != null) {
            selectionModifierCursorController.resetTouchOffsets();
        }
        this.stopTextActionMode();
    }

    void setContextMenuAnchor(float f, float f2) {
        this.mContextMenuAnchorX = f;
        this.mContextMenuAnchorY = f2;
    }

    public void setError(CharSequence object, Drawable drawable2) {
        this.mError = TextUtils.stringOrSpannedString((CharSequence)object);
        this.mErrorWasChanged = true;
        if (this.mError == null) {
            this.setErrorIcon(null);
            object = this.mErrorPopup;
            if (object != null) {
                if (((PopupWindow)object).isShowing()) {
                    this.mErrorPopup.dismiss();
                }
                this.mErrorPopup = null;
            }
            this.mShowErrorAfterAttach = false;
        } else {
            this.setErrorIcon(drawable2);
            if (this.mTextView.isFocused()) {
                this.showError();
            }
        }
    }

    void setFrame() {
        Object object = this.mErrorPopup;
        if (object != null) {
            object = (TextView)((PopupWindow)object).getContentView();
            this.chooseSize(this.mErrorPopup, this.mError, (TextView)object);
            this.mErrorPopup.update(this.mTextView, this.getErrorX(), this.getErrorY(), this.mErrorPopup.getWidth(), this.mErrorPopup.getHeight());
        }
    }

    void setRestartActionModeOnNextRefresh(boolean bl) {
        this.mRestartActionModeOnNextRefresh = bl;
    }

    boolean shouldRenderCursor() {
        boolean bl = this.isCursorVisible();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (this.mRenderCursorRegardlessTiming) {
            return true;
        }
        if ((SystemClock.uptimeMillis() - this.mShowCursor) % 1000L < 500L) {
            bl2 = true;
        }
        return bl2;
    }

    boolean startActionModeInternal(@TextActionMode int n) {
        if (this.extractedTextModeWillBeStarted()) {
            return false;
        }
        if (this.mTextActionMode != null) {
            this.invalidateActionMode();
            return false;
        }
        if (!(n == 2 || this.checkField() && this.mTextView.hasSelection())) {
            return false;
        }
        Object object = new TextActionModeCallback(n);
        TextView textView = this.mTextView;
        boolean bl = true;
        this.mTextActionMode = textView.startActionMode((ActionMode.Callback)object, 1);
        boolean bl2 = this.mTextView.isTextEditable() || this.mTextView.isTextSelectable();
        if (n == 2 && !bl2 && (object = this.mTextActionMode) instanceof FloatingActionMode) {
            ((FloatingActionMode)object).setOutsideTouchable(true, new _$$Lambda$Editor$TdqUlJ6RRep0wXYHaRH51nTa08I(this));
        }
        if (this.mTextActionMode == null) {
            bl = false;
        }
        if (bl && this.mTextView.isTextEditable() && !this.mTextView.isTextSelectable() && this.mShowSoftInputOnFocus && (object = this.getInputMethodManager()) != null) {
            ((InputMethodManager)object).showSoftInput(this.mTextView, 0, null);
        }
        return bl;
    }

    void startInsertionActionMode() {
        Object object = this.mInsertionActionModeRunnable;
        if (object != null) {
            this.mTextView.removeCallbacks((Runnable)object);
        }
        if (this.extractedTextModeWillBeStarted()) {
            return;
        }
        this.stopTextActionMode();
        object = new TextActionModeCallback(1);
        this.mTextActionMode = this.mTextView.startActionMode((ActionMode.Callback)object, 1);
        if (this.mTextActionMode != null && this.getInsertionController() != null) {
            this.getInsertionController().show();
        }
    }

    void startLinkActionModeAsync(int n, int n2) {
        if (!(this.mTextView.getText() instanceof Spannable)) {
            return;
        }
        this.stopTextActionMode();
        this.mRequestingLinkActionMode = true;
        this.getSelectionActionModeHelper().startLinkActionModeAsync(n, n2);
    }

    void startSelectionActionModeAsync(boolean bl) {
        this.getSelectionActionModeHelper().startSelectionActionModeAsync(bl);
    }

    protected void stopTextActionMode() {
        ActionMode actionMode = this.mTextActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    void undo() {
        if (!this.mAllowUndo) {
            return;
        }
        UndoOwner undoOwner = this.mUndoOwner;
        this.mUndoManager.undo(new UndoOwner[]{undoOwner}, 1);
    }

    void updateCursorPosition() {
        this.loadCursorDrawable();
        if (this.mDrawableForCursor == null) {
            return;
        }
        Layout layout2 = this.mTextView.getLayout();
        int n = this.mTextView.getSelectionStart();
        int n2 = layout2.getLineForOffset(n);
        this.updateCursorPosition(layout2.getLineTop(n2), layout2.getLineBottomWithoutSpacing(n2), layout2.getPrimaryHorizontal(n, layout2.shouldClampCursor(n2)));
    }

    private class Blink
    implements Runnable {
        private boolean mCancelled;

        private Blink() {
        }

        void cancel() {
            if (!this.mCancelled) {
                Editor.this.mTextView.removeCallbacks(this);
                this.mCancelled = true;
            }
        }

        @Override
        public void run() {
            if (this.mCancelled) {
                return;
            }
            Editor.this.mTextView.removeCallbacks(this);
            if (Editor.this.shouldBlink()) {
                if (Editor.this.mTextView.getLayout() != null) {
                    Editor.this.mTextView.invalidateCursorPath();
                }
                Editor.this.mTextView.postDelayed(this, 500L);
            }
        }

        void uncancel() {
            this.mCancelled = false;
        }
    }

    private class CorrectionHighlighter {
        private static final int FADE_OUT_DURATION = 400;
        private int mEnd;
        private long mFadingStartTime;
        private final Paint mPaint = new Paint(1);
        private final Path mPath = new Path();
        private int mStart;
        private RectF mTempRectF;

        public CorrectionHighlighter() {
            this.mPaint.setCompatibilityScaling(Editor.access$300((Editor)Editor.this).getResources().getCompatibilityInfo().applicationScale);
            this.mPaint.setStyle(Paint.Style.FILL);
        }

        private void invalidate(boolean bl) {
            if (Editor.this.mTextView.getLayout() == null) {
                return;
            }
            if (this.mTempRectF == null) {
                this.mTempRectF = new RectF();
            }
            this.mPath.computeBounds(this.mTempRectF, false);
            int n = Editor.this.mTextView.getCompoundPaddingLeft();
            int n2 = Editor.this.mTextView.getExtendedPaddingTop() + Editor.this.mTextView.getVerticalOffset(true);
            if (bl) {
                Editor.this.mTextView.postInvalidateOnAnimation((int)this.mTempRectF.left + n, (int)this.mTempRectF.top + n2, (int)this.mTempRectF.right + n, (int)this.mTempRectF.bottom + n2);
            } else {
                Editor.this.mTextView.postInvalidate((int)this.mTempRectF.left, (int)this.mTempRectF.top, (int)this.mTempRectF.right, (int)this.mTempRectF.bottom);
            }
        }

        private void stopAnimation() {
            Editor.this.mCorrectionHighlighter = null;
        }

        private boolean updatePaint() {
            long l = SystemClock.uptimeMillis() - this.mFadingStartTime;
            if (l > 400L) {
                return false;
            }
            float f = (float)l / 400.0f;
            int n = Color.alpha(Editor.access$300((Editor)Editor.this).mHighlightColor);
            int n2 = Editor.access$300((Editor)Editor.this).mHighlightColor;
            n = (int)((float)n * (1.0f - f));
            this.mPaint.setColor((n2 & 16777215) + (n << 24));
            return true;
        }

        private boolean updatePath() {
            Layout layout2 = Editor.this.mTextView.getLayout();
            if (layout2 == null) {
                return false;
            }
            int n = Editor.this.mTextView.getText().length();
            int n2 = Math.min(n, this.mStart);
            n = Math.min(n, this.mEnd);
            this.mPath.reset();
            layout2.getSelectionPath(n2, n, this.mPath);
            return true;
        }

        public void draw(Canvas canvas, int n) {
            if (this.updatePath() && this.updatePaint()) {
                if (n != 0) {
                    canvas.translate(0.0f, n);
                }
                canvas.drawPath(this.mPath, this.mPaint);
                if (n != 0) {
                    canvas.translate(0.0f, -n);
                }
                this.invalidate(true);
            } else {
                this.stopAnimation();
                this.invalidate(false);
            }
        }

        public void highlight(CorrectionInfo correctionInfo) {
            this.mStart = correctionInfo.getOffset();
            this.mEnd = this.mStart + correctionInfo.getNewText().length();
            this.mFadingStartTime = SystemClock.uptimeMillis();
            if (this.mStart < 0 || this.mEnd < 0) {
                this.stopAnimation();
            }
        }
    }

    private final class CursorAnchorInfoNotifier
    implements TextViewPositionListener {
        final CursorAnchorInfo.Builder mSelectionInfoBuilder = new CursorAnchorInfo.Builder();
        final int[] mTmpIntOffset = new int[2];
        final Matrix mViewToScreenMatrix = new Matrix();

        private CursorAnchorInfoNotifier() {
        }

        @Override
        public void updatePosition(int n, int n2, boolean bl, boolean bl2) {
            block15 : {
                CursorAnchorInfo.Builder builder;
                Object object;
                block16 : {
                    float f;
                    Layout layout2;
                    float f2;
                    int n3;
                    float f3;
                    float f4;
                    float f5;
                    block18 : {
                        block17 : {
                            object = Editor.this.mInputMethodState;
                            if (object == null || ((InputMethodState)object).mBatchEditNesting > 0) break block15;
                            object = Editor.this.getInputMethodManager();
                            if (object == null) {
                                return;
                            }
                            if (!((InputMethodManager)object).isActive(Editor.this.mTextView)) {
                                return;
                            }
                            if (!((InputMethodManager)object).isCursorAnchorInfoEnabled()) {
                                return;
                            }
                            layout2 = Editor.this.mTextView.getLayout();
                            if (layout2 == null) {
                                return;
                            }
                            builder = this.mSelectionInfoBuilder;
                            builder.reset();
                            n3 = Editor.this.mTextView.getSelectionStart();
                            builder.setSelectionRange(n3, Editor.this.mTextView.getSelectionEnd());
                            this.mViewToScreenMatrix.set(Editor.this.mTextView.getMatrix());
                            Editor.this.mTextView.getLocationOnScreen(this.mTmpIntOffset);
                            Object object2 = this.mViewToScreenMatrix;
                            Object object3 = this.mTmpIntOffset;
                            int n4 = 0;
                            ((Matrix)object2).postTranslate(object3[0], object3[1]);
                            builder.setMatrix(this.mViewToScreenMatrix);
                            f5 = Editor.this.mTextView.viewportToContentHorizontalOffset();
                            f = Editor.this.mTextView.viewportToContentVerticalOffset();
                            object3 = Editor.this.mTextView.getText();
                            if (object3 instanceof Spannable) {
                                object2 = (Spannable)object3;
                                n = EditableInputConnection.getComposingSpanStart((Spannable)object2);
                                int n5 = EditableInputConnection.getComposingSpanEnd((Spannable)object2);
                                if (n5 < n) {
                                    n2 = n5;
                                } else {
                                    n2 = n;
                                    n = n5;
                                }
                                n5 = n4;
                                if (n2 >= 0) {
                                    n5 = n4;
                                    if (n2 < n) {
                                        n5 = 1;
                                    }
                                }
                                if (n5 != 0) {
                                    builder.setComposingText(n2, object3.subSequence(n2, n));
                                    Editor.this.mTextView.populateCharacterBounds(builder, n2, n, f5, f);
                                }
                            }
                            if (n3 < 0) break block16;
                            n = layout2.getLineForOffset(n3);
                            f5 = layout2.getPrimaryHorizontal(n3) + f5;
                            f4 = (float)layout2.getLineTop(n) + f;
                            f3 = layout2.getLineBaseline(n);
                            f2 = (float)layout2.getLineBottomWithoutSpacing(n) + f;
                            bl = Editor.this.mTextView.isPositionVisible(f5, f4);
                            bl2 = Editor.this.mTextView.isPositionVisible(f5, f2);
                            n = 0;
                            if (bl || bl2) {
                                n = false | true;
                            }
                            if (!bl) break block17;
                            n2 = n;
                            if (bl2) break block18;
                        }
                        n2 = n | 2;
                    }
                    if (layout2.isRtlCharAt(n3)) {
                        n2 |= 4;
                    }
                    builder.setInsertionMarkerLocation(f5, f4, f3 + f, f2, n2);
                }
                ((InputMethodManager)object).updateCursorAnchorInfo(Editor.this.mTextView, builder.build());
                return;
            }
        }
    }

    private static interface CursorController
    extends ViewTreeObserver.OnTouchModeChangeListener {
        public void hide();

        public boolean isActive();

        public boolean isCursorBeingModified();

        public void onDetached();

        public void show();
    }

    private static class DragLocalState {
        public int end;
        public TextView sourceTextView;
        public int start;

        public DragLocalState(TextView textView, int n, int n2) {
            this.sourceTextView = textView;
            this.start = n;
            this.end = n2;
        }
    }

    private static interface EasyEditDeleteListener {
        public void onDeleteClick(EasyEditSpan var1);
    }

    private class EasyEditPopupWindow
    extends PinnedPopupWindow
    implements View.OnClickListener {
        private static final int POPUP_TEXT_LAYOUT = 17367309;
        private TextView mDeleteTextView;
        private EasyEditSpan mEasyEditSpan;
        private EasyEditDeleteListener mOnDeleteListener;

        private EasyEditPopupWindow() {
        }

        private void setOnDeleteListener(EasyEditDeleteListener easyEditDeleteListener) {
            this.mOnDeleteListener = easyEditDeleteListener;
        }

        @Override
        protected int clipVertically(int n) {
            return n;
        }

        @Override
        protected void createPopupWindow() {
            this.mPopupWindow = new PopupWindow(Editor.this.mTextView.getContext(), null, 16843464);
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setClippingEnabled(true);
        }

        @Override
        protected int getTextOffset() {
            return ((Editable)Editor.this.mTextView.getText()).getSpanEnd(this.mEasyEditSpan);
        }

        @Override
        protected int getVerticalLocalPosition(int n) {
            return Editor.this.mTextView.getLayout().getLineBottomWithoutSpacing(n);
        }

        @Override
        public void hide() {
            EasyEditSpan easyEditSpan = this.mEasyEditSpan;
            if (easyEditSpan != null) {
                easyEditSpan.setDeleteEnabled(false);
            }
            this.mOnDeleteListener = null;
            super.hide();
        }

        @Override
        protected void initContentView() {
            Object object = new LinearLayout(Editor.this.mTextView.getContext());
            ((LinearLayout)object).setOrientation(0);
            this.mContentView = object;
            this.mContentView.setBackgroundResource(17303692);
            object = (LayoutInflater)Editor.this.mTextView.getContext().getSystemService("layout_inflater");
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
            this.mDeleteTextView = (TextView)((LayoutInflater)object).inflate(17367309, null);
            this.mDeleteTextView.setLayoutParams(layoutParams);
            this.mDeleteTextView.setText(17039851);
            this.mDeleteTextView.setOnClickListener(this);
            this.mContentView.addView(this.mDeleteTextView);
        }

        @Override
        public void onClick(View object) {
            if (object == this.mDeleteTextView && (object = this.mEasyEditSpan) != null && ((EasyEditSpan)object).isDeleteEnabled() && (object = this.mOnDeleteListener) != null) {
                object.onDeleteClick(this.mEasyEditSpan);
            }
        }

        public void setEasyEditSpan(EasyEditSpan easyEditSpan) {
            this.mEasyEditSpan = easyEditSpan;
        }
    }

    public static class EditOperation
    extends UndoOperation<Editor> {
        public static final Parcelable.ClassLoaderCreator<EditOperation> CREATOR = new Parcelable.ClassLoaderCreator<EditOperation>(){

            @Override
            public EditOperation createFromParcel(Parcel parcel) {
                return new EditOperation(parcel, null);
            }

            @Override
            public EditOperation createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new EditOperation(parcel, classLoader);
            }

            public EditOperation[] newArray(int n) {
                return new EditOperation[n];
            }
        };
        private static final int TYPE_DELETE = 1;
        private static final int TYPE_INSERT = 0;
        private static final int TYPE_REPLACE = 2;
        private boolean mFrozen;
        private boolean mIsComposition;
        private int mNewCursorPos;
        private String mNewText;
        private int mOldCursorPos;
        private String mOldText;
        private int mStart;
        private int mType;

        public EditOperation(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mType = parcel.readInt();
            this.mOldText = parcel.readString();
            this.mNewText = parcel.readString();
            this.mStart = parcel.readInt();
            this.mOldCursorPos = parcel.readInt();
            this.mNewCursorPos = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = n == 1;
            this.mFrozen = bl2;
            bl2 = bl;
            if (parcel.readInt() == 1) {
                bl2 = true;
            }
            this.mIsComposition = bl2;
        }

        public EditOperation(Editor editor, String string2, int n, String string3, boolean bl) {
            super(editor.mUndoOwner);
            this.mOldText = string2;
            this.mNewText = string3;
            this.mType = this.mNewText.length() > 0 && this.mOldText.length() == 0 ? 0 : (this.mNewText.length() == 0 && this.mOldText.length() > 0 ? 1 : 2);
            this.mStart = n;
            this.mOldCursorPos = editor.mTextView.getSelectionStart();
            this.mNewCursorPos = this.mNewText.length() + n;
            this.mIsComposition = bl;
        }

        private int getNewTextEnd() {
            return this.mStart + this.mNewText.length();
        }

        private int getOldTextEnd() {
            return this.mStart + this.mOldText.length();
        }

        private String getTypeString() {
            int n = this.mType;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        return "";
                    }
                    return "replace";
                }
                return "delete";
            }
            return "insert";
        }

        private boolean mergeDeleteWith(EditOperation editOperation) {
            if (editOperation.mType != 1) {
                return false;
            }
            if (this.mStart != editOperation.getOldTextEnd()) {
                return false;
            }
            this.mStart = editOperation.mStart;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(editOperation.mOldText);
            stringBuilder.append(this.mOldText);
            this.mOldText = stringBuilder.toString();
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
            return true;
        }

        private boolean mergeInsertWith(EditOperation editOperation) {
            int n = editOperation.mType;
            if (n == 0) {
                if (this.getNewTextEnd() != editOperation.mStart) {
                    return false;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mNewText);
                stringBuilder.append(editOperation.mNewText);
                this.mNewText = stringBuilder.toString();
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mFrozen = editOperation.mFrozen;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (this.mIsComposition && n == 2 && this.mStart <= editOperation.mStart && this.getNewTextEnd() >= editOperation.getOldTextEnd()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mNewText.substring(0, editOperation.mStart - this.mStart));
                stringBuilder.append(editOperation.mNewText);
                stringBuilder.append(this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length()));
                this.mNewText = stringBuilder.toString();
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            return false;
        }

        private boolean mergeReplaceWith(EditOperation editOperation) {
            if (editOperation.mType == 0 && this.getNewTextEnd() == editOperation.mStart) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mNewText);
                stringBuilder.append(editOperation.mNewText);
                this.mNewText = stringBuilder.toString();
                this.mNewCursorPos = editOperation.mNewCursorPos;
                return true;
            }
            if (!this.mIsComposition) {
                return false;
            }
            if (editOperation.mType == 1 && this.mStart <= editOperation.mStart && this.getNewTextEnd() >= editOperation.getOldTextEnd()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mNewText.substring(0, editOperation.mStart - this.mStart));
                stringBuilder.append(this.mNewText.substring(editOperation.getOldTextEnd() - this.mStart, this.mNewText.length()));
                this.mNewText = stringBuilder.toString();
                if (this.mNewText.isEmpty()) {
                    this.mType = 1;
                }
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            if (editOperation.mType == 2 && this.mStart == editOperation.mStart && TextUtils.equals(this.mNewText, editOperation.mOldText)) {
                this.mNewText = editOperation.mNewText;
                this.mNewCursorPos = editOperation.mNewCursorPos;
                this.mIsComposition = editOperation.mIsComposition;
                return true;
            }
            return false;
        }

        private boolean mergeWith(EditOperation editOperation) {
            if (this.mFrozen) {
                return false;
            }
            int n = this.mType;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        return false;
                    }
                    return this.mergeReplaceWith(editOperation);
                }
                return this.mergeDeleteWith(editOperation);
            }
            return this.mergeInsertWith(editOperation);
        }

        private static void modifyText(Editable editable, int n, int n2, CharSequence charSequence, int n3, int n4) {
            if (Editor.isValidRange(editable, n, n2) && n3 <= editable.length() - (n2 - n)) {
                if (n != n2) {
                    editable.delete(n, n2);
                }
                if (charSequence.length() != 0) {
                    editable.insert(n3, charSequence);
                }
            }
            if (n4 >= 0 && n4 <= editable.length()) {
                Selection.setSelection(editable, n4);
            }
        }

        @Override
        public void commit() {
        }

        public void forceMergeWith(EditOperation editOperation) {
            if (this.mergeWith(editOperation)) {
                return;
            }
            Editable editable = (Editable)((Editor)this.getOwnerData()).mTextView.getText();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(editable.toString());
            EditOperation.modifyText(spannableStringBuilder, this.mStart, this.getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
            editable = new SpannableStringBuilder(editable.toString());
            EditOperation.modifyText(editable, editOperation.mStart, editOperation.getOldTextEnd(), editOperation.mNewText, editOperation.mStart, editOperation.mNewCursorPos);
            this.mType = 2;
            this.mNewText = editable.toString();
            this.mOldText = ((Object)spannableStringBuilder).toString();
            this.mStart = 0;
            this.mNewCursorPos = editOperation.mNewCursorPos;
            this.mIsComposition = editOperation.mIsComposition;
        }

        @Override
        public void redo() {
            EditOperation.modifyText((Editable)((Editor)this.getOwnerData()).mTextView.getText(), this.mStart, this.getOldTextEnd(), this.mNewText, this.mStart, this.mNewCursorPos);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[mType=");
            stringBuilder.append(this.getTypeString());
            stringBuilder.append(", mOldText=");
            stringBuilder.append(this.mOldText);
            stringBuilder.append(", mNewText=");
            stringBuilder.append(this.mNewText);
            stringBuilder.append(", mStart=");
            stringBuilder.append(this.mStart);
            stringBuilder.append(", mOldCursorPos=");
            stringBuilder.append(this.mOldCursorPos);
            stringBuilder.append(", mNewCursorPos=");
            stringBuilder.append(this.mNewCursorPos);
            stringBuilder.append(", mFrozen=");
            stringBuilder.append(this.mFrozen);
            stringBuilder.append(", mIsComposition=");
            stringBuilder.append(this.mIsComposition);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public void undo() {
            EditOperation.modifyText((Editable)((Editor)this.getOwnerData()).mTextView.getText(), this.mStart, this.getNewTextEnd(), this.mOldText, this.mStart, this.mOldCursorPos);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mType);
            parcel.writeString(this.mOldText);
            parcel.writeString(this.mNewText);
            parcel.writeInt(this.mStart);
            parcel.writeInt(this.mOldCursorPos);
            parcel.writeInt(this.mNewCursorPos);
            parcel.writeInt((int)this.mFrozen);
            parcel.writeInt((int)this.mIsComposition);
        }

    }

    private static class ErrorPopup
    extends PopupWindow {
        private boolean mAbove = false;
        private int mPopupInlineErrorAboveBackgroundId = 0;
        private int mPopupInlineErrorBackgroundId = 0;
        private final TextView mView;

        ErrorPopup(TextView textView, int n, int n2) {
            super(textView, n, n2);
            this.mView = textView;
            this.mPopupInlineErrorBackgroundId = this.getResourceId(this.mPopupInlineErrorBackgroundId, 297);
            this.mView.setBackgroundResource(this.mPopupInlineErrorBackgroundId);
        }

        private int getResourceId(int n, int n2) {
            int n3 = n;
            if (n == 0) {
                TypedArray typedArray = this.mView.getContext().obtainStyledAttributes(R.styleable.Theme);
                n3 = typedArray.getResourceId(n2, 0);
                typedArray.recycle();
            }
            return n3;
        }

        void fixDirection(boolean bl) {
            this.mAbove = bl;
            if (bl) {
                this.mPopupInlineErrorAboveBackgroundId = this.getResourceId(this.mPopupInlineErrorAboveBackgroundId, 296);
            } else {
                this.mPopupInlineErrorBackgroundId = this.getResourceId(this.mPopupInlineErrorBackgroundId, 297);
            }
            TextView textView = this.mView;
            int n = bl ? this.mPopupInlineErrorAboveBackgroundId : this.mPopupInlineErrorBackgroundId;
            textView.setBackgroundResource(n);
        }

        @Override
        public void update(int n, int n2, int n3, int n4, boolean bl) {
            super.update(n, n2, n3, n4, bl);
            bl = this.isAboveAnchor();
            if (bl != this.mAbove) {
                this.fixDirection(bl);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface HandleType {
    }

    @VisibleForTesting
    public abstract class HandleView
    extends View
    implements TextViewPositionListener {
        private static final int HISTORY_SIZE = 5;
        private static final int TOUCH_UP_FILTER_DELAY_AFTER = 150;
        private static final int TOUCH_UP_FILTER_DELAY_BEFORE = 350;
        private final PopupWindow mContainer;
        private float mCurrentDragInitialTouchRawX;
        protected Drawable mDrawable;
        protected Drawable mDrawableLtr;
        protected Drawable mDrawableRtl;
        protected int mHorizontalGravity;
        protected int mHotspotX;
        private float mIdealVerticalOffset;
        private boolean mIsDragging;
        private int mLastParentX;
        private int mLastParentXOnScreen;
        private int mLastParentY;
        private int mLastParentYOnScreen;
        private int mMinSize;
        private int mNumberPreviousOffsets;
        private boolean mPositionHasChanged;
        private int mPositionX;
        private int mPositionY;
        protected int mPrevLine;
        protected int mPreviousLineTouched;
        protected int mPreviousOffset;
        private int mPreviousOffsetIndex;
        private final int[] mPreviousOffsets;
        private final long[] mPreviousOffsetsTimes;
        private float mTextViewScaleX;
        private float mTextViewScaleY;
        private float mTouchOffsetY;
        private float mTouchToWindowOffsetX;
        private float mTouchToWindowOffsetY;

        private HandleView(Drawable drawable2, Drawable drawable3, int n) {
            super(Editor.this.mTextView.getContext());
            this.mPreviousOffset = -1;
            this.mPositionHasChanged = true;
            this.mPrevLine = -1;
            this.mPreviousLineTouched = -1;
            this.mCurrentDragInitialTouchRawX = -1.0f;
            this.mPreviousOffsetsTimes = new long[5];
            this.mPreviousOffsets = new int[5];
            this.mPreviousOffsetIndex = 0;
            this.mNumberPreviousOffsets = 0;
            this.setId(n);
            this.mContainer = new PopupWindow(Editor.this.mTextView.getContext(), null, 16843464);
            this.mContainer.setSplitTouchEnabled(true);
            this.mContainer.setClippingEnabled(false);
            this.mContainer.setWindowLayoutType(1002);
            this.mContainer.setWidth(-2);
            this.mContainer.setHeight(-2);
            this.mContainer.setContentView(this);
            this.setDrawables(drawable2, drawable3);
            this.mMinSize = Editor.this.mTextView.getContext().getResources().getDimensionPixelSize(17105435);
            n = this.getPreferredHeight();
            this.mTouchOffsetY = (float)n * -0.3f;
            this.mIdealVerticalOffset = (float)n * 0.7f;
        }

        private void addPositionToTouchUpFilter(int n) {
            this.mPreviousOffsetIndex = (this.mPreviousOffsetIndex + 1) % 5;
            int[] arrn = this.mPreviousOffsets;
            int n2 = this.mPreviousOffsetIndex;
            arrn[n2] = n;
            this.mPreviousOffsetsTimes[n2] = SystemClock.uptimeMillis();
            ++this.mNumberPreviousOffsets;
        }

        private boolean checkForTransforms() {
            if (Editor.this.mMagnifierAnimator.mMagnifierIsShowing) {
                return true;
            }
            if (Editor.this.mTextView.getRotation() == 0.0f && Editor.this.mTextView.getRotationX() == 0.0f && Editor.this.mTextView.getRotationY() == 0.0f) {
                this.mTextViewScaleX = Editor.this.mTextView.getScaleX();
                this.mTextViewScaleY = Editor.this.mTextView.getScaleY();
                for (ViewParent viewParent = Editor.access$300((Editor)Editor.this).getParent(); viewParent != null; viewParent = viewParent.getParent()) {
                    if (!(viewParent instanceof View)) continue;
                    View view = (View)((Object)viewParent);
                    if (view.getRotation() == 0.0f && view.getRotationX() == 0.0f && view.getRotationY() == 0.0f) {
                        this.mTextViewScaleX *= view.getScaleX();
                        this.mTextViewScaleY *= view.getScaleY();
                        continue;
                    }
                    return false;
                }
                return true;
            }
            return false;
        }

        private void filterOnTouchUp(boolean bl) {
            long l = SystemClock.uptimeMillis();
            int n = 0;
            int n2 = this.mPreviousOffsetIndex;
            int n3 = Math.min(this.mNumberPreviousOffsets, 5);
            while (n < n3 && l - this.mPreviousOffsetsTimes[n2] < 150L) {
                n2 = (this.mPreviousOffsetIndex - ++n + 5) % 5;
            }
            if (n > 0 && n < n3 && l - this.mPreviousOffsetsTimes[n2] > 350L) {
                this.positionAtCursorOffset(this.mPreviousOffsets[n2], false, bl);
            }
        }

        private int getHorizontalOffset() {
            int n = this.getPreferredWidth();
            int n2 = this.mDrawable.getIntrinsicWidth();
            int n3 = this.mHorizontalGravity;
            n2 = n3 != 3 ? (n3 != 5 ? (n - n2) / 2 : n - n2) : 0;
            return n2;
        }

        private HandleView getOtherSelectionHandle() {
            Object object = Editor.this.getSelectionController();
            if (object != null && ((SelectionModifierCursorController)object).isActive()) {
                object = ((SelectionModifierCursorController)object).mStartHandle != this ? ((SelectionModifierCursorController)object).mStartHandle : ((SelectionModifierCursorController)object).mEndHandle;
                return object;
            }
            return null;
        }

        private int getPreferredHeight() {
            return Math.max(this.mDrawable.getIntrinsicHeight(), this.mMinSize);
        }

        private int getPreferredWidth() {
            return Math.max(this.mDrawable.getIntrinsicWidth(), this.mMinSize);
        }

        private boolean handleOverlapsMagnifier(HandleView object, Rect rect) {
            object = ((HandleView)object).mContainer;
            if (!((PopupWindow)object).hasDecorView()) {
                return false;
            }
            return Rect.intersects(new Rect(object.getDecorViewLayoutParams().x, object.getDecorViewLayoutParams().y, object.getDecorViewLayoutParams().x + ((PopupWindow)object).getContentView().getWidth(), object.getDecorViewLayoutParams().y + ((PopupWindow)object).getContentView().getHeight()), rect);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private boolean obtainMagnifierShowCoordinates(MotionEvent var1_1, PointF var2_2) {
            var3_3 = this.getMagnifierHandleTrigger();
            if (var3_3 != 0) {
                if (var3_3 != 1) {
                    if (var3_3 != 2) {
                        return false;
                    }
                    var4_4 = Editor.access$300(Editor.this).getSelectionEnd();
                    var5_5 = Editor.access$300(Editor.this).getSelectionStart();
                } else {
                    var4_4 = Editor.access$300(Editor.this).getSelectionStart();
                    var5_5 = Editor.access$300(Editor.this).getSelectionEnd();
                }
            } else {
                var4_4 = Editor.access$300(Editor.this).getSelectionStart();
                var5_5 = -1;
            }
            if (var4_4 == -1) {
                return false;
            }
            if (var1_1.getActionMasked() == 0) {
                this.mCurrentDragInitialTouchRawX = var1_1.getRawX();
            } else if (var1_1.getActionMasked() == 1) {
                this.mCurrentDragInitialTouchRawX = -1.0f;
            }
            var6_6 = Editor.access$300(Editor.this).getLayout();
            var7_7 = var6_6.getLineForOffset(var4_4);
            var8_8 = var5_5 != -1 && var7_7 == var6_6.getLineForOffset(var5_5) ? 1 : 0;
            if (var8_8 == 0) ** GOTO lbl-1000
            var9_9 = var4_4 < var5_5 ? 1 : 0;
            var4_4 = this.getHorizontal(Editor.access$300(Editor.this).getLayout(), var4_4) < this.getHorizontal(Editor.access$300(Editor.this).getLayout(), var5_5) ? 1 : 0;
            if (var9_9 != var4_4) {
                var4_4 = 1;
            } else lbl-1000: // 2 sources:
            {
                var4_4 = 0;
            }
            var6_6 = new int[2];
            Editor.access$300(Editor.this).getLocationOnScreen(var6_6);
            var10_10 = var1_1.getRawX() - (float)var6_6[0];
            var11_11 = Editor.access$300(Editor.this).getTotalPaddingLeft() - Editor.access$300(Editor.this).getScrollX();
            var12_12 = Editor.access$300(Editor.this).getTotalPaddingLeft() - Editor.access$300(Editor.this).getScrollX();
            var11_11 = var8_8 != 0 && ((var9_9 = var3_3 == 2 ? 1 : 0) ^ var4_4) != 0 ? (var11_11 += this.getHorizontal(Editor.access$300(Editor.this).getLayout(), var5_5)) : (var11_11 += Editor.access$300(Editor.this).getLayout().getLineLeft(var7_7));
            var12_12 = var8_8 != 0 && ((var8_8 = var3_3 == 1 ? 1 : 0) ^ var4_4) != 0 ? (var12_12 += this.getHorizontal(Editor.access$300(Editor.this).getLayout(), var5_5)) : (var12_12 += Editor.access$300(Editor.this).getLayout().getLineRight(var7_7));
            var13_13 = this.mTextViewScaleX;
            var14_14 = var11_11 * var13_13;
            var12_12 *= var13_13;
            var11_11 = Math.round((float)MagnifierMotionAnimator.access$5100(Editor.access$000(Editor.this)).getWidth() / MagnifierMotionAnimator.access$5100(Editor.access$000(Editor.this)).getZoom());
            if (var10_10 < var14_14 - var11_11 / 2.0f) return false;
            if (var10_10 > var12_12 + var11_11 / 2.0f) {
                return false;
            }
            if (this.mTextViewScaleX == 1.0f) {
                var11_11 = var10_10;
            } else {
                var10_10 = var1_1.getRawX();
                var11_11 = this.mCurrentDragInitialTouchRawX;
                var11_11 = (var10_10 - var11_11) * this.mTextViewScaleX + var11_11 - (float)var6_6[0];
            }
            var2_2.x = Math.max(var14_14, Math.min(var12_12, var11_11));
            var2_2.y = ((float)(Editor.access$300(Editor.this).getLayout().getLineTop(var7_7) + Editor.access$300(Editor.this).getLayout().getLineBottom(var7_7)) / 2.0f + (float)Editor.access$300(Editor.this).getTotalPaddingTop() - (float)Editor.access$300(Editor.this).getScrollY()) * this.mTextViewScaleY;
            return true;
        }

        private void setVisible(boolean bl) {
            View view = this.mContainer.getContentView();
            int n = bl ? 0 : 4;
            view.setVisibility(n);
        }

        private boolean shouldShow() {
            if (this.mIsDragging) {
                return true;
            }
            if (Editor.this.mTextView.isInBatchEditMode()) {
                return false;
            }
            return Editor.this.mTextView.isPositionVisible(this.mPositionX + this.mHotspotX + this.getHorizontalOffset(), this.mPositionY);
        }

        private void startTouchUpFilter(int n) {
            this.mNumberPreviousOffsets = 0;
            this.addPositionToTouchUpFilter(n);
        }

        private boolean tooLargeTextForMagnifier() {
            float f = Math.round((float)Editor.this.mMagnifierAnimator.mMagnifier.getHeight() / Editor.this.mMagnifierAnimator.mMagnifier.getZoom());
            Paint.FontMetrics fontMetrics = Editor.this.mTextView.getPaint().getFontMetrics();
            float f2 = fontMetrics.descent;
            float f3 = fontMetrics.ascent;
            boolean bl = this.mTextViewScaleY * (f2 - f3) > f;
            return bl;
        }

        private void updateHandlesVisibility() {
            Object object = Editor.this.mMagnifierAnimator.mMagnifier.getPosition();
            if (object == null) {
                return;
            }
            Rect rect = new Rect(((Point)object).x, ((Point)object).y, ((Point)object).x + Editor.this.mMagnifierAnimator.mMagnifier.getWidth(), ((Point)object).y + Editor.this.mMagnifierAnimator.mMagnifier.getHeight());
            this.setVisible(this.handleOverlapsMagnifier(this, rect) ^ true);
            object = this.getOtherSelectionHandle();
            if (object != null) {
                HandleView.super.setVisible(this.handleOverlapsMagnifier((HandleView)object, rect) ^ true);
            }
        }

        protected void dismiss() {
            this.mIsDragging = false;
            this.mContainer.dismiss();
            this.onDetached();
        }

        protected final void dismissMagnifier() {
            if (Editor.this.mMagnifierAnimator != null) {
                Editor.this.mMagnifierAnimator.dismiss();
                Editor.this.mRenderCursorRegardlessTiming = false;
                Editor.this.resumeBlink();
                this.setVisible(true);
                HandleView handleView = this.getOtherSelectionHandle();
                if (handleView != null) {
                    handleView.setVisible(true);
                }
            }
        }

        public abstract int getCurrentCursorOffset();

        int getCursorHorizontalPosition(Layout layout2, int n) {
            return (int)(this.getHorizontal(layout2, n) - 0.5f);
        }

        protected int getCursorOffset() {
            return 0;
        }

        @VisibleForTesting
        public float getHorizontal(Layout layout2, int n) {
            return layout2.getPrimaryHorizontal(n);
        }

        protected abstract int getHorizontalGravity(boolean var1);

        protected abstract int getHotspotX(Drawable var1, boolean var2);

        public float getIdealVerticalOffset() {
            return this.mIdealVerticalOffset;
        }

        protected abstract int getMagnifierHandleTrigger();

        protected int getOffsetAtCoordinate(Layout layout2, int n, float f) {
            return Editor.this.mTextView.getOffsetAtCoordinate(n, f);
        }

        public void hide() {
            this.dismiss();
            Editor.this.getPositionListener().removeSubscriber(this);
        }

        @Override
        public void invalidate() {
            super.invalidate();
            if (this.isShowing()) {
                this.positionAtCursorOffset(this.getCurrentCursorOffset(), true, false);
            }
        }

        protected boolean isAtRtlRun(Layout layout2, int n) {
            return layout2.isRtlCharAt(n);
        }

        public boolean isDragging() {
            return this.mIsDragging;
        }

        public boolean isShowing() {
            return this.mContainer.isShowing();
        }

        public boolean offsetHasBeenChanged() {
            int n = this.mNumberPreviousOffsets;
            boolean bl = true;
            if (n <= 1) {
                bl = false;
            }
            return bl;
        }

        public void onDetached() {
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int n = this.mDrawable.getIntrinsicWidth();
            int n2 = this.getHorizontalOffset();
            Drawable drawable2 = this.mDrawable;
            drawable2.setBounds(n2, 0, n2 + n, drawable2.getIntrinsicHeight());
            this.mDrawable.draw(canvas);
        }

        void onHandleMoved() {
        }

        @Override
        protected void onMeasure(int n, int n2) {
            this.setMeasuredDimension(this.getPreferredWidth(), this.getPreferredHeight());
        }

        @Override
        protected void onSizeChanged(int n, int n2, int n3, int n4) {
            super.onSizeChanged(n, n2, n3, n4);
            this.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, n, n2)));
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            block4 : {
                block0 : {
                    block3 : {
                        block1 : {
                            int n;
                            block2 : {
                                Editor.this.updateFloatingToolbarVisibility(motionEvent);
                                n = motionEvent.getActionMasked();
                                if (n == 0) break block0;
                                if (n == 1) break block1;
                                if (n == 2) break block2;
                                if (n == 3) break block3;
                                break block4;
                            }
                            float f = motionEvent.getRawX();
                            float f2 = this.mLastParentXOnScreen;
                            float f3 = this.mLastParentX;
                            float f4 = motionEvent.getRawY();
                            float f5 = this.mLastParentYOnScreen;
                            n = this.mLastParentY;
                            f4 = f4 - f5 + (float)n;
                            float f6 = this.mTouchToWindowOffsetY - (float)n;
                            float f7 = f4 - (float)this.mPositionY - (float)n;
                            f5 = this.mIdealVerticalOffset;
                            f5 = f6 < f5 ? Math.max(Math.min(f7, f5), f6) : Math.min(Math.max(f7, f5), f6);
                            this.mTouchToWindowOffsetY = (float)this.mLastParentY + f5;
                            f7 = this.mTouchToWindowOffsetX;
                            f5 = this.mHotspotX;
                            f6 = this.getHorizontalOffset();
                            float f8 = this.mTouchToWindowOffsetY;
                            float f9 = this.mTouchOffsetY;
                            this.updatePosition(f - f2 + f3 - f7 + f5 + f6, f4 - f8 + f9, motionEvent.isFromSource(4098));
                            break block4;
                        }
                        this.filterOnTouchUp(motionEvent.isFromSource(4098));
                    }
                    this.mIsDragging = false;
                    this.updateDrawable(false);
                    break block4;
                }
                this.startTouchUpFilter(this.getCurrentCursorOffset());
                PositionListener positionListener = Editor.this.getPositionListener();
                this.mLastParentX = positionListener.getPositionX();
                this.mLastParentY = positionListener.getPositionY();
                this.mLastParentXOnScreen = positionListener.getPositionXOnScreen();
                this.mLastParentYOnScreen = positionListener.getPositionYOnScreen();
                float f = motionEvent.getRawX();
                float f10 = this.mLastParentXOnScreen;
                float f11 = this.mLastParentX;
                float f12 = motionEvent.getRawY();
                float f13 = this.mLastParentYOnScreen;
                float f14 = this.mLastParentY;
                this.mTouchToWindowOffsetX = f - f10 + f11 - (float)this.mPositionX;
                this.mTouchToWindowOffsetY = f12 - f13 + f14 - (float)this.mPositionY;
                this.mIsDragging = true;
                this.mPreviousLineTouched = -1;
            }
            return true;
        }

        protected void positionAtCursorOffset(int n, boolean bl, boolean bl2) {
            if (Editor.this.mTextView.getLayout() == null) {
                Editor.this.prepareCursorControllers();
                return;
            }
            Layout layout2 = Editor.this.mTextView.getLayout();
            int n2 = n != this.mPreviousOffset ? 1 : 0;
            if (n2 != 0 || bl) {
                if (n2 != 0) {
                    this.updateSelection(n);
                    if (bl2 && Editor.this.mHapticTextHandleEnabled) {
                        Editor.this.mTextView.performHapticFeedback(9);
                    }
                    this.addPositionToTouchUpFilter(n);
                }
                this.mPrevLine = n2 = layout2.getLineForOffset(n);
                this.mPositionX = this.getCursorHorizontalPosition(layout2, n) - this.mHotspotX - this.getHorizontalOffset() + this.getCursorOffset();
                this.mPositionY = layout2.getLineBottomWithoutSpacing(n2);
                this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionY += Editor.this.mTextView.viewportToContentVerticalOffset();
                this.mPreviousOffset = n;
                this.mPositionHasChanged = true;
            }
        }

        void setDrawables(Drawable drawable2, Drawable drawable3) {
            this.mDrawableLtr = drawable2;
            this.mDrawableRtl = drawable3;
            this.updateDrawable(true);
        }

        public void show() {
            if (this.isShowing()) {
                return;
            }
            Editor.this.getPositionListener().addSubscriber(this, true);
            this.mPreviousOffset = -1;
            this.positionAtCursorOffset(this.getCurrentCursorOffset(), false, false);
        }

        protected void updateDrawable(boolean bl) {
            if (!bl && this.mIsDragging) {
                return;
            }
            Layout layout2 = Editor.this.mTextView.getLayout();
            if (layout2 == null) {
                return;
            }
            int n = this.getCurrentCursorOffset();
            bl = this.isAtRtlRun(layout2, n);
            Drawable drawable2 = this.mDrawable;
            Drawable drawable3 = bl ? this.mDrawableRtl : this.mDrawableLtr;
            this.mDrawable = drawable3;
            this.mHotspotX = this.getHotspotX(this.mDrawable, bl);
            this.mHorizontalGravity = this.getHorizontalGravity(bl);
            if (drawable2 != this.mDrawable && this.isShowing()) {
                this.mPositionX = this.getCursorHorizontalPosition(layout2, n) - this.mHotspotX - this.getHorizontalOffset() + this.getCursorOffset();
                this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
                this.mPositionHasChanged = true;
                this.updatePosition(this.mLastParentX, this.mLastParentY, false, false);
                this.postInvalidate();
            }
        }

        protected final void updateMagnifier(MotionEvent motionEvent) {
            if (Editor.this.mMagnifierAnimator == null) {
                return;
            }
            PointF pointF = new PointF();
            boolean bl = this.checkForTransforms() && !this.tooLargeTextForMagnifier() && this.obtainMagnifierShowCoordinates(motionEvent, pointF);
            if (bl) {
                Editor.this.mRenderCursorRegardlessTiming = true;
                Editor.this.mTextView.invalidateCursorPath();
                Editor.this.suspendBlink();
                Editor.this.mMagnifierAnimator.show(pointF.x, pointF.y);
                this.updateHandlesVisibility();
            } else {
                this.dismissMagnifier();
            }
        }

        protected abstract void updatePosition(float var1, float var2, boolean var3);

        @Override
        public void updatePosition(int n, int n2, boolean bl, boolean bl2) {
            this.positionAtCursorOffset(this.getCurrentCursorOffset(), bl2, false);
            if (bl || this.mPositionHasChanged) {
                if (this.mIsDragging) {
                    if (n != this.mLastParentX || n2 != this.mLastParentY) {
                        this.mTouchToWindowOffsetX += (float)(n - this.mLastParentX);
                        this.mTouchToWindowOffsetY += (float)(n2 - this.mLastParentY);
                        this.mLastParentX = n;
                        this.mLastParentY = n2;
                    }
                    this.onHandleMoved();
                }
                if (this.shouldShow()) {
                    int[] arrn = new int[]{this.mPositionX + this.mHotspotX + this.getHorizontalOffset(), this.mPositionY};
                    Editor.this.mTextView.transformFromViewToWindowSpace(arrn);
                    arrn[0] = arrn[0] - (this.mHotspotX + this.getHorizontalOffset());
                    if (this.isShowing()) {
                        this.mContainer.update(arrn[0], arrn[1], -1, -1);
                    } else {
                        this.mContainer.showAtLocation(Editor.this.mTextView, 0, arrn[0], arrn[1]);
                    }
                } else if (this.isShowing()) {
                    this.dismiss();
                }
                this.mPositionHasChanged = false;
            }
        }

        protected abstract void updateSelection(int var1);
    }

    static class InputContentType {
        boolean enterDown;
        Bundle extras;
        int imeActionId;
        CharSequence imeActionLabel;
        LocaleList imeHintLocales;
        int imeOptions = 0;
        TextView.OnEditorActionListener onEditorActionListener;
        @UnsupportedAppUsage
        String privateImeOptions;

        InputContentType() {
        }
    }

    static class InputMethodState {
        int mBatchEditNesting;
        int mChangedDelta;
        int mChangedEnd;
        int mChangedStart;
        boolean mContentChanged;
        boolean mCursorChanged;
        final ExtractedText mExtractedText = new ExtractedText();
        ExtractedTextRequest mExtractedTextRequest;
        boolean mSelectionModeChanged;

        InputMethodState() {
        }
    }

    private class InsertionHandleView
    extends HandleView {
        private static final int DELAY_BEFORE_HANDLE_FADES_OUT = 4000;
        private static final int RECENT_CUT_COPY_DURATION = 15000;
        private float mDownPositionX;
        private float mDownPositionY;
        private Runnable mHider;

        public InsertionHandleView(Drawable drawable2) {
            super(drawable2, drawable2, 16909028);
        }

        private void hideAfterDelay() {
            if (this.mHider == null) {
                this.mHider = new Runnable(){

                    @Override
                    public void run() {
                        InsertionHandleView.this.hide();
                    }
                };
            } else {
                this.removeHiderCallback();
            }
            Editor.this.mTextView.postDelayed(this.mHider, 4000L);
        }

        private void removeHiderCallback() {
            if (this.mHider != null) {
                Editor.this.mTextView.removeCallbacks(this.mHider);
            }
        }

        @Override
        public int getCurrentCursorOffset() {
            return Editor.this.mTextView.getSelectionStart();
        }

        @Override
        int getCursorHorizontalPosition(Layout object, int n) {
            if (Editor.this.mDrawableForCursor != null) {
                float f = this.getHorizontal((Layout)object, n);
                object = Editor.this;
                return ((Editor)object).clampHorizontalPosition(((Editor)object).mDrawableForCursor, f) + Editor.access$3900((Editor)Editor.this).left;
            }
            return super.getCursorHorizontalPosition((Layout)object, n);
        }

        @Override
        protected int getCursorOffset() {
            int n;
            int n2 = n = super.getCursorOffset();
            if (Editor.this.mDrawableForCursor != null) {
                Editor.this.mDrawableForCursor.getPadding(Editor.this.mTempRect);
                n2 = n + (Editor.this.mDrawableForCursor.getIntrinsicWidth() - Editor.access$3900((Editor)Editor.this).left - Editor.access$3900((Editor)Editor.this).right) / 2;
            }
            return n2;
        }

        @Override
        protected int getHorizontalGravity(boolean bl) {
            return 1;
        }

        @Override
        protected int getHotspotX(Drawable drawable2, boolean bl) {
            return drawable2.getIntrinsicWidth() / 2;
        }

        @Override
        protected int getMagnifierHandleTrigger() {
            return 0;
        }

        @Override
        public void onDetached() {
            super.onDetached();
            this.removeHiderCallback();
        }

        @Override
        void onHandleMoved() {
            super.onHandleMoved();
            this.removeHiderCallback();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean bl;
            block10 : {
                block6 : {
                    block9 : {
                        int n;
                        block7 : {
                            block8 : {
                                bl = super.onTouchEvent(motionEvent);
                                n = motionEvent.getActionMasked();
                                if (n == 0) break block6;
                                if (n == 1) break block7;
                                if (n == 2) break block8;
                                if (n == 3) break block9;
                                break block10;
                            }
                            this.updateMagnifier(motionEvent);
                            break block10;
                        }
                        if (!this.offsetHasBeenChanged()) {
                            float f;
                            float f2 = this.mDownPositionX - motionEvent.getRawX();
                            if (f2 * f2 + (f = this.mDownPositionY - motionEvent.getRawY()) * f < (float)((n = ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledTouchSlop()) * n)) {
                                if (Editor.this.mTextActionMode != null) {
                                    Editor.this.stopTextActionMode();
                                } else {
                                    Editor.this.startInsertionActionMode();
                                }
                            }
                        } else if (Editor.this.mTextActionMode != null) {
                            Editor.this.mTextActionMode.invalidateContentRect();
                        }
                    }
                    this.hideAfterDelay();
                    this.dismissMagnifier();
                    break block10;
                }
                this.mDownPositionX = motionEvent.getRawX();
                this.mDownPositionY = motionEvent.getRawY();
                this.updateMagnifier(motionEvent);
            }
            return bl;
        }

        @Override
        public void show() {
            super.show();
            long l = SystemClock.uptimeMillis();
            long l2 = TextView.sLastCutCopyOrTextChangedTime;
            if (Editor.this.mInsertionActionModeRunnable != null && (Editor.this.mTapState == 2 || Editor.this.mTapState == 3 || Editor.this.isCursorInsideEasyCorrectionSpan())) {
                Editor.this.mTextView.removeCallbacks(Editor.this.mInsertionActionModeRunnable);
            }
            if (Editor.this.mTapState != 2 && Editor.this.mTapState != 3 && !Editor.this.isCursorInsideEasyCorrectionSpan() && l - l2 < 15000L && Editor.this.mTextActionMode == null) {
                if (Editor.this.mInsertionActionModeRunnable == null) {
                    Editor.this.mInsertionActionModeRunnable = new Runnable(){

                        @Override
                        public void run() {
                            Editor.this.startInsertionActionMode();
                        }
                    };
                }
                Editor.this.mTextView.postDelayed(Editor.this.mInsertionActionModeRunnable, ViewConfiguration.getDoubleTapTimeout() + 1);
            }
            this.hideAfterDelay();
        }

        @Override
        protected void updatePosition(float f, float f2, boolean bl) {
            int n;
            Layout layout2 = Editor.this.mTextView.getLayout();
            if (layout2 != null) {
                if (this.mPreviousLineTouched == -1) {
                    this.mPreviousLineTouched = Editor.this.mTextView.getLineAtCoordinate(f2);
                }
                int n2 = Editor.this.getCurrentLineAdjustedForSlop(layout2, this.mPreviousLineTouched, f2);
                n = this.getOffsetAtCoordinate(layout2, n2, f);
                this.mPreviousLineTouched = n2;
            } else {
                n = -1;
            }
            this.positionAtCursorOffset(n, false, bl);
            if (Editor.this.mTextActionMode != null) {
                Editor.this.invalidateActionMode();
            }
        }

        @Override
        public void updateSelection(int n) {
            Selection.setSelection((Spannable)Editor.this.mTextView.getText(), n);
        }

    }

    private class InsertionPointCursorController
    implements CursorController {
        private InsertionHandleView mHandle;

        private InsertionPointCursorController() {
        }

        private InsertionHandleView getHandle() {
            if (this.mHandle == null) {
                Editor.this.loadHandleDrawables(false);
                Editor editor = Editor.this;
                this.mHandle = editor.new InsertionHandleView(editor.mSelectHandleCenter);
            }
            return this.mHandle;
        }

        private void reloadHandleDrawable() {
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView == null) {
                return;
            }
            insertionHandleView.setDrawables(Editor.this.mSelectHandleCenter, Editor.this.mSelectHandleCenter);
        }

        @Override
        public void hide() {
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.hide();
            }
        }

        public void invalidateHandle() {
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.invalidate();
            }
        }

        @Override
        public boolean isActive() {
            InsertionHandleView insertionHandleView = this.mHandle;
            boolean bl = insertionHandleView != null && insertionHandleView.isShowing();
            return bl;
        }

        @Override
        public boolean isCursorBeingModified() {
            InsertionHandleView insertionHandleView = this.mHandle;
            boolean bl = insertionHandleView != null && insertionHandleView.isDragging();
            return bl;
        }

        @Override
        public void onDetached() {
            Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            InsertionHandleView insertionHandleView = this.mHandle;
            if (insertionHandleView != null) {
                insertionHandleView.onDetached();
            }
        }

        @Override
        public void onTouchModeChanged(boolean bl) {
            if (!bl) {
                this.hide();
            }
        }

        @Override
        public void show() {
            this.getHandle().show();
            if (Editor.this.mSelectionModifierCursorController != null) {
                Editor.this.mSelectionModifierCursorController.hide();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface MagnifierHandleTrigger {
        public static final int INSERTION = 0;
        public static final int SELECTION_END = 2;
        public static final int SELECTION_START = 1;
    }

    private static class MagnifierMotionAnimator {
        private static final long DURATION = 100L;
        private float mAnimationCurrentX;
        private float mAnimationCurrentY;
        private float mAnimationStartX;
        private float mAnimationStartY;
        private final ValueAnimator mAnimator;
        private float mLastX;
        private float mLastY;
        private final Magnifier mMagnifier;
        private boolean mMagnifierIsShowing;

        private MagnifierMotionAnimator(Magnifier magnifier) {
            this.mMagnifier = magnifier;
            this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAnimator.setDuration(100L);
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.addUpdateListener(new _$$Lambda$Editor$MagnifierMotionAnimator$E_RaelOMgCHAzvKgSSZE_hDYeIg(this));
        }

        private void dismiss() {
            this.mMagnifier.dismiss();
            this.mAnimator.cancel();
            this.mMagnifierIsShowing = false;
        }

        private void show(float f, float f2) {
            boolean bl = this.mMagnifierIsShowing && f2 != this.mLastY;
            if (bl) {
                if (this.mAnimator.isRunning()) {
                    this.mAnimator.cancel();
                    this.mAnimationStartX = this.mAnimationCurrentX;
                    this.mAnimationStartY = this.mAnimationCurrentY;
                } else {
                    this.mAnimationStartX = this.mLastX;
                    this.mAnimationStartY = this.mLastY;
                }
                this.mAnimator.start();
            } else if (!this.mAnimator.isRunning()) {
                this.mMagnifier.show(f, f2);
            }
            this.mLastX = f;
            this.mLastY = f2;
            this.mMagnifierIsShowing = true;
        }

        private void update() {
            this.mMagnifier.update();
        }

        public /* synthetic */ void lambda$new$0$Editor$MagnifierMotionAnimator(ValueAnimator valueAnimator) {
            float f = this.mAnimationStartX;
            this.mAnimationCurrentX = f + (this.mLastX - f) * valueAnimator.getAnimatedFraction();
            f = this.mAnimationStartY;
            this.mAnimationCurrentY = f + (this.mLastY - f) * valueAnimator.getAnimatedFraction();
            this.mMagnifier.show(this.mAnimationCurrentX, this.mAnimationCurrentY);
        }
    }

    private abstract class PinnedPopupWindow
    implements TextViewPositionListener {
        int mClippingLimitLeft;
        int mClippingLimitRight;
        protected ViewGroup mContentView;
        protected PopupWindow mPopupWindow;
        int mPositionX;
        int mPositionY;

        public PinnedPopupWindow() {
            this.setUp();
            this.createPopupWindow();
            this.mPopupWindow.setWindowLayoutType(1005);
            this.mPopupWindow.setWidth(-2);
            this.mPopupWindow.setHeight(-2);
            this.initContentView();
            Editor.this = new ViewGroup.LayoutParams(-2, -2);
            this.mContentView.setLayoutParams((ViewGroup.LayoutParams)Editor.this);
            this.mPopupWindow.setContentView(this.mContentView);
        }

        private void computeLocalPosition() {
            this.measureContent();
            int n = this.mContentView.getMeasuredWidth();
            int n2 = this.getTextOffset();
            this.mPositionX = (int)(Editor.this.mTextView.getLayout().getPrimaryHorizontal(n2) - (float)n / 2.0f);
            this.mPositionX += Editor.this.mTextView.viewportToContentHorizontalOffset();
            this.mPositionY = this.getVerticalLocalPosition(Editor.this.mTextView.getLayout().getLineForOffset(n2));
            this.mPositionY += Editor.this.mTextView.viewportToContentVerticalOffset();
        }

        private void updatePosition(int n, int n2) {
            int n3 = this.mPositionX;
            n2 = this.clipVertically(this.mPositionY + n2);
            DisplayMetrics displayMetrics = Editor.this.mTextView.getResources().getDisplayMetrics();
            int n4 = this.mContentView.getMeasuredWidth();
            n = Math.min(displayMetrics.widthPixels - n4 + this.mClippingLimitRight, n3 + n);
            n = Math.max(-this.mClippingLimitLeft, n);
            if (this.isShowing()) {
                this.mPopupWindow.update(n, n2, -1, -1);
            } else {
                this.mPopupWindow.showAtLocation(Editor.this.mTextView, 0, n, n2);
            }
        }

        protected abstract int clipVertically(int var1);

        protected abstract void createPopupWindow();

        protected abstract int getTextOffset();

        protected abstract int getVerticalLocalPosition(int var1);

        public void hide() {
            if (!this.isShowing()) {
                return;
            }
            this.mPopupWindow.dismiss();
            Editor.this.getPositionListener().removeSubscriber(this);
        }

        protected abstract void initContentView();

        public boolean isShowing() {
            return this.mPopupWindow.isShowing();
        }

        protected void measureContent() {
            DisplayMetrics displayMetrics = Editor.this.mTextView.getResources().getDisplayMetrics();
            this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, Integer.MIN_VALUE));
        }

        protected void setUp() {
        }

        public void show() {
            Editor.this.getPositionListener().addSubscriber(this, false);
            this.computeLocalPosition();
            PositionListener positionListener = Editor.this.getPositionListener();
            this.updatePosition(positionListener.getPositionX(), positionListener.getPositionY());
        }

        @Override
        public void updatePosition(int n, int n2, boolean bl, boolean bl2) {
            if (this.isShowing() && Editor.this.isOffsetVisible(this.getTextOffset())) {
                if (bl2) {
                    this.computeLocalPosition();
                }
                this.updatePosition(n, n2);
            } else {
                this.hide();
            }
        }
    }

    private class PositionListener
    implements ViewTreeObserver.OnPreDrawListener {
        private static final int MAXIMUM_NUMBER_OF_LISTENERS = 7;
        private boolean[] mCanMove = new boolean[7];
        private int mNumberOfListeners;
        private boolean mPositionHasChanged = true;
        private TextViewPositionListener[] mPositionListeners = new TextViewPositionListener[7];
        private int mPositionX;
        private int mPositionXOnScreen;
        private int mPositionY;
        private int mPositionYOnScreen;
        private boolean mScrollHasChanged;
        final int[] mTempCoords = new int[2];

        private PositionListener() {
        }

        private void updatePosition() {
            Editor.this.mTextView.getLocationInWindow(this.mTempCoords);
            int[] arrn = this.mTempCoords;
            boolean bl = arrn[0] != this.mPositionX || arrn[1] != this.mPositionY;
            this.mPositionHasChanged = bl;
            arrn = this.mTempCoords;
            this.mPositionX = arrn[0];
            this.mPositionY = arrn[1];
            Editor.this.mTextView.getLocationOnScreen(this.mTempCoords);
            arrn = this.mTempCoords;
            this.mPositionXOnScreen = arrn[0];
            this.mPositionYOnScreen = arrn[1];
        }

        public void addSubscriber(TextViewPositionListener textViewPositionListener, boolean bl) {
            if (this.mNumberOfListeners == 0) {
                this.updatePosition();
                Editor.this.mTextView.getViewTreeObserver().addOnPreDrawListener(this);
            }
            int n = -1;
            for (int i = 0; i < 7; ++i) {
                TextViewPositionListener textViewPositionListener2 = this.mPositionListeners[i];
                if (textViewPositionListener2 == textViewPositionListener) {
                    return;
                }
                int n2 = n;
                if (n < 0) {
                    n2 = n;
                    if (textViewPositionListener2 == null) {
                        n2 = i;
                    }
                }
                n = n2;
            }
            this.mPositionListeners[n] = textViewPositionListener;
            this.mCanMove[n] = bl;
            ++this.mNumberOfListeners;
        }

        public int getPositionX() {
            return this.mPositionX;
        }

        public int getPositionXOnScreen() {
            return this.mPositionXOnScreen;
        }

        public int getPositionY() {
            return this.mPositionY;
        }

        public int getPositionYOnScreen() {
            return this.mPositionYOnScreen;
        }

        @Override
        public boolean onPreDraw() {
            this.updatePosition();
            for (int i = 0; i < 7; ++i) {
                TextViewPositionListener textViewPositionListener;
                if (!this.mPositionHasChanged && !this.mScrollHasChanged && !this.mCanMove[i] || (textViewPositionListener = this.mPositionListeners[i]) == null) continue;
                textViewPositionListener.updatePosition(this.mPositionX, this.mPositionY, this.mPositionHasChanged, this.mScrollHasChanged);
            }
            this.mScrollHasChanged = false;
            return true;
        }

        public void onScrollChanged() {
            this.mScrollHasChanged = true;
        }

        public void removeSubscriber(TextViewPositionListener textViewPositionListener) {
            for (int i = 0; i < 7; ++i) {
                TextViewPositionListener[] arrtextViewPositionListener = this.mPositionListeners;
                if (arrtextViewPositionListener[i] != textViewPositionListener) continue;
                arrtextViewPositionListener[i] = null;
                --this.mNumberOfListeners;
                break;
            }
            if (this.mNumberOfListeners == 0) {
                Editor.this.mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
            }
        }
    }

    static final class ProcessTextIntentActionsHandler {
        private final SparseArray<AccessibilityNodeInfo.AccessibilityAction> mAccessibilityActions = new SparseArray();
        private final SparseArray<Intent> mAccessibilityIntents = new SparseArray();
        private final Context mContext;
        private final Editor mEditor;
        private final PackageManager mPackageManager;
        private final String mPackageName;
        private final List<ResolveInfo> mSupportedActivities = new ArrayList<ResolveInfo>();
        private final TextView mTextView;

        private ProcessTextIntentActionsHandler(Editor editor) {
            this.mEditor = Preconditions.checkNotNull(editor);
            this.mTextView = Preconditions.checkNotNull(this.mEditor.mTextView);
            this.mContext = Preconditions.checkNotNull(this.mTextView.getContext());
            this.mPackageManager = Preconditions.checkNotNull(this.mContext.getPackageManager());
            this.mPackageName = Preconditions.checkNotNull(this.mContext.getPackageName());
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo) {
            return this.createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", this.mTextView.isTextEditable() ^ true).setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        }

        private boolean fireIntent(Intent intent) {
            if (intent != null && "android.intent.action.PROCESS_TEXT".equals(intent.getAction())) {
                intent.putExtra("android.intent.extra.PROCESS_TEXT", TextUtils.trimToParcelableSize(this.mTextView.getSelectedText()));
                this.mEditor.mPreserveSelection = true;
                this.mTextView.startActivityForResult(intent, 100);
                return true;
            }
            return false;
        }

        private CharSequence getLabel(ResolveInfo resolveInfo) {
            return resolveInfo.loadLabel(this.mPackageManager);
        }

        private boolean isSupportedActivity(ResolveInfo resolveInfo) {
            boolean bl = this.mPackageName.equals(resolveInfo.activityInfo.packageName) || resolveInfo.activityInfo.exported && (resolveInfo.activityInfo.permission == null || this.mContext.checkSelfPermission(resolveInfo.activityInfo.permission) == 0);
            return bl;
        }

        private void loadSupportedActivities() {
            this.mSupportedActivities.clear();
            if (!this.mContext.canStartActivityForResult()) {
                return;
            }
            PackageManager packageManager = this.mTextView.getContext().getPackageManager();
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(this.createProcessTextIntent(), 0)) {
                if (!this.isSupportedActivity(resolveInfo)) continue;
                this.mSupportedActivities.add(resolveInfo);
            }
        }

        public void initializeAccessibilityActions() {
            this.mAccessibilityIntents.clear();
            this.mAccessibilityActions.clear();
            int n = 0;
            this.loadSupportedActivities();
            for (ResolveInfo resolveInfo : this.mSupportedActivities) {
                int n2 = n + 268435712;
                this.mAccessibilityActions.put(n2, new AccessibilityNodeInfo.AccessibilityAction(n2, this.getLabel(resolveInfo)));
                this.mAccessibilityIntents.put(n2, this.createProcessTextIntentForResolveInfo(resolveInfo));
                ++n;
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            for (int i = 0; i < this.mAccessibilityActions.size(); ++i) {
                accessibilityNodeInfo.addAction(this.mAccessibilityActions.valueAt(i));
            }
        }

        public void onInitializeMenu(Menu menu2) {
            this.loadSupportedActivities();
            int n = this.mSupportedActivities.size();
            for (int i = 0; i < n; ++i) {
                ResolveInfo resolveInfo = this.mSupportedActivities.get(i);
                menu2.add(0, 0, i + 100, this.getLabel(resolveInfo)).setIntent(this.createProcessTextIntentForResolveInfo(resolveInfo)).setShowAsAction(0);
            }
        }

        public boolean performAccessibilityAction(int n) {
            return this.fireIntent(this.mAccessibilityIntents.get(n));
        }

        public boolean performMenuItemAction(MenuItem menuItem) {
            return this.fireIntent(menuItem.getIntent());
        }
    }

    @VisibleForTesting
    public final class SelectionHandleView
    extends HandleView {
        private final int mHandleType;
        private boolean mInWord;
        private boolean mLanguageDirectionChanged;
        private float mPrevX;
        private final float mTextViewEdgeSlop;
        private final int[] mTextViewLocation;
        private float mTouchWordDelta;

        public SelectionHandleView(Drawable drawable2, Drawable drawable3, int n, int n2) {
            super(drawable2, drawable3, n);
            this.mInWord = false;
            this.mLanguageDirectionChanged = false;
            this.mTextViewLocation = new int[2];
            this.mHandleType = n2;
            this.mTextViewEdgeSlop = ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledTouchSlop() * 4;
        }

        private float getHorizontal(Layout layout2, int n, boolean bl) {
            int n2 = layout2.getLineForOffset(n);
            boolean bl2 = false;
            int n3 = bl ? n : Math.max(n - 1, 0);
            boolean bl3 = layout2.isRtlCharAt(n3);
            bl = bl2;
            if (layout2.getParagraphDirection(n2) == -1) {
                bl = true;
            }
            float f = bl3 == bl ? layout2.getPrimaryHorizontal(n) : layout2.getSecondaryHorizontal(n);
            return f;
        }

        private boolean isStartHandle() {
            boolean bl = this.mHandleType == 0;
            return bl;
        }

        private void positionAndAdjustForCrossingHandles(int n, boolean bl) {
            int n2;
            block7 : {
                int n3;
                block6 : {
                    n3 = this.isStartHandle() ? Editor.this.mTextView.getSelectionEnd() : Editor.this.mTextView.getSelectionStart();
                    if (this.isStartHandle() && n >= n3) break block6;
                    n2 = n;
                    if (this.isStartHandle()) break block7;
                    n2 = n;
                    if (n > n3) break block7;
                }
                this.mTouchWordDelta = 0.0f;
                Layout layout2 = Editor.this.mTextView.getLayout();
                if (layout2 != null && n != n3) {
                    float f = this.getHorizontal(layout2, n);
                    float f2 = this.getHorizontal(layout2, n3, this.isStartHandle() ^ true);
                    float f3 = this.getHorizontal(layout2, this.mPreviousOffset);
                    if (f3 < f2 && f < f2 || f3 > f2 && f > f2) {
                        n = this.getCurrentCursorOffset();
                        if (!this.isStartHandle()) {
                            n = Math.max(n - 1, 0);
                        }
                        long l = layout2.getRunRange(n);
                        n = this.isStartHandle() ? TextUtils.unpackRangeStartFromLong(l) : TextUtils.unpackRangeEndFromLong(l);
                        this.positionAtCursorOffset(n, false, bl);
                        return;
                    }
                }
                n2 = Editor.this.getNextCursorOffset(n3, this.isStartHandle() ^ true);
            }
            this.positionAtCursorOffset(n2, false, bl);
        }

        private boolean positionNearEdgeOfScrollingView(float f, boolean bl) {
            Editor.this.mTextView.getLocationOnScreen(this.mTextViewLocation);
            boolean bl2 = this.isStartHandle();
            boolean bl3 = true;
            boolean bl4 = true;
            bl = bl == bl2 ? (f > (float)(this.mTextViewLocation[0] + Editor.this.mTextView.getWidth() - Editor.this.mTextView.getPaddingRight()) - this.mTextViewEdgeSlop ? bl4 : false) : (f < (float)(this.mTextViewLocation[0] + Editor.this.mTextView.getPaddingLeft()) + this.mTextViewEdgeSlop ? bl3 : false);
            return bl;
        }

        @Override
        public int getCurrentCursorOffset() {
            int n = this.isStartHandle() ? Editor.this.mTextView.getSelectionStart() : Editor.this.mTextView.getSelectionEnd();
            return n;
        }

        @Override
        public float getHorizontal(Layout layout2, int n) {
            return this.getHorizontal(layout2, n, this.isStartHandle());
        }

        @Override
        protected int getHorizontalGravity(boolean bl) {
            int n = bl == this.isStartHandle() ? 3 : 5;
            return n;
        }

        @Override
        protected int getHotspotX(Drawable drawable2, boolean bl) {
            if (bl == this.isStartHandle()) {
                return drawable2.getIntrinsicWidth() / 4;
            }
            return drawable2.getIntrinsicWidth() * 3 / 4;
        }

        @Override
        protected int getMagnifierHandleTrigger() {
            int n = this.isStartHandle() ? 1 : 2;
            return n;
        }

        @Override
        protected int getOffsetAtCoordinate(Layout layout2, int n, float f) {
            int n2;
            f = Editor.this.mTextView.convertToLocalHorizontalCoordinate(f);
            boolean bl = true;
            int n3 = layout2.getOffsetForHorizontal(n, f, true);
            if (!layout2.isLevelBoundary(n3)) {
                return n3;
            }
            int n4 = layout2.getOffsetForHorizontal(n, f, false);
            int n5 = this.getCurrentCursorOffset();
            int n6 = Math.abs(n3 - n5);
            if (n6 < (n2 = Math.abs(n4 - n5))) {
                return n3;
            }
            if (n6 > n2) {
                return n4;
            }
            if (!this.isStartHandle()) {
                n5 = Math.max(n5 - 1, 0);
            }
            boolean bl2 = layout2.isRtlCharAt(n5);
            if (layout2.getParagraphDirection(n) != -1) {
                bl = false;
            }
            n = bl2 == bl ? n3 : n4;
            return n;
        }

        @Override
        protected boolean isAtRtlRun(Layout layout2, int n) {
            if (!this.isStartHandle()) {
                n = Math.max(n - 1, 0);
            }
            return layout2.isRtlCharAt(n);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean bl;
            block3 : {
                block0 : {
                    block1 : {
                        block2 : {
                            bl = super.onTouchEvent(motionEvent);
                            int n = motionEvent.getActionMasked();
                            if (n == 0) break block0;
                            if (n == 1) break block1;
                            if (n == 2) break block2;
                            if (n == 3) break block1;
                            break block3;
                        }
                        this.updateMagnifier(motionEvent);
                        break block3;
                    }
                    this.dismissMagnifier();
                    break block3;
                }
                this.mTouchWordDelta = 0.0f;
                this.mPrevX = -1.0f;
                this.updateMagnifier(motionEvent);
            }
            return bl;
        }

        @Override
        protected void positionAtCursorOffset(int n, boolean bl, boolean bl2) {
            super.positionAtCursorOffset(n, bl, bl2);
            bl = n != -1 && !Editor.this.getWordIteratorWithText().isBoundary(n);
            this.mInWord = bl;
        }

        @Override
        protected void updatePosition(float f, float f2, boolean bl) {
            int n;
            block30 : {
                int n2;
                int n3;
                boolean bl2;
                int n4;
                int n5;
                int n6;
                Layout layout2;
                int n7;
                block31 : {
                    int n8;
                    block32 : {
                        TextView textView;
                        block29 : {
                            block28 : {
                                layout2 = Editor.this.mTextView.getLayout();
                                if (layout2 == null) {
                                    this.positionAndAdjustForCrossingHandles(Editor.this.mTextView.getOffsetForPosition(f, f2), bl);
                                    return;
                                }
                                if (this.mPreviousLineTouched == -1) {
                                    this.mPreviousLineTouched = Editor.this.mTextView.getLineAtCoordinate(f2);
                                }
                                n3 = this.isStartHandle() ? Editor.this.mTextView.getSelectionEnd() : Editor.this.mTextView.getSelectionStart();
                                n = Editor.this.getCurrentLineAdjustedForSlop(layout2, this.mPreviousLineTouched, f2);
                                n6 = this.getOffsetAtCoordinate(layout2, n, f);
                                if (this.isStartHandle() && n6 >= n3) break block28;
                                n2 = n;
                                n5 = n6;
                                if (this.isStartHandle()) break block29;
                                n2 = n;
                                n5 = n6;
                                if (n6 > n3) break block29;
                            }
                            n2 = layout2.getLineForOffset(n3);
                            n5 = this.getOffsetAtCoordinate(layout2, n2, f);
                        }
                        n = n5;
                        n7 = Editor.this.getWordEnd(n);
                        n6 = Editor.this.getWordStart(n);
                        if (this.mPrevX == -1.0f) {
                            this.mPrevX = f;
                        }
                        n8 = this.getCurrentCursorOffset();
                        boolean bl3 = this.isAtRtlRun(layout2, n8);
                        bl2 = this.isAtRtlRun(layout2, n);
                        boolean bl4 = layout2.isLevelBoundary(n);
                        if (bl4 || bl3 && !bl2) break block30;
                        if (!bl3 && bl2) break block30;
                        if (this.mLanguageDirectionChanged && !bl4) {
                            this.positionAndAdjustForCrossingHandles(n, bl);
                            this.mTouchWordDelta = 0.0f;
                            this.mLanguageDirectionChanged = false;
                            return;
                        }
                        f2 = f - this.mPrevX;
                        n3 = this.isStartHandle() ? (n2 < this.mPreviousLineTouched ? 1 : 0) : (n2 > this.mPreviousLineTouched ? 1 : 0);
                        if (bl2 == this.isStartHandle()) {
                            n4 = f2 > 0.0f ? 1 : 0;
                            n3 |= n4;
                        } else {
                            n4 = f2 < 0.0f ? 1 : 0;
                            n3 |= n4;
                        }
                        if (!Editor.this.mTextView.getHorizontallyScrolling() || !this.positionNearEdgeOfScrollingView(f, bl2)) break block31;
                        if (this.isStartHandle() && Editor.this.mTextView.getScrollX() != 0) break block32;
                        if (this.isStartHandle() || !(textView = Editor.this.mTextView).canScrollHorizontally(n4 = bl2 ? -1 : 1)) break block31;
                    }
                    if (n3 != 0 && (this.isStartHandle() && n < n8 || !this.isStartHandle() && n > n8) || n3 == 0) {
                        this.mTouchWordDelta = 0.0f;
                        n3 = bl2 == this.isStartHandle() ? layout2.getOffsetToRightOf(this.mPreviousOffset) : layout2.getOffsetToLeftOf(this.mPreviousOffset);
                        this.positionAndAdjustForCrossingHandles(n3, bl);
                        return;
                    }
                }
                if (n3 != 0) {
                    n3 = this.isStartHandle() ? n6 : n7;
                    n4 = (!this.mInWord || (this.isStartHandle() ? n2 < this.mPrevLine : n2 > this.mPrevLine)) && bl2 == this.isAtRtlRun(layout2, n3) ? 1 : 0;
                    if (n4 != 0) {
                        n4 = n3;
                        if (layout2.getLineForOffset(n3) != n2) {
                            n3 = this.isStartHandle() ? layout2.getLineStart(n2) : layout2.getLineEnd(n2);
                            n4 = n3;
                        }
                        n3 = this.isStartHandle() ? n7 - (n7 - n4) / 2 : (n4 - n6) / 2 + n6;
                        n3 = this.isStartHandle() && (n <= n3 || n2 < this.mPrevLine) ? n6 : (!(this.isStartHandle() || n < n3 && n2 <= this.mPrevLine) ? n7 : this.mPreviousOffset);
                    } else {
                        n3 = n;
                    }
                    if (this.isStartHandle() && n3 < n5 || !this.isStartHandle() && n3 > n5) {
                        f2 = this.getHorizontal(layout2, n3);
                        this.mTouchWordDelta = Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - f2;
                    } else {
                        this.mTouchWordDelta = 0.0f;
                    }
                    n5 = 1;
                } else {
                    n4 = this.getOffsetAtCoordinate(layout2, n2, f - this.mTouchWordDelta);
                    n3 = this.isStartHandle() ? (n4 <= this.mPreviousOffset && n2 <= this.mPrevLine ? 0 : 1) : (n4 >= this.mPreviousOffset && n2 >= this.mPrevLine ? 0 : 1);
                    if (n3 != 0) {
                        if (n2 != this.mPrevLine) {
                            n3 = this.isStartHandle() ? n6 : n7;
                            if (this.isStartHandle() && n3 < n5 || !this.isStartHandle() && n3 > n5) {
                                f2 = this.getHorizontal(layout2, n3);
                                this.mTouchWordDelta = Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - f2;
                            } else {
                                this.mTouchWordDelta = 0.0f;
                            }
                        } else {
                            n3 = n4;
                        }
                        n5 = 1;
                    } else {
                        if (this.isStartHandle() && n4 < this.mPreviousOffset || !this.isStartHandle() && n4 > this.mPreviousOffset) {
                            this.mTouchWordDelta = Editor.this.mTextView.convertToLocalHorizontalCoordinate(f) - this.getHorizontal(layout2, this.mPreviousOffset);
                        }
                        n5 = 0;
                        n3 = n;
                    }
                }
                if (n5 != 0) {
                    this.mPreviousLineTouched = n2;
                    this.positionAndAdjustForCrossingHandles(n3, bl);
                }
                this.mPrevX = f;
                return;
            }
            this.mLanguageDirectionChanged = true;
            this.mTouchWordDelta = 0.0f;
            this.positionAndAdjustForCrossingHandles(n, bl);
        }

        @Override
        protected void updateSelection(int n) {
            if (this.isStartHandle()) {
                Selection.setSelection((Spannable)Editor.this.mTextView.getText(), n, Editor.this.mTextView.getSelectionEnd());
            } else {
                Selection.setSelection((Spannable)Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionStart(), n);
            }
            this.updateDrawable(false);
            if (Editor.this.mTextActionMode != null) {
                Editor.this.invalidateActionMode();
            }
        }
    }

    class SelectionModifierCursorController
    implements CursorController {
        private static final int DRAG_ACCELERATOR_MODE_CHARACTER = 1;
        private static final int DRAG_ACCELERATOR_MODE_INACTIVE = 0;
        private static final int DRAG_ACCELERATOR_MODE_PARAGRAPH = 3;
        private static final int DRAG_ACCELERATOR_MODE_WORD = 2;
        private float mDownPositionX;
        private float mDownPositionY;
        private int mDragAcceleratorMode = 0;
        private SelectionHandleView mEndHandle;
        private boolean mGestureStayedInTapRegion;
        private boolean mHaventMovedEnoughToStartDrag;
        private int mLineSelectionIsOn = -1;
        private int mMaxTouchOffset;
        private int mMinTouchOffset;
        private SelectionHandleView mStartHandle;
        private int mStartOffset = -1;
        private boolean mSwitchedLines = false;

        SelectionModifierCursorController() {
            this.resetTouchOffsets();
        }

        private void initHandles() {
            Editor editor;
            if (this.mStartHandle == null) {
                editor = Editor.this;
                this.mStartHandle = editor.new SelectionHandleView(editor.mSelectHandleLeft, Editor.this.mSelectHandleRight, 16909332, 0);
            }
            if (this.mEndHandle == null) {
                editor = Editor.this;
                this.mEndHandle = editor.new SelectionHandleView(editor.mSelectHandleRight, Editor.this.mSelectHandleLeft, 16909331, 1);
            }
            this.mStartHandle.show();
            this.mEndHandle.show();
            Editor.this.hideInsertionPointCursorController();
        }

        private void reloadHandleDrawables() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView == null) {
                return;
            }
            selectionHandleView.setDrawables(Editor.this.mSelectHandleLeft, Editor.this.mSelectHandleRight);
            this.mEndHandle.setDrawables(Editor.this.mSelectHandleRight, Editor.this.mSelectHandleLeft);
        }

        private void resetDragAcceleratorState() {
            this.mStartOffset = -1;
            this.mDragAcceleratorMode = 0;
            this.mSwitchedLines = false;
            int n = Editor.this.mTextView.getSelectionStart();
            int n2 = Editor.this.mTextView.getSelectionEnd();
            if (n >= 0 && n2 >= 0) {
                if (n > n2) {
                    Selection.setSelection((Spannable)Editor.this.mTextView.getText(), n2, n);
                }
            } else {
                Selection.removeSelection((Spannable)Editor.this.mTextView.getText());
            }
        }

        private boolean selectCurrentParagraphAndStartDrag() {
            if (Editor.this.mInsertionActionModeRunnable != null) {
                Editor.this.mTextView.removeCallbacks(Editor.this.mInsertionActionModeRunnable);
            }
            Editor.this.stopTextActionMode();
            if (!Editor.this.selectCurrentParagraph()) {
                return false;
            }
            this.enterDrag(3);
            return true;
        }

        private void updateCharacterBasedSelection(MotionEvent motionEvent) {
            int n = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            this.updateSelectionInternal(this.mStartOffset, n, motionEvent.isFromSource(4098));
        }

        private void updateMinAndMaxOffsets(MotionEvent motionEvent) {
            int n = motionEvent.getPointerCount();
            for (int i = 0; i < n; ++i) {
                int n2 = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(i), motionEvent.getY(i));
                if (n2 < this.mMinTouchOffset) {
                    this.mMinTouchOffset = n2;
                }
                if (n2 <= this.mMaxTouchOffset) continue;
                this.mMaxTouchOffset = n2;
            }
        }

        private void updateParagraphBasedSelection(MotionEvent motionEvent) {
            int n = Editor.this.mTextView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
            int n2 = Math.min(n, this.mStartOffset);
            n = Math.max(n, this.mStartOffset);
            long l = Editor.this.getParagraphsRange(n2, n);
            n2 = TextUtils.unpackRangeStartFromLong(l);
            n = TextUtils.unpackRangeEndFromLong(l);
            this.updateSelectionInternal(n2, n, motionEvent.isFromSource(4098));
        }

        private void updateSelection(MotionEvent motionEvent) {
            if (Editor.this.mTextView.getLayout() != null) {
                int n = this.mDragAcceleratorMode;
                if (n != 1) {
                    if (n != 2) {
                        if (n == 3) {
                            this.updateParagraphBasedSelection(motionEvent);
                        }
                    } else {
                        this.updateWordBasedSelection(motionEvent);
                    }
                } else {
                    this.updateCharacterBasedSelection(motionEvent);
                }
            }
        }

        private void updateSelectionInternal(int n, int n2, boolean bl) {
            boolean bl2 = bl && Editor.this.mHapticTextHandleEnabled && (Editor.this.mTextView.getSelectionStart() != n || Editor.this.mTextView.getSelectionEnd() != n2);
            Selection.setSelection((Spannable)Editor.this.mTextView.getText(), n, n2);
            if (bl2) {
                Editor.this.mTextView.performHapticFeedback(9);
            }
        }

        private void updateWordBasedSelection(MotionEvent motionEvent) {
            int n;
            int n2;
            int n3;
            if (this.mHaventMovedEnoughToStartDrag) {
                return;
            }
            boolean bl = motionEvent.isFromSource(8194);
            Object object = ViewConfiguration.get(Editor.this.mTextView.getContext());
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            if (bl) {
                n3 = Editor.this.mTextView.getLineAtCoordinate(f2);
            } else {
                float f3 = f2;
                if (this.mSwitchedLines) {
                    n2 = ((ViewConfiguration)object).getScaledTouchSlop();
                    object = this.mStartHandle;
                    f3 = object != null ? ((HandleView)object).getIdealVerticalOffset() : (float)n2;
                    f3 = f2 - f3;
                }
                object = Editor.this;
                n3 = ((Editor)object).getCurrentLineAdjustedForSlop(((Editor)object).mTextView.getLayout(), this.mLineSelectionIsOn, f3);
                if (!this.mSwitchedLines && n3 != this.mLineSelectionIsOn) {
                    this.mSwitchedLines = true;
                    return;
                }
            }
            n2 = Editor.this.mTextView.getOffsetAtCoordinate(n3, f);
            if (this.mStartOffset < n2) {
                n2 = Editor.this.getWordEnd(n2);
                n = Editor.this.getWordStart(this.mStartOffset);
            } else {
                int n4 = Editor.this.getWordStart(n2);
                int n5 = Editor.this.getWordEnd(this.mStartOffset);
                n2 = n4;
                n = n5;
                if (n5 == n4) {
                    n2 = Editor.this.getNextCursorOffset(n4, false);
                    n = n5;
                }
            }
            this.mLineSelectionIsOn = n3;
            this.updateSelectionInternal(n, n2, motionEvent.isFromSource(4098));
        }

        public void enterDrag(int n) {
            this.show();
            this.mDragAcceleratorMode = n;
            this.mStartOffset = Editor.this.mTextView.getOffsetForPosition(Editor.this.mLastDownPositionX, Editor.this.mLastDownPositionY);
            this.mLineSelectionIsOn = Editor.this.mTextView.getLineAtCoordinate(Editor.this.mLastDownPositionY);
            this.hide();
            Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(true);
            Editor.this.mTextView.cancelLongPress();
        }

        public int getMaxTouchOffset() {
            return this.mMaxTouchOffset;
        }

        public int getMinTouchOffset() {
            return this.mMinTouchOffset;
        }

        @Override
        public void hide() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.hide();
            }
            if ((selectionHandleView = this.mEndHandle) != null) {
                selectionHandleView.hide();
            }
        }

        public void invalidateHandles() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.invalidate();
            }
            if ((selectionHandleView = this.mEndHandle) != null) {
                selectionHandleView.invalidate();
            }
        }

        @Override
        public boolean isActive() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            boolean bl = selectionHandleView != null && selectionHandleView.isShowing();
            return bl;
        }

        @Override
        public boolean isCursorBeingModified() {
            SelectionHandleView selectionHandleView;
            boolean bl = this.isDragAcceleratorActive() || this.isSelectionStartDragged() || (selectionHandleView = this.mEndHandle) != null && selectionHandleView.isDragging();
            return bl;
        }

        public boolean isDragAcceleratorActive() {
            boolean bl = this.mDragAcceleratorMode != 0;
            return bl;
        }

        public boolean isSelectionStartDragged() {
            SelectionHandleView selectionHandleView = this.mStartHandle;
            boolean bl = selectionHandleView != null && selectionHandleView.isDragging();
            return bl;
        }

        @Override
        public void onDetached() {
            Editor.this.mTextView.getViewTreeObserver().removeOnTouchModeChangeListener(this);
            SelectionHandleView selectionHandleView = this.mStartHandle;
            if (selectionHandleView != null) {
                selectionHandleView.onDetached();
            }
            if ((selectionHandleView = this.mEndHandle) != null) {
                selectionHandleView.onDetached();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onTouchEvent(MotionEvent motionEvent) {
            float f = motionEvent.getX();
            float f2 = motionEvent.getY();
            boolean bl = motionEvent.isFromSource(8194);
            int n = motionEvent.getActionMasked();
            int n2 = 0;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 5 && n != 6) {
                            return;
                        }
                        if (!Editor.this.mTextView.getContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch.distinct")) return;
                        this.updateMinAndMaxOffsets(motionEvent);
                        return;
                    }
                    Object object = ViewConfiguration.get(Editor.this.mTextView.getContext());
                    n2 = ((ViewConfiguration)object).getScaledTouchSlop();
                    if (this.mGestureStayedInTapRegion || this.mHaventMovedEnoughToStartDrag) {
                        boolean bl2;
                        float f3 = f - this.mDownPositionX;
                        float f4 = f2 - this.mDownPositionY;
                        f4 = f3 * f3 + f4 * f4;
                        if (this.mGestureStayedInTapRegion) {
                            n = ((ViewConfiguration)object).getScaledDoubleTapTouchSlop();
                            bl2 = f4 <= (float)(n * n);
                            this.mGestureStayedInTapRegion = bl2;
                        }
                        if (this.mHaventMovedEnoughToStartDrag) {
                            bl2 = f4 <= (float)(n2 * n2);
                            this.mHaventMovedEnoughToStartDrag = bl2;
                        }
                    }
                    if (bl && !this.isDragAcceleratorActive()) {
                        n2 = Editor.this.mTextView.getOffsetForPosition(f, f2);
                        if (Editor.this.mTextView.hasSelection() && (!this.mHaventMovedEnoughToStartDrag || this.mStartOffset != n2) && n2 >= Editor.this.mTextView.getSelectionStart() && n2 <= Editor.this.mTextView.getSelectionEnd()) {
                            Editor.this.startDragAndDrop();
                            return;
                        }
                        if (this.mStartOffset != n2) {
                            Editor.this.stopTextActionMode();
                            this.enterDrag(1);
                            Editor.this.mDiscardNextActionUp = true;
                            this.mHaventMovedEnoughToStartDrag = false;
                        }
                    }
                    if ((object = this.mStartHandle) != null && ((HandleView)object).isShowing()) {
                        return;
                    }
                    this.updateSelection(motionEvent);
                    return;
                }
                if (!this.isDragAcceleratorActive()) {
                    return;
                }
                this.updateSelection(motionEvent);
                Editor.this.mTextView.getParent().requestDisallowInterceptTouchEvent(false);
                this.resetDragAcceleratorState();
                if (!Editor.this.mTextView.hasSelection()) return;
                Editor.this.startSelectionActionModeAsync(this.mHaventMovedEnoughToStartDrag);
                return;
            }
            if (Editor.this.extractedTextModeWillBeStarted()) {
                this.hide();
                return;
            }
            this.mMaxTouchOffset = n = Editor.this.mTextView.getOffsetForPosition(f, f2);
            this.mMinTouchOffset = n;
            if (this.mGestureStayedInTapRegion && (Editor.this.mTapState == 2 || Editor.this.mTapState == 3)) {
                float f5 = f - this.mDownPositionX;
                float f6 = f2 - this.mDownPositionY;
                n = ViewConfiguration.get(Editor.this.mTextView.getContext()).getScaledDoubleTapSlop();
                if (f5 * f5 + f6 * f6 < (float)(n * n)) {
                    n2 = 1;
                }
                if (n2 != 0 && (bl || Editor.this.isPositionOnText(f, f2))) {
                    if (Editor.this.mTapState == 2) {
                        Editor.this.selectCurrentWordAndStartDrag();
                    } else if (Editor.this.mTapState == 3) {
                        this.selectCurrentParagraphAndStartDrag();
                    }
                    Editor.this.mDiscardNextActionUp = true;
                }
            }
            this.mDownPositionX = f;
            this.mDownPositionY = f2;
            this.mGestureStayedInTapRegion = true;
            this.mHaventMovedEnoughToStartDrag = true;
        }

        @Override
        public void onTouchModeChanged(boolean bl) {
            if (!bl) {
                this.hide();
            }
        }

        public void resetTouchOffsets() {
            this.mMaxTouchOffset = -1;
            this.mMinTouchOffset = -1;
            this.resetDragAcceleratorState();
        }

        @Override
        public void show() {
            if (Editor.this.mTextView.isInBatchEditMode()) {
                return;
            }
            Editor.this.loadHandleDrawables(false);
            this.initHandles();
        }
    }

    private class SpanController
    implements SpanWatcher {
        private static final int DISPLAY_TIMEOUT_MS = 3000;
        private Runnable mHidePopup;
        private EasyEditPopupWindow mPopupWindow;

        private SpanController() {
        }

        private boolean isNonIntermediateSelectionSpan(Spannable spannable, Object object) {
            boolean bl = (Selection.SELECTION_START == object || Selection.SELECTION_END == object) && (spannable.getSpanFlags(object) & 512) == 0;
            return bl;
        }

        private void sendEasySpanNotification(int n, EasyEditSpan parcelable) {
            block3 : {
                PendingIntent pendingIntent = ((EasyEditSpan)parcelable).getPendingIntent();
                if (pendingIntent == null) break block3;
                try {
                    parcelable = new Intent();
                    ((Intent)parcelable).putExtra("android.text.style.EXTRA_TEXT_CHANGED_TYPE", n);
                    pendingIntent.send(Editor.this.mTextView.getContext(), 0, (Intent)parcelable);
                }
                catch (PendingIntent.CanceledException canceledException) {
                    Log.w("Editor", "PendingIntent for notification cannot be sent", canceledException);
                }
            }
        }

        public void hide() {
            EasyEditPopupWindow easyEditPopupWindow = this.mPopupWindow;
            if (easyEditPopupWindow != null) {
                easyEditPopupWindow.hide();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
            }
        }

        @Override
        public void onSpanAdded(Spannable spannable, Object object, int n, int n2) {
            if (this.isNonIntermediateSelectionSpan(spannable, object)) {
                Editor.this.sendUpdateSelection();
            } else if (object instanceof EasyEditSpan) {
                if (this.mPopupWindow == null) {
                    this.mPopupWindow = new EasyEditPopupWindow();
                    this.mHidePopup = new Runnable(){

                        @Override
                        public void run() {
                            SpanController.this.hide();
                        }
                    };
                }
                if (this.mPopupWindow.mEasyEditSpan != null) {
                    this.mPopupWindow.mEasyEditSpan.setDeleteEnabled(false);
                }
                this.mPopupWindow.setEasyEditSpan((EasyEditSpan)object);
                this.mPopupWindow.setOnDeleteListener(new EasyEditDeleteListener(){

                    @Override
                    public void onDeleteClick(EasyEditSpan easyEditSpan) {
                        Editable editable = (Editable)Editor.this.mTextView.getText();
                        int n = editable.getSpanStart(easyEditSpan);
                        int n2 = editable.getSpanEnd(easyEditSpan);
                        if (n >= 0 && n2 >= 0) {
                            SpanController.this.sendEasySpanNotification(1, easyEditSpan);
                            Editor.this.mTextView.deleteText_internal(n, n2);
                        }
                        editable.removeSpan(easyEditSpan);
                    }
                });
                if (Editor.this.mTextView.getWindowVisibility() != 0) {
                    return;
                }
                if (Editor.this.mTextView.getLayout() == null) {
                    return;
                }
                if (Editor.this.extractedTextModeWillBeStarted()) {
                    return;
                }
                this.mPopupWindow.show();
                Editor.this.mTextView.removeCallbacks(this.mHidePopup);
                Editor.this.mTextView.postDelayed(this.mHidePopup, 3000L);
            }
        }

        @Override
        public void onSpanChanged(Spannable spannable, Object object, int n, int n2, int n3, int n4) {
            if (this.isNonIntermediateSelectionSpan(spannable, object)) {
                Editor.this.sendUpdateSelection();
            } else if (this.mPopupWindow != null && object instanceof EasyEditSpan) {
                object = (EasyEditSpan)object;
                this.sendEasySpanNotification(2, (EasyEditSpan)object);
                spannable.removeSpan(object);
            }
        }

        @Override
        public void onSpanRemoved(Spannable object, Object object2, int n, int n2) {
            if (this.isNonIntermediateSelectionSpan((Spannable)object, object2)) {
                Editor.this.sendUpdateSelection();
            } else {
                object = this.mPopupWindow;
                if (object != null && object2 == ((EasyEditPopupWindow)object).mEasyEditSpan) {
                    this.hide();
                }
            }
        }

    }

    private class SuggestionHelper {
        private final HashMap<SuggestionSpan, Integer> mSpansLengths = new HashMap();
        private final Comparator<SuggestionSpan> mSuggestionSpanComparator = new SuggestionSpanComparator();

        private SuggestionHelper() {
        }

        private SuggestionSpan[] getSortedSuggestionSpans() {
            int n = Editor.this.mTextView.getSelectionStart();
            Spannable spannable = (Spannable)Editor.this.mTextView.getText();
            SuggestionSpan[] arrsuggestionSpan = spannable.getSpans(n, n, SuggestionSpan.class);
            this.mSpansLengths.clear();
            int n2 = arrsuggestionSpan.length;
            for (n = 0; n < n2; ++n) {
                SuggestionSpan suggestionSpan = arrsuggestionSpan[n];
                int n3 = spannable.getSpanStart(suggestionSpan);
                int n4 = spannable.getSpanEnd(suggestionSpan);
                this.mSpansLengths.put(suggestionSpan, n4 - n3);
            }
            Arrays.sort(arrsuggestionSpan, this.mSuggestionSpanComparator);
            this.mSpansLengths.clear();
            return arrsuggestionSpan;
        }

        public int getSuggestionInfo(SuggestionInfo[] arrsuggestionInfo, SuggestionSpanInfo arrstring) {
            Spannable spannable = (Spannable)Editor.this.mTextView.getText();
            SuggestionSpan[] arrsuggestionSpan = this.getSortedSuggestionSpans();
            int n = arrsuggestionSpan.length;
            boolean bl = false;
            if (n == 0) {
                return 0;
            }
            int n2 = arrsuggestionSpan.length;
            int n3 = 0;
            n = 0;
            do {
                String[] arrstring2 = arrstring;
                if (n >= n2) break;
                SuggestionSpan suggestionSpan = arrsuggestionSpan[n];
                int n4 = spannable.getSpanStart(suggestionSpan);
                int n5 = spannable.getSpanEnd(suggestionSpan);
                if (arrstring2 != null && (suggestionSpan.getFlags() & 2) != 0) {
                    arrstring2.mSuggestionSpan = suggestionSpan;
                    arrstring2.mSpanStart = n4;
                    arrstring2.mSpanEnd = n5;
                }
                arrstring2 = suggestionSpan.getSuggestions();
                int n6 = arrstring2.length;
                for (int i = 0; i < n6; ++i) {
                    block7 : {
                        SuggestionInfo suggestionInfo;
                        int n7;
                        String string2 = arrstring2[i];
                        for (n7 = 0; n7 < n3; ++n7) {
                            suggestionInfo = arrsuggestionInfo[n7];
                            if (!suggestionInfo.mText.toString().equals(string2)) continue;
                            int n8 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
                            int n9 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
                            if (n4 != n8 || n5 != n9) {
                                continue;
                            }
                            break block7;
                        }
                        suggestionInfo = arrsuggestionInfo[n3];
                        suggestionInfo.setSpanInfo(suggestionSpan, n4, n5);
                        suggestionInfo.mSuggestionIndex = i;
                        suggestionInfo.mSuggestionStart = 0;
                        suggestionInfo.mSuggestionEnd = string2.length();
                        suggestionInfo.mText.replace(0, suggestionInfo.mText.length(), string2);
                        n3 = n7 = n3 + 1;
                        if (n7 >= arrsuggestionInfo.length) {
                            return n7;
                        }
                    }
                    bl = false;
                }
                ++n;
            } while (true);
            return n3;
        }

        private class SuggestionSpanComparator
        implements Comparator<SuggestionSpan> {
            private SuggestionSpanComparator() {
            }

            @Override
            public int compare(SuggestionSpan suggestionSpan, SuggestionSpan suggestionSpan2) {
                int n;
                int n2 = suggestionSpan.getFlags();
                if (n2 != (n = suggestionSpan2.getFlags())) {
                    boolean bl = false;
                    boolean bl2 = (n2 & 1) != 0;
                    boolean bl3 = (n & 1) != 0;
                    n2 = (n2 & 2) != 0 ? 1 : 0;
                    if ((n & 2) != 0) {
                        bl = true;
                    }
                    if (bl2 && n2 == 0) {
                        return -1;
                    }
                    if (bl3 && !bl) {
                        return 1;
                    }
                    if (n2 != 0) {
                        return -1;
                    }
                    if (bl) {
                        return 1;
                    }
                }
                return (Integer)SuggestionHelper.this.mSpansLengths.get(suggestionSpan) - (Integer)SuggestionHelper.this.mSpansLengths.get(suggestionSpan2);
            }
        }

    }

    private static final class SuggestionInfo {
        int mSuggestionEnd;
        int mSuggestionIndex;
        final SuggestionSpanInfo mSuggestionSpanInfo = new SuggestionSpanInfo();
        int mSuggestionStart;
        final SpannableStringBuilder mText = new SpannableStringBuilder();

        private SuggestionInfo() {
        }

        void clear() {
            this.mSuggestionSpanInfo.clear();
            this.mText.clear();
        }

        void setSpanInfo(SuggestionSpan suggestionSpan, int n, int n2) {
            SuggestionSpanInfo suggestionSpanInfo = this.mSuggestionSpanInfo;
            suggestionSpanInfo.mSuggestionSpan = suggestionSpan;
            suggestionSpanInfo.mSpanStart = n;
            suggestionSpanInfo.mSpanEnd = n2;
        }
    }

    private static final class SuggestionSpanInfo {
        int mSpanEnd;
        int mSpanStart;
        SuggestionSpan mSuggestionSpan;

        private SuggestionSpanInfo() {
        }

        void clear() {
            this.mSuggestionSpan = null;
        }
    }

    private final class SuggestionsPopupWindow
    extends PinnedPopupWindow
    implements AdapterView.OnItemClickListener {
        private static final int MAX_NUMBER_SUGGESTIONS = 5;
        private static final String USER_DICTIONARY_EXTRA_LOCALE = "locale";
        private static final String USER_DICTIONARY_EXTRA_WORD = "word";
        private TextView mAddToDictionaryButton;
        private int mContainerMarginTop;
        private int mContainerMarginWidth;
        private LinearLayout mContainerView;
        private Context mContext;
        private boolean mCursorWasVisibleBeforeSuggestions;
        private TextView mDeleteButton;
        private TextAppearanceSpan mHighlightSpan;
        private boolean mIsShowingUp = false;
        private final SuggestionSpanInfo mMisspelledSpanInfo = new SuggestionSpanInfo();
        private int mNumberOfSuggestions;
        private SuggestionInfo[] mSuggestionInfos;
        private ListView mSuggestionListView;
        private SuggestionAdapter mSuggestionsAdapter;

        public SuggestionsPopupWindow() {
            this.mCursorWasVisibleBeforeSuggestions = Editor.this.mCursorVisible;
        }

        private Context applyDefaultTheme(Context context) {
            TypedArray typedArray = context.obtainStyledAttributes(new int[]{16844176});
            int n = typedArray.getBoolean(0, true) ? 16974410 : 16974411;
            typedArray.recycle();
            return new ContextThemeWrapper(context, n);
        }

        private void hideWithCleanUp() {
            SuggestionInfo[] arrsuggestionInfo = this.mSuggestionInfos;
            int n = arrsuggestionInfo.length;
            for (int i = 0; i < n; ++i) {
                arrsuggestionInfo[i].clear();
            }
            this.mMisspelledSpanInfo.clear();
            this.hide();
        }

        private void highlightTextDifferences(SuggestionInfo suggestionInfo, int n, int n2) {
            CharSequence charSequence = (Spannable)Editor.this.mTextView.getText();
            int n3 = suggestionInfo.mSuggestionSpanInfo.mSpanStart;
            int n4 = suggestionInfo.mSuggestionSpanInfo.mSpanEnd;
            suggestionInfo.mSuggestionStart = n3 - n;
            suggestionInfo.mSuggestionEnd = suggestionInfo.mSuggestionStart + suggestionInfo.mText.length();
            suggestionInfo.mText.setSpan(this.mHighlightSpan, 0, suggestionInfo.mText.length(), 33);
            charSequence = ((Object)charSequence).toString();
            suggestionInfo.mText.insert(0, ((String)charSequence).substring(n, n3));
            suggestionInfo.mText.append(((String)charSequence).substring(n4, n2));
        }

        private boolean updateSuggestions() {
            int n;
            Spannable spannable = (Spannable)Editor.this.mTextView.getText();
            this.mNumberOfSuggestions = Editor.this.mSuggestionHelper.getSuggestionInfo(this.mSuggestionInfos, this.mMisspelledSpanInfo);
            if (this.mNumberOfSuggestions == 0 && this.mMisspelledSpanInfo.mSuggestionSpan == null) {
                return false;
            }
            int n2 = Editor.this.mTextView.getText().length();
            int n3 = 0;
            for (n = 0; n < this.mNumberOfSuggestions; ++n) {
                SuggestionSpanInfo suggestionSpanInfo = this.mSuggestionInfos[n].mSuggestionSpanInfo;
                n2 = Math.min(n2, suggestionSpanInfo.mSpanStart);
                n3 = Math.max(n3, suggestionSpanInfo.mSpanEnd);
            }
            int n4 = n2;
            n = n3;
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                n4 = Math.min(n2, this.mMisspelledSpanInfo.mSpanStart);
                n = Math.max(n3, this.mMisspelledSpanInfo.mSpanEnd);
            }
            for (n3 = 0; n3 < this.mNumberOfSuggestions; ++n3) {
                this.highlightTextDifferences(this.mSuggestionInfos[n3], n4, n);
            }
            n3 = n2 = 8;
            if (this.mMisspelledSpanInfo.mSuggestionSpan != null) {
                n3 = n2;
                if (this.mMisspelledSpanInfo.mSpanStart >= 0) {
                    n3 = n2;
                    if (this.mMisspelledSpanInfo.mSpanEnd > this.mMisspelledSpanInfo.mSpanStart) {
                        n3 = 0;
                    }
                }
            }
            this.mAddToDictionaryButton.setVisibility(n3);
            if (Editor.this.mSuggestionRangeSpan == null) {
                Editor.this.mSuggestionRangeSpan = new SuggestionRangeSpan();
            }
            if ((n3 = this.mNumberOfSuggestions != 0 ? this.mSuggestionInfos[0].mSuggestionSpanInfo.mSuggestionSpan.getUnderlineColor() : this.mMisspelledSpanInfo.mSuggestionSpan.getUnderlineColor()) == 0) {
                Editor.this.mSuggestionRangeSpan.setBackgroundColor(Editor.access$300((Editor)Editor.this).mHighlightColor);
            } else {
                n2 = (int)((float)Color.alpha(n3) * 0.4f);
                Editor.this.mSuggestionRangeSpan.setBackgroundColor((16777215 & n3) + (n2 << 24));
            }
            spannable.setSpan(Editor.this.mSuggestionRangeSpan, n4, n, 33);
            this.mSuggestionsAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        protected int clipVertically(int n) {
            int n2 = this.mContentView.getMeasuredHeight();
            return Math.min(n, Editor.access$300((Editor)Editor.this).getResources().getDisplayMetrics().heightPixels - n2);
        }

        @Override
        protected void createPopupWindow() {
            this.mPopupWindow = new CustomPopupWindow();
            this.mPopupWindow.setInputMethodMode(2);
            this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.setClippingEnabled(false);
        }

        @Override
        protected int getTextOffset() {
            return (Editor.this.mTextView.getSelectionStart() + Editor.this.mTextView.getSelectionStart()) / 2;
        }

        @Override
        protected int getVerticalLocalPosition(int n) {
            return Editor.this.mTextView.getLayout().getLineBottomWithoutSpacing(n) - this.mContainerMarginTop;
        }

        @Override
        protected void initContentView() {
            this.mContentView = (ViewGroup)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(Editor.access$300((Editor)Editor.this).mTextEditSuggestionContainerLayout, null);
            this.mContainerView = (LinearLayout)this.mContentView.findViewById(16909416);
            SuggestionInfo[] arrsuggestionInfo = (SuggestionInfo[])this.mContainerView.getLayoutParams();
            this.mContainerMarginWidth = arrsuggestionInfo.leftMargin + arrsuggestionInfo.rightMargin;
            this.mContainerMarginTop = arrsuggestionInfo.topMargin;
            this.mClippingLimitLeft = arrsuggestionInfo.leftMargin;
            this.mClippingLimitRight = arrsuggestionInfo.rightMargin;
            this.mSuggestionListView = (ListView)this.mContentView.findViewById(16909415);
            this.mSuggestionsAdapter = new SuggestionAdapter();
            this.mSuggestionListView.setAdapter(this.mSuggestionsAdapter);
            this.mSuggestionListView.setOnItemClickListener(this);
            this.mSuggestionInfos = new SuggestionInfo[5];
            for (int i = 0; i < (arrsuggestionInfo = this.mSuggestionInfos).length; ++i) {
                arrsuggestionInfo[i] = new SuggestionInfo();
            }
            this.mAddToDictionaryButton = (TextView)this.mContentView.findViewById(16908698);
            this.mAddToDictionaryButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View object) {
                    Parcelable parcelable = Editor.this.findEquivalentSuggestionSpan(SuggestionsPopupWindow.this.mMisspelledSpanInfo);
                    if (parcelable == null) {
                        return;
                    }
                    object = (Editable)Editor.this.mTextView.getText();
                    int n = object.getSpanStart(parcelable);
                    int n2 = object.getSpanEnd(parcelable);
                    if (n >= 0 && n2 > n) {
                        String string2 = TextUtils.substring((CharSequence)object, n, n2);
                        parcelable = new Intent("com.android.settings.USER_DICTIONARY_INSERT");
                        ((Intent)parcelable).putExtra(SuggestionsPopupWindow.USER_DICTIONARY_EXTRA_WORD, string2);
                        ((Intent)parcelable).putExtra(SuggestionsPopupWindow.USER_DICTIONARY_EXTRA_LOCALE, Editor.this.mTextView.getTextServicesLocale().toString());
                        ((Intent)parcelable).setFlags(((Intent)parcelable).getFlags() | 268435456);
                        Editor.this.mTextView.startActivityAsTextOperationUserIfNecessary((Intent)parcelable);
                        object.removeSpan(SuggestionsPopupWindow.access$3200((SuggestionsPopupWindow)SuggestionsPopupWindow.this).mSuggestionSpan);
                        Selection.setSelection((Spannable)object, n2);
                        Editor.this.updateSpellCheckSpans(n, n2, false);
                        SuggestionsPopupWindow.this.hideWithCleanUp();
                        return;
                    }
                }
            });
            this.mDeleteButton = (TextView)this.mContentView.findViewById(16908875);
            this.mDeleteButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View object) {
                    block2 : {
                        int n;
                        int n2;
                        block3 : {
                            int n3;
                            block4 : {
                                object = (Editable)Editor.this.mTextView.getText();
                                n = object.getSpanStart(Editor.this.mSuggestionRangeSpan);
                                n3 = object.getSpanEnd(Editor.this.mSuggestionRangeSpan);
                                if (n < 0 || n3 <= n) break block2;
                                n2 = n3;
                                if (n3 >= object.length()) break block3;
                                n2 = n3;
                                if (!Character.isSpaceChar(object.charAt(n3))) break block3;
                                if (n == 0) break block4;
                                n2 = n3;
                                if (!Character.isSpaceChar(object.charAt(n - 1))) break block3;
                            }
                            n2 = n3 + 1;
                        }
                        Editor.this.mTextView.deleteText_internal(n, n2);
                    }
                    SuggestionsPopupWindow.this.hideWithCleanUp();
                }
            });
        }

        public boolean isShowingUp() {
            return this.mIsShowingUp;
        }

        @Override
        protected void measureContent() {
            int n;
            Object object = Editor.this.mTextView.getResources().getDisplayMetrics();
            int n2 = View.MeasureSpec.makeMeasureSpec(((DisplayMetrics)object).widthPixels, Integer.MIN_VALUE);
            int n3 = View.MeasureSpec.makeMeasureSpec(((DisplayMetrics)object).heightPixels, Integer.MIN_VALUE);
            int n4 = 0;
            object = null;
            for (n = 0; n < this.mNumberOfSuggestions; ++n) {
                object = this.mSuggestionsAdapter.getView(n, (View)object, this.mContentView);
                object.getLayoutParams().width = -2;
                ((View)object).measure(n2, n3);
                n4 = Math.max(n4, ((View)object).getMeasuredWidth());
            }
            n = n4;
            if (this.mAddToDictionaryButton.getVisibility() != 8) {
                this.mAddToDictionaryButton.measure(n2, n3);
                n = Math.max(n4, this.mAddToDictionaryButton.getMeasuredWidth());
            }
            this.mDeleteButton.measure(n2, n3);
            n = Math.max(n, this.mDeleteButton.getMeasuredWidth()) + (this.mContainerView.getPaddingLeft() + this.mContainerView.getPaddingRight() + this.mContainerMarginWidth);
            this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), n3);
            object = this.mPopupWindow.getBackground();
            n4 = n;
            if (object != null) {
                if (Editor.this.mTempRect == null) {
                    Editor.this.mTempRect = new Rect();
                }
                ((Drawable)object).getPadding(Editor.this.mTempRect);
                n4 = n + (Editor.access$3900((Editor)Editor.this).left + Editor.access$3900((Editor)Editor.this).right);
            }
            this.mPopupWindow.setWidth(n4);
        }

        @Override
        public void onItemClick(AdapterView<?> object, View view, int n, long l) {
            object = this.mSuggestionInfos[n];
            Editor.this.replaceWithSuggestion((SuggestionInfo)object);
            this.hideWithCleanUp();
        }

        public void onParentLostFocus() {
            this.mIsShowingUp = false;
        }

        @Override
        protected void setUp() {
            this.mContext = this.applyDefaultTheme(Editor.this.mTextView.getContext());
            this.mHighlightSpan = new TextAppearanceSpan(this.mContext, Editor.access$300((Editor)Editor.this).mTextEditSuggestionHighlightStyle);
        }

        @Override
        public void show() {
            if (!(Editor.this.mTextView.getText() instanceof Editable)) {
                return;
            }
            if (Editor.this.extractedTextModeWillBeStarted()) {
                return;
            }
            boolean bl = this.updateSuggestions();
            int n = 0;
            if (bl) {
                this.mCursorWasVisibleBeforeSuggestions = Editor.this.mCursorVisible;
                Editor.this.mTextView.setCursorVisible(false);
                this.mIsShowingUp = true;
                super.show();
            }
            ListView listView = this.mSuggestionListView;
            if (this.mNumberOfSuggestions == 0) {
                n = 8;
            }
            listView.setVisibility(n);
        }

        private class CustomPopupWindow
        extends PopupWindow {
            private CustomPopupWindow() {
            }

            @Override
            public void dismiss() {
                if (!this.isShowing()) {
                    return;
                }
                super.dismiss();
                Editor.this.getPositionListener().removeSubscriber(SuggestionsPopupWindow.this);
                ((Spannable)Editor.this.mTextView.getText()).removeSpan(Editor.this.mSuggestionRangeSpan);
                Editor.this.mTextView.setCursorVisible(SuggestionsPopupWindow.this.mCursorWasVisibleBeforeSuggestions);
                if (Editor.this.hasInsertionController() && !Editor.this.extractedTextModeWillBeStarted()) {
                    Editor.this.getInsertionController().show();
                }
            }
        }

        private class SuggestionAdapter
        extends BaseAdapter {
            private LayoutInflater mInflater;

            private SuggestionAdapter() {
                this.mInflater = (LayoutInflater)SuggestionsPopupWindow.this.mContext.getSystemService("layout_inflater");
            }

            @Override
            public int getCount() {
                return SuggestionsPopupWindow.this.mNumberOfSuggestions;
            }

            @Override
            public Object getItem(int n) {
                return SuggestionsPopupWindow.this.mSuggestionInfos[n];
            }

            @Override
            public long getItemId(int n) {
                return n;
            }

            @Override
            public View getView(int n, View view, ViewGroup viewGroup) {
                TextView textView = (TextView)view;
                view = textView;
                if (textView == null) {
                    view = (TextView)this.mInflater.inflate(Editor.access$300((Editor)Editor.this).mTextEditSuggestionItemLayout, viewGroup, false);
                }
                ((TextView)view).setText(SuggestionsPopupWindow.access$3800((SuggestionsPopupWindow)SuggestionsPopupWindow.this)[n].mText);
                return view;
            }
        }

    }

    static @interface TextActionMode {
        public static final int INSERTION = 1;
        public static final int SELECTION = 0;
        public static final int TEXT_LINK = 2;
    }

    private class TextActionModeCallback
    extends ActionMode.Callback2 {
        private final Map<MenuItem, View.OnClickListener> mAssistClickHandlers = new HashMap<MenuItem, View.OnClickListener>();
        private final int mHandleHeight;
        private final boolean mHasSelection;
        private final RectF mSelectionBounds = new RectF();
        private final Path mSelectionPath = new Path();

        TextActionModeCallback(int n) {
            boolean bl = n == 0 || Editor.this.mTextIsSelectable && n == 2;
            this.mHasSelection = bl;
            if (this.mHasSelection) {
                SelectionModifierCursorController selectionModifierCursorController = Editor.this.getSelectionController();
                if (selectionModifierCursorController.mStartHandle == null) {
                    Editor.this.loadHandleDrawables(false);
                    selectionModifierCursorController.initHandles();
                    selectionModifierCursorController.hide();
                }
                this.mHandleHeight = Math.max(Editor.this.mSelectHandleLeft.getMinimumHeight(), Editor.this.mSelectHandleRight.getMinimumHeight());
            } else {
                InsertionPointCursorController insertionPointCursorController = Editor.this.getInsertionController();
                if (insertionPointCursorController != null) {
                    insertionPointCursorController.getHandle();
                    this.mHandleHeight = Editor.this.mSelectHandleCenter.getMinimumHeight();
                } else {
                    this.mHandleHeight = 0;
                }
            }
        }

        private MenuItem addAssistMenuItem(Menu object, RemoteAction remoteAction, int n, int n2, int n3) {
            object = object.add(16908353, n, n2, remoteAction.getTitle()).setContentDescription(remoteAction.getContentDescription());
            if (remoteAction.shouldShowIcon()) {
                object.setIcon(remoteAction.getIcon().loadDrawable(Editor.this.mTextView.getContext()));
            }
            object.setShowAsAction(n3);
            this.mAssistClickHandlers.put((MenuItem)object, TextClassification.createIntentOnClickListener(remoteAction.getActionIntent()));
            return object;
        }

        private void clearAssistMenuItems(Menu menu2) {
            int n = 0;
            while (n < menu2.size()) {
                MenuItem menuItem = menu2.getItem(n);
                if (menuItem.getGroupId() == 16908353) {
                    menu2.removeItem(menuItem.getItemId());
                    continue;
                }
                ++n;
            }
        }

        private int createAssistMenuItemPendingIntentRequestCode() {
            int n = Editor.this.mTextView.hasSelection() ? Editor.this.mTextView.getText().subSequence(Editor.this.mTextView.getSelectionStart(), Editor.this.mTextView.getSelectionEnd()).hashCode() : 0;
            return n;
        }

        private ActionMode.Callback getCustomCallback() {
            ActionMode.Callback callback = this.mHasSelection ? Editor.this.mCustomSelectionActionModeCallback : Editor.this.mCustomInsertionActionModeCallback;
            return callback;
        }

        private boolean hasLegacyAssistItem(TextClassification textClassification) {
            boolean bl = !(textClassification.getIcon() == null && TextUtils.isEmpty(textClassification.getLabel()) || textClassification.getIntent() == null && textClassification.getOnClickListener() == null);
            return bl;
        }

        private boolean onAssistMenuItemClicked(MenuItem object) {
            boolean bl = object.getGroupId() == 16908353;
            Preconditions.checkArgument(bl);
            Object object2 = Editor.this.getSelectionActionModeHelper().getTextClassification();
            if (this.shouldEnableAssistMenuItems() && object2 != null) {
                View.OnClickListener onClickListener = this.mAssistClickHandlers.get(object);
                object2 = onClickListener;
                if (onClickListener == null) {
                    object = object.getIntent();
                    object2 = onClickListener;
                    if (object != null) {
                        object2 = TextClassification.createIntentOnClickListener(TextClassification.createPendingIntent(Editor.this.mTextView.getContext(), (Intent)object, this.createAssistMenuItemPendingIntentRequestCode()));
                    }
                }
                if (object2 != null) {
                    object2.onClick(Editor.this.mTextView);
                    Editor.this.stopTextActionMode();
                }
                return true;
            }
            return true;
        }

        private void populateMenuWithItems(Menu menu2) {
            String string2;
            if (Editor.this.mTextView.canCut()) {
                menu2.add(0, 16908320, 4, 17039363).setAlphabeticShortcut('x').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canCopy()) {
                menu2.add(0, 16908321, 5, 17039361).setAlphabeticShortcut('c').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canPaste()) {
                menu2.add(0, 16908322, 6, 17039371).setAlphabeticShortcut('v').setShowAsAction(2);
            }
            if (Editor.this.mTextView.canShare()) {
                menu2.add(0, 16908341, 7, 17041006).setShowAsAction(1);
            }
            if (Editor.this.mTextView.canRequestAutofill() && ((string2 = Editor.this.mTextView.getSelectedText()) == null || string2.isEmpty())) {
                menu2.add(0, 16908355, 10, 17039386).setShowAsAction(0);
            }
            if (Editor.this.mTextView.canPasteAsPlainText()) {
                menu2.add(0, 16908337, 11, 17039385).setShowAsAction(1);
            }
            this.updateSelectAllItem(menu2);
            this.updateReplaceItem(menu2);
            this.updateAssistMenuItems(menu2);
        }

        private boolean shouldEnableAssistMenuItems() {
            boolean bl = Editor.this.mTextView.isDeviceProvisioned() && TextClassificationManager.getSettings(Editor.this.mTextView.getContext()).isSmartTextShareEnabled();
            return bl;
        }

        private void updateAssistMenuItems(Menu menu2) {
            this.clearAssistMenuItems(menu2);
            if (!this.shouldEnableAssistMenuItems()) {
                return;
            }
            TextClassification textClassification = Editor.this.getSelectionActionModeHelper().getTextClassification();
            if (textClassification == null) {
                return;
            }
            if (!textClassification.getActions().isEmpty()) {
                this.addAssistMenuItem(menu2, textClassification.getActions().get(0), 16908353, 0, 2).setIntent(textClassification.getIntent());
            } else if (this.hasLegacyAssistItem(textClassification)) {
                MenuItem menuItem = menu2.add(16908353, 16908353, 0, textClassification.getLabel()).setIcon(textClassification.getIcon()).setIntent(textClassification.getIntent());
                menuItem.setShowAsAction(2);
                this.mAssistClickHandlers.put(menuItem, TextClassification.createIntentOnClickListener(TextClassification.createPendingIntent(Editor.this.mTextView.getContext(), textClassification.getIntent(), this.createAssistMenuItemPendingIntentRequestCode())));
            }
            int n = textClassification.getActions().size();
            for (int i = 1; i < n; ++i) {
                this.addAssistMenuItem(menu2, textClassification.getActions().get(i), 0, i + 50 - 1, 0);
            }
        }

        private void updateReplaceItem(Menu menu2) {
            boolean bl = Editor.this.mTextView.isSuggestionsEnabled() && Editor.this.shouldOfferToShowSuggestions();
            boolean bl2 = menu2.findItem(16908340) != null;
            if (bl && !bl2) {
                menu2.add(0, 16908340, 9, 17040921).setShowAsAction(1);
            } else if (!bl && bl2) {
                menu2.removeItem(16908340);
            }
        }

        private void updateSelectAllItem(Menu menu2) {
            boolean bl = Editor.this.mTextView.canSelectAllText();
            boolean bl2 = menu2.findItem(16908319) != null;
            if (bl && !bl2) {
                menu2.add(0, 16908319, 8, 17039373).setShowAsAction(1);
            } else if (!bl && bl2) {
                menu2.removeItem(16908319);
            }
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Editor.this.getSelectionActionModeHelper().onSelectionAction(menuItem.getItemId(), menuItem.getTitle().toString());
            if (Editor.this.mProcessTextIntentActionsHandler.performMenuItemAction(menuItem)) {
                return true;
            }
            ActionMode.Callback callback = this.getCustomCallback();
            if (callback != null && callback.onActionItemClicked(actionMode, menuItem)) {
                return true;
            }
            if (menuItem.getGroupId() == 16908353 && this.onAssistMenuItemClicked(menuItem)) {
                return true;
            }
            return Editor.this.mTextView.onTextContextMenuItem(menuItem.getItemId());
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            this.mAssistClickHandlers.clear();
            actionMode.setTitle(null);
            actionMode.setSubtitle(null);
            actionMode.setTitleOptionalHint(true);
            this.populateMenuWithItems(menu2);
            ActionMode.Callback callback = this.getCustomCallback();
            if (callback != null && !callback.onCreateActionMode(actionMode, menu2)) {
                Selection.setSelection((Spannable)Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionEnd());
                return false;
            }
            if (Editor.this.mTextView.canProcessText()) {
                Editor.this.mProcessTextIntentActionsHandler.onInitializeMenu(menu2);
            }
            if (this.mHasSelection && !Editor.this.mTextView.hasTransientState()) {
                Editor.this.mTextView.setHasTransientState(true);
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Editor.this.getSelectionActionModeHelper().onDestroyActionMode();
            Editor.this.mTextActionMode = null;
            ActionMode.Callback callback = this.getCustomCallback();
            if (callback != null) {
                callback.onDestroyActionMode(actionMode);
            }
            if (!Editor.this.mPreserveSelection) {
                Selection.setSelection((Spannable)Editor.this.mTextView.getText(), Editor.this.mTextView.getSelectionEnd());
            }
            if (Editor.this.mSelectionModifierCursorController != null) {
                Editor.this.mSelectionModifierCursorController.hide();
            }
            this.mAssistClickHandlers.clear();
            Editor.this.mRequestingLinkActionMode = false;
        }

        @Override
        public void onGetContentRect(ActionMode object, View object2, Rect rect) {
            if (object2.equals(Editor.this.mTextView) && Editor.this.mTextView.getLayout() != null) {
                int n;
                if (Editor.this.mTextView.getSelectionStart() != Editor.this.mTextView.getSelectionEnd()) {
                    this.mSelectionPath.reset();
                    Editor.this.mTextView.getLayout().getSelectionPath(Editor.this.mTextView.getSelectionStart(), Editor.this.mTextView.getSelectionEnd(), this.mSelectionPath);
                    this.mSelectionPath.computeBounds(this.mSelectionBounds, true);
                    object = this.mSelectionBounds;
                    ((RectF)object).bottom += (float)this.mHandleHeight;
                } else {
                    object2 = Editor.this.mTextView.getLayout();
                    n = ((Layout)object2).getLineForOffset(Editor.this.mTextView.getSelectionStart());
                    object = Editor.this;
                    float f = ((Editor)object).clampHorizontalPosition(null, ((Layout)object2).getPrimaryHorizontal(((Editor)object).mTextView.getSelectionStart()));
                    this.mSelectionBounds.set(f, ((Layout)object2).getLineTop(n), f, ((Layout)object2).getLineBottom(n) + this.mHandleHeight);
                }
                n = Editor.this.mTextView.viewportToContentHorizontalOffset();
                int n2 = Editor.this.mTextView.viewportToContentVerticalOffset();
                rect.set((int)Math.floor(this.mSelectionBounds.left + (float)n), (int)Math.floor(this.mSelectionBounds.top + (float)n2), (int)Math.ceil(this.mSelectionBounds.right + (float)n), (int)Math.ceil(this.mSelectionBounds.bottom + (float)n2));
                return;
            }
            super.onGetContentRect((ActionMode)object, (View)object2, rect);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            this.updateSelectAllItem(menu2);
            this.updateReplaceItem(menu2);
            this.updateAssistMenuItems(menu2);
            ActionMode.Callback callback = this.getCustomCallback();
            if (callback != null) {
                return callback.onPrepareActionMode(actionMode, menu2);
            }
            return true;
        }
    }

    private static class TextRenderNode {
        boolean isDirty;
        boolean needsToBeShifted;
        RenderNode renderNode;

        public TextRenderNode(String string2) {
            this.renderNode = RenderNode.create(string2, null);
            this.isDirty = true;
            this.needsToBeShifted = true;
        }

        boolean needsRecord() {
            boolean bl = this.isDirty || !this.renderNode.hasDisplayList();
            return bl;
        }
    }

    private static interface TextViewPositionListener {
        public void updatePosition(int var1, int var2, boolean var3, boolean var4);
    }

    public static class UndoInputFilter
    implements InputFilter {
        private static final int MERGE_EDIT_MODE_FORCE_MERGE = 0;
        private static final int MERGE_EDIT_MODE_NEVER_MERGE = 1;
        private static final int MERGE_EDIT_MODE_NORMAL = 2;
        private final Editor mEditor;
        private boolean mExpanding;
        private boolean mHasComposition;
        private boolean mIsUserEdit;
        private boolean mPreviousOperationWasInSameBatchEdit;

        public UndoInputFilter(Editor editor) {
            this.mEditor = editor;
        }

        private boolean canUndoEdit(CharSequence charSequence, int n, int n2, Spanned spanned, int n3, int n4) {
            if (!this.mEditor.mAllowUndo) {
                return false;
            }
            if (this.mEditor.mUndoManager.isInUndo()) {
                return false;
            }
            if (Editor.isValidRange(charSequence, n, n2) && Editor.isValidRange(spanned, n3, n4)) {
                return n != n2 || n3 != n4;
            }
            return false;
        }

        private EditOperation getLastEdit() {
            return this.mEditor.mUndoManager.getLastOperation(EditOperation.class, this.mEditor.mUndoOwner, 1);
        }

        private void handleEdit(CharSequence object, int n, int n2, Spanned charSequence, int n3, int n4, boolean bl) {
            int n5 = !this.isInTextWatcher() && !this.mPreviousOperationWasInSameBatchEdit ? (bl ? 1 : 2) : 0;
            object = TextUtils.substring((CharSequence)object, n, n2);
            charSequence = TextUtils.substring(charSequence, n3, n4);
            object = new EditOperation(this.mEditor, (String)charSequence, n3, (String)object, this.mHasComposition);
            if (this.mHasComposition && TextUtils.equals(((EditOperation)object).mNewText, ((EditOperation)object).mOldText)) {
                return;
            }
            this.recordEdit((EditOperation)object, n5);
        }

        private static boolean isComposition(CharSequence charSequence) {
            boolean bl = charSequence instanceof Spannable;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (EditableInputConnection.getComposingSpanStart((Spannable)(charSequence = (Spannable)charSequence)) < EditableInputConnection.getComposingSpanEnd((Spannable)charSequence)) {
                bl2 = true;
            }
            return bl2;
        }

        private boolean isInTextWatcher() {
            CharSequence charSequence = this.mEditor.mTextView.getText();
            boolean bl = charSequence instanceof SpannableStringBuilder && ((SpannableStringBuilder)charSequence).getTextWatcherDepth() > 0;
            return bl;
        }

        private void recordEdit(EditOperation editOperation, int n) {
            UndoManager undoManager = this.mEditor.mUndoManager;
            undoManager.beginUpdate("Edit text");
            EditOperation editOperation2 = this.getLastEdit();
            if (editOperation2 == null) {
                undoManager.addOperation(editOperation, 0);
            } else if (n == 0) {
                editOperation2.forceMergeWith(editOperation);
            } else if (!this.mIsUserEdit) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            } else if (n != 2 || !editOperation2.mergeWith(editOperation)) {
                undoManager.commitState(this.mEditor.mUndoOwner);
                undoManager.addOperation(editOperation, 0);
            }
            this.mPreviousOperationWasInSameBatchEdit = this.mIsUserEdit;
            undoManager.endUpdate();
        }

        public void beginBatchEdit() {
            this.mIsUserEdit = true;
        }

        public void endBatchEdit() {
            this.mIsUserEdit = false;
            this.mPreviousOperationWasInSameBatchEdit = false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public CharSequence filter(CharSequence var1_1, int var2_2, int var3_3, Spanned var4_4, int var5_5, int var6_6) {
            if (!this.canUndoEdit(var1_1, var2_2, var3_3, var4_4, var5_5, var6_6)) {
                return null;
            }
            var7_7 = this.mHasComposition;
            this.mHasComposition = UndoInputFilter.isComposition(var1_1);
            var8_8 = this.mExpanding;
            if (var3_3 - var2_2 == var6_6 - var5_5) ** GOTO lbl-1000
            var9_9 = var3_3 - var2_2 > var6_6 - var5_5;
            this.mExpanding = var9_9;
            if (var7_7 && this.mExpanding != var8_8) {
                var9_9 = true;
            } else lbl-1000: // 2 sources:
            {
                var9_9 = false;
            }
            this.handleEdit(var1_1, var2_2, var3_3, var4_4, var5_5, var6_6, var9_9);
            return null;
        }

        void freezeLastEdit() {
            this.mEditor.mUndoManager.beginUpdate("Edit text");
            EditOperation editOperation = this.getLastEdit();
            if (editOperation != null) {
                editOperation.mFrozen = true;
            }
            this.mEditor.mUndoManager.endUpdate();
        }

        public void restoreInstanceState(Parcel parcel) {
            int n = parcel.readInt();
            boolean bl = true;
            boolean bl2 = n != 0;
            this.mIsUserEdit = bl2;
            bl2 = parcel.readInt() != 0;
            this.mHasComposition = bl2;
            bl2 = parcel.readInt() != 0;
            this.mExpanding = bl2;
            bl2 = parcel.readInt() != 0 ? bl : false;
            this.mPreviousOperationWasInSameBatchEdit = bl2;
        }

        public void saveInstanceState(Parcel parcel) {
            parcel.writeInt((int)this.mIsUserEdit);
            parcel.writeInt((int)this.mHasComposition);
            parcel.writeInt((int)this.mExpanding);
            parcel.writeInt((int)this.mPreviousOperationWasInSameBatchEdit);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface MergeMode {
        }

    }

}

