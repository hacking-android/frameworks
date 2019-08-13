/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.NetworkConfig
 *  android.net.NetworkRequest
 */
package com.android.internal.telephony.dataconnection;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkConfig;
import android.net.NetworkRequest;
import com.android.internal.telephony.dataconnection.ApnContext;
import java.util.HashMap;

public class DcRequest
implements Comparable<DcRequest> {
    private static final String LOG_TAG = "DcRequest";
    private static final HashMap<Integer, Integer> sApnPriorityMap = new HashMap();
    public final int apnType;
    public final NetworkRequest networkRequest;
    public final int priority;

    public DcRequest(NetworkRequest networkRequest, Context context) {
        this.initApnPriorities(context);
        this.networkRequest = networkRequest;
        this.apnType = ApnContext.getApnTypeFromNetworkRequest(this.networkRequest);
        this.priority = this.priorityForApnType(this.apnType);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initApnPriorities(Context object2) {
        HashMap<Integer, Integer> hashMap = sApnPriorityMap;
        synchronized (hashMap) {
            if (sApnPriorityMap.isEmpty()) {
                for (Object object2 : object2.getResources().getStringArray(17236090)) {
                    NetworkConfig networkConfig = new NetworkConfig((String)object2);
                    int n = ApnContext.getApnTypeFromNetworkType(networkConfig.type);
                    sApnPriorityMap.put(n, networkConfig.priority);
                }
            }
            return;
        }
    }

    private int priorityForApnType(int n) {
        Integer n2 = sApnPriorityMap.get(n);
        n = n2 != null ? n2 : 0;
        return n;
    }

    @Override
    public int compareTo(DcRequest dcRequest) {
        return dcRequest.priority - this.priority;
    }

    public boolean equals(Object object) {
        if (object instanceof DcRequest) {
            return this.networkRequest.equals((Object)((DcRequest)object).networkRequest);
        }
        return false;
    }

    public int hashCode() {
        return this.networkRequest.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.networkRequest.toString());
        stringBuilder.append(", priority=");
        stringBuilder.append(this.priority);
        stringBuilder.append(", apnType=");
        stringBuilder.append(this.apnType);
        return stringBuilder.toString();
    }
}

