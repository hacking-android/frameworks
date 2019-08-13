/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.infra;

import android.content.ComponentName;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.WhitelistHelper;
import java.io.PrintWriter;
import java.util.List;

public class GlobalWhitelistState {
    protected final Object mGlobalWhitelistStateLock = new Object();
    @GuardedBy(value={"mGlobalWhitelistStateLock"})
    protected SparseArray<WhitelistHelper> mWhitelisterHelpers;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(String object, PrintWriter printWriter) {
        printWriter.print((String)object);
        printWriter.print("State: ");
        Object object2 = this.mGlobalWhitelistStateLock;
        synchronized (object2) {
            if (this.mWhitelisterHelpers == null) {
                printWriter.println("empty");
                return;
            }
            printWriter.print(this.mWhitelisterHelpers.size());
            printWriter.println(" services");
            CharSequence charSequence = new StringBuilder();
            charSequence.append((String)object);
            charSequence.append("  ");
            charSequence = charSequence.toString();
            int n = 0;
            while (n < this.mWhitelisterHelpers.size()) {
                int n2 = this.mWhitelisterHelpers.keyAt(n);
                object = this.mWhitelisterHelpers.valueAt(n);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Whitelist for userId ");
                stringBuilder.append(n2);
                ((WhitelistHelper)object).dump((String)charSequence, stringBuilder.toString(), printWriter);
                ++n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArraySet<ComponentName> getWhitelistedComponents(int n, String object) {
        Object object2 = this.mGlobalWhitelistStateLock;
        synchronized (object2) {
            Object object3 = this.mWhitelisterHelpers;
            Object var5_5 = null;
            if (object3 == null) {
                return null;
            }
            object3 = this.mWhitelisterHelpers.get(n);
            if (object3 != null) return ((WhitelistHelper)object3).getWhitelistedComponents((String)object);
            return var5_5;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isWhitelisted(int n, ComponentName componentName) {
        Object object = this.mGlobalWhitelistStateLock;
        synchronized (object) {
            Object object2 = this.mWhitelisterHelpers;
            boolean bl = false;
            if (object2 == null) {
                return false;
            }
            object2 = this.mWhitelisterHelpers.get(n);
            if (object2 != null) return ((WhitelistHelper)object2).isWhitelisted(componentName);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isWhitelisted(int n, String string2) {
        Object object = this.mGlobalWhitelistStateLock;
        synchronized (object) {
            Object object2 = this.mWhitelisterHelpers;
            boolean bl = false;
            if (object2 == null) {
                return false;
            }
            object2 = this.mWhitelisterHelpers.get(n);
            if (object2 != null) return ((WhitelistHelper)object2).isWhitelisted(string2);
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resetWhitelist(int n) {
        Object object = this.mGlobalWhitelistStateLock;
        synchronized (object) {
            if (this.mWhitelisterHelpers == null) {
                return;
            }
            this.mWhitelisterHelpers.remove(n);
            if (this.mWhitelisterHelpers.size() == 0) {
                this.mWhitelisterHelpers = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWhitelist(int n, List<String> list, List<ComponentName> list2) {
        Object object = this.mGlobalWhitelistStateLock;
        synchronized (object) {
            Object object2;
            if (this.mWhitelisterHelpers == null) {
                object2 = new SparseArray(1);
                this.mWhitelisterHelpers = object2;
            }
            WhitelistHelper whitelistHelper = this.mWhitelisterHelpers.get(n);
            object2 = whitelistHelper;
            if (whitelistHelper == null) {
                object2 = new WhitelistHelper();
                this.mWhitelisterHelpers.put(n, (WhitelistHelper)object2);
            }
            ((WhitelistHelper)object2).setWhitelist(list, list2);
            return;
        }
    }
}

