/*
 * Decompiled with CFR 0.145.
 */
package android.net.shared;

import android.os.Parcel;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtils {
    private InetAddressUtils() {
    }

    public static void parcelInetAddress(Parcel parcel, InetAddress object, int n) {
        object = object != null ? object.getAddress() : null;
        parcel.writeByteArray((byte[])object);
    }

    public static InetAddress unparcelInetAddress(Parcel object) {
        if ((object = object.createByteArray()) == null) {
            return null;
        }
        try {
            object = InetAddress.getByAddress(object);
            return object;
        }
        catch (UnknownHostException unknownHostException) {
            return null;
        }
    }
}

