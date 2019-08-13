/*
 * Decompiled with CFR 0.145.
 */
package libcore.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import libcore.reflect.ListOfTypes;
import libcore.reflect.ParameterizedTypeImpl;
import libcore.util.EmptyArray;

public final class Types {
    private static final Map<Class<?>, String> PRIMITIVE_TO_SIGNATURE = new HashMap(9);

    static {
        PRIMITIVE_TO_SIGNATURE.put(Byte.TYPE, "B");
        PRIMITIVE_TO_SIGNATURE.put(Character.TYPE, "C");
        PRIMITIVE_TO_SIGNATURE.put(Short.TYPE, "S");
        PRIMITIVE_TO_SIGNATURE.put(Integer.TYPE, "I");
        PRIMITIVE_TO_SIGNATURE.put(Long.TYPE, "J");
        PRIMITIVE_TO_SIGNATURE.put(Float.TYPE, "F");
        PRIMITIVE_TO_SIGNATURE.put(Double.TYPE, "D");
        PRIMITIVE_TO_SIGNATURE.put(Void.TYPE, "V");
        PRIMITIVE_TO_SIGNATURE.put(Boolean.TYPE, "Z");
    }

    private Types() {
    }

    public static void appendArrayGenericType(StringBuilder stringBuilder, Type[] arrtype) {
        if (arrtype.length == 0) {
            return;
        }
        Types.appendGenericType(stringBuilder, arrtype[0]);
        for (int i = 1; i < arrtype.length; ++i) {
            stringBuilder.append(',');
            Types.appendGenericType(stringBuilder, arrtype[i]);
        }
    }

    public static void appendGenericType(StringBuilder stringBuilder, Type arrstring) {
        block23 : {
            block25 : {
                block24 : {
                    block22 : {
                        if (!(arrstring instanceof TypeVariable)) break block22;
                        stringBuilder.append(((TypeVariable)arrstring).getName());
                        break block23;
                    }
                    if (!(arrstring instanceof ParameterizedType)) break block24;
                    stringBuilder.append(arrstring.toString());
                    break block23;
                }
                if (!(arrstring instanceof GenericArrayType)) break block25;
                Types.appendGenericType(stringBuilder, ((GenericArrayType)arrstring).getGenericComponentType());
                stringBuilder.append("[]");
                break block23;
            }
            if (!(arrstring instanceof Class)) break block23;
            if ((arrstring = (Class)arrstring).isArray()) {
                int n;
                int n2;
                if ((arrstring = arrstring.getName().split("\\["))[n = arrstring.length - 1].length() > 1) {
                    stringBuilder.append(arrstring[n].substring(1, arrstring[n].length() - 1));
                } else {
                    n2 = arrstring[n].charAt(0);
                    if (n2 == 73) {
                        stringBuilder.append("int");
                    } else if (n2 == 66) {
                        stringBuilder.append("byte");
                    } else if (n2 == 74) {
                        stringBuilder.append("long");
                    } else if (n2 == 70) {
                        stringBuilder.append("float");
                    } else if (n2 == 68) {
                        stringBuilder.append("double");
                    } else if (n2 == 83) {
                        stringBuilder.append("short");
                    } else if (n2 == 67) {
                        stringBuilder.append("char");
                    } else if (n2 == 90) {
                        stringBuilder.append("boolean");
                    } else if (n2 == 86) {
                        stringBuilder.append("void");
                    }
                }
                for (n2 = 0; n2 < n; ++n2) {
                    stringBuilder.append("[]");
                }
            } else {
                stringBuilder.append(arrstring.getName());
            }
        }
    }

    public static void appendTypeName(StringBuilder stringBuilder, Class<?> class_) {
        int n = 0;
        while (class_.isArray()) {
            class_ = class_.getComponentType();
            ++n;
        }
        stringBuilder.append(class_.getName());
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("[]");
        }
    }

    public static String getSignature(Class<?> class_) {
        CharSequence charSequence = PRIMITIVE_TO_SIGNATURE.get(class_);
        if (charSequence != null) {
            return charSequence;
        }
        if (class_.isArray()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[");
            ((StringBuilder)charSequence).append(Types.getSignature(class_.getComponentType()));
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("L");
        ((StringBuilder)charSequence).append(class_.getName());
        ((StringBuilder)charSequence).append(";");
        return ((StringBuilder)charSequence).toString();
    }

    public static Type getType(Type type) {
        if (type instanceof ParameterizedTypeImpl) {
            return ((ParameterizedTypeImpl)type).getResolvedType();
        }
        return type;
    }

    public static Type[] getTypeArray(ListOfTypes arrtype, boolean bl) {
        block1 : {
            if (arrtype.length() == 0) {
                return EmptyArray.TYPE;
            }
            arrtype = arrtype.getResolvedTypes();
            if (!bl) break block1;
            arrtype = (Type[])arrtype.clone();
        }
        return arrtype;
    }

    public static String toString(Class<?>[] arrclass) {
        if (arrclass.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Types.appendTypeName(stringBuilder, arrclass[0]);
        for (int i = 1; i < arrclass.length; ++i) {
            stringBuilder.append(',');
            Types.appendTypeName(stringBuilder, arrclass[i]);
        }
        return stringBuilder.toString();
    }
}

