/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaDisplayInfoRecord;
import android.hardware.radio.V1_0.CdmaInfoRecName;
import android.hardware.radio.V1_0.CdmaLineControlInfoRecord;
import android.hardware.radio.V1_0.CdmaNumberInfoRecord;
import android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord;
import android.hardware.radio.V1_0.CdmaT53ClirInfoRecord;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaInformationRecord {
    public ArrayList<CdmaT53AudioControlInfoRecord> audioCtrl = new ArrayList();
    public ArrayList<CdmaT53ClirInfoRecord> clir = new ArrayList();
    public ArrayList<CdmaDisplayInfoRecord> display = new ArrayList();
    public ArrayList<CdmaLineControlInfoRecord> lineCtrl = new ArrayList();
    public int name;
    public ArrayList<CdmaNumberInfoRecord> number = new ArrayList();
    public ArrayList<CdmaRedirectingNumberInfoRecord> redir = new ArrayList();
    public ArrayList<CdmaSignalInfoRecord> signal = new ArrayList();

    public static final ArrayList<CdmaInformationRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaInformationRecord> arrayList = new ArrayList<CdmaInformationRecord>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 120, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaInformationRecord cdmaInformationRecord = new CdmaInformationRecord();
            cdmaInformationRecord.readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            arrayList.add(cdmaInformationRecord);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaInformationRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 120);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
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
        if (object.getClass() != CdmaInformationRecord.class) {
            return false;
        }
        object = (CdmaInformationRecord)object;
        if (this.name != ((CdmaInformationRecord)object).name) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.display, ((CdmaInformationRecord)object).display)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.number, ((CdmaInformationRecord)object).number)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.signal, ((CdmaInformationRecord)object).signal)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.redir, ((CdmaInformationRecord)object).redir)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.lineCtrl, ((CdmaInformationRecord)object).lineCtrl)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.clir, ((CdmaInformationRecord)object).clir)) {
            return false;
        }
        return HidlSupport.deepEquals(this.audioCtrl, ((CdmaInformationRecord)object).audioCtrl);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.display), HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.signal), HidlSupport.deepHashCode(this.redir), HidlSupport.deepHashCode(this.lineCtrl), HidlSupport.deepHashCode(this.clir), HidlSupport.deepHashCode(this.audioCtrl));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Object object;
        this.name = hwBlob.getInt32(l + 0L);
        int n2 = hwBlob.getInt32(l + 8L + 8L);
        Object object2 = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 8L + 0L, true);
        this.display.clear();
        for (n = 0; n < n2; ++n) {
            object = new CdmaDisplayInfoRecord();
            ((CdmaDisplayInfoRecord)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 16);
            this.display.add((CdmaDisplayInfoRecord)object);
        }
        n2 = hwBlob.getInt32(l + 24L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 24, hwBlob.handle(), l + 24L + 0L, true);
        this.number.clear();
        for (n = 0; n < n2; ++n) {
            object = new CdmaNumberInfoRecord();
            ((CdmaNumberInfoRecord)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 24);
            this.number.add((CdmaNumberInfoRecord)object);
        }
        n2 = hwBlob.getInt32(l + 40L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 4, hwBlob.handle(), l + 40L + 0L, true);
        this.signal.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CdmaSignalInfoRecord();
            ((CdmaSignalInfoRecord)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 4);
            this.signal.add((CdmaSignalInfoRecord)object2);
        }
        n2 = hwBlob.getInt32(l + 56L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 32, hwBlob.handle(), l + 56L + 0L, true);
        this.redir.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CdmaRedirectingNumberInfoRecord();
            ((CdmaRedirectingNumberInfoRecord)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 32);
            this.redir.add((CdmaRedirectingNumberInfoRecord)object2);
        }
        n2 = hwBlob.getInt32(l + 72L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 4, hwBlob.handle(), l + 72L + 0L, true);
        this.lineCtrl.clear();
        for (n = 0; n < n2; ++n) {
            object = new CdmaLineControlInfoRecord();
            ((CdmaLineControlInfoRecord)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 4);
            this.lineCtrl.add((CdmaLineControlInfoRecord)object);
        }
        n2 = hwBlob.getInt32(l + 88L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 1, hwBlob.handle(), l + 88L + 0L, true);
        this.clir.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CdmaT53ClirInfoRecord();
            ((CdmaT53ClirInfoRecord)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 1);
            this.clir.add((CdmaT53ClirInfoRecord)object2);
        }
        n2 = hwBlob.getInt32(l + 104L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n2 * 2, hwBlob.handle(), l + 104L + 0L, true);
        this.audioCtrl.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CdmaT53AudioControlInfoRecord();
            ((CdmaT53AudioControlInfoRecord)object2).readEmbeddedFromParcel(hwParcel, hwBlob, n * 2);
            this.audioCtrl.add((CdmaT53AudioControlInfoRecord)object2);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".name = ");
        stringBuilder.append(CdmaInfoRecName.toString(this.name));
        stringBuilder.append(", .display = ");
        stringBuilder.append(this.display);
        stringBuilder.append(", .number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .signal = ");
        stringBuilder.append(this.signal);
        stringBuilder.append(", .redir = ");
        stringBuilder.append(this.redir);
        stringBuilder.append(", .lineCtrl = ");
        stringBuilder.append(this.lineCtrl);
        stringBuilder.append(", .clir = ");
        stringBuilder.append(this.clir);
        stringBuilder.append(", .audioCtrl = ");
        stringBuilder.append(this.audioCtrl);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.name);
        int n2 = this.display.size();
        hwBlob.putInt32(l + 8L + 8L, n2);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            this.display.get(n).writeEmbeddedToBlob(hwBlob2, n * 16);
        }
        hwBlob.putBlob(l + 8L + 0L, hwBlob2);
        n2 = this.number.size();
        hwBlob.putInt32(l + 24L + 8L, n2);
        hwBlob.putBool(l + 24L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 24);
        for (n = 0; n < n2; ++n) {
            this.number.get(n).writeEmbeddedToBlob(hwBlob2, n * 24);
        }
        hwBlob.putBlob(l + 24L + 0L, hwBlob2);
        n2 = this.signal.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            this.signal.get(n).writeEmbeddedToBlob(hwBlob2, n * 4);
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
        n2 = this.redir.size();
        hwBlob.putInt32(l + 56L + 8L, n2);
        hwBlob.putBool(l + 56L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 32);
        for (n = 0; n < n2; ++n) {
            this.redir.get(n).writeEmbeddedToBlob(hwBlob2, n * 32);
        }
        hwBlob.putBlob(l + 56L + 0L, hwBlob2);
        n2 = this.lineCtrl.size();
        hwBlob.putInt32(l + 72L + 8L, n2);
        hwBlob.putBool(l + 72L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            this.lineCtrl.get(n).writeEmbeddedToBlob(hwBlob2, n * 4);
        }
        hwBlob.putBlob(l + 72L + 0L, hwBlob2);
        n2 = this.clir.size();
        hwBlob.putInt32(l + 88L + 8L, n2);
        hwBlob.putBool(l + 88L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 1);
        for (n = 0; n < n2; ++n) {
            this.clir.get(n).writeEmbeddedToBlob(hwBlob2, n * 1);
        }
        hwBlob.putBlob(l + 88L + 0L, hwBlob2);
        n2 = this.audioCtrl.size();
        hwBlob.putInt32(l + 104L + 8L, n2);
        hwBlob.putBool(l + 104L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 2);
        for (n = 0; n < n2; ++n) {
            this.audioCtrl.get(n).writeEmbeddedToBlob(hwBlob2, n * 2);
        }
        hwBlob.putBlob(l + 104L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

