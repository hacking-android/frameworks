/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayAdapter<T>
extends BaseAdapter
implements Filterable,
ThemedSpinnerAdapter {
    private final Context mContext;
    private LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private int mFieldId = 0;
    private ArrayAdapter<T> mFilter;
    private final LayoutInflater mInflater;
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    private boolean mNotifyOnChange = true;
    @UnsupportedAppUsage
    private List<T> mObjects;
    private boolean mObjectsFromResources;
    @UnsupportedAppUsage
    private ArrayList<T> mOriginalValues;
    private final int mResource;

    public ArrayAdapter(Context context, int n) {
        this(context, n, 0, new ArrayList());
    }

    public ArrayAdapter(Context context, int n, int n2) {
        this(context, n, n2, new ArrayList());
    }

    public ArrayAdapter(Context context, int n, int n2, List<T> list) {
        this(context, n, n2, list, false);
    }

    private ArrayAdapter(Context context, int n, int n2, List<T> list, boolean bl) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDropDownResource = n;
        this.mResource = n;
        this.mObjects = list;
        this.mObjectsFromResources = bl;
        this.mFieldId = n2;
    }

    public ArrayAdapter(Context context, int n, int n2, T[] arrT) {
        this(context, n, n2, Arrays.asList(arrT));
    }

    public ArrayAdapter(Context context, int n, List<T> list) {
        this(context, n, 0, list);
    }

    public ArrayAdapter(Context context, int n, T[] arrT) {
        this(context, n, 0, Arrays.asList(arrT));
    }

    public static ArrayAdapter<CharSequence> createFromResource(Context context, int n, int n2) {
        return new ArrayAdapter<CharSequence>(context, n2, 0, Arrays.asList(context.getResources().getTextArray(n)), true);
    }

    private View createViewFromResource(LayoutInflater object, int n, View object2, ViewGroup viewGroup, int n2) {
        block7 : {
            block6 : {
                object = object2 == null ? ((LayoutInflater)object).inflate(n2, viewGroup, false) : object2;
                try {
                    if (this.mFieldId == 0) {
                        object2 = (TextView)object;
                        break block6;
                    }
                    object2 = (TextView)((View)object).findViewById(this.mFieldId);
                    if (object2 == null) break block7;
                }
                catch (ClassCastException classCastException) {
                    Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
                    throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", classCastException);
                }
            }
            viewGroup = this.getItem(n);
            if (viewGroup instanceof CharSequence) {
                ((TextView)object2).setText((CharSequence)((Object)viewGroup));
            } else {
                ((TextView)object2).setText(((Object)viewGroup).toString());
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to find view with ID ");
        ((StringBuilder)object).append(this.mContext.getResources().getResourceName(this.mFieldId));
        ((StringBuilder)object).append(" in item layout");
        object2 = new RuntimeException(((StringBuilder)object).toString());
        throw object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void add(T t) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            this.mOriginalValues.add(t);
        } else {
            this.mObjects.add(t);
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void addAll(Collection<? extends T> collection) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            this.mOriginalValues.addAll(collection);
        } else {
            this.mObjects.addAll(collection);
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void addAll(T ... arrT) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            Collections.addAll(this.mOriginalValues, arrT);
        } else {
            Collections.addAll(this.mObjects, arrT);
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void clear() {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            this.mOriginalValues.clear();
        } else {
            this.mObjects.clear();
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        Object object = super.getAutofillOptions();
        if (object != null) {
            return object;
        }
        if (this.mObjectsFromResources && (object = this.mObjects) != null && !object.isEmpty()) {
            object = new CharSequence[this.mObjects.size()];
            this.mObjects.toArray((T[])object);
            return object;
        }
        return null;
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override
    public int getCount() {
        return this.mObjects.size();
    }

    @Override
    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater;
        LayoutInflater layoutInflater2 = layoutInflater = this.mDropDownInflater;
        if (layoutInflater == null) {
            layoutInflater2 = this.mInflater;
        }
        return this.createViewFromResource(layoutInflater2, n, view, viewGroup, this.mDropDownResource);
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
        Object object = this.mDropDownInflater;
        object = object == null ? null : ((LayoutInflater)object).getContext().getTheme();
        return object;
    }

    @Override
    public Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new ArrayFilter();
        }
        return this.mFilter;
    }

    public T getItem(int n) {
        return this.mObjects.get(n);
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    public int getPosition(T t) {
        return this.mObjects.indexOf(t);
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        return this.createViewFromResource(this.mInflater, n, view, viewGroup, this.mResource);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void insert(T t, int n) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            this.mOriginalValues.add(n, t);
        } else {
            this.mObjects.add(n, t);
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mNotifyOnChange = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void remove(T t) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            this.mOriginalValues.remove(t);
        } else {
            this.mObjects.remove(t);
        }
        this.mObjectsFromResources = false;
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    public void setDropDownViewResource(int n) {
        this.mDropDownResource = n;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        this.mDropDownInflater = theme == null ? null : (theme == this.mInflater.getContext().getTheme() ? this.mInflater : LayoutInflater.from(new ContextThemeWrapper(this.mContext, theme)));
    }

    public void setNotifyOnChange(boolean bl) {
        this.mNotifyOnChange = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void sort(Comparator<? super T> comparator) {
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mOriginalValues != null) {
            Collections.sort(this.mOriginalValues, comparator);
        } else {
            Collections.sort(this.mObjects, comparator);
        }
        // MONITOREXIT : object
        if (!this.mNotifyOnChange) return;
        this.notifyDataSetChanged();
    }

    private class ArrayFilter
    extends Filter {
        private ArrayFilter() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected Filter.FilterResults performFiltering(CharSequence object) {
            ArrayList arrayList;
            ArrayList arrayList2;
            ArrayList arrayList3;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (ArrayAdapter.this.mOriginalValues == null) {
                arrayList2 = ArrayAdapter.this.mLock;
                synchronized (arrayList2) {
                    arrayList = ArrayAdapter.this;
                    arrayList3 = new ArrayList(ArrayAdapter.this.mObjects);
                    ((ArrayAdapter)((Object)arrayList)).mOriginalValues = arrayList3;
                }
            }
            if (object != null && object.length() != 0) {
                object = object.toString().toLowerCase();
                arrayList = ArrayAdapter.this.mLock;
                synchronized (arrayList) {
                    arrayList2 = new ArrayList(ArrayAdapter.this.mOriginalValues);
                }
            } else {
                object = ArrayAdapter.this.mLock;
                synchronized (object) {
                    arrayList2 = new ArrayList(ArrayAdapter.this.mOriginalValues);
                }
                filterResults.values = arrayList2;
                filterResults.count = arrayList2.size();
                return filterResults;
            }
            int n = arrayList2.size();
            arrayList = new ArrayList();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                    return filterResults;
                }
                arrayList3 = arrayList2.get(n2);
                String[] arrstring = ((Object)arrayList3).toString().toLowerCase();
                if (arrstring.startsWith((String)object)) {
                    arrayList.add(arrayList3);
                } else {
                    arrstring = arrstring.split(" ");
                    int n3 = arrstring.length;
                    for (int i = 0; i < n3; ++i) {
                        if (!arrstring[i].startsWith((String)object)) continue;
                        arrayList.add(arrayList3);
                        break;
                    }
                }
                ++n2;
            } while (true);
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ArrayAdapter.this.mObjects = (List)filterResults.values;
            if (filterResults.count > 0) {
                ArrayAdapter.this.notifyDataSetChanged();
            } else {
                ArrayAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

}

