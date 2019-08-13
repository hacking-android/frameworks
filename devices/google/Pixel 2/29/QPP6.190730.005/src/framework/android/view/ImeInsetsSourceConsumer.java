/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.InsetsController;
import android.view.InsetsSourceConsumer;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.function.Supplier;

public final class ImeInsetsSourceConsumer
extends InsetsSourceConsumer {
    private EditorInfo mFocusedEditor;
    private boolean mHasWindowFocus;
    private EditorInfo mPreRenderedEditor;
    private boolean mShowOnNextImeRender;

    public ImeInsetsSourceConsumer(InsetsState insetsState, Supplier<SurfaceControl.Transaction> supplier, InsetsController insetsController) {
        super(10, insetsState, supplier, insetsController);
    }

    @VisibleForTesting
    public static boolean areEditorsSimilar(EditorInfo object, EditorInfo editorInfo) {
        boolean bl;
        boolean bl2 = ((EditorInfo)object).imeOptions == editorInfo.imeOptions && ((EditorInfo)object).inputType == editorInfo.inputType && TextUtils.equals(((EditorInfo)object).packageName, editorInfo.packageName);
        if (!(bl2 & (bl = ((EditorInfo)object).privateImeOptions != null ? ((EditorInfo)object).privateImeOptions.equals(editorInfo.privateImeOptions) : true))) {
            return false;
        }
        if (((EditorInfo)object).extras == null && editorInfo.extras == null || ((EditorInfo)object).extras == editorInfo.extras) {
            return true;
        }
        if (((EditorInfo)object).extras == null && editorInfo.extras != null || ((EditorInfo)object).extras == null && editorInfo.extras != null) {
            return false;
        }
        if (((EditorInfo)object).extras.hashCode() != editorInfo.extras.hashCode() && !((EditorInfo)object).extras.equals(object)) {
            if (((EditorInfo)object).extras.size() != editorInfo.extras.size()) {
                return false;
            }
            if (((EditorInfo)object).extras.toString().equals(editorInfo.extras.toString())) {
                return true;
            }
            Parcel parcel = Parcel.obtain();
            ((EditorInfo)object).extras.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            object = Parcel.obtain();
            editorInfo.extras.writeToParcel((Parcel)object, 0);
            ((Parcel)object).setDataPosition(0);
            return Arrays.equals(parcel.createByteArray(), ((Parcel)object).createByteArray());
        }
        return true;
    }

    private InputMethodManager getImm() {
        return this.mController.getViewRoot().mContext.getSystemService(InputMethodManager.class);
    }

    private boolean isDummyOrEmptyEditor(EditorInfo editorInfo) {
        boolean bl = editorInfo == null || editorInfo.fieldId <= 0 && editorInfo.inputType <= 0;
        return bl;
    }

    private boolean isServedEditorRendered() {
        EditorInfo editorInfo = this.mFocusedEditor;
        if (editorInfo != null && this.mPreRenderedEditor != null && !this.isDummyOrEmptyEditor(editorInfo) && !this.isDummyOrEmptyEditor(this.mPreRenderedEditor)) {
            return ImeInsetsSourceConsumer.areEditorsSimilar(this.mFocusedEditor, this.mPreRenderedEditor);
        }
        return false;
    }

    public void applyImeVisibility(boolean bl) {
        if (!this.mHasWindowFocus) {
            return;
        }
        this.mController.applyImeVisibility(bl);
    }

    @Override
    void notifyHidden() {
        this.getImm().notifyImeHidden();
    }

    public void onPreRendered(EditorInfo editorInfo) {
        this.mPreRenderedEditor = editorInfo;
        if (this.mShowOnNextImeRender) {
            this.mShowOnNextImeRender = false;
            if (this.isServedEditorRendered()) {
                this.applyImeVisibility(true);
            }
        }
    }

    public void onServedEditorChanged(EditorInfo editorInfo) {
        if (this.isDummyOrEmptyEditor(editorInfo)) {
            this.mShowOnNextImeRender = false;
        }
        this.mFocusedEditor = editorInfo;
    }

    @Override
    public void onWindowFocusGained() {
        this.mHasWindowFocus = true;
        this.getImm().registerImeConsumer(this);
    }

    @Override
    public void onWindowFocusLost() {
        this.mHasWindowFocus = false;
        this.getImm().unregisterImeConsumer(this);
    }

    @Override
    int requestShow(boolean bl) {
        if (bl) {
            return 0;
        }
        int n = this.getImm().requestImeShow(null) ? 1 : 2;
        return n;
    }
}

