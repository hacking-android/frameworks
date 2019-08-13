/*
 * Decompiled with CFR 0.145.
 */
package java.time.format;

import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.time.format.Parsed;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

final class DateTimeParseContext {
    private boolean caseSensitive = true;
    private ArrayList<Consumer<Chronology>> chronoListeners = null;
    private DateTimeFormatter formatter;
    private final ArrayList<Parsed> parsed = new ArrayList();
    private boolean strict = true;

    DateTimeParseContext(DateTimeFormatter dateTimeFormatter) {
        this.formatter = dateTimeFormatter;
        this.parsed.add(new Parsed());
    }

    static boolean charEqualsIgnoreCase(char c, char c2) {
        boolean bl = c == c2 || Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
        return bl;
    }

    private Parsed currentParsed() {
        ArrayList<Parsed> arrayList = this.parsed;
        return arrayList.get(arrayList.size() - 1);
    }

    void addChronoChangedListener(Consumer<Chronology> consumer) {
        if (this.chronoListeners == null) {
            this.chronoListeners = new ArrayList();
        }
        this.chronoListeners.add(consumer);
    }

    boolean charEquals(char c, char c2) {
        if (this.isCaseSensitive()) {
            boolean bl = c == c2;
            return bl;
        }
        return DateTimeParseContext.charEqualsIgnoreCase(c, c2);
    }

    DateTimeParseContext copy() {
        DateTimeParseContext dateTimeParseContext = new DateTimeParseContext(this.formatter);
        dateTimeParseContext.caseSensitive = this.caseSensitive;
        dateTimeParseContext.strict = this.strict;
        return dateTimeParseContext;
    }

    void endOptional(boolean bl) {
        if (bl) {
            ArrayList<Parsed> arrayList = this.parsed;
            arrayList.remove(arrayList.size() - 2);
        } else {
            ArrayList<Parsed> arrayList = this.parsed;
            arrayList.remove(arrayList.size() - 1);
        }
    }

    DecimalStyle getDecimalStyle() {
        return this.formatter.getDecimalStyle();
    }

    Chronology getEffectiveChronology() {
        Chronology chronology;
        Chronology chronology2 = chronology = this.currentParsed().chrono;
        if (chronology == null) {
            chronology2 = chronology = this.formatter.getChronology();
            if (chronology == null) {
                chronology2 = IsoChronology.INSTANCE;
            }
        }
        return chronology2;
    }

    Locale getLocale() {
        return this.formatter.getLocale();
    }

    Long getParsed(TemporalField temporalField) {
        return this.currentParsed().fieldValues.get(temporalField);
    }

    boolean isCaseSensitive() {
        return this.caseSensitive;
    }

    boolean isStrict() {
        return this.strict;
    }

    void setCaseSensitive(boolean bl) {
        this.caseSensitive = bl;
    }

    void setParsed(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        this.currentParsed().zone = zoneId;
    }

    void setParsed(Chronology chronology) {
        Objects.requireNonNull(chronology, "chrono");
        this.currentParsed().chrono = chronology;
        Consumer[] arrconsumer = this.chronoListeners;
        if (arrconsumer != null && !arrconsumer.isEmpty()) {
            arrconsumer = new Consumer[1];
            arrconsumer = this.chronoListeners.toArray(arrconsumer);
            this.chronoListeners.clear();
            int n = arrconsumer.length;
            for (int i = 0; i < n; ++i) {
                arrconsumer[i].accept(chronology);
            }
        }
    }

    int setParsedField(TemporalField object, long l, int n, int n2) {
        Objects.requireNonNull(object, "field");
        object = this.currentParsed().fieldValues.put((TemporalField)object, l);
        if (object == null || (Long)object == l) {
            n = n2;
        }
        return n;
    }

    void setParsedLeapSecond() {
        this.currentParsed().leapSecond = true;
    }

    void setStrict(boolean bl) {
        this.strict = bl;
    }

    void startOptional() {
        this.parsed.add(this.currentParsed().copy());
    }

    boolean subSequenceEquals(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3) {
        if (n + n3 <= charSequence.length() && n2 + n3 <= charSequence2.length()) {
            if (this.isCaseSensitive()) {
                for (int i = 0; i < n3; ++i) {
                    if (charSequence.charAt(n + i) == charSequence2.charAt(n2 + i)) continue;
                    return false;
                }
            } else {
                for (int i = 0; i < n3; ++i) {
                    char c;
                    char c2 = charSequence.charAt(n + i);
                    if (c2 == (c = charSequence2.charAt(n2 + i)) || Character.toUpperCase(c2) == Character.toUpperCase(c) || Character.toLowerCase(c2) == Character.toLowerCase(c)) continue;
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    TemporalAccessor toResolved(ResolverStyle resolverStyle, Set<TemporalField> set) {
        Parsed parsed = this.currentParsed();
        parsed.chrono = this.getEffectiveChronology();
        ZoneId zoneId = parsed.zone != null ? parsed.zone : this.formatter.getZone();
        parsed.zone = zoneId;
        return parsed.resolve(resolverStyle, set);
    }

    public String toString() {
        return this.currentParsed().toString();
    }

    Parsed toUnresolved() {
        return this.currentParsed();
    }
}

