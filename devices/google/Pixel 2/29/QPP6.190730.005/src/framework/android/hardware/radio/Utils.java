/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ICloseHandle;
import android.hardware.radio._$$Lambda$Utils$CpgxAbBJVMfl2IUCmgGvKDeq9_U;
import android.hardware.radio._$$Lambda$Utils$Cu3trYWUZE7O75pNHuKMUbHskAY;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

final class Utils {
    private static final String TAG = "BroadcastRadio.utils";

    Utils() {
    }

    static void close(ICloseHandle iCloseHandle) {
        try {
            iCloseHandle.close();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    static Set<Integer> createIntSet(Parcel parcel) {
        int n;
        HashSet<Integer> hashSet = new HashSet<Integer>(n);
        for (n = parcel.readInt(); n > 0; --n) {
            hashSet.add(parcel.readInt());
        }
        return hashSet;
    }

    static <T> Set<T> createSet(Parcel parcel, Parcelable.Creator<T> creator) {
        int n;
        HashSet<T> hashSet = new HashSet<T>(n);
        for (n = parcel.readInt(); n > 0; --n) {
            hashSet.add(parcel.readTypedObject(creator));
        }
        return hashSet;
    }

    static /* synthetic */ void lambda$writeIntSet$1(Parcel parcel, Integer n) {
        parcel.writeInt(Objects.requireNonNull(n));
    }

    static /* synthetic */ void lambda$writeSet$0(Parcel parcel, Parcelable parcelable) {
        parcel.writeTypedObject(parcelable, 0);
    }

    static Map<String, Integer> readStringIntMap(Parcel parcel) {
        int n;
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>(n);
        for (n = parcel.readInt(); n > 0; --n) {
            hashMap.put(parcel.readString(), parcel.readInt());
        }
        return hashMap;
    }

    static Map<String, String> readStringMap(Parcel parcel) {
        int n;
        HashMap<String, String> hashMap = new HashMap<String, String>(n);
        for (n = parcel.readInt(); n > 0; --n) {
            hashMap.put(parcel.readString(), parcel.readString());
        }
        return hashMap;
    }

    static void writeIntSet(Parcel parcel, Set<Integer> set) {
        if (set == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(set.size());
        set.stream().forEach(new _$$Lambda$Utils$CpgxAbBJVMfl2IUCmgGvKDeq9_U(parcel));
    }

    static <T extends Parcelable> void writeSet(Parcel parcel, Set<T> set) {
        if (set == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(set.size());
        set.stream().forEach(new _$$Lambda$Utils$Cu3trYWUZE7O75pNHuKMUbHskAY(parcel));
    }

    static void writeStringIntMap(Parcel parcel, Map<String, Integer> object) {
        if (object == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(object.size());
        for (Map.Entry entry : object.entrySet()) {
            parcel.writeString((String)entry.getKey());
            parcel.writeInt((Integer)entry.getValue());
        }
    }

    static void writeStringMap(Parcel parcel, Map<String, String> object2) {
        if (object2 == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(object2.size());
        for (Map.Entry entry : object2.entrySet()) {
            parcel.writeString((String)entry.getKey());
            parcel.writeString((String)entry.getValue());
        }
    }

    static <T extends Parcelable> void writeTypedCollection(Parcel parcel, Collection<T> collection) {
        ArrayList<T> arrayList = null;
        if (collection != null) {
            arrayList = collection instanceof ArrayList ? (ArrayList<T>)collection : new ArrayList<T>(collection);
        }
        parcel.writeTypedList(arrayList);
    }
}

