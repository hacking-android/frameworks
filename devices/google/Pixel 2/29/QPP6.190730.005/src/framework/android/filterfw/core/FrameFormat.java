/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.MutableFrameFormat;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrameFormat {
    public static final int BYTES_PER_SAMPLE_UNSPECIFIED = 1;
    protected static final int SIZE_UNKNOWN = -1;
    public static final int SIZE_UNSPECIFIED = 0;
    public static final int TARGET_GPU = 3;
    public static final int TARGET_NATIVE = 2;
    public static final int TARGET_RS = 5;
    public static final int TARGET_SIMPLE = 1;
    public static final int TARGET_UNSPECIFIED = 0;
    public static final int TARGET_VERTEXBUFFER = 4;
    public static final int TYPE_BIT = 1;
    public static final int TYPE_BYTE = 2;
    public static final int TYPE_DOUBLE = 6;
    public static final int TYPE_FLOAT = 5;
    public static final int TYPE_INT16 = 3;
    public static final int TYPE_INT32 = 4;
    public static final int TYPE_OBJECT = 8;
    public static final int TYPE_POINTER = 7;
    public static final int TYPE_UNSPECIFIED = 0;
    protected int mBaseType = 0;
    protected int mBytesPerSample = 1;
    protected int[] mDimensions;
    protected KeyValueMap mMetaData;
    protected Class mObjectClass;
    protected int mSize = -1;
    protected int mTarget = 0;

    protected FrameFormat() {
    }

    public FrameFormat(int n, int n2) {
        this.mBaseType = n;
        this.mTarget = n2;
        this.initDefaults();
    }

    public static String baseTypeToString(int n) {
        switch (n) {
            default: {
                return "unknown";
            }
            case 8: {
                return "object";
            }
            case 7: {
                return "pointer";
            }
            case 6: {
                return "double";
            }
            case 5: {
                return "float";
            }
            case 4: {
                return "int";
            }
            case 3: {
                return "int";
            }
            case 2: {
                return "byte";
            }
            case 1: {
                return "bit";
            }
            case 0: 
        }
        return "unspecified";
    }

    public static int bytesPerSampleOf(int n) {
        switch (n) {
            default: {
                return 1;
            }
            case 6: {
                return 8;
            }
            case 4: 
            case 5: 
            case 7: {
                return 4;
            }
            case 3: {
                return 2;
            }
            case 1: 
            case 2: 
        }
        return 1;
    }

    public static String dimensionsToString(int[] arrn) {
        StringBuffer stringBuffer = new StringBuffer();
        if (arrn != null) {
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                if (arrn[i] == 0) {
                    stringBuffer.append("[]");
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(String.valueOf(arrn[i]));
                stringBuilder.append("]");
                stringBuffer.append(stringBuilder.toString());
            }
        }
        return stringBuffer.toString();
    }

    private void initDefaults() {
        this.mBytesPerSample = FrameFormat.bytesPerSampleOf(this.mBaseType);
    }

    public static String metaDataToString(KeyValueMap object2) {
        if (object2 == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{ ");
        for (Map.Entry entry : ((HashMap)object2).entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(" ");
            stringBuffer.append(stringBuilder.toString());
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static int readTargetString(String string2) {
        if (!string2.equalsIgnoreCase("CPU") && !string2.equalsIgnoreCase("NATIVE")) {
            if (string2.equalsIgnoreCase("GPU")) {
                return 3;
            }
            if (string2.equalsIgnoreCase("SIMPLE")) {
                return 1;
            }
            if (string2.equalsIgnoreCase("VERTEXBUFFER")) {
                return 4;
            }
            if (string2.equalsIgnoreCase("UNSPECIFIED")) {
                return 0;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown target type '");
            stringBuilder.append(string2);
            stringBuilder.append("'!");
            throw new RuntimeException(stringBuilder.toString());
        }
        return 2;
    }

    public static String targetToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return "unknown";
                            }
                            return "renderscript";
                        }
                        return "vbo";
                    }
                    return "gpu";
                }
                return "native";
            }
            return "simple";
        }
        return "unspecified";
    }

    public static FrameFormat unspecified() {
        return new FrameFormat(0, 0);
    }

    int calcSize(int[] arrn) {
        if (arrn != null && arrn.length > 0) {
            int n = this.getBytesPerSample();
            int n2 = arrn.length;
            for (int i = 0; i < n2; ++i) {
                n *= arrn[i];
            }
            return n;
        }
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof FrameFormat)) {
            return false;
        }
        object = (FrameFormat)object;
        if (((FrameFormat)object).mBaseType != this.mBaseType || ((FrameFormat)object).mTarget != this.mTarget || ((FrameFormat)object).mBytesPerSample != this.mBytesPerSample || !Arrays.equals(((FrameFormat)object).mDimensions, this.mDimensions) || !((FrameFormat)object).mMetaData.equals(this.mMetaData)) {
            bl = false;
        }
        return bl;
    }

    public int getBaseType() {
        return this.mBaseType;
    }

    public int getBytesPerSample() {
        return this.mBytesPerSample;
    }

    public int getDepth() {
        int[] arrn = this.mDimensions;
        int n = arrn != null && arrn.length >= 3 ? arrn[2] : -1;
        return n;
    }

    public int getDimension(int n) {
        return this.mDimensions[n];
    }

    public int getDimensionCount() {
        int[] arrn = this.mDimensions;
        int n = arrn == null ? 0 : arrn.length;
        return n;
    }

    public int[] getDimensions() {
        return this.mDimensions;
    }

    @UnsupportedAppUsage
    public int getHeight() {
        int[] arrn = this.mDimensions;
        int n = arrn != null && arrn.length >= 2 ? arrn[1] : -1;
        return n;
    }

    public int getLength() {
        int[] arrn = this.mDimensions;
        int n = arrn != null && arrn.length >= 1 ? arrn[0] : -1;
        return n;
    }

    public Object getMetaValue(String string2) {
        KeyValueMap keyValueMap = this.mMetaData;
        string2 = keyValueMap != null ? keyValueMap.get(string2) : null;
        return string2;
    }

    public int getNumberOfDimensions() {
        int[] arrn = this.mDimensions;
        int n = arrn != null ? arrn.length : 0;
        return n;
    }

    public Class getObjectClass() {
        return this.mObjectClass;
    }

    public int getSize() {
        if (this.mSize == -1) {
            this.mSize = this.calcSize(this.mDimensions);
        }
        return this.mSize;
    }

    @UnsupportedAppUsage
    public int getTarget() {
        return this.mTarget;
    }

    public int getValuesPerSample() {
        return this.mBytesPerSample / FrameFormat.bytesPerSampleOf(this.mBaseType);
    }

    @UnsupportedAppUsage
    public int getWidth() {
        return this.getLength();
    }

    public boolean hasMetaKey(String string2) {
        KeyValueMap keyValueMap = this.mMetaData;
        boolean bl = keyValueMap != null ? keyValueMap.containsKey(string2) : false;
        return bl;
    }

    public boolean hasMetaKey(String string2, Class class_) {
        Serializable serializable = this.mMetaData;
        if (serializable != null && ((HashMap)serializable).containsKey(string2)) {
            if (class_.isAssignableFrom(this.mMetaData.get(string2).getClass())) {
                return true;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("FrameFormat meta-key '");
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append("' is of type ");
            ((StringBuilder)serializable).append(this.mMetaData.get(string2).getClass());
            ((StringBuilder)serializable).append(" but expected to be of type ");
            ((StringBuilder)serializable).append(class_);
            ((StringBuilder)serializable).append("!");
            throw new RuntimeException(((StringBuilder)serializable).toString());
        }
        return false;
    }

    public int hashCode() {
        return this.mBaseType ^ 4211 ^ this.mBytesPerSample ^ this.getSize();
    }

    public boolean isBinaryDataType() {
        int n = this.mBaseType;
        boolean bl = true;
        if (n < 1 || n > 6) {
            bl = false;
        }
        return bl;
    }

    public boolean isCompatibleWith(FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && this.getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && this.getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && this.getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && this.getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); ++i) {
            int n = frameFormat.getDimension(i);
            if (n == 0 || this.getDimension(i) == n) continue;
            return false;
        }
        if (!(frameFormat.getObjectClass() == null || this.getObjectClass() != null && frameFormat.getObjectClass().isAssignableFrom(this.getObjectClass()))) {
            return false;
        }
        KeyValueMap keyValueMap = frameFormat.mMetaData;
        if (keyValueMap != null) {
            for (String string2 : keyValueMap.keySet()) {
                KeyValueMap keyValueMap2 = this.mMetaData;
                if (keyValueMap2 != null && keyValueMap2.containsKey(string2) && this.mMetaData.get(string2).equals(frameFormat.mMetaData.get(string2))) continue;
                return false;
            }
        }
        return true;
    }

    boolean isReplaceableBy(FrameFormat frameFormat) {
        boolean bl = this.mTarget == frameFormat.mTarget && this.getSize() == frameFormat.getSize() && Arrays.equals(frameFormat.mDimensions, this.mDimensions);
        return bl;
    }

    public boolean mayBeCompatibleWith(FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && this.getBaseType() != 0 && this.getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && this.getTarget() != 0 && this.getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && this.getBytesPerSample() != 1 && this.getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && this.getDimensionCount() > 0 && this.getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); ++i) {
            int n = frameFormat.getDimension(i);
            if (n == 0 || this.getDimension(i) == 0 || this.getDimension(i) == n) continue;
            return false;
        }
        if (frameFormat.getObjectClass() != null && this.getObjectClass() != null && !frameFormat.getObjectClass().isAssignableFrom(this.getObjectClass())) {
            return false;
        }
        KeyValueMap keyValueMap = frameFormat.mMetaData;
        if (keyValueMap != null && this.mMetaData != null) {
            for (String string2 : keyValueMap.keySet()) {
                if (!this.mMetaData.containsKey(string2) || this.mMetaData.get(string2).equals(frameFormat.mMetaData.get(string2))) continue;
                return false;
            }
        }
        return true;
    }

    @UnsupportedAppUsage
    public MutableFrameFormat mutableCopy() {
        MutableFrameFormat mutableFrameFormat = new MutableFrameFormat();
        mutableFrameFormat.setBaseType(this.getBaseType());
        mutableFrameFormat.setTarget(this.getTarget());
        mutableFrameFormat.setBytesPerSample(this.getBytesPerSample());
        mutableFrameFormat.setDimensions(this.getDimensions());
        mutableFrameFormat.setObjectClass(this.getObjectClass());
        KeyValueMap keyValueMap = this.mMetaData;
        keyValueMap = keyValueMap == null ? null : (KeyValueMap)keyValueMap.clone();
        mutableFrameFormat.mMetaData = keyValueMap;
        return mutableFrameFormat;
    }

    public String toString() {
        CharSequence charSequence;
        int n = this.getValuesPerSample();
        CharSequence charSequence2 = "";
        String string2 = n == 1 ? "" : String.valueOf(n);
        if (this.mTarget == 0) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(FrameFormat.targetToString(this.mTarget));
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        if (this.mObjectClass != null) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(" class(");
            ((StringBuilder)charSequence2).append(this.mObjectClass.getSimpleName());
            ((StringBuilder)charSequence2).append(") ");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(FrameFormat.baseTypeToString(this.mBaseType));
        stringBuilder.append(string2);
        stringBuilder.append(FrameFormat.dimensionsToString(this.mDimensions));
        stringBuilder.append((String)charSequence2);
        stringBuilder.append(FrameFormat.metaDataToString(this.mMetaData));
        return stringBuilder.toString();
    }
}

