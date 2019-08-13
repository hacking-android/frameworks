/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal;

import android.util.Rational;
import com.android.internal.util.Preconditions;

public final class MarshalHelpers {
    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_DOUBLE = 8;
    public static final int SIZEOF_FLOAT = 4;
    public static final int SIZEOF_INT32 = 4;
    public static final int SIZEOF_INT64 = 8;
    public static final int SIZEOF_RATIONAL = 8;

    private MarshalHelpers() {
        throw new AssertionError();
    }

    public static int checkNativeType(int n) {
        if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4 && n != 5) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown nativeType ");
            stringBuilder.append(n);
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        return n;
    }

    public static int checkNativeTypeEquals(int n, int n2) {
        if (n == n2) {
            return n2;
        }
        throw new UnsupportedOperationException(String.format("Expected native type %d, but got %d", n, n2));
    }

    public static <T> Class<T> checkPrimitiveClass(Class<T> class_) {
        Preconditions.checkNotNull(class_, "klass must not be null");
        if (MarshalHelpers.isPrimitiveClass(class_)) {
            return class_;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported class '");
        stringBuilder.append(class_);
        stringBuilder.append("'; expected a metadata primitive class");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public static int getPrimitiveTypeSize(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                return 8;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown type, can't get size for ");
                            stringBuilder.append(n);
                            throw new UnsupportedOperationException(stringBuilder.toString());
                        }
                        return 8;
                    }
                    return 8;
                }
                return 4;
            }
            return 4;
        }
        return 1;
    }

    public static <T> boolean isPrimitiveClass(Class<T> class_) {
        if (class_ == null) {
            return false;
        }
        if (class_ != Byte.TYPE && class_ != Byte.class) {
            if (class_ != Integer.TYPE && class_ != Integer.class) {
                if (class_ != Float.TYPE && class_ != Float.class) {
                    if (class_ != Long.TYPE && class_ != Long.class) {
                        if (class_ != Double.TYPE && class_ != Double.class) {
                            return class_ == Rational.class;
                        }
                        return true;
                    }
                    return true;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public static String toStringNativeType(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("UNKNOWN(");
                                stringBuilder.append(n);
                                stringBuilder.append(")");
                                return stringBuilder.toString();
                            }
                            return "TYPE_RATIONAL";
                        }
                        return "TYPE_DOUBLE";
                    }
                    return "TYPE_INT64";
                }
                return "TYPE_FLOAT";
            }
            return "TYPE_INT32";
        }
        return "TYPE_BYTE";
    }

    public static <T> Class<T> wrapClassIfPrimitive(Class<T> class_) {
        if (class_ == Byte.TYPE) {
            return Byte.class;
        }
        if (class_ == Integer.TYPE) {
            return Integer.class;
        }
        if (class_ == Float.TYPE) {
            return Float.class;
        }
        if (class_ == Long.TYPE) {
            return Long.class;
        }
        if (class_ == Double.TYPE) {
            return Double.class;
        }
        return class_;
    }
}

