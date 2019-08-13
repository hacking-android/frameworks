/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.widget.-$
 *  android.widget.-$$Lambda
 *  android.widget.-$$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU
 */
package android.widget;

import android.app.RemoteAction;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Parcelable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.EventLog;
import android.util.Log;
import android.view.ActionMode;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextSelection;
import android.widget.-$;
import android.widget.Editor;
import android.widget.SmartSelectSprite;
import android.widget.TextView;
import android.widget._$$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU;
import android.widget._$$Lambda$E_XesXLNXm7BCuVAnjZcIGfnQJQ;
import android.widget._$$Lambda$IfzAW5fP9thoftErKAjo9SLZufw;
import android.widget._$$Lambda$SelectionActionModeHelper$CcJ0IF8nDFsmkuaqvOxFqYGazzY;
import android.widget._$$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ;
import android.widget._$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E;
import android.widget._$$Lambda$SelectionActionModeHelper$WnFw1_gP20c3ltvTN6OPqQ5XUns;
import android.widget._$$Lambda$SelectionActionModeHelper$cMbIRcH_yFkksR3CQmROa0_hmgM;
import android.widget._$$Lambda$SelectionActionModeHelper$l1f1_V5lw6noQxI_3u11qF753Iw;
import android.widget._$$Lambda$SelectionActionModeHelper$mSUWA79GbPno_4_1PEW8ZDcf0L0;
import android.widget._$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA;
import android.widget._$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI;
import android.widget._$$Lambda$etfJkiCJnT2dqM2O4M2TCm9i_oA;
import android.widget._$$Lambda$yIdmBO6ZxaY03PGN08RySVVQXuE;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class SelectionActionModeHelper {
    private static final String LOG_TAG = "SelectActionModeHelper";
    private final Editor mEditor;
    private final SelectionTracker mSelectionTracker;
    private final SmartSelectSprite mSmartSelectSprite;
    private TextClassification mTextClassification;
    private AsyncTask mTextClassificationAsyncTask;
    private final TextClassificationHelper mTextClassificationHelper;
    private final TextView mTextView;

    SelectionActionModeHelper(Editor object) {
        this.mEditor = Preconditions.checkNotNull(object);
        this.mTextView = this.mEditor.getTextView();
        Context context = this.mTextView.getContext();
        TextView textView = this.mTextView;
        Objects.requireNonNull(textView);
        this.mTextClassificationHelper = new TextClassificationHelper(context, new _$$Lambda$yIdmBO6ZxaY03PGN08RySVVQXuE(textView), SelectionActionModeHelper.getText(this.mTextView), 0, 1, this.mTextView.getTextLocales());
        this.mSelectionTracker = new SelectionTracker(this.mTextView);
        if (this.getTextClassificationSettings().isSmartSelectionAnimationEnabled()) {
            context = this.mTextView.getContext();
            int n = object.getTextView().mHighlightColor;
            object = this.mTextView;
            Objects.requireNonNull(object);
            this.mSmartSelectSprite = new SmartSelectSprite(context, n, new _$$Lambda$IfzAW5fP9thoftErKAjo9SLZufw((TextView)object));
        } else {
            this.mSmartSelectSprite = null;
        }
    }

    private void cancelAsyncTask() {
        AsyncTask asyncTask = this.mTextClassificationAsyncTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mTextClassificationAsyncTask = null;
        }
        this.mTextClassification = null;
    }

    private void cancelSmartSelectAnimation() {
        SmartSelectSprite smartSelectSprite = this.mSmartSelectSprite;
        if (smartSelectSprite != null) {
            smartSelectSprite.cancelAnimation();
        }
    }

    private List<SmartSelectSprite.RectangleWithTextSelectionLayout> convertSelectionToRectangles(Layout layout2, int n, int n2) {
        ArrayList<SmartSelectSprite.RectangleWithTextSelectionLayout> arrayList = new ArrayList<SmartSelectSprite.RectangleWithTextSelectionLayout>();
        layout2.getSelection(n, n2, new _$$Lambda$SelectionActionModeHelper$cMbIRcH_yFkksR3CQmROa0_hmgM(arrayList));
        arrayList.sort(Comparator.comparing(_$$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE, SmartSelectSprite.RECTANGLE_COMPARATOR));
        return arrayList;
    }

    /*
     * Exception decompiling
     */
    private static int getActionType(int var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    private static CharSequence getText(TextView object) {
        if ((object = ((TextView)object).getText()) != null) {
            return object;
        }
        return "";
    }

    private TextClassificationConstants getTextClassificationSettings() {
        return TextClassificationManager.getSettings(this.mTextView.getContext());
    }

    private void invalidateActionMode(SelectionResult object) {
        this.cancelSmartSelectAnimation();
        object = object != null ? ((SelectionResult)object).mClassification : null;
        this.mTextClassification = object;
        object = this.mEditor.getTextActionMode();
        if (object != null) {
            ((ActionMode)object).invalidate();
        }
        this.mSelectionTracker.onSelectionUpdated(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), this.mTextClassification);
        this.mTextClassificationAsyncTask = null;
    }

    public static /* synthetic */ void lambda$CcJ0IF8nDFsmkuaqvOxFqYGazzY(SelectionActionModeHelper selectionActionModeHelper, SelectionResult selectionResult) {
        selectionActionModeHelper.startSelectionActionMode(selectionResult);
    }

    public static /* synthetic */ void lambda$Lwzg10CkEpNBaAXBpjnWEpIlTzQ(SelectionActionModeHelper selectionActionModeHelper, SelectionResult selectionResult) {
        selectionActionModeHelper.invalidateActionMode(selectionResult);
    }

    public static /* synthetic */ void lambda$WnFw1_gP20c3ltvTN6OPqQ5XUns(SelectionActionModeHelper selectionActionModeHelper, SelectionResult selectionResult) {
        selectionActionModeHelper.startLinkActionMode(selectionResult);
    }

    static /* synthetic */ SmartSelectSprite.RectangleWithTextSelectionLayout lambda$convertSelectionToRectangles$1(int n, RectF rectF) {
        return new SmartSelectSprite.RectangleWithTextSelectionLayout(rectF, n);
    }

    static /* synthetic */ void lambda$convertSelectionToRectangles$2(List list, float f, float f2, float f3, float f4, int n) {
        SelectionActionModeHelper.mergeRectangleIntoList(list, new RectF(f, f2, f3, f4), _$$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE, new _$$Lambda$SelectionActionModeHelper$mSUWA79GbPno_4_1PEW8ZDcf0L0(n));
    }

    public static /* synthetic */ void lambda$l1f1_V5lw6noQxI_3u11qF753Iw(SelectionActionModeHelper selectionActionModeHelper, SelectionResult selectionResult) {
        selectionActionModeHelper.startSelectionActionModeWithSmartSelectAnimation(selectionResult);
    }

    @VisibleForTesting
    public static <T> void mergeRectangleIntoList(List<T> list, RectF rectF, Function<T, RectF> function, Function<RectF, T> function2) {
        int n;
        if (rectF.isEmpty()) {
            return;
        }
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            RectF rectF2 = function.apply(list.get(n));
            if (rectF2.contains(rectF)) {
                return;
            }
            if (rectF.contains(rectF2)) {
                rectF2.setEmpty();
                continue;
            }
            float f = rectF.left;
            float f2 = rectF2.right;
            boolean bl = false;
            boolean bl2 = f == f2 || rectF.right == rectF2.left;
            bl2 = rectF.top == rectF2.top && rectF.bottom == rectF2.bottom && (RectF.intersects(rectF, rectF2) || bl2) ? true : bl;
            if (!bl2) continue;
            rectF.union(rectF2);
            rectF2.setEmpty();
        }
        for (n = n2 - 1; n >= 0; --n) {
            if (!function.apply(list.get(n)).isEmpty()) continue;
            list.remove(n);
        }
        list.add(function2.apply(rectF));
    }

    @VisibleForTesting
    public static <T> PointF movePointInsideNearestRectangle(PointF pointF, List<T> list, Function<T, RectF> function) {
        float f = -1.0f;
        float f2 = -1.0f;
        double d = Double.MAX_VALUE;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            RectF rectF = function.apply(list.get(i));
            float f3 = rectF.centerY();
            float f4 = pointF.x > rectF.right ? rectF.right : (pointF.x < rectF.left ? rectF.left : pointF.x);
            double d2 = Math.pow(pointF.x - f4, 2.0) + Math.pow(pointF.y - f3, 2.0);
            double d3 = d;
            if (d2 < d) {
                f2 = f3;
                d3 = d2;
                f = f4;
            }
            d = d3;
        }
        return new PointF(f, f2);
    }

    private void resetTextClassificationHelper() {
        this.resetTextClassificationHelper(-1, -1);
    }

    private void resetTextClassificationHelper(int n, int n2) {
        int n3;
        block3 : {
            block2 : {
                if (n < 0) break block2;
                n3 = n;
                n = n2;
                if (n2 >= 0) break block3;
            }
            n3 = this.mTextView.getSelectionStart();
            n = this.mTextView.getSelectionEnd();
        }
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        TextView textView = this.mTextView;
        Objects.requireNonNull(textView);
        textClassificationHelper.init(new _$$Lambda$yIdmBO6ZxaY03PGN08RySVVQXuE(textView), SelectionActionModeHelper.getText(this.mTextView), n3, n, this.mTextView.getTextLocales());
    }

    private boolean skipTextClassification() {
        boolean bl = this.mTextView.usesNoOpTextClassifier();
        int n = this.mTextView.getSelectionEnd();
        int n2 = this.mTextView.getSelectionStart();
        boolean bl2 = true;
        n2 = n == n2 ? 1 : 0;
        n = !this.mTextView.hasPasswordTransformationMethod() && !TextView.isPasswordInputType(this.mTextView.getInputType()) ? 0 : 1;
        boolean bl3 = bl2;
        if (!bl) {
            bl3 = bl2;
            if (n2 == 0) {
                bl3 = n != 0 ? bl2 : false;
            }
        }
        return bl3;
    }

    private void startActionMode(@Editor.TextActionMode int n, SelectionResult selectionResult) {
        Object object = SelectionActionModeHelper.getText(this.mTextView);
        if (selectionResult != null && object instanceof Spannable && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
            if (!this.getTextClassificationSettings().isModelDarkLaunchEnabled()) {
                Selection.setSelection((Spannable)object, selectionResult.mStart, selectionResult.mEnd);
                this.mTextView.invalidate();
            }
            this.mTextClassification = selectionResult.mClassification;
        } else {
            this.mTextClassification = selectionResult != null && n == 2 ? selectionResult.mClassification : null;
        }
        if (this.mEditor.startActionModeInternal(n)) {
            object = this.mEditor.getSelectionController();
            if (object != null && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
                ((Editor.SelectionModifierCursorController)object).show();
            }
            if (selectionResult != null) {
                if (n != 0) {
                    if (n == 2) {
                        this.mSelectionTracker.onLinkSelected(selectionResult);
                    }
                } else {
                    this.mSelectionTracker.onSmartSelection(selectionResult);
                }
            }
        }
        this.mEditor.setRestartActionModeOnNextRefresh(false);
        this.mTextClassificationAsyncTask = null;
    }

    private void startLinkActionMode(SelectionResult selectionResult) {
        this.startActionMode(2, selectionResult);
    }

    private void startSelectionActionMode(SelectionResult selectionResult) {
        this.startActionMode(0, selectionResult);
    }

    private void startSelectionActionModeWithSmartSelectAnimation(SelectionResult object) {
        Object object2 = this.mTextView.getLayout();
        _$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA _$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA = new _$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA(this, (SelectionResult)object);
        boolean bl = object != null && (this.mTextView.getSelectionStart() != ((SelectionResult)object).mStart || this.mTextView.getSelectionEnd() != ((SelectionResult)object).mEnd);
        if (!bl) {
            _$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA.run();
            return;
        }
        object2 = this.convertSelectionToRectangles((Layout)object2, ((SelectionResult)object).mStart, ((SelectionResult)object).mEnd);
        object = SelectionActionModeHelper.movePointInsideNearestRectangle(new PointF(this.mEditor.getLastUpPositionX(), this.mEditor.getLastUpPositionY()), object2, _$$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE);
        this.mSmartSelectSprite.startAnimation((PointF)object, (List<SmartSelectSprite.RectangleWithTextSelectionLayout>)object2, _$$Lambda$SelectionActionModeHelper$xdBRwQcbRdz8duQr0RBo4YKAnOA);
    }

    public TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public void invalidateActionModeAsync() {
        this.cancelAsyncTask();
        if (this.skipTextClassification()) {
            this.invalidateActionMode(null);
        } else {
            this.resetTextClassificationHelper();
            TextView textView = this.mTextView;
            int n = this.mTextClassificationHelper.getTimeoutDuration();
            Object object = this.mTextClassificationHelper;
            Objects.requireNonNull(object);
            object = new _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI((TextClassificationHelper)object);
            _$$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ _$$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ = new _$$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ(this);
            TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper);
            this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, n, (Supplier<SelectionResult>)object, _$$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ, new _$$Lambda$etfJkiCJnT2dqM2O4M2TCm9i_oA(textClassificationHelper)).execute(new Void[0]);
        }
    }

    public boolean isDrawingHighlight() {
        SmartSelectSprite smartSelectSprite = this.mSmartSelectSprite;
        boolean bl = smartSelectSprite != null && smartSelectSprite.isAnimationActive();
        return bl;
    }

    public /* synthetic */ void lambda$startSelectionActionModeWithSmartSelectAnimation$0$SelectionActionModeHelper(SelectionResult selectionResult) {
        if (selectionResult == null || selectionResult.mStart < 0 || selectionResult.mEnd > SelectionActionModeHelper.getText(this.mTextView).length() || selectionResult.mStart > selectionResult.mEnd) {
            selectionResult = null;
        }
        this.startSelectionActionMode(selectionResult);
    }

    public void onDestroyActionMode() {
        this.cancelSmartSelectAnimation();
        this.mSelectionTracker.onSelectionDestroyed();
        this.cancelAsyncTask();
    }

    public void onDraw(Canvas canvas) {
        SmartSelectSprite smartSelectSprite;
        if (this.isDrawingHighlight() && (smartSelectSprite = this.mSmartSelectSprite) != null) {
            smartSelectSprite.draw(canvas);
        }
    }

    public void onSelectionAction(int n, String string2) {
        this.mSelectionTracker.onSelectionAction(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), SelectionActionModeHelper.getActionType(n), string2, this.mTextClassification);
    }

    public void onSelectionDrag() {
        this.mSelectionTracker.onSelectionAction(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), 106, null, this.mTextClassification);
    }

    public void onTextChanged(int n, int n2) {
        this.mSelectionTracker.onTextChanged(n, n2, this.mTextClassification);
    }

    public boolean resetSelection(int n) {
        if (this.mSelectionTracker.resetSelection(n, this.mEditor)) {
            this.invalidateActionModeAsync();
            return true;
        }
        return false;
    }

    public void startLinkActionModeAsync(int n, int n2) {
        this.mSelectionTracker.onOriginalSelection(SelectionActionModeHelper.getText(this.mTextView), n, n2, true);
        this.cancelAsyncTask();
        if (this.skipTextClassification()) {
            this.startLinkActionMode(null);
        } else {
            this.resetTextClassificationHelper(n, n2);
            TextView textView = this.mTextView;
            n = this.mTextClassificationHelper.getTimeoutDuration();
            Object object = this.mTextClassificationHelper;
            Objects.requireNonNull(object);
            _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI = new _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI((TextClassificationHelper)object);
            object = new _$$Lambda$SelectionActionModeHelper$WnFw1_gP20c3ltvTN6OPqQ5XUns(this);
            TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper);
            this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, n, _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI, (Consumer<SelectionResult>)object, new _$$Lambda$etfJkiCJnT2dqM2O4M2TCm9i_oA(textClassificationHelper)).execute(new Void[0]);
        }
    }

    public void startSelectionActionModeAsync(boolean bl) {
        boolean bl2 = this.getTextClassificationSettings().isSmartSelectionEnabled();
        this.mSelectionTracker.onOriginalSelection(SelectionActionModeHelper.getText(this.mTextView), this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), false);
        this.cancelAsyncTask();
        if (this.skipTextClassification()) {
            this.startSelectionActionMode(null);
        } else {
            Object object;
            this.resetTextClassificationHelper();
            TextView textView = this.mTextView;
            int n = this.mTextClassificationHelper.getTimeoutDuration();
            if (bl & bl2) {
                object = this.mTextClassificationHelper;
                Objects.requireNonNull(object);
                object = new _$$Lambda$E_XesXLNXm7BCuVAnjZcIGfnQJQ((TextClassificationHelper)object);
            } else {
                object = this.mTextClassificationHelper;
                Objects.requireNonNull(object);
                object = new _$$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI((TextClassificationHelper)object);
            }
            Consumer consumer = this.mSmartSelectSprite != null ? new _$$Lambda$SelectionActionModeHelper$l1f1_V5lw6noQxI_3u11qF753Iw(this) : new _$$Lambda$SelectionActionModeHelper$CcJ0IF8nDFsmkuaqvOxFqYGazzY(this);
            TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper);
            this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, n, (Supplier<SelectionResult>)object, consumer, new _$$Lambda$etfJkiCJnT2dqM2O4M2TCm9i_oA(textClassificationHelper)).execute(new Void[0]);
        }
    }

    private static final class SelectionMetricsLogger {
        private static final String LOG_TAG = "SelectionMetricsLogger";
        private static final Pattern PATTERN_WHITESPACE = Pattern.compile("\\s+");
        private TextClassificationContext mClassificationContext;
        private TextClassifier mClassificationSession;
        private final boolean mEditTextLogger;
        private int mStartIndex;
        private String mText;
        private final BreakIterator mTokenIterator;
        private TextClassifierEvent mTranslateClickEvent;
        private TextClassifierEvent mTranslateViewEvent;

        SelectionMetricsLogger(TextView textView) {
            Preconditions.checkNotNull(textView);
            this.mEditTextLogger = textView.isTextEditable();
            this.mTokenIterator = SelectionSessionLogger.getTokenIterator(textView.getTextLocale());
        }

        private int countWordsBackward(int n) {
            boolean bl = n >= this.mStartIndex;
            Preconditions.checkArgument(bl);
            int n2 = 0;
            int n3 = n;
            while (n3 > this.mStartIndex) {
                int n4 = this.mTokenIterator.preceding(n3);
                n = n2;
                if (!this.isWhitespace(n4, n3)) {
                    n = n2 + 1;
                }
                n3 = n4;
                n2 = n;
            }
            return n2;
        }

        private int countWordsForward(int n) {
            boolean bl = n <= this.mStartIndex;
            Preconditions.checkArgument(bl);
            int n2 = 0;
            while (n < this.mStartIndex) {
                int n3 = this.mTokenIterator.following(n);
                int n4 = n2;
                if (!this.isWhitespace(n, n3)) {
                    n4 = n2 + 1;
                }
                n = n3;
                n2 = n4;
            }
            return n2;
        }

        private static TextClassifierEvent generateTranslateEvent(int n, TextClassification textClassification, TextClassificationContext textClassificationContext, String string2) {
            RemoteAction remoteAction = ExtrasUtils.findTranslateAction(textClassification);
            if (remoteAction == null) {
                return null;
            }
            if (n == 13 && !remoteAction.getTitle().toString().equals(string2)) {
                return null;
            }
            Object object = ExtrasUtils.getForeignLanguageExtra(textClassification);
            string2 = ExtrasUtils.getEntityType((Bundle)object);
            float f = ExtrasUtils.getScore((Bundle)object);
            object = ExtrasUtils.getModelName((Bundle)object);
            return ((TextClassifierEvent.LanguageDetectionEvent.Builder)((TextClassifierEvent.LanguageDetectionEvent.Builder)((TextClassifierEvent.LanguageDetectionEvent.Builder)((TextClassifierEvent.LanguageDetectionEvent.Builder)((TextClassifierEvent.LanguageDetectionEvent.Builder)((TextClassifierEvent.LanguageDetectionEvent.Builder)new TextClassifierEvent.LanguageDetectionEvent.Builder(n).setEventContext(textClassificationContext)).setResultId(textClassification.getId())).setEntityTypes(string2)).setScores(f)).setActionIndices(textClassification.getActions().indexOf(remoteAction))).setModelName((String)object)).build();
        }

        private int[] getWordDelta(int n, int n2) {
            int[] arrn = new int[2];
            int n3 = this.mStartIndex;
            if (n == n3) {
                arrn[0] = 0;
            } else if (n < n3) {
                arrn[0] = -this.countWordsForward(n);
            } else {
                arrn[0] = this.countWordsBackward(n);
                if (!this.mTokenIterator.isBoundary(n) && !this.isWhitespace(this.mTokenIterator.preceding(n), this.mTokenIterator.following(n))) {
                    arrn[0] = arrn[0] - 1;
                }
            }
            n = this.mStartIndex;
            arrn[1] = n2 == n ? 0 : (n2 < n ? -this.countWordsForward(n2) : this.countWordsBackward(n2));
            return arrn;
        }

        private boolean hasActiveClassificationSession() {
            TextClassifier textClassifier = this.mClassificationSession;
            boolean bl = textClassifier != null && !textClassifier.isDestroyed();
            return bl;
        }

        private boolean isWhitespace(int n, int n2) {
            return PATTERN_WHITESPACE.matcher(this.mText.substring(n, n2)).matches();
        }

        private void maybeGenerateTranslateClickEvent(TextClassification textClassification, String string2) {
            if (textClassification != null) {
                this.mTranslateClickEvent = SelectionMetricsLogger.generateTranslateEvent(13, textClassification, this.mClassificationContext, string2);
            }
        }

        private void maybeGenerateTranslateViewEvent(TextClassification parcelable) {
            if (parcelable != null) {
                if ((parcelable = SelectionMetricsLogger.generateTranslateEvent(6, parcelable, this.mClassificationContext, null)) == null) {
                    parcelable = this.mTranslateViewEvent;
                }
                this.mTranslateViewEvent = parcelable;
            }
        }

        private void maybeReportTranslateEvents() {
            TextClassifierEvent textClassifierEvent = this.mTranslateViewEvent;
            if (textClassifierEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(textClassifierEvent);
                this.mTranslateViewEvent = null;
            }
            if ((textClassifierEvent = this.mTranslateClickEvent) != null) {
                this.mClassificationSession.onTextClassifierEvent(textClassifierEvent);
                this.mTranslateClickEvent = null;
            }
        }

        public void endTextClassificationSession() {
            if (this.hasActiveClassificationSession()) {
                this.maybeReportTranslateEvents();
                this.mClassificationSession.destroy();
            }
        }

        public boolean isEditTextLogger() {
            return this.mEditTextLogger;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void logSelectionAction(int n, int n2, int n3, String charSequence, TextClassification textClassification) {
            try {
                if (!this.hasActiveClassificationSession()) return;
                Preconditions.checkArgumentInRange(n, 0, this.mText.length(), "start");
                Preconditions.checkArgumentInRange(n2, n, this.mText.length(), "end");
                int[] arrn = this.getWordDelta(n, n2);
                if (textClassification != null) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(arrn[0], arrn[1], n3, textClassification));
                } else {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(arrn[0], arrn[1], n3));
                }
                this.maybeGenerateTranslateClickEvent(textClassification, (String)charSequence);
                if (!SelectionEvent.isTerminal(n3)) return;
                this.endTextClassificationSession();
                return;
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("");
                ((StringBuilder)charSequence).append(exception.getMessage());
                Log.e(LOG_TAG, ((StringBuilder)charSequence).toString(), exception);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void logSelectionModified(int n, int n2, TextClassification object, TextSelection textSelection) {
            try {
                if (!this.hasActiveClassificationSession()) return;
                Preconditions.checkArgumentInRange(n, 0, this.mText.length(), "start");
                Preconditions.checkArgumentInRange(n2, n, this.mText.length(), "end");
                int[] arrn = this.getWordDelta(n, n2);
                if (textSelection != null) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(arrn[0], arrn[1], textSelection));
                } else if (object != null) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(arrn[0], arrn[1], (TextClassification)object));
                } else {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(arrn[0], arrn[1]));
                }
                this.maybeGenerateTranslateViewEvent((TextClassification)object);
                return;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(exception.getMessage());
                Log.e(LOG_TAG, ((StringBuilder)object).toString(), exception);
            }
        }

        public void logSelectionStarted(TextClassifier object, TextClassificationContext textClassificationContext, CharSequence charSequence, int n, int n2) {
            try {
                Preconditions.checkNotNull(charSequence);
                Preconditions.checkArgumentInRange(n, 0, charSequence.length(), "index");
                if (this.mText == null || !this.mText.contentEquals(charSequence)) {
                    this.mText = charSequence.toString();
                }
                this.mTokenIterator.setText(this.mText);
                this.mStartIndex = n;
                this.mClassificationSession = object;
                this.mClassificationContext = textClassificationContext;
                if (this.hasActiveClassificationSession()) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionStartedEvent(n2, 0));
                }
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(exception.getMessage());
                Log.e(LOG_TAG, ((StringBuilder)object).toString(), exception);
            }
        }
    }

    private static final class SelectionResult {
        private final TextClassification mClassification;
        private final int mEnd;
        private final TextSelection mSelection;
        private final int mStart;

        SelectionResult(int n, int n2, TextClassification textClassification, TextSelection textSelection) {
            this.mStart = n;
            this.mEnd = n2;
            this.mClassification = textClassification;
            this.mSelection = textSelection;
        }
    }

    private static final class SelectionTracker {
        private boolean mAllowReset;
        private final LogAbandonRunnable mDelayedLogAbandon = new LogAbandonRunnable();
        private SelectionMetricsLogger mLogger;
        private int mOriginalEnd;
        private int mOriginalStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private final TextView mTextView;

        SelectionTracker(TextView textView) {
            this.mTextView = Preconditions.checkNotNull(textView);
            this.mLogger = new SelectionMetricsLogger(textView);
        }

        private boolean isSelectionStarted() {
            int n;
            int n2 = this.mSelectionStart;
            boolean bl = n2 >= 0 && (n = this.mSelectionEnd) >= 0 && n2 != n;
            return bl;
        }

        private void maybeInvalidateLogger() {
            if (this.mLogger.isEditTextLogger() != this.mTextView.isTextEditable()) {
                this.mLogger = new SelectionMetricsLogger(this.mTextView);
            }
        }

        private void onClassifiedSelection(SelectionResult selectionResult) {
            if (this.isSelectionStarted()) {
                this.mSelectionStart = selectionResult.mStart;
                this.mSelectionEnd = selectionResult.mEnd;
                boolean bl = this.mSelectionStart != this.mOriginalStart || this.mSelectionEnd != this.mOriginalEnd;
                this.mAllowReset = bl;
            }
        }

        public void onLinkSelected(SelectionResult selectionResult) {
            this.onClassifiedSelection(selectionResult);
        }

        public void onOriginalSelection(CharSequence charSequence, int n, int n2, boolean bl) {
            this.mDelayedLogAbandon.flush();
            this.mSelectionStart = n;
            this.mOriginalStart = n;
            this.mSelectionEnd = n2;
            this.mOriginalEnd = n2;
            this.mAllowReset = false;
            this.maybeInvalidateLogger();
            SelectionMetricsLogger selectionMetricsLogger = this.mLogger;
            TextClassifier textClassifier = this.mTextView.getTextClassificationSession();
            TextClassificationContext textClassificationContext = this.mTextView.getTextClassificationContext();
            n2 = bl ? 2 : 1;
            selectionMetricsLogger.logSelectionStarted(textClassifier, textClassificationContext, charSequence, n, n2);
        }

        public void onSelectionAction(int n, int n2, int n3, String string2, TextClassification textClassification) {
            if (this.isSelectionStarted()) {
                this.mAllowReset = false;
                this.mLogger.logSelectionAction(n, n2, n3, string2, textClassification);
            }
        }

        public void onSelectionDestroyed() {
            this.mAllowReset = false;
            this.mDelayedLogAbandon.schedule(100);
        }

        public void onSelectionUpdated(int n, int n2, TextClassification textClassification) {
            if (this.isSelectionStarted()) {
                this.mSelectionStart = n;
                this.mSelectionEnd = n2;
                this.mAllowReset = false;
                this.mLogger.logSelectionModified(n, n2, textClassification, null);
            }
        }

        public void onSmartSelection(SelectionResult selectionResult) {
            this.onClassifiedSelection(selectionResult);
            this.mLogger.logSelectionModified(selectionResult.mStart, selectionResult.mEnd, selectionResult.mClassification, selectionResult.mSelection);
        }

        public void onTextChanged(int n, int n2, TextClassification textClassification) {
            if (this.isSelectionStarted() && n == this.mSelectionStart && n2 == this.mSelectionEnd) {
                this.onSelectionAction(n, n2, 100, null, textClassification);
            }
        }

        public boolean resetSelection(int n, Editor editor) {
            TextView textView = editor.getTextView();
            if (this.isSelectionStarted() && this.mAllowReset && n >= this.mSelectionStart && n <= this.mSelectionEnd && SelectionActionModeHelper.getText(textView) instanceof Spannable) {
                this.mAllowReset = false;
                boolean bl = editor.selectCurrentWord();
                if (bl) {
                    this.mSelectionStart = editor.getTextView().getSelectionStart();
                    this.mSelectionEnd = editor.getTextView().getSelectionEnd();
                    this.mLogger.logSelectionAction(textView.getSelectionStart(), textView.getSelectionEnd(), 201, null, null);
                }
                return bl;
            }
            return false;
        }

        private final class LogAbandonRunnable
        implements Runnable {
            private boolean mIsPending;

            private LogAbandonRunnable() {
            }

            void flush() {
                SelectionTracker.this.mTextView.removeCallbacks(this);
                this.run();
            }

            @Override
            public void run() {
                if (this.mIsPending) {
                    SelectionTracker.this.mLogger.logSelectionAction(SelectionTracker.this.mSelectionStart, SelectionTracker.this.mSelectionEnd, 107, null, null);
                    SelectionTracker selectionTracker = SelectionTracker.this;
                    selectionTracker.mSelectionStart = (selectionTracker.mSelectionEnd = -1);
                    SelectionTracker.this.mLogger.endTextClassificationSession();
                    this.mIsPending = false;
                }
            }

            void schedule(int n) {
                if (this.mIsPending) {
                    Log.e(SelectionActionModeHelper.LOG_TAG, "Force flushing abandon due to new scheduling request");
                    this.flush();
                }
                this.mIsPending = true;
                SelectionTracker.this.mTextView.postDelayed(this, n);
            }
        }

    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    private static final class TextClassificationAsyncTask
    extends AsyncTask<Void, Void, SelectionResult> {
        private final String mOriginalText;
        private final Consumer<SelectionResult> mSelectionResultCallback;
        private final Supplier<SelectionResult> mSelectionResultSupplier;
        private final TextView mTextView;
        private final int mTimeOutDuration;
        private final Supplier<SelectionResult> mTimeOutResultSupplier;

        TextClassificationAsyncTask(TextView textView, int n, Supplier<SelectionResult> supplier, Consumer<SelectionResult> consumer, Supplier<SelectionResult> supplier2) {
            Handler handler = textView != null ? textView.getHandler() : null;
            super(handler);
            this.mTextView = Preconditions.checkNotNull(textView);
            this.mTimeOutDuration = n;
            this.mSelectionResultSupplier = Preconditions.checkNotNull(supplier);
            this.mSelectionResultCallback = Preconditions.checkNotNull(consumer);
            this.mTimeOutResultSupplier = Preconditions.checkNotNull(supplier2);
            this.mOriginalText = SelectionActionModeHelper.getText(this.mTextView).toString();
        }

        public static /* synthetic */ void lambda$D5tkmK-caFBtl9ux2L0aUfUee4E(TextClassificationAsyncTask textClassificationAsyncTask) {
            textClassificationAsyncTask.onTimeOut();
        }

        private void onTimeOut() {
            if (this.getStatus() == AsyncTask.Status.RUNNING) {
                this.onPostExecute(this.mTimeOutResultSupplier.get());
            }
            this.cancel(true);
        }

        protected SelectionResult doInBackground(Void ... object) {
            _$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E _$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E = new _$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E(this);
            this.mTextView.postDelayed(_$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E, this.mTimeOutDuration);
            object = this.mSelectionResultSupplier.get();
            this.mTextView.removeCallbacks(_$$Lambda$SelectionActionModeHelper$TextClassificationAsyncTask$D5tkmK_caFBtl9ux2L0aUfUee4E);
            return object;
        }

        @Override
        protected void onPostExecute(SelectionResult selectionResult) {
            if (!TextUtils.equals(this.mOriginalText, SelectionActionModeHelper.getText(this.mTextView))) {
                selectionResult = null;
            }
            this.mSelectionResultCallback.accept(selectionResult);
        }
    }

    private static final class TextClassificationHelper {
        private static final int TRIM_DELTA = 120;
        private final Context mContext;
        private LocaleList mDefaultLocales;
        private boolean mHot;
        private LocaleList mLastClassificationLocales;
        private SelectionResult mLastClassificationResult;
        private int mLastClassificationSelectionEnd;
        private int mLastClassificationSelectionStart;
        private CharSequence mLastClassificationText;
        private int mRelativeEnd;
        private int mRelativeStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private String mText;
        private Supplier<TextClassifier> mTextClassifier;
        private int mTrimStart;
        private CharSequence mTrimmedText;

        TextClassificationHelper(Context context, Supplier<TextClassifier> supplier, CharSequence charSequence, int n, int n2, LocaleList localeList) {
            this.init(supplier, charSequence, n, n2, localeList);
            this.mContext = Preconditions.checkNotNull(context);
        }

        private boolean isDarkLaunchEnabled() {
            return TextClassificationManager.getSettings(this.mContext).isModelDarkLaunchEnabled();
        }

        private SelectionResult performClassification(TextSelection textSelection) {
            if (!Objects.equals(this.mText, this.mLastClassificationText) || this.mSelectionStart != this.mLastClassificationSelectionStart || this.mSelectionEnd != this.mLastClassificationSelectionEnd || !Objects.equals(this.mDefaultLocales, this.mLastClassificationLocales)) {
                Parcelable parcelable;
                this.mLastClassificationText = this.mText;
                this.mLastClassificationSelectionStart = this.mSelectionStart;
                this.mLastClassificationSelectionEnd = this.mSelectionEnd;
                this.mLastClassificationLocales = this.mDefaultLocales;
                this.trimText();
                if (Linkify.containsUnsupportedCharacters(this.mText)) {
                    EventLog.writeEvent(1397638484, "116321860", -1, "");
                    parcelable = TextClassification.EMPTY;
                } else if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                    parcelable = new TextClassification.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).build();
                    parcelable = this.mTextClassifier.get().classifyText((TextClassification.Request)parcelable);
                } else {
                    parcelable = this.mTextClassifier.get().classifyText(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
                }
                this.mLastClassificationResult = new SelectionResult(this.mSelectionStart, this.mSelectionEnd, (TextClassification)parcelable, textSelection);
            }
            return this.mLastClassificationResult;
        }

        private void trimText() {
            this.mTrimStart = Math.max(0, this.mSelectionStart - 120);
            int n = Math.min(this.mText.length(), this.mSelectionEnd + 120);
            this.mTrimmedText = this.mText.subSequence(this.mTrimStart, n);
            n = this.mSelectionStart;
            int n2 = this.mTrimStart;
            this.mRelativeStart = n - n2;
            this.mRelativeEnd = this.mSelectionEnd - n2;
        }

        public SelectionResult classifyText() {
            this.mHot = true;
            return this.performClassification(null);
        }

        public SelectionResult getOriginalSelection() {
            return new SelectionResult(this.mSelectionStart, this.mSelectionEnd, null, null);
        }

        public int getTimeoutDuration() {
            if (this.mHot) {
                return 200;
            }
            return 500;
        }

        public void init(Supplier<TextClassifier> supplier, CharSequence charSequence, int n, int n2, LocaleList localeList) {
            this.mTextClassifier = Preconditions.checkNotNull(supplier);
            this.mText = Preconditions.checkNotNull(charSequence).toString();
            this.mLastClassificationText = null;
            boolean bl = n2 > n;
            Preconditions.checkArgument(bl);
            this.mSelectionStart = n;
            this.mSelectionEnd = n2;
            this.mDefaultLocales = localeList;
        }

        public SelectionResult suggestSelection() {
            Parcelable parcelable;
            this.mHot = true;
            this.trimText();
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                parcelable = new TextSelection.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).setDarkLaunchAllowed(true).build();
                parcelable = this.mTextClassifier.get().suggestSelection((TextSelection.Request)parcelable);
            } else {
                parcelable = this.mTextClassifier.get().suggestSelection(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
            }
            if (!this.isDarkLaunchEnabled()) {
                this.mSelectionStart = Math.max(0, ((TextSelection)parcelable).getSelectionStartIndex() + this.mTrimStart);
                this.mSelectionEnd = Math.min(this.mText.length(), ((TextSelection)parcelable).getSelectionEndIndex() + this.mTrimStart);
            }
            return this.performClassification((TextSelection)parcelable);
        }
    }

}

