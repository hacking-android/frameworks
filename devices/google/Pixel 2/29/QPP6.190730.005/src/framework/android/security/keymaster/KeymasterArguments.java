/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keymaster.KeymasterArgument;
import android.security.keymaster.KeymasterBlobArgument;
import android.security.keymaster.KeymasterBooleanArgument;
import android.security.keymaster.KeymasterDateArgument;
import android.security.keymaster.KeymasterDefs;
import android.security.keymaster.KeymasterIntArgument;
import android.security.keymaster.KeymasterLongArgument;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KeymasterArguments
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<KeymasterArguments> CREATOR;
    public static final long UINT32_MAX_VALUE = 0xFFFFFFFFL;
    private static final long UINT32_RANGE = 0x100000000L;
    public static final BigInteger UINT64_MAX_VALUE;
    private static final BigInteger UINT64_RANGE;
    private List<KeymasterArgument> mArguments;

    static {
        UINT64_RANGE = BigInteger.ONE.shiftLeft(64);
        UINT64_MAX_VALUE = UINT64_RANGE.subtract(BigInteger.ONE);
        CREATOR = new Parcelable.Creator<KeymasterArguments>(){

            @Override
            public KeymasterArguments createFromParcel(Parcel parcel) {
                return new KeymasterArguments(parcel);
            }

            public KeymasterArguments[] newArray(int n) {
                return new KeymasterArguments[n];
            }
        };
    }

    @UnsupportedAppUsage
    public KeymasterArguments() {
        this.mArguments = new ArrayList<KeymasterArgument>();
    }

    private KeymasterArguments(Parcel parcel) {
        this.mArguments = parcel.createTypedArrayList(KeymasterArgument.CREATOR);
    }

    private void addEnumTag(int n, int n2) {
        this.mArguments.add(new KeymasterIntArgument(n, n2));
    }

    private void addLongTag(int n, BigInteger bigInteger) {
        if (bigInteger.signum() != -1 && bigInteger.compareTo(UINT64_MAX_VALUE) <= 0) {
            this.mArguments.add(new KeymasterLongArgument(n, bigInteger.longValue()));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Long tag value out of range: ");
        stringBuilder.append(bigInteger);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private KeymasterArgument getArgumentByTag(int n) {
        for (KeymasterArgument keymasterArgument : this.mArguments) {
            if (keymasterArgument.tag != n) continue;
            return keymasterArgument;
        }
        return null;
    }

    private int getEnumTagValue(KeymasterArgument keymasterArgument) {
        return ((KeymasterIntArgument)keymasterArgument).value;
    }

    private BigInteger getLongTagValue(KeymasterArgument keymasterArgument) {
        return KeymasterArguments.toUint64(((KeymasterLongArgument)keymasterArgument).value);
    }

    public static BigInteger toUint64(long l) {
        if (l >= 0L) {
            return BigInteger.valueOf(l);
        }
        return BigInteger.valueOf(l).add(UINT64_RANGE);
    }

    public void addBoolean(int n) {
        if (KeymasterDefs.getTagType(n) == 1879048192) {
            this.mArguments.add(new KeymasterBooleanArgument(n));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a boolean tag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void addBytes(int n, byte[] object) {
        if (KeymasterDefs.getTagType(n) == -1879048192) {
            if (object != null) {
                this.mArguments.add(new KeymasterBlobArgument(n, (byte[])object));
                return;
            }
            throw new NullPointerException("value == nulll");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not a bytes tag: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void addDate(int n, Date serializable) {
        if (KeymasterDefs.getTagType(n) == 1610612736) {
            if (serializable != null) {
                if (((Date)serializable).getTime() >= 0L) {
                    this.mArguments.add(new KeymasterDateArgument(n, (Date)serializable));
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Date tag value out of range: ");
                stringBuilder.append(serializable);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            throw new NullPointerException("value == nulll");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Not a date tag: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public void addDateIfNotNull(int n, Date serializable) {
        if (KeymasterDefs.getTagType(n) == 1610612736) {
            if (serializable != null) {
                this.addDate(n, (Date)serializable);
            }
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Not a date tag: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    @UnsupportedAppUsage
    public void addEnum(int n, int n2) {
        int n3 = KeymasterDefs.getTagType(n);
        if (n3 != 268435456 && n3 != 536870912) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not an enum or repeating enum tag: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.addEnumTag(n, n2);
    }

    public void addEnums(int n, int ... object) {
        if (KeymasterDefs.getTagType(n) == 536870912) {
            int n2 = ((int[])object).length;
            for (int i = 0; i < n2; ++i) {
                this.addEnumTag(n, (int)object[i]);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not a repeating enum tag: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public void addUnsignedInt(int n, long l) {
        int n2 = KeymasterDefs.getTagType(n);
        if (n2 != 805306368 && n2 != 1073741824) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not an int or repeating int tag: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (l >= 0L && l <= 0xFFFFFFFFL) {
            this.mArguments.add(new KeymasterIntArgument(n, (int)l));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Int tag value out of range: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void addUnsignedLong(int n, BigInteger serializable) {
        int n2 = KeymasterDefs.getTagType(n);
        if (n2 != 1342177280 && n2 != -1610612736) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Not a long or repeating long tag: ");
            ((StringBuilder)serializable).append(n);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        this.addLongTag(n, (BigInteger)serializable);
    }

    public boolean containsTag(int n) {
        boolean bl = this.getArgumentByTag(n) != null;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getBoolean(int n) {
        if (KeymasterDefs.getTagType(n) == 1879048192) {
            return this.getArgumentByTag(n) != null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a boolean tag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public byte[] getBytes(int n, byte[] object) {
        if (KeymasterDefs.getTagType(n) == -1879048192) {
            KeymasterArgument keymasterArgument = this.getArgumentByTag(n);
            if (keymasterArgument == null) {
                return object;
            }
            return ((KeymasterBlobArgument)keymasterArgument).blob;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not a bytes tag: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public Date getDate(int n, Date serializable) {
        if (KeymasterDefs.getTagType(n) == 1610612736) {
            KeymasterArgument keymasterArgument = this.getArgumentByTag(n);
            if (keymasterArgument == null) {
                return serializable;
            }
            serializable = ((KeymasterDateArgument)keymasterArgument).date;
            if (((Date)serializable).getTime() >= 0L) {
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Tag value too large. Tag: ");
            ((StringBuilder)serializable).append(n);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Tag is not a date type: ");
        ((StringBuilder)serializable).append(n);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public int getEnum(int n, int n2) {
        if (KeymasterDefs.getTagType(n) == 268435456) {
            KeymasterArgument keymasterArgument = this.getArgumentByTag(n);
            if (keymasterArgument == null) {
                return n2;
            }
            return this.getEnumTagValue(keymasterArgument);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not an enum tag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public List<Integer> getEnums(int n) {
        if (KeymasterDefs.getTagType(n) == 536870912) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (KeymasterArgument keymasterArgument : this.mArguments) {
                if (keymasterArgument.tag != n) continue;
                arrayList.add(this.getEnumTagValue(keymasterArgument));
            }
            return arrayList;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a repeating enum tag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public long getUnsignedInt(int n, long l) {
        if (KeymasterDefs.getTagType(n) == 805306368) {
            KeymasterArgument keymasterArgument = this.getArgumentByTag(n);
            if (keymasterArgument == null) {
                return l;
            }
            return (long)((KeymasterIntArgument)keymasterArgument).value & 0xFFFFFFFFL;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not an int tag: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public List<BigInteger> getUnsignedLongs(int n) {
        if (KeymasterDefs.getTagType(n) == -1610612736) {
            ArrayList<BigInteger> arrayList = new ArrayList<BigInteger>();
            for (KeymasterArgument keymasterArgument : this.mArguments) {
                if (keymasterArgument.tag != n) continue;
                arrayList.add(this.getLongTagValue(keymasterArgument));
            }
            return arrayList;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tag is not a repeating long: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void readFromParcel(Parcel parcel) {
        parcel.readTypedList(this.mArguments, KeymasterArgument.CREATOR);
    }

    public int size() {
        return this.mArguments.size();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mArguments);
    }

}

