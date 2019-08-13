/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Collection;

public abstract class IntRangeManager {
    private static final int INITIAL_CLIENTS_ARRAY_SIZE = 4;
    @UnsupportedAppUsage
    private ArrayList<IntRange> mRanges = new ArrayList();

    protected IntRangeManager() {
    }

    private void populateAllClientRanges() {
        int n = this.mRanges.size();
        for (int i = 0; i < n; ++i) {
            IntRange intRange = this.mRanges.get(i);
            int n2 = intRange.mClients.size();
            for (int j = 0; j < n2; ++j) {
                ClientRange clientRange = intRange.mClients.get(j);
                this.addRange(clientRange.mStartId, clientRange.mEndId, true);
            }
        }
    }

    private void populateAllRanges() {
        for (IntRange intRange : this.mRanges) {
            this.addRange(intRange.mStartId, intRange.mEndId, true);
        }
    }

    protected abstract void addRange(int var1, int var2, boolean var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean disableRange(int n, int n2, String object) {
        synchronized (this) {
            int n3;
            int n4;
            ArrayList<Object> arrayList;
            int n5;
            Object object2;
            IntRange intRange;
            int n6;
            int n7 = this.mRanges.size();
            int n8 = 0;
            block10 : do {
                n6 = n;
                if (n8 >= n7) {
                    return false;
                }
                intRange = this.mRanges.get(n8);
                n5 = intRange.mStartId;
                if (n6 < n5) {
                    return false;
                }
                if (n2 <= intRange.mEndId) {
                    object2 = intRange.mClients;
                    n3 = ((ArrayList)object2).size();
                    n4 = 1;
                    if (n3 == 1) {
                        object2 = ((ArrayList)object2).get(0);
                        if (((ClientRange)object2).mStartId == n6 && ((ClientRange)object2).mEndId == n2 && ((ClientRange)object2).mClient.equals(object)) {
                            this.mRanges.remove(n8);
                            boolean bl = this.updateRanges();
                            if (bl) {
                                return true;
                            }
                            this.mRanges.add(n8, intRange);
                            return false;
                        }
                        return false;
                    }
                    n6 = Integer.MIN_VALUE;
                    int n9 = 0;
                    int n10 = 0;
                    for (n5 = 0; n5 < n3; ++n5) {
                        arrayList = (ClientRange)((ArrayList)object2).get(n5);
                        if (((ClientRange)arrayList).mStartId == n && ((ClientRange)arrayList).mEndId == n2 && ((ClientRange)arrayList).mClient.equals(object)) {
                            if (n5 == n3 - 1) {
                                if (intRange.mEndId == n6) {
                                    ((ArrayList)object2).remove(n5);
                                    return n4 != 0;
                                }
                                ((ArrayList)object2).remove(n5);
                                intRange.mEndId = n6;
                                boolean bl = this.updateRanges();
                                if (bl) {
                                    return n4 != 0;
                                }
                                ((ArrayList)object2).add(n5, arrayList);
                                intRange.mEndId = ((ClientRange)arrayList).mEndId;
                                return false;
                            }
                            object = new IntRange(intRange, n5);
                            n = n9;
                            if (n5 == 0) {
                                n6 = ((ClientRange)object2.get((int)n4)).mStartId;
                                n = n10;
                                if (n6 != intRange.mStartId) {
                                    n = 1;
                                    ((IntRange)object).mStartId = n6;
                                }
                                n6 = ((ClientRange)object2.get((int)1)).mEndId;
                            }
                            arrayList = new ArrayList<Object>();
                            ++n5;
                            break block10;
                        }
                        int n11 = n6;
                        if (((ClientRange)arrayList).mEndId > n6) {
                            n11 = ((ClientRange)arrayList).mEndId;
                        }
                        n6 = n11;
                    }
                }
                ++n8;
            } while (true);
            while (n5 < n3) {
                ClientRange clientRange = (ClientRange)((ArrayList)object2).get(n5);
                if (clientRange.mStartId > n6 + 1) {
                    ((IntRange)object).mEndId = n6;
                    arrayList.add(object);
                    object = new IntRange(clientRange);
                    n = 1;
                } else {
                    if (((IntRange)object).mEndId < clientRange.mEndId) {
                        ((IntRange)object).mEndId = clientRange.mEndId;
                    }
                    ((IntRange)object).mClients.add(clientRange);
                }
                n4 = n6;
                if (clientRange.mEndId > n6) {
                    n4 = clientRange.mEndId;
                }
                ++n5;
                n6 = n4;
            }
            if (n6 < n2) {
                n = 1;
                ((IntRange)object).mEndId = n6;
            }
            arrayList.add(object);
            this.mRanges.remove(n8);
            this.mRanges.addAll(n8, arrayList);
            if (n != 0 && !this.updateRanges()) {
                this.mRanges.removeAll(arrayList);
                this.mRanges.add(n8, intRange);
                return false;
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean enableRange(int n, int n2, String object) {
        synchronized (this) {
            int n3;
            int n4;
            IntRange intRange;
            int n5;
            int n6;
            block49 : {
                Object object2;
                block48 : {
                    n3 = this.mRanges.size();
                    if (n3 == 0) {
                        if (this.tryAddRanges(n, n2, true)) {
                            ArrayList<IntRange> arrayList = this.mRanges;
                            IntRange intRange2 = new IntRange(n, n2, (String)object);
                            arrayList.add(intRange2);
                            return true;
                        }
                        return false;
                    }
                    for (n5 = 0; n5 < n3; ++n5) {
                        intRange = this.mRanges.get(n5);
                        if (n >= intRange.mStartId && n2 <= intRange.mEndId) {
                            ClientRange clientRange = new ClientRange(n, n2, (String)object);
                            intRange.insert(clientRange);
                            return true;
                        }
                        if (n - 1 == intRange.mEndId) {
                            Object object3;
                            int n7 = n2;
                            Object object4 = null;
                            int n8 = n7;
                            if (n5 + 1 < n3) {
                                object3 = this.mRanges.get(n5 + 1);
                                if (((IntRange)object3).mStartId - 1 <= n2) {
                                    n8 = n7;
                                    object4 = object3;
                                    if (n2 <= ((IntRange)object3).mEndId) {
                                        n8 = ((IntRange)object3).mStartId - 1;
                                        object4 = object3;
                                    }
                                } else {
                                    object4 = null;
                                    n8 = n7;
                                }
                            }
                            if (!this.tryAddRanges(n, n8, true)) {
                                return false;
                            }
                            intRange.mEndId = n2;
                            object3 = new ClientRange(n, n2, (String)object);
                            intRange.insert((ClientRange)object3);
                            if (object4 != null) {
                                if (intRange.mEndId < ((IntRange)object4).mEndId) {
                                    intRange.mEndId = ((IntRange)object4).mEndId;
                                }
                                intRange.mClients.addAll(((IntRange)object4).mClients);
                                this.mRanges.remove(object4);
                            }
                            return true;
                        }
                        if (n < intRange.mStartId) {
                            if (n2 + 1 < intRange.mStartId) {
                                if (this.tryAddRanges(n, n2, true)) {
                                    ArrayList<IntRange> arrayList = this.mRanges;
                                    IntRange intRange3 = new IntRange(n, n2, (String)object);
                                    arrayList.add(n5, intRange3);
                                    return true;
                                }
                                return false;
                            }
                            if (n2 <= intRange.mEndId) {
                                if (this.tryAddRanges(n, intRange.mStartId - 1, true)) {
                                    intRange.mStartId = n;
                                    ArrayList<ClientRange> arrayList = intRange.mClients;
                                    ClientRange clientRange = new ClientRange(n, n2, (String)object);
                                    arrayList.add(0, clientRange);
                                    return true;
                                }
                                return false;
                            }
                            break block48;
                        }
                        if (n + 1 > intRange.mEndId) continue;
                        if (n2 <= intRange.mEndId) {
                            ClientRange clientRange = new ClientRange(n, n2, (String)object);
                            intRange.insert(clientRange);
                            return true;
                        }
                        n6 = n5;
                        n4 = n5 + 1;
                        break block49;
                    }
                    if (this.tryAddRanges(n, n2, true)) {
                        ArrayList<IntRange> arrayList = this.mRanges;
                        IntRange intRange4 = new IntRange(n, n2, (String)object);
                        arrayList.add(intRange4);
                        return true;
                    }
                    return false;
                }
                for (int i = n5 + 1; i < n3; ++i) {
                    object2 = this.mRanges.get(i);
                    if (n2 + 1 < ((IntRange)object2).mStartId) {
                        if (!this.tryAddRanges(n, n2, true)) {
                            return false;
                        }
                        intRange.mStartId = n;
                        intRange.mEndId = n2;
                        object2 = intRange.mClients;
                        ClientRange clientRange = new ClientRange(n, n2, (String)object);
                        ((ArrayList)object2).add(0, (ClientRange)clientRange);
                        n = n2 = n5 + 1;
                        do {
                            if (n >= i) {
                                return true;
                            }
                            object = this.mRanges.get(n2);
                            intRange.mClients.addAll(((IntRange)object).mClients);
                            this.mRanges.remove(object);
                            ++n;
                        } while (true);
                    }
                    if (n2 > ((IntRange)object2).mEndId) continue;
                    if (!this.tryAddRanges(n, ((IntRange)object2).mStartId - 1, true)) {
                        return false;
                    }
                    intRange.mStartId = n;
                    intRange.mEndId = ((IntRange)object2).mEndId;
                    ArrayList<ClientRange> arrayList = intRange.mClients;
                    object2 = new ClientRange(n, n2, (String)object);
                    arrayList.add(0, (ClientRange)object2);
                    n = n2 = n5 + 1;
                    while (n <= i) {
                        object = this.mRanges.get(n2);
                        intRange.mClients.addAll(((IntRange)object).mClients);
                        this.mRanges.remove(object);
                        ++n;
                    }
                    return true;
                }
                if (!this.tryAddRanges(n, n2, true)) {
                    return false;
                }
                intRange.mStartId = n;
                intRange.mEndId = n2;
                object2 = intRange.mClients;
                ClientRange clientRange = new ClientRange(n, n2, (String)object);
                ((ArrayList)object2).add(0, (ClientRange)clientRange);
                n = n2 = n5 + 1;
                do {
                    if (n >= n3) {
                        return true;
                    }
                    object = this.mRanges.get(n2);
                    intRange.mClients.addAll(((IntRange)object).mClients);
                    this.mRanges.remove(object);
                    ++n;
                } while (true);
            }
            while (n4 < n3 && n2 + 1 >= this.mRanges.get((int)n4).mStartId) {
                n6 = n4++;
            }
            if (n6 == n5) {
                if (this.tryAddRanges(intRange.mEndId + 1, n2, true)) {
                    intRange.mEndId = n2;
                    ClientRange clientRange = new ClientRange(n, n2, (String)object);
                    intRange.insert(clientRange);
                    return true;
                }
                return false;
            }
            Object object5 = this.mRanges.get(n6);
            n4 = n2 <= ((IntRange)object5).mEndId ? ((IntRange)object5).mStartId - 1 : n2;
            if (!this.tryAddRanges(intRange.mEndId + 1, n4, true)) {
                return false;
            }
            n4 = n2 <= ((IntRange)object5).mEndId ? ((IntRange)object5).mEndId : n2;
            intRange.mEndId = n4;
            object5 = new ClientRange(n, n2, (String)object);
            intRange.insert((ClientRange)object5);
            n = n2 = n5 + 1;
            while (n <= n6) {
                object = this.mRanges.get(n2);
                intRange.mClients.addAll(((IntRange)object).mClients);
                this.mRanges.remove(object);
                ++n;
            }
            return true;
        }
    }

    protected abstract boolean finishUpdate();

    public boolean isEmpty() {
        return this.mRanges.isEmpty();
    }

    protected abstract void startUpdate();

    protected boolean tryAddRanges(int n, int n2, boolean bl) {
        this.startUpdate();
        this.populateAllRanges();
        this.addRange(n, n2, bl);
        return this.finishUpdate();
    }

    public boolean updateRanges() {
        this.startUpdate();
        this.populateAllRanges();
        return this.finishUpdate();
    }

    private class ClientRange {
        final String mClient;
        final int mEndId;
        final int mStartId;

        ClientRange(int n, int n2, String string) {
            this.mStartId = n;
            this.mEndId = n2;
            this.mClient = string;
        }

        public boolean equals(Object object) {
            boolean bl = false;
            if (object != null && object instanceof ClientRange) {
                object = (ClientRange)object;
                if (this.mStartId == ((ClientRange)object).mStartId && this.mEndId == ((ClientRange)object).mEndId && this.mClient.equals(((ClientRange)object).mClient)) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return (this.mStartId * 31 + this.mEndId) * 31 + this.mClient.hashCode();
        }
    }

    private class IntRange {
        final ArrayList<ClientRange> mClients;
        int mEndId;
        int mStartId;

        IntRange(int n, int n2, String string) {
            this.mStartId = n;
            this.mEndId = n2;
            this.mClients = new ArrayList(4);
            this.mClients.add(new ClientRange(n, n2, string));
        }

        IntRange(ClientRange clientRange) {
            this.mStartId = clientRange.mStartId;
            this.mEndId = clientRange.mEndId;
            this.mClients = new ArrayList(4);
            this.mClients.add(clientRange);
        }

        IntRange(IntRange intRange, int n) {
            this.mStartId = intRange.mStartId;
            this.mEndId = intRange.mEndId;
            this.mClients = new ArrayList(intRange.mClients.size());
            for (int i = 0; i < n; ++i) {
                this.mClients.add(intRange.mClients.get(i));
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void insert(ClientRange clientRange) {
            int n;
            int n2 = this.mClients.size();
            int n3 = -1;
            int n4 = 0;
            do {
                n = n3;
                if (n4 >= n2) break;
                ClientRange clientRange2 = this.mClients.get(n4);
                if (clientRange.mStartId <= clientRange2.mStartId) {
                    if (clientRange.equals(clientRange2)) return;
                    if (clientRange.mStartId == clientRange2.mStartId && clientRange.mEndId > clientRange2.mEndId) {
                        n = n3 = n4 + 1;
                        if (n3 >= n2) break;
                    } else {
                        this.mClients.add(n4, clientRange);
                        return;
                    }
                }
                ++n4;
            } while (true);
            if (n != -1 && n < n2) {
                this.mClients.add(n, clientRange);
                return;
            }
            this.mClients.add(clientRange);
        }
    }

}

