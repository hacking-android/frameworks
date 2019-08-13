/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.android.internal.telephony.ITelephony;

public abstract class CellLocation {
    public static CellLocation getEmpty() {
        int n = TelephonyManager.getDefault().getCurrentPhoneType();
        if (n != 1) {
            if (n != 2) {
                return null;
            }
            return new CdmaCellLocation();
        }
        return new GsmCellLocation();
    }

    @UnsupportedAppUsage
    public static CellLocation newFromBundle(Bundle bundle) {
        int n = TelephonyManager.getDefault().getCurrentPhoneType();
        if (n != 1) {
            if (n != 2) {
                return null;
            }
            return new CdmaCellLocation(bundle);
        }
        return new GsmCellLocation(bundle);
    }

    public static void requestLocationUpdate() {
        block3 : {
            ITelephony iTelephony = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
            if (iTelephony == null) break block3;
            try {
                iTelephony.updateServiceLocation();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @UnsupportedAppUsage
    public abstract void fillInNotifierBundle(Bundle var1);

    @UnsupportedAppUsage
    public abstract boolean isEmpty();

    public abstract void setStateInvalid();
}

