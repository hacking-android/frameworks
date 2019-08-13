/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BluetoothLeUtils {
    static void checkAdapterStateOn(BluetoothAdapter bluetoothAdapter) {
        if (bluetoothAdapter != null && bluetoothAdapter.isLeEnabled()) {
            return;
        }
        throw new IllegalStateException("BT Adapter is not turned ON");
    }

    static boolean equals(SparseArray<byte[]> sparseArray, SparseArray<byte[]> sparseArray2) {
        if (sparseArray == sparseArray2) {
            return true;
        }
        if (sparseArray != null && sparseArray2 != null) {
            if (sparseArray.size() != sparseArray2.size()) {
                return false;
            }
            for (int i = 0; i < sparseArray.size(); ++i) {
                if (sparseArray.keyAt(i) == sparseArray2.keyAt(i) && Arrays.equals(sparseArray.valueAt(i), sparseArray2.valueAt(i))) {
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    static <T> boolean equals(Map<T, byte[]> map, Map<T, byte[]> map2) {
        if (map == map2) {
            return true;
        }
        if (map != null && map2 != null) {
            if (map.size() != map2.size()) {
                return false;
            }
            Set<T> set2 = map.keySet();
            if (!set2.equals(map2.keySet())) {
                return false;
            }
            for (Set<T> set2 : set2) {
                if (Objects.deepEquals(map.get(set2), map2.get(set2))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    static String toString(SparseArray<byte[]> sparseArray) {
        if (sparseArray == null) {
            return "null";
        }
        if (sparseArray.size() == 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        for (int i = 0; i < sparseArray.size(); ++i) {
            stringBuilder.append(sparseArray.keyAt(i));
            stringBuilder.append("=");
            stringBuilder.append(Arrays.toString(sparseArray.valueAt(i)));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    static <T> String toString(Map<T, byte[]> map) {
        if (map == null) {
            return "null";
        }
        if (map.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        Iterator<Map.Entry<T, byte[]>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            T t = iterator.next().getKey();
            stringBuilder.append(t);
            stringBuilder.append("=");
            stringBuilder.append(Arrays.toString(map.get(t)));
            if (!iterator.hasNext()) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

