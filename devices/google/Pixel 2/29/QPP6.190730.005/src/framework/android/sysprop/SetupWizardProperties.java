/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.sysprop.-$
 *  android.sysprop.-$$Lambda
 *  android.sysprop.-$$Lambda$SetupWizardProperties
 *  android.sysprop.-$$Lambda$SetupWizardProperties$0Kz3pPj1bN4LsGiOMvxPbgDHtMI
 */
package android.sysprop;

import android.os.SystemProperties;
import android.sysprop.-$;
import android.sysprop._$$Lambda$SetupWizardProperties$0Kz3pPj1bN4LsGiOMvxPbgDHtMI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

public final class SetupWizardProperties {
    private SetupWizardProperties() {
    }

    public static List<String> esim_cid_ignore() {
        String string2 = SystemProperties.get("ro.setupwizard.esim_cid_ignore");
        return SetupWizardProperties.tryParseList(_$$Lambda$SetupWizardProperties$0Kz3pPj1bN4LsGiOMvxPbgDHtMI.INSTANCE, string2);
    }

    private static <T extends Enum<T>> String formatEnumList(List<T> object, Function<T, String> function) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<T> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (Enum)iterator.next();
            object = object == null ? "" : (CharSequence)function.apply(object);
            stringJoiner.add((CharSequence)object);
        }
        return stringJoiner.toString();
    }

    private static <T> String formatList(List<T> object) {
        StringJoiner stringJoiner = new StringJoiner(",");
        Iterator<T> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            object = object == null ? "" : object.toString();
            stringJoiner.add((CharSequence)object);
        }
        return stringJoiner.toString();
    }

    static /* synthetic */ String lambda$esim_cid_ignore$0(String string2) {
        return SetupWizardProperties.tryParseString(string2);
    }

    public static Optional<String> theme() {
        return Optional.ofNullable(SetupWizardProperties.tryParseString(SystemProperties.get("setupwizard.theme")));
    }

    public static void theme(String string2) {
        string2 = string2 == null ? "" : string2.toString();
        SystemProperties.set("setupwizard.theme", string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Boolean tryParseBoolean(String string2) {
        int n = (string2 = string2.toLowerCase(Locale.US)).hashCode();
        if (n != 48) {
            if (n != 49) {
                if (n != 3569038) {
                    if (n != 97196323) return null;
                    if (!string2.equals("false")) return null;
                    n = 3;
                } else {
                    if (!string2.equals("true")) return null;
                    n = 1;
                }
            } else {
                if (!string2.equals("1")) return null;
                n = 0;
            }
        } else {
            if (!string2.equals("0")) return null;
            n = 2;
        }
        if (n == 0) return Boolean.TRUE;
        if (n == 1) return Boolean.TRUE;
        if (n == 2) return Boolean.FALSE;
        if (n == 3) return Boolean.FALSE;
        return null;
    }

    private static Double tryParseDouble(String object) {
        try {
            object = Double.valueOf((String)object);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static <T extends Enum<T>> T tryParseEnum(Class<T> class_, String string2) {
        try {
            class_ = Enum.valueOf(class_, string2.toUpperCase(Locale.US));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        return (T)class_;
    }

    private static <T extends Enum<T>> List<T> tryParseEnumList(Class<T> class_, String arrstring) {
        if ("".equals(arrstring)) {
            return new ArrayList();
        }
        ArrayList<T> arrayList = new ArrayList<T>();
        arrstring = arrstring.split(",");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(SetupWizardProperties.tryParseEnum(class_, arrstring[i]));
        }
        return arrayList;
    }

    private static Integer tryParseInteger(String object) {
        try {
            object = Integer.valueOf((String)object);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static <T> List<T> tryParseList(Function<String, T> function, String arrstring) {
        if ("".equals(arrstring)) {
            return new ArrayList();
        }
        ArrayList<T> arrayList = new ArrayList<T>();
        arrstring = arrstring.split(",");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(function.apply(arrstring[i]));
        }
        return arrayList;
    }

    private static Long tryParseLong(String object) {
        try {
            object = Long.valueOf((String)object);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    private static String tryParseString(String string2) {
        block0 : {
            if (!"".equals(string2)) break block0;
            string2 = null;
        }
        return string2;
    }
}

