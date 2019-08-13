/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.globalactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface Action {
    public View create(Context var1, View var2, ViewGroup var3, LayoutInflater var4);

    public CharSequence getLabelForAccessibility(Context var1);

    public boolean isEnabled();

    public void onPress();

    public boolean showBeforeProvisioning();

    public boolean showDuringKeyguard();
}

