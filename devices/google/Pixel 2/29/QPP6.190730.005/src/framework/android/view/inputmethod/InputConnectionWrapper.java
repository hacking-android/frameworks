/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionInspector;
import android.view.inputmethod.InputContentInfo;

public class InputConnectionWrapper
implements InputConnection {
    private int mMissingMethodFlags;
    final boolean mMutable;
    private InputConnection mTarget;

    public InputConnectionWrapper(InputConnection inputConnection, boolean bl) {
        this.mMutable = bl;
        this.mTarget = inputConnection;
        this.mMissingMethodFlags = InputConnectionInspector.getMissingMethodFlags(inputConnection);
    }

    @Override
    public boolean beginBatchEdit() {
        return this.mTarget.beginBatchEdit();
    }

    @Override
    public boolean clearMetaKeyStates(int n) {
        return this.mTarget.clearMetaKeyStates(n);
    }

    @Override
    public void closeConnection() {
        this.mTarget.closeConnection();
    }

    @Override
    public boolean commitCompletion(CompletionInfo completionInfo) {
        return this.mTarget.commitCompletion(completionInfo);
    }

    @Override
    public boolean commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle) {
        return this.mTarget.commitContent(inputContentInfo, n, bundle);
    }

    @Override
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        return this.mTarget.commitCorrection(correctionInfo);
    }

    @Override
    public boolean commitText(CharSequence charSequence, int n) {
        return this.mTarget.commitText(charSequence, n);
    }

    @Override
    public boolean deleteSurroundingText(int n, int n2) {
        return this.mTarget.deleteSurroundingText(n, n2);
    }

    @Override
    public boolean deleteSurroundingTextInCodePoints(int n, int n2) {
        return this.mTarget.deleteSurroundingTextInCodePoints(n, n2);
    }

    @Override
    public boolean endBatchEdit() {
        return this.mTarget.endBatchEdit();
    }

    @Override
    public boolean finishComposingText() {
        return this.mTarget.finishComposingText();
    }

    @Override
    public int getCursorCapsMode(int n) {
        return this.mTarget.getCursorCapsMode(n);
    }

    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int n) {
        return this.mTarget.getExtractedText(extractedTextRequest, n);
    }

    @Override
    public Handler getHandler() {
        return this.mTarget.getHandler();
    }

    public int getMissingMethodFlags() {
        return this.mMissingMethodFlags;
    }

    @Override
    public CharSequence getSelectedText(int n) {
        return this.mTarget.getSelectedText(n);
    }

    @Override
    public CharSequence getTextAfterCursor(int n, int n2) {
        return this.mTarget.getTextAfterCursor(n, n2);
    }

    @Override
    public CharSequence getTextBeforeCursor(int n, int n2) {
        return this.mTarget.getTextBeforeCursor(n, n2);
    }

    @Override
    public boolean performContextMenuAction(int n) {
        return this.mTarget.performContextMenuAction(n);
    }

    @Override
    public boolean performEditorAction(int n) {
        return this.mTarget.performEditorAction(n);
    }

    @Override
    public boolean performPrivateCommand(String string2, Bundle bundle) {
        return this.mTarget.performPrivateCommand(string2, bundle);
    }

    @Override
    public boolean reportFullscreenMode(boolean bl) {
        return this.mTarget.reportFullscreenMode(bl);
    }

    @Override
    public boolean requestCursorUpdates(int n) {
        return this.mTarget.requestCursorUpdates(n);
    }

    @Override
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        return this.mTarget.sendKeyEvent(keyEvent);
    }

    @Override
    public boolean setComposingRegion(int n, int n2) {
        return this.mTarget.setComposingRegion(n, n2);
    }

    @Override
    public boolean setComposingText(CharSequence charSequence, int n) {
        return this.mTarget.setComposingText(charSequence, n);
    }

    @Override
    public boolean setSelection(int n, int n2) {
        return this.mTarget.setSelection(n, n2);
    }

    public void setTarget(InputConnection inputConnection) {
        if (this.mTarget != null && !this.mMutable) {
            throw new SecurityException("not mutable");
        }
        this.mTarget = inputConnection;
        this.mMissingMethodFlags = InputConnectionInspector.getMissingMethodFlags(inputConnection);
    }
}

