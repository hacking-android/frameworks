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

public interface TelephonyProto {

    public static final class ActiveSubscriptionInfo
    extends ExtendableMessageNano<ActiveSubscriptionInfo> {
        private static volatile ActiveSubscriptionInfo[] _emptyArray;
        public int carrierId;
        public int isOpportunistic;
        public int slotIndex;

        public ActiveSubscriptionInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ActiveSubscriptionInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ActiveSubscriptionInfo[0];
                return _emptyArray;
            }
        }

        public static ActiveSubscriptionInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ActiveSubscriptionInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static ActiveSubscriptionInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ActiveSubscriptionInfo(), arrby);
        }

        public ActiveSubscriptionInfo clear() {
            this.slotIndex = 0;
            this.carrierId = 0;
            this.isOpportunistic = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.slotIndex;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.carrierId;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.isOpportunistic;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            return n3;
        }

        @Override
        public ActiveSubscriptionInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.isOpportunistic = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.carrierId = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.slotIndex = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.slotIndex;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.carrierId) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.isOpportunistic) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class EmergencyNumberInfo
    extends ExtendableMessageNano<EmergencyNumberInfo> {
        private static volatile EmergencyNumberInfo[] _emptyArray;
        public String address;
        public String countryIso;
        public String mnc;
        public int numberSourcesBitmask;
        public int routing;
        public int serviceCategoriesBitmask;
        public String[] urns;

        public EmergencyNumberInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static EmergencyNumberInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new EmergencyNumberInfo[0];
                return _emptyArray;
            }
        }

        public static EmergencyNumberInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new EmergencyNumberInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static EmergencyNumberInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new EmergencyNumberInfo(), arrby);
        }

        public EmergencyNumberInfo clear() {
            this.address = "";
            this.countryIso = "";
            this.mnc = "";
            this.serviceCategoriesBitmask = 0;
            this.urns = WireFormatNano.EMPTY_STRING_ARRAY;
            this.numberSourcesBitmask = 0;
            this.routing = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.address.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.address);
            }
            int n3 = n2;
            if (!this.countryIso.equals("")) {
                n3 = n2 + CodedOutputByteBufferNano.computeStringSize(2, this.countryIso);
            }
            n = n3;
            if (!this.mnc.equals("")) {
                n = n3 + CodedOutputByteBufferNano.computeStringSize(3, this.mnc);
            }
            n3 = this.serviceCategoriesBitmask;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            Object object = this.urns;
            n = n2;
            if (object != null) {
                n = n2;
                if (((String[])object).length > 0) {
                    int n4 = 0;
                    n3 = 0;
                    for (n = 0; n < ((String[])(object = this.urns)).length; ++n) {
                        object = object[n];
                        int n5 = n4;
                        int n6 = n3;
                        if (object != null) {
                            n5 = n4 + 1;
                            n6 = n3 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                        }
                        n4 = n5;
                        n3 = n6;
                    }
                    n = n2 + n3 + n4 * 1;
                }
            }
            n3 = this.numberSourcesBitmask;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            n3 = this.routing;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(7, n3);
            }
            return n;
        }

        @Override
        public EmergencyNumberInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 32) {
                                if (n != 42) {
                                    if (n != 48) {
                                        if (n != 56) {
                                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                            return this;
                                        }
                                        this.routing = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    this.numberSourcesBitmask = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                String[] arrstring = this.urns;
                                n = arrstring == null ? 0 : arrstring.length;
                                arrstring = new String[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.urns, 0, arrstring, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrstring.length - 1) {
                                    arrstring[n2] = codedInputByteBufferNano.readString();
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrstring[n2] = codedInputByteBufferNano.readString();
                                this.urns = arrstring;
                                continue;
                            }
                            this.serviceCategoriesBitmask = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.mnc = codedInputByteBufferNano.readString();
                        continue;
                    }
                    this.countryIso = codedInputByteBufferNano.readString();
                    continue;
                }
                this.address = codedInputByteBufferNano.readString();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object;
            if (!this.address.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.address);
            }
            if (!this.countryIso.equals("")) {
                codedOutputByteBufferNano.writeString(2, this.countryIso);
            }
            if (!this.mnc.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.mnc);
            }
            if ((n = this.serviceCategoriesBitmask) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((object = this.urns) != null && ((String[])object).length > 0) {
                for (n = 0; n < ((String[])(object = this.urns)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeString(5, (String)object);
                }
            }
            if ((n = this.numberSourcesBitmask) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.routing) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ImsCapabilities
    extends ExtendableMessageNano<ImsCapabilities> {
        private static volatile ImsCapabilities[] _emptyArray;
        public boolean utOverLte;
        public boolean utOverWifi;
        public boolean videoOverLte;
        public boolean videoOverWifi;
        public boolean voiceOverLte;
        public boolean voiceOverWifi;

        public ImsCapabilities() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ImsCapabilities[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ImsCapabilities[0];
                return _emptyArray;
            }
        }

        public static ImsCapabilities parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ImsCapabilities().mergeFrom(codedInputByteBufferNano);
        }

        public static ImsCapabilities parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ImsCapabilities(), arrby);
        }

        public ImsCapabilities clear() {
            this.voiceOverLte = false;
            this.voiceOverWifi = false;
            this.videoOverLte = false;
            this.videoOverWifi = false;
            this.utOverLte = false;
            this.utOverWifi = false;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            boolean bl = this.voiceOverLte;
            int n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(1, bl);
            }
            bl = this.voiceOverWifi;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(2, bl);
            }
            bl = this.videoOverLte;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(3, bl);
            }
            bl = this.videoOverWifi;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            bl = this.utOverLte;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(5, bl);
            }
            bl = this.utOverWifi;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(6, bl);
            }
            return n;
        }

        @Override
        public ImsCapabilities mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                        return this;
                                    }
                                    this.utOverWifi = codedInputByteBufferNano.readBool();
                                    continue;
                                }
                                this.utOverLte = codedInputByteBufferNano.readBool();
                                continue;
                            }
                            this.videoOverWifi = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        this.videoOverLte = codedInputByteBufferNano.readBool();
                        continue;
                    }
                    this.voiceOverWifi = codedInputByteBufferNano.readBool();
                    continue;
                }
                this.voiceOverLte = codedInputByteBufferNano.readBool();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean bl = this.voiceOverLte;
            if (bl) {
                codedOutputByteBufferNano.writeBool(1, bl);
            }
            if (bl = this.voiceOverWifi) {
                codedOutputByteBufferNano.writeBool(2, bl);
            }
            if (bl = this.videoOverLte) {
                codedOutputByteBufferNano.writeBool(3, bl);
            }
            if (bl = this.videoOverWifi) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            if (bl = this.utOverLte) {
                codedOutputByteBufferNano.writeBool(5, bl);
            }
            if (bl = this.utOverWifi) {
                codedOutputByteBufferNano.writeBool(6, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ImsConnectionState
    extends ExtendableMessageNano<ImsConnectionState> {
        private static volatile ImsConnectionState[] _emptyArray;
        public ImsReasonInfo reasonInfo;
        public int state;

        public ImsConnectionState() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ImsConnectionState[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ImsConnectionState[0];
                return _emptyArray;
            }
        }

        public static ImsConnectionState parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ImsConnectionState().mergeFrom(codedInputByteBufferNano);
        }

        public static ImsConnectionState parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ImsConnectionState(), arrby);
        }

        public ImsConnectionState clear() {
            this.state = 0;
            this.reasonInfo = null;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.state;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            ImsReasonInfo imsReasonInfo = this.reasonInfo;
            n = n3;
            if (imsReasonInfo != null) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, imsReasonInfo);
            }
            return n;
        }

        @Override
        public ImsConnectionState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    if (this.reasonInfo == null) {
                        this.reasonInfo = new ImsReasonInfo();
                    }
                    codedInputByteBufferNano.readMessage(this.reasonInfo);
                    continue;
                }
                int n2 = codedInputByteBufferNano.getPosition();
                int n3 = codedInputByteBufferNano.readInt32();
                if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3 && n3 != 4 && n3 != 5) {
                    codedInputByteBufferNano.rewindToPosition(n2);
                    this.storeUnknownField(codedInputByteBufferNano, n);
                    continue;
                }
                this.state = n3;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            ImsReasonInfo imsReasonInfo;
            int n = this.state;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((imsReasonInfo = this.reasonInfo) != null) {
                codedOutputByteBufferNano.writeMessage(2, imsReasonInfo);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface State {
            public static final int CONNECTED = 1;
            public static final int DISCONNECTED = 3;
            public static final int PROGRESSING = 2;
            public static final int RESUMED = 4;
            public static final int STATE_UNKNOWN = 0;
            public static final int SUSPENDED = 5;
        }

    }

    public static final class ImsReasonInfo
    extends ExtendableMessageNano<ImsReasonInfo> {
        private static volatile ImsReasonInfo[] _emptyArray;
        public int extraCode;
        public String extraMessage;
        public int reasonCode;

        public ImsReasonInfo() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ImsReasonInfo[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ImsReasonInfo[0];
                return _emptyArray;
            }
        }

        public static ImsReasonInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ImsReasonInfo().mergeFrom(codedInputByteBufferNano);
        }

        public static ImsReasonInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ImsReasonInfo(), arrby);
        }

        public ImsReasonInfo clear() {
            this.reasonCode = 0;
            this.extraCode = 0;
            this.extraMessage = "";
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.reasonCode;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.extraCode;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n3 = n;
            if (!this.extraMessage.equals("")) {
                n3 = n + CodedOutputByteBufferNano.computeStringSize(3, this.extraMessage);
            }
            return n3;
        }

        @Override
        public ImsReasonInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 26) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.extraMessage = codedInputByteBufferNano.readString();
                        continue;
                    }
                    this.extraCode = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.reasonCode = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.reasonCode;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.extraCode) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if (!this.extraMessage.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.extraMessage);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static interface ImsServiceErrno {
        public static final int IMS_E_SMS_SEND_STATUS_ERROR = 2;
        public static final int IMS_E_SMS_SEND_STATUS_ERROR_FALLBACK = 4;
        public static final int IMS_E_SMS_SEND_STATUS_ERROR_RETRY = 3;
        public static final int IMS_E_SUCCESS = 1;
        public static final int IMS_E_UNKNOWN = 0;
    }

    public static final class ModemPowerStats
    extends ExtendableMessageNano<ModemPowerStats> {
        private static volatile ModemPowerStats[] _emptyArray;
        public long cellularKernelActiveTimeMs;
        public double energyConsumedMah;
        public long idleTimeMs;
        public long loggingDurationMs;
        public double monitoredRailEnergyConsumedMah;
        public long numBytesRx;
        public long numBytesTx;
        public long numPacketsRx;
        public long numPacketsTx;
        public long rxTimeMs;
        public long sleepTimeMs;
        public long[] timeInRatMs;
        public long[] timeInRxSignalStrengthLevelMs;
        public long timeInVeryPoorRxSignalLevelMs;
        public long[] txTimeMs;

        public ModemPowerStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ModemPowerStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ModemPowerStats[0];
                return _emptyArray;
            }
        }

        public static ModemPowerStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ModemPowerStats().mergeFrom(codedInputByteBufferNano);
        }

        public static ModemPowerStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ModemPowerStats(), arrby);
        }

        public ModemPowerStats clear() {
            this.loggingDurationMs = 0L;
            this.energyConsumedMah = 0.0;
            this.numPacketsTx = 0L;
            this.cellularKernelActiveTimeMs = 0L;
            this.timeInVeryPoorRxSignalLevelMs = 0L;
            this.sleepTimeMs = 0L;
            this.idleTimeMs = 0L;
            this.rxTimeMs = 0L;
            this.txTimeMs = WireFormatNano.EMPTY_LONG_ARRAY;
            this.numBytesTx = 0L;
            this.numPacketsRx = 0L;
            this.numBytesRx = 0L;
            this.timeInRatMs = WireFormatNano.EMPTY_LONG_ARRAY;
            this.timeInRxSignalStrengthLevelMs = WireFormatNano.EMPTY_LONG_ARRAY;
            this.monitoredRailEnergyConsumedMah = 0.0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.loggingDurationMs;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            n = n2;
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(0.0)) {
                n = n2 + CodedOutputByteBufferNano.computeDoubleSize(2, this.energyConsumedMah);
            }
            l = this.numPacketsTx;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.cellularKernelActiveTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.timeInVeryPoorRxSignalLevelMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.sleepTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.idleTimeMs;
            int n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.rxTimeMs;
            n2 = n3;
            if (l != 0L) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            long[] arrl = this.txTimeMs;
            n3 = n2;
            if (arrl != null) {
                n3 = n2;
                if (arrl.length > 0) {
                    n3 = 0;
                    for (n = 0; n < (arrl = this.txTimeMs).length; ++n) {
                        l = arrl[n];
                        n3 += CodedOutputByteBufferNano.computeInt64SizeNoTag(l);
                    }
                    n3 = n2 + n3 + arrl.length * 1;
                }
            }
            l = this.numBytesTx;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            l = this.numPacketsRx;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(11, l);
            }
            l = this.numBytesRx;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(12, l);
            }
            arrl = this.timeInRatMs;
            n2 = n;
            if (arrl != null) {
                n2 = n;
                if (arrl.length > 0) {
                    n3 = 0;
                    for (n2 = 0; n2 < (arrl = this.timeInRatMs).length; ++n2) {
                        l = arrl[n2];
                        n3 += CodedOutputByteBufferNano.computeInt64SizeNoTag(l);
                    }
                    n2 = n + n3 + arrl.length * 1;
                }
            }
            arrl = this.timeInRxSignalStrengthLevelMs;
            n = n2;
            if (arrl != null) {
                n = n2;
                if (arrl.length > 0) {
                    n3 = 0;
                    for (n = 0; n < (arrl = this.timeInRxSignalStrengthLevelMs).length; ++n) {
                        l = arrl[n];
                        n3 += CodedOutputByteBufferNano.computeInt64SizeNoTag(l);
                    }
                    n = n2 + n3 + arrl.length * 1;
                }
            }
            n2 = n;
            if (Double.doubleToLongBits(this.monitoredRailEnergyConsumedMah) != Double.doubleToLongBits(0.0)) {
                n2 = n + CodedOutputByteBufferNano.computeDoubleSize(15, this.monitoredRailEnergyConsumedMah);
            }
            return n2;
        }

        @Override
        public ModemPowerStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block21 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block21;
                        return this;
                    }
                    case 121: {
                        this.monitoredRailEnergyConsumedMah = codedInputByteBufferNano.readDouble();
                        continue block21;
                    }
                    case 114: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        long[] arrl = this.timeInRxSignalStrengthLevelMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.timeInRxSignalStrengthLevelMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        this.timeInRxSignalStrengthLevelMs = arrl;
                        codedInputByteBufferNano.popLimit(n2);
                        continue block21;
                    }
                    case 112: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 112);
                        long[] arrl = this.timeInRxSignalStrengthLevelMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.timeInRxSignalStrengthLevelMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length - 1) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrl[n3] = codedInputByteBufferNano.readInt64();
                        this.timeInRxSignalStrengthLevelMs = arrl;
                        continue block21;
                    }
                    case 106: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        long[] arrl = this.timeInRatMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.timeInRatMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        this.timeInRatMs = arrl;
                        codedInputByteBufferNano.popLimit(n2);
                        continue block21;
                    }
                    case 104: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 104);
                        long[] arrl = this.timeInRatMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.timeInRatMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length - 1) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrl[n3] = codedInputByteBufferNano.readInt64();
                        this.timeInRatMs = arrl;
                        continue block21;
                    }
                    case 96: {
                        this.numBytesRx = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 88: {
                        this.numPacketsRx = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 80: {
                        this.numBytesTx = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 74: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        long[] arrl = this.txTimeMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.txTimeMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            ++n3;
                        }
                        this.txTimeMs = arrl;
                        codedInputByteBufferNano.popLimit(n2);
                        continue block21;
                    }
                    case 72: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 72);
                        long[] arrl = this.txTimeMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.txTimeMs, 0, arrl, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrl.length - 1) {
                            arrl[n3] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrl[n3] = codedInputByteBufferNano.readInt64();
                        this.txTimeMs = arrl;
                        continue block21;
                    }
                    case 64: {
                        this.rxTimeMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 56: {
                        this.idleTimeMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 48: {
                        this.sleepTimeMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 40: {
                        this.timeInVeryPoorRxSignalLevelMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 32: {
                        this.cellularKernelActiveTimeMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 24: {
                        this.numPacketsTx = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 17: {
                        this.energyConsumedMah = codedInputByteBufferNano.readDouble();
                        continue block21;
                    }
                    case 8: {
                        this.loggingDurationMs = codedInputByteBufferNano.readInt64();
                        continue block21;
                    }
                    case 0: 
                }
                break;
            } while (true);
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            long[] arrl;
            long l = this.loggingDurationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(2, this.energyConsumedMah);
            }
            if ((l = this.numPacketsTx) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.cellularKernelActiveTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.timeInVeryPoorRxSignalLevelMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.sleepTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.idleTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.rxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((arrl = this.txTimeMs) != null && arrl.length > 0) {
                for (n = 0; n < (arrl = this.txTimeMs).length; ++n) {
                    codedOutputByteBufferNano.writeInt64(9, arrl[n]);
                }
            }
            if ((l = this.numBytesTx) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            if ((l = this.numPacketsRx) != 0L) {
                codedOutputByteBufferNano.writeInt64(11, l);
            }
            if ((l = this.numBytesRx) != 0L) {
                codedOutputByteBufferNano.writeInt64(12, l);
            }
            if ((arrl = this.timeInRatMs) != null && arrl.length > 0) {
                for (n = 0; n < (arrl = this.timeInRatMs).length; ++n) {
                    codedOutputByteBufferNano.writeInt64(13, arrl[n]);
                }
            }
            if ((arrl = this.timeInRxSignalStrengthLevelMs) != null && arrl.length > 0) {
                for (n = 0; n < (arrl = this.timeInRxSignalStrengthLevelMs).length; ++n) {
                    codedOutputByteBufferNano.writeInt64(14, arrl[n]);
                }
            }
            if (Double.doubleToLongBits(this.monitoredRailEnergyConsumedMah) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(15, this.monitoredRailEnergyConsumedMah);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static interface PdpType {
        public static final int PDP_TYPE_IP = 1;
        public static final int PDP_TYPE_IPV4V6 = 3;
        public static final int PDP_TYPE_IPV6 = 2;
        public static final int PDP_TYPE_NON_IP = 5;
        public static final int PDP_TYPE_PPP = 4;
        public static final int PDP_TYPE_UNSTRUCTURED = 6;
        public static final int PDP_UNKNOWN = 0;
    }

    public static interface RadioAccessTechnology {
        public static final int RAT_1XRTT = 6;
        public static final int RAT_EDGE = 2;
        public static final int RAT_EHRPD = 13;
        public static final int RAT_EVDO_0 = 7;
        public static final int RAT_EVDO_A = 8;
        public static final int RAT_EVDO_B = 12;
        public static final int RAT_GPRS = 1;
        public static final int RAT_GSM = 16;
        public static final int RAT_HSDPA = 9;
        public static final int RAT_HSPA = 11;
        public static final int RAT_HSPAP = 15;
        public static final int RAT_HSUPA = 10;
        public static final int RAT_IS95A = 4;
        public static final int RAT_IS95B = 5;
        public static final int RAT_IWLAN = 18;
        public static final int RAT_LTE = 14;
        public static final int RAT_LTE_CA = 19;
        public static final int RAT_TD_SCDMA = 17;
        public static final int RAT_UMTS = 3;
        public static final int RAT_UNKNOWN = 0;
        public static final int UNKNOWN = -1;
    }

    public static final class RilDataCall
    extends ExtendableMessageNano<RilDataCall> {
        private static volatile RilDataCall[] _emptyArray;
        public int apnTypeBitmask;
        public int cid;
        public String iframe;
        public int state;
        public int type;

        public RilDataCall() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static RilDataCall[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new RilDataCall[0];
                return _emptyArray;
            }
        }

        public static RilDataCall parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new RilDataCall().mergeFrom(codedInputByteBufferNano);
        }

        public static RilDataCall parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new RilDataCall(), arrby);
        }

        public RilDataCall clear() {
            this.cid = 0;
            this.type = 0;
            this.iframe = "";
            this.state = 0;
            this.apnTypeBitmask = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.cid;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n = this.type;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n);
            }
            n = n2;
            if (!this.iframe.equals("")) {
                n = n2 + CodedOutputByteBufferNano.computeStringSize(3, this.iframe);
            }
            n2 = this.state;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.apnTypeBitmask;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            return n;
        }

        @Override
        public RilDataCall mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    int n2;
                    int n3;
                    if (n != 16) {
                        if (n != 26) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                this.apnTypeBitmask = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            n3 = codedInputByteBufferNano.getPosition();
                            n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2) {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue;
                            }
                            this.state = n2;
                            continue;
                        }
                        this.iframe = codedInputByteBufferNano.readString();
                        continue;
                    }
                    n3 = codedInputByteBufferNano.getPosition();
                    n2 = codedInputByteBufferNano.readInt32();
                    switch (n2) {
                        default: {
                            codedInputByteBufferNano.rewindToPosition(n3);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue block3;
                        }
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                    }
                    this.type = n2;
                    continue;
                }
                this.cid = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.cid;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.type) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if (!this.iframe.equals("")) {
                codedOutputByteBufferNano.writeString(3, this.iframe);
            }
            if ((n = this.state) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.apnTypeBitmask) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface State {
            public static final int CONNECTED = 1;
            public static final int DISCONNECTED = 2;
            public static final int UNKNOWN = 0;
        }

    }

    public static interface RilErrno {
        public static final int RIL_E_ABORTED = 66;
        public static final int RIL_E_CANCELLED = 8;
        public static final int RIL_E_DEVICE_IN_USE = 65;
        public static final int RIL_E_DIAL_MODIFIED_TO_DIAL = 21;
        public static final int RIL_E_DIAL_MODIFIED_TO_SS = 20;
        public static final int RIL_E_DIAL_MODIFIED_TO_USSD = 19;
        public static final int RIL_E_EMPTY_RECORD = 56;
        public static final int RIL_E_ENCODING_ERR = 58;
        public static final int RIL_E_FDN_CHECK_FAILURE = 15;
        public static final int RIL_E_GENERIC_FAILURE = 3;
        public static final int RIL_E_ILLEGAL_SIM_OR_ME = 16;
        public static final int RIL_E_INTERNAL_ERR = 39;
        public static final int RIL_E_INVALID_ARGUMENTS = 45;
        public static final int RIL_E_INVALID_CALL_ID = 48;
        public static final int RIL_E_INVALID_MODEM_STATE = 47;
        public static final int RIL_E_INVALID_RESPONSE = 67;
        public static final int RIL_E_INVALID_SIM_STATE = 46;
        public static final int RIL_E_INVALID_SMSC_ADDRESS = 59;
        public static final int RIL_E_INVALID_SMS_FORMAT = 57;
        public static final int RIL_E_INVALID_STATE = 42;
        public static final int RIL_E_LCE_NOT_SUPPORTED = 36;
        public static final int RIL_E_LCE_NOT_SUPPORTED_NEW = 37;
        public static final int RIL_E_MISSING_RESOURCE = 17;
        public static final int RIL_E_MODEM_ERR = 41;
        public static final int RIL_E_MODE_NOT_SUPPORTED = 14;
        public static final int RIL_E_NETWORK_ERR = 50;
        public static final int RIL_E_NETWORK_NOT_READY = 61;
        public static final int RIL_E_NETWORK_REJECT = 54;
        public static final int RIL_E_NOT_PROVISIONED = 62;
        public static final int RIL_E_NO_MEMORY = 38;
        public static final int RIL_E_NO_NETWORK_FOUND = 64;
        public static final int RIL_E_NO_RESOURCES = 43;
        public static final int RIL_E_NO_SMS_TO_ACK = 49;
        public static final int RIL_E_NO_SUBSCRIPTION = 63;
        public static final int RIL_E_NO_SUCH_ELEMENT = 18;
        public static final int RIL_E_NO_SUCH_ENTRY = 60;
        public static final int RIL_E_OPERATION_NOT_ALLOWED = 55;
        public static final int RIL_E_OP_NOT_ALLOWED_BEFORE_REG_TO_NW = 10;
        public static final int RIL_E_OP_NOT_ALLOWED_DURING_VOICE_CALL = 9;
        public static final int RIL_E_PASSWORD_INCORRECT = 4;
        public static final int RIL_E_RADIO_NOT_AVAILABLE = 2;
        public static final int RIL_E_REQUEST_NOT_SUPPORTED = 7;
        public static final int RIL_E_REQUEST_RATE_LIMITED = 51;
        public static final int RIL_E_SIM_ABSENT = 12;
        public static final int RIL_E_SIM_BUSY = 52;
        public static final int RIL_E_SIM_ERR = 44;
        public static final int RIL_E_SIM_FULL = 53;
        public static final int RIL_E_SIM_PIN2 = 5;
        public static final int RIL_E_SIM_PUK2 = 6;
        public static final int RIL_E_SMS_SEND_FAIL_RETRY = 11;
        public static final int RIL_E_SS_MODIFIED_TO_DIAL = 25;
        public static final int RIL_E_SS_MODIFIED_TO_SS = 28;
        public static final int RIL_E_SS_MODIFIED_TO_USSD = 26;
        public static final int RIL_E_SUBSCRIPTION_NOT_AVAILABLE = 13;
        public static final int RIL_E_SUBSCRIPTION_NOT_SUPPORTED = 27;
        public static final int RIL_E_SUCCESS = 1;
        public static final int RIL_E_SYSTEM_ERR = 40;
        public static final int RIL_E_UNKNOWN = 0;
        public static final int RIL_E_USSD_MODIFIED_TO_DIAL = 22;
        public static final int RIL_E_USSD_MODIFIED_TO_SS = 23;
        public static final int RIL_E_USSD_MODIFIED_TO_USSD = 24;
    }

    public static interface SimState {
        public static final int SIM_STATE_ABSENT = 1;
        public static final int SIM_STATE_LOADED = 2;
        public static final int SIM_STATE_UNKNOWN = 0;
    }

    public static final class SmsSession
    extends ExtendableMessageNano<SmsSession> {
        private static volatile SmsSession[] _emptyArray;
        public Event[] events;
        public boolean eventsDropped;
        public int phoneId;
        public int startTimeMinutes;

        public SmsSession() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static SmsSession[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new SmsSession[0];
                return _emptyArray;
            }
        }

        public static SmsSession parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SmsSession().mergeFrom(codedInputByteBufferNano);
        }

        public static SmsSession parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new SmsSession(), arrby);
        }

        public SmsSession clear() {
            this.startTimeMinutes = 0;
            this.phoneId = 0;
            this.events = Event.emptyArray();
            this.eventsDropped = false;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.startTimeMinutes;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.phoneId;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            Object object = this.events;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Event[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.events;
                        n3 = n;
                        if (n2 >= ((Event[])object).length) break;
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
            boolean bl = this.eventsDropped;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            return n;
        }

        @Override
        public SmsSession mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 26) {
                            if (n != 32) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.eventsDropped = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        Event[] arrevent = this.events;
                        n = arrevent == null ? 0 : arrevent.length;
                        arrevent = new Event[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.events, 0, arrevent, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrevent.length - 1) {
                            arrevent[n2] = new Event();
                            codedInputByteBufferNano.readMessage(arrevent[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrevent[n2] = new Event();
                        codedInputByteBufferNano.readMessage(arrevent[n2]);
                        this.events = arrevent;
                        continue;
                    }
                    this.phoneId = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.startTimeMinutes = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            boolean bl;
            int n = this.startTimeMinutes;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.phoneId) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((object = this.events) != null && ((Event[])object).length > 0) {
                for (n = 0; n < ((Event[])(object = this.events)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if (bl = this.eventsDropped) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class Event
        extends ExtendableMessageNano<Event> {
            private static volatile Event[] _emptyArray;
            public boolean blocked;
            public CBMessage cellBroadcastMessage;
            public RilDataCall[] dataCalls;
            public int delay;
            public int error;
            public int errorCode;
            public int format;
            public ImsCapabilities imsCapabilities;
            public ImsConnectionState imsConnectionState;
            public int imsError;
            public IncompleteSms incompleteSms;
            public int rilRequestId;
            public TelephonyServiceState serviceState;
            public TelephonySettings settings;
            public int smsType;
            public int tech;
            public int type;

            public Event() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static Event[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new Event[0];
                    return _emptyArray;
                }
            }

            public static Event parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new Event().mergeFrom(codedInputByteBufferNano);
            }

            public static Event parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new Event(), arrby);
            }

            public Event clear() {
                this.type = 0;
                this.delay = 0;
                this.settings = null;
                this.serviceState = null;
                this.imsConnectionState = null;
                this.imsCapabilities = null;
                this.dataCalls = RilDataCall.emptyArray();
                this.format = 0;
                this.tech = 0;
                this.errorCode = 0;
                this.error = 0;
                this.rilRequestId = 0;
                this.cellBroadcastMessage = null;
                this.imsError = 0;
                this.incompleteSms = null;
                this.smsType = 0;
                this.blocked = false;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.type;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.delay;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                Object object = this.settings;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
                }
                object = this.serviceState;
                n2 = n3;
                if (object != null) {
                    n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                }
                object = this.imsConnectionState;
                n = n2;
                if (object != null) {
                    n = n2 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                }
                object = this.imsCapabilities;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
                }
                object = this.dataCalls;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((RilDataCall[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.dataCalls;
                            n = n3;
                            if (n2 >= ((RilDataCall[])object).length) break;
                            object = object[n2];
                            n = n3;
                            if (object != null) {
                                n = n3 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                            }
                            ++n2;
                            n3 = n;
                        } while (true);
                    }
                }
                n3 = this.format;
                n2 = n;
                if (n3 != 0) {
                    n2 = n + CodedOutputByteBufferNano.computeInt32Size(8, n3);
                }
                n = this.tech;
                n3 = n2;
                if (n != 0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(9, n);
                }
                n2 = this.errorCode;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(10, n2);
                }
                n2 = this.error;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(11, n2);
                }
                n2 = this.rilRequestId;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(12, n2);
                }
                object = this.cellBroadcastMessage;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(13, (MessageNano)object);
                }
                n2 = this.imsError;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(14, n2);
                }
                object = this.incompleteSms;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(15, (MessageNano)object);
                }
                n2 = this.smsType;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(16, n2);
                }
                boolean bl = this.blocked;
                n3 = n;
                if (bl) {
                    n3 = n + CodedOutputByteBufferNano.computeBoolSize(17, bl);
                }
                return n3;
            }

            @Override
            public Event mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                block32 : do {
                    int n = codedInputByteBufferNano.readTag();
                    switch (n) {
                        default: {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block32;
                            return this;
                        }
                        case 136: {
                            this.blocked = codedInputByteBufferNano.readBool();
                            continue block32;
                        }
                        case 128: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3 && n3 != 4) {
                                codedInputByteBufferNano.rewindToPosition(n2);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block32;
                            }
                            this.smsType = n3;
                            continue block32;
                        }
                        case 122: {
                            if (this.incompleteSms == null) {
                                this.incompleteSms = new IncompleteSms();
                            }
                            codedInputByteBufferNano.readMessage(this.incompleteSms);
                            continue block32;
                        }
                        case 112: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3 && n3 != 4) {
                                codedInputByteBufferNano.rewindToPosition(n2);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block32;
                            }
                            this.imsError = n3;
                            continue block32;
                        }
                        case 106: {
                            if (this.cellBroadcastMessage == null) {
                                this.cellBroadcastMessage = new CBMessage();
                            }
                            codedInputByteBufferNano.readMessage(this.cellBroadcastMessage);
                            continue block32;
                        }
                        case 96: {
                            this.rilRequestId = codedInputByteBufferNano.readInt32();
                            continue block32;
                        }
                        case 88: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    switch (n3) {
                                        default: {
                                            codedInputByteBufferNano.rewindToPosition(n2);
                                            this.storeUnknownField(codedInputByteBufferNano, n);
                                            continue block32;
                                        }
                                        case 36: 
                                        case 37: 
                                        case 38: 
                                        case 39: 
                                        case 40: 
                                        case 41: 
                                        case 42: 
                                        case 43: 
                                        case 44: 
                                        case 45: 
                                        case 46: 
                                        case 47: 
                                        case 48: 
                                        case 49: 
                                        case 50: 
                                        case 51: 
                                        case 52: 
                                        case 53: 
                                        case 54: 
                                        case 55: 
                                        case 56: 
                                        case 57: 
                                        case 58: 
                                        case 59: 
                                        case 60: 
                                        case 61: 
                                        case 62: 
                                        case 63: 
                                        case 64: 
                                        case 65: 
                                        case 66: 
                                        case 67: 
                                    }
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                                case 20: 
                                case 21: 
                                case 22: 
                                case 23: 
                                case 24: 
                                case 25: 
                                case 26: 
                                case 27: 
                                case 28: 
                            }
                            this.error = n3;
                            continue block32;
                        }
                        case 80: {
                            this.errorCode = codedInputByteBufferNano.readInt32();
                            continue block32;
                        }
                        case 72: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block32;
                            }
                            this.tech = n2;
                            continue block32;
                        }
                        case 64: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2) {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block32;
                            }
                            this.format = n2;
                            continue block32;
                        }
                        case 58: {
                            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                            RilDataCall[] arrrilDataCall = this.dataCalls;
                            n = arrrilDataCall == null ? 0 : arrrilDataCall.length;
                            arrrilDataCall = new RilDataCall[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.dataCalls, 0, arrrilDataCall, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrrilDataCall.length - 1) {
                                arrrilDataCall[n2] = new RilDataCall();
                                codedInputByteBufferNano.readMessage(arrrilDataCall[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrrilDataCall[n2] = new RilDataCall();
                            codedInputByteBufferNano.readMessage(arrrilDataCall[n2]);
                            this.dataCalls = arrrilDataCall;
                            continue block32;
                        }
                        case 50: {
                            if (this.imsCapabilities == null) {
                                this.imsCapabilities = new ImsCapabilities();
                            }
                            codedInputByteBufferNano.readMessage(this.imsCapabilities);
                            continue block32;
                        }
                        case 42: {
                            if (this.imsConnectionState == null) {
                                this.imsConnectionState = new ImsConnectionState();
                            }
                            codedInputByteBufferNano.readMessage(this.imsConnectionState);
                            continue block32;
                        }
                        case 34: {
                            if (this.serviceState == null) {
                                this.serviceState = new TelephonyServiceState();
                            }
                            codedInputByteBufferNano.readMessage(this.serviceState);
                            continue block32;
                        }
                        case 26: {
                            if (this.settings == null) {
                                this.settings = new TelephonySettings();
                            }
                            codedInputByteBufferNano.readMessage(this.settings);
                            continue block32;
                        }
                        case 16: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block32;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                            }
                            this.delay = n3;
                            continue block32;
                        }
                        case 8: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block32;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                            }
                            this.type = n2;
                            continue block32;
                        }
                        case 0: 
                    }
                    break;
                } while (true);
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                Object object;
                boolean bl;
                int n = this.type;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.delay) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((object = this.settings) != null) {
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
                if ((object = this.serviceState) != null) {
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
                if ((object = this.imsConnectionState) != null) {
                    codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                }
                if ((object = this.imsCapabilities) != null) {
                    codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
                }
                if ((object = this.dataCalls) != null && ((RilDataCall[])object).length > 0) {
                    for (n = 0; n < ((RilDataCall[])(object = this.dataCalls)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                    }
                }
                if ((n = this.format) != 0) {
                    codedOutputByteBufferNano.writeInt32(8, n);
                }
                if ((n = this.tech) != 0) {
                    codedOutputByteBufferNano.writeInt32(9, n);
                }
                if ((n = this.errorCode) != 0) {
                    codedOutputByteBufferNano.writeInt32(10, n);
                }
                if ((n = this.error) != 0) {
                    codedOutputByteBufferNano.writeInt32(11, n);
                }
                if ((n = this.rilRequestId) != 0) {
                    codedOutputByteBufferNano.writeInt32(12, n);
                }
                if ((object = this.cellBroadcastMessage) != null) {
                    codedOutputByteBufferNano.writeMessage(13, (MessageNano)object);
                }
                if ((n = this.imsError) != 0) {
                    codedOutputByteBufferNano.writeInt32(14, n);
                }
                if ((object = this.incompleteSms) != null) {
                    codedOutputByteBufferNano.writeMessage(15, (MessageNano)object);
                }
                if ((n = this.smsType) != 0) {
                    codedOutputByteBufferNano.writeInt32(16, n);
                }
                if (bl = this.blocked) {
                    codedOutputByteBufferNano.writeBool(17, bl);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static final class CBMessage
            extends ExtendableMessageNano<CBMessage> {
                private static volatile CBMessage[] _emptyArray;
                public long deliveredTimestampMillis;
                public int msgFormat;
                public int msgPriority;
                public int msgType;
                public int serialNumber;
                public int serviceCategory;

                public CBMessage() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static CBMessage[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new CBMessage[0];
                        return _emptyArray;
                    }
                }

                public static CBMessage parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new CBMessage().mergeFrom(codedInputByteBufferNano);
                }

                public static CBMessage parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new CBMessage(), arrby);
                }

                public CBMessage clear() {
                    this.msgFormat = 0;
                    this.msgPriority = 0;
                    this.msgType = 0;
                    this.serviceCategory = 0;
                    this.serialNumber = 0;
                    this.deliveredTimestampMillis = 0L;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.msgFormat;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    n2 = this.msgPriority;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                    }
                    n2 = this.msgType;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                    }
                    n2 = this.serviceCategory;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
                    }
                    n2 = this.serialNumber;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
                    }
                    long l = this.deliveredTimestampMillis;
                    n = n3;
                    if (l != 0L) {
                        n = n3 + CodedOutputByteBufferNano.computeInt64Size(6, l);
                    }
                    return n;
                }

                @Override
                public CBMessage mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    int n;
                    while ((n = codedInputByteBufferNano.readTag()) != 0) {
                        int n2;
                        int n3;
                        if (n != 8) {
                            if (n != 16) {
                                if (n != 24) {
                                    if (n != 32) {
                                        if (n != 40) {
                                            if (n != 48) {
                                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            this.deliveredTimestampMillis = codedInputByteBufferNano.readInt64();
                                            continue;
                                        }
                                        this.serialNumber = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    this.serviceCategory = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                n3 = codedInputByteBufferNano.getPosition();
                                n2 = codedInputByteBufferNano.readInt32();
                                if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue;
                                }
                                this.msgType = n2;
                                continue;
                            }
                            n2 = codedInputByteBufferNano.getPosition();
                            n3 = codedInputByteBufferNano.readInt32();
                            if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3 && n3 != 4) {
                                codedInputByteBufferNano.rewindToPosition(n2);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue;
                            }
                            this.msgPriority = n3;
                            continue;
                        }
                        n2 = codedInputByteBufferNano.getPosition();
                        n3 = codedInputByteBufferNano.readInt32();
                        if (n3 != 0 && n3 != 1 && n3 != 2) {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        this.msgFormat = n3;
                    }
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    long l;
                    int n = this.msgFormat;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    if ((n = this.msgPriority) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    if ((n = this.msgType) != 0) {
                        codedOutputByteBufferNano.writeInt32(3, n);
                    }
                    if ((n = this.serviceCategory) != 0) {
                        codedOutputByteBufferNano.writeInt32(4, n);
                    }
                    if ((n = this.serialNumber) != 0) {
                        codedOutputByteBufferNano.writeInt32(5, n);
                    }
                    if ((l = this.deliveredTimestampMillis) != 0L) {
                        codedOutputByteBufferNano.writeInt64(6, l);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }

            public static interface CBMessageType {
                public static final int CMAS = 2;
                public static final int ETWS = 1;
                public static final int OTHER = 3;
                public static final int TYPE_UNKNOWN = 0;
            }

            public static interface CBPriority {
                public static final int EMERGENCY = 4;
                public static final int INTERACTIVE = 2;
                public static final int NORMAL = 1;
                public static final int PRIORITY_UNKNOWN = 0;
                public static final int URGENT = 3;
            }

            public static interface Format {
                public static final int SMS_FORMAT_3GPP = 1;
                public static final int SMS_FORMAT_3GPP2 = 2;
                public static final int SMS_FORMAT_UNKNOWN = 0;
            }

            public static final class IncompleteSms
            extends ExtendableMessageNano<IncompleteSms> {
                private static volatile IncompleteSms[] _emptyArray;
                public int receivedParts;
                public int totalParts;

                public IncompleteSms() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static IncompleteSms[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new IncompleteSms[0];
                        return _emptyArray;
                    }
                }

                public static IncompleteSms parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new IncompleteSms().mergeFrom(codedInputByteBufferNano);
                }

                public static IncompleteSms parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new IncompleteSms(), arrby);
                }

                public IncompleteSms clear() {
                    this.receivedParts = 0;
                    this.totalParts = 0;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.receivedParts;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    n2 = this.totalParts;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                    }
                    return n;
                }

                @Override
                public IncompleteSms mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    int n;
                    while ((n = codedInputByteBufferNano.readTag()) != 0) {
                        if (n != 8) {
                            if (n != 16) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.totalParts = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.receivedParts = codedInputByteBufferNano.readInt32();
                    }
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    int n = this.receivedParts;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    if ((n = this.totalParts) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }

            public static interface SmsType {
                public static final int SMS_TYPE_NORMAL = 0;
                public static final int SMS_TYPE_SMS_PP = 1;
                public static final int SMS_TYPE_VOICEMAIL_INDICATION = 2;
                public static final int SMS_TYPE_WAP_PUSH = 4;
                public static final int SMS_TYPE_ZERO = 3;
            }

            public static interface Tech {
                public static final int SMS_CDMA = 2;
                public static final int SMS_GSM = 1;
                public static final int SMS_IMS = 3;
                public static final int SMS_UNKNOWN = 0;
            }

            public static interface Type {
                public static final int CB_SMS_RECEIVED = 9;
                public static final int DATA_CALL_LIST_CHANGED = 5;
                public static final int EVENT_UNKNOWN = 0;
                public static final int IMS_CAPABILITIES_CHANGED = 4;
                public static final int IMS_CONNECTION_STATE_CHANGED = 3;
                public static final int INCOMPLETE_SMS_RECEIVED = 10;
                public static final int RIL_SERVICE_STATE_CHANGED = 2;
                public static final int SETTINGS_CHANGED = 1;
                public static final int SMS_RECEIVED = 8;
                public static final int SMS_SEND = 6;
                public static final int SMS_SEND_RESULT = 7;
            }

        }

    }

    public static final class TelephonyCallSession
    extends ExtendableMessageNano<TelephonyCallSession> {
        private static volatile TelephonyCallSession[] _emptyArray;
        public Event[] events;
        public boolean eventsDropped;
        public int phoneId;
        public int startTimeMinutes;

        public TelephonyCallSession() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonyCallSession[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonyCallSession[0];
                return _emptyArray;
            }
        }

        public static TelephonyCallSession parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonyCallSession().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonyCallSession parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonyCallSession(), arrby);
        }

        public TelephonyCallSession clear() {
            this.startTimeMinutes = 0;
            this.phoneId = 0;
            this.events = Event.emptyArray();
            this.eventsDropped = false;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.startTimeMinutes;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.phoneId;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            Object object = this.events;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Event[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.events;
                        n3 = n;
                        if (n2 >= ((Event[])object).length) break;
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
            boolean bl = this.eventsDropped;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            return n;
        }

        @Override
        public TelephonyCallSession mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 26) {
                            if (n != 32) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.eventsDropped = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        Event[] arrevent = this.events;
                        n = arrevent == null ? 0 : arrevent.length;
                        arrevent = new Event[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.events, 0, arrevent, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrevent.length - 1) {
                            arrevent[n2] = new Event();
                            codedInputByteBufferNano.readMessage(arrevent[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrevent[n2] = new Event();
                        codedInputByteBufferNano.readMessage(arrevent[n2]);
                        this.events = arrevent;
                        continue;
                    }
                    this.phoneId = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.startTimeMinutes = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            boolean bl;
            int n = this.startTimeMinutes;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.phoneId) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((object = this.events) != null && ((Event[])object).length > 0) {
                for (n = 0; n < ((Event[])(object = this.events)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if (bl = this.eventsDropped) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class Event
        extends ExtendableMessageNano<Event> {
            private static volatile Event[] _emptyArray;
            public int audioCodec;
            public int callIndex;
            public CallQuality callQuality;
            public CallQualitySummary callQualitySummaryDl;
            public CallQualitySummary callQualitySummaryUl;
            public int callState;
            public RilCall[] calls;
            public RilDataCall[] dataCalls;
            public int delay;
            public int error;
            public ImsCapabilities imsCapabilities;
            public int imsCommand;
            public ImsConnectionState imsConnectionState;
            public EmergencyNumberInfo imsEmergencyNumberInfo;
            public boolean isImsEmergencyCall;
            public int mergedCallIndex;
            public long nitzTimestampMillis;
            public int phoneState;
            public ImsReasonInfo reasonInfo;
            public int rilRequest;
            public int rilRequestId;
            public TelephonyServiceState serviceState;
            public TelephonySettings settings;
            public int srcAccessTech;
            public int srvccState;
            public int targetAccessTech;
            public int type;

            public Event() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static Event[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new Event[0];
                    return _emptyArray;
                }
            }

            public static Event parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new Event().mergeFrom(codedInputByteBufferNano);
            }

            public static Event parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new Event(), arrby);
            }

            public Event clear() {
                this.type = 0;
                this.delay = 0;
                this.settings = null;
                this.serviceState = null;
                this.imsConnectionState = null;
                this.imsCapabilities = null;
                this.dataCalls = RilDataCall.emptyArray();
                this.phoneState = 0;
                this.callState = 0;
                this.callIndex = 0;
                this.mergedCallIndex = 0;
                this.calls = RilCall.emptyArray();
                this.error = 0;
                this.rilRequest = 0;
                this.rilRequestId = 0;
                this.srvccState = 0;
                this.imsCommand = 0;
                this.reasonInfo = null;
                this.srcAccessTech = -1;
                this.targetAccessTech = -1;
                this.nitzTimestampMillis = 0L;
                this.audioCodec = 0;
                this.callQuality = null;
                this.callQualitySummaryDl = null;
                this.callQualitySummaryUl = null;
                this.isImsEmergencyCall = false;
                this.imsEmergencyNumberInfo = null;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.type;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.delay;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                Object object = this.settings;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
                }
                object = this.serviceState;
                n2 = n3;
                if (object != null) {
                    n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                }
                object = this.imsConnectionState;
                n = n2;
                if (object != null) {
                    n = n2 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                }
                object = this.imsCapabilities;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
                }
                object = this.dataCalls;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((RilDataCall[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.dataCalls;
                            n = n3;
                            if (n2 >= ((RilDataCall[])object).length) break;
                            object = object[n2];
                            n = n3;
                            if (object != null) {
                                n = n3 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                            }
                            ++n2;
                            n3 = n;
                        } while (true);
                    }
                }
                n2 = this.phoneState;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(8, n2);
                }
                n = this.callState;
                n2 = n3;
                if (n != 0) {
                    n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(9, n);
                }
                n3 = this.callIndex;
                n = n2;
                if (n3 != 0) {
                    n = n2 + CodedOutputByteBufferNano.computeInt32Size(10, n3);
                }
                n2 = this.mergedCallIndex;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(11, n2);
                }
                object = this.calls;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((RilDataCall[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.calls;
                            n = n3;
                            if (n2 >= ((RilDataCall[])object).length) break;
                            object = object[n2];
                            n = n3;
                            if (object != null) {
                                n = n3 + CodedOutputByteBufferNano.computeMessageSize(12, (MessageNano)object);
                            }
                            ++n2;
                            n3 = n;
                        } while (true);
                    }
                }
                n2 = this.error;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(13, n2);
                }
                n = this.rilRequest;
                n2 = n3;
                if (n != 0) {
                    n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(14, n);
                }
                n3 = this.rilRequestId;
                n = n2;
                if (n3 != 0) {
                    n = n2 + CodedOutputByteBufferNano.computeInt32Size(15, n3);
                }
                n2 = this.srvccState;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(16, n2);
                }
                n = this.imsCommand;
                n2 = n3;
                if (n != 0) {
                    n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(17, n);
                }
                object = this.reasonInfo;
                n = n2;
                if (object != null) {
                    n = n2 + CodedOutputByteBufferNano.computeMessageSize(18, (MessageNano)object);
                }
                n2 = this.srcAccessTech;
                n3 = n;
                if (n2 != -1) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(19, n2);
                }
                n2 = this.targetAccessTech;
                n = n3;
                if (n2 != -1) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(20, n2);
                }
                long l = this.nitzTimestampMillis;
                n3 = n;
                if (l != 0L) {
                    n3 = n + CodedOutputByteBufferNano.computeInt64Size(21, l);
                }
                n2 = this.audioCodec;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(22, n2);
                }
                object = this.callQuality;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(23, (MessageNano)object);
                }
                object = this.callQualitySummaryDl;
                n = n3;
                if (object != null) {
                    n = n3 + CodedOutputByteBufferNano.computeMessageSize(24, (MessageNano)object);
                }
                object = this.callQualitySummaryUl;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(25, (MessageNano)object);
                }
                boolean bl = this.isImsEmergencyCall;
                n = n3;
                if (bl) {
                    n = n3 + CodedOutputByteBufferNano.computeBoolSize(26, bl);
                }
                object = this.imsEmergencyNumberInfo;
                n3 = n;
                if (object != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(27, (MessageNano)object);
                }
                return n3;
            }

            @Override
            public Event mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                block60 : do {
                    int n = codedInputByteBufferNano.readTag();
                    switch (n) {
                        default: {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block60;
                            return this;
                        }
                        case 218: {
                            if (this.imsEmergencyNumberInfo == null) {
                                this.imsEmergencyNumberInfo = new EmergencyNumberInfo();
                            }
                            codedInputByteBufferNano.readMessage(this.imsEmergencyNumberInfo);
                            continue block60;
                        }
                        case 208: {
                            this.isImsEmergencyCall = codedInputByteBufferNano.readBool();
                            continue block60;
                        }
                        case 202: {
                            if (this.callQualitySummaryUl == null) {
                                this.callQualitySummaryUl = new CallQualitySummary();
                            }
                            codedInputByteBufferNano.readMessage(this.callQualitySummaryUl);
                            continue block60;
                        }
                        case 194: {
                            if (this.callQualitySummaryDl == null) {
                                this.callQualitySummaryDl = new CallQualitySummary();
                            }
                            codedInputByteBufferNano.readMessage(this.callQualitySummaryDl);
                            continue block60;
                        }
                        case 186: {
                            if (this.callQuality == null) {
                                this.callQuality = new CallQuality();
                            }
                            codedInputByteBufferNano.readMessage(this.callQuality);
                            continue block60;
                        }
                        case 176: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                                case 20: 
                            }
                            this.audioCodec = n3;
                            continue block60;
                        }
                        case 168: {
                            this.nitzTimestampMillis = codedInputByteBufferNano.readInt64();
                            continue block60;
                        }
                        case 160: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case -1: 
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                            }
                            this.targetAccessTech = n2;
                            continue block60;
                        }
                        case 152: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case -1: 
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                            }
                            this.srcAccessTech = n3;
                            continue block60;
                        }
                        case 146: {
                            if (this.reasonInfo == null) {
                                this.reasonInfo = new ImsReasonInfo();
                            }
                            codedInputByteBufferNano.readMessage(this.reasonInfo);
                            continue block60;
                        }
                        case 136: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                            }
                            this.imsCommand = n3;
                            continue block60;
                        }
                        case 128: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3 && n2 != 4) {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block60;
                            }
                            this.srvccState = n2;
                            continue block60;
                        }
                        case 120: {
                            this.rilRequestId = codedInputByteBufferNano.readInt32();
                            continue block60;
                        }
                        case 112: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                            }
                            this.rilRequest = n2;
                            continue block60;
                        }
                        case 104: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    switch (n2) {
                                        default: {
                                            codedInputByteBufferNano.rewindToPosition(n3);
                                            this.storeUnknownField(codedInputByteBufferNano, n);
                                            continue block60;
                                        }
                                        case 36: 
                                        case 37: 
                                        case 38: 
                                        case 39: 
                                        case 40: 
                                        case 41: 
                                        case 42: 
                                        case 43: 
                                        case 44: 
                                        case 45: 
                                        case 46: 
                                        case 47: 
                                        case 48: 
                                        case 49: 
                                        case 50: 
                                        case 51: 
                                        case 52: 
                                        case 53: 
                                        case 54: 
                                        case 55: 
                                        case 56: 
                                        case 57: 
                                        case 58: 
                                        case 59: 
                                        case 60: 
                                        case 61: 
                                        case 62: 
                                        case 63: 
                                        case 64: 
                                        case 65: 
                                        case 66: 
                                        case 67: 
                                    }
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                                case 20: 
                                case 21: 
                                case 22: 
                                case 23: 
                                case 24: 
                                case 25: 
                                case 26: 
                                case 27: 
                                case 28: 
                            }
                            this.error = n2;
                            continue block60;
                        }
                        case 98: {
                            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 98);
                            ExtendableMessageNano[] arrextendableMessageNano = this.calls;
                            n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                            arrextendableMessageNano = new RilCall[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.calls, 0, arrextendableMessageNano, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrextendableMessageNano.length - 1) {
                                arrextendableMessageNano[n2] = new RilCall();
                                codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrextendableMessageNano[n2] = new RilCall();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            this.calls = arrextendableMessageNano;
                            continue block60;
                        }
                        case 88: {
                            this.mergedCallIndex = codedInputByteBufferNano.readInt32();
                            continue block60;
                        }
                        case 80: {
                            this.callIndex = codedInputByteBufferNano.readInt32();
                            continue block60;
                        }
                        case 72: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                            }
                            this.callState = n2;
                            continue block60;
                        }
                        case 64: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3) {
                                codedInputByteBufferNano.rewindToPosition(n2);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block60;
                            }
                            this.phoneState = n3;
                            continue block60;
                        }
                        case 58: {
                            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                            ExtendableMessageNano[] arrextendableMessageNano = this.dataCalls;
                            n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                            arrextendableMessageNano = new RilDataCall[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.dataCalls, 0, arrextendableMessageNano, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrextendableMessageNano.length - 1) {
                                arrextendableMessageNano[n2] = new RilDataCall();
                                codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrextendableMessageNano[n2] = new RilDataCall();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            this.dataCalls = arrextendableMessageNano;
                            continue block60;
                        }
                        case 50: {
                            if (this.imsCapabilities == null) {
                                this.imsCapabilities = new ImsCapabilities();
                            }
                            codedInputByteBufferNano.readMessage(this.imsCapabilities);
                            continue block60;
                        }
                        case 42: {
                            if (this.imsConnectionState == null) {
                                this.imsConnectionState = new ImsConnectionState();
                            }
                            codedInputByteBufferNano.readMessage(this.imsConnectionState);
                            continue block60;
                        }
                        case 34: {
                            if (this.serviceState == null) {
                                this.serviceState = new TelephonyServiceState();
                            }
                            codedInputByteBufferNano.readMessage(this.serviceState);
                            continue block60;
                        }
                        case 26: {
                            if (this.settings == null) {
                                this.settings = new TelephonySettings();
                            }
                            codedInputByteBufferNano.readMessage(this.settings);
                            continue block60;
                        }
                        case 16: {
                            int n3 = codedInputByteBufferNano.getPosition();
                            int n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                            }
                            this.delay = n2;
                            continue block60;
                        }
                        case 8: {
                            int n2 = codedInputByteBufferNano.getPosition();
                            int n3 = codedInputByteBufferNano.readInt32();
                            switch (n3) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n2);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block60;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                                case 18: 
                                case 19: 
                                case 20: 
                                case 21: 
                                case 22: 
                                case 23: 
                            }
                            this.type = n3;
                            continue block60;
                        }
                        case 0: 
                    }
                    break;
                } while (true);
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                boolean bl;
                long l;
                Object object;
                int n = this.type;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.delay) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((object = this.settings) != null) {
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
                if ((object = this.serviceState) != null) {
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
                if ((object = this.imsConnectionState) != null) {
                    codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                }
                if ((object = this.imsCapabilities) != null) {
                    codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
                }
                if ((object = this.dataCalls) != null && ((RilDataCall[])object).length > 0) {
                    for (n = 0; n < ((RilDataCall[])(object = this.dataCalls)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                    }
                }
                if ((n = this.phoneState) != 0) {
                    codedOutputByteBufferNano.writeInt32(8, n);
                }
                if ((n = this.callState) != 0) {
                    codedOutputByteBufferNano.writeInt32(9, n);
                }
                if ((n = this.callIndex) != 0) {
                    codedOutputByteBufferNano.writeInt32(10, n);
                }
                if ((n = this.mergedCallIndex) != 0) {
                    codedOutputByteBufferNano.writeInt32(11, n);
                }
                if ((object = this.calls) != null && ((RilDataCall[])object).length > 0) {
                    for (n = 0; n < ((RilDataCall[])(object = this.calls)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(12, (MessageNano)object);
                    }
                }
                if ((n = this.error) != 0) {
                    codedOutputByteBufferNano.writeInt32(13, n);
                }
                if ((n = this.rilRequest) != 0) {
                    codedOutputByteBufferNano.writeInt32(14, n);
                }
                if ((n = this.rilRequestId) != 0) {
                    codedOutputByteBufferNano.writeInt32(15, n);
                }
                if ((n = this.srvccState) != 0) {
                    codedOutputByteBufferNano.writeInt32(16, n);
                }
                if ((n = this.imsCommand) != 0) {
                    codedOutputByteBufferNano.writeInt32(17, n);
                }
                if ((object = this.reasonInfo) != null) {
                    codedOutputByteBufferNano.writeMessage(18, (MessageNano)object);
                }
                if ((n = this.srcAccessTech) != -1) {
                    codedOutputByteBufferNano.writeInt32(19, n);
                }
                if ((n = this.targetAccessTech) != -1) {
                    codedOutputByteBufferNano.writeInt32(20, n);
                }
                if ((l = this.nitzTimestampMillis) != 0L) {
                    codedOutputByteBufferNano.writeInt64(21, l);
                }
                if ((n = this.audioCodec) != 0) {
                    codedOutputByteBufferNano.writeInt32(22, n);
                }
                if ((object = this.callQuality) != null) {
                    codedOutputByteBufferNano.writeMessage(23, (MessageNano)object);
                }
                if ((object = this.callQualitySummaryDl) != null) {
                    codedOutputByteBufferNano.writeMessage(24, (MessageNano)object);
                }
                if ((object = this.callQualitySummaryUl) != null) {
                    codedOutputByteBufferNano.writeMessage(25, (MessageNano)object);
                }
                if (bl = this.isImsEmergencyCall) {
                    codedOutputByteBufferNano.writeBool(26, bl);
                }
                if ((object = this.imsEmergencyNumberInfo) != null) {
                    codedOutputByteBufferNano.writeMessage(27, (MessageNano)object);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface AudioCodec {
                public static final int AUDIO_CODEC_AMR = 1;
                public static final int AUDIO_CODEC_AMR_WB = 2;
                public static final int AUDIO_CODEC_EVRC = 4;
                public static final int AUDIO_CODEC_EVRC_B = 5;
                public static final int AUDIO_CODEC_EVRC_NW = 7;
                public static final int AUDIO_CODEC_EVRC_WB = 6;
                public static final int AUDIO_CODEC_EVS_FB = 20;
                public static final int AUDIO_CODEC_EVS_NB = 17;
                public static final int AUDIO_CODEC_EVS_SWB = 19;
                public static final int AUDIO_CODEC_EVS_WB = 18;
                public static final int AUDIO_CODEC_G711A = 13;
                public static final int AUDIO_CODEC_G711AB = 15;
                public static final int AUDIO_CODEC_G711U = 11;
                public static final int AUDIO_CODEC_G722 = 14;
                public static final int AUDIO_CODEC_G723 = 12;
                public static final int AUDIO_CODEC_G729 = 16;
                public static final int AUDIO_CODEC_GSM_EFR = 8;
                public static final int AUDIO_CODEC_GSM_FR = 9;
                public static final int AUDIO_CODEC_GSM_HR = 10;
                public static final int AUDIO_CODEC_QCELP13K = 3;
                public static final int AUDIO_CODEC_UNKNOWN = 0;
            }

            public static final class CallQuality
            extends ExtendableMessageNano<CallQuality> {
                private static volatile CallQuality[] _emptyArray;
                public int averageRelativeJitterMillis;
                public int averageRoundTripTime;
                public int codecType;
                public int downlinkLevel;
                public int durationInSeconds;
                public int maxRelativeJitterMillis;
                public int rtpPacketsNotReceived;
                public int rtpPacketsReceived;
                public int rtpPacketsTransmitted;
                public int rtpPacketsTransmittedLost;
                public int uplinkLevel;

                public CallQuality() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static CallQuality[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new CallQuality[0];
                        return _emptyArray;
                    }
                }

                public static CallQuality parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new CallQuality().mergeFrom(codedInputByteBufferNano);
                }

                public static CallQuality parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new CallQuality(), arrby);
                }

                public CallQuality clear() {
                    this.downlinkLevel = 0;
                    this.uplinkLevel = 0;
                    this.durationInSeconds = 0;
                    this.rtpPacketsTransmitted = 0;
                    this.rtpPacketsReceived = 0;
                    this.rtpPacketsTransmittedLost = 0;
                    this.rtpPacketsNotReceived = 0;
                    this.averageRelativeJitterMillis = 0;
                    this.maxRelativeJitterMillis = 0;
                    this.averageRoundTripTime = 0;
                    this.codecType = 0;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.downlinkLevel;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    n2 = this.uplinkLevel;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                    }
                    n2 = this.durationInSeconds;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                    }
                    n2 = this.rtpPacketsTransmitted;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
                    }
                    n2 = this.rtpPacketsReceived;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
                    }
                    n2 = this.rtpPacketsTransmittedLost;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(6, n2);
                    }
                    n2 = this.rtpPacketsNotReceived;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(7, n2);
                    }
                    n = this.averageRelativeJitterMillis;
                    n2 = n3;
                    if (n != 0) {
                        n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n);
                    }
                    n3 = this.maxRelativeJitterMillis;
                    n = n2;
                    if (n3 != 0) {
                        n = n2 + CodedOutputByteBufferNano.computeInt32Size(9, n3);
                    }
                    n2 = this.averageRoundTripTime;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(10, n2);
                    }
                    n2 = this.codecType;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(11, n2);
                    }
                    return n;
                }

                @Override
                public CallQuality mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    block23 : do {
                        int n = codedInputByteBufferNano.readTag();
                        switch (n) {
                            default: {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block23;
                                return this;
                            }
                            case 88: {
                                int n2 = codedInputByteBufferNano.getPosition();
                                int n3 = codedInputByteBufferNano.readInt32();
                                switch (n3) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n2);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        continue block23;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                    case 7: 
                                    case 8: 
                                    case 9: 
                                    case 10: 
                                    case 11: 
                                    case 12: 
                                    case 13: 
                                    case 14: 
                                    case 15: 
                                    case 16: 
                                    case 17: 
                                    case 18: 
                                    case 19: 
                                    case 20: 
                                }
                                this.codecType = n3;
                                continue block23;
                            }
                            case 80: {
                                this.averageRoundTripTime = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 72: {
                                this.maxRelativeJitterMillis = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 64: {
                                this.averageRelativeJitterMillis = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 56: {
                                this.rtpPacketsNotReceived = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 48: {
                                this.rtpPacketsTransmittedLost = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 40: {
                                this.rtpPacketsReceived = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 32: {
                                this.rtpPacketsTransmitted = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 24: {
                                this.durationInSeconds = codedInputByteBufferNano.readInt32();
                                continue block23;
                            }
                            case 16: {
                                int n3 = codedInputByteBufferNano.getPosition();
                                int n2 = codedInputByteBufferNano.readInt32();
                                switch (n2) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n3);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        continue block23;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                }
                                this.uplinkLevel = n2;
                                continue block23;
                            }
                            case 8: {
                                int n2 = codedInputByteBufferNano.getPosition();
                                int n3 = codedInputByteBufferNano.readInt32();
                                switch (n3) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n2);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        continue block23;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                }
                                this.downlinkLevel = n3;
                                continue block23;
                            }
                            case 0: 
                        }
                        break;
                    } while (true);
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    int n = this.downlinkLevel;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    if ((n = this.uplinkLevel) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    if ((n = this.durationInSeconds) != 0) {
                        codedOutputByteBufferNano.writeInt32(3, n);
                    }
                    if ((n = this.rtpPacketsTransmitted) != 0) {
                        codedOutputByteBufferNano.writeInt32(4, n);
                    }
                    if ((n = this.rtpPacketsReceived) != 0) {
                        codedOutputByteBufferNano.writeInt32(5, n);
                    }
                    if ((n = this.rtpPacketsTransmittedLost) != 0) {
                        codedOutputByteBufferNano.writeInt32(6, n);
                    }
                    if ((n = this.rtpPacketsNotReceived) != 0) {
                        codedOutputByteBufferNano.writeInt32(7, n);
                    }
                    if ((n = this.averageRelativeJitterMillis) != 0) {
                        codedOutputByteBufferNano.writeInt32(8, n);
                    }
                    if ((n = this.maxRelativeJitterMillis) != 0) {
                        codedOutputByteBufferNano.writeInt32(9, n);
                    }
                    if ((n = this.averageRoundTripTime) != 0) {
                        codedOutputByteBufferNano.writeInt32(10, n);
                    }
                    if ((n = this.codecType) != 0) {
                        codedOutputByteBufferNano.writeInt32(11, n);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }

                public static interface CallQualityLevel {
                    public static final int BAD = 5;
                    public static final int EXCELLENT = 1;
                    public static final int FAIR = 3;
                    public static final int GOOD = 2;
                    public static final int NOT_AVAILABLE = 6;
                    public static final int POOR = 4;
                    public static final int UNDEFINED = 0;
                }

            }

            public static final class CallQualitySummary
            extends ExtendableMessageNano<CallQualitySummary> {
                private static volatile CallQualitySummary[] _emptyArray;
                public SignalStrength bestSsWithBadQuality;
                public SignalStrength bestSsWithGoodQuality;
                public CallQuality snapshotOfBestSsWithBadQuality;
                public CallQuality snapshotOfBestSsWithGoodQuality;
                public CallQuality snapshotOfEnd;
                public CallQuality snapshotOfWorstSsWithBadQuality;
                public CallQuality snapshotOfWorstSsWithGoodQuality;
                public int totalBadQualityDurationInSeconds;
                public int totalDurationWithQualityInformationInSeconds;
                public int totalGoodQualityDurationInSeconds;
                public SignalStrength worstSsWithBadQuality;
                public SignalStrength worstSsWithGoodQuality;

                public CallQualitySummary() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static CallQualitySummary[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new CallQualitySummary[0];
                        return _emptyArray;
                    }
                }

                public static CallQualitySummary parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new CallQualitySummary().mergeFrom(codedInputByteBufferNano);
                }

                public static CallQualitySummary parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new CallQualitySummary(), arrby);
                }

                public CallQualitySummary clear() {
                    this.totalGoodQualityDurationInSeconds = 0;
                    this.totalBadQualityDurationInSeconds = 0;
                    this.totalDurationWithQualityInformationInSeconds = 0;
                    this.snapshotOfWorstSsWithGoodQuality = null;
                    this.snapshotOfBestSsWithGoodQuality = null;
                    this.snapshotOfWorstSsWithBadQuality = null;
                    this.snapshotOfBestSsWithBadQuality = null;
                    this.worstSsWithGoodQuality = null;
                    this.bestSsWithGoodQuality = null;
                    this.worstSsWithBadQuality = null;
                    this.bestSsWithBadQuality = null;
                    this.snapshotOfEnd = null;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.totalGoodQualityDurationInSeconds;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    n2 = this.totalBadQualityDurationInSeconds;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                    }
                    n2 = this.totalDurationWithQualityInformationInSeconds;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                    }
                    ExtendableMessageNano extendableMessageNano = this.snapshotOfWorstSsWithGoodQuality;
                    n = n3;
                    if (extendableMessageNano != null) {
                        n = n3 + CodedOutputByteBufferNano.computeMessageSize(4, extendableMessageNano);
                    }
                    extendableMessageNano = this.snapshotOfBestSsWithGoodQuality;
                    n3 = n;
                    if (extendableMessageNano != null) {
                        n3 = n + CodedOutputByteBufferNano.computeMessageSize(5, extendableMessageNano);
                    }
                    extendableMessageNano = this.snapshotOfWorstSsWithBadQuality;
                    n = n3;
                    if (extendableMessageNano != null) {
                        n = n3 + CodedOutputByteBufferNano.computeMessageSize(6, extendableMessageNano);
                    }
                    extendableMessageNano = this.snapshotOfBestSsWithBadQuality;
                    n3 = n;
                    if (extendableMessageNano != null) {
                        n3 = n + CodedOutputByteBufferNano.computeMessageSize(7, extendableMessageNano);
                    }
                    extendableMessageNano = this.worstSsWithGoodQuality;
                    n2 = n3;
                    if (extendableMessageNano != null) {
                        n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(8, extendableMessageNano);
                    }
                    extendableMessageNano = this.bestSsWithGoodQuality;
                    n = n2;
                    if (extendableMessageNano != null) {
                        n = n2 + CodedOutputByteBufferNano.computeMessageSize(9, extendableMessageNano);
                    }
                    extendableMessageNano = this.worstSsWithBadQuality;
                    n3 = n;
                    if (extendableMessageNano != null) {
                        n3 = n + CodedOutputByteBufferNano.computeMessageSize(10, extendableMessageNano);
                    }
                    extendableMessageNano = this.bestSsWithBadQuality;
                    n = n3;
                    if (extendableMessageNano != null) {
                        n = n3 + CodedOutputByteBufferNano.computeMessageSize(11, extendableMessageNano);
                    }
                    extendableMessageNano = this.snapshotOfEnd;
                    n3 = n;
                    if (extendableMessageNano != null) {
                        n3 = n + CodedOutputByteBufferNano.computeMessageSize(12, extendableMessageNano);
                    }
                    return n3;
                }

                @Override
                public CallQualitySummary mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    block15 : do {
                        int n = codedInputByteBufferNano.readTag();
                        switch (n) {
                            default: {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block15;
                                return this;
                            }
                            case 98: {
                                if (this.snapshotOfEnd == null) {
                                    this.snapshotOfEnd = new CallQuality();
                                }
                                codedInputByteBufferNano.readMessage(this.snapshotOfEnd);
                                continue block15;
                            }
                            case 90: {
                                if (this.bestSsWithBadQuality == null) {
                                    this.bestSsWithBadQuality = new SignalStrength();
                                }
                                codedInputByteBufferNano.readMessage(this.bestSsWithBadQuality);
                                continue block15;
                            }
                            case 82: {
                                if (this.worstSsWithBadQuality == null) {
                                    this.worstSsWithBadQuality = new SignalStrength();
                                }
                                codedInputByteBufferNano.readMessage(this.worstSsWithBadQuality);
                                continue block15;
                            }
                            case 74: {
                                if (this.bestSsWithGoodQuality == null) {
                                    this.bestSsWithGoodQuality = new SignalStrength();
                                }
                                codedInputByteBufferNano.readMessage(this.bestSsWithGoodQuality);
                                continue block15;
                            }
                            case 66: {
                                if (this.worstSsWithGoodQuality == null) {
                                    this.worstSsWithGoodQuality = new SignalStrength();
                                }
                                codedInputByteBufferNano.readMessage(this.worstSsWithGoodQuality);
                                continue block15;
                            }
                            case 58: {
                                if (this.snapshotOfBestSsWithBadQuality == null) {
                                    this.snapshotOfBestSsWithBadQuality = new CallQuality();
                                }
                                codedInputByteBufferNano.readMessage(this.snapshotOfBestSsWithBadQuality);
                                continue block15;
                            }
                            case 50: {
                                if (this.snapshotOfWorstSsWithBadQuality == null) {
                                    this.snapshotOfWorstSsWithBadQuality = new CallQuality();
                                }
                                codedInputByteBufferNano.readMessage(this.snapshotOfWorstSsWithBadQuality);
                                continue block15;
                            }
                            case 42: {
                                if (this.snapshotOfBestSsWithGoodQuality == null) {
                                    this.snapshotOfBestSsWithGoodQuality = new CallQuality();
                                }
                                codedInputByteBufferNano.readMessage(this.snapshotOfBestSsWithGoodQuality);
                                continue block15;
                            }
                            case 34: {
                                if (this.snapshotOfWorstSsWithGoodQuality == null) {
                                    this.snapshotOfWorstSsWithGoodQuality = new CallQuality();
                                }
                                codedInputByteBufferNano.readMessage(this.snapshotOfWorstSsWithGoodQuality);
                                continue block15;
                            }
                            case 24: {
                                this.totalDurationWithQualityInformationInSeconds = codedInputByteBufferNano.readInt32();
                                continue block15;
                            }
                            case 16: {
                                this.totalBadQualityDurationInSeconds = codedInputByteBufferNano.readInt32();
                                continue block15;
                            }
                            case 8: {
                                this.totalGoodQualityDurationInSeconds = codedInputByteBufferNano.readInt32();
                                continue block15;
                            }
                            case 0: 
                        }
                        break;
                    } while (true);
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    ExtendableMessageNano extendableMessageNano;
                    int n = this.totalGoodQualityDurationInSeconds;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    if ((n = this.totalBadQualityDurationInSeconds) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    if ((n = this.totalDurationWithQualityInformationInSeconds) != 0) {
                        codedOutputByteBufferNano.writeInt32(3, n);
                    }
                    if ((extendableMessageNano = this.snapshotOfWorstSsWithGoodQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(4, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.snapshotOfBestSsWithGoodQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(5, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.snapshotOfWorstSsWithBadQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(6, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.snapshotOfBestSsWithBadQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(7, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.worstSsWithGoodQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(8, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.bestSsWithGoodQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(9, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.worstSsWithBadQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(10, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.bestSsWithBadQuality) != null) {
                        codedOutputByteBufferNano.writeMessage(11, extendableMessageNano);
                    }
                    if ((extendableMessageNano = this.snapshotOfEnd) != null) {
                        codedOutputByteBufferNano.writeMessage(12, extendableMessageNano);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }

            public static interface CallState {
                public static final int CALL_ACTIVE = 2;
                public static final int CALL_ALERTING = 5;
                public static final int CALL_DIALING = 4;
                public static final int CALL_DISCONNECTED = 8;
                public static final int CALL_DISCONNECTING = 9;
                public static final int CALL_HOLDING = 3;
                public static final int CALL_IDLE = 1;
                public static final int CALL_INCOMING = 6;
                public static final int CALL_UNKNOWN = 0;
                public static final int CALL_WAITING = 7;
            }

            public static interface ImsCommand {
                public static final int IMS_CMD_ACCEPT = 2;
                public static final int IMS_CMD_CONFERENCE_EXTEND = 9;
                public static final int IMS_CMD_HOLD = 5;
                public static final int IMS_CMD_INVITE_PARTICIPANT = 10;
                public static final int IMS_CMD_MERGE = 7;
                public static final int IMS_CMD_REJECT = 3;
                public static final int IMS_CMD_REMOVE_PARTICIPANT = 11;
                public static final int IMS_CMD_RESUME = 6;
                public static final int IMS_CMD_START = 1;
                public static final int IMS_CMD_TERMINATE = 4;
                public static final int IMS_CMD_UNKNOWN = 0;
                public static final int IMS_CMD_UPDATE = 8;
            }

            public static interface PhoneState {
                public static final int STATE_IDLE = 1;
                public static final int STATE_OFFHOOK = 3;
                public static final int STATE_RINGING = 2;
                public static final int STATE_UNKNOWN = 0;
            }

            public static final class RilCall
            extends ExtendableMessageNano<RilCall> {
                private static volatile RilCall[] _emptyArray;
                public int callEndReason;
                public EmergencyNumberInfo emergencyNumberInfo;
                public int index;
                public boolean isEmergencyCall;
                public boolean isMultiparty;
                public int preciseDisconnectCause;
                public int state;
                public int type;

                public RilCall() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static RilCall[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new RilCall[0];
                        return _emptyArray;
                    }
                }

                public static RilCall parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new RilCall().mergeFrom(codedInputByteBufferNano);
                }

                public static RilCall parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new RilCall(), arrby);
                }

                public RilCall clear() {
                    this.index = 0;
                    this.state = 0;
                    this.type = 0;
                    this.callEndReason = 0;
                    this.isMultiparty = false;
                    this.preciseDisconnectCause = 0;
                    this.isEmergencyCall = false;
                    this.emergencyNumberInfo = null;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.index;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    n2 = this.state;
                    n = n3;
                    if (n2 != 0) {
                        n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                    }
                    n2 = this.type;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                    }
                    n = this.callEndReason;
                    n2 = n3;
                    if (n != 0) {
                        n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n);
                    }
                    boolean bl = this.isMultiparty;
                    n = n2;
                    if (bl) {
                        n = n2 + CodedOutputByteBufferNano.computeBoolSize(5, bl);
                    }
                    n2 = this.preciseDisconnectCause;
                    n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(6, n2);
                    }
                    bl = this.isEmergencyCall;
                    n = n3;
                    if (bl) {
                        n = n3 + CodedOutputByteBufferNano.computeBoolSize(7, bl);
                    }
                    EmergencyNumberInfo emergencyNumberInfo = this.emergencyNumberInfo;
                    n3 = n;
                    if (emergencyNumberInfo != null) {
                        n3 = n + CodedOutputByteBufferNano.computeMessageSize(8, emergencyNumberInfo);
                    }
                    return n3;
                }

                @Override
                public RilCall mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    int n;
                    block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                        if (n != 8) {
                            int n2;
                            int n3;
                            if (n != 16) {
                                if (n != 24) {
                                    if (n != 32) {
                                        if (n != 40) {
                                            if (n != 48) {
                                                if (n != 56) {
                                                    if (n != 66) {
                                                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                                        return this;
                                                    }
                                                    if (this.emergencyNumberInfo == null) {
                                                        this.emergencyNumberInfo = new EmergencyNumberInfo();
                                                    }
                                                    codedInputByteBufferNano.readMessage(this.emergencyNumberInfo);
                                                    continue;
                                                }
                                                this.isEmergencyCall = codedInputByteBufferNano.readBool();
                                                continue;
                                            }
                                            this.preciseDisconnectCause = codedInputByteBufferNano.readInt32();
                                            continue;
                                        }
                                        this.isMultiparty = codedInputByteBufferNano.readBool();
                                        continue;
                                    }
                                    this.callEndReason = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                n3 = codedInputByteBufferNano.getPosition();
                                n2 = codedInputByteBufferNano.readInt32();
                                if (n2 != 0 && n2 != 1 && n2 != 2) {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue;
                                }
                                this.type = n2;
                                continue;
                            }
                            n3 = codedInputByteBufferNano.getPosition();
                            n2 = codedInputByteBufferNano.readInt32();
                            switch (n2) {
                                default: {
                                    codedInputByteBufferNano.rewindToPosition(n3);
                                    this.storeUnknownField(codedInputByteBufferNano, n);
                                    continue block3;
                                }
                                case 0: 
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                            }
                            this.state = n2;
                            continue;
                        }
                        this.index = codedInputByteBufferNano.readInt32();
                    }
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    boolean bl;
                    EmergencyNumberInfo emergencyNumberInfo;
                    int n = this.index;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    if ((n = this.state) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    if ((n = this.type) != 0) {
                        codedOutputByteBufferNano.writeInt32(3, n);
                    }
                    if ((n = this.callEndReason) != 0) {
                        codedOutputByteBufferNano.writeInt32(4, n);
                    }
                    if (bl = this.isMultiparty) {
                        codedOutputByteBufferNano.writeBool(5, bl);
                    }
                    if ((n = this.preciseDisconnectCause) != 0) {
                        codedOutputByteBufferNano.writeInt32(6, n);
                    }
                    if (bl = this.isEmergencyCall) {
                        codedOutputByteBufferNano.writeBool(7, bl);
                    }
                    if ((emergencyNumberInfo = this.emergencyNumberInfo) != null) {
                        codedOutputByteBufferNano.writeMessage(8, emergencyNumberInfo);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }

                public static interface Type {
                    public static final int MO = 1;
                    public static final int MT = 2;
                    public static final int UNKNOWN = 0;
                }

            }

            public static interface RilRequest {
                public static final int RIL_REQUEST_ANSWER = 2;
                public static final int RIL_REQUEST_CDMA_FLASH = 6;
                public static final int RIL_REQUEST_CONFERENCE = 7;
                public static final int RIL_REQUEST_DIAL = 1;
                public static final int RIL_REQUEST_HANGUP = 3;
                public static final int RIL_REQUEST_SET_CALL_WAITING = 4;
                public static final int RIL_REQUEST_SWITCH_HOLDING_AND_ACTIVE = 5;
                public static final int RIL_REQUEST_UNKNOWN = 0;
            }

            public static interface RilSrvccState {
                public static final int HANDOVER_CANCELED = 4;
                public static final int HANDOVER_COMPLETED = 2;
                public static final int HANDOVER_FAILED = 3;
                public static final int HANDOVER_STARTED = 1;
                public static final int HANDOVER_UNKNOWN = 0;
            }

            public static final class SignalStrength
            extends ExtendableMessageNano<SignalStrength> {
                private static volatile SignalStrength[] _emptyArray;
                public int lteSnr;

                public SignalStrength() {
                    this.clear();
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                public static SignalStrength[] emptyArray() {
                    if (_emptyArray != null) return _emptyArray;
                    Object object = InternalNano.LAZY_INIT_LOCK;
                    synchronized (object) {
                        if (_emptyArray != null) return _emptyArray;
                        _emptyArray = new SignalStrength[0];
                        return _emptyArray;
                    }
                }

                public static SignalStrength parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    return new SignalStrength().mergeFrom(codedInputByteBufferNano);
                }

                public static SignalStrength parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                    return MessageNano.mergeFrom(new SignalStrength(), arrby);
                }

                public SignalStrength clear() {
                    this.lteSnr = 0;
                    this.unknownFieldData = null;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    int n2 = this.lteSnr;
                    int n3 = n;
                    if (n2 != 0) {
                        n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                    }
                    return n3;
                }

                @Override
                public SignalStrength mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    int n;
                    while ((n = codedInputByteBufferNano.readTag()) != 0) {
                        if (n != 8) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.lteSnr = codedInputByteBufferNano.readInt32();
                    }
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    int n = this.lteSnr;
                    if (n != 0) {
                        codedOutputByteBufferNano.writeInt32(1, n);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }

            public static interface Type {
                public static final int AUDIO_CODEC = 22;
                public static final int CALL_QUALITY_CHANGED = 23;
                public static final int DATA_CALL_LIST_CHANGED = 5;
                public static final int EVENT_UNKNOWN = 0;
                public static final int IMS_CALL_HANDOVER = 18;
                public static final int IMS_CALL_HANDOVER_FAILED = 19;
                public static final int IMS_CALL_RECEIVE = 15;
                public static final int IMS_CALL_STATE_CHANGED = 16;
                public static final int IMS_CALL_TERMINATED = 17;
                public static final int IMS_CAPABILITIES_CHANGED = 4;
                public static final int IMS_COMMAND = 11;
                public static final int IMS_COMMAND_COMPLETE = 14;
                public static final int IMS_COMMAND_FAILED = 13;
                public static final int IMS_COMMAND_RECEIVED = 12;
                public static final int IMS_CONNECTION_STATE_CHANGED = 3;
                public static final int NITZ_TIME = 21;
                public static final int PHONE_STATE_CHANGED = 20;
                public static final int RIL_CALL_LIST_CHANGED = 10;
                public static final int RIL_CALL_RING = 8;
                public static final int RIL_CALL_SRVCC = 9;
                public static final int RIL_REQUEST = 6;
                public static final int RIL_RESPONSE = 7;
                public static final int RIL_SERVICE_STATE_CHANGED = 2;
                public static final int SETTINGS_CHANGED = 1;
            }

        }

    }

    public static final class TelephonyEvent
    extends ExtendableMessageNano<TelephonyEvent> {
        private static volatile TelephonyEvent[] _emptyArray;
        public ActiveSubscriptionInfo activeSubscriptionInfo;
        public CarrierIdMatching carrierIdMatching;
        public CarrierKeyChange carrierKeyChange;
        public RilDataCall[] dataCalls;
        public int dataStallAction;
        public DataSwitch dataSwitch;
        public RilDeactivateDataCall deactivateDataCall;
        public int enabledModemBitmap;
        public int error;
        public ImsCapabilities imsCapabilities;
        public ImsConnectionState imsConnectionState;
        public ModemRestart modemRestart;
        public int networkValidationState;
        public long nitzTimestampMillis;
        public OnDemandDataSwitch onDemandDataSwitch;
        public int phoneId;
        public TelephonyServiceState serviceState;
        public TelephonySettings settings;
        public RilSetupDataCall setupDataCall;
        public RilSetupDataCallResponse setupDataCallResponse;
        public int[] simState;
        public long timestampMillis;
        public int type;
        public EmergencyNumberInfo updatedEmergencyNumber;

        public TelephonyEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonyEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonyEvent[0];
                return _emptyArray;
            }
        }

        public static TelephonyEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonyEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonyEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonyEvent(), arrby);
        }

        public TelephonyEvent clear() {
            this.timestampMillis = 0L;
            this.phoneId = 0;
            this.type = 0;
            this.settings = null;
            this.serviceState = null;
            this.imsConnectionState = null;
            this.imsCapabilities = null;
            this.dataCalls = RilDataCall.emptyArray();
            this.error = 0;
            this.setupDataCall = null;
            this.setupDataCallResponse = null;
            this.deactivateDataCall = null;
            this.dataStallAction = 0;
            this.modemRestart = null;
            this.nitzTimestampMillis = 0L;
            this.carrierIdMatching = null;
            this.carrierKeyChange = null;
            this.dataSwitch = null;
            this.networkValidationState = 0;
            this.onDemandDataSwitch = null;
            this.simState = WireFormatNano.EMPTY_INT_ARRAY;
            this.activeSubscriptionInfo = null;
            this.enabledModemBitmap = 0;
            this.updatedEmergencyNumber = null;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.timestampMillis;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.phoneId;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.type;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            Object object = this.settings;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
            }
            object = this.serviceState;
            n = n3;
            if (object != null) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
            }
            object = this.imsConnectionState;
            n2 = n;
            if (object != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
            }
            object = this.imsCapabilities;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
            }
            object = this.dataCalls;
            n2 = n;
            if (object != null) {
                n2 = n;
                if (((RilDataCall[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.dataCalls;
                        n2 = n;
                        if (n3 >= ((RilDataCall[])object).length) break;
                        object = object[n3];
                        n2 = n;
                        if (object != null) {
                            n2 = n + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)object);
                        }
                        ++n3;
                        n = n2;
                    } while (true);
                }
            }
            n3 = this.error;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(9, n3);
            }
            object = this.setupDataCall;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(10, (MessageNano)object);
            }
            object = this.setupDataCallResponse;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(11, (MessageNano)object);
            }
            object = this.deactivateDataCall;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(12, (MessageNano)object);
            }
            n3 = this.dataStallAction;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(13, n3);
            }
            object = this.modemRestart;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(14, (MessageNano)object);
            }
            l = this.nitzTimestampMillis;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(15, l);
            }
            object = this.carrierIdMatching;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(16, (MessageNano)object);
            }
            object = this.carrierKeyChange;
            n2 = n;
            if (object != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(17, (MessageNano)object);
            }
            object = this.dataSwitch;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(19, (MessageNano)object);
            }
            n3 = this.networkValidationState;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(20, n3);
            }
            object = this.onDemandDataSwitch;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(21, (MessageNano)object);
            }
            object = this.simState;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((RilDataCall[])object).length > 0) {
                    n3 = 0;
                    for (n2 = 0; n2 < ((RilDataCall[])(object = this.simState)).length; ++n2) {
                        RilDataCall rilDataCall = object[n2];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag((int)rilDataCall);
                    }
                    n3 = n + n3 + ((RilDataCall[])object).length * 2;
                }
            }
            object = this.activeSubscriptionInfo;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(23, (MessageNano)object);
            }
            n3 = this.enabledModemBitmap;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(24, n3);
            }
            object = this.updatedEmergencyNumber;
            n2 = n;
            if (object != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(25, (MessageNano)object);
            }
            return n2;
        }

        @Override
        public TelephonyEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block37 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block37;
                        return this;
                    }
                    case 202: {
                        if (this.updatedEmergencyNumber == null) {
                            this.updatedEmergencyNumber = new EmergencyNumberInfo();
                        }
                        codedInputByteBufferNano.readMessage(this.updatedEmergencyNumber);
                        break;
                    }
                    case 192: {
                        this.enabledModemBitmap = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 186: {
                        if (this.activeSubscriptionInfo == null) {
                            this.activeSubscriptionInfo = new ActiveSubscriptionInfo();
                        }
                        codedInputByteBufferNano.readMessage(this.activeSubscriptionInfo);
                        break;
                    }
                    case 178: {
                        int n2;
                        Object[] arrobject;
                        n = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        int n4 = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            n2 = codedInputByteBufferNano.readInt32();
                            if (n2 != 0 && n2 != 1 && n2 != 2) continue;
                            ++n3;
                        }
                        if (n3 != 0) {
                            codedInputByteBufferNano.rewindToPosition(n4);
                            arrobject = this.simState;
                            n4 = arrobject == null ? 0 : arrobject.length;
                            arrobject = new int[n4 + n3];
                            n3 = n4;
                            if (n4 != 0) {
                                System.arraycopy(this.simState, 0, arrobject, 0, n4);
                                n3 = n4;
                            }
                            while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                n4 = codedInputByteBufferNano.getPosition();
                                n2 = codedInputByteBufferNano.readInt32();
                                if (n2 != 0 && n2 != 1 && n2 != 2) {
                                    codedInputByteBufferNano.rewindToPosition(n4);
                                    this.storeUnknownField(codedInputByteBufferNano, 176);
                                    continue;
                                }
                                arrobject[n3] = n2;
                                ++n3;
                            }
                            this.simState = arrobject;
                        }
                        codedInputByteBufferNano.popLimit(n);
                        break;
                    }
                    case 176: {
                        int n3;
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 176);
                        Object[] arrobject = new int[n2];
                        int n4 = 0;
                        for (n3 = 0; n3 < n2; ++n3) {
                            if (n3 != 0) {
                                codedInputByteBufferNano.readTag();
                            }
                            int n5 = codedInputByteBufferNano.getPosition();
                            int n6 = codedInputByteBufferNano.readInt32();
                            if (n6 != 0 && n6 != 1 && n6 != 2) {
                                codedInputByteBufferNano.rewindToPosition(n5);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue;
                            }
                            arrobject[n4] = n6;
                            ++n4;
                        }
                        if (n4 == 0) continue block37;
                        int[] arrn = this.simState;
                        n3 = arrn == null ? 0 : arrn.length;
                        if (n3 == 0 && n4 == arrobject.length) {
                            this.simState = arrobject;
                            break;
                        }
                        arrn = new int[n3 + n4];
                        if (n3 != 0) {
                            System.arraycopy(this.simState, 0, arrn, 0, n3);
                        }
                        System.arraycopy(arrobject, 0, arrn, n3, n4);
                        this.simState = arrn;
                        break;
                    }
                    case 170: {
                        if (this.onDemandDataSwitch == null) {
                            this.onDemandDataSwitch = new OnDemandDataSwitch();
                        }
                        codedInputByteBufferNano.readMessage(this.onDemandDataSwitch);
                        break;
                    }
                    case 160: {
                        int n3 = codedInputByteBufferNano.getPosition();
                        int n4 = codedInputByteBufferNano.readInt32();
                        if (n4 != 0 && n4 != 1 && n4 != 2 && n4 != 3) {
                            codedInputByteBufferNano.rewindToPosition(n3);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            break;
                        }
                        this.networkValidationState = n4;
                        break;
                    }
                    case 154: {
                        if (this.dataSwitch == null) {
                            this.dataSwitch = new DataSwitch();
                        }
                        codedInputByteBufferNano.readMessage(this.dataSwitch);
                        break;
                    }
                    case 138: {
                        if (this.carrierKeyChange == null) {
                            this.carrierKeyChange = new CarrierKeyChange();
                        }
                        codedInputByteBufferNano.readMessage(this.carrierKeyChange);
                        break;
                    }
                    case 130: {
                        if (this.carrierIdMatching == null) {
                            this.carrierIdMatching = new CarrierIdMatching();
                        }
                        codedInputByteBufferNano.readMessage(this.carrierIdMatching);
                        break;
                    }
                    case 120: {
                        this.nitzTimestampMillis = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 114: {
                        if (this.modemRestart == null) {
                            this.modemRestart = new ModemRestart();
                        }
                        codedInputByteBufferNano.readMessage(this.modemRestart);
                        break;
                    }
                    case 104: {
                        this.dataStallAction = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 98: {
                        if (this.deactivateDataCall == null) {
                            this.deactivateDataCall = new RilDeactivateDataCall();
                        }
                        codedInputByteBufferNano.readMessage(this.deactivateDataCall);
                        break;
                    }
                    case 90: {
                        if (this.setupDataCallResponse == null) {
                            this.setupDataCallResponse = new RilSetupDataCallResponse();
                        }
                        codedInputByteBufferNano.readMessage(this.setupDataCallResponse);
                        break;
                    }
                    case 82: {
                        if (this.setupDataCall == null) {
                            this.setupDataCall = new RilSetupDataCall();
                        }
                        codedInputByteBufferNano.readMessage(this.setupDataCall);
                        break;
                    }
                    case 72: {
                        int n3 = codedInputByteBufferNano.getPosition();
                        int n4 = codedInputByteBufferNano.readInt32();
                        switch (n4) {
                            default: {
                                switch (n4) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n3);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        break block0;
                                    }
                                    case 36: 
                                    case 37: 
                                    case 38: 
                                    case 39: 
                                    case 40: 
                                    case 41: 
                                    case 42: 
                                    case 43: 
                                    case 44: 
                                    case 45: 
                                    case 46: 
                                    case 47: 
                                    case 48: 
                                    case 49: 
                                    case 50: 
                                    case 51: 
                                    case 52: 
                                    case 53: 
                                    case 54: 
                                    case 55: 
                                    case 56: 
                                    case 57: 
                                    case 58: 
                                    case 59: 
                                    case 60: 
                                    case 61: 
                                    case 62: 
                                    case 63: 
                                    case 64: 
                                    case 65: 
                                    case 66: 
                                    case 67: 
                                }
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                            case 9: 
                            case 10: 
                            case 11: 
                            case 12: 
                            case 13: 
                            case 14: 
                            case 15: 
                            case 16: 
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                            case 22: 
                            case 23: 
                            case 24: 
                            case 25: 
                            case 26: 
                            case 27: 
                            case 28: 
                        }
                        this.error = n4;
                        break;
                    }
                    case 66: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        Object[] arrobject = this.dataCalls;
                        int n4 = arrobject == null ? 0 : arrobject.length;
                        arrobject = new RilDataCall[n4 + n3];
                        n3 = n4;
                        if (n4 != 0) {
                            System.arraycopy(this.dataCalls, 0, arrobject, 0, n4);
                            n3 = n4;
                        }
                        while (n3 < arrobject.length - 1) {
                            arrobject[n3] = (int)new RilDataCall();
                            codedInputByteBufferNano.readMessage((MessageNano)arrobject[n3]);
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrobject[n3] = (int)new RilDataCall();
                        codedInputByteBufferNano.readMessage((MessageNano)arrobject[n3]);
                        this.dataCalls = arrobject;
                        break;
                    }
                    case 58: {
                        if (this.imsCapabilities == null) {
                            this.imsCapabilities = new ImsCapabilities();
                        }
                        codedInputByteBufferNano.readMessage(this.imsCapabilities);
                        break;
                    }
                    case 50: {
                        if (this.imsConnectionState == null) {
                            this.imsConnectionState = new ImsConnectionState();
                        }
                        codedInputByteBufferNano.readMessage(this.imsConnectionState);
                        break;
                    }
                    case 42: {
                        if (this.serviceState == null) {
                            this.serviceState = new TelephonyServiceState();
                        }
                        codedInputByteBufferNano.readMessage(this.serviceState);
                        break;
                    }
                    case 34: {
                        if (this.settings == null) {
                            this.settings = new TelephonySettings();
                        }
                        codedInputByteBufferNano.readMessage(this.settings);
                        break;
                    }
                    case 24: {
                        int n4 = codedInputByteBufferNano.getPosition();
                        int n3 = codedInputByteBufferNano.readInt32();
                        switch (n3) {
                            default: {
                                codedInputByteBufferNano.rewindToPosition(n4);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                break block0;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                            case 9: 
                            case 10: 
                            case 11: 
                            case 12: 
                            case 13: 
                            case 14: 
                            case 15: 
                            case 16: 
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                        }
                        this.type = n3;
                        break;
                    }
                    case 16: {
                        this.phoneId = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 8: {
                        this.timestampMillis = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 0: {
                        return this;
                    }
                }
            } while (true);
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object;
            long l = this.timestampMillis;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.phoneId) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.type) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((object = this.settings) != null) {
                codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
            }
            if ((object = this.serviceState) != null) {
                codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
            }
            if ((object = this.imsConnectionState) != null) {
                codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
            }
            if ((object = this.imsCapabilities) != null) {
                codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
            }
            if ((object = this.dataCalls) != null && ((RilDataCall[])object).length > 0) {
                for (n = 0; n < ((RilDataCall[])(object = this.dataCalls)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
                }
            }
            if ((n = this.error) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if ((object = this.setupDataCall) != null) {
                codedOutputByteBufferNano.writeMessage(10, (MessageNano)object);
            }
            if ((object = this.setupDataCallResponse) != null) {
                codedOutputByteBufferNano.writeMessage(11, (MessageNano)object);
            }
            if ((object = this.deactivateDataCall) != null) {
                codedOutputByteBufferNano.writeMessage(12, (MessageNano)object);
            }
            if ((n = this.dataStallAction) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            if ((object = this.modemRestart) != null) {
                codedOutputByteBufferNano.writeMessage(14, (MessageNano)object);
            }
            if ((l = this.nitzTimestampMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(15, l);
            }
            if ((object = this.carrierIdMatching) != null) {
                codedOutputByteBufferNano.writeMessage(16, (MessageNano)object);
            }
            if ((object = this.carrierKeyChange) != null) {
                codedOutputByteBufferNano.writeMessage(17, (MessageNano)object);
            }
            if ((object = this.dataSwitch) != null) {
                codedOutputByteBufferNano.writeMessage(19, (MessageNano)object);
            }
            if ((n = this.networkValidationState) != 0) {
                codedOutputByteBufferNano.writeInt32(20, n);
            }
            if ((object = this.onDemandDataSwitch) != null) {
                codedOutputByteBufferNano.writeMessage(21, (MessageNano)object);
            }
            if ((object = this.simState) != null && ((RilDataCall[])object).length > 0) {
                for (n = 0; n < ((RilDataCall[])(object = this.simState)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(22, (int)object[n]);
                }
            }
            if ((object = this.activeSubscriptionInfo) != null) {
                codedOutputByteBufferNano.writeMessage(23, (MessageNano)object);
            }
            if ((n = this.enabledModemBitmap) != 0) {
                codedOutputByteBufferNano.writeInt32(24, n);
            }
            if ((object = this.updatedEmergencyNumber) != null) {
                codedOutputByteBufferNano.writeMessage(25, (MessageNano)object);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface ApnType {
            public static final int APN_TYPE_CBS = 8;
            public static final int APN_TYPE_DEFAULT = 1;
            public static final int APN_TYPE_DUN = 4;
            public static final int APN_TYPE_EMERGENCY = 10;
            public static final int APN_TYPE_FOTA = 6;
            public static final int APN_TYPE_HIPRI = 5;
            public static final int APN_TYPE_IA = 9;
            public static final int APN_TYPE_IMS = 7;
            public static final int APN_TYPE_MMS = 2;
            public static final int APN_TYPE_SUPL = 3;
            public static final int APN_TYPE_UNKNOWN = 0;
        }

        public static final class CarrierIdMatching
        extends ExtendableMessageNano<CarrierIdMatching> {
            private static volatile CarrierIdMatching[] _emptyArray;
            public int cidTableVersion;
            public CarrierIdMatchingResult result;

            public CarrierIdMatching() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static CarrierIdMatching[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new CarrierIdMatching[0];
                    return _emptyArray;
                }
            }

            public static CarrierIdMatching parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new CarrierIdMatching().mergeFrom(codedInputByteBufferNano);
            }

            public static CarrierIdMatching parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new CarrierIdMatching(), arrby);
            }

            public CarrierIdMatching clear() {
                this.cidTableVersion = 0;
                this.result = null;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.cidTableVersion;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                CarrierIdMatchingResult carrierIdMatchingResult = this.result;
                n = n3;
                if (carrierIdMatchingResult != null) {
                    n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, carrierIdMatchingResult);
                }
                return n;
            }

            @Override
            public CarrierIdMatching mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 18) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        if (this.result == null) {
                            this.result = new CarrierIdMatchingResult();
                        }
                        codedInputByteBufferNano.readMessage(this.result);
                        continue;
                    }
                    this.cidTableVersion = codedInputByteBufferNano.readInt32();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                CarrierIdMatchingResult carrierIdMatchingResult;
                int n = this.cidTableVersion;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((carrierIdMatchingResult = this.result) != null) {
                    codedOutputByteBufferNano.writeMessage(2, carrierIdMatchingResult);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class CarrierIdMatchingResult
        extends ExtendableMessageNano<CarrierIdMatchingResult> {
            private static volatile CarrierIdMatchingResult[] _emptyArray;
            public int carrierId;
            public String gid1;
            public String gid2;
            public String iccidPrefix;
            public String imsiPrefix;
            public String mccmnc;
            public String pnn;
            public String preferApn;
            public String[] privilegeAccessRule;
            public String spn;
            public String unknownGid1;
            public String unknownMccmnc;

            public CarrierIdMatchingResult() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static CarrierIdMatchingResult[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new CarrierIdMatchingResult[0];
                    return _emptyArray;
                }
            }

            public static CarrierIdMatchingResult parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new CarrierIdMatchingResult().mergeFrom(codedInputByteBufferNano);
            }

            public static CarrierIdMatchingResult parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new CarrierIdMatchingResult(), arrby);
            }

            public CarrierIdMatchingResult clear() {
                this.carrierId = 0;
                this.unknownGid1 = "";
                this.unknownMccmnc = "";
                this.mccmnc = "";
                this.gid1 = "";
                this.gid2 = "";
                this.spn = "";
                this.pnn = "";
                this.iccidPrefix = "";
                this.imsiPrefix = "";
                this.privilegeAccessRule = WireFormatNano.EMPTY_STRING_ARRAY;
                this.preferApn = "";
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.carrierId;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n = n3;
                if (!this.unknownGid1.equals("")) {
                    n = n3 + CodedOutputByteBufferNano.computeStringSize(2, this.unknownGid1);
                }
                n3 = n;
                if (!this.unknownMccmnc.equals("")) {
                    n3 = n + CodedOutputByteBufferNano.computeStringSize(3, this.unknownMccmnc);
                }
                n = n3;
                if (!this.mccmnc.equals("")) {
                    n = n3 + CodedOutputByteBufferNano.computeStringSize(4, this.mccmnc);
                }
                n3 = n;
                if (!this.gid1.equals("")) {
                    n3 = n + CodedOutputByteBufferNano.computeStringSize(5, this.gid1);
                }
                n = n3;
                if (!this.gid2.equals("")) {
                    n = n3 + CodedOutputByteBufferNano.computeStringSize(6, this.gid2);
                }
                n2 = n;
                if (!this.spn.equals("")) {
                    n2 = n + CodedOutputByteBufferNano.computeStringSize(7, this.spn);
                }
                n3 = n2;
                if (!this.pnn.equals("")) {
                    n3 = n2 + CodedOutputByteBufferNano.computeStringSize(8, this.pnn);
                }
                n = n3;
                if (!this.iccidPrefix.equals("")) {
                    n = n3 + CodedOutputByteBufferNano.computeStringSize(9, this.iccidPrefix);
                }
                n3 = n;
                if (!this.imsiPrefix.equals("")) {
                    n3 = n + CodedOutputByteBufferNano.computeStringSize(10, this.imsiPrefix);
                }
                Object object = this.privilegeAccessRule;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((String[])object).length > 0) {
                        int n4 = 0;
                        n2 = 0;
                        for (n = 0; n < ((String[])(object = this.privilegeAccessRule)).length; ++n) {
                            object = object[n];
                            int n5 = n4;
                            int n6 = n2;
                            if (object != null) {
                                n5 = n4 + 1;
                                n6 = n2 + CodedOutputByteBufferNano.computeStringSizeNoTag((String)object);
                            }
                            n4 = n5;
                            n2 = n6;
                        }
                        n = n3 + n2 + n4 * 1;
                    }
                }
                n3 = n;
                if (!this.preferApn.equals("")) {
                    n3 = n + CodedOutputByteBufferNano.computeStringSize(12, this.preferApn);
                }
                return n3;
            }

            @Override
            public CarrierIdMatchingResult mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                block15 : do {
                    int n = codedInputByteBufferNano.readTag();
                    switch (n) {
                        default: {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block15;
                            return this;
                        }
                        case 98: {
                            this.preferApn = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 90: {
                            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                            String[] arrstring = this.privilegeAccessRule;
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
                            continue block15;
                        }
                        case 82: {
                            this.imsiPrefix = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 74: {
                            this.iccidPrefix = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 66: {
                            this.pnn = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 58: {
                            this.spn = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 50: {
                            this.gid2 = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 42: {
                            this.gid1 = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 34: {
                            this.mccmnc = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 26: {
                            this.unknownMccmnc = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 18: {
                            this.unknownGid1 = codedInputByteBufferNano.readString();
                            continue block15;
                        }
                        case 8: {
                            this.carrierId = codedInputByteBufferNano.readInt32();
                            continue block15;
                        }
                        case 0: 
                    }
                    break;
                } while (true);
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                Object object;
                int n = this.carrierId;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if (!this.unknownGid1.equals("")) {
                    codedOutputByteBufferNano.writeString(2, this.unknownGid1);
                }
                if (!this.unknownMccmnc.equals("")) {
                    codedOutputByteBufferNano.writeString(3, this.unknownMccmnc);
                }
                if (!this.mccmnc.equals("")) {
                    codedOutputByteBufferNano.writeString(4, this.mccmnc);
                }
                if (!this.gid1.equals("")) {
                    codedOutputByteBufferNano.writeString(5, this.gid1);
                }
                if (!this.gid2.equals("")) {
                    codedOutputByteBufferNano.writeString(6, this.gid2);
                }
                if (!this.spn.equals("")) {
                    codedOutputByteBufferNano.writeString(7, this.spn);
                }
                if (!this.pnn.equals("")) {
                    codedOutputByteBufferNano.writeString(8, this.pnn);
                }
                if (!this.iccidPrefix.equals("")) {
                    codedOutputByteBufferNano.writeString(9, this.iccidPrefix);
                }
                if (!this.imsiPrefix.equals("")) {
                    codedOutputByteBufferNano.writeString(10, this.imsiPrefix);
                }
                if ((object = this.privilegeAccessRule) != null && ((String[])object).length > 0) {
                    for (n = 0; n < ((String[])(object = this.privilegeAccessRule)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeString(11, (String)object);
                    }
                }
                if (!this.preferApn.equals("")) {
                    codedOutputByteBufferNano.writeString(12, this.preferApn);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class CarrierKeyChange
        extends ExtendableMessageNano<CarrierKeyChange> {
            private static volatile CarrierKeyChange[] _emptyArray;
            public boolean isDownloadSuccessful;
            public int keyType;

            public CarrierKeyChange() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static CarrierKeyChange[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new CarrierKeyChange[0];
                    return _emptyArray;
                }
            }

            public static CarrierKeyChange parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new CarrierKeyChange().mergeFrom(codedInputByteBufferNano);
            }

            public static CarrierKeyChange parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new CarrierKeyChange(), arrby);
            }

            public CarrierKeyChange clear() {
                this.keyType = 0;
                this.isDownloadSuccessful = false;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.keyType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                boolean bl = this.isDownloadSuccessful;
                n = n3;
                if (bl) {
                    n = n3 + CodedOutputByteBufferNano.computeBoolSize(2, bl);
                }
                return n;
            }

            @Override
            public CarrierKeyChange mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.isDownloadSuccessful = codedInputByteBufferNano.readBool();
                        continue;
                    }
                    int n2 = codedInputByteBufferNano.getPosition();
                    int n3 = codedInputByteBufferNano.readInt32();
                    if (n3 != 0 && n3 != 1 && n3 != 2) {
                        codedInputByteBufferNano.rewindToPosition(n2);
                        this.storeUnknownField(codedInputByteBufferNano, n);
                        continue;
                    }
                    this.keyType = n3;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                boolean bl;
                int n = this.keyType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if (bl = this.isDownloadSuccessful) {
                    codedOutputByteBufferNano.writeBool(2, bl);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface KeyType {
                public static final int EPDG = 2;
                public static final int UNKNOWN = 0;
                public static final int WLAN = 1;
            }

        }

        public static final class DataSwitch
        extends ExtendableMessageNano<DataSwitch> {
            private static volatile DataSwitch[] _emptyArray;
            public int reason;
            public int state;

            public DataSwitch() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static DataSwitch[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new DataSwitch[0];
                    return _emptyArray;
                }
            }

            public static DataSwitch parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new DataSwitch().mergeFrom(codedInputByteBufferNano);
            }

            public static DataSwitch parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new DataSwitch(), arrby);
            }

            public DataSwitch clear() {
                this.reason = 0;
                this.state = 0;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.reason;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.state;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public DataSwitch mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    int n2;
                    int n3;
                    if (n != 8) {
                        if (n != 16) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        n3 = codedInputByteBufferNano.getPosition();
                        n2 = codedInputByteBufferNano.readInt32();
                        if (n2 != 0 && n2 != 1 && n2 != 2) {
                            codedInputByteBufferNano.rewindToPosition(n3);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        this.state = n2;
                        continue;
                    }
                    n3 = codedInputByteBufferNano.getPosition();
                    n2 = codedInputByteBufferNano.readInt32();
                    if (n2 != 0 && n2 != 1 && n2 != 2 && n2 != 3) {
                        codedInputByteBufferNano.rewindToPosition(n3);
                        this.storeUnknownField(codedInputByteBufferNano, n);
                        continue;
                    }
                    this.reason = n2;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.reason;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.state) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface Reason {
                public static final int DATA_SWITCH_REASON_CBRS = 3;
                public static final int DATA_SWITCH_REASON_IN_CALL = 2;
                public static final int DATA_SWITCH_REASON_MANUAL = 1;
                public static final int DATA_SWITCH_REASON_UNKNOWN = 0;
            }

        }

        public static interface EventState {
            public static final int EVENT_STATE_END = 2;
            public static final int EVENT_STATE_START = 1;
            public static final int EVENT_STATE_UNKNOWN = 0;
        }

        public static final class ModemRestart
        extends ExtendableMessageNano<ModemRestart> {
            private static volatile ModemRestart[] _emptyArray;
            public String basebandVersion;
            public String reason;

            public ModemRestart() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static ModemRestart[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new ModemRestart[0];
                    return _emptyArray;
                }
            }

            public static ModemRestart parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ModemRestart().mergeFrom(codedInputByteBufferNano);
            }

            public static ModemRestart parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new ModemRestart(), arrby);
            }

            public ModemRestart clear() {
                this.basebandVersion = "";
                this.reason = "";
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n;
                int n2 = n = super.computeSerializedSize();
                if (!this.basebandVersion.equals("")) {
                    n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.basebandVersion);
                }
                n = n2;
                if (!this.reason.equals("")) {
                    n = n2 + CodedOutputByteBufferNano.computeStringSize(2, this.reason);
                }
                return n;
            }

            @Override
            public ModemRestart mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 10) {
                        if (n != 18) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.reason = codedInputByteBufferNano.readString();
                        continue;
                    }
                    this.basebandVersion = codedInputByteBufferNano.readString();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if (!this.basebandVersion.equals("")) {
                    codedOutputByteBufferNano.writeString(1, this.basebandVersion);
                }
                if (!this.reason.equals("")) {
                    codedOutputByteBufferNano.writeString(2, this.reason);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static interface NetworkValidationState {
            public static final int NETWORK_VALIDATION_STATE_AVAILABLE = 1;
            public static final int NETWORK_VALIDATION_STATE_FAILED = 2;
            public static final int NETWORK_VALIDATION_STATE_PASSED = 3;
            public static final int NETWORK_VALIDATION_STATE_UNKNOWN = 0;
        }

        public static final class OnDemandDataSwitch
        extends ExtendableMessageNano<OnDemandDataSwitch> {
            private static volatile OnDemandDataSwitch[] _emptyArray;
            public int apn;
            public int state;

            public OnDemandDataSwitch() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static OnDemandDataSwitch[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new OnDemandDataSwitch[0];
                    return _emptyArray;
                }
            }

            public static OnDemandDataSwitch parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new OnDemandDataSwitch().mergeFrom(codedInputByteBufferNano);
            }

            public static OnDemandDataSwitch parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new OnDemandDataSwitch(), arrby);
            }

            public OnDemandDataSwitch clear() {
                this.apn = 0;
                this.state = 0;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.apn;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.state;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public OnDemandDataSwitch mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    int n2;
                    int n3;
                    if (n != 8) {
                        if (n != 16) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        n3 = codedInputByteBufferNano.getPosition();
                        n2 = codedInputByteBufferNano.readInt32();
                        if (n2 != 0 && n2 != 1 && n2 != 2) {
                            codedInputByteBufferNano.rewindToPosition(n3);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        this.state = n2;
                        continue;
                    }
                    n2 = codedInputByteBufferNano.getPosition();
                    n3 = codedInputByteBufferNano.readInt32();
                    switch (n3) {
                        default: {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue block3;
                        }
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                        case 7: 
                        case 8: 
                        case 9: 
                        case 10: 
                    }
                    this.apn = n3;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.apn;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.state) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class RilDeactivateDataCall
        extends ExtendableMessageNano<RilDeactivateDataCall> {
            private static volatile RilDeactivateDataCall[] _emptyArray;
            public int cid;
            public int reason;

            public RilDeactivateDataCall() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RilDeactivateDataCall[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RilDeactivateDataCall[0];
                    return _emptyArray;
                }
            }

            public static RilDeactivateDataCall parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RilDeactivateDataCall().mergeFrom(codedInputByteBufferNano);
            }

            public static RilDeactivateDataCall parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RilDeactivateDataCall(), arrby);
            }

            public RilDeactivateDataCall clear() {
                this.cid = 0;
                this.reason = 0;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.cid;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.reason;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public RilDeactivateDataCall mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        int n2 = codedInputByteBufferNano.getPosition();
                        int n3 = codedInputByteBufferNano.readInt32();
                        if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3 && n3 != 4) {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        this.reason = n3;
                        continue;
                    }
                    this.cid = codedInputByteBufferNano.readInt32();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.cid;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.reason) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface DeactivateReason {
                public static final int DEACTIVATE_REASON_HANDOVER = 4;
                public static final int DEACTIVATE_REASON_NONE = 1;
                public static final int DEACTIVATE_REASON_PDP_RESET = 3;
                public static final int DEACTIVATE_REASON_RADIO_OFF = 2;
                public static final int DEACTIVATE_REASON_UNKNOWN = 0;
            }

        }

        public static final class RilSetupDataCall
        extends ExtendableMessageNano<RilSetupDataCall> {
            private static volatile RilSetupDataCall[] _emptyArray;
            public String apn;
            public int dataProfile;
            public int rat;
            public int type;

            public RilSetupDataCall() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RilSetupDataCall[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RilSetupDataCall[0];
                    return _emptyArray;
                }
            }

            public static RilSetupDataCall parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RilSetupDataCall().mergeFrom(codedInputByteBufferNano);
            }

            public static RilSetupDataCall parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RilSetupDataCall(), arrby);
            }

            public RilSetupDataCall clear() {
                this.rat = -1;
                this.dataProfile = 0;
                this.apn = "";
                this.type = 0;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.rat;
                int n3 = n;
                if (n2 != -1) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.dataProfile;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                n3 = n;
                if (!this.apn.equals("")) {
                    n3 = n + CodedOutputByteBufferNano.computeStringSize(3, this.apn);
                }
                n2 = this.type;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
                }
                return n;
            }

            @Override
            public RilSetupDataCall mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block9 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    int n2;
                    int n3;
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 26) {
                                if (n != 32) {
                                    if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                n3 = codedInputByteBufferNano.getPosition();
                                n2 = codedInputByteBufferNano.readInt32();
                                switch (n2) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n3);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        continue block9;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                }
                                this.type = n2;
                                continue;
                            }
                            this.apn = codedInputByteBufferNano.readString();
                            continue;
                        }
                        n3 = codedInputByteBufferNano.getPosition();
                        n2 = codedInputByteBufferNano.readInt32();
                        switch (n2) {
                            default: {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block9;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                        }
                        this.dataProfile = n2;
                        continue;
                    }
                    n2 = codedInputByteBufferNano.getPosition();
                    n3 = codedInputByteBufferNano.readInt32();
                    switch (n3) {
                        default: {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue block9;
                        }
                        case -1: 
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                        case 7: 
                        case 8: 
                        case 9: 
                        case 10: 
                        case 11: 
                        case 12: 
                        case 13: 
                        case 14: 
                        case 15: 
                        case 16: 
                        case 17: 
                        case 18: 
                        case 19: 
                    }
                    this.rat = n3;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.rat;
                if (n != -1) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.dataProfile) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if (!this.apn.equals("")) {
                    codedOutputByteBufferNano.writeString(3, this.apn);
                }
                if ((n = this.type) != 0) {
                    codedOutputByteBufferNano.writeInt32(4, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface RilDataProfile {
                public static final int RIL_DATA_PROFILE_CBS = 5;
                public static final int RIL_DATA_PROFILE_DEFAULT = 1;
                public static final int RIL_DATA_PROFILE_FOTA = 4;
                public static final int RIL_DATA_PROFILE_IMS = 3;
                public static final int RIL_DATA_PROFILE_INVALID = 7;
                public static final int RIL_DATA_PROFILE_OEM_BASE = 6;
                public static final int RIL_DATA_PROFILE_TETHERED = 2;
                public static final int RIL_DATA_UNKNOWN = 0;
            }

        }

        public static final class RilSetupDataCallResponse
        extends ExtendableMessageNano<RilSetupDataCallResponse> {
            private static volatile RilSetupDataCallResponse[] _emptyArray;
            public RilDataCall call;
            public int status;
            public int suggestedRetryTimeMillis;

            public RilSetupDataCallResponse() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RilSetupDataCallResponse[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RilSetupDataCallResponse[0];
                    return _emptyArray;
                }
            }

            public static RilSetupDataCallResponse parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RilSetupDataCallResponse().mergeFrom(codedInputByteBufferNano);
            }

            public static RilSetupDataCallResponse parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RilSetupDataCallResponse(), arrby);
            }

            public RilSetupDataCallResponse clear() {
                this.status = 0;
                this.suggestedRetryTimeMillis = 0;
                this.call = null;
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.status;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.suggestedRetryTimeMillis;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                RilDataCall rilDataCall = this.call;
                n3 = n;
                if (rilDataCall != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, rilDataCall);
                }
                return n3;
            }

            @Override
            public RilSetupDataCallResponse mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block18 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 26) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            if (this.call == null) {
                                this.call = new RilDataCall();
                            }
                            codedInputByteBufferNano.readMessage(this.call);
                            continue;
                        }
                        this.suggestedRetryTimeMillis = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    int n2 = codedInputByteBufferNano.getPosition();
                    int n3 = codedInputByteBufferNano.readInt32();
                    if (n3 != 8 && n3 != 14 && n3 != 48 && n3 != 81 && n3 != 65535 && n3 != 65 && n3 != 66 && n3 != 127 && n3 != 128) {
                        switch (n3) {
                            default: {
                                switch (n3) {
                                    default: {
                                        switch (n3) {
                                            default: {
                                                switch (n3) {
                                                    default: {
                                                        switch (n3) {
                                                            default: {
                                                                switch (n3) {
                                                                    default: {
                                                                        codedInputByteBufferNano.rewindToPosition(n2);
                                                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                                                        continue block18;
                                                                    }
                                                                    case 2000: 
                                                                    case 2001: 
                                                                    case 2002: 
                                                                    case 2003: 
                                                                    case 2004: 
                                                                    case 2005: 
                                                                    case 2006: 
                                                                    case 2007: 
                                                                    case 2008: 
                                                                    case 2009: 
                                                                    case 2010: 
                                                                    case 2011: 
                                                                    case 2012: 
                                                                    case 2013: 
                                                                    case 2014: 
                                                                    case 2015: 
                                                                    case 2016: 
                                                                    case 2017: 
                                                                    case 2018: 
                                                                    case 2019: 
                                                                    case 2020: 
                                                                    case 2021: 
                                                                    case 2022: 
                                                                    case 2023: 
                                                                    case 2024: 
                                                                    case 2025: 
                                                                    case 2026: 
                                                                    case 2027: 
                                                                    case 2028: 
                                                                    case 2029: 
                                                                    case 2030: 
                                                                    case 2031: 
                                                                    case 2032: 
                                                                    case 2033: 
                                                                    case 2034: 
                                                                    case 2035: 
                                                                    case 2036: 
                                                                    case 2037: 
                                                                    case 2038: 
                                                                    case 2039: 
                                                                    case 2040: 
                                                                    case 2041: 
                                                                    case 2042: 
                                                                    case 2043: 
                                                                    case 2044: 
                                                                    case 2045: 
                                                                    case 2046: 
                                                                    case 2047: 
                                                                    case 2048: 
                                                                    case 2049: 
                                                                    case 2050: 
                                                                    case 2051: 
                                                                    case 2052: 
                                                                    case 2053: 
                                                                    case 2054: 
                                                                    case 2055: 
                                                                    case 2056: 
                                                                    case 2057: 
                                                                    case 2058: 
                                                                    case 2059: 
                                                                    case 2060: 
                                                                    case 2061: 
                                                                    case 2062: 
                                                                    case 2063: 
                                                                    case 2064: 
                                                                    case 2065: 
                                                                    case 2066: 
                                                                    case 2067: 
                                                                    case 2068: 
                                                                    case 2069: 
                                                                    case 2070: 
                                                                    case 2071: 
                                                                    case 2072: 
                                                                    case 2073: 
                                                                    case 2074: 
                                                                    case 2075: 
                                                                    case 2076: 
                                                                    case 2077: 
                                                                    case 2078: 
                                                                    case 2079: 
                                                                    case 2080: 
                                                                    case 2081: 
                                                                    case 2082: 
                                                                    case 2083: 
                                                                    case 2084: 
                                                                    case 2085: 
                                                                    case 2086: 
                                                                    case 2087: 
                                                                    case 2088: 
                                                                    case 2089: 
                                                                    case 2090: 
                                                                    case 2091: 
                                                                    case 2092: 
                                                                    case 2093: 
                                                                    case 2094: 
                                                                    case 2095: 
                                                                    case 2096: 
                                                                    case 2097: 
                                                                    case 2098: 
                                                                    case 2099: 
                                                                    case 2100: 
                                                                    case 2101: 
                                                                    case 2102: 
                                                                    case 2103: 
                                                                    case 2104: 
                                                                    case 2105: 
                                                                    case 2106: 
                                                                    case 2107: 
                                                                    case 2108: 
                                                                    case 2109: 
                                                                    case 2110: 
                                                                    case 2111: 
                                                                    case 2112: 
                                                                    case 2113: 
                                                                    case 2114: 
                                                                    case 2115: 
                                                                    case 2116: 
                                                                    case 2117: 
                                                                    case 2118: 
                                                                    case 2119: 
                                                                    case 2120: 
                                                                    case 2121: 
                                                                    case 2122: 
                                                                    case 2123: 
                                                                    case 2124: 
                                                                    case 2125: 
                                                                    case 2126: 
                                                                    case 2127: 
                                                                    case 2128: 
                                                                    case 2129: 
                                                                    case 2130: 
                                                                    case 2131: 
                                                                    case 2132: 
                                                                    case 2133: 
                                                                    case 2134: 
                                                                    case 2135: 
                                                                    case 2136: 
                                                                    case 2137: 
                                                                    case 2138: 
                                                                    case 2139: 
                                                                    case 2140: 
                                                                    case 2141: 
                                                                    case 2142: 
                                                                    case 2143: 
                                                                    case 2144: 
                                                                    case 2145: 
                                                                    case 2146: 
                                                                    case 2147: 
                                                                    case 2148: 
                                                                    case 2149: 
                                                                    case 2150: 
                                                                    case 2151: 
                                                                    case 2152: 
                                                                    case 2153: 
                                                                    case 2154: 
                                                                    case 2155: 
                                                                    case 2156: 
                                                                    case 2157: 
                                                                    case 2158: 
                                                                    case 2159: 
                                                                    case 2160: 
                                                                    case 2161: 
                                                                    case 2162: 
                                                                    case 2163: 
                                                                    case 2164: 
                                                                    case 2165: 
                                                                    case 2166: 
                                                                    case 2167: 
                                                                    case 2168: 
                                                                    case 2169: 
                                                                    case 2170: 
                                                                    case 2171: 
                                                                    case 2172: 
                                                                    case 2173: 
                                                                    case 2174: 
                                                                    case 2175: 
                                                                    case 2176: 
                                                                    case 2177: 
                                                                    case 2178: 
                                                                    case 2179: 
                                                                    case 2180: 
                                                                    case 2181: 
                                                                    case 2182: 
                                                                    case 2183: 
                                                                    case 2184: 
                                                                    case 2185: 
                                                                    case 2186: 
                                                                    case 2187: 
                                                                    case 2188: 
                                                                    case 2189: 
                                                                    case 2190: 
                                                                    case 2191: 
                                                                    case 2192: 
                                                                    case 2193: 
                                                                    case 2194: 
                                                                    case 2195: 
                                                                    case 2196: 
                                                                    case 2197: 
                                                                    case 2198: 
                                                                    case 2199: 
                                                                    case 2200: 
                                                                    case 2201: 
                                                                    case 2202: 
                                                                    case 2203: 
                                                                    case 2204: 
                                                                    case 2205: 
                                                                    case 2206: 
                                                                    case 2207: 
                                                                    case 2208: 
                                                                    case 2209: 
                                                                    case 2210: 
                                                                    case 2211: 
                                                                    case 2212: 
                                                                    case 2213: 
                                                                    case 2214: 
                                                                    case 2215: 
                                                                    case 2216: 
                                                                    case 2217: 
                                                                    case 2218: 
                                                                    case 2219: 
                                                                    case 2220: 
                                                                    case 2221: 
                                                                    case 2222: 
                                                                    case 2223: 
                                                                    case 2224: 
                                                                    case 2225: 
                                                                    case 2226: 
                                                                    case 2227: 
                                                                    case 2228: 
                                                                    case 2229: 
                                                                    case 2230: 
                                                                    case 2231: 
                                                                    case 2232: 
                                                                    case 2233: 
                                                                    case 2234: 
                                                                    case 2235: 
                                                                    case 2236: 
                                                                    case 2237: 
                                                                    case 2238: 
                                                                    case 2239: 
                                                                    case 2240: 
                                                                    case 2241: 
                                                                    case 2242: 
                                                                    case 2243: 
                                                                    case 2244: 
                                                                    case 2245: 
                                                                    case 2246: 
                                                                    case 2247: 
                                                                    case 2248: 
                                                                    case 2249: 
                                                                    case 2250: 
                                                                    case 2251: 
                                                                }
                                                            }
                                                            case 111: 
                                                            case 112: 
                                                            case 113: 
                                                            case 114: 
                                                            case 115: 
                                                            case 116: 
                                                            case 117: 
                                                            case 118: 
                                                            case 119: 
                                                            case 120: 
                                                            case 121: 
                                                            case 122: 
                                                            case 123: 
                                                            case 124: 
                                                        }
                                                    }
                                                    case 95: 
                                                    case 96: 
                                                    case 97: 
                                                    case 98: 
                                                    case 99: 
                                                    case 100: 
                                                    case 101: 
                                                }
                                            }
                                            case 50: 
                                            case 51: 
                                            case 52: 
                                            case 53: 
                                            case 54: 
                                            case 55: 
                                            case 56: 
                                            case 57: 
                                            case 58: 
                                            case 59: 
                                            case 60: 
                                        }
                                    }
                                    case 25: 
                                    case 26: 
                                    case 27: 
                                    case 28: 
                                    case 29: 
                                    case 30: 
                                    case 31: 
                                    case 32: 
                                    case 33: 
                                    case 34: 
                                    case 35: 
                                    case 36: 
                                    case 37: 
                                    case 38: 
                                    case 39: 
                                    case 40: 
                                    case 41: 
                                    case 42: 
                                    case 43: 
                                    case 44: 
                                    case 45: 
                                    case 46: 
                                }
                            }
                            case -6: 
                            case -5: 
                            case -4: 
                            case -3: 
                            case -2: 
                            case -1: 
                            case 0: 
                            case 1: 
                        }
                    }
                    this.status = n3;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                RilDataCall rilDataCall;
                int n = this.status;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.suggestedRetryTimeMillis) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((rilDataCall = this.call) != null) {
                    codedOutputByteBufferNano.writeMessage(3, rilDataCall);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static interface RilDataCallFailCause {
                public static final int PDP_FAIL_ACCESS_ATTEMPT_ALREADY_IN_PROGRESS = 2219;
                public static final int PDP_FAIL_ACCESS_BLOCK = 2087;
                public static final int PDP_FAIL_ACCESS_BLOCK_ALL = 2088;
                public static final int PDP_FAIL_ACCESS_CLASS_DSAC_REJECTION = 2108;
                public static final int PDP_FAIL_ACCESS_CONTROL_LIST_CHECK_FAILURE = 2128;
                public static final int PDP_FAIL_ACTIVATION_REJECTED_BCM_VIOLATION = 48;
                public static final int PDP_FAIL_ACTIVATION_REJECT_GGSN = 30;
                public static final int PDP_FAIL_ACTIVATION_REJECT_UNSPECIFIED = 31;
                public static final int PDP_FAIL_APN_DISABLED = 2045;
                public static final int PDP_FAIL_APN_DISALLOWED_ON_ROAMING = 2059;
                public static final int PDP_FAIL_APN_MISMATCH = 2054;
                public static final int PDP_FAIL_APN_PARAMETERS_CHANGED = 2060;
                public static final int PDP_FAIL_APN_PENDING_HANDOVER = 2041;
                public static final int PDP_FAIL_APN_TYPE_CONFLICT = 112;
                public static final int PDP_FAIL_AUTH_FAILURE_ON_EMERGENCY_CALL = 122;
                public static final int PDP_FAIL_BEARER_HANDLING_NOT_SUPPORTED = 60;
                public static final int PDP_FAIL_CALL_DISALLOWED_IN_ROAMING = 2068;
                public static final int PDP_FAIL_CALL_PREEMPT_BY_EMERGENCY_APN = 127;
                public static final int PDP_FAIL_CANNOT_ENCODE_OTA_MESSAGE = 2159;
                public static final int PDP_FAIL_CDMA_ALERT_STOP = 2077;
                public static final int PDP_FAIL_CDMA_INCOMING_CALL = 2076;
                public static final int PDP_FAIL_CDMA_INTERCEPT = 2073;
                public static final int PDP_FAIL_CDMA_LOCK = 2072;
                public static final int PDP_FAIL_CDMA_RELEASE_DUE_TO_SO_REJECTION = 2075;
                public static final int PDP_FAIL_CDMA_REORDER = 2074;
                public static final int PDP_FAIL_CDMA_RETRY_ORDER = 2086;
                public static final int PDP_FAIL_CHANNEL_ACQUISITION_FAILURE = 2078;
                public static final int PDP_FAIL_CLOSE_IN_PROGRESS = 2030;
                public static final int PDP_FAIL_COLLISION_WITH_NETWORK_INITIATED_REQUEST = 56;
                public static final int PDP_FAIL_COMPANION_IFACE_IN_USE = 118;
                public static final int PDP_FAIL_CONCURRENT_SERVICES_INCOMPATIBLE = 2083;
                public static final int PDP_FAIL_CONCURRENT_SERVICES_NOT_ALLOWED = 2091;
                public static final int PDP_FAIL_CONCURRENT_SERVICE_NOT_SUPPORTED_BY_BASE_STATION = 2080;
                public static final int PDP_FAIL_CONDITIONAL_IE_ERROR = 100;
                public static final int PDP_FAIL_CONGESTION = 2106;
                public static final int PDP_FAIL_CONNECTION_RELEASED = 2113;
                public static final int PDP_FAIL_CS_DOMAIN_NOT_AVAILABLE = 2181;
                public static final int PDP_FAIL_CS_FALLBACK_CALL_ESTABLISHMENT_NOT_ALLOWED = 2188;
                public static final int PDP_FAIL_DATA_PLAN_EXPIRED = 2198;
                public static final int PDP_FAIL_DATA_REGISTRATION_FAIL = -2;
                public static final int PDP_FAIL_DATA_ROAMING_SETTINGS_DISABLED = 2064;
                public static final int PDP_FAIL_DATA_SETTINGS_DISABLED = 2063;
                public static final int PDP_FAIL_DBM_OR_SMS_IN_PROGRESS = 2211;
                public static final int PDP_FAIL_DDS_SWITCHED = 2065;
                public static final int PDP_FAIL_DDS_SWITCH_IN_PROGRESS = 2067;
                public static final int PDP_FAIL_DRB_RELEASED_BY_RRC = 2112;
                public static final int PDP_FAIL_DS_EXPLICIT_DEACTIVATION = 2125;
                public static final int PDP_FAIL_DUAL_SWITCH = 2227;
                public static final int PDP_FAIL_DUN_CALL_DISALLOWED = 2056;
                public static final int PDP_FAIL_DUPLICATE_BEARER_ID = 2118;
                public static final int PDP_FAIL_EHRPD_TO_HRPD_FALLBACK = 2049;
                public static final int PDP_FAIL_EMBMS_NOT_ENABLED = 2193;
                public static final int PDP_FAIL_EMBMS_REGULAR_DEACTIVATION = 2195;
                public static final int PDP_FAIL_EMERGENCY_IFACE_ONLY = 116;
                public static final int PDP_FAIL_EMERGENCY_MODE = 2221;
                public static final int PDP_FAIL_EMM_ACCESS_BARRED = 115;
                public static final int PDP_FAIL_EMM_ACCESS_BARRED_INFINITE_RETRY = 121;
                public static final int PDP_FAIL_EMM_ATTACH_FAILED = 2115;
                public static final int PDP_FAIL_EMM_ATTACH_STARTED = 2116;
                public static final int PDP_FAIL_EMM_DETACHED = 2114;
                public static final int PDP_FAIL_EMM_T3417_EXPIRED = 2130;
                public static final int PDP_FAIL_EMM_T3417_EXT_EXPIRED = 2131;
                public static final int PDP_FAIL_EPS_SERVICES_AND_NON_EPS_SERVICES_NOT_ALLOWED = 2178;
                public static final int PDP_FAIL_EPS_SERVICES_NOT_ALLOWED_IN_PLMN = 2179;
                public static final int PDP_FAIL_ERROR_UNSPECIFIED = 65535;
                public static final int PDP_FAIL_ESM_BAD_OTA_MESSAGE = 2122;
                public static final int PDP_FAIL_ESM_BEARER_DEACTIVATED_TO_SYNC_WITH_NETWORK = 2120;
                public static final int PDP_FAIL_ESM_COLLISION_SCENARIOS = 2119;
                public static final int PDP_FAIL_ESM_CONTEXT_TRANSFERRED_DUE_TO_IRAT = 2124;
                public static final int PDP_FAIL_ESM_DOWNLOAD_SERVER_REJECTED_THE_CALL = 2123;
                public static final int PDP_FAIL_ESM_FAILURE = 2182;
                public static final int PDP_FAIL_ESM_INFO_NOT_RECEIVED = 53;
                public static final int PDP_FAIL_ESM_LOCAL_CAUSE_NONE = 2126;
                public static final int PDP_FAIL_ESM_NW_ACTIVATED_DED_BEARER_WITH_ID_OF_DEF_BEARER = 2121;
                public static final int PDP_FAIL_ESM_PROCEDURE_TIME_OUT = 2155;
                public static final int PDP_FAIL_ESM_UNKNOWN_EPS_BEARER_CONTEXT = 2111;
                public static final int PDP_FAIL_EVDO_CONNECTION_DENY_BY_BILLING_OR_AUTHENTICATION_FAILURE = 2201;
                public static final int PDP_FAIL_EVDO_CONNECTION_DENY_BY_GENERAL_OR_NETWORK_BUSY = 2200;
                public static final int PDP_FAIL_EVDO_HDR_CHANGED = 2202;
                public static final int PDP_FAIL_EVDO_HDR_CONNECTION_SETUP_TIMEOUT = 2206;
                public static final int PDP_FAIL_EVDO_HDR_EXITED = 2203;
                public static final int PDP_FAIL_EVDO_HDR_NO_SESSION = 2204;
                public static final int PDP_FAIL_EVDO_USING_GPS_FIX_INSTEAD_OF_HDR_CALL = 2205;
                public static final int PDP_FAIL_FADE = 2217;
                public static final int PDP_FAIL_FAILED_TO_ACQUIRE_COLOCATED_HDR = 2207;
                public static final int PDP_FAIL_FEATURE_NOT_SUPP = 40;
                public static final int PDP_FAIL_FILTER_SEMANTIC_ERROR = 44;
                public static final int PDP_FAIL_FILTER_SYTAX_ERROR = 45;
                public static final int PDP_FAIL_FORBIDDEN_APN_NAME = 2066;
                public static final int PDP_FAIL_GPRS_SERVICES_AND_NON_GPRS_SERVICES_NOT_ALLOWED = 2097;
                public static final int PDP_FAIL_GPRS_SERVICES_NOT_ALLOWED = 2098;
                public static final int PDP_FAIL_GPRS_SERVICES_NOT_ALLOWED_IN_THIS_PLMN = 2103;
                public static final int PDP_FAIL_HANDOFF_PREFERENCE_CHANGED = 2251;
                public static final int PDP_FAIL_HDR_ACCESS_FAILURE = 2213;
                public static final int PDP_FAIL_HDR_FADE = 2212;
                public static final int PDP_FAIL_HDR_NO_LOCK_GRANTED = 2210;
                public static final int PDP_FAIL_IFACE_AND_POL_FAMILY_MISMATCH = 120;
                public static final int PDP_FAIL_IFACE_MISMATCH = 117;
                public static final int PDP_FAIL_ILLEGAL_ME = 2096;
                public static final int PDP_FAIL_ILLEGAL_MS = 2095;
                public static final int PDP_FAIL_IMEI_NOT_ACCEPTED = 2177;
                public static final int PDP_FAIL_IMPLICITLY_DETACHED = 2100;
                public static final int PDP_FAIL_IMSI_UNKNOWN_IN_HOME_SUBSCRIBER_SERVER = 2176;
                public static final int PDP_FAIL_INCOMING_CALL_REJECTED = 2092;
                public static final int PDP_FAIL_INSUFFICIENT_RESOURCES = 26;
                public static final int PDP_FAIL_INTERFACE_IN_USE = 2058;
                public static final int PDP_FAIL_INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN = 114;
                public static final int PDP_FAIL_INTERNAL_EPC_NONEPC_TRANSITION = 2057;
                public static final int PDP_FAIL_INVALID_CONNECTION_ID = 2156;
                public static final int PDP_FAIL_INVALID_DNS_ADDR = 123;
                public static final int PDP_FAIL_INVALID_EMM_STATE = 2190;
                public static final int PDP_FAIL_INVALID_MANDATORY_INFO = 96;
                public static final int PDP_FAIL_INVALID_MODE = 2223;
                public static final int PDP_FAIL_INVALID_PCSCF_ADDR = 113;
                public static final int PDP_FAIL_INVALID_PCSCF_OR_DNS_ADDRESS = 124;
                public static final int PDP_FAIL_INVALID_PRIMARY_NSAPI = 2158;
                public static final int PDP_FAIL_INVALID_SIM_STATE = 2224;
                public static final int PDP_FAIL_INVALID_TRANSACTION_ID = 81;
                public static final int PDP_FAIL_IPV6_ADDRESS_TRANSFER_FAILED = 2047;
                public static final int PDP_FAIL_IPV6_PREFIX_UNAVAILABLE = 2250;
                public static final int PDP_FAIL_IP_ADDRESS_MISMATCH = 119;
                public static final int PDP_FAIL_IP_VERSION_MISMATCH = 2055;
                public static final int PDP_FAIL_IRAT_HANDOVER_FAILED = 2194;
                public static final int PDP_FAIL_IS707B_MAX_ACCESS_PROBES = 2089;
                public static final int PDP_FAIL_LIMITED_TO_IPV4 = 2234;
                public static final int PDP_FAIL_LIMITED_TO_IPV6 = 2235;
                public static final int PDP_FAIL_LLC_SNDCP = 25;
                public static final int PDP_FAIL_LOCAL_END = 2215;
                public static final int PDP_FAIL_LOCATION_AREA_NOT_ALLOWED = 2102;
                public static final int PDP_FAIL_LOWER_LAYER_REGISTRATION_FAILURE = 2197;
                public static final int PDP_FAIL_LOW_POWER_MODE_OR_POWERING_DOWN = 2044;
                public static final int PDP_FAIL_LTE_NAS_SERVICE_REQUEST_FAILED = 2117;
                public static final int PDP_FAIL_LTE_THROTTLING_NOT_REQUIRED = 2127;
                public static final int PDP_FAIL_MAC_FAILURE = 2183;
                public static final int PDP_FAIL_MAXIMIUM_NSAPIS_EXCEEDED = 2157;
                public static final int PDP_FAIL_MAXINUM_SIZE_OF_L2_MESSAGE_EXCEEDED = 2166;
                public static final int PDP_FAIL_MAX_ACCESS_PROBE = 2079;
                public static final int PDP_FAIL_MAX_ACTIVE_PDP_CONTEXT_REACHED = 65;
                public static final int PDP_FAIL_MAX_IPV4_CONNECTIONS = 2052;
                public static final int PDP_FAIL_MAX_IPV6_CONNECTIONS = 2053;
                public static final int PDP_FAIL_MAX_PPP_INACTIVITY_TIMER_EXPIRED = 2046;
                public static final int PDP_FAIL_MESSAGE_INCORRECT_SEMANTIC = 95;
                public static final int PDP_FAIL_MESSAGE_TYPE_UNSUPPORTED = 97;
                public static final int PDP_FAIL_MIP_CONFIG_FAILURE = 2050;
                public static final int PDP_FAIL_MIP_FA_ADMIN_PROHIBITED = 2001;
                public static final int PDP_FAIL_MIP_FA_DELIVERY_STYLE_NOT_SUPPORTED = 2012;
                public static final int PDP_FAIL_MIP_FA_ENCAPSULATION_UNAVAILABLE = 2008;
                public static final int PDP_FAIL_MIP_FA_HOME_AGENT_AUTHENTICATION_FAILURE = 2004;
                public static final int PDP_FAIL_MIP_FA_INSUFFICIENT_RESOURCES = 2002;
                public static final int PDP_FAIL_MIP_FA_MALFORMED_REPLY = 2007;
                public static final int PDP_FAIL_MIP_FA_MALFORMED_REQUEST = 2006;
                public static final int PDP_FAIL_MIP_FA_MISSING_CHALLENGE = 2017;
                public static final int PDP_FAIL_MIP_FA_MISSING_HOME_ADDRESS = 2015;
                public static final int PDP_FAIL_MIP_FA_MISSING_HOME_AGENT = 2014;
                public static final int PDP_FAIL_MIP_FA_MISSING_NAI = 2013;
                public static final int PDP_FAIL_MIP_FA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2003;
                public static final int PDP_FAIL_MIP_FA_REASON_UNSPECIFIED = 2000;
                public static final int PDP_FAIL_MIP_FA_REQUESTED_LIFETIME_TOO_LONG = 2005;
                public static final int PDP_FAIL_MIP_FA_REVERSE_TUNNEL_IS_MANDATORY = 2011;
                public static final int PDP_FAIL_MIP_FA_REVERSE_TUNNEL_UNAVAILABLE = 2010;
                public static final int PDP_FAIL_MIP_FA_STALE_CHALLENGE = 2018;
                public static final int PDP_FAIL_MIP_FA_UNKNOWN_CHALLENGE = 2016;
                public static final int PDP_FAIL_MIP_FA_VJ_HEADER_COMPRESSION_UNAVAILABLE = 2009;
                public static final int PDP_FAIL_MIP_HA_ADMIN_PROHIBITED = 2020;
                public static final int PDP_FAIL_MIP_HA_ENCAPSULATION_UNAVAILABLE = 2029;
                public static final int PDP_FAIL_MIP_HA_FOREIGN_AGENT_AUTHENTICATION_FAILURE = 2023;
                public static final int PDP_FAIL_MIP_HA_INSUFFICIENT_RESOURCES = 2021;
                public static final int PDP_FAIL_MIP_HA_MALFORMED_REQUEST = 2025;
                public static final int PDP_FAIL_MIP_HA_MOBILE_NODE_AUTHENTICATION_FAILURE = 2022;
                public static final int PDP_FAIL_MIP_HA_REASON_UNSPECIFIED = 2019;
                public static final int PDP_FAIL_MIP_HA_REGISTRATION_ID_MISMATCH = 2024;
                public static final int PDP_FAIL_MIP_HA_REVERSE_TUNNEL_IS_MANDATORY = 2028;
                public static final int PDP_FAIL_MIP_HA_REVERSE_TUNNEL_UNAVAILABLE = 2027;
                public static final int PDP_FAIL_MIP_HA_UNKNOWN_HOME_AGENT_ADDRESS = 2026;
                public static final int PDP_FAIL_MISSING_UKNOWN_APN = 27;
                public static final int PDP_FAIL_MODEM_APP_PREEMPTED = 2032;
                public static final int PDP_FAIL_MODEM_RESTART = 2037;
                public static final int PDP_FAIL_MSC_TEMPORARILY_NOT_REACHABLE = 2180;
                public static final int PDP_FAIL_MSG_AND_PROTOCOL_STATE_UNCOMPATIBLE = 101;
                public static final int PDP_FAIL_MSG_TYPE_NONCOMPATIBLE_STATE = 98;
                public static final int PDP_FAIL_MS_IDENTITY_CANNOT_BE_DERIVED_BY_THE_NETWORK = 2099;
                public static final int PDP_FAIL_MULTIPLE_PDP_CALL_NOT_ALLOWED = 2192;
                public static final int PDP_FAIL_MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED = 55;
                public static final int PDP_FAIL_NAS_LAYER_FAILURE = 2191;
                public static final int PDP_FAIL_NAS_REQUEST_REJECTED_BY_NETWORK = 2167;
                public static final int PDP_FAIL_NAS_SIGNALLING = 14;
                public static final int PDP_FAIL_NETWORK_FAILURE = 38;
                public static final int PDP_FAIL_NETWORK_INITIATED_DETACH_NO_AUTO_REATTACH = 2154;
                public static final int PDP_FAIL_NETWORK_INITIATED_DETACH_WITH_AUTO_REATTACH = 2153;
                public static final int PDP_FAIL_NETWORK_INITIATED_TERMINATION = 2031;
                public static final int PDP_FAIL_NONE = 1;
                public static final int PDP_FAIL_NON_IP_NOT_SUPPORTED = 2069;
                public static final int PDP_FAIL_NORMAL_RELEASE = 2218;
                public static final int PDP_FAIL_NO_CDMA_SERVICE = 2084;
                public static final int PDP_FAIL_NO_COLLOCATED_HDR = 2225;
                public static final int PDP_FAIL_NO_EPS_BEARER_CONTEXT_ACTIVATED = 2189;
                public static final int PDP_FAIL_NO_GPRS_CONTEXT = 2094;
                public static final int PDP_FAIL_NO_HYBRID_HDR_SERVICE = 2209;
                public static final int PDP_FAIL_NO_PDP_CONTEXT_ACTIVATED = 2107;
                public static final int PDP_FAIL_NO_RESPONSE_FROM_BASE_STATION = 2081;
                public static final int PDP_FAIL_NO_SERVICE = 2216;
                public static final int PDP_FAIL_NO_SERVICE_ON_GATEWAY = 2093;
                public static final int PDP_FAIL_NSAPI_IN_USE = 35;
                public static final int PDP_FAIL_NULL_APN_DISALLOWED = 2061;
                public static final int PDP_FAIL_ONLY_IPV4V6_ALLOWED = 57;
                public static final int PDP_FAIL_ONLY_IPV4_ALLOWED = 50;
                public static final int PDP_FAIL_ONLY_IPV6_ALLOWED = 51;
                public static final int PDP_FAIL_ONLY_NON_IP_ALLOWED = 58;
                public static final int PDP_FAIL_ONLY_SINGLE_BEARER_ALLOWED = 52;
                public static final int PDP_FAIL_OPERATOR_BARRED = 8;
                public static final int PDP_FAIL_OTASP_COMMIT_IN_PROGRESS = 2208;
                public static final int PDP_FAIL_PDN_CONN_DOES_NOT_EXIST = 54;
                public static final int PDP_FAIL_PDN_INACTIVITY_TIMER_EXPIRED = 2051;
                public static final int PDP_FAIL_PDN_IPV4_CALL_DISALLOWED = 2033;
                public static final int PDP_FAIL_PDN_IPV4_CALL_THROTTLED = 2034;
                public static final int PDP_FAIL_PDN_IPV6_CALL_DISALLOWED = 2035;
                public static final int PDP_FAIL_PDN_IPV6_CALL_THROTTLED = 2036;
                public static final int PDP_FAIL_PDN_NON_IP_CALL_DISALLOWED = 2071;
                public static final int PDP_FAIL_PDN_NON_IP_CALL_THROTTLED = 2070;
                public static final int PDP_FAIL_PDP_ACTIVATE_MAX_RETRY_FAILED = 2109;
                public static final int PDP_FAIL_PDP_DUPLICATE = 2104;
                public static final int PDP_FAIL_PDP_ESTABLISH_TIMEOUT_EXPIRED = 2161;
                public static final int PDP_FAIL_PDP_INACTIVE_TIMEOUT_EXPIRED = 2163;
                public static final int PDP_FAIL_PDP_LOWERLAYER_ERROR = 2164;
                public static final int PDP_FAIL_PDP_MODIFY_COLLISION = 2165;
                public static final int PDP_FAIL_PDP_MODIFY_TIMEOUT_EXPIRED = 2162;
                public static final int PDP_FAIL_PDP_PPP_NOT_SUPPORTED = 2038;
                public static final int PDP_FAIL_PDP_WITHOUT_ACTIVE_TFT = 46;
                public static final int PDP_FAIL_PHONE_IN_USE = 2222;
                public static final int PDP_FAIL_PHYSICAL_LINK_CLOSE_IN_PROGRESS = 2040;
                public static final int PDP_FAIL_PLMN_NOT_ALLOWED = 2101;
                public static final int PDP_FAIL_PPP_AUTH_FAILURE = 2229;
                public static final int PDP_FAIL_PPP_CHAP_FAILURE = 2232;
                public static final int PDP_FAIL_PPP_CLOSE_IN_PROGRESS = 2233;
                public static final int PDP_FAIL_PPP_OPTION_MISMATCH = 2230;
                public static final int PDP_FAIL_PPP_PAP_FAILURE = 2231;
                public static final int PDP_FAIL_PPP_TIMEOUT = 2228;
                public static final int PDP_FAIL_PREF_RADIO_TECH_CHANGED = -4;
                public static final int PDP_FAIL_PROFILE_BEARER_INCOMPATIBLE = 2042;
                public static final int PDP_FAIL_PROTOCOL_ERRORS = 111;
                public static final int PDP_FAIL_QOS_NOT_ACCEPTED = 37;
                public static final int PDP_FAIL_RADIO_ACCESS_BEARER_FAILURE = 2110;
                public static final int PDP_FAIL_RADIO_ACCESS_BEARER_SETUP_FAILURE = 2160;
                public static final int PDP_FAIL_RADIO_POWER_OFF = -5;
                public static final int PDP_FAIL_REDIRECTION_OR_HANDOFF_IN_PROGRESS = 2220;
                public static final int PDP_FAIL_REGULAR_DEACTIVATION = 36;
                public static final int PDP_FAIL_REJECTED_BY_BASE_STATION = 2082;
                public static final int PDP_FAIL_RRC_CONNECTION_ABORTED_AFTER_HANDOVER = 2173;
                public static final int PDP_FAIL_RRC_CONNECTION_ABORTED_AFTER_IRAT_CELL_CHANGE = 2174;
                public static final int PDP_FAIL_RRC_CONNECTION_ABORTED_DUE_TO_IRAT_CHANGE = 2171;
                public static final int PDP_FAIL_RRC_CONNECTION_ABORTED_DURING_IRAT_CELL_CHANGE = 2175;
                public static final int PDP_FAIL_RRC_CONNECTION_ABORT_REQUEST = 2151;
                public static final int PDP_FAIL_RRC_CONNECTION_ACCESS_BARRED = 2139;
                public static final int PDP_FAIL_RRC_CONNECTION_ACCESS_STRATUM_FAILURE = 2137;
                public static final int PDP_FAIL_RRC_CONNECTION_ANOTHER_PROCEDURE_IN_PROGRESS = 2138;
                public static final int PDP_FAIL_RRC_CONNECTION_CELL_NOT_CAMPED = 2144;
                public static final int PDP_FAIL_RRC_CONNECTION_CELL_RESELECTION = 2140;
                public static final int PDP_FAIL_RRC_CONNECTION_CONFIG_FAILURE = 2141;
                public static final int PDP_FAIL_RRC_CONNECTION_INVALID_REQUEST = 2168;
                public static final int PDP_FAIL_RRC_CONNECTION_LINK_FAILURE = 2143;
                public static final int PDP_FAIL_RRC_CONNECTION_NORMAL_RELEASE = 2147;
                public static final int PDP_FAIL_RRC_CONNECTION_OUT_OF_SERVICE_DURING_CELL_REGISTER = 2150;
                public static final int PDP_FAIL_RRC_CONNECTION_RADIO_LINK_FAILURE = 2148;
                public static final int PDP_FAIL_RRC_CONNECTION_REESTABLISHMENT_FAILURE = 2149;
                public static final int PDP_FAIL_RRC_CONNECTION_REJECT_BY_NETWORK = 2146;
                public static final int PDP_FAIL_RRC_CONNECTION_RELEASED_SECURITY_NOT_ACTIVE = 2172;
                public static final int PDP_FAIL_RRC_CONNECTION_RF_UNAVAILABLE = 2170;
                public static final int PDP_FAIL_RRC_CONNECTION_SYSTEM_INFORMATION_BLOCK_READ_ERROR = 2152;
                public static final int PDP_FAIL_RRC_CONNECTION_SYSTEM_INTERVAL_FAILURE = 2145;
                public static final int PDP_FAIL_RRC_CONNECTION_TIMER_EXPIRED = 2142;
                public static final int PDP_FAIL_RRC_CONNECTION_TRACKING_AREA_ID_CHANGED = 2169;
                public static final int PDP_FAIL_RRC_UPLINK_CONNECTION_RELEASE = 2134;
                public static final int PDP_FAIL_RRC_UPLINK_DATA_TRANSMISSION_FAILURE = 2132;
                public static final int PDP_FAIL_RRC_UPLINK_DELIVERY_FAILED_DUE_TO_HANDOVER = 2133;
                public static final int PDP_FAIL_RRC_UPLINK_ERROR_REQUEST_FROM_NAS = 2136;
                public static final int PDP_FAIL_RRC_UPLINK_RADIO_LINK_FAILURE = 2135;
                public static final int PDP_FAIL_RUIM_NOT_PRESENT = 2085;
                public static final int PDP_FAIL_SECURITY_MODE_REJECTED = 2186;
                public static final int PDP_FAIL_SERVICE_NOT_ALLOWED_ON_PLMN = 2129;
                public static final int PDP_FAIL_SERVICE_OPTION_NOT_SUBSCRIBED = 33;
                public static final int PDP_FAIL_SERVICE_OPTION_NOT_SUPPORTED = 32;
                public static final int PDP_FAIL_SERVICE_OPTION_OUT_OF_ORDER = 34;
                public static final int PDP_FAIL_SIGNAL_LOST = -3;
                public static final int PDP_FAIL_SIM_CARD_CHANGED = 2043;
                public static final int PDP_FAIL_SYNCHRONIZATION_FAILURE = 2184;
                public static final int PDP_FAIL_TEST_LOOPBACK_REGULAR_DEACTIVATION = 2196;
                public static final int PDP_FAIL_TETHERED_CALL_ACTIVE = -6;
                public static final int PDP_FAIL_TFT_SEMANTIC_ERROR = 41;
                public static final int PDP_FAIL_TFT_SYTAX_ERROR = 42;
                public static final int PDP_FAIL_THERMAL_EMERGENCY = 2090;
                public static final int PDP_FAIL_THERMAL_MITIGATION = 2062;
                public static final int PDP_FAIL_TRAT_SWAP_FAILED = 2048;
                public static final int PDP_FAIL_UE_INITIATED_DETACH_OR_DISCONNECT = 128;
                public static final int PDP_FAIL_UE_IS_ENTERING_POWERSAVE_MODE = 2226;
                public static final int PDP_FAIL_UE_RAT_CHANGE = 2105;
                public static final int PDP_FAIL_UE_SECURITY_CAPABILITIES_MISMATCH = 2185;
                public static final int PDP_FAIL_UMTS_HANDOVER_TO_IWLAN = 2199;
                public static final int PDP_FAIL_UMTS_REACTIVATION_REQ = 39;
                public static final int PDP_FAIL_UNACCEPTABLE_NON_EPS_AUTHENTICATION = 2187;
                public static final int PDP_FAIL_UNKNOWN = 0;
                public static final int PDP_FAIL_UNKNOWN_INFO_ELEMENT = 99;
                public static final int PDP_FAIL_UNKNOWN_PDP_ADDRESS_TYPE = 28;
                public static final int PDP_FAIL_UNKNOWN_PDP_CONTEXT = 43;
                public static final int PDP_FAIL_UNPREFERRED_RAT = 2039;
                public static final int PDP_FAIL_UNSUPPORTED_1X_PREV = 2214;
                public static final int PDP_FAIL_UNSUPPORTED_APN_IN_CURRENT_PLMN = 66;
                public static final int PDP_FAIL_UNSUPPORTED_QCI_VALUE = 59;
                public static final int PDP_FAIL_USER_AUTHENTICATION = 29;
                public static final int PDP_FAIL_VOICE_REGISTRATION_FAIL = -1;
                public static final int PDP_FAIL_VSNCP_ADMINISTRATIVELY_PROHIBITED = 2245;
                public static final int PDP_FAIL_VSNCP_APN_UNATHORIZED = 2238;
                public static final int PDP_FAIL_VSNCP_GEN_ERROR = 2237;
                public static final int PDP_FAIL_VSNCP_INSUFFICIENT_PARAMETERS = 2243;
                public static final int PDP_FAIL_VSNCP_NO_PDN_GATEWAY_ADDRESS = 2240;
                public static final int PDP_FAIL_VSNCP_PDN_EXISTS_FOR_THIS_APN = 2248;
                public static final int PDP_FAIL_VSNCP_PDN_GATEWAY_REJECT = 2242;
                public static final int PDP_FAIL_VSNCP_PDN_GATEWAY_UNREACHABLE = 2241;
                public static final int PDP_FAIL_VSNCP_PDN_ID_IN_USE = 2246;
                public static final int PDP_FAIL_VSNCP_PDN_LIMIT_EXCEEDED = 2239;
                public static final int PDP_FAIL_VSNCP_RECONNECT_NOT_ALLOWED = 2249;
                public static final int PDP_FAIL_VSNCP_RESOURCE_UNAVAILABLE = 2244;
                public static final int PDP_FAIL_VSNCP_SUBSCRIBER_LIMITATION = 2247;
                public static final int PDP_FAIL_VSNCP_TIMEOUT = 2236;
            }

        }

        public static interface Type {
            public static final int ACTIVE_SUBSCRIPTION_INFO_CHANGED = 19;
            public static final int CARRIER_ID_MATCHING = 13;
            public static final int CARRIER_KEY_CHANGED = 14;
            public static final int DATA_CALL_DEACTIVATE = 8;
            public static final int DATA_CALL_DEACTIVATE_RESPONSE = 9;
            public static final int DATA_CALL_LIST_CHANGED = 7;
            public static final int DATA_CALL_SETUP = 5;
            public static final int DATA_CALL_SETUP_RESPONSE = 6;
            public static final int DATA_STALL_ACTION = 10;
            public static final int DATA_SWITCH = 15;
            public static final int EMERGENCY_NUMBER_REPORT = 21;
            public static final int ENABLED_MODEM_CHANGED = 20;
            public static final int IMS_CAPABILITIES_CHANGED = 4;
            public static final int IMS_CONNECTION_STATE_CHANGED = 3;
            public static final int MODEM_RESTART = 11;
            public static final int NETWORK_VALIDATE = 16;
            public static final int NITZ_TIME = 12;
            public static final int ON_DEMAND_DATA_SWITCH = 17;
            public static final int RIL_SERVICE_STATE_CHANGED = 2;
            public static final int SETTINGS_CHANGED = 1;
            public static final int SIM_STATE_CHANGED = 18;
            public static final int UNKNOWN = 0;
        }

    }

    public static final class TelephonyHistogram
    extends ExtendableMessageNano<TelephonyHistogram> {
        private static volatile TelephonyHistogram[] _emptyArray;
        public int avgTimeMillis;
        public int bucketCount;
        public int[] bucketCounters;
        public int[] bucketEndPoints;
        public int category;
        public int count;
        public int id;
        public int maxTimeMillis;
        public int minTimeMillis;

        public TelephonyHistogram() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonyHistogram[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonyHistogram[0];
                return _emptyArray;
            }
        }

        public static TelephonyHistogram parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonyHistogram().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonyHistogram parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonyHistogram(), arrby);
        }

        public TelephonyHistogram clear() {
            this.category = 0;
            this.id = 0;
            this.minTimeMillis = 0;
            this.maxTimeMillis = 0;
            this.avgTimeMillis = 0;
            this.count = 0;
            this.bucketCount = 0;
            this.bucketEndPoints = WireFormatNano.EMPTY_INT_ARRAY;
            this.bucketCounters = WireFormatNano.EMPTY_INT_ARRAY;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            int n3 = this.category;
            int n4 = n2;
            if (n3 != 0) {
                n4 = n2 + CodedOutputByteBufferNano.computeInt32Size(1, n3);
            }
            n3 = this.id;
            n2 = n4;
            if (n3 != 0) {
                n2 = n4 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.minTimeMillis;
            n4 = n2;
            if (n3 != 0) {
                n4 = n2 + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n2 = this.maxTimeMillis;
            n3 = n4;
            if (n2 != 0) {
                n3 = n4 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n4 = this.avgTimeMillis;
            n2 = n3;
            if (n4 != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n4);
            }
            n3 = this.count;
            n4 = n2;
            if (n3 != 0) {
                n4 = n2 + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            n3 = this.bucketCount;
            n2 = n4;
            if (n3 != 0) {
                n2 = n4 + CodedOutputByteBufferNano.computeInt32Size(7, n3);
            }
            int[] arrn = this.bucketEndPoints;
            n4 = n2;
            if (arrn != null) {
                n4 = n2;
                if (arrn.length > 0) {
                    n3 = 0;
                    for (n4 = 0; n4 < (arrn = this.bucketEndPoints).length; ++n4) {
                        n = arrn[n4];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n4 = n2 + n3 + arrn.length * 1;
                }
            }
            arrn = this.bucketCounters;
            n2 = n4;
            if (arrn != null) {
                n2 = n4;
                if (arrn.length > 0) {
                    n3 = 0;
                    for (n2 = 0; n2 < (arrn = this.bucketCounters).length; ++n2) {
                        n = arrn[n2];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n2 = n4 + n3 + arrn.length * 1;
                }
            }
            return n2;
        }

        @Override
        public TelephonyHistogram mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block14 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block14;
                        return this;
                    }
                    case 74: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        int[] arrn = this.bucketCounters;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.bucketCounters, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        this.bucketCounters = arrn;
                        codedInputByteBufferNano.popLimit(n2);
                        continue block14;
                    }
                    case 72: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 72);
                        int[] arrn = this.bucketCounters;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.bucketCounters, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrn[n3] = codedInputByteBufferNano.readInt32();
                        this.bucketCounters = arrn;
                        continue block14;
                    }
                    case 66: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        int[] arrn = this.bucketEndPoints;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.bucketEndPoints, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        this.bucketEndPoints = arrn;
                        codedInputByteBufferNano.popLimit(n2);
                        continue block14;
                    }
                    case 64: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 64);
                        int[] arrn = this.bucketEndPoints;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.bucketEndPoints, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrn[n3] = codedInputByteBufferNano.readInt32();
                        this.bucketEndPoints = arrn;
                        continue block14;
                    }
                    case 56: {
                        this.bucketCount = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 48: {
                        this.count = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 40: {
                        this.avgTimeMillis = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 32: {
                        this.maxTimeMillis = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 24: {
                        this.minTimeMillis = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 16: {
                        this.id = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 8: {
                        this.category = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 0: 
                }
                break;
            } while (true);
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int[] arrn;
            int n = this.category;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.id) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.minTimeMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.maxTimeMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.avgTimeMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.bucketCount) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((arrn = this.bucketEndPoints) != null && arrn.length > 0) {
                for (n = 0; n < (arrn = this.bucketEndPoints).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(8, arrn[n]);
                }
            }
            if ((arrn = this.bucketCounters) != null && arrn.length > 0) {
                for (n = 0; n < (arrn = this.bucketCounters).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(9, arrn[n]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class TelephonyLog
    extends ExtendableMessageNano<TelephonyLog> {
        private static volatile TelephonyLog[] _emptyArray;
        public TelephonyCallSession[] callSessions;
        public Time endTime;
        public TelephonyEvent[] events;
        public boolean eventsDropped;
        public String hardwareRevision;
        public TelephonyHistogram[] histograms;
        public ActiveSubscriptionInfo[] lastActiveSubscriptionInfo;
        public ModemPowerStats modemPowerStats;
        public SmsSession[] smsSessions;
        public Time startTime;

        public TelephonyLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonyLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonyLog[0];
                return _emptyArray;
            }
        }

        public static TelephonyLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonyLog().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonyLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonyLog(), arrby);
        }

        public TelephonyLog clear() {
            this.events = TelephonyEvent.emptyArray();
            this.callSessions = TelephonyCallSession.emptyArray();
            this.smsSessions = SmsSession.emptyArray();
            this.histograms = TelephonyHistogram.emptyArray();
            this.eventsDropped = false;
            this.startTime = null;
            this.endTime = null;
            this.modemPowerStats = null;
            this.hardwareRevision = "";
            this.lastActiveSubscriptionInfo = ActiveSubscriptionInfo.emptyArray();
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.events;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((TelephonyEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.events;
                        n3 = n2;
                        if (n >= ((ExtendableMessageNano[])object).length) break;
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
            object = this.callSessions;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ExtendableMessageNano[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.callSessions;
                        n2 = n3;
                        if (n >= ((ExtendableMessageNano[])object).length) break;
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
            object = this.smsSessions;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ExtendableMessageNano[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.smsSessions;
                        n3 = n2;
                        if (n >= ((ExtendableMessageNano[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.histograms;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ExtendableMessageNano[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.histograms;
                        n2 = n3;
                        if (n >= ((ExtendableMessageNano[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            boolean bl = this.eventsDropped;
            n3 = n2;
            if (bl) {
                n3 = n2 + CodedOutputByteBufferNano.computeBoolSize(5, bl);
            }
            object = this.startTime;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
            }
            object = this.endTime;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
            }
            object = this.modemPowerStats;
            n = n3;
            if (object != null) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)object);
            }
            n2 = n;
            if (!this.hardwareRevision.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(9, this.hardwareRevision);
            }
            object = this.lastActiveSubscriptionInfo;
            n = n2;
            if (object != null) {
                n = n2;
                if (((ExtendableMessageNano[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.lastActiveSubscriptionInfo;
                        n = n2;
                        if (n3 >= ((ExtendableMessageNano[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(10, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            return n;
        }

        @Override
        public TelephonyLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block13 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block13;
                        return this;
                    }
                    case 82: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        ExtendableMessageNano[] arrextendableMessageNano = this.lastActiveSubscriptionInfo;
                        n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                        arrextendableMessageNano = new ActiveSubscriptionInfo[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.lastActiveSubscriptionInfo, 0, arrextendableMessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrextendableMessageNano.length - 1) {
                            arrextendableMessageNano[n2] = new ActiveSubscriptionInfo();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrextendableMessageNano[n2] = new ActiveSubscriptionInfo();
                        codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                        this.lastActiveSubscriptionInfo = arrextendableMessageNano;
                        continue block13;
                    }
                    case 74: {
                        this.hardwareRevision = codedInputByteBufferNano.readString();
                        continue block13;
                    }
                    case 66: {
                        if (this.modemPowerStats == null) {
                            this.modemPowerStats = new ModemPowerStats();
                        }
                        codedInputByteBufferNano.readMessage(this.modemPowerStats);
                        continue block13;
                    }
                    case 58: {
                        if (this.endTime == null) {
                            this.endTime = new Time();
                        }
                        codedInputByteBufferNano.readMessage(this.endTime);
                        continue block13;
                    }
                    case 50: {
                        if (this.startTime == null) {
                            this.startTime = new Time();
                        }
                        codedInputByteBufferNano.readMessage(this.startTime);
                        continue block13;
                    }
                    case 40: {
                        this.eventsDropped = codedInputByteBufferNano.readBool();
                        continue block13;
                    }
                    case 34: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        ExtendableMessageNano[] arrextendableMessageNano = this.histograms;
                        n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                        arrextendableMessageNano = new TelephonyHistogram[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histograms, 0, arrextendableMessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrextendableMessageNano.length - 1) {
                            arrextendableMessageNano[n2] = new TelephonyHistogram();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrextendableMessageNano[n2] = new TelephonyHistogram();
                        codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                        this.histograms = arrextendableMessageNano;
                        continue block13;
                    }
                    case 26: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        ExtendableMessageNano[] arrextendableMessageNano = this.smsSessions;
                        n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                        arrextendableMessageNano = new SmsSession[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.smsSessions, 0, arrextendableMessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrextendableMessageNano.length - 1) {
                            arrextendableMessageNano[n2] = new SmsSession();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrextendableMessageNano[n2] = new SmsSession();
                        codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                        this.smsSessions = arrextendableMessageNano;
                        continue block13;
                    }
                    case 18: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                        ExtendableMessageNano[] arrextendableMessageNano = this.callSessions;
                        n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                        arrextendableMessageNano = new TelephonyCallSession[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.callSessions, 0, arrextendableMessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrextendableMessageNano.length - 1) {
                            arrextendableMessageNano[n2] = new TelephonyCallSession();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrextendableMessageNano[n2] = new TelephonyCallSession();
                        codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                        this.callSessions = arrextendableMessageNano;
                        continue block13;
                    }
                    case 10: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        ExtendableMessageNano[] arrextendableMessageNano = this.events;
                        n = arrextendableMessageNano == null ? 0 : arrextendableMessageNano.length;
                        arrextendableMessageNano = new TelephonyEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.events, 0, arrextendableMessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrextendableMessageNano.length - 1) {
                            arrextendableMessageNano[n2] = new TelephonyEvent();
                            codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrextendableMessageNano[n2] = new TelephonyEvent();
                        codedInputByteBufferNano.readMessage(arrextendableMessageNano[n2]);
                        this.events = arrextendableMessageNano;
                        continue block13;
                    }
                    case 0: 
                }
                break;
            } while (true);
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            boolean bl;
            Object object = this.events;
            if (object != null && ((TelephonyEvent[])object).length > 0) {
                for (n = 0; n < ((ExtendableMessageNano[])(object = this.events)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((object = this.callSessions) != null && ((ExtendableMessageNano[])object).length > 0) {
                for (n = 0; n < ((ExtendableMessageNano[])(object = this.callSessions)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((object = this.smsSessions) != null && ((ExtendableMessageNano[])object).length > 0) {
                for (n = 0; n < ((ExtendableMessageNano[])(object = this.smsSessions)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if ((object = this.histograms) != null && ((ExtendableMessageNano[])object).length > 0) {
                for (n = 0; n < ((ExtendableMessageNano[])(object = this.histograms)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if (bl = this.eventsDropped) {
                codedOutputByteBufferNano.writeBool(5, bl);
            }
            if ((object = this.startTime) != null) {
                codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
            }
            if ((object = this.endTime) != null) {
                codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
            }
            if ((object = this.modemPowerStats) != null) {
                codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
            }
            if (!this.hardwareRevision.equals("")) {
                codedOutputByteBufferNano.writeString(9, this.hardwareRevision);
            }
            if ((object = this.lastActiveSubscriptionInfo) != null && ((ExtendableMessageNano[])object).length > 0) {
                for (n = 0; n < ((ExtendableMessageNano[])(object = this.lastActiveSubscriptionInfo)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(10, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class TelephonyServiceState
    extends ExtendableMessageNano<TelephonyServiceState> {
        private static volatile TelephonyServiceState[] _emptyArray;
        public int channelNumber;
        public TelephonyOperator dataOperator;
        public int dataRat;
        public int dataRoamingType;
        public TelephonyOperator voiceOperator;
        public int voiceRat;
        public int voiceRoamingType;

        public TelephonyServiceState() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonyServiceState[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonyServiceState[0];
                return _emptyArray;
            }
        }

        public static TelephonyServiceState parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonyServiceState().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonyServiceState parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonyServiceState(), arrby);
        }

        public TelephonyServiceState clear() {
            this.voiceOperator = null;
            this.dataOperator = null;
            this.voiceRoamingType = -1;
            this.dataRoamingType = -1;
            this.voiceRat = -1;
            this.dataRat = -1;
            this.channelNumber = 0;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            TelephonyOperator telephonyOperator = this.voiceOperator;
            int n2 = n;
            if (telephonyOperator != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(1, telephonyOperator);
            }
            telephonyOperator = this.dataOperator;
            n = n2;
            if (telephonyOperator != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(2, telephonyOperator);
            }
            int n3 = this.voiceRoamingType;
            n2 = n;
            if (n3 != -1) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n = this.dataRoamingType;
            n3 = n2;
            if (n != -1) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n);
            }
            n2 = this.voiceRat;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            n3 = this.dataRat;
            n2 = n;
            if (n3 != -1) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            n3 = this.channelNumber;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(7, n3);
            }
            return n;
        }

        @Override
        public TelephonyServiceState mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            block6 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 18) {
                        int n2;
                        int n3;
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (n != 56) {
                                            if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                            return this;
                                        }
                                        this.channelNumber = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    n3 = codedInputByteBufferNano.getPosition();
                                    n2 = codedInputByteBufferNano.readInt32();
                                    switch (n2) {
                                        default: {
                                            codedInputByteBufferNano.rewindToPosition(n3);
                                            this.storeUnknownField(codedInputByteBufferNano, n);
                                            continue block6;
                                        }
                                        case -1: 
                                        case 0: 
                                        case 1: 
                                        case 2: 
                                        case 3: 
                                        case 4: 
                                        case 5: 
                                        case 6: 
                                        case 7: 
                                        case 8: 
                                        case 9: 
                                        case 10: 
                                        case 11: 
                                        case 12: 
                                        case 13: 
                                        case 14: 
                                        case 15: 
                                        case 16: 
                                        case 17: 
                                        case 18: 
                                        case 19: 
                                    }
                                    this.dataRat = n2;
                                    continue;
                                }
                                n3 = codedInputByteBufferNano.getPosition();
                                n2 = codedInputByteBufferNano.readInt32();
                                switch (n2) {
                                    default: {
                                        codedInputByteBufferNano.rewindToPosition(n3);
                                        this.storeUnknownField(codedInputByteBufferNano, n);
                                        continue block6;
                                    }
                                    case -1: 
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                    case 7: 
                                    case 8: 
                                    case 9: 
                                    case 10: 
                                    case 11: 
                                    case 12: 
                                    case 13: 
                                    case 14: 
                                    case 15: 
                                    case 16: 
                                    case 17: 
                                    case 18: 
                                    case 19: 
                                }
                                this.voiceRat = n2;
                                continue;
                            }
                            n2 = codedInputByteBufferNano.getPosition();
                            n3 = codedInputByteBufferNano.readInt32();
                            if (n3 != -1 && n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3) {
                                codedInputByteBufferNano.rewindToPosition(n2);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue;
                            }
                            this.dataRoamingType = n3;
                            continue;
                        }
                        n2 = codedInputByteBufferNano.getPosition();
                        n3 = codedInputByteBufferNano.readInt32();
                        if (n3 != -1 && n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3) {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue;
                        }
                        this.voiceRoamingType = n3;
                        continue;
                    }
                    if (this.dataOperator == null) {
                        this.dataOperator = new TelephonyOperator();
                    }
                    codedInputByteBufferNano.readMessage(this.dataOperator);
                    continue;
                }
                if (this.voiceOperator == null) {
                    this.voiceOperator = new TelephonyOperator();
                }
                codedInputByteBufferNano.readMessage(this.voiceOperator);
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            TelephonyOperator telephonyOperator = this.voiceOperator;
            if (telephonyOperator != null) {
                codedOutputByteBufferNano.writeMessage(1, telephonyOperator);
            }
            if ((telephonyOperator = this.dataOperator) != null) {
                codedOutputByteBufferNano.writeMessage(2, telephonyOperator);
            }
            if ((n = this.voiceRoamingType) != -1) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.dataRoamingType) != -1) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.voiceRat) != -1) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.dataRat) != -1) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.channelNumber) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface RoamingType {
            public static final int ROAMING_TYPE_DOMESTIC = 2;
            public static final int ROAMING_TYPE_INTERNATIONAL = 3;
            public static final int ROAMING_TYPE_NOT_ROAMING = 0;
            public static final int ROAMING_TYPE_UNKNOWN = 1;
            public static final int UNKNOWN = -1;
        }

        public static final class TelephonyOperator
        extends ExtendableMessageNano<TelephonyOperator> {
            private static volatile TelephonyOperator[] _emptyArray;
            public String alphaLong;
            public String alphaShort;
            public String numeric;

            public TelephonyOperator() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static TelephonyOperator[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new TelephonyOperator[0];
                    return _emptyArray;
                }
            }

            public static TelephonyOperator parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new TelephonyOperator().mergeFrom(codedInputByteBufferNano);
            }

            public static TelephonyOperator parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new TelephonyOperator(), arrby);
            }

            public TelephonyOperator clear() {
                this.alphaLong = "";
                this.alphaShort = "";
                this.numeric = "";
                this.unknownFieldData = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n;
                int n2 = n = super.computeSerializedSize();
                if (!this.alphaLong.equals("")) {
                    n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.alphaLong);
                }
                n = n2;
                if (!this.alphaShort.equals("")) {
                    n = n2 + CodedOutputByteBufferNano.computeStringSize(2, this.alphaShort);
                }
                n2 = n;
                if (!this.numeric.equals("")) {
                    n2 = n + CodedOutputByteBufferNano.computeStringSize(3, this.numeric);
                }
                return n2;
            }

            @Override
            public TelephonyOperator mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 10) {
                        if (n != 18) {
                            if (n != 26) {
                                if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.numeric = codedInputByteBufferNano.readString();
                            continue;
                        }
                        this.alphaShort = codedInputByteBufferNano.readString();
                        continue;
                    }
                    this.alphaLong = codedInputByteBufferNano.readString();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                if (!this.alphaLong.equals("")) {
                    codedOutputByteBufferNano.writeString(1, this.alphaLong);
                }
                if (!this.alphaShort.equals("")) {
                    codedOutputByteBufferNano.writeString(2, this.alphaShort);
                }
                if (!this.numeric.equals("")) {
                    codedOutputByteBufferNano.writeString(3, this.numeric);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class TelephonySettings
    extends ExtendableMessageNano<TelephonySettings> {
        private static volatile TelephonySettings[] _emptyArray;
        public boolean isAirplaneMode;
        public boolean isCellularDataEnabled;
        public boolean isDataRoamingEnabled;
        public boolean isEnhanced4GLteModeEnabled;
        public boolean isVtOverLteEnabled;
        public boolean isVtOverWifiEnabled;
        public boolean isWifiCallingEnabled;
        public boolean isWifiEnabled;
        public int preferredNetworkMode;
        public int wifiCallingMode;

        public TelephonySettings() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TelephonySettings[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TelephonySettings[0];
                return _emptyArray;
            }
        }

        public static TelephonySettings parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TelephonySettings().mergeFrom(codedInputByteBufferNano);
        }

        public static TelephonySettings parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TelephonySettings(), arrby);
        }

        public TelephonySettings clear() {
            this.isAirplaneMode = false;
            this.isCellularDataEnabled = false;
            this.isDataRoamingEnabled = false;
            this.preferredNetworkMode = 0;
            this.isEnhanced4GLteModeEnabled = false;
            this.isWifiEnabled = false;
            this.isWifiCallingEnabled = false;
            this.wifiCallingMode = 0;
            this.isVtOverLteEnabled = false;
            this.isVtOverWifiEnabled = false;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            boolean bl = this.isAirplaneMode;
            int n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(1, bl);
            }
            bl = this.isCellularDataEnabled;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(2, bl);
            }
            bl = this.isDataRoamingEnabled;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(3, bl);
            }
            int n3 = this.preferredNetworkMode;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            bl = this.isEnhanced4GLteModeEnabled;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(5, bl);
            }
            bl = this.isWifiEnabled;
            n2 = n3;
            if (bl) {
                n2 = n3 + CodedOutputByteBufferNano.computeBoolSize(6, bl);
            }
            bl = this.isWifiCallingEnabled;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(7, bl);
            }
            n3 = this.wifiCallingMode;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(8, n3);
            }
            bl = this.isVtOverLteEnabled;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(9, bl);
            }
            bl = this.isVtOverWifiEnabled;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(10, bl);
            }
            return n2;
        }

        @Override
        public TelephonySettings mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 80: {
                        this.isVtOverWifiEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 72: {
                        this.isVtOverLteEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 64: {
                        int n2 = codedInputByteBufferNano.getPosition();
                        int n3 = codedInputByteBufferNano.readInt32();
                        if (n3 != 0 && n3 != 1 && n3 != 2 && n3 != 3) {
                            codedInputByteBufferNano.rewindToPosition(n2);
                            this.storeUnknownField(codedInputByteBufferNano, n);
                            continue block16;
                        }
                        this.wifiCallingMode = n3;
                        continue block16;
                    }
                    case 56: {
                        this.isWifiCallingEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 48: {
                        this.isWifiEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 40: {
                        this.isEnhanced4GLteModeEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 32: {
                        int n3 = codedInputByteBufferNano.getPosition();
                        int n2 = codedInputByteBufferNano.readInt32();
                        switch (n2) {
                            default: {
                                codedInputByteBufferNano.rewindToPosition(n3);
                                this.storeUnknownField(codedInputByteBufferNano, n);
                                continue block16;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                            case 9: 
                            case 10: 
                            case 11: 
                            case 12: 
                            case 13: 
                            case 14: 
                            case 15: 
                            case 16: 
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                            case 22: 
                            case 23: 
                        }
                        this.preferredNetworkMode = n2;
                        continue block16;
                    }
                    case 24: {
                        this.isDataRoamingEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 16: {
                        this.isCellularDataEnabled = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 8: {
                        this.isAirplaneMode = codedInputByteBufferNano.readBool();
                        continue block16;
                    }
                    case 0: 
                }
                break;
            } while (true);
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            boolean bl = this.isAirplaneMode;
            if (bl) {
                codedOutputByteBufferNano.writeBool(1, bl);
            }
            if (bl = this.isCellularDataEnabled) {
                codedOutputByteBufferNano.writeBool(2, bl);
            }
            if (bl = this.isDataRoamingEnabled) {
                codedOutputByteBufferNano.writeBool(3, bl);
            }
            if ((n = this.preferredNetworkMode) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if (bl = this.isEnhanced4GLteModeEnabled) {
                codedOutputByteBufferNano.writeBool(5, bl);
            }
            if (bl = this.isWifiEnabled) {
                codedOutputByteBufferNano.writeBool(6, bl);
            }
            if (bl = this.isWifiCallingEnabled) {
                codedOutputByteBufferNano.writeBool(7, bl);
            }
            if ((n = this.wifiCallingMode) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if (bl = this.isVtOverLteEnabled) {
                codedOutputByteBufferNano.writeBool(9, bl);
            }
            if (bl = this.isVtOverWifiEnabled) {
                codedOutputByteBufferNano.writeBool(10, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static interface RilNetworkMode {
            public static final int NETWORK_MODE_CDMA = 5;
            public static final int NETWORK_MODE_CDMA_NO_EVDO = 6;
            public static final int NETWORK_MODE_EVDO_NO_CDMA = 7;
            public static final int NETWORK_MODE_GLOBAL = 8;
            public static final int NETWORK_MODE_GSM_ONLY = 2;
            public static final int NETWORK_MODE_GSM_UMTS = 4;
            public static final int NETWORK_MODE_LTE_CDMA_EVDO = 9;
            public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 11;
            public static final int NETWORK_MODE_LTE_GSM_WCDMA = 10;
            public static final int NETWORK_MODE_LTE_ONLY = 12;
            public static final int NETWORK_MODE_LTE_TDSCDMA = 16;
            public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 23;
            public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 18;
            public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 21;
            public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 20;
            public static final int NETWORK_MODE_LTE_WCDMA = 13;
            public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
            public static final int NETWORK_MODE_TDSCDMA_GSM = 17;
            public static final int NETWORK_MODE_TDSCDMA_GSM_WCDMA = 19;
            public static final int NETWORK_MODE_TDSCDMA_ONLY = 14;
            public static final int NETWORK_MODE_TDSCDMA_WCDMA = 15;
            public static final int NETWORK_MODE_UNKNOWN = 0;
            public static final int NETWORK_MODE_WCDMA_ONLY = 3;
            public static final int NETWORK_MODE_WCDMA_PREF = 1;
        }

        public static interface WiFiCallingMode {
            public static final int WFC_MODE_CELLULAR_PREFERRED = 2;
            public static final int WFC_MODE_UNKNOWN = 0;
            public static final int WFC_MODE_WIFI_ONLY = 1;
            public static final int WFC_MODE_WIFI_PREFERRED = 3;
        }

    }

    public static final class Time
    extends ExtendableMessageNano<Time> {
        private static volatile Time[] _emptyArray;
        public long elapsedTimestampMillis;
        public long systemTimestampMillis;

        public Time() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static Time[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new Time[0];
                return _emptyArray;
            }
        }

        public static Time parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new Time().mergeFrom(codedInputByteBufferNano);
        }

        public static Time parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new Time(), arrby);
        }

        public Time clear() {
            this.systemTimestampMillis = 0L;
            this.elapsedTimestampMillis = 0L;
            this.unknownFieldData = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.systemTimestampMillis;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            l = this.elapsedTimestampMillis;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            return n;
        }

        @Override
        public Time mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (this.storeUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.elapsedTimestampMillis = codedInputByteBufferNano.readInt64();
                    continue;
                }
                this.systemTimestampMillis = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l = this.systemTimestampMillis;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.elapsedTimestampMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static interface TimeInterval {
        public static final int TI_100_MILLIS = 4;
        public static final int TI_10_MILLIS = 1;
        public static final int TI_10_MINUTES = 14;
        public static final int TI_10_SEC = 10;
        public static final int TI_1_HOUR = 16;
        public static final int TI_1_MINUTE = 12;
        public static final int TI_1_SEC = 7;
        public static final int TI_200_MILLIS = 5;
        public static final int TI_20_MILLIS = 2;
        public static final int TI_2_HOURS = 17;
        public static final int TI_2_SEC = 8;
        public static final int TI_30_MINUTES = 15;
        public static final int TI_30_SEC = 11;
        public static final int TI_3_MINUTES = 13;
        public static final int TI_4_HOURS = 18;
        public static final int TI_500_MILLIS = 6;
        public static final int TI_50_MILLIS = 3;
        public static final int TI_5_SEC = 9;
        public static final int TI_MANY_HOURS = 19;
        public static final int TI_UNKNOWN = 0;
    }

}

