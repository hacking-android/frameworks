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

public final class DisplayProperties {
    private DisplayProperties() {
    }

    public static Optional<Boolean> debug_force_msaa() {
        return Optional.ofNullable(DisplayProperties.tryParseBoolean(SystemProperties.get("debug.egl.force_msaa")));
    }

    public static void debug_force_msaa(Boolean object) {
        object = object == null ? "" : ((Boolean)object).toString();
        SystemProperties.set("debug.egl.force_msaa", (String)object);
    }

    public static Optional<Boolean> debug_force_rtl() {
        return Optional.ofNullable(DisplayProperties.tryParseBoolean(SystemProperties.get("debug.force_rtl")));
    }

    public static void debug_force_rtl(Boolean object) {
        object = object == null ? "" : ((Boolean)object).toString();
        SystemProperties.set("debug.force_rtl", (String)object);
    }

    public static Optional<Boolean> debug_layout() {
        return Optional.ofNullable(DisplayProperties.tryParseBoolean(SystemProperties.get("debug.layout")));
    }

    public static void debug_layout(Boolean object) {
        object = object == null ? "" : ((Boolean)object).toString();
        SystemProperties.set("debug.layout", (String)object);
    }

    public static Optional<String> debug_opengl_trace() {
        return Optional.ofNullable(DisplayProperties.tryParseString(SystemProperties.get("debug.egl.trace")));
    }

    public static void debug_opengl_trace(String string2) {
        string2 = string2 == null ? "" : string2.toString();
        SystemProperties.set("debug.egl.trace", string2);
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
            arrayList.add(DisplayProperties.tryParseEnum(class_, arrstring[i]));
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

