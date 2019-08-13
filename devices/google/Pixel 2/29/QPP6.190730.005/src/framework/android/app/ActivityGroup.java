/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import java.util.HashMap;

@Deprecated
public class ActivityGroup
extends Activity {
    static final String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";
    private static final String STATES_KEY = "android:states";
    @UnsupportedAppUsage
    protected LocalActivityManager mLocalActivityManager;

    public ActivityGroup() {
        this(true);
    }

    public ActivityGroup(boolean bl) {
        this.mLocalActivityManager = new LocalActivityManager(this, bl);
    }

    @Override
    void dispatchActivityResult(String string2, int n, int n2, Intent intent, String string3) {
        Activity activity;
        if (string2 != null && (activity = this.mLocalActivityManager.getActivity(string2)) != null) {
            activity.onActivityResult(n, n2, intent);
            return;
        }
        super.dispatchActivityResult(string2, n, n2, intent, string3);
    }

    public Activity getCurrentActivity() {
        return this.mLocalActivityManager.getCurrentActivity();
    }

    public final LocalActivityManager getLocalActivityManager() {
        return this.mLocalActivityManager;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = bundle != null ? bundle.getBundle(STATES_KEY) : null;
        this.mLocalActivityManager.dispatchCreate(bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLocalActivityManager.dispatchDestroy(this.isFinishing());
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mLocalActivityManager.dispatchPause(this.isFinishing());
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mLocalActivityManager.dispatchResume();
    }

    @Override
    public HashMap<String, Object> onRetainNonConfigurationChildInstances() {
        return this.mLocalActivityManager.dispatchRetainNonConfigurationInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Bundle bundle2 = this.mLocalActivityManager.saveInstanceState();
        if (bundle2 != null) {
            bundle.putBundle(STATES_KEY, bundle2);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mLocalActivityManager.dispatchStop();
    }
}

