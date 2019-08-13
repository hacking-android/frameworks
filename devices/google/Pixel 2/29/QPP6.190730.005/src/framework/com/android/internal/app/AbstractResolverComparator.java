/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.app.ResolverActivity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

abstract class AbstractResolverComparator
implements Comparator<ResolverActivity.ResolvedComponentInfo> {
    private static final boolean DEBUG = false;
    private static final int NUM_OF_TOP_ANNOTATIONS_TO_USE = 3;
    static final int RANKER_RESULT_TIMEOUT = 1;
    static final int RANKER_SERVICE_RESULT = 0;
    private static final String TAG = "AbstractResolverComp";
    private static final int WATCHDOG_TIMEOUT_MILLIS = 500;
    protected AfterCompute mAfterCompute;
    protected String[] mAnnotations;
    protected String mContentType;
    private final String mDefaultBrowserPackageName;
    protected final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 0) {
                if (n != 1) {
                    super.handleMessage(message);
                } else {
                    AbstractResolverComparator.this.mHandler.removeMessages(0);
                    AbstractResolverComparator.this.afterCompute();
                }
            } else if (AbstractResolverComparator.this.mHandler.hasMessages(1)) {
                AbstractResolverComparator.this.handleResultMessage(message);
                AbstractResolverComparator.this.mHandler.removeMessages(1);
                AbstractResolverComparator.this.afterCompute();
            }
        }
    };
    private final boolean mHttp;
    protected final PackageManager mPm;
    protected final UsageStatsManager mUsm;

    AbstractResolverComparator(Context object, Intent intent) {
        String string2 = intent.getScheme();
        boolean bl = "http".equals(string2) || "https".equals(string2);
        this.mHttp = bl;
        this.mContentType = intent.getType();
        this.getContentAnnotations(intent);
        this.mPm = ((Context)object).getPackageManager();
        this.mUsm = (UsageStatsManager)((Context)object).getSystemService("usagestats");
        object = this.mHttp ? this.mPm.getDefaultBrowserPackageNameAsUser(UserHandle.myUserId()) : null;
        this.mDefaultBrowserPackageName = object;
    }

    private void getContentAnnotations(Intent cloneable) {
        if ((cloneable = ((Intent)cloneable).getStringArrayListExtra("android.intent.extra.CONTENT_ANNOTATIONS")) != null) {
            int n;
            int n2 = n = ((ArrayList)cloneable).size();
            if (n > 3) {
                n2 = 3;
            }
            this.mAnnotations = new String[n2];
            for (n = 0; n < n2; ++n) {
                this.mAnnotations[n] = (String)((ArrayList)cloneable).get(n);
            }
        }
    }

    private boolean isDefaultBrowser(ResolveInfo resolveInfo) {
        if (resolveInfo.targetUserId != -2) {
            return false;
        }
        return resolveInfo.activityInfo.packageName != null && resolveInfo.activityInfo.packageName.equals(this.mDefaultBrowserPackageName);
    }

    protected final void afterCompute() {
        AfterCompute afterCompute = this.mAfterCompute;
        if (afterCompute != null) {
            afterCompute.afterCompute();
        }
    }

    void beforeCompute() {
        Handler handler = this.mHandler;
        if (handler == null) {
            Log.d(TAG, "Error: Handler is Null; Needs to be initialized.");
            return;
        }
        handler.sendEmptyMessageDelayed(1, 500L);
    }

    @Override
    abstract int compare(ResolveInfo var1, ResolveInfo var2);

    @Override
    public final int compare(ResolverActivity.ResolvedComponentInfo object, ResolverActivity.ResolvedComponentInfo object2) {
        int n = 0;
        object = ((ResolverActivity.ResolvedComponentInfo)object).getResolveInfoAt(0);
        object2 = ((ResolverActivity.ResolvedComponentInfo)object2).getResolveInfoAt(0);
        if (((ResolveInfo)object).targetUserId != -2) {
            if (((ResolveInfo)object2).targetUserId == -2) {
                n = 1;
            }
            return n;
        }
        int n2 = ((ResolveInfo)object2).targetUserId;
        n = -1;
        if (n2 != -2) {
            return -1;
        }
        if (this.mHttp) {
            if (this.isDefaultBrowser((ResolveInfo)object)) {
                return -1;
            }
            if (this.isDefaultBrowser((ResolveInfo)object2)) {
                return 1;
            }
            boolean bl = ResolverActivity.isSpecificUriMatch(((ResolveInfo)object).match);
            if (bl != ResolverActivity.isSpecificUriMatch(((ResolveInfo)object2).match)) {
                if (!bl) {
                    n = 1;
                }
                return n;
            }
        }
        return this.compare((ResolveInfo)object, (ResolveInfo)object2);
    }

    final void compute(List<ResolverActivity.ResolvedComponentInfo> list) {
        this.beforeCompute();
        this.doCompute(list);
    }

    void destroy() {
        this.mHandler.removeMessages(0);
        this.mHandler.removeMessages(1);
        this.afterCompute();
    }

    abstract void doCompute(List<ResolverActivity.ResolvedComponentInfo> var1);

    abstract float getScore(ComponentName var1);

    abstract void handleResultMessage(Message var1);

    void setCallBack(AfterCompute afterCompute) {
        this.mAfterCompute = afterCompute;
    }

    final void updateChooserCounts(String string2, int n, String string3) {
        UsageStatsManager usageStatsManager = this.mUsm;
        if (usageStatsManager != null) {
            usageStatsManager.reportChooserSelection(string2, n, this.mContentType, this.mAnnotations, string3);
        }
    }

    void updateModel(ComponentName componentName) {
    }

    static interface AfterCompute {
        public void afterCompute();
    }

}

