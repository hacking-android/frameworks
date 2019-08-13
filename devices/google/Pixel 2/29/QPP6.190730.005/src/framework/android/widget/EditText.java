/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

public class EditText
extends TextView {
    public EditText(Context context) {
        this(context, null);
    }

    public EditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public EditText(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public EditText(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    public void extendSelection(int n) {
        Selection.extendSelection(this.getText(), n);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return EditText.class.getName();
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override
    public boolean getFreezesText() {
        return true;
    }

    @Override
    public Editable getText() {
        CharSequence charSequence = super.getText();
        if (charSequence == null) {
            return null;
        }
        if (charSequence instanceof Editable) {
            return (Editable)super.getText();
        }
        super.setText(charSequence, TextView.BufferType.EDITABLE);
        return (Editable)super.getText();
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.isEnabled()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
        }
    }

    public void selectAll() {
        Selection.selectAll(this.getText());
    }

    @Override
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (truncateAt != TextUtils.TruncateAt.MARQUEE) {
            super.setEllipsize(truncateAt);
            return;
        }
        throw new IllegalArgumentException("EditText cannot use the ellipsize mode TextUtils.TruncateAt.MARQUEE");
    }

    public void setSelection(int n) {
        Selection.setSelection(this.getText(), n);
    }

    public void setSelection(int n, int n2) {
        Selection.setSelection(this.getText(), n, n2);
    }

    @Override
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, TextView.BufferType.EDITABLE);
    }

    @Override
    protected boolean supportsAutoSizeText() {
        return false;
    }
}

