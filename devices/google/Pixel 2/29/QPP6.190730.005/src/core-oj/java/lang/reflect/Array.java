/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.io.Serializable;

public final class Array {
    private Array() {
    }

    private static RuntimeException badArray(Object object) {
        if (object != null) {
            if (!object.getClass().isArray()) {
                throw Array.notAnArray(object);
            }
            throw Array.incompatibleType(object);
        }
        throw new NullPointerException("array == null");
    }

    @FastNative
    private static native Object createMultiArray(Class<?> var0, int[] var1) throws NegativeArraySizeException;

    @FastNative
    private static native Object createObjectArray(Class<?> var0, int var1) throws NegativeArraySizeException;

    public static Object get(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof Object[]) {
            return ((Object[])object)[n];
        }
        if (object instanceof boolean[]) {
            object = ((boolean[])object)[n] ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        if (object instanceof char[]) {
            return Character.valueOf(((char[])object)[n]);
        }
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        if (object instanceof int[]) {
            return ((int[])object)[n];
        }
        if (object instanceof long[]) {
            return ((long[])object)[n];
        }
        if (object instanceof float[]) {
            return new Float(((float[])object)[n]);
        }
        if (object instanceof double[]) {
            return new Double(((double[])object)[n]);
        }
        if (object == null) {
            throw new NullPointerException("array == null");
        }
        throw Array.notAnArray(object);
    }

    public static boolean getBoolean(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof boolean[]) {
            return ((boolean[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static byte getByte(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static char getChar(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof char[]) {
            return ((char[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static double getDouble(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof double[]) {
            return ((double[])object)[n];
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        if (object instanceof char[]) {
            return ((char[])object)[n];
        }
        if (object instanceof float[]) {
            return ((float[])object)[n];
        }
        if (object instanceof int[]) {
            return ((int[])object)[n];
        }
        if (object instanceof long[]) {
            return ((long[])object)[n];
        }
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static float getFloat(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof float[]) {
            return ((float[])object)[n];
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        if (object instanceof char[]) {
            return ((char[])object)[n];
        }
        if (object instanceof int[]) {
            return ((int[])object)[n];
        }
        if (object instanceof long[]) {
            return ((long[])object)[n];
        }
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static int getInt(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof int[]) {
            return ((int[])object)[n];
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        if (object instanceof char[]) {
            return ((char[])object)[n];
        }
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static int getLength(Object object) {
        if (object instanceof Object[]) {
            return ((Object[])object).length;
        }
        if (object instanceof boolean[]) {
            return ((boolean[])object).length;
        }
        if (object instanceof byte[]) {
            return ((byte[])object).length;
        }
        if (object instanceof char[]) {
            return ((char[])object).length;
        }
        if (object instanceof double[]) {
            return ((double[])object).length;
        }
        if (object instanceof float[]) {
            return ((float[])object).length;
        }
        if (object instanceof int[]) {
            return ((int[])object).length;
        }
        if (object instanceof long[]) {
            return ((long[])object).length;
        }
        if (object instanceof short[]) {
            return ((short[])object).length;
        }
        throw Array.badArray(object);
    }

    public static long getLong(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof long[]) {
            return ((long[])object)[n];
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        if (object instanceof char[]) {
            return ((char[])object)[n];
        }
        if (object instanceof int[]) {
            return ((int[])object)[n];
        }
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        throw Array.badArray(object);
    }

    public static short getShort(Object object, int n) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof short[]) {
            return ((short[])object)[n];
        }
        if (object instanceof byte[]) {
            return ((byte[])object)[n];
        }
        throw Array.badArray(object);
    }

    private static IllegalArgumentException incompatibleType(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Array has incompatible type: ");
        stringBuilder.append(object.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static Object newArray(Class<?> class_, int n) throws NegativeArraySizeException {
        if (!class_.isPrimitive()) {
            return Array.createObjectArray(class_, n);
        }
        if (class_ == Character.TYPE) {
            return new char[n];
        }
        if (class_ == Integer.TYPE) {
            return new int[n];
        }
        if (class_ == Byte.TYPE) {
            return new byte[n];
        }
        if (class_ == Boolean.TYPE) {
            return new boolean[n];
        }
        if (class_ == Short.TYPE) {
            return new short[n];
        }
        if (class_ == Long.TYPE) {
            return new long[n];
        }
        if (class_ == Float.TYPE) {
            return new float[n];
        }
        if (class_ == Double.TYPE) {
            return new double[n];
        }
        if (class_ == Void.TYPE) {
            throw new IllegalArgumentException("Can't allocate an array of void");
        }
        throw new AssertionError();
    }

    public static Object newInstance(Class<?> class_, int n) throws NegativeArraySizeException {
        return Array.newArray(class_, n);
    }

    public static Object newInstance(Class<?> serializable, int ... arrn) throws IllegalArgumentException, NegativeArraySizeException {
        if (arrn.length > 0 && arrn.length <= 255) {
            if (serializable != Void.TYPE) {
                if (serializable != null) {
                    return Array.createMultiArray(serializable, arrn);
                }
                throw new NullPointerException("componentType == null");
            }
            throw new IllegalArgumentException("Can't allocate an array of void");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Bad number of dimensions: ");
        ((StringBuilder)serializable).append(arrn.length);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private static IllegalArgumentException notAnArray(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not an array: ");
        stringBuilder.append(object.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static void set(Object object, int n, Object object2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block19 : {
            block22 : {
                block21 : {
                    block20 : {
                        if (!object.getClass().isArray()) break block19;
                        if (!(object instanceof Object[])) break block20;
                        if (object2 != null && !object.getClass().getComponentType().isInstance(object2)) {
                            throw Array.incompatibleType(object);
                        }
                        ((Object[])object)[n] = object2;
                        break block21;
                    }
                    if (object2 == null) break block22;
                    if (object2 instanceof Boolean) {
                        Array.setBoolean(object, n, (Boolean)object2);
                    } else if (object2 instanceof Byte) {
                        Array.setByte(object, n, (Byte)object2);
                    } else if (object2 instanceof Character) {
                        Array.setChar(object, n, ((Character)object2).charValue());
                    } else if (object2 instanceof Short) {
                        Array.setShort(object, n, (Short)object2);
                    } else if (object2 instanceof Integer) {
                        Array.setInt(object, n, (Integer)object2);
                    } else if (object2 instanceof Long) {
                        Array.setLong(object, n, (Long)object2);
                    } else if (object2 instanceof Float) {
                        Array.setFloat(object, n, ((Float)object2).floatValue());
                    } else if (object2 instanceof Double) {
                        Array.setDouble(object, n, (Double)object2);
                    }
                }
                return;
            }
            throw new IllegalArgumentException("Primitive array can't take null values.");
        }
        throw Array.notAnArray(object);
    }

    public static void setBoolean(Object object, int n, boolean bl) {
        if (object instanceof boolean[]) {
            ((boolean[])object)[n] = bl;
            return;
        }
        throw Array.badArray(object);
    }

    public static void setByte(Object object, int n, byte by) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block8 : {
            block3 : {
                block7 : {
                    block6 : {
                        block5 : {
                            block4 : {
                                block2 : {
                                    if (!(object instanceof byte[])) break block2;
                                    ((byte[])object)[n] = by;
                                    break block3;
                                }
                                if (!(object instanceof double[])) break block4;
                                ((double[])object)[n] = by;
                                break block3;
                            }
                            if (!(object instanceof float[])) break block5;
                            ((float[])object)[n] = by;
                            break block3;
                        }
                        if (!(object instanceof int[])) break block6;
                        ((int[])object)[n] = by;
                        break block3;
                    }
                    if (!(object instanceof long[])) break block7;
                    ((long[])object)[n] = by;
                    break block3;
                }
                if (!(object instanceof short[])) break block8;
                ((short[])object)[n] = by;
            }
            return;
        }
        throw Array.badArray(object);
    }

    public static void setChar(Object object, int n, char c) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block7 : {
            block3 : {
                block6 : {
                    block5 : {
                        block4 : {
                            block2 : {
                                if (!(object instanceof char[])) break block2;
                                ((char[])object)[n] = c;
                                break block3;
                            }
                            if (!(object instanceof double[])) break block4;
                            ((double[])object)[n] = c;
                            break block3;
                        }
                        if (!(object instanceof float[])) break block5;
                        ((float[])object)[n] = c;
                        break block3;
                    }
                    if (!(object instanceof int[])) break block6;
                    ((int[])object)[n] = c;
                    break block3;
                }
                if (!(object instanceof long[])) break block7;
                ((long[])object)[n] = c;
            }
            return;
        }
        throw Array.badArray(object);
    }

    public static void setDouble(Object object, int n, double d) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (object instanceof double[]) {
            ((double[])object)[n] = d;
            return;
        }
        throw Array.badArray(object);
    }

    public static void setFloat(Object object, int n, float f) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block4 : {
            block3 : {
                block2 : {
                    if (!(object instanceof float[])) break block2;
                    ((float[])object)[n] = f;
                    break block3;
                }
                if (!(object instanceof double[])) break block4;
                ((double[])object)[n] = f;
            }
            return;
        }
        throw Array.badArray(object);
    }

    public static void setInt(Object object, int n, int n2) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block6 : {
            block3 : {
                block5 : {
                    block4 : {
                        block2 : {
                            if (!(object instanceof int[])) break block2;
                            ((int[])object)[n] = n2;
                            break block3;
                        }
                        if (!(object instanceof double[])) break block4;
                        ((double[])object)[n] = n2;
                        break block3;
                    }
                    if (!(object instanceof float[])) break block5;
                    ((float[])object)[n] = n2;
                    break block3;
                }
                if (!(object instanceof long[])) break block6;
                ((long[])object)[n] = n2;
            }
            return;
        }
        throw Array.badArray(object);
    }

    public static void setLong(Object object, int n, long l) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (!(object instanceof long[])) break block2;
                        ((long[])object)[n] = l;
                        break block3;
                    }
                    if (!(object instanceof double[])) break block4;
                    ((double[])object)[n] = l;
                    break block3;
                }
                if (!(object instanceof float[])) break block5;
                ((float[])object)[n] = l;
            }
            return;
        }
        throw Array.badArray(object);
    }

    public static void setShort(Object object, int n, short s) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        block7 : {
            block3 : {
                block6 : {
                    block5 : {
                        block4 : {
                            block2 : {
                                if (!(object instanceof short[])) break block2;
                                ((short[])object)[n] = s;
                                break block3;
                            }
                            if (!(object instanceof double[])) break block4;
                            ((double[])object)[n] = s;
                            break block3;
                        }
                        if (!(object instanceof float[])) break block5;
                        ((float[])object)[n] = s;
                        break block3;
                    }
                    if (!(object instanceof int[])) break block6;
                    ((int[])object)[n] = s;
                    break block3;
                }
                if (!(object instanceof long[])) break block7;
                ((long[])object)[n] = s;
            }
            return;
        }
        throw Array.badArray(object);
    }
}

