/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.wifi.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface WifiMetricsProto {

    public static final class AlertReasonCount
    extends MessageNano {
        private static volatile AlertReasonCount[] _emptyArray;
        public int count;
        public int reason;

        public AlertReasonCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static AlertReasonCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new AlertReasonCount[0];
                return _emptyArray;
            }
        }

        public static AlertReasonCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new AlertReasonCount().mergeFrom(codedInputByteBufferNano);
        }

        public static AlertReasonCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new AlertReasonCount(), arrby);
        }

        public AlertReasonCount clear() {
            this.reason = 0;
            this.count = 0;
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
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public AlertReasonCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.reason = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.reason;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ConnectToNetworkNotificationAndActionCount
    extends MessageNano {
        public static final int ACTION_CONNECT_TO_NETWORK = 2;
        public static final int ACTION_PICK_WIFI_NETWORK = 3;
        public static final int ACTION_PICK_WIFI_NETWORK_AFTER_CONNECT_FAILURE = 4;
        public static final int ACTION_UNKNOWN = 0;
        public static final int ACTION_USER_DISMISSED_NOTIFICATION = 1;
        public static final int NOTIFICATION_CONNECTED_TO_NETWORK = 3;
        public static final int NOTIFICATION_CONNECTING_TO_NETWORK = 2;
        public static final int NOTIFICATION_FAILED_TO_CONNECT = 4;
        public static final int NOTIFICATION_RECOMMEND_NETWORK = 1;
        public static final int NOTIFICATION_UNKNOWN = 0;
        public static final int RECOMMENDER_OPEN = 1;
        public static final int RECOMMENDER_UNKNOWN = 0;
        private static volatile ConnectToNetworkNotificationAndActionCount[] _emptyArray;
        public int action;
        public int count;
        public int notification;
        public int recommender;

        public ConnectToNetworkNotificationAndActionCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ConnectToNetworkNotificationAndActionCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ConnectToNetworkNotificationAndActionCount[0];
                return _emptyArray;
            }
        }

        public static ConnectToNetworkNotificationAndActionCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ConnectToNetworkNotificationAndActionCount().mergeFrom(codedInputByteBufferNano);
        }

        public static ConnectToNetworkNotificationAndActionCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ConnectToNetworkNotificationAndActionCount(), arrby);
        }

        public ConnectToNetworkNotificationAndActionCount clear() {
            this.notification = 0;
            this.action = 0;
            this.recommender = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.notification;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.action;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.recommender;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            return n;
        }

        @Override
        public ConnectToNetworkNotificationAndActionCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.count = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1) continue;
                        this.recommender = n;
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue;
                    this.action = n;
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue;
                this.notification = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.notification;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.action) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.recommender) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ConnectionEvent
    extends MessageNano {
        public static final int AUTH_FAILURE_EAP_FAILURE = 4;
        public static final int AUTH_FAILURE_NONE = 1;
        public static final int AUTH_FAILURE_TIMEOUT = 2;
        public static final int AUTH_FAILURE_WRONG_PSWD = 3;
        public static final int FAILURE_REASON_UNKNOWN = 0;
        public static final int HLF_DHCP = 2;
        public static final int HLF_NONE = 1;
        public static final int HLF_NO_INTERNET = 3;
        public static final int HLF_UNKNOWN = 0;
        public static final int HLF_UNWANTED = 4;
        public static final int NOMINATOR_CARRIER = 5;
        public static final int NOMINATOR_EXTERNAL_SCORED = 6;
        public static final int NOMINATOR_MANUAL = 1;
        public static final int NOMINATOR_OPEN_NETWORK_AVAILABLE = 9;
        public static final int NOMINATOR_PASSPOINT = 4;
        public static final int NOMINATOR_SAVED = 2;
        public static final int NOMINATOR_SAVED_USER_CONNECT_CHOICE = 8;
        public static final int NOMINATOR_SPECIFIER = 7;
        public static final int NOMINATOR_SUGGESTION = 3;
        public static final int NOMINATOR_UNKNOWN = 0;
        public static final int ROAM_DBDC = 2;
        public static final int ROAM_ENTERPRISE = 3;
        public static final int ROAM_NONE = 1;
        public static final int ROAM_UNKNOWN = 0;
        public static final int ROAM_UNRELATED = 5;
        public static final int ROAM_USER_SELECTED = 4;
        private static volatile ConnectionEvent[] _emptyArray;
        public boolean automaticBugReportTaken;
        public int connectionNominator;
        public int connectionResult;
        public int connectivityLevelFailureCode;
        public int durationTakenToConnectMillis;
        public int level2FailureCode;
        public int level2FailureReason;
        public int networkSelectorExperimentId;
        public int roamType;
        public RouterFingerPrint routerFingerprint;
        public int signalStrength;
        public long startTimeMillis;
        public boolean useRandomizedMac;

        public ConnectionEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ConnectionEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ConnectionEvent[0];
                return _emptyArray;
            }
        }

        public static ConnectionEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ConnectionEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static ConnectionEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ConnectionEvent(), arrby);
        }

        public ConnectionEvent clear() {
            this.startTimeMillis = 0L;
            this.durationTakenToConnectMillis = 0;
            this.routerFingerprint = null;
            this.signalStrength = 0;
            this.roamType = 0;
            this.connectionResult = 0;
            this.level2FailureCode = 0;
            this.connectivityLevelFailureCode = 0;
            this.automaticBugReportTaken = false;
            this.useRandomizedMac = false;
            this.connectionNominator = 0;
            this.networkSelectorExperimentId = 0;
            this.level2FailureReason = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.startTimeMillis;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.durationTakenToConnectMillis;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            RouterFingerPrint routerFingerPrint = this.routerFingerprint;
            n3 = n;
            if (routerFingerPrint != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, routerFingerPrint);
            }
            n = this.signalStrength;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n);
            }
            n3 = this.roamType;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            n3 = this.connectionResult;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            n3 = this.level2FailureCode;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(7, n3);
            }
            n3 = this.connectivityLevelFailureCode;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(8, n3);
            }
            boolean bl = this.automaticBugReportTaken;
            n3 = n2;
            if (bl) {
                n3 = n2 + CodedOutputByteBufferNano.computeBoolSize(9, bl);
            }
            bl = this.useRandomizedMac;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(10, bl);
            }
            n3 = this.connectionNominator;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(11, n3);
            }
            n3 = this.networkSelectorExperimentId;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(12, n3);
            }
            n3 = this.level2FailureReason;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(13, n3);
            }
            return n2;
        }

        @Override
        public ConnectionEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block19 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block19;
                        return this;
                    }
                    case 104: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue block19;
                        this.level2FailureReason = n;
                        break;
                    }
                    case 96: {
                        this.networkSelectorExperimentId = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 88: {
                        n = codedInputByteBufferNano.readInt32();
                        switch (n) {
                            default: {
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
                        }
                        this.connectionNominator = n;
                        break;
                    }
                    case 80: {
                        this.useRandomizedMac = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 72: {
                        this.automaticBugReportTaken = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 64: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue block19;
                        this.connectivityLevelFailureCode = n;
                        break;
                    }
                    case 56: {
                        this.level2FailureCode = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 48: {
                        this.connectionResult = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 40: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5) continue block19;
                        this.roamType = n;
                        break;
                    }
                    case 32: {
                        this.signalStrength = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 26: {
                        if (this.routerFingerprint == null) {
                            this.routerFingerprint = new RouterFingerPrint();
                        }
                        codedInputByteBufferNano.readMessage(this.routerFingerprint);
                        break;
                    }
                    case 16: {
                        this.durationTakenToConnectMillis = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 8: {
                        this.startTimeMillis = codedInputByteBufferNano.readInt64();
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
            RouterFingerPrint routerFingerPrint;
            boolean bl;
            int n;
            long l = this.startTimeMillis;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.durationTakenToConnectMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((routerFingerPrint = this.routerFingerprint) != null) {
                codedOutputByteBufferNano.writeMessage(3, routerFingerPrint);
            }
            if ((n = this.signalStrength) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.roamType) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.connectionResult) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.level2FailureCode) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.connectivityLevelFailureCode) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if (bl = this.automaticBugReportTaken) {
                codedOutputByteBufferNano.writeBool(9, bl);
            }
            if (bl = this.useRandomizedMac) {
                codedOutputByteBufferNano.writeBool(10, bl);
            }
            if ((n = this.connectionNominator) != 0) {
                codedOutputByteBufferNano.writeInt32(11, n);
            }
            if ((n = this.networkSelectorExperimentId) != 0) {
                codedOutputByteBufferNano.writeInt32(12, n);
            }
            if ((n = this.level2FailureReason) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class DeviceMobilityStatePnoScanStats
    extends MessageNano {
        public static final int HIGH_MVMT = 1;
        public static final int LOW_MVMT = 2;
        public static final int STATIONARY = 3;
        public static final int UNKNOWN = 0;
        private static volatile DeviceMobilityStatePnoScanStats[] _emptyArray;
        public int deviceMobilityState;
        public int numTimesEnteredState;
        public long pnoDurationMs;
        public long totalDurationMs;

        public DeviceMobilityStatePnoScanStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static DeviceMobilityStatePnoScanStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new DeviceMobilityStatePnoScanStats[0];
                return _emptyArray;
            }
        }

        public static DeviceMobilityStatePnoScanStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DeviceMobilityStatePnoScanStats().mergeFrom(codedInputByteBufferNano);
        }

        public static DeviceMobilityStatePnoScanStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new DeviceMobilityStatePnoScanStats(), arrby);
        }

        public DeviceMobilityStatePnoScanStats clear() {
            this.deviceMobilityState = 0;
            this.numTimesEnteredState = 0;
            this.totalDurationMs = 0L;
            this.pnoDurationMs = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.deviceMobilityState;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numTimesEnteredState;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            long l = this.totalDurationMs;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.pnoDurationMs;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            return n;
        }

        @Override
        public DeviceMobilityStatePnoScanStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.pnoDurationMs = codedInputByteBufferNano.readInt64();
                            continue;
                        }
                        this.totalDurationMs = codedInputByteBufferNano.readInt64();
                        continue;
                    }
                    this.numTimesEnteredState = codedInputByteBufferNano.readInt32();
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                this.deviceMobilityState = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l;
            int n = this.deviceMobilityState;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numTimesEnteredState) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((l = this.totalDurationMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.pnoDurationMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ExperimentValues
    extends MessageNano {
        private static volatile ExperimentValues[] _emptyArray;
        public boolean linkSpeedCountsLoggingEnabled;
        public int wifiDataStallMinTxBad;
        public int wifiDataStallMinTxSuccessWithoutRx;
        public boolean wifiIsUnusableLoggingEnabled;

        public ExperimentValues() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ExperimentValues[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ExperimentValues[0];
                return _emptyArray;
            }
        }

        public static ExperimentValues parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ExperimentValues().mergeFrom(codedInputByteBufferNano);
        }

        public static ExperimentValues parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ExperimentValues(), arrby);
        }

        public ExperimentValues clear() {
            this.wifiIsUnusableLoggingEnabled = false;
            this.wifiDataStallMinTxBad = 0;
            this.wifiDataStallMinTxSuccessWithoutRx = 0;
            this.linkSpeedCountsLoggingEnabled = false;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            boolean bl = this.wifiIsUnusableLoggingEnabled;
            int n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(1, bl);
            }
            int n3 = this.wifiDataStallMinTxBad;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.wifiDataStallMinTxSuccessWithoutRx;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            bl = this.linkSpeedCountsLoggingEnabled;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            return n;
        }

        @Override
        public ExperimentValues mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.linkSpeedCountsLoggingEnabled = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        this.wifiDataStallMinTxSuccessWithoutRx = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.wifiDataStallMinTxBad = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.wifiIsUnusableLoggingEnabled = codedInputByteBufferNano.readBool();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            boolean bl = this.wifiIsUnusableLoggingEnabled;
            if (bl) {
                codedOutputByteBufferNano.writeBool(1, bl);
            }
            if ((n = this.wifiDataStallMinTxBad) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.wifiDataStallMinTxSuccessWithoutRx) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if (bl = this.linkSpeedCountsLoggingEnabled) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class GroupEvent
    extends MessageNano {
        public static final int GROUP_CLIENT = 1;
        public static final int GROUP_OWNER = 0;
        private static volatile GroupEvent[] _emptyArray;
        public int channelFrequency;
        public int groupRole;
        public int idleDurationMillis;
        public int netId;
        public int numConnectedClients;
        public int numCumulativeClients;
        public int sessionDurationMillis;
        public long startTimeMillis;

        public GroupEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static GroupEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new GroupEvent[0];
                return _emptyArray;
            }
        }

        public static GroupEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new GroupEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static GroupEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new GroupEvent(), arrby);
        }

        public GroupEvent clear() {
            this.netId = 0;
            this.startTimeMillis = 0L;
            this.channelFrequency = 0;
            this.groupRole = 0;
            this.numConnectedClients = 0;
            this.numCumulativeClients = 0;
            this.sessionDurationMillis = 0;
            this.idleDurationMillis = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.netId;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            long l = this.startTimeMillis;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            n2 = this.channelFrequency;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.groupRole;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            n2 = this.numConnectedClients;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(6, n2);
            }
            n = this.numCumulativeClients;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(7, n);
            }
            n3 = this.sessionDurationMillis;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(8, n3);
            }
            n2 = this.idleDurationMillis;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(9, n2);
            }
            return n3;
        }

        @Override
        public GroupEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 40) {
                                if (n != 48) {
                                    if (n != 56) {
                                        if (n != 64) {
                                            if (n != 72) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            this.idleDurationMillis = codedInputByteBufferNano.readInt32();
                                            continue;
                                        }
                                        this.sessionDurationMillis = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    this.numCumulativeClients = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                this.numConnectedClients = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            n = codedInputByteBufferNano.readInt32();
                            if (n != 0 && n != 1) continue;
                            this.groupRole = n;
                            continue;
                        }
                        this.channelFrequency = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.startTimeMillis = codedInputByteBufferNano.readInt64();
                    continue;
                }
                this.netId = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l;
            int n = this.netId;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((l = this.startTimeMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((n = this.channelFrequency) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.groupRole) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.numConnectedClients) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.numCumulativeClients) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.sessionDurationMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.idleDurationMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class HistogramBucketInt32
    extends MessageNano {
        private static volatile HistogramBucketInt32[] _emptyArray;
        public int count;
        public int end;
        public int start;

        public HistogramBucketInt32() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static HistogramBucketInt32[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new HistogramBucketInt32[0];
                return _emptyArray;
            }
        }

        public static HistogramBucketInt32 parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new HistogramBucketInt32().mergeFrom(codedInputByteBufferNano);
        }

        public static HistogramBucketInt32 parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new HistogramBucketInt32(), arrby);
        }

        public HistogramBucketInt32 clear() {
            this.start = 0;
            this.end = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.start;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.end;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.count;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            return n3;
        }

        @Override
        public HistogramBucketInt32 mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.end = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.start = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.start;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.end) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class Int32Count
    extends MessageNano {
        private static volatile Int32Count[] _emptyArray;
        public int count;
        public int key;

        public Int32Count() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static Int32Count[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new Int32Count[0];
                return _emptyArray;
            }
        }

        public static Int32Count parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new Int32Count().mergeFrom(codedInputByteBufferNano);
        }

        public static Int32Count parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new Int32Count(), arrby);
        }

        public Int32Count clear() {
            this.key = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.key;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public Int32Count mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.key = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.key;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class LinkProbeStats
    extends MessageNano {
        public static final int LINK_PROBE_FAILURE_REASON_ALREADY_STARTED = 4;
        public static final int LINK_PROBE_FAILURE_REASON_MCS_UNSUPPORTED = 1;
        public static final int LINK_PROBE_FAILURE_REASON_NO_ACK = 2;
        public static final int LINK_PROBE_FAILURE_REASON_TIMEOUT = 3;
        public static final int LINK_PROBE_FAILURE_REASON_UNKNOWN = 0;
        private static volatile LinkProbeStats[] _emptyArray;
        public ExperimentProbeCounts[] experimentProbeCounts;
        public Int32Count[] failureLinkSpeedCounts;
        public LinkProbeFailureReasonCount[] failureReasonCounts;
        public Int32Count[] failureRssiCounts;
        public HistogramBucketInt32[] failureSecondsSinceLastTxSuccessHistogram;
        public HistogramBucketInt32[] successElapsedTimeMsHistogram;
        public Int32Count[] successLinkSpeedCounts;
        public Int32Count[] successRssiCounts;
        public HistogramBucketInt32[] successSecondsSinceLastTxSuccessHistogram;

        public LinkProbeStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static LinkProbeStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new LinkProbeStats[0];
                return _emptyArray;
            }
        }

        public static LinkProbeStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new LinkProbeStats().mergeFrom(codedInputByteBufferNano);
        }

        public static LinkProbeStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new LinkProbeStats(), arrby);
        }

        public LinkProbeStats clear() {
            this.successRssiCounts = Int32Count.emptyArray();
            this.failureRssiCounts = Int32Count.emptyArray();
            this.successLinkSpeedCounts = Int32Count.emptyArray();
            this.failureLinkSpeedCounts = Int32Count.emptyArray();
            this.successSecondsSinceLastTxSuccessHistogram = HistogramBucketInt32.emptyArray();
            this.failureSecondsSinceLastTxSuccessHistogram = HistogramBucketInt32.emptyArray();
            this.successElapsedTimeMsHistogram = HistogramBucketInt32.emptyArray();
            this.failureReasonCounts = LinkProbeFailureReasonCount.emptyArray();
            this.experimentProbeCounts = ExperimentProbeCounts.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.successRssiCounts;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.successRssiCounts;
                        n3 = n2;
                        if (n >= ((Int32Count[])object).length) break;
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
            object = this.failureRssiCounts;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.failureRssiCounts;
                        n2 = n3;
                        if (n >= ((Int32Count[])object).length) break;
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
            object = this.successLinkSpeedCounts;
            n = n2;
            if (object != null) {
                n = n2;
                if (((Int32Count[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.successLinkSpeedCounts;
                        n = n2;
                        if (n3 >= ((Int32Count[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            object = this.failureLinkSpeedCounts;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Int32Count[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.failureLinkSpeedCounts;
                        n3 = n;
                        if (n2 >= ((Int32Count[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.successSecondsSinceLastTxSuccessHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.successSecondsSinceLastTxSuccessHistogram;
                        n2 = n3;
                        if (n >= ((Int32Count[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.failureSecondsSinceLastTxSuccessHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.failureSecondsSinceLastTxSuccessHistogram;
                        n3 = n2;
                        if (n >= ((Int32Count[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.successElapsedTimeMsHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.successElapsedTimeMsHistogram;
                        n2 = n3;
                        if (n >= ((Int32Count[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.failureReasonCounts;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.failureReasonCounts;
                        n3 = n2;
                        if (n >= ((Int32Count[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.experimentProbeCounts;
            n = n3;
            if (object != null) {
                n = n3;
                if (((Int32Count[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.experimentProbeCounts;
                        n = n3;
                        if (n2 >= ((Int32Count[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            return n;
        }

        @Override
        public LinkProbeStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                MessageNano[] arrmessageNano;
                int n2;
                if (n != 10) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 34) {
                                if (n != 42) {
                                    if (n != 50) {
                                        if (n != 58) {
                                            if (n != 66) {
                                                if (n != 74) {
                                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                    return this;
                                                }
                                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                                                arrmessageNano = this.experimentProbeCounts;
                                                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                                arrmessageNano = new ExperimentProbeCounts[n + n2];
                                                n2 = n;
                                                if (n != 0) {
                                                    System.arraycopy(this.experimentProbeCounts, 0, arrmessageNano, 0, n);
                                                    n2 = n;
                                                }
                                                while (n2 < arrmessageNano.length - 1) {
                                                    arrmessageNano[n2] = new ExperimentProbeCounts();
                                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                    codedInputByteBufferNano.readTag();
                                                    ++n2;
                                                }
                                                arrmessageNano[n2] = new ExperimentProbeCounts();
                                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                this.experimentProbeCounts = arrmessageNano;
                                                continue;
                                            }
                                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                                            arrmessageNano = this.failureReasonCounts;
                                            n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                            arrmessageNano = new LinkProbeFailureReasonCount[n + n2];
                                            n2 = n;
                                            if (n != 0) {
                                                System.arraycopy(this.failureReasonCounts, 0, arrmessageNano, 0, n);
                                                n2 = n;
                                            }
                                            while (n2 < arrmessageNano.length - 1) {
                                                arrmessageNano[n2] = new LinkProbeFailureReasonCount();
                                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                codedInputByteBufferNano.readTag();
                                                ++n2;
                                            }
                                            arrmessageNano[n2] = new LinkProbeFailureReasonCount();
                                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                            this.failureReasonCounts = arrmessageNano;
                                            continue;
                                        }
                                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                                        arrmessageNano = this.successElapsedTimeMsHistogram;
                                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                        arrmessageNano = new HistogramBucketInt32[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.successElapsedTimeMsHistogram, 0, arrmessageNano, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrmessageNano.length - 1) {
                                            arrmessageNano[n2] = new HistogramBucketInt32();
                                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                            codedInputByteBufferNano.readTag();
                                            ++n2;
                                        }
                                        arrmessageNano[n2] = new HistogramBucketInt32();
                                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                        this.successElapsedTimeMsHistogram = arrmessageNano;
                                        continue;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                                    arrmessageNano = this.failureSecondsSinceLastTxSuccessHistogram;
                                    n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                    arrmessageNano = new HistogramBucketInt32[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.failureSecondsSinceLastTxSuccessHistogram, 0, arrmessageNano, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrmessageNano.length - 1) {
                                        arrmessageNano[n2] = new HistogramBucketInt32();
                                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrmessageNano[n2] = new HistogramBucketInt32();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    this.failureSecondsSinceLastTxSuccessHistogram = arrmessageNano;
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                arrmessageNano = this.successSecondsSinceLastTxSuccessHistogram;
                                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                arrmessageNano = new HistogramBucketInt32[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.successSecondsSinceLastTxSuccessHistogram, 0, arrmessageNano, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrmessageNano.length - 1) {
                                    arrmessageNano[n2] = new HistogramBucketInt32();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrmessageNano[n2] = new HistogramBucketInt32();
                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                this.successSecondsSinceLastTxSuccessHistogram = arrmessageNano;
                                continue;
                            }
                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            arrmessageNano = this.failureLinkSpeedCounts;
                            n = arrmessageNano == null ? 0 : arrmessageNano.length;
                            arrmessageNano = new Int32Count[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.failureLinkSpeedCounts, 0, arrmessageNano, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrmessageNano.length - 1) {
                                arrmessageNano[n2] = new Int32Count();
                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrmessageNano[n2] = new Int32Count();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            this.failureLinkSpeedCounts = arrmessageNano;
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        arrmessageNano = this.successLinkSpeedCounts;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new Int32Count[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.successLinkSpeedCounts, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new Int32Count();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new Int32Count();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.successLinkSpeedCounts = arrmessageNano;
                        continue;
                    }
                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    arrmessageNano = this.failureRssiCounts;
                    n = arrmessageNano == null ? 0 : arrmessageNano.length;
                    arrmessageNano = new Int32Count[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.failureRssiCounts, 0, arrmessageNano, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrmessageNano.length - 1) {
                        arrmessageNano[n2] = new Int32Count();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrmessageNano[n2] = new Int32Count();
                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                    this.failureRssiCounts = arrmessageNano;
                    continue;
                }
                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                arrmessageNano = this.successRssiCounts;
                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                arrmessageNano = new Int32Count[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.successRssiCounts, 0, arrmessageNano, 0, n);
                    n2 = n;
                }
                while (n2 < arrmessageNano.length - 1) {
                    arrmessageNano[n2] = new Int32Count();
                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arrmessageNano[n2] = new Int32Count();
                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                this.successRssiCounts = arrmessageNano;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.successRssiCounts;
            if (object != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.successRssiCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((object = this.failureRssiCounts) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.failureRssiCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((object = this.successLinkSpeedCounts) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.successLinkSpeedCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if ((object = this.failureLinkSpeedCounts) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.failureLinkSpeedCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if ((object = this.successSecondsSinceLastTxSuccessHistogram) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.successSecondsSinceLastTxSuccessHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                }
            }
            if ((object = this.failureSecondsSinceLastTxSuccessHistogram) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.failureSecondsSinceLastTxSuccessHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
                }
            }
            if ((object = this.successElapsedTimeMsHistogram) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.successElapsedTimeMsHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                }
            }
            if ((object = this.failureReasonCounts) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.failureReasonCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
                }
            }
            if ((object = this.experimentProbeCounts) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.experimentProbeCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(9, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class ExperimentProbeCounts
        extends MessageNano {
            private static volatile ExperimentProbeCounts[] _emptyArray;
            public String experimentId;
            public int probeCount;

            public ExperimentProbeCounts() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static ExperimentProbeCounts[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new ExperimentProbeCounts[0];
                    return _emptyArray;
                }
            }

            public static ExperimentProbeCounts parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ExperimentProbeCounts().mergeFrom(codedInputByteBufferNano);
            }

            public static ExperimentProbeCounts parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new ExperimentProbeCounts(), arrby);
            }

            public ExperimentProbeCounts clear() {
                this.experimentId = "";
                this.probeCount = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n;
                int n2 = n = super.computeSerializedSize();
                if (!this.experimentId.equals("")) {
                    n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.experimentId);
                }
                int n3 = this.probeCount;
                n = n2;
                if (n3 != 0) {
                    n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
                }
                return n;
            }

            @Override
            public ExperimentProbeCounts mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 10) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.probeCount = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.experimentId = codedInputByteBufferNano.readString();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n;
                if (!this.experimentId.equals("")) {
                    codedOutputByteBufferNano.writeString(1, this.experimentId);
                }
                if ((n = this.probeCount) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class LinkProbeFailureReasonCount
        extends MessageNano {
            private static volatile LinkProbeFailureReasonCount[] _emptyArray;
            public int count;
            public int failureReason;

            public LinkProbeFailureReasonCount() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static LinkProbeFailureReasonCount[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new LinkProbeFailureReasonCount[0];
                    return _emptyArray;
                }
            }

            public static LinkProbeFailureReasonCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new LinkProbeFailureReasonCount().mergeFrom(codedInputByteBufferNano);
            }

            public static LinkProbeFailureReasonCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new LinkProbeFailureReasonCount(), arrby);
            }

            public LinkProbeFailureReasonCount clear() {
                this.failureReason = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.failureReason;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public LinkProbeFailureReasonCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue;
                    this.failureReason = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.failureReason;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class LinkSpeedCount
    extends MessageNano {
        private static volatile LinkSpeedCount[] _emptyArray;
        public int count;
        public int linkSpeedMbps;
        public int rssiSumDbm;
        public long rssiSumOfSquaresDbmSq;

        public LinkSpeedCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static LinkSpeedCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new LinkSpeedCount[0];
                return _emptyArray;
            }
        }

        public static LinkSpeedCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new LinkSpeedCount().mergeFrom(codedInputByteBufferNano);
        }

        public static LinkSpeedCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new LinkSpeedCount(), arrby);
        }

        public LinkSpeedCount clear() {
            this.linkSpeedMbps = 0;
            this.count = 0;
            this.rssiSumDbm = 0;
            this.rssiSumOfSquaresDbmSq = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.linkSpeedMbps;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.rssiSumDbm;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            long l = this.rssiSumOfSquaresDbmSq;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            return n;
        }

        @Override
        public LinkSpeedCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.rssiSumOfSquaresDbmSq = codedInputByteBufferNano.readInt64();
                            continue;
                        }
                        this.rssiSumDbm = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.linkSpeedMbps = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l;
            int n = this.linkSpeedMbps;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.rssiSumDbm) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((l = this.rssiSumOfSquaresDbmSq) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class NetworkSelectionExperimentDecisions
    extends MessageNano {
        private static volatile NetworkSelectionExperimentDecisions[] _emptyArray;
        public Int32Count[] differentSelectionNumChoicesCounter;
        public int experiment1Id;
        public int experiment2Id;
        public Int32Count[] sameSelectionNumChoicesCounter;

        public NetworkSelectionExperimentDecisions() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static NetworkSelectionExperimentDecisions[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new NetworkSelectionExperimentDecisions[0];
                return _emptyArray;
            }
        }

        public static NetworkSelectionExperimentDecisions parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkSelectionExperimentDecisions().mergeFrom(codedInputByteBufferNano);
        }

        public static NetworkSelectionExperimentDecisions parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new NetworkSelectionExperimentDecisions(), arrby);
        }

        public NetworkSelectionExperimentDecisions clear() {
            this.experiment1Id = 0;
            this.experiment2Id = 0;
            this.sameSelectionNumChoicesCounter = Int32Count.emptyArray();
            this.differentSelectionNumChoicesCounter = Int32Count.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.experiment1Id;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.experiment2Id;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            Object object = this.sameSelectionNumChoicesCounter;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Int32Count[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.sameSelectionNumChoicesCounter;
                        n3 = n;
                        if (n2 >= ((Int32Count[])object).length) break;
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
            object = this.differentSelectionNumChoicesCounter;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((Int32Count[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.differentSelectionNumChoicesCounter;
                        n2 = n3;
                        if (n >= ((Int32Count[])object).length) break;
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
            return n2;
        }

        @Override
        public NetworkSelectionExperimentDecisions mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        int n2;
                        Int32Count[] arrint32Count;
                        if (n != 26) {
                            if (n != 34) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            arrint32Count = this.differentSelectionNumChoicesCounter;
                            n = arrint32Count == null ? 0 : arrint32Count.length;
                            arrint32Count = new Int32Count[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.differentSelectionNumChoicesCounter, 0, arrint32Count, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrint32Count.length - 1) {
                                arrint32Count[n2] = new Int32Count();
                                codedInputByteBufferNano.readMessage(arrint32Count[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrint32Count[n2] = new Int32Count();
                            codedInputByteBufferNano.readMessage(arrint32Count[n2]);
                            this.differentSelectionNumChoicesCounter = arrint32Count;
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        arrint32Count = this.sameSelectionNumChoicesCounter;
                        n = arrint32Count == null ? 0 : arrint32Count.length;
                        arrint32Count = new Int32Count[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.sameSelectionNumChoicesCounter, 0, arrint32Count, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrint32Count.length - 1) {
                            arrint32Count[n2] = new Int32Count();
                            codedInputByteBufferNano.readMessage(arrint32Count[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrint32Count[n2] = new Int32Count();
                        codedInputByteBufferNano.readMessage(arrint32Count[n2]);
                        this.sameSelectionNumChoicesCounter = arrint32Count;
                        continue;
                    }
                    this.experiment2Id = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.experiment1Id = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.experiment1Id;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.experiment2Id) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((object = this.sameSelectionNumChoicesCounter) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.sameSelectionNumChoicesCounter)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if ((object = this.differentSelectionNumChoicesCounter) != null && ((Int32Count[])object).length > 0) {
                for (n = 0; n < ((Int32Count[])(object = this.differentSelectionNumChoicesCounter)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class NumConnectableNetworksBucket
    extends MessageNano {
        private static volatile NumConnectableNetworksBucket[] _emptyArray;
        public int count;
        public int numConnectableNetworks;

        public NumConnectableNetworksBucket() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static NumConnectableNetworksBucket[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new NumConnectableNetworksBucket[0];
                return _emptyArray;
            }
        }

        public static NumConnectableNetworksBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NumConnectableNetworksBucket().mergeFrom(codedInputByteBufferNano);
        }

        public static NumConnectableNetworksBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new NumConnectableNetworksBucket(), arrby);
        }

        public NumConnectableNetworksBucket clear() {
            this.numConnectableNetworks = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numConnectableNetworks;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public NumConnectableNetworksBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numConnectableNetworks = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.numConnectableNetworks;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class P2pConnectionEvent
    extends MessageNano {
        public static final int CLF_CANCEL = 3;
        public static final int CLF_INVITATION_FAIL = 5;
        public static final int CLF_NEW_CONNECTION_ATTEMPT = 7;
        public static final int CLF_NONE = 1;
        public static final int CLF_PROV_DISC_FAIL = 4;
        public static final int CLF_TIMEOUT = 2;
        public static final int CLF_UNKNOWN = 0;
        public static final int CLF_USER_REJECT = 6;
        public static final int CONNECTION_FAST = 3;
        public static final int CONNECTION_FRESH = 0;
        public static final int CONNECTION_LOCAL = 2;
        public static final int CONNECTION_REINVOKE = 1;
        public static final int WPS_DISPLAY = 1;
        public static final int WPS_KEYPAD = 2;
        public static final int WPS_LABEL = 3;
        public static final int WPS_NA = -1;
        public static final int WPS_PBC = 0;
        private static volatile P2pConnectionEvent[] _emptyArray;
        public int connectionType;
        public int connectivityLevelFailureCode;
        public int durationTakenToConnectMillis;
        public long startTimeMillis;
        public int wpsMethod;

        public P2pConnectionEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static P2pConnectionEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new P2pConnectionEvent[0];
                return _emptyArray;
            }
        }

        public static P2pConnectionEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new P2pConnectionEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static P2pConnectionEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new P2pConnectionEvent(), arrby);
        }

        public P2pConnectionEvent clear() {
            this.startTimeMillis = 0L;
            this.connectionType = 0;
            this.wpsMethod = -1;
            this.durationTakenToConnectMillis = 0;
            this.connectivityLevelFailureCode = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.startTimeMillis;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.connectionType;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.wpsMethod;
            n2 = n;
            if (n3 != -1) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n3 = this.durationTakenToConnectMillis;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            n3 = this.connectivityLevelFailureCode;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            return n2;
        }

        @Override
        public P2pConnectionEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                n = codedInputByteBufferNano.readInt32();
                                switch (n) {
                                    default: {
                                        break;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                    case 7: {
                                        this.connectivityLevelFailureCode = n;
                                        break;
                                    }
                                }
                                continue;
                            }
                            this.durationTakenToConnectMillis = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        n = codedInputByteBufferNano.readInt32();
                        if (n != -1 && n != 0 && n != 1 && n != 2 && n != 3) continue;
                        this.wpsMethod = n;
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                    this.connectionType = n;
                    continue;
                }
                this.startTimeMillis = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            long l = this.startTimeMillis;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.connectionType) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.wpsMethod) != -1) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.durationTakenToConnectMillis) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.connectivityLevelFailureCode) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class PasspointProfileTypeCount
    extends MessageNano {
        public static final int TYPE_EAP_AKA = 4;
        public static final int TYPE_EAP_AKA_PRIME = 5;
        public static final int TYPE_EAP_SIM = 3;
        public static final int TYPE_EAP_TLS = 1;
        public static final int TYPE_EAP_TTLS = 2;
        public static final int TYPE_UNKNOWN = 0;
        private static volatile PasspointProfileTypeCount[] _emptyArray;
        public int count;
        public int eapMethodType;

        public PasspointProfileTypeCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static PasspointProfileTypeCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new PasspointProfileTypeCount[0];
                return _emptyArray;
            }
        }

        public static PasspointProfileTypeCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PasspointProfileTypeCount().mergeFrom(codedInputByteBufferNano);
        }

        public static PasspointProfileTypeCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new PasspointProfileTypeCount(), arrby);
        }

        public PasspointProfileTypeCount clear() {
            this.eapMethodType = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.eapMethodType;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public PasspointProfileTypeCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5) continue;
                this.eapMethodType = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.eapMethodType;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class PasspointProvisionStats
    extends MessageNano {
        public static final int OSU_FAILURE_ADD_PASSPOINT_CONFIGURATION = 22;
        public static final int OSU_FAILURE_AP_CONNECTION = 1;
        public static final int OSU_FAILURE_INVALID_URL_FORMAT_FOR_OSU = 8;
        public static final int OSU_FAILURE_NO_AAA_SERVER_TRUST_ROOT_NODE = 17;
        public static final int OSU_FAILURE_NO_AAA_TRUST_ROOT_CERTIFICATE = 21;
        public static final int OSU_FAILURE_NO_OSU_ACTIVITY_FOUND = 14;
        public static final int OSU_FAILURE_NO_POLICY_SERVER_TRUST_ROOT_NODE = 19;
        public static final int OSU_FAILURE_NO_PPS_MO = 16;
        public static final int OSU_FAILURE_NO_REMEDIATION_SERVER_TRUST_ROOT_NODE = 18;
        public static final int OSU_FAILURE_OSU_PROVIDER_NOT_FOUND = 23;
        public static final int OSU_FAILURE_PROVISIONING_ABORTED = 6;
        public static final int OSU_FAILURE_PROVISIONING_NOT_AVAILABLE = 7;
        public static final int OSU_FAILURE_RETRIEVE_TRUST_ROOT_CERTIFICATES = 20;
        public static final int OSU_FAILURE_SERVER_CONNECTION = 3;
        public static final int OSU_FAILURE_SERVER_URL_INVALID = 2;
        public static final int OSU_FAILURE_SERVER_VALIDATION = 4;
        public static final int OSU_FAILURE_SERVICE_PROVIDER_VERIFICATION = 5;
        public static final int OSU_FAILURE_SOAP_MESSAGE_EXCHANGE = 11;
        public static final int OSU_FAILURE_START_REDIRECT_LISTENER = 12;
        public static final int OSU_FAILURE_TIMED_OUT_REDIRECT_LISTENER = 13;
        public static final int OSU_FAILURE_UNEXPECTED_COMMAND_TYPE = 9;
        public static final int OSU_FAILURE_UNEXPECTED_SOAP_MESSAGE_STATUS = 15;
        public static final int OSU_FAILURE_UNEXPECTED_SOAP_MESSAGE_TYPE = 10;
        public static final int OSU_FAILURE_UNKNOWN = 0;
        private static volatile PasspointProvisionStats[] _emptyArray;
        public int numProvisionSuccess;
        public ProvisionFailureCount[] provisionFailureCount;

        public PasspointProvisionStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static PasspointProvisionStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new PasspointProvisionStats[0];
                return _emptyArray;
            }
        }

        public static PasspointProvisionStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PasspointProvisionStats().mergeFrom(codedInputByteBufferNano);
        }

        public static PasspointProvisionStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new PasspointProvisionStats(), arrby);
        }

        public PasspointProvisionStats clear() {
            this.numProvisionSuccess = 0;
            this.provisionFailureCount = ProvisionFailureCount.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numProvisionSuccess;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.provisionFailureCount;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ProvisionFailureCount[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.provisionFailureCount;
                        n2 = n3;
                        if (n >= ((ProvisionFailureCount[])object).length) break;
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
        public PasspointProvisionStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    ProvisionFailureCount[] arrprovisionFailureCount = this.provisionFailureCount;
                    n = arrprovisionFailureCount == null ? 0 : arrprovisionFailureCount.length;
                    arrprovisionFailureCount = new ProvisionFailureCount[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.provisionFailureCount, 0, arrprovisionFailureCount, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrprovisionFailureCount.length - 1) {
                        arrprovisionFailureCount[n2] = new ProvisionFailureCount();
                        codedInputByteBufferNano.readMessage(arrprovisionFailureCount[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrprovisionFailureCount[n2] = new ProvisionFailureCount();
                    codedInputByteBufferNano.readMessage(arrprovisionFailureCount[n2]);
                    this.provisionFailureCount = arrprovisionFailureCount;
                    continue;
                }
                this.numProvisionSuccess = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numProvisionSuccess;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.provisionFailureCount) != null && ((ProvisionFailureCount[])object).length > 0) {
                for (n = 0; n < ((ProvisionFailureCount[])(object = this.provisionFailureCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class ProvisionFailureCount
        extends MessageNano {
            private static volatile ProvisionFailureCount[] _emptyArray;
            public int count;
            public int failureCode;

            public ProvisionFailureCount() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static ProvisionFailureCount[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new ProvisionFailureCount[0];
                    return _emptyArray;
                }
            }

            public static ProvisionFailureCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ProvisionFailureCount().mergeFrom(codedInputByteBufferNano);
            }

            public static ProvisionFailureCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new ProvisionFailureCount(), arrby);
            }

            public ProvisionFailureCount clear() {
                this.failureCode = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.failureCode;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public ProvisionFailureCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    switch (n) {
                        default: {
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
                    this.failureCode = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.failureCode;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class PnoScanMetrics
    extends MessageNano {
        private static volatile PnoScanMetrics[] _emptyArray;
        public int numPnoFoundNetworkEvents;
        public int numPnoScanAttempts;
        public int numPnoScanFailed;
        public int numPnoScanFailedOverOffload;
        public int numPnoScanStartedOverOffload;

        public PnoScanMetrics() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static PnoScanMetrics[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new PnoScanMetrics[0];
                return _emptyArray;
            }
        }

        public static PnoScanMetrics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PnoScanMetrics().mergeFrom(codedInputByteBufferNano);
        }

        public static PnoScanMetrics parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new PnoScanMetrics(), arrby);
        }

        public PnoScanMetrics clear() {
            this.numPnoScanAttempts = 0;
            this.numPnoScanFailed = 0;
            this.numPnoScanStartedOverOffload = 0;
            this.numPnoScanFailedOverOffload = 0;
            this.numPnoFoundNetworkEvents = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numPnoScanAttempts;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numPnoScanFailed;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.numPnoScanStartedOverOffload;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.numPnoScanFailedOverOffload;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.numPnoFoundNetworkEvents;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            return n3;
        }

        @Override
        public PnoScanMetrics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                this.numPnoFoundNetworkEvents = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.numPnoScanFailedOverOffload = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numPnoScanStartedOverOffload = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numPnoScanFailed = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numPnoScanAttempts = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.numPnoScanAttempts;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numPnoScanFailed) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numPnoScanStartedOverOffload) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numPnoScanFailedOverOffload) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.numPnoFoundNetworkEvents) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class RouterFingerPrint
    extends MessageNano {
        public static final int AUTH_ENTERPRISE = 3;
        public static final int AUTH_OPEN = 1;
        public static final int AUTH_PERSONAL = 2;
        public static final int AUTH_UNKNOWN = 0;
        public static final int ROAM_TYPE_DBDC = 3;
        public static final int ROAM_TYPE_ENTERPRISE = 2;
        public static final int ROAM_TYPE_NONE = 1;
        public static final int ROAM_TYPE_UNKNOWN = 0;
        public static final int ROUTER_TECH_A = 1;
        public static final int ROUTER_TECH_AC = 5;
        public static final int ROUTER_TECH_B = 2;
        public static final int ROUTER_TECH_G = 3;
        public static final int ROUTER_TECH_N = 4;
        public static final int ROUTER_TECH_OTHER = 6;
        public static final int ROUTER_TECH_UNKNOWN = 0;
        private static volatile RouterFingerPrint[] _emptyArray;
        public int authentication;
        public int channelInfo;
        public int dtim;
        public boolean hidden;
        public boolean passpoint;
        public int roamType;
        public int routerTechnology;
        public boolean supportsIpv6;

        public RouterFingerPrint() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static RouterFingerPrint[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new RouterFingerPrint[0];
                return _emptyArray;
            }
        }

        public static RouterFingerPrint parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new RouterFingerPrint().mergeFrom(codedInputByteBufferNano);
        }

        public static RouterFingerPrint parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new RouterFingerPrint(), arrby);
        }

        public RouterFingerPrint clear() {
            this.roamType = 0;
            this.channelInfo = 0;
            this.dtim = 0;
            this.authentication = 0;
            this.hidden = false;
            this.routerTechnology = 0;
            this.supportsIpv6 = false;
            this.passpoint = false;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.roamType;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.channelInfo;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.dtim;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.authentication;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            boolean bl = this.hidden;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(5, bl);
            }
            n = this.routerTechnology;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(6, n);
            }
            bl = this.supportsIpv6;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(7, bl);
            }
            bl = this.passpoint;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(8, bl);
            }
            return n3;
        }

        @Override
        public RouterFingerPrint mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (n != 56) {
                                            if (n != 64) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            this.passpoint = codedInputByteBufferNano.readBool();
                                            continue;
                                        }
                                        this.supportsIpv6 = codedInputByteBufferNano.readBool();
                                        continue;
                                    }
                                    n = codedInputByteBufferNano.readInt32();
                                    switch (n) {
                                        default: {
                                            break;
                                        }
                                        case 0: 
                                        case 1: 
                                        case 2: 
                                        case 3: 
                                        case 4: 
                                        case 5: 
                                        case 6: {
                                            this.routerTechnology = n;
                                            break;
                                        }
                                    }
                                    continue;
                                }
                                this.hidden = codedInputByteBufferNano.readBool();
                                continue;
                            }
                            n = codedInputByteBufferNano.readInt32();
                            if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                            this.authentication = n;
                            continue;
                        }
                        this.dtim = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.channelInfo = codedInputByteBufferNano.readInt32();
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                this.roamType = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean bl;
            int n = this.roamType;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.channelInfo) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.dtim) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.authentication) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if (bl = this.hidden) {
                codedOutputByteBufferNano.writeBool(5, bl);
            }
            if ((n = this.routerTechnology) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if (bl = this.supportsIpv6) {
                codedOutputByteBufferNano.writeBool(7, bl);
            }
            if (bl = this.passpoint) {
                codedOutputByteBufferNano.writeBool(8, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class RssiPollCount
    extends MessageNano {
        private static volatile RssiPollCount[] _emptyArray;
        public int count;
        public int frequency;
        public int rssi;

        public RssiPollCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static RssiPollCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new RssiPollCount[0];
                return _emptyArray;
            }
        }

        public static RssiPollCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new RssiPollCount().mergeFrom(codedInputByteBufferNano);
        }

        public static RssiPollCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new RssiPollCount(), arrby);
        }

        public RssiPollCount clear() {
            this.rssi = 0;
            this.count = 0;
            this.frequency = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.rssi;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.frequency;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            return n3;
        }

        @Override
        public RssiPollCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.frequency = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.rssi = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.rssi;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.frequency) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class SoftApConnectedClientsEvent
    extends MessageNano {
        public static final int BANDWIDTH_160 = 6;
        public static final int BANDWIDTH_20 = 2;
        public static final int BANDWIDTH_20_NOHT = 1;
        public static final int BANDWIDTH_40 = 3;
        public static final int BANDWIDTH_80 = 4;
        public static final int BANDWIDTH_80P80 = 5;
        public static final int BANDWIDTH_INVALID = 0;
        public static final int NUM_CLIENTS_CHANGED = 2;
        public static final int SOFT_AP_DOWN = 1;
        public static final int SOFT_AP_UP = 0;
        private static volatile SoftApConnectedClientsEvent[] _emptyArray;
        public int channelBandwidth;
        public int channelFrequency;
        public int eventType;
        public int numConnectedClients;
        public long timeStampMillis;

        public SoftApConnectedClientsEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static SoftApConnectedClientsEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new SoftApConnectedClientsEvent[0];
                return _emptyArray;
            }
        }

        public static SoftApConnectedClientsEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SoftApConnectedClientsEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static SoftApConnectedClientsEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new SoftApConnectedClientsEvent(), arrby);
        }

        public SoftApConnectedClientsEvent clear() {
            this.eventType = 0;
            this.timeStampMillis = 0L;
            this.numConnectedClients = 0;
            this.channelFrequency = 0;
            this.channelBandwidth = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.eventType;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            long l = this.timeStampMillis;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            n2 = this.numConnectedClients;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.channelFrequency;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.channelBandwidth;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            return n3;
        }

        @Override
        public SoftApConnectedClientsEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                n = codedInputByteBufferNano.readInt32();
                                switch (n) {
                                    default: {
                                        break;
                                    }
                                    case 0: 
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: {
                                        this.channelBandwidth = n;
                                        break;
                                    }
                                }
                                continue;
                            }
                            this.channelFrequency = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numConnectedClients = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.timeStampMillis = codedInputByteBufferNano.readInt64();
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2) continue;
                this.eventType = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l;
            int n = this.eventType;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((l = this.timeStampMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((n = this.numConnectedClients) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.channelFrequency) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.channelBandwidth) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class SoftApDurationBucket
    extends MessageNano {
        private static volatile SoftApDurationBucket[] _emptyArray;
        public int bucketSizeSec;
        public int count;
        public int durationSec;

        public SoftApDurationBucket() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static SoftApDurationBucket[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new SoftApDurationBucket[0];
                return _emptyArray;
            }
        }

        public static SoftApDurationBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SoftApDurationBucket().mergeFrom(codedInputByteBufferNano);
        }

        public static SoftApDurationBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new SoftApDurationBucket(), arrby);
        }

        public SoftApDurationBucket clear() {
            this.durationSec = 0;
            this.bucketSizeSec = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.durationSec;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.bucketSizeSec;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.count;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            return n3;
        }

        @Override
        public SoftApDurationBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.bucketSizeSec = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.durationSec = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.durationSec;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.bucketSizeSec) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class SoftApReturnCodeCount
    extends MessageNano {
        public static final int SOFT_AP_FAILED_GENERAL_ERROR = 2;
        public static final int SOFT_AP_FAILED_NO_CHANNEL = 3;
        public static final int SOFT_AP_RETURN_CODE_UNKNOWN = 0;
        public static final int SOFT_AP_STARTED_SUCCESSFULLY = 1;
        private static volatile SoftApReturnCodeCount[] _emptyArray;
        public int count;
        public int returnCode;
        public int startResult;

        public SoftApReturnCodeCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static SoftApReturnCodeCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new SoftApReturnCodeCount[0];
                return _emptyArray;
            }
        }

        public static SoftApReturnCodeCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new SoftApReturnCodeCount().mergeFrom(codedInputByteBufferNano);
        }

        public static SoftApReturnCodeCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new SoftApReturnCodeCount(), arrby);
        }

        public SoftApReturnCodeCount clear() {
            this.returnCode = 0;
            this.count = 0;
            this.startResult = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.returnCode;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.startResult;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            return n3;
        }

        @Override
        public SoftApReturnCodeCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                        this.startResult = n;
                        continue;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.returnCode = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.returnCode;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.startResult) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class StaEvent
    extends MessageNano {
        public static final int AUTH_FAILURE_EAP_FAILURE = 4;
        public static final int AUTH_FAILURE_NONE = 1;
        public static final int AUTH_FAILURE_TIMEOUT = 2;
        public static final int AUTH_FAILURE_UNKNOWN = 0;
        public static final int AUTH_FAILURE_WRONG_PSWD = 3;
        public static final int DISCONNECT_API = 1;
        public static final int DISCONNECT_GENERIC = 2;
        public static final int DISCONNECT_P2P_DISCONNECT_WIFI_REQUEST = 5;
        public static final int DISCONNECT_RESET_SIM_NETWORKS = 6;
        public static final int DISCONNECT_ROAM_WATCHDOG_TIMER = 4;
        public static final int DISCONNECT_UNKNOWN = 0;
        public static final int DISCONNECT_UNWANTED = 3;
        public static final int STATE_ASSOCIATED = 6;
        public static final int STATE_ASSOCIATING = 5;
        public static final int STATE_AUTHENTICATING = 4;
        public static final int STATE_COMPLETED = 9;
        public static final int STATE_DISCONNECTED = 0;
        public static final int STATE_DORMANT = 10;
        public static final int STATE_FOUR_WAY_HANDSHAKE = 7;
        public static final int STATE_GROUP_HANDSHAKE = 8;
        public static final int STATE_INACTIVE = 2;
        public static final int STATE_INTERFACE_DISABLED = 1;
        public static final int STATE_INVALID = 12;
        public static final int STATE_SCANNING = 3;
        public static final int STATE_UNINITIALIZED = 11;
        public static final int TYPE_ASSOCIATION_REJECTION_EVENT = 1;
        public static final int TYPE_AUTHENTICATION_FAILURE_EVENT = 2;
        public static final int TYPE_CMD_ASSOCIATED_BSSID = 6;
        public static final int TYPE_CMD_IP_CONFIGURATION_LOST = 8;
        public static final int TYPE_CMD_IP_CONFIGURATION_SUCCESSFUL = 7;
        public static final int TYPE_CMD_IP_REACHABILITY_LOST = 9;
        public static final int TYPE_CMD_START_CONNECT = 11;
        public static final int TYPE_CMD_START_ROAM = 12;
        public static final int TYPE_CMD_TARGET_BSSID = 10;
        public static final int TYPE_CONNECT_NETWORK = 13;
        public static final int TYPE_FRAMEWORK_DISCONNECT = 15;
        public static final int TYPE_LINK_PROBE = 21;
        public static final int TYPE_MAC_CHANGE = 17;
        public static final int TYPE_NETWORK_AGENT_VALID_NETWORK = 14;
        public static final int TYPE_NETWORK_CONNECTION_EVENT = 3;
        public static final int TYPE_NETWORK_DISCONNECTION_EVENT = 4;
        public static final int TYPE_SCORE_BREACH = 16;
        public static final int TYPE_SUPPLICANT_STATE_CHANGE_EVENT = 5;
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_WIFI_DISABLED = 19;
        public static final int TYPE_WIFI_ENABLED = 18;
        public static final int TYPE_WIFI_USABILITY_SCORE_BREACH = 20;
        private static volatile StaEvent[] _emptyArray;
        public boolean associationTimedOut;
        public int authFailureReason;
        public ConfigInfo configInfo;
        public int frameworkDisconnectReason;
        public int lastFreq;
        public int lastLinkSpeed;
        public int lastPredictionHorizonSec;
        public int lastRssi;
        public int lastScore;
        public int lastWifiUsabilityScore;
        public int linkProbeFailureReason;
        public int linkProbeSuccessElapsedTimeMs;
        public boolean linkProbeWasSuccess;
        public boolean localGen;
        public int reason;
        public long startTimeMillis;
        public int status;
        public int supplicantStateChangesBitmask;
        public int type;

        public StaEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static StaEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new StaEvent[0];
                return _emptyArray;
            }
        }

        public static StaEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new StaEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static StaEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new StaEvent(), arrby);
        }

        public StaEvent clear() {
            this.type = 0;
            this.reason = -1;
            this.status = -1;
            this.localGen = false;
            this.configInfo = null;
            this.lastRssi = -127;
            this.lastLinkSpeed = -1;
            this.lastFreq = -1;
            this.supplicantStateChangesBitmask = 0;
            this.startTimeMillis = 0L;
            this.frameworkDisconnectReason = 0;
            this.associationTimedOut = false;
            this.authFailureReason = 0;
            this.lastScore = -1;
            this.lastWifiUsabilityScore = -1;
            this.lastPredictionHorizonSec = -1;
            this.linkProbeWasSuccess = false;
            this.linkProbeSuccessElapsedTimeMs = 0;
            this.linkProbeFailureReason = 0;
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
            n2 = this.reason;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.status;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            boolean bl = this.localGen;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            ConfigInfo configInfo = this.configInfo;
            n3 = n;
            if (configInfo != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(5, configInfo);
            }
            n2 = this.lastRssi;
            n = n3;
            if (n2 != -127) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(6, n2);
            }
            n2 = this.lastLinkSpeed;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(7, n2);
            }
            n2 = this.lastFreq;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n2);
            }
            n2 = this.supplicantStateChangesBitmask;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeUInt32Size(9, n2);
            }
            long l = this.startTimeMillis;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            n2 = this.frameworkDisconnectReason;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(11, n2);
            }
            bl = this.associationTimedOut;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(12, bl);
            }
            n2 = this.authFailureReason;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(13, n2);
            }
            n2 = this.lastScore;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(14, n2);
            }
            n2 = this.lastWifiUsabilityScore;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(15, n2);
            }
            n2 = this.lastPredictionHorizonSec;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(16, n2);
            }
            bl = this.linkProbeWasSuccess;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(17, bl);
            }
            n = this.linkProbeSuccessElapsedTimeMs;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(18, n);
            }
            n2 = this.linkProbeFailureReason;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(19, n2);
            }
            return n;
        }

        @Override
        public StaEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block28 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block28;
                        return this;
                    }
                    case 152: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue block28;
                        this.linkProbeFailureReason = n;
                        break;
                    }
                    case 144: {
                        this.linkProbeSuccessElapsedTimeMs = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 136: {
                        this.linkProbeWasSuccess = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 128: {
                        this.lastPredictionHorizonSec = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 120: {
                        this.lastWifiUsabilityScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 112: {
                        this.lastScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 104: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue block28;
                        this.authFailureReason = n;
                        break;
                    }
                    case 96: {
                        this.associationTimedOut = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 88: {
                        n = codedInputByteBufferNano.readInt32();
                        switch (n) {
                            default: {
                                break block0;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                        }
                        this.frameworkDisconnectReason = n;
                        break;
                    }
                    case 80: {
                        this.startTimeMillis = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 72: {
                        this.supplicantStateChangesBitmask = codedInputByteBufferNano.readUInt32();
                        break;
                    }
                    case 64: {
                        this.lastFreq = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 56: {
                        this.lastLinkSpeed = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 48: {
                        this.lastRssi = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 42: {
                        if (this.configInfo == null) {
                            this.configInfo = new ConfigInfo();
                        }
                        codedInputByteBufferNano.readMessage(this.configInfo);
                        break;
                    }
                    case 32: {
                        this.localGen = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 24: {
                        this.status = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 16: {
                        this.reason = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 8: {
                        n = codedInputByteBufferNano.readInt32();
                        switch (n) {
                            default: {
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
                        this.type = n;
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
            ConfigInfo configInfo;
            long l;
            boolean bl;
            int n = this.type;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.reason) != -1) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.status) != -1) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if (bl = this.localGen) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            if ((configInfo = this.configInfo) != null) {
                codedOutputByteBufferNano.writeMessage(5, configInfo);
            }
            if ((n = this.lastRssi) != -127) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.lastLinkSpeed) != -1) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.lastFreq) != -1) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.supplicantStateChangesBitmask) != 0) {
                codedOutputByteBufferNano.writeUInt32(9, n);
            }
            if ((l = this.startTimeMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            if ((n = this.frameworkDisconnectReason) != 0) {
                codedOutputByteBufferNano.writeInt32(11, n);
            }
            if (bl = this.associationTimedOut) {
                codedOutputByteBufferNano.writeBool(12, bl);
            }
            if ((n = this.authFailureReason) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            if ((n = this.lastScore) != -1) {
                codedOutputByteBufferNano.writeInt32(14, n);
            }
            if ((n = this.lastWifiUsabilityScore) != -1) {
                codedOutputByteBufferNano.writeInt32(15, n);
            }
            if ((n = this.lastPredictionHorizonSec) != -1) {
                codedOutputByteBufferNano.writeInt32(16, n);
            }
            if (bl = this.linkProbeWasSuccess) {
                codedOutputByteBufferNano.writeBool(17, bl);
            }
            if ((n = this.linkProbeSuccessElapsedTimeMs) != 0) {
                codedOutputByteBufferNano.writeInt32(18, n);
            }
            if ((n = this.linkProbeFailureReason) != 0) {
                codedOutputByteBufferNano.writeInt32(19, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class ConfigInfo
        extends MessageNano {
            private static volatile ConfigInfo[] _emptyArray;
            public int allowedAuthAlgorithms;
            public int allowedGroupCiphers;
            public int allowedKeyManagement;
            public int allowedPairwiseCiphers;
            public int allowedProtocols;
            public boolean hasEverConnected;
            public boolean hiddenSsid;
            public boolean isEphemeral;
            public boolean isPasspoint;
            public int scanFreq;
            public int scanRssi;

            public ConfigInfo() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static ConfigInfo[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new ConfigInfo[0];
                    return _emptyArray;
                }
            }

            public static ConfigInfo parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ConfigInfo().mergeFrom(codedInputByteBufferNano);
            }

            public static ConfigInfo parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new ConfigInfo(), arrby);
            }

            public ConfigInfo clear() {
                this.allowedKeyManagement = 0;
                this.allowedProtocols = 0;
                this.allowedAuthAlgorithms = 0;
                this.allowedPairwiseCiphers = 0;
                this.allowedGroupCiphers = 0;
                this.hiddenSsid = false;
                this.isPasspoint = false;
                this.isEphemeral = false;
                this.hasEverConnected = false;
                this.scanRssi = -127;
                this.scanFreq = -1;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.allowedKeyManagement;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeUInt32Size(1, n2);
                }
                n2 = this.allowedProtocols;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeUInt32Size(2, n2);
                }
                n3 = this.allowedAuthAlgorithms;
                n2 = n;
                if (n3 != 0) {
                    n2 = n + CodedOutputByteBufferNano.computeUInt32Size(3, n3);
                }
                n = this.allowedPairwiseCiphers;
                n3 = n2;
                if (n != 0) {
                    n3 = n2 + CodedOutputByteBufferNano.computeUInt32Size(4, n);
                }
                n2 = this.allowedGroupCiphers;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeUInt32Size(5, n2);
                }
                boolean bl = this.hiddenSsid;
                n2 = n;
                if (bl) {
                    n2 = n + CodedOutputByteBufferNano.computeBoolSize(6, bl);
                }
                bl = this.isPasspoint;
                n3 = n2;
                if (bl) {
                    n3 = n2 + CodedOutputByteBufferNano.computeBoolSize(7, bl);
                }
                bl = this.isEphemeral;
                n2 = n3;
                if (bl) {
                    n2 = n3 + CodedOutputByteBufferNano.computeBoolSize(8, bl);
                }
                bl = this.hasEverConnected;
                n = n2;
                if (bl) {
                    n = n2 + CodedOutputByteBufferNano.computeBoolSize(9, bl);
                }
                n2 = this.scanRssi;
                n3 = n;
                if (n2 != -127) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(10, n2);
                }
                n2 = this.scanFreq;
                n = n3;
                if (n2 != -1) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(11, n2);
                }
                return n;
            }

            @Override
            public ConfigInfo mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                block14 : do {
                    int n = codedInputByteBufferNano.readTag();
                    switch (n) {
                        default: {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block14;
                            return this;
                        }
                        case 88: {
                            this.scanFreq = codedInputByteBufferNano.readInt32();
                            continue block14;
                        }
                        case 80: {
                            this.scanRssi = codedInputByteBufferNano.readInt32();
                            continue block14;
                        }
                        case 72: {
                            this.hasEverConnected = codedInputByteBufferNano.readBool();
                            continue block14;
                        }
                        case 64: {
                            this.isEphemeral = codedInputByteBufferNano.readBool();
                            continue block14;
                        }
                        case 56: {
                            this.isPasspoint = codedInputByteBufferNano.readBool();
                            continue block14;
                        }
                        case 48: {
                            this.hiddenSsid = codedInputByteBufferNano.readBool();
                            continue block14;
                        }
                        case 40: {
                            this.allowedGroupCiphers = codedInputByteBufferNano.readUInt32();
                            continue block14;
                        }
                        case 32: {
                            this.allowedPairwiseCiphers = codedInputByteBufferNano.readUInt32();
                            continue block14;
                        }
                        case 24: {
                            this.allowedAuthAlgorithms = codedInputByteBufferNano.readUInt32();
                            continue block14;
                        }
                        case 16: {
                            this.allowedProtocols = codedInputByteBufferNano.readUInt32();
                            continue block14;
                        }
                        case 8: {
                            this.allowedKeyManagement = codedInputByteBufferNano.readUInt32();
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
                boolean bl;
                int n = this.allowedKeyManagement;
                if (n != 0) {
                    codedOutputByteBufferNano.writeUInt32(1, n);
                }
                if ((n = this.allowedProtocols) != 0) {
                    codedOutputByteBufferNano.writeUInt32(2, n);
                }
                if ((n = this.allowedAuthAlgorithms) != 0) {
                    codedOutputByteBufferNano.writeUInt32(3, n);
                }
                if ((n = this.allowedPairwiseCiphers) != 0) {
                    codedOutputByteBufferNano.writeUInt32(4, n);
                }
                if ((n = this.allowedGroupCiphers) != 0) {
                    codedOutputByteBufferNano.writeUInt32(5, n);
                }
                if (bl = this.hiddenSsid) {
                    codedOutputByteBufferNano.writeBool(6, bl);
                }
                if (bl = this.isPasspoint) {
                    codedOutputByteBufferNano.writeBool(7, bl);
                }
                if (bl = this.isEphemeral) {
                    codedOutputByteBufferNano.writeBool(8, bl);
                }
                if (bl = this.hasEverConnected) {
                    codedOutputByteBufferNano.writeBool(9, bl);
                }
                if ((n = this.scanRssi) != -127) {
                    codedOutputByteBufferNano.writeInt32(10, n);
                }
                if ((n = this.scanFreq) != -1) {
                    codedOutputByteBufferNano.writeInt32(11, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiAwareLog
    extends MessageNano {
        public static final int ALREADY_ENABLED = 11;
        public static final int FOLLOWUP_TX_QUEUE_FULL = 12;
        public static final int INTERNAL_FAILURE = 2;
        public static final int INVALID_ARGS = 6;
        public static final int INVALID_NDP_ID = 8;
        public static final int INVALID_PEER_ID = 7;
        public static final int INVALID_SESSION_ID = 4;
        public static final int NAN_NOT_ALLOWED = 9;
        public static final int NO_OTA_ACK = 10;
        public static final int NO_RESOURCES_AVAILABLE = 5;
        public static final int PROTOCOL_FAILURE = 3;
        public static final int SUCCESS = 1;
        public static final int UNKNOWN = 0;
        public static final int UNKNOWN_HAL_STATUS = 14;
        public static final int UNSUPPORTED_CONCURRENCY_NAN_DISABLED = 13;
        private static volatile WifiAwareLog[] _emptyArray;
        public long availableTimeMs;
        public long enabledTimeMs;
        public HistogramBucket[] histogramAttachDurationMs;
        public NanStatusHistogramBucket[] histogramAttachSessionStatus;
        public HistogramBucket[] histogramAwareAvailableDurationMs;
        public HistogramBucket[] histogramAwareEnabledDurationMs;
        public HistogramBucket[] histogramNdpCreationTimeMs;
        public HistogramBucket[] histogramNdpSessionDataUsageMb;
        public HistogramBucket[] histogramNdpSessionDurationMs;
        public HistogramBucket[] histogramPublishSessionDurationMs;
        public NanStatusHistogramBucket[] histogramPublishStatus;
        public NanStatusHistogramBucket[] histogramRequestNdpOobStatus;
        public NanStatusHistogramBucket[] histogramRequestNdpStatus;
        public HistogramBucket[] histogramSubscribeGeofenceMax;
        public HistogramBucket[] histogramSubscribeGeofenceMin;
        public HistogramBucket[] histogramSubscribeSessionDurationMs;
        public NanStatusHistogramBucket[] histogramSubscribeStatus;
        public int maxConcurrentAttachSessionsInApp;
        public int maxConcurrentDiscoverySessionsInApp;
        public int maxConcurrentDiscoverySessionsInSystem;
        public int maxConcurrentNdiInApp;
        public int maxConcurrentNdiInSystem;
        public int maxConcurrentNdpInApp;
        public int maxConcurrentNdpInSystem;
        public int maxConcurrentNdpPerNdi;
        public int maxConcurrentPublishInApp;
        public int maxConcurrentPublishInSystem;
        public int maxConcurrentPublishWithRangingInApp;
        public int maxConcurrentPublishWithRangingInSystem;
        public int maxConcurrentSecureNdpInApp;
        public int maxConcurrentSecureNdpInSystem;
        public int maxConcurrentSubscribeInApp;
        public int maxConcurrentSubscribeInSystem;
        public int maxConcurrentSubscribeWithRangingInApp;
        public int maxConcurrentSubscribeWithRangingInSystem;
        public long ndpCreationTimeMsMax;
        public long ndpCreationTimeMsMin;
        public long ndpCreationTimeMsNumSamples;
        public long ndpCreationTimeMsSum;
        public long ndpCreationTimeMsSumOfSq;
        public int numApps;
        public int numAppsUsingIdentityCallback;
        public int numAppsWithDiscoverySessionFailureOutOfResources;
        public int numMatchesWithRanging;
        public int numMatchesWithoutRangingForRangingEnabledSubscribes;
        public int numSubscribesWithRanging;

        public WifiAwareLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiAwareLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiAwareLog[0];
                return _emptyArray;
            }
        }

        public static WifiAwareLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiAwareLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiAwareLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiAwareLog(), arrby);
        }

        public WifiAwareLog clear() {
            this.numApps = 0;
            this.numAppsUsingIdentityCallback = 0;
            this.maxConcurrentAttachSessionsInApp = 0;
            this.histogramAttachSessionStatus = NanStatusHistogramBucket.emptyArray();
            this.maxConcurrentPublishInApp = 0;
            this.maxConcurrentSubscribeInApp = 0;
            this.maxConcurrentDiscoverySessionsInApp = 0;
            this.maxConcurrentPublishInSystem = 0;
            this.maxConcurrentSubscribeInSystem = 0;
            this.maxConcurrentDiscoverySessionsInSystem = 0;
            this.histogramPublishStatus = NanStatusHistogramBucket.emptyArray();
            this.histogramSubscribeStatus = NanStatusHistogramBucket.emptyArray();
            this.numAppsWithDiscoverySessionFailureOutOfResources = 0;
            this.histogramRequestNdpStatus = NanStatusHistogramBucket.emptyArray();
            this.histogramRequestNdpOobStatus = NanStatusHistogramBucket.emptyArray();
            this.maxConcurrentNdiInApp = 0;
            this.maxConcurrentNdiInSystem = 0;
            this.maxConcurrentNdpInApp = 0;
            this.maxConcurrentNdpInSystem = 0;
            this.maxConcurrentSecureNdpInApp = 0;
            this.maxConcurrentSecureNdpInSystem = 0;
            this.maxConcurrentNdpPerNdi = 0;
            this.histogramAwareAvailableDurationMs = HistogramBucket.emptyArray();
            this.histogramAwareEnabledDurationMs = HistogramBucket.emptyArray();
            this.histogramAttachDurationMs = HistogramBucket.emptyArray();
            this.histogramPublishSessionDurationMs = HistogramBucket.emptyArray();
            this.histogramSubscribeSessionDurationMs = HistogramBucket.emptyArray();
            this.histogramNdpSessionDurationMs = HistogramBucket.emptyArray();
            this.histogramNdpSessionDataUsageMb = HistogramBucket.emptyArray();
            this.histogramNdpCreationTimeMs = HistogramBucket.emptyArray();
            this.ndpCreationTimeMsMin = 0L;
            this.ndpCreationTimeMsMax = 0L;
            this.ndpCreationTimeMsSum = 0L;
            this.ndpCreationTimeMsSumOfSq = 0L;
            this.ndpCreationTimeMsNumSamples = 0L;
            this.availableTimeMs = 0L;
            this.enabledTimeMs = 0L;
            this.maxConcurrentPublishWithRangingInApp = 0;
            this.maxConcurrentSubscribeWithRangingInApp = 0;
            this.maxConcurrentPublishWithRangingInSystem = 0;
            this.maxConcurrentSubscribeWithRangingInSystem = 0;
            this.histogramSubscribeGeofenceMin = HistogramBucket.emptyArray();
            this.histogramSubscribeGeofenceMax = HistogramBucket.emptyArray();
            this.numSubscribesWithRanging = 0;
            this.numMatchesWithRanging = 0;
            this.numMatchesWithoutRangingForRangingEnabledSubscribes = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numApps;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numAppsUsingIdentityCallback;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.maxConcurrentAttachSessionsInApp;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            Object object = this.histogramAttachSessionStatus;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramAttachSessionStatus;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n3 = this.maxConcurrentPublishInApp;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            n = this.maxConcurrentSubscribeInApp;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(6, n);
            }
            n2 = this.maxConcurrentDiscoverySessionsInApp;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(7, n2);
            }
            n2 = this.maxConcurrentPublishInSystem;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(8, n2);
            }
            n = this.maxConcurrentSubscribeInSystem;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(9, n);
            }
            n3 = this.maxConcurrentDiscoverySessionsInSystem;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(10, n3);
            }
            object = this.histogramPublishStatus;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramPublishStatus;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(11, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramSubscribeStatus;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.histogramSubscribeStatus;
                        n2 = n3;
                        if (n >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(12, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n3 = this.numAppsWithDiscoverySessionFailureOutOfResources;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(13, n3);
            }
            object = this.histogramRequestNdpStatus;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramRequestNdpStatus;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(14, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramRequestNdpOobStatus;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramRequestNdpOobStatus;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(15, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n2 = this.maxConcurrentNdiInApp;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(19, n2);
            }
            n2 = this.maxConcurrentNdiInSystem;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(20, n2);
            }
            n2 = this.maxConcurrentNdpInApp;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(21, n2);
            }
            n2 = this.maxConcurrentNdpInSystem;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(22, n2);
            }
            n2 = this.maxConcurrentSecureNdpInApp;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(23, n2);
            }
            n2 = this.maxConcurrentSecureNdpInSystem;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(24, n2);
            }
            n2 = this.maxConcurrentNdpPerNdi;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(25, n2);
            }
            object = this.histogramAwareAvailableDurationMs;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramAwareAvailableDurationMs;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(26, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            object = this.histogramAwareEnabledDurationMs;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramAwareEnabledDurationMs;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(27, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramAttachDurationMs;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramAttachDurationMs;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(28, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            object = this.histogramPublishSessionDurationMs;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramPublishSessionDurationMs;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(29, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramSubscribeSessionDurationMs;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.histogramSubscribeSessionDurationMs;
                        n2 = n3;
                        if (n >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(30, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.histogramNdpSessionDurationMs;
            n = n2;
            if (object != null) {
                n = n2;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.histogramNdpSessionDurationMs;
                        n = n2;
                        if (n3 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(31, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            object = this.histogramNdpSessionDataUsageMb;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramNdpSessionDataUsageMb;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(32, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramNdpCreationTimeMs;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramNdpCreationTimeMs;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(33, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            long l = this.ndpCreationTimeMsMin;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(34, l);
            }
            l = this.ndpCreationTimeMsMax;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(35, l);
            }
            l = this.ndpCreationTimeMsSum;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(36, l);
            }
            l = this.ndpCreationTimeMsSumOfSq;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(37, l);
            }
            l = this.ndpCreationTimeMsNumSamples;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(38, l);
            }
            l = this.availableTimeMs;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(39, l);
            }
            l = this.enabledTimeMs;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(40, l);
            }
            n2 = this.maxConcurrentPublishWithRangingInApp;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(41, n2);
            }
            n2 = this.maxConcurrentSubscribeWithRangingInApp;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(42, n2);
            }
            n = this.maxConcurrentPublishWithRangingInSystem;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(43, n);
            }
            n3 = this.maxConcurrentSubscribeWithRangingInSystem;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(44, n3);
            }
            object = this.histogramSubscribeGeofenceMin;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramSubscribeGeofenceMin;
                        n3 = n;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(45, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.histogramSubscribeGeofenceMax;
            n = n3;
            if (object != null) {
                n = n3;
                if (((NanStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramSubscribeGeofenceMax;
                        n = n3;
                        if (n2 >= ((NanStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(46, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n3 = this.numSubscribesWithRanging;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(47, n3);
            }
            n = this.numMatchesWithRanging;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(48, n);
            }
            n2 = this.numMatchesWithoutRangingForRangingEnabledSubscribes;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(49, n2);
            }
            return n;
        }

        @Override
        public WifiAwareLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block49 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block49;
                        return this;
                    }
                    case 392: {
                        this.numMatchesWithoutRangingForRangingEnabledSubscribes = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 384: {
                        this.numMatchesWithRanging = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 376: {
                        this.numSubscribesWithRanging = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 370: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 370);
                        MessageNano[] arrmessageNano = this.histogramSubscribeGeofenceMax;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramSubscribeGeofenceMax, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramSubscribeGeofenceMax = arrmessageNano;
                        continue block49;
                    }
                    case 362: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 362);
                        MessageNano[] arrmessageNano = this.histogramSubscribeGeofenceMin;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramSubscribeGeofenceMin, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramSubscribeGeofenceMin = arrmessageNano;
                        continue block49;
                    }
                    case 352: {
                        this.maxConcurrentSubscribeWithRangingInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 344: {
                        this.maxConcurrentPublishWithRangingInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 336: {
                        this.maxConcurrentSubscribeWithRangingInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 328: {
                        this.maxConcurrentPublishWithRangingInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 320: {
                        this.enabledTimeMs = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 312: {
                        this.availableTimeMs = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 304: {
                        this.ndpCreationTimeMsNumSamples = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 296: {
                        this.ndpCreationTimeMsSumOfSq = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 288: {
                        this.ndpCreationTimeMsSum = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 280: {
                        this.ndpCreationTimeMsMax = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 272: {
                        this.ndpCreationTimeMsMin = codedInputByteBufferNano.readInt64();
                        continue block49;
                    }
                    case 266: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 266);
                        MessageNano[] arrmessageNano = this.histogramNdpCreationTimeMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramNdpCreationTimeMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramNdpCreationTimeMs = arrmessageNano;
                        continue block49;
                    }
                    case 258: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 258);
                        MessageNano[] arrmessageNano = this.histogramNdpSessionDataUsageMb;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramNdpSessionDataUsageMb, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramNdpSessionDataUsageMb = arrmessageNano;
                        continue block49;
                    }
                    case 250: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 250);
                        MessageNano[] arrmessageNano = this.histogramNdpSessionDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramNdpSessionDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramNdpSessionDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 242: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 242);
                        MessageNano[] arrmessageNano = this.histogramSubscribeSessionDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramSubscribeSessionDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramSubscribeSessionDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 234: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 234);
                        MessageNano[] arrmessageNano = this.histogramPublishSessionDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramPublishSessionDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramPublishSessionDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 226: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 226);
                        MessageNano[] arrmessageNano = this.histogramAttachDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramAttachDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramAttachDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 218: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 218);
                        MessageNano[] arrmessageNano = this.histogramAwareEnabledDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramAwareEnabledDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramAwareEnabledDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 210: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 210);
                        MessageNano[] arrmessageNano = this.histogramAwareAvailableDurationMs;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new HistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramAwareAvailableDurationMs, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new HistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new HistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramAwareAvailableDurationMs = arrmessageNano;
                        continue block49;
                    }
                    case 200: {
                        this.maxConcurrentNdpPerNdi = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 192: {
                        this.maxConcurrentSecureNdpInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 184: {
                        this.maxConcurrentSecureNdpInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 176: {
                        this.maxConcurrentNdpInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 168: {
                        this.maxConcurrentNdpInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 160: {
                        this.maxConcurrentNdiInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 152: {
                        this.maxConcurrentNdiInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 122: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 122);
                        MessageNano[] arrmessageNano = this.histogramRequestNdpOobStatus;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NanStatusHistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramRequestNdpOobStatus, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NanStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NanStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramRequestNdpOobStatus = arrmessageNano;
                        continue block49;
                    }
                    case 114: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        MessageNano[] arrmessageNano = this.histogramRequestNdpStatus;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NanStatusHistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramRequestNdpStatus, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NanStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NanStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramRequestNdpStatus = arrmessageNano;
                        continue block49;
                    }
                    case 104: {
                        this.numAppsWithDiscoverySessionFailureOutOfResources = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 98: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 98);
                        MessageNano[] arrmessageNano = this.histogramSubscribeStatus;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NanStatusHistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramSubscribeStatus, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NanStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NanStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramSubscribeStatus = arrmessageNano;
                        continue block49;
                    }
                    case 90: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 90);
                        MessageNano[] arrmessageNano = this.histogramPublishStatus;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NanStatusHistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramPublishStatus, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NanStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NanStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramPublishStatus = arrmessageNano;
                        continue block49;
                    }
                    case 80: {
                        this.maxConcurrentDiscoverySessionsInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 72: {
                        this.maxConcurrentSubscribeInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 64: {
                        this.maxConcurrentPublishInSystem = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 56: {
                        this.maxConcurrentDiscoverySessionsInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 48: {
                        this.maxConcurrentSubscribeInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 40: {
                        this.maxConcurrentPublishInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 34: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                        MessageNano[] arrmessageNano = this.histogramAttachSessionStatus;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NanStatusHistogramBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.histogramAttachSessionStatus, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NanStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NanStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.histogramAttachSessionStatus = arrmessageNano;
                        continue block49;
                    }
                    case 24: {
                        this.maxConcurrentAttachSessionsInApp = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 16: {
                        this.numAppsUsingIdentityCallback = codedInputByteBufferNano.readInt32();
                        continue block49;
                    }
                    case 8: {
                        this.numApps = codedInputByteBufferNano.readInt32();
                        continue block49;
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
            long l;
            int n = this.numApps;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numAppsUsingIdentityCallback) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.maxConcurrentAttachSessionsInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((object = this.histogramAttachSessionStatus) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramAttachSessionStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if ((n = this.maxConcurrentPublishInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.maxConcurrentSubscribeInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.maxConcurrentDiscoverySessionsInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.maxConcurrentPublishInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.maxConcurrentSubscribeInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if ((n = this.maxConcurrentDiscoverySessionsInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(10, n);
            }
            if ((object = this.histogramPublishStatus) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramPublishStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(11, (MessageNano)object);
                }
            }
            if ((object = this.histogramSubscribeStatus) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramSubscribeStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(12, (MessageNano)object);
                }
            }
            if ((n = this.numAppsWithDiscoverySessionFailureOutOfResources) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            if ((object = this.histogramRequestNdpStatus) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramRequestNdpStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(14, (MessageNano)object);
                }
            }
            if ((object = this.histogramRequestNdpOobStatus) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramRequestNdpOobStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(15, (MessageNano)object);
                }
            }
            if ((n = this.maxConcurrentNdiInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(19, n);
            }
            if ((n = this.maxConcurrentNdiInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(20, n);
            }
            if ((n = this.maxConcurrentNdpInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(21, n);
            }
            if ((n = this.maxConcurrentNdpInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(22, n);
            }
            if ((n = this.maxConcurrentSecureNdpInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(23, n);
            }
            if ((n = this.maxConcurrentSecureNdpInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(24, n);
            }
            if ((n = this.maxConcurrentNdpPerNdi) != 0) {
                codedOutputByteBufferNano.writeInt32(25, n);
            }
            if ((object = this.histogramAwareAvailableDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramAwareAvailableDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(26, (MessageNano)object);
                }
            }
            if ((object = this.histogramAwareEnabledDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramAwareEnabledDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(27, (MessageNano)object);
                }
            }
            if ((object = this.histogramAttachDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramAttachDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(28, (MessageNano)object);
                }
            }
            if ((object = this.histogramPublishSessionDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramPublishSessionDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(29, (MessageNano)object);
                }
            }
            if ((object = this.histogramSubscribeSessionDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramSubscribeSessionDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(30, (MessageNano)object);
                }
            }
            if ((object = this.histogramNdpSessionDurationMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramNdpSessionDurationMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(31, (MessageNano)object);
                }
            }
            if ((object = this.histogramNdpSessionDataUsageMb) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramNdpSessionDataUsageMb)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(32, (MessageNano)object);
                }
            }
            if ((object = this.histogramNdpCreationTimeMs) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramNdpCreationTimeMs)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(33, (MessageNano)object);
                }
            }
            if ((l = this.ndpCreationTimeMsMin) != 0L) {
                codedOutputByteBufferNano.writeInt64(34, l);
            }
            if ((l = this.ndpCreationTimeMsMax) != 0L) {
                codedOutputByteBufferNano.writeInt64(35, l);
            }
            if ((l = this.ndpCreationTimeMsSum) != 0L) {
                codedOutputByteBufferNano.writeInt64(36, l);
            }
            if ((l = this.ndpCreationTimeMsSumOfSq) != 0L) {
                codedOutputByteBufferNano.writeInt64(37, l);
            }
            if ((l = this.ndpCreationTimeMsNumSamples) != 0L) {
                codedOutputByteBufferNano.writeInt64(38, l);
            }
            if ((l = this.availableTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(39, l);
            }
            if ((l = this.enabledTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(40, l);
            }
            if ((n = this.maxConcurrentPublishWithRangingInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(41, n);
            }
            if ((n = this.maxConcurrentSubscribeWithRangingInApp) != 0) {
                codedOutputByteBufferNano.writeInt32(42, n);
            }
            if ((n = this.maxConcurrentPublishWithRangingInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(43, n);
            }
            if ((n = this.maxConcurrentSubscribeWithRangingInSystem) != 0) {
                codedOutputByteBufferNano.writeInt32(44, n);
            }
            if ((object = this.histogramSubscribeGeofenceMin) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramSubscribeGeofenceMin)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(45, (MessageNano)object);
                }
            }
            if ((object = this.histogramSubscribeGeofenceMax) != null && ((NanStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((NanStatusHistogramBucket[])(object = this.histogramSubscribeGeofenceMax)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(46, (MessageNano)object);
                }
            }
            if ((n = this.numSubscribesWithRanging) != 0) {
                codedOutputByteBufferNano.writeInt32(47, n);
            }
            if ((n = this.numMatchesWithRanging) != 0) {
                codedOutputByteBufferNano.writeInt32(48, n);
            }
            if ((n = this.numMatchesWithoutRangingForRangingEnabledSubscribes) != 0) {
                codedOutputByteBufferNano.writeInt32(49, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class HistogramBucket
        extends MessageNano {
            private static volatile HistogramBucket[] _emptyArray;
            public int count;
            public long end;
            public long start;

            public HistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static HistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new HistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static HistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new HistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static HistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new HistogramBucket(), arrby);
            }

            public HistogramBucket clear() {
                this.start = 0L;
                this.end = 0L;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                long l = this.start;
                int n2 = n;
                if (l != 0L) {
                    n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
                }
                l = this.end;
                n = n2;
                if (l != 0L) {
                    n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
                }
                int n3 = this.count;
                n2 = n;
                if (n3 != 0) {
                    n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
                }
                return n2;
            }

            @Override
            public HistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 24) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.count = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.end = codedInputByteBufferNano.readInt64();
                        continue;
                    }
                    this.start = codedInputByteBufferNano.readInt64();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n;
                long l = this.start;
                if (l != 0L) {
                    codedOutputByteBufferNano.writeInt64(1, l);
                }
                if ((l = this.end) != 0L) {
                    codedOutputByteBufferNano.writeInt64(2, l);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(3, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class NanStatusHistogramBucket
        extends MessageNano {
            private static volatile NanStatusHistogramBucket[] _emptyArray;
            public int count;
            public int nanStatusType;

            public NanStatusHistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static NanStatusHistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new NanStatusHistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static NanStatusHistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new NanStatusHistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static NanStatusHistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new NanStatusHistogramBucket(), arrby);
            }

            public NanStatusHistogramBucket clear() {
                this.nanStatusType = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.nanStatusType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public NanStatusHistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    switch (n) {
                        default: {
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
                        case 11: 
                        case 12: 
                        case 13: 
                        case 14: 
                    }
                    this.nanStatusType = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.nanStatusType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiConfigStoreIO
    extends MessageNano {
        private static volatile WifiConfigStoreIO[] _emptyArray;
        public DurationBucket[] readDurations;
        public DurationBucket[] writeDurations;

        public WifiConfigStoreIO() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiConfigStoreIO[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiConfigStoreIO[0];
                return _emptyArray;
            }
        }

        public static WifiConfigStoreIO parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiConfigStoreIO().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiConfigStoreIO parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiConfigStoreIO(), arrby);
        }

        public WifiConfigStoreIO clear() {
            this.readDurations = DurationBucket.emptyArray();
            this.writeDurations = DurationBucket.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.readDurations;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((DurationBucket[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.readDurations;
                        n3 = n2;
                        if (n >= ((DurationBucket[])object).length) break;
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
            object = this.writeDurations;
            n = n3;
            if (object != null) {
                n = n3;
                if (((DurationBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.writeDurations;
                        n = n3;
                        if (n2 >= ((DurationBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            return n;
        }

        @Override
        public WifiConfigStoreIO mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                DurationBucket[] arrdurationBucket;
                int n2;
                if (n != 10) {
                    if (n != 18) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    arrdurationBucket = this.writeDurations;
                    n = arrdurationBucket == null ? 0 : arrdurationBucket.length;
                    arrdurationBucket = new DurationBucket[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.writeDurations, 0, arrdurationBucket, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrdurationBucket.length - 1) {
                        arrdurationBucket[n2] = new DurationBucket();
                        codedInputByteBufferNano.readMessage(arrdurationBucket[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrdurationBucket[n2] = new DurationBucket();
                    codedInputByteBufferNano.readMessage(arrdurationBucket[n2]);
                    this.writeDurations = arrdurationBucket;
                    continue;
                }
                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                arrdurationBucket = this.readDurations;
                n = arrdurationBucket == null ? 0 : arrdurationBucket.length;
                arrdurationBucket = new DurationBucket[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.readDurations, 0, arrdurationBucket, 0, n);
                    n2 = n;
                }
                while (n2 < arrdurationBucket.length - 1) {
                    arrdurationBucket[n2] = new DurationBucket();
                    codedInputByteBufferNano.readMessage(arrdurationBucket[n2]);
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arrdurationBucket[n2] = new DurationBucket();
                codedInputByteBufferNano.readMessage(arrdurationBucket[n2]);
                this.readDurations = arrdurationBucket;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.readDurations;
            if (object != null && ((DurationBucket[])object).length > 0) {
                for (n = 0; n < ((DurationBucket[])(object = this.readDurations)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((object = this.writeDurations) != null && ((DurationBucket[])object).length > 0) {
                for (n = 0; n < ((DurationBucket[])(object = this.writeDurations)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class DurationBucket
        extends MessageNano {
            private static volatile DurationBucket[] _emptyArray;
            public int count;
            public int rangeEndMs;
            public int rangeStartMs;

            public DurationBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static DurationBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new DurationBucket[0];
                    return _emptyArray;
                }
            }

            public static DurationBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new DurationBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static DurationBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new DurationBucket(), arrby);
            }

            public DurationBucket clear() {
                this.rangeStartMs = 0;
                this.rangeEndMs = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.rangeStartMs;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.rangeEndMs;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                n2 = this.count;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                }
                return n3;
            }

            @Override
            public DurationBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 24) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.count = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.rangeEndMs = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.rangeStartMs = codedInputByteBufferNano.readInt32();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.rangeStartMs;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.rangeEndMs) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(3, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiDppLog
    extends MessageNano {
        public static final int EASY_CONNECT_EVENT_FAILURE_AUTHENTICATION = 2;
        public static final int EASY_CONNECT_EVENT_FAILURE_BUSY = 5;
        public static final int EASY_CONNECT_EVENT_FAILURE_CONFIGURATION = 4;
        public static final int EASY_CONNECT_EVENT_FAILURE_GENERIC = 7;
        public static final int EASY_CONNECT_EVENT_FAILURE_INVALID_NETWORK = 9;
        public static final int EASY_CONNECT_EVENT_FAILURE_INVALID_URI = 1;
        public static final int EASY_CONNECT_EVENT_FAILURE_NOT_COMPATIBLE = 3;
        public static final int EASY_CONNECT_EVENT_FAILURE_NOT_SUPPORTED = 8;
        public static final int EASY_CONNECT_EVENT_FAILURE_TIMEOUT = 6;
        public static final int EASY_CONNECT_EVENT_FAILURE_UNKNOWN = 0;
        public static final int EASY_CONNECT_EVENT_SUCCESS_CONFIGURATION_SENT = 1;
        public static final int EASY_CONNECT_EVENT_SUCCESS_UNKNOWN = 0;
        private static volatile WifiDppLog[] _emptyArray;
        public DppConfiguratorSuccessStatusHistogramBucket[] dppConfiguratorSuccessCode;
        public DppFailureStatusHistogramBucket[] dppFailureCode;
        public HistogramBucketInt32[] dppOperationTime;
        public int numDppConfiguratorInitiatorRequests;
        public int numDppEnrolleeInitiatorRequests;
        public int numDppEnrolleeSuccess;

        public WifiDppLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiDppLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiDppLog[0];
                return _emptyArray;
            }
        }

        public static WifiDppLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiDppLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiDppLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiDppLog(), arrby);
        }

        public WifiDppLog clear() {
            this.numDppConfiguratorInitiatorRequests = 0;
            this.numDppEnrolleeInitiatorRequests = 0;
            this.numDppEnrolleeSuccess = 0;
            this.dppConfiguratorSuccessCode = DppConfiguratorSuccessStatusHistogramBucket.emptyArray();
            this.dppFailureCode = DppFailureStatusHistogramBucket.emptyArray();
            this.dppOperationTime = HistogramBucketInt32.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numDppConfiguratorInitiatorRequests;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numDppEnrolleeInitiatorRequests;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.numDppEnrolleeSuccess;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            Object object = this.dppConfiguratorSuccessCode;
            n = n3;
            if (object != null) {
                n = n3;
                if (((DppConfiguratorSuccessStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.dppConfiguratorSuccessCode;
                        n = n3;
                        if (n2 >= ((MessageNano[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            object = this.dppFailureCode;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((MessageNano[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.dppFailureCode;
                        n3 = n;
                        if (n2 >= ((MessageNano[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.dppOperationTime;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((MessageNano[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.dppOperationTime;
                        n2 = n3;
                        if (n >= ((MessageNano[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            return n2;
        }

        @Override
        public WifiDppLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            MessageNano[] arrmessageNano;
                            int n2;
                            if (n != 34) {
                                if (n != 42) {
                                    if (n != 58) {
                                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                        return this;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                                    arrmessageNano = this.dppOperationTime;
                                    n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                    arrmessageNano = new HistogramBucketInt32[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.dppOperationTime, 0, arrmessageNano, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrmessageNano.length - 1) {
                                        arrmessageNano[n2] = new HistogramBucketInt32();
                                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrmessageNano[n2] = new HistogramBucketInt32();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    this.dppOperationTime = arrmessageNano;
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                arrmessageNano = this.dppFailureCode;
                                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                arrmessageNano = new DppFailureStatusHistogramBucket[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.dppFailureCode, 0, arrmessageNano, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrmessageNano.length - 1) {
                                    arrmessageNano[n2] = new DppFailureStatusHistogramBucket();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrmessageNano[n2] = new DppFailureStatusHistogramBucket();
                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                this.dppFailureCode = arrmessageNano;
                                continue;
                            }
                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            arrmessageNano = this.dppConfiguratorSuccessCode;
                            n = arrmessageNano == null ? 0 : arrmessageNano.length;
                            arrmessageNano = new DppConfiguratorSuccessStatusHistogramBucket[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.dppConfiguratorSuccessCode, 0, arrmessageNano, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrmessageNano.length - 1) {
                                arrmessageNano[n2] = new DppConfiguratorSuccessStatusHistogramBucket();
                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrmessageNano[n2] = new DppConfiguratorSuccessStatusHistogramBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            this.dppConfiguratorSuccessCode = arrmessageNano;
                            continue;
                        }
                        this.numDppEnrolleeSuccess = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numDppEnrolleeInitiatorRequests = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numDppConfiguratorInitiatorRequests = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numDppConfiguratorInitiatorRequests;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numDppEnrolleeInitiatorRequests) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numDppEnrolleeSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((object = this.dppConfiguratorSuccessCode) != null && ((DppConfiguratorSuccessStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((MessageNano[])(object = this.dppConfiguratorSuccessCode)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if ((object = this.dppFailureCode) != null && ((MessageNano[])object).length > 0) {
                for (n = 0; n < ((MessageNano[])(object = this.dppFailureCode)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                }
            }
            if ((object = this.dppOperationTime) != null && ((MessageNano[])object).length > 0) {
                for (n = 0; n < ((MessageNano[])(object = this.dppOperationTime)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class DppConfiguratorSuccessStatusHistogramBucket
        extends MessageNano {
            private static volatile DppConfiguratorSuccessStatusHistogramBucket[] _emptyArray;
            public int count;
            public int dppStatusType;

            public DppConfiguratorSuccessStatusHistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static DppConfiguratorSuccessStatusHistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new DppConfiguratorSuccessStatusHistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static DppConfiguratorSuccessStatusHistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new DppConfiguratorSuccessStatusHistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static DppConfiguratorSuccessStatusHistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new DppConfiguratorSuccessStatusHistogramBucket(), arrby);
            }

            public DppConfiguratorSuccessStatusHistogramBucket clear() {
                this.dppStatusType = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.dppStatusType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public DppConfiguratorSuccessStatusHistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1) continue;
                    this.dppStatusType = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.dppStatusType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class DppFailureStatusHistogramBucket
        extends MessageNano {
            private static volatile DppFailureStatusHistogramBucket[] _emptyArray;
            public int count;
            public int dppStatusType;

            public DppFailureStatusHistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static DppFailureStatusHistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new DppFailureStatusHistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static DppFailureStatusHistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new DppFailureStatusHistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static DppFailureStatusHistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new DppFailureStatusHistogramBucket(), arrby);
            }

            public DppFailureStatusHistogramBucket clear() {
                this.dppStatusType = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.dppStatusType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public DppFailureStatusHistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    switch (n) {
                        default: {
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
                    this.dppStatusType = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.dppStatusType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiIsUnusableEvent
    extends MessageNano {
        public static final int TYPE_DATA_STALL_BAD_TX = 1;
        public static final int TYPE_DATA_STALL_BOTH = 3;
        public static final int TYPE_DATA_STALL_TX_WITHOUT_RX = 2;
        public static final int TYPE_FIRMWARE_ALERT = 4;
        public static final int TYPE_IP_REACHABILITY_LOST = 5;
        public static final int TYPE_UNKNOWN = 0;
        private static volatile WifiIsUnusableEvent[] _emptyArray;
        public int firmwareAlertCode;
        public long lastLinkLayerStatsUpdateTime;
        public int lastPredictionHorizonSec;
        public int lastScore;
        public int lastWifiUsabilityScore;
        public long packetUpdateTimeDelta;
        public long rxSuccessDelta;
        public boolean screenOn;
        public long startTimeMillis;
        public long txBadDelta;
        public long txRetriesDelta;
        public long txSuccessDelta;
        public int type;

        public WifiIsUnusableEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiIsUnusableEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiIsUnusableEvent[0];
                return _emptyArray;
            }
        }

        public static WifiIsUnusableEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiIsUnusableEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiIsUnusableEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiIsUnusableEvent(), arrby);
        }

        public WifiIsUnusableEvent clear() {
            this.type = 0;
            this.startTimeMillis = 0L;
            this.lastScore = -1;
            this.txSuccessDelta = 0L;
            this.txRetriesDelta = 0L;
            this.txBadDelta = 0L;
            this.rxSuccessDelta = 0L;
            this.packetUpdateTimeDelta = 0L;
            this.lastLinkLayerStatsUpdateTime = 0L;
            this.firmwareAlertCode = -1;
            this.lastWifiUsabilityScore = -1;
            this.lastPredictionHorizonSec = -1;
            this.screenOn = false;
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
            long l = this.startTimeMillis;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            n2 = this.lastScore;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            l = this.txSuccessDelta;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.txRetriesDelta;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.txBadDelta;
            n3 = n2;
            if (l != 0L) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.rxSuccessDelta;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.packetUpdateTimeDelta;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            l = this.lastLinkLayerStatsUpdateTime;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(9, l);
            }
            n2 = this.firmwareAlertCode;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(10, n2);
            }
            n2 = this.lastWifiUsabilityScore;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(11, n2);
            }
            n2 = this.lastPredictionHorizonSec;
            n3 = n;
            if (n2 != -1) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(12, n2);
            }
            boolean bl = this.screenOn;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(13, bl);
            }
            return n;
        }

        @Override
        public WifiIsUnusableEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 104: {
                        this.screenOn = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 96: {
                        this.lastPredictionHorizonSec = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 88: {
                        this.lastWifiUsabilityScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 80: {
                        this.firmwareAlertCode = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 72: {
                        this.lastLinkLayerStatsUpdateTime = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 64: {
                        this.packetUpdateTimeDelta = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 56: {
                        this.rxSuccessDelta = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 48: {
                        this.txBadDelta = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 40: {
                        this.txRetriesDelta = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 32: {
                        this.txSuccessDelta = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 24: {
                        this.lastScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 16: {
                        this.startTimeMillis = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 8: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5) continue block16;
                        this.type = n;
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
            long l;
            boolean bl;
            int n = this.type;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((l = this.startTimeMillis) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((n = this.lastScore) != -1) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((l = this.txSuccessDelta) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.txRetriesDelta) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.txBadDelta) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.rxSuccessDelta) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.packetUpdateTimeDelta) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((l = this.lastLinkLayerStatsUpdateTime) != 0L) {
                codedOutputByteBufferNano.writeInt64(9, l);
            }
            if ((n = this.firmwareAlertCode) != -1) {
                codedOutputByteBufferNano.writeInt32(10, n);
            }
            if ((n = this.lastWifiUsabilityScore) != -1) {
                codedOutputByteBufferNano.writeInt32(11, n);
            }
            if ((n = this.lastPredictionHorizonSec) != -1) {
                codedOutputByteBufferNano.writeInt32(12, n);
            }
            if (bl = this.screenOn) {
                codedOutputByteBufferNano.writeBool(13, bl);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiLinkLayerUsageStats
    extends MessageNano {
        private static volatile WifiLinkLayerUsageStats[] _emptyArray;
        public long loggingDurationMs;
        public long radioBackgroundScanTimeMs;
        public long radioHs20ScanTimeMs;
        public long radioNanScanTimeMs;
        public long radioOnTimeMs;
        public long radioPnoScanTimeMs;
        public long radioRoamScanTimeMs;
        public long radioRxTimeMs;
        public long radioScanTimeMs;
        public long radioTxTimeMs;

        public WifiLinkLayerUsageStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiLinkLayerUsageStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiLinkLayerUsageStats[0];
                return _emptyArray;
            }
        }

        public static WifiLinkLayerUsageStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiLinkLayerUsageStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiLinkLayerUsageStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiLinkLayerUsageStats(), arrby);
        }

        public WifiLinkLayerUsageStats clear() {
            this.loggingDurationMs = 0L;
            this.radioOnTimeMs = 0L;
            this.radioTxTimeMs = 0L;
            this.radioRxTimeMs = 0L;
            this.radioScanTimeMs = 0L;
            this.radioNanScanTimeMs = 0L;
            this.radioBackgroundScanTimeMs = 0L;
            this.radioRoamScanTimeMs = 0L;
            this.radioPnoScanTimeMs = 0L;
            this.radioHs20ScanTimeMs = 0L;
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
            l = this.radioOnTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            l = this.radioTxTimeMs;
            int n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.radioRxTimeMs;
            n2 = n3;
            if (l != 0L) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.radioScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.radioNanScanTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.radioBackgroundScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.radioRoamScanTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            l = this.radioPnoScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(9, l);
            }
            l = this.radioHs20ScanTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            return n2;
        }

        @Override
        public WifiLinkLayerUsageStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block13 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block13;
                        return this;
                    }
                    case 80: {
                        this.radioHs20ScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 72: {
                        this.radioPnoScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 64: {
                        this.radioRoamScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 56: {
                        this.radioBackgroundScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 48: {
                        this.radioNanScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 40: {
                        this.radioScanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 32: {
                        this.radioRxTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 24: {
                        this.radioTxTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 16: {
                        this.radioOnTimeMs = codedInputByteBufferNano.readInt64();
                        continue block13;
                    }
                    case 8: {
                        this.loggingDurationMs = codedInputByteBufferNano.readInt64();
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
            long l = this.loggingDurationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.radioOnTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((l = this.radioTxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.radioRxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.radioScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.radioNanScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.radioBackgroundScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.radioRoamScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((l = this.radioPnoScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(9, l);
            }
            if ((l = this.radioHs20ScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiLockStats
    extends MessageNano {
        private static volatile WifiLockStats[] _emptyArray;
        public HistogramBucketInt32[] highPerfActiveSessionDurationSecHistogram;
        public long highPerfActiveTimeMs;
        public HistogramBucketInt32[] highPerfLockAcqDurationSecHistogram;
        public HistogramBucketInt32[] lowLatencyActiveSessionDurationSecHistogram;
        public long lowLatencyActiveTimeMs;
        public HistogramBucketInt32[] lowLatencyLockAcqDurationSecHistogram;

        public WifiLockStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiLockStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiLockStats[0];
                return _emptyArray;
            }
        }

        public static WifiLockStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiLockStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiLockStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiLockStats(), arrby);
        }

        public WifiLockStats clear() {
            this.highPerfActiveTimeMs = 0L;
            this.lowLatencyActiveTimeMs = 0L;
            this.highPerfLockAcqDurationSecHistogram = HistogramBucketInt32.emptyArray();
            this.lowLatencyLockAcqDurationSecHistogram = HistogramBucketInt32.emptyArray();
            this.highPerfActiveSessionDurationSecHistogram = HistogramBucketInt32.emptyArray();
            this.lowLatencyActiveSessionDurationSecHistogram = HistogramBucketInt32.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            long l = this.highPerfActiveTimeMs;
            int n3 = n2;
            if (l != 0L) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            l = this.lowLatencyActiveTimeMs;
            n2 = n3;
            if (l != 0L) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            Object object = this.highPerfLockAcqDurationSecHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.highPerfLockAcqDurationSecHistogram;
                        n3 = n2;
                        if (n >= ((HistogramBucketInt32[])object).length) break;
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
            object = this.lowLatencyLockAcqDurationSecHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.lowLatencyLockAcqDurationSecHistogram;
                        n2 = n3;
                        if (n >= ((HistogramBucketInt32[])object).length) break;
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
            object = this.highPerfActiveSessionDurationSecHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.highPerfActiveSessionDurationSecHistogram;
                        n3 = n2;
                        if (n >= ((HistogramBucketInt32[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.lowLatencyActiveSessionDurationSecHistogram;
            n = n3;
            if (object != null) {
                n = n3;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.lowLatencyActiveSessionDurationSecHistogram;
                        n = n3;
                        if (n2 >= ((HistogramBucketInt32[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            return n;
        }

        @Override
        public WifiLockStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        HistogramBucketInt32[] arrhistogramBucketInt32;
                        int n2;
                        if (n != 26) {
                            if (n != 34) {
                                if (n != 42) {
                                    if (n != 50) {
                                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                        return this;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                                    arrhistogramBucketInt32 = this.lowLatencyActiveSessionDurationSecHistogram;
                                    n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                                    arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.lowLatencyActiveSessionDurationSecHistogram, 0, arrhistogramBucketInt32, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrhistogramBucketInt32.length - 1) {
                                        arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                        codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                    codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                    this.lowLatencyActiveSessionDurationSecHistogram = arrhistogramBucketInt32;
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                arrhistogramBucketInt32 = this.highPerfActiveSessionDurationSecHistogram;
                                n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                                arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.highPerfActiveSessionDurationSecHistogram, 0, arrhistogramBucketInt32, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrhistogramBucketInt32.length - 1) {
                                    arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                    codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                this.highPerfActiveSessionDurationSecHistogram = arrhistogramBucketInt32;
                                continue;
                            }
                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            arrhistogramBucketInt32 = this.lowLatencyLockAcqDurationSecHistogram;
                            n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                            arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.lowLatencyLockAcqDurationSecHistogram, 0, arrhistogramBucketInt32, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrhistogramBucketInt32.length - 1) {
                                arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                            codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                            this.lowLatencyLockAcqDurationSecHistogram = arrhistogramBucketInt32;
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 26);
                        arrhistogramBucketInt32 = this.highPerfLockAcqDurationSecHistogram;
                        n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                        arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.highPerfLockAcqDurationSecHistogram, 0, arrhistogramBucketInt32, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrhistogramBucketInt32.length - 1) {
                            arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                            codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                        codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                        this.highPerfLockAcqDurationSecHistogram = arrhistogramBucketInt32;
                        continue;
                    }
                    this.lowLatencyActiveTimeMs = codedInputByteBufferNano.readInt64();
                    continue;
                }
                this.highPerfActiveTimeMs = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object;
            long l = this.highPerfActiveTimeMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.lowLatencyActiveTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((object = this.highPerfLockAcqDurationSecHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.highPerfLockAcqDurationSecHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
                }
            }
            if ((object = this.lowLatencyLockAcqDurationSecHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.lowLatencyLockAcqDurationSecHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if ((object = this.highPerfActiveSessionDurationSecHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.highPerfActiveSessionDurationSecHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                }
            }
            if ((object = this.lowLatencyActiveSessionDurationSecHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.lowLatencyActiveSessionDurationSecHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiLog
    extends MessageNano {
        public static final int FAILURE_WIFI_DISABLED = 4;
        public static final int SCAN_FAILURE_INTERRUPTED = 2;
        public static final int SCAN_FAILURE_INVALID_CONFIGURATION = 3;
        public static final int SCAN_SUCCESS = 1;
        public static final int SCAN_UNKNOWN = 0;
        public static final int WIFI_ASSOCIATED = 3;
        public static final int WIFI_DISABLED = 1;
        public static final int WIFI_DISCONNECTED = 2;
        public static final int WIFI_UNKNOWN = 0;
        private static volatile WifiLog[] _emptyArray;
        public AlertReasonCount[] alertReasonCount;
        public NumConnectableNetworksBucket[] availableOpenBssidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableOpenOrSavedBssidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableOpenOrSavedSsidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableOpenSsidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableSavedBssidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableSavedPasspointProviderBssidsInScanHistogram;
        public NumConnectableNetworksBucket[] availableSavedPasspointProviderProfilesInScanHistogram;
        public NumConnectableNetworksBucket[] availableSavedSsidsInScanHistogram;
        public WifiSystemStateEntry[] backgroundScanRequestState;
        public ScanReturnEntry[] backgroundScanReturnEntries;
        public ConnectToNetworkNotificationAndActionCount[] connectToNetworkNotificationActionCount;
        public ConnectToNetworkNotificationAndActionCount[] connectToNetworkNotificationCount;
        public ConnectionEvent[] connectionEvent;
        public ExperimentValues experimentValues;
        public int fullBandAllSingleScanListenerResults;
        public String hardwareRevision;
        public PasspointProfileTypeCount[] installedPasspointProfileTypeForR1;
        public PasspointProfileTypeCount[] installedPasspointProfileTypeForR2;
        public boolean isLocationEnabled;
        public boolean isMacRandomizationOn;
        public boolean isScanningAlwaysEnabled;
        public boolean isWifiNetworksAvailableNotificationOn;
        public LinkProbeStats linkProbeStats;
        public LinkSpeedCount[] linkSpeedCounts;
        public DeviceMobilityStatePnoScanStats[] mobilityStatePnoStatsList;
        public NetworkSelectionExperimentDecisions[] networkSelectionExperimentDecisionsList;
        public int numAddOrUpdateNetworkCalls;
        public int numBackgroundScans;
        public int numClientInterfaceDown;
        public int numConnectivityOneshotScans;
        public int numConnectivityWatchdogBackgroundBad;
        public int numConnectivityWatchdogBackgroundGood;
        public int numConnectivityWatchdogPnoBad;
        public int numConnectivityWatchdogPnoGood;
        public int numEmptyScanResults;
        public int numEnableNetworkCalls;
        public int numEnhancedOpenNetworkScanResults;
        public int numEnhancedOpenNetworks;
        public int numExternalAppOneshotScanRequests;
        public int numExternalBackgroundAppOneshotScanRequestsThrottled;
        public int numExternalForegroundAppOneshotScanRequestsThrottled;
        public int numHalCrashes;
        public int numHiddenNetworkScanResults;
        public int numHiddenNetworks;
        public int numHostapdCrashes;
        public int numHotspot2R1NetworkScanResults;
        public int numHotspot2R2NetworkScanResults;
        public int numLastResortWatchdogAvailableNetworksTotal;
        public int numLastResortWatchdogBadAssociationNetworksTotal;
        public int numLastResortWatchdogBadAuthenticationNetworksTotal;
        public int numLastResortWatchdogBadDhcpNetworksTotal;
        public int numLastResortWatchdogBadOtherNetworksTotal;
        public int numLastResortWatchdogSuccesses;
        public int numLastResortWatchdogTriggers;
        public int numLastResortWatchdogTriggersWithBadAssociation;
        public int numLastResortWatchdogTriggersWithBadAuthentication;
        public int numLastResortWatchdogTriggersWithBadDhcp;
        public int numLastResortWatchdogTriggersWithBadOther;
        public int numLegacyEnterpriseNetworkScanResults;
        public int numLegacyEnterpriseNetworks;
        public int numLegacyPersonalNetworkScanResults;
        public int numLegacyPersonalNetworks;
        public int numNetworksAddedByApps;
        public int numNetworksAddedByUser;
        public int numNonEmptyScanResults;
        public int numOneshotHasDfsChannelScans;
        public int numOneshotScans;
        public int numOpenNetworkConnectMessageFailedToSend;
        public int numOpenNetworkRecommendationUpdates;
        public int numOpenNetworkScanResults;
        public int numOpenNetworks;
        public int numPasspointNetworks;
        public int numPasspointProviderInstallSuccess;
        public int numPasspointProviderInstallation;
        public int numPasspointProviderUninstallSuccess;
        public int numPasspointProviderUninstallation;
        public int numPasspointProviders;
        public int numPasspointProvidersSuccessfullyConnected;
        public int numRadioModeChangeToDbs;
        public int numRadioModeChangeToMcc;
        public int numRadioModeChangeToSbs;
        public int numRadioModeChangeToScc;
        public int numSarSensorRegistrationFailures;
        public int numSavedNetworks;
        public int numSavedNetworksWithMacRandomization;
        public int numScans;
        public int numSetupClientInterfaceFailureDueToHal;
        public int numSetupClientInterfaceFailureDueToSupplicant;
        public int numSetupClientInterfaceFailureDueToWificond;
        public int numSetupSoftApInterfaceFailureDueToHal;
        public int numSetupSoftApInterfaceFailureDueToHostapd;
        public int numSetupSoftApInterfaceFailureDueToWificond;
        public int numSoftApInterfaceDown;
        public int numSoftApUserBandPreferenceUnsatisfied;
        public int numSupplicantCrashes;
        public int numTotalScanResults;
        public int numWifiToggledViaAirplane;
        public int numWifiToggledViaSettings;
        public int numWificondCrashes;
        public int numWpa3EnterpriseNetworkScanResults;
        public int numWpa3EnterpriseNetworks;
        public int numWpa3PersonalNetworkScanResults;
        public int numWpa3PersonalNetworks;
        public NumConnectableNetworksBucket[] observed80211McSupportingApsInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR1ApsInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR1ApsPerEssInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR1EssInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR2ApsInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR2ApsPerEssInScanHistogram;
        public NumConnectableNetworksBucket[] observedHotspotR2EssInScanHistogram;
        public int openNetworkRecommenderBlacklistSize;
        public int partialAllSingleScanListenerResults;
        public PasspointProvisionStats passpointProvisionStats;
        public PnoScanMetrics pnoScanMetrics;
        public int recordDurationSec;
        public RssiPollCount[] rssiPollDeltaCount;
        public RssiPollCount[] rssiPollRssiCount;
        public ScanReturnEntry[] scanReturnEntries;
        public String scoreExperimentId;
        public SoftApConnectedClientsEvent[] softApConnectedClientsEventsLocalOnly;
        public SoftApConnectedClientsEvent[] softApConnectedClientsEventsTethered;
        public SoftApDurationBucket[] softApDuration;
        public SoftApReturnCodeCount[] softApReturnCode;
        public StaEvent[] staEventList;
        public NumConnectableNetworksBucket[] totalBssidsInScanHistogram;
        public NumConnectableNetworksBucket[] totalSsidsInScanHistogram;
        public long watchdogTotalConnectionFailureCountAfterTrigger;
        public long watchdogTriggerToConnectionSuccessDurationMs;
        public WifiAwareLog wifiAwareLog;
        public WifiConfigStoreIO wifiConfigStoreIo;
        public WifiDppLog wifiDppLog;
        public WifiIsUnusableEvent[] wifiIsUnusableEventList;
        public WifiLinkLayerUsageStats wifiLinkLayerUsageStats;
        public WifiLockStats wifiLockStats;
        public WifiNetworkRequestApiLog wifiNetworkRequestApiLog;
        public WifiNetworkSuggestionApiLog wifiNetworkSuggestionApiLog;
        public WifiP2pStats wifiP2PStats;
        public WifiPowerStats wifiPowerStats;
        public WifiRadioUsage wifiRadioUsage;
        public WifiRttLog wifiRttLog;
        public WifiScoreCount[] wifiScoreCount;
        public WifiSystemStateEntry[] wifiSystemStateEntries;
        public WifiToggleStats wifiToggleStats;
        public WifiUsabilityScoreCount[] wifiUsabilityScoreCount;
        public WifiUsabilityStats[] wifiUsabilityStatsList;
        public WifiWakeStats wifiWakeStats;
        public WpsMetrics wpsMetrics;

        public WifiLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiLog[0];
                return _emptyArray;
            }
        }

        public static WifiLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiLog(), arrby);
        }

        public WifiLog clear() {
            this.connectionEvent = ConnectionEvent.emptyArray();
            this.numSavedNetworks = 0;
            this.numOpenNetworks = 0;
            this.numLegacyPersonalNetworks = 0;
            this.numLegacyEnterpriseNetworks = 0;
            this.isLocationEnabled = false;
            this.isScanningAlwaysEnabled = false;
            this.numWifiToggledViaSettings = 0;
            this.numWifiToggledViaAirplane = 0;
            this.numNetworksAddedByUser = 0;
            this.numNetworksAddedByApps = 0;
            this.numEmptyScanResults = 0;
            this.numNonEmptyScanResults = 0;
            this.numOneshotScans = 0;
            this.numBackgroundScans = 0;
            this.scanReturnEntries = ScanReturnEntry.emptyArray();
            this.wifiSystemStateEntries = WifiSystemStateEntry.emptyArray();
            this.backgroundScanReturnEntries = ScanReturnEntry.emptyArray();
            this.backgroundScanRequestState = WifiSystemStateEntry.emptyArray();
            this.numLastResortWatchdogTriggers = 0;
            this.numLastResortWatchdogBadAssociationNetworksTotal = 0;
            this.numLastResortWatchdogBadAuthenticationNetworksTotal = 0;
            this.numLastResortWatchdogBadDhcpNetworksTotal = 0;
            this.numLastResortWatchdogBadOtherNetworksTotal = 0;
            this.numLastResortWatchdogAvailableNetworksTotal = 0;
            this.numLastResortWatchdogTriggersWithBadAssociation = 0;
            this.numLastResortWatchdogTriggersWithBadAuthentication = 0;
            this.numLastResortWatchdogTriggersWithBadDhcp = 0;
            this.numLastResortWatchdogTriggersWithBadOther = 0;
            this.numConnectivityWatchdogPnoGood = 0;
            this.numConnectivityWatchdogPnoBad = 0;
            this.numConnectivityWatchdogBackgroundGood = 0;
            this.numConnectivityWatchdogBackgroundBad = 0;
            this.recordDurationSec = 0;
            this.rssiPollRssiCount = RssiPollCount.emptyArray();
            this.numLastResortWatchdogSuccesses = 0;
            this.numHiddenNetworks = 0;
            this.numPasspointNetworks = 0;
            this.numTotalScanResults = 0;
            this.numOpenNetworkScanResults = 0;
            this.numLegacyPersonalNetworkScanResults = 0;
            this.numLegacyEnterpriseNetworkScanResults = 0;
            this.numHiddenNetworkScanResults = 0;
            this.numHotspot2R1NetworkScanResults = 0;
            this.numHotspot2R2NetworkScanResults = 0;
            this.numScans = 0;
            this.alertReasonCount = AlertReasonCount.emptyArray();
            this.wifiScoreCount = WifiScoreCount.emptyArray();
            this.softApDuration = SoftApDurationBucket.emptyArray();
            this.softApReturnCode = SoftApReturnCodeCount.emptyArray();
            this.rssiPollDeltaCount = RssiPollCount.emptyArray();
            this.staEventList = StaEvent.emptyArray();
            this.numHalCrashes = 0;
            this.numWificondCrashes = 0;
            this.numSetupClientInterfaceFailureDueToHal = 0;
            this.numSetupClientInterfaceFailureDueToWificond = 0;
            this.wifiAwareLog = null;
            this.numPasspointProviders = 0;
            this.numPasspointProviderInstallation = 0;
            this.numPasspointProviderInstallSuccess = 0;
            this.numPasspointProviderUninstallation = 0;
            this.numPasspointProviderUninstallSuccess = 0;
            this.numPasspointProvidersSuccessfullyConnected = 0;
            this.totalSsidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.totalBssidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableOpenSsidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableOpenBssidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableSavedSsidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableSavedBssidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableOpenOrSavedSsidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableOpenOrSavedBssidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableSavedPasspointProviderProfilesInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.availableSavedPasspointProviderBssidsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.fullBandAllSingleScanListenerResults = 0;
            this.partialAllSingleScanListenerResults = 0;
            this.pnoScanMetrics = null;
            this.connectToNetworkNotificationCount = ConnectToNetworkNotificationAndActionCount.emptyArray();
            this.connectToNetworkNotificationActionCount = ConnectToNetworkNotificationAndActionCount.emptyArray();
            this.openNetworkRecommenderBlacklistSize = 0;
            this.isWifiNetworksAvailableNotificationOn = false;
            this.numOpenNetworkRecommendationUpdates = 0;
            this.numOpenNetworkConnectMessageFailedToSend = 0;
            this.observedHotspotR1ApsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.observedHotspotR2ApsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.observedHotspotR1EssInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.observedHotspotR2EssInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.observedHotspotR1ApsPerEssInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.observedHotspotR2ApsPerEssInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.softApConnectedClientsEventsTethered = SoftApConnectedClientsEvent.emptyArray();
            this.softApConnectedClientsEventsLocalOnly = SoftApConnectedClientsEvent.emptyArray();
            this.wpsMetrics = null;
            this.wifiPowerStats = null;
            this.numConnectivityOneshotScans = 0;
            this.wifiWakeStats = null;
            this.observed80211McSupportingApsInScanHistogram = NumConnectableNetworksBucket.emptyArray();
            this.numSupplicantCrashes = 0;
            this.numHostapdCrashes = 0;
            this.numSetupClientInterfaceFailureDueToSupplicant = 0;
            this.numSetupSoftApInterfaceFailureDueToHal = 0;
            this.numSetupSoftApInterfaceFailureDueToWificond = 0;
            this.numSetupSoftApInterfaceFailureDueToHostapd = 0;
            this.numClientInterfaceDown = 0;
            this.numSoftApInterfaceDown = 0;
            this.numExternalAppOneshotScanRequests = 0;
            this.numExternalForegroundAppOneshotScanRequestsThrottled = 0;
            this.numExternalBackgroundAppOneshotScanRequestsThrottled = 0;
            this.watchdogTriggerToConnectionSuccessDurationMs = -1L;
            this.watchdogTotalConnectionFailureCountAfterTrigger = 0L;
            this.numOneshotHasDfsChannelScans = 0;
            this.wifiRttLog = null;
            this.isMacRandomizationOn = false;
            this.numRadioModeChangeToMcc = 0;
            this.numRadioModeChangeToScc = 0;
            this.numRadioModeChangeToSbs = 0;
            this.numRadioModeChangeToDbs = 0;
            this.numSoftApUserBandPreferenceUnsatisfied = 0;
            this.scoreExperimentId = "";
            this.wifiRadioUsage = null;
            this.experimentValues = null;
            this.wifiIsUnusableEventList = WifiIsUnusableEvent.emptyArray();
            this.linkSpeedCounts = LinkSpeedCount.emptyArray();
            this.numSarSensorRegistrationFailures = 0;
            this.hardwareRevision = "";
            this.wifiLinkLayerUsageStats = null;
            this.wifiUsabilityStatsList = WifiUsabilityStats.emptyArray();
            this.wifiUsabilityScoreCount = WifiUsabilityScoreCount.emptyArray();
            this.mobilityStatePnoStatsList = DeviceMobilityStatePnoScanStats.emptyArray();
            this.wifiP2PStats = null;
            this.wifiDppLog = null;
            this.numEnhancedOpenNetworks = 0;
            this.numWpa3PersonalNetworks = 0;
            this.numWpa3EnterpriseNetworks = 0;
            this.numEnhancedOpenNetworkScanResults = 0;
            this.numWpa3PersonalNetworkScanResults = 0;
            this.numWpa3EnterpriseNetworkScanResults = 0;
            this.wifiConfigStoreIo = null;
            this.numSavedNetworksWithMacRandomization = 0;
            this.linkProbeStats = null;
            this.networkSelectionExperimentDecisionsList = NetworkSelectionExperimentDecisions.emptyArray();
            this.wifiNetworkRequestApiLog = null;
            this.wifiNetworkSuggestionApiLog = null;
            this.wifiLockStats = null;
            this.wifiToggleStats = null;
            this.numAddOrUpdateNetworkCalls = 0;
            this.numEnableNetworkCalls = 0;
            this.passpointProvisionStats = null;
            this.installedPasspointProfileTypeForR1 = PasspointProfileTypeCount.emptyArray();
            this.installedPasspointProfileTypeForR2 = PasspointProfileTypeCount.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.connectionEvent;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.connectionEvent;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
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
            n2 = this.numSavedNetworks;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n3 = this.numOpenNetworks;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n = this.numLegacyPersonalNetworks;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n);
            }
            n = this.numLegacyEnterpriseNetworks;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n);
            }
            boolean bl = this.isLocationEnabled;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(6, bl);
            }
            bl = this.isScanningAlwaysEnabled;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(7, bl);
            }
            n = this.numWifiToggledViaSettings;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n);
            }
            n = this.numWifiToggledViaAirplane;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(9, n);
            }
            n = this.numNetworksAddedByUser;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(10, n);
            }
            n = this.numNetworksAddedByApps;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(11, n);
            }
            n = this.numEmptyScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(12, n);
            }
            n = this.numNonEmptyScanResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(13, n);
            }
            n2 = this.numOneshotScans;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(14, n2);
            }
            n3 = this.numBackgroundScans;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(15, n3);
            }
            object = this.scanReturnEntries;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.scanReturnEntries;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(16, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.wifiSystemStateEntries;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.wifiSystemStateEntries;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(17, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.backgroundScanReturnEntries;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.backgroundScanReturnEntries;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(18, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.backgroundScanRequestState;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.backgroundScanRequestState;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(19, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.numLastResortWatchdogTriggers;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(20, n);
            }
            n = this.numLastResortWatchdogBadAssociationNetworksTotal;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(21, n);
            }
            n = this.numLastResortWatchdogBadAuthenticationNetworksTotal;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(22, n);
            }
            n = this.numLastResortWatchdogBadDhcpNetworksTotal;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(23, n);
            }
            n = this.numLastResortWatchdogBadOtherNetworksTotal;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(24, n);
            }
            n = this.numLastResortWatchdogAvailableNetworksTotal;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(25, n);
            }
            n = this.numLastResortWatchdogTriggersWithBadAssociation;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(26, n);
            }
            n = this.numLastResortWatchdogTriggersWithBadAuthentication;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(27, n);
            }
            n = this.numLastResortWatchdogTriggersWithBadDhcp;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(28, n);
            }
            n = this.numLastResortWatchdogTriggersWithBadOther;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(29, n);
            }
            n = this.numConnectivityWatchdogPnoGood;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(30, n);
            }
            n = this.numConnectivityWatchdogPnoBad;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(31, n);
            }
            n = this.numConnectivityWatchdogBackgroundGood;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(32, n);
            }
            n = this.numConnectivityWatchdogBackgroundBad;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(33, n);
            }
            n = this.recordDurationSec;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(34, n);
            }
            object = this.rssiPollRssiCount;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.rssiPollRssiCount;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(35, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.numLastResortWatchdogSuccesses;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(36, n);
            }
            n = this.numHiddenNetworks;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(37, n);
            }
            n = this.numPasspointNetworks;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(38, n);
            }
            n = this.numTotalScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(39, n);
            }
            n = this.numOpenNetworkScanResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(40, n);
            }
            n = this.numLegacyPersonalNetworkScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(41, n);
            }
            n = this.numLegacyEnterpriseNetworkScanResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(42, n);
            }
            n = this.numHiddenNetworkScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(43, n);
            }
            n = this.numHotspot2R1NetworkScanResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(44, n);
            }
            n2 = this.numHotspot2R2NetworkScanResults;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(45, n2);
            }
            n3 = this.numScans;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(46, n3);
            }
            object = this.alertReasonCount;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.alertReasonCount;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(47, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.wifiScoreCount;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.wifiScoreCount;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(48, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.softApDuration;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.softApDuration;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(49, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.softApReturnCode;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.softApReturnCode;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(50, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.rssiPollDeltaCount;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.rssiPollDeltaCount;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(51, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.staEventList;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.staEventList;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(52, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.numHalCrashes;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(53, n);
            }
            n = this.numWificondCrashes;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(54, n);
            }
            n = this.numSetupClientInterfaceFailureDueToHal;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(55, n);
            }
            n = this.numSetupClientInterfaceFailureDueToWificond;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(56, n);
            }
            object = this.wifiAwareLog;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(57, (MessageNano)object);
            }
            n2 = this.numPasspointProviders;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(58, n2);
            }
            n = this.numPasspointProviderInstallation;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(59, n);
            }
            n = this.numPasspointProviderInstallSuccess;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(60, n);
            }
            n = this.numPasspointProviderUninstallation;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(61, n);
            }
            n = this.numPasspointProviderUninstallSuccess;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(62, n);
            }
            n = this.numPasspointProvidersSuccessfullyConnected;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(63, n);
            }
            object = this.totalSsidsInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.totalSsidsInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(64, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.totalBssidsInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.totalBssidsInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(65, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.availableOpenSsidsInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableOpenSsidsInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(66, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.availableOpenBssidsInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableOpenBssidsInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(67, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.availableSavedSsidsInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableSavedSsidsInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(68, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.availableSavedBssidsInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableSavedBssidsInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(69, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.availableOpenOrSavedSsidsInScanHistogram;
            n = n2;
            if (object != null) {
                n = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.availableOpenOrSavedSsidsInScanHistogram;
                        n = n2;
                        if (n3 >= ((ConnectionEvent[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(70, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            object = this.availableOpenOrSavedBssidsInScanHistogram;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((ConnectionEvent[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.availableOpenOrSavedBssidsInScanHistogram;
                        n3 = n;
                        if (n2 >= ((ConnectionEvent[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(71, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            object = this.availableSavedPasspointProviderProfilesInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableSavedPasspointProviderProfilesInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(72, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.availableSavedPasspointProviderBssidsInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.availableSavedPasspointProviderBssidsInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(73, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            n = this.fullBandAllSingleScanListenerResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(74, n);
            }
            n = this.partialAllSingleScanListenerResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(75, n);
            }
            object = this.pnoScanMetrics;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(76, (MessageNano)object);
            }
            object = this.connectToNetworkNotificationCount;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.connectToNetworkNotificationCount;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(77, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.connectToNetworkNotificationActionCount;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.connectToNetworkNotificationActionCount;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(78, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.openNetworkRecommenderBlacklistSize;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(79, n);
            }
            bl = this.isWifiNetworksAvailableNotificationOn;
            n2 = n3;
            if (bl) {
                n2 = n3 + CodedOutputByteBufferNano.computeBoolSize(80, bl);
            }
            n = this.numOpenNetworkRecommendationUpdates;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(81, n);
            }
            n = this.numOpenNetworkConnectMessageFailedToSend;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(82, n);
            }
            object = this.observedHotspotR1ApsInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR1ApsInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(83, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.observedHotspotR2ApsInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR2ApsInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(84, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.observedHotspotR1EssInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR1EssInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(85, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.observedHotspotR2EssInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR2EssInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(86, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.observedHotspotR1ApsPerEssInScanHistogram;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR1ApsPerEssInScanHistogram;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(87, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.observedHotspotR2ApsPerEssInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observedHotspotR2ApsPerEssInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(88, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.softApConnectedClientsEventsTethered;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.softApConnectedClientsEventsTethered;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(89, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.softApConnectedClientsEventsLocalOnly;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.softApConnectedClientsEventsLocalOnly;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(90, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.wpsMetrics;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(91, (MessageNano)object);
            }
            object = this.wifiPowerStats;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(92, (MessageNano)object);
            }
            n = this.numConnectivityOneshotScans;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(93, n);
            }
            object = this.wifiWakeStats;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(94, (MessageNano)object);
            }
            object = this.observed80211McSupportingApsInScanHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.observed80211McSupportingApsInScanHistogram;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(95, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.numSupplicantCrashes;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(96, n);
            }
            n = this.numHostapdCrashes;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(97, n);
            }
            n = this.numSetupClientInterfaceFailureDueToSupplicant;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(98, n);
            }
            n = this.numSetupSoftApInterfaceFailureDueToHal;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(99, n);
            }
            n = this.numSetupSoftApInterfaceFailureDueToWificond;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(100, n);
            }
            n = this.numSetupSoftApInterfaceFailureDueToHostapd;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(101, n);
            }
            n = this.numClientInterfaceDown;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(102, n);
            }
            n = this.numSoftApInterfaceDown;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(103, n);
            }
            n3 = this.numExternalAppOneshotScanRequests;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(104, n3);
            }
            n2 = this.numExternalForegroundAppOneshotScanRequestsThrottled;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(105, n2);
            }
            n = this.numExternalBackgroundAppOneshotScanRequestsThrottled;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(106, n);
            }
            long l = this.watchdogTriggerToConnectionSuccessDurationMs;
            n = n2;
            if (l != -1L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(107, l);
            }
            l = this.watchdogTotalConnectionFailureCountAfterTrigger;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(108, l);
            }
            n = this.numOneshotHasDfsChannelScans;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(109, n);
            }
            object = this.wifiRttLog;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(110, (MessageNano)object);
            }
            bl = this.isMacRandomizationOn;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(111, bl);
            }
            n = this.numRadioModeChangeToMcc;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(112, n);
            }
            n = this.numRadioModeChangeToScc;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(113, n);
            }
            n = this.numRadioModeChangeToSbs;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(114, n);
            }
            n = this.numRadioModeChangeToDbs;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(115, n);
            }
            n = this.numSoftApUserBandPreferenceUnsatisfied;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(116, n);
            }
            n = n2;
            if (!this.scoreExperimentId.equals("")) {
                n = n2 + CodedOutputByteBufferNano.computeStringSize(117, this.scoreExperimentId);
            }
            object = this.wifiRadioUsage;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(118, (MessageNano)object);
            }
            object = this.experimentValues;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(119, (MessageNano)object);
            }
            object = this.wifiIsUnusableEventList;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.wifiIsUnusableEventList;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(120, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.linkSpeedCounts;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.linkSpeedCounts;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(121, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = this.numSarSensorRegistrationFailures;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(122, n);
            }
            object = this.installedPasspointProfileTypeForR1;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.installedPasspointProfileTypeForR1;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(123, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n = n2;
            if (!this.hardwareRevision.equals("")) {
                n = n2 + CodedOutputByteBufferNano.computeStringSize(124, this.hardwareRevision);
            }
            object = this.wifiLinkLayerUsageStats;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(125, (MessageNano)object);
            }
            object = this.wifiUsabilityStatsList;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.wifiUsabilityStatsList;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(126, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.wifiUsabilityScoreCount;
            n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.wifiUsabilityScoreCount;
                        n3 = n2;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n3 = n2;
                        if (object != null) {
                            n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(127, (MessageNano)object);
                        }
                        ++n;
                        n2 = n3;
                    } while (true);
                }
            }
            object = this.mobilityStatePnoStatsList;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.mobilityStatePnoStatsList;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(128, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.wifiP2PStats;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(129, (MessageNano)object);
            }
            object = this.wifiDppLog;
            n2 = n3;
            if (object != null) {
                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(130, (MessageNano)object);
            }
            n = this.numEnhancedOpenNetworks;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(131, n);
            }
            n = this.numWpa3PersonalNetworks;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(132, n);
            }
            n = this.numWpa3EnterpriseNetworks;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(133, n);
            }
            n = this.numEnhancedOpenNetworkScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(134, n);
            }
            n = this.numWpa3PersonalNetworkScanResults;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(135, n);
            }
            n = this.numWpa3EnterpriseNetworkScanResults;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(136, n);
            }
            object = this.wifiConfigStoreIo;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(137, (MessageNano)object);
            }
            n = this.numSavedNetworksWithMacRandomization;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(138, n);
            }
            object = this.linkProbeStats;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(139, (MessageNano)object);
            }
            object = this.networkSelectionExperimentDecisionsList;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.networkSelectionExperimentDecisionsList;
                        n2 = n3;
                        if (n >= ((ConnectionEvent[])object).length) break;
                        object = object[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(140, (MessageNano)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            object = this.wifiNetworkRequestApiLog;
            n = n2;
            if (object != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(141, (MessageNano)object);
            }
            object = this.wifiNetworkSuggestionApiLog;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(142, (MessageNano)object);
            }
            object = this.wifiLockStats;
            n = n3;
            if (object != null) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(143, (MessageNano)object);
            }
            object = this.wifiToggleStats;
            n2 = n;
            if (object != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(144, (MessageNano)object);
            }
            n = this.numAddOrUpdateNetworkCalls;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(145, n);
            }
            n = this.numEnableNetworkCalls;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(146, n);
            }
            object = this.passpointProvisionStats;
            n3 = n2;
            if (object != null) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(147, (MessageNano)object);
            }
            object = this.installedPasspointProfileTypeForR2;
            n = n3;
            if (object != null) {
                n = n3;
                if (((ConnectionEvent[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.installedPasspointProfileTypeForR2;
                        n = n3;
                        if (n2 >= ((ConnectionEvent[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(148, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            return n;
        }

        @Override
        public WifiLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block151 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block151;
                        return this;
                    }
                    case 1186: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1186);
                        MessageNano[] arrmessageNano = this.installedPasspointProfileTypeForR2;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new PasspointProfileTypeCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.installedPasspointProfileTypeForR2, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new PasspointProfileTypeCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new PasspointProfileTypeCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.installedPasspointProfileTypeForR2 = arrmessageNano;
                        continue block151;
                    }
                    case 1178: {
                        if (this.passpointProvisionStats == null) {
                            this.passpointProvisionStats = new PasspointProvisionStats();
                        }
                        codedInputByteBufferNano.readMessage(this.passpointProvisionStats);
                        continue block151;
                    }
                    case 1168: {
                        this.numEnableNetworkCalls = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1160: {
                        this.numAddOrUpdateNetworkCalls = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1154: {
                        if (this.wifiToggleStats == null) {
                            this.wifiToggleStats = new WifiToggleStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiToggleStats);
                        continue block151;
                    }
                    case 1146: {
                        if (this.wifiLockStats == null) {
                            this.wifiLockStats = new WifiLockStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiLockStats);
                        continue block151;
                    }
                    case 1138: {
                        if (this.wifiNetworkSuggestionApiLog == null) {
                            this.wifiNetworkSuggestionApiLog = new WifiNetworkSuggestionApiLog();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiNetworkSuggestionApiLog);
                        continue block151;
                    }
                    case 1130: {
                        if (this.wifiNetworkRequestApiLog == null) {
                            this.wifiNetworkRequestApiLog = new WifiNetworkRequestApiLog();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiNetworkRequestApiLog);
                        continue block151;
                    }
                    case 1122: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1122);
                        MessageNano[] arrmessageNano = this.networkSelectionExperimentDecisionsList;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NetworkSelectionExperimentDecisions[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.networkSelectionExperimentDecisionsList, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NetworkSelectionExperimentDecisions();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NetworkSelectionExperimentDecisions();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.networkSelectionExperimentDecisionsList = arrmessageNano;
                        continue block151;
                    }
                    case 1114: {
                        if (this.linkProbeStats == null) {
                            this.linkProbeStats = new LinkProbeStats();
                        }
                        codedInputByteBufferNano.readMessage(this.linkProbeStats);
                        continue block151;
                    }
                    case 1104: {
                        this.numSavedNetworksWithMacRandomization = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1098: {
                        if (this.wifiConfigStoreIo == null) {
                            this.wifiConfigStoreIo = new WifiConfigStoreIO();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiConfigStoreIo);
                        continue block151;
                    }
                    case 1088: {
                        this.numWpa3EnterpriseNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1080: {
                        this.numWpa3PersonalNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1072: {
                        this.numEnhancedOpenNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1064: {
                        this.numWpa3EnterpriseNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1056: {
                        this.numWpa3PersonalNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1048: {
                        this.numEnhancedOpenNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 1042: {
                        if (this.wifiDppLog == null) {
                            this.wifiDppLog = new WifiDppLog();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiDppLog);
                        continue block151;
                    }
                    case 1034: {
                        if (this.wifiP2PStats == null) {
                            this.wifiP2PStats = new WifiP2pStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiP2PStats);
                        continue block151;
                    }
                    case 1026: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1026);
                        MessageNano[] arrmessageNano = this.mobilityStatePnoStatsList;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new DeviceMobilityStatePnoScanStats[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.mobilityStatePnoStatsList, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new DeviceMobilityStatePnoScanStats();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new DeviceMobilityStatePnoScanStats();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.mobilityStatePnoStatsList = arrmessageNano;
                        continue block151;
                    }
                    case 1018: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1018);
                        MessageNano[] arrmessageNano = this.wifiUsabilityScoreCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiUsabilityScoreCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.wifiUsabilityScoreCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiUsabilityScoreCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiUsabilityScoreCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.wifiUsabilityScoreCount = arrmessageNano;
                        continue block151;
                    }
                    case 1010: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 1010);
                        MessageNano[] arrmessageNano = this.wifiUsabilityStatsList;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiUsabilityStats[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.wifiUsabilityStatsList, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiUsabilityStats();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiUsabilityStats();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.wifiUsabilityStatsList = arrmessageNano;
                        continue block151;
                    }
                    case 1002: {
                        if (this.wifiLinkLayerUsageStats == null) {
                            this.wifiLinkLayerUsageStats = new WifiLinkLayerUsageStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiLinkLayerUsageStats);
                        continue block151;
                    }
                    case 994: {
                        this.hardwareRevision = codedInputByteBufferNano.readString();
                        continue block151;
                    }
                    case 986: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 986);
                        MessageNano[] arrmessageNano = this.installedPasspointProfileTypeForR1;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new PasspointProfileTypeCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.installedPasspointProfileTypeForR1, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new PasspointProfileTypeCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new PasspointProfileTypeCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.installedPasspointProfileTypeForR1 = arrmessageNano;
                        continue block151;
                    }
                    case 976: {
                        this.numSarSensorRegistrationFailures = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 970: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 970);
                        MessageNano[] arrmessageNano = this.linkSpeedCounts;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new LinkSpeedCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.linkSpeedCounts, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new LinkSpeedCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new LinkSpeedCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.linkSpeedCounts = arrmessageNano;
                        continue block151;
                    }
                    case 962: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 962);
                        MessageNano[] arrmessageNano = this.wifiIsUnusableEventList;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiIsUnusableEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.wifiIsUnusableEventList, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiIsUnusableEvent();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiIsUnusableEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.wifiIsUnusableEventList = arrmessageNano;
                        continue block151;
                    }
                    case 954: {
                        if (this.experimentValues == null) {
                            this.experimentValues = new ExperimentValues();
                        }
                        codedInputByteBufferNano.readMessage(this.experimentValues);
                        continue block151;
                    }
                    case 946: {
                        if (this.wifiRadioUsage == null) {
                            this.wifiRadioUsage = new WifiRadioUsage();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiRadioUsage);
                        continue block151;
                    }
                    case 938: {
                        this.scoreExperimentId = codedInputByteBufferNano.readString();
                        continue block151;
                    }
                    case 928: {
                        this.numSoftApUserBandPreferenceUnsatisfied = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 920: {
                        this.numRadioModeChangeToDbs = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 912: {
                        this.numRadioModeChangeToSbs = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 904: {
                        this.numRadioModeChangeToScc = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 896: {
                        this.numRadioModeChangeToMcc = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 888: {
                        this.isMacRandomizationOn = codedInputByteBufferNano.readBool();
                        continue block151;
                    }
                    case 882: {
                        if (this.wifiRttLog == null) {
                            this.wifiRttLog = new WifiRttLog();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiRttLog);
                        continue block151;
                    }
                    case 872: {
                        this.numOneshotHasDfsChannelScans = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 864: {
                        this.watchdogTotalConnectionFailureCountAfterTrigger = codedInputByteBufferNano.readInt64();
                        continue block151;
                    }
                    case 856: {
                        this.watchdogTriggerToConnectionSuccessDurationMs = codedInputByteBufferNano.readInt64();
                        continue block151;
                    }
                    case 848: {
                        this.numExternalBackgroundAppOneshotScanRequestsThrottled = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 840: {
                        this.numExternalForegroundAppOneshotScanRequestsThrottled = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 832: {
                        this.numExternalAppOneshotScanRequests = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 824: {
                        this.numSoftApInterfaceDown = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 816: {
                        this.numClientInterfaceDown = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 808: {
                        this.numSetupSoftApInterfaceFailureDueToHostapd = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 800: {
                        this.numSetupSoftApInterfaceFailureDueToWificond = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 792: {
                        this.numSetupSoftApInterfaceFailureDueToHal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 784: {
                        this.numSetupClientInterfaceFailureDueToSupplicant = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 776: {
                        this.numHostapdCrashes = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 768: {
                        this.numSupplicantCrashes = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 762: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 762);
                        MessageNano[] arrmessageNano = this.observed80211McSupportingApsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observed80211McSupportingApsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observed80211McSupportingApsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 754: {
                        if (this.wifiWakeStats == null) {
                            this.wifiWakeStats = new WifiWakeStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiWakeStats);
                        continue block151;
                    }
                    case 744: {
                        this.numConnectivityOneshotScans = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 738: {
                        if (this.wifiPowerStats == null) {
                            this.wifiPowerStats = new WifiPowerStats();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiPowerStats);
                        continue block151;
                    }
                    case 730: {
                        if (this.wpsMetrics == null) {
                            this.wpsMetrics = new WpsMetrics();
                        }
                        codedInputByteBufferNano.readMessage(this.wpsMetrics);
                        continue block151;
                    }
                    case 722: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 722);
                        MessageNano[] arrmessageNano = this.softApConnectedClientsEventsLocalOnly;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new SoftApConnectedClientsEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.softApConnectedClientsEventsLocalOnly, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new SoftApConnectedClientsEvent();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new SoftApConnectedClientsEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.softApConnectedClientsEventsLocalOnly = arrmessageNano;
                        continue block151;
                    }
                    case 714: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 714);
                        MessageNano[] arrmessageNano = this.softApConnectedClientsEventsTethered;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new SoftApConnectedClientsEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.softApConnectedClientsEventsTethered, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new SoftApConnectedClientsEvent();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new SoftApConnectedClientsEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.softApConnectedClientsEventsTethered = arrmessageNano;
                        continue block151;
                    }
                    case 706: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 706);
                        MessageNano[] arrmessageNano = this.observedHotspotR2ApsPerEssInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR2ApsPerEssInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR2ApsPerEssInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 698: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 698);
                        MessageNano[] arrmessageNano = this.observedHotspotR1ApsPerEssInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR1ApsPerEssInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR1ApsPerEssInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 690: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 690);
                        MessageNano[] arrmessageNano = this.observedHotspotR2EssInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR2EssInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR2EssInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 682: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 682);
                        MessageNano[] arrmessageNano = this.observedHotspotR1EssInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR1EssInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR1EssInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 674: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 674);
                        MessageNano[] arrmessageNano = this.observedHotspotR2ApsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR2ApsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR2ApsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 666: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 666);
                        MessageNano[] arrmessageNano = this.observedHotspotR1ApsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.observedHotspotR1ApsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.observedHotspotR1ApsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 656: {
                        this.numOpenNetworkConnectMessageFailedToSend = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 648: {
                        this.numOpenNetworkRecommendationUpdates = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 640: {
                        this.isWifiNetworksAvailableNotificationOn = codedInputByteBufferNano.readBool();
                        continue block151;
                    }
                    case 632: {
                        this.openNetworkRecommenderBlacklistSize = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 626: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 626);
                        MessageNano[] arrmessageNano = this.connectToNetworkNotificationActionCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new ConnectToNetworkNotificationAndActionCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.connectToNetworkNotificationActionCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new ConnectToNetworkNotificationAndActionCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new ConnectToNetworkNotificationAndActionCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.connectToNetworkNotificationActionCount = arrmessageNano;
                        continue block151;
                    }
                    case 618: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 618);
                        MessageNano[] arrmessageNano = this.connectToNetworkNotificationCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new ConnectToNetworkNotificationAndActionCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.connectToNetworkNotificationCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new ConnectToNetworkNotificationAndActionCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new ConnectToNetworkNotificationAndActionCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.connectToNetworkNotificationCount = arrmessageNano;
                        continue block151;
                    }
                    case 610: {
                        if (this.pnoScanMetrics == null) {
                            this.pnoScanMetrics = new PnoScanMetrics();
                        }
                        codedInputByteBufferNano.readMessage(this.pnoScanMetrics);
                        continue block151;
                    }
                    case 600: {
                        this.partialAllSingleScanListenerResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 592: {
                        this.fullBandAllSingleScanListenerResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 586: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 586);
                        MessageNano[] arrmessageNano = this.availableSavedPasspointProviderBssidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableSavedPasspointProviderBssidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableSavedPasspointProviderBssidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 578: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 578);
                        MessageNano[] arrmessageNano = this.availableSavedPasspointProviderProfilesInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableSavedPasspointProviderProfilesInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableSavedPasspointProviderProfilesInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 570: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 570);
                        MessageNano[] arrmessageNano = this.availableOpenOrSavedBssidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableOpenOrSavedBssidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableOpenOrSavedBssidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 562: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 562);
                        MessageNano[] arrmessageNano = this.availableOpenOrSavedSsidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableOpenOrSavedSsidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableOpenOrSavedSsidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 554: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 554);
                        MessageNano[] arrmessageNano = this.availableSavedBssidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableSavedBssidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableSavedBssidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 546: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 546);
                        MessageNano[] arrmessageNano = this.availableSavedSsidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableSavedSsidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableSavedSsidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 538: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 538);
                        MessageNano[] arrmessageNano = this.availableOpenBssidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableOpenBssidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableOpenBssidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 530: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 530);
                        MessageNano[] arrmessageNano = this.availableOpenSsidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.availableOpenSsidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.availableOpenSsidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 522: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 522);
                        MessageNano[] arrmessageNano = this.totalBssidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.totalBssidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.totalBssidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 514: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 514);
                        MessageNano[] arrmessageNano = this.totalSsidsInScanHistogram;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new NumConnectableNetworksBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.totalSsidsInScanHistogram, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new NumConnectableNetworksBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new NumConnectableNetworksBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.totalSsidsInScanHistogram = arrmessageNano;
                        continue block151;
                    }
                    case 504: {
                        this.numPasspointProvidersSuccessfullyConnected = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 496: {
                        this.numPasspointProviderUninstallSuccess = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 488: {
                        this.numPasspointProviderUninstallation = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 480: {
                        this.numPasspointProviderInstallSuccess = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 472: {
                        this.numPasspointProviderInstallation = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 464: {
                        this.numPasspointProviders = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 458: {
                        if (this.wifiAwareLog == null) {
                            this.wifiAwareLog = new WifiAwareLog();
                        }
                        codedInputByteBufferNano.readMessage(this.wifiAwareLog);
                        continue block151;
                    }
                    case 448: {
                        this.numSetupClientInterfaceFailureDueToWificond = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 440: {
                        this.numSetupClientInterfaceFailureDueToHal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 432: {
                        this.numWificondCrashes = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 424: {
                        this.numHalCrashes = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 418: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 418);
                        MessageNano[] arrmessageNano = this.staEventList;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new StaEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.staEventList, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new StaEvent();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new StaEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.staEventList = arrmessageNano;
                        continue block151;
                    }
                    case 410: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 410);
                        MessageNano[] arrmessageNano = this.rssiPollDeltaCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new RssiPollCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.rssiPollDeltaCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new RssiPollCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new RssiPollCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.rssiPollDeltaCount = arrmessageNano;
                        continue block151;
                    }
                    case 402: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 402);
                        MessageNano[] arrmessageNano = this.softApReturnCode;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new SoftApReturnCodeCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.softApReturnCode, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new SoftApReturnCodeCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new SoftApReturnCodeCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.softApReturnCode = arrmessageNano;
                        continue block151;
                    }
                    case 394: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 394);
                        MessageNano[] arrmessageNano = this.softApDuration;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new SoftApDurationBucket[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.softApDuration, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new SoftApDurationBucket();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new SoftApDurationBucket();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.softApDuration = arrmessageNano;
                        continue block151;
                    }
                    case 386: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 386);
                        MessageNano[] arrmessageNano = this.wifiScoreCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiScoreCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.wifiScoreCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiScoreCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiScoreCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.wifiScoreCount = arrmessageNano;
                        continue block151;
                    }
                    case 378: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 378);
                        MessageNano[] arrmessageNano = this.alertReasonCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new AlertReasonCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.alertReasonCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new AlertReasonCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new AlertReasonCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.alertReasonCount = arrmessageNano;
                        continue block151;
                    }
                    case 368: {
                        this.numScans = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 360: {
                        this.numHotspot2R2NetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 352: {
                        this.numHotspot2R1NetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 344: {
                        this.numHiddenNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 336: {
                        this.numLegacyEnterpriseNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 328: {
                        this.numLegacyPersonalNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 320: {
                        this.numOpenNetworkScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 312: {
                        this.numTotalScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 304: {
                        this.numPasspointNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 296: {
                        this.numHiddenNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 288: {
                        this.numLastResortWatchdogSuccesses = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 282: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 282);
                        MessageNano[] arrmessageNano = this.rssiPollRssiCount;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new RssiPollCount[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.rssiPollRssiCount, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new RssiPollCount();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new RssiPollCount();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.rssiPollRssiCount = arrmessageNano;
                        continue block151;
                    }
                    case 272: {
                        this.recordDurationSec = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 264: {
                        this.numConnectivityWatchdogBackgroundBad = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 256: {
                        this.numConnectivityWatchdogBackgroundGood = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 248: {
                        this.numConnectivityWatchdogPnoBad = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 240: {
                        this.numConnectivityWatchdogPnoGood = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 232: {
                        this.numLastResortWatchdogTriggersWithBadOther = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 224: {
                        this.numLastResortWatchdogTriggersWithBadDhcp = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 216: {
                        this.numLastResortWatchdogTriggersWithBadAuthentication = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 208: {
                        this.numLastResortWatchdogTriggersWithBadAssociation = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 200: {
                        this.numLastResortWatchdogAvailableNetworksTotal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 192: {
                        this.numLastResortWatchdogBadOtherNetworksTotal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 184: {
                        this.numLastResortWatchdogBadDhcpNetworksTotal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 176: {
                        this.numLastResortWatchdogBadAuthenticationNetworksTotal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 168: {
                        this.numLastResortWatchdogBadAssociationNetworksTotal = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 160: {
                        this.numLastResortWatchdogTriggers = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 154: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 154);
                        MessageNano[] arrmessageNano = this.backgroundScanRequestState;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiSystemStateEntry[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.backgroundScanRequestState, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiSystemStateEntry();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiSystemStateEntry();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.backgroundScanRequestState = arrmessageNano;
                        continue block151;
                    }
                    case 146: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 146);
                        MessageNano[] arrmessageNano = this.backgroundScanReturnEntries;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new ScanReturnEntry[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.backgroundScanReturnEntries, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new ScanReturnEntry();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new ScanReturnEntry();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.backgroundScanReturnEntries = arrmessageNano;
                        continue block151;
                    }
                    case 138: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 138);
                        MessageNano[] arrmessageNano = this.wifiSystemStateEntries;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new WifiSystemStateEntry[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.wifiSystemStateEntries, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new WifiSystemStateEntry();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new WifiSystemStateEntry();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.wifiSystemStateEntries = arrmessageNano;
                        continue block151;
                    }
                    case 130: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 130);
                        MessageNano[] arrmessageNano = this.scanReturnEntries;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new ScanReturnEntry[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.scanReturnEntries, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new ScanReturnEntry();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new ScanReturnEntry();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.scanReturnEntries = arrmessageNano;
                        continue block151;
                    }
                    case 120: {
                        this.numBackgroundScans = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 112: {
                        this.numOneshotScans = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 104: {
                        this.numNonEmptyScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 96: {
                        this.numEmptyScanResults = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 88: {
                        this.numNetworksAddedByApps = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 80: {
                        this.numNetworksAddedByUser = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 72: {
                        this.numWifiToggledViaAirplane = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 64: {
                        this.numWifiToggledViaSettings = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 56: {
                        this.isScanningAlwaysEnabled = codedInputByteBufferNano.readBool();
                        continue block151;
                    }
                    case 48: {
                        this.isLocationEnabled = codedInputByteBufferNano.readBool();
                        continue block151;
                    }
                    case 40: {
                        this.numLegacyEnterpriseNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 32: {
                        this.numLegacyPersonalNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 24: {
                        this.numOpenNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 16: {
                        this.numSavedNetworks = codedInputByteBufferNano.readInt32();
                        continue block151;
                    }
                    case 10: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                        MessageNano[] arrmessageNano = this.connectionEvent;
                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                        arrmessageNano = new ConnectionEvent[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.connectionEvent, 0, arrmessageNano, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrmessageNano.length - 1) {
                            arrmessageNano[n2] = new ConnectionEvent();
                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrmessageNano[n2] = new ConnectionEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        this.connectionEvent = arrmessageNano;
                        continue block151;
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
            int n;
            Object object = this.connectionEvent;
            if (object != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.connectionEvent)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((n = this.numSavedNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numOpenNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numLegacyPersonalNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.numLegacyEnterpriseNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if (bl = this.isLocationEnabled) {
                codedOutputByteBufferNano.writeBool(6, bl);
            }
            if (bl = this.isScanningAlwaysEnabled) {
                codedOutputByteBufferNano.writeBool(7, bl);
            }
            if ((n = this.numWifiToggledViaSettings) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.numWifiToggledViaAirplane) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if ((n = this.numNetworksAddedByUser) != 0) {
                codedOutputByteBufferNano.writeInt32(10, n);
            }
            if ((n = this.numNetworksAddedByApps) != 0) {
                codedOutputByteBufferNano.writeInt32(11, n);
            }
            if ((n = this.numEmptyScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(12, n);
            }
            if ((n = this.numNonEmptyScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            if ((n = this.numOneshotScans) != 0) {
                codedOutputByteBufferNano.writeInt32(14, n);
            }
            if ((n = this.numBackgroundScans) != 0) {
                codedOutputByteBufferNano.writeInt32(15, n);
            }
            if ((object = this.scanReturnEntries) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.scanReturnEntries)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(16, (MessageNano)object);
                }
            }
            if ((object = this.wifiSystemStateEntries) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.wifiSystemStateEntries)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(17, (MessageNano)object);
                }
            }
            if ((object = this.backgroundScanReturnEntries) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.backgroundScanReturnEntries)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(18, (MessageNano)object);
                }
            }
            if ((object = this.backgroundScanRequestState) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.backgroundScanRequestState)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(19, (MessageNano)object);
                }
            }
            if ((n = this.numLastResortWatchdogTriggers) != 0) {
                codedOutputByteBufferNano.writeInt32(20, n);
            }
            if ((n = this.numLastResortWatchdogBadAssociationNetworksTotal) != 0) {
                codedOutputByteBufferNano.writeInt32(21, n);
            }
            if ((n = this.numLastResortWatchdogBadAuthenticationNetworksTotal) != 0) {
                codedOutputByteBufferNano.writeInt32(22, n);
            }
            if ((n = this.numLastResortWatchdogBadDhcpNetworksTotal) != 0) {
                codedOutputByteBufferNano.writeInt32(23, n);
            }
            if ((n = this.numLastResortWatchdogBadOtherNetworksTotal) != 0) {
                codedOutputByteBufferNano.writeInt32(24, n);
            }
            if ((n = this.numLastResortWatchdogAvailableNetworksTotal) != 0) {
                codedOutputByteBufferNano.writeInt32(25, n);
            }
            if ((n = this.numLastResortWatchdogTriggersWithBadAssociation) != 0) {
                codedOutputByteBufferNano.writeInt32(26, n);
            }
            if ((n = this.numLastResortWatchdogTriggersWithBadAuthentication) != 0) {
                codedOutputByteBufferNano.writeInt32(27, n);
            }
            if ((n = this.numLastResortWatchdogTriggersWithBadDhcp) != 0) {
                codedOutputByteBufferNano.writeInt32(28, n);
            }
            if ((n = this.numLastResortWatchdogTriggersWithBadOther) != 0) {
                codedOutputByteBufferNano.writeInt32(29, n);
            }
            if ((n = this.numConnectivityWatchdogPnoGood) != 0) {
                codedOutputByteBufferNano.writeInt32(30, n);
            }
            if ((n = this.numConnectivityWatchdogPnoBad) != 0) {
                codedOutputByteBufferNano.writeInt32(31, n);
            }
            if ((n = this.numConnectivityWatchdogBackgroundGood) != 0) {
                codedOutputByteBufferNano.writeInt32(32, n);
            }
            if ((n = this.numConnectivityWatchdogBackgroundBad) != 0) {
                codedOutputByteBufferNano.writeInt32(33, n);
            }
            if ((n = this.recordDurationSec) != 0) {
                codedOutputByteBufferNano.writeInt32(34, n);
            }
            if ((object = this.rssiPollRssiCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.rssiPollRssiCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(35, (MessageNano)object);
                }
            }
            if ((n = this.numLastResortWatchdogSuccesses) != 0) {
                codedOutputByteBufferNano.writeInt32(36, n);
            }
            if ((n = this.numHiddenNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(37, n);
            }
            if ((n = this.numPasspointNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(38, n);
            }
            if ((n = this.numTotalScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(39, n);
            }
            if ((n = this.numOpenNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(40, n);
            }
            if ((n = this.numLegacyPersonalNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(41, n);
            }
            if ((n = this.numLegacyEnterpriseNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(42, n);
            }
            if ((n = this.numHiddenNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(43, n);
            }
            if ((n = this.numHotspot2R1NetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(44, n);
            }
            if ((n = this.numHotspot2R2NetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(45, n);
            }
            if ((n = this.numScans) != 0) {
                codedOutputByteBufferNano.writeInt32(46, n);
            }
            if ((object = this.alertReasonCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.alertReasonCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(47, (MessageNano)object);
                }
            }
            if ((object = this.wifiScoreCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.wifiScoreCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(48, (MessageNano)object);
                }
            }
            if ((object = this.softApDuration) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.softApDuration)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(49, (MessageNano)object);
                }
            }
            if ((object = this.softApReturnCode) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.softApReturnCode)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(50, (MessageNano)object);
                }
            }
            if ((object = this.rssiPollDeltaCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.rssiPollDeltaCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(51, (MessageNano)object);
                }
            }
            if ((object = this.staEventList) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.staEventList)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(52, (MessageNano)object);
                }
            }
            if ((n = this.numHalCrashes) != 0) {
                codedOutputByteBufferNano.writeInt32(53, n);
            }
            if ((n = this.numWificondCrashes) != 0) {
                codedOutputByteBufferNano.writeInt32(54, n);
            }
            if ((n = this.numSetupClientInterfaceFailureDueToHal) != 0) {
                codedOutputByteBufferNano.writeInt32(55, n);
            }
            if ((n = this.numSetupClientInterfaceFailureDueToWificond) != 0) {
                codedOutputByteBufferNano.writeInt32(56, n);
            }
            if ((object = this.wifiAwareLog) != null) {
                codedOutputByteBufferNano.writeMessage(57, (MessageNano)object);
            }
            if ((n = this.numPasspointProviders) != 0) {
                codedOutputByteBufferNano.writeInt32(58, n);
            }
            if ((n = this.numPasspointProviderInstallation) != 0) {
                codedOutputByteBufferNano.writeInt32(59, n);
            }
            if ((n = this.numPasspointProviderInstallSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(60, n);
            }
            if ((n = this.numPasspointProviderUninstallation) != 0) {
                codedOutputByteBufferNano.writeInt32(61, n);
            }
            if ((n = this.numPasspointProviderUninstallSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(62, n);
            }
            if ((n = this.numPasspointProvidersSuccessfullyConnected) != 0) {
                codedOutputByteBufferNano.writeInt32(63, n);
            }
            if ((object = this.totalSsidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.totalSsidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(64, (MessageNano)object);
                }
            }
            if ((object = this.totalBssidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.totalBssidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(65, (MessageNano)object);
                }
            }
            if ((object = this.availableOpenSsidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableOpenSsidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(66, (MessageNano)object);
                }
            }
            if ((object = this.availableOpenBssidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableOpenBssidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(67, (MessageNano)object);
                }
            }
            if ((object = this.availableSavedSsidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableSavedSsidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(68, (MessageNano)object);
                }
            }
            if ((object = this.availableSavedBssidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableSavedBssidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(69, (MessageNano)object);
                }
            }
            if ((object = this.availableOpenOrSavedSsidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableOpenOrSavedSsidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(70, (MessageNano)object);
                }
            }
            if ((object = this.availableOpenOrSavedBssidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableOpenOrSavedBssidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(71, (MessageNano)object);
                }
            }
            if ((object = this.availableSavedPasspointProviderProfilesInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableSavedPasspointProviderProfilesInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(72, (MessageNano)object);
                }
            }
            if ((object = this.availableSavedPasspointProviderBssidsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.availableSavedPasspointProviderBssidsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(73, (MessageNano)object);
                }
            }
            if ((n = this.fullBandAllSingleScanListenerResults) != 0) {
                codedOutputByteBufferNano.writeInt32(74, n);
            }
            if ((n = this.partialAllSingleScanListenerResults) != 0) {
                codedOutputByteBufferNano.writeInt32(75, n);
            }
            if ((object = this.pnoScanMetrics) != null) {
                codedOutputByteBufferNano.writeMessage(76, (MessageNano)object);
            }
            if ((object = this.connectToNetworkNotificationCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.connectToNetworkNotificationCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(77, (MessageNano)object);
                }
            }
            if ((object = this.connectToNetworkNotificationActionCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.connectToNetworkNotificationActionCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(78, (MessageNano)object);
                }
            }
            if ((n = this.openNetworkRecommenderBlacklistSize) != 0) {
                codedOutputByteBufferNano.writeInt32(79, n);
            }
            if (bl = this.isWifiNetworksAvailableNotificationOn) {
                codedOutputByteBufferNano.writeBool(80, bl);
            }
            if ((n = this.numOpenNetworkRecommendationUpdates) != 0) {
                codedOutputByteBufferNano.writeInt32(81, n);
            }
            if ((n = this.numOpenNetworkConnectMessageFailedToSend) != 0) {
                codedOutputByteBufferNano.writeInt32(82, n);
            }
            if ((object = this.observedHotspotR1ApsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR1ApsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(83, (MessageNano)object);
                }
            }
            if ((object = this.observedHotspotR2ApsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR2ApsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(84, (MessageNano)object);
                }
            }
            if ((object = this.observedHotspotR1EssInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR1EssInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(85, (MessageNano)object);
                }
            }
            if ((object = this.observedHotspotR2EssInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR2EssInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(86, (MessageNano)object);
                }
            }
            if ((object = this.observedHotspotR1ApsPerEssInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR1ApsPerEssInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(87, (MessageNano)object);
                }
            }
            if ((object = this.observedHotspotR2ApsPerEssInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observedHotspotR2ApsPerEssInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(88, (MessageNano)object);
                }
            }
            if ((object = this.softApConnectedClientsEventsTethered) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.softApConnectedClientsEventsTethered)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(89, (MessageNano)object);
                }
            }
            if ((object = this.softApConnectedClientsEventsLocalOnly) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.softApConnectedClientsEventsLocalOnly)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(90, (MessageNano)object);
                }
            }
            if ((object = this.wpsMetrics) != null) {
                codedOutputByteBufferNano.writeMessage(91, (MessageNano)object);
            }
            if ((object = this.wifiPowerStats) != null) {
                codedOutputByteBufferNano.writeMessage(92, (MessageNano)object);
            }
            if ((n = this.numConnectivityOneshotScans) != 0) {
                codedOutputByteBufferNano.writeInt32(93, n);
            }
            if ((object = this.wifiWakeStats) != null) {
                codedOutputByteBufferNano.writeMessage(94, (MessageNano)object);
            }
            if ((object = this.observed80211McSupportingApsInScanHistogram) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.observed80211McSupportingApsInScanHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(95, (MessageNano)object);
                }
            }
            if ((n = this.numSupplicantCrashes) != 0) {
                codedOutputByteBufferNano.writeInt32(96, n);
            }
            if ((n = this.numHostapdCrashes) != 0) {
                codedOutputByteBufferNano.writeInt32(97, n);
            }
            if ((n = this.numSetupClientInterfaceFailureDueToSupplicant) != 0) {
                codedOutputByteBufferNano.writeInt32(98, n);
            }
            if ((n = this.numSetupSoftApInterfaceFailureDueToHal) != 0) {
                codedOutputByteBufferNano.writeInt32(99, n);
            }
            if ((n = this.numSetupSoftApInterfaceFailureDueToWificond) != 0) {
                codedOutputByteBufferNano.writeInt32(100, n);
            }
            if ((n = this.numSetupSoftApInterfaceFailureDueToHostapd) != 0) {
                codedOutputByteBufferNano.writeInt32(101, n);
            }
            if ((n = this.numClientInterfaceDown) != 0) {
                codedOutputByteBufferNano.writeInt32(102, n);
            }
            if ((n = this.numSoftApInterfaceDown) != 0) {
                codedOutputByteBufferNano.writeInt32(103, n);
            }
            if ((n = this.numExternalAppOneshotScanRequests) != 0) {
                codedOutputByteBufferNano.writeInt32(104, n);
            }
            if ((n = this.numExternalForegroundAppOneshotScanRequestsThrottled) != 0) {
                codedOutputByteBufferNano.writeInt32(105, n);
            }
            if ((n = this.numExternalBackgroundAppOneshotScanRequestsThrottled) != 0) {
                codedOutputByteBufferNano.writeInt32(106, n);
            }
            if ((l = this.watchdogTriggerToConnectionSuccessDurationMs) != -1L) {
                codedOutputByteBufferNano.writeInt64(107, l);
            }
            if ((l = this.watchdogTotalConnectionFailureCountAfterTrigger) != 0L) {
                codedOutputByteBufferNano.writeInt64(108, l);
            }
            if ((n = this.numOneshotHasDfsChannelScans) != 0) {
                codedOutputByteBufferNano.writeInt32(109, n);
            }
            if ((object = this.wifiRttLog) != null) {
                codedOutputByteBufferNano.writeMessage(110, (MessageNano)object);
            }
            if (bl = this.isMacRandomizationOn) {
                codedOutputByteBufferNano.writeBool(111, bl);
            }
            if ((n = this.numRadioModeChangeToMcc) != 0) {
                codedOutputByteBufferNano.writeInt32(112, n);
            }
            if ((n = this.numRadioModeChangeToScc) != 0) {
                codedOutputByteBufferNano.writeInt32(113, n);
            }
            if ((n = this.numRadioModeChangeToSbs) != 0) {
                codedOutputByteBufferNano.writeInt32(114, n);
            }
            if ((n = this.numRadioModeChangeToDbs) != 0) {
                codedOutputByteBufferNano.writeInt32(115, n);
            }
            if ((n = this.numSoftApUserBandPreferenceUnsatisfied) != 0) {
                codedOutputByteBufferNano.writeInt32(116, n);
            }
            if (!this.scoreExperimentId.equals("")) {
                codedOutputByteBufferNano.writeString(117, this.scoreExperimentId);
            }
            if ((object = this.wifiRadioUsage) != null) {
                codedOutputByteBufferNano.writeMessage(118, (MessageNano)object);
            }
            if ((object = this.experimentValues) != null) {
                codedOutputByteBufferNano.writeMessage(119, (MessageNano)object);
            }
            if ((object = this.wifiIsUnusableEventList) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.wifiIsUnusableEventList)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(120, (MessageNano)object);
                }
            }
            if ((object = this.linkSpeedCounts) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.linkSpeedCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(121, (MessageNano)object);
                }
            }
            if ((n = this.numSarSensorRegistrationFailures) != 0) {
                codedOutputByteBufferNano.writeInt32(122, n);
            }
            if ((object = this.installedPasspointProfileTypeForR1) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.installedPasspointProfileTypeForR1)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(123, (MessageNano)object);
                }
            }
            if (!this.hardwareRevision.equals("")) {
                codedOutputByteBufferNano.writeString(124, this.hardwareRevision);
            }
            if ((object = this.wifiLinkLayerUsageStats) != null) {
                codedOutputByteBufferNano.writeMessage(125, (MessageNano)object);
            }
            if ((object = this.wifiUsabilityStatsList) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.wifiUsabilityStatsList)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(126, (MessageNano)object);
                }
            }
            if ((object = this.wifiUsabilityScoreCount) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.wifiUsabilityScoreCount)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(127, (MessageNano)object);
                }
            }
            if ((object = this.mobilityStatePnoStatsList) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.mobilityStatePnoStatsList)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(128, (MessageNano)object);
                }
            }
            if ((object = this.wifiP2PStats) != null) {
                codedOutputByteBufferNano.writeMessage(129, (MessageNano)object);
            }
            if ((object = this.wifiDppLog) != null) {
                codedOutputByteBufferNano.writeMessage(130, (MessageNano)object);
            }
            if ((n = this.numEnhancedOpenNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(131, n);
            }
            if ((n = this.numWpa3PersonalNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(132, n);
            }
            if ((n = this.numWpa3EnterpriseNetworks) != 0) {
                codedOutputByteBufferNano.writeInt32(133, n);
            }
            if ((n = this.numEnhancedOpenNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(134, n);
            }
            if ((n = this.numWpa3PersonalNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(135, n);
            }
            if ((n = this.numWpa3EnterpriseNetworkScanResults) != 0) {
                codedOutputByteBufferNano.writeInt32(136, n);
            }
            if ((object = this.wifiConfigStoreIo) != null) {
                codedOutputByteBufferNano.writeMessage(137, (MessageNano)object);
            }
            if ((n = this.numSavedNetworksWithMacRandomization) != 0) {
                codedOutputByteBufferNano.writeInt32(138, n);
            }
            if ((object = this.linkProbeStats) != null) {
                codedOutputByteBufferNano.writeMessage(139, (MessageNano)object);
            }
            if ((object = this.networkSelectionExperimentDecisionsList) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.networkSelectionExperimentDecisionsList)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(140, (MessageNano)object);
                }
            }
            if ((object = this.wifiNetworkRequestApiLog) != null) {
                codedOutputByteBufferNano.writeMessage(141, (MessageNano)object);
            }
            if ((object = this.wifiNetworkSuggestionApiLog) != null) {
                codedOutputByteBufferNano.writeMessage(142, (MessageNano)object);
            }
            if ((object = this.wifiLockStats) != null) {
                codedOutputByteBufferNano.writeMessage(143, (MessageNano)object);
            }
            if ((object = this.wifiToggleStats) != null) {
                codedOutputByteBufferNano.writeMessage(144, (MessageNano)object);
            }
            if ((n = this.numAddOrUpdateNetworkCalls) != 0) {
                codedOutputByteBufferNano.writeInt32(145, n);
            }
            if ((n = this.numEnableNetworkCalls) != 0) {
                codedOutputByteBufferNano.writeInt32(146, n);
            }
            if ((object = this.passpointProvisionStats) != null) {
                codedOutputByteBufferNano.writeMessage(147, (MessageNano)object);
            }
            if ((object = this.installedPasspointProfileTypeForR2) != null && ((ConnectionEvent[])object).length > 0) {
                for (n = 0; n < ((ConnectionEvent[])(object = this.installedPasspointProfileTypeForR2)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(148, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class ScanReturnEntry
        extends MessageNano {
            private static volatile ScanReturnEntry[] _emptyArray;
            public int scanResultsCount;
            public int scanReturnCode;

            public ScanReturnEntry() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static ScanReturnEntry[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new ScanReturnEntry[0];
                    return _emptyArray;
                }
            }

            public static ScanReturnEntry parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new ScanReturnEntry().mergeFrom(codedInputByteBufferNano);
            }

            public static ScanReturnEntry parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new ScanReturnEntry(), arrby);
            }

            public ScanReturnEntry clear() {
                this.scanReturnCode = 0;
                this.scanResultsCount = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.scanReturnCode;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.scanResultsCount;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public ScanReturnEntry mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.scanResultsCount = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) continue;
                    this.scanReturnCode = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.scanReturnCode;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.scanResultsCount) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class WifiSystemStateEntry
        extends MessageNano {
            private static volatile WifiSystemStateEntry[] _emptyArray;
            public boolean isScreenOn;
            public int wifiState;
            public int wifiStateCount;

            public WifiSystemStateEntry() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static WifiSystemStateEntry[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new WifiSystemStateEntry[0];
                    return _emptyArray;
                }
            }

            public static WifiSystemStateEntry parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new WifiSystemStateEntry().mergeFrom(codedInputByteBufferNano);
            }

            public static WifiSystemStateEntry parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new WifiSystemStateEntry(), arrby);
            }

            public WifiSystemStateEntry clear() {
                this.wifiState = 0;
                this.wifiStateCount = 0;
                this.isScreenOn = false;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.wifiState;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.wifiStateCount;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                boolean bl = this.isScreenOn;
                n3 = n;
                if (bl) {
                    n3 = n + CodedOutputByteBufferNano.computeBoolSize(3, bl);
                }
                return n3;
            }

            @Override
            public WifiSystemStateEntry mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 24) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.isScreenOn = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        this.wifiStateCount = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                    this.wifiState = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                boolean bl;
                int n = this.wifiState;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.wifiStateCount) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if (bl = this.isScreenOn) {
                    codedOutputByteBufferNano.writeBool(3, bl);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiNetworkRequestApiLog
    extends MessageNano {
        private static volatile WifiNetworkRequestApiLog[] _emptyArray;
        public HistogramBucketInt32[] networkMatchSizeHistogram;
        public int numApps;
        public int numConnectSuccess;
        public int numRequest;
        public int numUserApprovalBypass;
        public int numUserReject;

        public WifiNetworkRequestApiLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiNetworkRequestApiLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiNetworkRequestApiLog[0];
                return _emptyArray;
            }
        }

        public static WifiNetworkRequestApiLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiNetworkRequestApiLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiNetworkRequestApiLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiNetworkRequestApiLog(), arrby);
        }

        public WifiNetworkRequestApiLog clear() {
            this.numRequest = 0;
            this.networkMatchSizeHistogram = HistogramBucketInt32.emptyArray();
            this.numConnectSuccess = 0;
            this.numUserApprovalBypass = 0;
            this.numUserReject = 0;
            this.numApps = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numRequest;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.networkMatchSizeHistogram;
            n = n3;
            if (object != null) {
                n = n3;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.networkMatchSizeHistogram;
                        n = n3;
                        if (n2 >= ((HistogramBucketInt32[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n2 = this.numConnectSuccess;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.numUserApprovalBypass;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.numUserReject;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            n2 = this.numApps;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(6, n2);
            }
            return n;
        }

        @Override
        public WifiNetworkRequestApiLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                        return this;
                                    }
                                    this.numApps = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                this.numUserReject = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.numUserApprovalBypass = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numConnectSuccess = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    HistogramBucketInt32[] arrhistogramBucketInt32 = this.networkMatchSizeHistogram;
                    n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                    arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.networkMatchSizeHistogram, 0, arrhistogramBucketInt32, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrhistogramBucketInt32.length - 1) {
                        arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                        codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                    codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                    this.networkMatchSizeHistogram = arrhistogramBucketInt32;
                    continue;
                }
                this.numRequest = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numRequest;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.networkMatchSizeHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.networkMatchSizeHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((n = this.numConnectSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numUserApprovalBypass) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.numUserReject) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.numApps) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiNetworkSuggestionApiLog
    extends MessageNano {
        private static volatile WifiNetworkSuggestionApiLog[] _emptyArray;
        public HistogramBucketInt32[] networkListSizeHistogram;
        public int numConnectFailure;
        public int numConnectSuccess;
        public int numModification;

        public WifiNetworkSuggestionApiLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiNetworkSuggestionApiLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiNetworkSuggestionApiLog[0];
                return _emptyArray;
            }
        }

        public static WifiNetworkSuggestionApiLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiNetworkSuggestionApiLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiNetworkSuggestionApiLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiNetworkSuggestionApiLog(), arrby);
        }

        public WifiNetworkSuggestionApiLog clear() {
            this.numModification = 0;
            this.numConnectSuccess = 0;
            this.numConnectFailure = 0;
            this.networkListSizeHistogram = HistogramBucketInt32.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numModification;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numConnectSuccess;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.numConnectFailure;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            Object object = this.networkListSizeHistogram;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((HistogramBucketInt32[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.networkListSizeHistogram;
                        n2 = n3;
                        if (n >= ((HistogramBucketInt32[])object).length) break;
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
            return n2;
        }

        @Override
        public WifiNetworkSuggestionApiLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 34) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                            HistogramBucketInt32[] arrhistogramBucketInt32 = this.networkListSizeHistogram;
                            n = arrhistogramBucketInt32 == null ? 0 : arrhistogramBucketInt32.length;
                            arrhistogramBucketInt32 = new HistogramBucketInt32[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.networkListSizeHistogram, 0, arrhistogramBucketInt32, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrhistogramBucketInt32.length - 1) {
                                arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                                codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                                codedInputByteBufferNano.readTag();
                                ++n2;
                            }
                            arrhistogramBucketInt32[n2] = new HistogramBucketInt32();
                            codedInputByteBufferNano.readMessage(arrhistogramBucketInt32[n2]);
                            this.networkListSizeHistogram = arrhistogramBucketInt32;
                            continue;
                        }
                        this.numConnectFailure = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numConnectSuccess = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numModification = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numModification;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numConnectSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numConnectFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((object = this.networkListSizeHistogram) != null && ((HistogramBucketInt32[])object).length > 0) {
                for (n = 0; n < ((HistogramBucketInt32[])(object = this.networkListSizeHistogram)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiP2pStats
    extends MessageNano {
        private static volatile WifiP2pStats[] _emptyArray;
        public P2pConnectionEvent[] connectionEvent;
        public GroupEvent[] groupEvent;
        public int numPersistentGroup;
        public int numTotalPeerScans;
        public int numTotalServiceScans;

        public WifiP2pStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiP2pStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiP2pStats[0];
                return _emptyArray;
            }
        }

        public static WifiP2pStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiP2pStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiP2pStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiP2pStats(), arrby);
        }

        public WifiP2pStats clear() {
            this.groupEvent = GroupEvent.emptyArray();
            this.connectionEvent = P2pConnectionEvent.emptyArray();
            this.numPersistentGroup = 0;
            this.numTotalPeerScans = 0;
            this.numTotalServiceScans = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            Object object = this.groupEvent;
            int n3 = n2;
            if (object != null) {
                n3 = n2;
                if (((GroupEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.groupEvent;
                        n3 = n2;
                        if (n >= ((MessageNano[])object).length) break;
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
            object = this.connectionEvent;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((MessageNano[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.connectionEvent;
                        n2 = n3;
                        if (n >= ((MessageNano[])object).length) break;
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
            n3 = this.numPersistentGroup;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n2 = this.numTotalPeerScans;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n = this.numTotalServiceScans;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n);
            }
            return n2;
        }

        @Override
        public WifiP2pStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                MessageNano[] arrmessageNano;
                int n2;
                if (n != 10) {
                    if (n != 18) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                this.numTotalServiceScans = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.numTotalPeerScans = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numPersistentGroup = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    arrmessageNano = this.connectionEvent;
                    n = arrmessageNano == null ? 0 : arrmessageNano.length;
                    arrmessageNano = new P2pConnectionEvent[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.connectionEvent, 0, arrmessageNano, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrmessageNano.length - 1) {
                        arrmessageNano[n2] = new P2pConnectionEvent();
                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrmessageNano[n2] = new P2pConnectionEvent();
                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                    this.connectionEvent = arrmessageNano;
                    continue;
                }
                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                arrmessageNano = this.groupEvent;
                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                arrmessageNano = new GroupEvent[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.groupEvent, 0, arrmessageNano, 0, n);
                    n2 = n;
                }
                while (n2 < arrmessageNano.length - 1) {
                    arrmessageNano[n2] = new GroupEvent();
                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arrmessageNano[n2] = new GroupEvent();
                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                this.groupEvent = arrmessageNano;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.groupEvent;
            if (object != null && ((GroupEvent[])object).length > 0) {
                for (n = 0; n < ((MessageNano[])(object = this.groupEvent)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((object = this.connectionEvent) != null && ((MessageNano[])object).length > 0) {
                for (n = 0; n < ((MessageNano[])(object = this.connectionEvent)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((n = this.numPersistentGroup) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numTotalPeerScans) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.numTotalServiceScans) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiPowerStats
    extends MessageNano {
        private static volatile WifiPowerStats[] _emptyArray;
        public double energyConsumedMah;
        public long idleTimeMs;
        public long loggingDurationMs;
        public double monitoredRailEnergyConsumedMah;
        public long numBytesRx;
        public long numBytesTx;
        public long numPacketsRx;
        public long numPacketsTx;
        public long rxTimeMs;
        public long scanTimeMs;
        public long sleepTimeMs;
        public long txTimeMs;
        public long wifiKernelActiveTimeMs;

        public WifiPowerStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiPowerStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiPowerStats[0];
                return _emptyArray;
            }
        }

        public static WifiPowerStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiPowerStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiPowerStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiPowerStats(), arrby);
        }

        public WifiPowerStats clear() {
            this.loggingDurationMs = 0L;
            this.energyConsumedMah = 0.0;
            this.idleTimeMs = 0L;
            this.rxTimeMs = 0L;
            this.txTimeMs = 0L;
            this.wifiKernelActiveTimeMs = 0L;
            this.numPacketsTx = 0L;
            this.numBytesTx = 0L;
            this.numPacketsRx = 0L;
            this.numBytesRx = 0L;
            this.sleepTimeMs = 0L;
            this.scanTimeMs = 0L;
            this.monitoredRailEnergyConsumedMah = 0.0;
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
            l = this.idleTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.rxTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.txTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.wifiKernelActiveTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.numPacketsTx;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.numBytesTx;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            l = this.numPacketsRx;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(9, l);
            }
            l = this.numBytesRx;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            l = this.sleepTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(11, l);
            }
            l = this.scanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(12, l);
            }
            n2 = n;
            if (Double.doubleToLongBits(this.monitoredRailEnergyConsumedMah) != Double.doubleToLongBits(0.0)) {
                n2 = n + CodedOutputByteBufferNano.computeDoubleSize(13, this.monitoredRailEnergyConsumedMah);
            }
            return n2;
        }

        @Override
        public WifiPowerStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 105: {
                        this.monitoredRailEnergyConsumedMah = codedInputByteBufferNano.readDouble();
                        continue block16;
                    }
                    case 96: {
                        this.scanTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 88: {
                        this.sleepTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 80: {
                        this.numBytesRx = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 72: {
                        this.numPacketsRx = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 64: {
                        this.numBytesTx = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 56: {
                        this.numPacketsTx = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 48: {
                        this.wifiKernelActiveTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 40: {
                        this.txTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 32: {
                        this.rxTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 24: {
                        this.idleTimeMs = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 17: {
                        this.energyConsumedMah = codedInputByteBufferNano.readDouble();
                        continue block16;
                    }
                    case 8: {
                        this.loggingDurationMs = codedInputByteBufferNano.readInt64();
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
            long l = this.loggingDurationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(2, this.energyConsumedMah);
            }
            if ((l = this.idleTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.rxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.txTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.wifiKernelActiveTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.numPacketsTx) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.numBytesTx) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((l = this.numPacketsRx) != 0L) {
                codedOutputByteBufferNano.writeInt64(9, l);
            }
            if ((l = this.numBytesRx) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            if ((l = this.sleepTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(11, l);
            }
            if ((l = this.scanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(12, l);
            }
            if (Double.doubleToLongBits(this.monitoredRailEnergyConsumedMah) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(13, this.monitoredRailEnergyConsumedMah);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiRadioUsage
    extends MessageNano {
        private static volatile WifiRadioUsage[] _emptyArray;
        public long loggingDurationMs;
        public long scanTimeMs;

        public WifiRadioUsage() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiRadioUsage[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiRadioUsage[0];
                return _emptyArray;
            }
        }

        public static WifiRadioUsage parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiRadioUsage().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiRadioUsage parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiRadioUsage(), arrby);
        }

        public WifiRadioUsage clear() {
            this.loggingDurationMs = 0L;
            this.scanTimeMs = 0L;
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
            l = this.scanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            return n;
        }

        @Override
        public WifiRadioUsage mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.scanTimeMs = codedInputByteBufferNano.readInt64();
                    continue;
                }
                this.loggingDurationMs = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l = this.loggingDurationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.scanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiRttLog
    extends MessageNano {
        public static final int ABORTED = 9;
        public static final int FAILURE = 2;
        public static final int FAIL_AP_ON_DIFF_CHANNEL = 7;
        public static final int FAIL_BUSY_TRY_LATER = 13;
        public static final int FAIL_FTM_PARAM_OVERRIDE = 16;
        public static final int FAIL_INVALID_TS = 10;
        public static final int FAIL_NOT_SCHEDULED_YET = 5;
        public static final int FAIL_NO_CAPABILITY = 8;
        public static final int FAIL_NO_RSP = 3;
        public static final int FAIL_PROTOCOL = 11;
        public static final int FAIL_REJECTED = 4;
        public static final int FAIL_SCHEDULE = 12;
        public static final int FAIL_TM_TIMEOUT = 6;
        public static final int INVALID_REQ = 14;
        public static final int MISSING_RESULT = 17;
        public static final int NO_WIFI = 15;
        public static final int OVERALL_AWARE_TRANSLATION_FAILURE = 7;
        public static final int OVERALL_FAIL = 2;
        public static final int OVERALL_HAL_FAILURE = 6;
        public static final int OVERALL_LOCATION_PERMISSION_MISSING = 8;
        public static final int OVERALL_RTT_NOT_AVAILABLE = 3;
        public static final int OVERALL_SUCCESS = 1;
        public static final int OVERALL_THROTTLE = 5;
        public static final int OVERALL_TIMEOUT = 4;
        public static final int OVERALL_UNKNOWN = 0;
        public static final int SUCCESS = 1;
        public static final int UNKNOWN = 0;
        private static volatile WifiRttLog[] _emptyArray;
        public RttOverallStatusHistogramBucket[] histogramOverallStatus;
        public int numRequests;
        public RttToPeerLog rttToAp;
        public RttToPeerLog rttToAware;

        public WifiRttLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiRttLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiRttLog[0];
                return _emptyArray;
            }
        }

        public static WifiRttLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiRttLog().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiRttLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiRttLog(), arrby);
        }

        public WifiRttLog clear() {
            this.numRequests = 0;
            this.histogramOverallStatus = RttOverallStatusHistogramBucket.emptyArray();
            this.rttToAp = null;
            this.rttToAware = null;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numRequests;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.histogramOverallStatus;
            n = n3;
            if (object != null) {
                n = n3;
                if (((RttOverallStatusHistogramBucket[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.histogramOverallStatus;
                        n = n3;
                        if (n2 >= ((RttOverallStatusHistogramBucket[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            object = this.rttToAp;
            n3 = n;
            if (object != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)object);
            }
            object = this.rttToAware;
            n = n3;
            if (object != null) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
            }
            return n;
        }

        @Override
        public WifiRttLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 34) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            if (this.rttToAware == null) {
                                this.rttToAware = new RttToPeerLog();
                            }
                            codedInputByteBufferNano.readMessage(this.rttToAware);
                            continue;
                        }
                        if (this.rttToAp == null) {
                            this.rttToAp = new RttToPeerLog();
                        }
                        codedInputByteBufferNano.readMessage(this.rttToAp);
                        continue;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    RttOverallStatusHistogramBucket[] arrrttOverallStatusHistogramBucket = this.histogramOverallStatus;
                    n = arrrttOverallStatusHistogramBucket == null ? 0 : arrrttOverallStatusHistogramBucket.length;
                    arrrttOverallStatusHistogramBucket = new RttOverallStatusHistogramBucket[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.histogramOverallStatus, 0, arrrttOverallStatusHistogramBucket, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrrttOverallStatusHistogramBucket.length - 1) {
                        arrrttOverallStatusHistogramBucket[n2] = new RttOverallStatusHistogramBucket();
                        codedInputByteBufferNano.readMessage(arrrttOverallStatusHistogramBucket[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrrttOverallStatusHistogramBucket[n2] = new RttOverallStatusHistogramBucket();
                    codedInputByteBufferNano.readMessage(arrrttOverallStatusHistogramBucket[n2]);
                    this.histogramOverallStatus = arrrttOverallStatusHistogramBucket;
                    continue;
                }
                this.numRequests = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numRequests;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.histogramOverallStatus) != null && ((RttOverallStatusHistogramBucket[])object).length > 0) {
                for (n = 0; n < ((RttOverallStatusHistogramBucket[])(object = this.histogramOverallStatus)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((object = this.rttToAp) != null) {
                codedOutputByteBufferNano.writeMessage(3, (MessageNano)object);
            }
            if ((object = this.rttToAware) != null) {
                codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class HistogramBucket
        extends MessageNano {
            private static volatile HistogramBucket[] _emptyArray;
            public int count;
            public long end;
            public long start;

            public HistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static HistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new HistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static HistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new HistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static HistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new HistogramBucket(), arrby);
            }

            public HistogramBucket clear() {
                this.start = 0L;
                this.end = 0L;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                long l = this.start;
                int n2 = n;
                if (l != 0L) {
                    n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
                }
                l = this.end;
                n = n2;
                if (l != 0L) {
                    n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
                }
                int n3 = this.count;
                n2 = n;
                if (n3 != 0) {
                    n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
                }
                return n2;
            }

            @Override
            public HistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 24) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.count = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.end = codedInputByteBufferNano.readInt64();
                        continue;
                    }
                    this.start = codedInputByteBufferNano.readInt64();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n;
                long l = this.start;
                if (l != 0L) {
                    codedOutputByteBufferNano.writeInt64(1, l);
                }
                if ((l = this.end) != 0L) {
                    codedOutputByteBufferNano.writeInt64(2, l);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(3, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class RttIndividualStatusHistogramBucket
        extends MessageNano {
            private static volatile RttIndividualStatusHistogramBucket[] _emptyArray;
            public int count;
            public int statusType;

            public RttIndividualStatusHistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RttIndividualStatusHistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RttIndividualStatusHistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static RttIndividualStatusHistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RttIndividualStatusHistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static RttIndividualStatusHistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RttIndividualStatusHistogramBucket(), arrby);
            }

            public RttIndividualStatusHistogramBucket clear() {
                this.statusType = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.statusType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public RttIndividualStatusHistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    switch (n) {
                        default: {
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
                        case 11: 
                        case 12: 
                        case 13: 
                        case 14: 
                        case 15: 
                        case 16: 
                        case 17: 
                    }
                    this.statusType = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.statusType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class RttOverallStatusHistogramBucket
        extends MessageNano {
            private static volatile RttOverallStatusHistogramBucket[] _emptyArray;
            public int count;
            public int statusType;

            public RttOverallStatusHistogramBucket() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RttOverallStatusHistogramBucket[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RttOverallStatusHistogramBucket[0];
                    return _emptyArray;
                }
            }

            public static RttOverallStatusHistogramBucket parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RttOverallStatusHistogramBucket().mergeFrom(codedInputByteBufferNano);
            }

            public static RttOverallStatusHistogramBucket parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RttOverallStatusHistogramBucket(), arrby);
            }

            public RttOverallStatusHistogramBucket clear() {
                this.statusType = 0;
                this.count = 0;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.statusType;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.count;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                return n;
            }

            @Override
            public RttOverallStatusHistogramBucket mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                block3 : while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.count = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    switch (n) {
                        default: {
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
                    }
                    this.statusType = n;
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                int n = this.statusType;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.count) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

        public static final class RttToPeerLog
        extends MessageNano {
            private static volatile RttToPeerLog[] _emptyArray;
            public HistogramBucket[] histogramDistance;
            public RttIndividualStatusHistogramBucket[] histogramIndividualStatus;
            public HistogramBucket[] histogramNumPeersPerRequest;
            public HistogramBucket[] histogramNumRequestsPerApp;
            public HistogramBucket[] histogramRequestIntervalMs;
            public int numApps;
            public int numIndividualRequests;
            public int numRequests;

            public RttToPeerLog() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static RttToPeerLog[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new RttToPeerLog[0];
                    return _emptyArray;
                }
            }

            public static RttToPeerLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new RttToPeerLog().mergeFrom(codedInputByteBufferNano);
            }

            public static RttToPeerLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new RttToPeerLog(), arrby);
            }

            public RttToPeerLog clear() {
                this.numRequests = 0;
                this.numIndividualRequests = 0;
                this.numApps = 0;
                this.histogramNumRequestsPerApp = HistogramBucket.emptyArray();
                this.histogramNumPeersPerRequest = HistogramBucket.emptyArray();
                this.histogramIndividualStatus = RttIndividualStatusHistogramBucket.emptyArray();
                this.histogramDistance = HistogramBucket.emptyArray();
                this.histogramRequestIntervalMs = HistogramBucket.emptyArray();
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                int n2 = this.numRequests;
                int n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
                }
                n2 = this.numIndividualRequests;
                n = n3;
                if (n2 != 0) {
                    n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
                }
                n2 = this.numApps;
                n3 = n;
                if (n2 != 0) {
                    n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
                }
                Object object = this.histogramNumRequestsPerApp;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((HistogramBucket[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.histogramNumRequestsPerApp;
                            n = n3;
                            if (n2 >= ((MessageNano[])object).length) break;
                            object = object[n2];
                            n = n3;
                            if (object != null) {
                                n = n3 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                            }
                            ++n2;
                            n3 = n;
                        } while (true);
                    }
                }
                object = this.histogramNumPeersPerRequest;
                n3 = n;
                if (object != null) {
                    n3 = n;
                    if (((HistogramBucket[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.histogramNumPeersPerRequest;
                            n3 = n;
                            if (n2 >= ((MessageNano[])object).length) break;
                            object = object[n2];
                            n3 = n;
                            if (object != null) {
                                n3 = n + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)object);
                            }
                            ++n2;
                            n = n3;
                        } while (true);
                    }
                }
                object = this.histogramIndividualStatus;
                n = n3;
                if (object != null) {
                    n = n3;
                    if (((MessageNano[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.histogramIndividualStatus;
                            n = n3;
                            if (n2 >= ((MessageNano[])object).length) break;
                            object = object[n2];
                            n = n3;
                            if (object != null) {
                                n = n3 + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)object);
                            }
                            ++n2;
                            n3 = n;
                        } while (true);
                    }
                }
                object = this.histogramDistance;
                n3 = n;
                if (object != null) {
                    n3 = n;
                    if (((MessageNano[])object).length > 0) {
                        n2 = 0;
                        do {
                            object = this.histogramDistance;
                            n3 = n;
                            if (n2 >= ((MessageNano[])object).length) break;
                            object = object[n2];
                            n3 = n;
                            if (object != null) {
                                n3 = n + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                            }
                            ++n2;
                            n = n3;
                        } while (true);
                    }
                }
                object = this.histogramRequestIntervalMs;
                n2 = n3;
                if (object != null) {
                    n2 = n3;
                    if (((MessageNano[])object).length > 0) {
                        n = 0;
                        do {
                            object = this.histogramRequestIntervalMs;
                            n2 = n3;
                            if (n >= ((MessageNano[])object).length) break;
                            object = object[n];
                            n2 = n3;
                            if (object != null) {
                                n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)object);
                            }
                            ++n;
                            n3 = n2;
                        } while (true);
                    }
                }
                return n2;
            }

            @Override
            public RttToPeerLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 24) {
                                MessageNano[] arrmessageNano;
                                int n2;
                                if (n != 34) {
                                    if (n != 42) {
                                        if (n != 50) {
                                            if (n != 58) {
                                                if (n != 66) {
                                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                    return this;
                                                }
                                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                                                arrmessageNano = this.histogramRequestIntervalMs;
                                                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                                arrmessageNano = new HistogramBucket[n + n2];
                                                n2 = n;
                                                if (n != 0) {
                                                    System.arraycopy(this.histogramRequestIntervalMs, 0, arrmessageNano, 0, n);
                                                    n2 = n;
                                                }
                                                while (n2 < arrmessageNano.length - 1) {
                                                    arrmessageNano[n2] = new HistogramBucket();
                                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                    codedInputByteBufferNano.readTag();
                                                    ++n2;
                                                }
                                                arrmessageNano[n2] = new HistogramBucket();
                                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                this.histogramRequestIntervalMs = arrmessageNano;
                                                continue;
                                            }
                                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                                            arrmessageNano = this.histogramDistance;
                                            n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                            arrmessageNano = new HistogramBucket[n + n2];
                                            n2 = n;
                                            if (n != 0) {
                                                System.arraycopy(this.histogramDistance, 0, arrmessageNano, 0, n);
                                                n2 = n;
                                            }
                                            while (n2 < arrmessageNano.length - 1) {
                                                arrmessageNano[n2] = new HistogramBucket();
                                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                                codedInputByteBufferNano.readTag();
                                                ++n2;
                                            }
                                            arrmessageNano[n2] = new HistogramBucket();
                                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                            this.histogramDistance = arrmessageNano;
                                            continue;
                                        }
                                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 50);
                                        arrmessageNano = this.histogramIndividualStatus;
                                        n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                        arrmessageNano = new RttIndividualStatusHistogramBucket[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.histogramIndividualStatus, 0, arrmessageNano, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrmessageNano.length - 1) {
                                            arrmessageNano[n2] = new RttIndividualStatusHistogramBucket();
                                            codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                            codedInputByteBufferNano.readTag();
                                            ++n2;
                                        }
                                        arrmessageNano[n2] = new RttIndividualStatusHistogramBucket();
                                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                        this.histogramIndividualStatus = arrmessageNano;
                                        continue;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 42);
                                    arrmessageNano = this.histogramNumPeersPerRequest;
                                    n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                    arrmessageNano = new HistogramBucket[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.histogramNumPeersPerRequest, 0, arrmessageNano, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrmessageNano.length - 1) {
                                        arrmessageNano[n2] = new HistogramBucket();
                                        codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrmessageNano[n2] = new HistogramBucket();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    this.histogramNumPeersPerRequest = arrmessageNano;
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                                arrmessageNano = this.histogramNumRequestsPerApp;
                                n = arrmessageNano == null ? 0 : arrmessageNano.length;
                                arrmessageNano = new HistogramBucket[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.histogramNumRequestsPerApp, 0, arrmessageNano, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrmessageNano.length - 1) {
                                    arrmessageNano[n2] = new HistogramBucket();
                                    codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrmessageNano[n2] = new HistogramBucket();
                                codedInputByteBufferNano.readMessage(arrmessageNano[n2]);
                                this.histogramNumRequestsPerApp = arrmessageNano;
                                continue;
                            }
                            this.numApps = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numIndividualRequests = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numRequests = codedInputByteBufferNano.readInt32();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                Object object;
                int n = this.numRequests;
                if (n != 0) {
                    codedOutputByteBufferNano.writeInt32(1, n);
                }
                if ((n = this.numIndividualRequests) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((n = this.numApps) != 0) {
                    codedOutputByteBufferNano.writeInt32(3, n);
                }
                if ((object = this.histogramNumRequestsPerApp) != null && ((HistogramBucket[])object).length > 0) {
                    for (n = 0; n < ((MessageNano[])(object = this.histogramNumRequestsPerApp)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                    }
                }
                if ((object = this.histogramNumPeersPerRequest) != null && ((HistogramBucket[])object).length > 0) {
                    for (n = 0; n < ((MessageNano[])(object = this.histogramNumPeersPerRequest)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(5, (MessageNano)object);
                    }
                }
                if ((object = this.histogramIndividualStatus) != null && ((MessageNano[])object).length > 0) {
                    for (n = 0; n < ((MessageNano[])(object = this.histogramIndividualStatus)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(6, (MessageNano)object);
                    }
                }
                if ((object = this.histogramDistance) != null && ((MessageNano[])object).length > 0) {
                    for (n = 0; n < ((MessageNano[])(object = this.histogramDistance)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                    }
                }
                if ((object = this.histogramRequestIntervalMs) != null && ((MessageNano[])object).length > 0) {
                    for (n = 0; n < ((MessageNano[])(object = this.histogramRequestIntervalMs)).length; ++n) {
                        if ((object = object[n]) == null) continue;
                        codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
                    }
                }
                super.writeTo(codedOutputByteBufferNano);
            }
        }

    }

    public static final class WifiScoreCount
    extends MessageNano {
        private static volatile WifiScoreCount[] _emptyArray;
        public int count;
        public int score;

        public WifiScoreCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiScoreCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiScoreCount[0];
                return _emptyArray;
            }
        }

        public static WifiScoreCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiScoreCount().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiScoreCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiScoreCount(), arrby);
        }

        public WifiScoreCount clear() {
            this.score = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.score;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public WifiScoreCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.score = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.score;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiToggleStats
    extends MessageNano {
        private static volatile WifiToggleStats[] _emptyArray;
        public int numToggleOffNormal;
        public int numToggleOffPrivileged;
        public int numToggleOnNormal;
        public int numToggleOnPrivileged;

        public WifiToggleStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiToggleStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiToggleStats[0];
                return _emptyArray;
            }
        }

        public static WifiToggleStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiToggleStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiToggleStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiToggleStats(), arrby);
        }

        public WifiToggleStats clear() {
            this.numToggleOnPrivileged = 0;
            this.numToggleOffPrivileged = 0;
            this.numToggleOnNormal = 0;
            this.numToggleOffNormal = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numToggleOnPrivileged;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numToggleOffPrivileged;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.numToggleOnNormal;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.numToggleOffNormal;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            return n;
        }

        @Override
        public WifiToggleStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.numToggleOffNormal = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numToggleOnNormal = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numToggleOffPrivileged = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numToggleOnPrivileged = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.numToggleOnPrivileged;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numToggleOffPrivileged) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numToggleOnNormal) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numToggleOffNormal) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiUsabilityScoreCount
    extends MessageNano {
        private static volatile WifiUsabilityScoreCount[] _emptyArray;
        public int count;
        public int score;

        public WifiUsabilityScoreCount() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiUsabilityScoreCount[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiUsabilityScoreCount[0];
                return _emptyArray;
            }
        }

        public static WifiUsabilityScoreCount parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiUsabilityScoreCount().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiUsabilityScoreCount parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiUsabilityScoreCount(), arrby);
        }

        public WifiUsabilityScoreCount clear() {
            this.score = 0;
            this.count = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.score;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.count;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public WifiUsabilityScoreCount mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.count = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.score = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.score;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.count) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiUsabilityStats
    extends MessageNano {
        public static final int LABEL_BAD = 2;
        public static final int LABEL_GOOD = 1;
        public static final int LABEL_UNKNOWN = 0;
        public static final int TYPE_DATA_STALL_BAD_TX = 1;
        public static final int TYPE_DATA_STALL_BOTH = 3;
        public static final int TYPE_DATA_STALL_TX_WITHOUT_RX = 2;
        public static final int TYPE_FIRMWARE_ALERT = 4;
        public static final int TYPE_IP_REACHABILITY_LOST = 5;
        public static final int TYPE_UNKNOWN = 0;
        private static volatile WifiUsabilityStats[] _emptyArray;
        public int firmwareAlertCode;
        public int label;
        public WifiUsabilityStatsEntry[] stats;
        public long timeStampMs;
        public int triggerType;

        public WifiUsabilityStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiUsabilityStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiUsabilityStats[0];
                return _emptyArray;
            }
        }

        public static WifiUsabilityStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiUsabilityStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiUsabilityStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiUsabilityStats(), arrby);
        }

        public WifiUsabilityStats clear() {
            this.label = 0;
            this.stats = WifiUsabilityStatsEntry.emptyArray();
            this.triggerType = 0;
            this.firmwareAlertCode = -1;
            this.timeStampMs = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.label;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.stats;
            n = n3;
            if (object != null) {
                n = n3;
                if (((WifiUsabilityStatsEntry[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.stats;
                        n = n3;
                        if (n2 >= ((WifiUsabilityStatsEntry[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n2 = this.triggerType;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.firmwareAlertCode;
            n = n3;
            if (n2 != -1) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            long l = this.timeStampMs;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            return n3;
        }

        @Override
        public WifiUsabilityStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                    return this;
                                }
                                this.timeStampMs = codedInputByteBufferNano.readInt64();
                                continue;
                            }
                            this.firmwareAlertCode = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5) continue;
                        this.triggerType = n;
                        continue;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    WifiUsabilityStatsEntry[] arrwifiUsabilityStatsEntry = this.stats;
                    n = arrwifiUsabilityStatsEntry == null ? 0 : arrwifiUsabilityStatsEntry.length;
                    arrwifiUsabilityStatsEntry = new WifiUsabilityStatsEntry[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.stats, 0, arrwifiUsabilityStatsEntry, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrwifiUsabilityStatsEntry.length - 1) {
                        arrwifiUsabilityStatsEntry[n2] = new WifiUsabilityStatsEntry();
                        codedInputByteBufferNano.readMessage(arrwifiUsabilityStatsEntry[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrwifiUsabilityStatsEntry[n2] = new WifiUsabilityStatsEntry();
                    codedInputByteBufferNano.readMessage(arrwifiUsabilityStatsEntry[n2]);
                    this.stats = arrwifiUsabilityStatsEntry;
                    continue;
                }
                n = codedInputByteBufferNano.readInt32();
                if (n != 0 && n != 1 && n != 2) continue;
                this.label = n;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            long l;
            int n = this.label;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.stats) != null && ((WifiUsabilityStatsEntry[])object).length > 0) {
                for (n = 0; n < ((WifiUsabilityStatsEntry[])(object = this.stats)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((n = this.triggerType) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.firmwareAlertCode) != -1) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((l = this.timeStampMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiUsabilityStatsEntry
    extends MessageNano {
        public static final int NETWORK_TYPE_CDMA = 2;
        public static final int NETWORK_TYPE_EVDO_0 = 3;
        public static final int NETWORK_TYPE_GSM = 1;
        public static final int NETWORK_TYPE_LTE = 6;
        public static final int NETWORK_TYPE_NR = 7;
        public static final int NETWORK_TYPE_TD_SCDMA = 5;
        public static final int NETWORK_TYPE_UMTS = 4;
        public static final int NETWORK_TYPE_UNKNOWN = 0;
        public static final int PROBE_STATUS_FAILURE = 3;
        public static final int PROBE_STATUS_NO_PROBE = 1;
        public static final int PROBE_STATUS_SUCCESS = 2;
        public static final int PROBE_STATUS_UNKNOWN = 0;
        private static volatile WifiUsabilityStatsEntry[] _emptyArray;
        public int cellularDataNetworkType;
        public int cellularSignalStrengthDb;
        public int cellularSignalStrengthDbm;
        public int deviceMobilityState;
        public boolean isSameBssidAndFreq;
        public boolean isSameRegisteredCell;
        public int linkSpeedMbps;
        public int predictionHorizonSec;
        public int probeElapsedTimeSinceLastUpdateMs;
        public int probeMcsRateSinceLastUpdate;
        public int probeStatusSinceLastUpdate;
        public int rssi;
        public int rxLinkSpeedMbps;
        public int seqNumInsideFramework;
        public int seqNumToFramework;
        public long timeStampMs;
        public long totalBackgroundScanTimeMs;
        public long totalBeaconRx;
        public long totalCcaBusyFreqTimeMs;
        public long totalHotspot2ScanTimeMs;
        public long totalNanScanTimeMs;
        public long totalPnoScanTimeMs;
        public long totalRadioOnFreqTimeMs;
        public long totalRadioOnTimeMs;
        public long totalRadioRxTimeMs;
        public long totalRadioTxTimeMs;
        public long totalRoamScanTimeMs;
        public long totalRxSuccess;
        public long totalScanTimeMs;
        public long totalTxBad;
        public long totalTxRetries;
        public long totalTxSuccess;
        public int wifiScore;
        public int wifiUsabilityScore;

        public WifiUsabilityStatsEntry() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiUsabilityStatsEntry[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiUsabilityStatsEntry[0];
                return _emptyArray;
            }
        }

        public static WifiUsabilityStatsEntry parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiUsabilityStatsEntry().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiUsabilityStatsEntry parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiUsabilityStatsEntry(), arrby);
        }

        public WifiUsabilityStatsEntry clear() {
            this.timeStampMs = 0L;
            this.rssi = 0;
            this.linkSpeedMbps = 0;
            this.totalTxSuccess = 0L;
            this.totalTxRetries = 0L;
            this.totalTxBad = 0L;
            this.totalRxSuccess = 0L;
            this.totalRadioOnTimeMs = 0L;
            this.totalRadioTxTimeMs = 0L;
            this.totalRadioRxTimeMs = 0L;
            this.totalScanTimeMs = 0L;
            this.totalNanScanTimeMs = 0L;
            this.totalBackgroundScanTimeMs = 0L;
            this.totalRoamScanTimeMs = 0L;
            this.totalPnoScanTimeMs = 0L;
            this.totalHotspot2ScanTimeMs = 0L;
            this.wifiScore = 0;
            this.wifiUsabilityScore = 0;
            this.seqNumToFramework = 0;
            this.totalCcaBusyFreqTimeMs = 0L;
            this.totalRadioOnFreqTimeMs = 0L;
            this.totalBeaconRx = 0L;
            this.predictionHorizonSec = 0;
            this.probeStatusSinceLastUpdate = 0;
            this.probeElapsedTimeSinceLastUpdateMs = 0;
            this.probeMcsRateSinceLastUpdate = 0;
            this.rxLinkSpeedMbps = 0;
            this.seqNumInsideFramework = 0;
            this.isSameBssidAndFreq = false;
            this.cellularDataNetworkType = 0;
            this.cellularSignalStrengthDbm = 0;
            this.cellularSignalStrengthDb = 0;
            this.isSameRegisteredCell = false;
            this.deviceMobilityState = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.timeStampMs;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.rssi;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.linkSpeedMbps;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            l = this.totalTxSuccess;
            n3 = n2;
            if (l != 0L) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.totalTxRetries;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.totalTxBad;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.totalRxSuccess;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.totalRadioOnTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            l = this.totalRadioTxTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(9, l);
            }
            l = this.totalRadioRxTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            l = this.totalScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(11, l);
            }
            l = this.totalNanScanTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(12, l);
            }
            l = this.totalBackgroundScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(13, l);
            }
            l = this.totalRoamScanTimeMs;
            n3 = n;
            if (l != 0L) {
                n3 = n + CodedOutputByteBufferNano.computeInt64Size(14, l);
            }
            l = this.totalPnoScanTimeMs;
            n2 = n3;
            if (l != 0L) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt64Size(15, l);
            }
            l = this.totalHotspot2ScanTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(16, l);
            }
            n3 = this.wifiScore;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(17, n3);
            }
            n3 = this.wifiUsabilityScore;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(18, n3);
            }
            n3 = this.seqNumToFramework;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(19, n3);
            }
            l = this.totalCcaBusyFreqTimeMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(20, l);
            }
            l = this.totalRadioOnFreqTimeMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(21, l);
            }
            l = this.totalBeaconRx;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(22, l);
            }
            n3 = this.predictionHorizonSec;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(23, n3);
            }
            n3 = this.probeStatusSinceLastUpdate;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(24, n3);
            }
            n3 = this.probeElapsedTimeSinceLastUpdateMs;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(25, n3);
            }
            n = this.probeMcsRateSinceLastUpdate;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(26, n);
            }
            n2 = this.rxLinkSpeedMbps;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(27, n2);
            }
            n3 = this.seqNumInsideFramework;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(28, n3);
            }
            boolean bl = this.isSameBssidAndFreq;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(29, bl);
            }
            n3 = this.cellularDataNetworkType;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(30, n3);
            }
            n3 = this.cellularSignalStrengthDbm;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(31, n3);
            }
            n3 = this.cellularSignalStrengthDb;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(32, n3);
            }
            bl = this.isSameRegisteredCell;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(33, bl);
            }
            n3 = this.deviceMobilityState;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(34, n3);
            }
            return n2;
        }

        @Override
        public WifiUsabilityStatsEntry mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block40 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block40;
                        return this;
                    }
                    case 272: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3) continue block40;
                        this.deviceMobilityState = n;
                        break;
                    }
                    case 264: {
                        this.isSameRegisteredCell = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 256: {
                        this.cellularSignalStrengthDb = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 248: {
                        this.cellularSignalStrengthDbm = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 240: {
                        n = codedInputByteBufferNano.readInt32();
                        switch (n) {
                            default: {
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
                        }
                        this.cellularDataNetworkType = n;
                        break;
                    }
                    case 232: {
                        this.isSameBssidAndFreq = codedInputByteBufferNano.readBool();
                        break;
                    }
                    case 224: {
                        this.seqNumInsideFramework = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 216: {
                        this.rxLinkSpeedMbps = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 208: {
                        this.probeMcsRateSinceLastUpdate = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 200: {
                        this.probeElapsedTimeSinceLastUpdateMs = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 192: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3) continue block40;
                        this.probeStatusSinceLastUpdate = n;
                        break;
                    }
                    case 184: {
                        this.predictionHorizonSec = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 176: {
                        this.totalBeaconRx = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 168: {
                        this.totalRadioOnFreqTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 160: {
                        this.totalCcaBusyFreqTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 152: {
                        this.seqNumToFramework = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 144: {
                        this.wifiUsabilityScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 136: {
                        this.wifiScore = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 128: {
                        this.totalHotspot2ScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 120: {
                        this.totalPnoScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 112: {
                        this.totalRoamScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 104: {
                        this.totalBackgroundScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 96: {
                        this.totalNanScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 88: {
                        this.totalScanTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 80: {
                        this.totalRadioRxTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 72: {
                        this.totalRadioTxTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 64: {
                        this.totalRadioOnTimeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 56: {
                        this.totalRxSuccess = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 48: {
                        this.totalTxBad = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 40: {
                        this.totalTxRetries = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 32: {
                        this.totalTxSuccess = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 24: {
                        this.linkSpeedMbps = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 16: {
                        this.rssi = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 8: {
                        this.timeStampMs = codedInputByteBufferNano.readInt64();
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
            boolean bl;
            int n;
            long l = this.timeStampMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.rssi) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.linkSpeedMbps) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((l = this.totalTxSuccess) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.totalTxRetries) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.totalTxBad) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.totalRxSuccess) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.totalRadioOnTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((l = this.totalRadioTxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(9, l);
            }
            if ((l = this.totalRadioRxTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            if ((l = this.totalScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(11, l);
            }
            if ((l = this.totalNanScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(12, l);
            }
            if ((l = this.totalBackgroundScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(13, l);
            }
            if ((l = this.totalRoamScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(14, l);
            }
            if ((l = this.totalPnoScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(15, l);
            }
            if ((l = this.totalHotspot2ScanTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(16, l);
            }
            if ((n = this.wifiScore) != 0) {
                codedOutputByteBufferNano.writeInt32(17, n);
            }
            if ((n = this.wifiUsabilityScore) != 0) {
                codedOutputByteBufferNano.writeInt32(18, n);
            }
            if ((n = this.seqNumToFramework) != 0) {
                codedOutputByteBufferNano.writeInt32(19, n);
            }
            if ((l = this.totalCcaBusyFreqTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(20, l);
            }
            if ((l = this.totalRadioOnFreqTimeMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(21, l);
            }
            if ((l = this.totalBeaconRx) != 0L) {
                codedOutputByteBufferNano.writeInt64(22, l);
            }
            if ((n = this.predictionHorizonSec) != 0) {
                codedOutputByteBufferNano.writeInt32(23, n);
            }
            if ((n = this.probeStatusSinceLastUpdate) != 0) {
                codedOutputByteBufferNano.writeInt32(24, n);
            }
            if ((n = this.probeElapsedTimeSinceLastUpdateMs) != 0) {
                codedOutputByteBufferNano.writeInt32(25, n);
            }
            if ((n = this.probeMcsRateSinceLastUpdate) != 0) {
                codedOutputByteBufferNano.writeInt32(26, n);
            }
            if ((n = this.rxLinkSpeedMbps) != 0) {
                codedOutputByteBufferNano.writeInt32(27, n);
            }
            if ((n = this.seqNumInsideFramework) != 0) {
                codedOutputByteBufferNano.writeInt32(28, n);
            }
            if (bl = this.isSameBssidAndFreq) {
                codedOutputByteBufferNano.writeBool(29, bl);
            }
            if ((n = this.cellularDataNetworkType) != 0) {
                codedOutputByteBufferNano.writeInt32(30, n);
            }
            if ((n = this.cellularSignalStrengthDbm) != 0) {
                codedOutputByteBufferNano.writeInt32(31, n);
            }
            if ((n = this.cellularSignalStrengthDb) != 0) {
                codedOutputByteBufferNano.writeInt32(32, n);
            }
            if (bl = this.isSameRegisteredCell) {
                codedOutputByteBufferNano.writeBool(33, bl);
            }
            if ((n = this.deviceMobilityState) != 0) {
                codedOutputByteBufferNano.writeInt32(34, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WifiWakeStats
    extends MessageNano {
        private static volatile WifiWakeStats[] _emptyArray;
        public int numIgnoredStarts;
        public int numSessions;
        public int numWakeups;
        public Session[] sessions;

        public WifiWakeStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WifiWakeStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WifiWakeStats[0];
                return _emptyArray;
            }
        }

        public static WifiWakeStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WifiWakeStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WifiWakeStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WifiWakeStats(), arrby);
        }

        public WifiWakeStats clear() {
            this.numSessions = 0;
            this.sessions = Session.emptyArray();
            this.numIgnoredStarts = 0;
            this.numWakeups = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numSessions;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            Object object = this.sessions;
            n = n3;
            if (object != null) {
                n = n3;
                if (((Session[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.sessions;
                        n = n3;
                        if (n2 >= ((Session[])object).length) break;
                        object = object[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)object);
                        }
                        ++n2;
                        n3 = n;
                    } while (true);
                }
            }
            n2 = this.numIgnoredStarts;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.numWakeups;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            return n;
        }

        @Override
        public WifiWakeStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 18) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.numWakeups = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numIgnoredStarts = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                    Session[] arrsession = this.sessions;
                    n = arrsession == null ? 0 : arrsession.length;
                    arrsession = new Session[n + n2];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.sessions, 0, arrsession, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrsession.length - 1) {
                        arrsession[n2] = new Session();
                        codedInputByteBufferNano.readMessage(arrsession[n2]);
                        codedInputByteBufferNano.readTag();
                        ++n2;
                    }
                    arrsession[n2] = new Session();
                    codedInputByteBufferNano.readMessage(arrsession[n2]);
                    this.sessions = arrsession;
                    continue;
                }
                this.numSessions = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.numSessions;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((object = this.sessions) != null && ((Session[])object).length > 0) {
                for (n = 0; n < ((Session[])(object = this.sessions)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(2, (MessageNano)object);
                }
            }
            if ((n = this.numIgnoredStarts) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numWakeups) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }

        public static final class Session
        extends MessageNano {
            private static volatile Session[] _emptyArray;
            public Event initializeEvent;
            public int lockedNetworksAtInitialize;
            public int lockedNetworksAtStart;
            public Event resetEvent;
            public long startTimeMillis;
            public Event unlockEvent;
            public Event wakeupEvent;

            public Session() {
                this.clear();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public static Session[] emptyArray() {
                if (_emptyArray != null) return _emptyArray;
                Object object = InternalNano.LAZY_INIT_LOCK;
                synchronized (object) {
                    if (_emptyArray != null) return _emptyArray;
                    _emptyArray = new Session[0];
                    return _emptyArray;
                }
            }

            public static Session parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                return new Session().mergeFrom(codedInputByteBufferNano);
            }

            public static Session parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
                return MessageNano.mergeFrom(new Session(), arrby);
            }

            public Session clear() {
                this.startTimeMillis = 0L;
                this.lockedNetworksAtStart = 0;
                this.lockedNetworksAtInitialize = 0;
                this.initializeEvent = null;
                this.unlockEvent = null;
                this.wakeupEvent = null;
                this.resetEvent = null;
                this.cachedSize = -1;
                return this;
            }

            @Override
            protected int computeSerializedSize() {
                int n = super.computeSerializedSize();
                long l = this.startTimeMillis;
                int n2 = n;
                if (l != 0L) {
                    n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
                }
                int n3 = this.lockedNetworksAtStart;
                n = n2;
                if (n3 != 0) {
                    n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
                }
                Event event = this.unlockEvent;
                n3 = n;
                if (event != null) {
                    n3 = n + CodedOutputByteBufferNano.computeMessageSize(3, event);
                }
                event = this.wakeupEvent;
                n2 = n3;
                if (event != null) {
                    n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(4, event);
                }
                event = this.resetEvent;
                n = n2;
                if (event != null) {
                    n = n2 + CodedOutputByteBufferNano.computeMessageSize(5, event);
                }
                n3 = this.lockedNetworksAtInitialize;
                n2 = n;
                if (n3 != 0) {
                    n2 = n + CodedOutputByteBufferNano.computeInt32Size(6, n3);
                }
                event = this.initializeEvent;
                n = n2;
                if (event != null) {
                    n = n2 + CodedOutputByteBufferNano.computeMessageSize(7, event);
                }
                return n;
            }

            @Override
            public Session mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                int n;
                while ((n = codedInputByteBufferNano.readTag()) != 0) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 26) {
                                if (n != 34) {
                                    if (n != 42) {
                                        if (n != 48) {
                                            if (n != 58) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            if (this.initializeEvent == null) {
                                                this.initializeEvent = new Event();
                                            }
                                            codedInputByteBufferNano.readMessage(this.initializeEvent);
                                            continue;
                                        }
                                        this.lockedNetworksAtInitialize = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    if (this.resetEvent == null) {
                                        this.resetEvent = new Event();
                                    }
                                    codedInputByteBufferNano.readMessage(this.resetEvent);
                                    continue;
                                }
                                if (this.wakeupEvent == null) {
                                    this.wakeupEvent = new Event();
                                }
                                codedInputByteBufferNano.readMessage(this.wakeupEvent);
                                continue;
                            }
                            if (this.unlockEvent == null) {
                                this.unlockEvent = new Event();
                            }
                            codedInputByteBufferNano.readMessage(this.unlockEvent);
                            continue;
                        }
                        this.lockedNetworksAtStart = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.startTimeMillis = codedInputByteBufferNano.readInt64();
                }
                return this;
            }

            @Override
            public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                Event event;
                int n;
                long l = this.startTimeMillis;
                if (l != 0L) {
                    codedOutputByteBufferNano.writeInt64(1, l);
                }
                if ((n = this.lockedNetworksAtStart) != 0) {
                    codedOutputByteBufferNano.writeInt32(2, n);
                }
                if ((event = this.unlockEvent) != null) {
                    codedOutputByteBufferNano.writeMessage(3, event);
                }
                if ((event = this.wakeupEvent) != null) {
                    codedOutputByteBufferNano.writeMessage(4, event);
                }
                if ((event = this.resetEvent) != null) {
                    codedOutputByteBufferNano.writeMessage(5, event);
                }
                if ((n = this.lockedNetworksAtInitialize) != 0) {
                    codedOutputByteBufferNano.writeInt32(6, n);
                }
                if ((event = this.initializeEvent) != null) {
                    codedOutputByteBufferNano.writeMessage(7, event);
                }
                super.writeTo(codedOutputByteBufferNano);
            }

            public static final class Event
            extends MessageNano {
                private static volatile Event[] _emptyArray;
                public int elapsedScans;
                public long elapsedTimeMillis;

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
                    this.elapsedTimeMillis = 0L;
                    this.elapsedScans = 0;
                    this.cachedSize = -1;
                    return this;
                }

                @Override
                protected int computeSerializedSize() {
                    int n = super.computeSerializedSize();
                    long l = this.elapsedTimeMillis;
                    int n2 = n;
                    if (l != 0L) {
                        n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
                    }
                    int n3 = this.elapsedScans;
                    n = n2;
                    if (n3 != 0) {
                        n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
                    }
                    return n;
                }

                @Override
                public Event mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
                    int n;
                    while ((n = codedInputByteBufferNano.readTag()) != 0) {
                        if (n != 8) {
                            if (n != 16) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.elapsedScans = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.elapsedTimeMillis = codedInputByteBufferNano.readInt64();
                    }
                    return this;
                }

                @Override
                public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
                    int n;
                    long l = this.elapsedTimeMillis;
                    if (l != 0L) {
                        codedOutputByteBufferNano.writeInt64(1, l);
                    }
                    if ((n = this.elapsedScans) != 0) {
                        codedOutputByteBufferNano.writeInt32(2, n);
                    }
                    super.writeTo(codedOutputByteBufferNano);
                }
            }

        }

    }

    public static final class WpsMetrics
    extends MessageNano {
        private static volatile WpsMetrics[] _emptyArray;
        public int numWpsAttempts;
        public int numWpsCancellation;
        public int numWpsOtherConnectionFailure;
        public int numWpsOverlapFailure;
        public int numWpsStartFailure;
        public int numWpsSuccess;
        public int numWpsSupplicantFailure;
        public int numWpsTimeoutFailure;

        public WpsMetrics() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WpsMetrics[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WpsMetrics[0];
                return _emptyArray;
            }
        }

        public static WpsMetrics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WpsMetrics().mergeFrom(codedInputByteBufferNano);
        }

        public static WpsMetrics parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WpsMetrics(), arrby);
        }

        public WpsMetrics clear() {
            this.numWpsAttempts = 0;
            this.numWpsSuccess = 0;
            this.numWpsStartFailure = 0;
            this.numWpsOverlapFailure = 0;
            this.numWpsTimeoutFailure = 0;
            this.numWpsOtherConnectionFailure = 0;
            this.numWpsSupplicantFailure = 0;
            this.numWpsCancellation = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numWpsAttempts;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.numWpsSuccess;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n3 = this.numWpsStartFailure;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n = this.numWpsOverlapFailure;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n);
            }
            n = this.numWpsTimeoutFailure;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(5, n);
            }
            n3 = this.numWpsOtherConnectionFailure;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            n2 = this.numWpsSupplicantFailure;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(7, n2);
            }
            n2 = this.numWpsCancellation;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n2);
            }
            return n;
        }

        @Override
        public WpsMetrics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (n != 56) {
                                            if (n != 64) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            this.numWpsCancellation = codedInputByteBufferNano.readInt32();
                                            continue;
                                        }
                                        this.numWpsSupplicantFailure = codedInputByteBufferNano.readInt32();
                                        continue;
                                    }
                                    this.numWpsOtherConnectionFailure = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                this.numWpsTimeoutFailure = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.numWpsOverlapFailure = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.numWpsStartFailure = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.numWpsSuccess = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.numWpsAttempts = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.numWpsAttempts;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.numWpsSuccess) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numWpsStartFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.numWpsOverlapFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.numWpsTimeoutFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.numWpsOtherConnectionFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.numWpsSupplicantFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.numWpsCancellation) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

}

