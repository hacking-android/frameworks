/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.servertransaction.PendingTransactionActions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Window;
import com.android.internal.content.ReferrerIntent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public class LocalActivityManager {
    static final int CREATED = 2;
    static final int DESTROYED = 5;
    static final int INITIALIZING = 1;
    static final int RESTORED = 0;
    static final int RESUMED = 4;
    static final int STARTED = 3;
    private static final String TAG = "LocalActivityManager";
    private static final boolean localLOGV = false;
    @UnsupportedAppUsage
    private final Map<String, LocalActivityRecord> mActivities = new HashMap<String, LocalActivityRecord>();
    @UnsupportedAppUsage
    private final ArrayList<LocalActivityRecord> mActivityArray = new ArrayList();
    private final ActivityThread mActivityThread = ActivityThread.currentActivityThread();
    private int mCurState = 1;
    private boolean mFinishing;
    @UnsupportedAppUsage
    private final Activity mParent;
    @UnsupportedAppUsage
    private LocalActivityRecord mResumed;
    @UnsupportedAppUsage
    private boolean mSingleMode;

    public LocalActivityManager(Activity activity, boolean bl) {
        this.mParent = activity;
        this.mSingleMode = bl;
    }

    @UnsupportedAppUsage
    private void moveToState(LocalActivityRecord localActivityRecord, int n) {
        if (localActivityRecord.curState != 0 && localActivityRecord.curState != 5) {
            if (localActivityRecord.curState == 1) {
                Object object;
                HashMap<String, Object> hashMap = this.mParent.getLastNonConfigurationChildInstances();
                hashMap = hashMap != null ? hashMap.get(localActivityRecord.id) : null;
                if (hashMap != null) {
                    object = new Activity.NonConfigurationInstances();
                    ((Activity.NonConfigurationInstances)object).activity = hashMap;
                    hashMap = object;
                } else {
                    hashMap = null;
                }
                if (localActivityRecord.activityInfo == null) {
                    localActivityRecord.activityInfo = this.mActivityThread.resolveActivityInfo(localActivityRecord.intent);
                }
                localActivityRecord.activity = this.mActivityThread.startActivityNow(this.mParent, localActivityRecord.id, localActivityRecord.intent, localActivityRecord.activityInfo, localActivityRecord, localActivityRecord.instanceState, (Activity.NonConfigurationInstances)((Object)hashMap), localActivityRecord);
                if (localActivityRecord.activity == null) {
                    return;
                }
                localActivityRecord.window = localActivityRecord.activity.getWindow();
                localActivityRecord.instanceState = null;
                object = this.mActivityThread.getActivityClient(localActivityRecord);
                if (!localActivityRecord.activity.mFinished) {
                    hashMap = new PendingTransactionActions();
                    ((PendingTransactionActions)((Object)hashMap)).setOldState(((ActivityThread.ActivityClientRecord)object).state);
                    ((PendingTransactionActions)((Object)hashMap)).setRestoreInstanceState(true);
                    ((PendingTransactionActions)((Object)hashMap)).setCallOnPostCreate(true);
                } else {
                    hashMap = null;
                }
                this.mActivityThread.handleStartActivity((ActivityThread.ActivityClientRecord)object, (PendingTransactionActions)((Object)hashMap));
                localActivityRecord.curState = 3;
                if (n == 4) {
                    this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-INITIALIZING");
                    localActivityRecord.curState = 4;
                }
                return;
            }
            int n2 = localActivityRecord.curState;
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 != 4) {
                        return;
                    }
                    if (n == 3) {
                        this.performPause(localActivityRecord, this.mFinishing);
                        localActivityRecord.curState = 3;
                    }
                    if (n == 2) {
                        this.performPause(localActivityRecord, this.mFinishing);
                        this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-RESUMED");
                        localActivityRecord.curState = 2;
                    }
                    return;
                }
                if (n == 4) {
                    this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-STARTED");
                    localActivityRecord.instanceState = null;
                    localActivityRecord.curState = 4;
                }
                if (n == 2) {
                    this.mActivityThread.performStopActivity(localActivityRecord, false, "moveToState-STARTED");
                    localActivityRecord.curState = 2;
                }
                return;
            }
            if (n == 3) {
                this.mActivityThread.performRestartActivity(localActivityRecord, true);
                localActivityRecord.curState = 3;
            }
            if (n == 4) {
                this.mActivityThread.performRestartActivity(localActivityRecord, true);
                this.mActivityThread.performResumeActivity(localActivityRecord, true, "moveToState-CREATED");
                localActivityRecord.curState = 4;
            }
            return;
        }
    }

    private Window performDestroy(LocalActivityRecord localActivityRecord, boolean bl) {
        Window window = localActivityRecord.window;
        if (localActivityRecord.curState == 4 && !bl) {
            this.performPause(localActivityRecord, bl);
        }
        this.mActivityThread.performDestroyActivity(localActivityRecord, bl, 0, false, "LocalActivityManager::performDestroy");
        localActivityRecord.activity = null;
        localActivityRecord.window = null;
        if (bl) {
            localActivityRecord.instanceState = null;
        }
        localActivityRecord.curState = 5;
        return window;
    }

    private void performPause(LocalActivityRecord localActivityRecord, boolean bl) {
        boolean bl2 = localActivityRecord.instanceState == null;
        Bundle bundle = this.mActivityThread.performPauseActivity(localActivityRecord, bl, "performPause", null);
        if (bl2) {
            localActivityRecord.instanceState = bundle;
        }
    }

    public Window destroyActivity(String string2, boolean bl) {
        LocalActivityRecord localActivityRecord = this.mActivities.get(string2);
        Window window = null;
        if (localActivityRecord != null) {
            Window window2;
            window = window2 = this.performDestroy(localActivityRecord, bl);
            if (bl) {
                this.mActivities.remove(string2);
                this.mActivityArray.remove(localActivityRecord);
                window = window2;
            }
        }
        return window;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispatchCreate(Bundle bundle) {
        if (bundle != null) {
            for (String string2 : bundle.keySet()) {
                try {
                    Bundle bundle2 = bundle.getBundle(string2);
                    LocalActivityRecord localActivityRecord = this.mActivities.get(string2);
                    if (localActivityRecord != null) {
                        localActivityRecord.instanceState = bundle2;
                        continue;
                    }
                    localActivityRecord = new LocalActivityRecord(string2, null);
                    localActivityRecord.instanceState = bundle2;
                    this.mActivities.put(string2, localActivityRecord);
                    this.mActivityArray.add(localActivityRecord);
                }
                catch (Exception exception) {
                    Log.e(TAG, "Exception thrown when restoring LocalActivityManager state", exception);
                }
            }
        }
        this.mCurState = 2;
    }

    public void dispatchDestroy(boolean bl) {
        int n = this.mActivityArray.size();
        for (int i = 0; i < n; ++i) {
            LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            this.mActivityThread.performDestroyActivity(localActivityRecord, bl, 0, false, "LocalActivityManager::dispatchDestroy");
        }
        this.mActivities.clear();
        this.mActivityArray.clear();
    }

    public void dispatchPause(boolean bl) {
        if (bl) {
            this.mFinishing = true;
        }
        this.mCurState = 3;
        if (this.mSingleMode) {
            LocalActivityRecord localActivityRecord = this.mResumed;
            if (localActivityRecord != null) {
                this.moveToState(localActivityRecord, 3);
            }
        } else {
            int n = this.mActivityArray.size();
            for (int i = 0; i < n; ++i) {
                LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
                if (localActivityRecord.curState != 4) continue;
                this.moveToState(localActivityRecord, 3);
            }
        }
    }

    public void dispatchResume() {
        this.mCurState = 4;
        if (this.mSingleMode) {
            LocalActivityRecord localActivityRecord = this.mResumed;
            if (localActivityRecord != null) {
                this.moveToState(localActivityRecord, 4);
            }
        } else {
            int n = this.mActivityArray.size();
            for (int i = 0; i < n; ++i) {
                this.moveToState(this.mActivityArray.get(i), 4);
            }
        }
    }

    public HashMap<String, Object> dispatchRetainNonConfigurationInstance() {
        HashMap<String, Object> hashMap = null;
        int n = this.mActivityArray.size();
        for (int i = 0; i < n; ++i) {
            LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            HashMap<String, Object> hashMap2 = hashMap;
            if (localActivityRecord != null) {
                hashMap2 = hashMap;
                if (localActivityRecord.activity != null) {
                    Object object = localActivityRecord.activity.onRetainNonConfigurationInstance();
                    hashMap2 = hashMap;
                    if (object != null) {
                        hashMap2 = hashMap;
                        if (hashMap == null) {
                            hashMap2 = new HashMap<String, Object>();
                        }
                        hashMap2.put(localActivityRecord.id, object);
                    }
                }
            }
            hashMap = hashMap2;
        }
        return hashMap;
    }

    public void dispatchStop() {
        this.mCurState = 2;
        int n = this.mActivityArray.size();
        for (int i = 0; i < n; ++i) {
            this.moveToState(this.mActivityArray.get(i), 2);
        }
    }

    public Activity getActivity(String object) {
        object = (object = this.mActivities.get(object)) != null ? ((LocalActivityRecord)object).activity : null;
        return object;
    }

    public Activity getCurrentActivity() {
        Object object = this.mResumed;
        object = object != null ? ((LocalActivityRecord)object).activity : null;
        return object;
    }

    public String getCurrentId() {
        Object object = this.mResumed;
        object = object != null ? ((LocalActivityRecord)object).id : null;
        return object;
    }

    public void removeAllActivities() {
        this.dispatchDestroy(true);
    }

    public Bundle saveInstanceState() {
        Bundle bundle = null;
        int n = this.mActivityArray.size();
        for (int i = 0; i < n; ++i) {
            LocalActivityRecord localActivityRecord = this.mActivityArray.get(i);
            Bundle bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            if ((localActivityRecord.instanceState != null || localActivityRecord.curState == 4) && localActivityRecord.activity != null) {
                bundle = new Bundle();
                localActivityRecord.activity.performSaveInstanceState(bundle);
                localActivityRecord.instanceState = bundle;
            }
            if (localActivityRecord.instanceState != null) {
                bundle2.putBundle(localActivityRecord.id, localActivityRecord.instanceState);
            }
            bundle = bundle2;
        }
        return bundle;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Window startActivity(String var1_1, Intent var2_2) {
        block9 : {
            block11 : {
                block10 : {
                    block8 : {
                        if (this.mCurState == 1) throw new IllegalStateException("Activities can't be added until the containing group has been created.");
                        var3_3 = false;
                        var4_4 = false;
                        var5_5 = null;
                        var6_6 = this.mActivities.get(var1_1);
                        if (var6_6 == null) {
                            var7_7 = new LocalActivityRecord((String)var1_1, var2_2);
                            var8_8 = true;
                            var9_9 = var5_5;
                        } else {
                            var8_8 = var3_3;
                            var9_9 = var5_5;
                            var7_7 = var6_6;
                            if (var6_6.intent != null) {
                                var10_10 = var6_6.intent.filterEquals(var2_2);
                                var8_8 = var3_3;
                                var4_4 = var10_10;
                                var9_9 = var5_5;
                                var7_7 = var6_6;
                                if (var10_10) {
                                    var9_9 = var6_6.activityInfo;
                                    var7_7 = var6_6;
                                    var4_4 = var10_10;
                                    var8_8 = var3_3;
                                }
                            }
                        }
                        var6_6 = var9_9;
                        if (var9_9 == null) {
                            var6_6 = this.mActivityThread.resolveActivityInfo(var2_2);
                        }
                        if (this.mSingleMode && (var9_9 = this.mResumed) != null && var9_9 != var7_7 && this.mCurState == 4) {
                            this.moveToState((LocalActivityRecord)var9_9, 3);
                        }
                        if (!var8_8) break block8;
                        this.mActivities.put((String)var1_1, (LocalActivityRecord)var7_7);
                        this.mActivityArray.add((LocalActivityRecord)var7_7);
                        break block9;
                    }
                    if (var7_7.activityInfo == null) break block9;
                    if (var6_6 != var7_7.activityInfo && (!var6_6.name.equals(var7_7.activityInfo.name) || !var6_6.packageName.equals(var7_7.activityInfo.packageName))) break block10;
                    if (var6_6.launchMode == 0 && (var2_2.getFlags() & 536870912) == 0) {
                        if (var4_4 && (var2_2.getFlags() & 67108864) == 0) {
                            var7_7.intent = var2_2;
                            this.moveToState((LocalActivityRecord)var7_7, this.mCurState);
                            if (this.mSingleMode == false) return var7_7.window;
                            this.mResumed = var7_7;
                            return var7_7.window;
                        } else {
                            ** GOTO lbl47
                        }
                    }
                    break block11;
                }
                this.performDestroy((LocalActivityRecord)var7_7, true);
                break block9;
            }
            var1_1 = new ArrayList<E>(1);
            var1_1.add(new ReferrerIntent(var2_2, this.mParent.getPackageName()));
            this.mActivityThread.handleNewIntent((IBinder)var7_7, var1_1);
            var7_7.intent = var2_2;
            this.moveToState((LocalActivityRecord)var7_7, this.mCurState);
            if (this.mSingleMode == false) return var7_7.window;
            this.mResumed = var7_7;
            return var7_7.window;
        }
        var7_7.intent = var2_2;
        var7_7.curState = 1;
        var7_7.activityInfo = var6_6;
        this.moveToState((LocalActivityRecord)var7_7, this.mCurState);
        if (this.mSingleMode == false) return var7_7.window;
        this.mResumed = var7_7;
        return var7_7.window;
    }

    private static class LocalActivityRecord
    extends Binder {
        Activity activity;
        ActivityInfo activityInfo;
        int curState = 0;
        final String id;
        Bundle instanceState;
        Intent intent;
        Window window;

        LocalActivityRecord(String string2, Intent intent) {
            this.id = string2;
            this.intent = intent;
        }
    }

}

