/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.location.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface GnssLogsProto {

    public static final class GnssLog
    extends MessageNano {
        private static volatile GnssLog[] _emptyArray;
        public String hardwareRevision;
        public int meanPositionAccuracyMeters;
        public int meanTimeToFirstFixSecs;
        public double meanTopFourAverageCn0DbHz;
        public int numLocationReportProcessed;
        public int numPositionAccuracyProcessed;
        public int numTimeToFirstFixProcessed;
        public int numTopFourAverageCn0Processed;
        public int percentageLocationFailure;
        public PowerMetrics powerMetrics;
        public int standardDeviationPositionAccuracyMeters;
        public int standardDeviationTimeToFirstFixSecs;
        public double standardDeviationTopFourAverageCn0DbHz;

        public GnssLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static GnssLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new GnssLog[0];
                return _emptyArray;
            }
        }

        public static GnssLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new GnssLog().mergeFrom(codedInputByteBufferNano);
        }

        public static GnssLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new GnssLog(), arrby);
        }

        public GnssLog clear() {
            this.numLocationReportProcessed = 0;
            this.percentageLocationFailure = 0;
            this.numTimeToFirstFixProcessed = 0;
            this.meanTimeToFirstFixSecs = 0;
            this.standardDeviationTimeToFirstFixSecs = 0;
            this.numPositionAccuracyProcessed = 0;
            this.meanPositionAccuracyMeters = 0;
            this.standardDeviationPositionAccuracyMeters = 0;
            this.numTopFourAverageCn0Processed = 0;
            this.meanTopFourAverageCn0DbHz = 0.0;
            this.standardDeviationTopFourAverageCn0DbHz = 0.0;
            this.powerMetrics = null;
            this.hardwareRevision = "";
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.numLocationReportProcessed;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.percentageLocationFailure;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.numTimeToFirstFixProcessed;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.meanTimeToFirstFixSecs;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.standardDeviationTimeToFirstFixSecs;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            n2 = this.numPositionAccuracyProcessed;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(6, n2);
            }
            n2 = this.meanPositionAccuracyMeters;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(7, n2);
            }
            n2 = this.standardDeviationPositionAccuracyMeters;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n2);
            }
            n2 = this.numTopFourAverageCn0Processed;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(9, n2);
            }
            n2 = n3;
            if (Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != Double.doubleToLongBits(0.0)) {
                n2 = n3 + CodedOutputByteBufferNano.computeDoubleSize(10, this.meanTopFourAverageCn0DbHz);
            }
            n = n2;
            if (Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != Double.doubleToLongBits(0.0)) {
                n = n2 + CodedOutputByteBufferNano.computeDoubleSize(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            PowerMetrics powerMetrics = this.powerMetrics;
            n3 = n;
            if (powerMetrics != null) {
                n3 = n + CodedOutputByteBufferNano.computeMessageSize(12, powerMetrics);
            }
            n = n3;
            if (!this.hardwareRevision.equals("")) {
                n = n3 + CodedOutputByteBufferNano.computeStringSize(13, this.hardwareRevision);
            }
            return n;
        }

        @Override
        public GnssLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 106: {
                        this.hardwareRevision = codedInputByteBufferNano.readString();
                        continue block16;
                    }
                    case 98: {
                        if (this.powerMetrics == null) {
                            this.powerMetrics = new PowerMetrics();
                        }
                        codedInputByteBufferNano.readMessage(this.powerMetrics);
                        continue block16;
                    }
                    case 89: {
                        this.standardDeviationTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        continue block16;
                    }
                    case 81: {
                        this.meanTopFourAverageCn0DbHz = codedInputByteBufferNano.readDouble();
                        continue block16;
                    }
                    case 72: {
                        this.numTopFourAverageCn0Processed = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 64: {
                        this.standardDeviationPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 56: {
                        this.meanPositionAccuracyMeters = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 48: {
                        this.numPositionAccuracyProcessed = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 40: {
                        this.standardDeviationTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 32: {
                        this.meanTimeToFirstFixSecs = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 24: {
                        this.numTimeToFirstFixProcessed = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 16: {
                        this.percentageLocationFailure = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 8: {
                        this.numLocationReportProcessed = codedInputByteBufferNano.readInt32();
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
            PowerMetrics powerMetrics;
            int n = this.numLocationReportProcessed;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.percentageLocationFailure) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.numTimeToFirstFixProcessed) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.meanTimeToFirstFixSecs) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.standardDeviationTimeToFirstFixSecs) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.numPositionAccuracyProcessed) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.meanPositionAccuracyMeters) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.standardDeviationPositionAccuracyMeters) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.numTopFourAverageCn0Processed) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if (Double.doubleToLongBits(this.meanTopFourAverageCn0DbHz) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(10, this.meanTopFourAverageCn0DbHz);
            }
            if (Double.doubleToLongBits(this.standardDeviationTopFourAverageCn0DbHz) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(11, this.standardDeviationTopFourAverageCn0DbHz);
            }
            if ((powerMetrics = this.powerMetrics) != null) {
                codedOutputByteBufferNano.writeMessage(12, powerMetrics);
            }
            if (!this.hardwareRevision.equals("")) {
                codedOutputByteBufferNano.writeString(13, this.hardwareRevision);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class PowerMetrics
    extends MessageNano {
        private static volatile PowerMetrics[] _emptyArray;
        public double energyConsumedMah;
        public long loggingDurationMs;
        public long[] timeInSignalQualityLevelMs;

        public PowerMetrics() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static PowerMetrics[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new PowerMetrics[0];
                return _emptyArray;
            }
        }

        public static PowerMetrics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new PowerMetrics().mergeFrom(codedInputByteBufferNano);
        }

        public static PowerMetrics parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new PowerMetrics(), arrby);
        }

        public PowerMetrics clear() {
            this.loggingDurationMs = 0L;
            this.energyConsumedMah = 0.0;
            this.timeInSignalQualityLevelMs = WireFormatNano.EMPTY_LONG_ARRAY;
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
            long[] arrl = this.timeInSignalQualityLevelMs;
            n2 = n;
            if (arrl != null) {
                n2 = n;
                if (arrl.length > 0) {
                    int n3 = 0;
                    for (n2 = 0; n2 < (arrl = this.timeInSignalQualityLevelMs).length; ++n2) {
                        l = arrl[n2];
                        n3 += CodedOutputByteBufferNano.computeInt64SizeNoTag(l);
                    }
                    n2 = n + n3 + arrl.length * 1;
                }
            }
            return n2;
        }

        @Override
        public PowerMetrics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 17) {
                        long[] arrl;
                        int n2;
                        if (n != 24) {
                            if (n != 26) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            int n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                            n2 = 0;
                            n = codedInputByteBufferNano.getPosition();
                            while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                codedInputByteBufferNano.readInt64();
                                ++n2;
                            }
                            codedInputByteBufferNano.rewindToPosition(n);
                            arrl = this.timeInSignalQualityLevelMs;
                            n = arrl == null ? 0 : arrl.length;
                            arrl = new long[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.timeInSignalQualityLevelMs, 0, arrl, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrl.length) {
                                arrl[n2] = codedInputByteBufferNano.readInt64();
                                ++n2;
                            }
                            this.timeInSignalQualityLevelMs = arrl;
                            codedInputByteBufferNano.popLimit(n3);
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        arrl = this.timeInSignalQualityLevelMs;
                        n = arrl == null ? 0 : arrl.length;
                        arrl = new long[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.timeInSignalQualityLevelMs, 0, arrl, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrl.length - 1) {
                            arrl[n2] = codedInputByteBufferNano.readInt64();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrl[n2] = codedInputByteBufferNano.readInt64();
                        this.timeInSignalQualityLevelMs = arrl;
                        continue;
                    }
                    this.energyConsumedMah = codedInputByteBufferNano.readDouble();
                    continue;
                }
                this.loggingDurationMs = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long[] arrl;
            long l = this.loggingDurationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if (Double.doubleToLongBits(this.energyConsumedMah) != Double.doubleToLongBits(0.0)) {
                codedOutputByteBufferNano.writeDouble(2, this.energyConsumedMah);
            }
            if ((arrl = this.timeInSignalQualityLevelMs) != null && arrl.length > 0) {
                for (int i = 0; i < (arrl = this.timeInSignalQualityLevelMs).length; ++i) {
                    codedOutputByteBufferNano.writeInt64(3, arrl[i]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

}

