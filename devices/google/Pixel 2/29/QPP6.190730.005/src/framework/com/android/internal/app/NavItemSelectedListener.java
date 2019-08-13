/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;

class NavItemSelectedListener
implements AdapterView.OnItemSelectedListener {
    private final ActionBar.OnNavigationListener mListener;

    public NavItemSelectedListener(ActionBar.OnNavigationListener onNavigationListener) {
        this.mListener = onNavigationListener;
    }

    @Override
    public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
        object = this.mListener;
        if (object != null) {
            object.onNavigationItemSelected(n, l);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}

