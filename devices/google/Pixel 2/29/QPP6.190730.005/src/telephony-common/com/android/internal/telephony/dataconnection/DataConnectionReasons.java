/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.dataconnection;

import java.util.HashSet;
import java.util.Iterator;

public class DataConnectionReasons {
    private DataAllowedReasonType mDataAllowedReason = DataAllowedReasonType.NONE;
    private HashSet<DataDisallowedReasonType> mDataDisallowedReasonSet = new HashSet();

    void add(DataAllowedReasonType dataAllowedReasonType) {
        this.mDataDisallowedReasonSet.clear();
        if (dataAllowedReasonType.ordinal() > this.mDataAllowedReason.ordinal()) {
            this.mDataAllowedReason = dataAllowedReasonType;
        }
    }

    void add(DataDisallowedReasonType dataDisallowedReasonType) {
        this.mDataAllowedReason = DataAllowedReasonType.NONE;
        this.mDataDisallowedReasonSet.add(dataDisallowedReasonType);
    }

    boolean allowed() {
        boolean bl = this.mDataDisallowedReasonSet.size() == 0;
        return bl;
    }

    boolean contains(DataAllowedReasonType dataAllowedReasonType) {
        boolean bl = dataAllowedReasonType == this.mDataAllowedReason;
        return bl;
    }

    boolean contains(DataDisallowedReasonType dataDisallowedReasonType) {
        return this.mDataDisallowedReasonSet.contains((Object)dataDisallowedReasonType);
    }

    boolean containsHardDisallowedReasons() {
        Iterator<DataDisallowedReasonType> iterator = this.mDataDisallowedReasonSet.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isHardReason()) continue;
            return true;
        }
        return false;
    }

    public boolean containsOnly(DataDisallowedReasonType dataDisallowedReasonType) {
        int n = this.mDataDisallowedReasonSet.size();
        boolean bl = true;
        if (n != 1 || !this.contains(dataDisallowedReasonType)) {
            bl = false;
        }
        return bl;
    }

    void copyFrom(DataConnectionReasons dataConnectionReasons) {
        this.mDataDisallowedReasonSet = dataConnectionReasons.mDataDisallowedReasonSet;
        this.mDataAllowedReason = dataConnectionReasons.mDataAllowedReason;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.mDataDisallowedReasonSet.size() > 0) {
            stringBuilder.append("Data disallowed, reasons:");
            for (DataDisallowedReasonType dataDisallowedReasonType : this.mDataDisallowedReasonSet) {
                stringBuilder.append(" ");
                stringBuilder.append((Object)dataDisallowedReasonType);
            }
        } else {
            stringBuilder.append("Data allowed, reason:");
            stringBuilder.append(" ");
            stringBuilder.append((Object)this.mDataAllowedReason);
        }
        return stringBuilder.toString();
    }

    static enum DataAllowedReasonType {
        NONE,
        NORMAL,
        UNMETERED_APN,
        RESTRICTED_REQUEST,
        EMERGENCY_APN;
        
    }

    public static enum DataDisallowedReasonType {
        DATA_DISABLED(false),
        ROAMING_DISABLED(false),
        NOT_ATTACHED(true),
        RECORD_NOT_LOADED(true),
        INVALID_PHONE_STATE(true),
        CONCURRENT_VOICE_DATA_NOT_ALLOWED(true),
        PS_RESTRICTED(true),
        UNDESIRED_POWER_STATE(true),
        INTERNAL_DATA_DISABLED(true),
        DEFAULT_DATA_UNSELECTED(true),
        RADIO_DISABLED_BY_CARRIER(true),
        APN_NOT_CONNECTABLE(true),
        ON_IWLAN(true),
        IN_ECBM(true);
        
        private boolean mIsHardReason;

        private DataDisallowedReasonType(boolean bl) {
            this.mIsHardReason = bl;
        }

        boolean isHardReason() {
            return this.mIsHardReason;
        }
    }

}

