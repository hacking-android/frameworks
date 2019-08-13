/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.appwidget.-$
 *  android.appwidget.-$$Lambda
 *  android.appwidget.-$$Lambda$AppWidgetHostView
 *  android.appwidget.-$$Lambda$AppWidgetHostView$AzPWN1sIsRb7M-0Ss1rK2mksT-o
 */
package android.appwidget;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityOptions;
import android.appwidget.-$;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.appwidget._$$Lambda$AppWidgetHostView$AzPWN1sIsRb7M_0Ss1rK2mksT_o;
import android.appwidget._$$Lambda$AppWidgetHostView$ab7zr5jJn3_7TaWMNA8VPkK4SdQ;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsAdapter;
import android.widget.TextView;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class AppWidgetHostView
extends FrameLayout {
    private static final LayoutInflater.Filter INFLATER_FILTER = _$$Lambda$AppWidgetHostView$AzPWN1sIsRb7M_0Ss1rK2mksT_o.INSTANCE;
    private static final String KEY_JAILED_ARRAY = "jail";
    static final boolean LOGD = false;
    static final String TAG = "AppWidgetHostView";
    static final int VIEW_MODE_CONTENT = 1;
    static final int VIEW_MODE_DEFAULT = 3;
    static final int VIEW_MODE_ERROR = 2;
    static final int VIEW_MODE_NOINIT = 0;
    @UnsupportedAppUsage
    int mAppWidgetId;
    private Executor mAsyncExecutor;
    Context mContext;
    @UnsupportedAppUsage
    AppWidgetProviderInfo mInfo;
    private CancellationSignal mLastExecutionSignal;
    int mLayoutId = -1;
    private RemoteViews.OnClickHandler mOnClickHandler;
    private boolean mOnLightBackground;
    Context mRemoteContext;
    View mView;
    int mViewMode = 0;

    public AppWidgetHostView(Context context) {
        this(context, 17432576, 17432577);
    }

    public AppWidgetHostView(Context context, int n, int n2) {
        super(context);
        this.mContext = context;
        this.setIsRootNamespace(true);
    }

    public AppWidgetHostView(Context context, RemoteViews.OnClickHandler onClickHandler) {
        this(context, 17432576, 17432577);
        this.mOnClickHandler = onClickHandler;
    }

    private void applyContent(View object, boolean bl, Exception exception) {
        View view = object;
        if (object == null) {
            if (this.mViewMode == 2) {
                return;
            }
            if (exception != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error inflating RemoteViews : ");
                ((StringBuilder)object).append(exception.toString());
                Log.w(TAG, ((StringBuilder)object).toString());
            }
            view = this.getErrorView();
            this.mViewMode = 2;
        }
        if (!bl) {
            this.prepareView(view);
            this.addView(view);
        }
        if ((object = this.mView) != view) {
            this.removeView((View)object);
            this.mView = view;
        }
    }

    private int generateId() {
        int n;
        block0 : {
            n = this.getId();
            if (n != -1) break block0;
            n = this.mAppWidgetId;
        }
        return n;
    }

    private Rect getDefaultPadding() {
        return AppWidgetHostView.getDefaultPaddingForWidget(this.mContext, null);
    }

    public static Rect getDefaultPaddingForWidget(Context context, ComponentName componentName, Rect rect) {
        return AppWidgetHostView.getDefaultPaddingForWidget(context, rect);
    }

    private static Rect getDefaultPaddingForWidget(Context object, Rect rect) {
        if (rect == null) {
            rect = new Rect(0, 0, 0, 0);
        } else {
            rect.set(0, 0, 0, 0);
        }
        object = ((Context)object).getResources();
        rect.left = ((Resources)object).getDimensionPixelSize(17105111);
        rect.right = ((Resources)object).getDimensionPixelSize(17105112);
        rect.top = ((Resources)object).getDimensionPixelSize(17105113);
        rect.bottom = ((Resources)object).getDimensionPixelSize(17105110);
        return rect;
    }

    private void inflateAsync(RemoteViews remoteViews) {
        View view;
        this.mRemoteContext = this.getRemoteContext();
        int n = remoteViews.getLayoutId();
        if (n == this.mLayoutId && (view = this.mView) != null) {
            try {
                Context context = this.mContext;
                Executor executor = this.mAsyncExecutor;
                ViewApplyListener viewApplyListener = new ViewApplyListener(remoteViews, n, true);
                this.mLastExecutionSignal = remoteViews.reapplyAsync(context, view, executor, viewApplyListener, this.mOnClickHandler);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.mLastExecutionSignal == null) {
            this.mLastExecutionSignal = remoteViews.applyAsync(this.mContext, this, this.mAsyncExecutor, new ViewApplyListener(remoteViews, n, false), this.mOnClickHandler);
        }
    }

    public static /* synthetic */ void lambda$ab7zr5jJn3-7TaWMNA8VPkK4SdQ(AppWidgetHostView appWidgetHostView, View view) {
        appWidgetHostView.onDefaultViewClicked(view);
    }

    static /* synthetic */ boolean lambda$static$0(Class class_) {
        return class_.isAnnotationPresent(RemoteViews.RemoteView.class);
    }

    private void onDefaultViewClicked(View view) {
        LauncherApps launcherApps;
        Object object;
        if (this.mInfo != null && !(object = (launcherApps = this.getContext().getSystemService(LauncherApps.class)).getActivityList(this.mInfo.provider.getPackageName(), this.mInfo.getProfile())).isEmpty()) {
            object = object.get(0);
            launcherApps.startMainActivity(((LauncherActivityInfo)object).getComponentName(), ((LauncherActivityInfo)object).getUser(), RemoteViews.getSourceBounds(view), null);
        }
    }

    protected void applyRemoteViews(RemoteViews object, boolean bl) {
        boolean bl2 = false;
        boolean bl3 = false;
        Object object2 = null;
        Exception exception = null;
        Exception exception2 = null;
        Object object3 = this.mLastExecutionSignal;
        if (object3 != null) {
            ((CancellationSignal)object3).cancel();
            this.mLastExecutionSignal = null;
        }
        if (object == null) {
            if (this.mViewMode == 3) {
                return;
            }
            object2 = this.getDefaultView();
            this.mLayoutId = -1;
            this.mViewMode = 3;
            bl = bl2;
            exception2 = exception;
        } else {
            object3 = object;
            if (this.mOnLightBackground) {
                object3 = ((RemoteViews)object).getDarkTextViews();
            }
            if (this.mAsyncExecutor != null && bl) {
                this.inflateAsync((RemoteViews)object3);
                return;
            }
            this.mRemoteContext = this.getRemoteContext();
            int n = ((RemoteViews)object3).getLayoutId();
            bl = bl3;
            object = object2;
            exception = exception2;
            if (!false) {
                bl = bl3;
                object = object2;
                exception = exception2;
                if (n == this.mLayoutId) {
                    try {
                        ((RemoteViews)object3).reapply(this.mContext, this.mView, this.mOnClickHandler);
                        object = this.mView;
                        bl = true;
                        exception = exception2;
                    }
                    catch (RuntimeException runtimeException) {
                        object = object2;
                        bl = bl3;
                    }
                }
            }
            object2 = object;
            exception2 = exception;
            if (object == null) {
                try {
                    object2 = ((RemoteViews)object3).apply(this.mContext, this, this.mOnClickHandler);
                    exception2 = exception;
                }
                catch (RuntimeException runtimeException) {
                    object2 = object;
                }
            }
            this.mLayoutId = n;
            this.mViewMode = 1;
        }
        this.applyContent((View)object2, bl, exception2);
    }

    public ActivityOptions createSharedElementActivityOptions(int[] object, String[] arrstring, Intent intent) {
        Context context = this.getContext();
        while (context instanceof ContextWrapper && !(context instanceof Activity)) {
            context = ((ContextWrapper)context).getBaseContext();
        }
        if (!(context instanceof Activity)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        for (int i = 0; i < ((int[])object).length; ++i) {
            Object t = this.findViewById(object[i]);
            if (t == null) continue;
            arrayList.add(Pair.create(t, arrstring[i]));
            bundle.putParcelable(arrstring[i], RemoteViews.getSourceBounds(t));
        }
        if (!arrayList.isEmpty()) {
            intent.putExtra("android.widget.extra.SHARED_ELEMENT_BOUNDS", bundle);
            object = ActivityOptions.makeSceneTransitionAnimation((Activity)context, arrayList.toArray(new Pair[arrayList.size()]));
            ((ActivityOptions)object).setPendingIntentLaunchFlags(268435456);
            return object;
        }
        return null;
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> object) {
        SparseArray<Parcelable> sparseArray = ((SparseArray)object).get(this.generateId());
        object = null;
        if (sparseArray instanceof Bundle) {
            object = ((Bundle)((Object)sparseArray)).getSparseParcelableArray(KEY_JAILED_ARRAY);
        }
        sparseArray = object;
        if (object == null) {
            sparseArray = new SparseArray<Parcelable>();
        }
        try {
            super.dispatchRestoreInstanceState(sparseArray);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed to restoreInstanceState for widget id: ");
            stringBuilder.append(this.mAppWidgetId);
            stringBuilder.append(", ");
            object = this.mInfo;
            object = object == null ? "null" : ((AppWidgetProviderInfo)object).provider;
            stringBuilder.append(object);
            Log.e(TAG, stringBuilder.toString(), exception);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        SparseArray<Parcelable> sparseArray2 = new SparseArray<Parcelable>();
        super.dispatchSaveInstanceState(sparseArray2);
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray(KEY_JAILED_ARRAY, sparseArray2);
        sparseArray.put(this.generateId(), bundle);
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = this.mRemoteContext;
        if (context == null) {
            context = this.mContext;
        }
        return new FrameLayout.LayoutParams(context, attributeSet);
    }

    public int getAppWidgetId() {
        return this.mAppWidgetId;
    }

    public AppWidgetProviderInfo getAppWidgetInfo() {
        return this.mInfo;
    }

    protected View getDefaultView() {
        StringBuilder stringBuilder;
        Object object;
        Object object2;
        block21 : {
            Object object3;
            block19 : {
                LayoutInflater layoutInflater;
                int n;
                block20 : {
                    int n2;
                    object = null;
                    object3 = null;
                    stringBuilder = null;
                    object2 = object;
                    if (this.mInfo == null) break block19;
                    object2 = object;
                    object3 = this.getRemoteContext();
                    object2 = object;
                    this.mRemoteContext = object3;
                    object2 = object;
                    layoutInflater = ((LayoutInflater)((Context)object3).getSystemService("layout_inflater")).cloneInContext((Context)object3);
                    object2 = object;
                    layoutInflater.setFilter(INFLATER_FILTER);
                    object2 = object;
                    object3 = AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
                    object2 = object;
                    n = n2 = this.mInfo.initialLayout;
                    object2 = object;
                    if (!((BaseBundle)object3).containsKey("appWidgetCategory")) break block20;
                    n = n2;
                    object2 = object;
                    if (((BaseBundle)object3).getInt("appWidgetCategory") != 2) break block20;
                    object2 = object;
                    n = this.mInfo.initialKeyguardLayout;
                    if (n != 0) {
                        n2 = n;
                    }
                    n = n2;
                }
                object2 = object;
                object2 = object = layoutInflater.inflate(n, (ViewGroup)this, false);
                object2 = object;
                object3 = new _$$Lambda$AppWidgetHostView$ab7zr5jJn3_7TaWMNA8VPkK4SdQ(this);
                object2 = object;
                ((View)object).setOnClickListener((View.OnClickListener)object3);
                object2 = object;
                break block21;
            }
            object2 = object;
            try {
                Log.w(TAG, "can't inflate defaultView because mInfo is missing");
                object2 = object3;
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
        object = stringBuilder;
        if (object != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Error inflating AppWidget ");
            stringBuilder.append(this.mInfo);
            stringBuilder.append(": ");
            stringBuilder.append(((Throwable)object).toString());
            Log.w(TAG, stringBuilder.toString());
        }
        object = object2;
        if (object2 == null) {
            object = this.getErrorView();
        }
        return object;
    }

    protected View getErrorView() {
        TextView textView = new TextView(this.mContext);
        textView.setText(17040047);
        textView.setBackgroundColor(Color.argb(127, 0, 0, 0));
        return textView;
    }

    protected Context getRemoteContext() {
        try {
            Context context = this.mContext.createApplicationContext(this.mInfo.providerInfo.applicationInfo, 4);
            return context;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Package name ");
            stringBuilder.append(this.mInfo.providerInfo.packageName);
            stringBuilder.append(" not found");
            Log.e(TAG, stringBuilder.toString());
            return this.mContext;
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AppWidgetHostView.class.getName());
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        try {
            super.onLayout(bl, n, n2, n3, n4);
        }
        catch (RuntimeException runtimeException) {
            Log.e(TAG, "Remote provider threw runtime exception, using error view instead.", runtimeException);
            this.removeViewInLayout(this.mView);
            View view = this.getErrorView();
            this.prepareView(view);
            this.addViewInLayout(view, 0, view.getLayoutParams());
            this.measureChild(view, View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight(), 1073741824));
            view.layout(0, 0, view.getMeasuredWidth() + this.mPaddingLeft + this.mPaddingRight, view.getMeasuredHeight() + this.mPaddingTop + this.mPaddingBottom);
            this.mView = view;
            this.mViewMode = 2;
        }
    }

    protected void prepareView(View view) {
        FrameLayout.LayoutParams layoutParams;
        FrameLayout.LayoutParams layoutParams2 = layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        }
        layoutParams2.gravity = 17;
        view.setLayoutParams(layoutParams2);
    }

    void resetAppWidget(AppWidgetProviderInfo appWidgetProviderInfo) {
        this.setAppWidget(this.mAppWidgetId, appWidgetProviderInfo);
        this.mViewMode = 0;
        this.updateAppWidget(null);
    }

    public void setAppWidget(int n, AppWidgetProviderInfo appWidgetProviderInfo) {
        this.mAppWidgetId = n;
        this.mInfo = appWidgetProviderInfo;
        Object object = this.getDefaultPadding();
        this.setPadding(((Rect)object).left, ((Rect)object).top, ((Rect)object).right, ((Rect)object).bottom);
        if (appWidgetProviderInfo != null) {
            String string2 = appWidgetProviderInfo.loadLabel(this.getContext().getPackageManager());
            object = string2;
            if ((appWidgetProviderInfo.providerInfo.applicationInfo.flags & 1073741824) != 0) {
                object = Resources.getSystem().getString(17041110, string2);
            }
            this.setContentDescription((CharSequence)object);
        }
    }

    public void setExecutor(Executor executor) {
        CancellationSignal cancellationSignal = this.mLastExecutionSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mLastExecutionSignal = null;
        }
        this.mAsyncExecutor = executor;
    }

    public void setOnClickHandler(RemoteViews.OnClickHandler onClickHandler) {
        this.mOnClickHandler = onClickHandler;
    }

    public void setOnLightBackground(boolean bl) {
        this.mOnLightBackground = bl;
    }

    public void updateAppWidget(RemoteViews remoteViews) {
        this.applyRemoteViews(remoteViews, true);
    }

    public void updateAppWidgetOptions(Bundle bundle) {
        AppWidgetManager.getInstance(this.mContext).updateAppWidgetOptions(this.mAppWidgetId, bundle);
    }

    public void updateAppWidgetSize(Bundle bundle, int n, int n2, int n3, int n4) {
        this.updateAppWidgetSize(bundle, n, n2, n3, n4, false);
    }

    @UnsupportedAppUsage
    public void updateAppWidgetSize(Bundle bundle, int n, int n2, int n3, int n4, boolean bl) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Parcelable parcelable = this.getDefaultPadding();
        float f = this.getResources().getDisplayMetrics().density;
        int n5 = (int)((float)(((Rect)parcelable).left + ((Rect)parcelable).right) / f);
        int n6 = (int)((float)(((Rect)parcelable).top + ((Rect)parcelable).bottom) / f);
        int n7 = 0;
        int n8 = bl ? 0 : n5;
        n8 = n - n8;
        n = bl ? 0 : n6;
        n2 -= n;
        if (bl) {
            n5 = 0;
        }
        n3 -= n5;
        if (bl) {
            n6 = n7;
        }
        parcelable = AppWidgetManager.getInstance(this.mContext).getAppWidgetOptions(this.mAppWidgetId);
        n = 0;
        if (n8 != ((BaseBundle)((Object)parcelable)).getInt("appWidgetMinWidth") || n2 != ((BaseBundle)((Object)parcelable)).getInt("appWidgetMinHeight") || n3 != ((BaseBundle)((Object)parcelable)).getInt("appWidgetMaxWidth") || (n4 -= n6) != ((BaseBundle)((Object)parcelable)).getInt("appWidgetMaxHeight")) {
            n = 1;
        }
        if (n != 0) {
            bundle.putInt("appWidgetMinWidth", n8);
            bundle.putInt("appWidgetMinHeight", n2);
            bundle.putInt("appWidgetMaxWidth", n3);
            bundle.putInt("appWidgetMaxHeight", n4);
            this.updateAppWidgetOptions(bundle);
        }
    }

    void viewDataChanged(int n) {
        Object t = this.findViewById(n);
        if (t != null && t instanceof AdapterView) {
            AdapterView adapterView = (AdapterView)t;
            t = adapterView.getAdapter();
            if (t instanceof BaseAdapter) {
                ((BaseAdapter)t).notifyDataSetChanged();
            } else if (t == null && adapterView instanceof RemoteViewsAdapter.RemoteAdapterConnectionCallback) {
                ((RemoteViewsAdapter.RemoteAdapterConnectionCallback)((Object)adapterView)).deferNotifyDataSetChanged();
            }
        }
    }

    private class ViewApplyListener
    implements RemoteViews.OnViewAppliedListener {
        private final boolean mIsReapply;
        private final int mLayoutId;
        private final RemoteViews mViews;

        public ViewApplyListener(RemoteViews remoteViews, int n, boolean bl) {
            this.mViews = remoteViews;
            this.mLayoutId = n;
            this.mIsReapply = bl;
        }

        @Override
        public void onError(Exception object) {
            if (this.mIsReapply) {
                AppWidgetHostView appWidgetHostView = AppWidgetHostView.this;
                RemoteViews remoteViews = this.mViews;
                object = appWidgetHostView.mContext;
                AppWidgetHostView appWidgetHostView2 = AppWidgetHostView.this;
                appWidgetHostView.mLastExecutionSignal = remoteViews.applyAsync((Context)object, appWidgetHostView2, appWidgetHostView2.mAsyncExecutor, new ViewApplyListener(this.mViews, this.mLayoutId, false), AppWidgetHostView.this.mOnClickHandler);
            } else {
                AppWidgetHostView.this.applyContent(null, false, (Exception)object);
            }
        }

        @Override
        public void onViewApplied(View view) {
            AppWidgetHostView appWidgetHostView = AppWidgetHostView.this;
            appWidgetHostView.mLayoutId = this.mLayoutId;
            appWidgetHostView.mViewMode = 1;
            appWidgetHostView.applyContent(view, this.mIsReapply, null);
        }
    }

}

