/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano;

import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import java.io.IOException;
import java.util.Arrays;

final class UnknownFieldData {
    final byte[] bytes;
    final int tag;

    UnknownFieldData(int n, byte[] arrby) {
        this.tag = n;
        this.bytes = arrby;
    }

    int computeSerializedSize() {
        return 0 + CodedOutputByteBufferNano.computeRawVarint32Size(this.tag) + this.bytes.length;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof UnknownFieldData)) {
            return false;
        }
        object = (UnknownFieldData)object;
        if (this.tag != ((UnknownFieldData)object).tag || !Arrays.equals(this.bytes, ((UnknownFieldData)object).bytes)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return (17 * 31 + this.tag) * 31 + Arrays.hashCode(this.bytes);
    }

    void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        codedOutputByteBufferNano.writeRawVarint32(this.tag);
        codedOutputByteBufferNano.writeRawBytes(this.bytes);
    }
}

