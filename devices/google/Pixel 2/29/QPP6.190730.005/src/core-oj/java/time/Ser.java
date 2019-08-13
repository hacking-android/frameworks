/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZoneRegion;
import java.time.ZonedDateTime;

final class Ser
implements Externalizable {
    static final byte DURATION_TYPE = 1;
    static final byte INSTANT_TYPE = 2;
    static final byte LOCAL_DATE_TIME_TYPE = 5;
    static final byte LOCAL_DATE_TYPE = 3;
    static final byte LOCAL_TIME_TYPE = 4;
    static final byte MONTH_DAY_TYPE = 13;
    static final byte OFFSET_DATE_TIME_TYPE = 10;
    static final byte OFFSET_TIME_TYPE = 9;
    static final byte PERIOD_TYPE = 14;
    static final byte YEAR_MONTH_TYPE = 12;
    static final byte YEAR_TYPE = 11;
    static final byte ZONE_DATE_TIME_TYPE = 6;
    static final byte ZONE_OFFSET_TYPE = 8;
    static final byte ZONE_REGION_TYPE = 7;
    private static final long serialVersionUID = -7683839454370182990L;
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
            case 14: {
                return Period.readExternal(objectInput);
            }
            case 13: {
                return MonthDay.readExternal(objectInput);
            }
            case 12: {
                return YearMonth.readExternal(objectInput);
            }
            case 11: {
                return Year.readExternal(objectInput);
            }
            case 10: {
                return OffsetDateTime.readExternal(objectInput);
            }
            case 9: {
                return OffsetTime.readExternal(objectInput);
            }
            case 8: {
                return ZoneOffset.readExternal(objectInput);
            }
            case 7: {
                return ZoneRegion.readExternal(objectInput);
            }
            case 6: {
                return ZonedDateTime.readExternal(objectInput);
            }
            case 5: {
                return LocalDateTime.readExternal(objectInput);
            }
            case 4: {
                return LocalTime.readExternal(objectInput);
            }
            case 3: {
                return LocalDate.readExternal(objectInput);
            }
            case 2: {
                return Instant.readExternal(objectInput);
            }
            case 1: 
        }
        return Duration.readExternal(objectInput);
    }

    private Object readResolve() {
        return this.object;
    }

    static void writeInternal(byte by, Object object, ObjectOutput objectOutput) throws IOException {
        objectOutput.writeByte(by);
        switch (by) {
            default: {
                throw new InvalidClassException("Unknown serialized type");
            }
            case 14: {
                ((Period)object).writeExternal(objectOutput);
                break;
            }
            case 13: {
                ((MonthDay)object).writeExternal(objectOutput);
                break;
            }
            case 12: {
                ((YearMonth)object).writeExternal(objectOutput);
                break;
            }
            case 11: {
                ((Year)object).writeExternal(objectOutput);
                break;
            }
            case 10: {
                ((OffsetDateTime)object).writeExternal(objectOutput);
                break;
            }
            case 9: {
                ((OffsetTime)object).writeExternal(objectOutput);
                break;
            }
            case 8: {
                ((ZoneOffset)object).writeExternal(objectOutput);
                break;
            }
            case 7: {
                ((ZoneRegion)object).writeExternal(objectOutput);
                break;
            }
            case 6: {
                ((ZonedDateTime)object).writeExternal(objectOutput);
                break;
            }
            case 5: {
                ((LocalDateTime)object).writeExternal(objectOutput);
                break;
            }
            case 4: {
                ((LocalTime)object).writeExternal(objectOutput);
                break;
            }
            case 3: {
                ((LocalDate)object).writeExternal(objectOutput);
                break;
            }
            case 2: {
                ((Instant)object).writeExternal(objectOutput);
                break;
            }
            case 1: {
                ((Duration)object).writeExternal(objectOutput);
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

