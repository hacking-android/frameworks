/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.gsm;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.telephony.CellLocation;

public class GsmCellLocation
extends CellLocation {
    private int mCid;
    private int mLac;
    private int mPsc;

    public GsmCellLocation() {
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
    }

    public GsmCellLocation(Bundle bundle) {
        this.mLac = bundle.getInt("lac", -1);
        this.mCid = bundle.getInt("cid", -1);
        this.mPsc = bundle.getInt("psc", -1);
    }

    private static boolean equalsHandlesNulls(Object object, Object object2) {
        boolean bl = object == null ? object2 == null : object.equals(object2);
        return bl;
    }

    public boolean equals(Object object) {
        GsmCellLocation gsmCellLocation;
        boolean bl;
        block3 : {
            bl = false;
            try {
                gsmCellLocation = (GsmCellLocation)object;
                if (object != null) break block3;
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        if (GsmCellLocation.equalsHandlesNulls(this.mLac, gsmCellLocation.mLac) && GsmCellLocation.equalsHandlesNulls(this.mCid, gsmCellLocation.mCid) && GsmCellLocation.equalsHandlesNulls(this.mPsc, gsmCellLocation.mPsc)) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putInt("lac", this.mLac);
        bundle.putInt("cid", this.mCid);
        bundle.putInt("psc", this.mPsc);
    }

    public int getCid() {
        return this.mCid;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getPsc() {
        return this.mPsc;
    }

    public int hashCode() {
        return this.mLac ^ this.mCid;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.mLac == -1 && this.mCid == -1 && this.mPsc == -1;
        return bl;
    }

    public void setLacAndCid(int n, int n2) {
        this.mLac = n;
        this.mCid = n2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setPsc(int n) {
        this.mPsc = n;
    }

    @Override
    public void setStateInvalid() {
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mLac);
        stringBuilder.append(",");
        stringBuilder.append(this.mCid);
        stringBuilder.append(",");
        stringBuilder.append(this.mPsc);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

