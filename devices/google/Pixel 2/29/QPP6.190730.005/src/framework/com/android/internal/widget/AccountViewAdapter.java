/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.internal.widget.AccountItemView;
import java.util.List;

public class AccountViewAdapter
extends BaseAdapter {
    private Context mContext;
    private List<AccountElements> mData;

    public AccountViewAdapter(Context context, List<AccountElements> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Object getItem(int n) {
        return this.mData.get(n);
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        view = view == null ? new AccountItemView(this.mContext) : (AccountItemView)view;
        ((AccountItemView)view).setViewItem((AccountElements)this.getItem(n));
        return view;
    }

    public void updateData(List<AccountElements> list) {
        this.mData = list;
        this.notifyDataSetChanged();
    }

    public static class AccountElements {
        private Drawable mDrawable;
        private int mIcon;
        private String mName;
        private String mNumber;

        private AccountElements(int n, Drawable drawable2, String string2, String string3) {
            this.mIcon = n;
            this.mDrawable = drawable2;
            this.mName = string2;
            this.mNumber = string3;
        }

        public AccountElements(int n, String string2, String string3) {
            this(n, null, string2, string3);
        }

        public AccountElements(Drawable drawable2, String string2, String string3) {
            this(0, drawable2, string2, string3);
        }

        public Drawable getDrawable() {
            return this.mDrawable;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public String getName() {
            return this.mName;
        }

        public String getNumber() {
            return this.mNumber;
        }
    }

}

