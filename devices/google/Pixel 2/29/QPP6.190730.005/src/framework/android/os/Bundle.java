/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.BaseBundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Bundle
extends BaseBundle
implements Cloneable,
Parcelable {
    public static final Parcelable.Creator<Bundle> CREATOR;
    public static final Bundle EMPTY;
    @VisibleForTesting
    static final int FLAG_ALLOW_FDS = 1024;
    @VisibleForTesting
    static final int FLAG_HAS_FDS = 256;
    @VisibleForTesting
    static final int FLAG_HAS_FDS_KNOWN = 512;
    public static final Bundle STRIPPED;

    static {
        EMPTY = new Bundle();
        Bundle.EMPTY.mMap = ArrayMap.EMPTY;
        STRIPPED = new Bundle();
        STRIPPED.putInt("STRIPPED", 1);
        CREATOR = new Parcelable.Creator<Bundle>(){

            @Override
            public Bundle createFromParcel(Parcel parcel) {
                return parcel.readBundle();
            }

            public Bundle[] newArray(int n) {
                return new Bundle[n];
            }
        };
    }

    public Bundle() {
        this.mFlags = 1536;
    }

    public Bundle(int n) {
        super(n);
        this.mFlags = 1536;
    }

    public Bundle(Bundle bundle) {
        super(bundle);
        this.mFlags = bundle.mFlags;
    }

    @VisibleForTesting
    public Bundle(Parcel parcel) {
        super(parcel);
        this.mFlags = 1024;
        this.maybePrefillHasFds();
    }

    @VisibleForTesting
    public Bundle(Parcel parcel, int n) {
        super(parcel, n);
        this.mFlags = 1024;
        this.maybePrefillHasFds();
    }

    public Bundle(PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.mFlags = 1536;
    }

    public Bundle(ClassLoader classLoader) {
        super(classLoader);
        this.mFlags = 1536;
    }

    Bundle(boolean bl) {
        super(bl);
    }

    @UnsupportedAppUsage
    public static Bundle forPair(String string2, String string3) {
        Bundle bundle = new Bundle(1);
        bundle.putString(string2, string3);
        return bundle;
    }

    private void maybePrefillHasFds() {
        if (this.mParcelledData != null) {
            this.mFlags = this.mParcelledData.hasFileDescriptors() ? (this.mFlags |= 768) : (this.mFlags |= 512);
        }
    }

    @UnsupportedAppUsage
    public static Bundle setDefusable(Bundle bundle, boolean bl) {
        if (bundle != null) {
            bundle.setDefusable(bl);
        }
        return bundle;
    }

    @Override
    public void clear() {
        super.clear();
        this.mFlags = 1536;
    }

    public Object clone() {
        return new Bundle(this);
    }

    public Bundle deepCopy() {
        Bundle bundle = new Bundle(false);
        bundle.copyInternal(this, true);
        return bundle;
    }

    @Override
    public int describeContents() {
        int n = 0;
        if (this.hasFileDescriptors()) {
            n = false | true;
        }
        return n;
    }

    @UnsupportedAppUsage
    public Bundle filterValues() {
        Bundle bundle;
        this.unparcel();
        Bundle bundle2 = bundle = this;
        if (this.mMap != null) {
            ArrayMap arrayMap = this.mMap;
            int n = arrayMap.size() - 1;
            do {
                ArrayMap arrayMap2;
                bundle2 = bundle;
                if (n < 0) break;
                Object v = arrayMap.valueAt(n);
                if (PersistableBundle.isValidType(v)) {
                    bundle2 = bundle;
                    arrayMap2 = arrayMap;
                } else if (v instanceof Bundle) {
                    Bundle bundle3 = ((Bundle)v).filterValues();
                    bundle2 = bundle;
                    arrayMap2 = arrayMap;
                    if (bundle3 != v) {
                        arrayMap2 = arrayMap;
                        if (arrayMap == this.mMap) {
                            bundle = new Bundle(this);
                            arrayMap2 = bundle.mMap;
                        }
                        arrayMap2.setValueAt(n, bundle3);
                        bundle2 = bundle;
                    }
                } else if (v.getClass().getName().startsWith("android.")) {
                    bundle2 = bundle;
                    arrayMap2 = arrayMap;
                } else {
                    arrayMap2 = arrayMap;
                    if (arrayMap == this.mMap) {
                        bundle = new Bundle(this);
                        arrayMap2 = bundle.mMap;
                    }
                    arrayMap2.removeAt(n);
                    bundle2 = bundle;
                }
                --n;
                bundle = bundle2;
                arrayMap = arrayMap2;
            } while (true);
        }
        this.mFlags |= 512;
        this.mFlags &= -257;
        return bundle2;
    }

    public IBinder getBinder(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            IBinder iBinder = (IBinder)v;
            return iBinder;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "IBinder", classCastException);
            return null;
        }
    }

    public Bundle getBundle(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            Bundle bundle = (Bundle)v;
            return bundle;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "Bundle", classCastException);
            return null;
        }
    }

    @Override
    public byte getByte(String string2) {
        return super.getByte(string2);
    }

    @Override
    public Byte getByte(String string2, byte by) {
        return super.getByte(string2, by);
    }

    @Override
    public byte[] getByteArray(String string2) {
        return super.getByteArray(string2);
    }

    @Override
    public char getChar(String string2) {
        return super.getChar(string2);
    }

    @Override
    public char getChar(String string2, char c) {
        return super.getChar(string2, c);
    }

    @Override
    public char[] getCharArray(String string2) {
        return super.getCharArray(string2);
    }

    @Override
    public CharSequence getCharSequence(String string2) {
        return super.getCharSequence(string2);
    }

    @Override
    public CharSequence getCharSequence(String string2, CharSequence charSequence) {
        return super.getCharSequence(string2, charSequence);
    }

    @Override
    public CharSequence[] getCharSequenceArray(String string2) {
        return super.getCharSequenceArray(string2);
    }

    @Override
    public ArrayList<CharSequence> getCharSequenceArrayList(String string2) {
        return super.getCharSequenceArrayList(string2);
    }

    @Override
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    @Override
    public float getFloat(String string2) {
        return super.getFloat(string2);
    }

    @Override
    public float getFloat(String string2, float f) {
        return super.getFloat(string2, f);
    }

    @Override
    public float[] getFloatArray(String string2) {
        return super.getFloatArray(string2);
    }

    @Deprecated
    @UnsupportedAppUsage
    public IBinder getIBinder(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            IBinder iBinder = (IBinder)v;
            return iBinder;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "IBinder", classCastException);
            return null;
        }
    }

    @Override
    public ArrayList<Integer> getIntegerArrayList(String string2) {
        return super.getIntegerArrayList(string2);
    }

    public <T extends Parcelable> T getParcelable(String string2) {
        Parcelable parcelable;
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            parcelable = (Parcelable)v;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "Parcelable", classCastException);
            return null;
        }
        return (T)parcelable;
    }

    public Parcelable[] getParcelableArray(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            Parcelable[] arrparcelable = (Parcelable[])v;
            return arrparcelable;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "Parcelable[]", classCastException);
            return null;
        }
    }

    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)v;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "ArrayList", classCastException);
            return null;
        }
    }

    @Override
    public Serializable getSerializable(String string2) {
        return super.getSerializable(string2);
    }

    @Override
    public short getShort(String string2) {
        return super.getShort(string2);
    }

    @Override
    public short getShort(String string2, short s) {
        return super.getShort(string2, s);
    }

    @Override
    public short[] getShortArray(String string2) {
        return super.getShortArray(string2);
    }

    @UnsupportedAppUsage
    public int getSize() {
        if (this.mParcelledData != null) {
            return this.mParcelledData.dataSize();
        }
        return 0;
    }

    public Size getSize(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        try {
            Size size = (Size)v;
            return size;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "Size", classCastException);
            return null;
        }
    }

    public SizeF getSizeF(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        try {
            SizeF sizeF = (SizeF)v;
            return sizeF;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "SizeF", classCastException);
            return null;
        }
    }

    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(String string2) {
        this.unparcel();
        Object v = this.mMap.get(string2);
        if (v == null) {
            return null;
        }
        try {
            SparseArray sparseArray = (SparseArray)v;
            return sparseArray;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, v, "SparseArray", classCastException);
            return null;
        }
    }

    @Override
    public ArrayList<String> getStringArrayList(String string2) {
        return super.getStringArrayList(string2);
    }

    public boolean hasFileDescriptors() {
        int n = this.mFlags;
        boolean bl = false;
        if ((n & 512) == 0) {
            int n2 = 0;
            n = 0;
            if (this.mParcelledData != null) {
                if (this.mParcelledData.hasFileDescriptors()) {
                    n2 = 1;
                }
            } else {
                int n3 = this.mMap.size() - 1;
                do {
                    block20 : {
                        n2 = n;
                        if (n3 < 0) break;
                        Object object = this.mMap.valueAt(n3);
                        if (object instanceof Parcelable) {
                            n2 = n;
                            if ((((Parcelable)object).describeContents() & 1) != 0) {
                                n2 = 1;
                                break;
                            }
                        } else {
                            Object object2;
                            int n4;
                            if (object instanceof Parcelable[]) {
                                object = (Parcelable[])object;
                                n4 = ((V)object).length - 1;
                                do {
                                    n2 = n;
                                    if (n4 < 0) break block20;
                                    object2 = object[n4];
                                    if (object2 != null && (object2.describeContents() & 1) != 0) {
                                        n2 = 1;
                                        break block20;
                                    }
                                    --n4;
                                } while (true);
                            }
                            if (object instanceof SparseArray) {
                                object2 = (SparseArray)object;
                                n4 = ((SparseArray)object2).size() - 1;
                                do {
                                    n2 = n;
                                    if (n4 < 0) break block20;
                                    object = (Parcelable)((SparseArray)object2).valueAt(n4);
                                    if (object != null && (object.describeContents() & 1) != 0) {
                                        n2 = 1;
                                        break block20;
                                    }
                                    --n4;
                                } while (true);
                            }
                            n2 = n;
                            if (object instanceof ArrayList) {
                                object = (ArrayList)object;
                                n2 = n;
                                if (!((ArrayList)object).isEmpty()) {
                                    n2 = n;
                                    if (((ArrayList)object).get(0) instanceof Parcelable) {
                                        n4 = ((ArrayList)object).size() - 1;
                                        do {
                                            n2 = n;
                                            if (n4 < 0) break;
                                            object2 = (Parcelable)((ArrayList)object).get(n4);
                                            if (object2 != null && (object2.describeContents() & 1) != 0) {
                                                n2 = 1;
                                                break;
                                            }
                                            --n4;
                                        } while (true);
                                    }
                                }
                            }
                        }
                    }
                    --n3;
                    n = n2;
                } while (true);
            }
            this.mFlags = n2 != 0 ? (this.mFlags |= 256) : (this.mFlags &= -257);
            this.mFlags |= 512;
        }
        if ((this.mFlags & 256) != 0) {
            bl = true;
        }
        return bl;
    }

    public void putAll(Bundle bundle) {
        this.unparcel();
        bundle.unparcel();
        this.mMap.putAll(bundle.mMap);
        if ((bundle.mFlags & 256) != 0) {
            this.mFlags |= 256;
        }
        if ((bundle.mFlags & 512) == 0) {
            this.mFlags &= -513;
        }
    }

    public void putBinder(String string2, IBinder iBinder) {
        this.unparcel();
        this.mMap.put(string2, iBinder);
    }

    public void putBundle(String string2, Bundle bundle) {
        this.unparcel();
        this.mMap.put(string2, bundle);
    }

    @Override
    public void putByte(String string2, byte by) {
        super.putByte(string2, by);
    }

    @Override
    public void putByteArray(String string2, byte[] arrby) {
        super.putByteArray(string2, arrby);
    }

    @Override
    public void putChar(String string2, char c) {
        super.putChar(string2, c);
    }

    @Override
    public void putCharArray(String string2, char[] arrc) {
        super.putCharArray(string2, arrc);
    }

    @Override
    public void putCharSequence(String string2, CharSequence charSequence) {
        super.putCharSequence(string2, charSequence);
    }

    @Override
    public void putCharSequenceArray(String string2, CharSequence[] arrcharSequence) {
        super.putCharSequenceArray(string2, arrcharSequence);
    }

    @Override
    public void putCharSequenceArrayList(String string2, ArrayList<CharSequence> arrayList) {
        super.putCharSequenceArrayList(string2, arrayList);
    }

    @Override
    public void putFloat(String string2, float f) {
        super.putFloat(string2, f);
    }

    @Override
    public void putFloatArray(String string2, float[] arrf) {
        super.putFloatArray(string2, arrf);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void putIBinder(String string2, IBinder iBinder) {
        this.unparcel();
        this.mMap.put(string2, iBinder);
    }

    @Override
    public void putIntegerArrayList(String string2, ArrayList<Integer> arrayList) {
        super.putIntegerArrayList(string2, arrayList);
    }

    public void putParcelable(String string2, Parcelable parcelable) {
        this.unparcel();
        this.mMap.put(string2, parcelable);
        this.mFlags &= -513;
    }

    public void putParcelableArray(String string2, Parcelable[] arrparcelable) {
        this.unparcel();
        this.mMap.put(string2, arrparcelable);
        this.mFlags &= -513;
    }

    public void putParcelableArrayList(String string2, ArrayList<? extends Parcelable> arrayList) {
        this.unparcel();
        this.mMap.put(string2, arrayList);
        this.mFlags &= -513;
    }

    @UnsupportedAppUsage
    public void putParcelableList(String string2, List<? extends Parcelable> list) {
        this.unparcel();
        this.mMap.put(string2, list);
        this.mFlags &= -513;
    }

    @Override
    public void putSerializable(String string2, Serializable serializable) {
        super.putSerializable(string2, serializable);
    }

    @Override
    public void putShort(String string2, short s) {
        super.putShort(string2, s);
    }

    @Override
    public void putShortArray(String string2, short[] arrs) {
        super.putShortArray(string2, arrs);
    }

    public void putSize(String string2, Size size) {
        this.unparcel();
        this.mMap.put(string2, size);
    }

    public void putSizeF(String string2, SizeF sizeF) {
        this.unparcel();
        this.mMap.put(string2, sizeF);
    }

    public void putSparseParcelableArray(String string2, SparseArray<? extends Parcelable> sparseArray) {
        this.unparcel();
        this.mMap.put(string2, sparseArray);
        this.mFlags &= -513;
    }

    @Override
    public void putStringArrayList(String string2, ArrayList<String> arrayList) {
        super.putStringArrayList(string2, arrayList);
    }

    public void readFromParcel(Parcel parcel) {
        super.readFromParcelInner(parcel);
        this.mFlags = 1024;
        this.maybePrefillHasFds();
    }

    @Override
    public void remove(String string2) {
        super.remove(string2);
        if ((this.mFlags & 256) != 0) {
            this.mFlags &= -513;
        }
    }

    public boolean setAllowFds(boolean bl) {
        boolean bl2 = (this.mFlags & 1024) != 0;
        this.mFlags = bl ? (this.mFlags |= 1024) : (this.mFlags &= -1025);
        return bl2;
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }

    public void setDefusable(boolean bl) {
        this.mFlags = bl ? (this.mFlags |= 1) : (this.mFlags &= -2);
    }

    public String toShortString() {
        synchronized (this) {
            block5 : {
                block6 : {
                    if (this.mParcelledData == null) break block5;
                    if (!this.isEmptyParcel()) break block6;
                    return "EMPTY_PARCEL";
                }
                CharSequence charSequence = new StringBuilder();
                charSequence.append("mParcelledData.dataSize=");
                charSequence.append(this.mParcelledData.dataSize());
                charSequence = charSequence.toString();
                return charSequence;
            }
            String string2 = this.mMap.toString();
            return string2;
        }
    }

    public String toString() {
        synchronized (this) {
            block5 : {
                block6 : {
                    if (this.mParcelledData == null) break block5;
                    if (!this.isEmptyParcel()) break block6;
                    return "Bundle[EMPTY_PARCEL]";
                }
                CharSequence charSequence = new StringBuilder();
                charSequence.append("Bundle[mParcelledData.dataSize=");
                charSequence.append(this.mParcelledData.dataSize());
                charSequence.append("]");
                charSequence = charSequence.toString();
                return charSequence;
            }
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Bundle[");
            charSequence.append(this.mMap.toString());
            charSequence.append("]");
            charSequence = charSequence.toString();
            return charSequence;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        boolean bl = (this.mFlags & 1024) != 0;
        bl = parcel.pushAllowFds(bl);
        try {
            super.writeToParcelInner(parcel, n);
            return;
        }
        finally {
            parcel.restoreAllowFds(bl);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        if (this.mParcelledData != null) {
            if (this.isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(l);
    }

}

