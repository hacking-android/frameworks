/*
 * Decompiled with CFR 0.145.
 */
package android.app.timezone;

final class Utils {
    private Utils() {
    }

    static <T> T validateConditionalNull(boolean bl, String string2, T t) {
        if (bl) {
            return Utils.validateNotNull(string2, t);
        }
        return Utils.validateNull(string2, t);
    }

    static <T> T validateNotNull(String string2, T object) {
        if (object != null) {
            return (T)object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" == null");
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    static <T> T validateNull(String string2, T object) {
        if (object == null) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" != null");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static String validateRulesVersion(String string2, String charSequence) {
        Utils.validateNotNull(string2, charSequence);
        if (!((String)charSequence).isEmpty()) {
            return charSequence;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" must not be empty");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    static int validateVersion(String string2, int n) {
        if (n >= 0 && n <= 999) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid ");
        stringBuilder.append(string2);
        stringBuilder.append(" version=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

