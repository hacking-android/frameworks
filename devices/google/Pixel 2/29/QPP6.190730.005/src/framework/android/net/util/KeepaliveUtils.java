/*
 * Decompiled with CFR 0.145.
 */
package android.net.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.NetworkCapabilities;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;

public final class KeepaliveUtils {
    public static final String TAG = "KeepaliveUtils";

    public static int[] getSupportedKeepalives(Context object) {
        int[] arrn = null;
        try {
            object = ((Context)object).getResources().getStringArray(17236041);
        }
        catch (Resources.NotFoundException notFoundException) {
            object = arrn;
        }
        if (object != null) {
            arrn = new int[8];
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                String[] arrstring = object[i];
                if (!TextUtils.isEmpty((CharSequence)arrstring)) {
                    if ((arrstring = arrstring.split(",")).length == 2) {
                        int n2;
                        int n3;
                        try {
                            n3 = Integer.parseInt(arrstring[0]);
                            n2 = Integer.parseInt(arrstring[1]);
                        }
                        catch (NumberFormatException numberFormatException) {
                            throw new KeepaliveDeviceConfigurationException("Invalid number format");
                        }
                        if (NetworkCapabilities.isValidTransport(n3)) {
                            if (n2 >= 0) {
                                arrn[n3] = n2;
                                continue;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid supported count ");
                            ((StringBuilder)object).append(n2);
                            ((StringBuilder)object).append(" for ");
                            ((StringBuilder)object).append(NetworkCapabilities.transportNameOf(n3));
                            throw new KeepaliveDeviceConfigurationException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid transport ");
                        ((StringBuilder)object).append(n3);
                        throw new KeepaliveDeviceConfigurationException(((StringBuilder)object).toString());
                    }
                    throw new KeepaliveDeviceConfigurationException("Invalid parameter length");
                }
                throw new KeepaliveDeviceConfigurationException("Empty string");
            }
            return arrn;
        }
        throw new KeepaliveDeviceConfigurationException("invalid resource");
    }

    public static int getSupportedKeepalivesForNetworkCapabilities(int[] arrn, NetworkCapabilities arrn2) {
        arrn2 = arrn2.getTransportTypes();
        int n = arrn2.length;
        if (n == 0) {
            return 0;
        }
        int n2 = arrn[arrn2[0]];
        for (int n3 : arrn2) {
            n = n2;
            if (n2 > arrn[n3]) {
                n = arrn[n3];
            }
            n2 = n;
        }
        return n2;
    }

    public static class KeepaliveDeviceConfigurationException
    extends AndroidRuntimeException {
        public KeepaliveDeviceConfigurationException(String string2) {
            super(string2);
        }
    }

}

