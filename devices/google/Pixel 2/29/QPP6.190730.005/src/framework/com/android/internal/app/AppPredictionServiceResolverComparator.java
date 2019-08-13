/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import android.app.prediction.AppTargetEvent;
import android.app.prediction.AppTargetId;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverRankerServiceResolverComparator;
import com.android.internal.app._$$Lambda$AppPredictionServiceResolverComparator$25gj8kU_BfxuxUXCZ0QzLVhZs9Y;
import com.android.internal.app._$$Lambda$AppPredictionServiceResolverComparator$PQ_i16vesHTtkDyBgU_HkS0uF1A;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

class AppPredictionServiceResolverComparator
extends AbstractResolverComparator {
    private static final boolean DEBUG = false;
    private static final String TAG = "APSResolverComparator";
    private final AppPredictor mAppPredictor;
    private final Context mContext;
    private final Intent mIntent;
    private final String mReferrerPackage;
    private ResolverRankerServiceResolverComparator mResolverRankerService;
    private final Map<ComponentName, Integer> mTargetRanks = new HashMap<ComponentName, Integer>();
    private final UserHandle mUser;

    AppPredictionServiceResolverComparator(Context context, Intent intent, String string2, AppPredictor appPredictor, UserHandle userHandle) {
        super(context, intent);
        this.mContext = context;
        this.mIntent = intent;
        this.mAppPredictor = appPredictor;
        this.mUser = userHandle;
        this.mReferrerPackage = string2;
    }

    @Override
    int compare(ResolveInfo object, ResolveInfo object2) {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = this.mResolverRankerService;
        if (resolverRankerServiceResolverComparator != null) {
            return resolverRankerServiceResolverComparator.compare((ResolveInfo)object, (ResolveInfo)object2);
        }
        object = this.mTargetRanks.get(new ComponentName(object.activityInfo.packageName, object.activityInfo.name));
        object2 = this.mTargetRanks.get(new ComponentName(object2.activityInfo.packageName, object2.activityInfo.name));
        if (object == null && object2 == null) {
            return 0;
        }
        if (object == null) {
            return -1;
        }
        if (object2 == null) {
            return 1;
        }
        return (Integer)object - (Integer)object2;
    }

    @Override
    void destroy() {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = this.mResolverRankerService;
        if (resolverRankerServiceResolverComparator != null) {
            resolverRankerServiceResolverComparator.destroy();
            this.mResolverRankerService = null;
        }
    }

    @Override
    void doCompute(List<ResolverActivity.ResolvedComponentInfo> list) {
        if (list.isEmpty()) {
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        ArrayList<AppTarget> arrayList = new ArrayList<AppTarget>();
        for (ResolverActivity.ResolvedComponentInfo resolvedComponentInfo : list) {
            arrayList.add(new AppTarget.Builder(new AppTargetId(resolvedComponentInfo.name.flattenToString()), resolvedComponentInfo.name.getPackageName(), this.mUser).setClassName(resolvedComponentInfo.name.getClassName()).build());
        }
        this.mAppPredictor.sortTargets(arrayList, Executors.newSingleThreadExecutor(), new _$$Lambda$AppPredictionServiceResolverComparator$PQ_i16vesHTtkDyBgU_HkS0uF1A(this, list));
    }

    @Override
    float getScore(ComponentName comparable) {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = this.mResolverRankerService;
        if (resolverRankerServiceResolverComparator != null) {
            return resolverRankerServiceResolverComparator.getScore((ComponentName)comparable);
        }
        if ((comparable = this.mTargetRanks.get(comparable)) == null) {
            Log.w(TAG, "Score requested for unknown component.");
            return 0.0f;
        }
        int n = (this.mTargetRanks.size() - 1) * this.mTargetRanks.size() / 2;
        return 1.0f - (float)((Integer)comparable).intValue() / (float)n;
    }

    @Override
    void handleResultMessage(Message object) {
        block2 : {
            block1 : {
                if (((Message)object).what != 0 || ((Message)object).obj == null) break block1;
                object = (List)((Message)object).obj;
                for (int i = 0; i < object.size(); ++i) {
                    this.mTargetRanks.put(new ComponentName(((AppTarget)object.get(i)).getPackageName(), ((AppTarget)object.get(i)).getClassName()), i);
                }
                break block2;
            }
            if (((Message)object).obj != null || this.mResolverRankerService != null) break block2;
            Log.e(TAG, "Unexpected null result");
        }
    }

    public /* synthetic */ void lambda$doCompute$0$AppPredictionServiceResolverComparator() {
        this.mHandler.sendEmptyMessage(0);
    }

    public /* synthetic */ void lambda$doCompute$1$AppPredictionServiceResolverComparator(List list, List list2) {
        if (list2.isEmpty()) {
            this.mResolverRankerService = new ResolverRankerServiceResolverComparator(this.mContext, this.mIntent, this.mReferrerPackage, new _$$Lambda$AppPredictionServiceResolverComparator$25gj8kU_BfxuxUXCZ0QzLVhZs9Y(this));
            this.mResolverRankerService.compute(list);
        } else {
            Message.obtain(this.mHandler, 0, list2).sendToTarget();
        }
    }

    @Override
    void updateModel(ComponentName componentName) {
        ResolverRankerServiceResolverComparator resolverRankerServiceResolverComparator = this.mResolverRankerService;
        if (resolverRankerServiceResolverComparator != null) {
            resolverRankerServiceResolverComparator.updateModel(componentName);
            return;
        }
        this.mAppPredictor.notifyAppTargetEvent(new AppTargetEvent.Builder(new AppTarget.Builder(new AppTargetId(componentName.toString()), componentName.getPackageName(), this.mUser).setClassName(componentName.getClassName()).build(), 1).build());
    }
}

