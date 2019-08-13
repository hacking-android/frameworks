/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

public final class Log {
    static final boolean ENABLE_FULL_LOGGING = android.util.Log.isLoggable("androidtc", 2);

    private Log() {
    }

    public static void d(String string2, String string3) {
        android.util.Log.d(string2, string3);
    }

    public static void e(String string2, String string3, Throwable object) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.e(string2, string3, (Throwable)object);
        } else {
            object = object != null ? object.getClass().getSimpleName() : "??";
            android.util.Log.d(string2, String.format("%s (%s)", string3, object));
        }
    }

    public static void v(String string2, String string3) {
        if (ENABLE_FULL_LOGGING) {
            android.util.Log.v(string2, string3);
        }
    }

    public static void w(String string2, String string3) {
        android.util.Log.w(string2, string3);
    }
}

