/*
 * Decompiled with CFR 0.145.
 */
package android.app.backup;

import android.annotation.SystemApi;
import android.app.backup.RestoreSet;

public abstract class RestoreObserver {
    public void onUpdate(int n, String string2) {
    }

    public void restoreFinished(int n) {
    }

    @SystemApi
    public void restoreSetsAvailable(RestoreSet[] arrrestoreSet) {
    }

    public void restoreStarting(int n) {
    }
}

