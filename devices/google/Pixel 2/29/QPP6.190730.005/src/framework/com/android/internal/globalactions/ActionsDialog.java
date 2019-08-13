/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.android.internal.app.AlertController;
import com.android.internal.globalactions.Action;
import com.android.internal.globalactions.ActionsAdapter;
import java.util.List;

public final class ActionsDialog
extends Dialog
implements DialogInterface {
    private final ActionsAdapter mAdapter;
    private final AlertController mAlert = AlertController.create(this.mContext, this, this.getWindow());
    private final Context mContext = this.getContext();

    public ActionsDialog(Context context, AlertController.AlertParams alertParams) {
        super(context, ActionsDialog.getDialogTheme(context));
        this.mAdapter = (ActionsAdapter)alertParams.mAdapter;
        alertParams.apply(this.mAlert);
    }

    private static int getDialogTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            for (int i = 0; i < this.mAdapter.getCount(); ++i) {
                CharSequence charSequence = this.mAdapter.getItem(i).getLabelForAccessibility(this.getContext());
                if (charSequence == null) continue;
                accessibilityEvent.getText().add(charSequence);
            }
        }
        return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
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

    @Override
    protected void onStart() {
        super.setCanceledOnTouchOutside(true);
        super.onStart();
    }
}

