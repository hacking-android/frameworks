/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class ContentValues
implements Parcelable {
    public static final Parcelable.Creator<ContentValues> CREATOR = new Parcelable.Creator<ContentValues>(){

        @Override
        public ContentValues createFromParcel(Parcel parcel) {
            return new ContentValues(parcel);
        }

        public ContentValues[] newArray(int n) {
            return new ContentValues[n];
        }
    };
    public static final String TAG = "ContentValues";
    private final ArrayMap<String, Object> mMap;
    @Deprecated
    @UnsupportedAppUsage
    private HashMap<String, Object> mValues;

    public ContentValues() {
        this.mMap = new ArrayMap();
    }

    public ContentValues(int n) {
        Preconditions.checkArgumentNonnegative(n);
        this.mMap = new ArrayMap(n);
    }

    public ContentValues(ContentValues contentValues) {
        Objects.requireNonNull(contentValues);
        this.mMap = new ArrayMap<String, Object>(contentValues.mMap);
    }

    private ContentValues(Parcel parcel) {
        this.mMap = new ArrayMap(parcel.readInt());
        parcel.readArrayMap(this.mMap, null);
    }

    @Deprecated
    @UnsupportedAppUsage
    private ContentValues(HashMap<String, Object> hashMap) {
        this.mMap = new ArrayMap();
        this.mMap.putAll(hashMap);
    }

    public void clear() {
        this.mMap.clear();
    }

    public boolean containsKey(String string2) {
        return this.mMap.containsKey(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ContentValues)) {
            return false;
        }
        return this.mMap.equals(((ContentValues)object).mMap);
    }

    public Object get(String string2) {
        return this.mMap.get(string2);
    }

    public Boolean getAsBoolean(String string2) {
        Object object = this.mMap.get(string2);
        try {
            Boolean bl = (Boolean)object;
            return bl;
        }
        catch (ClassCastException classCastException) {
            boolean bl = object instanceof CharSequence;
            boolean bl2 = false;
            boolean bl3 = false;
            if (bl) {
                if (Boolean.valueOf(object.toString()).booleanValue() || "1".equals(object)) {
                    bl3 = true;
                }
                return bl3;
            }
            if (object instanceof Number) {
                bl3 = bl2;
                if (((Number)object).intValue() != 0) {
                    bl3 = true;
                }
                return bl3;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot cast value for ");
            stringBuilder.append(string2);
            stringBuilder.append(" to a Boolean: ");
            stringBuilder.append(object);
            Log.e(TAG, stringBuilder.toString(), classCastException);
            return null;
        }
    }

    public Byte getAsByte(String string2) {
        Object object = this.mMap.get(string2);
        Serializable serializable = null;
        if (object != null) {
            try {
                serializable = Byte.valueOf(((Number)object).byteValue());
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        serializable = Byte.valueOf(object.toString());
                        return serializable;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Byte value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Cannot cast value for ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" to a Byte: ");
                ((StringBuilder)serializable).append(object);
                Log.e(TAG, ((StringBuilder)serializable).toString(), classCastException);
                return null;
            }
        }
        return serializable;
    }

    public byte[] getAsByteArray(String object) {
        if ((object = this.mMap.get(object)) instanceof byte[]) {
            return (byte[])object;
        }
        return null;
    }

    public Double getAsDouble(String string2) {
        Object object = this.mMap.get(string2);
        Serializable serializable = null;
        if (object != null) {
            try {
                serializable = Double.valueOf(((Number)object).doubleValue());
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        serializable = Double.valueOf(object.toString());
                        return serializable;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Double value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Cannot cast value for ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" to a Double: ");
                ((StringBuilder)serializable).append(object);
                Log.e(TAG, ((StringBuilder)serializable).toString(), classCastException);
                return null;
            }
        }
        return serializable;
    }

    public Float getAsFloat(String string2) {
        Object object = this.mMap.get(string2);
        Serializable serializable = null;
        if (object != null) {
            try {
                serializable = Float.valueOf(((Number)object).floatValue());
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        serializable = Float.valueOf(object.toString());
                        return serializable;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Float value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Cannot cast value for ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" to a Float: ");
                ((StringBuilder)serializable).append(object);
                Log.e(TAG, ((StringBuilder)serializable).toString(), classCastException);
                return null;
            }
        }
        return serializable;
    }

    public Integer getAsInteger(String string2) {
        Object object = this.mMap.get(string2);
        Integer n = null;
        if (object != null) {
            try {
                n = ((Number)object).intValue();
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        Integer n2 = Integer.valueOf(object.toString());
                        return n2;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Integer value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot cast value for ");
                stringBuilder.append(string2);
                stringBuilder.append(" to a Integer: ");
                stringBuilder.append(object);
                Log.e(TAG, stringBuilder.toString(), classCastException);
                return null;
            }
        }
        return n;
    }

    public Long getAsLong(String string2) {
        Object object = this.mMap.get(string2);
        Serializable serializable = null;
        if (object != null) {
            try {
                serializable = Long.valueOf(((Number)object).longValue());
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        serializable = Long.valueOf(object.toString());
                        return serializable;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Long value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Cannot cast value for ");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append(" to a Long: ");
                ((StringBuilder)serializable).append(object);
                Log.e(TAG, ((StringBuilder)serializable).toString(), classCastException);
                return null;
            }
        }
        return serializable;
    }

    public Short getAsShort(String string2) {
        Object object = this.mMap.get(string2);
        Short s = null;
        if (object != null) {
            try {
                s = ((Number)object).shortValue();
            }
            catch (ClassCastException classCastException) {
                if (object instanceof CharSequence) {
                    try {
                        Short s2 = Short.valueOf(object.toString());
                        return s2;
                    }
                    catch (NumberFormatException numberFormatException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Cannot parse Short value for ");
                        stringBuilder.append(object);
                        stringBuilder.append(" at key ");
                        stringBuilder.append(string2);
                        Log.e(TAG, stringBuilder.toString());
                        return null;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot cast value for ");
                stringBuilder.append(string2);
                stringBuilder.append(" to a Short: ");
                stringBuilder.append(object);
                Log.e(TAG, stringBuilder.toString(), classCastException);
                return null;
            }
        }
        return s;
    }

    public String getAsString(String object) {
        object = (object = this.mMap.get(object)) != null ? object.toString() : null;
        return object;
    }

    @Deprecated
    @UnsupportedAppUsage
    public ArrayList<String> getStringArrayList(String string2) {
        return (ArrayList)this.mMap.get(string2);
    }

    public ArrayMap<String, Object> getValues() {
        return this.mMap;
    }

    public int hashCode() {
        return this.mMap.hashCode();
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    public Set<String> keySet() {
        return this.mMap.keySet();
    }

    public void put(String string2, Boolean bl) {
        this.mMap.put(string2, bl);
    }

    public void put(String string2, Byte by) {
        this.mMap.put(string2, by);
    }

    public void put(String string2, Double d) {
        this.mMap.put(string2, d);
    }

    public void put(String string2, Float f) {
        this.mMap.put(string2, f);
    }

    public void put(String string2, Integer n) {
        this.mMap.put(string2, n);
    }

    public void put(String string2, Long l) {
        this.mMap.put(string2, l);
    }

    public void put(String string2, Short s) {
        this.mMap.put(string2, s);
    }

    public void put(String string2, String string3) {
        this.mMap.put(string2, string3);
    }

    public void put(String string2, byte[] arrby) {
        this.mMap.put(string2, arrby);
    }

    public void putAll(ContentValues contentValues) {
        this.mMap.putAll(contentValues.mMap);
    }

    public void putNull(String string2) {
        this.mMap.put(string2, null);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void putStringArrayList(String string2, ArrayList<String> arrayList) {
        this.mMap.put(string2, arrayList);
    }

    public void remove(String string2) {
        this.mMap.remove(string2);
    }

    public int size() {
        return this.mMap.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : this.mMap.keySet()) {
            String string3 = this.getAsString(string2);
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string2);
            stringBuilder2.append("=");
            stringBuilder2.append(string3);
            stringBuilder.append(stringBuilder2.toString());
        }
        return stringBuilder.toString();
    }

    public Set<Map.Entry<String, Object>> valueSet() {
        return this.mMap.entrySet();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMap.size());
        parcel.writeArrayMap(this.mMap);
    }

}

