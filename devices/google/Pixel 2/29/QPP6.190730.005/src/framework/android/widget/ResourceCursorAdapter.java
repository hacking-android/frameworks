/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public abstract class ResourceCursorAdapter
extends CursorAdapter {
    private LayoutInflater mDropDownInflater;
    private int mDropDownLayout;
    private LayoutInflater mInflater;
    private int mLayout;

    @Deprecated
    public ResourceCursorAdapter(Context context, int n, Cursor cursor) {
        super(context, cursor);
        this.mDropDownLayout = n;
        this.mLayout = n;
        this.mDropDownInflater = this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public ResourceCursorAdapter(Context context, int n, Cursor cursor, int n2) {
        super(context, cursor, n2);
        this.mDropDownLayout = n;
        this.mLayout = n;
        this.mDropDownInflater = this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public ResourceCursorAdapter(Context context, int n, Cursor cursor, boolean bl) {
        super(context, cursor, bl);
        this.mDropDownLayout = n;
        this.mLayout = n;
        this.mDropDownInflater = this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    @Override
    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mDropDownInflater.inflate(this.mDropDownLayout, viewGroup, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.mInflater.inflate(this.mLayout, viewGroup, false);
    }

    public void setDropDownViewResource(int n) {
        this.mDropDownLayout = n;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        super.setDropDownViewTheme(theme);
        this.mDropDownInflater = theme == null ? null : (theme == this.mInflater.getContext().getTheme() ? this.mInflater : LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme)));
    }

    public void setViewResource(int n) {
        this.mLayout = n;
    }
}

