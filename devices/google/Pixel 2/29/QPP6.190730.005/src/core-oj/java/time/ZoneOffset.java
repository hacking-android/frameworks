/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Ser;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.zone.ZoneRules;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ZoneOffset
extends ZoneId
implements TemporalAccessor,
TemporalAdjuster,
Comparable<ZoneOffset>,
Serializable {
    private static final ConcurrentMap<String, ZoneOffset> ID_CACHE;
    public static final ZoneOffset MAX;
    private static final int MAX_SECONDS = 64800;
    public static final ZoneOffset MIN;
    private static final ConcurrentMap<Integer, ZoneOffset> SECONDS_CACHE;
    public static final ZoneOffset UTC;
    private static final long serialVersionUID = 2357656521762053153L;
    private final transient String id;
    private final int totalSeconds;

    static {
        SECONDS_CACHE = new ConcurrentHashMap<Integer, ZoneOffset>(16, 0.75f, 4);
        ID_CACHE = new ConcurrentHashMap<String, ZoneOffset>(16, 0.75f, 4);
        UTC = ZoneOffset.ofTotalSeconds(0);
        MIN = ZoneOffset.ofTotalSeconds(-64800);
        MAX = ZoneOffset.ofTotalSeconds(64800);
    }

    private ZoneOffset(int n) {
        this.totalSeconds = n;
        this.id = ZoneOffset.buildId(n);
    }

    private static String buildId(int n) {
        if (n == 0) {
            return "Z";
        }
        int n2 = Math.abs(n);
        StringBuilder stringBuilder = new StringBuilder();
        int n3 = n2 / 3600;
        int n4 = n2 / 60 % 60;
        String string = n < 0 ? "-" : "+";
        stringBuilder.append(string);
        string = n3 < 10 ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(n3);
        String string2 = ":0";
        string = n4 < 10 ? ":0" : ":";
        stringBuilder.append(string);
        stringBuilder.append(n4);
        n = n2 % 60;
        if (n != 0) {
            string = n < 10 ? string2 : ":";
            stringBuilder.append(string);
            stringBuilder.append(n);
        }
        return stringBuilder.toString();
    }

    public static ZoneOffset from(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        Serializable serializable = temporalAccessor.query(TemporalQueries.offset());
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unable to obtain ZoneOffset from TemporalAccessor: ");
        ((StringBuilder)serializable).append(temporalAccessor);
        ((StringBuilder)serializable).append(" of type ");
        ((StringBuilder)serializable).append(temporalAccessor.getClass().getName());
        throw new DateTimeException(((StringBuilder)serializable).toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ZoneOffset of(String object) {
        int n;
        Object object2;
        int n2;
        int n3;
        block12 : {
            block11 : {
                block10 : {
                    Objects.requireNonNull(object, "offsetId");
                    object2 = (ZoneOffset)ID_CACHE.get(object);
                    if (object2 != null) {
                        return object2;
                    }
                    n3 = ((String)object).length();
                    if (n3 == 2) break block10;
                    object2 = object;
                    if (n3 == 3) break block11;
                    if (n3 != 5) {
                        if (n3 != 6) {
                            if (n3 != 7) {
                                if (n3 != 9) {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Invalid ID for ZoneOffset, invalid format: ");
                                    ((StringBuilder)object2).append((String)object);
                                    throw new DateTimeException(((StringBuilder)object2).toString());
                                }
                                n3 = ZoneOffset.parseNumber((CharSequence)object, 1, false);
                                n2 = ZoneOffset.parseNumber((CharSequence)object, 4, true);
                                n = ZoneOffset.parseNumber((CharSequence)object, 7, true);
                            } else {
                                n3 = ZoneOffset.parseNumber((CharSequence)object, 1, false);
                                n2 = ZoneOffset.parseNumber((CharSequence)object, 3, false);
                                n = ZoneOffset.parseNumber((CharSequence)object, 5, false);
                            }
                        } else {
                            n3 = ZoneOffset.parseNumber((CharSequence)object, 1, false);
                            n2 = ZoneOffset.parseNumber((CharSequence)object, 4, true);
                            n = 0;
                        }
                    } else {
                        n3 = ZoneOffset.parseNumber((CharSequence)object, 1, false);
                        n2 = ZoneOffset.parseNumber((CharSequence)object, 3, false);
                        n = 0;
                    }
                    break block12;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(((String)object).charAt(0));
                ((StringBuilder)object2).append("0");
                ((StringBuilder)object2).append(((String)object).charAt(1));
                object2 = ((StringBuilder)object2).toString();
            }
            n3 = ZoneOffset.parseNumber((CharSequence)object2, 1, false);
            n2 = 0;
            n = 0;
            object = object2;
        }
        char c = ((String)object).charAt(0);
        if (c != '+' && c != '-') {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Invalid ID for ZoneOffset, plus/minus not found when expected: ");
            ((StringBuilder)object2).append((String)object);
            throw new DateTimeException(((StringBuilder)object2).toString());
        }
        if (c == '-') {
            return ZoneOffset.ofHoursMinutesSeconds(-n3, -n2, -n);
        }
        return ZoneOffset.ofHoursMinutesSeconds(n3, n2, n);
    }

    public static ZoneOffset ofHours(int n) {
        return ZoneOffset.ofHoursMinutesSeconds(n, 0, 0);
    }

    public static ZoneOffset ofHoursMinutes(int n, int n2) {
        return ZoneOffset.ofHoursMinutesSeconds(n, n2, 0);
    }

    public static ZoneOffset ofHoursMinutesSeconds(int n, int n2, int n3) {
        ZoneOffset.validate(n, n2, n3);
        return ZoneOffset.ofTotalSeconds(ZoneOffset.totalSeconds(n, n2, n3));
    }

    public static ZoneOffset ofTotalSeconds(int n) {
        if (Math.abs(n) <= 64800) {
            if (n % 900 == 0) {
                ZoneOffset zoneOffset;
                Integer n2 = n;
                ZoneOffset zoneOffset2 = zoneOffset = (ZoneOffset)SECONDS_CACHE.get(n2);
                if (zoneOffset == null) {
                    zoneOffset2 = new ZoneOffset(n);
                    SECONDS_CACHE.putIfAbsent(n2, zoneOffset2);
                    zoneOffset2 = (ZoneOffset)SECONDS_CACHE.get(n2);
                    ID_CACHE.putIfAbsent(zoneOffset2.getId(), zoneOffset2);
                }
                return zoneOffset2;
            }
            return new ZoneOffset(n);
        }
        throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
    }

    private static int parseNumber(CharSequence charSequence, int n, boolean bl) {
        if (bl && charSequence.charAt(n - 1) != ':') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ID for ZoneOffset, colon not found when expected: ");
            stringBuilder.append((Object)charSequence);
            throw new DateTimeException(stringBuilder.toString());
        }
        char c = charSequence.charAt(n);
        n = charSequence.charAt(n + 1);
        if (c >= '0' && c <= '9' && n >= 48 && n <= 57) {
            return (c - 48) * 10 + (n - 48);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid ID for ZoneOffset, non numeric characters found: ");
        stringBuilder.append((Object)charSequence);
        throw new DateTimeException(stringBuilder.toString());
    }

    static ZoneOffset readExternal(DataInput dataInput) throws IOException {
        int n = dataInput.readByte();
        n = n == 127 ? dataInput.readInt() : (n *= 900);
        return ZoneOffset.ofTotalSeconds(n);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private static int totalSeconds(int n, int n2, int n3) {
        return n * 3600 + n2 * 60 + n3;
    }

    private static void validate(int n, int n2, int n3) {
        if (n >= -18 && n <= 18) {
            if (n > 0) {
                if (n2 < 0 || n3 < 0) {
                    throw new DateTimeException("Zone offset minutes and seconds must be positive because hours is positive");
                }
            } else if (n < 0) {
                if (n2 > 0 || n3 > 0) {
                    throw new DateTimeException("Zone offset minutes and seconds must be negative because hours is negative");
                }
            } else if (n2 > 0 && n3 < 0 || n2 < 0 && n3 > 0) {
                throw new DateTimeException("Zone offset minutes and seconds must have the same sign");
            }
            if (Math.abs(n2) <= 59) {
                if (Math.abs(n3) <= 59) {
                    if (Math.abs(n) == 18 && (Math.abs(n2) > 0 || Math.abs(n3) > 0)) {
                        throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Zone offset seconds not in valid range: abs(value) ");
                stringBuilder.append(Math.abs(n3));
                stringBuilder.append(" is not in the range 0 to 59");
                throw new DateTimeException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Zone offset minutes not in valid range: abs(value) ");
            stringBuilder.append(Math.abs(n2));
            stringBuilder.append(" is not in the range 0 to 59");
            throw new DateTimeException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Zone offset hours not in valid range: value ");
        stringBuilder.append(n);
        stringBuilder.append(" is not in the range -18 to 18");
        throw new DateTimeException(stringBuilder.toString());
    }

    private Object writeReplace() {
        return new Ser(8, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.OFFSET_SECONDS, this.totalSeconds);
    }

    @Override
    public int compareTo(ZoneOffset zoneOffset) {
        return zoneOffset.totalSeconds - this.totalSeconds;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ZoneOffset) {
            if (this.totalSeconds != ((ZoneOffset)object).totalSeconds) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField == ChronoField.OFFSET_SECONDS) {
            return this.totalSeconds;
        }
        if (!(temporalField instanceof ChronoField)) {
            return this.range(temporalField).checkValidIntValue(this.getLong(temporalField), temporalField);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField == ChronoField.OFFSET_SECONDS) {
            return this.totalSeconds;
        }
        if (!(temporalField instanceof ChronoField)) {
            return temporalField.getFrom(this);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    public ZoneRules getRules() {
        return ZoneRules.of(this);
    }

    public int getTotalSeconds() {
        return this.totalSeconds;
    }

    @Override
    public int hashCode() {
        return this.totalSeconds;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (temporalField != ChronoField.OFFSET_SECONDS) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = temporalField != null && temporalField.isSupportedBy(this) ? bl2 : false;
        return bl3;
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.offset() && temporalQuery != TemporalQueries.zone()) {
            return TemporalAccessor.super.query(temporalQuery);
        }
        return (R)this;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        return TemporalAccessor.super.range(temporalField);
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(8);
        this.writeExternal(dataOutput);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        int n = this.totalSeconds;
        int n2 = n % 900 == 0 ? n / 900 : 127;
        dataOutput.writeByte(n2);
        if (n2 == 127) {
            dataOutput.writeInt(n);
        }
    }
}

