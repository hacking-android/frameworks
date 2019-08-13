/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_2.CellConnectionStatus;
import android.hardware.radio.V1_2.CellInfoCdma;
import android.hardware.radio.V1_2.CellInfoGsm;
import android.hardware.radio.V1_2.CellInfoTdscdma;
import android.hardware.radio.V1_2.CellInfoWcdma;
import android.hardware.radio.V1_4.CellInfoLte;
import android.hardware.radio.V1_4.CellInfoNr;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfo {
    public int connectionStatus;
    public Info info = new Info();
    public boolean isRegistered;

    public static final ArrayList<CellInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfo> arrayList = new ArrayList<CellInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 136, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfo();
            ((CellInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 136);
            arrayList.add((CellInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 136);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 136);
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
        if (object.getClass() != CellInfo.class) {
            return false;
        }
        object = (CellInfo)object;
        if (this.isRegistered != ((CellInfo)object).isRegistered) {
            return false;
        }
        if (this.connectionStatus != ((CellInfo)object).connectionStatus) {
            return false;
        }
        return HidlSupport.deepEquals(this.info, ((CellInfo)object).info);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.isRegistered), HidlSupport.deepHashCode(this.connectionStatus), HidlSupport.deepHashCode(this.info));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.isRegistered = hwBlob.getBool(0L + l);
        this.connectionStatus = hwBlob.getInt32(4L + l);
        this.info.readEmbeddedFromParcel(hwParcel, hwBlob, 8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(136L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".isRegistered = ");
        stringBuilder.append(this.isRegistered);
        stringBuilder.append(", .connectionStatus = ");
        stringBuilder.append(CellConnectionStatus.toString(this.connectionStatus));
        stringBuilder.append(", .info = ");
        stringBuilder.append(this.info);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putBool(0L + l, this.isRegistered);
        hwBlob.putInt32(4L + l, this.connectionStatus);
        this.info.writeEmbeddedToBlob(hwBlob, 8L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(136);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final class Info {
        private byte hidl_d = (byte)(false ? 1 : 0);
        private Object hidl_o = new CellInfoGsm();

        public static final ArrayList<Info> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<Info> arrayList = new ArrayList<Info>();
            HwBlob hwBlob = hwParcel.readBuffer(16L);
            int n = hwBlob.getInt32(8L);
            hwBlob = hwParcel.readEmbeddedBuffer(n * 128, hwBlob.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < n; ++i) {
                Info info = new Info();
                info.readEmbeddedFromParcel(hwParcel, hwBlob, i * 128);
                arrayList.add(info);
            }
            return arrayList;
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Info> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int n = arrayList.size();
            hwBlob.putInt32(8L, n);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(n * 128);
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 128);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public CellInfoCdma cdma() {
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
            if (object != null && !CellInfoCdma.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoCdma)this.hidl_o;
        }

        public void cdma(CellInfoCdma cellInfoCdma) {
            this.hidl_d = (byte)(true ? 1 : 0);
            this.hidl_o = cellInfoCdma;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (object.getClass() != Info.class) {
                return false;
            }
            object = (Info)object;
            if (this.hidl_d != ((Info)object).hidl_d) {
                return false;
            }
            return HidlSupport.deepEquals(this.hidl_o, ((Info)object).hidl_o);
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public CellInfoGsm gsm() {
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
            if (object != null && !CellInfoGsm.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoGsm)this.hidl_o;
        }

        public void gsm(CellInfoGsm cellInfoGsm) {
            this.hidl_d = (byte)(false ? 1 : 0);
            this.hidl_o = cellInfoGsm;
        }

        public final int hashCode() {
            return Objects.hash(HidlSupport.deepHashCode(this.hidl_o), Objects.hashCode(this.hidl_d));
        }

        public CellInfoLte lte() {
            if (this.hidl_d != 4) {
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
            if (object != null && !CellInfoLte.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoLte)this.hidl_o;
        }

        public void lte(CellInfoLte cellInfoLte) {
            this.hidl_d = (byte)4;
            this.hidl_o = cellInfoLte;
        }

        public CellInfoNr nr() {
            if (this.hidl_d != 5) {
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
            if (object != null && !CellInfoNr.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoNr)this.hidl_o;
        }

        public void nr(CellInfoNr cellInfoNr) {
            this.hidl_d = (byte)5;
            this.hidl_o = cellInfoNr;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
            this.hidl_d = hwBlob.getInt8(0L + l);
            byte by = this.hidl_d;
            if (by == 0) {
                this.hidl_o = new CellInfoGsm();
                ((CellInfoGsm)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            if (by == 1) {
                this.hidl_o = new CellInfoCdma();
                ((CellInfoCdma)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            if (by == 2) {
                this.hidl_o = new CellInfoWcdma();
                ((CellInfoWcdma)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            if (by == 3) {
                this.hidl_o = new CellInfoTdscdma();
                ((CellInfoTdscdma)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            if (by == 4) {
                this.hidl_o = new CellInfoLte();
                ((CellInfoLte)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            if (by == 5) {
                this.hidl_o = new CellInfoNr();
                ((CellInfoNr)this.hidl_o).readEmbeddedFromParcel((HwParcel)object, hwBlob, 8L + l);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown union discriminator (value: ");
            ((StringBuilder)object).append(this.hidl_d);
            ((StringBuilder)object).append(").");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        public final void readFromParcel(HwParcel hwParcel) {
            this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(128L), 0L);
        }

        public CellInfoTdscdma tdscdma() {
            if (this.hidl_d != 3) {
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
            if (object != null && !CellInfoTdscdma.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoTdscdma)this.hidl_o;
        }

        public void tdscdma(CellInfoTdscdma cellInfoTdscdma) {
            this.hidl_d = (byte)3;
            this.hidl_o = cellInfoTdscdma;
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
                    if (by != 2) {
                        if (by != 3) {
                            if (by != 4) {
                                if (by != 5) {
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Unknown union discriminator (value: ");
                                    stringBuilder.append(this.hidl_d);
                                    stringBuilder.append(").");
                                    throw new Error(stringBuilder.toString());
                                }
                                stringBuilder.append(".nr = ");
                                stringBuilder.append(this.nr());
                            } else {
                                stringBuilder.append(".lte = ");
                                stringBuilder.append(this.lte());
                            }
                        } else {
                            stringBuilder.append(".tdscdma = ");
                            stringBuilder.append(this.tdscdma());
                        }
                    } else {
                        stringBuilder.append(".wcdma = ");
                        stringBuilder.append(this.wcdma());
                    }
                } else {
                    stringBuilder.append(".cdma = ");
                    stringBuilder.append(this.cdma());
                }
            } else {
                stringBuilder.append(".gsm = ");
                stringBuilder.append(this.gsm());
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public CellInfoWcdma wcdma() {
            if (this.hidl_d != 2) {
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
            if (object != null && !CellInfoWcdma.class.isInstance(object)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoWcdma)this.hidl_o;
        }

        public void wcdma(CellInfoWcdma cellInfoWcdma) {
            this.hidl_d = (byte)2;
            this.hidl_o = cellInfoWcdma;
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void writeEmbeddedToBlob(HwBlob object, long l) {
            ((HwBlob)object).putInt8(0L + l, this.hidl_d);
            byte by = this.hidl_d;
            if (by == 0) {
                this.gsm().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            if (by == 1) {
                this.cdma().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            if (by == 2) {
                this.wcdma().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            if (by == 3) {
                this.tdscdma().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            if (by == 4) {
                this.lte().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            if (by == 5) {
                this.nr().writeEmbeddedToBlob((HwBlob)object, 8L + l);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown union discriminator (value: ");
            ((StringBuilder)object).append(this.hidl_d);
            ((StringBuilder)object).append(").");
            throw new Error(((StringBuilder)object).toString());
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(128);
            this.writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final class hidl_discriminator {
            public static final byte cdma = 1;
            public static final byte gsm = 0;
            public static final byte lte = 4;
            public static final byte nr = 5;
            public static final byte tdscdma = 3;
            public static final byte wcdma = 2;

            private hidl_discriminator() {
            }

            public static final String getName(byte by) {
                if (by != 0) {
                    if (by != 1) {
                        if (by != 2) {
                            if (by != 3) {
                                if (by != 4) {
                                    if (by != 5) {
                                        return "Unknown";
                                    }
                                    return "nr";
                                }
                                return "lte";
                            }
                            return "tdscdma";
                        }
                        return "wcdma";
                    }
                    return "cdma";
                }
                return "gsm";
            }
        }

    }

}

