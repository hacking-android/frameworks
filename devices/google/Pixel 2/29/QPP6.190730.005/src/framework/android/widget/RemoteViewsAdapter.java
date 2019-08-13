/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.IServiceConnection;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget._$$Lambda$RemoteViewsAdapter$_xHEGE7CkOWJ8u7GAjsH_hc_iiA;
import com.android.internal.widget.IRemoteViewsFactory;
import java.lang.ref.WeakReference;
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Executor;

public class RemoteViewsAdapter
extends BaseAdapter
implements Handler.Callback {
    private static final int CACHE_RESET_CONFIG_FLAGS = -1073737216;
    private static final int DEFAULT_CACHE_SIZE = 40;
    private static final int DEFAULT_LOADING_VIEW_HEIGHT = 50;
    static final int MSG_LOAD_NEXT_ITEM = 3;
    private static final int MSG_MAIN_HANDLER_COMMIT_METADATA = 1;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_CONNECTED = 3;
    private static final int MSG_MAIN_HANDLER_REMOTE_ADAPTER_DISCONNECTED = 4;
    private static final int MSG_MAIN_HANDLER_REMOTE_VIEWS_LOADED = 5;
    private static final int MSG_MAIN_HANDLER_SUPER_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_NOTIFY_DATA_SET_CHANGED = 2;
    static final int MSG_REQUEST_BIND = 1;
    static final int MSG_UNBIND_SERVICE = 4;
    private static final int REMOTE_VIEWS_CACHE_DURATION = 5000;
    private static final String TAG = "RemoteViewsAdapter";
    private static final int UNBIND_SERVICE_DELAY = 5000;
    private static Handler sCacheRemovalQueue;
    private static HandlerThread sCacheRemovalThread;
    private static final HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> sCachedRemoteViewsCaches;
    private static final HashMap<RemoteViewsCacheKey, Runnable> sRemoteViewsCacheRemoveRunnables;
    private final int mAppWidgetId;
    private final Executor mAsyncViewLoadExecutor;
    @UnsupportedAppUsage
    private final FixedSizeRemoteViewsCache mCache;
    private final RemoteAdapterConnectionCallback mCallback;
    private final Context mContext;
    private boolean mDataReady = false;
    private final Intent mIntent;
    private ApplicationInfo mLastRemoteViewAppInfo;
    private final Handler mMainHandler;
    private final boolean mOnLightBackground;
    private RemoteViews.OnClickHandler mRemoteViewsOnClickHandler;
    private RemoteViewsFrameLayoutRefSet mRequestedViews;
    private final RemoteServiceHandler mServiceHandler;
    private int mVisibleWindowLowerBound;
    private int mVisibleWindowUpperBound;
    @UnsupportedAppUsage
    private final HandlerThread mWorkerThread;

    static {
        sCachedRemoteViewsCaches = new HashMap();
        sRemoteViewsCacheRemoveRunnables = new HashMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public RemoteViewsAdapter(Context object, Intent hashMap, RemoteAdapterConnectionCallback object2, boolean bl) {
        this.mContext = object;
        this.mIntent = hashMap;
        if (this.mIntent == null) {
            throw new IllegalArgumentException("Non-null Intent must be specified.");
        }
        this.mAppWidgetId = ((Intent)((Object)hashMap)).getIntExtra("remoteAdapterAppWidgetId", -1);
        FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache = null;
        this.mRequestedViews = new RemoteViewsFrameLayoutRefSet();
        this.mOnLightBackground = ((Intent)((Object)hashMap)).getBooleanExtra("remoteAdapterOnLightBackground", false);
        ((Intent)((Object)hashMap)).removeExtra("remoteAdapterAppWidgetId");
        ((Intent)((Object)hashMap)).removeExtra("remoteAdapterOnLightBackground");
        this.mWorkerThread = new HandlerThread("RemoteViewsCache-loader");
        this.mWorkerThread.start();
        this.mMainHandler = new Handler(Looper.myLooper(), this);
        this.mServiceHandler = new RemoteServiceHandler(this.mWorkerThread.getLooper(), this, ((Context)object).getApplicationContext());
        hashMap = fixedSizeRemoteViewsCache;
        if (bl) {
            hashMap = new HandlerThreadExecutor(this.mWorkerThread);
        }
        this.mAsyncViewLoadExecutor = hashMap;
        this.mCallback = object2;
        if (sCacheRemovalThread == null) {
            sCacheRemovalThread = new HandlerThread("RemoteViewsAdapter-cachePruner");
            sCacheRemovalThread.start();
            sCacheRemovalQueue = new Handler(sCacheRemovalThread.getLooper());
        }
        object2 = new RemoteViewsCacheKey(new Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        hashMap = sCachedRemoteViewsCaches;
        synchronized (hashMap) {
            fixedSizeRemoteViewsCache = sCachedRemoteViewsCaches.get(object2);
            object = ((Context)object).getResources().getConfiguration();
            if (fixedSizeRemoteViewsCache != null && (fixedSizeRemoteViewsCache.mConfiguration.diff((Configuration)object) & -1073737216) == 0) {
                this.mCache = sCachedRemoteViewsCaches.get(object2);
                object2 = this.mCache.mMetaData;
                synchronized (object2) {
                    if (FixedSizeRemoteViewsCache.access$900((FixedSizeRemoteViewsCache)this.mCache).count > 0) {
                        this.mDataReady = true;
                    }
                }
            } else {
                this.mCache = object2 = new FixedSizeRemoteViewsCache(40, (Configuration)object);
            }
            if (!this.mDataReady) {
                this.requestBindService();
            }
            return;
        }
    }

    private int[] getVisibleWindow(int n) {
        int n2 = this.mVisibleWindowLowerBound;
        int n3 = this.mVisibleWindowUpperBound;
        if ((n2 != 0 || n3 != 0) && n2 >= 0 && n3 >= 0) {
            int[] arrn;
            if (n2 <= n3) {
                arrn = new int[n3 + 1 - n2];
                n = 0;
                while (n2 <= n3) {
                    arrn[n] = n2++;
                    ++n;
                }
            } else {
                int n4 = Math.max(n, n2);
                int[] arrn2 = new int[n4 - n2 + n3 + 1];
                n = 0;
                int n5 = 0;
                while (n5 <= n3) {
                    arrn2[n] = n5++;
                    ++n;
                }
                do {
                    arrn = arrn2;
                    if (n2 >= n4) break;
                    arrn2[n] = n2++;
                    ++n;
                } while (true);
            }
            return arrn;
        }
        return new int[0];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static /* synthetic */ void lambda$saveRemoteViewsCache$0(RemoteViewsCacheKey remoteViewsCacheKey) {
        HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> hashMap = sCachedRemoteViewsCaches;
        synchronized (hashMap) {
            if (sCachedRemoteViewsCaches.containsKey(remoteViewsCacheKey)) {
                sCachedRemoteViewsCaches.remove(remoteViewsCacheKey);
            }
            if (sRemoteViewsCacheRemoveRunnables.containsKey(remoteViewsCacheKey)) {
                sRemoteViewsCacheRemoveRunnables.remove(remoteViewsCacheKey);
            }
            return;
        }
    }

    private void requestBindService() {
        this.mServiceHandler.removeMessages(4);
        Message.obtain(this.mServiceHandler, 1, this.mAppWidgetId, 0, this.mIntent).sendToTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateRemoteViews(IRemoteViewsFactory object, int n, boolean bl) {
        block13 : {
            long l;
            boolean bl2;
            RemoteViews remoteViews;
            block14 : {
                remoteViews = object.getViewAt(n);
                l = object.getItemId(n);
                if (remoteViews == null) break block13;
                if (remoteViews.mApplication == null) break block14;
                object = this.mLastRemoteViewAppInfo;
                if (object != null && remoteViews.hasSameAppInfo((ApplicationInfo)object)) {
                    remoteViews.mApplication = this.mLastRemoteViewAppInfo;
                } else {
                    this.mLastRemoteViewAppInfo = remoteViews.mApplication;
                }
            }
            int n2 = remoteViews.getLayoutId();
            object = this.mCache.getMetaData();
            synchronized (object) {
                bl2 = ((RemoteViewsMetaData)object).isViewTypeInRange(n2);
                n2 = FixedSizeRemoteViewsCache.access$900((FixedSizeRemoteViewsCache)this.mCache).count;
            }
            object = this.mCache;
            synchronized (object) {
                if (bl2) {
                    int[] arrn = this.getVisibleWindow(n2);
                    this.mCache.insert(n, remoteViews, l, arrn);
                    if (bl) {
                        Message.obtain(this.mMainHandler, 5, n, 0, remoteViews).sendToTarget();
                    }
                } else {
                    Log.e(TAG, "Error: widget's RemoteViewsFactory returns more view types than  indicated by getViewTypeCount() ");
                }
                return;
            }
        }
        try {
            object = new RuntimeException("Null remoteViews");
            throw object;
        }
        catch (RemoteException | RuntimeException exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Error in updateRemoteViews(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("): ");
            ((StringBuilder)object).append(exception.getMessage());
            Log.e(TAG, ((StringBuilder)object).toString());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void updateTemporaryMetaData(IRemoteViewsFactory object) {
        int n;
        int n2;
        boolean bl;
        LoadingViewTemplate loadingViewTemplate;
        try {
            bl = object.hasStableIds();
            n2 = object.getViewTypeCount();
            n = object.getCount();
            loadingViewTemplate = new LoadingViewTemplate(object.getLoadingView(), this.mContext);
            if (n > 0 && loadingViewTemplate.remoteViews == null && (object = object.getViewAt(0)) != null) {
                Context context = this.mContext;
                HandlerThreadExecutor handlerThreadExecutor = new HandlerThreadExecutor(this.mWorkerThread);
                loadingViewTemplate.loadFirstViewHeight((RemoteViews)object, context, handlerThreadExecutor);
            }
            object = this.mCache.getTemporaryMetaData();
            // MONITORENTER : object
        }
        catch (RemoteException | RuntimeException exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error in updateMetaData: ");
            stringBuilder.append(exception.getMessage());
            Log.e(TAG, stringBuilder.toString());
            Object object2 = this.mCache.getMetaData();
            // MONITORENTER : object2
            this.mCache.getMetaData().reset();
            // MONITOREXIT : object2
            object2 = this.mCache;
            // MONITORENTER : object2
            this.mCache.reset();
            // MONITOREXIT : object2
            this.mMainHandler.sendEmptyMessage(2);
        }
        ((RemoteViewsMetaData)object).hasStableIds = bl;
        ((RemoteViewsMetaData)object).viewTypeCount = n2 + 1;
        ((RemoteViewsMetaData)object).count = n;
        ((RemoteViewsMetaData)object).loadingTemplate = loadingViewTemplate;
        // MONITOREXIT : object
        return;
    }

    protected void finalize() throws Throwable {
        try {
            this.mServiceHandler.unbindNow();
            this.mWorkerThread.quit();
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getCount() {
        RemoteViewsMetaData remoteViewsMetaData = this.mCache.getMetaData();
        synchronized (remoteViewsMetaData) {
            return remoteViewsMetaData.count;
        }
    }

    @Override
    public Object getItem(int n) {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public long getItemId(int n) {
        FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache = this.mCache;
        synchronized (fixedSizeRemoteViewsCache) {
            if (!this.mCache.containsMetaDataAt(n)) return 0L;
            return this.mCache.getMetaDataAt((int)n).itemId;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getItemViewType(int n) {
        Object object = this.mCache;
        synchronized (object) {
            if (!this.mCache.containsMetaDataAt(n)) return 0;
            n = this.mCache.getMetaDataAt((int)n).typeId;
        }
        object = this.mCache.getMetaData();
        synchronized (object) {
            return ((RemoteViewsMetaData)object).getMappedViewType(n);
        }
    }

    @UnsupportedAppUsage
    public Intent getRemoteViewsServiceIntent() {
        return this.mIntent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache = this.mCache;
        synchronized (fixedSizeRemoteViewsCache) {
            RemoteViews remoteViews = this.mCache.getRemoteViewsAt(n);
            boolean bl = remoteViews != null;
            boolean bl2 = false;
            if (view != null && view instanceof RemoteViewsFrameLayout) {
                this.mRequestedViews.removeView((RemoteViewsFrameLayout)view);
            }
            if (!bl) {
                this.requestBindService();
            } else {
                bl2 = this.mCache.queuePositionsToBePreloadedFromRequestedPosition(n);
            }
            if (view instanceof RemoteViewsFrameLayout) {
                view = (RemoteViewsFrameLayout)view;
            } else {
                view = new RemoteViewsFrameLayout(viewGroup.getContext(), this.mCache);
                ((AppWidgetHostView)view).setExecutor(this.mAsyncViewLoadExecutor);
                ((AppWidgetHostView)view).setOnLightBackground(this.mOnLightBackground);
            }
            if (bl) {
                ((RemoteViewsFrameLayout)view).onRemoteViewsLoaded(remoteViews, this.mRemoteViewsOnClickHandler, false);
                if (bl2) {
                    this.mServiceHandler.sendEmptyMessage(3);
                }
            } else {
                ((RemoteViewsFrameLayout)view).onRemoteViewsLoaded(this.mCache.getMetaData().getLoadingTemplate((Context)this.mContext).remoteViews, this.mRemoteViewsOnClickHandler, false);
                this.mRequestedViews.add(n, (RemoteViewsFrameLayout)view);
                this.mCache.queueRequestedPositionToLoad(n);
                this.mServiceHandler.sendEmptyMessage(3);
            }
            return view;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getViewTypeCount() {
        RemoteViewsMetaData remoteViewsMetaData = this.mCache.getMetaData();
        synchronized (remoteViewsMetaData) {
            return remoteViewsMetaData.viewTypeCount;
        }
    }

    @Override
    public boolean handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return false;
                        }
                        this.mRequestedViews.notifyOnRemoteViewsLoaded(((Message)object).arg1, (RemoteViews)((Message)object).obj);
                        return true;
                    }
                    object = this.mCallback;
                    if (object != null) {
                        object.onRemoteAdapterDisconnected();
                    }
                    return true;
                }
                object = this.mCallback;
                if (object != null) {
                    object.onRemoteAdapterConnected();
                }
                return true;
            }
            this.superNotifyDataSetChanged();
            return true;
        }
        this.mCache.commitTemporaryMetaData();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean hasStableIds() {
        RemoteViewsMetaData remoteViewsMetaData = this.mCache.getMetaData();
        synchronized (remoteViewsMetaData) {
            return remoteViewsMetaData.hasStableIds;
        }
    }

    @UnsupportedAppUsage
    public boolean isDataReady() {
        return this.mDataReady;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.getCount() <= 0;
        return bl;
    }

    @Override
    public void notifyDataSetChanged() {
        this.mServiceHandler.removeMessages(4);
        this.mServiceHandler.sendEmptyMessage(2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void saveRemoteViewsCache() {
        RemoteViewsCacheKey remoteViewsCacheKey = new RemoteViewsCacheKey(new Intent.FilterComparison(this.mIntent), this.mAppWidgetId);
        HashMap<RemoteViewsCacheKey, FixedSizeRemoteViewsCache> hashMap = sCachedRemoteViewsCaches;
        // MONITORENTER : hashMap
        if (sRemoteViewsCacheRemoveRunnables.containsKey(remoteViewsCacheKey)) {
            sCacheRemovalQueue.removeCallbacks(sRemoteViewsCacheRemoveRunnables.get(remoteViewsCacheKey));
            sRemoteViewsCacheRemoveRunnables.remove(remoteViewsCacheKey);
        }
        Object object = this.mCache.mMetaData;
        // MONITORENTER : object
        int n = FixedSizeRemoteViewsCache.access$900((FixedSizeRemoteViewsCache)this.mCache).count;
        // MONITOREXIT : object
        object = this.mCache;
        // MONITORENTER : object
        int n2 = this.mCache.mIndexRemoteViews.size();
        // MONITOREXIT : object
        if (n > 0 && n2 > 0) {
            sCachedRemoteViewsCaches.put(remoteViewsCacheKey, this.mCache);
        }
        object = new _$$Lambda$RemoteViewsAdapter$_xHEGE7CkOWJ8u7GAjsH_hc_iiA(remoteViewsCacheKey);
        sRemoteViewsCacheRemoveRunnables.put(remoteViewsCacheKey, (Runnable)object);
        sCacheRemovalQueue.postDelayed((Runnable)object, 5000L);
        // MONITOREXIT : hashMap
    }

    @UnsupportedAppUsage
    public void setRemoteViewsOnClickHandler(RemoteViews.OnClickHandler onClickHandler) {
        this.mRemoteViewsOnClickHandler = onClickHandler;
    }

    @UnsupportedAppUsage
    public void setVisibleRangeHint(int n, int n2) {
        this.mVisibleWindowLowerBound = n;
        this.mVisibleWindowUpperBound = n2;
    }

    void superNotifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public static class AsyncRemoteAdapterAction
    implements Runnable {
        private final RemoteAdapterConnectionCallback mCallback;
        private final Intent mIntent;

        public AsyncRemoteAdapterAction(RemoteAdapterConnectionCallback remoteAdapterConnectionCallback, Intent intent) {
            this.mCallback = remoteAdapterConnectionCallback;
            this.mIntent = intent;
        }

        @Override
        public void run() {
            this.mCallback.setRemoteViewsAdapter(this.mIntent, true);
        }
    }

    private static class FixedSizeRemoteViewsCache {
        private static final float sMaxCountSlackPercent = 0.75f;
        private static final int sMaxMemoryLimitInBytes = 2097152;
        private final Configuration mConfiguration;
        private final SparseArray<RemoteViewsIndexMetaData> mIndexMetaData = new SparseArray();
        private final SparseArray<RemoteViews> mIndexRemoteViews = new SparseArray();
        private final SparseBooleanArray mIndicesToLoad = new SparseBooleanArray();
        private int mLastRequestedIndex;
        private final int mMaxCount;
        private final int mMaxCountSlack;
        private final RemoteViewsMetaData mMetaData = new RemoteViewsMetaData();
        private int mPreloadLowerBound;
        private int mPreloadUpperBound;
        private final RemoteViewsMetaData mTemporaryMetaData = new RemoteViewsMetaData();

        FixedSizeRemoteViewsCache(int n, Configuration configuration) {
            this.mMaxCount = n;
            this.mMaxCountSlack = Math.round((float)(this.mMaxCount / 2) * 0.75f);
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mConfiguration = new Configuration(configuration);
        }

        private int getFarthestPositionFrom(int n, int[] arrn) {
            int n2 = 0;
            int n3 = -1;
            int n4 = 0;
            int n5 = -1;
            for (int i = this.mIndexRemoteViews.size() - 1; i >= 0; --i) {
                int n6 = this.mIndexRemoteViews.keyAt(i);
                int n7 = Math.abs(n6 - n);
                int n8 = n4;
                int n9 = n5;
                if (n7 > n4) {
                    n8 = n4;
                    n9 = n5;
                    if (Arrays.binarySearch(arrn, n6) < 0) {
                        n9 = n6;
                        n8 = n7;
                    }
                }
                n5 = n2;
                if (n7 >= n2) {
                    n3 = n6;
                    n5 = n7;
                }
                n2 = n5;
                n4 = n8;
                n5 = n9;
            }
            if (n5 > -1) {
                return n5;
            }
            return n3;
        }

        private int getRemoteViewsBitmapMemoryUsage() {
            int n = 0;
            for (int i = this.mIndexRemoteViews.size() - 1; i >= 0; --i) {
                RemoteViews remoteViews = this.mIndexRemoteViews.valueAt(i);
                int n2 = n;
                if (remoteViews != null) {
                    n2 = n + remoteViews.estimateMemoryUsage();
                }
                n = n2;
            }
            return n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void commitTemporaryMetaData() {
            RemoteViewsMetaData remoteViewsMetaData = this.mTemporaryMetaData;
            synchronized (remoteViewsMetaData) {
                RemoteViewsMetaData remoteViewsMetaData2 = this.mMetaData;
                synchronized (remoteViewsMetaData2) {
                    this.mMetaData.set(this.mTemporaryMetaData);
                    return;
                }
            }
        }

        public boolean containsMetaDataAt(int n) {
            boolean bl = this.mIndexMetaData.indexOfKey(n) >= 0;
            return bl;
        }

        public boolean containsRemoteViewAt(int n) {
            boolean bl = this.mIndexRemoteViews.indexOfKey(n) >= 0;
            return bl;
        }

        public RemoteViewsMetaData getMetaData() {
            return this.mMetaData;
        }

        public RemoteViewsIndexMetaData getMetaDataAt(int n) {
            return this.mIndexMetaData.get(n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int getNextIndexToLoad() {
            SparseBooleanArray sparseBooleanArray = this.mIndicesToLoad;
            synchronized (sparseBooleanArray) {
                int n;
                int n2 = n = this.mIndicesToLoad.indexOfValue(true);
                if (n < 0) {
                    n2 = this.mIndicesToLoad.indexOfValue(false);
                }
                if (n2 < 0) {
                    return -1;
                }
                n = this.mIndicesToLoad.keyAt(n2);
                this.mIndicesToLoad.removeAt(n2);
                return n;
            }
        }

        public RemoteViews getRemoteViewsAt(int n) {
            return this.mIndexRemoteViews.get(n);
        }

        public RemoteViewsMetaData getTemporaryMetaData() {
            return this.mTemporaryMetaData;
        }

        public void insert(int n, RemoteViews remoteViews, long l, int[] object) {
            int n2;
            int n3;
            if (this.mIndexRemoteViews.size() >= this.mMaxCount) {
                this.mIndexRemoteViews.remove(this.getFarthestPositionFrom(n, (int[])object));
            }
            if ((n3 = this.mLastRequestedIndex) <= -1) {
                n3 = n;
            }
            while (this.getRemoteViewsBitmapMemoryUsage() >= 2097152 && (n2 = this.getFarthestPositionFrom(n3, (int[])object)) >= 0) {
                this.mIndexRemoteViews.remove(n2);
            }
            object = this.mIndexMetaData.get(n);
            if (object != null) {
                ((RemoteViewsIndexMetaData)object).set(remoteViews, l);
            } else {
                this.mIndexMetaData.put(n, new RemoteViewsIndexMetaData(remoteViews, l));
            }
            this.mIndexRemoteViews.put(n, remoteViews);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean queuePositionsToBePreloadedFromRequestedPosition(int n) {
            int n2;
            int n3 = this.mPreloadLowerBound;
            if (n3 <= n && n <= (n2 = this.mPreloadUpperBound) && Math.abs(n - (n2 + n3) / 2) < this.mMaxCountSlack) {
                return false;
            }
            Object object = this.mMetaData;
            synchronized (object) {
                n2 = this.mMetaData.count;
            }
            object = this.mIndicesToLoad;
            synchronized (object) {
                for (n3 = this.mIndicesToLoad.size() - 1; n3 >= 0; --n3) {
                    if (this.mIndicesToLoad.valueAt(n3)) continue;
                    this.mIndicesToLoad.removeAt(n3);
                }
                n3 = this.mMaxCount / 2;
                this.mPreloadLowerBound = n - n3;
                this.mPreloadUpperBound = n + n3;
                n = Math.max(0, this.mPreloadLowerBound);
                n3 = Math.min(this.mPreloadUpperBound, n2 - 1);
                while (n <= n3) {
                    if (this.mIndexRemoteViews.indexOfKey(n) < 0 && !this.mIndicesToLoad.get(n)) {
                        this.mIndicesToLoad.put(n, false);
                    }
                    ++n;
                }
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void queueRequestedPositionToLoad(int n) {
            this.mLastRequestedIndex = n;
            SparseBooleanArray sparseBooleanArray = this.mIndicesToLoad;
            synchronized (sparseBooleanArray) {
                this.mIndicesToLoad.put(n, true);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void reset() {
            this.mPreloadLowerBound = 0;
            this.mPreloadUpperBound = -1;
            this.mLastRequestedIndex = -1;
            this.mIndexRemoteViews.clear();
            this.mIndexMetaData.clear();
            SparseBooleanArray sparseBooleanArray = this.mIndicesToLoad;
            synchronized (sparseBooleanArray) {
                this.mIndicesToLoad.clear();
                return;
            }
        }
    }

    private static class HandlerThreadExecutor
    implements Executor {
        private final HandlerThread mThread;

        HandlerThreadExecutor(HandlerThread handlerThread) {
            this.mThread = handlerThread;
        }

        @Override
        public void execute(Runnable runnable) {
            if (Thread.currentThread().getId() == this.mThread.getId()) {
                runnable.run();
            } else {
                new Handler(this.mThread.getLooper()).post(runnable);
            }
        }
    }

    private static class LoadingViewTemplate {
        public int defaultHeight;
        public final RemoteViews remoteViews;

        LoadingViewTemplate(RemoteViews remoteViews, Context context) {
            this.remoteViews = remoteViews;
            this.defaultHeight = Math.round(50.0f * context.getResources().getDisplayMetrics().density);
        }

        public void loadFirstViewHeight(RemoteViews remoteViews, Context context, Executor executor) {
            remoteViews.applyAsync(context, new RemoteViewsFrameLayout(context, null), executor, new RemoteViews.OnViewAppliedListener(){

                @Override
                public void onError(Exception exception) {
                    Log.w(RemoteViewsAdapter.TAG, "Error inflating first RemoteViews", exception);
                }

                @Override
                public void onViewApplied(View view) {
                    try {
                        view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                        LoadingViewTemplate.this.defaultHeight = view.getMeasuredHeight();
                    }
                    catch (Exception exception) {
                        this.onError(exception);
                    }
                }
            });
        }

    }

    public static interface RemoteAdapterConnectionCallback {
        public void deferNotifyDataSetChanged();

        public boolean onRemoteAdapterConnected();

        public void onRemoteAdapterDisconnected();

        public void setRemoteViewsAdapter(Intent var1, boolean var2);
    }

    private static class RemoteServiceHandler
    extends Handler
    implements ServiceConnection {
        private final WeakReference<RemoteViewsAdapter> mAdapter;
        private boolean mBindRequested = false;
        private final Context mContext;
        private boolean mNotifyDataSetChangedPending = false;
        private IRemoteViewsFactory mRemoteViewsFactory;

        RemoteServiceHandler(Looper looper, RemoteViewsAdapter remoteViewsAdapter, Context context) {
            super(looper);
            this.mAdapter = new WeakReference<RemoteViewsAdapter>(remoteViewsAdapter);
            this.mContext = context;
        }

        private void enqueueDeferredUnbindServiceMessage() {
            this.removeMessages(4);
            this.sendEmptyMessageDelayed(4, 5000L);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private boolean sendNotifyDataSetChange(boolean var1_1) {
            if (var1_1) ** GOTO lbl4
            try {
                if (this.mRemoteViewsFactory.isCreated() != false) return true;
lbl4: // 2 sources:
                this.mRemoteViewsFactory.onDataSetChanged();
                return true;
            }
            catch (RemoteException | RuntimeException var2_2) {
                var3_3 = new StringBuilder();
                var3_3.append("Error in updateNotifyDataSetChanged(): ");
                var3_3.append(var2_2.getMessage());
                Log.e("RemoteViewsAdapter", var3_3.toString());
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message object) {
            Object object2 = (RemoteViewsAdapter)this.mAdapter.get();
            int n = ((Message)object).what;
            if (n != 1) {
                int[] arrn;
                int n2;
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return;
                        }
                        this.unbindNow();
                        return;
                    }
                    if (object2 == null) return;
                    if (this.mRemoteViewsFactory == null) {
                        return;
                    }
                    this.removeMessages(4);
                    n = ((RemoteViewsAdapter)object2).mCache.getNextIndexToLoad();
                    if (n > -1) {
                        ((RemoteViewsAdapter)object2).updateRemoteViews(this.mRemoteViewsFactory, n, true);
                        this.sendEmptyMessage(3);
                        return;
                    }
                    this.enqueueDeferredUnbindServiceMessage();
                    return;
                }
                this.enqueueDeferredUnbindServiceMessage();
                if (object2 == null) {
                    return;
                }
                if (this.mRemoteViewsFactory == null) {
                    this.mNotifyDataSetChangedPending = true;
                    ((RemoteViewsAdapter)object2).requestBindService();
                    return;
                }
                if (!this.sendNotifyDataSetChange(true)) {
                    return;
                }
                object = ((RemoteViewsAdapter)object2).mCache;
                synchronized (object) {
                    ((RemoteViewsAdapter)object2).mCache.reset();
                }
                ((RemoteViewsAdapter)object2).updateTemporaryMetaData(this.mRemoteViewsFactory);
                object = ((RemoteViewsAdapter)object2).mCache.getTemporaryMetaData();
                synchronized (object) {
                    n2 = RemoteViewsAdapter.access$300((RemoteViewsAdapter)object2).getTemporaryMetaData().count;
                    arrn = ((RemoteViewsAdapter)object2).getVisibleWindow(n2);
                }
                int n3 = arrn.length;
                n = 0;
                do {
                    if (n >= n3) {
                        ((RemoteViewsAdapter)object2).mMainHandler.sendEmptyMessage(1);
                        ((RemoteViewsAdapter)object2).mMainHandler.sendEmptyMessage(2);
                        return;
                    }
                    int n4 = arrn[n];
                    if (n4 < n2) {
                        ((RemoteViewsAdapter)object2).updateRemoteViews(this.mRemoteViewsFactory, n4, false);
                    }
                    ++n;
                } while (true);
            }
            if (object2 == null || this.mRemoteViewsFactory != null) {
                this.enqueueDeferredUnbindServiceMessage();
            }
            if (this.mBindRequested) {
                return;
            }
            IServiceConnection iServiceConnection = this.mContext.getServiceDispatcher(this, this, 33554433);
            object2 = (Intent)((Message)object).obj;
            n = ((Message)object).arg1;
            try {
                this.mBindRequested = AppWidgetManager.getInstance(this.mContext).bindRemoteViewsService(this.mContext, n, (Intent)object2, iServiceConnection, 33554433);
                return;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to bind remoteViewsService: ");
                ((StringBuilder)object).append(exception.getMessage());
                Log.e(RemoteViewsAdapter.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            this.mRemoteViewsFactory = IRemoteViewsFactory.Stub.asInterface(iBinder);
            this.enqueueDeferredUnbindServiceMessage();
            object = (RemoteViewsAdapter)this.mAdapter.get();
            if (object == null) {
                return;
            }
            if (this.mNotifyDataSetChangedPending) {
                this.mNotifyDataSetChangedPending = false;
                object = Message.obtain((Handler)this, 2);
                this.handleMessage((Message)object);
                ((Message)object).recycle();
            } else {
                if (!this.sendNotifyDataSetChange(false)) {
                    return;
                }
                ((RemoteViewsAdapter)object).updateTemporaryMetaData(this.mRemoteViewsFactory);
                ((RemoteViewsAdapter)object).mMainHandler.sendEmptyMessage(1);
                ((RemoteViewsAdapter)object).mMainHandler.sendEmptyMessage(3);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName object) {
            this.mRemoteViewsFactory = null;
            object = (RemoteViewsAdapter)this.mAdapter.get();
            if (object != null) {
                ((RemoteViewsAdapter)object).mMainHandler.sendEmptyMessage(4);
            }
        }

        protected void unbindNow() {
            if (this.mBindRequested) {
                this.mBindRequested = false;
                this.mContext.unbindService(this);
            }
            this.mRemoteViewsFactory = null;
        }
    }

    static class RemoteViewsCacheKey {
        final Intent.FilterComparison filter;
        final int widgetId;

        RemoteViewsCacheKey(Intent.FilterComparison filterComparison, int n) {
            this.filter = filterComparison;
            this.widgetId = n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof RemoteViewsCacheKey;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (RemoteViewsCacheKey)object;
            bl = bl2;
            if (((RemoteViewsCacheKey)object).filter.equals(this.filter)) {
                bl = bl2;
                if (((RemoteViewsCacheKey)object).widgetId == this.widgetId) {
                    bl = true;
                }
            }
            return bl;
        }

        public int hashCode() {
            Intent.FilterComparison filterComparison = this.filter;
            int n = filterComparison == null ? 0 : filterComparison.hashCode();
            return n ^ this.widgetId << 2;
        }
    }

    static class RemoteViewsFrameLayout
    extends AppWidgetHostView {
        public int cacheIndex = -1;
        private final FixedSizeRemoteViewsCache mCache;

        public RemoteViewsFrameLayout(Context context, FixedSizeRemoteViewsCache fixedSizeRemoteViewsCache) {
            super(context);
            this.mCache = fixedSizeRemoteViewsCache;
        }

        @Override
        protected View getDefaultView() {
            int n = this.mCache.getMetaData().getLoadingTemplate((Context)this.getContext()).defaultHeight;
            TextView textView = (TextView)LayoutInflater.from(this.getContext()).inflate(17367257, (ViewGroup)this, false);
            textView.setHeight(n);
            return textView;
        }

        @Override
        protected View getErrorView() {
            return this.getDefaultView();
        }

        @Override
        protected Context getRemoteContext() {
            return null;
        }

        public void onRemoteViewsLoaded(RemoteViews remoteViews, RemoteViews.OnClickHandler onClickHandler, boolean bl) {
            this.setOnClickHandler(onClickHandler);
            bl = bl || remoteViews != null && remoteViews.prefersAsyncApply();
            this.applyRemoteViews(remoteViews, bl);
        }
    }

    private class RemoteViewsFrameLayoutRefSet
    extends SparseArray<LinkedList<RemoteViewsFrameLayout>> {
        private RemoteViewsFrameLayoutRefSet() {
        }

        public void add(int n, RemoteViewsFrameLayout remoteViewsFrameLayout) {
            LinkedList<RemoteViewsFrameLayout> linkedList;
            LinkedList<RemoteViewsFrameLayout> linkedList2 = linkedList = (LinkedList<RemoteViewsFrameLayout>)this.get(n);
            if (linkedList == null) {
                linkedList2 = new LinkedList<RemoteViewsFrameLayout>();
                this.put(n, linkedList2);
            }
            remoteViewsFrameLayout.cacheIndex = n;
            linkedList2.add(remoteViewsFrameLayout);
        }

        public void notifyOnRemoteViewsLoaded(int n, RemoteViews remoteViews) {
            if (remoteViews == null) {
                return;
            }
            Object object = (LinkedList)this.removeReturnOld(n);
            if (object != null) {
                object = ((AbstractSequentialList)object).iterator();
                while (object.hasNext()) {
                    ((RemoteViewsFrameLayout)object.next()).onRemoteViewsLoaded(remoteViews, RemoteViewsAdapter.this.mRemoteViewsOnClickHandler, true);
                }
            }
        }

        public void removeView(RemoteViewsFrameLayout remoteViewsFrameLayout) {
            if (remoteViewsFrameLayout.cacheIndex < 0) {
                return;
            }
            LinkedList linkedList = (LinkedList)this.get(remoteViewsFrameLayout.cacheIndex);
            if (linkedList != null) {
                linkedList.remove(remoteViewsFrameLayout);
            }
            remoteViewsFrameLayout.cacheIndex = -1;
        }
    }

    private static class RemoteViewsIndexMetaData {
        long itemId;
        int typeId;

        public RemoteViewsIndexMetaData(RemoteViews remoteViews, long l) {
            this.set(remoteViews, l);
        }

        public void set(RemoteViews remoteViews, long l) {
            this.itemId = l;
            this.typeId = remoteViews != null ? remoteViews.getLayoutId() : 0;
        }
    }

    private static class RemoteViewsMetaData {
        int count;
        boolean hasStableIds;
        LoadingViewTemplate loadingTemplate;
        private final SparseIntArray mTypeIdIndexMap = new SparseIntArray();
        int viewTypeCount;

        public RemoteViewsMetaData() {
            this.reset();
        }

        public LoadingViewTemplate getLoadingTemplate(Context object) {
            synchronized (this) {
                if (this.loadingTemplate == null) {
                    LoadingViewTemplate loadingViewTemplate;
                    this.loadingTemplate = loadingViewTemplate = new LoadingViewTemplate(null, (Context)object);
                }
                object = this.loadingTemplate;
                return object;
            }
        }

        public int getMappedViewType(int n) {
            int n2;
            int n3 = n2 = this.mTypeIdIndexMap.get(n, -1);
            if (n2 == -1) {
                n3 = this.mTypeIdIndexMap.size() + 1;
                this.mTypeIdIndexMap.put(n, n3);
            }
            return n3;
        }

        public boolean isViewTypeInRange(int n) {
            boolean bl = this.getMappedViewType(n) < this.viewTypeCount;
            return bl;
        }

        public void reset() {
            this.count = 0;
            this.viewTypeCount = 1;
            this.hasStableIds = true;
            this.loadingTemplate = null;
            this.mTypeIdIndexMap.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void set(RemoteViewsMetaData remoteViewsMetaData) {
            synchronized (remoteViewsMetaData) {
                this.count = remoteViewsMetaData.count;
                this.viewTypeCount = remoteViewsMetaData.viewTypeCount;
                this.hasStableIds = remoteViewsMetaData.hasStableIds;
                this.loadingTemplate = remoteViewsMetaData.loadingTemplate;
                return;
            }
        }
    }

}

