/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.InetAddressUtils
 */
package android.net;

import java.net.InetAddress;
import libcore.net.InetAddressUtils;

public class InetAddresses {
    private InetAddresses() {
    }

    public static boolean isNumericAddress(String string2) {
        return InetAddressUtils.isNumericAddress((String)string2);
    }

    public static InetAddress parseNumericAddress(String string2) {
        return InetAddressUtils.parseNumericAddress((String)string2);
    }
}

