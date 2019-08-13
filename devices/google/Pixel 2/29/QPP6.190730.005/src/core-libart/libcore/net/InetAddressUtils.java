/*
 * Decompiled with CFR 0.145.
 */
package libcore.net;

import android.system.GaiException;
import android.system.OsConstants;
import android.system.StructAddrinfo;
import java.io.Serializable;
import java.net.InetAddress;
import libcore.io.Libcore;
import libcore.io.Os;

public class InetAddressUtils {
    private static final int NETID_UNSET = 0;

    private InetAddressUtils() {
    }

    public static boolean isNumericAddress(String string) {
        boolean bl = InetAddressUtils.parseNumericAddressNoThrow(string) != null;
        return bl;
    }

    public static InetAddress parseNumericAddress(String string) {
        Serializable serializable = InetAddressUtils.parseNumericAddressNoThrow(string);
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Not a numeric address: ");
        ((StringBuilder)serializable).append(string);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public static InetAddress parseNumericAddressNoThrow(String arrinetAddress) {
        StructAddrinfo structAddrinfo = new StructAddrinfo();
        structAddrinfo.ai_flags = OsConstants.AI_NUMERICHOST;
        Object var2_3 = null;
        try {
            arrinetAddress = Libcore.os.android_getaddrinfo((String)arrinetAddress, structAddrinfo, 0);
        }
        catch (GaiException gaiException) {
            arrinetAddress = var2_3;
        }
        if (arrinetAddress == null) {
            return null;
        }
        return arrinetAddress[0];
    }

    public static InetAddress parseNumericAddressNoThrowStripOptionalBrackets(String string) {
        String string2 = string;
        if (string.startsWith("[")) {
            string2 = string;
            if (string.endsWith("]")) {
                string2 = string;
                if (string.indexOf(58) != -1) {
                    string2 = string.substring(1, string.length() - 1);
                }
            }
        }
        return InetAddressUtils.parseNumericAddressNoThrow(string2);
    }
}

