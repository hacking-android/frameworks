/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class StatsDimensionsValue
implements Parcelable {
    public static final int BOOLEAN_VALUE_TYPE = 5;
    public static final Parcelable.Creator<StatsDimensionsValue> CREATOR = new Parcelable.Creator<StatsDimensionsValue>(){

        @Override
        public StatsDimensionsValue createFromParcel(Parcel parcel) {
            return new StatsDimensionsValue(parcel);
        }

        public StatsDimensionsValue[] newArray(int n) {
            return new StatsDimensionsValue[n];
        }
    };
    public static final int FLOAT_VALUE_TYPE = 6;
    public static final int INT_VALUE_TYPE = 3;
    public static final int LONG_VALUE_TYPE = 4;
    public static final int STRING_VALUE_TYPE = 2;
    private static final String TAG = "StatsDimensionsValue";
    public static final int TUPLE_VALUE_TYPE = 7;
    private final int mField;
    private final Object mValue;
    private final int mValueType;

    public StatsDimensionsValue(Parcel parcel) {
        this.mField = parcel.readInt();
        this.mValueType = parcel.readInt();
        this.mValue = StatsDimensionsValue.readValueFromParcel(this.mValueType, parcel);
    }

    private static Object readValueFromParcel(int n, Parcel object) {
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("readValue of an impossible type ");
                ((StringBuilder)object).append(n);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return null;
            }
            case 7: {
                int n2 = ((Parcel)object).readInt();
                StatsDimensionsValue[] arrstatsDimensionsValue = new StatsDimensionsValue[n2];
                for (n = 0; n < n2; ++n) {
                    arrstatsDimensionsValue[n] = new StatsDimensionsValue((Parcel)object);
                }
                return arrstatsDimensionsValue;
            }
            case 6: {
                return Float.valueOf(((Parcel)object).readFloat());
            }
            case 5: {
                return ((Parcel)object).readBoolean();
            }
            case 4: {
                return ((Parcel)object).readLong();
            }
            case 3: {
                return ((Parcel)object).readInt();
            }
            case 2: 
        }
        return ((Parcel)object).readString();
    }

    /*
     * Exception decompiling
     */
    private static boolean writeValueToParcel(int var0, Object var1_1, Parcel var2_3, int var3_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getBooleanValue() {
        try {
            if (this.mValueType == 5) {
                boolean bl = (Boolean)this.mValue;
                return bl;
            }
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
        }
        return false;
    }

    public int getField() {
        return this.mField;
    }

    public float getFloatValue() {
        try {
            if (this.mValueType == 6) {
                float f = ((Float)this.mValue).floatValue();
                return f;
            }
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
        }
        return 0.0f;
    }

    public int getIntValue() {
        try {
            if (this.mValueType == 3) {
                int n = (Integer)this.mValue;
                return n;
            }
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
        }
        return 0;
    }

    public long getLongValue() {
        try {
            if (this.mValueType == 4) {
                long l = (Long)this.mValue;
                return l;
            }
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
        }
        return 0L;
    }

    public String getStringValue() {
        try {
            if (this.mValueType == 2) {
                String string2 = (String)this.mValue;
                return string2;
            }
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<StatsDimensionsValue> getTupleValueList() {
        StatsDimensionsValue[] arrstatsDimensionsValue;
        ArrayList<StatsDimensionsValue> arrayList;
        int n;
        if (this.mValueType != 7) {
            return null;
        }
        try {
            arrstatsDimensionsValue = (StatsDimensionsValue[])this.mValue;
            arrayList = new ArrayList<StatsDimensionsValue>(arrstatsDimensionsValue.length);
            n = 0;
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
            return null;
        }
        do {
            if (n >= arrstatsDimensionsValue.length) return arrayList;
            arrayList.add(arrstatsDimensionsValue[n]);
            ++n;
            continue;
            break;
        } while (true);
    }

    public int getValueType() {
        return this.mValueType;
    }

    public boolean isValueType(int n) {
        boolean bl = this.mValueType == n;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        try {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mField);
            ((StringBuilder)charSequence).append(":");
            if (this.mValueType == 7) {
                ((StringBuilder)charSequence).append("{");
                StatsDimensionsValue[] arrstatsDimensionsValue = (StatsDimensionsValue[])this.mValue;
                for (int i = 0; i < arrstatsDimensionsValue.length; ++i) {
                    ((StringBuilder)charSequence).append(arrstatsDimensionsValue[i].toString());
                    ((StringBuilder)charSequence).append("|");
                }
                ((StringBuilder)charSequence).append("}");
                return ((StringBuilder)charSequence).toString();
            } else {
                ((StringBuilder)charSequence).append(this.mValue.toString());
            }
            return ((StringBuilder)charSequence).toString();
        }
        catch (ClassCastException classCastException) {
            Slog.w(TAG, "Failed to successfully get value", classCastException);
            return "";
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mField);
        parcel.writeInt(this.mValueType);
        StatsDimensionsValue.writeValueToParcel(this.mValueType, this.mValue, parcel, n);
    }

}

