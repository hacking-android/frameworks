/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.android.internal.widget.IRemoteViewsFactory;
import java.util.HashMap;

public abstract class RemoteViewsService
extends Service {
    private static final String LOG_TAG = "RemoteViewsService";
    private static final Object sLock;
    private static final HashMap<Intent.FilterComparison, RemoteViewsFactory> sRemoteViewFactories;

    static {
        sRemoteViewFactories = new HashMap();
        sLock = new Object();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public IBinder onBind(Intent object) {
        Object object2 = sLock;
        synchronized (object2) {
            boolean bl;
            Object object3 = new Intent.FilterComparison((Intent)object);
            if (!sRemoteViewFactories.containsKey(object3)) {
                object = this.onGetViewFactory((Intent)object);
                sRemoteViewFactories.put((Intent.FilterComparison)object3, (RemoteViewsFactory)object);
                object.onCreate();
                bl = false;
                return new RemoteViewsFactoryAdapter((RemoteViewsFactory)object, bl);
            } else {
                object = sRemoteViewFactories.get(object3);
                bl = true;
            }
            return new RemoteViewsFactoryAdapter((RemoteViewsFactory)object, bl);
        }
    }

    public abstract RemoteViewsFactory onGetViewFactory(Intent var1);

    public static interface RemoteViewsFactory {
        public int getCount();

        public long getItemId(int var1);

        public RemoteViews getLoadingView();

        public RemoteViews getViewAt(int var1);

        public int getViewTypeCount();

        public boolean hasStableIds();

        public void onCreate();

        public void onDataSetChanged();

        public void onDestroy();
    }

    private static class RemoteViewsFactoryAdapter
    extends IRemoteViewsFactory.Stub {
        private RemoteViewsFactory mFactory;
        private boolean mIsCreated;

        public RemoteViewsFactoryAdapter(RemoteViewsFactory remoteViewsFactory, boolean bl) {
            this.mFactory = remoteViewsFactory;
            this.mIsCreated = bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getCount() {
            synchronized (this) {
                int n = 0;
                try {
                    try {
                        return this.mFactory.getCount();
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                        return n;
                    }
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long getItemId(int n) {
            synchronized (this) {
                long l = 0L;
                try {
                    try {
                        long l2 = this.mFactory.getItemId(n);
                        return l2;
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    return l;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public RemoteViews getLoadingView() {
            synchronized (this) {
                RemoteViews remoteViews = null;
                try {
                    try {
                        RemoteViews remoteViews2 = this.mFactory.getLoadingView();
                        return remoteViews2;
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    return remoteViews;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public RemoteViews getViewAt(int n) {
            synchronized (this) {
                RemoteViews remoteViews = null;
                try {
                    try {
                        RemoteViews remoteViews2 = this.mFactory.getViewAt(n);
                        if (remoteViews2 == null) return remoteViews2;
                        remoteViews = remoteViews2;
                        remoteViews2.addFlags(2);
                        return remoteViews2;
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    return remoteViews;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int getViewTypeCount() {
            synchronized (this) {
                int n = 0;
                try {
                    try {
                        int n2 = this.mFactory.getViewTypeCount();
                        return n2;
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    return n;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean hasStableIds() {
            synchronized (this) {
                boolean bl = false;
                try {
                    try {
                        return this.mFactory.hasStableIds();
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                        return bl;
                    }
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        @Override
        public boolean isCreated() {
            synchronized (this) {
                boolean bl = this.mIsCreated;
                return bl;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDataSetChanged() {
            synchronized (this) {
                try {
                    try {
                        this.mFactory.onDataSetChanged();
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    return;
                }
                catch (Throwable throwable2) {}
                throw throwable2;
            }
        }

        @Override
        public void onDataSetChangedAsync() {
            synchronized (this) {
                this.onDataSetChanged();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDestroy(Intent object) {
            Object object2 = sLock;
            synchronized (object2) {
                Intent.FilterComparison filterComparison = new Intent.FilterComparison((Intent)object);
                if (sRemoteViewFactories.containsKey(filterComparison)) {
                    object = (RemoteViewsFactory)sRemoteViewFactories.get(filterComparison);
                    try {
                        object.onDestroy();
                    }
                    catch (Exception exception) {
                        Thread thread = Thread.currentThread();
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, exception);
                    }
                    sRemoteViewFactories.remove(filterComparison);
                }
                return;
            }
        }
    }

}

