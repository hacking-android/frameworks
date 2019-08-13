/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.connectivity.metrics.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface IpConnectivityLogClass {
    public static final int BLUETOOTH = 1;
    public static final int CELLULAR = 2;
    public static final int ETHERNET = 3;
    public static final int LOWPAN = 9;
    public static final int MULTIPLE = 6;
    public static final int NONE = 5;
    public static final int UNKNOWN = 0;
    public static final int WIFI = 4;
    public static final int WIFI_NAN = 8;
    public static final int WIFI_P2P = 7;

    public static final class ApfProgramEvent
    extends MessageNano {
        private static volatile ApfProgramEvent[] _emptyArray;
        public int currentRas;
        public boolean dropMulticast;
        public long effectiveLifetime;
        public int filteredRas;
        public boolean hasIpv4Addr;
        public long lifetime;
        public int programLength;

        public ApfProgramEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ApfProgramEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ApfProgramEvent[0];
                return _emptyArray;
            }
        }

        public static ApfProgramEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApfProgramEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static ApfProgramEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ApfProgramEvent(), arrby);
        }

        public ApfProgramEvent clear() {
            this.lifetime = 0L;
            this.effectiveLifetime = 0L;
            this.filteredRas = 0;
            this.currentRas = 0;
            this.programLength = 0;
            this.dropMulticast = false;
            this.hasIpv4Addr = false;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.lifetime;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.filteredRas;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.currentRas;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n3 = this.programLength;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            boolean bl = this.dropMulticast;
            n2 = n;
            if (bl) {
                n2 = n + CodedOutputByteBufferNano.computeBoolSize(5, bl);
            }
            bl = this.hasIpv4Addr;
            n = n2;
            if (bl) {
                n = n2 + CodedOutputByteBufferNano.computeBoolSize(6, bl);
            }
            l = this.effectiveLifetime;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            return n2;
        }

        @Override
        public ApfProgramEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (n != 56) {
                                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                            return this;
                                        }
                                        this.effectiveLifetime = codedInputByteBufferNano.readInt64();
                                        continue;
                                    }
                                    this.hasIpv4Addr = codedInputByteBufferNano.readBool();
                                    continue;
                                }
                                this.dropMulticast = codedInputByteBufferNano.readBool();
                                continue;
                            }
                            this.programLength = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.currentRas = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.filteredRas = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.lifetime = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean bl;
            int n;
            long l = this.lifetime;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.filteredRas) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.currentRas) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.programLength) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if (bl = this.dropMulticast) {
                codedOutputByteBufferNano.writeBool(5, bl);
            }
            if (bl = this.hasIpv4Addr) {
                codedOutputByteBufferNano.writeBool(6, bl);
            }
            if ((l = this.effectiveLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ApfStatistics
    extends MessageNano {
        private static volatile ApfStatistics[] _emptyArray;
        public int droppedRas;
        public long durationMs;
        public Pair[] hardwareCounters;
        public int matchingRas;
        public int maxProgramSize;
        public int parseErrors;
        public int programUpdates;
        public int programUpdatesAll;
        public int programUpdatesAllowingMulticast;
        public int receivedRas;
        public int totalPacketDropped;
        public int totalPacketProcessed;
        public int zeroLifetimeRas;

        public ApfStatistics() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ApfStatistics[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ApfStatistics[0];
                return _emptyArray;
            }
        }

        public static ApfStatistics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ApfStatistics().mergeFrom(codedInputByteBufferNano);
        }

        public static ApfStatistics parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ApfStatistics(), arrby);
        }

        public ApfStatistics clear() {
            this.durationMs = 0L;
            this.receivedRas = 0;
            this.matchingRas = 0;
            this.droppedRas = 0;
            this.zeroLifetimeRas = 0;
            this.parseErrors = 0;
            this.programUpdates = 0;
            this.maxProgramSize = 0;
            this.programUpdatesAll = 0;
            this.programUpdatesAllowingMulticast = 0;
            this.totalPacketProcessed = 0;
            this.totalPacketDropped = 0;
            this.hardwareCounters = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.durationMs;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.receivedRas;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.matchingRas;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n = this.droppedRas;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(5, n);
            }
            n2 = this.zeroLifetimeRas;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(6, n2);
            }
            n3 = this.parseErrors;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(7, n3);
            }
            n3 = this.programUpdates;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(8, n3);
            }
            n2 = this.maxProgramSize;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(9, n2);
            }
            n = this.programUpdatesAll;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(10, n);
            }
            n3 = this.programUpdatesAllowingMulticast;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(11, n3);
            }
            n3 = this.totalPacketProcessed;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(12, n3);
            }
            n3 = this.totalPacketDropped;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(13, n3);
            }
            Object object = this.hardwareCounters;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Pair[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.hardwareCounters;
                        n3 = n;
                        if (n2 >= ((Pair[])object).length) break;
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
            return n3;
        }

        @Override
        public ApfStatistics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 114: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 114);
                        Pair[] arrpair = this.hardwareCounters;
                        n = arrpair == null ? 0 : arrpair.length;
                        arrpair = new Pair[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.hardwareCounters, 0, arrpair, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrpair.length - 1) {
                            arrpair[n2] = new Pair();
                            codedInputByteBufferNano.readMessage(arrpair[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrpair[n2] = new Pair();
                        codedInputByteBufferNano.readMessage(arrpair[n2]);
                        this.hardwareCounters = arrpair;
                        continue block16;
                    }
                    case 104: {
                        this.totalPacketDropped = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 96: {
                        this.totalPacketProcessed = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 88: {
                        this.programUpdatesAllowingMulticast = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 80: {
                        this.programUpdatesAll = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 72: {
                        this.maxProgramSize = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 64: {
                        this.programUpdates = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 56: {
                        this.parseErrors = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 48: {
                        this.zeroLifetimeRas = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 40: {
                        this.droppedRas = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 24: {
                        this.matchingRas = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 16: {
                        this.receivedRas = codedInputByteBufferNano.readInt32();
                        continue block16;
                    }
                    case 8: {
                        this.durationMs = codedInputByteBufferNano.readInt64();
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
            Object object;
            int n;
            long l = this.durationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.receivedRas) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.matchingRas) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.droppedRas) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.zeroLifetimeRas) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((n = this.parseErrors) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.programUpdates) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if ((n = this.maxProgramSize) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if ((n = this.programUpdatesAll) != 0) {
                codedOutputByteBufferNano.writeInt32(10, n);
            }
            if ((n = this.programUpdatesAllowingMulticast) != 0) {
                codedOutputByteBufferNano.writeInt32(11, n);
            }
            if ((n = this.totalPacketProcessed) != 0) {
                codedOutputByteBufferNano.writeInt32(12, n);
            }
            if ((n = this.totalPacketDropped) != 0) {
                codedOutputByteBufferNano.writeInt32(13, n);
            }
            if ((object = this.hardwareCounters) != null && ((Pair[])object).length > 0) {
                for (n = 0; n < ((Pair[])(object = this.hardwareCounters)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(14, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ConnectStatistics
    extends MessageNano {
        private static volatile ConnectStatistics[] _emptyArray;
        public int connectBlockingCount;
        public int connectCount;
        public Pair[] errnosCounters;
        public int ipv6AddrCount;
        public int[] latenciesMs;
        public int[] nonBlockingLatenciesMs;

        public ConnectStatistics() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ConnectStatistics[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ConnectStatistics[0];
                return _emptyArray;
            }
        }

        public static ConnectStatistics parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ConnectStatistics().mergeFrom(codedInputByteBufferNano);
        }

        public static ConnectStatistics parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ConnectStatistics(), arrby);
        }

        public ConnectStatistics clear() {
            this.connectCount = 0;
            this.connectBlockingCount = 0;
            this.ipv6AddrCount = 0;
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.nonBlockingLatenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.errnosCounters = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = super.computeSerializedSize();
            int n3 = this.connectCount;
            int n4 = n2;
            if (n3 != 0) {
                n4 = n2 + CodedOutputByteBufferNano.computeInt32Size(1, n3);
            }
            n3 = this.ipv6AddrCount;
            n2 = n4;
            if (n3 != 0) {
                n2 = n4 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            Object object = this.latenciesMs;
            n4 = n2;
            if (object != null) {
                n4 = n2;
                if (((int[])object).length > 0) {
                    n3 = 0;
                    for (n4 = 0; n4 < ((int[])(object = this.latenciesMs)).length; ++n4) {
                        n = object[n4];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n4 = n2 + n3 + ((int[])object).length * 1;
                }
            }
            object = this.errnosCounters;
            n2 = n4;
            if (object != null) {
                n2 = n4;
                if (((int[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.errnosCounters;
                        n2 = n4;
                        if (n3 >= ((int[])object).length) break;
                        object = object[n3];
                        n2 = n4;
                        if (object != null) {
                            n2 = n4 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)object);
                        }
                        ++n3;
                        n4 = n2;
                    } while (true);
                }
            }
            n3 = this.connectBlockingCount;
            n4 = n2;
            if (n3 != 0) {
                n4 = n2 + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            object = this.nonBlockingLatenciesMs;
            n2 = n4;
            if (object != null) {
                n2 = n4;
                if (((int[])object).length > 0) {
                    n3 = 0;
                    for (n2 = 0; n2 < ((int[])(object = this.nonBlockingLatenciesMs)).length; ++n2) {
                        n = object[n2];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n2 = n4 + n3 + ((int[])object).length * 1;
                }
            }
            return n2;
        }

        @Override
        public ConnectStatistics mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        Object[] arrobject;
                        int n2;
                        if (n != 24) {
                            int n3;
                            if (n != 26) {
                                if (n != 34) {
                                    if (n != 40) {
                                        if (n != 48) {
                                            if (n != 50) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                                            n2 = 0;
                                            n = codedInputByteBufferNano.getPosition();
                                            while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                                codedInputByteBufferNano.readInt32();
                                                ++n2;
                                            }
                                            codedInputByteBufferNano.rewindToPosition(n);
                                            arrobject = this.nonBlockingLatenciesMs;
                                            n = arrobject == null ? 0 : arrobject.length;
                                            arrobject = new int[n + n2];
                                            n2 = n;
                                            if (n != 0) {
                                                System.arraycopy(this.nonBlockingLatenciesMs, 0, arrobject, 0, n);
                                                n2 = n;
                                            }
                                            while (n2 < arrobject.length) {
                                                arrobject[n2] = codedInputByteBufferNano.readInt32();
                                                ++n2;
                                            }
                                            this.nonBlockingLatenciesMs = arrobject;
                                            codedInputByteBufferNano.popLimit(n3);
                                            continue;
                                        }
                                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                                        arrobject = this.nonBlockingLatenciesMs;
                                        n = arrobject == null ? 0 : arrobject.length;
                                        arrobject = new int[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.nonBlockingLatenciesMs, 0, arrobject, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrobject.length - 1) {
                                            arrobject[n2] = codedInputByteBufferNano.readInt32();
                                            codedInputByteBufferNano.readTag();
                                            ++n2;
                                        }
                                        arrobject[n2] = codedInputByteBufferNano.readInt32();
                                        this.nonBlockingLatenciesMs = arrobject;
                                        continue;
                                    }
                                    this.connectBlockingCount = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
                                arrobject = this.errnosCounters;
                                n = arrobject == null ? 0 : arrobject.length;
                                arrobject = new Pair[n + n2];
                                n2 = n;
                                if (n != 0) {
                                    System.arraycopy(this.errnosCounters, 0, arrobject, 0, n);
                                    n2 = n;
                                }
                                while (n2 < arrobject.length - 1) {
                                    arrobject[n2] = (int)new Pair();
                                    codedInputByteBufferNano.readMessage((MessageNano)arrobject[n2]);
                                    codedInputByteBufferNano.readTag();
                                    ++n2;
                                }
                                arrobject[n2] = (int)new Pair();
                                codedInputByteBufferNano.readMessage((MessageNano)arrobject[n2]);
                                this.errnosCounters = arrobject;
                                continue;
                            }
                            n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                            n2 = 0;
                            n = codedInputByteBufferNano.getPosition();
                            while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                codedInputByteBufferNano.readInt32();
                                ++n2;
                            }
                            codedInputByteBufferNano.rewindToPosition(n);
                            arrobject = this.latenciesMs;
                            n = arrobject == null ? 0 : arrobject.length;
                            arrobject = new int[n + n2];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.latenciesMs, 0, arrobject, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrobject.length) {
                                arrobject[n2] = codedInputByteBufferNano.readInt32();
                                ++n2;
                            }
                            this.latenciesMs = arrobject;
                            codedInputByteBufferNano.popLimit(n3);
                            continue;
                        }
                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        arrobject = this.latenciesMs;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.latenciesMs, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = codedInputByteBufferNano.readInt32();
                        this.latenciesMs = arrobject;
                        continue;
                    }
                    this.ipv6AddrCount = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.connectCount = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            Object object;
            int n = this.connectCount;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.ipv6AddrCount) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((object = this.latenciesMs) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.latenciesMs)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(3, object[n]);
                }
            }
            if ((object = this.errnosCounters) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.errnosCounters)).length; ++n) {
                    if ((object = (Object)object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(4, (MessageNano)object);
                }
            }
            if ((n = this.connectBlockingCount) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((object = this.nonBlockingLatenciesMs) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.nonBlockingLatenciesMs)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(6, object[n]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class DHCPEvent
    extends MessageNano {
        public static final int ERROR_CODE_FIELD_NUMBER = 3;
        public static final int STATE_TRANSITION_FIELD_NUMBER = 2;
        private static volatile DHCPEvent[] _emptyArray;
        public int durationMs;
        public String ifName;
        private int valueCase_ = 0;
        private Object value_;

        public DHCPEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static DHCPEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new DHCPEvent[0];
                return _emptyArray;
            }
        }

        public static DHCPEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DHCPEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static DHCPEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new DHCPEvent(), arrby);
        }

        public DHCPEvent clear() {
            this.ifName = "";
            this.durationMs = 0;
            this.clearValue();
            this.cachedSize = -1;
            return this;
        }

        public DHCPEvent clearValue() {
            this.valueCase_ = 0;
            this.value_ = null;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            n = n2;
            if (this.valueCase_ == 2) {
                n = n2 + CodedOutputByteBufferNano.computeStringSize(2, (String)this.value_);
            }
            n2 = n;
            if (this.valueCase_ == 3) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, (Integer)this.value_);
            }
            int n3 = this.durationMs;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            return n;
        }

        public int getErrorCode() {
            if (this.valueCase_ == 3) {
                return (Integer)this.value_;
            }
            return 0;
        }

        public String getStateTransition() {
            if (this.valueCase_ == 2) {
                return (String)this.value_;
            }
            return "";
        }

        public int getValueCase() {
            return this.valueCase_;
        }

        public boolean hasErrorCode() {
            boolean bl = this.valueCase_ == 3;
            return bl;
        }

        public boolean hasStateTransition() {
            boolean bl = this.valueCase_ == 2;
            return bl;
        }

        @Override
        public DHCPEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 18) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.durationMs = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.value_ = codedInputByteBufferNano.readInt32();
                        this.valueCase_ = 3;
                        continue;
                    }
                    this.value_ = codedInputByteBufferNano.readString();
                    this.valueCase_ = 2;
                    continue;
                }
                this.ifName = codedInputByteBufferNano.readString();
            }
            return this;
        }

        public DHCPEvent setErrorCode(int n) {
            this.valueCase_ = 3;
            this.value_ = n;
            return this;
        }

        public DHCPEvent setStateTransition(String string2) {
            this.valueCase_ = 2;
            this.value_ = string2;
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if (this.valueCase_ == 2) {
                codedOutputByteBufferNano.writeString(2, (String)this.value_);
            }
            if (this.valueCase_ == 3) {
                codedOutputByteBufferNano.writeInt32(3, (Integer)this.value_);
            }
            if ((n = this.durationMs) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class DNSLatencies
    extends MessageNano {
        private static volatile DNSLatencies[] _emptyArray;
        public int aCount;
        public int aaaaCount;
        public int[] latenciesMs;
        public int queryCount;
        public int returnCode;
        public int type;

        public DNSLatencies() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static DNSLatencies[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new DNSLatencies[0];
                return _emptyArray;
            }
        }

        public static DNSLatencies parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DNSLatencies().mergeFrom(codedInputByteBufferNano);
        }

        public static DNSLatencies parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new DNSLatencies(), arrby);
        }

        public DNSLatencies clear() {
            this.type = 0;
            this.returnCode = 0;
            this.queryCount = 0;
            this.aCount = 0;
            this.aaaaCount = 0;
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
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
            n2 = this.returnCode;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.queryCount;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n = this.aCount;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n);
            }
            n3 = this.aaaaCount;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            int[] arrn = this.latenciesMs;
            n3 = n;
            if (arrn != null) {
                n3 = n;
                if (arrn.length > 0) {
                    n2 = 0;
                    for (n3 = 0; n3 < (arrn = this.latenciesMs).length; ++n3) {
                        int n4 = arrn[n3];
                        n2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n4);
                    }
                    n3 = n + n2 + arrn.length * 1;
                }
            }
            return n3;
        }

        @Override
        public DNSLatencies mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    int[] arrn;
                                    int n2;
                                    if (n != 48) {
                                        if (n != 50) {
                                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                            return this;
                                        }
                                        int n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                                        n2 = 0;
                                        n = codedInputByteBufferNano.getPosition();
                                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                                            codedInputByteBufferNano.readInt32();
                                            ++n2;
                                        }
                                        codedInputByteBufferNano.rewindToPosition(n);
                                        arrn = this.latenciesMs;
                                        n = arrn == null ? 0 : arrn.length;
                                        arrn = new int[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.latenciesMs, 0, arrn, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrn.length) {
                                            arrn[n2] = codedInputByteBufferNano.readInt32();
                                            ++n2;
                                        }
                                        this.latenciesMs = arrn;
                                        codedInputByteBufferNano.popLimit(n3);
                                        continue;
                                    }
                                    n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 48);
                                    arrn = this.latenciesMs;
                                    n = arrn == null ? 0 : arrn.length;
                                    arrn = new int[n + n2];
                                    n2 = n;
                                    if (n != 0) {
                                        System.arraycopy(this.latenciesMs, 0, arrn, 0, n);
                                        n2 = n;
                                    }
                                    while (n2 < arrn.length - 1) {
                                        arrn[n2] = codedInputByteBufferNano.readInt32();
                                        codedInputByteBufferNano.readTag();
                                        ++n2;
                                    }
                                    arrn[n2] = codedInputByteBufferNano.readInt32();
                                    this.latenciesMs = arrn;
                                    continue;
                                }
                                this.aaaaCount = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.aCount = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.queryCount = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.returnCode = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.type = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int[] arrn;
            int n = this.type;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.returnCode) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.queryCount) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.aCount) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.aaaaCount) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((arrn = this.latenciesMs) != null && arrn.length > 0) {
                for (n = 0; n < (arrn = this.latenciesMs).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(6, arrn[n]);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class DNSLookupBatch
    extends MessageNano {
        private static volatile DNSLookupBatch[] _emptyArray;
        public int[] eventTypes;
        public long getaddrinfoErrorCount;
        public Pair[] getaddrinfoErrors;
        public long getaddrinfoQueryCount;
        public long gethostbynameErrorCount;
        public Pair[] gethostbynameErrors;
        public long gethostbynameQueryCount;
        public int[] latenciesMs;
        public NetworkId networkId;
        public int[] returnCodes;

        public DNSLookupBatch() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static DNSLookupBatch[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new DNSLookupBatch[0];
                return _emptyArray;
            }
        }

        public static DNSLookupBatch parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DNSLookupBatch().mergeFrom(codedInputByteBufferNano);
        }

        public static DNSLookupBatch parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new DNSLookupBatch(), arrby);
        }

        public DNSLookupBatch clear() {
            this.latenciesMs = WireFormatNano.EMPTY_INT_ARRAY;
            this.getaddrinfoQueryCount = 0L;
            this.gethostbynameQueryCount = 0L;
            this.getaddrinfoErrorCount = 0L;
            this.gethostbynameErrorCount = 0L;
            this.getaddrinfoErrors = Pair.emptyArray();
            this.gethostbynameErrors = Pair.emptyArray();
            this.networkId = null;
            this.eventTypes = WireFormatNano.EMPTY_INT_ARRAY;
            this.returnCodes = WireFormatNano.EMPTY_INT_ARRAY;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2;
            int n3 = super.computeSerializedSize();
            Object object = this.networkId;
            int n4 = n3;
            if (object != null) {
                n4 = n3 + CodedOutputByteBufferNano.computeMessageSize(1, (MessageNano)object);
            }
            object = this.eventTypes;
            n3 = n4;
            if (object != null) {
                n3 = n4;
                if (((int[])object).length > 0) {
                    n2 = 0;
                    for (n3 = 0; n3 < ((int[])(object = this.eventTypes)).length; ++n3) {
                        n = object[n3];
                        n2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n3 = n4 + n2 + ((int[])object).length * 1;
                }
            }
            object = this.returnCodes;
            n4 = n3;
            if (object != null) {
                n4 = n3;
                if (((int[])object).length > 0) {
                    n2 = 0;
                    for (n4 = 0; n4 < ((int[])(object = this.returnCodes)).length; ++n4) {
                        n = object[n4];
                        n2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n4 = n3 + n2 + ((int[])object).length * 1;
                }
            }
            object = this.latenciesMs;
            n3 = n4;
            if (object != null) {
                n3 = n4;
                if (((int[])object).length > 0) {
                    n2 = 0;
                    for (n3 = 0; n3 < ((int[])(object = this.latenciesMs)).length; ++n3) {
                        n = object[n3];
                        n2 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n);
                    }
                    n3 = n4 + n2 + ((int[])object).length * 1;
                }
            }
            long l = this.getaddrinfoQueryCount;
            n4 = n3;
            if (l != 0L) {
                n4 = n3 + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.gethostbynameQueryCount;
            n2 = n4;
            if (l != 0L) {
                n2 = n4 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.getaddrinfoErrorCount;
            n3 = n2;
            if (l != 0L) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.gethostbynameErrorCount;
            n4 = n3;
            if (l != 0L) {
                n4 = n3 + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            object = this.getaddrinfoErrors;
            n3 = n4;
            if (object != null) {
                n3 = n4;
                if (((int[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.getaddrinfoErrors;
                        n3 = n4;
                        if (n2 >= ((int[])object).length) break;
                        object = object[n2];
                        n3 = n4;
                        if (object != null) {
                            n3 = n4 + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano)object);
                        }
                        ++n2;
                        n4 = n3;
                    } while (true);
                }
            }
            object = this.gethostbynameErrors;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (((int[])object).length > 0) {
                    n4 = 0;
                    do {
                        object = this.gethostbynameErrors;
                        n2 = n3;
                        if (n4 >= ((int[])object).length) break;
                        object = object[n4];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + CodedOutputByteBufferNano.computeMessageSize(10, (MessageNano)object);
                        }
                        ++n4;
                        n3 = n2;
                    } while (true);
                }
            }
            return n2;
        }

        @Override
        public DNSLookupBatch mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block16 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block16;
                        return this;
                    }
                    case 82: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 82);
                        Object[] arrobject = this.gethostbynameErrors;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new Pair[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.gethostbynameErrors, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new Pair();
                            codedInputByteBufferNano.readMessage(arrobject[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = new Pair();
                        codedInputByteBufferNano.readMessage(arrobject[n2]);
                        this.gethostbynameErrors = arrobject;
                        continue block16;
                    }
                    case 74: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        Object[] arrobject = this.getaddrinfoErrors;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new Pair[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.getaddrinfoErrors, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new Pair();
                            codedInputByteBufferNano.readMessage(arrobject[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = new Pair();
                        codedInputByteBufferNano.readMessage(arrobject[n2]);
                        this.getaddrinfoErrors = arrobject;
                        continue block16;
                    }
                    case 64: {
                        this.gethostbynameErrorCount = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 56: {
                        this.getaddrinfoErrorCount = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 48: {
                        this.gethostbynameQueryCount = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 40: {
                        this.getaddrinfoQueryCount = codedInputByteBufferNano.readInt64();
                        continue block16;
                    }
                    case 34: {
                        int n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n2 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        Object[] arrobject = this.latenciesMs;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.latenciesMs, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        this.latenciesMs = arrobject;
                        codedInputByteBufferNano.popLimit(n3);
                        continue block16;
                    }
                    case 32: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        Object[] arrobject = this.latenciesMs;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.latenciesMs, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                        this.latenciesMs = arrobject;
                        continue block16;
                    }
                    case 26: {
                        int n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n2 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        Object[] arrobject = this.returnCodes;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.returnCodes, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        this.returnCodes = arrobject;
                        codedInputByteBufferNano.popLimit(n3);
                        continue block16;
                    }
                    case 24: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 24);
                        Object[] arrobject = this.returnCodes;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.returnCodes, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                        this.returnCodes = arrobject;
                        continue block16;
                    }
                    case 18: {
                        int n3 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n2 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        Object[] arrobject = this.eventTypes;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.eventTypes, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            ++n2;
                        }
                        this.eventTypes = arrobject;
                        codedInputByteBufferNano.popLimit(n3);
                        continue block16;
                    }
                    case 16: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 16);
                        Object[] arrobject = this.eventTypes;
                        n = arrobject == null ? 0 : arrobject.length;
                        arrobject = new int[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.eventTypes, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrobject[n2] = (Pair)codedInputByteBufferNano.readInt32();
                        this.eventTypes = arrobject;
                        continue block16;
                    }
                    case 10: {
                        if (this.networkId == null) {
                            this.networkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
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
            long l;
            Object object = this.networkId;
            if (object != null) {
                codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
            }
            if ((object = this.eventTypes) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.eventTypes)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(2, object[n]);
                }
            }
            if ((object = this.returnCodes) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.returnCodes)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(3, object[n]);
                }
            }
            if ((object = this.latenciesMs) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.latenciesMs)).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(4, object[n]);
                }
            }
            if ((l = this.getaddrinfoQueryCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.gethostbynameQueryCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.getaddrinfoErrorCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.gethostbynameErrorCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((object = this.getaddrinfoErrors) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.getaddrinfoErrors)).length; ++n) {
                    if ((object = (Object)object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(9, (MessageNano)object);
                }
            }
            if ((object = this.gethostbynameErrors) != null && ((int[])object).length > 0) {
                for (n = 0; n < ((int[])(object = this.gethostbynameErrors)).length; ++n) {
                    if ((object = (Object)object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(10, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class DefaultNetworkEvent
    extends MessageNano {
        public static final int DISCONNECT = 3;
        public static final int DUAL = 3;
        public static final int INVALIDATION = 2;
        public static final int IPV4 = 1;
        public static final int IPV6 = 2;
        public static final int NONE = 0;
        public static final int OUTSCORED = 1;
        public static final int UNKNOWN = 0;
        private static volatile DefaultNetworkEvent[] _emptyArray;
        public long defaultNetworkDurationMs;
        public long finalScore;
        public long initialScore;
        public int ipSupport;
        public NetworkId networkId;
        public long noDefaultNetworkDurationMs;
        public int previousDefaultNetworkLinkLayer;
        public NetworkId previousNetworkId;
        public int previousNetworkIpSupport;
        public int[] transportTypes;
        public long validationDurationMs;

        public DefaultNetworkEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static DefaultNetworkEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new DefaultNetworkEvent[0];
                return _emptyArray;
            }
        }

        public static DefaultNetworkEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new DefaultNetworkEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static DefaultNetworkEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new DefaultNetworkEvent(), arrby);
        }

        public DefaultNetworkEvent clear() {
            this.defaultNetworkDurationMs = 0L;
            this.validationDurationMs = 0L;
            this.initialScore = 0L;
            this.finalScore = 0L;
            this.ipSupport = 0;
            this.previousDefaultNetworkLinkLayer = 0;
            this.networkId = null;
            this.previousNetworkId = null;
            this.previousNetworkIpSupport = 0;
            this.transportTypes = WireFormatNano.EMPTY_INT_ARRAY;
            this.noDefaultNetworkDurationMs = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int[] arrn = this.networkId;
            int n2 = n;
            if (arrn != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(1, (MessageNano)arrn);
            }
            arrn = this.previousNetworkId;
            n = n2;
            if (arrn != null) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)arrn);
            }
            int n3 = this.previousNetworkIpSupport;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            arrn = this.transportTypes;
            n = n2;
            if (arrn != null) {
                n = n2;
                if (arrn.length > 0) {
                    n3 = 0;
                    for (n = 0; n < (arrn = this.transportTypes).length; ++n) {
                        int n4 = arrn[n];
                        n3 += CodedOutputByteBufferNano.computeInt32SizeNoTag(n4);
                    }
                    n = n2 + n3 + arrn.length * 1;
                }
            }
            long l = this.defaultNetworkDurationMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.noDefaultNetworkDurationMs;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.initialScore;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            l = this.finalScore;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(8, l);
            }
            n3 = this.ipSupport;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(9, n3);
            }
            n3 = this.previousDefaultNetworkLinkLayer;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(10, n3);
            }
            l = this.validationDurationMs;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(11, l);
            }
            return n2;
        }

        @Override
        public DefaultNetworkEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block18 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block18;
                        return this;
                    }
                    case 88: {
                        this.validationDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 80: {
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
                        this.previousDefaultNetworkLinkLayer = n;
                        break;
                    }
                    case 72: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3) continue block18;
                        this.ipSupport = n;
                        break;
                    }
                    case 64: {
                        this.finalScore = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 56: {
                        this.initialScore = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 48: {
                        this.noDefaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 40: {
                        this.defaultNetworkDurationMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 34: {
                        int n2 = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
                        int n3 = 0;
                        n = codedInputByteBufferNano.getPosition();
                        while (codedInputByteBufferNano.getBytesUntilLimit() > 0) {
                            codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        codedInputByteBufferNano.rewindToPosition(n);
                        int[] arrn = this.transportTypes;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.transportTypes, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            ++n3;
                        }
                        this.transportTypes = arrn;
                        codedInputByteBufferNano.popLimit(n2);
                        break;
                    }
                    case 32: {
                        int n3 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 32);
                        int[] arrn = this.transportTypes;
                        n = arrn == null ? 0 : arrn.length;
                        arrn = new int[n + n3];
                        n3 = n;
                        if (n != 0) {
                            System.arraycopy(this.transportTypes, 0, arrn, 0, n);
                            n3 = n;
                        }
                        while (n3 < arrn.length - 1) {
                            arrn[n3] = codedInputByteBufferNano.readInt32();
                            codedInputByteBufferNano.readTag();
                            ++n3;
                        }
                        arrn[n3] = codedInputByteBufferNano.readInt32();
                        this.transportTypes = arrn;
                        break;
                    }
                    case 24: {
                        n = codedInputByteBufferNano.readInt32();
                        if (n != 0 && n != 1 && n != 2 && n != 3) continue block18;
                        this.previousNetworkIpSupport = n;
                        break;
                    }
                    case 18: {
                        if (this.previousNetworkId == null) {
                            this.previousNetworkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.previousNetworkId);
                        break;
                    }
                    case 10: {
                        if (this.networkId == null) {
                            this.networkId = new NetworkId();
                        }
                        codedInputByteBufferNano.readMessage(this.networkId);
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
            long l;
            int[] arrn = this.networkId;
            if (arrn != null) {
                codedOutputByteBufferNano.writeMessage(1, (MessageNano)arrn);
            }
            if ((arrn = this.previousNetworkId) != null) {
                codedOutputByteBufferNano.writeMessage(2, (MessageNano)arrn);
            }
            if ((n = this.previousNetworkIpSupport) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((arrn = this.transportTypes) != null && arrn.length > 0) {
                for (n = 0; n < (arrn = this.transportTypes).length; ++n) {
                    codedOutputByteBufferNano.writeInt32(4, arrn[n]);
                }
            }
            if ((l = this.defaultNetworkDurationMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.noDefaultNetworkDurationMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.initialScore) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((l = this.finalScore) != 0L) {
                codedOutputByteBufferNano.writeInt64(8, l);
            }
            if ((n = this.ipSupport) != 0) {
                codedOutputByteBufferNano.writeInt32(9, n);
            }
            if ((n = this.previousDefaultNetworkLinkLayer) != 0) {
                codedOutputByteBufferNano.writeInt32(10, n);
            }
            if ((l = this.validationDurationMs) != 0L) {
                codedOutputByteBufferNano.writeInt64(11, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class IpConnectivityEvent
    extends MessageNano {
        public static final int APF_PROGRAM_EVENT_FIELD_NUMBER = 9;
        public static final int APF_STATISTICS_FIELD_NUMBER = 10;
        public static final int CONNECT_STATISTICS_FIELD_NUMBER = 14;
        public static final int DEFAULT_NETWORK_EVENT_FIELD_NUMBER = 2;
        public static final int DHCP_EVENT_FIELD_NUMBER = 6;
        public static final int DNS_LATENCIES_FIELD_NUMBER = 13;
        public static final int DNS_LOOKUP_BATCH_FIELD_NUMBER = 5;
        public static final int IP_PROVISIONING_EVENT_FIELD_NUMBER = 7;
        public static final int IP_REACHABILITY_EVENT_FIELD_NUMBER = 3;
        public static final int NETWORK_EVENT_FIELD_NUMBER = 4;
        public static final int NETWORK_STATS_FIELD_NUMBER = 19;
        public static final int RA_EVENT_FIELD_NUMBER = 11;
        public static final int VALIDATION_PROBE_EVENT_FIELD_NUMBER = 8;
        public static final int WAKEUP_STATS_FIELD_NUMBER = 20;
        private static volatile IpConnectivityEvent[] _emptyArray;
        private int eventCase_ = 0;
        private Object event_;
        public String ifName;
        public int linkLayer;
        public int networkId;
        public long timeMs;
        public long transports;

        public IpConnectivityEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static IpConnectivityEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new IpConnectivityEvent[0];
                return _emptyArray;
            }
        }

        public static IpConnectivityEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpConnectivityEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static IpConnectivityEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new IpConnectivityEvent(), arrby);
        }

        public IpConnectivityEvent clear() {
            this.timeMs = 0L;
            this.linkLayer = 0;
            this.networkId = 0;
            this.ifName = "";
            this.transports = 0L;
            this.clearEvent();
            this.cachedSize = -1;
            return this;
        }

        public IpConnectivityEvent clearEvent() {
            this.eventCase_ = 0;
            this.event_ = null;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.timeMs;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            n = n2;
            if (this.eventCase_ == 2) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano)this.event_);
            }
            n2 = n;
            if (this.eventCase_ == 3) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano)this.event_);
            }
            n = n2;
            if (this.eventCase_ == 4) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(4, (MessageNano)this.event_);
            }
            n2 = n;
            if (this.eventCase_ == 5) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano)this.event_);
            }
            int n3 = n2;
            if (this.eventCase_ == 6) {
                n3 = n2 + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano)this.event_);
            }
            n = n3;
            if (this.eventCase_ == 7) {
                n = n3 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)this.event_);
            }
            n2 = n;
            if (this.eventCase_ == 8) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)this.event_);
            }
            n = n2;
            if (this.eventCase_ == 9) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano)this.event_);
            }
            n2 = n;
            if (this.eventCase_ == 10) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(10, (MessageNano)this.event_);
            }
            n = n2;
            if (this.eventCase_ == 11) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(11, (MessageNano)this.event_);
            }
            n2 = n;
            if (this.eventCase_ == 13) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(13, (MessageNano)this.event_);
            }
            n = n2;
            if (this.eventCase_ == 14) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(14, (MessageNano)this.event_);
            }
            n3 = this.linkLayer;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(15, n3);
            }
            n3 = this.networkId;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(16, n3);
            }
            n2 = n;
            if (!this.ifName.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(17, this.ifName);
            }
            l = this.transports;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(18, l);
            }
            n2 = n;
            if (this.eventCase_ == 19) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(19, (MessageNano)this.event_);
            }
            n = n2;
            if (this.eventCase_ == 20) {
                n = n2 + CodedOutputByteBufferNano.computeMessageSize(20, (MessageNano)this.event_);
            }
            return n;
        }

        public ApfProgramEvent getApfProgramEvent() {
            if (this.eventCase_ == 9) {
                return (ApfProgramEvent)this.event_;
            }
            return null;
        }

        public ApfStatistics getApfStatistics() {
            if (this.eventCase_ == 10) {
                return (ApfStatistics)this.event_;
            }
            return null;
        }

        public ConnectStatistics getConnectStatistics() {
            if (this.eventCase_ == 14) {
                return (ConnectStatistics)this.event_;
            }
            return null;
        }

        public DefaultNetworkEvent getDefaultNetworkEvent() {
            if (this.eventCase_ == 2) {
                return (DefaultNetworkEvent)this.event_;
            }
            return null;
        }

        public DHCPEvent getDhcpEvent() {
            if (this.eventCase_ == 6) {
                return (DHCPEvent)this.event_;
            }
            return null;
        }

        public DNSLatencies getDnsLatencies() {
            if (this.eventCase_ == 13) {
                return (DNSLatencies)this.event_;
            }
            return null;
        }

        public DNSLookupBatch getDnsLookupBatch() {
            if (this.eventCase_ == 5) {
                return (DNSLookupBatch)this.event_;
            }
            return null;
        }

        public int getEventCase() {
            return this.eventCase_;
        }

        public IpProvisioningEvent getIpProvisioningEvent() {
            if (this.eventCase_ == 7) {
                return (IpProvisioningEvent)this.event_;
            }
            return null;
        }

        public IpReachabilityEvent getIpReachabilityEvent() {
            if (this.eventCase_ == 3) {
                return (IpReachabilityEvent)this.event_;
            }
            return null;
        }

        public NetworkEvent getNetworkEvent() {
            if (this.eventCase_ == 4) {
                return (NetworkEvent)this.event_;
            }
            return null;
        }

        public NetworkStats getNetworkStats() {
            if (this.eventCase_ == 19) {
                return (NetworkStats)this.event_;
            }
            return null;
        }

        public RaEvent getRaEvent() {
            if (this.eventCase_ == 11) {
                return (RaEvent)this.event_;
            }
            return null;
        }

        public ValidationProbeEvent getValidationProbeEvent() {
            if (this.eventCase_ == 8) {
                return (ValidationProbeEvent)this.event_;
            }
            return null;
        }

        public WakeupStats getWakeupStats() {
            if (this.eventCase_ == 20) {
                return (WakeupStats)this.event_;
            }
            return null;
        }

        public boolean hasApfProgramEvent() {
            boolean bl = this.eventCase_ == 9;
            return bl;
        }

        public boolean hasApfStatistics() {
            boolean bl = this.eventCase_ == 10;
            return bl;
        }

        public boolean hasConnectStatistics() {
            boolean bl = this.eventCase_ == 14;
            return bl;
        }

        public boolean hasDefaultNetworkEvent() {
            boolean bl = this.eventCase_ == 2;
            return bl;
        }

        public boolean hasDhcpEvent() {
            boolean bl = this.eventCase_ == 6;
            return bl;
        }

        public boolean hasDnsLatencies() {
            boolean bl = this.eventCase_ == 13;
            return bl;
        }

        public boolean hasDnsLookupBatch() {
            boolean bl = this.eventCase_ == 5;
            return bl;
        }

        public boolean hasIpProvisioningEvent() {
            boolean bl = this.eventCase_ == 7;
            return bl;
        }

        public boolean hasIpReachabilityEvent() {
            boolean bl = this.eventCase_ == 3;
            return bl;
        }

        public boolean hasNetworkEvent() {
            boolean bl = this.eventCase_ == 4;
            return bl;
        }

        public boolean hasNetworkStats() {
            boolean bl = this.eventCase_ == 19;
            return bl;
        }

        public boolean hasRaEvent() {
            boolean bl = this.eventCase_ == 11;
            return bl;
        }

        public boolean hasValidationProbeEvent() {
            boolean bl = this.eventCase_ == 8;
            return bl;
        }

        public boolean hasWakeupStats() {
            boolean bl = this.eventCase_ == 20;
            return bl;
        }

        @Override
        public IpConnectivityEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block25 : do {
                int n = codedInputByteBufferNano.readTag();
                block0 : switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block25;
                        return this;
                    }
                    case 162: {
                        if (this.eventCase_ != 20) {
                            this.event_ = new WakeupStats();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 20;
                        break;
                    }
                    case 154: {
                        if (this.eventCase_ != 19) {
                            this.event_ = new NetworkStats();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 19;
                        break;
                    }
                    case 144: {
                        this.transports = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 138: {
                        this.ifName = codedInputByteBufferNano.readString();
                        break;
                    }
                    case 128: {
                        this.networkId = codedInputByteBufferNano.readInt32();
                        break;
                    }
                    case 120: {
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
                        this.linkLayer = n;
                        break;
                    }
                    case 114: {
                        if (this.eventCase_ != 14) {
                            this.event_ = new ConnectStatistics();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 14;
                        break;
                    }
                    case 106: {
                        if (this.eventCase_ != 13) {
                            this.event_ = new DNSLatencies();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 13;
                        break;
                    }
                    case 90: {
                        if (this.eventCase_ != 11) {
                            this.event_ = new RaEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 11;
                        break;
                    }
                    case 82: {
                        if (this.eventCase_ != 10) {
                            this.event_ = new ApfStatistics();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 10;
                        break;
                    }
                    case 74: {
                        if (this.eventCase_ != 9) {
                            this.event_ = new ApfProgramEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 9;
                        break;
                    }
                    case 66: {
                        if (this.eventCase_ != 8) {
                            this.event_ = new ValidationProbeEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 8;
                        break;
                    }
                    case 58: {
                        if (this.eventCase_ != 7) {
                            this.event_ = new IpProvisioningEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 7;
                        break;
                    }
                    case 50: {
                        if (this.eventCase_ != 6) {
                            this.event_ = new DHCPEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 6;
                        break;
                    }
                    case 42: {
                        if (this.eventCase_ != 5) {
                            this.event_ = new DNSLookupBatch();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 5;
                        break;
                    }
                    case 34: {
                        if (this.eventCase_ != 4) {
                            this.event_ = new NetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 4;
                        break;
                    }
                    case 26: {
                        if (this.eventCase_ != 3) {
                            this.event_ = new IpReachabilityEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 3;
                        break;
                    }
                    case 18: {
                        if (this.eventCase_ != 2) {
                            this.event_ = new DefaultNetworkEvent();
                        }
                        codedInputByteBufferNano.readMessage((MessageNano)this.event_);
                        this.eventCase_ = 2;
                        break;
                    }
                    case 8: {
                        this.timeMs = codedInputByteBufferNano.readInt64();
                        break;
                    }
                    case 0: {
                        return this;
                    }
                }
            } while (true);
        }

        public IpConnectivityEvent setApfProgramEvent(ApfProgramEvent apfProgramEvent) {
            if (apfProgramEvent != null) {
                this.eventCase_ = 9;
                this.event_ = apfProgramEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setApfStatistics(ApfStatistics apfStatistics) {
            if (apfStatistics != null) {
                this.eventCase_ = 10;
                this.event_ = apfStatistics;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setConnectStatistics(ConnectStatistics connectStatistics) {
            if (connectStatistics != null) {
                this.eventCase_ = 14;
                this.event_ = connectStatistics;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setDefaultNetworkEvent(DefaultNetworkEvent defaultNetworkEvent) {
            if (defaultNetworkEvent != null) {
                this.eventCase_ = 2;
                this.event_ = defaultNetworkEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setDhcpEvent(DHCPEvent dHCPEvent) {
            if (dHCPEvent != null) {
                this.eventCase_ = 6;
                this.event_ = dHCPEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setDnsLatencies(DNSLatencies dNSLatencies) {
            if (dNSLatencies != null) {
                this.eventCase_ = 13;
                this.event_ = dNSLatencies;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setDnsLookupBatch(DNSLookupBatch dNSLookupBatch) {
            if (dNSLookupBatch != null) {
                this.eventCase_ = 5;
                this.event_ = dNSLookupBatch;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setIpProvisioningEvent(IpProvisioningEvent ipProvisioningEvent) {
            if (ipProvisioningEvent != null) {
                this.eventCase_ = 7;
                this.event_ = ipProvisioningEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setIpReachabilityEvent(IpReachabilityEvent ipReachabilityEvent) {
            if (ipReachabilityEvent != null) {
                this.eventCase_ = 3;
                this.event_ = ipReachabilityEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setNetworkEvent(NetworkEvent networkEvent) {
            if (networkEvent != null) {
                this.eventCase_ = 4;
                this.event_ = networkEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setNetworkStats(NetworkStats networkStats) {
            if (networkStats != null) {
                this.eventCase_ = 19;
                this.event_ = networkStats;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setRaEvent(RaEvent raEvent) {
            if (raEvent != null) {
                this.eventCase_ = 11;
                this.event_ = raEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setValidationProbeEvent(ValidationProbeEvent validationProbeEvent) {
            if (validationProbeEvent != null) {
                this.eventCase_ = 8;
                this.event_ = validationProbeEvent;
                return this;
            }
            throw new NullPointerException();
        }

        public IpConnectivityEvent setWakeupStats(WakeupStats wakeupStats) {
            if (wakeupStats != null) {
                this.eventCase_ = 20;
                this.event_ = wakeupStats;
                return this;
            }
            throw new NullPointerException();
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            long l = this.timeMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if (this.eventCase_ == 2) {
                codedOutputByteBufferNano.writeMessage(2, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 3) {
                codedOutputByteBufferNano.writeMessage(3, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 4) {
                codedOutputByteBufferNano.writeMessage(4, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 5) {
                codedOutputByteBufferNano.writeMessage(5, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 6) {
                codedOutputByteBufferNano.writeMessage(6, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 7) {
                codedOutputByteBufferNano.writeMessage(7, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 8) {
                codedOutputByteBufferNano.writeMessage(8, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 9) {
                codedOutputByteBufferNano.writeMessage(9, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 10) {
                codedOutputByteBufferNano.writeMessage(10, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 11) {
                codedOutputByteBufferNano.writeMessage(11, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 13) {
                codedOutputByteBufferNano.writeMessage(13, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 14) {
                codedOutputByteBufferNano.writeMessage(14, (MessageNano)this.event_);
            }
            if ((n = this.linkLayer) != 0) {
                codedOutputByteBufferNano.writeInt32(15, n);
            }
            if ((n = this.networkId) != 0) {
                codedOutputByteBufferNano.writeInt32(16, n);
            }
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(17, this.ifName);
            }
            if ((l = this.transports) != 0L) {
                codedOutputByteBufferNano.writeInt64(18, l);
            }
            if (this.eventCase_ == 19) {
                codedOutputByteBufferNano.writeMessage(19, (MessageNano)this.event_);
            }
            if (this.eventCase_ == 20) {
                codedOutputByteBufferNano.writeMessage(20, (MessageNano)this.event_);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class IpConnectivityLog
    extends MessageNano {
        private static volatile IpConnectivityLog[] _emptyArray;
        public int droppedEvents;
        public IpConnectivityEvent[] events;
        public int version;

        public IpConnectivityLog() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static IpConnectivityLog[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new IpConnectivityLog[0];
                return _emptyArray;
            }
        }

        public static IpConnectivityLog parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpConnectivityLog().mergeFrom(codedInputByteBufferNano);
        }

        public static IpConnectivityLog parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new IpConnectivityLog(), arrby);
        }

        public IpConnectivityLog clear() {
            this.events = IpConnectivityEvent.emptyArray();
            this.droppedEvents = 0;
            this.version = 0;
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
                if (((IpConnectivityEvent[])object).length > 0) {
                    n = 0;
                    do {
                        object = this.events;
                        n3 = n2;
                        if (n >= ((IpConnectivityEvent[])object).length) break;
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
            n = this.droppedEvents;
            n2 = n3;
            if (n != 0) {
                n2 = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n);
            }
            n = this.version;
            n3 = n2;
            if (n != 0) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt32Size(3, n);
            }
            return n3;
        }

        @Override
        public IpConnectivityLog mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.version = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.droppedEvents = codedInputByteBufferNano.readInt32();
                    continue;
                }
                int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                IpConnectivityEvent[] arripConnectivityEvent = this.events;
                n = arripConnectivityEvent == null ? 0 : arripConnectivityEvent.length;
                arripConnectivityEvent = new IpConnectivityEvent[n + n2];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.events, 0, arripConnectivityEvent, 0, n);
                    n2 = n;
                }
                while (n2 < arripConnectivityEvent.length - 1) {
                    arripConnectivityEvent[n2] = new IpConnectivityEvent();
                    codedInputByteBufferNano.readMessage(arripConnectivityEvent[n2]);
                    codedInputByteBufferNano.readTag();
                    ++n2;
                }
                arripConnectivityEvent[n2] = new IpConnectivityEvent();
                codedInputByteBufferNano.readMessage(arripConnectivityEvent[n2]);
                this.events = arripConnectivityEvent;
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            Object object = this.events;
            if (object != null && ((IpConnectivityEvent[])object).length > 0) {
                for (n = 0; n < ((IpConnectivityEvent[])(object = this.events)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(1, (MessageNano)object);
                }
            }
            if ((n = this.droppedEvents) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.version) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class IpProvisioningEvent
    extends MessageNano {
        private static volatile IpProvisioningEvent[] _emptyArray;
        public int eventType;
        public String ifName;
        public int latencyMs;

        public IpProvisioningEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static IpProvisioningEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new IpProvisioningEvent[0];
                return _emptyArray;
            }
        }

        public static IpProvisioningEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpProvisioningEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static IpProvisioningEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new IpProvisioningEvent(), arrby);
        }

        public IpProvisioningEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            int n3 = this.eventType;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.latencyMs;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            return n2;
        }

        @Override
        public IpProvisioningEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.latencyMs = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.eventType = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.ifName = codedInputByteBufferNano.readString();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if ((n = this.eventType) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.latencyMs) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class IpReachabilityEvent
    extends MessageNano {
        private static volatile IpReachabilityEvent[] _emptyArray;
        public int eventType;
        public String ifName;

        public IpReachabilityEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static IpReachabilityEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new IpReachabilityEvent[0];
                return _emptyArray;
            }
        }

        public static IpReachabilityEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new IpReachabilityEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static IpReachabilityEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new IpReachabilityEvent(), arrby);
        }

        public IpReachabilityEvent clear() {
            this.ifName = "";
            this.eventType = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n;
            int n2 = n = super.computeSerializedSize();
            if (!this.ifName.equals("")) {
                n2 = n + CodedOutputByteBufferNano.computeStringSize(1, this.ifName);
            }
            int n3 = this.eventType;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            return n;
        }

        @Override
        public IpReachabilityEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.eventType = codedInputByteBufferNano.readInt32();
                    continue;
                }
                this.ifName = codedInputByteBufferNano.readString();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            if (!this.ifName.equals("")) {
                codedOutputByteBufferNano.writeString(1, this.ifName);
            }
            if ((n = this.eventType) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class NetworkEvent
    extends MessageNano {
        private static volatile NetworkEvent[] _emptyArray;
        public int eventType;
        public int latencyMs;
        public NetworkId networkId;

        public NetworkEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static NetworkEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new NetworkEvent[0];
                return _emptyArray;
            }
        }

        public static NetworkEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static NetworkEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new NetworkEvent(), arrby);
        }

        public NetworkEvent clear() {
            this.networkId = null;
            this.eventType = 0;
            this.latencyMs = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            int n2 = n;
            if (networkId != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            int n3 = this.eventType;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.latencyMs;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            return n2;
        }

        @Override
        public NetworkEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (n != 24) {
                            if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                            return this;
                        }
                        this.latencyMs = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.eventType = codedInputByteBufferNano.readInt32();
                    continue;
                }
                if (this.networkId == null) {
                    this.networkId = new NetworkId();
                }
                codedInputByteBufferNano.readMessage(this.networkId);
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            if ((n = this.eventType) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.latencyMs) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class NetworkId
    extends MessageNano {
        private static volatile NetworkId[] _emptyArray;
        public int networkId;

        public NetworkId() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static NetworkId[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new NetworkId[0];
                return _emptyArray;
            }
        }

        public static NetworkId parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkId().mergeFrom(codedInputByteBufferNano);
        }

        public static NetworkId parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new NetworkId(), arrby);
        }

        public NetworkId clear() {
            this.networkId = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.networkId;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            return n3;
        }

        @Override
        public NetworkId mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                    return this;
                }
                this.networkId = codedInputByteBufferNano.readInt32();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n = this.networkId;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class NetworkStats
    extends MessageNano {
        private static volatile NetworkStats[] _emptyArray;
        public long durationMs;
        public boolean everValidated;
        public int ipSupport;
        public int noConnectivityReports;
        public boolean portalFound;
        public int validationAttempts;
        public Pair[] validationEvents;
        public Pair[] validationStates;

        public NetworkStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static NetworkStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new NetworkStats[0];
                return _emptyArray;
            }
        }

        public static NetworkStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new NetworkStats().mergeFrom(codedInputByteBufferNano);
        }

        public static NetworkStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new NetworkStats(), arrby);
        }

        public NetworkStats clear() {
            this.durationMs = 0L;
            this.ipSupport = 0;
            this.everValidated = false;
            this.portalFound = false;
            this.noConnectivityReports = 0;
            this.validationAttempts = 0;
            this.validationEvents = Pair.emptyArray();
            this.validationStates = Pair.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.durationMs;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            int n3 = this.ipSupport;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            boolean bl = this.everValidated;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(3, bl);
            }
            bl = this.portalFound;
            n2 = n3;
            if (bl) {
                n2 = n3 + CodedOutputByteBufferNano.computeBoolSize(4, bl);
            }
            n3 = this.noConnectivityReports;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(5, n3);
            }
            n3 = this.validationAttempts;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(6, n3);
            }
            Object object = this.validationEvents;
            n = n2;
            if (object != null) {
                n = n2;
                if (((Pair[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.validationEvents;
                        n = n2;
                        if (n3 >= ((Pair[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(7, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            object = this.validationStates;
            n3 = n;
            if (object != null) {
                n3 = n;
                if (((Pair[])object).length > 0) {
                    n2 = 0;
                    do {
                        object = this.validationStates;
                        n3 = n;
                        if (n2 >= ((Pair[])object).length) break;
                        object = object[n2];
                        n3 = n;
                        if (object != null) {
                            n3 = n + CodedOutputByteBufferNano.computeMessageSize(8, (MessageNano)object);
                        }
                        ++n2;
                        n = n3;
                    } while (true);
                }
            }
            return n3;
        }

        @Override
        public NetworkStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        int n2;
                                        Pair[] arrpair;
                                        if (n != 58) {
                                            if (n != 66) {
                                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                                return this;
                                            }
                                            n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                                            arrpair = this.validationStates;
                                            n = arrpair == null ? 0 : arrpair.length;
                                            arrpair = new Pair[n + n2];
                                            n2 = n;
                                            if (n != 0) {
                                                System.arraycopy(this.validationStates, 0, arrpair, 0, n);
                                                n2 = n;
                                            }
                                            while (n2 < arrpair.length - 1) {
                                                arrpair[n2] = new Pair();
                                                codedInputByteBufferNano.readMessage(arrpair[n2]);
                                                codedInputByteBufferNano.readTag();
                                                ++n2;
                                            }
                                            arrpair[n2] = new Pair();
                                            codedInputByteBufferNano.readMessage(arrpair[n2]);
                                            this.validationStates = arrpair;
                                            continue;
                                        }
                                        n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 58);
                                        arrpair = this.validationEvents;
                                        n = arrpair == null ? 0 : arrpair.length;
                                        arrpair = new Pair[n + n2];
                                        n2 = n;
                                        if (n != 0) {
                                            System.arraycopy(this.validationEvents, 0, arrpair, 0, n);
                                            n2 = n;
                                        }
                                        while (n2 < arrpair.length - 1) {
                                            arrpair[n2] = new Pair();
                                            codedInputByteBufferNano.readMessage(arrpair[n2]);
                                            codedInputByteBufferNano.readTag();
                                            ++n2;
                                        }
                                        arrpair[n2] = new Pair();
                                        codedInputByteBufferNano.readMessage(arrpair[n2]);
                                        this.validationEvents = arrpair;
                                        continue;
                                    }
                                    this.validationAttempts = codedInputByteBufferNano.readInt32();
                                    continue;
                                }
                                this.noConnectivityReports = codedInputByteBufferNano.readInt32();
                                continue;
                            }
                            this.portalFound = codedInputByteBufferNano.readBool();
                            continue;
                        }
                        this.everValidated = codedInputByteBufferNano.readBool();
                        continue;
                    }
                    n = codedInputByteBufferNano.readInt32();
                    if (n != 0 && n != 1 && n != 2 && n != 3) continue;
                    this.ipSupport = n;
                    continue;
                }
                this.durationMs = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            boolean bl;
            Object object;
            int n;
            long l = this.durationMs;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((n = this.ipSupport) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if (bl = this.everValidated) {
                codedOutputByteBufferNano.writeBool(3, bl);
            }
            if (bl = this.portalFound) {
                codedOutputByteBufferNano.writeBool(4, bl);
            }
            if ((n = this.noConnectivityReports) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if ((n = this.validationAttempts) != 0) {
                codedOutputByteBufferNano.writeInt32(6, n);
            }
            if ((object = this.validationEvents) != null && ((Pair[])object).length > 0) {
                for (n = 0; n < ((Pair[])(object = this.validationEvents)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(7, (MessageNano)object);
                }
            }
            if ((object = this.validationStates) != null && ((Pair[])object).length > 0) {
                for (n = 0; n < ((Pair[])(object = this.validationStates)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
                }
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class Pair
    extends MessageNano {
        private static volatile Pair[] _emptyArray;
        public int key;
        public int value;

        public Pair() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static Pair[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new Pair[0];
                return _emptyArray;
            }
        }

        public static Pair parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new Pair().mergeFrom(codedInputByteBufferNano);
        }

        public static Pair parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new Pair(), arrby);
        }

        public Pair clear() {
            this.key = 0;
            this.value = 0;
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
            n2 = this.value;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            return n;
        }

        @Override
        public Pair mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                        return this;
                    }
                    this.value = codedInputByteBufferNano.readInt32();
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
            if ((n = this.value) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class RaEvent
    extends MessageNano {
        private static volatile RaEvent[] _emptyArray;
        public long dnsslLifetime;
        public long prefixPreferredLifetime;
        public long prefixValidLifetime;
        public long rdnssLifetime;
        public long routeInfoLifetime;
        public long routerLifetime;

        public RaEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static RaEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new RaEvent[0];
                return _emptyArray;
            }
        }

        public static RaEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new RaEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static RaEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new RaEvent(), arrby);
        }

        public RaEvent clear() {
            this.routerLifetime = 0L;
            this.prefixValidLifetime = 0L;
            this.prefixPreferredLifetime = 0L;
            this.routeInfoLifetime = 0L;
            this.rdnssLifetime = 0L;
            this.dnsslLifetime = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.routerLifetime;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            l = this.prefixValidLifetime;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            l = this.prefixPreferredLifetime;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.routeInfoLifetime;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.rdnssLifetime;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.dnsslLifetime;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            return n;
        }

        @Override
        public RaEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (n != 48) {
                                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                        return this;
                                    }
                                    this.dnsslLifetime = codedInputByteBufferNano.readInt64();
                                    continue;
                                }
                                this.rdnssLifetime = codedInputByteBufferNano.readInt64();
                                continue;
                            }
                            this.routeInfoLifetime = codedInputByteBufferNano.readInt64();
                            continue;
                        }
                        this.prefixPreferredLifetime = codedInputByteBufferNano.readInt64();
                        continue;
                    }
                    this.prefixValidLifetime = codedInputByteBufferNano.readInt64();
                    continue;
                }
                this.routerLifetime = codedInputByteBufferNano.readInt64();
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            long l = this.routerLifetime;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.prefixValidLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((l = this.prefixPreferredLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.routeInfoLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.rdnssLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.dnsslLifetime) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class ValidationProbeEvent
    extends MessageNano {
        private static volatile ValidationProbeEvent[] _emptyArray;
        public int latencyMs;
        public NetworkId networkId;
        public int probeResult;
        public int probeType;

        public ValidationProbeEvent() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static ValidationProbeEvent[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new ValidationProbeEvent[0];
                return _emptyArray;
            }
        }

        public static ValidationProbeEvent parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new ValidationProbeEvent().mergeFrom(codedInputByteBufferNano);
        }

        public static ValidationProbeEvent parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new ValidationProbeEvent(), arrby);
        }

        public ValidationProbeEvent clear() {
            this.networkId = null;
            this.latencyMs = 0;
            this.probeType = 0;
            this.probeResult = 0;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            NetworkId networkId = this.networkId;
            int n2 = n;
            if (networkId != null) {
                n2 = n + CodedOutputByteBufferNano.computeMessageSize(1, networkId);
            }
            int n3 = this.latencyMs;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(2, n3);
            }
            n3 = this.probeType;
            n2 = n;
            if (n3 != 0) {
                n2 = n + CodedOutputByteBufferNano.computeInt32Size(3, n3);
            }
            n3 = this.probeResult;
            n = n2;
            if (n3 != 0) {
                n = n2 + CodedOutputByteBufferNano.computeInt32Size(4, n3);
            }
            return n;
        }

        @Override
        public ValidationProbeEvent mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            int n;
            while ((n = codedInputByteBufferNano.readTag()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue;
                                return this;
                            }
                            this.probeResult = codedInputByteBufferNano.readInt32();
                            continue;
                        }
                        this.probeType = codedInputByteBufferNano.readInt32();
                        continue;
                    }
                    this.latencyMs = codedInputByteBufferNano.readInt32();
                    continue;
                }
                if (this.networkId == null) {
                    this.networkId = new NetworkId();
                }
                codedInputByteBufferNano.readMessage(this.networkId);
            }
            return this;
        }

        @Override
        public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
            int n;
            NetworkId networkId = this.networkId;
            if (networkId != null) {
                codedOutputByteBufferNano.writeMessage(1, networkId);
            }
            if ((n = this.latencyMs) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.probeType) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.probeResult) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

    public static final class WakeupStats
    extends MessageNano {
        private static volatile WakeupStats[] _emptyArray;
        public long applicationWakeups;
        public long durationSec;
        public Pair[] ethertypeCounts;
        public Pair[] ipNextHeaderCounts;
        public long l2BroadcastCount;
        public long l2MulticastCount;
        public long l2UnicastCount;
        public long noUidWakeups;
        public long nonApplicationWakeups;
        public long rootWakeups;
        public long systemWakeups;
        public long totalWakeups;

        public WakeupStats() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static WakeupStats[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new WakeupStats[0];
                return _emptyArray;
            }
        }

        public static WakeupStats parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new WakeupStats().mergeFrom(codedInputByteBufferNano);
        }

        public static WakeupStats parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new WakeupStats(), arrby);
        }

        public WakeupStats clear() {
            this.durationSec = 0L;
            this.totalWakeups = 0L;
            this.rootWakeups = 0L;
            this.systemWakeups = 0L;
            this.applicationWakeups = 0L;
            this.nonApplicationWakeups = 0L;
            this.noUidWakeups = 0L;
            this.ethertypeCounts = Pair.emptyArray();
            this.ipNextHeaderCounts = Pair.emptyArray();
            this.l2UnicastCount = 0L;
            this.l2MulticastCount = 0L;
            this.l2BroadcastCount = 0L;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            long l = this.durationSec;
            int n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(1, l);
            }
            l = this.totalWakeups;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(2, l);
            }
            l = this.rootWakeups;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(3, l);
            }
            l = this.systemWakeups;
            int n3 = n2;
            if (l != 0L) {
                n3 = n2 + CodedOutputByteBufferNano.computeInt64Size(4, l);
            }
            l = this.applicationWakeups;
            n = n3;
            if (l != 0L) {
                n = n3 + CodedOutputByteBufferNano.computeInt64Size(5, l);
            }
            l = this.nonApplicationWakeups;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(6, l);
            }
            l = this.noUidWakeups;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(7, l);
            }
            Object object = this.ethertypeCounts;
            n2 = n;
            if (object != null) {
                n2 = n;
                if (((Pair[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.ethertypeCounts;
                        n2 = n;
                        if (n3 >= ((Pair[])object).length) break;
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
            object = this.ipNextHeaderCounts;
            n = n2;
            if (object != null) {
                n = n2;
                if (((Pair[])object).length > 0) {
                    n3 = 0;
                    do {
                        object = this.ipNextHeaderCounts;
                        n = n2;
                        if (n3 >= ((Pair[])object).length) break;
                        object = object[n3];
                        n = n2;
                        if (object != null) {
                            n = n2 + CodedOutputByteBufferNano.computeMessageSize(9, (MessageNano)object);
                        }
                        ++n3;
                        n2 = n;
                    } while (true);
                }
            }
            l = this.l2UnicastCount;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(10, l);
            }
            l = this.l2MulticastCount;
            n = n2;
            if (l != 0L) {
                n = n2 + CodedOutputByteBufferNano.computeInt64Size(11, l);
            }
            l = this.l2BroadcastCount;
            n2 = n;
            if (l != 0L) {
                n2 = n + CodedOutputByteBufferNano.computeInt64Size(12, l);
            }
            return n2;
        }

        @Override
        public WakeupStats mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block15 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block15;
                        return this;
                    }
                    case 96: {
                        this.l2BroadcastCount = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 88: {
                        this.l2MulticastCount = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 80: {
                        this.l2UnicastCount = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 74: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 74);
                        Pair[] arrpair = this.ipNextHeaderCounts;
                        n = arrpair == null ? 0 : arrpair.length;
                        arrpair = new Pair[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.ipNextHeaderCounts, 0, arrpair, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrpair.length - 1) {
                            arrpair[n2] = new Pair();
                            codedInputByteBufferNano.readMessage(arrpair[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrpair[n2] = new Pair();
                        codedInputByteBufferNano.readMessage(arrpair[n2]);
                        this.ipNextHeaderCounts = arrpair;
                        continue block15;
                    }
                    case 66: {
                        int n2 = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 66);
                        Pair[] arrpair = this.ethertypeCounts;
                        n = arrpair == null ? 0 : arrpair.length;
                        arrpair = new Pair[n + n2];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.ethertypeCounts, 0, arrpair, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrpair.length - 1) {
                            arrpair[n2] = new Pair();
                            codedInputByteBufferNano.readMessage(arrpair[n2]);
                            codedInputByteBufferNano.readTag();
                            ++n2;
                        }
                        arrpair[n2] = new Pair();
                        codedInputByteBufferNano.readMessage(arrpair[n2]);
                        this.ethertypeCounts = arrpair;
                        continue block15;
                    }
                    case 56: {
                        this.noUidWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 48: {
                        this.nonApplicationWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 40: {
                        this.applicationWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 32: {
                        this.systemWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 24: {
                        this.rootWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 16: {
                        this.totalWakeups = codedInputByteBufferNano.readInt64();
                        continue block15;
                    }
                    case 8: {
                        this.durationSec = codedInputByteBufferNano.readInt64();
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
            int n;
            Object object;
            long l = this.durationSec;
            if (l != 0L) {
                codedOutputByteBufferNano.writeInt64(1, l);
            }
            if ((l = this.totalWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(2, l);
            }
            if ((l = this.rootWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(3, l);
            }
            if ((l = this.systemWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(4, l);
            }
            if ((l = this.applicationWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(5, l);
            }
            if ((l = this.nonApplicationWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(6, l);
            }
            if ((l = this.noUidWakeups) != 0L) {
                codedOutputByteBufferNano.writeInt64(7, l);
            }
            if ((object = this.ethertypeCounts) != null && ((Pair[])object).length > 0) {
                for (n = 0; n < ((Pair[])(object = this.ethertypeCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(8, (MessageNano)object);
                }
            }
            if ((object = this.ipNextHeaderCounts) != null && ((Pair[])object).length > 0) {
                for (n = 0; n < ((Pair[])(object = this.ipNextHeaderCounts)).length; ++n) {
                    if ((object = object[n]) == null) continue;
                    codedOutputByteBufferNano.writeMessage(9, (MessageNano)object);
                }
            }
            if ((l = this.l2UnicastCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(10, l);
            }
            if ((l = this.l2MulticastCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(11, l);
            }
            if ((l = this.l2BroadcastCount) != 0L) {
                codedOutputByteBufferNano.writeInt64(12, l);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

}

