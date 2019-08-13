/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import android.icu.util.TimeZone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import libcore.timezone.ZoneInfoDB;

public final class CountryTimeZones {
    private final String countryIso;
    private final String defaultTimeZoneId;
    private final boolean everUsesUtc;
    private TimeZone icuDefaultTimeZone;
    private List<TimeZone> icuTimeZones;
    private final List<TimeZoneMapping> timeZoneMappings;

    private CountryTimeZones(String string, String string2, boolean bl, List<TimeZoneMapping> list) {
        this.countryIso = Objects.requireNonNull(string);
        this.defaultTimeZoneId = string2;
        this.everUsesUtc = bl;
        this.timeZoneMappings = Collections.unmodifiableList(new ArrayList<TimeZoneMapping>(list));
    }

    public static CountryTimeZones createValidated(String string, String string2, boolean bl, List<TimeZoneMapping> object, String string3) {
        HashSet<String> hashSet = new HashSet<String>(Arrays.asList(ZoneInfoDB.getInstance().getAvailableIDs()));
        ArrayList<TimeZoneMapping> arrayList = new ArrayList<TimeZoneMapping>();
        Iterator<TimeZoneMapping> iterator = object.iterator();
        while (iterator.hasNext()) {
            Object object2 = iterator.next();
            object = ((TimeZoneMapping)object2).timeZoneId;
            if (!hashSet.contains(object)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Skipping invalid zone: ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(" at ");
                ((StringBuilder)object2).append(string3);
                System.logW((String)((StringBuilder)object2).toString());
                continue;
            }
            arrayList.add((TimeZoneMapping)object2);
        }
        object = string2;
        if (!hashSet.contains(string2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid default time zone ID: ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" at ");
            ((StringBuilder)object).append(string3);
            System.logW((String)((StringBuilder)object).toString());
            object = null;
        }
        return new CountryTimeZones(CountryTimeZones.normalizeCountryIso(string), (String)object, bl, arrayList);
    }

    private static TimeZone getValidFrozenTimeZoneOrNull(String object) {
        if (((TimeZone)(object = TimeZone.getFrozenTimeZone((String)object))).getID().equals("Etc/Unknown")) {
            return null;
        }
        return object;
    }

    private static String normalizeCountryIso(String string) {
        return string.toLowerCase(Locale.US);
    }

    private static boolean offsetMatchesAtTime(TimeZone timeZone, int n, Integer n2, long l) {
        int[] arrn = new int[2];
        boolean bl = false;
        timeZone.getOffset(l, false, arrn);
        if (n2 != null && n2 != arrn[1]) {
            return false;
        }
        if (n == arrn[0] + arrn[1]) {
            bl = true;
        }
        return bl;
    }

    private static boolean offsetMatchesAtTime(TimeZone timeZone, int n, boolean bl, long l) {
        int[] arrn = new int[2];
        boolean bl2 = false;
        timeZone.getOffset(l, false, arrn);
        boolean bl3 = arrn[1] != 0;
        if (bl != bl3) {
            return false;
        }
        bl = bl2;
        if (n == arrn[0] + arrn[1]) {
            bl = true;
        }
        return bl;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            CountryTimeZones countryTimeZones = (CountryTimeZones)object;
            if (this.everUsesUtc != countryTimeZones.everUsesUtc) {
                return false;
            }
            if (!this.countryIso.equals(countryTimeZones.countryIso)) {
                return false;
            }
            object = this.defaultTimeZoneId;
            if (object != null ? !((String)object).equals(countryTimeZones.defaultTimeZoneId) : countryTimeZones.defaultTimeZoneId != null) {
                return false;
            }
            return this.timeZoneMappings.equals(countryTimeZones.timeZoneMappings);
        }
        return false;
    }

    public String getCountryIso() {
        return this.countryIso;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public TimeZone getDefaultTimeZone() {
        synchronized (this) {
            if (this.icuDefaultTimeZone != null) return this.icuDefaultTimeZone;
            TimeZone timeZone = this.defaultTimeZoneId == null ? null : CountryTimeZones.getValidFrozenTimeZoneOrNull(this.defaultTimeZoneId);
            this.icuDefaultTimeZone = timeZone;
            return this.icuDefaultTimeZone;
        }
    }

    public String getDefaultTimeZoneId() {
        return this.defaultTimeZoneId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<TimeZone> getIcuTimeZones() {
        synchronized (this) {
            if (this.icuTimeZones != null) return this.icuTimeZones;
            ArrayList<Object> arrayList = new ArrayList<Object>(this.timeZoneMappings.size());
            Iterator<TimeZoneMapping> iterator = this.timeZoneMappings.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next().timeZoneId;
                Object object = string.equals(this.defaultTimeZoneId) ? this.getDefaultTimeZone() : CountryTimeZones.getValidFrozenTimeZoneOrNull(string);
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Skipping invalid zone: ");
                    ((StringBuilder)object).append(string);
                    System.logW((String)((StringBuilder)object).toString());
                    continue;
                }
                arrayList.add(object);
            }
            this.icuTimeZones = Collections.unmodifiableList(arrayList);
            return this.icuTimeZones;
        }
    }

    public List<TimeZoneMapping> getTimeZoneMappings() {
        return this.timeZoneMappings;
    }

    public boolean hasUtcZone(long l) {
        if (!this.everUsesUtc) {
            return false;
        }
        Iterator<TimeZone> iterator = this.getIcuTimeZones().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getOffset(l) != 0) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        int n = this.countryIso.hashCode();
        String string = this.defaultTimeZoneId;
        int n2 = string != null ? string.hashCode() : 0;
        return ((n * 31 + n2) * 31 + this.timeZoneMappings.hashCode()) * 31 + this.everUsesUtc;
    }

    public boolean isDefaultOkForCountryTimeZoneDetection(long l) {
        if (this.timeZoneMappings.isEmpty()) {
            return false;
        }
        if (this.timeZoneMappings.size() == 1) {
            return true;
        }
        TimeZone timeZone = this.getDefaultTimeZone();
        if (timeZone == null) {
            return false;
        }
        int n = timeZone.getOffset(l);
        for (TimeZone timeZone2 : this.getIcuTimeZones()) {
            if (timeZone2 == timeZone || n == timeZone2.getOffset(l)) continue;
            return false;
        }
        return true;
    }

    public boolean isForCountryCode(String string) {
        return this.countryIso.equals(CountryTimeZones.normalizeCountryIso(string));
    }

    public OffsetResult lookupByOffsetWithBias(int n, Integer n2, long l, TimeZone object) {
        Object object2 = this.timeZoneMappings;
        if (object2 != null && !object2.isEmpty()) {
            boolean bl;
            boolean bl2;
            object2 = this.getIcuTimeZones();
            List<TimeZoneMapping> list = null;
            boolean bl3 = false;
            boolean bl4 = true;
            Iterator<Object> iterator = object2.iterator();
            do {
                object2 = list;
                bl2 = bl3;
                bl = bl4;
                if (!iterator.hasNext()) break;
                object2 = (TimeZone)iterator.next();
                if (!CountryTimeZones.offsetMatchesAtTime((TimeZone)object2, n, n2, l)) continue;
                if (list == null) {
                    list = object2;
                } else {
                    bl4 = false;
                }
                boolean bl5 = bl3;
                if (object != null) {
                    bl5 = bl3;
                    if (((TimeZone)object2).getID().equals(((TimeZone)object).getID())) {
                        bl5 = true;
                    }
                }
                if (list != null && !bl4) {
                    object2 = list;
                    bl2 = bl5;
                    bl = bl4;
                    if (object == null) break;
                    if (bl5) {
                        object2 = list;
                        bl2 = bl5;
                        bl = bl4;
                        break;
                    }
                }
                bl3 = bl5;
            } while (true);
            if (object2 == null) {
                return null;
            }
            if (!bl2) {
                object = object2;
            }
            return new OffsetResult((TimeZone)object, bl);
        }
        return null;
    }

    @Deprecated
    public OffsetResult lookupByOffsetWithBias(int n, boolean bl, long l, TimeZone object) {
        Object object2 = this.timeZoneMappings;
        if (object2 != null && !object2.isEmpty()) {
            boolean bl2;
            boolean bl3;
            object2 = this.getIcuTimeZones();
            List<TimeZoneMapping> list = null;
            boolean bl4 = false;
            boolean bl5 = true;
            Iterator<Object> iterator = object2.iterator();
            do {
                object2 = list;
                bl3 = bl4;
                bl2 = bl5;
                if (!iterator.hasNext()) break;
                object2 = (TimeZone)iterator.next();
                if (!CountryTimeZones.offsetMatchesAtTime((TimeZone)object2, n, bl, l)) continue;
                if (list == null) {
                    list = object2;
                } else {
                    bl5 = false;
                }
                boolean bl6 = bl4;
                if (object != null) {
                    bl6 = bl4;
                    if (((TimeZone)object2).getID().equals(((TimeZone)object).getID())) {
                        bl6 = true;
                    }
                }
                if (list != null && !bl5) {
                    object2 = list;
                    bl3 = bl6;
                    bl2 = bl5;
                    if (object == null) break;
                    if (bl6) {
                        object2 = list;
                        bl3 = bl6;
                        bl2 = bl5;
                        break;
                    }
                }
                bl4 = bl6;
            } while (true);
            if (object2 == null) {
                return null;
            }
            if (!bl3) {
                object = object2;
            }
            return new OffsetResult((TimeZone)object, bl2);
        }
        return null;
    }

    public static final class OffsetResult {
        public final boolean mOneMatch;
        public final TimeZone mTimeZone;

        public OffsetResult(TimeZone timeZone, boolean bl) {
            this.mTimeZone = Objects.requireNonNull(timeZone);
            this.mOneMatch = bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Result{mTimeZone='");
            stringBuilder.append(this.mTimeZone);
            stringBuilder.append('\'');
            stringBuilder.append(", mOneMatch=");
            stringBuilder.append(this.mOneMatch);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    public static final class TimeZoneMapping {
        public final Long notUsedAfter;
        public final boolean showInPicker;
        public final String timeZoneId;

        TimeZoneMapping(String string, boolean bl, Long l) {
            this.timeZoneId = string;
            this.showInPicker = bl;
            this.notUsedAfter = l;
        }

        public static boolean containsTimeZoneId(List<TimeZoneMapping> object, String string) {
            object = object.iterator();
            while (object.hasNext()) {
                if (!((TimeZoneMapping)object.next()).timeZoneId.equals(string)) continue;
                return true;
            }
            return false;
        }

        public static TimeZoneMapping createForTests(String string, boolean bl, Long l) {
            return new TimeZoneMapping(string, bl, l);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (TimeZoneMapping)object;
                if (this.showInPicker != ((TimeZoneMapping)object).showInPicker || !Objects.equals(this.timeZoneId, ((TimeZoneMapping)object).timeZoneId) || !Objects.equals(this.notUsedAfter, ((TimeZoneMapping)object).notUsedAfter)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.timeZoneId, this.showInPicker, this.notUsedAfter);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TimeZoneMapping{timeZoneId='");
            stringBuilder.append(this.timeZoneId);
            stringBuilder.append('\'');
            stringBuilder.append(", showInPicker=");
            stringBuilder.append(this.showInPicker);
            stringBuilder.append(", notUsedAfter=");
            stringBuilder.append(this.notUsedAfter);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

