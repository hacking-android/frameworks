/*
 * Decompiled with CFR 0.145.
 */
package com.android.server.wm.nano;

import com.android.framework.protobuf.nano.CodedInputByteBufferNano;
import com.android.framework.protobuf.nano.CodedOutputByteBufferNano;
import com.android.framework.protobuf.nano.InternalNano;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.framework.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface WindowManagerProtos {

    public static final class TaskSnapshotProto
    extends MessageNano {
        private static volatile TaskSnapshotProto[] _emptyArray;
        public int insetBottom;
        public int insetLeft;
        public int insetRight;
        public int insetTop;
        public boolean isRealSnapshot;
        public boolean isTranslucent;
        public int orientation;
        public float scale;
        public int systemUiVisibility;
        public String topActivityComponent;
        public int windowingMode;

        public TaskSnapshotProto() {
            this.clear();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TaskSnapshotProto[] emptyArray() {
            if (_emptyArray != null) return _emptyArray;
            Object object = InternalNano.LAZY_INIT_LOCK;
            synchronized (object) {
                if (_emptyArray != null) return _emptyArray;
                _emptyArray = new TaskSnapshotProto[0];
                return _emptyArray;
            }
        }

        public static TaskSnapshotProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            return new TaskSnapshotProto().mergeFrom(codedInputByteBufferNano);
        }

        public static TaskSnapshotProto parseFrom(byte[] arrby) throws InvalidProtocolBufferNanoException {
            return MessageNano.mergeFrom(new TaskSnapshotProto(), arrby);
        }

        public TaskSnapshotProto clear() {
            this.orientation = 0;
            this.insetLeft = 0;
            this.insetTop = 0;
            this.insetRight = 0;
            this.insetBottom = 0;
            this.isRealSnapshot = false;
            this.windowingMode = 0;
            this.systemUiVisibility = 0;
            this.isTranslucent = false;
            this.topActivityComponent = "";
            this.scale = 0.0f;
            this.cachedSize = -1;
            return this;
        }

        @Override
        protected int computeSerializedSize() {
            int n = super.computeSerializedSize();
            int n2 = this.orientation;
            int n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(1, n2);
            }
            n2 = this.insetLeft;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(2, n2);
            }
            n2 = this.insetTop;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(3, n2);
            }
            n2 = this.insetRight;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(4, n2);
            }
            n2 = this.insetBottom;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(5, n2);
            }
            boolean bl = this.isRealSnapshot;
            n = n3;
            if (bl) {
                n = n3 + CodedOutputByteBufferNano.computeBoolSize(6, bl);
            }
            n2 = this.windowingMode;
            n3 = n;
            if (n2 != 0) {
                n3 = n + CodedOutputByteBufferNano.computeInt32Size(7, n2);
            }
            n2 = this.systemUiVisibility;
            n = n3;
            if (n2 != 0) {
                n = n3 + CodedOutputByteBufferNano.computeInt32Size(8, n2);
            }
            bl = this.isTranslucent;
            n3 = n;
            if (bl) {
                n3 = n + CodedOutputByteBufferNano.computeBoolSize(9, bl);
            }
            n = n3;
            if (!this.topActivityComponent.equals("")) {
                n = n3 + CodedOutputByteBufferNano.computeStringSize(10, this.topActivityComponent);
            }
            n3 = n;
            if (Float.floatToIntBits(this.scale) != Float.floatToIntBits(0.0f)) {
                n3 = n + CodedOutputByteBufferNano.computeFloatSize(11, this.scale);
            }
            return n3;
        }

        @Override
        public TaskSnapshotProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
            block14 : do {
                int n = codedInputByteBufferNano.readTag();
                switch (n) {
                    default: {
                        if (WireFormatNano.parseUnknownField(codedInputByteBufferNano, n)) continue block14;
                        return this;
                    }
                    case 93: {
                        this.scale = codedInputByteBufferNano.readFloat();
                        continue block14;
                    }
                    case 82: {
                        this.topActivityComponent = codedInputByteBufferNano.readString();
                        continue block14;
                    }
                    case 72: {
                        this.isTranslucent = codedInputByteBufferNano.readBool();
                        continue block14;
                    }
                    case 64: {
                        this.systemUiVisibility = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 56: {
                        this.windowingMode = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 48: {
                        this.isRealSnapshot = codedInputByteBufferNano.readBool();
                        continue block14;
                    }
                    case 40: {
                        this.insetBottom = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 32: {
                        this.insetRight = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 24: {
                        this.insetTop = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 16: {
                        this.insetLeft = codedInputByteBufferNano.readInt32();
                        continue block14;
                    }
                    case 8: {
                        this.orientation = codedInputByteBufferNano.readInt32();
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
            int n = this.orientation;
            if (n != 0) {
                codedOutputByteBufferNano.writeInt32(1, n);
            }
            if ((n = this.insetLeft) != 0) {
                codedOutputByteBufferNano.writeInt32(2, n);
            }
            if ((n = this.insetTop) != 0) {
                codedOutputByteBufferNano.writeInt32(3, n);
            }
            if ((n = this.insetRight) != 0) {
                codedOutputByteBufferNano.writeInt32(4, n);
            }
            if ((n = this.insetBottom) != 0) {
                codedOutputByteBufferNano.writeInt32(5, n);
            }
            if (bl = this.isRealSnapshot) {
                codedOutputByteBufferNano.writeBool(6, bl);
            }
            if ((n = this.windowingMode) != 0) {
                codedOutputByteBufferNano.writeInt32(7, n);
            }
            if ((n = this.systemUiVisibility) != 0) {
                codedOutputByteBufferNano.writeInt32(8, n);
            }
            if (bl = this.isTranslucent) {
                codedOutputByteBufferNano.writeBool(9, bl);
            }
            if (!this.topActivityComponent.equals("")) {
                codedOutputByteBufferNano.writeString(10, this.topActivityComponent);
            }
            if (Float.floatToIntBits(this.scale) != Float.floatToIntBits(0.0f)) {
                codedOutputByteBufferNano.writeFloat(11, this.scale);
            }
            super.writeTo(codedOutputByteBufferNano);
        }
    }

}

