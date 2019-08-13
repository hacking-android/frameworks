/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import com.android.internal.app.MediaRouteControllerDialog;

public class MediaRouteControllerDialogFragment
extends DialogFragment {
    public MediaRouteControllerDialogFragment() {
        this.setCancelable(true);
    }

    public MediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle bundle) {
        return new MediaRouteControllerDialog(context, this.getTheme());
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        return this.onCreateControllerDialog(this.getContext(), bundle);
    }
}

