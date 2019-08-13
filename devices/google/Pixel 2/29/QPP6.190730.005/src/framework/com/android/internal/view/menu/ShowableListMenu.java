/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view.menu;

import android.widget.ListView;

public interface ShowableListMenu {
    public void dismiss();

    public ListView getListView();

    public boolean isShowing();

    public void show();
}

