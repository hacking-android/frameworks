/*
 * Decompiled with CFR 0.145.
 */
package java.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;

final class Ser
implements Externalizable {
    static final byte ZOT = 2;
    static final byte ZOTRULE = 3;
    static final byte ZRULES = 1;
    private static final long serialVersionUID = -8885321777449118786L;
    private Object object;
    private byte type;

    public Ser() {
    }

    Ser(byte by, Object object) {
        this.type = by;
        this.object = object;
    }

    static Object read(DataInput dataInput) throws IOException, ClassNotFoundException {
        return Ser.readInternal(dataInput.readByte(), dataInput);
    }

    static long readEpochSec(DataInput dataInput) throws IOException {
        int n = dataInput.readByte() & 255;
        if (n == 255) {
            return dataInput.readLong();
        }
        return 900L * (long)((n << 16) + ((dataInput.readByte() & 255) << 8) + (255 & dataInput.readByte())) - 4575744000L;
    }

    private static Object readInternal(byte by, DataInput dataInput) throws IOException, ClassNotFoundException {
        if (by != 1) {
            if (by != 2) {
                if (by == 3) {
                    return ZoneOffsetTransitionRule.readExternal(dataInput);
                }
                throw new StreamCorruptedException("Unknown serialized type");
            }
            return ZoneOffsetTransition.readExternal(dataInput);
        }
        return ZoneRules.readExternal(dataInput);
    }

    static ZoneOffset readOffset(DataInput dataInput) throws IOException {
        int n = dataInput.readByte();
        n = n == 127 ? dataInput.readInt() : (n *= 900);
        return ZoneOffset.ofTotalSeconds(n);
    }

    private Object readResolve() {
        return this.object;
    }

    static void write(Object object, DataOutput dataOutput) throws IOException {
        Ser.writeInternal((byte)1, object, dataOutput);
    }

    static void writeEpochSec(long l, DataOutput dataOutput) throws IOException {
        if (l >= -4575744000L && l < 10413792000L && l % 900L == 0L) {
            int n = (int)((4575744000L + l) / 900L);
            dataOutput.writeByte(n >>> 16 & 255);
            dataOutput.writeByte(255 & n >>> 8);
            dataOutput.writeByte(n & 255);
        } else {
            dataOutput.writeByte(255);
            dataOutput.writeLong(l);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void writeInternal(byte by, Object object, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(by);
        if (by != 1) {
            if (by != 2) {
                if (by != 3) throw new InvalidClassException("Unknown serialized type");
                ((ZoneOffsetTransitionRule)object).writeExternal(dataOutput);
                return;
            } else {
                ((ZoneOffsetTransition)object).writeExternal(dataOutput);
            }
            return;
        } else {
            ((ZoneRules)object).writeExternal(dataOutput);
        }
    }

    static void writeOffset(ZoneOffset zoneOffset, DataOutput dataOutput) throws IOException {
        int n = zoneOffset.getTotalSeconds();
        int n2 = n % 900 == 0 ? n / 900 : 127;
        dataOutput.writeByte(n2);
        if (n2 == 127) {
            dataOutput.writeInt(n);
        }
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.type = objectInput.readByte();
        this.object = Ser.readInternal(this.type, objectInput);
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        Ser.writeInternal(this.type, this.object, objectOutput);
    }
}

