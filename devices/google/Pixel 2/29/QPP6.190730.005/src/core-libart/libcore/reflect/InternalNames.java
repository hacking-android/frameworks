/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.Array;

public final class InternalNames {
    private InternalNames() {
    }

    public static Class<?> getClass(ClassLoader object, String object2) {
        if (((String)object2).startsWith("[")) {
            return Array.newInstance(InternalNames.getClass((ClassLoader)object, ((String)object2).substring(1)), 0).getClass();
        }
        if (((String)object2).equals("Z")) {
            return Boolean.TYPE;
        }
        if (((String)object2).equals("B")) {
            return Byte.TYPE;
        }
        if (((String)object2).equals("S")) {
            return Short.TYPE;
        }
        if (((String)object2).equals("I")) {
            return Integer.TYPE;
        }
        if (((String)object2).equals("J")) {
            return Long.TYPE;
        }
        if (((String)object2).equals("F")) {
            return Float.TYPE;
        }
        if (((String)object2).equals("D")) {
            return Double.TYPE;
        }
        if (((String)object2).equals("C")) {
            return Character.TYPE;
        }
        if (((String)object2).equals("V")) {
            return Void.TYPE;
        }
        object2 = ((String)object2).substring(1, ((String)object2).length() - 1).replace('/', '.');
        try {
            object = ((ClassLoader)object).loadClass((String)object2);
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            object2 = new NoClassDefFoundError((String)object2);
            ((Throwable)object2).initCause(classNotFoundException);
            throw object2;
        }
    }

    public static String getInternalName(Class<?> class_) {
        if (class_.isArray()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            stringBuilder.append(InternalNames.getInternalName(class_.getComponentType()));
            return stringBuilder.toString();
        }
        if (class_ == Boolean.TYPE) {
            return "Z";
        }
        if (class_ == Byte.TYPE) {
            return "B";
        }
        if (class_ == Short.TYPE) {
            return "S";
        }
        if (class_ == Integer.TYPE) {
            return "I";
        }
        if (class_ == Long.TYPE) {
            return "J";
        }
        if (class_ == Float.TYPE) {
            return "F";
        }
        if (class_ == Double.TYPE) {
            return "D";
        }
        if (class_ == Character.TYPE) {
            return "C";
        }
        if (class_ == Void.TYPE) {
            return "V";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('L');
        stringBuilder.append(class_.getName().replace('.', '/'));
        stringBuilder.append(';');
        return stringBuilder.toString();
    }
}

