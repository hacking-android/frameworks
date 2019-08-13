/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class WindowCallbackWrapper
implements Window.Callback {
    private Window.Callback mWrapped;

    public WindowCallbackWrapper(Window.Callback callback) {
        if (callback != null) {
            this.mWrapped = callback;
            return;
        }
        throw new IllegalArgumentException("Window callback may not be null");
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyEvent(keyEvent);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.mWrapped.dispatchKeyShortcutEvent(keyEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return this.mWrapped.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return this.mWrapped.dispatchTrackballEvent(motionEvent);
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
        this.mWrapped.onActionModeFinished(actionMode);
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
        this.mWrapped.onActionModeStarted(actionMode);
    }

    @Override
    public void onAttachedToWindow() {
        this.mWrapped.onAttachedToWindow();
    }

    @Override
    public void onContentChanged() {
        this.mWrapped.onContentChanged();
    }

    @Override
    public boolean onCreatePanelMenu(int n, Menu menu2) {
        return this.mWrapped.onCreatePanelMenu(n, menu2);
    }

    @Override
    public View onCreatePanelView(int n) {
        return this.mWrapped.onCreatePanelView(n);
    }

    @Override
    public void onDetachedFromWindow() {
        this.mWrapped.onDetachedFromWindow();
    }

    @Override
    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        return this.mWrapped.onMenuItemSelected(n, menuItem);
    }

    @Override
    public boolean onMenuOpened(int n, Menu menu2) {
        return this.mWrapped.onMenuOpened(n, menu2);
    }

    @Override
    public void onPanelClosed(int n, Menu menu2) {
        this.mWrapped.onPanelClosed(n, menu2);
    }

    @Override
    public void onPointerCaptureChanged(boolean bl) {
        this.mWrapped.onPointerCaptureChanged(bl);
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu2) {
        return this.mWrapped.onPreparePanel(n, view, menu2);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu2, int n) {
        this.mWrapped.onProvideKeyboardShortcuts(list, menu2, n);
    }

    @Override
    public boolean onSearchRequested() {
        return this.mWrapped.onSearchRequested();
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return this.mWrapped.onSearchRequested(searchEvent);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        this.mWrapped.onWindowAttributesChanged(layoutParams);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        this.mWrapped.onWindowFocusChanged(bl);
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.mWrapped.onWindowStartingActionMode(callback);
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n) {
        return this.mWrapped.onWindowStartingActionMode(callback, n);
    }
}

