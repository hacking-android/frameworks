/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

public class ResolverTargetActionsDialogFragment
extends DialogFragment
implements DialogInterface.OnClickListener {
    private static final int APP_INFO_INDEX = 0;
    private static final String NAME_KEY = "componentName";
    private static final String TITLE_KEY = "title";

    public ResolverTargetActionsDialogFragment() {
    }

    public ResolverTargetActionsDialogFragment(CharSequence charSequence, ComponentName componentName) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TITLE_KEY, charSequence);
        bundle.putParcelable(NAME_KEY, componentName);
        this.setArguments(bundle);
    }

    @Override
    public void onClick(DialogInterface object, int n) {
        object = (ComponentName)this.getArguments().getParcelable(NAME_KEY);
        if (n == 0) {
            this.startActivity(new Intent().setAction("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", ((ComponentName)object).getPackageName(), null)).addFlags(524288));
        }
        this.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        bundle = this.getArguments();
        return new AlertDialog.Builder(this.getContext()).setCancelable(true).setItems(17236102, (DialogInterface.OnClickListener)this).setTitle(bundle.getCharSequence(TITLE_KEY)).create();
    }
}

