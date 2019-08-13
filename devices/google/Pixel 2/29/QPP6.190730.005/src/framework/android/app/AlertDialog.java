/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ResourceId;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.method.MovementMethod;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.internal.app.AlertController;

public class AlertDialog
extends Dialog
implements DialogInterface {
    public static final int LAYOUT_HINT_NONE = 0;
    public static final int LAYOUT_HINT_SIDE = 1;
    @Deprecated
    public static final int THEME_DEVICE_DEFAULT_DARK = 4;
    @Deprecated
    public static final int THEME_DEVICE_DEFAULT_LIGHT = 5;
    @Deprecated
    public static final int THEME_HOLO_DARK = 2;
    @Deprecated
    public static final int THEME_HOLO_LIGHT = 3;
    @Deprecated
    public static final int THEME_TRADITIONAL = 1;
    @UnsupportedAppUsage
    private AlertController mAlert;

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, int n) {
        this(context, n, true);
    }

    AlertDialog(Context context, int n, boolean bl) {
        n = bl ? AlertDialog.resolveDialogTheme(context, n) : 0;
        super(context, n, bl);
        this.mWindow.alwaysReadCloseOnTouchAttr();
        this.mAlert = AlertController.create(this.getContext(), this, this.getWindow());
    }

    protected AlertDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        this(context, 0);
        this.setCancelable(bl);
        this.setOnCancelListener(onCancelListener);
    }

    static int resolveDialogTheme(Context context, int n) {
        if (n == 1) {
            return 16974850;
        }
        if (n == 2) {
            return 16974857;
        }
        if (n == 3) {
            return 16974864;
        }
        if (n == 4) {
            return 16974545;
        }
        if (n == 5) {
            return 16974546;
        }
        if (ResourceId.isValid(n)) {
            return n;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    public Button getButton(int n) {
        return this.mAlert.getButton(n);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(n, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(n, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    public void setButton(int n, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.mAlert.setButton(n, charSequence, onClickListener, null);
    }

    public void setButton(int n, CharSequence charSequence, Message message) {
        this.mAlert.setButton(n, charSequence, null, message);
    }

    @Deprecated
    public void setButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.setButton(-1, charSequence, onClickListener);
    }

    @Deprecated
    public void setButton(CharSequence charSequence, Message message) {
        this.setButton(-1, charSequence, message);
    }

    @Deprecated
    public void setButton2(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.setButton(-2, charSequence, onClickListener);
    }

    @Deprecated
    public void setButton2(CharSequence charSequence, Message message) {
        this.setButton(-2, charSequence, message);
    }

    @Deprecated
    public void setButton3(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        this.setButton(-3, charSequence, onClickListener);
    }

    @Deprecated
    public void setButton3(CharSequence charSequence, Message message) {
        this.setButton(-3, charSequence, message);
    }

    void setButtonPanelLayoutHint(int n) {
        this.mAlert.setButtonPanelLayoutHint(n);
    }

    public void setCustomTitle(View view) {
        this.mAlert.setCustomTitle(view);
    }

    public void setIcon(int n) {
        this.mAlert.setIcon(n);
    }

    public void setIcon(Drawable drawable2) {
        this.mAlert.setIcon(drawable2);
    }

    public void setIconAttribute(int n) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(n, typedValue, true);
        this.mAlert.setIcon(typedValue.resourceId);
    }

    public void setInverseBackgroundForced(boolean bl) {
        this.mAlert.setInverseBackgroundForced(bl);
    }

    public void setMessage(CharSequence charSequence) {
        this.mAlert.setMessage(charSequence);
    }

    public void setMessageHyphenationFrequency(int n) {
        this.mAlert.setMessageHyphenationFrequency(n);
    }

    public void setMessageMovementMethod(MovementMethod movementMethod) {
        this.mAlert.setMessageMovementMethod(movementMethod);
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mAlert.setTitle(charSequence);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public void setView(View view, int n, int n2, int n3, int n4) {
        this.mAlert.setView(view, n, n2, n3, n4);
    }

    public static class Builder {
        @UnsupportedAppUsage
        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int n) {
            this.P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, n)));
        }

        public AlertDialog create() {
            AlertDialog alertDialog = new AlertDialog(this.P.mContext, 0, false);
            this.P.apply(alertDialog.mAlert);
            alertDialog.setCancelable(this.P.mCancelable);
            if (this.P.mCancelable) {
                alertDialog.setCanceledOnTouchOutside(true);
            }
            alertDialog.setOnCancelListener(this.P.mOnCancelListener);
            alertDialog.setOnDismissListener(this.P.mOnDismissListener);
            if (this.P.mOnKeyListener != null) {
                alertDialog.setOnKeyListener(this.P.mOnKeyListener);
            }
            return alertDialog;
        }

        public Context getContext() {
            return this.P.mContext;
        }

        public Builder setAdapter(ListAdapter listAdapter, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mAdapter = listAdapter;
            alertParams.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCancelable(boolean bl) {
            this.P.mCancelable = bl;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener onClickListener, String string2) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mCursor = cursor;
            alertParams.mLabelColumn = string2;
            alertParams.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setCustomTitle(View view) {
            this.P.mCustomTitleView = view;
            return this;
        }

        public Builder setIcon(int n) {
            this.P.mIconId = n;
            return this;
        }

        public Builder setIcon(Drawable drawable2) {
            this.P.mIcon = drawable2;
            return this;
        }

        public Builder setIconAttribute(int n) {
            TypedValue typedValue = new TypedValue();
            this.P.mContext.getTheme().resolveAttribute(n, typedValue, true);
            this.P.mIconId = typedValue.resourceId;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean bl) {
            this.P.mForceInverseBackground = bl;
            return this;
        }

        public Builder setItems(int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(n);
            this.P.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setItems(CharSequence[] arrcharSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = arrcharSequence;
            alertParams.mOnClickListener = onClickListener;
            return this;
        }

        public Builder setMessage(int n) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mMessage = alertParams.mContext.getText(n);
            return this;
        }

        public Builder setMessage(CharSequence charSequence) {
            this.P.mMessage = charSequence;
            return this;
        }

        public Builder setMultiChoiceItems(int n, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(n);
            alertParams = this.P;
            alertParams.mOnCheckboxClickListener = onMultiChoiceClickListener;
            alertParams.mCheckedItems = arrbl;
            alertParams.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String string2, String string3, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mCursor = cursor;
            alertParams.mOnCheckboxClickListener = onMultiChoiceClickListener;
            alertParams.mIsCheckedColumn = string2;
            alertParams.mLabelColumn = string3;
            alertParams.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] arrcharSequence, boolean[] arrbl, DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = arrcharSequence;
            alertParams.mOnCheckboxClickListener = onMultiChoiceClickListener;
            alertParams.mCheckedItems = arrbl;
            alertParams.mIsMultiChoice = true;
            return this;
        }

        public Builder setNegativeButton(int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(n);
            this.P.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNegativeButtonText = charSequence;
            alertParams.mNegativeButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNeutralButtonText = alertParams.mContext.getText(n);
            this.P.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mNeutralButtonText = charSequence;
            alertParams.mNeutralButtonListener = onClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
            this.P.mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setPositiveButton(int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(n);
            this.P.mPositiveButtonListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mPositiveButtonText = charSequence;
            alertParams.mPositiveButtonListener = onClickListener;
            return this;
        }

        @UnsupportedAppUsage
        public Builder setRecycleOnMeasureEnabled(boolean bl) {
            this.P.mRecycleOnMeasure = bl;
            return this;
        }

        public Builder setSingleChoiceItems(int n, int n2, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(n);
            alertParams = this.P;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = n2;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int n, String string2, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mCursor = cursor;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = n;
            alertParams.mLabelColumn = string2;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter listAdapter, int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mAdapter = listAdapter;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = n;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] arrcharSequence, int n, DialogInterface.OnClickListener onClickListener) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mItems = arrcharSequence;
            alertParams.mOnClickListener = onClickListener;
            alertParams.mCheckedItem = n;
            alertParams.mIsSingleChoice = true;
            return this;
        }

        public Builder setTitle(int n) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mTitle = alertParams.mContext.getText(n);
            return this;
        }

        public Builder setTitle(CharSequence charSequence) {
            this.P.mTitle = charSequence;
            return this;
        }

        public Builder setView(int n) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mView = null;
            alertParams.mViewLayoutResId = n;
            alertParams.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mView = view;
            alertParams.mViewLayoutResId = 0;
            alertParams.mViewSpacingSpecified = false;
            return this;
        }

        @Deprecated
        @UnsupportedAppUsage
        public Builder setView(View view, int n, int n2, int n3, int n4) {
            AlertController.AlertParams alertParams = this.P;
            alertParams.mView = view;
            alertParams.mViewLayoutResId = 0;
            alertParams.mViewSpacingSpecified = true;
            alertParams.mViewSpacingLeft = n;
            alertParams.mViewSpacingTop = n2;
            alertParams.mViewSpacingRight = n3;
            alertParams.mViewSpacingBottom = n4;
            return this;
        }

        public AlertDialog show() {
            AlertDialog alertDialog = this.create();
            alertDialog.show();
            return alertDialog;
        }
    }

}

