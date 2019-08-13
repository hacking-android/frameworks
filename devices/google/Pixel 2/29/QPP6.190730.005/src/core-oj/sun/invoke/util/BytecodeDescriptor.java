/*
 * Decompiled with CFR 0.145.
 */
package sun.invoke.util;

import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sun.invoke.util.Wrapper;

public class BytecodeDescriptor {
    private BytecodeDescriptor() {
    }

    private static void parseError(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad signature: ");
        stringBuilder.append(string);
        stringBuilder.append(": ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static List<Class<?>> parseMethod(String string, int n, int n2, ClassLoader object) {
        Object object2 = object;
        if (object == null) {
            object2 = ClassLoader.getSystemClassLoader();
        }
        int[] arrn = new int[]{n};
        object = new ArrayList();
        if (arrn[0] < n2 && string.charAt(arrn[0]) == '(') {
            arrn[0] = arrn[0] + 1;
            while (arrn[0] < n2 && string.charAt(arrn[0]) != ')') {
                Class<?> class_ = BytecodeDescriptor.parseSig(string, arrn, n2, (ClassLoader)object2);
                if (class_ == null || class_ == Void.TYPE) {
                    BytecodeDescriptor.parseError(string, "bad argument type");
                }
                ((ArrayList)object).add(class_);
            }
            arrn[0] = arrn[0] + 1;
        } else {
            BytecodeDescriptor.parseError(string, "not a method type");
        }
        object2 = BytecodeDescriptor.parseSig(string, arrn, n2, (ClassLoader)object2);
        if (object2 == null || arrn[0] != n2) {
            BytecodeDescriptor.parseError(string, "bad return type");
        }
        ((ArrayList)object).add(object2);
        return object;
    }

    public static List<Class<?>> parseMethod(String string, ClassLoader classLoader) {
        return BytecodeDescriptor.parseMethod(string, 0, string.length(), classLoader);
    }

    private static Class<?> parseSig(String object, int[] object2, int n, ClassLoader classLoader) {
        if (object2[0] == n) {
            return null;
        }
        int n2 = object2[0];
        object2[0] = n2 + 1;
        char c = object.charAt(n2);
        if (c == 'L') {
            n = object2[0];
            n2 = object.indexOf(59, n);
            if (n2 < 0) {
                return null;
            }
            object2[0] = n2 + 1;
            object = object.substring(n, n2).replace('/', '.');
            try {
                object2 = classLoader.loadClass((String)object);
                return object2;
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new TypeNotPresentException((String)object, classNotFoundException);
            }
        }
        if (c == '[') {
            object2 = BytecodeDescriptor.parseSig((String)object, (int[])object2, n, classLoader);
            object = object2;
            if (object2 != null) {
                object = Array.newInstance(object2, 0).getClass();
            }
            return object;
        }
        return Wrapper.forBasicType(c).primitiveType();
    }

    public static String unparse(Class<?> class_) {
        StringBuilder stringBuilder = new StringBuilder();
        BytecodeDescriptor.unparseSig(class_, stringBuilder);
        return stringBuilder.toString();
    }

    public static String unparse(Object object) {
        if (object instanceof Class) {
            return BytecodeDescriptor.unparse((Class)object);
        }
        if (object instanceof MethodType) {
            return BytecodeDescriptor.unparse((MethodType)object);
        }
        return (String)object;
    }

    public static String unparse(MethodType methodType) {
        return BytecodeDescriptor.unparseMethod(methodType.returnType(), methodType.parameterList());
    }

    public static String unparseMethod(Class<?> class_, List<Class<?>> object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        object = object.iterator();
        while (object.hasNext()) {
            BytecodeDescriptor.unparseSig((Class)object.next(), stringBuilder);
        }
        stringBuilder.append(')');
        BytecodeDescriptor.unparseSig(class_, stringBuilder);
        return stringBuilder.toString();
    }

    private static void unparseSig(Class<?> class_, StringBuilder stringBuilder) {
        char c = Wrapper.forBasicType(class_).basicTypeChar();
        if (c != 'L') {
            stringBuilder.append(c);
        } else {
            boolean bl = class_.isArray() ^ true;
            if (bl) {
                stringBuilder.append('L');
            }
            stringBuilder.append(class_.getName().replace('.', '/'));
            if (bl) {
                stringBuilder.append(';');
            }
        }
    }
}

