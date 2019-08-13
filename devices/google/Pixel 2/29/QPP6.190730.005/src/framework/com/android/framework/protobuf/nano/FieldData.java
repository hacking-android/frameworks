/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano;

import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.Extension;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.UnknownFieldData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class FieldData
implements Cloneable {
    private Extension<?, ?> cachedExtension;
    private List<UnknownFieldData> unknownFieldData;
    private Object value;

    FieldData() {
        this.unknownFieldData = new ArrayList<UnknownFieldData>();
    }

    <T> FieldData(Extension<?, T> extension, T t) {
        this.cachedExtension = extension;
        this.value = t;
    }

    private byte[] toByteArray() throws IOException {
        byte[] arrby = new byte[this.computeSerializedSize()];
        this.writeTo(CodedOutputByteBufferNano.newInstance(arrby));
        return arrby;
    }

    void addUnknownField(UnknownFieldData unknownFieldData) {
        this.unknownFieldData.add(unknownFieldData);
    }

    public final FieldData clone() {
        FieldData fieldData;
        block16 : {
            int n;
            int n2;
            MessageNano[] arrmessageNano;
            block17 : {
                byte[][] arrarrby;
                fieldData = new FieldData();
                fieldData.cachedExtension = this.cachedExtension;
                if (this.unknownFieldData == null) {
                    fieldData.unknownFieldData = null;
                } else {
                    fieldData.unknownFieldData.addAll(this.unknownFieldData);
                }
                if (this.value == null) break block16;
                if (this.value instanceof MessageNano) {
                    fieldData.value = ((MessageNano)this.value).clone();
                    break block16;
                }
                if (this.value instanceof byte[]) {
                    fieldData.value = ((byte[])this.value).clone();
                    break block16;
                }
                boolean bl = this.value instanceof byte[][];
                n2 = 0;
                if (!bl) break block17;
                byte[][] arrby = (byte[][])this.value;
                fieldData.value = arrarrby = new byte[arrby.length][];
                for (n = 0; n < arrby.length; ++n) {
                    arrarrby[n] = (byte[])arrby[n].clone();
                }
                break block16;
            }
            try {
                MessageNano[] arrmessageNano2;
                if (this.value instanceof boolean[]) {
                    fieldData.value = ((boolean[])this.value).clone();
                    break block16;
                }
                if (this.value instanceof int[]) {
                    fieldData.value = ((int[])this.value).clone();
                    break block16;
                }
                if (this.value instanceof long[]) {
                    fieldData.value = ((long[])this.value).clone();
                    break block16;
                }
                if (this.value instanceof float[]) {
                    fieldData.value = ((float[])this.value).clone();
                    break block16;
                }
                if (this.value instanceof double[]) {
                    fieldData.value = ((double[])this.value).clone();
                    break block16;
                }
                if (!(this.value instanceof MessageNano[])) break block16;
                arrmessageNano = (MessageNano[])this.value;
                fieldData.value = arrmessageNano2 = new MessageNano[arrmessageNano.length];
                n = n2;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new AssertionError(cloneNotSupportedException);
            }
            do {
                if (n >= arrmessageNano.length) break;
                arrmessageNano2[n] = arrmessageNano[n].clone();
                ++n;
                continue;
                break;
            } while (true);
        }
        return fieldData;
    }

    int computeSerializedSize() {
        int n;
        int n2 = 0;
        Iterator<UnknownFieldData> iterator = this.value;
        if (iterator != null) {
            n = this.cachedExtension.computeSerializedSize(iterator);
        } else {
            iterator = this.unknownFieldData.iterator();
            do {
                n = n2;
                if (!iterator.hasNext()) break;
                n2 += iterator.next().computeSerializedSize();
            } while (true);
        }
        return n;
    }

    public boolean equals(Object object) {
        List<UnknownFieldData> list;
        if (object == this) {
            return true;
        }
        if (!(object instanceof FieldData)) {
            return false;
        }
        object = (FieldData)object;
        if (this.value != null && ((FieldData)object).value != null) {
            Object object2 = this.cachedExtension;
            if (object2 != ((FieldData)object).cachedExtension) {
                return false;
            }
            if (!((Extension)object2).clazz.isArray()) {
                return this.value.equals(((FieldData)object).value);
            }
            object2 = this.value;
            if (object2 instanceof byte[]) {
                return Arrays.equals((byte[])object2, (byte[])((FieldData)object).value);
            }
            if (object2 instanceof int[]) {
                return Arrays.equals((int[])object2, (int[])((FieldData)object).value);
            }
            if (object2 instanceof long[]) {
                return Arrays.equals((long[])object2, (long[])((FieldData)object).value);
            }
            if (object2 instanceof float[]) {
                return Arrays.equals((float[])object2, (float[])((FieldData)object).value);
            }
            if (object2 instanceof double[]) {
                return Arrays.equals((double[])object2, (double[])((FieldData)object).value);
            }
            if (object2 instanceof boolean[]) {
                return Arrays.equals((boolean[])object2, (boolean[])((FieldData)object).value);
            }
            return Arrays.deepEquals((Object[])object2, (Object[])((FieldData)object).value);
        }
        List<UnknownFieldData> list2 = this.unknownFieldData;
        if (list2 != null && (list = ((FieldData)object).unknownFieldData) != null) {
            return list2.equals(list);
        }
        try {
            boolean bl = Arrays.equals(this.toByteArray(), FieldData.super.toByteArray());
            return bl;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    UnknownFieldData getUnknownField(int n) {
        List<UnknownFieldData> list = this.unknownFieldData;
        if (list == null) {
            return null;
        }
        if (n < list.size()) {
            return this.unknownFieldData.get(n);
        }
        return null;
    }

    int getUnknownFieldSize() {
        List<UnknownFieldData> list = this.unknownFieldData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    <T> T getValue(Extension<?, T> extension) {
        if (this.value != null) {
            if (this.cachedExtension != extension) {
                throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
            }
        } else {
            this.cachedExtension = extension;
            this.value = extension.getValueFrom(this.unknownFieldData);
            this.unknownFieldData = null;
        }
        return (T)this.value;
    }

    public int hashCode() {
        try {
            int n = Arrays.hashCode(this.toByteArray());
            return 17 * 31 + n;
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException);
        }
    }

    <T> void setValue(Extension<?, T> extension, T t) {
        this.cachedExtension = extension;
        this.value = t;
        this.unknownFieldData = null;
    }

    void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        Iterator<UnknownFieldData> iterator = this.value;
        if (iterator != null) {
            this.cachedExtension.writeTo(iterator, codedOutputByteBufferNano);
        } else {
            iterator = this.unknownFieldData.iterator();
            while (iterator.hasNext()) {
                iterator.next().writeTo(codedOutputByteBufferNano);
            }
        }
    }
}

