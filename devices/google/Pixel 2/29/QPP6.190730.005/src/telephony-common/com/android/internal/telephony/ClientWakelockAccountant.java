/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ClientRequestStats
 *  android.telephony.Rlog
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.telephony.ClientRequestStats;
import android.telephony.Rlog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.RilWakelockInfo;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientWakelockAccountant {
    public static final String LOG_TAG = "ClientWakelockAccountant: ";
    @VisibleForTesting
    public ArrayList<RilWakelockInfo> mPendingRilWakelocks = new ArrayList();
    @VisibleForTesting
    public ClientRequestStats mRequestStats = new ClientRequestStats();

    @VisibleForTesting
    public ClientWakelockAccountant(String string) {
        this.mRequestStats.setCallingPackage(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void completeRequest(RilWakelockInfo rilWakelockInfo, long l) {
        rilWakelockInfo.setResponseTime(l);
        ClientRequestStats clientRequestStats = this.mRequestStats;
        synchronized (clientRequestStats) {
            this.mRequestStats.addCompletedWakelockTime(rilWakelockInfo.getWakelockTimeAttributedToClient());
            this.mRequestStats.incrementCompletedRequestsCount();
            this.mRequestStats.updateRequestHistograms(rilWakelockInfo.getRilRequestSent(), (int)rilWakelockInfo.getWakelockTimeAttributedToClient());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private RilWakelockInfo removePendingWakelock(int n, int n2) {
        Object object;
        StringBuilder stringBuilder = null;
        ArrayList<RilWakelockInfo> arrayList = this.mPendingRilWakelocks;
        // MONITORENTER : arrayList
        for (RilWakelockInfo rilWakelockInfo : this.mPendingRilWakelocks) {
            object = stringBuilder;
            if (rilWakelockInfo.getTokenNumber() == n2) {
                object = stringBuilder;
                if (rilWakelockInfo.getRilRequestSent() == n) {
                    object = rilWakelockInfo;
                }
            }
            stringBuilder = object;
        }
        if (stringBuilder != null) {
            this.mPendingRilWakelocks.remove(stringBuilder);
        }
        // MONITOREXIT : arrayList
        if (stringBuilder != null) return stringBuilder;
        object = new StringBuilder();
        ((StringBuilder)object).append("Looking for Request<");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(",");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append("> in ");
        ((StringBuilder)object).append(this.mPendingRilWakelocks);
        Rlog.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return stringBuilder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void changeConcurrentRequests(int n, long l) {
        ArrayList<RilWakelockInfo> arrayList = this.mPendingRilWakelocks;
        synchronized (arrayList) {
            Iterator<RilWakelockInfo> iterator = this.mPendingRilWakelocks.iterator();
            while (iterator.hasNext()) {
                iterator.next().updateConcurrentRequests(n, l);
            }
            return;
        }
    }

    @VisibleForTesting
    public int getPendingRequestCount() {
        return this.mPendingRilWakelocks.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void startAttributingWakelock(int n, int n2, int n3, long l) {
        RilWakelockInfo rilWakelockInfo = new RilWakelockInfo(n, n2, n3, l);
        ArrayList<RilWakelockInfo> arrayList = this.mPendingRilWakelocks;
        synchronized (arrayList) {
            this.mPendingRilWakelocks.add(rilWakelockInfo);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void stopAllPendingRequests(long l) {
        ArrayList<RilWakelockInfo> arrayList = this.mPendingRilWakelocks;
        synchronized (arrayList) {
            Iterator<RilWakelockInfo> iterator = this.mPendingRilWakelocks.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mPendingRilWakelocks.clear();
                    return;
                }
                this.completeRequest(iterator.next(), l);
            } while (true);
        }
    }

    @VisibleForTesting
    public void stopAttributingWakelock(int n, int n2, long l) {
        RilWakelockInfo rilWakelockInfo = this.removePendingWakelock(n, n2);
        if (rilWakelockInfo != null) {
            this.completeRequest(rilWakelockInfo, l);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClientWakelockAccountant{mRequestStats=");
        stringBuilder.append((Object)this.mRequestStats);
        stringBuilder.append(", mPendingRilWakelocks=");
        stringBuilder.append(this.mPendingRilWakelocks);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public long updatePendingRequestWakelockTime(long l) {
        synchronized (this) {
            long l2 = 0L;
            ClientRequestStats clientRequestStats = this.mPendingRilWakelocks;
            synchronized (clientRequestStats) {
                Throwable throwable2;
                Iterator<RilWakelockInfo> iterator = this.mPendingRilWakelocks.iterator();
                do {
                    boolean bl;
                    if (!(bl = iterator.hasNext())) {
                        // MONITOREXIT [10, 11, 14, 15] lbl8 : MonitorExitStatement: MONITOREXIT : var5_3
                        clientRequestStats = this.mRequestStats;
                        synchronized (clientRequestStats) {
                            this.mRequestStats.setPendingRequestsCount((long)this.getPendingRequestCount());
                            this.mRequestStats.setPendingRequestsWakelockTime(l2);
                            return l2;
                        }
                    }
                    try {
                        RilWakelockInfo rilWakelockInfo = iterator.next();
                        rilWakelockInfo.updateTime(l);
                        long l3 = rilWakelockInfo.getWakelockTimeAttributedToClient();
                        l2 += l3;
                        continue;
                    }
                    catch (Throwable throwable2) {}
                    break;
                } while (true);
                throw throwable2;
            }
        }
    }
}

