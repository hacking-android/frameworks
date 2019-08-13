/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ContextHubMsg {
    public long appName;
    public short hostEndPoint;
    public ArrayList<Byte> msg = new ArrayList();
    public int msgType;

    public static final ArrayList<ContextHubMsg> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ContextHubMsg> arrayList = new ArrayList<ContextHubMsg>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new ContextHubMsg();
            ((ContextHubMsg)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((ContextHubMsg)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ContextHubMsg> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != ContextHubMsg.class) {
            return false;
        }
        object = (ContextHubMsg)object;
        if (this.appName != ((ContextHubMsg)object).appName) {
            return false;
        }
        if (this.hostEndPoint != ((ContextHubMsg)object).hostEndPoint) {
            return false;
        }
        if (this.msgType != ((ContextHubMsg)object).msgType) {
            return false;
        }
        return HidlSupport.deepEquals(this.msg, ((ContextHubMsg)object).msg);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.appName), HidlSupport.deepHashCode(this.hostEndPoint), HidlSupport.deepHashCode(this.msgType), HidlSupport.deepHashCode(this.msg));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.appName = hwBlob.getInt64(l + 0L);
        this.hostEndPoint = hwBlob.getInt16(l + 8L);
        this.msgType = hwBlob.getInt32(l + 12L);
        int n = hwBlob.getInt32(l + 16L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 16L + 0L, true);
        this.msg.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.msg.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".appName = ");
        stringBuilder.append(this.appName);
        stringBuilder.append(", .hostEndPoint = ");
        stringBuilder.append(this.hostEndPoint);
        stringBuilder.append(", .msgType = ");
        stringBuilder.append(this.msgType);
        stringBuilder.append(", .msg = ");
        stringBuilder.append(this.msg);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt64(l + 0L, this.appName);
        hwBlob.putInt16(l + 8L, this.hostEndPoint);
        hwBlob.putInt32(l + 12L, this.msgType);
        int n = this.msg.size();
        hwBlob.putInt32(l + 16L + 8L, n);
        hwBlob.putBool(l + 16L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.msg.get(i));
        }
        hwBlob.putBlob(16L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

