/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.globalactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.internal.globalactions.Action;
import java.util.List;
import java.util.function.BooleanSupplier;

public class ActionsAdapter
extends BaseAdapter {
    private final Context mContext;
    private final BooleanSupplier mDeviceProvisioned;
    private final List<Action> mItems;
    private final BooleanSupplier mKeyguardShowing;

    public ActionsAdapter(Context context, List<Action> list, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2) {
        this.mContext = context;
        this.mItems = list;
        this.mDeviceProvisioned = booleanSupplier;
        this.mKeyguardShowing = booleanSupplier2;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        boolean bl = this.mKeyguardShowing.getAsBoolean();
        boolean bl2 = this.mDeviceProvisioned.getAsBoolean();
        int n = 0;
        for (int i = 0; i < this.mItems.size(); ++i) {
            Action action = this.mItems.get(i);
            if (bl && !action.showDuringKeyguard() || !bl2 && !action.showBeforeProvisioning()) continue;
            ++n;
        }
        return n;
    }

    @Override
    public Action getItem(int n) {
        Object object;
        boolean bl = this.mKeyguardShowing.getAsBoolean();
        boolean bl2 = this.mDeviceProvisioned.getAsBoolean();
        int n2 = 0;
        for (int i = 0; i < this.mItems.size(); ++i) {
            object = this.mItems.get(i);
            if (bl && !object.showDuringKeyguard() || !bl2 && !object.showBeforeProvisioning()) continue;
            if (n2 == n) {
                return object;
            }
            ++n2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("position ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" out of range of showable actions, filtered count=");
        ((StringBuilder)object).append(this.getCount());
        ((StringBuilder)object).append(", keyguardshowing=");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(", provisioned=");
        ((StringBuilder)object).append(bl2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        Action action = this.getItem(n);
        Context context = this.mContext;
        return action.create(context, view, viewGroup, LayoutInflater.from(context));
    }

    @Override
    public boolean isEnabled(int n) {
        return this.getItem(n).isEnabled();
    }
}

