/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.BaseBundle;
import android.os.Bundle;
import android.view.View;
import com.android.internal.app.MediaRouteChooserDialog;

public class MediaRouteChooserDialogFragment
extends DialogFragment {
    private final String ARGUMENT_ROUTE_TYPES;
    private View.OnClickListener mExtendedSettingsClickListener;

    public MediaRouteChooserDialogFragment() {
        this.ARGUMENT_ROUTE_TYPES = "routeTypes";
        int n = MediaRouteChooserDialog.isLightTheme(this.getContext()) ? 16974130 : 16974126;
        this.setCancelable(true);
        this.setStyle(0, n);
    }

    public int getRouteTypes() {
        Bundle bundle = this.getArguments();
        int n = bundle != null ? bundle.getInt("routeTypes") : 0;
        return n;
    }

    public MediaRouteChooserDialog onCreateChooserDialog(Context context, Bundle bundle) {
        return new MediaRouteChooserDialog(context, this.getTheme());
    }

    @Override
    public Dialog onCreateDialog(Bundle object) {
        object = this.onCreateChooserDialog(this.getActivity(), (Bundle)object);
        ((MediaRouteChooserDialog)object).setRouteTypes(this.getRouteTypes());
        ((MediaRouteChooserDialog)object).setExtendedSettingsClickListener(this.mExtendedSettingsClickListener);
        return object;
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            MediaRouteChooserDialog mediaRouteChooserDialog = (MediaRouteChooserDialog)this.getDialog();
            if (mediaRouteChooserDialog != null) {
                mediaRouteChooserDialog.setExtendedSettingsClickListener(onClickListener);
            }
        }
    }

    public void setRouteTypes(int n) {
        if (n != this.getRouteTypes()) {
            Bundle bundle = this.getArguments();
            Object object = bundle;
            if (bundle == null) {
                object = new Bundle();
            }
            ((BaseBundle)object).putInt("routeTypes", n);
            this.setArguments((Bundle)object);
            object = (MediaRouteChooserDialog)this.getDialog();
            if (object != null) {
                ((MediaRouteChooserDialog)object).setRouteTypes(n);
            }
        }
    }
}

