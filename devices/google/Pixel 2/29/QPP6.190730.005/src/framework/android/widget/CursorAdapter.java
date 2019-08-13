/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorFilter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ThemedSpinnerAdapter;

public abstract class CursorAdapter
extends BaseAdapter
implements Filterable,
CursorFilter.CursorFilterClient,
ThemedSpinnerAdapter {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    protected boolean mAutoRequery;
    @UnsupportedAppUsage
    protected ChangeObserver mChangeObserver;
    @UnsupportedAppUsage
    protected Context mContext;
    @UnsupportedAppUsage
    protected Cursor mCursor;
    protected CursorFilter mCursorFilter;
    @UnsupportedAppUsage
    protected DataSetObserver mDataSetObserver;
    @UnsupportedAppUsage
    protected boolean mDataValid;
    protected Context mDropDownContext;
    protected FilterQueryProvider mFilterQueryProvider;
    @UnsupportedAppUsage
    protected int mRowIDColumn;

    @Deprecated
    public CursorAdapter(Context context, Cursor cursor) {
        this.init(context, cursor, 1);
    }

    public CursorAdapter(Context context, Cursor cursor, int n) {
        this.init(context, cursor, n);
    }

    public CursorAdapter(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public abstract void bindView(View var1, Context var2, Cursor var3);

    @Override
    public void changeCursor(Cursor cursor) {
        if ((cursor = this.swapCursor(cursor)) != null) {
            cursor.close();
        }
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        object = object == null ? "" : object.toString();
        return object;
    }

    @Override
    public int getCount() {
        Cursor cursor;
        if (this.mDataValid && (cursor = this.mCursor) != null) {
            return cursor.getCount();
        }
        return 0;
    }

    @Override
    public Cursor getCursor() {
        return this.mCursor;
    }

    @Override
    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        if (this.mDataValid) {
            Context context;
            Context context2 = context = this.mDropDownContext;
            if (context == null) {
                context2 = this.mContext;
            }
            this.mCursor.moveToPosition(n);
            if (view == null) {
                view = this.newDropDownView(context2, this.mCursor, viewGroup);
            }
            this.bindView(view, context2, this.mCursor);
            return view;
        }
        return null;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        Object object = this.mDropDownContext;
        object = object == null ? null : ((Context)object).getTheme();
        return object;
    }

    @Override
    public Filter getFilter() {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    @Override
    public Object getItem(int n) {
        Cursor cursor;
        if (this.mDataValid && (cursor = this.mCursor) != null) {
            cursor.moveToPosition(n);
            return this.mCursor;
        }
        return null;
    }

    @Override
    public long getItemId(int n) {
        Cursor cursor;
        if (this.mDataValid && (cursor = this.mCursor) != null) {
            if (cursor.moveToPosition(n)) {
                return this.mCursor.getLong(this.mRowIDColumn);
            }
            return 0L;
        }
        return 0L;
    }

    @Override
    public View getView(int n, View object, ViewGroup viewGroup) {
        if (this.mDataValid) {
            if (this.mCursor.moveToPosition(n)) {
                if (object == null) {
                    object = this.newView(this.mContext, this.mCursor, viewGroup);
                }
                this.bindView((View)object, this.mContext, this.mCursor);
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("couldn't move cursor to position ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        throw new IllegalStateException("this should only be called when the cursor is valid");
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    void init(Context object, Cursor cursor, int n) {
        boolean bl = false;
        if ((n & 1) == 1) {
            n |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        if (cursor != null) {
            bl = true;
        }
        this.mCursor = cursor;
        this.mDataValid = bl;
        this.mContext = object;
        int n2 = bl ? cursor.getColumnIndexOrThrow("_id") : -1;
        this.mRowIDColumn = n2;
        if ((n & 2) == 2) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (bl) {
            object = this.mChangeObserver;
            if (object != null) {
                cursor.registerContentObserver((ContentObserver)object);
            }
            if ((object = this.mDataSetObserver) != null) {
                cursor.registerDataSetObserver((DataSetObserver)object);
            }
        }
    }

    @Deprecated
    protected void init(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.newView(context, cursor, viewGroup);
    }

    public abstract View newView(Context var1, Cursor var2, ViewGroup var3);

    protected void onContentChanged() {
        Cursor cursor;
        if (this.mAutoRequery && (cursor = this.mCursor) != null && !cursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
        if (filterQueryProvider != null) {
            return filterQueryProvider.runQuery(charSequence);
        }
        return this.mCursor;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        this.mDropDownContext = theme == null ? null : (theme == this.mContext.getTheme() ? this.mContext : new ContextThemeWrapper(this.mContext, theme));
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    public Cursor swapCursor(Cursor cursor) {
        Object object;
        if (cursor == this.mCursor) {
            return null;
        }
        Cursor cursor2 = this.mCursor;
        if (cursor2 != null) {
            object = this.mChangeObserver;
            if (object != null) {
                cursor2.unregisterContentObserver((ContentObserver)object);
            }
            if ((object = this.mDataSetObserver) != null) {
                cursor2.unregisterDataSetObserver((DataSetObserver)object);
            }
        }
        this.mCursor = cursor;
        if (cursor != null) {
            object = this.mChangeObserver;
            if (object != null) {
                cursor.registerContentObserver((ContentObserver)object);
            }
            if ((object = this.mDataSetObserver) != null) {
                cursor.registerDataSetObserver((DataSetObserver)object);
            }
            this.mRowIDColumn = cursor.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            this.notifyDataSetChanged();
        } else {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            this.notifyDataSetInvalidated();
        }
        return cursor2;
    }

    private class ChangeObserver
    extends ContentObserver {
        public ChangeObserver() {
            super(new Handler());
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean bl) {
            CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver
    extends DataSetObserver {
        private MyDataSetObserver() {
        }

        @Override
        public void onChanged() {
            CursorAdapter cursorAdapter = CursorAdapter.this;
            cursorAdapter.mDataValid = true;
            cursorAdapter.notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            CursorAdapter cursorAdapter = CursorAdapter.this;
            cursorAdapter.mDataValid = false;
            cursorAdapter.notifyDataSetInvalidated();
        }
    }

}

