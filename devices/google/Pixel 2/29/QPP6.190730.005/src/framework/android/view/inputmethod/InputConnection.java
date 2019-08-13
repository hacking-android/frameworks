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
import android.view.inputmethod.InputContentInfo;

public interface InputConnection {
    public static final int CURSOR_UPDATE_IMMEDIATE = 1;
    public static final int CURSOR_UPDATE_MONITOR = 2;
    public static final int GET_EXTRACTED_TEXT_MONITOR = 1;
    public static final int GET_TEXT_WITH_STYLES = 1;
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    public boolean beginBatchEdit();

    public boolean clearMetaKeyStates(int var1);

    public void closeConnection();

    public boolean commitCompletion(CompletionInfo var1);

    public boolean commitContent(InputContentInfo var1, int var2, Bundle var3);

    public boolean commitCorrection(CorrectionInfo var1);

    public boolean commitText(CharSequence var1, int var2);

    public boolean deleteSurroundingText(int var1, int var2);

    public boolean deleteSurroundingTextInCodePoints(int var1, int var2);

    public boolean endBatchEdit();

    public boolean finishComposingText();

    public int getCursorCapsMode(int var1);

    public ExtractedText getExtractedText(ExtractedTextRequest var1, int var2);

    public Handler getHandler();

    public CharSequence getSelectedText(int var1);

    public CharSequence getTextAfterCursor(int var1, int var2);

    public CharSequence getTextBeforeCursor(int var1, int var2);

    public boolean performContextMenuAction(int var1);

    public boolean performEditorAction(int var1);

    public boolean performPrivateCommand(String var1, Bundle var2);

    public boolean reportFullscreenMode(boolean var1);

    public boolean requestCursorUpdates(int var1);

    public boolean sendKeyEvent(KeyEvent var1);

    public boolean setComposingRegion(int var1, int var2);

    public boolean setComposingText(CharSequence var1, int var2);

    public boolean setSelection(int var1, int var2);
}

