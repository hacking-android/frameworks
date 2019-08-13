/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDateTimeImpl;
import java.time.chrono.ChronoPeriodImpl;
import java.time.chrono.ChronoZonedDateTimeImpl;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;

final class Ser
implements Externalizable {
    static final byte CHRONO_LOCAL_DATE_TIME_TYPE = 2;
    static final byte CHRONO_PERIOD_TYPE = 9;
    static final byte CHRONO_TYPE = 1;
    static final byte CHRONO_ZONE_DATE_TIME_TYPE = 3;
    static final byte HIJRAH_DATE_TYPE = 6;
    static final byte JAPANESE_DATE_TYPE = 4;
    static final byte JAPANESE_ERA_TYPE = 5;
    static final byte MINGUO_DATE_TYPE = 7;
    static final byte THAIBUDDHIST_DATE_TYPE = 8;
    private static final long serialVersionUID = -6103370247208168577L;
    private Object object;
    private byte type;

    public Ser() {
    }

    Ser(byte by, Object object) {
        this.type = by;
        this.object = object;
    }

    static Object read(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return Ser.readInternal(objectInput.readByte(), objectInput);
    }

    private static Object readInternal(byte by, ObjectInput objectInput) throws IOException, ClassNotFoundException {
        switch (by) {
            default: {
                throw new StreamCorruptedException("Unknown serialized type");
            }
            case 9: {
                return ChronoPeriodImpl.readExternal(objectInput);
            }
            case 8: {
                return ThaiBuddhistDate.readExternal(objectInput);
            }
            case 7: {
                return MinguoDate.readExternal(objectInput);
            }
            case 6: {
                return HijrahDate.readExternal(objectInput);
            }
            case 5: {
                return JapaneseEra.readExternal(objectInput);
            }
            case 4: {
                return JapaneseDate.readExternal(objectInput);
            }
            case 3: {
                return ChronoZonedDateTimeImpl.readExternal(objectInput);
            }
            case 2: {
                return ChronoLocalDateTimeImpl.readExternal(objectInput);
            }
            case 1: 
        }
        return AbstractChronology.readExternal(objectInput);
    }

    private Object readResolve() {
        return this.object;
    }

    private static void writeInternal(byte by, Object object, ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(by);
        switch (by) {
            default: {
                throw new InvalidClassException("Unknown serialized type");
            }
            case 9: {
                ((ChronoPeriodImpl)object).writeExternal(objectOutput);
                break;
            }
            case 8: {
                ((ThaiBuddhistDate)object).writeExternal(objectOutput);
                break;
            }
            case 7: {
                ((MinguoDate)object).writeExternal(objectOutput);
                break;
            }
            case 6: {
                ((HijrahDate)object).writeExternal(objectOutput);
                break;
            }
            case 5: {
                ((JapaneseEra)object).writeExternal(objectOutput);
                break;
            }
            case 4: {
                ((JapaneseDate)object).writeExternal(objectOutput);
                break;
            }
            case 3: {
                ((ChronoZonedDateTimeImpl)object).writeExternal(objectOutput);
                break;
            }
            case 2: {
                ((ChronoLocalDateTimeImpl)object).writeExternal(objectOutput);
                break;
            }
            case 1: {
                ((AbstractChronology)object).writeExternal(objectOutput);
            }
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

