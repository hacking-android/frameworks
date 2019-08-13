/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.net.wifi.ScanResult;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BluetoothDeviceFilterUtils {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "BluetoothDeviceFilterUtils";

    private BluetoothDeviceFilterUtils() {
    }

    private static void debugLogMatchResult(boolean bl, BluetoothDevice object, Object object2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal((BluetoothDevice)object));
        object = bl ? " ~ " : " !~ ";
        stringBuilder.append((String)object);
        stringBuilder.append(object2);
        Log.i(LOG_TAG, stringBuilder.toString());
    }

    private static void debugLogMatchResult(boolean bl, ScanResult object, Object object2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BluetoothDeviceFilterUtils.getDeviceDisplayNameInternal((ScanResult)object));
        object = bl ? " ~ " : " !~ ";
        stringBuilder.append((String)object);
        stringBuilder.append(object2);
        Log.i(LOG_TAG, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static String getDeviceDisplayNameInternal(BluetoothDevice bluetoothDevice) {
        return TextUtils.firstNotEmpty(bluetoothDevice.getAliasName(), bluetoothDevice.getAddress());
    }

    @UnsupportedAppUsage
    public static String getDeviceDisplayNameInternal(ScanResult scanResult) {
        return TextUtils.firstNotEmpty(scanResult.SSID, scanResult.BSSID);
    }

    @UnsupportedAppUsage
    public static String getDeviceMacAddress(Parcelable parcelable) {
        if (parcelable instanceof BluetoothDevice) {
            return ((BluetoothDevice)parcelable).getAddress();
        }
        if (parcelable instanceof ScanResult) {
            return ((ScanResult)parcelable).BSSID;
        }
        if (parcelable instanceof android.bluetooth.le.ScanResult) {
            return BluetoothDeviceFilterUtils.getDeviceMacAddress(((android.bluetooth.le.ScanResult)parcelable).getDevice());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown device type: ");
        stringBuilder.append(parcelable);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static boolean matches(ScanFilter scanFilter, BluetoothDevice bluetoothDevice) {
        boolean bl = BluetoothDeviceFilterUtils.matchesAddress(scanFilter.getDeviceAddress(), bluetoothDevice) && BluetoothDeviceFilterUtils.matchesServiceUuid(scanFilter.getServiceUuid(), scanFilter.getServiceUuidMask(), bluetoothDevice);
        return bl;
    }

    static boolean matchesAddress(String string2, BluetoothDevice bluetoothDevice) {
        boolean bl = string2 == null || bluetoothDevice != null && string2.equals(bluetoothDevice.getAddress());
        return bl;
    }

    static boolean matchesName(Pattern pattern, BluetoothDevice object) {
        boolean bl = pattern == null ? true : (object == null ? false : (object = ((BluetoothDevice)object).getName()) != null && pattern.matcher((CharSequence)object).find());
        return bl;
    }

    static boolean matchesName(Pattern pattern, ScanResult object) {
        boolean bl = pattern == null ? true : (object == null ? false : (object = ((ScanResult)object).SSID) != null && pattern.matcher((CharSequence)object).find());
        return bl;
    }

    static boolean matchesServiceUuid(ParcelUuid parcelUuid, ParcelUuid parcelUuid2, BluetoothDevice object) {
        object = object.getUuids();
        boolean bl = parcelUuid == null || ScanFilter.matchesServiceUuids(parcelUuid, parcelUuid2, (List<ParcelUuid>)(object = object == null ? Collections.emptyList() : Arrays.asList(object)));
        return bl;
    }

    static boolean matchesServiceUuids(List<ParcelUuid> list, List<ParcelUuid> list2, BluetoothDevice bluetoothDevice) {
        for (int i = 0; i < list.size(); ++i) {
            if (BluetoothDeviceFilterUtils.matchesServiceUuid(list.get(i), list2.get(i), bluetoothDevice)) continue;
            return false;
        }
        return true;
    }

    static Pattern patternFromString(String object) {
        object = object == null ? null : Pattern.compile((String)object);
        return object;
    }

    static String patternToString(Pattern object) {
        object = object == null ? null : ((Pattern)object).pattern();
        return object;
    }
}

