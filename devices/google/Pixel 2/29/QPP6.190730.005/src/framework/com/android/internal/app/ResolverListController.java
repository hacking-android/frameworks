/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.AbstractResolverComparator;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.ResolverRankerServiceResolverComparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ResolverListController {
    private static final boolean DEBUG = false;
    private static final String TAG = "ResolverListController";
    private boolean isComputed = false;
    private final Context mContext;
    private final int mLaunchedFromUid;
    private final String mReferrerPackage;
    private AbstractResolverComparator mResolverComparator;
    private final Intent mTargetIntent;
    private final PackageManager mpm;

    public ResolverListController(Context context, PackageManager packageManager, Intent intent, String string2, int n) {
        this(context, packageManager, intent, string2, n, new ResolverRankerServiceResolverComparator(context, intent, string2, null));
    }

    public ResolverListController(Context context, PackageManager packageManager, Intent intent, String string2, int n, AbstractResolverComparator abstractResolverComparator) {
        this.mContext = context;
        this.mpm = packageManager;
        this.mLaunchedFromUid = n;
        this.mTargetIntent = intent;
        this.mReferrerPackage = string2;
        this.mResolverComparator = abstractResolverComparator;
    }

    private static boolean isSameResolvedComponent(ResolveInfo parcelable, ResolverActivity.ResolvedComponentInfo resolvedComponentInfo) {
        parcelable = parcelable.activityInfo;
        boolean bl = ((ActivityInfo)parcelable).packageName.equals(resolvedComponentInfo.name.getPackageName()) && ((ActivityInfo)parcelable).name.equals(resolvedComponentInfo.name.getClassName());
        return bl;
    }

    @VisibleForTesting
    public void addResolveListDedupe(List<ResolverActivity.ResolvedComponentInfo> list, Intent intent, List<ResolveInfo> list2) {
        int n = list2.size();
        int n2 = list.size();
        for (int i = 0; i < n; ++i) {
            boolean bl;
            ResolveInfo resolveInfo = list2.get(i);
            boolean bl2 = false;
            int n3 = 0;
            do {
                bl = bl2;
                if (n3 >= n2) break;
                ResolverActivity.ResolvedComponentInfo resolvedComponentInfo = list.get(n3);
                if (ResolverListController.isSameResolvedComponent(resolveInfo, resolvedComponentInfo)) {
                    bl = true;
                    resolvedComponentInfo.add(intent, resolveInfo);
                    break;
                }
                ++n3;
            } while (true);
            if (bl) continue;
            list.add(new ResolverActivity.ResolvedComponentInfo(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name), intent, resolveInfo));
        }
    }

    public void destroy() {
        this.mResolverComparator.destroy();
    }

    @VisibleForTesting
    public ArrayList<ResolverActivity.ResolvedComponentInfo> filterIneligibleActivities(List<ResolverActivity.ResolvedComponentInfo> list, boolean bl) {
        ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList = null;
        for (int i = list.size() - 1; i >= 0; --i) {
            ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList2;
            block7 : {
                block6 : {
                    ActivityInfo activityInfo = list.get((int)i).getResolveInfoAt((int)0).activityInfo;
                    if (ActivityManager.checkComponentPermission(activityInfo.permission, this.mLaunchedFromUid, activityInfo.applicationInfo.uid, activityInfo.exported) != 0) break block6;
                    arrayList2 = arrayList;
                    if (!this.isComponentFiltered(activityInfo.getComponentName())) break block7;
                }
                arrayList2 = arrayList;
                if (bl) {
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList<ResolverActivity.ResolvedComponentInfo>(list);
                    }
                }
                list.remove(i);
            }
            arrayList = arrayList2;
        }
        return arrayList;
    }

    @VisibleForTesting
    public ArrayList<ResolverActivity.ResolvedComponentInfo> filterLowPriority(List<ResolverActivity.ResolvedComponentInfo> list, boolean bl) {
        ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList = null;
        ResolveInfo resolveInfo = list.get(0).getResolveInfoAt(0);
        int n = list.size();
        for (int i = 1; i < n; ++i) {
            ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList2;
            int n2;
            block8 : {
                int n3;
                ArrayList<ResolverActivity.ResolvedComponentInfo> arrayList3;
                block7 : {
                    ResolveInfo resolveInfo2 = list.get(i).getResolveInfoAt(0);
                    arrayList3 = arrayList;
                    n3 = n;
                    if (resolveInfo.priority != resolveInfo2.priority) break block7;
                    arrayList2 = arrayList;
                    n2 = n;
                    if (resolveInfo.isDefault == resolveInfo2.isDefault) break block8;
                    n3 = n;
                    arrayList3 = arrayList;
                }
                do {
                    arrayList2 = arrayList3;
                    n2 = --n3;
                    if (i >= n3) break;
                    arrayList = arrayList3;
                    if (bl) {
                        arrayList = arrayList3;
                        if (arrayList3 == null) {
                            arrayList = new ArrayList<ResolverActivity.ResolvedComponentInfo>(list);
                        }
                    }
                    list.remove(i);
                    arrayList3 = arrayList;
                } while (true);
            }
            arrayList = arrayList2;
            n = n2;
        }
        return arrayList;
    }

    @VisibleForTesting
    public ResolveInfo getLastChosen() throws RemoteException {
        IPackageManager iPackageManager = AppGlobals.getPackageManager();
        Intent intent = this.mTargetIntent;
        return iPackageManager.getLastChosenActivity(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 65536);
    }

    @VisibleForTesting
    public List<ResolverActivity.ResolvedComponentInfo> getResolversForIntent(boolean bl, boolean bl2, List<Intent> list) {
        Object object = null;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            int n2;
            Object object2;
            Intent intent;
            block8 : {
                int n3;
                block7 : {
                    intent = list.get(i);
                    n3 = 0;
                    n2 = bl ? 64 : 0;
                    if (bl2) {
                        n3 = 128;
                    }
                    n3 = 65536 | n2 | n3;
                    if (intent.isWebIntent()) break block7;
                    n2 = n3;
                    if ((intent.getFlags() & 2048) == 0) break block8;
                }
                n2 = n3 | 8388608;
            }
            List<ResolveInfo> list2 = this.mpm.queryIntentActivities(intent, n2);
            for (n2 = list2.size() - 1; n2 >= 0; --n2) {
                object2 = list2.get(n2);
                if (((ResolveInfo)object2).activityInfo == null || object2.activityInfo.exported) continue;
                list2.remove(n2);
            }
            object2 = object;
            if (object == null) {
                object2 = new ArrayList();
            }
            this.addResolveListDedupe((List<ResolverActivity.ResolvedComponentInfo>)object2, intent, list2);
            object = object2;
        }
        return object;
    }

    @VisibleForTesting
    public float getScore(ResolverActivity.DisplayResolveInfo displayResolveInfo) {
        return this.mResolverComparator.getScore(displayResolveInfo.getResolvedComponentName());
    }

    boolean isComponentFiltered(ComponentName componentName) {
        return false;
    }

    @VisibleForTesting
    public void setLastChosen(Intent intent, IntentFilter intentFilter, int n) throws RemoteException {
        AppGlobals.getPackageManager().setLastChosenActivity(intent, intent.resolveType(this.mContext.getContentResolver()), 65536, intentFilter, n, intent.getComponent());
    }

    @VisibleForTesting
    public void sort(List<ResolverActivity.ResolvedComponentInfo> object) {
        if (this.mResolverComparator == null) {
            Log.d(TAG, "Comparator has already been destroyed; skipped.");
            return;
        }
        try {
            System.currentTimeMillis();
            if (!this.isComputed) {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                ComputeCallback computeCallback = new ComputeCallback(countDownLatch);
                this.mResolverComparator.setCallBack(computeCallback);
                this.mResolverComparator.compute((List<ResolverActivity.ResolvedComponentInfo>)object);
                countDownLatch.await();
                this.isComputed = true;
            }
            Collections.sort(object, this.mResolverComparator);
            System.currentTimeMillis();
        }
        catch (InterruptedException interruptedException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Compute & Sort was interrupted: ");
            ((StringBuilder)object).append(interruptedException);
            Log.e(TAG, ((StringBuilder)object).toString());
        }
    }

    public void updateChooserCounts(String string2, int n, String string3) {
        this.mResolverComparator.updateChooserCounts(string2, n, string3);
    }

    public void updateModel(ComponentName componentName) {
        this.mResolverComparator.updateModel(componentName);
    }

    private class ComputeCallback
    implements AbstractResolverComparator.AfterCompute {
        private CountDownLatch mFinishComputeSignal;

        public ComputeCallback(CountDownLatch countDownLatch) {
            this.mFinishComputeSignal = countDownLatch;
        }

        @Override
        public void afterCompute() {
            this.mFinishComputeSignal.countDown();
        }
    }

}

