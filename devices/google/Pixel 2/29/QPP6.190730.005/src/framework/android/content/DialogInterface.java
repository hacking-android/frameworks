/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.view.KeyEvent;

public interface DialogInterface {
    @Deprecated
    public static final int BUTTON1 = -1;
    @Deprecated
    public static final int BUTTON2 = -2;
    @Deprecated
    public static final int BUTTON3 = -3;
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_NEUTRAL = -3;
    public static final int BUTTON_POSITIVE = -1;

    public void cancel();

    public void dismiss();

    public static interface OnCancelListener {
        public void onCancel(DialogInterface var1);
    }

    public static interface OnClickListener {
        public void onClick(DialogInterface var1, int var2);
    }

    public static interface OnDismissListener {
        public void onDismiss(DialogInterface var1);
    }

    public static interface OnKeyListener {
        public boolean onKey(DialogInterface var1, int var2, KeyEvent var3);
    }

    public static interface OnMultiChoiceClickListener {
        public void onClick(DialogInterface var1, int var2, boolean var3);
    }

    public static interface OnShowListener {
        public void onShow(DialogInterface var1);
    }

}

