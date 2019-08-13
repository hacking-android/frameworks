/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.protobuf.nano;

import com.android.internal.telephony.protobuf.nano.CodedInputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.CodedOutputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.internal.telephony.protobuf.nano.MessageNanoPrinter;
import java.io.IOException;
import java.util.Arrays;

public abstract class MessageNano {
    protected volatile int cachedSize = -1;

    public static final <T extends MessageNano> T mergeFrom(T t, byte[] arrby) throws InvalidProtocolBufferNanoException {
        return MessageNano.mergeFrom(t, arrby, 0, arrby.length);
    }

    public static final <T extends MessageNano> T mergeFrom(T t, byte[] object, int n, int n2) throws InvalidProtocolBufferNanoException {
        try {
            object = CodedInputByteBufferNano.newInstance((byte[])object, n, n2);
            ((MessageNano)t).mergeFrom((CodedInputByteBufferNano)object);
            ((CodedInputByteBufferNano)object).checkLastTagWas(0);
        }
        catch (IOException iOException) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
        catch (InvalidProtocolBufferNanoException invalidProtocolBufferNanoException) {
            throw invalidProtocolBufferNanoException;
        }
        return t;
    }

    public static final boolean messageNanoEquals(MessageNano messageNano, MessageNano messageNano2) {
        if (messageNano == messageNano2) {
            return true;
        }
        if (messageNano != null && messageNano2 != null) {
            if (messageNano.getClass() != messageNano2.getClass()) {
                return false;
            }
            int n = messageNano.getSerializedSize();
            if (messageNano2.getSerializedSize() != n) {
                return false;
            }
            byte[] arrby = new byte[n];
            byte[] arrby2 = new byte[n];
            MessageNano.toByteArray(messageNano, arrby, 0, n);
            MessageNano.toByteArray(messageNano2, arrby2, 0, n);
            return Arrays.equals(arrby, arrby2);
        }
        return false;
    }

    public static final void toByteArray(MessageNano messageNano, byte[] object, int n, int n2) {
        try {
            object = CodedOutputByteBufferNano.newInstance((byte[])object, n, n2);
            messageNano.writeTo((CodedOutputByteBufferNano)object);
            ((CodedOutputByteBufferNano)object).checkNoSpaceLeft();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", iOException);
        }
    }

    public static final byte[] toByteArray(MessageNano messageNano) {
        byte[] arrby = new byte[messageNano.getSerializedSize()];
        MessageNano.toByteArray(messageNano, arrby, 0, arrby.length);
        return arrby;
    }

    public MessageNano clone() throws CloneNotSupportedException {
        return (MessageNano)super.clone();
    }

    protected int computeSerializedSize() {
        return 0;
    }

    public int getCachedSize() {
        if (this.cachedSize < 0) {
            this.getSerializedSize();
        }
        return this.cachedSize;
    }

    public int getSerializedSize() {
        int n;
        this.cachedSize = n = this.computeSerializedSize();
        return n;
    }

    public abstract MessageNano mergeFrom(CodedInputByteBufferNano var1) throws IOException;

    public String toString() {
        return MessageNanoPrinter.print(this);
    }

    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
    }
}

