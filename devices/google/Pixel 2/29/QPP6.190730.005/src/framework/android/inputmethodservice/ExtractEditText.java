/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.util.AttributeSet;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class ExtractEditText
extends EditText {
    private InputMethodService mIME;
    private int mSettingExtractedText;

    public ExtractEditText(Context context) {
        super(context, null);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842862);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ExtractEditText(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected void deleteText_internal(int n, int n2) {
        this.mIME.onExtractedDeleteText(n, n2);
    }

    public void finishInternalChanges() {
        --this.mSettingExtractedText;
    }

    @Override
    public boolean hasFocus() {
        return this.isEnabled();
    }

    public boolean hasVerticalScrollBar() {
        boolean bl = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        return bl;
    }

    @Override
    public boolean hasWindowFocus() {
        return this.isEnabled();
    }

    @Override
    public boolean isFocused() {
        return this.isEnabled();
    }

    @Override
    public boolean isInExtractedMode() {
        return true;
    }

    @Override
    public boolean isInputMethodTarget() {
        return true;
    }

    @Override
    protected void onSelectionChanged(int n, int n2) {
        InputMethodService inputMethodService;
        if (this.mSettingExtractedText == 0 && (inputMethodService = this.mIME) != null && n >= 0 && n2 >= 0) {
            inputMethodService.onExtractedSelectionChanged(n, n2);
        }
    }

    @Override
    public boolean onTextContextMenuItem(int n) {
        if (n != 16908319 && n != 16908340) {
            InputMethodService inputMethodService = this.mIME;
            if (inputMethodService != null && inputMethodService.onExtractTextContextMenuItem(n)) {
                if (n == 16908321 || n == 16908322) {
                    this.stopTextActionMode();
                }
                return true;
            }
            return super.onTextContextMenuItem(n);
        }
        return super.onTextContextMenuItem(n);
    }

    @Override
    public boolean performClick() {
        InputMethodService inputMethodService;
        if (!super.performClick() && (inputMethodService = this.mIME) != null) {
            inputMethodService.onExtractedTextClicked();
            return true;
        }
        return false;
    }

    @Override
    protected void replaceText_internal(int n, int n2, CharSequence charSequence) {
        this.mIME.onExtractedReplaceText(n, n2, charSequence);
    }

    @Override
    protected void setCursorPosition_internal(int n, int n2) {
        this.mIME.onExtractedSelectionChanged(n, n2);
    }

    @Override
    public void setExtractedText(ExtractedText extractedText) {
        try {
            ++this.mSettingExtractedText;
            super.setExtractedText(extractedText);
            return;
        }
        finally {
            --this.mSettingExtractedText;
        }
    }

    void setIME(InputMethodService inputMethodService) {
        this.mIME = inputMethodService;
    }

    @Override
    protected void setSpan_internal(Object object, int n, int n2, int n3) {
        this.mIME.onExtractedSetSpan(object, n, n2, n3);
    }

    public void startInternalChanges() {
        ++this.mSettingExtractedText;
    }

    @Override
    protected void viewClicked(InputMethodManager object) {
        object = this.mIME;
        if (object != null) {
            ((InputMethodService)object).onViewClicked(false);
        }
    }
}

