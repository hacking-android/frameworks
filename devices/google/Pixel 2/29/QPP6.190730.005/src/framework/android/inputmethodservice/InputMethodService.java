/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.ExtractEditText;
import android.inputmethodservice.SoftInputWindow;
import android.inputmethodservice._$$Lambda$InputMethodService$8T9TmAUIN7vW9eU6kTg8309_d4E;
import android.inputmethodservice._$$Lambda$InputMethodService$wp8DeVGx_WDOPw4F6an7QbwVxf0;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.inputmethod.IInputContentUriToken;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.InputMethodPrivilegedOperations;
import com.android.internal.inputmethod.InputMethodPrivilegedOperationsRegistry;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class InputMethodService
extends AbstractInputMethodService {
    public static final int BACK_DISPOSITION_ADJUST_NOTHING = 3;
    public static final int BACK_DISPOSITION_DEFAULT = 0;
    private static final int BACK_DISPOSITION_MAX = 3;
    private static final int BACK_DISPOSITION_MIN = 0;
    @Deprecated
    public static final int BACK_DISPOSITION_WILL_DISMISS = 2;
    @Deprecated
    public static final int BACK_DISPOSITION_WILL_NOT_DISMISS = 1;
    static final boolean DEBUG = false;
    public static final int IME_ACTIVE = 1;
    public static final int IME_INVISIBLE = 4;
    public static final int IME_VISIBLE = 2;
    static final int MOVEMENT_DOWN = -1;
    static final int MOVEMENT_UP = -2;
    static final String TAG = "InputMethodService";
    final View.OnClickListener mActionClickListener = new _$$Lambda$InputMethodService$wp8DeVGx_WDOPw4F6an7QbwVxf0(this);
    int mBackDisposition;
    boolean mCanPreRender;
    FrameLayout mCandidatesFrame;
    boolean mCandidatesViewStarted;
    int mCandidatesVisibility;
    CompletionInfo[] mCurCompletions;
    boolean mDecorViewVisible;
    boolean mDecorViewWasVisible;
    ViewGroup mExtractAccessories;
    View mExtractAction;
    @UnsupportedAppUsage
    ExtractEditText mExtractEditText;
    FrameLayout mExtractFrame;
    @UnsupportedAppUsage
    View mExtractView;
    boolean mExtractViewHidden;
    ExtractedText mExtractedText;
    int mExtractedToken;
    boolean mFullscreenApplied;
    ViewGroup mFullscreenArea;
    InputMethodManager mImm;
    boolean mInShowWindow;
    LayoutInflater mInflater;
    boolean mInitialized;
    InputBinding mInputBinding;
    InputConnection mInputConnection;
    EditorInfo mInputEditorInfo;
    FrameLayout mInputFrame;
    boolean mInputStarted;
    View mInputView;
    boolean mInputViewStarted;
    final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new _$$Lambda$InputMethodService$8T9TmAUIN7vW9eU6kTg8309_d4E(this);
    boolean mIsFullscreen;
    boolean mIsInputViewShown;
    boolean mIsPreRendered;
    boolean mLastShowInputRequested;
    private Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private boolean mNotifyUserActionSent;
    private InputMethodPrivilegedOperations mPrivOps = new InputMethodPrivilegedOperations();
    @UnsupportedAppUsage
    View mRootView;
    @UnsupportedAppUsage
    private SettingsObserver mSettingsObserver;
    int mShowInputFlags;
    boolean mShowInputRequested;
    InputConnection mStartedInputConnection;
    int mStatusIcon;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mTheme = 0;
    TypedArray mThemeAttrs;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    final Insets mTmpInsets = new Insets();
    final int[] mTmpLocation = new int[2];
    IBinder mToken;
    boolean mViewsCreated;
    SoftInputWindow mWindow;
    boolean mWindowVisible;

    private void applyVisibilityInInsetsConsumer(boolean bl) {
        if (!this.mIsPreRendered) {
            return;
        }
        this.mPrivOps.applyImeVisibility(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispatchOnCurrentInputMethodSubtypeChanged(InputMethodSubtype inputMethodSubtype) {
        Object object = this.mLock;
        synchronized (object) {
            this.mNotifyUserActionSent = false;
        }
        this.onCurrentInputMethodSubtypeChanged(inputMethodSubtype);
    }

    private boolean dispatchOnShowInputRequested(int n, boolean bl) {
        this.mShowInputFlags = (bl = this.onShowInputRequested(n, bl)) ? n : 0;
        return bl;
    }

    private void doHideWindow() {
        this.setImeWindowStatus(0, this.mBackDisposition);
        this.hideWindow();
    }

    private void exposeContentInternal(InputContentInfo object, EditorInfo editorInfo) {
        Uri uri = ((InputContentInfo)object).getContentUri();
        IInputContentUriToken iInputContentUriToken = this.mPrivOps.createInputContentUriToken(uri, editorInfo.packageName);
        if (iInputContentUriToken == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createInputContentAccessToken failed. contentUri=");
            ((StringBuilder)object).append(uri.toString());
            ((StringBuilder)object).append(" packageName=");
            ((StringBuilder)object).append(editorInfo.packageName);
            Log.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        ((InputContentInfo)object).setUriToken(iInputContentUriToken);
    }

    private void finishViews(boolean bl) {
        if (this.mInputViewStarted) {
            this.onFinishInputView(bl);
        } else if (this.mCandidatesViewStarted) {
            this.onFinishCandidatesView(bl);
        }
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
    }

    private ExtractEditText getExtractEditTextIfVisible() {
        if (this.isExtractViewShown() && this.isInputViewShown()) {
            return this.mExtractEditText;
        }
        return null;
    }

    private int getIconForImeAction(int n) {
        switch (n & 255) {
            default: {
                return 17302432;
            }
            case 7: {
                return 17302431;
            }
            case 6: {
                return 17302428;
            }
            case 5: {
                return 17302430;
            }
            case 4: {
                return 17302434;
            }
            case 3: {
                return 17302433;
            }
            case 2: 
        }
        return 17302429;
    }

    private boolean handleBack(boolean bl) {
        if (this.mShowInputRequested) {
            if (bl) {
                this.requestHideSelf(0);
            }
            return true;
        }
        if (this.mDecorViewVisible) {
            if (this.mCandidatesVisibility == 0) {
                if (bl) {
                    this.setCandidatesViewShown(false);
                }
            } else if (bl) {
                this.doHideWindow();
            }
            return true;
        }
        return false;
    }

    private int mapToImeWindowStatus() {
        boolean bl = this.isInputViewShown();
        int n = 2;
        if (bl) {
            if (this.mCanPreRender && !this.mWindowVisible) {
                n = 4;
            }
        } else {
            n = 0;
        }
        return 1 | n;
    }

    private void maybeNotifyPreRendered() {
        if (this.mCanPreRender && this.mIsPreRendered) {
            this.mPrivOps.reportPreRendered(this.getCurrentInputEditorInfo());
            return;
        }
    }

    private void notifyImeHidden() {
        this.setImeWindowStatus(5, this.mBackDisposition);
        this.onPreRenderedWindowVisibilityChanged(false);
    }

    private void onPreRenderedWindowVisibilityChanged(boolean bl) {
        this.mWindowVisible = bl;
        int n = bl ? this.mShowInputFlags : 0;
        this.mShowInputFlags = n;
        this.mShowInputRequested = bl;
        this.mDecorViewVisible = bl;
        if (bl) {
            this.onWindowShown();
        }
    }

    private void onToggleSoftInput(int n, int n2) {
        if (this.isInputViewShown()) {
            this.requestHideSelf(n2);
        } else {
            this.requestShowSelf(n);
        }
    }

    private boolean prepareWindow(boolean bl) {
        boolean bl2 = false;
        this.mDecorViewVisible = true;
        boolean bl3 = bl2;
        if (!this.mShowInputRequested) {
            bl3 = bl2;
            if (this.mInputStarted) {
                bl3 = bl2;
                if (bl) {
                    bl3 = true;
                    this.mShowInputRequested = true;
                }
            }
        }
        this.initialize();
        this.updateFullscreenMode();
        this.updateInputViewShown();
        if (!this.mViewsCreated) {
            this.mViewsCreated = true;
            this.initialize();
            View view = this.onCreateCandidatesView();
            if (view != null) {
                this.setCandidatesView(view);
            }
        }
        return bl3;
    }

    private void reportFullscreenMode() {
        this.mPrivOps.reportFullscreenMode(this.mIsFullscreen);
    }

    private void resetStateForNewConfiguration() {
        boolean bl = this.mDecorViewVisible;
        int n = this.mShowInputFlags;
        boolean bl2 = this.mShowInputRequested;
        CompletionInfo[] arrcompletionInfo = this.mCurCompletions;
        this.initViews();
        int n2 = 0;
        this.mInputViewStarted = false;
        this.mCandidatesViewStarted = false;
        if (this.mInputStarted) {
            this.doStartInput(this.getCurrentInputConnection(), this.getCurrentInputEditorInfo(), true);
        }
        if (bl) {
            if (bl2) {
                if (this.dispatchOnShowInputRequested(n, true)) {
                    this.showWindow(true);
                    if (arrcompletionInfo != null) {
                        this.mCurCompletions = arrcompletionInfo;
                        this.onDisplayCompletions(arrcompletionInfo);
                    }
                } else {
                    this.doHideWindow();
                }
            } else if (this.mCandidatesVisibility == 0) {
                this.showWindow(false);
            } else {
                this.doHideWindow();
            }
            if (this.onEvaluateInputViewShown()) {
                n2 = 2;
            }
            this.setImeWindowStatus(n2 | 1, this.mBackDisposition);
        }
    }

    private void setImeWindowStatus(int n, int n2) {
        this.mPrivOps.setImeWindowStatus(n, n2);
    }

    private void startViews(boolean bl) {
        if (this.mShowInputRequested) {
            if (!this.mInputViewStarted) {
                this.mInputViewStarted = true;
                this.onStartInputView(this.mInputEditorInfo, false);
            }
        } else if (!this.mCandidatesViewStarted) {
            this.mCandidatesViewStarted = true;
            this.onStartCandidatesView(this.mInputEditorInfo, false);
        }
        if (bl) {
            this.startExtractingText(false);
        }
    }

    void doFinishInput() {
        this.finishViews(true);
        if (this.mInputStarted) {
            this.onFinishInput();
        }
        this.mInputStarted = false;
        this.mStartedInputConnection = null;
        this.mCurCompletions = null;
    }

    boolean doMovementKey(int n, KeyEvent keyEvent, int n2) {
        ExtractEditText extractEditText = this.getExtractEditTextIfVisible();
        if (extractEditText != null) {
            MovementMethod movementMethod = extractEditText.getMovementMethod();
            Object object = extractEditText.getLayout();
            if (movementMethod != null && object != null) {
                if (n2 == -1) {
                    if (movementMethod.onKeyDown(extractEditText, extractEditText.getText(), n, keyEvent)) {
                        this.reportExtractedMovement(n, 1);
                        return true;
                    }
                } else if (n2 == -2) {
                    if (movementMethod.onKeyUp(extractEditText, extractEditText.getText(), n, keyEvent)) {
                        return true;
                    }
                } else if (movementMethod.onKeyOther(extractEditText, extractEditText.getText(), keyEvent)) {
                    this.reportExtractedMovement(n, n2);
                } else {
                    object = KeyEvent.changeAction(keyEvent, 0);
                    if (movementMethod.onKeyDown(extractEditText, extractEditText.getText(), n, (KeyEvent)object)) {
                        keyEvent = KeyEvent.changeAction(keyEvent, 1);
                        movementMethod.onKeyUp(extractEditText, extractEditText.getText(), n, keyEvent);
                        while (--n2 > 0) {
                            movementMethod.onKeyDown(extractEditText, extractEditText.getText(), n, (KeyEvent)object);
                            movementMethod.onKeyUp(extractEditText, extractEditText.getText(), n, keyEvent);
                        }
                        this.reportExtractedMovement(n, n2);
                    }
                }
            }
            switch (n) {
                default: {
                    break;
                }
                case 19: 
                case 20: 
                case 21: 
                case 22: {
                    return true;
                }
            }
        }
        return false;
    }

    void doStartInput(InputConnection inputConnection, EditorInfo editorInfo, boolean bl) {
        if (!bl) {
            this.doFinishInput();
        }
        this.mInputStarted = true;
        this.mStartedInputConnection = inputConnection;
        this.mInputEditorInfo = editorInfo;
        this.initialize();
        this.onStartInput(editorInfo, bl);
        boolean bl2 = this.mDecorViewVisible;
        if (bl2) {
            if (this.mShowInputRequested) {
                this.mInputViewStarted = true;
                this.onStartInputView(this.mInputEditorInfo, bl);
                this.startExtractingText(true);
            } else if (this.mCandidatesVisibility == 0) {
                this.mCandidatesViewStarted = true;
                this.onStartCandidatesView(this.mInputEditorInfo, bl);
            }
        } else if (this.mCanPreRender && this.mInputEditorInfo != null && this.mStartedInputConnection != null) {
            if (this.mInShowWindow) {
                Log.w(TAG, "Re-entrance in to showWindow");
                return;
            }
            this.mDecorViewWasVisible = bl2;
            this.mInShowWindow = true;
            this.startViews(this.prepareWindow(true));
            this.mIsPreRendered = true;
            this.onPreRenderedWindowVisibilityChanged(false);
            this.mWindow.show();
            this.maybeNotifyPreRendered();
            this.mDecorViewWasVisible = true;
            this.mInShowWindow = false;
        } else {
            this.mIsPreRendered = false;
        }
    }

    @Override
    protected void dump(FileDescriptor object, PrintWriter appendable, String[] arrstring) {
        object = new PrintWriterPrinter((PrintWriter)appendable);
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("Input method service state for ");
        ((StringBuilder)appendable).append(this);
        ((StringBuilder)appendable).append(":");
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mViewsCreated=");
        ((StringBuilder)appendable).append(this.mViewsCreated);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mDecorViewVisible=");
        ((StringBuilder)appendable).append(this.mDecorViewVisible);
        ((StringBuilder)appendable).append(" mDecorViewWasVisible=");
        ((StringBuilder)appendable).append(this.mDecorViewWasVisible);
        ((StringBuilder)appendable).append(" mWindowVisible=");
        ((StringBuilder)appendable).append(this.mWindowVisible);
        ((StringBuilder)appendable).append(" mInShowWindow=");
        ((StringBuilder)appendable).append(this.mInShowWindow);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  Configuration=");
        ((StringBuilder)appendable).append(this.getResources().getConfiguration());
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mToken=");
        ((StringBuilder)appendable).append(this.mToken);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mInputBinding=");
        ((StringBuilder)appendable).append(this.mInputBinding);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mInputConnection=");
        ((StringBuilder)appendable).append(this.mInputConnection);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mStartedInputConnection=");
        ((StringBuilder)appendable).append(this.mStartedInputConnection);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mInputStarted=");
        ((StringBuilder)appendable).append(this.mInputStarted);
        ((StringBuilder)appendable).append(" mInputViewStarted=");
        ((StringBuilder)appendable).append(this.mInputViewStarted);
        ((StringBuilder)appendable).append(" mCandidatesViewStarted=");
        ((StringBuilder)appendable).append(this.mCandidatesViewStarted);
        object.println(((StringBuilder)appendable).toString());
        if (this.mInputEditorInfo != null) {
            object.println("  mInputEditorInfo:");
            this.mInputEditorInfo.dump((Printer)object, "    ");
        } else {
            object.println("  mInputEditorInfo: null");
        }
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mShowInputRequested=");
        ((StringBuilder)appendable).append(this.mShowInputRequested);
        ((StringBuilder)appendable).append(" mLastShowInputRequested=");
        ((StringBuilder)appendable).append(this.mLastShowInputRequested);
        ((StringBuilder)appendable).append(" mCanPreRender=");
        ((StringBuilder)appendable).append(this.mCanPreRender);
        ((StringBuilder)appendable).append(" mIsPreRendered=");
        ((StringBuilder)appendable).append(this.mIsPreRendered);
        ((StringBuilder)appendable).append(" mShowInputFlags=0x");
        ((StringBuilder)appendable).append(Integer.toHexString(this.mShowInputFlags));
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mCandidatesVisibility=");
        ((StringBuilder)appendable).append(this.mCandidatesVisibility);
        ((StringBuilder)appendable).append(" mFullscreenApplied=");
        ((StringBuilder)appendable).append(this.mFullscreenApplied);
        ((StringBuilder)appendable).append(" mIsFullscreen=");
        ((StringBuilder)appendable).append(this.mIsFullscreen);
        ((StringBuilder)appendable).append(" mExtractViewHidden=");
        ((StringBuilder)appendable).append(this.mExtractViewHidden);
        object.println(((StringBuilder)appendable).toString());
        if (this.mExtractedText != null) {
            object.println("  mExtractedText:");
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("    text=");
            ((StringBuilder)appendable).append(this.mExtractedText.text.length());
            ((StringBuilder)appendable).append(" chars startOffset=");
            ((StringBuilder)appendable).append(this.mExtractedText.startOffset);
            object.println(((StringBuilder)appendable).toString());
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("    selectionStart=");
            ((StringBuilder)appendable).append(this.mExtractedText.selectionStart);
            ((StringBuilder)appendable).append(" selectionEnd=");
            ((StringBuilder)appendable).append(this.mExtractedText.selectionEnd);
            ((StringBuilder)appendable).append(" flags=0x");
            ((StringBuilder)appendable).append(Integer.toHexString(this.mExtractedText.flags));
            object.println(((StringBuilder)appendable).toString());
        } else {
            object.println("  mExtractedText: null");
        }
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mExtractedToken=");
        ((StringBuilder)appendable).append(this.mExtractedToken);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  mIsInputViewShown=");
        ((StringBuilder)appendable).append(this.mIsInputViewShown);
        ((StringBuilder)appendable).append(" mStatusIcon=");
        ((StringBuilder)appendable).append(this.mStatusIcon);
        object.println(((StringBuilder)appendable).toString());
        object.println("Last computed insets:");
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("  contentTopInsets=");
        ((StringBuilder)appendable).append(this.mTmpInsets.contentTopInsets);
        ((StringBuilder)appendable).append(" visibleTopInsets=");
        ((StringBuilder)appendable).append(this.mTmpInsets.visibleTopInsets);
        ((StringBuilder)appendable).append(" touchableInsets=");
        ((StringBuilder)appendable).append(this.mTmpInsets.touchableInsets);
        ((StringBuilder)appendable).append(" touchableRegion=");
        ((StringBuilder)appendable).append(this.mTmpInsets.touchableRegion);
        object.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append(" mSettingsObserver=");
        ((StringBuilder)appendable).append(this.mSettingsObserver);
        object.println(((StringBuilder)appendable).toString());
    }

    @Deprecated
    public boolean enableHardwareAcceleration() {
        if (this.mWindow == null) {
            return ActivityManager.isHighEndGfx();
        }
        throw new IllegalStateException("Must be called before onCreate()");
    }

    @Override
    public final void exposeContent(InputContentInfo inputContentInfo, InputConnection inputConnection) {
        if (inputConnection == null) {
            return;
        }
        if (this.getCurrentInputConnection() != inputConnection) {
            return;
        }
        this.exposeContentInternal(inputContentInfo, this.getCurrentInputEditorInfo());
    }

    public int getBackDisposition() {
        return this.mBackDisposition;
    }

    public int getCandidatesHiddenVisibility() {
        int n = this.isExtractViewShown() ? 8 : 4;
        return n;
    }

    public InputBinding getCurrentInputBinding() {
        return this.mInputBinding;
    }

    public InputConnection getCurrentInputConnection() {
        InputConnection inputConnection = this.mStartedInputConnection;
        if (inputConnection != null) {
            return inputConnection;
        }
        return this.mInputConnection;
    }

    public EditorInfo getCurrentInputEditorInfo() {
        return this.mInputEditorInfo;
    }

    public boolean getCurrentInputStarted() {
        return this.mInputStarted;
    }

    @Deprecated
    public int getInputMethodWindowRecommendedHeight() {
        Log.w(TAG, "getInputMethodWindowRecommendedHeight() is deprecated and now always returns 0. Do not use this method.");
        return 0;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public int getMaxWidth() {
        return ((WindowManager)this.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public CharSequence getTextForImeAction(int n) {
        switch (n & 255) {
            default: {
                return this.getText(17040121);
            }
            case 7: {
                return this.getText(17040125);
            }
            case 6: {
                return this.getText(17040122);
            }
            case 5: {
                return this.getText(17040124);
            }
            case 4: {
                return this.getText(17040127);
            }
            case 3: {
                return this.getText(17040126);
            }
            case 2: {
                return this.getText(17040123);
            }
            case 1: 
        }
        return null;
    }

    public Dialog getWindow() {
        return this.mWindow;
    }

    public void hideStatusIcon() {
        this.mStatusIcon = 0;
        this.mPrivOps.updateStatusIcon(null, 0);
    }

    public void hideWindow() {
        this.mIsPreRendered = false;
        this.mWindowVisible = false;
        this.finishViews(false);
        if (this.mDecorViewVisible) {
            this.mWindow.hide();
            this.mDecorViewVisible = false;
            this.onWindowHidden();
            this.mDecorViewWasVisible = false;
        }
        this.updateFullscreenMode();
    }

    void initViews() {
        this.mInitialized = false;
        this.mViewsCreated = false;
        this.mShowInputRequested = false;
        this.mShowInputFlags = 0;
        this.mThemeAttrs = this.obtainStyledAttributes(R.styleable.InputMethodService);
        this.mRootView = this.mInflater.inflate(17367166, null);
        this.mWindow.setContentView(this.mRootView);
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
        if (Settings.Global.getInt(this.getContentResolver(), "fancy_ime_animations", 0) != 0) {
            this.mWindow.getWindow().setWindowAnimations(16974587);
        }
        this.mFullscreenArea = (ViewGroup)this.mRootView.findViewById(16908960);
        this.mExtractViewHidden = false;
        this.mExtractFrame = (FrameLayout)this.mRootView.findViewById(16908316);
        this.mExtractView = null;
        this.mExtractEditText = null;
        this.mExtractAccessories = null;
        this.mExtractAction = null;
        this.mFullscreenApplied = false;
        this.mCandidatesFrame = (FrameLayout)this.mRootView.findViewById(16908317);
        this.mInputFrame = (FrameLayout)this.mRootView.findViewById(16908318);
        this.mInputView = null;
        this.mIsInputViewShown = false;
        this.mExtractFrame.setVisibility(8);
        this.mCandidatesVisibility = this.getCandidatesHiddenVisibility();
        this.mCandidatesFrame.setVisibility(this.mCandidatesVisibility);
        this.mInputFrame.setVisibility(8);
    }

    void initialize() {
        if (!this.mInitialized) {
            this.mInitialized = true;
            this.onInitializeInterface();
        }
    }

    public boolean isExtractViewShown() {
        boolean bl = this.mIsFullscreen && !this.mExtractViewHidden;
        return bl;
    }

    public boolean isFullscreenMode() {
        return this.mIsFullscreen;
    }

    public boolean isInputViewShown() {
        boolean bl = this.mCanPreRender ? this.mWindowVisible : this.mIsInputViewShown && this.mDecorViewVisible;
        return bl;
    }

    public boolean isShowInputRequested() {
        return this.mShowInputRequested;
    }

    public /* synthetic */ void lambda$new$0$InputMethodService(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        if (this.isExtractViewShown()) {
            int n;
            View view = this.getWindow().getWindow().getDecorView();
            Rect rect = internalInsetsInfo.contentInsets;
            Rect rect2 = internalInsetsInfo.visibleInsets;
            rect2.top = n = view.getHeight();
            rect.top = n;
            internalInsetsInfo.touchableRegion.setEmpty();
            internalInsetsInfo.setTouchableInsets(0);
        } else {
            this.onComputeInsets(this.mTmpInsets);
            internalInsetsInfo.contentInsets.top = this.mTmpInsets.contentTopInsets;
            internalInsetsInfo.visibleInsets.top = this.mTmpInsets.visibleTopInsets;
            internalInsetsInfo.touchableRegion.set(this.mTmpInsets.touchableRegion);
            internalInsetsInfo.setTouchableInsets(this.mTmpInsets.touchableInsets);
        }
    }

    public /* synthetic */ void lambda$new$1$InputMethodService(View object) {
        object = this.getCurrentInputEditorInfo();
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (object != null && inputConnection != null) {
            if (((EditorInfo)object).actionId != 0) {
                inputConnection.performEditorAction(((EditorInfo)object).actionId);
            } else if ((((EditorInfo)object).imeOptions & 255) != 1) {
                inputConnection.performEditorAction(((EditorInfo)object).imeOptions & 255);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void notifyUserActionIfNecessary() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mNotifyUserActionSent) {
                return;
            }
            this.mPrivOps.notifyUserAction();
            this.mNotifyUserActionSent = true;
            return;
        }
    }

    public void onAppPrivateCommand(String string2, Bundle bundle) {
    }

    public void onBindInput() {
    }

    public void onComputeInsets(Insets insets) {
        int[] arrn = this.mTmpLocation;
        if (this.mInputFrame.getVisibility() == 0) {
            this.mInputFrame.getLocationInWindow(arrn);
        } else {
            arrn[1] = this.getWindow().getWindow().getDecorView().getHeight();
        }
        insets.contentTopInsets = this.isFullscreenMode() ? this.getWindow().getWindow().getDecorView().getHeight() : arrn[1];
        if (this.mCandidatesFrame.getVisibility() == 0) {
            this.mCandidatesFrame.getLocationInWindow(arrn);
        }
        insets.visibleTopInsets = arrn[1];
        insets.touchableInsets = 2;
        insets.touchableRegion.setEmpty();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.resetStateForNewConfiguration();
    }

    public void onConfigureWindow(Window window, boolean bl, boolean bl2) {
        int n = this.mWindow.getWindow().getAttributes().height;
        n = bl ? -1 : -2;
        bl = this.mIsInputViewShown;
        this.mWindow.getWindow().setLayout(-1, n);
    }

    @Override
    public void onCreate() {
        this.mTheme = Resources.selectSystemTheme(this.mTheme, this.getApplicationInfo().targetSdkVersion, 16973908, 16973951, 16974142, 16974142);
        super.setTheme(this.mTheme);
        super.onCreate();
        this.mImm = (InputMethodManager)this.getSystemService("input_method");
        this.mSettingsObserver = SettingsObserver.createAndRegister(this);
        this.mInflater = (LayoutInflater)this.getSystemService("layout_inflater");
        this.mWindow = new SoftInputWindow(this, "InputMethod", this.mTheme, null, null, this.mDispatcherState, 2011, 80, false);
        this.mWindow.getWindow().setFlags(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.initViews();
        this.mWindow.getWindow().setLayout(-1, -2);
    }

    public View onCreateCandidatesView() {
        return null;
    }

    public View onCreateExtractTextView() {
        return this.mInflater.inflate(17367167, null);
    }

    @Override
    public AbstractInputMethodService.AbstractInputMethodImpl onCreateInputMethodInterface() {
        return new InputMethodImpl();
    }

    @Override
    public AbstractInputMethodService.AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface() {
        return new InputMethodSessionImpl();
    }

    public View onCreateInputView() {
        return null;
    }

    protected void onCurrentInputMethodSubtypeChanged(InputMethodSubtype inputMethodSubtype) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
        this.doFinishInput();
        this.mWindow.dismissForDestroyIfNecessary();
        Object object = this.mSettingsObserver;
        if (object != null) {
            ((SettingsObserver)object).unregister();
            this.mSettingsObserver = null;
        }
        if ((object = this.mToken) != null) {
            InputMethodPrivilegedOperationsRegistry.remove((IBinder)object);
        }
    }

    public void onDisplayCompletions(CompletionInfo[] arrcompletionInfo) {
    }

    public boolean onEvaluateFullscreenMode() {
        if (this.getResources().getConfiguration().orientation != 2) {
            return false;
        }
        EditorInfo editorInfo = this.mInputEditorInfo;
        return editorInfo == null || (editorInfo.imeOptions & 33554432) == 0;
    }

    public boolean onEvaluateInputViewShown() {
        Object object = this.mSettingsObserver;
        boolean bl = false;
        if (object == null) {
            Log.w(TAG, "onEvaluateInputViewShown: mSettingsObserver must not be null here.");
            return false;
        }
        if (((SettingsObserver)object).shouldShowImeWithHardKeyboard()) {
            return true;
        }
        object = this.getResources().getConfiguration();
        if (((Configuration)object).keyboard == 1 || ((Configuration)object).hardKeyboardHidden == 2) {
            bl = true;
        }
        return bl;
    }

    public boolean onExtractTextContextMenuItem(int n) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.performContextMenuAction(n);
        }
        return true;
    }

    public void onExtractedCursorMovement(int n, int n2) {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && n2 != 0) {
            if (extractEditText.hasVerticalScrollBar()) {
                this.setCandidatesViewShown(false);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void onExtractedDeleteText(int n, int n2) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.finishComposingText();
            inputConnection.setSelection(n, n);
            inputConnection.deleteSurroundingText(0, n2 - n);
        }
    }

    @UnsupportedAppUsage
    public void onExtractedReplaceText(int n, int n2, CharSequence charSequence) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.setComposingRegion(n, n2);
            inputConnection.commitText(charSequence, 1);
        }
    }

    public void onExtractedSelectionChanged(int n, int n2) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.setSelection(n, n2);
        }
    }

    @UnsupportedAppUsage
    public void onExtractedSetSpan(Object object, int n, int n2, int n3) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            if (!inputConnection.setSelection(n, n2)) {
                return;
            }
            CharSequence charSequence = inputConnection.getSelectedText(1);
            if (charSequence instanceof Spannable) {
                ((Spannable)charSequence).setSpan(object, 0, charSequence.length(), n3);
                inputConnection.setComposingRegion(n, n2);
                inputConnection.commitText(charSequence, 1);
            }
        }
    }

    public void onExtractedTextClicked() {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText == null) {
            return;
        }
        if (extractEditText.hasVerticalScrollBar()) {
            this.setCandidatesViewShown(false);
        }
    }

    public void onExtractingInputChanged(EditorInfo editorInfo) {
        if (editorInfo.inputType == 0) {
            this.requestHideSelf(2);
        }
    }

    public void onFinishCandidatesView(boolean bl) {
        InputConnection inputConnection;
        if (!bl && (inputConnection = this.getCurrentInputConnection()) != null) {
            inputConnection.finishComposingText();
        }
    }

    public void onFinishInput() {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection != null) {
            inputConnection.finishComposingText();
        }
    }

    public void onFinishInputView(boolean bl) {
        InputConnection inputConnection;
        if (!bl && (inputConnection = this.getCurrentInputConnection()) != null) {
            inputConnection.finishComposingText();
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onInitializeInterface() {
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            ExtractEditText extractEditText = this.getExtractEditTextIfVisible();
            if (extractEditText != null && extractEditText.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (this.handleBack(false)) {
                keyEvent.startTracking();
                return true;
            }
            return false;
        }
        return this.doMovementKey(n, keyEvent, -1);
    }

    @Override
    public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return this.doMovementKey(n, keyEvent, n2);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            ExtractEditText extractEditText = this.getExtractEditTextIfVisible();
            if (extractEditText != null && extractEditText.handleBackInTextActionModeIfNeeded(keyEvent)) {
                return true;
            }
            if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                return this.handleBack(true);
            }
        }
        return this.doMovementKey(n, keyEvent, -2);
    }

    public boolean onShowInputRequested(int n, boolean bl) {
        if (!this.onEvaluateInputViewShown()) {
            return false;
        }
        if ((n & 1) == 0) {
            if (!bl && this.onEvaluateFullscreenMode()) {
                return false;
            }
            if (!this.mSettingsObserver.shouldShowImeWithHardKeyboard() && this.getResources().getConfiguration().keyboard != 1) {
                return false;
            }
        }
        return true;
    }

    public void onStartCandidatesView(EditorInfo editorInfo, boolean bl) {
    }

    public void onStartInput(EditorInfo editorInfo, boolean bl) {
    }

    public void onStartInputView(EditorInfo editorInfo, boolean bl) {
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onUnbindInput() {
    }

    @Deprecated
    public void onUpdateCursor(Rect rect) {
    }

    public void onUpdateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
    }

    public void onUpdateExtractedText(int n, ExtractedText extractedText) {
        ExtractEditText extractEditText;
        if (this.mExtractedToken != n) {
            return;
        }
        if (extractedText != null && (extractEditText = this.mExtractEditText) != null) {
            this.mExtractedText = extractedText;
            extractEditText.setExtractedText(extractedText);
        }
    }

    public void onUpdateExtractingViews(EditorInfo object) {
        boolean bl;
        if (!this.isExtractViewShown()) {
            return;
        }
        if (this.mExtractAccessories == null) {
            return;
        }
        Object object2 = ((EditorInfo)object).actionLabel;
        boolean bl2 = bl = true;
        if (object2 == null) {
            bl2 = (((EditorInfo)object).imeOptions & 255) != 1 && (((EditorInfo)object).imeOptions & 536870912) == 0 && ((EditorInfo)object).inputType != 0 ? bl : false;
        }
        if (bl2) {
            this.mExtractAccessories.setVisibility(0);
            object2 = this.mExtractAction;
            if (object2 != null) {
                if (object2 instanceof ImageButton) {
                    ((ImageButton)object2).setImageResource(this.getIconForImeAction(((EditorInfo)object).imeOptions));
                    if (((EditorInfo)object).actionLabel != null) {
                        this.mExtractAction.setContentDescription(((EditorInfo)object).actionLabel);
                    } else {
                        this.mExtractAction.setContentDescription(this.getTextForImeAction(((EditorInfo)object).imeOptions));
                    }
                } else if (((EditorInfo)object).actionLabel != null) {
                    ((TextView)this.mExtractAction).setText(((EditorInfo)object).actionLabel);
                } else {
                    ((TextView)this.mExtractAction).setText(this.getTextForImeAction(((EditorInfo)object).imeOptions));
                }
                this.mExtractAction.setOnClickListener(this.mActionClickListener);
            }
        } else {
            this.mExtractAccessories.setVisibility(8);
            object = this.mExtractAction;
            if (object != null) {
                ((View)object).setOnClickListener(null);
            }
        }
    }

    public void onUpdateExtractingVisibility(EditorInfo editorInfo) {
        if (editorInfo.inputType != 0 && (editorInfo.imeOptions & 268435456) == 0) {
            this.setExtractViewShown(true);
            return;
        }
        this.setExtractViewShown(false);
    }

    public void onUpdateSelection(int n, int n2, int n3, int n4, int n5, int n6) {
        ExtractedText extractedText;
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && this.isFullscreenMode() && (extractedText = this.mExtractedText) != null) {
            n = extractedText.startOffset;
            extractEditText.startInternalChanges();
            n2 = n3 - n;
            n4 -= n;
            n3 = extractEditText.getText().length();
            if (n2 < 0) {
                n = 0;
            } else {
                n = n2;
                if (n2 > n3) {
                    n = n3;
                }
            }
            if (n4 < 0) {
                n2 = 0;
            } else {
                n2 = n4;
                if (n4 > n3) {
                    n2 = n3;
                }
            }
            extractEditText.setSelection(n, n2);
            extractEditText.finishInternalChanges();
        }
    }

    @Deprecated
    public void onViewClicked(boolean bl) {
    }

    public void onWindowHidden() {
    }

    public void onWindowShown() {
    }

    void reportExtractedMovement(int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        switch (n) {
            default: {
                n = n3;
                n2 = n4;
                break;
            }
            case 22: {
                n = n2;
                n2 = n4;
                break;
            }
            case 21: {
                n = -n2;
                n2 = n4;
                break;
            }
            case 20: {
                n = n3;
                break;
            }
            case 19: {
                n2 = -n2;
                n = n3;
            }
        }
        this.onExtractedCursorMovement(n, n2);
    }

    public void requestHideSelf(int n) {
        this.mPrivOps.hideMySoftInput(n);
    }

    public final void requestShowSelf(int n) {
        this.mPrivOps.showMySoftInput(n);
    }

    public boolean sendDefaultEditorAction(boolean bl) {
        EditorInfo editorInfo = this.getCurrentInputEditorInfo();
        if (!(editorInfo == null || bl && (editorInfo.imeOptions & 1073741824) != 0 || (editorInfo.imeOptions & 255) == 1)) {
            InputConnection inputConnection = this.getCurrentInputConnection();
            if (inputConnection != null) {
                inputConnection.performEditorAction(editorInfo.imeOptions & 255);
            }
            return true;
        }
        return false;
    }

    public void sendDownUpKeyEvents(int n) {
        InputConnection inputConnection = this.getCurrentInputConnection();
        if (inputConnection == null) {
            return;
        }
        long l = SystemClock.uptimeMillis();
        inputConnection.sendKeyEvent(new KeyEvent(l, l, 0, n, 0, 0, -1, 0, 6));
        inputConnection.sendKeyEvent(new KeyEvent(l, SystemClock.uptimeMillis(), 1, n, 0, 0, -1, 0, 6));
    }

    public void sendKeyChar(char c) {
        if (c != '\n') {
            if (c >= '0' && c <= '9') {
                this.sendDownUpKeyEvents(c - 48 + 7);
            } else {
                InputConnection inputConnection = this.getCurrentInputConnection();
                if (inputConnection != null) {
                    inputConnection.commitText(String.valueOf(c), 1);
                }
            }
        } else if (!this.sendDefaultEditorAction(true)) {
            this.sendDownUpKeyEvents(66);
        }
    }

    public void setBackDisposition(int n) {
        if (n == this.mBackDisposition) {
            return;
        }
        if (n <= 3 && n >= 0) {
            this.mBackDisposition = n;
            this.setImeWindowStatus(this.mapToImeWindowStatus(), this.mBackDisposition);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid back disposition value (");
        stringBuilder.append(n);
        stringBuilder.append(") specified.");
        Log.e(TAG, stringBuilder.toString());
    }

    public void setCandidatesView(View view) {
        this.mCandidatesFrame.removeAllViews();
        this.mCandidatesFrame.addView(view, new FrameLayout.LayoutParams(-1, -2));
    }

    public void setCandidatesViewShown(boolean bl) {
        this.updateCandidatesVisibility(bl);
        if (!this.mShowInputRequested && this.mDecorViewVisible != bl) {
            if (bl) {
                this.showWindow(false);
            } else {
                this.doHideWindow();
            }
        }
    }

    public void setExtractView(View view) {
        this.mExtractFrame.removeAllViews();
        this.mExtractFrame.addView(view, new FrameLayout.LayoutParams(-1, -1));
        this.mExtractView = view;
        if (view != null) {
            this.mExtractEditText = (ExtractEditText)view.findViewById(16908325);
            this.mExtractEditText.setIME(this);
            this.mExtractAction = view.findViewById(16909021);
            if (this.mExtractAction != null) {
                this.mExtractAccessories = (ViewGroup)view.findViewById(16909020);
            }
            this.startExtractingText(false);
        } else {
            this.mExtractEditText = null;
            this.mExtractAccessories = null;
            this.mExtractAction = null;
        }
    }

    public void setExtractViewShown(boolean bl) {
        if (this.mExtractViewHidden == bl) {
            this.mExtractViewHidden = bl ^ true;
            this.updateExtractFrameVisibility();
        }
    }

    public void setInputView(View view) {
        this.mInputFrame.removeAllViews();
        this.mInputFrame.addView(view, new FrameLayout.LayoutParams(-1, -2));
        this.mInputView = view;
    }

    @Override
    public void setTheme(int n) {
        if (this.mWindow == null) {
            this.mTheme = n;
            return;
        }
        throw new IllegalStateException("Must be called before onCreate()");
    }

    public final boolean shouldOfferSwitchingToNextInputMethod() {
        return this.mPrivOps.shouldOfferSwitchingToNextInputMethod();
    }

    public void showStatusIcon(int n) {
        this.mStatusIcon = n;
        this.mPrivOps.updateStatusIcon(this.getPackageName(), n);
    }

    public void showWindow(boolean bl) {
        if (this.mInShowWindow) {
            Log.w(TAG, "Re-entrance in to showWindow");
            return;
        }
        this.mDecorViewWasVisible = this.mDecorViewVisible;
        this.mInShowWindow = true;
        int n = this.mIsPreRendered && !this.mWindowVisible ? 1 : 0;
        int n2 = this.mDecorViewVisible;
        n = this.isInputViewShown() ? (n != 0 ? 4 : 2) : 0;
        int n3 = n2 | n;
        this.startViews(this.prepareWindow(bl));
        n = this.mapToImeWindowStatus();
        if (n3 != n) {
            this.setImeWindowStatus(n, this.mBackDisposition);
        }
        this.onWindowShown();
        this.mIsPreRendered = this.mCanPreRender;
        if (this.mIsPreRendered) {
            this.onPreRenderedWindowVisibilityChanged(true);
        } else {
            this.mWindowVisible = true;
        }
        if ((n3 & 1) == 0) {
            this.mWindow.show();
        }
        this.maybeNotifyPreRendered();
        this.mDecorViewWasVisible = true;
        this.mInShowWindow = false;
    }

    void startExtractingText(boolean bl) {
        ExtractEditText extractEditText = this.mExtractEditText;
        if (extractEditText != null && this.getCurrentInputStarted() && this.isFullscreenMode()) {
            int n;
            ++this.mExtractedToken;
            Object object = new ExtractedTextRequest();
            ((ExtractedTextRequest)object).token = this.mExtractedToken;
            ((ExtractedTextRequest)object).flags = 1;
            ((ExtractedTextRequest)object).hintMaxLines = 10;
            ((ExtractedTextRequest)object).hintMaxChars = 10000;
            InputConnection inputConnection = this.getCurrentInputConnection();
            object = inputConnection == null ? null : inputConnection.getExtractedText((ExtractedTextRequest)object, 1);
            this.mExtractedText = object;
            if (this.mExtractedText == null || inputConnection == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected null in startExtractingText : mExtractedText = ");
                ((StringBuilder)object).append(this.mExtractedText);
                ((StringBuilder)object).append(", input connection = ");
                ((StringBuilder)object).append(inputConnection);
                Log.e(TAG, ((StringBuilder)object).toString());
            }
            object = this.getCurrentInputEditorInfo();
            extractEditText.startInternalChanges();
            this.onUpdateExtractingVisibility((EditorInfo)object);
            this.onUpdateExtractingViews((EditorInfo)object);
            int n2 = n = ((EditorInfo)object).inputType;
            if ((n & 15) == 1) {
                n2 = n;
                if ((262144 & n) != 0) {
                    n2 = n | 131072;
                }
            }
            try {
                extractEditText.setInputType(n2);
                extractEditText.setHint(((EditorInfo)object).hintText);
                if (this.mExtractedText != null) {
                    extractEditText.setEnabled(true);
                    extractEditText.setExtractedText(this.mExtractedText);
                } else {
                    extractEditText.setEnabled(false);
                    extractEditText.setText("");
                }
                if (bl) {
                    this.onExtractingInputChanged((EditorInfo)object);
                }
            }
            finally {
                extractEditText.finishInternalChanges();
            }
        }
    }

    public void switchInputMethod(String string2) {
        this.mPrivOps.setInputMethod(string2);
    }

    public final void switchInputMethod(String string2, InputMethodSubtype inputMethodSubtype) {
        this.mPrivOps.setInputMethodAndSubtype(string2, inputMethodSubtype);
    }

    public final boolean switchToNextInputMethod(boolean bl) {
        return this.mPrivOps.switchToNextInputMethod(bl);
    }

    public final boolean switchToPreviousInputMethod() {
        return this.mPrivOps.switchToPreviousInputMethod();
    }

    void updateCandidatesVisibility(boolean bl) {
        int n = bl ? 0 : this.getCandidatesHiddenVisibility();
        if (this.mCandidatesVisibility != n) {
            this.mCandidatesFrame.setVisibility(n);
            this.mCandidatesVisibility = n;
        }
    }

    void updateExtractFrameVisibility() {
        int n;
        if (this.isFullscreenMode()) {
            n = this.mExtractViewHidden ? 4 : 0;
            this.mExtractFrame.setVisibility(n);
        } else {
            n = 0;
            this.mExtractFrame.setVisibility(8);
        }
        int n2 = this.mCandidatesVisibility;
        int n3 = 1;
        boolean bl = n2 == 0;
        this.updateCandidatesVisibility(bl);
        if (this.mDecorViewWasVisible && this.mFullscreenArea.getVisibility() != n) {
            TypedArray typedArray = this.mThemeAttrs;
            if (n != 0) {
                n3 = 2;
            }
            n3 = typedArray.getResourceId(n3, 0);
            if (n3 != 0) {
                this.mFullscreenArea.startAnimation(AnimationUtils.loadAnimation(this, n3));
            }
        }
        this.mFullscreenArea.setVisibility(n);
    }

    public void updateFullscreenMode() {
        boolean bl = this.mShowInputRequested && this.onEvaluateFullscreenMode();
        boolean bl2 = this.mLastShowInputRequested != this.mShowInputRequested;
        if (this.mIsFullscreen != bl || !this.mFullscreenApplied) {
            bl2 = true;
            this.mIsFullscreen = bl;
            this.reportFullscreenMode();
            this.mFullscreenApplied = true;
            this.initialize();
            Object object = (LinearLayout.LayoutParams)this.mFullscreenArea.getLayoutParams();
            if (bl) {
                this.mFullscreenArea.setBackgroundDrawable(this.mThemeAttrs.getDrawable(0));
                ((LinearLayout.LayoutParams)object).height = 0;
                ((LinearLayout.LayoutParams)object).weight = 1.0f;
            } else {
                this.mFullscreenArea.setBackgroundDrawable(null);
                ((LinearLayout.LayoutParams)object).height = -2;
                ((LinearLayout.LayoutParams)object).weight = 0.0f;
            }
            ((ViewGroup)this.mFullscreenArea.getParent()).updateViewLayout(this.mFullscreenArea, (ViewGroup.LayoutParams)object);
            if (bl) {
                if (this.mExtractView == null && (object = this.onCreateExtractTextView()) != null) {
                    this.setExtractView((View)object);
                }
                this.startExtractingText(false);
            }
            this.updateExtractFrameVisibility();
        }
        if (bl2) {
            this.onConfigureWindow(this.mWindow.getWindow(), bl, true ^ this.mShowInputRequested);
            this.mLastShowInputRequested = this.mShowInputRequested;
        }
    }

    public void updateInputViewShown() {
        boolean bl = this.mShowInputRequested;
        int n = 0;
        if (this.mIsInputViewShown != (bl = bl && this.onEvaluateInputViewShown()) && this.mDecorViewVisible) {
            this.mIsInputViewShown = bl;
            View view = this.mInputFrame;
            if (!bl) {
                n = 8;
            }
            view.setVisibility(n);
            if (this.mInputView == null) {
                this.initialize();
                view = this.onCreateInputView();
                if (view != null) {
                    this.setInputView(view);
                }
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BackDispositionMode {
    }

    public class InputMethodImpl
    extends AbstractInputMethodService.AbstractInputMethodImpl {
        public InputMethodImpl() {
            super((AbstractInputMethodService)InputMethodService.this);
        }

        @Override
        public void attachToken(IBinder iBinder) {
            if (InputMethodService.this.mToken == null) {
                InputMethodService inputMethodService = InputMethodService.this;
                inputMethodService.mToken = iBinder;
                inputMethodService.mWindow.setToken(iBinder);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attachToken() must be called at most once. token=");
            stringBuilder.append(iBinder);
            throw new IllegalStateException(stringBuilder.toString());
        }

        @Override
        public void bindInput(InputBinding inputBinding) {
            InputMethodService inputMethodService = InputMethodService.this;
            inputMethodService.mInputBinding = inputBinding;
            inputMethodService.mInputConnection = inputBinding.getConnection();
            InputMethodService.this.reportFullscreenMode();
            InputMethodService.this.initialize();
            InputMethodService.this.onBindInput();
        }

        @Override
        public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
            InputMethodService.this.dispatchOnCurrentInputMethodSubtypeChanged(inputMethodSubtype);
        }

        @Override
        public final void dispatchStartInputWithToken(InputConnection inputConnection, EditorInfo editorInfo, boolean bl, IBinder iBinder, boolean bl2) {
            InputMethodService.this.mPrivOps.reportStartInput(iBinder);
            InputMethodService.this.mCanPreRender = bl2;
            if (bl) {
                this.restartInput(inputConnection, editorInfo);
            } else {
                this.startInput(inputConnection, editorInfo);
            }
        }

        @Override
        public void hideSoftInput(int n, ResultReceiver resultReceiver) {
            boolean bl = InputMethodService.this.mIsPreRendered;
            int n2 = 1;
            bl = bl ? InputMethodService.this.mDecorViewVisible && InputMethodService.this.mWindowVisible : InputMethodService.this.isInputViewShown();
            if (InputMethodService.this.mIsPreRendered) {
                InputMethodService inputMethodService = InputMethodService.this;
                inputMethodService.setImeWindowStatus(5, inputMethodService.mBackDisposition);
                InputMethodService.this.applyVisibilityInInsetsConsumer(false);
                InputMethodService.this.onPreRenderedWindowVisibilityChanged(false);
            } else {
                InputMethodService inputMethodService = InputMethodService.this;
                inputMethodService.mShowInputFlags = 0;
                inputMethodService.mShowInputRequested = false;
                inputMethodService.doHideWindow();
            }
            boolean bl2 = InputMethodService.this.mIsPreRendered ? InputMethodService.this.mDecorViewVisible && InputMethodService.this.mWindowVisible : InputMethodService.this.isInputViewShown();
            n = bl2 != bl ? 1 : 0;
            if (resultReceiver != null) {
                n = n != 0 ? 3 : (bl ? 0 : n2);
                resultReceiver.send(n, null);
            }
        }

        @Override
        public final void initializeInternal(IBinder iBinder, int n, IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations) {
            if (InputMethodPrivilegedOperationsRegistry.isRegistered(iBinder)) {
                Log.w(InputMethodService.TAG, "The token has already registered, ignore this initialization.");
                return;
            }
            InputMethodService.this.mPrivOps.set(iInputMethodPrivilegedOperations);
            InputMethodPrivilegedOperationsRegistry.put(iBinder, InputMethodService.this.mPrivOps);
            this.updateInputMethodDisplay(n);
            this.attachToken(iBinder);
        }

        @Override
        public void restartInput(InputConnection inputConnection, EditorInfo editorInfo) {
            InputMethodService.this.doStartInput(inputConnection, editorInfo, true);
        }

        @Override
        public void showSoftInput(int n, ResultReceiver resultReceiver) {
            boolean bl = InputMethodService.this.mIsPreRendered;
            int n2 = 0;
            bl = bl ? InputMethodService.this.mDecorViewVisible && InputMethodService.this.mWindowVisible : InputMethodService.this.isInputViewShown();
            if (InputMethodService.this.dispatchOnShowInputRequested(n, false)) {
                if (InputMethodService.this.mIsPreRendered) {
                    InputMethodService.this.applyVisibilityInInsetsConsumer(true);
                    InputMethodService.this.onPreRenderedWindowVisibilityChanged(true);
                } else {
                    InputMethodService.this.showWindow(true);
                }
            }
            InputMethodService inputMethodService = InputMethodService.this;
            inputMethodService.setImeWindowStatus(inputMethodService.mapToImeWindowStatus(), InputMethodService.this.mBackDisposition);
            boolean bl2 = InputMethodService.this.mIsPreRendered ? InputMethodService.this.mDecorViewVisible && InputMethodService.this.mWindowVisible : InputMethodService.this.isInputViewShown();
            n = bl2 != bl ? 1 : 0;
            if (resultReceiver != null) {
                n = n != 0 ? 2 : (bl ? n2 : 1);
                resultReceiver.send(n, null);
            }
        }

        @Override
        public void startInput(InputConnection inputConnection, EditorInfo editorInfo) {
            InputMethodService.this.doStartInput(inputConnection, editorInfo, false);
        }

        @Override
        public void unbindInput() {
            InputMethodService.this.onUnbindInput();
            InputMethodService inputMethodService = InputMethodService.this;
            inputMethodService.mInputBinding = null;
            inputMethodService.mInputConnection = null;
        }

        @Override
        public void updateInputMethodDisplay(int n) {
            if (n != 0) {
                InputMethodService.this.updateDisplay(n);
            }
        }
    }

    public class InputMethodSessionImpl
    extends AbstractInputMethodService.AbstractInputMethodSessionImpl {
        public InputMethodSessionImpl() {
            super((AbstractInputMethodService)InputMethodService.this);
        }

        @Override
        public void appPrivateCommand(String string2, Bundle bundle) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onAppPrivateCommand(string2, bundle);
        }

        @Override
        public void displayCompletions(CompletionInfo[] arrcompletionInfo) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService inputMethodService = InputMethodService.this;
            inputMethodService.mCurCompletions = arrcompletionInfo;
            inputMethodService.onDisplayCompletions(arrcompletionInfo);
        }

        @Override
        public void finishInput() {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.doFinishInput();
        }

        @Override
        public final void notifyImeHidden() {
            InputMethodService.this.notifyImeHidden();
        }

        @Override
        public void toggleSoftInput(int n, int n2) {
            InputMethodService.this.onToggleSoftInput(n, n2);
        }

        @Override
        public void updateCursor(Rect rect) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onUpdateCursor(rect);
        }

        @Override
        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onUpdateCursorAnchorInfo(cursorAnchorInfo);
        }

        @Override
        public void updateExtractedText(int n, ExtractedText extractedText) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onUpdateExtractedText(n, extractedText);
        }

        @Override
        public void updateSelection(int n, int n2, int n3, int n4, int n5, int n6) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onUpdateSelection(n, n2, n3, n4, n5, n6);
        }

        @Override
        public void viewClicked(boolean bl) {
            if (!this.isEnabled()) {
                return;
            }
            InputMethodService.this.onViewClicked(bl);
        }
    }

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        public int contentTopInsets;
        public int touchableInsets;
        public final Region touchableRegion = new Region();
        public int visibleTopInsets;
    }

    private static final class SettingsObserver
    extends ContentObserver {
        private final InputMethodService mService;
        private int mShowImeWithHardKeyboard = 0;

        private SettingsObserver(InputMethodService inputMethodService) {
            super(new Handler(inputMethodService.getMainLooper()));
            this.mService = inputMethodService;
        }

        public static SettingsObserver createAndRegister(InputMethodService inputMethodService) {
            SettingsObserver settingsObserver = new SettingsObserver(inputMethodService);
            inputMethodService.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("show_ime_with_hard_keyboard"), false, settingsObserver);
            return settingsObserver;
        }

        @UnsupportedAppUsage
        private boolean shouldShowImeWithHardKeyboard() {
            int n;
            if (this.mShowImeWithHardKeyboard == 0) {
                n = Settings.Secure.getInt(this.mService.getContentResolver(), "show_ime_with_hard_keyboard", 0) != 0 ? 2 : 1;
                this.mShowImeWithHardKeyboard = n;
            }
            if ((n = this.mShowImeWithHardKeyboard) != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected mShowImeWithHardKeyboard=");
                    stringBuilder.append(this.mShowImeWithHardKeyboard);
                    Log.e(InputMethodService.TAG, stringBuilder.toString());
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public void onChange(boolean bl, Uri uri) {
            if (Settings.Secure.getUriFor("show_ime_with_hard_keyboard").equals(uri)) {
                int n = Settings.Secure.getInt(this.mService.getContentResolver(), "show_ime_with_hard_keyboard", 0) != 0 ? 2 : 1;
                this.mShowImeWithHardKeyboard = n;
                this.mService.resetStateForNewConfiguration();
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SettingsObserver{mShowImeWithHardKeyboard=");
            stringBuilder.append(this.mShowImeWithHardKeyboard);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        void unregister() {
            this.mService.getContentResolver().unregisterContentObserver(this);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface ShowImeWithHardKeyboardType {
            public static final int FALSE = 1;
            public static final int TRUE = 2;
            public static final int UNKNOWN = 0;
        }

    }

}

