/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.infra;

import android.content.ComponentName;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

public final class WhitelistHelper {
    private static final String TAG = "WhitelistHelper";
    private ArrayMap<String, ArraySet<ComponentName>> mWhitelistedPackages;

    public void dump(String object, String string2, PrintWriter printWriter) {
        ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
        if (arrayMap != null && arrayMap.size() != 0) {
            arrayMap = new StringBuilder();
            ((StringBuilder)((Object)arrayMap)).append((String)object);
            ((StringBuilder)((Object)arrayMap)).append("  ");
            arrayMap = ((StringBuilder)((Object)arrayMap)).toString();
            int n = this.mWhitelistedPackages.size();
            printWriter.print((String)object);
            printWriter.print(string2);
            printWriter.print(": ");
            printWriter.print(n);
            printWriter.println(" packages");
            for (n = 0; n < this.mWhitelistedPackages.size(); ++n) {
                string2 = this.mWhitelistedPackages.keyAt(n);
                object = this.mWhitelistedPackages.valueAt(n);
                printWriter.print((String)((Object)arrayMap));
                printWriter.print(n);
                printWriter.print(".");
                printWriter.print(string2);
                printWriter.print(": ");
                if (object == null) {
                    printWriter.println("(whole package)");
                    continue;
                }
                printWriter.print("[");
                printWriter.print(((ArraySet)object).valueAt(0));
                for (int i = 1; i < ((ArraySet)object).size(); ++i) {
                    printWriter.print(", ");
                    printWriter.print(((ArraySet)object).valueAt(i));
                }
                printWriter.println("]");
            }
            return;
        }
        printWriter.print((String)object);
        printWriter.print(string2);
        printWriter.println(": (no whitelisted packages)");
    }

    public ArraySet<ComponentName> getWhitelistedComponents(String object) {
        Preconditions.checkNotNull(object);
        ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
        object = arrayMap == null ? null : arrayMap.get(object);
        return object;
    }

    public boolean isWhitelisted(ComponentName componentName) {
        Preconditions.checkNotNull(componentName);
        String string2 = componentName.getPackageName();
        ArraySet<ComponentName> arraySet = this.getWhitelistedComponents(string2);
        if (arraySet != null) {
            return arraySet.contains(componentName);
        }
        return this.isWhitelisted(string2);
    }

    public boolean isWhitelisted(String string2) {
        boolean bl;
        block1 : {
            Preconditions.checkNotNull(string2);
            ArrayMap<String, ArraySet<ComponentName>> arrayMap = this.mWhitelistedPackages;
            bl = false;
            if (arrayMap == null) {
                return false;
            }
            if (!arrayMap.containsKey(string2) || this.mWhitelistedPackages.get(string2) != null) break block1;
            bl = true;
        }
        return bl;
    }

    public void setWhitelist(ArraySet<String> arraySet, ArraySet<ComponentName> arraySet2) {
        int n;
        this.mWhitelistedPackages = null;
        if (arraySet == null && arraySet2 == null) {
            return;
        }
        if (arraySet != null && arraySet.isEmpty() || arraySet2 != null && arraySet2.isEmpty()) {
            throw new IllegalArgumentException("Packages or Components cannot be empty.");
        }
        this.mWhitelistedPackages = new ArrayMap();
        if (arraySet != null) {
            for (n = 0; n < arraySet.size(); ++n) {
                this.mWhitelistedPackages.put(arraySet.valueAt(n), null);
            }
        }
        if (arraySet2 != null) {
            for (n = 0; n < arraySet2.size(); ++n) {
                ComponentName componentName = arraySet2.valueAt(n);
                if (componentName == null) {
                    Log.w(TAG, "setWhitelist(): component is null");
                    continue;
                }
                String string2 = componentName.getPackageName();
                ArraySet<ComponentName> arraySet3 = this.mWhitelistedPackages.get(string2);
                arraySet = arraySet3;
                if (arraySet3 == null) {
                    arraySet = new ArraySet();
                    this.mWhitelistedPackages.put(string2, arraySet);
                }
                arraySet.add((String)((Object)componentName));
            }
        }
    }

    public void setWhitelist(List<String> collection, List<ComponentName> collection2) {
        Object var3_3 = null;
        collection = collection == null ? null : new ArraySet<String>(collection);
        collection2 = collection2 == null ? var3_3 : new ArraySet<ComponentName>(collection2);
        this.setWhitelist((ArraySet<String>)collection, (ArraySet<ComponentName>)collection2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WhitelistHelper[");
        stringBuilder.append(this.mWhitelistedPackages);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

