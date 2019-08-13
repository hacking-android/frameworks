/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.Ser;
import java.time.ZoneOffset;
import java.time.ZoneRegion;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

public abstract class ZoneId
implements Serializable {
    public static final Map<String, String> SHORT_IDS;
    private static final long serialVersionUID = 8352817235686L;

    static {
        HashMap<String, String> hashMap = new HashMap<String, String>(64);
        hashMap.put("ACT", "Australia/Darwin");
        hashMap.put("AET", "Australia/Sydney");
        hashMap.put("AGT", "America/Argentina/Buenos_Aires");
        hashMap.put("ART", "Africa/Cairo");
        hashMap.put("AST", "America/Anchorage");
        hashMap.put("BET", "America/Sao_Paulo");
        hashMap.put("BST", "Asia/Dhaka");
        hashMap.put("CAT", "Africa/Harare");
        hashMap.put("CNT", "America/St_Johns");
        hashMap.put("CST", "America/Chicago");
        hashMap.put("CTT", "Asia/Shanghai");
        hashMap.put("EAT", "Africa/Addis_Ababa");
        hashMap.put("ECT", "Europe/Paris");
        hashMap.put("IET", "America/Indiana/Indianapolis");
        hashMap.put("IST", "Asia/Kolkata");
        hashMap.put("JST", "Asia/Tokyo");
        hashMap.put("MIT", "Pacific/Apia");
        hashMap.put("NET", "Asia/Yerevan");
        hashMap.put("NST", "Pacific/Auckland");
        hashMap.put("PLT", "Asia/Karachi");
        hashMap.put("PNT", "America/Phoenix");
        hashMap.put("PRT", "America/Puerto_Rico");
        hashMap.put("PST", "America/Los_Angeles");
        hashMap.put("SST", "Pacific/Guadalcanal");
        hashMap.put("VST", "Asia/Ho_Chi_Minh");
        hashMap.put("EST", "-05:00");
        hashMap.put("MST", "-07:00");
        hashMap.put("HST", "-10:00");
        SHORT_IDS = Collections.unmodifiableMap(hashMap);
    }

    ZoneId() {
        if (this.getClass() != ZoneOffset.class && this.getClass() != ZoneRegion.class) {
            throw new AssertionError((Object)"Invalid subclass");
        }
    }

    public static ZoneId from(TemporalAccessor temporalAccessor) {
        Serializable serializable = temporalAccessor.query(TemporalQueries.zone());
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unable to obtain ZoneId from TemporalAccessor: ");
        ((StringBuilder)serializable).append(temporalAccessor);
        ((StringBuilder)serializable).append(" of type ");
        ((StringBuilder)serializable).append(temporalAccessor.getClass().getName());
        throw new DateTimeException(((StringBuilder)serializable).toString());
    }

    public static Set<String> getAvailableZoneIds() {
        return ZoneRulesProvider.getAvailableZoneIds();
    }

    public static ZoneId of(String string) {
        return ZoneId.of(string, true);
    }

    public static ZoneId of(String object, Map<String, String> object2) {
        block0 : {
            Objects.requireNonNull(object, "zoneId");
            Objects.requireNonNull(object2, "aliasMap");
            object2 = object2.get(object);
            if (object2 == null) break block0;
            object = object2;
        }
        return ZoneId.of((String)object);
    }

    static ZoneId of(String string, boolean bl) {
        Objects.requireNonNull(string, "zoneId");
        if (string.length() > 1 && !string.startsWith("+") && !string.startsWith("-")) {
            if (!string.startsWith("UTC") && !string.startsWith("GMT")) {
                if (string.startsWith("UT")) {
                    return ZoneId.ofWithPrefix(string, 2, bl);
                }
                return ZoneRegion.ofId(string, bl);
            }
            return ZoneId.ofWithPrefix(string, 3, bl);
        }
        return ZoneOffset.of(string);
    }

    public static ZoneId ofOffset(String string, ZoneOffset serializable) {
        Objects.requireNonNull(string, "prefix");
        Objects.requireNonNull(serializable, "offset");
        if (string.length() == 0) {
            return serializable;
        }
        if (!(string.equals("GMT") || string.equals("UTC") || string.equals("UT"))) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("prefix should be GMT, UTC or UT, is: ");
            ((StringBuilder)serializable).append(string);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        String string2 = string;
        if (((ZoneOffset)serializable).getTotalSeconds() != 0) {
            string2 = string.concat(((ZoneOffset)serializable).getId());
        }
        return new ZoneRegion(string2, ((ZoneOffset)serializable).getRules());
    }

    private static ZoneId ofWithPrefix(String string, int n, boolean bl) {
        Object object = string.substring(0, n);
        if (string.length() == n) {
            return ZoneId.ofOffset((String)object, ZoneOffset.UTC);
        }
        if (string.charAt(n) != '+' && string.charAt(n) != '-') {
            return ZoneRegion.ofId(string, bl);
        }
        try {
            ZoneOffset zoneOffset = ZoneOffset.of(string.substring(n));
            if (zoneOffset == ZoneOffset.UTC) {
                return ZoneId.ofOffset((String)object, zoneOffset);
            }
            object = ZoneId.ofOffset((String)object, zoneOffset);
            return object;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ID for offset-based ZoneId: ");
            stringBuilder.append(string);
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public static ZoneId systemDefault() {
        return TimeZone.getDefault().toZoneId();
    }

    private TemporalAccessor toTemporal() {
        return new TemporalAccessor(){

            @Override
            public long getLong(TemporalField temporalField) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported field: ");
                stringBuilder.append(temporalField);
                throw new UnsupportedTemporalTypeException(stringBuilder.toString());
            }

            @Override
            public boolean isSupported(TemporalField temporalField) {
                return false;
            }

            @Override
            public <R> R query(TemporalQuery<R> temporalQuery) {
                if (temporalQuery == TemporalQueries.zoneId()) {
                    return (R)ZoneId.this;
                }
                return TemporalAccessor.super.query(temporalQuery);
            }
        };
    }

    private Object writeReplace() {
        return new Ser(7, this);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ZoneId) {
            object = (ZoneId)object;
            return this.getId().equals(((ZoneId)object).getId());
        }
        return false;
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendZoneText(textStyle).toFormatter(locale).format(this.toTemporal());
    }

    public abstract String getId();

    public abstract ZoneRules getRules();

    public int hashCode() {
        return this.getId().hashCode();
    }

    public ZoneId normalized() {
        try {
            Serializable serializable = this.getRules();
            if (serializable.isFixedOffset()) {
                serializable = serializable.getOffset(Instant.EPOCH);
                return serializable;
            }
        }
        catch (ZoneRulesException zoneRulesException) {
            // empty catch block
        }
        return this;
    }

    public String toString() {
        return this.getId();
    }

    abstract void write(DataOutput var1) throws IOException;

}

