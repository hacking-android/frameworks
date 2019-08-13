/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.media.MediaRouter;
import android.util.Log;
import android.view.View;
import com.android.internal.app.MediaRouteChooserDialog;
import com.android.internal.app.MediaRouteChooserDialogFragment;
import com.android.internal.app.MediaRouteControllerDialog;
import com.android.internal.app.MediaRouteControllerDialogFragment;

public abstract class MediaRouteDialogPresenter {
    private static final String CHOOSER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteChooserDialogFragment";
    private static final String CONTROLLER_FRAGMENT_TAG = "android.app.MediaRouteButton:MediaRouteControllerDialogFragment";
    private static final String TAG = "MediaRouter";

    public static Dialog createDialog(Context object, int n, View.OnClickListener onClickListener) {
        Object object2 = (MediaRouter)((Context)object).getSystemService("media_router");
        int n2 = MediaRouteChooserDialog.isLightTheme((Context)object) ? 16974130 : 16974126;
        if (!((MediaRouter.RouteInfo)(object2 = ((MediaRouter)object2).getSelectedRoute())).isDefault() && ((MediaRouter.RouteInfo)object2).matchesTypes(n)) {
            return new MediaRouteControllerDialog((Context)object, n2);
        }
        object = new MediaRouteChooserDialog((Context)object, n2);
        ((MediaRouteChooserDialog)object).setRouteTypes(n);
        ((MediaRouteChooserDialog)object).setExtendedSettingsClickListener(onClickListener);
        return object;
    }

    public static DialogFragment showDialogFragment(Activity object, int n, View.OnClickListener object2) {
        Object object3 = (MediaRouter)((Activity)object).getSystemService("media_router");
        object = ((Activity)object).getFragmentManager();
        if (!((MediaRouter.RouteInfo)(object3 = ((MediaRouter)object3).getSelectedRoute())).isDefault() && ((MediaRouter.RouteInfo)object3).matchesTypes(n)) {
            if (((FragmentManager)object).findFragmentByTag(CONTROLLER_FRAGMENT_TAG) != null) {
                Log.w(TAG, "showDialog(): Route controller dialog already showing!");
                return null;
            }
            object2 = new MediaRouteControllerDialogFragment();
            ((DialogFragment)object2).show((FragmentManager)object, CONTROLLER_FRAGMENT_TAG);
            return object2;
        }
        if (((FragmentManager)object).findFragmentByTag(CHOOSER_FRAGMENT_TAG) != null) {
            Log.w(TAG, "showDialog(): Route chooser dialog already showing!");
            return null;
        }
        object3 = new MediaRouteChooserDialogFragment();
        ((MediaRouteChooserDialogFragment)object3).setRouteTypes(n);
        ((MediaRouteChooserDialogFragment)object3).setExtendedSettingsClickListener((View.OnClickListener)object2);
        ((DialogFragment)object3).show((FragmentManager)object, CHOOSER_FRAGMENT_TAG);
        return object3;
    }
}

