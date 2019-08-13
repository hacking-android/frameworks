/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;

public class DebugUtils {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static void buildShortClassTag(Object object, StringBuilder stringBuilder) {
        if (object == null) {
            stringBuilder.append("null");
        } else {
            String string2;
            String string3 = string2 = object.getClass().getSimpleName();
            if (string2.isEmpty()) {
                string2 = object.getClass().getName();
                int n = string2.lastIndexOf(46);
                string3 = string2;
                if (n > 0) {
                    string3 = string2.substring(n + 1);
                }
            }
            stringBuilder.append(string3);
            stringBuilder.append('{');
            stringBuilder.append(Integer.toHexString(System.identityHashCode(object)));
        }
    }

    private static String constNameWithoutPrefix(String string2, Field field) {
        return field.getName().substring(string2.length());
    }

    public static String flagsToString(Class<?> arrfield, String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = n == 0;
        for (Field field : arrfield.getDeclaredFields()) {
            int n2;
            block9 : {
                int n3;
                block8 : {
                    n3 = field.getModifiers();
                    n2 = n;
                    if (!Modifier.isStatic(n3)) break block9;
                    n2 = n;
                    if (!Modifier.isFinal(n3)) break block9;
                    n2 = n;
                    if (!field.getType().equals(Integer.TYPE)) break block9;
                    n2 = n;
                    if (!field.getName().startsWith(string2)) break block9;
                    n2 = n;
                    n3 = field.getInt(null);
                    if (n3 != 0 || !bl) break block8;
                    n2 = n;
                    return DebugUtils.constNameWithoutPrefix(string2, field);
                }
                n2 = n;
                if ((n & n3) != n3) break block9;
                n2 = n &= n3;
                stringBuilder.append(DebugUtils.constNameWithoutPrefix(string2, field));
                n2 = n;
                try {
                    stringBuilder.append('|');
                    n2 = n;
                }
                catch (IllegalAccessException illegalAccessException) {
                    // empty catch block
                }
            }
            n = n2;
        }
        if (n == 0 && stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } else {
            stringBuilder.append(Integer.toHexString(n));
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isObjectSelected(Object object) {
        boolean bl = false;
        boolean bl2 = false;
        Object object2 = System.getenv("ANDROID_OBJECT_FILTER");
        boolean bl3 = bl;
        if (object2 != null) {
            bl3 = bl;
            if (((String)object2).length() > 0) {
                String[] arrstring = ((String)object2).split("@");
                bl3 = bl;
                if (object.getClass().getSimpleName().matches(arrstring[0])) {
                    int n = 1;
                    do {
                        block10 : {
                            bl3 = bl2;
                            if (n >= arrstring.length) break;
                            String[] arrstring2 = arrstring[n].split("=");
                            Class<?> class_ = object.getClass();
                            object2 = class_;
                            try {
                                Serializable serializable;
                                Method method;
                                do {
                                    serializable = new StringBuilder();
                                    serializable.append("get");
                                    serializable.append(arrstring2[0].substring(0, 1).toUpperCase(Locale.ROOT));
                                    serializable.append(arrstring2[0].substring(1));
                                    method = ((Class)object2).getDeclaredMethod(serializable.toString(), null);
                                    serializable = class_.getSuperclass();
                                    object2 = serializable;
                                } while (serializable != null && method == null);
                                bl3 = bl2;
                                if (method != null) {
                                    object2 = method.invoke(object, null);
                                    object2 = object2 != null ? object2.toString() : "null";
                                    bl3 = ((String)object2).matches(arrstring2[1]);
                                    bl3 = bl2 | bl3;
                                }
                            }
                            catch (InvocationTargetException invocationTargetException) {
                                invocationTargetException.printStackTrace();
                                break block10;
                            }
                            catch (IllegalAccessException illegalAccessException) {
                                illegalAccessException.printStackTrace();
                                bl3 = bl2;
                            }
                            catch (NoSuchMethodException noSuchMethodException) {
                                noSuchMethodException.printStackTrace();
                                bl3 = bl2;
                            }
                            bl2 = bl3;
                        }
                        ++n;
                    } while (true);
                }
            }
        }
        return bl3;
    }

    public static void printSizeValue(PrintWriter printWriter, long l) {
        float f = l;
        String string2 = "";
        float f2 = f;
        if (f > 900.0f) {
            string2 = "KB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 > 900.0f) {
            string2 = "MB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        if (f > 900.0f) {
            string2 = "GB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 > 900.0f) {
            string2 = "TB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        String string3 = string2;
        if (f > 900.0f) {
            string3 = "PB";
            f2 = f / 1024.0f;
        }
        string2 = f2 < 1.0f ? String.format("%.2f", Float.valueOf(f2)) : (f2 < 10.0f ? String.format("%.1f", Float.valueOf(f2)) : (f2 < 100.0f ? String.format("%.0f", Float.valueOf(f2)) : String.format("%.0f", Float.valueOf(f2))));
        printWriter.print(string2);
        printWriter.print(string3);
    }

    public static String sizeValueToString(long l, StringBuilder charSequence) {
        StringBuilder stringBuilder = charSequence;
        if (charSequence == null) {
            stringBuilder = new StringBuilder(32);
        }
        float f = l;
        charSequence = "";
        float f2 = f;
        if (f > 900.0f) {
            charSequence = "KB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 > 900.0f) {
            charSequence = "MB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        if (f > 900.0f) {
            charSequence = "GB";
            f2 = f / 1024.0f;
        }
        f = f2;
        if (f2 > 900.0f) {
            charSequence = "TB";
            f = f2 / 1024.0f;
        }
        f2 = f;
        CharSequence charSequence2 = charSequence;
        if (f > 900.0f) {
            charSequence2 = "PB";
            f2 = f / 1024.0f;
        }
        charSequence = f2 < 1.0f ? String.format("%.2f", Float.valueOf(f2)) : (f2 < 10.0f ? String.format("%.1f", Float.valueOf(f2)) : (f2 < 100.0f ? String.format("%.0f", Float.valueOf(f2)) : String.format("%.0f", Float.valueOf(f2))));
        stringBuilder.append((String)charSequence);
        stringBuilder.append((String)charSequence2);
        return stringBuilder.toString();
    }

    public static String valueToString(Class<?> arrfield, String string2, int n) {
        for (Field illegalAccessException : arrfield.getDeclaredFields()) {
            int n2 = illegalAccessException.getModifiers();
            if (!Modifier.isStatic(n2) || !Modifier.isFinal(n2) || !illegalAccessException.getType().equals(Integer.TYPE) || !illegalAccessException.getName().startsWith(string2)) continue;
            try {
                if (n != illegalAccessException.getInt(null)) continue;
                String string3 = DebugUtils.constNameWithoutPrefix(string2, illegalAccessException);
                return string3;
            }
            catch (IllegalAccessException illegalAccessException2) {
                // empty catch block
            }
        }
        return Integer.toString(n);
    }
}

