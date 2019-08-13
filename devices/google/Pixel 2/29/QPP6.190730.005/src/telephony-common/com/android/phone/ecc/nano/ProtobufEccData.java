/*
 * Decompiled with CFR 0.145.
 */
package com.android.phone.ecc.nano;

import com.android.phone.ecc.nano.CodedInputByteBufferNano;
import com.android.phone.ecc.nano.CodedOutputByteBufferNano;
import com.android.phone.ecc.nano.ExtendableMessageNano;
import com.android.phone.ecc.nano.FieldArray;
import com.android.phone.ecc.nano.InternalNano;
import com.android.phone.ecc.nano.InvalidProtocolBufferNanoException;
import com.android.phone.ecc.nano.MessageNano;
import com.android.phone.ecc.nano.WireFormatNano;
import java.io.IOException;

public interface ProtobufEccData {

    public static final class AllInfo
    extends ExtendableMessageNano<AllInfo> {
        private static volatile AllInfo[] _emptyArray;
        public CountryInfo[] countries;
        public int revision;

        public AllInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static AllInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new AllInfo[0];
                return _emptyArray;
            }
        }

        public static AllInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new AllInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static AllInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new AllInfo(), arrby);
        }

        public AllInfo clear() {
            this.revision = 0;
            this.countries = CountryInfo.emptyArray();
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.revision;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.countries;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((CountryInfo[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.countries;
                        n2 = n3;
                        if (n >= ((CountryInfo[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            return n2;
        }

        @Override
        public AllInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    CountryInfo[] arrcountryInfo = this.countries;
                    n = arrcountryInfo == null ? 0 : arrcountryInfo.length;
                    arrcountryInfo = new CountryInfo[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.countries, 0, arrcountryInfo, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrcountryInfo.length - 1) {
                        arrcountryInfo[n2] = new CountryInfo();
                        codedInputByteBufferNano.readMessage(arrcountryInfo[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrcountryInfo[n2] = new CountryInfo();
                    codedInputByteBufferNano.readMessage(arrcountryInfo[n2]);
                    this.countries = arrcountryInfo;
                    continue;
                }
                this.revision = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.revision;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.countries) != null && ((CountryInfo[])object).length > 0) {
                for (n = 0; n < ((CountryInfo[])(object = this.countries)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class CountryInfo
    extends ExtendableMessageNano<CountryInfo> {
        private static volatile CountryInfo[] _emptyArray;
        public String eccFallback;
        public EccInfo[] eccs;
        public String isoCode;

        public CountryInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static CountryInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new CountryInfo[0];
                return _emptyArray;
            }
        }

        public static CountryInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new CountryInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static CountryInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new CountryInfo(), arrby);
        }

        public CountryInfo clear() {
            this.isoCode = "";
            this.eccs = EccInfo.emptyArray();
            this.eccFallback = "";
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.isoCode.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.isoCode);
            }
            Object object = this.eccs;
            n = n2;
            if (object != null) {
                n = n2;
                if (((EccInfo[])object).length > 0) {
                    int n3 = 0;
                    do {
                        object = this.eccs;
                        n = n2;
                        if (n3 >= ((EccInfo[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            n2 = n;
            if (!this.eccFallback.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(3, this.eccFallback);
            }
            return n2;
        }

        @Override
        public CountryInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 18) {
                        if (n != 26) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.eccFallback = codedInputByteBufferNano.readString();
                        continue;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    EccInfo[] arreccInfo = this.eccs;
                    n = arreccInfo == null ? 0 : arreccInfo.length;
                    arreccInfo = new EccInfo[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.eccs, 0, arreccInfo, 0, n);
                        n2 = n;
                    }
                    while (n2 < arreccInfo.length - 1) {
                        arreccInfo[n2] = new EccInfo();
                        codedInputByteBufferNano.readMessage(arreccInfo[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arreccInfo[n2] = new EccInfo();
                    codedInputByteBufferNano.readMessage(arreccInfo[n2]);
                    this.eccs = arreccInfo;
                    continue;
                }
                this.isoCode = codedInputByteBufferNano.readString();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            if (!this.isoCode.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.isoCode);
            }
            if ((object = this.eccs) != null && ((EccInfo[])object).length > 0) {
                for (int i = 0; i < ((EccInfo[])(object = this.eccs)).length; ++i) {
                    if ((object = object[i]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if (!this.eccFallback.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.eccFallback);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EccInfo
    extends ExtendableMessageNano<EccInfo> {
        private static volatile EccInfo[] _emptyArray;
        public String phoneNumber;
        public int[] types;

        public EccInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static EccInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new EccInfo[0];
                return _emptyArray;
            }
        }

        public static EccInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EccInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static EccInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new EccInfo(), arrby);
        }

        public EccInfo clear() {
            this.phoneNumber = "";
            this.types = WireFormatNano.EMPTY_INT_ARRAY;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.phoneNumber.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.phoneNumber);
            }
            int[] arrn = this.types;
            n = n2;
            if (arrn != null) {
                n = n2;
                if (arrn.length > 0) {
                    int n3 = 0;
                    for (n = 0; n < (arrn = this.types).length; ++n) {
                        int n4 = arrn[n];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n4);
                    }
                    n = n2 + n3 + 1 + CodedOutputByteBufferNano.computeRawVarint32Size(n3);
                }
            }
            return n;
        }

        @Override
        public EccInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    int n2;
                    int n3;
                    int n4;
                    int[] arrn;
                    if (n != 16) {
                        if (n != 18) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        n = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        n4 = 0;
                        n3 = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) continue;
                            ++n4;
                        }
                        if (n4 != 0) {
                            codedInputByteBufferNano.rewindToPosition(n3);
                            arrn = this.types;
                            n3 = arrn == null ? 0 : arrn.length;
                            arrn = new int[n3 + n4];
                            n4 = n3;
                            if (n3 != 0) {
                                System.arraycopy(this.types, 0, arrn, 0, n3);
                                n4 = n3;
                            }
                            while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                n2 = codedInputByteBufferNano.getPosition();
                                n3 = codedInputByteBufferNano.readInt32();
                                if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3) {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, 16);
                                    continue;
                                }
                                arrn[n4] = n3;
                                ++n4;
                            }
                            this.types = arrn;
                        }
                        codedInputByteBufferNano.popLimit(n);
                        continue;
                    }
                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 16);
                    arrn = new int[n2];
                    n3 = 0;
                    for (n4 = 0; n4 < n2; ++n4) {
                        if (n4 != 0) {
                            codedInputByteBufferNano.readTag();
                        }
                        int n5 = codedInputByteBufferNano.getPosition();
                        int n6 = codedInputByteBufferNano.readInt32();
                        if (n6 != 0 && n6 != 1 && n6 != 2 && n6 != 3) {
                            codedInputByteBufferNano.rewindToPosition(n5);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        arrn[n3] = n6;
                        ++n3;
                    }
                    if (n3 == 0) continue;
                    int[] arrn2 = this.types;
                    n4 = arrn2 == null ? 0 : arrn2.length;
                    if (n4 == 0 && n3 == arrn.length) {
                        this.types = arrn;
                        continue;
                    }
                    arrn2 = new int[n4 + n3];
                    if (n4 != 0) {
                        System.arraycopy(this.types, 0, arrn2, 0, n4);
                    }
                    System.arraycopy(arrn, 0, arrn2, n4, n3);
                    this.types = arrn2;
                    continue;
                }
                this.phoneNumber = codedInputByteBufferNano.readString();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int[] arrn;
            if (!this.phoneNumber.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.phoneNumber);
            }
            if ((arrn = this.types) != null && arrn.length > 0) {
                int n;
                int n2 = 0;
                for (n = 0; n < (arrn = this.types).length; ++n) {
                    int n3 = arrn[n];
                    n2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n3);
                }
                codedOutputByteBufferNano.writeRawVarint32(18);
                codedOutputByteBufferNano.writeRawVarint32(n2);
                for (n = 0; n < (arrn = this.types).length; ++n) {
                    codedOutputByteBufferNano.writeRawVarint32(arrn[n]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface Type {
            public static final int AMBULANCE = 2;
            public static final int FIRE = 3;
            public static final int POLICE = 1;
            public static final int TYPE_UNSPECIFIED = 0;
        }

    }

}

