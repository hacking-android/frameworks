/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CursorFilter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorTreeAdapter
extends BaseExpandableListAdapter
implements Filterable,
CursorFilter.CursorFilterClient {
    private boolean mAutoRequery;
    SparseArray<MyCursorHelper> mChildrenCursorHelpers;
    private Context mContext;
    CursorFilter mCursorFilter;
    FilterQueryProvider mFilterQueryProvider;
    MyCursorHelper mGroupCursorHelper;
    private Handler mHandler;

    public CursorTreeAdapter(Cursor cursor, Context context) {
        this.init(cursor, context, true);
    }

    public CursorTreeAdapter(Cursor cursor, Context context, boolean bl) {
        this.init(cursor, context, bl);
    }

    private void init(Cursor cursor, Context context, boolean bl) {
        this.mContext = context;
        this.mHandler = new Handler();
        this.mAutoRequery = bl;
        this.mGroupCursorHelper = new MyCursorHelper(cursor);
        this.mChildrenCursorHelpers = new SparseArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void releaseCursorHelpers() {
        synchronized (this) {
            int n = this.mChildrenCursorHelpers.size() - 1;
            do {
                if (n < 0) {
                    this.mChildrenCursorHelpers.clear();
                    return;
                }
                this.mChildrenCursorHelpers.valueAt(n).deactivate();
                --n;
            } while (true);
        }
    }

    protected abstract void bindChildView(View var1, Context var2, Cursor var3, boolean var4);

    protected abstract void bindGroupView(View var1, Context var2, Cursor var3, boolean var4);

    @Override
    public void changeCursor(Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, true);
    }

    @Override
    public String convertToString(Cursor object) {
        object = object == null ? "" : object.toString();
        return object;
    }

    void deactivateChildrenCursorHelper(int n) {
        synchronized (this) {
            MyCursorHelper myCursorHelper = this.getChildrenCursorHelper(n, true);
            this.mChildrenCursorHelpers.remove(n);
            myCursorHelper.deactivate();
            return;
        }
    }

    @Override
    public Cursor getChild(int n, int n2) {
        return this.getChildrenCursorHelper(n, true).moveTo(n2);
    }

    @Override
    public long getChildId(int n, int n2) {
        return this.getChildrenCursorHelper(n, true).getId(n2);
    }

    @Override
    public View getChildView(int n, int n2, boolean bl, View view, ViewGroup viewGroup) {
        Cursor cursor = this.getChildrenCursorHelper(n, true).moveTo(n2);
        if (cursor != null) {
            if (view == null) {
                view = this.newChildView(this.mContext, cursor, bl, viewGroup);
            }
            this.bindChildView(view, this.mContext, cursor, bl);
            return view;
        }
        throw new IllegalStateException("this should only be called when the cursor is valid");
    }

    @Override
    public int getChildrenCount(int n) {
        MyCursorHelper myCursorHelper = this.getChildrenCursorHelper(n, true);
        n = this.mGroupCursorHelper.isValid() && myCursorHelper != null ? myCursorHelper.getCount() : 0;
        return n;
    }

    protected abstract Cursor getChildrenCursor(Cursor var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    MyCursorHelper getChildrenCursorHelper(int n, boolean bl) {
        synchronized (this) {
            Object object;
            Object object2 = object = this.mChildrenCursorHelpers.get(n);
            if (object == null) {
                object2 = this.mGroupCursorHelper.moveTo(n);
                if (object2 == null) {
                    return null;
                }
                object = this.getChildrenCursor(this.mGroupCursorHelper.getCursor());
                object2 = new MyCursorHelper((Cursor)object);
                this.mChildrenCursorHelpers.put(n, (MyCursorHelper)object2);
            }
            return object2;
        }
    }

    @Override
    public Cursor getCursor() {
        return this.mGroupCursorHelper.getCursor();
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
    public Cursor getGroup(int n) {
        return this.mGroupCursorHelper.moveTo(n);
    }

    @Override
    public int getGroupCount() {
        return this.mGroupCursorHelper.getCount();
    }

    @Override
    public long getGroupId(int n) {
        return this.mGroupCursorHelper.getId(n);
    }

    @Override
    public View getGroupView(int n, boolean bl, View view, ViewGroup viewGroup) {
        Cursor cursor = this.mGroupCursorHelper.moveTo(n);
        if (cursor != null) {
            if (view == null) {
                view = this.newGroupView(this.mContext, cursor, bl, viewGroup);
            }
            this.bindGroupView(view, this.mContext, cursor, bl);
            return view;
        }
        throw new IllegalStateException("this should only be called when the cursor is valid");
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int n, int n2) {
        return true;
    }

    protected abstract View newChildView(Context var1, Cursor var2, boolean var3, ViewGroup var4);

    protected abstract View newGroupView(Context var1, Cursor var2, boolean var3, ViewGroup var4);

    @Override
    public void notifyDataSetChanged() {
        this.notifyDataSetChanged(true);
    }

    public void notifyDataSetChanged(boolean bl) {
        if (bl) {
            this.releaseCursorHelpers();
        }
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        this.releaseCursorHelpers();
        super.notifyDataSetInvalidated();
    }

    @Override
    public void onGroupCollapsed(int n) {
        this.deactivateChildrenCursorHelper(n);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
        if (filterQueryProvider != null) {
            return filterQueryProvider.runQuery(charSequence);
        }
        return this.mGroupCursorHelper.getCursor();
    }

    public void setChildrenCursor(int n, Cursor cursor) {
        this.getChildrenCursorHelper(n, false).changeCursor(cursor, false);
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    public void setGroupCursor(Cursor cursor) {
        this.mGroupCursorHelper.changeCursor(cursor, false);
    }

    class MyCursorHelper {
        private MyContentObserver mContentObserver;
        private Cursor mCursor;
        private MyDataSetObserver mDataSetObserver;
        private boolean mDataValid;
        private int mRowIDColumn;

        MyCursorHelper(Cursor cursor) {
            boolean bl = cursor != null;
            this.mCursor = cursor;
            this.mDataValid = bl;
            int n = bl ? cursor.getColumnIndex("_id") : -1;
            this.mRowIDColumn = n;
            this.mContentObserver = new MyContentObserver();
            this.mDataSetObserver = new MyDataSetObserver();
            if (bl) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }

        void changeCursor(Cursor cursor, boolean bl) {
            if (cursor == this.mCursor) {
                return;
            }
            this.deactivate();
            this.mCursor = cursor;
            if (cursor != null) {
                cursor.registerContentObserver(this.mContentObserver);
                cursor.registerDataSetObserver(this.mDataSetObserver);
                this.mRowIDColumn = cursor.getColumnIndex("_id");
                this.mDataValid = true;
                CursorTreeAdapter.this.notifyDataSetChanged(bl);
            } else {
                this.mRowIDColumn = -1;
                this.mDataValid = false;
                CursorTreeAdapter.this.notifyDataSetInvalidated();
            }
        }

        void deactivate() {
            Cursor cursor = this.mCursor;
            if (cursor == null) {
                return;
            }
            cursor.unregisterContentObserver(this.mContentObserver);
            this.mCursor.unregisterDataSetObserver(this.mDataSetObserver);
            this.mCursor.close();
            this.mCursor = null;
        }

        int getCount() {
            Cursor cursor;
            if (this.mDataValid && (cursor = this.mCursor) != null) {
                return cursor.getCount();
            }
            return 0;
        }

        Cursor getCursor() {
            return this.mCursor;
        }

        long getId(int n) {
            Cursor cursor;
            if (this.mDataValid && (cursor = this.mCursor) != null) {
                if (cursor.moveToPosition(n)) {
                    return this.mCursor.getLong(this.mRowIDColumn);
                }
                return 0L;
            }
            return 0L;
        }

        boolean isValid() {
            boolean bl = this.mDataValid && this.mCursor != null;
            return bl;
        }

        Cursor moveTo(int n) {
            Cursor cursor;
            if (this.mDataValid && (cursor = this.mCursor) != null && cursor.moveToPosition(n)) {
                return this.mCursor;
            }
            return null;
        }

        private class MyContentObserver
        extends ContentObserver {
            public MyContentObserver() {
                super(CursorTreeAdapter.this.mHandler);
            }

            @Override
            public boolean deliverSelfNotifications() {
                return true;
            }

            @Override
            public void onChange(boolean bl) {
                if (CursorTreeAdapter.this.mAutoRequery && MyCursorHelper.this.mCursor != null && !MyCursorHelper.this.mCursor.isClosed()) {
                    MyCursorHelper myCursorHelper = MyCursorHelper.this;
                    myCursorHelper.mDataValid = myCursorHelper.mCursor.requery();
                }
            }
        }

        private class MyDataSetObserver
        extends DataSetObserver {
            private MyDataSetObserver() {
            }

            @Override
            public void onChanged() {
                MyCursorHelper.this.mDataValid = true;
                CursorTreeAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onInvalidated() {
                MyCursorHelper.this.mDataValid = false;
                CursorTreeAdapter.this.notifyDataSetInvalidated();
            }
        }

    }

}

