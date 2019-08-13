/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class BaseBundle {
    private static final int BUNDLE_MAGIC = 1279544898;
    private static final int BUNDLE_MAGIC_NATIVE = 1279544900;
    static final boolean DEBUG = false;
    static final int FLAG_DEFUSABLE = 1;
    private static final boolean LOG_DEFUSABLE = false;
    private static final String TAG = "Bundle";
    private static volatile boolean sShouldDefuse = false;
    private ClassLoader mClassLoader;
    @VisibleForTesting
    public int mFlags;
    @UnsupportedAppUsage
    ArrayMap<String, Object> mMap = null;
    private boolean mParcelledByNative;
    @UnsupportedAppUsage
    Parcel mParcelledData = null;

    BaseBundle() {
        this((ClassLoader)null, 0);
    }

    BaseBundle(int n) {
        this((ClassLoader)null, n);
    }

    BaseBundle(BaseBundle baseBundle) {
        this.copyInternal(baseBundle, false);
    }

    BaseBundle(Parcel parcel) {
        this.readFromParcelInner(parcel);
    }

    BaseBundle(Parcel parcel, int n) {
        this.readFromParcelInner(parcel, n);
    }

    BaseBundle(ClassLoader classLoader) {
        this(classLoader, 0);
    }

    BaseBundle(ClassLoader classLoader, int n) {
        ArrayMap arrayMap = n > 0 ? new ArrayMap(n) : new ArrayMap();
        this.mMap = arrayMap;
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        this.mClassLoader = classLoader;
    }

    BaseBundle(boolean bl) {
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, BaseBundle object) {
        indentingPrintWriter.increaseIndent();
        if (object == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        object = ((BaseBundle)object).getMap();
        for (int i = 0; i < ((ArrayMap)object).size(); ++i) {
            BaseBundle.dumpStats(indentingPrintWriter, (String)((ArrayMap)object).keyAt(i), ((ArrayMap)object).valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, SparseArray sparseArray) {
        indentingPrintWriter.increaseIndent();
        if (sparseArray == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        for (int i = 0; i < sparseArray.size(); ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(sparseArray.keyAt(i)));
            BaseBundle.dumpStats(indentingPrintWriter, stringBuilder.toString(), sparseArray.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpStats(IndentingPrintWriter indentingPrintWriter, String string2, Object object) {
        Object object2 = Parcel.obtain();
        ((Parcel)object2).writeValue(object);
        int n = ((Parcel)object2).dataPosition();
        ((Parcel)object2).recycle();
        if (n > 1024) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" [size=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append("]");
            indentingPrintWriter.println(((StringBuilder)object2).toString());
            if (object instanceof BaseBundle) {
                BaseBundle.dumpStats(indentingPrintWriter, (BaseBundle)object);
            } else if (object instanceof SparseArray) {
                BaseBundle.dumpStats(indentingPrintWriter, (SparseArray)object);
            }
        }
    }

    /*
     * Exception decompiling
     */
    private void initializeFromParcelLocked(Parcel var1_1, boolean var2_2, boolean var3_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[SIMPLE_IF_TAKEN]], but top level block is 0[TRYBLOCK]
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

    private static boolean isEmptyParcel(Parcel parcel) {
        boolean bl = parcel == NoImagePreloadHolder.EMPTY_PARCEL;
        return bl;
    }

    public static boolean kindofEquals(BaseBundle baseBundle, BaseBundle baseBundle2) {
        boolean bl = baseBundle == baseBundle2 || baseBundle != null && baseBundle.kindofEquals(baseBundle2);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readFromParcelInner(Parcel object, int n) {
        if (n < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad length in parcel: ");
            ((StringBuilder)object).append(n);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (n == 0) {
            this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            this.mParcelledByNative = false;
            return;
        }
        if (n % 4 != 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bundle length is not aligned by 4: ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        int n2 = ((Parcel)object).readInt();
        boolean bl = true;
        int n3 = n2 == 1279544898 ? 1 : 0;
        if (n2 != 1279544900) {
            bl = false;
        }
        if (n3 == 0 && !bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad magic number for Bundle: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n2));
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        if (((Parcel)object).hasReadWriteHelper()) {
            synchronized (this) {
                this.initializeFromParcelLocked((Parcel)object, false, bl);
                return;
            }
        }
        n3 = ((Parcel)object).dataPosition();
        ((Parcel)object).setDataPosition(MathUtils.addOrThrow(n3, n));
        Parcel parcel = Parcel.obtain();
        parcel.setDataPosition(0);
        parcel.appendFrom((Parcel)object, n3, n);
        parcel.adoptClassCookies((Parcel)object);
        parcel.setDataPosition(0);
        this.mParcelledData = parcel;
        this.mParcelledByNative = bl;
    }

    private static void recycleParcel(Parcel parcel) {
        if (parcel != null && !BaseBundle.isEmptyParcel(parcel)) {
            parcel.recycle();
        }
    }

    public static void setShouldDefuse(boolean bl) {
        sShouldDefuse = bl;
    }

    public void clear() {
        this.unparcel();
        this.mMap.clear();
    }

    public boolean containsKey(String string2) {
        this.unparcel();
        return this.mMap.containsKey(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void copyInternal(BaseBundle baseBundle, boolean bl) {
        synchronized (baseBundle) {
            Object object;
            ArrayMap arrayMap;
            if (baseBundle.mParcelledData != null) {
                if (baseBundle.isEmptyParcel()) {
                    this.mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
                    this.mParcelledByNative = false;
                } else {
                    this.mParcelledData = Parcel.obtain();
                    arrayMap = this.mParcelledData;
                    object = baseBundle.mParcelledData;
                    ((Parcel)((Object)arrayMap)).appendFrom((Parcel)object, 0, ((Parcel)object).dataSize());
                    this.mParcelledData.setDataPosition(0);
                    this.mParcelledByNative = baseBundle.mParcelledByNative;
                }
            } else {
                this.mParcelledData = null;
                this.mParcelledByNative = false;
            }
            object = baseBundle.mMap;
            if (object != null) {
                if (!bl) {
                    this.mMap = arrayMap = new ArrayMap(object);
                } else {
                    int n = ((ArrayMap)object).size();
                    arrayMap = new ArrayMap(n);
                    this.mMap = arrayMap;
                    for (int i = 0; i < n; ++i) {
                        this.mMap.append((String)((ArrayMap)object).keyAt(i), this.deepCopyValue(((ArrayMap)object).valueAt(i)));
                    }
                }
            } else {
                this.mMap = null;
            }
            this.mClassLoader = baseBundle.mClassLoader;
            return;
        }
    }

    Object deepCopyValue(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Bundle) {
            return ((Bundle)object).deepCopy();
        }
        if (object instanceof PersistableBundle) {
            return ((PersistableBundle)object).deepCopy();
        }
        if (object instanceof ArrayList) {
            return this.deepcopyArrayList((ArrayList)object);
        }
        if (object.getClass().isArray()) {
            if (object instanceof int[]) {
                return ((int[])object).clone();
            }
            if (object instanceof long[]) {
                return ((long[])object).clone();
            }
            if (object instanceof float[]) {
                return ((float[])object).clone();
            }
            if (object instanceof double[]) {
                return ((double[])object).clone();
            }
            if (object instanceof Object[]) {
                return ((Object[])object).clone();
            }
            if (object instanceof byte[]) {
                return ((byte[])object).clone();
            }
            if (object instanceof short[]) {
                return ((short[])object).clone();
            }
            if (object instanceof char[]) {
                return ((char[])object).clone();
            }
        }
        return object;
    }

    ArrayList deepcopyArrayList(ArrayList arrayList) {
        int n = arrayList.size();
        ArrayList<Object> arrayList2 = new ArrayList<Object>(n);
        for (int i = 0; i < n; ++i) {
            arrayList2.add(this.deepCopyValue(arrayList.get(i)));
        }
        return arrayList2;
    }

    public Object get(String string2) {
        this.unparcel();
        return this.mMap.get(string2);
    }

    public boolean getBoolean(String string2) {
        this.unparcel();
        return this.getBoolean(string2, false);
    }

    public boolean getBoolean(String string2, boolean bl) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return bl;
        }
        try {
            boolean bl2 = (Boolean)object;
            return bl2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Boolean", bl, classCastException);
            return bl;
        }
    }

    public boolean[] getBooleanArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            boolean[] arrbl = (boolean[])object;
            return arrbl;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "byte[]", classCastException);
            return null;
        }
    }

    byte getByte(String string2) {
        this.unparcel();
        return this.getByte(string2, (byte)0);
    }

    Byte getByte(String string2, byte by) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return by;
        }
        try {
            Byte by2 = (Byte)object;
            return by2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Byte", by, classCastException);
            return by;
        }
    }

    byte[] getByteArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            byte[] arrby = (byte[])object;
            return arrby;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "byte[]", classCastException);
            return null;
        }
    }

    char getChar(String string2) {
        this.unparcel();
        return this.getChar(string2, '\u0000');
    }

    char getChar(String string2, char c) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return c;
        }
        try {
            char c2 = ((Character)object).charValue();
            return c2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Character", Character.valueOf(c), classCastException);
            return c;
        }
    }

    char[] getCharArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            char[] arrc = (char[])object;
            return arrc;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "char[]", classCastException);
            return null;
        }
    }

    CharSequence getCharSequence(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        try {
            CharSequence charSequence = (CharSequence)object;
            return charSequence;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "CharSequence", classCastException);
            return null;
        }
    }

    CharSequence getCharSequence(String charSequence, CharSequence charSequence2) {
        block0 : {
            if ((charSequence = this.getCharSequence((String)charSequence)) != null) break block0;
            charSequence = charSequence2;
        }
        return charSequence;
    }

    CharSequence[] getCharSequenceArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            CharSequence[] arrcharSequence = (CharSequence[])object;
            return arrcharSequence;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "CharSequence[]", classCastException);
            return null;
        }
    }

    ArrayList<CharSequence> getCharSequenceArrayList(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "ArrayList<CharSequence>", classCastException);
            return null;
        }
    }

    ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public double getDouble(String string2) {
        this.unparcel();
        return this.getDouble(string2, 0.0);
    }

    public double getDouble(String string2, double d) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return d;
        }
        try {
            double d2 = (Double)object;
            return d2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Double", d, classCastException);
            return d;
        }
    }

    public double[] getDoubleArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            double[] arrd = (double[])object;
            return arrd;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "double[]", classCastException);
            return null;
        }
    }

    float getFloat(String string2) {
        this.unparcel();
        return this.getFloat(string2, 0.0f);
    }

    float getFloat(String string2, float f) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return f;
        }
        try {
            float f2 = ((Float)object).floatValue();
            return f2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Float", Float.valueOf(f), classCastException);
            return f;
        }
    }

    float[] getFloatArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            float[] arrf = (float[])object;
            return arrf;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "float[]", classCastException);
            return null;
        }
    }

    public int getInt(String string2) {
        this.unparcel();
        return this.getInt(string2, 0);
    }

    public int getInt(String string2, int n) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return n;
        }
        try {
            int n2 = (Integer)object;
            return n2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Integer", n, classCastException);
            return n;
        }
    }

    public int[] getIntArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            int[] arrn = (int[])object;
            return arrn;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "int[]", classCastException);
            return null;
        }
    }

    ArrayList<Integer> getIntegerArrayList(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "ArrayList<Integer>", classCastException);
            return null;
        }
    }

    public long getLong(String string2) {
        this.unparcel();
        return this.getLong(string2, 0L);
    }

    public long getLong(String string2, long l) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return l;
        }
        try {
            long l2 = (Long)object;
            return l2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Long", l, classCastException);
            return l;
        }
    }

    public long[] getLongArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            long[] arrl = (long[])object;
            return arrl;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "long[]", classCastException);
            return null;
        }
    }

    ArrayMap<String, Object> getMap() {
        this.unparcel();
        return this.mMap;
    }

    public String getPairValue() {
        this.unparcel();
        int n = this.mMap.size();
        if (n > 1) {
            Log.w(TAG, "getPairValue() used on Bundle with multiple pairs.");
        }
        if (n == 0) {
            return null;
        }
        Object object = this.mMap.valueAt(0);
        try {
            String string2 = (String)object;
            return string2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning("getPairValue()", object, "String", classCastException);
            return null;
        }
    }

    Serializable getSerializable(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            Serializable serializable = (Serializable)object;
            return serializable;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Serializable", classCastException);
            return null;
        }
    }

    short getShort(String string2) {
        this.unparcel();
        return this.getShort(string2, (short)0);
    }

    short getShort(String string2, short s) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return s;
        }
        try {
            short s2 = (Short)object;
            return s2;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "Short", s, classCastException);
            return s;
        }
    }

    short[] getShortArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            short[] arrs = (short[])object;
            return arrs;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "short[]", classCastException);
            return null;
        }
    }

    public String getString(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        try {
            String string3 = (String)object;
            return string3;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "String", classCastException);
            return null;
        }
    }

    public String getString(String string2, String string3) {
        block0 : {
            if ((string2 = this.getString(string2)) != null) break block0;
            string2 = string3;
        }
        return string2;
    }

    public String[] getStringArray(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            String[] arrstring = (String[])object;
            return arrstring;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "String[]", classCastException);
            return null;
        }
    }

    ArrayList<String> getStringArrayList(String string2) {
        this.unparcel();
        Object object = this.mMap.get(string2);
        if (object == null) {
            return null;
        }
        try {
            ArrayList arrayList = (ArrayList)object;
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            this.typeWarning(string2, object, "ArrayList<String>", classCastException);
            return null;
        }
    }

    public boolean isEmpty() {
        this.unparcel();
        return this.mMap.isEmpty();
    }

    public boolean isEmptyParcel() {
        return BaseBundle.isEmptyParcel(this.mParcelledData);
    }

    @UnsupportedAppUsage
    public boolean isParcelled() {
        boolean bl = this.mParcelledData != null;
        return bl;
    }

    public Set<String> keySet() {
        this.unparcel();
        return this.mMap.keySet();
    }

    public boolean kindofEquals(BaseBundle baseBundle) {
        boolean bl = false;
        if (baseBundle == null) {
            return false;
        }
        if (this.isParcelled() != baseBundle.isParcelled()) {
            return false;
        }
        if (this.isParcelled()) {
            if (this.mParcelledData.compareData(baseBundle.mParcelledData) == 0) {
                bl = true;
            }
            return bl;
        }
        return this.mMap.equals(baseBundle.mMap);
    }

    public boolean maybeIsEmpty() {
        if (this.isParcelled()) {
            return this.isEmptyParcel();
        }
        return this.isEmpty();
    }

    public void putAll(PersistableBundle persistableBundle) {
        this.unparcel();
        persistableBundle.unparcel();
        this.mMap.putAll(persistableBundle.mMap);
    }

    void putAll(ArrayMap arrayMap) {
        this.unparcel();
        this.mMap.putAll(arrayMap);
    }

    public void putBoolean(String string2, boolean bl) {
        this.unparcel();
        this.mMap.put(string2, bl);
    }

    public void putBooleanArray(String string2, boolean[] arrbl) {
        this.unparcel();
        this.mMap.put(string2, arrbl);
    }

    void putByte(String string2, byte by) {
        this.unparcel();
        this.mMap.put(string2, by);
    }

    void putByteArray(String string2, byte[] arrby) {
        this.unparcel();
        this.mMap.put(string2, arrby);
    }

    void putChar(String string2, char c) {
        this.unparcel();
        this.mMap.put(string2, Character.valueOf(c));
    }

    void putCharArray(String string2, char[] arrc) {
        this.unparcel();
        this.mMap.put(string2, arrc);
    }

    void putCharSequence(String string2, CharSequence charSequence) {
        this.unparcel();
        this.mMap.put(string2, charSequence);
    }

    void putCharSequenceArray(String string2, CharSequence[] arrcharSequence) {
        this.unparcel();
        this.mMap.put(string2, arrcharSequence);
    }

    void putCharSequenceArrayList(String string2, ArrayList<CharSequence> arrayList) {
        this.unparcel();
        this.mMap.put(string2, arrayList);
    }

    public void putDouble(String string2, double d) {
        this.unparcel();
        this.mMap.put(string2, d);
    }

    public void putDoubleArray(String string2, double[] arrd) {
        this.unparcel();
        this.mMap.put(string2, arrd);
    }

    void putFloat(String string2, float f) {
        this.unparcel();
        this.mMap.put(string2, Float.valueOf(f));
    }

    void putFloatArray(String string2, float[] arrf) {
        this.unparcel();
        this.mMap.put(string2, arrf);
    }

    public void putInt(String string2, int n) {
        this.unparcel();
        this.mMap.put(string2, n);
    }

    public void putIntArray(String string2, int[] arrn) {
        this.unparcel();
        this.mMap.put(string2, arrn);
    }

    void putIntegerArrayList(String string2, ArrayList<Integer> arrayList) {
        this.unparcel();
        this.mMap.put(string2, arrayList);
    }

    public void putLong(String string2, long l) {
        this.unparcel();
        this.mMap.put(string2, l);
    }

    public void putLongArray(String string2, long[] arrl) {
        this.unparcel();
        this.mMap.put(string2, arrl);
    }

    void putSerializable(String string2, Serializable serializable) {
        this.unparcel();
        this.mMap.put(string2, serializable);
    }

    void putShort(String string2, short s) {
        this.unparcel();
        this.mMap.put(string2, s);
    }

    void putShortArray(String string2, short[] arrs) {
        this.unparcel();
        this.mMap.put(string2, arrs);
    }

    public void putString(String string2, String string3) {
        this.unparcel();
        this.mMap.put(string2, string3);
    }

    public void putStringArray(String string2, String[] arrstring) {
        this.unparcel();
        this.mMap.put(string2, arrstring);
    }

    void putStringArrayList(String string2, ArrayList<String> arrayList) {
        this.unparcel();
        this.mMap.put(string2, arrayList);
    }

    void readFromParcelInner(Parcel parcel) {
        this.readFromParcelInner(parcel, parcel.readInt());
    }

    public void remove(String string2) {
        this.unparcel();
        this.mMap.remove(string2);
    }

    void setClassLoader(ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    public int size() {
        this.unparcel();
        return this.mMap.size();
    }

    void typeWarning(String string2, Object object, String string3, ClassCastException classCastException) {
        this.typeWarning(string2, object, string3, "<null>", classCastException);
    }

    void typeWarning(String string2, Object object, String string3, Object object2, ClassCastException classCastException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Key ");
        stringBuilder.append(string2);
        stringBuilder.append(" expected ");
        stringBuilder.append(string3);
        stringBuilder.append(" but value was a ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append(".  The default value ");
        stringBuilder.append(object2);
        stringBuilder.append(" was returned.");
        Log.w(TAG, stringBuilder.toString());
        Log.w(TAG, "Attempt to cast generated internal exception:", classCastException);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void unparcel() {
        synchronized (this) {
            Parcel parcel = this.mParcelledData;
            if (parcel != null) {
                this.initializeFromParcelLocked(parcel, true, this.mParcelledByNative);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void writeToParcelInner(Parcel parcel, int n) {
        if (parcel.hasReadWriteHelper()) {
            this.unparcel();
        }
        // MONITORENTER : this
        Object object = this.mParcelledData;
        n = 1279544898;
        if (object != null) {
            if (this.mParcelledData == NoImagePreloadHolder.EMPTY_PARCEL) {
                parcel.writeInt(0);
                return;
            }
            int n2 = this.mParcelledData.dataSize();
            parcel.writeInt(n2);
            if (this.mParcelledByNative) {
                n = 1279544900;
            }
            parcel.writeInt(n);
            parcel.appendFrom(this.mParcelledData, 0, n2);
            // MONITOREXIT : this
            return;
        }
        object = this.mMap;
        // MONITOREXIT : this
        if (object != null && ((ArrayMap)object).size() > 0) {
            n = parcel.dataPosition();
            parcel.writeInt(-1);
            parcel.writeInt(1279544898);
            int n3 = parcel.dataPosition();
            parcel.writeArrayMapInternal((ArrayMap<String, Object>)object);
            int n4 = parcel.dataPosition();
            parcel.setDataPosition(n);
            parcel.writeInt(n4 - n3);
            parcel.setDataPosition(n4);
            return;
        }
        parcel.writeInt(0);
    }

    static final class NoImagePreloadHolder {
        public static final Parcel EMPTY_PARCEL = Parcel.obtain();

        NoImagePreloadHolder() {
        }
    }

}

