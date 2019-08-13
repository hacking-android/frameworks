/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.mtp.-$
 *  android.mtp.-$$Lambda
 *  android.mtp.-$$Lambda$ELHKvd8JMVRD8rbALqYPKbDX2mM
 *  android.mtp.-$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw
 */
package android.mtp;

import android.annotation.UnsupportedAppUsage;
import android.mtp.-$;
import android.mtp._$$Lambda$ELHKvd8JMVRD8rbALqYPKbDX2mM;
import android.mtp._$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class MtpPropertyList {
    private int mCode;
    private List<Integer> mDataTypes;
    private List<Long> mLongValues;
    private List<Integer> mObjectHandles;
    private List<Integer> mPropertyCodes;
    private List<String> mStringValues;

    public MtpPropertyList(int n) {
        this.mCode = n;
        this.mObjectHandles = new ArrayList<Integer>();
        this.mPropertyCodes = new ArrayList<Integer>();
        this.mDataTypes = new ArrayList<Integer>();
        this.mLongValues = new ArrayList<Long>();
        this.mStringValues = new ArrayList<String>();
    }

    @UnsupportedAppUsage
    public void append(int n, int n2, int n3, long l) {
        this.mObjectHandles.add(n);
        this.mPropertyCodes.add(n2);
        this.mDataTypes.add(n3);
        this.mLongValues.add(l);
        this.mStringValues.add(null);
    }

    @UnsupportedAppUsage
    public void append(int n, int n2, String string2) {
        this.mObjectHandles.add(n);
        this.mPropertyCodes.add(n2);
        this.mDataTypes.add(65535);
        this.mStringValues.add(string2);
        this.mLongValues.add(0L);
    }

    public int getCode() {
        return this.mCode;
    }

    public int getCount() {
        return this.mObjectHandles.size();
    }

    public int[] getDataTypes() {
        return this.mDataTypes.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
    }

    public long[] getLongValues() {
        return this.mLongValues.stream().mapToLong(_$$Lambda$ELHKvd8JMVRD8rbALqYPKbDX2mM.INSTANCE).toArray();
    }

    public int[] getObjectHandles() {
        return this.mObjectHandles.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
    }

    public int[] getPropertyCodes() {
        return this.mPropertyCodes.stream().mapToInt(_$$Lambda$UV1wDVoVlbcxpr8zevj_aMFtUGw.INSTANCE).toArray();
    }

    public String[] getStringValues() {
        return this.mStringValues.toArray(new String[0]);
    }
}

