/*
 * Decompiled with CFR 0.145.
 */
package android.stats.devicepolicy.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class StringList
extends MessageNano {
    private static volatile StringList[] _emptyArray;
    public String[] stringValue;

    public StringList() {
        this.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static StringList[] emptyArray() {
        if (_emptyArray != null) return _emptyArray;
        Object object = InternalNano.LAZY_INIT_LOCK;
        synchronized (object) {
            if (_emptyArray != null) return _emptyArray;
            _emptyArray = new StringList[0];
            return _emptyArray;
        }
    }

    public static StringList parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new StringList().mergeFrom(codedInputByteBufferNano);
    }

    public static StringList parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
        return MessageNano.mergeFrom(new StringList(), arrby);
    }

    public StringList clear() {
        this.stringValue = WireFormatNano.EMPTY_STRING_ARRAY;
        this.cachedSize = -1;
        return this;
    }

    @Override
    protected int computeSerializedSize() {
        int n = super.computeSerializedSize();
        Object object = this.stringValue;
        int n2 = n;
        if (object != null) {
            n2 = n;
            if (((String[])object).length > 0) {
                int n3 = 0;
                int n4 = 0;
                for (n2 = 0; n2 < ((String[])(object = this.stringValue)).length; ++n2) {
                    object = object[n2];
                    int n5 = n3;
                    int n6 = n4;
                    if (object != null) {
                        n5 = n3 + 1;
                        n6 = n4 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                    }
                    n3 = n5;
                    n4 = n6;
                }
                n2 = n + n4 + n3 * 1;
            }
        }
        return n2;
    }

    @Override
    public StringList mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        int n;
        while ((n = codedInputByteBufferNano.readTag()) != 0) {
            if (n != 10) {
                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                return this;
            }
            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
            String[] arrstring = this.stringValue;
            n = arrstring == null ? 0 : arrstring.length;
            arrstring = new String[n + n2];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.stringValue, 0, arrstring, 0, n);
                n2 = n;
            }
            while (n2 < arrstring.length - 1) {
                arrstring[n2] = codedInputByteBufferNano.readString();
                codedInputByteBufferNano.readTag();
                ++n2;
            }
            arrstring[n2] = codedInputByteBufferNano.readString();
            this.stringValue = arrstring;
        }
        return this;
    }

    @Override
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        Object object = this.stringValue;
        if (object != null && ((String[])object).length > 0) {
            for (int i = 0; i < ((String[])(object = this.stringValue)).length; ++i) {
                if ((object = object[i]) == null) continue;
                codedOutputByteBufferNano.writeString(1, (String)object);
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}

