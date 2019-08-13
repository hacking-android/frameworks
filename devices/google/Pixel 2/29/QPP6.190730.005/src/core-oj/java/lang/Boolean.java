/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.Serializable;

public final class Boolean
implements Serializable,
Comparable<Boolean> {
    public static final Boolean FALSE;
    public static final Boolean TRUE;
    public static final Class<Boolean> TYPE;
    private static final long serialVersionUID = -3665804199014368530L;
    private final boolean value;

    static {
        TRUE = new Boolean(true);
        FALSE = new Boolean(false);
        TYPE = Class.getPrimitiveClass("boolean");
    }

    public Boolean(String string) {
        this(Boolean.parseBoolean(string));
    }

    public Boolean(boolean bl) {
        this.value = bl;
    }

    public static int compare(boolean bl, boolean bl2) {
        int n = bl == bl2 ? 0 : (bl ? 1 : -1);
        return n;
    }

    public static boolean getBoolean(String string) {
        boolean bl = false;
        try {
            boolean bl2;
            bl = bl2 = Boolean.parseBoolean(System.getProperty(string));
        }
        catch (IllegalArgumentException | NullPointerException runtimeException) {
            // empty catch block
        }
        return bl;
    }

    public static int hashCode(boolean bl) {
        int n = bl ? 1231 : 1237;
        return n;
    }

    public static boolean logicalAnd(boolean bl, boolean bl2) {
        bl = bl && bl2;
        return bl;
    }

    public static boolean logicalOr(boolean bl, boolean bl2) {
        bl = bl || bl2;
        return bl;
    }

    public static boolean logicalXor(boolean bl, boolean bl2) {
        return bl ^ bl2;
    }

    public static boolean parseBoolean(String string) {
        boolean bl = string != null && string.equalsIgnoreCase("true");
        return bl;
    }

    public static String toString(boolean bl) {
        String string = bl ? "true" : "false";
        return string;
    }

    public static Boolean valueOf(String object) {
        object = Boolean.parseBoolean((String)object) ? TRUE : FALSE;
        return object;
    }

    public static Boolean valueOf(boolean bl) {
        Boolean bl2 = bl ? TRUE : FALSE;
        return bl2;
    }

    public boolean booleanValue() {
        return this.value;
    }

    @Override
    public int compareTo(Boolean bl) {
        return Boolean.compare(this.value, bl.value);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Boolean;
        boolean bl2 = false;
        if (bl) {
            if (this.value == (Boolean)object) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int hashCode() {
        return Boolean.hashCode(this.value);
    }

    public String toString() {
        String string = this.value ? "true" : "false";
        return string;
    }
}

