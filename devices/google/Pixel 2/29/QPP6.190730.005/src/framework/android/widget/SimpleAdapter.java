/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimpleAdapter
extends BaseAdapter
implements Filterable,
ThemedSpinnerAdapter {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private List<? extends Map<String, ?>> mData;
    private LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private SimpleFilter mFilter;
    private String[] mFrom;
    private final LayoutInflater mInflater;
    private int mResource;
    private int[] mTo;
    private ArrayList<Map<String, ?>> mUnfilteredData;
    private ViewBinder mViewBinder;

    public SimpleAdapter(Context context, List<? extends Map<String, ?>> list, int n, String[] arrstring, int[] arrn) {
        this.mData = list;
        this.mDropDownResource = n;
        this.mResource = n;
        this.mFrom = arrstring;
        this.mTo = arrn;
        this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    private void bindView(int n, View object) {
        Map<String, ?> map = this.mData.get(n);
        if (map == null) {
            return;
        }
        ViewBinder viewBinder = this.mViewBinder;
        String[] arrstring = this.mFrom;
        int[] arrn = this.mTo;
        int n2 = arrn.length;
        for (n = 0; n < n2; ++n) {
            Object t = ((View)object).findViewById(arrn[n]);
            if (t == null) continue;
            Object obj = map.get(arrstring[n]);
            CharSequence charSequence = obj == null ? "" : obj.toString();
            String string2 = charSequence;
            if (charSequence == null) {
                string2 = "";
            }
            boolean bl = false;
            if (viewBinder != null) {
                bl = viewBinder.setViewValue((View)t, obj, string2);
            }
            if (bl) continue;
            if (t instanceof Checkable) {
                if (obj instanceof Boolean) {
                    ((Checkable)t).setChecked((Boolean)obj);
                    continue;
                }
                if (t instanceof TextView) {
                    this.setViewText((TextView)t, string2);
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(t.getClass().getName());
                ((StringBuilder)charSequence).append(" should be bound to a Boolean, not a ");
                object = obj == null ? "<unknown type>" : obj.getClass();
                ((StringBuilder)charSequence).append(object);
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            if (t instanceof TextView) {
                this.setViewText((TextView)t, string2);
                continue;
            }
            if (t instanceof ImageView) {
                if (obj instanceof Integer) {
                    this.setViewImage((ImageView)t, (Integer)obj);
                    continue;
                }
                this.setViewImage((ImageView)t, string2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(t.getClass().getName());
            ((StringBuilder)object).append(" is not a  view that can be bounds by this SimpleAdapter");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
    }

    private View createViewFromResource(LayoutInflater layoutInflater, int n, View view, ViewGroup viewGroup, int n2) {
        if (view == null) {
            view = layoutInflater.inflate(n2, viewGroup, false);
        }
        this.bindView(n, view);
        return view;
    }

    @Override
    public int getCount() {
        return this.mData.size();
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
            this.mFilter = new SimpleFilter();
        }
        return this.mFilter;
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
        return this.createViewFromResource(this.mInflater, n, view, viewGroup, this.mResource);
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setDropDownViewResource(int n) {
        this.mDropDownResource = n;
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
        this.mDropDownInflater = theme == null ? null : (theme == this.mInflater.getContext().getTheme() ? this.mInflater : LayoutInflater.from(new ContextThemeWrapper(this.mInflater.getContext(), theme)));
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView imageView, int n) {
        imageView.setImageResource(n);
    }

    public void setViewImage(ImageView imageView, String string2) {
        try {
            imageView.setImageResource(Integer.parseInt(string2));
        }
        catch (NumberFormatException numberFormatException) {
            imageView.setImageURI(Uri.parse(string2));
        }
    }

    public void setViewText(TextView textView, String string2) {
        textView.setText(string2);
    }

    private class SimpleFilter
    extends Filter {
        private SimpleFilter() {
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence object) {
            String[] arrstring;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (SimpleAdapter.this.mUnfilteredData == null) {
                arrstring = SimpleAdapter.this;
                ((SimpleAdapter)arrstring).mUnfilteredData = new ArrayList(((SimpleAdapter)arrstring).mData);
            }
            if (object != null && object.length() != 0) {
                object = object.toString().toLowerCase();
                ArrayList arrayList = SimpleAdapter.this.mUnfilteredData;
                int n = arrayList.size();
                ArrayList<Map> arrayList2 = new ArrayList<Map>(n);
                for (int i = 0; i < n; ++i) {
                    Map map = (Map)arrayList.get(i);
                    if (map == null) continue;
                    int n2 = SimpleAdapter.this.mTo.length;
                    block1 : for (int j = 0; j < n2; ++j) {
                        arrstring = ((String)map.get(SimpleAdapter.this.mFrom[j])).split(" ");
                        int n3 = arrstring.length;
                        for (int k = 0; k < n3; ++k) {
                            if (!arrstring[k].toLowerCase().startsWith((String)object)) continue;
                            arrayList2.add(map);
                            continue block1;
                        }
                    }
                }
                filterResults.values = arrayList2;
                filterResults.count = arrayList2.size();
            } else {
                filterResults.values = object = SimpleAdapter.this.mUnfilteredData;
                filterResults.count = ((ArrayList)object).size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            SimpleAdapter.this.mData = (List)filterResults.values;
            if (filterResults.count > 0) {
                SimpleAdapter.this.notifyDataSetChanged();
            } else {
                SimpleAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Object var2, String var3);
    }

}

