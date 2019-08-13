/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.inputmethodservice.AbstractInputMethodService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionInspector;
import android.view.inputmethod.InputContentInfo;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputContextCallback;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class InputConnectionWrapper
implements InputConnection {
    private static final int MAX_WAIT_TIME_MILLIS = 2000;
    private final IInputContext mIInputContext;
    private final WeakReference<AbstractInputMethodService> mInputMethodService;
    private final AtomicBoolean mIsUnbindIssued;
    private final int mMissingMethods;

    public InputConnectionWrapper(WeakReference<AbstractInputMethodService> weakReference, IInputContext iInputContext, int n, AtomicBoolean atomicBoolean) {
        this.mInputMethodService = weakReference;
        this.mIInputContext = iInputContext;
        this.mMissingMethods = n;
        this.mIsUnbindIssued = atomicBoolean;
    }

    private boolean isMethodMissing(int n) {
        boolean bl = (this.mMissingMethods & n) == n;
        return bl;
    }

    private void notifyUserActionIfNecessary() {
        AbstractInputMethodService abstractInputMethodService = (AbstractInputMethodService)this.mInputMethodService.get();
        if (abstractInputMethodService == null) {
            return;
        }
        abstractInputMethodService.notifyUserActionIfNecessary();
    }

    @Override
    public boolean beginBatchEdit() {
        try {
            this.mIInputContext.beginBatchEdit();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean clearMetaKeyStates(int n) {
        try {
            this.mIInputContext.clearMetaKeyStates(n);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public void closeConnection() {
    }

    @Override
    public boolean commitCompletion(CompletionInfo completionInfo) {
        if (this.isMethodMissing(4)) {
            return false;
        }
        try {
            this.mIInputContext.commitCompletion(completionInfo);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean commitContent(InputContentInfo var1_1, int var2_3, Bundle var3_4) {
        block10 : {
            if (this.mIsUnbindIssued.get()) {
                return false;
            }
            var4_5 = false;
            if (this.isMethodMissing(128)) {
                return false;
            }
            if ((var2_3 & 1) == 0) ** GOTO lbl12
            try {
                var5_6 = (AbstractInputMethodService)this.mInputMethodService.get();
                if (var5_6 == null) {
                    return false;
                }
                var5_6.exposeContent(var1_1, this);
lbl12: // 2 sources:
                var5_6 = InputContextCallback.access$000();
                this.mIInputContext.commitContent(var1_1, var2_3, var3_4, var5_6.mSeq, (IInputContextCallback)var5_6);
                // MONITORENTER : var5_6
                var5_6.waitForResultLocked();
                if (!var5_6.mHaveValue) break block10;
                var4_5 = var5_6.mCommitContentResult;
            }
            catch (RemoteException var1_2) {
                return false;
            }
        }
        // MONITOREXIT : var5_6
        InputContextCallback.access$100((InputContextCallback)var5_6);
        return var4_5;
    }

    @Override
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        try {
            this.mIInputContext.commitCorrection(correctionInfo);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean commitText(CharSequence charSequence, int n) {
        try {
            this.mIInputContext.commitText(charSequence, n);
            this.notifyUserActionIfNecessary();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean deleteSurroundingText(int n, int n2) {
        try {
            this.mIInputContext.deleteSurroundingText(n, n2);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean deleteSurroundingTextInCodePoints(int n, int n2) {
        if (this.isMethodMissing(16)) {
            return false;
        }
        try {
            this.mIInputContext.deleteSurroundingTextInCodePoints(n, n2);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean endBatchEdit() {
        try {
            this.mIInputContext.endBatchEdit();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean finishComposingText() {
        try {
            this.mIInputContext.finishComposingText();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getCursorCapsMode(int n) {
        InputContextCallback inputContextCallback;
        block8 : {
            if (this.mIsUnbindIssued.get()) {
                return 0;
            }
            int n2 = 0;
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.getCursorCapsMode(n, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    n = n2;
                    if (!inputContextCallback.mHaveValue) break block8;
                    n = inputContextCallback.mCursorCapsMode;
                }
            }
            catch (RemoteException remoteException) {
                return 0;
            }
        }
        inputContextCallback.dispose();
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest parcelable, int n) {
        InputContextCallback inputContextCallback;
        block8 : {
            if (this.mIsUnbindIssued.get()) {
                return null;
            }
            Object var3_4 = null;
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.getExtractedText((ExtractedTextRequest)parcelable, n, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    parcelable = var3_4;
                    if (!inputContextCallback.mHaveValue) break block8;
                    parcelable = inputContextCallback.mExtractedText;
                }
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        inputContextCallback.dispose();
        return parcelable;
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CharSequence getSelectedText(int n) {
        InputContextCallback inputContextCallback;
        CharSequence charSequence;
        block9 : {
            if (this.mIsUnbindIssued.get()) {
                return null;
            }
            if (this.isMethodMissing(1)) {
                return null;
            }
            charSequence = null;
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.getSelectedText(n, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    if (!inputContextCallback.mHaveValue) break block9;
                    charSequence = inputContextCallback.mSelectedText;
                }
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        inputContextCallback.dispose();
        return charSequence;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CharSequence getTextAfterCursor(int n, int n2) {
        InputContextCallback inputContextCallback;
        CharSequence charSequence;
        block8 : {
            if (this.mIsUnbindIssued.get()) {
                return null;
            }
            charSequence = null;
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.getTextAfterCursor(n, n2, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    if (!inputContextCallback.mHaveValue) break block8;
                    charSequence = inputContextCallback.mTextAfterCursor;
                }
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        inputContextCallback.dispose();
        return charSequence;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CharSequence getTextBeforeCursor(int n, int n2) {
        InputContextCallback inputContextCallback;
        CharSequence charSequence;
        block8 : {
            if (this.mIsUnbindIssued.get()) {
                return null;
            }
            charSequence = null;
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.getTextBeforeCursor(n, n2, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    if (!inputContextCallback.mHaveValue) break block8;
                    charSequence = inputContextCallback.mTextBeforeCursor;
                }
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        inputContextCallback.dispose();
        return charSequence;
    }

    @Override
    public boolean performContextMenuAction(int n) {
        try {
            this.mIInputContext.performContextMenuAction(n);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean performEditorAction(int n) {
        try {
            this.mIInputContext.performEditorAction(n);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean performPrivateCommand(String string2, Bundle bundle) {
        try {
            this.mIInputContext.performPrivateCommand(string2, bundle);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean reportFullscreenMode(boolean bl) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean requestCursorUpdates(int n) {
        boolean bl;
        InputContextCallback inputContextCallback;
        block9 : {
            if (this.mIsUnbindIssued.get()) {
                return false;
            }
            bl = false;
            if (this.isMethodMissing(8)) {
                return false;
            }
            try {
                inputContextCallback = InputContextCallback.getInstance();
                this.mIInputContext.requestUpdateCursorAnchorInfo(n, inputContextCallback.mSeq, inputContextCallback);
                synchronized (inputContextCallback) {
                    inputContextCallback.waitForResultLocked();
                    if (!inputContextCallback.mHaveValue) break block9;
                    bl = inputContextCallback.mRequestUpdateCursorAnchorInfoResult;
                }
            }
            catch (RemoteException remoteException) {
                return false;
            }
        }
        inputContextCallback.dispose();
        return bl;
    }

    @Override
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        try {
            this.mIInputContext.sendKeyEvent(keyEvent);
            this.notifyUserActionIfNecessary();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean setComposingRegion(int n, int n2) {
        if (this.isMethodMissing(2)) {
            return false;
        }
        try {
            this.mIInputContext.setComposingRegion(n, n2);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean setComposingText(CharSequence charSequence, int n) {
        try {
            this.mIInputContext.setComposingText(charSequence, n);
            this.notifyUserActionIfNecessary();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean setSelection(int n, int n2) {
        try {
            this.mIInputContext.setSelection(n, n2);
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InputConnectionWrapper{idHash=#");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" mMissingMethods=");
        stringBuilder.append(InputConnectionInspector.getMissingMethodFlagsAsString(this.mMissingMethods));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static class InputContextCallback
    extends IInputContextCallback.Stub {
        private static final String TAG = "InputConnectionWrapper.ICC";
        private static InputContextCallback sInstance = new InputContextCallback();
        private static int sSequenceNumber = 1;
        public boolean mCommitContentResult;
        public int mCursorCapsMode;
        public ExtractedText mExtractedText;
        public boolean mHaveValue;
        public boolean mRequestUpdateCursorAnchorInfoResult;
        public CharSequence mSelectedText;
        public int mSeq;
        public CharSequence mTextAfterCursor;
        public CharSequence mTextBeforeCursor;

        InputContextCallback() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        private void dispose() {
            synchronized (InputContextCallback.class) {
                if (sInstance == null) {
                    this.mTextAfterCursor = null;
                    this.mTextBeforeCursor = null;
                    this.mExtractedText = null;
                    sInstance = this;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
        private static InputContextCallback getInstance() {
            synchronized (InputContextCallback.class) {
                InputContextCallback inputContextCallback;
                if (sInstance != null) {
                    inputContextCallback = sInstance;
                    sInstance = null;
                    inputContextCallback.mHaveValue = false;
                } else {
                    inputContextCallback = new InputContextCallback();
                }
                int n = sSequenceNumber;
                sSequenceNumber = n + 1;
                inputContextCallback.mSeq = n;
                return inputContextCallback;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setCommitContentResult(boolean bl, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mCommitContentResult = bl;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Got out-of-sequence callback ");
                    stringBuilder.append(n);
                    stringBuilder.append(" (expected ");
                    stringBuilder.append(this.mSeq);
                    stringBuilder.append(") in setCommitContentResult, ignoring.");
                    Log.i(TAG, stringBuilder.toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setCursorCapsMode(int n, int n2) {
            synchronized (this) {
                if (n2 == this.mSeq) {
                    this.mCursorCapsMode = n;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Got out-of-sequence callback ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" (expected ");
                    stringBuilder.append(this.mSeq);
                    stringBuilder.append(") in setCursorCapsMode, ignoring.");
                    Log.i(TAG, stringBuilder.toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setExtractedText(ExtractedText object, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mExtractedText = object;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Got out-of-sequence callback ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" (expected ");
                    ((StringBuilder)object).append(this.mSeq);
                    ((StringBuilder)object).append(") in setExtractedText, ignoring.");
                    Log.i(TAG, ((StringBuilder)object).toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setRequestUpdateCursorAnchorInfoResult(boolean bl, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mRequestUpdateCursorAnchorInfoResult = bl;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Got out-of-sequence callback ");
                    stringBuilder.append(n);
                    stringBuilder.append(" (expected ");
                    stringBuilder.append(this.mSeq);
                    stringBuilder.append(") in setCursorAnchorInfoRequestResult, ignoring.");
                    Log.i(TAG, stringBuilder.toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setSelectedText(CharSequence charSequence, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mSelectedText = charSequence;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Got out-of-sequence callback ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" (expected ");
                    ((StringBuilder)charSequence).append(this.mSeq);
                    ((StringBuilder)charSequence).append(") in setSelectedText, ignoring.");
                    Log.i(TAG, ((StringBuilder)charSequence).toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setTextAfterCursor(CharSequence charSequence, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mTextAfterCursor = charSequence;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Got out-of-sequence callback ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" (expected ");
                    ((StringBuilder)charSequence).append(this.mSeq);
                    ((StringBuilder)charSequence).append(") in setTextAfterCursor, ignoring.");
                    Log.i(TAG, ((StringBuilder)charSequence).toString());
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setTextBeforeCursor(CharSequence charSequence, int n) {
            synchronized (this) {
                if (n == this.mSeq) {
                    this.mTextBeforeCursor = charSequence;
                    this.mHaveValue = true;
                    this.notifyAll();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Got out-of-sequence callback ");
                    ((StringBuilder)charSequence).append(n);
                    ((StringBuilder)charSequence).append(" (expected ");
                    ((StringBuilder)charSequence).append(this.mSeq);
                    ((StringBuilder)charSequence).append(") in setTextBeforeCursor, ignoring.");
                    Log.i(TAG, ((StringBuilder)charSequence).toString());
                }
                return;
            }
        }

        void waitForResultLocked() {
            long l = SystemClock.uptimeMillis();
            while (!this.mHaveValue) {
                long l2 = 2000L + l - SystemClock.uptimeMillis();
                if (l2 <= 0L) {
                    Log.w(TAG, "Timed out waiting on IInputContextCallback");
                    return;
                }
                try {
                    this.wait(l2);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

}

