/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.resolver.IResolverRankerResult;
import android.service.resolver.IResolverRankerService;
import android.service.resolver.ResolverTarget;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.logging.MetricsLogger;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class ResolverRankerServiceResolverComparator
extends AbstractResolverComparator {
    private static final int CONNECTION_COST_TIMEOUT_MILLIS = 200;
    private static final boolean DEBUG = false;
    private static final float RECENCY_MULTIPLIER = 2.0f;
    private static final long RECENCY_TIME_PERIOD = 43200000L;
    private static final String TAG = "RRSResolverComparator";
    private static final long USAGE_STATS_PERIOD = 604800000L;
    private String mAction;
    private final Collator mCollator;
    private CountDownLatch mConnectSignal;
    private ResolverRankerServiceConnection mConnection;
    private Context mContext;
    private final long mCurrentTime;
    private final Object mLock = new Object();
    private IResolverRankerService mRanker;
    private ComponentName mRankerServiceName;
    private final String mReferrerPackage;
    private ComponentName mResolvedRankerName;
    private final long mSinceTime;
    private final Map<String, UsageStats> mStats;
    private ArrayList<ResolverTarget> mTargets;
    private final LinkedHashMap<ComponentName, ResolverTarget> mTargetsDict = new LinkedHashMap();

    public ResolverRankerServiceResolverComparator(Context context, Intent intent, String string2, AbstractResolverComparator.AfterCompute afterCompute) {
        super(context, intent);
        this.mCollator = Collator.getInstance(context.getResources().getConfiguration().locale);
        this.mReferrerPackage = string2;
        this.mContext = context;
        this.mCurrentTime = System.currentTimeMillis();
        this.mSinceTime = this.mCurrentTime - 604800000L;
        this.mStats = this.mUsm.queryAndAggregateUsageStats(this.mSinceTime, this.mCurrentTime);
        this.mAction = intent.getAction();
        this.mRankerServiceName = new ComponentName(this.mContext, this.getClass());
        this.setCallBack(afterCompute);
    }

    private void addDefaultSelectProbability(ResolverTarget resolverTarget) {
        resolverTarget.setSelectProbability((float)(1.0 / (Math.exp(1.6568f - (resolverTarget.getLaunchScore() * 2.5543f + resolverTarget.getTimeSpentScore() * 2.8412f + resolverTarget.getRecencyScore() * 0.269f + resolverTarget.getChooserScore() * 4.2222f)) + 1.0)));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initRanker(Context context) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConnection != null && this.mRanker != null) {
                return;
            }
        }
        object = this.resolveRankerService();
        if (object == null) {
            return;
        }
        this.mConnectSignal = new CountDownLatch(1);
        this.mConnection = new ResolverRankerServiceConnection(this.mConnectSignal);
        context.bindServiceAsUser((Intent)object, this.mConnection, 1, UserHandle.SYSTEM);
    }

    static boolean isPersistentProcess(ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        boolean bl = false;
        if (resolvedComponentInfo != null && resolvedComponentInfo.getCount() > 0) {
            if ((resolvedComponentInfo.getResolveInfoAt((int)0).activityInfo.applicationInfo.flags & 8) != 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private void logMetrics(int n) {
        if (this.mRankerServiceName != null) {
            MetricsLogger metricsLogger = new MetricsLogger();
            LogMaker logMaker = new LogMaker(1085);
            logMaker.setComponentName(this.mRankerServiceName);
            int n2 = this.mAnnotations == null ? 0 : 1;
            logMaker.addTaggedData(1086, n2);
            logMaker.addTaggedData(1087, n);
            metricsLogger.write(logMaker);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void predictSelectProbabilities(List<ResolverTarget> object) {
        if (this.mConnection != null) {
            block7 : {
                try {
                    this.mConnectSignal.await(200L, TimeUnit.MILLISECONDS);
                    Object object2 = this.mLock;
                    // MONITORENTER : object2
                    if (this.mRanker == null) break block7;
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error in Predict: ");
                    ((StringBuilder)object).append(remoteException);
                    Log.e(TAG, ((StringBuilder)object).toString());
                }
                catch (InterruptedException interruptedException) {
                    Log.e(TAG, "Error in Wait for Service Connection.");
                }
                this.mRanker.predict((List<ResolverTarget>)object, this.mConnection.resolverRankerResult);
                // MONITOREXIT : object2
                return;
            }
            // MONITOREXIT : object2
        }
        this.afterCompute();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Intent resolveRankerService() {
        Intent intent = new Intent("android.service.resolver.ResolverRankerService");
        Iterator<ResolveInfo> iterator = this.mPm.queryIntentServices(intent, 0).iterator();
        while (iterator.hasNext()) {
            ResolveInfo resolveInfo = iterator.next();
            if (resolveInfo == null || resolveInfo.serviceInfo == null || resolveInfo.serviceInfo.applicationInfo == null) continue;
            ComponentName componentName = new ComponentName(resolveInfo.serviceInfo.applicationInfo.packageName, resolveInfo.serviceInfo.name);
            try {
                boolean bl = "android.permission.BIND_RESOLVER_RANKER_SERVICE".equals(this.mPm.getServiceInfo((ComponentName)componentName, (int)0).permission);
                if (!bl) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ResolverRankerService ");
                    stringBuilder.append(componentName);
                    stringBuilder.append(" does not require permission ");
                    stringBuilder.append("android.permission.BIND_RESOLVER_RANKER_SERVICE");
                    stringBuilder.append(" - this service will not be queried for ResolverRankerServiceResolverComparator. add android:permission=\"");
                    stringBuilder.append("android.permission.BIND_RESOLVER_RANKER_SERVICE");
                    stringBuilder.append("\" to the <service> tag for ");
                    stringBuilder.append(componentName);
                    stringBuilder.append(" in the manifest.");
                    Log.w(TAG, stringBuilder.toString());
                    continue;
                }
                if (this.mPm.checkPermission("android.permission.PROVIDE_RESOLVER_RANKER_SERVICE", resolveInfo.serviceInfo.packageName) != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ResolverRankerService ");
                    stringBuilder.append(componentName);
                    stringBuilder.append(" does not hold permission ");
                    stringBuilder.append("android.permission.PROVIDE_RESOLVER_RANKER_SERVICE");
                    stringBuilder.append(" - this service will not be queried for ResolverRankerServiceResolverComparator.");
                    Log.w(TAG, stringBuilder.toString());
                    continue;
                }
                this.mResolvedRankerName = componentName;
                intent.setComponent(componentName);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not look up service ");
                stringBuilder.append(componentName);
                stringBuilder.append("; component name not found");
                Log.e(TAG, stringBuilder.toString());
                continue;
            }
            return intent;
            break;
        }
        return null;
    }

    private void setFeatures(ResolverTarget resolverTarget, float f, float f2, float f3, float f4) {
        resolverTarget.setRecencyScore(f);
        resolverTarget.setLaunchScore(f2);
        resolverTarget.setTimeSpentScore(f3);
        resolverTarget.setChooserScore(f4);
    }

    @Override
    void beforeCompute() {
        super.beforeCompute();
        this.mTargetsDict.clear();
        this.mTargets = null;
        this.mRankerServiceName = new ComponentName(this.mContext, this.getClass());
        this.mResolvedRankerName = null;
        this.initRanker(this.mContext);
    }

    @Override
    public int compare(ResolveInfo object, ResolveInfo resolveInfo) {
        Object object2;
        Object object3;
        if (this.mStats != null) {
            int n;
            object3 = this.mTargetsDict.get(new ComponentName(object.activityInfo.packageName, object.activityInfo.name));
            object2 = this.mTargetsDict.get(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            if (object3 != null && object2 != null && (n = Float.compare(((ResolverTarget)object2).getSelectProbability(), ((ResolverTarget)object3).getSelectProbability())) != 0) {
                n = n > 0 ? 1 : -1;
                return n;
            }
        }
        object3 = object2 = ((ResolveInfo)object).loadLabel(this.mPm);
        if (object2 == null) {
            object3 = object.activityInfo.name;
        }
        object = object2 = resolveInfo.loadLabel(this.mPm);
        if (object2 == null) {
            object = resolveInfo.activityInfo.name;
        }
        return this.mCollator.compare(object3.toString().trim(), object.toString().trim());
    }

    @Override
    public void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        ResolverRankerServiceConnection resolverRankerServiceConnection = this.mConnection;
        if (resolverRankerServiceConnection != null) {
            this.mContext.unbindService(resolverRankerServiceConnection);
            this.mConnection.destroy();
        }
        this.afterCompute();
    }

    @Override
    public void doCompute(List<ResolverActivity.ResolvedComponentInfo> iterator) {
        float f;
        long l = this.mCurrentTime;
        iterator = iterator.iterator();
        float f2 = 1.0f;
        float f3 = 1.0f;
        float f4 = 1.0f;
        float f5 = 1.0f;
        while (iterator.hasNext()) {
            float f6;
            float f7;
            float f8;
            float f9;
            ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = (ResolverActivity.ResolvedComponentInfo)((Object)iterator.next());
            ResolverTarget resolverTarget = new ResolverTarget();
            this.mTargetsDict.put(resolvedComponentInfo.name, resolverTarget);
            UsageStats parcelable = this.mStats.get(resolvedComponentInfo.name.getPackageName());
            if (parcelable != null) {
                if (!resolvedComponentInfo.name.getPackageName().equals(this.mReferrerPackage)) {
                    if (!ResolverRankerServiceResolverComparator.isPersistentProcess(resolvedComponentInfo)) {
                        f7 = Math.max(parcelable.getLastTimeUsed() - (l - 43200000L), 0L);
                        resolverTarget.setRecencyScore(f7);
                        f = f2;
                        if (f7 > f2) {
                            f = f7;
                        }
                    } else {
                        f = f2;
                    }
                } else {
                    f = f2;
                }
                f7 = parcelable.getTotalTimeInForeground();
                resolverTarget.setTimeSpentScore(f7);
                f2 = f3;
                if (f7 > f3) {
                    f2 = f7;
                }
                f7 = parcelable.mLaunchCount;
                resolverTarget.setLaunchScore(f7);
                f3 = f4;
                if (f7 > f4) {
                    f3 = f7;
                }
                f4 = 0.0f;
                if (parcelable.mChooserCounts != null && this.mAction != null && parcelable.mChooserCounts.get(this.mAction) != null) {
                    f4 = parcelable.mChooserCounts.get(this.mAction).getOrDefault(this.mContentType, 0).intValue();
                    if (this.mAnnotations != null) {
                        int n = this.mAnnotations.length;
                        for (int i = 0; i < n; ++i) {
                            f4 += (float)parcelable.mChooserCounts.get(this.mAction).getOrDefault(this.mAnnotations[i], 0).intValue();
                        }
                    }
                }
                resolverTarget.setChooserScore(f4);
                f6 = f;
                f8 = f2;
                f9 = f3;
                f7 = f5;
                if (f4 > f5) {
                    f6 = f;
                    f8 = f2;
                    f9 = f3;
                    f7 = f4;
                }
            } else {
                f7 = f5;
                f9 = f4;
                f8 = f3;
                f6 = f2;
            }
            f2 = f6;
            f3 = f8;
            f4 = f9;
            f5 = f7;
        }
        this.mTargets = new ArrayList<ResolverTarget>(this.mTargetsDict.values());
        for (ResolverTarget resolverTarget : this.mTargets) {
            f = resolverTarget.getRecencyScore() / f2;
            this.setFeatures(resolverTarget, f * f * 2.0f, resolverTarget.getLaunchScore() / f4, resolverTarget.getTimeSpentScore() / f3, resolverTarget.getChooserScore() / f5);
            this.addDefaultSelectProbability(resolverTarget);
        }
        this.predictSelectProbabilities(this.mTargets);
    }

    @Override
    public float getScore(ComponentName parcelable) {
        if ((parcelable = this.mTargetsDict.get(parcelable)) != null) {
            return ((ResolverTarget)parcelable).getSelectProbability();
        }
        return 0.0f;
    }

    @Override
    public void handleResultMessage(Message object) {
        if (((Message)object).what != 0) {
            return;
        }
        if (((Message)object).obj == null) {
            Log.e(TAG, "Receiving null prediction results.");
            return;
        }
        object = (List)((Message)object).obj;
        if (object != null && this.mTargets != null && object.size() == this.mTargets.size()) {
            int n = this.mTargets.size();
            boolean bl = false;
            for (int i = 0; i < n; ++i) {
                float f = ((ResolverTarget)object.get(i)).getSelectProbability();
                if (f == this.mTargets.get(i).getSelectProbability()) continue;
                this.mTargets.get(i).setSelectProbability(f);
                bl = true;
            }
            if (bl) {
                this.mRankerServiceName = this.mResolvedRankerName;
            }
        } else {
            Log.e(TAG, "Sizes of sent and received ResolverTargets diff.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void updateModel(ComponentName object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            ArrayList<ComponentName> arrayList = this.mRanker;
            if (arrayList != null) {
                try {
                    arrayList = new ArrayList<ComponentName>(this.mTargetsDict.keySet());
                    int n = arrayList.indexOf(object);
                    if (n >= 0 && this.mTargets != null) {
                        float f = this.getScore((ComponentName)object);
                        int n2 = 0;
                        object = this.mTargets.iterator();
                        while (object.hasNext()) {
                            int n3 = n2;
                            if (((ResolverTarget)object.next()).getSelectProbability() > f) {
                                n3 = n2 + 1;
                            }
                            n2 = n3;
                        }
                        this.logMetrics(n2);
                        this.mRanker.train(this.mTargets, n);
                    }
                }
                catch (RemoteException remoteException) {
                    arrayList = new ArrayList<ComponentName>();
                    ((StringBuilder)((Object)arrayList)).append("Error in Train: ");
                    ((StringBuilder)((Object)arrayList)).append(remoteException);
                    Log.e(TAG, ((StringBuilder)((Object)arrayList)).toString());
                }
            }
            return;
        }
    }

    private class ResolverRankerServiceConnection
    implements ServiceConnection {
        private final CountDownLatch mConnectSignal;
        public final IResolverRankerResult resolverRankerResult = new IResolverRankerResult.Stub(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sendResult(List<ResolverTarget> list) throws RemoteException {
                Object object = ResolverRankerServiceResolverComparator.this.mLock;
                synchronized (object) {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = list;
                    ResolverRankerServiceResolverComparator.this.mHandler.sendMessage(message);
                    return;
                }
            }
        };

        public ResolverRankerServiceConnection(CountDownLatch countDownLatch) {
            this.mConnectSignal = countDownLatch;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void destroy() {
            Object object = ResolverRankerServiceResolverComparator.this.mLock;
            synchronized (object) {
                ResolverRankerServiceResolverComparator.this.mRanker = null;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            object = ResolverRankerServiceResolverComparator.this.mLock;
            synchronized (object) {
                ResolverRankerServiceResolverComparator.this.mRanker = IResolverRankerService.Stub.asInterface(iBinder);
                this.mConnectSignal.countDown();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServiceDisconnected(ComponentName object) {
            object = ResolverRankerServiceResolverComparator.this.mLock;
            synchronized (object) {
                this.destroy();
                return;
            }
        }

    }

}

