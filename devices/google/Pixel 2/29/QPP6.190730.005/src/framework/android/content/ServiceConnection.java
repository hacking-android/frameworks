/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ComponentName;
import android.os.IBinder;

public interface ServiceConnection {
    default public void onBindingDied(ComponentName componentName) {
    }

    default public void onNullBinding(ComponentName componentName) {
    }

    public void onServiceConnected(ComponentName var1, IBinder var2);

    public void onServiceDisconnected(ComponentName var1);
}

