/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.nano;

import com.android.internal.telephony.protobuf.nano.CodedInputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.CodedOutputByteBufferNano;
import com.android.internal.telephony.protobuf.nano.ExtendableMessageNano;
import com.android.internal.telephony.protobuf.nano.FieldArray;
import com.android.internal.telephony.protobuf.nano.InternalNano;
import com.android.internal.telephony.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.internal.telephony.protobuf.nano.MessageNano;
import com.android.internal.telephony.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface CarrierIdProto {

    public static final class CarrierAttribute
    extends ExtendableMessageNano<CarrierAttribute> {
        private static volatile CarrierAttribute[] _emptyArray;
        public String[] gid1;
        public String[] gid2;
        public String[] iccidPrefix;
        public String[] imsiPrefixXpattern;
        public String[] mccmncTuple;
        public String[] plmn;
        public String[] preferredApn;
        public String[] privilegeAccessRule;
        public String[] spn;

        public CarrierAttribute() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static CarrierAttribute[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new CarrierAttribute[0];
                return _emptyArray;
            }
        }

        public static CarrierAttribute parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new CarrierAttribute().mergeFrom(codedInputByteBufferNano);
        }

        public static CarrierAttribute parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new CarrierAttribute(), arrby);
        }

        public CarrierAttribute clear() {
            this.mccmncTuple = WireFormatNano.EMPTY_STRING_ARRAY;
            this.imsiPrefixXpattern = WireFormatNano.EMPTY_STRING_ARRAY;
            this.spn = WireFormatNano.EMPTY_STRING_ARRAY;
            this.plmn = WireFormatNano.EMPTY_STRING_ARRAY;
            this.gid1 = WireFormatNano.EMPTY_STRING_ARRAY;
            this.gid2 = WireFormatNano.EMPTY_STRING_ARRAY;
            this.preferredApn = WireFormatNano.EMPTY_STRING_ARRAY;
            this.iccidPrefix = WireFormatNano.EMPTY_STRING_ARRAY;
            this.privilegeAccessRule = WireFormatNano.EMPTY_STRING_ARRAY;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2;
            int n3;
            int n4;
            int n5 = super.computeSerializedSize();
            Object object = this.mccmncTuple;
            int n6 = n5;
            if (object != null) {
                n6 = n5;
                if (((String[])object).length > 0) {
                    n2 = 0;
                    n6 = 0;
                    for (n = 0; n < ((String[])(object = this.mccmncTuple)).length; ++n) {
                        object = object[n];
                        n4 = n2;
                        n3 = n6;
                        if (object != null) {
                            n4 = n2 + 1;
                            n3 = n6 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n2 = n4;
                        n6 = n3;
                    }
                    n6 = n5 + n6 + n2 * 1;
                }
            }
            object = this.imsiPrefixXpattern;
            n = n6;
            if (object != null) {
                n = n6;
                if (((String[])object).length > 0) {
                    n5 = 0;
                    n3 = 0;
                    for (n = 0; n < ((String[])(object = this.imsiPrefixXpattern)).length; ++n) {
                        object = object[n];
                        n2 = n5;
                        n4 = n3;
                        if (object != null) {
                            n2 = n5 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n5 = n2;
                        n3 = n4;
                    }
                    n = n6 + n3 + n5 * 1;
                }
            }
            object = this.spn;
            n6 = n;
            if (object != null) {
                n6 = n;
                if (((String[])object).length > 0) {
                    n2 = 0;
                    n3 = 0;
                    for (n6 = 0; n6 < ((String[])(object = this.spn)).length; ++n6) {
                        object = object[n6];
                        n5 = n2;
                        n4 = n3;
                        if (object != null) {
                            n5 = n2 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n2 = n5;
                        n3 = n4;
                    }
                    n6 = n + n3 + n2 * 1;
                }
            }
            object = this.plmn;
            n = n6;
            if (object != null) {
                n = n6;
                if (((String[])object).length > 0) {
                    n2 = 0;
                    n3 = 0;
                    for (n = 0; n < ((String[])(object = this.plmn)).length; ++n) {
                        object = object[n];
                        n5 = n2;
                        n4 = n3;
                        if (object != null) {
                            n5 = n2 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n2 = n5;
                        n3 = n4;
                    }
                    n = n6 + n3 + n2 * 1;
                }
            }
            object = this.gid1;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((String[])object).length > 0) {
                    n5 = 0;
                    n3 = 0;
                    for (n6 = 0; n6 < ((String[])(object = this.gid1)).length; ++n6) {
                        object = object[n6];
                        n2 = n5;
                        n4 = n3;
                        if (object != null) {
                            n2 = n5 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n5 = n2;
                        n3 = n4;
                    }
                    n3 = n + n3 + n5 * 1;
                }
            }
            object = this.gid2;
            n6 = n3;
            if (object != null) {
                n6 = n3;
                if (((String[])object).length > 0) {
                    n2 = 0;
                    n6 = 0;
                    for (n = 0; n < ((String[])(object = this.gid2)).length; ++n) {
                        object = object[n];
                        n5 = n2;
                        n4 = n6;
                        if (object != null) {
                            n5 = n2 + 1;
                            n4 = n6 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n2 = n5;
                        n6 = n4;
                    }
                    n6 = n3 + n6 + n2 * 1;
                }
            }
            object = this.preferredApn;
            n = n6;
            if (object != null) {
                n = n6;
                if (((String[])object).length > 0) {
                    n5 = 0;
                    n3 = 0;
                    for (n = 0; n < ((String[])(object = this.preferredApn)).length; ++n) {
                        object = object[n];
                        n2 = n5;
                        n4 = n3;
                        if (object != null) {
                            n2 = n5 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n5 = n2;
                        n3 = n4;
                    }
                    n = n6 + n3 + n5 * 1;
                }
            }
            object = this.iccidPrefix;
            n6 = n;
            if (object != null) {
                n6 = n;
                if (((String[])object).length > 0) {
                    n5 = 0;
                    n3 = 0;
                    for (n6 = 0; n6 < ((String[])(object = this.iccidPrefix)).length; ++n6) {
                        object = object[n6];
                        n2 = n5;
                        n4 = n3;
                        if (object != null) {
                            n2 = n5 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n5 = n2;
                        n3 = n4;
                    }
                    n6 = n + n3 + n5 * 1;
                }
            }
            object = this.privilegeAccessRule;
            n = n6;
            if (object != null) {
                n = n6;
                if (((String[])object).length > 0) {
                    n5 = 0;
                    n3 = 0;
                    for (n = 0; n < ((String[])(object = this.privilegeAccessRule)).length; ++n) {
                        object = object[n];
                        n2 = n5;
                        n4 = n3;
                        if (object != null) {
                            n2 = n5 + 1;
                            n4 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n5 = n2;
                        n3 = n4;
                    }
                    n = n6 + n3 + n5 * 1;
                }
            }
            return n;
        }

        @Override
        public CarrierAttribute mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                int n2;
                String[] arrstring;
                if (n != 10) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 34) {
                                if (n != 42) {
                                    if (n != 50) {
                                        if (n != 58) {
                                            if (n != 66) {
                                                if (n != 74) {
                                                    if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                                    return this;
                                                }
                                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                                                arrstring = this.privilegeAccessRule;
                                                n = arrstring == null ? 0 : arrstring.length;
                                                arrstring = new String[n + n2];
                                                n2 = n;
                                                if (n != 0) {
                                                    System.arraycopy(this.privilegeAccessRule, 0, arrstring, 0, n);
                                                    n2 = n;
                                                }
                                                while (n2 < arrstring.length - 1) {
                                                    arrstring[n2] = codedInputByteBufferNano.readString();
                                                    codedInputByteBufferNano.readTag();
                                                    ++n2;
                                                }
                                                arrstring[n2] = codedInputByteBufferNano.readString();
                                                this.privilegeAccessRule = arrstring;
                                                continue;
                                            }
                                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                                            arrstring = this.iccidPrefix;
                                            n = arrstring == null ? 0 : arrstring.length;
                                            arrstring = new String[n + n2];
                                            n2 = n;
                                            if (n != 0) {
                                                System.arraycopy(this.iccidPrefix, 0, arrstring, 0, n);
                                                n2 = n;
                                            }
                                            while (n2 < arrstring.length - 1) {
                                                arrstring[n2] = codedInputByteBufferNano.readString();
                                                codedInputByteBufferNano.readTag();
                                                ++n2;
                                            }
                                            arrstring[n2] = codedInputByteBufferNano.readString();
                                            this.iccidPrefix = arrstring;
                                            continue;
                                        }
                                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                                        arrstring = this.preferredApn;
                                        n = arrstring == null ? 0 : arrstring.length;
                                        arrstring = new String[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.preferredApn, 0, arrstring, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrstring.length - 1) {
                                            arrstring[n2] = codedInputByteBufferNano.readString();
                                            codedInputByteBufferNano.readTag();
                                            ++n2;
                                        }
                                        arrstring[n2] = codedInputByteBufferNano.readString();
                                        this.preferredApn = arrstring;
                                        continue;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                                    arrstring = this.gid2;
                                    n = arrstring == null ? 0 : arrstring.length;
                                    arrstring = new String[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.gid2, 0, arrstring, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrstring.length - 1) {
                                        arrstring[n2] = codedInputByteBufferNano.readString();
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrstring[n2] = codedInputByteBufferNano.readString();
                                    this.gid2 = arrstring;
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                arrstring = this.gid1;
                                n = arrstring == null ? 0 : arrstring.length;
                                arrstring = new String[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.gid1, 0, arrstring, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrstring.length - 1) {
                                    arrstring[n2] = codedInputByteBufferNano.readString();
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrstring[n2] = codedInputByteBufferNano.readString();
                                this.gid1 = arrstring;
                                continue;
                            }
                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            arrstring = this.plmn;
                            n = arrstring == null ? 0 : arrstring.length;
                            arrstring = new String[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.plmn, 0, arrstring, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrstring.length - 1) {
                                arrstring[n2] = codedInputByteBufferNano.readString();
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrstring[n2] = codedInputByteBufferNano.readString();
                            this.plmn = arrstring;
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        arrstring = this.spn;
                        n = arrstring == null ? 0 : arrstring.length;
                        arrstring = new String[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.spn, 0, arrstring, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrstring.length - 1) {
                            arrstring[n2] = codedInputByteBufferNano.readString();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrstring[n2] = codedInputByteBufferNano.readString();
                        this.spn = arrstring;
                        continue;
                    }
                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    arrstring = this.imsiPrefixXpattern;
                    n = arrstring == null ? 0 : arrstring.length;
                    arrstring = new String[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.imsiPrefixXpattern, 0, arrstring, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrstring.length - 1) {
                        arrstring[n2] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrstring[n2] = codedInputByteBufferNano.readString();
                    this.imsiPrefixXpattern = arrstring;
                    continue;
                }
                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                arrstring = this.mccmncTuple;
                n = arrstring == null ? 0 : arrstring.length;
                arrstring = new String[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.mccmncTuple, 0, arrstring, 0, n);
                    n2 = n;
                }
                while (n2 < arrstring.length - 1) {
                    arrstring[n2] = codedInputByteBufferNano.readString();
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arrstring[n2] = codedInputByteBufferNano.readString();
                this.mccmncTuple = arrstring;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.mccmncTuple;
            if (object != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.mccmncTuple)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(1, (String)object);
                }
            }
            if ((object = this.imsiPrefixXpattern) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.imsiPrefixXpattern)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(2, (String)object);
                }
            }
            if ((object = this.spn) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.spn)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(3, (String)object);
                }
            }
            if ((object = this.plmn) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.plmn)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(4, (String)object);
                }
            }
            if ((object = this.gid1) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.gid1)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(5, (String)object);
                }
            }
            if ((object = this.gid2) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.gid2)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(6, (String)object);
                }
            }
            if ((object = this.preferredApn) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.preferredApn)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(7, (String)object);
                }
            }
            if ((object = this.iccidPrefix) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.iccidPrefix)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(8, (String)object);
                }
            }
            if ((object = this.privilegeAccessRule) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.privilegeAccessRule)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(9, (String)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class CarrierId
    extends ExtendableMessageNano<CarrierId> {
        private static volatile CarrierId[] _emptyArray;
        public int canonicalId;
        public CarrierAttribute[] carrierAttribute;
        public String carrierName;
        public int parentCanonicalId;

        public CarrierId() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static CarrierId[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new CarrierId[0];
                return _emptyArray;
            }
        }

        public static CarrierId parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new CarrierId().mergeFrom(codedInputByteBufferNano);
        }

        public static CarrierId parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new CarrierId(), arrby);
        }

        public CarrierId clear() {
            this.canonicalId = 0;
            this.carrierName = "";
            this.carrierAttribute = CarrierAttribute.emptyArray();
            this.parentCanonicalId = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.canonicalId;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n = n3;
            if (!this.carrierName.equals("")) {
                n = n3 + CodedOutputByteBufferNano.computeStringSize(2, this.carrierName);
            }
            Object object = this.carrierAttribute;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((CarrierAttribute[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.carrierAttribute;
                        n3 = n;
                        if (n2 >= ((CarrierAttribute[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            n2 = this.parentCanonicalId;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            return n;
        }

        @Override
        public CarrierId mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 32) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.parentCanonicalId = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        CarrierAttribute[] arrcarrierAttribute = this.carrierAttribute;
                        n = arrcarrierAttribute == null ? 0 : arrcarrierAttribute.length;
                        arrcarrierAttribute = new CarrierAttribute[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.carrierAttribute, 0, arrcarrierAttribute, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrcarrierAttribute.length - 1) {
                            arrcarrierAttribute[n2] = new CarrierAttribute();
                            codedInputByteBufferNano.readMessage(arrcarrierAttribute[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrcarrierAttribute[n2] = new CarrierAttribute();
                        codedInputByteBufferNano.readMessage(arrcarrierAttribute[n2]);
                        this.carrierAttribute = arrcarrierAttribute;
                        continue;
                    }
                    this.carrierName = codedInputByteBufferNano.readString();
                    continue;
                }
                this.canonicalId = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.canonicalId;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if (!this.carrierName.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.carrierName);
            }
            if ((object = this.carrierAttribute) != null && ((CarrierAttribute[])object).length > 0) {
                for (n = 0; n < ((CarrierAttribute[])(object = this.carrierAttribute)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if ((n = this.parentCanonicalId) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class CarrierList
    extends ExtendableMessageNano<CarrierList> {
        private static volatile CarrierList[] _emptyArray;
        public CarrierId[] carrierId;
        public int version;

        public CarrierList() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static CarrierList[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new CarrierList[0];
                return _emptyArray;
            }
        }

        public static CarrierList parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new CarrierList().mergeFrom(codedInputByteBufferNano);
        }

        public static CarrierList parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new CarrierList(), arrby);
        }

        public CarrierList clear() {
            this.carrierId = CarrierId.emptyArray();
            this.version = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.carrierId;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((CarrierId[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.carrierId;
                        n3 = n2;
                        if (n >= ((CarrierId[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(1, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            n = this.version;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n);
            }
            return n2;
        }

        @Override
        public CarrierList mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.version = codedInputByteBufferNano.readInt32();
                    continue;
                }
                int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                CarrierId[] arrcarrierId = this.carrierId;
                n = arrcarrierId == null ? 0 : arrcarrierId.length;
                arrcarrierId = new CarrierId[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.carrierId, 0, arrcarrierId, 0, n);
                    n2 = n;
                }
                while (n2 < arrcarrierId.length - 1) {
                    arrcarrierId[n2] = new CarrierId();
                    codedInputByteBufferNano.readMessage(arrcarrierId[n2]);
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arrcarrierId[n2] = new CarrierId();
                codedInputByteBufferNano.readMessage(arrcarrierId[n2]);
                this.carrierId = arrcarrierId;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.carrierId;
            if (object != null && ((CarrierId[])object).length > 0) {
                for (n = 0; n < ((CarrierId[])(object = this.carrierId)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((n = this.version) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

}

