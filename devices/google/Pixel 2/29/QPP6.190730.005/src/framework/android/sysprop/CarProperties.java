/*
 * Decompiled with CFR 0.145.
 */
package android.sysprop;

import android.os.SystemProperties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

public final class CarProperties {
    private CarProperties() {
    }

    public static Optional<Integer> boot_user_override_id() {
        return Optional.ofNullable(CarProperties.tryParseInteger(SystemProperties.get("android.car.systemuser.bootuseroverrideid")));
    }

    public static void boot_user_override_id(Integer object) {
        object = object == null ? "" : ((Integer)object).toString();
        SystemProperties.set("android.car.systemuser.bootuseroverrideid", (String)object);
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

    public static Optional<Boolean> headless_system_user() {
        return Optional.ofNullable(CarProperties.tryParseBoolean(SystemProperties.get("android.car.systemuser.headless")));
    }

    public static void headless_system_user(Boolean object) {
        object = object == null ? "" : ((Boolean)object).toString();
        SystemProperties.set("android.car.systemuser.headless", (String)object);
    }

    public static Optional<String> trusted_device_device_name_prefix() {
        return Optional.ofNullable(CarProperties.tryParseString(SystemProperties.get("ro.android.car.trusteddevice.device_name_prefix")));
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
            arrayList.add(CarProperties.tryParseEnum(class_, arrstring[i]));
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

