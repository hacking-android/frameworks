/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;

public interface InputMethodSession {
    public void appPrivateCommand(String var1, Bundle var2);

    public void dispatchGenericMotionEvent(int var1, MotionEvent var2, EventCallback var3);

    public void dispatchKeyEvent(int var1, KeyEvent var2, EventCallback var3);

    public void dispatchTrackballEvent(int var1, MotionEvent var2, EventCallback var3);

    public void displayCompletions(CompletionInfo[] var1);

    public void finishInput();

    public void notifyImeHidden();

    public void toggleSoftInput(int var1, int var2);

    public void updateCursor(Rect var1);

    public void updateCursorAnchorInfo(CursorAnchorInfo var1);

    public void updateExtractedText(int var1, ExtractedText var2);

    public void updateSelection(int var1, int var2, int var3, int var4, int var5, int var6);

    public void viewClicked(boolean var1);

    public static interface EventCallback {
        public void finishedEvent(int var1, boolean var2);
    }

}

