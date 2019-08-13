/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;

public abstract class ResourceCursorTreeAdapter
extends CursorTreeAdapter {
    private int mChildLayout;
    private int mCollapsedGroupLayout;
    private int mExpandedGroupLayout;
    private LayoutInflater mInflater;
    private int mLastChildLayout;

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int n, int n2) {
        this(context, cursor, n, n, n2, n2);
    }

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int n, int n2, int n3) {
        this(context, cursor, n, n2, n3, n3);
    }

    public ResourceCursorTreeAdapter(Context context, Cursor cursor, int n, int n2, int n3, int n4) {
        super(cursor, context);
        this.mCollapsedGroupLayout = n;
        this.mExpandedGroupLayout = n2;
        this.mChildLayout = n3;
        this.mLastChildLayout = n4;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    @Override
    public View newChildView(Context object, Cursor cursor, boolean bl, ViewGroup viewGroup) {
        object = this.mInflater;
        int n = bl ? this.mLastChildLayout : this.mChildLayout;
        return ((LayoutInflater)object).inflate(n, viewGroup, false);
    }

    @Override
    public View newGroupView(Context object, Cursor cursor, boolean bl, ViewGroup viewGroup) {
        object = this.mInflater;
        int n = bl ? this.mExpandedGroupLayout : this.mCollapsedGroupLayout;
        return ((LayoutInflater)object).inflate(n, viewGroup, false);
    }
}

