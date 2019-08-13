/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.WorkSource;
import android.util.Slog;
import java.util.ArrayList;
import java.util.List;

public final class StatsLogEventWrapper
implements Parcelable {
    public static final Parcelable.Creator<StatsLogEventWrapper> CREATOR = new Parcelable.Creator<StatsLogEventWrapper>(){

        @Override
        public StatsLogEventWrapper createFromParcel(Parcel parcel) {
            return new StatsLogEventWrapper(parcel);
        }

        public StatsLogEventWrapper[] newArray(int n) {
            return new StatsLogEventWrapper[n];
        }
    };
    static final boolean DEBUG = false;
    private static final int EVENT_TYPE_DOUBLE = 4;
    private static final int EVENT_TYPE_FLOAT = 3;
    private static final int EVENT_TYPE_INT = 1;
    private static final int EVENT_TYPE_LONG = 2;
    private static final int EVENT_TYPE_STORAGE = 6;
    private static final int EVENT_TYPE_STRING = 5;
    private static final int EVENT_TYPE_UNKNOWN = 0;
    static final String TAG = "StatsLogEventWrapper";
    long mElapsedTimeNs;
    int mTag;
    List<Integer> mTypes = new ArrayList<Integer>();
    List<Object> mValues = new ArrayList<Object>();
    long mWallClockTimeNs;
    WorkSource mWorkSource = null;

    public StatsLogEventWrapper(int n, long l, long l2) {
        this.mTag = n;
        this.mElapsedTimeNs = l;
        this.mWallClockTimeNs = l2;
    }

    private StatsLogEventWrapper(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        int n;
        int n2;
        this.mTypes = new ArrayList<Integer>();
        this.mValues = new ArrayList<Object>();
        this.mWorkSource = null;
        this.mTag = parcel.readInt();
        this.mElapsedTimeNs = parcel.readLong();
        this.mWallClockTimeNs = parcel.readLong();
        int n3 = parcel.readInt();
        if (n3 > 0) {
            this.mWorkSource = new WorkSource();
            for (n = 0; n < n3; ++n) {
                WorkSource.WorkChain workChain = this.mWorkSource.createWorkChain();
                int n4 = parcel.readInt();
                for (n2 = 0; n2 < n4; ++n2) {
                    workChain.addNode(parcel.readInt(), parcel.readString());
                }
            }
        }
        n2 = parcel.readInt();
        block10 : for (n = 0; n < n2; ++n) {
            n3 = parcel.readInt();
            this.mTypes.add(n3);
            switch (n3) {
                default: {
                    continue block10;
                }
                case 6: {
                    this.mValues.add(parcel.createByteArray());
                    continue block10;
                }
                case 5: {
                    this.mValues.add(parcel.readString());
                    continue block10;
                }
                case 4: {
                    this.mValues.add(parcel.readDouble());
                    continue block10;
                }
                case 3: {
                    this.mValues.add(Float.valueOf(parcel.readFloat()));
                    continue block10;
                }
                case 2: {
                    this.mValues.add(parcel.readLong());
                    continue block10;
                }
                case 1: {
                    this.mValues.add(parcel.readInt());
                }
            }
        }
    }

    public void setWorkSource(WorkSource workSource) {
        if (workSource.getWorkChains() != null && workSource.getWorkChains().size() != 0) {
            this.mWorkSource = workSource;
            return;
        }
        Slog.w(TAG, "Empty worksource!");
    }

    public void writeBoolean(boolean bl) {
        this.mTypes.add(1);
        this.mValues.add((int)bl);
    }

    public void writeFloat(float f) {
        this.mTypes.add(3);
        this.mValues.add(Float.valueOf(f));
    }

    public void writeInt(int n) {
        this.mTypes.add(1);
        this.mValues.add(n);
    }

    public void writeLong(long l) {
        this.mTypes.add(2);
        this.mValues.add(l);
    }

    public void writeStorage(byte[] arrby) {
        this.mTypes.add(6);
        this.mValues.add(arrby);
    }

    public void writeString(String string2) {
        this.mTypes.add(5);
        List<Object> list = this.mValues;
        if (string2 == null) {
            string2 = "";
        }
        list.add(string2);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTag);
        parcel.writeLong(this.mElapsedTimeNs);
        parcel.writeLong(this.mWallClockTimeNs);
        Object object = this.mWorkSource;
        if (object != null) {
            ArrayList<WorkSource.WorkChain> arrayList = ((WorkSource)object).getWorkChains();
            parcel.writeInt(arrayList.size());
            for (n = 0; n < arrayList.size(); ++n) {
                WorkSource.WorkChain workChain = arrayList.get(n);
                if (workChain.getSize() == 0) {
                    Slog.w(TAG, "Empty work chain.");
                    parcel.writeInt(0);
                    continue;
                }
                if (workChain.getUids().length == workChain.getTags().length && workChain.getUids().length == workChain.getSize()) {
                    parcel.writeInt(workChain.getSize());
                    for (int i = 0; i < workChain.getSize(); ++i) {
                        parcel.writeInt(workChain.getUids()[i]);
                        object = workChain.getTags()[i] == null ? "" : workChain.getTags()[i];
                        parcel.writeString((String)object);
                    }
                    continue;
                }
                Slog.w(TAG, "Malformated work chain.");
                parcel.writeInt(0);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mTypes.size());
        block10 : for (n = 0; n < this.mTypes.size(); ++n) {
            parcel.writeInt(this.mTypes.get(n));
            switch (this.mTypes.get(n)) {
                default: {
                    continue block10;
                }
                case 6: {
                    parcel.writeByteArray((byte[])this.mValues.get(n));
                    continue block10;
                }
                case 5: {
                    parcel.writeString((String)this.mValues.get(n));
                    continue block10;
                }
                case 4: {
                    parcel.writeDouble((Double)this.mValues.get(n));
                    continue block10;
                }
                case 3: {
                    parcel.writeFloat(((Float)this.mValues.get(n)).floatValue());
                    continue block10;
                }
                case 2: {
                    parcel.writeLong((Long)this.mValues.get(n));
                    continue block10;
                }
                case 1: {
                    parcel.writeInt((Integer)this.mValues.get(n));
                }
            }
        }
    }

}

