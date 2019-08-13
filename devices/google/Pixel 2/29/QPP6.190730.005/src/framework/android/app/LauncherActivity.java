/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class LauncherActivity
extends ListActivity {
    IconResizer mIconResizer;
    Intent mIntent;
    PackageManager mPackageManager;

    private void updateAlertTitle() {
        TextView textView = (TextView)this.findViewById(16908711);
        if (textView != null) {
            textView.setText(this.getTitle());
        }
    }

    private void updateButtonText() {
        Button button = (Button)this.findViewById(16908313);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    LauncherActivity.this.finish();
                }
            });
        }
    }

    protected Intent getTargetIntent() {
        return new Intent();
    }

    protected Intent intentForPosition(int n) {
        return ((ActivityAdapter)this.mAdapter).intentForPosition(n);
    }

    protected ListItem itemForPosition(int n) {
        return ((ActivityAdapter)this.mAdapter).itemForPosition(n);
    }

    public List<ListItem> makeListItems() {
        List<ResolveInfo> list = this.onQueryPackageManager(this.mIntent);
        this.onSortResultList(list);
        ArrayList<ListItem> arrayList = new ArrayList<ListItem>(list.size());
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            ResolveInfo resolveInfo = list.get(i);
            arrayList.add(new ListItem(this.mPackageManager, resolveInfo, null));
        }
        return arrayList;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPackageManager = this.getPackageManager();
        if (!this.mPackageManager.hasSystemFeature("android.hardware.type.watch")) {
            this.requestWindowFeature(5);
            this.setProgressBarIndeterminateVisibility(true);
        }
        this.onSetContentView();
        this.mIconResizer = new IconResizer();
        this.mIntent = new Intent(this.getTargetIntent());
        this.mIntent.setComponent(null);
        this.mAdapter = new ActivityAdapter(this.mIconResizer);
        this.setListAdapter(this.mAdapter);
        this.getListView().setTextFilterEnabled(true);
        this.updateAlertTitle();
        this.updateButtonText();
        if (!this.mPackageManager.hasSystemFeature("android.hardware.type.watch")) {
            this.setProgressBarIndeterminateVisibility(false);
        }
    }

    protected boolean onEvaluateShowIcons() {
        return true;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int n, long l) {
        this.startActivity(this.intentForPosition(n));
    }

    protected List<ResolveInfo> onQueryPackageManager(Intent intent) {
        return this.mPackageManager.queryIntentActivities(intent, 0);
    }

    protected void onSetContentView() {
        this.setContentView(17367077);
    }

    protected void onSortResultList(List<ResolveInfo> list) {
        Collections.sort(list, new ResolveInfo.DisplayNameComparator(this.mPackageManager));
    }

    @Override
    public void setTitle(int n) {
        super.setTitle(n);
        this.updateAlertTitle();
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.updateAlertTitle();
    }

    private class ActivityAdapter
    extends BaseAdapter
    implements Filterable {
        private final Object lock = new Object();
        protected List<ListItem> mActivitiesList;
        private Filter mFilter;
        protected final IconResizer mIconResizer;
        protected final LayoutInflater mInflater;
        private ArrayList<ListItem> mOriginalValues;
        private final boolean mShowIcons;

        public ActivityAdapter(IconResizer iconResizer) {
            this.mIconResizer = iconResizer;
            this.mInflater = (LayoutInflater)LauncherActivity.this.getSystemService("layout_inflater");
            this.mShowIcons = LauncherActivity.this.onEvaluateShowIcons();
            this.mActivitiesList = LauncherActivity.this.makeListItems();
        }

        private void bindView(View view, ListItem listItem) {
            view = (TextView)view;
            ((TextView)view).setText(listItem.label);
            if (this.mShowIcons) {
                if (listItem.icon == null) {
                    listItem.icon = this.mIconResizer.createIconThumbnail(listItem.resolveInfo.loadIcon(LauncherActivity.this.getPackageManager()));
                }
                ((TextView)view).setCompoundDrawablesRelativeWithIntrinsicBounds(listItem.icon, null, null, null);
            }
        }

        @Override
        public int getCount() {
            List<ListItem> list = this.mActivitiesList;
            int n = list != null ? list.size() : 0;
            return n;
        }

        @Override
        public Filter getFilter() {
            if (this.mFilter == null) {
                this.mFilter = new ArrayFilter();
            }
            return this.mFilter;
        }

        @Override
        public Object getItem(int n) {
            return n;
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(17367078, viewGroup, false);
            }
            this.bindView(view, this.mActivitiesList.get(n));
            return view;
        }

        public Intent intentForPosition(int n) {
            if (this.mActivitiesList == null) {
                return null;
            }
            Intent intent = new Intent(LauncherActivity.this.mIntent);
            ListItem listItem = this.mActivitiesList.get(n);
            intent.setClassName(listItem.packageName, listItem.className);
            if (listItem.extras != null) {
                intent.putExtras(listItem.extras);
            }
            return intent;
        }

        public ListItem itemForPosition(int n) {
            List<ListItem> list = this.mActivitiesList;
            if (list == null) {
                return null;
            }
            return list.get(n);
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
            protected Filter.FilterResults performFiltering(CharSequence arrstring) {
                Object object;
                ArrayList<ListItem> arrayList;
                Object object2;
                Filter.FilterResults filterResults = new Filter.FilterResults();
                if (ActivityAdapter.this.mOriginalValues == null) {
                    object2 = ActivityAdapter.this.lock;
                    synchronized (object2) {
                        object = ActivityAdapter.this;
                        arrayList = new ArrayList<ListItem>(ActivityAdapter.this.mActivitiesList);
                        ((ActivityAdapter)object).mOriginalValues = arrayList;
                    }
                }
                if (arrstring == null || arrstring.length() == 0) {
                    arrstring = ActivityAdapter.this.lock;
                    synchronized (arrstring) {
                        object2 = new ArrayList(ActivityAdapter.this.mOriginalValues);
                        filterResults.values = object2;
                        filterResults.count = ((ArrayList)object2).size();
                        return filterResults;
                    }
                }
                String string2 = arrstring.toString().toLowerCase();
                arrayList = ActivityAdapter.this.mOriginalValues;
                int n = arrayList.size();
                object = new ArrayList(n);
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        filterResults.values = object;
                        filterResults.count = ((ArrayList)object).size();
                        return filterResults;
                    }
                    object2 = arrayList.get(n2);
                    arrstring = ((ListItem)object2).label.toString().toLowerCase().split(" ");
                    int n3 = arrstring.length;
                    for (int i = 0; i < n3; ++i) {
                        if (!arrstring[i].startsWith(string2)) continue;
                        ((ArrayList)object).add(object2);
                        break;
                    }
                    ++n2;
                } while (true);
            }

            @Override
            protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                ActivityAdapter.this.mActivitiesList = (List)filterResults.values;
                if (filterResults.count > 0) {
                    ActivityAdapter.this.notifyDataSetChanged();
                } else {
                    ActivityAdapter.this.notifyDataSetInvalidated();
                }
            }
        }

    }

    public class IconResizer {
        private Canvas mCanvas = new Canvas();
        private int mIconHeight = -1;
        private int mIconWidth = -1;
        private final Rect mOldBounds = new Rect();

        public IconResizer() {
            int n;
            this.mCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
            this.mIconHeight = n = (int)LauncherActivity.this.getResources().getDimension(17104896);
            this.mIconWidth = n;
        }

        public Drawable createIconThumbnail(Drawable drawable2) {
            Object object;
            int n = this.mIconWidth;
            int n2 = this.mIconHeight;
            int n3 = drawable2.getIntrinsicWidth();
            int n4 = drawable2.getIntrinsicHeight();
            if (drawable2 instanceof PaintDrawable) {
                object = (PaintDrawable)drawable2;
                ((ShapeDrawable)object).setIntrinsicWidth(n);
                ((ShapeDrawable)object).setIntrinsicHeight(n2);
            }
            object = drawable2;
            if (n > 0) {
                object = drawable2;
                if (n2 > 0) {
                    if (n >= n3 && n2 >= n4) {
                        object = drawable2;
                        if (n3 < n) {
                            object = drawable2;
                            if (n4 < n2) {
                                object = Bitmap.Config.ARGB_8888;
                                object = Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, (Bitmap.Config)((Object)object));
                                Canvas canvas = this.mCanvas;
                                canvas.setBitmap((Bitmap)object);
                                this.mOldBounds.set(drawable2.getBounds());
                                int n5 = (n - n3) / 2;
                                n = (n2 - n4) / 2;
                                drawable2.setBounds(n5, n, n5 + n3, n + n4);
                                drawable2.draw(canvas);
                                drawable2.setBounds(this.mOldBounds);
                                object = new BitmapDrawable(LauncherActivity.this.getResources(), (Bitmap)object);
                                canvas.setBitmap(null);
                            }
                        }
                    } else {
                        int n6;
                        float f = (float)n3 / (float)n4;
                        if (n3 > n4) {
                            n6 = (int)((float)n / f);
                        } else {
                            n6 = n2;
                            if (n4 > n3) {
                                n = (int)((float)n2 * f);
                                n6 = n2;
                            }
                        }
                        object = drawable2.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                        object = Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, (Bitmap.Config)((Object)object));
                        Canvas canvas = this.mCanvas;
                        canvas.setBitmap((Bitmap)object);
                        this.mOldBounds.set(drawable2.getBounds());
                        n3 = (this.mIconWidth - n) / 2;
                        n2 = (this.mIconHeight - n6) / 2;
                        drawable2.setBounds(n3, n2, n3 + n, n2 + n6);
                        drawable2.draw(canvas);
                        drawable2.setBounds(this.mOldBounds);
                        object = new BitmapDrawable(LauncherActivity.this.getResources(), (Bitmap)object);
                        canvas.setBitmap(null);
                    }
                }
            }
            return object;
        }
    }

    public static class ListItem {
        public String className;
        public Bundle extras;
        public Drawable icon;
        public CharSequence label;
        public String packageName;
        public ResolveInfo resolveInfo;

        public ListItem() {
        }

        ListItem(PackageManager packageManager, ResolveInfo resolveInfo, IconResizer iconResizer) {
            ActivityInfo activityInfo;
            this.resolveInfo = resolveInfo;
            this.label = resolveInfo.loadLabel(packageManager);
            ComponentInfo componentInfo = activityInfo = resolveInfo.activityInfo;
            if (activityInfo == null) {
                componentInfo = resolveInfo.serviceInfo;
            }
            if (this.label == null && componentInfo != null) {
                this.label = resolveInfo.activityInfo.name;
            }
            if (iconResizer != null) {
                this.icon = iconResizer.createIconThumbnail(resolveInfo.loadIcon(packageManager));
            }
            this.packageName = componentInfo.applicationInfo.packageName;
            this.className = componentInfo.name;
        }
    }

}

