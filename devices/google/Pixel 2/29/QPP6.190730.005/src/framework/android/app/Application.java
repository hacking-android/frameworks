/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.app.LoadedApk;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.autofill.AutofillManager;
import android.view.autofill.Helper;
import java.util.ArrayList;

public class Application
extends ContextWrapper
implements ComponentCallbacks2 {
    private static final String TAG = "Application";
    @UnsupportedAppUsage
    private ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList();
    @UnsupportedAppUsage
    private ArrayList<OnProvideAssistDataListener> mAssistCallbacks = null;
    @UnsupportedAppUsage
    private ArrayList<ComponentCallbacks> mComponentCallbacks = new ArrayList();
    @UnsupportedAppUsage
    public LoadedApk mLoadedApk;

    public Application() {
        super(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private Object[] collectActivityLifecycleCallbacks() {
        Object[] arrobject = null;
        ArrayList<ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            if (this.mActivityLifecycleCallbacks.size() <= 0) return arrobject;
            return this.mActivityLifecycleCallbacks.toArray();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object[] collectComponentCallbacks() {
        Object[] arrobject = null;
        ArrayList<ComponentCallbacks> arrayList = this.mComponentCallbacks;
        synchronized (arrayList) {
            if (this.mComponentCallbacks.size() <= 0) return arrobject;
            return this.mComponentCallbacks.toArray();
        }
    }

    public static String getProcessName() {
        return ActivityThread.currentProcessName();
    }

    @UnsupportedAppUsage
    final void attach(Context context) {
        this.attachBaseContext(context);
        this.mLoadedApk = ContextImpl.getImpl((Context)context).mPackageInfo;
    }

    @UnsupportedAppUsage
    void dispatchActivityCreated(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityCreated(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityDestroyed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityDestroyed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPaused(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPaused(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostCreated(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostCreated(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostDestroyed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostDestroyed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostPaused(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostPaused(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostResumed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostResumed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostSaveInstanceState(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostStarted(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostStarted(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPostStopped(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPostStopped(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreCreated(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreCreated(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreDestroyed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreDestroyed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPrePaused(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPrePaused(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreResumed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreResumed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreSaveInstanceState(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreStarted(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreStarted(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityPreStopped(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityPreStopped(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityResumed(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityResumed(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityStarted(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityStarted(activity);
            }
        }
    }

    @UnsupportedAppUsage
    void dispatchActivityStopped(Activity activity) {
        Object[] arrobject = this.collectActivityLifecycleCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ActivityLifecycleCallbacks)arrobject[i]).onActivityStopped(activity);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void dispatchOnProvideAssistData(Activity activity, Bundle bundle) {
        // MONITORENTER : this
        if (this.mAssistCallbacks == null) {
            // MONITOREXIT : this
            return;
        }
        Object[] arrobject = this.mAssistCallbacks.toArray();
        // MONITOREXIT : this
        if (arrobject == null) return;
        int n = 0;
        while (n < arrobject.length) {
            ((OnProvideAssistDataListener)arrobject[n]).onProvideAssistData(activity, bundle);
            ++n;
        }
    }

    @Override
    public AutofillManager.AutofillClient getAutofillClient() {
        Object object;
        Object object2 = super.getAutofillClient();
        if (object2 != null) {
            return object2;
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "getAutofillClient(): null on super, trying to find activity thread");
        }
        if ((object = ActivityThread.currentActivityThread()) == null) {
            return null;
        }
        int n = ((ActivityThread)object).mActivities.size();
        for (int i = 0; i < n; ++i) {
            object2 = ((ActivityThread)object).mActivities.valueAt(i);
            if (object2 == null || (object2 = ((ActivityThread.ActivityClientRecord)object2).activity) == null || !((Activity)object2).getWindow().getDecorView().hasFocus()) continue;
            if (Helper.sVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("getAutofillClient(): found activity for ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(object2);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return object2;
        }
        if (Helper.sVerbose) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getAutofillClient(): none of the ");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" activities on ");
            ((StringBuilder)object2).append(this);
            ((StringBuilder)object2).append(" have focus");
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        Object[] arrobject = this.collectComponentCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ComponentCallbacks)arrobject[i]).onConfigurationChanged(configuration);
            }
        }
    }

    public void onCreate() {
    }

    @Override
    public void onLowMemory() {
        Object[] arrobject = this.collectComponentCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                ((ComponentCallbacks)arrobject[i]).onLowMemory();
            }
        }
    }

    public void onTerminate() {
    }

    @Override
    public void onTrimMemory(int n) {
        Object[] arrobject = this.collectComponentCallbacks();
        if (arrobject != null) {
            for (int i = 0; i < arrobject.length; ++i) {
                Object object = arrobject[i];
                if (!(object instanceof ComponentCallbacks2)) continue;
                ((ComponentCallbacks2)object).onTrimMemory(n);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        ArrayList<ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        ArrayList<ComponentCallbacks> arrayList = this.mComponentCallbacks;
        synchronized (arrayList) {
            this.mComponentCallbacks.add(componentCallbacks);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            if (this.mAssistCallbacks == null) {
                ArrayList arrayList = new ArrayList();
                this.mAssistCallbacks = arrayList;
            }
            this.mAssistCallbacks.add(onProvideAssistDataListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        ArrayList<ActivityLifecycleCallbacks> arrayList = this.mActivityLifecycleCallbacks;
        synchronized (arrayList) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        ArrayList<ComponentCallbacks> arrayList = this.mComponentCallbacks;
        synchronized (arrayList) {
            this.mComponentCallbacks.remove(componentCallbacks);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            if (this.mAssistCallbacks != null) {
                this.mAssistCallbacks.remove(onProvideAssistDataListener);
            }
            return;
        }
    }

    public static interface ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity var1, Bundle var2);

        public void onActivityDestroyed(Activity var1);

        public void onActivityPaused(Activity var1);

        default public void onActivityPostCreated(Activity activity, Bundle bundle) {
        }

        default public void onActivityPostDestroyed(Activity activity) {
        }

        default public void onActivityPostPaused(Activity activity) {
        }

        default public void onActivityPostResumed(Activity activity) {
        }

        default public void onActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
        }

        default public void onActivityPostStarted(Activity activity) {
        }

        default public void onActivityPostStopped(Activity activity) {
        }

        default public void onActivityPreCreated(Activity activity, Bundle bundle) {
        }

        default public void onActivityPreDestroyed(Activity activity) {
        }

        default public void onActivityPrePaused(Activity activity) {
        }

        default public void onActivityPreResumed(Activity activity) {
        }

        default public void onActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
        }

        default public void onActivityPreStarted(Activity activity) {
        }

        default public void onActivityPreStopped(Activity activity) {
        }

        public void onActivityResumed(Activity var1);

        public void onActivitySaveInstanceState(Activity var1, Bundle var2);

        public void onActivityStarted(Activity var1);

        public void onActivityStopped(Activity var1);
    }

    public static interface OnProvideAssistDataListener {
        public void onProvideAssistData(Activity var1, Bundle var2);
    }

}

