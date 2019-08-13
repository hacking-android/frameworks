/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.media.MediaRouter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Comparator;

public class MediaRouteChooserDialog
extends Dialog {
    private RouteAdapter mAdapter;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private Button mExtendedSettingsButton;
    private View.OnClickListener mExtendedSettingsClickListener;
    private ListView mListView;
    private int mRouteTypes;
    private final MediaRouter mRouter;

    public MediaRouteChooserDialog(Context context, int n) {
        super(context, n);
        this.mRouter = (MediaRouter)context.getSystemService("media_router");
        this.mCallback = new MediaRouterCallback();
    }

    static boolean isLightTheme(Context object) {
        TypedValue typedValue = new TypedValue();
        object = ((Context)object).getTheme();
        boolean bl = true;
        if (!((Resources.Theme)object).resolveAttribute(16844176, typedValue, true) || typedValue.data == 0) {
            bl = false;
        }
        return bl;
    }

    private void updateExtendedSettingsButton() {
        Button button = this.mExtendedSettingsButton;
        if (button != null) {
            button.setOnClickListener(this.mExtendedSettingsClickListener);
            button = this.mExtendedSettingsButton;
            int n = this.mExtendedSettingsClickListener != null ? 0 : 8;
            button.setVisibility(n);
        }
    }

    public int getRouteTypes() {
        return this.mRouteTypes;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mRouteTypes, this.mCallback, 1);
        this.refreshRoutes();
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.getWindow().requestFeature(3);
        this.setContentView(17367185);
        int n = this.mRouteTypes == 4 ? 17040311 : 17040310;
        this.setTitle(n);
        object = this.getWindow();
        n = MediaRouteChooserDialog.isLightTheme(this.getContext()) ? 17302647 : 17302646;
        ((Window)object).setFeatureDrawableResource(3, n);
        this.mAdapter = new RouteAdapter(this.getContext());
        this.mListView = (ListView)this.findViewById(16909099);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(this.mAdapter);
        this.mListView.setEmptyView((View)this.findViewById(16908292));
        this.mExtendedSettingsButton = (Button)this.findViewById(16909098);
        this.updateExtendedSettingsButton();
    }

    @Override
    public void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        super.onDetachedFromWindow();
    }

    public boolean onFilterRoute(MediaRouter.RouteInfo routeInfo) {
        boolean bl = !routeInfo.isDefault() && routeInfo.isEnabled() && routeInfo.matchesTypes(this.mRouteTypes);
        return bl;
    }

    public void refreshRoutes() {
        if (this.mAttachedToWindow) {
            this.mAdapter.update();
        }
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            this.updateExtendedSettingsButton();
        }
    }

    public void setRouteTypes(int n) {
        if (this.mRouteTypes != n) {
            this.mRouteTypes = n;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(n, this.mCallback, 1);
            }
            this.refreshRoutes();
        }
    }

    private final class MediaRouterCallback
    extends MediaRouter.SimpleCallback {
        private MediaRouterCallback() {
        }

        @Override
        public void onRouteAdded(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override
        public void onRouteChanged(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override
        public void onRouteRemoved(MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.refreshRoutes();
        }

        @Override
        public void onRouteSelected(MediaRouter mediaRouter, int n, MediaRouter.RouteInfo routeInfo) {
            MediaRouteChooserDialog.this.dismiss();
        }
    }

    private final class RouteAdapter
    extends ArrayAdapter<MediaRouter.RouteInfo>
    implements AdapterView.OnItemClickListener {
        private final LayoutInflater mInflater;

        public RouteAdapter(Context context) {
            super(context, 0);
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public View getView(int n, View view, ViewGroup object) {
            View view2;
            view = view2 = view;
            if (view2 == null) {
                view = this.mInflater.inflate(17367187, (ViewGroup)object, false);
            }
            object = (MediaRouter.RouteInfo)this.getItem(n);
            Object object2 = (TextView)view.findViewById(16908308);
            view2 = (TextView)view.findViewById(16908309);
            ((TextView)object2).setText(((MediaRouter.RouteInfo)object).getName());
            object2 = ((MediaRouter.RouteInfo)object).getDescription();
            if (TextUtils.isEmpty((CharSequence)object2)) {
                view2.setVisibility(8);
                ((TextView)view2).setText("");
            } else {
                view2.setVisibility(0);
                ((TextView)view2).setText((CharSequence)object2);
            }
            view.setEnabled(((MediaRouter.RouteInfo)object).isEnabled());
            return view;
        }

        @Override
        public boolean isEnabled(int n) {
            return ((MediaRouter.RouteInfo)this.getItem(n)).isEnabled();
        }

        @Override
        public void onItemClick(AdapterView<?> object, View view, int n, long l) {
            object = (MediaRouter.RouteInfo)this.getItem(n);
            if (((MediaRouter.RouteInfo)object).isEnabled()) {
                ((MediaRouter.RouteInfo)object).select();
                MediaRouteChooserDialog.this.dismiss();
            }
        }

        public void update() {
            this.clear();
            int n = MediaRouteChooserDialog.this.mRouter.getRouteCount();
            for (int i = 0; i < n; ++i) {
                MediaRouter.RouteInfo routeInfo = MediaRouteChooserDialog.this.mRouter.getRouteAt(i);
                if (!MediaRouteChooserDialog.this.onFilterRoute(routeInfo)) continue;
                this.add(routeInfo);
            }
            this.sort(RouteComparator.sInstance);
            this.notifyDataSetChanged();
        }
    }

    private static final class RouteComparator
    implements Comparator<MediaRouter.RouteInfo> {
        public static final RouteComparator sInstance = new RouteComparator();

        private RouteComparator() {
        }

        @Override
        public int compare(MediaRouter.RouteInfo routeInfo, MediaRouter.RouteInfo routeInfo2) {
            return routeInfo.getName().toString().compareTo(routeInfo2.getName().toString());
        }
    }

}

