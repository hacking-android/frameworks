/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

public class Modifier {
    public static final int ABSTRACT = 1024;
    static final int ACCESS_MODIFIERS = 7;
    static final int ANNOTATION = 8192;
    static final int BRIDGE = 64;
    private static final int CLASS_MODIFIERS = 3103;
    public static final int CONSTRUCTOR = 65536;
    private static final int CONSTRUCTOR_MODIFIERS = 7;
    public static final int DEFAULT = 4194304;
    static final int ENUM = 16384;
    private static final int FIELD_MODIFIERS = 223;
    public static final int FINAL = 16;
    public static final int INTERFACE = 512;
    private static final int INTERFACE_MODIFIERS = 3087;
    static final int MANDATED = 32768;
    private static final int METHOD_MODIFIERS = 3391;
    public static final int NATIVE = 256;
    private static final int PARAMETER_MODIFIERS = 16;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int PUBLIC = 1;
    public static final int STATIC = 8;
    public static final int STRICT = 2048;
    public static final int SYNCHRONIZED = 32;
    public static final int SYNTHETIC = 4096;
    public static final int TRANSIENT = 128;
    static final int VARARGS = 128;
    public static final int VOLATILE = 64;

    public static int classModifiers() {
        return 3103;
    }

    public static int constructorModifiers() {
        return 7;
    }

    public static int fieldModifiers() {
        return 223;
    }

    public static int interfaceModifiers() {
        return 3087;
    }

    public static boolean isAbstract(int n) {
        boolean bl = (n & 1024) != 0;
        return bl;
    }

    public static boolean isConstructor(int n) {
        boolean bl = (65536 & n) != 0;
        return bl;
    }

    public static boolean isFinal(int n) {
        boolean bl = (n & 16) != 0;
        return bl;
    }

    public static boolean isInterface(int n) {
        boolean bl = (n & 512) != 0;
        return bl;
    }

    static boolean isMandated(int n) {
        boolean bl = (32768 & n) != 0;
        return bl;
    }

    public static boolean isNative(int n) {
        boolean bl = (n & 256) != 0;
        return bl;
    }

    public static boolean isPrivate(int n) {
        boolean bl = (n & 2) != 0;
        return bl;
    }

    public static boolean isProtected(int n) {
        boolean bl = (n & 4) != 0;
        return bl;
    }

    public static boolean isPublic(int n) {
        boolean bl = (n & 1) != 0;
        return bl;
    }

    public static boolean isStatic(int n) {
        boolean bl = (n & 8) != 0;
        return bl;
    }

    public static boolean isStrict(int n) {
        boolean bl = (n & 2048) != 0;
        return bl;
    }

    public static boolean isSynchronized(int n) {
        boolean bl = (n & 32) != 0;
        return bl;
    }

    static boolean isSynthetic(int n) {
        boolean bl = (n & 4096) != 0;
        return bl;
    }

    public static boolean isTransient(int n) {
        boolean bl = (n & 128) != 0;
        return bl;
    }

    public static boolean isVolatile(int n) {
        boolean bl = (n & 64) != 0;
        return bl;
    }

    public static int methodModifiers() {
        return 3391;
    }

    public static int parameterModifiers() {
        return 16;
    }

    public static String toString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 1) != 0) {
            stringBuilder.append("public ");
        }
        if ((n & 4) != 0) {
            stringBuilder.append("protected ");
        }
        if ((n & 2) != 0) {
            stringBuilder.append("private ");
        }
        if ((n & 1024) != 0) {
            stringBuilder.append("abstract ");
        }
        if ((n & 8) != 0) {
            stringBuilder.append("static ");
        }
        if ((n & 16) != 0) {
            stringBuilder.append("final ");
        }
        if ((n & 128) != 0) {
            stringBuilder.append("transient ");
        }
        if ((n & 64) != 0) {
            stringBuilder.append("volatile ");
        }
        if ((n & 32) != 0) {
            stringBuilder.append("synchronized ");
        }
        if ((n & 256) != 0) {
            stringBuilder.append("native ");
        }
        if ((n & 2048) != 0) {
            stringBuilder.append("strictfp ");
        }
        if ((n & 512) != 0) {
            stringBuilder.append("interface ");
        }
        if ((n = stringBuilder.length()) > 0) {
            return stringBuilder.toString().substring(0, n - 1);
        }
        return "";
    }
}

