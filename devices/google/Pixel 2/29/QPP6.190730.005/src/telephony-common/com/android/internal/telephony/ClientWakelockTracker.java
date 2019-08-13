/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.telephony.ClientRequestStats
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.os.SystemClock;
import android.telephony.ClientRequestStats;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ClientWakelockAccountant;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ClientWakelockTracker {
    public static final String LOG_TAG = "ClientWakelockTracker";
    @VisibleForTesting
    public ArrayList<ClientWakelockAccountant> mActiveClients = new ArrayList();
    @VisibleForTesting
    public HashMap<String, ClientWakelockAccountant> mClients = new HashMap();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ClientWakelockAccountant getClientWakelockAccountant(String object) {
        HashMap<String, ClientWakelockAccountant> hashMap = this.mClients;
        synchronized (hashMap) {
            if (this.mClients.containsKey(object)) {
                return this.mClients.get(object);
            }
            ClientWakelockAccountant clientWakelockAccountant = new ClientWakelockAccountant((String)object);
            this.mClients.put((String)object, clientWakelockAccountant);
            return clientWakelockAccountant;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateConcurrentRequests(int n, long l) {
        if (n == 0) return;
        ArrayList<ClientWakelockAccountant> arrayList = this.mActiveClients;
        synchronized (arrayList) {
            Iterator<ClientWakelockAccountant> iterator = this.mActiveClients.iterator();
            while (iterator.hasNext()) {
                iterator.next().changeConcurrentRequests(n, l);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void dumpClientRequestTracker(PrintWriter printWriter) {
        printWriter.println("-------mClients---------------");
        HashMap<String, ClientWakelockAccountant> hashMap = this.mClients;
        synchronized (hashMap) {
            Iterator<String> iterator = this.mClients.keySet().iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Client : ");
                stringBuilder.append(string);
                printWriter.println(stringBuilder.toString());
                printWriter.println(this.mClients.get(string).toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<ClientRequestStats> getClientRequestStats() {
        long l = SystemClock.uptimeMillis();
        HashMap<String, ClientWakelockAccountant> hashMap = this.mClients;
        synchronized (hashMap) {
            ArrayList<ClientRequestStats> arrayList = new ArrayList<ClientRequestStats>(this.mClients.size());
            Iterator<String> iterator = this.mClients.keySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                object = this.mClients.get(object);
                ((ClientWakelockAccountant)object).updatePendingRequestWakelockTime(l);
                ClientRequestStats clientRequestStats = new ClientRequestStats(((ClientWakelockAccountant)object).mRequestStats);
                arrayList.add(clientRequestStats);
            }
            return arrayList;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isClientActive(String object) {
        ClientWakelockAccountant clientWakelockAccountant = this.getClientWakelockAccountant((String)object);
        object = this.mActiveClients;
        synchronized (object) {
            return this.mActiveClients.contains(clientWakelockAccountant);
            {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void startTracking(String object, int n, int n2, int n3) {
        ClientWakelockAccountant clientWakelockAccountant = this.getClientWakelockAccountant((String)object);
        long l = SystemClock.uptimeMillis();
        clientWakelockAccountant.startAttributingWakelock(n, n2, n3, l);
        this.updateConcurrentRequests(n3, l);
        object = this.mActiveClients;
        synchronized (object) {
            if (!this.mActiveClients.contains(clientWakelockAccountant)) {
                this.mActiveClients.add(clientWakelockAccountant);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void stopTracking(String object, int n, int n2, int n3) {
        ClientWakelockAccountant clientWakelockAccountant = this.getClientWakelockAccountant((String)object);
        long l = SystemClock.uptimeMillis();
        clientWakelockAccountant.stopAttributingWakelock(n, n2, l);
        if (clientWakelockAccountant.getPendingRequestCount() == 0) {
            object = this.mActiveClients;
            synchronized (object) {
                this.mActiveClients.remove(clientWakelockAccountant);
            }
        }
        this.updateConcurrentRequests(n3, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void stopTrackingAll() {
        long l = SystemClock.uptimeMillis();
        ArrayList<ClientWakelockAccountant> arrayList = this.mActiveClients;
        synchronized (arrayList) {
            Iterator<ClientWakelockAccountant> iterator = this.mActiveClients.iterator();
            do {
                if (!iterator.hasNext()) {
                    this.mActiveClients.clear();
                    return;
                }
                iterator.next().stopAllPendingRequests(l);
            } while (true);
        }
    }
}

