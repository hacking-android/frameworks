/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Parcel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class ServiceInfo {
    static final int MAP_LIMIT = 1000;
    private final String className;
    private final List<Locale> locales;
    private final Map<Locale, String> names;
    private final String serviceId;
    private final Date sessionEndTime;
    private final Date sessionStartTime;

    protected ServiceInfo(Parcel object) {
        int n;
        if (n <= 1000 && n >= 0) {
            Locale locale;
            this.names = new HashMap<Locale, String>(n);
            for (n = object.readInt(); n > 0; --n) {
                locale = (Locale)((Parcel)object).readSerializable();
                String string2 = ((Parcel)object).readString();
                this.names.put(locale, string2);
            }
            this.className = ((Parcel)object).readString();
            n = ((Parcel)object).readInt();
            if (n <= 1000 && n >= 0) {
                this.locales = new ArrayList<Locale>(n);
                while (n > 0) {
                    locale = (Locale)((Parcel)object).readSerializable();
                    this.locales.add(locale);
                    --n;
                }
                this.serviceId = ((Parcel)object).readString();
                this.sessionStartTime = (Date)((Parcel)object).readSerializable();
                this.sessionEndTime = (Date)((Parcel)object).readSerializable();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("bad locale length ");
            ((StringBuilder)object).append(n);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bad map length");
        ((StringBuilder)object).append(n);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public ServiceInfo(Map<Locale, String> object, String charSequence, List<Locale> list, String string2, Date date, Date date2) {
        if (object != null && charSequence != null && list != null && string2 != null && date != null && date2 != null) {
            if (object.size() <= 1000) {
                if (list.size() <= 1000) {
                    this.names = new HashMap<Locale, String>(object.size());
                    this.names.putAll((Map<Locale, String>)object);
                    this.className = charSequence;
                    this.locales = new ArrayList<Locale>(list);
                    this.serviceId = string2;
                    this.sessionStartTime = (Date)date.clone();
                    this.sessionEndTime = (Date)date2.clone();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("bad locales length ");
                ((StringBuilder)object).append(list.size());
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("bad map length ");
            ((StringBuilder)charSequence).append(object.size());
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("Bad ServiceInfo construction");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof ServiceInfo)) {
            return false;
        }
        object = (ServiceInfo)object;
        if (!(Objects.equals(this.names, ((ServiceInfo)object).names) && Objects.equals(this.className, ((ServiceInfo)object).className) && Objects.equals(this.locales, ((ServiceInfo)object).locales) && Objects.equals(this.serviceId, ((ServiceInfo)object).serviceId) && Objects.equals(this.sessionStartTime, ((ServiceInfo)object).sessionStartTime) && Objects.equals(this.sessionEndTime, ((ServiceInfo)object).sessionEndTime))) {
            bl = false;
        }
        return bl;
    }

    public List<Locale> getLocales() {
        return this.locales;
    }

    public CharSequence getNameForLocale(Locale locale) {
        if (this.names.containsKey(locale)) {
            return this.names.get(locale);
        }
        throw new NoSuchElementException("Locale not supported");
    }

    public Set<Locale> getNamedContentLocales() {
        return Collections.unmodifiableSet(this.names.keySet());
    }

    public String getServiceClassName() {
        return this.className;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public Date getSessionEndTime() {
        return this.sessionEndTime;
    }

    public Date getSessionStartTime() {
        return this.sessionStartTime;
    }

    public int hashCode() {
        return Objects.hash(this.names, this.className, this.locales, this.serviceId, this.sessionStartTime, this.sessionEndTime);
    }

    public void writeToParcel(Parcel parcel, int n) {
        Iterator<Locale> iterator = this.names.keySet();
        parcel.writeInt(iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            Locale locale = (Locale)iterator.next();
            parcel.writeSerializable(locale);
            parcel.writeString(this.names.get(locale));
        }
        parcel.writeString(this.className);
        parcel.writeInt(this.locales.size());
        iterator = this.locales.iterator();
        while (iterator.hasNext()) {
            parcel.writeSerializable(iterator.next());
        }
        parcel.writeString(this.serviceId);
        parcel.writeSerializable(this.sessionStartTime);
        parcel.writeSerializable(this.sessionEndTime);
    }
}

