/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import java.util.ArrayList;

public class TaskStackBuilder {
    private static final String TAG = "TaskStackBuilder";
    private final ArrayList<Intent> mIntents = new ArrayList();
    private final Context mSourceContext;

    private TaskStackBuilder(Context context) {
        this.mSourceContext = context;
    }

    public static TaskStackBuilder create(Context context) {
        return new TaskStackBuilder(context);
    }

    public TaskStackBuilder addNextIntent(Intent intent) {
        this.mIntents.add(intent);
        return this;
    }

    public TaskStackBuilder addNextIntentWithParentStack(Intent intent) {
        ComponentName componentName;
        ComponentName componentName2 = componentName = intent.getComponent();
        if (componentName == null) {
            componentName2 = intent.resolveActivity(this.mSourceContext.getPackageManager());
        }
        if (componentName2 != null) {
            this.addParentStack(componentName2);
        }
        this.addNextIntent(intent);
        return this;
    }

    public TaskStackBuilder addParentStack(Activity object) {
        Intent intent = ((Activity)object).getParentActivityIntent();
        if (intent != null) {
            ComponentName componentName = intent.getComponent();
            object = componentName;
            if (componentName == null) {
                object = intent.resolveActivity(this.mSourceContext.getPackageManager());
            }
            this.addParentStack((ComponentName)object);
            this.addNextIntent(intent);
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TaskStackBuilder addParentStack(ComponentName object) {
        int n = this.mIntents.size();
        PackageManager packageManager = this.mSourceContext.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo((ComponentName)object, 0);
            object = activityInfo.parentActivityName;
            do {
                if (object == null) {
                    return this;
                }
                ComponentName componentName = new ComponentName(activityInfo.packageName, (String)object);
                activityInfo = packageManager.getActivityInfo(componentName, 0);
                String string2 = activityInfo.parentActivityName;
                if (string2 == null && n == 0) {
                    object = Intent.makeMainActivity(componentName);
                } else {
                    object = new Intent();
                    object = ((Intent)object).setComponent(componentName);
                }
                this.mIntents.add(n, (Intent)object);
                object = string2;
            } while (true);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(nameNotFoundException);
        }
    }

    public TaskStackBuilder addParentStack(Class<?> class_) {
        return this.addParentStack(new ComponentName(this.mSourceContext, class_));
    }

    public Intent editIntentAt(int n) {
        return this.mIntents.get(n);
    }

    public int getIntentCount() {
        return this.mIntents.size();
    }

    public Intent[] getIntents() {
        Intent[] arrintent = new Intent[this.mIntents.size()];
        if (arrintent.length == 0) {
            return arrintent;
        }
        arrintent[0] = new Intent(this.mIntents.get(0)).addFlags(268484608);
        for (int i = 1; i < arrintent.length; ++i) {
            arrintent[i] = new Intent(this.mIntents.get(i));
        }
        return arrintent;
    }

    public PendingIntent getPendingIntent(int n, int n2) {
        return this.getPendingIntent(n, n2, null);
    }

    public PendingIntent getPendingIntent(int n, int n2, Bundle bundle) {
        if (!this.mIntents.isEmpty()) {
            return PendingIntent.getActivities(this.mSourceContext, n, this.getIntents(), n2, bundle);
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
    }

    public PendingIntent getPendingIntent(int n, int n2, Bundle bundle, UserHandle userHandle) {
        if (!this.mIntents.isEmpty()) {
            return PendingIntent.getActivitiesAsUser(this.mSourceContext, n, this.getIntents(), n2, bundle, userHandle);
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
    }

    public int startActivities(Bundle bundle, UserHandle userHandle) {
        if (!this.mIntents.isEmpty()) {
            return this.mSourceContext.startActivitiesAsUser(this.getIntents(), bundle, userHandle);
        }
        throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    }

    public void startActivities() {
        this.startActivities(null);
    }

    public void startActivities(Bundle bundle) {
        this.startActivities(bundle, this.mSourceContext.getUser());
    }
}

