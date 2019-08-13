/*
 * Decompiled with CFR 0.145.
 */
package libcore.timezone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import libcore.timezone.CountryTimeZones;
import libcore.timezone.TimeZoneFinder;

public final class CountryZonesFinder {
    private final List<CountryTimeZones> countryTimeZonesList;

    CountryZonesFinder(List<CountryTimeZones> list) {
        this.countryTimeZonesList = new ArrayList<CountryTimeZones>(list);
    }

    public static CountryZonesFinder createForTests(List<CountryTimeZones> list) {
        return new CountryZonesFinder(list);
    }

    public List<String> lookupAllCountryIsoCodes() {
        ArrayList<String> arrayList = new ArrayList<String>(this.countryTimeZonesList.size());
        Iterator<CountryTimeZones> iterator = this.countryTimeZonesList.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getCountryIso());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public CountryTimeZones lookupCountryTimeZones(String string) {
        string = TimeZoneFinder.normalizeCountryIso(string);
        for (CountryTimeZones countryTimeZones : this.countryTimeZonesList) {
            if (!countryTimeZones.getCountryIso().equals(string)) continue;
            return countryTimeZones;
        }
        return null;
    }

    public List<CountryTimeZones> lookupCountryTimeZonesForZoneId(String string) {
        ArrayList<CountryTimeZones> arrayList = new ArrayList<CountryTimeZones>(2);
        for (CountryTimeZones countryTimeZones : this.countryTimeZonesList) {
            if (!CountryTimeZones.TimeZoneMapping.containsTimeZoneId(countryTimeZones.getTimeZoneMappings(), string)) continue;
            arrayList.add(countryTimeZones);
        }
        return Collections.unmodifiableList(arrayList);
    }
}

