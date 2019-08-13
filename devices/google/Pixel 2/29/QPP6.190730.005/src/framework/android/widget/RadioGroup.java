/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.android.internal.R;

public class RadioGroup
extends LinearLayout {
    private static final String LOG_TAG = RadioGroup.class.getSimpleName();
    private int mCheckedId = -1;
    @UnsupportedAppUsage
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mInitialCheckedId = -1;
    @UnsupportedAppUsage
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mProtectFromCheckedChange = false;

    public RadioGroup(Context context) {
        super(context);
        this.setOrientation(1);
        this.init();
    }

    public RadioGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (this.getImportantForAutofill() == 0) {
            this.setImportantForAutofill(1);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RadioGroup, 16842878, 0);
        this.saveAttributeDataForStyleable(context, R.styleable.RadioGroup, attributeSet, typedArray, 16842878, 0);
        int n = typedArray.getResourceId(1, -1);
        if (n != -1) {
            this.mCheckedId = n;
            this.mInitialCheckedId = n;
        }
        this.setOrientation(typedArray.getInt(0, 1));
        typedArray.recycle();
        this.init();
    }

    private void init() {
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        this.mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    private void setCheckedId(int n) {
        boolean bl = n != this.mCheckedId;
        this.mCheckedId = n;
        Object object = this.mOnCheckedChangeListener;
        if (object != null) {
            object.onCheckedChanged(this, this.mCheckedId);
        }
        if (bl && (object = this.mContext.getSystemService(AutofillManager.class)) != null) {
            ((AutofillManager)object).notifyValueChanged(this);
        }
    }

    private void setCheckedStateForView(int n, boolean bl) {
        Object t = this.findViewById(n);
        if (t != null && t instanceof RadioButton) {
            ((RadioButton)t).setChecked(bl);
        }
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        RadioButton radioButton;
        if (view instanceof RadioButton && (radioButton = (RadioButton)view).isChecked()) {
            this.mProtectFromCheckedChange = true;
            int n2 = this.mCheckedId;
            if (n2 != -1) {
                this.setCheckedStateForView(n2, false);
            }
            this.mProtectFromCheckedChange = false;
            this.setCheckedId(radioButton.getId());
        }
        super.addView(view, n, layoutParams);
    }

    @Override
    public void autofill(AutofillValue object) {
        if (!this.isEnabled()) {
            return;
        }
        if (!((AutofillValue)object).isList()) {
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object);
            stringBuilder.append(" could not be autofilled into ");
            stringBuilder.append(this);
            Log.w(string2, stringBuilder.toString());
            return;
        }
        int n = ((AutofillValue)object).getListValue();
        object = this.getChildAt(n);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("RadioGroup.autoFill(): no child with index ");
            ((StringBuilder)object).append(n);
            Log.w("View", ((StringBuilder)object).toString());
            return;
        }
        this.check(((View)object).getId());
    }

    public void check(int n) {
        if (n != -1 && n == this.mCheckedId) {
            return;
        }
        int n2 = this.mCheckedId;
        if (n2 != -1) {
            this.setCheckedStateForView(n2, false);
        }
        if (n != -1) {
            this.setCheckedStateForView(n, true);
        }
        this.setCheckedId(n);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void clearCheck() {
        this.check(-1);
    }

    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return RadioGroup.class.getName();
    }

    @Override
    public int getAutofillType() {
        int n = this.isEnabled() ? 3 : 0;
        return n;
    }

    @Override
    public AutofillValue getAutofillValue() {
        if (!this.isEnabled()) {
            return null;
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            if (this.getChildAt(i).getId() != this.mCheckedId) continue;
            return AutofillValue.forList(i);
        }
        return null;
    }

    public int getCheckedRadioButtonId() {
        return this.mCheckedId;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int n = this.mCheckedId;
        if (n != -1) {
            this.mProtectFromCheckedChange = true;
            this.setCheckedStateForView(n, true);
            this.mProtectFromCheckedChange = false;
            this.setCheckedId(this.mCheckedId);
        }
    }

    @Override
    protected void onProvideStructure(ViewStructure viewStructure, int n, int n2) {
        super.onProvideStructure(viewStructure, n, n2);
        boolean bl = true;
        if (n == 1) {
            if (this.mCheckedId == this.mInitialCheckedId) {
                bl = false;
            }
            viewStructure.setDataIsSensitive(bl);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    private class CheckedStateTracker
    implements CompoundButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
            if (RadioGroup.this.mProtectFromCheckedChange) {
                return;
            }
            RadioGroup.this.mProtectFromCheckedChange = true;
            if (RadioGroup.this.mCheckedId != -1) {
                RadioGroup radioGroup = RadioGroup.this;
                radioGroup.setCheckedStateForView(radioGroup.mCheckedId, false);
            }
            RadioGroup.this.mProtectFromCheckedChange = false;
            int n = compoundButton.getId();
            RadioGroup.this.setCheckedId(n);
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2, f);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @Override
        protected void setBaseAttributes(TypedArray typedArray, int n, int n2) {
            this.width = typedArray.hasValue(n) ? typedArray.getLayoutDimension(n, "layout_width") : -2;
            this.height = typedArray.hasValue(n2) ? typedArray.getLayoutDimension(n2, "layout_height") : -2;
        }
    }

    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup var1, int var2);
    }

    private class PassThroughHierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override
        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
            if (view == RadioGroup.this && view2 instanceof RadioButton) {
                if (view2.getId() == -1) {
                    view2.setId(View.generateViewId());
                }
                ((RadioButton)view2).setOnCheckedChangeWidgetListener(RadioGroup.this.mChildOnCheckedChangeListener);
            }
            if ((onHierarchyChangeListener = this.mOnHierarchyChangeListener) != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override
        public void onChildViewRemoved(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;
            if (view == RadioGroup.this && view2 instanceof RadioButton) {
                ((RadioButton)view2).setOnCheckedChangeWidgetListener(null);
            }
            if ((onHierarchyChangeListener = this.mOnHierarchyChangeListener) != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

}

