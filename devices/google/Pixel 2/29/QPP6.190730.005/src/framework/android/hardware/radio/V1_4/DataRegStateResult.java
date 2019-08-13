/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.LteVopsInfo;
import android.hardware.radio.V1_4.NrIndicators;
import android.internal.hidl.safe_union.V1_0.Monostate;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DataRegStateResult {
    public android.hardware.radio.V1_2.DataRegStateResult base = new android.hardware.radio.V1_2.DataRegStateResult();
    public NrIndicators nrIndicators = new NrIndicators();
    public VopsInfo vopsInfo = new VopsInfo();

    public static final ArrayList<DataRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataRegStateResult> arrayList = new ArrayList<DataRegStateResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 112, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new DataRegStateResult();
            ((DataRegStateResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add((DataRegStateResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataRegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 112);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != DataRegStateResult.class) {
            return false;
        }
        object = (DataRegStateResult)object;
        if (!HidlSupport.deepEquals(this.base, ((DataRegStateResult)object).base)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.vopsInfo, ((DataRegStateResult)object).vopsInfo)) {
            return false;
        }
        return HidlSupport.deepEquals(this.nrIndicators, ((DataRegStateResult)object).nrIndicators);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.vopsInfo), HidlSupport.deepHashCode(this.nrIndicators));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.vopsInfo.readEmbeddedFromParcel(hwParcel, hwBlob, 104L + l);
        this.nrIndicators.readEmbeddedFromParcel(hwParcel, hwBlob, 107L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .vopsInfo = ");
        stringBuilder.append(this.vopsInfo);
        stringBuilder.append(", .nrIndicators = ");
        stringBuilder.append(this.nrIndicators);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.vopsInfo.writeEmbeddedToBlob(hwBlob, 104L + l);
        this.nrIndicators.writeEmbeddedToBlob(hwBlob, 107L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final class VopsInfo {
        private byte hidl_d = (byte)(false ? 1 : 0);
        private Object hidl_o = new Monostate();

        public static final ArrayList<VopsInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<VopsInfo> arrayList = new ArrayList<VopsInfo>();
            Object object = hwParcel.readBuffer(16L);
            int n = ((HwBlob)object).getInt32(8L);
            HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 3, ((HwBlob)object).handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < n; ++i) {
                object = new VopsInfo();
                ((VopsInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 3);
                arrayList.add((VopsInfo)object);
            }
            return arrayList;
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<VopsInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int n = arrayList.size();
            hwBlob.putInt32(8L, n);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(n * 3);
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (object.getClass() != VopsInfo.class) {
                return false;
            }
            object = (VopsInfo)object;
            if (this.hidl_d != ((VopsInfo)object).hidl_d) {
                return false;
            }
            return HidlSupport.deepEquals(this.hidl_o, ((VopsInfo)object).hidl_o);
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final int hashCode() {
            return Objects.hash(HidlSupport.deepHashCode(this.hidl_o), Objects.hashCode(this.hidl_d));
        }

        public LteVopsInfo lteVopsInfo() {
            if (this.hidl_d != 1) {
                Object object = this.hidl_o;
                object = object != null ? object.getClass().getName() : "null";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Read access to inactive union components is disallowed. Discriminator value is ");
                stringBuilder.append(this.hidl_d);
                stringBuilder.append(" (corresponding to ");
                stringBuilder.append(hidl_discriminator.getName(this.hidl_d));
                stringBuilder.append("), and hidl_o is of type ");
                stringBuilder.append((String)object);
                stringBuilder.append(".");
                throw new IllegalStateException(stringBuilder.toString());
            }
            Object object = this.hidl_o;
            if (object != null && !LteVopsInfo.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (LteVopsInfo)this.hidl_o;
        }

        public void lteVopsInfo(LteVopsInfo lteVopsInfo) {
            this.hidl_d = (byte)(true ? 1 : 0);
            this.hidl_o = lteVopsInfo;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object object = this.hidl_o;
                object = object != null ? object.getClass().getName() : "null";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Read access to inactive union components is disallowed. Discriminator value is ");
                stringBuilder.append(this.hidl_d);
                stringBuilder.append(" (corresponding to ");
                stringBuilder.append(hidl_discriminator.getName(this.hidl_d));
                stringBuilder.append("), and hidl_o is of type ");
                stringBuilder.append((String)object);
                stringBuilder.append(".");
                throw new IllegalStateException(stringBuilder.toString());
            }
            Object object = this.hidl_o;
            if (object != null && !Monostate.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate)this.hidl_o;
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte)(false ? 1 : 0);
            this.hidl_o = monostate;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
            this.hidl_d = hwBlob.getInt8(0L + l);
            byte by = this.hidl_d;
            if (by == 0) {
                this.hidl_o = new Monostate();
                ((Monostate)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 1L + l);
                return;
            }
            if (by == 1) {
                this.hidl_o = new LteVopsInfo();
                ((LteVopsInfo)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 1L + l);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown union discriminator (value: ");
            ((StringBuilder)object).append(this.hidl_d);
            ((StringBuilder)object).append(").");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        public final void readFromParcel(HwParcel hwParcel) {
            this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
        }

        /*
         * Enabled aggressive block sorting
         */
        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            byte by = this.hidl_d;
            if (by != 0) {
                if (by != 1) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown union discriminator (value: ");
                    stringBuilder.append(this.hidl_d);
                    stringBuilder.append(").");
                    throw new Error(stringBuilder.toString());
                }
                stringBuilder.append(".lteVopsInfo = ");
                stringBuilder.append(this.lteVopsInfo());
            } else {
                stringBuilder.append(".noinit = ");
                stringBuilder.append(this.noinit());
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void writeEmbeddedToBlob(HwBlob object, long l) {
            ((HwBlob)object).putInt8(0L + l, this.hidl_d);
            byte by = this.hidl_d;
            if (by == 0) {
                this.noinit().writeEmbeddedToBlob((HwBlob)object, 1L + l);
                return;
            }
            if (by == 1) {
                this.lteVopsInfo().writeEmbeddedToBlob((HwBlob)object, 1L + l);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown union discriminator (value: ");
            ((StringBuilder)object).append(this.hidl_d);
            ((StringBuilder)object).append(").");
            throw new Error(((StringBuilder)object).toString());
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(3);
            this.writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final class hidl_discriminator {
            public static final byte lteVopsInfo = 1;
            public static final byte noinit = 0;

            private hidl_discriminator() {
            }

            public static final String getName(byte by) {
                if (by != 0) {
                    if (by != 1) {
                        return "Unknown";
                    }
                    return "lteVopsInfo";
                }
                return "noinit";
            }
        }

    }

}

