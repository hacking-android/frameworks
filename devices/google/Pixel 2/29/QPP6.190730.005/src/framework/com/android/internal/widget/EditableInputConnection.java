/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class EditableInputConnection
extends BaseInputConnection {
    private static final boolean DEBUG = false;
    private static final String TAG = "EditableInputConnection";
    private int mBatchEditNesting;
    private final TextView mTextView;

    @UnsupportedAppUsage
    public EditableInputConnection(TextView textView) {
        super(textView, true);
        this.mTextView = textView;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean beginBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting >= 0) {
                this.mTextView.beginBatchEdit();
                ++this.mBatchEditNesting;
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean clearMetaKeyStates(int n) {
        Editable editable = this.getEditable();
        if (editable == null) {
            return false;
        }
        KeyListener keyListener = this.mTextView.getKeyListener();
        if (keyListener != null) {
            try {
                keyListener.clearMetaKeyState(this.mTextView, editable, n);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void closeConnection() {
        super.closeConnection();
        synchronized (this) {
            do {
                if (this.mBatchEditNesting <= 0) {
                    this.mBatchEditNesting = -1;
                    return;
                }
                this.endBatchEdit();
            } while (true);
        }
    }

    @Override
    public boolean commitCompletion(CompletionInfo completionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCompletion(completionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override
    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onCommitCorrection(correctionInfo);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override
    public boolean commitText(CharSequence charSequence, int n) {
        TextView textView = this.mTextView;
        if (textView == null) {
            return super.commitText(charSequence, n);
        }
        textView.resetErrorChangedFlag();
        boolean bl = super.commitText(charSequence, n);
        this.mTextView.hideErrorIfUnchanged();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean endBatchEdit() {
        synchronized (this) {
            if (this.mBatchEditNesting > 0) {
                this.mTextView.endBatchEdit();
                --this.mBatchEditNesting;
                return true;
            }
            return false;
        }
    }

    @Override
    public Editable getEditable() {
        TextView textView = this.mTextView;
        if (textView != null) {
            return textView.getEditableText();
        }
        return null;
    }

    @Override
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int n) {
        ExtractedText extractedText;
        if (this.mTextView != null && this.mTextView.extractText(extractedTextRequest, extractedText = new ExtractedText())) {
            if ((n & 1) != 0) {
                this.mTextView.setExtracting(extractedTextRequest);
            }
            return extractedText;
        }
        return null;
    }

    @Override
    public boolean performContextMenuAction(int n) {
        this.mTextView.beginBatchEdit();
        this.mTextView.onTextContextMenuItem(n);
        this.mTextView.endBatchEdit();
        return true;
    }

    @Override
    public boolean performEditorAction(int n) {
        this.mTextView.onEditorAction(n);
        return true;
    }

    @Override
    public boolean performPrivateCommand(String string2, Bundle bundle) {
        this.mTextView.onPrivateIMECommand(string2, bundle);
        return true;
    }

    @Override
    public boolean requestCursorUpdates(int n) {
        TextView textView;
        if ((n & -4) != 0) {
            return false;
        }
        if (this.mIMM == null) {
            return false;
        }
        this.mIMM.setUpdateCursorAnchorInfoMode(n);
        if ((n & 1) != 0 && (textView = this.mTextView) != null && !textView.isInLayout()) {
            this.mTextView.requestLayout();
        }
        return true;
    }
}

