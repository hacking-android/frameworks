/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WorkSource
implements Parcelable {
    public static final Parcelable.Creator<WorkSource> CREATOR;
    static final boolean DEBUG = false;
    static final String TAG = "WorkSource";
    static WorkSource sGoneWork;
    static WorkSource sNewbWork;
    static final WorkSource sTmpWorkSource;
    private ArrayList<WorkChain> mChains;
    @UnsupportedAppUsage
    String[] mNames;
    @UnsupportedAppUsage
    int mNum;
    @UnsupportedAppUsage
    int[] mUids;

    static {
        sTmpWorkSource = new WorkSource(0);
        CREATOR = new Parcelable.Creator<WorkSource>(){

            @Override
            public WorkSource createFromParcel(Parcel parcel) {
                return new WorkSource(parcel);
            }

            public WorkSource[] newArray(int n) {
                return new WorkSource[n];
            }
        };
    }

    public WorkSource() {
        this.mNum = 0;
        this.mChains = null;
    }

    public WorkSource(int n) {
        this.mNum = 1;
        this.mUids = new int[]{n, 0};
        this.mNames = null;
        this.mChains = null;
    }

    public WorkSource(int n, String string2) {
        if (string2 != null) {
            this.mNum = 1;
            this.mUids = new int[]{n, 0};
            this.mNames = new String[]{string2, null};
            this.mChains = null;
            return;
        }
        throw new NullPointerException("Name can't be null");
    }

    @UnsupportedAppUsage
    WorkSource(Parcel parcel) {
        this.mNum = parcel.readInt();
        this.mUids = parcel.createIntArray();
        this.mNames = parcel.createStringArray();
        int n = parcel.readInt();
        if (n > 0) {
            this.mChains = new ArrayList(n);
            parcel.readParcelableList(this.mChains, WorkChain.class.getClassLoader());
        } else {
            this.mChains = null;
        }
    }

    public WorkSource(WorkSource parcelable2) {
        if (parcelable2 == null) {
            this.mNum = 0;
            this.mChains = null;
            return;
        }
        this.mNum = ((WorkSource)parcelable2).mNum;
        Object object = ((WorkSource)parcelable2).mUids;
        if (object != null) {
            this.mUids = (int[])object.clone();
            object = ((WorkSource)parcelable2).mNames;
            object = object != null ? (String[])object.clone() : null;
            this.mNames = object;
        } else {
            this.mUids = null;
            this.mNames = null;
        }
        object = ((WorkSource)parcelable2).mChains;
        if (object != null) {
            this.mChains = new ArrayList(((ArrayList)object).size());
            for (WorkChain workChain : ((WorkSource)parcelable2).mChains) {
                this.mChains.add(new WorkChain(workChain));
            }
        } else {
            this.mChains = null;
        }
    }

    private static WorkSource addWork(WorkSource workSource, int n) {
        if (workSource == null) {
            return new WorkSource(n);
        }
        workSource.insert(workSource.mNum, n);
        return workSource;
    }

    private static WorkSource addWork(WorkSource workSource, int n, String string2) {
        if (workSource == null) {
            return new WorkSource(n, string2);
        }
        workSource.insert(workSource.mNum, n, string2);
        return workSource;
    }

    private int compare(WorkSource workSource, int n, int n2) {
        int n3 = this.mUids[n] - workSource.mUids[n2];
        if (n3 != 0) {
            return n3;
        }
        return this.mNames[n].compareTo(workSource.mNames[n2]);
    }

    public static ArrayList<WorkChain>[] diffChains(WorkSource workSource, WorkSource workSource2) {
        ArrayList<WorkChain> arrayList;
        WorkChain workChain;
        block14 : {
            WorkChain workChain2;
            ArrayList<WorkChain> arrayList2;
            int n;
            Object object;
            block11 : {
                arrayList2 = null;
                workChain2 = null;
                workChain = null;
                object = null;
                if (workSource.mChains == null) break block11;
                n = 0;
                do {
                    block13 : {
                        block12 : {
                            workChain = object;
                            if (n >= workSource.mChains.size()) break;
                            workChain = workSource.mChains.get(n);
                            ArrayList<WorkChain> arrayList3 = workSource2.mChains;
                            if (arrayList3 == null) break block12;
                            arrayList = object;
                            if (arrayList3.contains(workChain)) break block13;
                        }
                        arrayList = object;
                        if (object == null) {
                            arrayList = new ArrayList(workSource.mChains.size());
                        }
                        arrayList.add(workChain);
                    }
                    ++n;
                    object = arrayList;
                } while (true);
            }
            arrayList = arrayList2;
            if (workSource2.mChains == null) break block14;
            n = 0;
            object = workChain2;
            do {
                block16 : {
                    block15 : {
                        arrayList = object;
                        if (n >= workSource2.mChains.size()) break;
                        workChain2 = workSource2.mChains.get(n);
                        arrayList2 = workSource.mChains;
                        if (arrayList2 == null) break block15;
                        arrayList = object;
                        if (arrayList2.contains(workChain2)) break block16;
                    }
                    arrayList = object;
                    if (object == null) {
                        arrayList = new ArrayList<WorkChain>(workSource2.mChains.size());
                    }
                    arrayList.add(workChain2);
                }
                ++n;
                object = arrayList;
            } while (true);
        }
        if (arrayList == null && workChain == null) {
            return null;
        }
        return new ArrayList[]{arrayList, workChain};
    }

    private void insert(int n, int n2) {
        int[] arrn = this.mUids;
        if (arrn == null) {
            this.mUids = new int[4];
            this.mUids[0] = n2;
            this.mNum = 1;
        } else {
            int n3 = this.mNum;
            if (n3 >= arrn.length) {
                int[] arrn2 = new int[n3 * 3 / 2];
                if (n > 0) {
                    System.arraycopy(arrn, 0, arrn2, 0, n);
                }
                if (n < (n3 = this.mNum)) {
                    System.arraycopy(this.mUids, n, arrn2, n + 1, n3 - n);
                }
                this.mUids = arrn2;
                this.mUids[n] = n2;
                ++this.mNum;
            } else {
                if (n < n3) {
                    System.arraycopy(arrn, n, arrn, n + 1, n3 - n);
                }
                this.mUids[n] = n2;
                ++this.mNum;
            }
        }
    }

    private void insert(int n, int n2, String string2) {
        int[] arrn = this.mUids;
        if (arrn == null) {
            this.mUids = new int[4];
            this.mUids[0] = n2;
            this.mNames = new String[4];
            this.mNames[0] = string2;
            this.mNum = 1;
        } else {
            int n3 = this.mNum;
            if (n3 >= arrn.length) {
                int[] arrn2 = new int[n3 * 3 / 2];
                String[] arrstring = new String[n3 * 3 / 2];
                if (n > 0) {
                    System.arraycopy(arrn, 0, arrn2, 0, n);
                    System.arraycopy(this.mNames, 0, arrstring, 0, n);
                }
                if (n < (n3 = this.mNum)) {
                    System.arraycopy(this.mUids, n, arrn2, n + 1, n3 - n);
                    System.arraycopy(this.mNames, n, arrstring, n + 1, this.mNum - n);
                }
                this.mUids = arrn2;
                this.mNames = arrstring;
                this.mUids[n] = n2;
                this.mNames[n] = string2;
                ++this.mNum;
            } else {
                if (n < n3) {
                    System.arraycopy(arrn, n, arrn, n + 1, n3 - n);
                    String[] arrstring = this.mNames;
                    System.arraycopy(arrstring, n, arrstring, n + 1, this.mNum - n);
                }
                this.mUids[n] = n2;
                this.mNames[n] = string2;
                ++this.mNum;
            }
        }
    }

    public static boolean isChainedBatteryAttributionEnabled(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)object, "chained_battery_attribution_enabled", 0) == 1) {
            bl = true;
        }
        return bl;
    }

    private boolean removeUids(WorkSource arrn) {
        int n = this.mNum;
        int[] arrn2 = this.mUids;
        int n2 = arrn.mNum;
        arrn = arrn.mUids;
        boolean bl = false;
        int n3 = 0;
        int n4 = 0;
        while (n3 < n && n4 < n2) {
            if (arrn[n4] == arrn2[n3]) {
                bl = true;
                if (n3 < --n) {
                    System.arraycopy(arrn2, n3 + 1, arrn2, n3, n - n3);
                }
                ++n4;
                continue;
            }
            if (arrn[n4] > arrn2[n3]) {
                ++n3;
                continue;
            }
            ++n4;
        }
        this.mNum = n;
        return bl;
    }

    private boolean removeUidsAndNames(WorkSource arrstring) {
        int n = this.mNum;
        int[] arrn = this.mUids;
        String[] arrstring2 = this.mNames;
        int n2 = arrstring.mNum;
        int[] arrn2 = arrstring.mUids;
        arrstring = arrstring.mNames;
        boolean bl = false;
        int n3 = 0;
        int n4 = 0;
        while (n3 < n && n4 < n2) {
            if (arrn2[n4] == arrn[n3] && arrstring[n4].equals(arrstring2[n3])) {
                bl = true;
                if (n3 < --n) {
                    System.arraycopy(arrn, n3 + 1, arrn, n3, n - n3);
                    System.arraycopy(arrstring2, n3 + 1, arrstring2, n3, n - n3);
                }
                ++n4;
                continue;
            }
            if (arrn2[n4] <= arrn[n3] && (arrn2[n4] != arrn[n3] || arrstring[n4].compareTo(arrstring2[n3]) <= 0)) {
                ++n4;
                continue;
            }
            ++n3;
        }
        this.mNum = n;
        return bl;
    }

    private boolean updateLocked(WorkSource workSource, boolean bl, boolean bl2) {
        if (this.mNames == null && workSource.mNames == null) {
            return this.updateUidsLocked(workSource, bl, bl2);
        }
        if (this.mNum > 0 && this.mNames == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Other ");
            stringBuilder.append(workSource);
            stringBuilder.append(" has names, but target ");
            stringBuilder.append(this);
            stringBuilder.append(" does not");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (workSource.mNum > 0 && workSource.mNames == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Target ");
            stringBuilder.append(this);
            stringBuilder.append(" has names, but other ");
            stringBuilder.append(workSource);
            stringBuilder.append(" does not");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return this.updateUidsAndNamesLocked(workSource, bl, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean updateUidsAndNamesLocked(WorkSource workSource, boolean bl, boolean bl2) {
        int n = workSource.mNum;
        int[] arrn = workSource.mUids;
        String[] arrstring = workSource.mNames;
        boolean bl3 = false;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            boolean bl4;
            block13 : {
                int n5;
                block12 : {
                    block10 : {
                        block11 : {
                            if (n2 >= this.mNum && n3 >= n) {
                                return bl3;
                            }
                            n5 = -1;
                            if (n2 >= this.mNum) break block10;
                            if (n3 >= n) break block11;
                            n5 = n4 = this.compare(workSource, n2, n3);
                            if (n4 > 0) break block10;
                        }
                        if (bl) break block12;
                        n4 = n3;
                        if (n3 < n) {
                            n4 = n3;
                            if (n5 == 0) {
                                n4 = n3 + 1;
                            }
                        }
                        ++n2;
                        bl4 = bl3;
                        break block13;
                    }
                    bl4 = true;
                    this.insert(n2, arrn[n3], arrstring[n3]);
                    if (bl2) {
                        sNewbWork = WorkSource.addWork(sNewbWork, arrn[n3], arrstring[n3]);
                    }
                    ++n2;
                    n4 = n3 + 1;
                    break block13;
                }
                int n6 = n5;
                n4 = n2;
                do {
                    n4 = n5 = n4;
                    if (n6 >= 0) break;
                    sGoneWork = WorkSource.addWork(sGoneWork, this.mUids[n5], this.mNames[n5]);
                    if (++n5 >= this.mNum) {
                        n4 = n5;
                        break;
                    }
                    n4 = n3 < n ? this.compare(workSource, n5, n3) : -1;
                    n6 = n4;
                    n4 = n5;
                } while (true);
                n5 = n4;
                if (n2 < n4) {
                    int[] arrn2 = this.mUids;
                    System.arraycopy(arrn2, n4, arrn2, n2, this.mNum - n4);
                    String[] arrstring2 = this.mNames;
                    System.arraycopy(arrstring2, n4, arrstring2, n2, this.mNum - n4);
                    this.mNum -= n4 - n2;
                    n5 = n2;
                }
                bl4 = bl3;
                n2 = n5;
                n4 = n3;
                if (n5 < this.mNum) {
                    bl4 = bl3;
                    n2 = n5;
                    n4 = n3;
                    if (n6 == 0) {
                        n2 = n5 + 1;
                        n4 = n3 + 1;
                        bl4 = bl3;
                    }
                }
            }
            bl3 = bl4;
            n3 = n4;
        } while (true);
    }

    private boolean updateUidsLocked(WorkSource arrn, boolean bl, boolean bl2) {
        int n = this.mNum;
        int[] arrn2 = this.mUids;
        int n2 = arrn.mNum;
        int[] arrn3 = arrn.mUids;
        boolean bl3 = false;
        int n3 = 0;
        int n4 = 0;
        arrn = arrn2;
        do {
            if (n3 >= n && n4 >= n2) {
                this.mNum = n;
                this.mUids = arrn;
                return bl3;
            }
            if (n3 < n && (n4 >= n2 || arrn3[n4] >= arrn[n3])) {
                int n5;
                int n6;
                if (!bl) {
                    n5 = n4;
                    if (n4 < n2) {
                        n5 = n4;
                        if (arrn3[n4] == arrn[n3]) {
                            n5 = n4 + 1;
                        }
                    }
                    ++n3;
                    n4 = n5;
                    continue;
                }
                n5 = n3;
                while ((n6 = n5) < n && (n4 >= n2 || arrn3[n4] > arrn[n6])) {
                    sGoneWork = WorkSource.addWork(sGoneWork, arrn[n6]);
                    n5 = n6 + 1;
                }
                int n7 = n;
                n5 = n6;
                if (n3 < n6) {
                    System.arraycopy(arrn, n6, arrn, n3, n - n6);
                    n7 = n - (n6 - n3);
                    n5 = n3;
                }
                n3 = n5;
                n6 = n4;
                if (n5 < n7) {
                    n3 = n5;
                    n6 = n4;
                    if (n4 < n2) {
                        n3 = n5;
                        n6 = n4;
                        if (arrn3[n4] == arrn[n5]) {
                            n3 = n5 + 1;
                            n6 = n4 + 1;
                        }
                    }
                }
                n = n7;
                n4 = n6;
                continue;
            }
            bl3 = true;
            if (arrn == null) {
                arrn = new int[4];
                arrn[0] = arrn3[n4];
            } else if (n >= arrn.length) {
                arrn2 = new int[arrn.length * 3 / 2];
                if (n3 > 0) {
                    System.arraycopy(arrn, 0, arrn2, 0, n3);
                }
                if (n3 < n) {
                    System.arraycopy(arrn, n3, arrn2, n3 + 1, n - n3);
                }
                arrn = arrn2;
                arrn[n3] = arrn3[n4];
            } else {
                if (n3 < n) {
                    System.arraycopy(arrn, n3, arrn, n3 + 1, n - n3);
                }
                arrn[n3] = arrn3[n4];
            }
            if (bl2) {
                sNewbWork = WorkSource.addWork(sNewbWork, arrn3[n4]);
            }
            ++n;
            ++n3;
            ++n4;
        } while (true);
    }

    public boolean add(int n) {
        int n2 = this.mNum;
        if (n2 <= 0) {
            this.mNames = null;
            this.insert(0, n);
            return true;
        }
        if (this.mNames == null) {
            if ((n2 = Arrays.binarySearch(this.mUids, 0, n2, n)) >= 0) {
                return false;
            }
            this.insert(-n2 - 1, n);
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Adding without name to named ");
        stringBuilder.append(this);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean add(int n, String charSequence) {
        if (this.mNum <= 0) {
            this.insert(0, n, (String)charSequence);
            return true;
        }
        if (this.mNames != null) {
            int[] arrn;
            int n2;
            for (n2 = 0; n2 < this.mNum && (arrn = this.mUids)[n2] <= n; ++n2) {
                if (arrn[n2] != n) continue;
                int n3 = this.mNames[n2].compareTo((String)charSequence);
                if (n3 > 0) break;
                if (n3 != 0) continue;
                return false;
            }
            this.insert(n2, n, (String)charSequence);
            return true;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Adding name to unnamed ");
        ((StringBuilder)charSequence).append(this);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean add(WorkSource object) {
        WorkSource workSource = sTmpWorkSource;
        synchronized (workSource) {
            boolean bl = false;
            boolean bl2 = this.updateLocked((WorkSource)object, false, false);
            if (((WorkSource)object).mChains != null) {
                if (this.mChains == null) {
                    Object object2;
                    this.mChains = object2 = new ArrayList(((WorkSource)object).mChains.size());
                }
                for (WorkChain workChain : ((WorkSource)object).mChains) {
                    if (this.mChains.contains(workChain)) continue;
                    object = this.mChains;
                    WorkChain workChain2 = new WorkChain(workChain);
                    ((ArrayList)object).add(workChain2);
                }
            }
            if (bl2) return true;
            if (!false) return bl;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public WorkSource addReturningNewbs(WorkSource workSource) {
        WorkSource workSource2 = sTmpWorkSource;
        synchronized (workSource2) {
            sNewbWork = null;
            this.updateLocked(workSource, false, true);
            return sNewbWork;
        }
    }

    public void clear() {
        this.mNum = 0;
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void clearNames() {
        if (this.mNames != null) {
            this.mNames = null;
            int n = 1;
            int n2 = this.mNum;
            for (int i = 1; i < this.mNum; ++i) {
                int[] arrn = this.mUids;
                if (arrn[i] == arrn[i - 1]) {
                    --n2;
                    continue;
                }
                arrn[n] = arrn[i];
                ++n;
            }
            this.mNum = n2;
        }
    }

    @SystemApi
    public WorkChain createWorkChain() {
        if (this.mChains == null) {
            this.mChains = new ArrayList(4);
        }
        WorkChain workChain = new WorkChain();
        this.mChains.add(workChain);
        return workChain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean diff(WorkSource arrstring) {
        int n = this.mNum;
        if (n != arrstring.mNum) {
            return true;
        }
        int[] arrn = this.mUids;
        int[] arrn2 = arrstring.mUids;
        String[] arrstring2 = this.mNames;
        arrstring = arrstring.mNames;
        for (int i = 0; i < n; ++i) {
            if (arrn[i] != arrn2[i]) {
                return true;
            }
            if (arrstring2 == null || arrstring == null || arrstring2[i].equals(arrstring[i])) continue;
            return true;
        }
        return false;
    }

    public boolean equals(Object arrayList) {
        boolean bl = arrayList instanceof WorkSource;
        boolean bl2 = false;
        if (bl) {
            WorkSource workSource = (WorkSource)((Object)arrayList);
            if (this.diff(workSource)) {
                return false;
            }
            arrayList = this.mChains;
            if (arrayList != null && !arrayList.isEmpty()) {
                return this.mChains.equals(workSource.mChains);
            }
            arrayList = workSource.mChains;
            if (arrayList == null || arrayList.isEmpty()) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int get(int n) {
        return this.mUids[n];
    }

    public int getAttributionUid() {
        if (this.isEmpty()) {
            return -1;
        }
        int n = this.mNum > 0 ? this.mUids[0] : this.mChains.get(0).getAttributionUid();
        return n;
    }

    public String getName(int n) {
        Object object = this.mNames;
        object = object != null ? object[n] : null;
        return object;
    }

    public ArrayList<WorkChain> getWorkChains() {
        return this.mChains;
    }

    public int hashCode() {
        int n;
        int n2 = 0;
        for (n = 0; n < this.mNum; ++n) {
            n2 = (n2 << 4 | n2 >>> 28) ^ this.mUids[n];
        }
        n = n2;
        if (this.mNames != null) {
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= this.mNum) break;
                n2 = (n2 << 4 | n2 >>> 28) ^ this.mNames[n3].hashCode();
                ++n3;
            } while (true);
        }
        ArrayList<WorkChain> arrayList = this.mChains;
        n2 = n;
        if (arrayList != null) {
            n2 = (n << 4 | n >>> 28) ^ arrayList.hashCode();
        }
        return n2;
    }

    public boolean isEmpty() {
        ArrayList<WorkChain> arrayList;
        boolean bl = this.mNum == 0 && ((arrayList = this.mChains) == null || arrayList.isEmpty());
        return bl;
    }

    public boolean remove(WorkSource object) {
        block5 : {
            block8 : {
                block9 : {
                    boolean bl;
                    block11 : {
                        block10 : {
                            boolean bl2;
                            block7 : {
                                block6 : {
                                    bl = this.isEmpty();
                                    bl2 = false;
                                    if (bl || ((WorkSource)object).isEmpty()) break block5;
                                    if (this.mNames != null || ((WorkSource)object).mNames != null) break block6;
                                    bl = this.removeUids((WorkSource)object);
                                    break block7;
                                }
                                if (this.mNames == null) break block8;
                                if (((WorkSource)object).mNames == null) break block9;
                                bl = this.removeUidsAndNames((WorkSource)object);
                            }
                            boolean bl3 = false;
                            object = ((WorkSource)object).mChains;
                            boolean bl4 = bl3;
                            if (object != null) {
                                ArrayList<WorkChain> arrayList = this.mChains;
                                bl4 = bl3;
                                if (arrayList != null) {
                                    bl4 = arrayList.removeAll((Collection<?>)object);
                                }
                            }
                            if (bl) break block10;
                            bl = bl2;
                            if (!bl4) break block11;
                        }
                        bl = true;
                    }
                    return bl;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Target ");
                stringBuilder.append(this);
                stringBuilder.append(" has names, but other ");
                stringBuilder.append(object);
                stringBuilder.append(" does not");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Other ");
            stringBuilder.append(object);
            stringBuilder.append(" has names, but target ");
            stringBuilder.append(this);
            stringBuilder.append(" does not");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return false;
    }

    public void set(int n) {
        this.mNum = 1;
        if (this.mUids == null) {
            this.mUids = new int[2];
        }
        this.mUids[0] = n;
        this.mNames = null;
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public void set(int n, String arrayList) {
        if (arrayList != null) {
            this.mNum = 1;
            if (this.mUids == null) {
                this.mUids = new int[2];
                this.mNames = new String[2];
            }
            this.mUids[0] = n;
            this.mNames[0] = arrayList;
            arrayList = this.mChains;
            if (arrayList != null) {
                arrayList.clear();
            }
            return;
        }
        throw new NullPointerException("Name can't be null");
    }

    public void set(WorkSource object) {
        Object object2;
        if (object == null) {
            this.mNum = 0;
            object = this.mChains;
            if (object != null) {
                object.clear();
            }
            return;
        }
        this.mNum = ((WorkSource)object).mNum;
        int[] object32 = ((WorkSource)object).mUids;
        if (object32 != null) {
            int n;
            int n2;
            object2 = this.mUids;
            if (object2 != null && (n2 = ((int[])object2).length) >= (n = this.mNum)) {
                System.arraycopy(object32, 0, object2, 0, n);
            } else {
                this.mUids = (int[])((WorkSource)object).mUids.clone();
            }
            object2 = ((WorkSource)object).mNames;
            if (object2 != null) {
                String[] arrstring = this.mNames;
                if (arrstring != null && (n = arrstring.length) >= (n2 = this.mNum)) {
                    System.arraycopy(object2, 0, arrstring, 0, n2);
                } else {
                    this.mNames = (String[])((WorkSource)object).mNames.clone();
                }
            } else {
                this.mNames = null;
            }
        } else {
            this.mUids = null;
            this.mNames = null;
        }
        ArrayList<WorkChain> arrayList = ((WorkSource)object).mChains;
        if (arrayList != null) {
            object2 = this.mChains;
            if (object2 != null) {
                ((ArrayList)object2).clear();
            } else {
                this.mChains = new ArrayList(arrayList.size());
            }
            for (WorkChain workChain : ((WorkSource)object).mChains) {
                this.mChains.add(new WorkChain(workChain));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public WorkSource[] setReturningDiffs(WorkSource workSource) {
        WorkSource workSource2 = sTmpWorkSource;
        synchronized (workSource2) {
            sNewbWork = null;
            sGoneWork = null;
            this.updateLocked(workSource, true, true);
            if (sNewbWork == null && sGoneWork == null) {
                return null;
            }
            workSource = sNewbWork;
            WorkSource workSource3 = sGoneWork;
            return new WorkSource[]{workSource, workSource3};
        }
    }

    public int size() {
        return this.mNum;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WorkSource{");
        for (n = 0; n < this.mNum; ++n) {
            if (n != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.mUids[n]);
            if (this.mNames == null) continue;
            stringBuilder.append(" ");
            stringBuilder.append(this.mNames[n]);
        }
        if (this.mChains != null) {
            stringBuilder.append(" chains=");
            for (n = 0; n < this.mChains.size(); ++n) {
                if (n != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(this.mChains.get(n));
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void transferWorkChains(WorkSource workSource) {
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList != null) {
            arrayList.clear();
        }
        if ((arrayList = workSource.mChains) != null && !arrayList.isEmpty()) {
            if (this.mChains == null) {
                this.mChains = new ArrayList(4);
            }
            this.mChains.addAll(workSource.mChains);
            workSource.mChains.clear();
            return;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mNum);
        parcel.writeIntArray(this.mUids);
        parcel.writeStringArray(this.mNames);
        ArrayList<WorkChain> arrayList = this.mChains;
        if (arrayList == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(arrayList.size());
            parcel.writeParcelableList(this.mChains, n);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        String[] arrstring;
        long l2;
        int n;
        l = protoOutputStream.start(l);
        for (n = 0; n < this.mNum; ++n) {
            l2 = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, this.mUids[n]);
            arrstring = this.mNames;
            if (arrstring != null) {
                protoOutputStream.write(1138166333442L, arrstring[n]);
            }
            protoOutputStream.end(l2);
        }
        if (this.mChains != null) {
            for (n = 0; n < this.mChains.size(); ++n) {
                int[] arrn = this.mChains.get(n);
                l2 = protoOutputStream.start(2246267895810L);
                arrstring = arrn.getTags();
                arrn = arrn.getUids();
                for (int i = 0; i < arrstring.length; ++i) {
                    long l3 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, arrn[i]);
                    protoOutputStream.write(1138166333442L, arrstring[i]);
                    protoOutputStream.end(l3);
                }
                protoOutputStream.end(l2);
            }
        }
        protoOutputStream.end(l);
    }

    @SystemApi
    public static final class WorkChain
    implements Parcelable {
        public static final Parcelable.Creator<WorkChain> CREATOR = new Parcelable.Creator<WorkChain>(){

            @Override
            public WorkChain createFromParcel(Parcel parcel) {
                return new WorkChain(parcel);
            }

            public WorkChain[] newArray(int n) {
                return new WorkChain[n];
            }
        };
        private int mSize;
        private String[] mTags;
        private int[] mUids;

        public WorkChain() {
            this.mSize = 0;
            this.mUids = new int[4];
            this.mTags = new String[4];
        }

        private WorkChain(Parcel parcel) {
            this.mSize = parcel.readInt();
            this.mUids = parcel.createIntArray();
            this.mTags = parcel.createStringArray();
        }

        @VisibleForTesting
        public WorkChain(WorkChain workChain) {
            this.mSize = workChain.mSize;
            this.mUids = (int[])workChain.mUids.clone();
            this.mTags = (String[])workChain.mTags.clone();
        }

        private void resizeArrays() {
            int n = this.mSize;
            int n2 = n * 2;
            int[] arrn = new int[n2];
            String[] arrstring = new String[n2];
            System.arraycopy(this.mUids, 0, arrn, 0, n);
            System.arraycopy(this.mTags, 0, arrstring, 0, this.mSize);
            this.mUids = arrn;
            this.mTags = arrstring;
        }

        public WorkChain addNode(int n, String string2) {
            if (this.mSize == this.mUids.length) {
                this.resizeArrays();
            }
            int[] arrn = this.mUids;
            int n2 = this.mSize;
            arrn[n2] = n;
            this.mTags[n2] = string2;
            this.mSize = n2 + 1;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof WorkChain;
            boolean bl2 = false;
            if (bl) {
                object = (WorkChain)object;
                if (this.mSize == ((WorkChain)object).mSize && Arrays.equals(this.mUids, ((WorkChain)object).mUids) && Arrays.equals(this.mTags, ((WorkChain)object).mTags)) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public String getAttributionTag() {
            Object object = this.mTags;
            object = ((String[])object).length > 0 ? object[0] : null;
            return object;
        }

        public int getAttributionUid() {
            int n = this.mSize > 0 ? this.mUids[0] : -1;
            return n;
        }

        @VisibleForTesting
        public int getSize() {
            return this.mSize;
        }

        @VisibleForTesting
        public String[] getTags() {
            int n = this.mSize;
            String[] arrstring = new String[n];
            System.arraycopy(this.mTags, 0, arrstring, 0, n);
            return arrstring;
        }

        @VisibleForTesting
        public int[] getUids() {
            int n = this.mSize;
            int[] arrn = new int[n];
            System.arraycopy(this.mUids, 0, arrn, 0, n);
            return arrn;
        }

        public int hashCode() {
            return (this.mSize + Arrays.hashCode(this.mUids) * 31) * 31 + Arrays.hashCode(this.mTags);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("WorkChain{");
            for (int i = 0; i < this.mSize; ++i) {
                if (i != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append("(");
                stringBuilder.append(this.mUids[i]);
                if (this.mTags[i] != null) {
                    stringBuilder.append(", ");
                    stringBuilder.append(this.mTags[i]);
                }
                stringBuilder.append(")");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mSize);
            parcel.writeIntArray(this.mUids);
            parcel.writeStringArray(this.mTags);
        }

    }

}

