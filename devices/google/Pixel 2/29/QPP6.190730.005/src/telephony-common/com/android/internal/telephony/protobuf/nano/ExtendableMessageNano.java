/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.CodedInputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.CodedOutputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.Extension;
import com.android.internal.telephony.protobuf.nano.FieldArray;
import com.android.internal.telephony.protobuf.nano.FieldData;
import com.android.internal.telephony.protobuf.nano.InternalNano;
import com.android.internal.telephony.protobuf.nano.MessageNano;
import com.android.internal.telephony.protobuf.nano.UnknownFieldData;
import com.android.internal.telephony.protobuf.nano.WireFormatNano;
import java.io.IOException;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>>
extends MessageNano {
    protected FieldArray unknownFieldData;

    public M clone() throws CloneNotSupportedException {
        ExtendableMessageNano extendableMessageNano = (ExtendableMessageNano)super.clone();
        InternalNano.cloneUnknownFieldData(this, extendableMessageNano);
        return (M)extendableMessageNano;
    }

    @Override
    protected int computeSerializedSize() {
        int n = 0;
        int n2 = 0;
        if (this.unknownFieldData != null) {
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= this.unknownFieldData.size()) break;
                n2 += this.unknownFieldData.dataAt(n3).computeSerializedSize();
                ++n3;
            } while (true);
        }
        return n;
    }

    public final <T> T getExtension(Extension<M, T> extension) {
        Cloneable cloneable = this.unknownFieldData;
        Object var3_3 = null;
        if (cloneable == null) {
            return null;
        }
        extension = (cloneable = ((FieldArray)cloneable).get(WireFormatNano.getTagFieldNumber(extension.tag))) == null ? var3_3 : ((FieldData)cloneable).getValue(extension);
        return (T)extension;
    }

    public final boolean hasExtension(Extension<M, ?> extension) {
        FieldArray fieldArray = this.unknownFieldData;
        boolean bl = false;
        if (fieldArray == null) {
            return false;
        }
        if (fieldArray.get(WireFormatNano.getTagFieldNumber(extension.tag)) != null) {
            bl = true;
        }
        return bl;
    }

    public final <T> M setExtension(Extension<M, T> object, T t) {
        int n = WireFormatNano.getTagFieldNumber(((Extension)object).tag);
        if (t == null) {
            object = this.unknownFieldData;
            if (object != null) {
                ((FieldArray)object).remove(n);
                if (this.unknownFieldData.isEmpty()) {
                    this.unknownFieldData = null;
                }
            }
        } else {
            FieldData fieldData = null;
            FieldArray fieldArray = this.unknownFieldData;
            if (fieldArray == null) {
                this.unknownFieldData = new FieldArray();
            } else {
                fieldData = fieldArray.get(n);
            }
            if (fieldData == null) {
                this.unknownFieldData.put(n, new FieldData(object, t));
            } else {
                fieldData.setValue(object, t);
            }
        }
        return (M)this;
    }

    protected final boolean storeUnknownField(CodedInputByteBufferNano object, int n) throws IOException {
        int n2 = ((CodedInputByteBufferNano)object).getPosition();
        if (!((CodedInputByteBufferNano)object).skipField(n)) {
            return false;
        }
        int n3 = WireFormatNano.getTagFieldNumber(n);
        UnknownFieldData unknownFieldData = new UnknownFieldData(n, ((CodedInputByteBufferNano)object).getData(n2, ((CodedInputByteBufferNano)object).getPosition() - n2));
        object = null;
        Object object2 = this.unknownFieldData;
        if (object2 == null) {
            this.unknownFieldData = new FieldArray();
        } else {
            object = ((FieldArray)object2).get(n3);
        }
        object2 = object;
        if (object == null) {
            object2 = new FieldData();
            this.unknownFieldData.put(n3, (FieldData)object2);
        }
        ((FieldData)object2).addUnknownField(unknownFieldData);
        return true;
    }

    @Override
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if (this.unknownFieldData == null) {
            return;
        }
        for (int i = 0; i < this.unknownFieldData.size(); ++i) {
            this.unknownFieldData.dataAt(i).writeTo(codedOutputByteBufferNano);
        }
    }
}

